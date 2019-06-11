package com.neu.ccwebapp.service;

import com.neu.ccwebapp.domain.Book;
import com.neu.ccwebapp.domain.Image;
import com.neu.ccwebapp.exceptions.BookNotFoundException;
import com.neu.ccwebapp.exceptions.ImageNotFoundException;
import com.neu.ccwebapp.repository.BookRepository;
import com.neu.ccwebapp.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public void addBookImage(UUID uuid, Image image) throws BookNotFoundException {

        Optional<Book> book = bookRepository.findById(uuid);
        if(book.isEmpty()){
            throw new BookNotFoundException("Could not find book with id : "+image.getIdBook());
        }

        Optional<Image> existingImage = imageRepository.findById(uuid);

        if(existingImage.isEmpty())
        {
            imageRepository.save(image);
        }
    }


    @Override
    public Image getImageById(UUID idBook, UUID idImage) throws ImageNotFoundException {


        Optional<Image> existingImage = imageRepository.findById(idImage);

        if(existingImage.isEmpty())
        {
            throw new ImageNotFoundException("The book with given id does not have anImage");

        }

        return imageRepository.getOne(idImage);
    }


    @Override
    public void updateImage(UUID idBook, UUID idImage, Image image) throws ImageNotFoundException {

        Optional<Image> existingImage = imageRepository.findById(idImage);

        if(existingImage.isEmpty())
        {
            throw new ImageNotFoundException("The book with given id does not have an Image");

        }

        imageRepository.save(image);


    }

    @Override
    public void deleteImage(UUID idBook, UUID idImage) throws ImageNotFoundException {


    }
}
