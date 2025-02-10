package org.ocr_project;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OCR {
    private static final String DATA_PATH = "Tess4J/tessdata";
    private File image;
    private Language language;

    public OCR(File image, Language language) {
        this.image = image;
        this.language = language;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getText() {
        Tesseract tesseract = new Tesseract();

        try {
            tesseract.setDatapath(DATA_PATH);
            tesseract.setLanguage(String.valueOf(language));

            return tesseract.doOCR(image);
        } catch (TesseractException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
