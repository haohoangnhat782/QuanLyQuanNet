package GUI.Components;

import org.jdesktop.swingx.border.DropShadowBorder;

import javax.swing.*;
import java.awt.*;

public class ShadowPanel extends JPanel {
    public ShadowPanel() {
        DropShadowBorder shadow = new DropShadowBorder();
        shadow.setShadowColor(Color.BLACK);
        shadow.setShowLeftShadow(true);
        shadow.setShowRightShadow(true);
        shadow.setShowBottomShadow(true);
        shadow.setShowTopShadow(true);
        this.setBorder(shadow);
    }
}
