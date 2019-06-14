package com.neu.ccwebapp.service;

import com.neu.ccwebapp.domain.Image;
import com.neu.ccwebapp.exceptions.BookNotFoundException;
import com.neu.ccwebapp.exceptions.ImageExistsException;
import com.neu.ccwebapp.exceptions.ImageNotFoundException;
import com.neu.ccwebapp.exceptions.InvalidFileException;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ImageService  {


    public Image addBookImage(UUID uuid, MultipartFile image) throws BookNotFoundException, ImageExistsException, InvalidFileException;

    public Image getImageById(UUID idBook,UUID idImage) throws ImageNotFoundException, BookNotFoundException;

    public void updateImage(UUID idBook,UUID idImage, MultipartFile image) throws ImageNotFoundException, BookNotFoundException, InvalidFileException;

    public void deleteImage(UUID idBook,UUID idImage) throws ImageNotFoundException, BookNotFoundException;






}
