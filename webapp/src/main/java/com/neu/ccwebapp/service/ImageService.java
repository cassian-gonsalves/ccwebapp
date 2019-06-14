package com.neu.ccwebapp.service;

import com.neu.ccwebapp.domain.Image;
import com.neu.ccwebapp.exceptions.BookNotFoundException;
import com.neu.ccwebapp.exceptions.ImageNotFoundException;

import java.util.UUID;

public interface ImageService  {


    public void addBookImage(UUID uuid, Image image) throws BookNotFoundException;

    public Image getImageById(UUID idBook,UUID idImage) throws ImageNotFoundException;

    public void updateImage(UUID idBook,UUID idImage, Image image) throws ImageNotFoundException;

    public void deleteImage(UUID idBook,UUID idImage) throws ImageNotFoundException;






}
