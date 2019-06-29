package com.neu.ccwebapp.service;

import com.neu.ccwebapp.domain.Image;
import com.neu.ccwebapp.exceptions.FileStorageException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Profile("!cloud")
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
    public Image store(MultipartFile file, UUID uuid)
    {
        String fileName = uuid.toString()+"_"+StringUtils.cleanPath(file.getOriginalFilename());
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        try
        {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e)
        {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
        Image image = new Image();
        image.setImageId(uuid);
        image.setFileName(fileName);
        image.setUrl(targetLocation.toUri().toString());
        return image;
    }

    @Override
    public void deleteImage(Image image)
    {
        try
        {
            Files.deleteIfExists(Paths.get(new URI(image.getUrl())));
        }
        catch (Exception e)
        {
            throw new FileStorageException("Error while deleting file " + image.getFileName() + ". Please try again!", e);
        }
    }
}
