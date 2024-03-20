package com.engeto.media.service;

public class Settings {
    private static final String fileName = "resources/contributions.txt";

    private static final String delimiter = "\t";

    public static String getDelimiter() {
        return delimiter;
    }

    public static String getFileName() {
        return fileName;
    }
}
