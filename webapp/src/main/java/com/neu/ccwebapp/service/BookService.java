package com.neu.ccwebapp.service;

import com.neu.ccwebapp.domain.Book;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService {


    public List<Book> getBook();
    public Book createBook(Book book);
    public void updateBook(Book book);
    public Optional<Book> getBookById(UUID id) ;
    public void deleteBook(UUID id);

}
