package com.neu.ccwebapp.service;

import com.neu.ccwebapp.domain.Image;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService
{
    String store(MultipartFile file);
}
