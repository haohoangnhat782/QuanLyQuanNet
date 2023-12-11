package GUI.Server.Product;

import BUS.ProductBUS;
import DTO.Product;
import Utils.Helper;
import Utils.ServiceProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductPanel extends JPanel {
    private ProductBUS productBUS;
    private Product product;
    public ProductPanel(Product product) {
        this.product = product;
        productBUS = ServiceProvider.getInstance().getService(ProductBUS.class);
        initComponent();
    }

    public void initComponent() {
        setLayout(new BorderLayout());

        JLabel imageLabel = new JLabel(Helper.getIcon(product.getImage(),300,200));
        add(imageLabel,BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3,1));

        JLabel namePanel = new JLabel(product.getName());
        infoPanel.add(namePanel);

        JLabel priceLabel = new JLabel(product.getPrice()+"đ");
        infoPanel.add(priceLabel);

        JTextField quantityField = new JTextField();
        infoPanel.add(quantityField);

        JButton addButton = new JButton("Add to Cart");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(quantityField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,"Vui Lòng Nhập Số Lượng Bạn Muốn Mua","Thông Báo",JOptionPane.ERROR_MESSAGE);
                } else {
                    int quantity = Integer.parseInt(quantityField.getText());
                    new Cart().addItem(product, quantity);
                }
            }
        });

        infoPanel.add(addButton);
        add(infoPanel,BorderLayout.SOUTH);
    }

    public void showAllProducts() {
        removeAll();

        try {
            List<Product> products = productBUS.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        revalidate();
        repaint();
    }

    public void showProductsByCategory(String productType) {
        removeAll();

        if (productType.equals("FOOD")){
            try {
                List<Product> products = productBUS.filterByTypeProduct(Product.ProductType.FOOD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (productType.equals("DRINK")) {
            try {
                List<Product> products = productBUS.filterByTypeProduct(Product.ProductType.DRINK);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (productType.equals("CARD")){
            try {
                List<Product> products = productBUS.filterByTypeProduct(Product.ProductType.CARD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        Helper.initUI();
        ServiceProvider.init();
        JFrame frame = new JFrame();
        ProductBUS p = ServiceProvider.getInstance().getService(ProductBUS.class);
        List<Product> list = new ArrayList<>();
        try {
            list = p.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ProductPanel productPanel = new ProductPanel(list.get(0));
        System.out.println(list.get(0));
        frame.add(productPanel);
        frame.setVisible(true);
    }
}

