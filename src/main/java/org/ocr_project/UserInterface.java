package org.ocr_project;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class UserInterface {
    private static final String DATA_PATH = "Tess4J/tessdata";
    private static JButton convertButton;
    private static JButton saveButton;
    private JFrame frame;
    private OCR ocr;
    private JTextArea textArea;
    private FileDragAndDrop fileDragAndDrop;

    public UserInterface() {
        initialize();
    }

    private void initialize() {
        this.frame = new JFrame();
        this.fileDragAndDrop = new FileDragAndDrop();
        convertButton = new JButton("Convert");
        saveButton = new JButton("Save");

        IconFontSwing.register(FontAwesome.getIconFont());
        Icon icon = IconFontSwing.buildIcon(FontAwesome.FLOPPY_O, 15);
        saveButton.setIcon(icon);

        disableConversion();

        saveButton.setEnabled(false);

        JScrollPane scrollPane = createScrollPane();
        JButton selectButton = new JButton("Select image");
        frame.setTitle("OCR Project");
//        frame.setIconImage();
        frame.setLayout(new MigLayout());
//        frame.setPreferredSize(new Dimension(800, 1000));

        frame.setLocationRelativeTo(null);
//        frame.getContentPane().setBackground();
        frame.setResizable(false);
        frame.add(createDragAndDropPanel(),"grow, push");
        frame.add(scrollPane, "wrap");
        frame.add(selectButton);
        frame.add(convertButton, "split3");
        frame.add(saveButton);

        JComboBox<FileExtension> fileExtensionList = new JComboBox<>(FileExtension.values());

        frame.add(fileExtensionList);
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
            this.ocr = new OCR(fileDragAndDrop.getFile(), DATA_PATH);
            textArea.setText(ocr.getText());
            disableConversion();
            saveButton.setEnabled(true);
        });

        selectButton.addActionListener(e -> {
            File file = getImageFromFileChooser();

            if (file != null) {
                this.ocr = new OCR(file, DATA_PATH);
                fileDragAndDrop.setImage(ocr.getImage());
                enableConversion();
            }
        });

        saveButton.addActionListener(e -> createFileChooser((FileExtension) fileExtensionList.getSelectedItem()));
    }

    private void createFileChooser(FileExtension extension) {
        JFileChooser fileChooser= new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getPath();

            if (FilenameUtils.getExtension(filePath).isEmpty()) {
                filePath += extension.getExtension();
            }

            switch (extension) {
                case TXT -> {
                    SaveToFile.writeToTxtFile(textArea.getText(), filePath);
                }
                case DOCX -> System.out.println(FileExtension.DOCX.getExtension());
                case PDF -> System.out.println(FileExtension.PDF.getExtension());
            }
        }
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
        scrollPane.setPreferredSize(new Dimension(600, 600));
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        return scrollPane;
    }

    private JPanel createDragAndDropPanel() {
        JPanel dropPanel = fileDragAndDrop.getDropPanel();
        dropPanel.setTransferHandler(fileDragAndDrop);
        dropPanel.setPreferredSize(new Dimension(400, 400));
        return dropPanel;
    }

    public static void enableConversion() {
        convertButton.setEnabled(true);
    }

    public static void disableConversion() {
        convertButton.setEnabled(false);
    }
}
