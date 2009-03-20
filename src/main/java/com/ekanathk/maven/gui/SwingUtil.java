package com.ekanathk.maven.gui;

import static com.ekanathk.maven.core.CommonUtil.assertNotNull;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class SwingUtil {
	public static void centerComponentOnScreen(Component component) {
        assertNotNull(component, "The component cannot be null");
        // Center on screen
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((d.getWidth() - component.getWidth()) / 2);
        int y = (int) ((d.getHeight() - component.getHeight()) / 2);
        component.setLocation(x, y);
    }
	
	public static void handleException(String msg) {
        /**
         * Apparently there is no better option to wrap the text in 
         * an alert box. Most of the work arounds are documented here
         * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4104906
         */
        JOptionPane alert = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE,
                JOptionPane.DEFAULT_OPTION) {
			private static final long serialVersionUID = 1L;

			@Override
            public int getMaxCharactersPerLineCount() {
                return 100;
            }
        };
        JDialog dialog = alert.createDialog(null, "Application Error");
        centerComponentOnScreen(dialog);
        dialog.setVisible(true);
    }
}
