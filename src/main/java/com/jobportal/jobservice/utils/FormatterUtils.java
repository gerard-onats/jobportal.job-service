package com.jobportal.jobservice.utils;

import com.jobportal.jobservice.constants.Constants;
import com.jobportal.jobservice.constants.TimeConstants;
import com.jobportal.jobservice.model.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FormatterUtils {
    private static final Logger logger = LoggerFactory.getLogger(FormatterUtils.class);

    public static String locationFormatter(Address address) {
        return String.format("%s, %s, %s", address.getCountry(), address.getCity(), address.getCompanyState());
    }

    public static String base64LogoFormatter(String imageName, String base64) {
        try {
            String extension = imageName.split("\\.")[1];
            return String.format("data:image/%s;base64,%s", extension, base64);
        }
        catch(ArrayIndexOutOfBoundsException exception) {
            throw new ArrayIndexOutOfBoundsException("Out of bounds exception occurred, invalid file format name.");
        }
    }

    public static String datePostedFormatter(Date date) {
        final long duration = TimeUnit.MILLISECONDS.toMinutes(new Date().getTime() - date.getTime());

        for(int i = TimeConstants.periodLength.length - 1; i >= 0; i--) {
            if(duration < TimeConstants.periodLength[i]) continue;
            long result = (duration / TimeConstants.periodLength[i]);
            if(result <= 1) return String.format("%d %s ago", result, TimeConstants.periodName[i]);
            return String.format("%d %ss ago", result, TimeConstants.periodName[i]);
        }

        return "Just now";
    }

}
