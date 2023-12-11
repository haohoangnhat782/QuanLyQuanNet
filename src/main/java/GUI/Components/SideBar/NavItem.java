/*
 * Created by JFormDesigner on Sat Mar 11 16:06:11 ICT 2023
 */

package GUI.Components.SideBar;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Laffy
 */
public class NavItem extends JButton {
    private boolean isSelected = false;
    @Getter
    @Setter
    private String key;
    @Getter
    @Setter
    private JPanel contentPanel;
    @Setter
    @Getter
    private ImageIcon iconSelected;
    @Getter
    private ImageIcon defaultIcon;
    public void setDefaultIcon(ImageIcon defaultIcon) {
        this.defaultIcon = defaultIcon;
        setIcon(defaultIcon);
    }


    public void setSelected(boolean selected) {
        isSelected = selected;
        if (isSelected) {
            this.setBackground(new Color(11, 197, 234));
            this.setForeground(Color.WHITE);
        } else {
            this.setBackground(Color.WHITE);
            this.setForeground(Color.BLACK);
        }
        // repaint();
        repaint();
    }



    public boolean isSelected() {
        return isSelected;
    }

    public NavItem() {
        initComponents();
    }
    private void thisMouseEntered(MouseEvent e) {
        //#0BC5EA
        this.setBackground(new Color(11, 197, 234));
        this.setForeground(Color.WHITE);
        if (iconSelected != null)
            setIcon(iconSelected);
        else
            setIcon(defaultIcon);
    }

    private void thisMouseExited(MouseEvent e) {
        this.setBackground(Color.WHITE);
        this.setForeground(Color.BLACK);
        setIcon(defaultIcon);
    }

    private void initComponents() {
        setIconTextGap(20);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setHorizontalAlignment(SwingConstants.LEFT);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                thisMouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                thisMouseExited(e);
            }
        });
        setMinimumSize(new Dimension(300, 50));
        setPreferredSize(new Dimension(300, 50));
    }

}
