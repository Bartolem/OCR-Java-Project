package org.ocr_project;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Run this program on the Event Dispatch Thread (EDT)
        EventQueue.invokeLater(UserInterface::new);
    }
}