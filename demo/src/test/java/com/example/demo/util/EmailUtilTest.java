package com.example.demo.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(MockitoExtension.class)
public class EmailUtilTest {
@InjectMocks
EmailUtil util;
    @ParameterizedTest
    @CsvSource({
            "'test@gmail.com', true,'1'",
            "'abc@yahoo.com', true,'2'",
            "'invalid-email', false,'3'",
            "'@gmail.com', false,'4'",
            "'test@', false,'5'"
    })
    void shouldValidateEmailFormat(String emailValue, boolean expected, int number) {

        boolean result = util.validateEmails(emailValue);
        System.out.println(number);

        assertEquals(expected, result);
    }

    @Test
    void testNullEmail(){
        assertThrows(IllegalArgumentException.class, () ->
                util.validateEmails(null));
    }
}
