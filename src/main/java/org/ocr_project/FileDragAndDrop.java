package org.ocr_project;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.File;

public class FileDragAndDrop extends TransferHandler {
    private final JPanel dropPanel;

    FileDragAndDrop() {
        this.dropPanel = new JPanel();
    }

    public JPanel getDropPanel() {
        return dropPanel;
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
            java.util.List<File> files = (java.util.List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
            if (files.size() != 1) {
                return false; // Only accept one file
            }
            File file = files.getFirst();
            // Do something with the dropped file, for example, display its name
            JLabel label = new JLabel(file.getName());
            dropPanel.add(label);
            dropPanel.revalidate();
            dropPanel.repaint();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
