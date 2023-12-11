package GUI.Server.Product;

import GUI.Components.Input;
import Utils.Fonts;
import Utils.Helper;
import Utils.ServiceProvider;
import DTO.Product;
import BUS.ProductBUS;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.Date;

public class CreateProductGUI extends JFrame {
    private JPanel parentPanel, panelHeader, panelBody, panel1, panel2, panel3, panelLeftPN, panelRightPN, imageEnd, panelPDRight, panelPDLeft, panel2d, panelRighNOP, panelRigth2, panelLeftPB, panelLeft2, panel2b, panelRigthTCB, panelRight1,panelLeftPP, panelLeft1, panel2h;
    private JButton updateButton, chooseButton;
    private JLabel logo, productName , productPrice, productType, numberOfProduct, productDescription, productImage;
    private Input txtProductName, txtProductPrice, txtNumberOfProduct, txtProductDescription;
    private Product product = Product.builder().image("/images/imageWhite.jpg").id(0).name("").price(0).createdAt(new Date()).description("").stock(0).build();
    private ProductBUS productBUS;
    private JLabel image;
    private JCheckBox placeBox;
    private String newPath;
    private JComboBox comboBox;
    public CreateProductGUI() {
        productBUS = ServiceProvider.getInstance().getService(ProductBUS.class);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(1030,1030);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        initComponents();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        newPath = "";
        var bg = new Color(255,255,255,245);

        parentPanel = new JPanel();
        BorderLayout borderLayout = new BorderLayout(5,30);
        parentPanel.setMinimumSize(new Dimension(1000,1000));
        Dimension minSize = parentPanel.getMinimumSize();

        setMinimumSize(new Dimension(minSize.width + getInsets().left + getInsets().right,
                minSize.height + getInsets().top + getInsets().bottom));

        parentPanel.setLayout(borderLayout);
        add(parentPanel,BorderLayout.CENTER);

        // create panel header
        panelHeader = new JPanel();
        panelHeader.setPreferredSize(new Dimension(1000-30,60));
        panelHeader.setLayout(new BorderLayout(30,0));
        parentPanel.add(panelHeader,BorderLayout.PAGE_START);
        // end panel headera

        JPanel panelLeft = new JPanel();
        panelLeft.setPreferredSize(new Dimension(200,300));

        image = new JLabel();
        image.setIcon(Helper.getIcon(product.getImage(),300,200));
        panelLeft.add(image);

        parentPanel.add(panelLeft,BorderLayout.LINE_START);

        // create logo Chinh sua thong tin san pham
        logo = new JLabel("Thêm Sản Phẩm");
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setFont(Fonts.getFont(Font.BOLD,30));
        panelHeader.add(logo,BorderLayout.CENTER);
        // end logo

        // create update button
        updateButton = new JButton("Save");
        updateButton.setPreferredSize(new Dimension(100,30));
        updateButton.setFont(Fonts.getFont(Font.BOLD,15));
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
String check ="";
                int productPrice = Integer.parseInt(txtProductPrice.getText());
                int productPrice1 = Integer.parseInt(txtNumberOfProduct.getText());
                String selected = (String)comboBox.getSelectedItem();
                while ((!Helper.isNumber(txtProductPrice.getText()) || Integer.parseInt(txtProductPrice.getText()) < 0)){
                    JOptionPane.showMessageDialog(null, "Dữ liệu nhật chưa đúng !");

                    return;
                }

                if (selected.equals("Chọn Loại Sản Phẩm")) {
                    JOptionPane.showMessageDialog(null,"Chọn Loại Sản Phẩm","Thông Báo",JOptionPane.ERROR_MESSAGE);
               return;
                }
                if (productPrice < 0) {
                    JOptionPane.showMessageDialog(null, "Giá Bán Sản Phẩm không thể là số âm", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (productPrice1 < 0) {
                    JOptionPane.showMessageDialog(null, "Số lượng trong kho không thể là số âm", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (txtProductName.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,"Tên Sản Phẩm Bạn Không Được Để Trống","Lỗi",JOptionPane.ERROR_MESSAGE);
                } else if (newPath.equals("")) {
                    JOptionPane.showMessageDialog(null,"Hình Ảnh Sản Phẩm Bạn Không Được Để Trống","Lỗi",JOptionPane.ERROR_MESSAGE);
                } else if (txtProductPrice.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,"Giá Bán Sản Phẩm Bạn Không Được Để Trống","Lỗi",JOptionPane.ERROR_MESSAGE);
                } else if (txtProductDescription.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,"Mô Tả Sản Phẩm Bạn Không Được Để Trống","Lỗi",JOptionPane.ERROR_MESSAGE);
                } else if (txtNumberOfProduct.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Số Lượng Sản Phẩm Bạn Không Được Để Trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else if (txtNumberOfProduct.getText().equals("[a-zA-Z]+")||txtNumberOfProduct.getText().equals(".*[!@#$%].*")) {
                    JOptionPane.showMessageDialog(null, "Giá Bán Của Sản Phẩm Không Chứa Các Ký Tự Chữ Cái Hoặc Ký Tự Đặt Biệt", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    product.setName(txtProductName.getText());
                    try {
                        product.setPrice(Double.parseDouble(txtProductPrice.getText()));
                    } catch (NumberFormatException exception) {

                            JOptionPane.showMessageDialog(null, "Giá Tiền Phải Là Chữ Số", "Lỗi", JOptionPane.ERROR_MESSAGE);


                    }
                    product.setDescription(txtProductDescription.getText());
                    try {
                        product.setStock(Integer.parseInt(txtNumberOfProduct.getText()));
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null,"Số Lượng Sản Phẩm Phải Là Chữ Số","Lỗi",JOptionPane.ERROR_MESSAGE);
                    }
                    try {
                        productBUS.create(product);
                        JOptionPane.showMessageDialog(null,"Thêm Sản Phẩm Mới Thành Công","Thông Báo",JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        panelHeader.add(updateButton,BorderLayout.LINE_END);
        // end update button

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
        txtProductName = new Input("Tên Sản Phẩm");
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

        txtProductPrice = new Input("Giá Bán");
        txtProductPrice.setFont(Fonts.getFont(Font.PLAIN,15));
//        txtProductPrice.setPreferredSize(new Dimension(470,18));
        txtProductPrice.setColumns(50);
        txtProductPrice.setBackground(bg);
        panelLeft1.add(txtProductPrice,BorderLayout.CENTER);
        //

        panelRight1 = new JPanel();
        panelRight1.setLayout(new BorderLayout());
        panelRight1.setPreferredSize(new Dimension(480,55));
        panel2h.add(panelRight1,BorderLayout.LINE_END);

        productType = new JLabel("Loại Sản Phẩm");
        productType.setFont(Fonts.getFont(Font.BOLD,18));
        panelRight1.add(productType,BorderLayout.PAGE_START);

        String combo[] = {"Chọn Loại Sản Phẩm","Nước Uống","Thức Ăn","Thẻ"};
        comboBox = new JComboBox(combo);
        comboBox.setFont(Fonts.getFont(Font.ITALIC,15));
        comboBox.setPreferredSize(new Dimension(480,18));
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String)comboBox.getSelectedItem();

                if (selected.equals("Nước Uống")) {
                    product.setType(1);
                }
                else if (selected.equals("Thức Ăn")) {
                    product.setType(0);
                }
               else if (selected.equals("Thẻ")) {
                    product.setType(2);
                }
               else {

                        JOptionPane.showMessageDialog(null,"Chọn Loại Sản Phẩm","Thông Báo",JOptionPane.ERROR_MESSAGE);

                }
            }
        });
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

        txtNumberOfProduct = new Input("Số lượng sản phẩm");
        txtNumberOfProduct.setText(product.getStock()+"");
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

        txtProductDescription = new Input("Mô tả sản phẩm");
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

        imageEnd = new JPanel();
        imageEnd.setPreferredSize(new Dimension(600,400));
        panel3.add(imageEnd,BorderLayout.PAGE_END);

        chooseButton = new JButton("Chọn Ảnh");
        chooseButton.setBorder(new EmptyBorder(50,50,50,50));
        chooseButton.setPreferredSize(new Dimension(60,60));
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        var name = f.getName();
                        return f.isDirectory() || name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg");
                    }

                    @Override
                    public String getDescription() {
                        return "Image File (*.jpg, *.png, *.jpeg)";
                    }
                }
                );
                int result = chooser.showOpenDialog(CreateProductGUI.this);
                try {
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = chooser.getSelectedFile();
                        String path = selectedFile.getAbsolutePath();
                        var newPatht = "src/main/resources/images/" + selectedFile.getName();
                        var selectedImage = ImageIO.read(new File(path));
                        var newimage = new File(newPatht);
                        newimage.createNewFile();
                        ImageIO.write(selectedImage,"png",newimage);
                        System.out.println(newimage.getAbsolutePath());
                        product.setImage("images/" + selectedFile.getName());
                        newPath = product.getImage();
                       image.setIcon(new ImageIcon(selectedImage.getScaledInstance(200,300,Image.SCALE_SMOOTH)));
                    }
                }catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        panel3.add(chooseButton,BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        Helper.initUI();
        ServiceProvider.init();
        new CreateProductGUI();
    }
}
