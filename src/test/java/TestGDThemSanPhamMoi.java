import GUI.Components.Input;
import Utils.Fonts;
import Utils.Helper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TestGDThemSanPhamMoi extends JFrame {
    public TestGDThemSanPhamMoi () {
        var bg = new Color(255,255,255,245);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // panelParent
        // start panelParent
        JPanel panelParent = new JPanel();
        panelParent.setPreferredSize(new Dimension(600,600));
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT,0,30);
        panelParent.setLayout(layout);
        this.add(panelParent);
        // end panel

        // panel header
        // start panel header
        JPanel panelHeader = new JPanel();
        panelHeader.setPreferredSize(new Dimension(600-30,30));
        panelHeader.setLayout(new BorderLayout());
        panelParent.add(panelHeader);

        // return button
        // start return button
        JButton returnButton = new JButton();
        returnButton.setIcon(Helper.getIcon("/icons/returnButton.jpg",25,25));
        returnButton.setPreferredSize(new Dimension(30,25));
        panelHeader.add(returnButton,BorderLayout.LINE_START);
        // end return button

        // logo Tạo Sản Phẩm
        // start logo
        JLabel logoLabel = new JLabel("Tạo Sản Phẩm.");
        logoLabel.setFont(Fonts.getFont(Font.BOLD,25));
        panelHeader.add(logoLabel,BorderLayout.CENTER);
        // end logo

        // add button
        // start add button
        JButton addButton = new JButton("Tạo");
        addButton.setPreferredSize(new Dimension(70,25));
        addButton.setBackground(Color.GREEN);
        panelHeader.add(addButton,BorderLayout.LINE_END);
        // end add button

        // end panel header

        // panel body
        // start panel body
        FlowLayout bodyLayout = new FlowLayout(FlowLayout.LEFT);
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(bodyLayout);
        bodyPanel.setPreferredSize(new Dimension(600-30,400));
//        bodyPanel.setBackground(Color.GRAY);
        panelParent.add(bodyPanel);

        // logo: tên sản phẩm
        // start name product
        JLabel nameProduct = new JLabel("Tên Sản Phẩm");
        nameProduct.setBorder(new EmptyBorder(0,5,0,0));
        nameProduct.setFont(Fonts.getFont(Font.BOLD,16));
        nameProduct.setPreferredSize(new Dimension(600-40,20));
        bodyPanel.add(nameProduct);
        // end name product

        // txtNameProduct
        // start txtNameProduct
        FlowLayout centerLayout = new FlowLayout();
        centerLayout.setAlignment(FlowLayout.CENTER);
        JPanel nameProductPanel = new JPanel();
        nameProductPanel.setLayout(centerLayout);
        nameProductPanel.setPreferredSize(new Dimension(600-40,35));
        nameProductPanel.setBackground(bg);

        Input txtNameProduct = new Input("Tên Sản Phẩm");
        txtNameProduct.setFont(Fonts.getFont(Font.PLAIN,15));
        txtNameProduct.setBackground(bg);
        txtNameProduct.setPreferredSize(new Dimension(600-43,30));
        nameProductPanel.add(txtNameProduct);
        bodyPanel.add(nameProductPanel);
        // end

        // panel : price & type
        // start panel
//        JPanel panel1 = new JPanel();
//        panel1.setLayout(bodyLayout);
//        panel1.setPreferredSize(new Dimension(560,60));
//        bodyPanel.add(panel1);
        // end panel

        // logo price
        // start logo price
        JLabel price = new JLabel("Giá Bán");
        price.setBorder(new EmptyBorder(10,5,0,0));
        price.setFont(Fonts.getFont(Font.BOLD,16));
        price.setPreferredSize(new Dimension(280-10,28));
        bodyPanel.add(price);
//        panel1.add(price);
        // end logo price

        // type
        // start type
        JLabel type = new JLabel("Thể Loại");
        type.setBorder(new EmptyBorder(10,5,0,0));
        type.setFont(Fonts.getFont(Font.BOLD,16));
        type.setPreferredSize(new Dimension(280-10,28));
        bodyPanel.add(type);
        // end type

        // txtPrice
        // start txtPrice
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(centerLayout);
        pricePanel.setPreferredSize(new Dimension(280-10,35));
        pricePanel.setBackground(bg);

        Input txtPrice = new Input("Giá Bán");
        txtPrice.setFont(Fonts.getFont(Font.PLAIN,15));
        txtPrice.setBackground(bg);
        txtPrice.setPreferredSize(new Dimension(280-13,30));
        pricePanel.add(txtPrice);
        bodyPanel.add(pricePanel);
        // end txtPrice

        // type combobox
        // start comboBox
        String combo[] = {"Chọn loại sản phẩm","Nước Uống","Thức Ăn"};
        JComboBox comboBox = new JComboBox(combo);
        comboBox.setBorder(new EmptyBorder(2,0,0,0));
        comboBox.setFont(Fonts.getFont(Font.ITALIC,15));
        comboBox.setPreferredSize(new Dimension(280-2,32));
        bodyPanel.add(comboBox);
        // end comboBox

        // logo nơi chế biến
        // start logo
        JLabel placeFoodLabel = new JLabel("Nơi Chế Biến Sản Phẩm");
        placeFoodLabel.setBorder(new EmptyBorder(10,5,0,0));
        placeFoodLabel.setFont(Fonts.getFont(Font.BOLD,16));
        placeFoodLabel.setPreferredSize(new Dimension(280-10,28));
        bodyPanel.add(placeFoodLabel);
        // end logo

        // logo số lượng sản phẩm
        // start logo
        JLabel countLabel = new JLabel("Số Lượng Hiện Có");
        countLabel.setBorder(new EmptyBorder(10,5,0,0));
        countLabel.setFont(Fonts.getFont(Font.BOLD,16));
        countLabel.setPreferredSize(new Dimension(280-10,28));
        bodyPanel.add(countLabel);
        // end

        // placeFoodCheckBox
        // start placeFoodCheckBox
        JPanel placeFoodCheckBox = new JPanel();
        placeFoodCheckBox.setLayout(centerLayout);
        placeFoodCheckBox.setPreferredSize(new Dimension(280-10,35));
        bodyPanel.add(placeFoodCheckBox);

        JCheckBox placeBox = new JCheckBox("Chế Biến Bên Ngoài");
        placeBox.setFont(Fonts.getFont(Font.ITALIC,15));
        placeBox.setPreferredSize(new Dimension(280-13,30));
        placeFoodCheckBox.add(placeBox);
        // end placeFoodCheckBox

        // txt số lượng sản phẩm hiện có
        // start txtCount
        JPanel panelCount = new JPanel();
        panelCount.setLayout(centerLayout);
        panelCount.setPreferredSize(new Dimension(280-2,35));
        panelCount.setBackground(bg);

        Input txtCount = new Input("Số Lượng Sản Phẩm Hiện Có");
        txtCount.setFont(Fonts.getFont(Font.PLAIN,15));
        txtCount.setBackground(bg);
        txtCount.setPreferredSize(new Dimension(280-5,30));
        panelCount.add(txtCount);
        bodyPanel.add(panelCount);
        // end txtCount

        // mô tả sản phẩm
        // start logo
        JLabel productDescription = new JLabel("Mô Tả Sản Phẩm");
        productDescription.setBorder(new EmptyBorder(0,5,0,0));
        productDescription.setFont(Fonts.getFont(Font.BOLD,16));
        productDescription.setPreferredSize(new Dimension(600-40,20));
        bodyPanel.add(productDescription);
        // end logo

        // txtProductDescription
        // start txtProductDescription
        JPanel panelProductDescription = new JPanel();
        panelProductDescription.setLayout(centerLayout);
        panelProductDescription.setPreferredSize(new Dimension(600-40,35));
        panelProductDescription.setBackground(bg);

        Input txtProductDescription = new Input("Mô Tả Sản Phẩm");
        txtProductDescription.setFont(Fonts.getFont(Font.PLAIN,15));
        txtProductDescription.setBackground(bg);
        txtProductDescription.setPreferredSize(new Dimension(600-43,30));
        panelProductDescription.add(txtProductDescription);
        bodyPanel.add(panelProductDescription);
        // end txtProductDescription

        // Hình ảnh minh họa
        // start logo
        JLabel imageLogo = new JLabel("Hình Ảnh Mô Tả Sản Phẩm");
        imageLogo.setBorder(new EmptyBorder(0,5,0,0));
        imageLogo.setFont(Fonts.getFont(Font.BOLD,16));
        imageLogo.setPreferredSize(new Dimension(600-40,20));
        bodyPanel.add(imageLogo);
        // end logo

        // panel image
        // start panel image
        JPanel panelImage = new JPanel();
        panelImage.setLayout(centerLayout);
        panelImage.setPreferredSize(new Dimension(600-40,35));
        panelImage.setBackground(bg);
        // end panel image



        // end panel body
        this.setVisible(true);
        FlowLayout frameLayout = new FlowLayout(FlowLayout.CENTER);
        this.setLayout(frameLayout);
        this.setSize(screenSize.width,screenSize.height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
    }

    public static void main(String[] args) {
        new TestGDThemSanPhamMoi();
    }
}
