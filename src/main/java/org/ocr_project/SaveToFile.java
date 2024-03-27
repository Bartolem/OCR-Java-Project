package org.ocr_project;

import java.io.FileWriter;
import java.io.IOException;

public class SaveToFile {
    public static void writeToTxtFile(String content, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Failed to write text content to file: " + e.getMessage());
        }
    }
}
