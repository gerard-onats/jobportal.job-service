package com.jobportal.jobservice.utils;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;

public class ImageUtils {
    static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    public static String imageToBase64(byte[] data) {
        try {
            return Base64.getEncoder().encodeToString(data);
        }
        catch(Exception e) {
            logger.warn("Exception occurred at imageToBase64 method with message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
