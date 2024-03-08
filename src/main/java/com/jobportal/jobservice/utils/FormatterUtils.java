package com.jobportal.jobservice.utils;

import com.jobportal.jobservice.constants.Constants;
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
        long duration = TimeUnit.MILLISECONDS.toMinutes(new Date().getTime() - date.getTime());
        if(duration < Constants.MINUTES_IN_HOUR) {
            if(duration <= 1) {
                if(duration == 0) return "Just now";
                return String.format("%d minute ago", duration);
            }
            return String.format("%d minutes ago", duration);
        }

        for(int i = 1; i < Constants.timeTypes.length; i++) {
            if(duration >= Constants.timeTypes[i]) continue;
            Long result = (duration / Constants.timeTypes[i - 1]);
            if(result <= 1) return String.format("%d %s ago", result, Constants.timeTypesName[i - 1]);
            return String.format("%d %ss ago", result, Constants.timeTypesName[i - 1]);
        }

        return Constants.EMPTY_STRING;
    }

}
