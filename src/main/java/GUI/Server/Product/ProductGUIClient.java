package GUI.Server.Product;

import GUI.Components.Input;
import Utils.Fonts;
import Utils.Helper;
import Utils.ServiceProvider;
import DTO.Product;
import BUS.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductGUIClient extends JFrame {
    private List<Product> list;
    private ProductBUS productBUS;
    private JPanel parentPanel, panelHeader, panelBody, panelBody1, panelBody2, buttonPanel;
    private JLabel txtListProduct, logoLabel;
    private JComboBox comboBox;
    private Input findByName;
    private int flag=0;
    private DefaultTableModel dtm;

    public ProductGUIClient() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        productBUS = ServiceProvider.getInstance().getService(ProductBUS.class);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setSize(1030,1030);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        parentPanel = new JPanel();
        // start parentPanel
        parentPanel.setPreferredSize(new Dimension(1000, 1000));
        BorderLayout layout = new BorderLayout(5,30);
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

        JButton addCart = new JButton("Cart");
        addCart.setFont(Fonts.getFont(Font.BOLD,13));
        addCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        addCart.setPreferredSize(new Dimension(60,60));
        panelHeader.add(addCart,BorderLayout.LINE_END);

        // start panelBody
        panelBody = new JPanel();
        panelBody.setLayout(new BorderLayout(5,20));
        panelBody.setPreferredSize(new Dimension(1000 - 30, 1000));
        parentPanel.add(panelBody, BorderLayout.CENTER);
        // end panelBody

        panelBody1 = new JPanel();
        panelBody1.setLayout(new BorderLayout());
        panelBody1.setPreferredSize(new Dimension(1000 - 10, 30));
        panelBody.add(panelBody1,BorderLayout.PAGE_START);
        // txt Danh sách sản phẩm
        // start logoListProduct

        panelBody2 = new JPanel();
        panelBody2.setPreferredSize(new Dimension(700 - 40, 30));
        panelBody2.setLayout(new BorderLayout());
        panelBody1.add(panelBody2, BorderLayout.LINE_END);


        // start typeProduct
        String typeProduct[] = {"Tất Cả", "Thức Ăn", "Nước Uống", "Thẻ"};
        comboBox = new JComboBox(typeProduct);
        comboBox.setBorder(new EmptyBorder(0,5,0,0));
        comboBox.setFont(Fonts.getFont(Font.ITALIC, 15));
        comboBox.setPreferredSize(new Dimension(250, 25));
        list = new ArrayList<>();
        var localProductService = this.productBUS;
        var dtm1 = this.dtm;
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
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        findByName.setFont(Fonts.getFont(Font.PLAIN, 15));
        findByName.setPreferredSize(new Dimension(150, 25));
        panelBody2.add(findByName, BorderLayout.CENTER);
        // end findByName


        try {
            list = localProductService.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JPanel panelProduct = new JPanel();
        panelProduct.setLayout(new FlowLayout());
        panelProduct.setPreferredSize(new Dimension(1000-40,600));
        panelProduct.setBackground(Color.RED);
        int count = 0;
        for (Product p : list) {
            System.out.println(p);
            ProductPanel product = new ProductPanel(p);
            panelProduct.add(product);
        }
        JScrollPane scrollPane = new JScrollPane(panelProduct);
        scrollPane.setPreferredSize(new Dimension(1000,1000));
        panelBody.add(scrollPane,BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        Helper.initUI();
        ServiceProvider.init();
        new ProductGUIClient();
    }
}
