package com.neu.ccwebapp.service;

import com.neu.ccwebapp.domain.Book;
import com.neu.ccwebapp.exceptions.BookNotFoundException;
import com.neu.ccwebapp.exceptions.ImageNotFoundException;

import java.util.List;
import java.util.UUID;

public interface BookService {


    List<Book> getBooks() throws BookNotFoundException, ImageNotFoundException;
    Book createBook(Book book);
    void updateBook(Book book) throws BookNotFoundException;
    Book getBookById(UUID id) throws BookNotFoundException, ImageNotFoundException;
    void deleteBook(UUID id) throws BookNotFoundException;

}
