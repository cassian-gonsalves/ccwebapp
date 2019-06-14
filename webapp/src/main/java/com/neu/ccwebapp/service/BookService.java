package com.neu.ccwebapp.service;

import com.neu.ccwebapp.domain.Book;
import com.neu.ccwebapp.exceptions.BookNotFoundException;

import java.util.List;
import java.util.UUID;

public interface BookService {


    List<Book> getBook();
    Book createBook(Book book);
    void updateBook(Book book) throws BookNotFoundException;
    Book getBookById(UUID id) throws BookNotFoundException;
    void deleteBook(UUID id) throws BookNotFoundException;

}
