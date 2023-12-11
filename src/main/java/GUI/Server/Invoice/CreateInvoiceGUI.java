package GUI.Server.Invoice;

import BUS.*;
import DTO.*;
import Utils.Helper;
import Utils.ServiceProvider;
import com.toedter.calendar.JDateChooser;
import lombok.Setter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateInvoiceGUI extends JPanel{
    @Setter
    private reloadInvoice onsave;
    JLabel titleCreateInvoice = new JLabel();
    JComboBox listEmployeeID;
    JLabel titleEmployeeCreate;
    JLabel titleProduct;
    JMenu menuProduct;
    JMenu food, drinks, card;
    DefaultTableModel listProductInvoiceModel;
    ProductBUS productBUS = ServiceProvider.getInstance().getService(ProductBUS.class);
    AccountBUS accountBUS = ServiceProvider.getInstance().getService(AccountBUS.class);
    ComputerBUS computerBUS = ServiceProvider.getInstance().getService(ComputerBUS.class);
    EmployeeBUS employeeService = ServiceProvider.getInstance().getService(EmployeeBUS.class);
    InvoiceBUS invoiceBUS = ServiceProvider.getInstance().getService(InvoiceBUS.class);
    InvoiceDetailBUS invoiceDetailBUS = ServiceProvider.getInstance().getService(InvoiceDetailBUS.class);
    JLabel lbTotalInvoice;
    List<ComboboxItem> listComputerComboboxItem;
    List<ComboboxItem> listAccountComboboxItem;
    List<ComboboxItem> listEmployeeComboboxItem;
    JDateChooser dateCreate;
    JComboBox status;
    JCheckBox isPaid;
    JButton btnAddInvoice;
    JButton btnSaveInvoice;
    JComboBox listAccountID;
    JLabel titleDateCreate;

    JLabel titleListAccountID;
    JLabel titleListComputerID;
    JComboBox listComputerID;
    JDialog jDialog;
    JLabel idInvoiceSelected;//muc dich de luu id cua hoa don muon edit




    public CreateInvoiceGUI() {
        this.setLayout(new BorderLayout());
        this.initCompunent();
    }

    public void initCompunent() {
        titleCreateInvoice.setText("Tạo hóa đơn bán");//tieu de cua khung tao hoa don
        titleCreateInvoice.setHorizontalAlignment(JLabel.CENTER);
        titleCreateInvoice.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 1)),
                BorderFactory.createEmptyBorder(20, 0, 20, 0)
        ));
        titleCreateInvoice.setFont(new Font("nunito", Font.BOLD, 30));
        JPanel headerCreateInvoice = new JPanel();
        headerCreateInvoice.add(titleCreateInvoice);
        headerCreateInvoice.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(42, 121, 255)),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));


        JLabel titleInforInvoice = new JLabel("Thông tin hóa đơn", JLabel.CENTER);
        titleInforInvoice.setFont(new Font("nunito", Font.BOLD, 25));
        titleInforInvoice.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(42, 121, 255)),
                BorderFactory.createEmptyBorder(20, 0, 10, 0)
        ));
        JPanel headerInforInvoice = new JPanel(new BorderLayout());
        headerInforInvoice.add(titleInforInvoice, BorderLayout.CENTER);


        titleDateCreate = new JLabel("Ngày tạo");
        titleDateCreate.setFont(new Font("nunito", Font.PLAIN, 17));
        dateCreate = new JDateChooser();
        dateCreate.setDateFormatString("yyyy-MM-dd");
        dateCreate.setFont(new Font("nunito", Font.PLAIN, 16));
        dateCreate.setDate(new Date());
        dateCreate.setPreferredSize(new Dimension(110, 25));
        JTextField dateChooserEditer =(JTextField) dateCreate.getDateEditor();
        dateChooserEditer.setEnabled(false);
        JPanel containDateCreate = new JPanel(new FlowLayout(FlowLayout.LEFT, 47, 0));
        containDateCreate.add(titleDateCreate);
        containDateCreate.add(dateCreate);


        ImageIcon employeesIcon = new ImageIcon("D:\\projectJava\\src\\GUI\\img\\nhanvien.png");
        Image imgEmployee = employeesIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        employeesIcon = new ImageIcon(imgEmployee);
        titleEmployeeCreate = new JLabel("Nhân viên", employeesIcon, JLabel.LEFT);
        titleEmployeeCreate.setFont(new Font("nunito", Font.PLAIN, 17));

        listEmployeeID = new JComboBox();
        List<Employee> allEmployee;
        EmployeeBUS employeeService = ServiceProvider.getInstance().getService(EmployeeBUS.class);
        allEmployee = employeeService.findAllEmployee();

        listEmployeeComboboxItem = new ArrayList<ComboboxItem>();
        for (int i = 0; i < allEmployee.size(); i++) {
            listEmployeeComboboxItem.add(new ComboboxItem());
            listEmployeeComboboxItem.get(i).setId(allEmployee.get(i).getId());
            listEmployeeComboboxItem.get(i).setValue(allEmployee.get(i).getName());
            listEmployeeID.addItem(listEmployeeComboboxItem.get(i).getValue());
        }

        listEmployeeID.setPreferredSize(new Dimension(110, 25));
        JPanel containEmployeeCreate = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        containEmployeeCreate.add(titleEmployeeCreate);
        containEmployeeCreate.add(listEmployeeID);


        ImageIcon computerIcon = new ImageIcon("D:\\projectJava\\src\\GUI\\img\\monitor.png");
        Image imgComputerIcon = computerIcon.getImage();
        imgComputerIcon = imgComputerIcon.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        computerIcon = new ImageIcon(imgComputerIcon);
        titleListComputerID = new JLabel("Máy", computerIcon, JLabel.LEFT);
        titleListComputerID.setFont(new Font("nunito", Font.PLAIN, 17));

        listComputerID = new JComboBox();
        ComputerBUS computerBUS = ServiceProvider.getInstance().getService(ComputerBUS.class);
        List<Computer> allComputer;
        try {
            allComputer = computerBUS.getAllComputers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        listComputerComboboxItem = new ArrayList<>(allComputer.size());
        for (int i = 0; i < allComputer.size(); i++) {
            listComputerComboboxItem.add(new ComboboxItem());
            listComputerComboboxItem.get(i).setId(allComputer.get(i).getId());
            listComputerComboboxItem.get(i).setValue(allComputer.get(i).getName());
            listComputerID.addItem(listComputerComboboxItem.get(i).getValue());
        }
        listComputerID.setPreferredSize(new Dimension(110, 25));
        JPanel containCoumputerID = new JPanel(new FlowLayout(FlowLayout.LEFT, 53, 0));
        containCoumputerID.add(titleListComputerID);
        containCoumputerID.add(listComputerID);

        titleListAccountID = new JLabel("Tài khoản");
        titleListAccountID.setFont(new Font("nunito", Font.PLAIN, 17));
        listAccountID = new JComboBox();
        AccountBUS accountBUS = ServiceProvider.getInstance().getService(AccountBUS.class);
        List<Account> allAccount;
        try {
            allAccount = accountBUS.getAllAccounts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        listAccountComboboxItem = new ArrayList<ComboboxItem>();
        listAccountComboboxItem.add(new ComboboxItem());
        listAccountComboboxItem.get(0).setId(0);
        listAccountComboboxItem.get(0).setValue("Khách vãn lai");
        listAccountID.addItem(listAccountComboboxItem.get(0).getValue());
        for (int i = 0; i < allAccount.size(); i++) {
            listAccountComboboxItem.add(new ComboboxItem());
            listAccountComboboxItem.get(i + 1).setId(allAccount.get(i).getId());
            listAccountComboboxItem.get(i + 1).setValue(allAccount.get(i).getUsername());
            listAccountID.addItem(listAccountComboboxItem.get(i + 1).getValue());
        }
        listAccountID.setPreferredSize(new Dimension(110, 25));
        JPanel containAccountID = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 0));
        containAccountID.add(titleListAccountID);
        containAccountID.add(listAccountID);

        titleProduct = new JLabel("Chọn sản phẩm");
        titleProduct.setFont(new Font("nunito", Font.PLAIN, 17));
        JMenuBar menu = new JMenuBar();
        menuProduct = new JMenu("Sản phẩm");
        menuProduct.setMargin(new Insets(0, 40, 0, 0));
        menuProduct.setPreferredSize(new Dimension(100, 25));

        food = new JMenu();
        food = showProductsByType(Product.ProductType.FOOD);
        food.setText("food");
        food.setPreferredSize(new Dimension(97, 25));

        drinks = new JMenu();
        drinks = showProductsByType(Product.ProductType.DRINK);
        drinks.setText("drink");

        card = new JMenu("card");
        card = showProductsByType(Product.ProductType.CARD);
        card.setText("card");

        menuProduct.add(food);
        menuProduct.add(drinks);
        menuProduct.add(card);
        menu.add(menuProduct);

        JPanel containProduct = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        containProduct.add(titleProduct);
        containProduct.add(menu);

        isPaid = new JCheckBox("Đã thanh toán");
        JLabel lbStatus = new JLabel("Trạng thái");
        status = new JComboBox();
        status.addItem(Invoice.Status.WAITING_FOR_ACCEPT);
        status.addItem(Invoice.Status.ACCEPTED);
        status.addItem(Invoice.Status.DONE);
        status.addItem(Invoice.Status.REJECTED);
        JPanel containStatus = new JPanel(new FlowLayout());
        containStatus.add(lbStatus);
        containStatus.add(status);


        //Muc dich de luu id cua hoa don muon edit,
        idInvoiceSelected = new JLabel();
        idInvoiceSelected.setVisible(false);


        JPanel bodyInforInvoice = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 18));
        bodyInforInvoice.add(containDateCreate);
        bodyInforInvoice.add(containAccountID);
        bodyInforInvoice.add(containCoumputerID);
        bodyInforInvoice.add(containEmployeeCreate);
        bodyInforInvoice.add(containProduct);
        bodyInforInvoice.add(isPaid);
        bodyInforInvoice.add(containStatus);
        bodyInforInvoice.add(idInvoiceSelected);


        JPanel inforInvoice = new JPanel(new BorderLayout(0, 0));
        inforInvoice.setPreferredSize(new Dimension(250, 500));
        inforInvoice.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(42, 121, 255)),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        inforInvoice.add(headerInforInvoice, BorderLayout.PAGE_START);
        inforInvoice.add(bodyInforInvoice, BorderLayout.CENTER);


        JLabel titleListProductInvoice = new JLabel("Sản phẩm hóa đơn", JLabel.CENTER);
        titleListProductInvoice.setFont(new Font("nunito", Font.BOLD, 25));
        titleListProductInvoice.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 1)),
                BorderFactory.createEmptyBorder(20, 0, 20, 0)
        ));

        listProductInvoiceModel = new DefaultTableModel();
        JTable listProductInvoice = new JTable();
        listProductInvoice.setComponentPopupMenu(operationForProductInvoice(listProductInvoice, listProductInvoiceModel));
        listProductInvoice.getTableHeader().setPreferredSize(new Dimension(0, 35));
        listProductInvoice.getTableHeader().setFont(new Font("nunito", Font.BOLD, 17));
        listProductInvoice.setModel(listProductInvoiceModel);
        JScrollPane listProductInvoiceScrollPane = new JScrollPane(listProductInvoice);

        lbTotalInvoice = new JLabel("Tổng tiền: " + "0.0" + " VNĐ", JLabel.RIGHT);
        lbTotalInvoice.setFont(new Font("nunito", Font.ITALIC, 17));
        lbTotalInvoice.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 1)),
                BorderFactory.createEmptyBorder(20, 0, 20, 100)
        ));

        JPanel containListProductInvoice = new JPanel(new BorderLayout());
        containListProductInvoice.add(titleListProductInvoice, BorderLayout.PAGE_START);
        containListProductInvoice.add(listProductInvoiceScrollPane, BorderLayout.CENTER);
        containListProductInvoice.add(lbTotalInvoice, BorderLayout.PAGE_END);

        JPanel bodyCreateInvoice = new JPanel(new BorderLayout());
        bodyCreateInvoice.setBorder(BorderFactory.createLineBorder(Color.red));
        bodyCreateInvoice.add(inforInvoice, BorderLayout.LINE_START);
        bodyCreateInvoice.add(containListProductInvoice, BorderLayout.CENTER);


        btnAddInvoice = new JButton("Tạo hóa đơn");
        btnAddInvoice.setPreferredSize(new Dimension(150, 30));
        btnAddInvoice.setFocusPainted(false);

        btnSaveInvoice = new JButton("Lưu hóa đơn");
        btnSaveInvoice.setPreferredSize(new Dimension(150, 30));
        btnSaveInvoice.setFocusPainted(false);
        btnSaveInvoice.setVisible(false);
        JPanel footerCreateInvoice = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        footerCreateInvoice.add(btnAddInvoice);
        footerCreateInvoice.add(btnSaveInvoice);

        add(headerCreateInvoice, BorderLayout.PAGE_START);
        add(bodyCreateInvoice, BorderLayout.CENTER);
        add(footerCreateInvoice, BorderLayout.PAGE_END);
        setVisible(true);
    }


    public void showDiaLog(Invoice.InvoiceType type) {
        jDialog = new JDialog();
        jDialog.setLayout(new BorderLayout());
        jDialog.add(this, BorderLayout.CENTER);
        jDialog.setSize(1000, 600);
        jDialog.setLocationRelativeTo(null);
        jDialog.setVisible(true);
        event();
        showGUICreateInvoiceByType(type);
        jDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }


    public JMenu showProductsByType(Product.ProductType type){
        List<Product> listProduct = null;
        try {
            listProduct = productBUS.filterByTypeProduct(type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JMenu jMenu = new JMenu();
        for(int i = 0; i < listProduct.size();i++) {
            String nameJCheckBoxMenuItem = listProduct.get(i).getName();
            JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem(nameJCheckBoxMenuItem);
            eventOfCheckBoxMenuItem(checkBoxMenuItem);
            jMenu.add(checkBoxMenuItem);
        }
        return jMenu;
    }



    public void eventOfCheckBoxMenuItem(JCheckBoxMenuItem jCheckBoxMenuItem){
        jCheckBoxMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Invoice.InvoiceType type;
                if(titleCreateInvoice.getText().toLowerCase().equals("tạo hóa đơn bán")||titleCreateInvoice.getText().toLowerCase().equals("chi tiết hóa đơn bán"))
                    type = Invoice.InvoiceType.EXPORT;
                else
                    type = Invoice.InvoiceType.IMPORT;

                JTextField quantity = new JTextField();
                JTextField price = new JTextField();
                Object[] message;
                String option[] = {"OK"};
                int result;
                Product product;
                try {
                    product = productBUS.findByName(jCheckBoxMenuItem.getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                if(type == Invoice.InvoiceType.EXPORT){
                    message = new Object[]{
                            "Số lượng: ", quantity
                    };
                    result = JOptionPane.showOptionDialog(null,message,null,JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE,null,option,option[0]);
                    if(Helper.isNumber(quantity.getText()) && Integer.parseInt(quantity.getText()) > 0){
                        if(productIsOccur(listProductInvoiceModel.getRowCount(),product.getId()) != null){
                            int rowOccur = productIsOccur(listProductInvoiceModel.getRowCount(),product.getId());//dong sp bi trung
                            int oldQuantity = (int) listProductInvoiceModel.getValueAt(rowOccur,2);//so luong sp cu
                            listProductInvoiceModel.setValueAt(Integer.parseInt(quantity.getText())+oldQuantity,rowOccur,2);
                            listProductInvoiceModel.setValueAt((Integer.parseInt(quantity.getText())+oldQuantity)*product.getPrice(),rowOccur,4);
                        }
                        else{
                            listProductInvoiceModel.addRow((Object[]) productInvoiceRow(Integer.parseInt(quantity.getText()),jCheckBoxMenuItem.getText(),0,Invoice.InvoiceType.EXPORT));
                        }
                    }
                    else{
                        while ((!Helper.isNumber(quantity.getText()) || Integer.parseInt(quantity.getText()) < 0) && result != JOptionPane.CLOSED_OPTION) {
                            JOptionPane.showMessageDialog(null, "Dữ liệu nhập chưa đúng !");
                            result = JOptionPane.showOptionDialog(null,message,null,JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE,null,option,option[0]);
                            return;
                        }
                        if(productIsOccur(listProductInvoiceModel.getRowCount(),product.getId()) != null){
                            int rowOccur = productIsOccur(listProductInvoiceModel.getRowCount(),product.getId());//dong sp bi trung
                            int oldQuantity = (int) listProductInvoiceModel.getValueAt(rowOccur,2);//so luong sp cu
                            listProductInvoiceModel.setValueAt(Integer.parseInt(quantity.getText())+oldQuantity,rowOccur,2);
                            listProductInvoiceModel.setValueAt((Integer.parseInt(quantity.getText())+oldQuantity)*product.getPrice(),rowOccur,4);
                        }
                        else {
                            listProductInvoiceModel.addRow((Object[]) productInvoiceRow(Integer.parseInt(quantity.getText()), jCheckBoxMenuItem.getText(), 0, Invoice.InvoiceType.EXPORT));
                        }
                    }
                }
                else {
                    message = new Object[]{
                            "Số lượng:", quantity,
                            "Giá tiền:", price
                    };
                    result = JOptionPane.showOptionDialog(null,message,null,JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE,null,option,option[0]);
                    if(Helper.isNumber(quantity.getText()) && Integer.parseInt(quantity.getText()) > 0 && Helper.isNumber(price.getText()) && Double.parseDouble(price.getText()) > 0.0){
                        if(productIsOccur(listProductInvoiceModel.getRowCount(),product.getId())!=null){
                            int rowOccur = productIsOccur(listProductInvoiceModel.getRowCount(),product.getId());
                            int oldQuantity = (int) listProductInvoiceModel.getValueAt(rowOccur,2);
                            Double newPrice = Double.parseDouble(price.getText());
                            listProductInvoiceModel.setValueAt(Integer.parseInt(quantity.getText())+oldQuantity,rowOccur,2);
                            listProductInvoiceModel.setValueAt(newPrice,rowOccur,3);
                            listProductInvoiceModel.setValueAt((Integer.parseInt(quantity.getText())+oldQuantity)*newPrice,rowOccur,4);
                        }
                        else{
                            listProductInvoiceModel.addRow((Object[]) productInvoiceRow(Integer.parseInt(quantity.getText()),jCheckBoxMenuItem.getText(),Double.parseDouble(price.getText()),Invoice.InvoiceType.IMPORT));
                        }
                    }
                    else{
                        while ((!Helper.isNumber(quantity.getText()) || Integer.parseInt(quantity.getText()) < 0 || !Helper.isNumber(price.getText()) || Integer.parseInt(price.getText()) < 0) && result != JOptionPane.CLOSED_OPTION) {
                            JOptionPane.showMessageDialog(null, "Dữ liệu nhật chưa đúng !");
                            result = JOptionPane.showOptionDialog(null,message,null,JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE,null,option,option[0]);
                            return;
                        }
                        if(productIsOccur(listProductInvoiceModel.getRowCount(),product.getId())!=null){
                            int rowOccur = productIsOccur(listProductInvoiceModel.getRowCount(),product.getId());
                            int oldQuantity = (int) listProductInvoiceModel.getValueAt(rowOccur,2);
                            Double newPrice = Double.parseDouble(price.getText());
                            listProductInvoiceModel.setValueAt(Integer.parseInt(quantity.getText())+oldQuantity,rowOccur,2);
                            listProductInvoiceModel.setValueAt(newPrice,rowOccur,3);
                            listProductInvoiceModel.setValueAt((Integer.parseInt(quantity.getText())+oldQuantity)*newPrice,rowOccur,4);
                        }
                        else{
                            listProductInvoiceModel.addRow((Object[]) productInvoiceRow(Integer.parseInt(quantity.getText()),jCheckBoxMenuItem.getText(),Double.parseDouble(price.getText()),Invoice.InvoiceType.IMPORT));
                        }
                    }
                }
                lbTotalInvoice.setText(String.valueOf(caculateTotalMoney()) +" VNĐ");
            }
        });
    }


    //ham nay co chuc nang se tao cac hang sang pham co trong hoa don
    public Object productInvoiceRow(int quantity,String nameProduct, double priceImport, Invoice.InvoiceType type){
        Product product = null;
        try {
            product = productBUS.findByName(nameProduct);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        if(type == Invoice.InvoiceType.EXPORT){
            return new Object[]{product.getId(),product.getName(),quantity,product.getPrice(),product.getPrice() * quantity};
        }
        else {
            return new Object[]{product.getId(),product.getName(),quantity,priceImport,priceImport*quantity};
        }
    }


    public Integer productIsOccur(Integer numberProduct,Integer productId){
        for(int i = 0; i < numberProduct;i++){
            if(productId == listProductInvoiceModel.getValueAt(i,0)){
                return i;
            }
        }
        return null;
    }



    public void event(){
        btnAddInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Invoice.InvoiceType type;
                if(titleCreateInvoice.getText().toLowerCase().equals("tạo hóa đơn bán")){
                    type = Invoice.InvoiceType.EXPORT;
                }
                else {
                    type = Invoice.InvoiceType.IMPORT;
                }
                Invoice newInvoice = invoiceBUS.createInvoice(getInforInvoice(type));
                createListInvoiceDetail(newInvoice.getId());
                JOptionPane.showMessageDialog(null,"Thêm hóa đơn thành công");
                List<Invoice> invoices = invoiceBUS.findAllByType(type);
                if(onsave != null){
                    onsave.loadJTable(type,invoices);
                }
                jDialog.dispose();
            }
        });

        btnSaveInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Invoice.InvoiceType type;
                if (titleCreateInvoice.getText().toLowerCase().equals("chi tiết hóa đơn bán")) {
                    type = Invoice.InvoiceType.EXPORT;
                } else {
                    type = Invoice.InvoiceType.IMPORT;
                }
                //lay thong tin hoa don
                Invoice invoice = getInforInvoice(type);
                invoice.setId(Integer.parseInt(idInvoiceSelected.getText()));
                //xoa tat ca cac sp cu cua hoa don don trong csdl
                invoiceDetailBUS.deleteDetailInvoice(Integer.parseInt(idInvoiceSelected.getText()));
                //goi toi ham updateInvoiceDetail
                createListInvoiceDetail(invoice.getId());
                Invoice signal = invoiceBUS.updateInvoice(invoice);
                if (signal == null) {
                    JOptionPane.showMessageDialog(null, "Sửa hóa đươn không thành công");
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa hóa đơn thành công");
                }

                List<Invoice> invoices = invoiceBUS.findAllByType(type);
                if(onsave != null){
                    onsave.loadJTable(type,invoices);
                }
                jDialog.dispose();
            }
        });
    }


    //Mehthod nay dung de tinh toan tong tien cua mot hoa don
    public Double caculateTotalMoney(){
        Double totalMoney = 0.0;
        for(int i = 0; i < listProductInvoiceModel.getRowCount();i++){
            totalMoney+=(Double)listProductInvoiceModel.getValueAt(i,4);
        }
        return totalMoney;
    }




    //Method nay co chuc nang lay thogn tin cua hoa don, sau do tra ve thogn tin cua hoa don
    public Invoice getInforInvoice(Invoice.InvoiceType type){
        Computer createdToComputer;
        Account createdToAccount;
        Employee createdByEmployee;
        int computerID;
        int accountID;
        int employeeID = listEmployeeComboboxItem.get(listEmployeeID.getSelectedIndex()).getId();
        createdByEmployee = employeeService.findEmployeeById(employeeID);
        Double totalInvoice = caculateTotalMoney();
        if(type == Invoice.InvoiceType.IMPORT){
            computerID = accountID = 0;
            createdToComputer = null;
            createdToAccount = null;
        }
        else {
            computerID = listComputerComboboxItem.get(listComputerID.getSelectedIndex()).getId();
            accountID = listAccountComboboxItem.get(listAccountID.getSelectedIndex()).getId();
            try {
                createdToComputer = computerBUS.getComputerById(computerID);
                createdToAccount = accountBUS.findById(accountID);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        Invoice.Status statusNewInvoice = (Invoice.Status) status.getSelectedItem();
        return new Invoice(accountID,createdToAccount,computerID,createdToComputer,totalInvoice,new Date(),statusNewInvoice,isPaid.isSelected(),employeeID,createdByEmployee,type);
    };


    //method nay dung de hien thi giao dien tao hoa don,
    //TH ma nguoi dung muon tao hoa don ban thi hien thi giao dien khac
    //TH nguoi dung muon tao hao don nhap thi hien thi giao dien khac
    public void showGUICreateInvoiceByType(Invoice.InvoiceType type){
        String columnProductInvoiceExport[] = {"MaSP","Tên sản phẩm","Số lượng","Giá bán","Tổng tiền"};
        String columnProductInvoiceImport[] = {"MaSP","Tên sản phẩm","Số luọng","Giá nhập","Tổng tiền"};
        if(type == Invoice.InvoiceType.EXPORT){
            titleCreateInvoice.setText("Tạo hóa đơn bán");
            titleListAccountID.setEnabled(true);
            listAccountID.setEnabled(true);
            titleListComputerID.setEnabled(true);
            listComputerID.setEnabled(true);
            listProductInvoiceModel.setColumnIdentifiers(columnProductInvoiceExport);
        }
        else {
            titleCreateInvoice.setText("Tạo hóa đơn nhập");
            titleListAccountID.setEnabled(false);
            listAccountID.setEnabled(false);
            listAccountID.setSelectedItem(null);
            titleListComputerID.setEnabled(false);
            listComputerID.setEnabled(false);
            listComputerID.setSelectedItem(null);
            listProductInvoiceModel.setColumnIdentifiers(columnProductInvoiceImport);

        }
    }


    public JPopupMenu operationForProductInvoice (JTable jtable,DefaultTableModel model){
        JMenuItem delete = new JMenuItem("delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtable.getSelectedRowCount() == 1){
                    int confirmDeleteInvoice = JOptionPane.showConfirmDialog(null,"Bạn muốn xóa sản phẩm này ?");
                    if(confirmDeleteInvoice == JOptionPane.NO_OPTION){
                        return;
                    }
                    else if(confirmDeleteInvoice == JOptionPane.YES_OPTION){
                        int indexRowSelected = jtable.getSelectedRow();
                        model.removeRow(indexRowSelected);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"Không thể xóa nhiều sản phẩm cùng lúc","Anounce",JOptionPane.ERROR_MESSAGE);
                }
                lbTotalInvoice.setText(String.valueOf(caculateTotalMoney()) + " VNĐ");
            }
        });


        JPopupMenu jPopupMenu = new JPopupMenu();
        jPopupMenu.add(delete);
        return jPopupMenu;
    }




    //Method nay co chuc nang lay tat ca cac hang san pham cua hoa don va luu no vao csdl
    //thong qua viec goi method createInvoiceDetail cua tang BUS
    public void createListInvoiceDetail(Integer invoiceId){
        for(int i = 0; i <listProductInvoiceModel.getRowCount();i++){
            int productId = (int) listProductInvoiceModel.getValueAt(i,0);
            int quantity = (int)listProductInvoiceModel.getValueAt(i,2);
            double price = (double) listProductInvoiceModel.getValueAt(i,3);
            invoiceDetailBUS.createInvoiceDetail(new InvoiceDetail(invoiceId,productId,price,quantity));
        }
    }
}



