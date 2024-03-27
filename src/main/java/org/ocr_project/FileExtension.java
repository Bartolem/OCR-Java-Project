package org.ocr_project;

public enum FileExtension {
    TXT(".txt"),
    DOCX(".docx"),
    PDF(".pdf");

    private final String extension;

    FileExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
