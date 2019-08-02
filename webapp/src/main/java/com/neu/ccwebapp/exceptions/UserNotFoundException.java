package com.neu.ccwebapp.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String username)
    {
        super("A user with username "+username+" does not exist.");
    }
}
