package org.ocr_project;

public enum Language {
    ENG("eng"),
    POL("pol");

    private final String name;

    Language(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
