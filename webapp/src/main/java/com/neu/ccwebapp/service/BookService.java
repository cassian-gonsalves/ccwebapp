package com.neu.ccwebapp.service;

import com.neu.ccwebapp.domain.Book;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.Optional;

public interface BookService {


    public List<Book> getBook();
    public void createBook(Book book);
    public void updateBook(Book book);
    public Optional<Book> getBookById(String id) ;
    public void deleteBook(String id);

}
