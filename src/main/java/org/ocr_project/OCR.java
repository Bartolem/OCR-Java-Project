package org.ocr_project;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OCR {
    private File image;
    private String dataPath;

    public OCR(File image, String dataPath) {
        this.image = image;
        this.dataPath = dataPath;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getText() {
        Tesseract tesseract = new Tesseract();

        try {
            tesseract.setDatapath(dataPath);
            tesseract.setLanguage("pol");

            return tesseract.doOCR(image);
        } catch (TesseractException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
