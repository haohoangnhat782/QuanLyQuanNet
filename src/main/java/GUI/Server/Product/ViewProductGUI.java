package GUI.Server.Product;

import Utils.Fonts;
import Utils.Helper;
import Utils.ServiceProvider;
import DTO.Product;
import BUS.ProductBUS;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.Date;

public class ViewProductGUI extends JFrame {
    private JPanel parentPanel, panelHeader, panelBody, panel1, panel2, panel3, panelLeftPN, panelRightPN, imageEnd, panelPDRight, panelPDLeft, panel2d, panelRighNOP, panelRigth2, panelLeftPB, panelLeft2, panel2b, panelRigthTCB, panelRight1,panelLeftPP, panelLeft1, panel2h;
    private JButton returnButton, updateButton, chooseButton;
    private JLabel logo, productName , productPrice, productType, numberOfProduct, productDescription, productImage;
    private JTextField txtProductName, txtProductPrice, txtNumberOfProduct, txtProductDescription;
    private Product product = Product.builder().image("/images/gtaV.jpg").id(0).name("").price(0).createdAt(new Date()).description("").stock(0).build();
    private ProductBUS productBUS;
    private JCheckBox placeBox;
    private JComboBox comboBox;
    public ViewProductGUI(int productId) {
        productBUS = ServiceProvider.getInstance().getService(ProductBUS.class);
        try {
            product = productBUS.findById(productId);
            if (product == null) {
                JOptionPane.showMessageDialog(this,"Không tìm thấy sản phẩm","Lỗi",JOptionPane.ERROR_MESSAGE);
                this.dispose();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Lỗi sản phẩm","Lỗi",JOptionPane.ERROR_MESSAGE);
            this.dispose();
            e.printStackTrace();
        }
        // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(1000,1000);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        initComponents();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        var bg = new Color(255,255,255,245);

        parentPanel = new JPanel();
        BorderLayout borderLayout = new BorderLayout(5,30);
        parentPanel.setPreferredSize(new Dimension(900,800));
        parentPanel.setLayout(borderLayout);
        add(parentPanel,BorderLayout.CENTER);

        // create panel header
        panelHeader = new JPanel();
        panelHeader.setPreferredSize(new Dimension(1000-30,60));
        panelHeader.setLayout(new BorderLayout(30,0));
        parentPanel.add(panelHeader,BorderLayout.PAGE_START);
        // end panel header

        JPanel panelLeft = new JPanel();
        panelLeft.setPreferredSize(new Dimension(200,300));

        JLabel image = new JLabel();
        image.setIcon(Helper.getIcon(product.getImage(),300,200));
        panelLeft.add(image);

        parentPanel.add(panelLeft,BorderLayout.LINE_START);

        // create logo Chinh sua thong tin san pham
        logo = new JLabel("Thông Tin Sản Phẩm");
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setFont(Fonts.getFont(Font.BOLD,30));
        panelHeader.add(logo,BorderLayout.CENTER);
        // end logo

        // create panel body
        panelBody = new JPanel();
        panelBody.setLayout(new BorderLayout());
        panelBody.setPreferredSize(new Dimension(1000-30,1000-70));
        parentPanel.add(panelBody,BorderLayout.CENTER);
        // end panel body

        // create panel1
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.setPreferredSize(new Dimension(100-35,55));
        panelBody.add(panel1,BorderLayout.PAGE_START);
        // end panel1

        // create label nameProduct
        productName = new JLabel("Tên Sản Phẩm");
        productName.setFont(Fonts.getFont(Font.BOLD,18));
        productName.setBorder(new EmptyBorder(0,20,0,0));
        panel1.add(productName,BorderLayout.PAGE_START);
        // end label nameProduct

        panelLeftPN = new JPanel();
        panelLeftPN.setPreferredSize(new Dimension(18,35));
        panel1.add(panelLeftPN,BorderLayout.LINE_START);
        // create txtNameProduct
        txtProductName = new JTextField();
        txtProductName.setText(product.getName());
        txtProductName.setEnabled(false);
        txtProductName.setFont(Fonts.getFont(Font.PLAIN,15));
        txtProductName.setPreferredSize(new Dimension(1000-40,20));
        panel1.add(txtProductName,BorderLayout.CENTER);
        // end txtNameProduct

        panelRightPN = new JPanel();
        panelRightPN.setPreferredSize(new Dimension(18,35));
        panel1.add(panelRightPN,BorderLayout.LINE_END);

        // create panel2
        panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(1000-40,170));
        panel2.setBackground(Color.RED);
        panel2.setLayout(new BorderLayout());
        panelBody.add(panel2,BorderLayout.CENTER);
        // end panel2

        // create panel2h
        panel2h = new JPanel();
        panel2h.setLayout(new BorderLayout());
        panel2h.setPreferredSize(new Dimension(1000-45,55));
        panel2.add(panel2h,BorderLayout.PAGE_START);
        // end panel2h

        //
        panelLeft1 = new JPanel();
        panelLeft1.setLayout(new BorderLayout());
//        panelLeft1.setPreferredSize(new Dimension(480,55));
        panel2h.add(panelLeft1,BorderLayout.CENTER);

        productPrice = new JLabel("Giá Bán");
        productPrice.setFont(Fonts.getFont(Font.BOLD,18));
        productPrice.setBorder(new EmptyBorder(0,20,0,0));
        panelLeft1.add(productPrice,BorderLayout.PAGE_START);

        panelLeftPP = new JPanel();
        panelLeftPP.setPreferredSize(new Dimension(18,35));
        panelLeft1.add(panelLeftPP,BorderLayout.LINE_START);

        txtProductPrice = new JTextField();
        txtProductPrice.setText(product.getPrice()+"");
        txtProductPrice.setEnabled(false);
        txtProductPrice.setFont(Fonts.getFont(Font.PLAIN,15));
//        txtProductPrice.setPreferredSize(new Dimension(470,18));
        txtProductPrice.setColumns(50);
        txtProductPrice.setBackground(bg);
        panelLeft1.add(txtProductPrice,BorderLayout.CENTER);


        panelRight1 = new JPanel();
        panelRight1.setLayout(new BorderLayout());
        panelRight1.setPreferredSize(new Dimension(480,55));
        panel2h.add(panelRight1,BorderLayout.LINE_END);

        productType = new JLabel("Loại Sản Phẩm");
        productType.setFont(Fonts.getFont(Font.BOLD,18));
        panelRight1.add(productType,BorderLayout.PAGE_START);

        String combo[] = {product.getType().toString()};
        comboBox = new JComboBox(combo);
        comboBox.setFont(Fonts.getFont(Font.ITALIC,15));
        comboBox.setPreferredSize(new Dimension(480,18));
        panelRight1.add(comboBox,BorderLayout.CENTER);

        panelRigthTCB = new JPanel();
        panelRigthTCB.setPreferredSize(new Dimension(18,18));
        panelRight1.add(panelRigthTCB,BorderLayout.LINE_END);


        //
        panel2b = new JPanel();
        panel2b.setLayout(new BorderLayout());
        panel2b.setPreferredSize(new Dimension(1000-45,55));
        panel2.add(panel2b,BorderLayout.CENTER);

        //
        panelLeft2 = new JPanel();
        panelLeft2.setLayout(new BorderLayout());
        panelLeft2.setPreferredSize(new Dimension(480,55));
        panel2b.add(panelLeft2,BorderLayout.CENTER);

        panelLeftPB = new JPanel();
        panelLeftPB.setPreferredSize(new Dimension(18,35));
        panelLeft2.add(panelLeftPB);

        //
        placeBox = new JCheckBox("Chế Biến Bên Ngoài");
        placeBox.setFont(Fonts.getFont(Font.BOLD,18));
        placeBox.setBorder(new EmptyBorder(0,20,0,0));
        panelLeft2.add(placeBox,BorderLayout.CENTER);

        //
        panelRigth2 = new JPanel();
        panelRigth2.setLayout(new BorderLayout());
        panelRigth2.setPreferredSize(new Dimension(480,55));
        panel2b.add(panelRigth2,BorderLayout.LINE_END);

        numberOfProduct = new JLabel("Số Lượng Sản Phẩm Trong Kho");
        numberOfProduct.setFont(Fonts.getFont(Font.BOLD,18));
        panelRigth2.add(numberOfProduct,BorderLayout.PAGE_START);

        txtNumberOfProduct = new JTextField();
        txtNumberOfProduct.setText(product.getStock()+"");
        txtNumberOfProduct.setEnabled(false);
        txtNumberOfProduct.setFont(Fonts.getFont(Font.PLAIN,15));
        panelRigth2.add(txtNumberOfProduct,BorderLayout.CENTER);

        panelRighNOP = new JPanel();
        panelRighNOP.setPreferredSize(new Dimension(18,40));
        panelRigth2.add(panelRighNOP,BorderLayout.LINE_END);

        panel2d = new JPanel();
        panel2d.setLayout(new BorderLayout());
        panel2d.setPreferredSize(new Dimension(1000-45,55));
        panel2.add(panel2d,BorderLayout.PAGE_END);

        productDescription = new JLabel("Mô Tả Sản Phẩm.");
        productDescription.setFont(Fonts.getFont(Font.BOLD,18));
        productDescription.setBorder(new EmptyBorder(0,20,0,0));
        panel2d.add(productDescription,BorderLayout.PAGE_START);

        panelPDLeft = new JPanel();
        panelPDLeft.setPreferredSize(new Dimension(18,35));
        panel2d.add(panelPDLeft,BorderLayout.LINE_START);

        txtProductDescription = new JTextField();
        txtProductDescription.setText(product.getDescription());
        txtProductDescription.setEnabled(false);
        txtProductDescription.setPreferredSize(new Dimension(1000-40,18));
        txtProductDescription.setFont(Fonts.getFont(Font.PLAIN,15));
        panel2d.add(txtProductDescription,BorderLayout.CENTER);

        panelPDRight = new JPanel();
        panelPDRight.setPreferredSize(new Dimension(18,35));
        panel2d.add(panelPDRight,BorderLayout.LINE_END);

        panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());
        panel3.setPreferredSize(new Dimension(600,515));
        panelBody.add(panel3,BorderLayout.PAGE_END);


        productImage = new JLabel("Hình Ảnh Minh Họa");
        productImage.setFont(Fonts.getFont(Font.BOLD,18));
        productImage.setBorder(new EmptyBorder(0,20,0,0));
        panel3.add(productImage,BorderLayout.PAGE_START);
    }

    public static void main(String[] args) {

    }
}
