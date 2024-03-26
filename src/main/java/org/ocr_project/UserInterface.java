package org.ocr_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Objects;

public class UserInterface {
    private JFrame frame;
    private OCR ocr;
    private JTextArea textArea;
    private JButton convertButton;

    public UserInterface() {
        initialize();
    }

    private void initialize() {
        this.frame = new JFrame();
        this.textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(600, 800));
        this.convertButton = new JButton("Convert");
        frame.setTitle("OCR Project");
//        frame.setIconImage();
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
//        frame.getContentPane().setBackground();
        frame.setResizable(false);
        frame.add(textArea, BorderLayout.CENTER);
        frame.add(convertButton, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to exit the program?",
                        "Exit Program Message Box",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                } else {
                    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        convertButton.addActionListener(e -> {
            File image = getImageFromFileChooser();
            this.ocr = new OCR(image, "Tess4J/tessdata");
            textArea.setText(ocr.getText());
        });
    }

    private File getImageFromFileChooser() {
        JFileChooser fileChooser= new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            return new File(fileChooser.getSelectedFile().getPath());
        }

        return null;
    }
}
