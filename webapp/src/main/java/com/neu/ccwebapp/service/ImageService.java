package com.neu.ccwebapp.service;

import com.neu.ccwebapp.domain.Image;
import com.neu.ccwebapp.exceptions.BookNotFoundException;

import java.util.UUID;

public interface ImageService  {


    public void addBookImage(UUID uuid, Image image) throws BookNotFoundException;

}
