package com.neu.ccwebapp.service;

import com.neu.ccwebapp.domain.Book;
import com.neu.ccwebapp.exceptions.BookNotFoundException;
import com.neu.ccwebapp.exceptions.ImageNotFoundException;
import com.neu.ccwebapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ImageService imageService;

    @Value("${spring.profiles.active:Unknown}")
    private String activeProfile;

    @Override
    public List<Book> getBooks() throws BookNotFoundException, ImageNotFoundException {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().forEach(books::add);
        if(activeProfile.equals("cloud"))
        {
            for (int i = 0; i < books.size(); i++) {
                Book book = books.get(i);
                if(book.getImage()!=null)
                {
                    book.setImage(imageService.getImageById(book.getId(),book.getId()));
                }
                books.set(i,book);
            }
        }
        return books;
    }

    @Override
    public Book createBook(Book book)
    {
        bookRepository.save(book);
        return book;
    }

    @Override
    public void updateBook(Book book) throws BookNotFoundException
    {
        Optional<Book> existingBook = bookRepository.findById(book.getId());
        if(existingBook.isEmpty())
        {
            throw new BookNotFoundException("Could not find book with id : "+book.getId());
        }
        bookRepository.save(book);
    }

    @Override
    public Book getBookById(UUID id) throws BookNotFoundException, ImageNotFoundException {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isEmpty())
        {
            throw new BookNotFoundException("Could not find book with id : "+id);
        }
        Book book = optionalBook.get();
        if(activeProfile.equals("cloud") && book.getImage()!=null)
        {
            book.setImage(imageService.getImageById(book.getId(),book.getId()));
        }
        return book;
    }

    @Override
    public void deleteBook(UUID id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty())
        {
            throw new BookNotFoundException("Could not find book with id : "+id);
        }
        if(book.get().getImage()!=null)
        {
            try {
                imageService.deleteImage(id,id);
            } catch (ImageNotFoundException e) {
                e.printStackTrace();
            }
        }
        bookRepository.deleteById(id);
    }



}
