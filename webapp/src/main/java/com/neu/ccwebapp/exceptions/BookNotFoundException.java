package com.neu.ccwebapp.exceptions;

public class BookNotFoundException extends Exception {
    public BookNotFoundException(String errorMessage)
    {
        super(errorMessage);
    }
}
