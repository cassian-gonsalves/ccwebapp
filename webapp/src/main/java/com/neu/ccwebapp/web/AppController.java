package com.neu.ccwebapp.web;

import com.neu.ccwebapp.domain.*;
import com.neu.ccwebapp.exceptions.*;
import com.neu.ccwebapp.service.BookService;
import com.neu.ccwebapp.service.ImageService;
import com.neu.ccwebapp.service.UserService;
import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class AppController {
    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private StatsDClient statsDClient;

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @GetMapping("/")
    public CurrentTime getCurrentTime() {
        statsDClient.incrementCounter("api.current.time");
        return new CurrentTime();
    }

    @PostMapping("/user/register")
    public void registerUser(@Valid @RequestBody User user) {
        statsDClient.incrementCounter("api.create.user");
        try
        {
            userService.registerUser(user);
        }
        catch (UserExistsException e)
        {
            logger.error("User already exists.",e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
        }
    }

    @GetMapping("/book")
    public List<Book> getBook()
    {
        statsDClient.incrementCounter("api.list.books");
        List<Book> books = new ArrayList<>();
        try
        {
            books = bookService.getBooks();
        }
        catch (BookNotFoundException e)
        {
            logger.error("Book not found.",e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e);
        }
        catch (ImageNotFoundException e)
        {
            logger.error("Image for the book was not found.",e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e);
        }
        return books;
    }

    @PostMapping("/book")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book)
    {
        statsDClient.incrementCounter("api.create.book");
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(book));
    }

    @PutMapping("/book")
    public ResponseEntity updateBook(@Valid @RequestBody Book book)
    {
        statsDClient.incrementCounter("api.update.book");
        try
        {
            bookService.updateBook(book);
        }
        catch (BookNotFoundException e)
        {
            logger.error("Book not found.",e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@Valid @PathVariable UUID id)
    {
        statsDClient.incrementCounter("api.get.book");
        try
        {
            return bookService.getBookById(id);
        }
        catch (BookNotFoundException e)
        {
            logger.error("Book not found.",e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
        catch (ImageNotFoundException e)
        {
            logger.error("Image for the book was not found.",e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e);
        }
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity deleteBook(@Valid @PathVariable UUID id)
    {
        statsDClient.incrementCounter("api.delete.book");
        try
        {
            bookService.deleteBook(id);
        }
        catch (BookNotFoundException e)
        {
            logger.error("Book not found.",e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    @PostMapping("/book/{bookId}/image")
    public Image addBookImage(@Valid @PathVariable UUID bookId, @RequestParam("file") MultipartFile file)
    {
        statsDClient.incrementCounter("api.add.image");
        Image image;
        try
        {
            image = imageService.addBookImage(bookId, file);
        }
        catch (BookNotFoundException e)
        {
            logger.error("Book not found.",e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
        catch (ImageExistsException e)
        {
            logger.error("Image for the book already exits.",e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
        }
        catch (InvalidFileException e)
        {
            logger.error("Invalid file type.",e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
        }
        return image;
    }

    @GetMapping("/book/{bookId}/image/{imageId}")
    public Image getImageById(@Valid @PathVariable UUID bookId, @Valid @PathVariable UUID imageId)
    {
        statsDClient.incrementCounter("api.get.image");
        try
        {
            return imageService.getImageById(bookId,imageId);
        }
        catch (ImageNotFoundException e)
        {
            logger.error("Image for the book was not found.",e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
        catch (BookNotFoundException e)
        {
            logger.error("Book not found.",e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }


    @PutMapping("/book/{bookId}/image/{imageId}")
    public ResponseEntity updateImage(@Valid @PathVariable UUID bookId, @Valid @PathVariable UUID imageId, @RequestParam("file") MultipartFile file)
    {
        statsDClient.incrementCounter("api.update.image");
        try
        {
            imageService.updateImage(bookId, imageId,file);
        }
        catch (ImageNotFoundException e)
        {
            logger.error("Image for the book was not found.",e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
        catch (BookNotFoundException e)
        {
            logger.error("Book not found.",e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
        catch (InvalidFileException e)
        {
            logger.error("Invalid file type.",e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/book/{bookId}/image/{imageId}")
    public ResponseEntity deleteImage(@Valid @PathVariable UUID bookId, @Valid @PathVariable UUID imageId)
    {
        statsDClient.incrementCounter("api.delete.image");
        try
        {
            imageService.deleteImage(bookId,imageId);
        }
        catch (ImageNotFoundException e)
        {
            logger.error("Image for the book was not found.",e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
        catch (BookNotFoundException e)
        {
            logger.error("Book not found.",e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/reset")
    public ResponseEntity resetPassword(@Valid @RequestBody ResetPassword user)
    {
        statsDClient.incrementCounter("api.reset.password");
        try
        {
            userService.resetPassword(user);
        }
        catch (UserNotFoundException e)
        {
            logger.error("User does not exist.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
