package org.ocr_project;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import java.io.File;
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

    public static void writeToDocxFile(String content, String filePath) {
        WordprocessingMLPackage wordPackage;
        try {
            wordPackage = WordprocessingMLPackage.createPackage();
            MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();

            mainDocumentPart.addParagraphOfText(content);
            wordPackage.save(new File(filePath));
        } catch (Docx4JException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
