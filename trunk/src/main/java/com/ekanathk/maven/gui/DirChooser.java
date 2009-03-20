package com.ekanathk.maven.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

public class DirChooser implements ActionListener {
	private static final Logger logger = Logger.getLogger(DirChooser.class.getName());
    private final JFileChooser chooser = new JFileChooser();
    private final JTextField textField;

    public DirChooser(JTextField textField) {
        this.textField = textField;
        this.chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    public void actionPerformed(ActionEvent ae) {
        // if the user selected a file, update the file name on screen
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String chosenFile = chooser.getSelectedFile().toString();
            logger.fine("Chosen file [" + chosenFile + "]");
			textField.setText(chosenFile);
        }
    }
}
