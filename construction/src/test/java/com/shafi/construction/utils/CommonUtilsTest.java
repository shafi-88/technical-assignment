package com.shafi.construction.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CommonUtilsTest {

    @Test
    public void extractFieldArray_validInputParams() throws Exception {
        String[] result = CommonUtils
                            .extractFieldArray("kljkjj,kkkll", ",", 2);
        assertTrue(result.length == 2);
    }
    @Test
    public void extractFieldArray_inValidExpectedLength() throws Exception {
        assertThrows(Exception.class,
                ()->CommonUtils
                        .extractFieldArray("kljkjj,kkkll", ",", 1)
                    );
    }
}
