package com.ekanathk.maven.gui;

import static com.ekanathk.maven.core.CommonUtil.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

public class SwingUtil {
	public static void centerComponentOnScreen(Component component) {
        assertNotNull(component, "The component cannot be null");
        // Center on screen
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((d.getWidth() - component.getWidth()) / 2);
        int y = (int) ((d.getHeight() - component.getHeight()) / 2);
        component.setLocation(x, y);
    }
}
