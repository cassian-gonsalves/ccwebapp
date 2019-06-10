package com.neu.ccwebapp.service;

import com.neu.ccwebapp.domain.Book;
import com.neu.ccwebapp.domain.Image;
import com.neu.ccwebapp.exceptions.BookNotFoundException;
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

        if(uuid.equals(image.getIdBook()))
        {

                    
            imageRepository.save(image);
        }
    }
}
