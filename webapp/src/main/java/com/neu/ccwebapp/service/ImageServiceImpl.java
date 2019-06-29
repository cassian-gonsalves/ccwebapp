package com.neu.ccwebapp.service;

import com.neu.ccwebapp.domain.Book;
import com.neu.ccwebapp.domain.Image;
import com.neu.ccwebapp.exceptions.BookNotFoundException;
import com.neu.ccwebapp.exceptions.ImageExistsException;
import com.neu.ccwebapp.exceptions.ImageNotFoundException;
import com.neu.ccwebapp.exceptions.InvalidFileException;
import com.neu.ccwebapp.repository.BookRepository;
import com.neu.ccwebapp.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    StorageService storageService;

    @Value("${spring.profiles.active:Unknown}")
    private String activeProfile;

    @Override
    public Image addBookImage(UUID uuid, MultipartFile file) throws BookNotFoundException, ImageExistsException, InvalidFileException {
        String fileType = file.getContentType();
        if(!fileType.equals("image/jpeg") && !fileType.equals("image/png"))
        {
            throw new InvalidFileException("Only images of .png, .jpg or .jpeg formats are accepted");
        }
        Optional<Book> book = bookRepository.findById(uuid);
        if(book.isEmpty())
        {
            throw new BookNotFoundException("Could not find book with id : "+uuid);
        }
        Optional<Image> existingImage = imageRepository.findById(uuid);
        if(existingImage.isPresent())
        {
            throw new ImageExistsException("Image exists for book with id : "+uuid+". Update the book image using the PUT request.");
        }
        Image image = storageService.store(file,uuid);
        image.setBook(book.get());
        imageRepository.save(image);
        return image;
    }

    @Override
    public Image getImageById(UUID bookId, UUID imageId) throws ImageNotFoundException, BookNotFoundException
    {
        Optional<Book> book = bookRepository.findById(bookId);
        if(book.isEmpty())
        {
            throw new BookNotFoundException("Could not find book with id : "+bookId);
        }

        Optional<Image> bookImage = imageRepository.findById(imageId);
        if(bookId.compareTo(imageId)!=0 || bookImage.isEmpty())
        {
            throw new ImageNotFoundException("The book (id : "+bookId+") does not have an image with id : "+imageId);
        }
        Image image = bookImage.get();
        if(activeProfile.equals("cloud"))
        {
            image.setUrl(((AWSS3StorageService)storageService).generatePresignedURL(image.getFileName()));
        }
        return image;
    }


    @Override
    public void updateImage(UUID bookId, UUID imageId, MultipartFile file) throws ImageNotFoundException, BookNotFoundException, InvalidFileException
    {
        String fileType = file.getContentType();
        if(!fileType.equals("image/jpeg") && !fileType.equals("image/png"))
        {
            throw new InvalidFileException("Only images of .png, .jpg or .jpeg formats are accepted");
        }
        Optional<Book> book = bookRepository.findById(bookId);
        if(book.isEmpty())
        {
            throw new BookNotFoundException("Could not find book with id : "+bookId);
        }
        Optional<Image> bookImage = imageRepository.findById(imageId);
        if(bookId.compareTo(imageId)!=0 || bookImage.isEmpty())
        {
            throw new ImageNotFoundException("The book (id : "+bookId+") does not have an image with id : "+imageId);
        }
        storageService.deleteImage(bookImage.get());
        Image image = storageService.store(file,bookId);
        image.setBook(book.get());
        imageRepository.save(image);
    }

    @Override
    @Transactional
    public void deleteImage(UUID bookId, UUID imageId) throws ImageNotFoundException, BookNotFoundException {
        Optional<Book> book = bookRepository.findById(bookId);
        if(book.isEmpty())
        {
            throw new BookNotFoundException("Could not find book with id : "+bookId);
        }
        Optional<Image> bookImage = imageRepository.findById(imageId);
        if(bookId.compareTo(imageId)!=0 || bookImage.isEmpty())
        {
            throw new ImageNotFoundException("The book (id : "+bookId+") does not have an image with id : "+imageId);
        }
        storageService.deleteImage(bookImage.get());
        imageRepository.deleteById(imageId);
    }

}
