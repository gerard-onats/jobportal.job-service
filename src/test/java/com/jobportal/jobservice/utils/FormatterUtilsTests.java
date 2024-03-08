package com.jobportal.jobservice.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class FormatterUtilsTests {
    @Test
    public void test_datePostedFormatter_just_now() {
        Date dateSecondsAgo = new Date();

        final Long MILLISECOND_SUBTRACT = 5000L;
        dateSecondsAgo.setTime(dateSecondsAgo.getTime() - MILLISECOND_SUBTRACT);

        final String expectedResult = "Just now";
        assertEquals(FormatterUtils.datePostedFormatter(dateSecondsAgo), expectedResult);
    }

    @Test
    public void test_datePostedFormatter_minute_ago() {
        Date dateMinutesAgo = new Date();

        final Long MILLISECOND_SUBTRACT = 60000L;
        dateMinutesAgo.setTime(dateMinutesAgo.getTime() - MILLISECOND_SUBTRACT);

        final String expectedResult = String.format("%s minute ago", 1);
        assertEquals(FormatterUtils.datePostedFormatter(dateMinutesAgo), expectedResult);
    }

    @Test
    public void test_datePostedFormatter_minutes_ago() {
        Date dateMinutesAgo = new Date();

        final Long MILLISECOND_SUBTRACT = 120000L;
        dateMinutesAgo.setTime(dateMinutesAgo.getTime() - MILLISECOND_SUBTRACT);

        final String expectedResult = String.format("%s minutes ago", 2);
        assertEquals(FormatterUtils.datePostedFormatter(dateMinutesAgo), expectedResult);
    }

    @Test
    public void test_base64LogoFormatter_should_throw_Exception() {
        Exception exception = assertThrows(ArrayIndexOutOfBoundsException.class, ()-> {
            final String invalidFileName = "INVALID_FILE_NAME";
            final String fakeBase64 = "fakeBase64";
            final String test = FormatterUtils.base64LogoFormatter(invalidFileName, fakeBase64);
        });
    }
}
