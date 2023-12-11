package GUI.Client.Product;

import Utils.Fonts;
import Utils.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductFrame extends JPanel {
    private JLabel image,name,price;
    private JButton button;
    public ProductFrame(int productId, String productImage, String productName, Double productPrice) {
        image = new JLabel();
        image.setIcon(Helper.getIcon(productImage,300,200));
        add(image, BorderLayout.CENTER);

        name = new JLabel(productName);
        name.setPreferredSize(new Dimension(45,20));
        name.setFont(Fonts.getFont(Font.PLAIN,18));

        price = new JLabel(productPrice+"");
        price.setPreferredSize(new Dimension(70,20));
        price.setFont(Fonts.getFont(Font.PLAIN,18));

        button = new JButton("Đặt Hàng");
        button.setFont(Fonts.getFont(Font.PLAIN,18));
        button.setPreferredSize(new Dimension(90,20));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
