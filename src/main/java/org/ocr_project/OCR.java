package org.ocr_project;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OCR {
    private static final String DATA_PATH = "Tess4J/tessdata";
    private File file;
    private BufferedImage imagePDF;
    private Language language;

    public OCR(File file, Language language) {
        this.file = file;
        this.language = language;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public BufferedImage getImagePDF() {
        return imagePDF;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getText() {
        if (isPDF(file)) {
            return extractTextFromPDF(file);
        } else {
            return extractTextFromImage(file);
        }
    }

    public static boolean isPDF(File file) {
        return file.getName().toLowerCase().endsWith(FileExtension.PDF.getExtension());
    }

    private String extractTextFromPDF(File pdfFile) {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            // Try to extract text directly
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String extractedText = pdfStripper.getText(document).trim();

            if (!extractedText.isEmpty()) {
                return extractedText; // Return if text is found (no OCR needed)
            }

            // No text found, perform OCR on images
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(DATA_PATH);
            tesseract.setLanguage(String.valueOf(language));

            StringBuilder ocrText = new StringBuilder();

            for (int page = 0; page < document.getNumberOfPages(); page++) {
                this.imagePDF = pdfRenderer.renderImageWithDPI(page, 300);
                String text = tesseract.doOCR(imagePDF);
                ocrText.append(text).append("\n");
            }

            return ocrText.toString();
        } catch (TesseractException e) {
            throw new RuntimeException("Error processing PDF: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String extractTextFromImage(File imageFile) {
        try {
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(DATA_PATH);
            tesseract.setLanguage(String.valueOf(language));

            return tesseract.doOCR(imageFile);
        } catch (TesseractException e) {
            throw new RuntimeException("Error processing image: " + e.getMessage());
        }
    }
}
