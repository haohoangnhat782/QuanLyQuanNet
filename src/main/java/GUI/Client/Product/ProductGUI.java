package GUI.Client.Product;

import GUI.Components.Input;
import Utils.Fonts;
import Utils.Helper;
import Utils.ServiceProvider;
import DTO.Product;
import BUS.ProductBUS;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductGUI extends JFrame {
    private ProductBUS productBUS;
    private JPanel parentPanel, panelHeader, panelBody, panelBody1, panelBody2, panel;
    private JLabel txtListProduct, logoLabel;
    private JComboBox comboBox;
    private Input findByName;
    private JButton editButton, viewButton, deleteButton;
    private List<Product> list;
    private DefaultTableModel dtm;

    public ProductGUI() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        productBUS = ServiceProvider.getInstance().getService(ProductBUS.class);
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(1030,1030));
        initComponents();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        parentPanel = new JPanel();
        // start parentPanel
        parentPanel.setPreferredSize(new Dimension(1000, 1000));
        BorderLayout layout = new BorderLayout(5, 30);
        parentPanel.setLayout(layout);
        add(parentPanel, BorderLayout.CENTER);
        // end parentPanel

        // panel header
        // start panel header
        panelHeader = new JPanel();
        panelHeader.setPreferredSize(new Dimension(1000 - 30, 60));
        panelHeader.setLayout(new BorderLayout());
        parentPanel.add(panelHeader, BorderLayout.PAGE_START);
        // end panel header

        // logo Quản Lý Sản Phẩm
        // start logo
        logoLabel = new JLabel("Thông Tin Sản Phẩm");
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setFont(Fonts.getFont(Font.BOLD, 25));
        panelHeader.add(logoLabel, BorderLayout.CENTER);
        // end logo

        // add button
        // start add button
        JButton addProductButton = new JButton("Cart");
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        addProductButton.setFont(Fonts.getFont(Font.BOLD, 14));
        addProductButton.setPreferredSize(new Dimension(80, 60));
        panelHeader.add(addProductButton, BorderLayout.LINE_END);
        // end add button

        // start panelBody
        panelBody = new JPanel();
        panelBody.setLayout(new BorderLayout(5, 20));
        panelBody.setPreferredSize(new Dimension(1000 - 30, 40));
        parentPanel.add(panelBody, BorderLayout.CENTER);
        // end panelBody

        panelBody1 = new JPanel();
        panelBody1.setLayout(new BorderLayout());
        panelBody1.setPreferredSize(new Dimension(1000 - 10, 30));
        panelBody.add(panelBody1, BorderLayout.PAGE_START);
        // txt Danh sách sản phẩm


        panelBody2 = new JPanel();
        panelBody2.setPreferredSize(new Dimension(700 - 40, 30));
        panelBody2.setLayout(new BorderLayout());
        panelBody1.add(panelBody2, BorderLayout.LINE_END);


        // start typeProduct
        String typeProduct[] = {"Tất Cả", "Thức Ăn", "Nước Uống", "Thẻ"};
        comboBox = new JComboBox(typeProduct);
        comboBox.setBorder(new EmptyBorder(0, 5, 0, 0));
        comboBox.setFont(Fonts.getFont(Font.ITALIC, 15));
        comboBox.setPreferredSize(new Dimension(250, 25));
        list = new ArrayList<>();
        var localProductService = this.productBUS;
        try {
            list = localProductService.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        panel = new JPanel();
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) comboBox.getSelectedItem();
                if (selected.equals("Tất Cả")) {
                    try {
                        list = localProductService.findAll();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (selected.equals("Thức Ăn")) {
                    try {
                        list = localProductService.filterByTypeProduct(Product.ProductType.FOOD);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (selected.equals("Nước Uống")) {
                    try {
                        list = localProductService.filterByTypeProduct(Product.ProductType.DRINK);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    try {
                        list = localProductService.filterByTypeProduct(Product.ProductType.CARD);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });
        panelBody2.add(comboBox, BorderLayout.LINE_START);
        // end typeProduct

        // findByName
        // start findByName
        findByName = new Input("Search Here...");
        findByName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    list = localProductService.findListByName(findByName.getText());
                    System.out.println(list);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                showProduct();

            }
        });
        findByName.setFont(Fonts.getFont(Font.PLAIN, 15));
        findByName.setPreferredSize(new Dimension(150, 25));
        panelBody2.add(findByName, BorderLayout.CENTER);
        // end findByName

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(1000-40,670));

        list = new ArrayList<>();
        try {
            list = localProductService.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        parentPanel.add(panel,BorderLayout.PAGE_END);

    }

    public void showProduct() {
//        var model = (DefaultTableModel)this.table.getModel();
//        model.setRowCount(0);
//        for (Product p : list) {
//            model.addRow(new Object[]{
//                    p.getId(), p.getName(), p.getPrice() ,p.getType(), p.getDescription(), p.getStock(),
//            });
//        }
    }

    public static void main(String[] args) {
        Helper.initUI();
        ServiceProvider.init();
        new ProductGUI();
    }
}
