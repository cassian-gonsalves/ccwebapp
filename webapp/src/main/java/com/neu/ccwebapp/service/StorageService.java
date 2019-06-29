package com.neu.ccwebapp.service;

import com.neu.ccwebapp.domain.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface StorageService
{
    Image store(MultipartFile file, UUID uuid);
    void deleteImage(Image image);
}
