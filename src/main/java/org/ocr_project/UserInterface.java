package org.ocr_project;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class UserInterface {
    private static final String DATA_PATH = "Tess4J/tessdata";
    private JFrame frame;
    private OCR ocr;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton convertButton;
    private JButton selectButton;

    public UserInterface() {
        initialize();
    }

    private void initialize() {
        this.frame = new JFrame();
        this.scrollPane = createScrollPane();
        this.convertButton = new JButton("Convert");
        convertButton.setEnabled(false);
        this.selectButton = new JButton("Select image");
        frame.setTitle("OCR Project");
//        frame.setIconImage();
        frame.setLayout(new MigLayout("debug"));
//        frame.setPreferredSize(new Dimension(800, 1000));

        frame.setLocationRelativeTo(null);
//        frame.getContentPane().setBackground();
        frame.setResizable(false);
        frame.add(createDragAndDropPanel(),"grow, push");
        frame.add(scrollPane, "wrap");
        frame.add(selectButton);
        frame.add(convertButton);
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
            textArea.setText(ocr.getText());
        });

        selectButton.addActionListener(e -> {
            File image = getImageFromFileChooser();
            this.ocr = new OCR(image, DATA_PATH);
            convertButton.setEnabled(true);
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

    private JScrollPane createScrollPane() {
        this.textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        textArea.setPreferredSize(new Dimension(500, 600));
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        return scrollPane;
    }

    private JPanel createDragAndDropPanel() {
        FileDragAndDrop fileDragAndDrop = new FileDragAndDrop();
        JPanel dropPanel = fileDragAndDrop.getDropPanel();
        dropPanel.setBorder(BorderFactory.createTitledBorder("Drop a File Here"));
        dropPanel.setTransferHandler(fileDragAndDrop);
        dropPanel.setPreferredSize(new Dimension(200, 200));
        return dropPanel;
    }
}
