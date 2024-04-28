package com.shafi.construction.utils;

public class CommonUtils {
    /**
     * Validate line and Extract fields from input line
     *
     * @param lineData       input data/line
     * @param delimiter      field separator
     * @param expectedLength expected number of fields
     * @return String[] if valid line is passed
     * @throws Exception if invalid line passed
     */
    public static String[] extractFieldArray(String lineData, String delimiter, int expectedLength) throws Exception {
        String[] fieldArray = lineData.split(delimiter);
        if (fieldArray.length != expectedLength) {
            throw new Exception("received data in invalid format");
        }
        return fieldArray;
    }
}
