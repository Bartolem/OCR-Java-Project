package org.ocr_project;

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

    FileDragAndDrop() {
        this.dropPanel = new JPanel(new BorderLayout());
        this.imageIcon = new ImageIcon(new ImageIcon("src/main/resources/upload.png").getImage().getScaledInstance(256, 256, Image.SCALE_SMOOTH));
        this.imageLabel = new JLabel(imageIcon);
        this.fileNameLabel = new JLabel("Drop a file here");

        fileNameLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        fileNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        dropPanel.add(fileNameLabel, BorderLayout.NORTH);
        dropPanel.add(imageLabel, BorderLayout.CENTER);
    }

    public JPanel getDropPanel() {
        return dropPanel;
    }

    public void setImage(File file) {
        this.imageIcon = new ImageIcon(new ImageIcon(file.getPath()).getImage().getScaledInstance(372, 480, Image.SCALE_SMOOTH));
        imageLabel.setIcon(imageIcon);
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
            @SuppressWarnings("unchecked")
            List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
            if (files.size() != 1) {
                return false; // Only accept one file
            }
            this.file = files.getFirst();
            // Do something with the dropped file, for example, display its name
            UserInterface.enableConversion();
            setImage(file);
            dropPanel.revalidate();
            dropPanel.repaint();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public File getFile() {
        return file;
    }
}
