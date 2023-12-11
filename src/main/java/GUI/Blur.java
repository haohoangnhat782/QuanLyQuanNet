package GUI;

import GUI.Server.MainUI;

import javax.swing.*;
import java.awt.*;

public class Blur extends JDialog{

    public Blur(Frame owner) {
        super(owner);

        setUndecorated(true);
        setBackground(new Color(0,0,0,120));
        setLocationRelativeTo(owner);
        setBounds(owner.getBounds());
    }

}
