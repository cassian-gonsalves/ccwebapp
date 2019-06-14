package com.neu.ccwebapp.service;

import com.neu.ccwebapp.domain.Book;
import com.neu.ccwebapp.exceptions.BookNotFoundException;
import com.neu.ccwebapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;


    @Override
    public List<Book> getBook() {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().forEach(books::add);
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
    public Book getBookById(UUID id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty())
        {
            throw new BookNotFoundException("Could not find book with id : "+id);
        }
        return book.get();
    }

    @Override
    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }



}
