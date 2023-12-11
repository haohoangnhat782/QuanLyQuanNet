import Utils.Helper;

import javax.swing.*;
import java.awt.*;

public class Test extends JFrame {
    public Test() {
        this.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(170,60));
        JButton deleteButton = new JButton("Delete");
//        deleteButton.setIcon(Helper.getIcon("C:\\Users\\luong\\Downloads\\delete.png"));
        deleteButton.setPreferredSize(new Dimension(25,25));
        panel.add(deleteButton,BorderLayout.LINE_START);
        JButton viewButton = new JButton("View");
//        viewButton.setIcon(Helper.getIcon("C:\\Users\\luong\\Downloads\\delete.png"));
        viewButton.setPreferredSize(new Dimension(25,25));
        panel.add(viewButton,BorderLayout.CENTER);
        JButton updateButton = new JButton("Update");
        updateButton.setPreferredSize(new Dimension(25,25));
//        updateButton.setIcon(Helper.getIcon("C:\\Users\\luong\\Downloads\\delete.png"));
        panel.add(updateButton,BorderLayout.LINE_END);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(panel);
        this.setSize(300,300);
    }

    public static void main(String[] args) {
        new Test();
    }
}
