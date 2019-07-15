package com.neu.ccwebapp.web;

import com.neu.ccwebapp.domain.Book;
import com.neu.ccwebapp.domain.CurrentTime;
import com.neu.ccwebapp.domain.Image;
import com.neu.ccwebapp.domain.User;
import com.neu.ccwebapp.exceptions.*;
import com.neu.ccwebapp.service.BookService;
import com.neu.ccwebapp.service.ImageService;
import com.neu.ccwebapp.service.UserService;
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

    @GetMapping("/")
    public CurrentTime getCurrentTime() {
        return new CurrentTime();
    }

    @PostMapping("/user/register")
    public void registerUser(@Valid @RequestBody User user) {
        try
        {
            userService.registerUser(user);
        }
        catch (UserExistsException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
        }
    }

    @GetMapping("/bookDhaanshri")
    public List<Book> getBook()
    {
        List<Book> books = new ArrayList<>();
        try
        {
            books = bookService.getBooks();
        }
        catch (BookNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e);
        }
        catch (ImageNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e);
        }
        return books;
    }

    @PostMapping("/book")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(book));
    }

    @PutMapping("/book")
    public ResponseEntity updateBook(@Valid @RequestBody Book book)
    {
        try
        {
            bookService.updateBook(book);
        }
        catch (BookNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@Valid @PathVariable UUID id)
    {
        try
        {
            return bookService.getBookById(id);
        }
        catch (BookNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
        catch (ImageNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e);
        }
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity deleteBook(@Valid @PathVariable UUID id) {
        try
        {
            bookService.deleteBook(id);
        }
        catch (BookNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    @PostMapping("/book/{bookId}/image")
    public Image addBookImage(@Valid @PathVariable UUID bookId, @RequestParam("file") MultipartFile file)
    {
        Image image;
        try
        {
            image = imageService.addBookImage(bookId, file);
        }
        catch (BookNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
        catch (ImageExistsException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
        }
        catch (InvalidFileException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
        }
        return image;
    }

    @GetMapping("/book/{bookId}/image/{imageId}")
    public Image getImageById(@Valid @PathVariable UUID bookId, @Valid @PathVariable UUID imageId)
    {
        try
        {
            return imageService.getImageById(bookId,imageId);
        }
        catch (ImageNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
        catch (BookNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }


    @PutMapping("/book/{bookId}/image/{imageId}")
    public ResponseEntity updateImage(@Valid @PathVariable UUID bookId, @Valid @PathVariable UUID imageId, @RequestParam("file") MultipartFile file)
    {
        try
        {
            imageService.updateImage(bookId, imageId,file);
        }
        catch (ImageNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
        catch (BookNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
        catch (InvalidFileException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/book/{bookId}/image/{imageId}")
    public ResponseEntity deleteImage(@Valid @PathVariable UUID bookId, @Valid @PathVariable UUID imageId)
    {
        try
        {
            imageService.deleteImage(bookId,imageId);
        }
        catch (ImageNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
        catch (BookNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
