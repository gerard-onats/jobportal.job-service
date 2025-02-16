package com.jobportal.jobservice.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class FormatterUtilsTests {
    @Test
    public void test_datePostedFormatter_just_now() {
        Date dateSecondsAgo = new Date();

        final long MILLISECOND_SUBTRACT = 5000L;
        dateSecondsAgo.setTime(dateSecondsAgo.getTime() - MILLISECOND_SUBTRACT);

        final String expectedResult = "Just now";
        assertEquals(expectedResult, FormatterUtils.datePostedFormatter(dateSecondsAgo));
    }

    @Test
    public void test_datePostedFormatter_minute_ago() {
        Date dateMinutesAgo = new Date();

        final long MILLISECOND_SUBTRACT = 60000L;
        dateMinutesAgo.setTime(dateMinutesAgo.getTime() - MILLISECOND_SUBTRACT);

        final String expectedResult = String.format("%s minute ago", 1);
        assertEquals(expectedResult, FormatterUtils.datePostedFormatter(dateMinutesAgo));
    }

    @Test
    public void test_datePostedFormatter_minutes_ago() {
        Date dateMinutesAgo = new Date();
        final long MILLISECOND_SUBTRACT = 120000L;
        dateMinutesAgo.setTime(dateMinutesAgo.getTime() - MILLISECOND_SUBTRACT);

        final String expectedResult = String.format("%s minutes ago", 2);
        assertEquals(FormatterUtils.datePostedFormatter(dateMinutesAgo), expectedResult);
    }

    @Test
    public void test_base64LogoFormatter_should_throw_Exception() {
        assertThrows(ArrayIndexOutOfBoundsException.class, ()-> {
            final String invalidFileName = "INVALID_FILE_NAME";
            final String fakeBase64 = "fakeBase64";
            FormatterUtils.base64LogoFormatter(invalidFileName, fakeBase64);
        });
    }
}
