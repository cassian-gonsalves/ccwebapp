package com.neu.ccwebapp.web;

import com.neu.ccwebapp.domain.Book;
import com.neu.ccwebapp.domain.User;
import com.neu.ccwebapp.service.BookService;
import com.neu.ccwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class AppController
{
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BookService bookService;

    @PostMapping("/user/register")
    public void registerUser(@RequestBody User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.registerUser(user);
    }



    @GetMapping("/book")
    public List<Book> getBook()
    {
        return bookService.getBook();

    }


    @PostMapping("/book")
    public Book createBook(@RequestBody Book book)
    {
        return bookService.createBook(book);

    }

    @PutMapping("/book")
    public void updateBook(@RequestBody Book book)
    {
        bookService.updateBook(book);

    }

    @GetMapping("/book/{id}")
    public Optional<Book> getBookById(@PathVariable UUID id)
    {
        return bookService.getBookById(id);

    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable UUID id)
    {
        bookService.deleteBook(id);

    }









}
