package com.neu.ccwebapp.service;

import com.neu.ccwebapp.exceptions.FileStorageException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService implements StorageService
{
    ServletContext context;

    private final Path fileStorageLocation;

    public FileSystemStorageService()
    {
        //this.fileStorageLocation = Paths.get(context.getRealPath("resources/uploads"));
        this.fileStorageLocation = Paths.get("resources/uploads")
                .toAbsolutePath().normalize();
        try
        {
            Files.createDirectories(this.fileStorageLocation);
        }
        catch (Exception ex)
        {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String store(MultipartFile file)
    {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        try
        {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e)
        {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
        return targetLocation.toUri().toString();
    }
}
