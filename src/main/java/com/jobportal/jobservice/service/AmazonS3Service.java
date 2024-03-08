package com.jobportal.jobservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.jobportal.jobservice.constants.Constants;
import com.jobportal.jobservice.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AmazonS3Service {
    @Autowired
    private AmazonS3 s3Client;

    @Value("${s3.bucket.name.company.files}")
    private String BUCKET_NAME;

    private final Logger logger = LoggerFactory.getLogger(AmazonS3Service.class);

    public String getObjectBase64(final String fileName) {
        try {
            Long start = System.currentTimeMillis();
            S3Object s3Object = s3Client.getObject(BUCKET_NAME, fileName);
            S3ObjectInputStream is = s3Object.getObjectContent();

            byte[] data = IOUtils.toByteArray(is);
            String result = ImageUtils.imageToBase64(data);

            Long timeTaken = System.currentTimeMillis() - start;
            logger.info("Successfully encoded {}, took {}ms", fileName, timeTaken);
            return result;
        }
        catch (IOException IOexception) {
            logger.warn("IOException occurred with message {}", IOexception.getMessage());
            throw new RuntimeException(IOexception.getMessage());
        }
        catch (AmazonS3Exception s3Exception) {
            logger.info("Exception occurred when retrieving object of key:{}", fileName);
            logger.warn("Amazon S3 exception occurred with message {}", s3Exception.getMessage());
            throw new RuntimeException(s3Exception.getMessage());
        }
    }
}
