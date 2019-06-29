package com.neu.ccwebapp.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.neu.ccwebapp.domain.Image;
import com.neu.ccwebapp.exceptions.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Service
@Profile("cloud")
public class AWSS3StorageService implements StorageService
{
    @Value("${aws.s3.bucket.name}")
    private String s3BucketName;

    private static AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

    @Override
    public Image store(MultipartFile file, UUID uuid)
    {
        String fileName = uuid.toString()+"_"+file.getOriginalFilename();
        try
        {
            System.out.println("BUCKET NAME : "+s3BucketName);
            PutObjectRequest request = new PutObjectRequest(s3BucketName, fileName, multipartToFile(file,fileName));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            request.setMetadata(metadata);
            s3Client.putObject(request);
        }
        catch (Exception e)
        {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
        Image image = new Image();
        image.setUrl(generatePresignedURL(fileName));
        image.setFileName(fileName);
        image.setImageId(uuid);
        return image;
    }

    @Override
    public void deleteImage(Image image) {
        s3Client.deleteObject(new DeleteObjectRequest(s3BucketName, image.getFileName()));
    }

    private static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException
    {
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
        multipart.transferTo(convFile);
        return convFile;
    }

    public String generatePresignedURL(String objectKey)
    {
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(s3BucketName, objectKey)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }
}
