package org.ocr_project;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.util.List;

public class FileDragAndDrop extends TransferHandler {
    private final JPanel dropPanel;
    private File file;
    private ImageIcon imageIcon;
    private final JLabel imageLabel;
    private final JLabel fileNameLabel;
    private static JButton deleteButton;

    FileDragAndDrop() {
        this.dropPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        this.imageIcon = new ImageIcon(new ImageIcon("src/main/resources/upload.png").getImage().getScaledInstance(256, 256, Image.SCALE_SMOOTH));
        this.imageLabel = new JLabel(imageIcon);
        this.fileNameLabel = new JLabel("Drop a image here");
        deleteButton = new JButton("Delete");
        deleteButton.setVisible(false);

        IconFontSwing.register(FontAwesome.getIconFont());
        deleteButton.setIcon(IconFontSwing.buildIcon(FontAwesome.TRASH, 15));

        fileNameLabel.setFont(new Font("Arial", Font.BOLD, 28));
        fileNameLabel.setForeground(Color.BLACK);
        fileNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        dropPanel.add(fileNameLabel, gridBagConstraints);

        gridBagConstraints.gridy = 5;
        dropPanel.add(imageLabel, gridBagConstraints);

        gridBagConstraints.gridy = 10;
        dropPanel.add(deleteButton, gridBagConstraints);

        deleteButton.addActionListener(e -> {
            this.imageLabel.setIcon(new ImageIcon(new ImageIcon("src/main/resources/upload.png").getImage().getScaledInstance(256, 256, Image.SCALE_SMOOTH)));
            fileNameLabel.setText("Drop a image here");
            deleteButton.setVisible(false);
            this.file = null;
            UserInterface.removeCurrentImage();
        });
    }

    public JPanel getDropPanel() {
        return dropPanel;
    }

    public void enableDeleteButton() {
        deleteButton.setVisible(true);
    }

    public void setImage(File file) {
        this.imageIcon = new ImageIcon(new ImageIcon(file.getPath()).getImage().getScaledInstance(372, 480, Image.SCALE_SMOOTH));
        imageLabel.setIcon(imageIcon);
        fileNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        fileNameLabel.setText(file.getName());
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support))
            return false;

        Transferable transferable = support.getTransferable();
        try {
            List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
            if (files.size() != 1) {
                return false; // Only accept one file
            }
            this.file = files.getFirst();
            UserInterface.enableConversion();
            setImage(file);
            dropPanel.revalidate();
            dropPanel.repaint();
            deleteButton.setVisible(true);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public File getFile() {
        return file;
    }
}
