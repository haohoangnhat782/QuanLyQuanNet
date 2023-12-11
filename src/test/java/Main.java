import Utils.Helper;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Helper.initUI();

        JFrame frame = new JFrame("Main");
        frame.setVisible(true);
        frame.setSize(500,500);


        var a= new JFileChooser().showOpenDialog(frame);

    }
}
