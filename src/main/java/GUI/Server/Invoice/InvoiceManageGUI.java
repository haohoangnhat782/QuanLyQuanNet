package GUI.Server.Invoice;

import Utils.Helper;
import Utils.ServiceProvider;
import com.toedter.calendar.JDateChooser;
import DTO.*;
import BUS.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceManageGUI extends JPanel{
    JLabel actionShowInvoiceImport ;
    JLabel actionShowInvoiceExport ;
    JLabel titleContainShowListInvoice;

    JLabel titleContainAccountFilter;
    JLabel titleComputerFilter;
    JComboBox computersToFilter;
    JComboBox accountToFilter;
    JComboBox employeeToFilter;
    JPanel containShowListInvoice;
    JScrollPane listInvoiceScrollPaneExport;
    JScrollPane listInvoiceScrollPaneImport;
    JButton btnCancel;
    JButton btnSearch;
    JButton btnCreateInvoice;
    JDateChooser dateChooserFrom;
    JDateChooser dateChooserTo;
    DefaultTableModel listInvoiceModelExport;
    DefaultTableModel listInvoiceModelImport;
    InvoiceBUS invoiceBUS = ServiceProvider.getInstance().getService(InvoiceBUS.class);
    EmployeeBUS employeeService = ServiceProvider.getInstance().getService(EmployeeBUS.class);
    AccountBUS accountBUS = ServiceProvider.getInstance().getService(AccountBUS.class);
    ComputerBUS computerBUS = ServiceProvider.getInstance().getService(ComputerBUS.class);
    ProductBUS productBUS = ServiceProvider.getInstance().getService(ProductBUS.class);

    JTextField limitTotalFrom;
    JTextField limitTotalTo;
    ArrayList<ComboboxItem> listComputerComboboxItem;
    ArrayList<ComboboxItem> listAccountComboboxItem;
    ArrayList<ComboboxItem> listEmployeeComboboxItem;
    JTable listInvoiceExport;
    JTable listInvoiceImport;
    JLabel lbtotalMoneyAllInvoice;


    public InvoiceManageGUI(){
        this.setLayout(new BorderLayout());
        initManagerInvoice();
        event();
        List<Invoice> invoices = invoiceBUS.findAllByType(Invoice.InvoiceType.EXPORT);
        loadJTable(Invoice.InvoiceType.EXPORT,invoices);
    }

    @Override
    public void setVisible(boolean aFlag) {
        if (aFlag){
            List<Invoice> invoices = invoiceBUS.findAllByType(Invoice.InvoiceType.EXPORT);
            loadJTable(Invoice.InvoiceType.EXPORT,invoices);
        }

    }

    //Cac phuogn thuc duoc dung trong code
    public static InvoiceManageGUI getInstance(){
        return new InvoiceManageGUI();
    }

    public void initManagerInvoice(){
        //manager header START
//        1-----Title Quan Ly hoa don-START---
        JLabel titleManagerInvoice = new JLabel("Quản lý hóa đơn");
        titleManagerInvoice.setFont(new Font("nunito",Font.BOLD,30));
        titleManagerInvoice.setForeground(Color.WHITE);
        titleManagerInvoice.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0,0,0,1)),
                BorderFactory.createEmptyBorder(0,20,0,0)
        ));
        JPanel containTitleManagerInvoice = new JPanel();
        containTitleManagerInvoice.setLayout(new FlowLayout(FlowLayout.LEFT));
        containTitleManagerInvoice.setPreferredSize(new Dimension(1198,100));
        containTitleManagerInvoice.setBackground(new Color(42,121,255));
        containTitleManagerInvoice.add(titleManagerInvoice);
//        1-----Title Quan Ly hoa don-END---


//        2-------ACTION IN HEADER-START-----
//        2.1------Hoa don nhap-Hoa don ban-START-----
        actionShowInvoiceExport = new JLabel("Hóa đơn bán");
        actionShowInvoiceExport.setFont(new Font("nunito",Font.BOLD,20));
        actionShowInvoiceExport.setCursor(new Cursor(Cursor.HAND_CURSOR));
        actionShowInvoiceExport.setForeground(Color.WHITE);
        actionShowInvoiceExport.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE),
                BorderFactory.createEmptyBorder(0,0,5,0)
        ));
        actionShowInvoiceImport = new JLabel("Hóa đơn nhập");
        actionShowInvoiceImport.setFont(new Font("nunito",Font.BOLD,20));
        actionShowInvoiceImport.setCursor(new Cursor(Cursor.HAND_CURSOR));
        actionShowInvoiceImport.setForeground(Color.WHITE);
        JPanel containShowInvoiceAction = new JPanel(new FlowLayout(FlowLayout.LEFT));
        containShowInvoiceAction.setBackground(new Color(42,121,255));
        containShowInvoiceAction.add(actionShowInvoiceExport);
        containShowInvoiceAction.add(new JLabel("       "));
        containShowInvoiceAction.add(actionShowInvoiceImport);
//        2.1------Hoa don nhap-hoa don ban-END-----



//        2.2-------Nut them hoa don moi-START
        btnCreateInvoice = new JButton("+ tạo mới");
        btnCreateInvoice.setBackground(new Color(238,238,238));
        btnCreateInvoice.setFont(new Font("nunito",Font.PLAIN,17));
        btnCreateInvoice.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCreateInvoice.setFocusPainted(false);//dung de bo border cua text ben trong button
        btnCreateInvoice.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0,0,0,1)),
                BorderFactory.createEmptyBorder(5,20,5,20)
        ));

        JTextField searchField = new JTextField(20);

        JPanel containCreateNewInvoiceAction = new JPanel(new FlowLayout(FlowLayout.RIGHT,30,5));//muc dich set hgap va vgap de cang chinh JButton
        containCreateNewInvoiceAction.setBackground(new Color(42,121,255));
        containCreateNewInvoiceAction.add(btnCreateInvoice,BorderLayout.LINE_END);
//        2.2-----Nut them hoa don moi-END-----


        JPanel containActionInHeader = new JPanel();
        containActionInHeader.setLayout(new BoxLayout(containActionInHeader,BoxLayout.X_AXIS));
        containActionInHeader.setPreferredSize(new Dimension(1190,50));
        containActionInHeader.setBackground(new Color(42,121,255));
        containActionInHeader.add( containShowInvoiceAction);
        containActionInHeader.add(containCreateNewInvoiceAction);
//        2-----ACTION IN HEADER-END------


        JPanel managerInvoiceHeader = new JPanel();
        managerInvoiceHeader.setLayout(new BoxLayout(managerInvoiceHeader,BoxLayout.Y_AXIS));
        managerInvoiceHeader.add(containTitleManagerInvoice);
        managerInvoiceHeader.add(containActionInHeader);
//        MANAGER HEADER END


//        MANAGER BODY START
//        1---------Body Filter for Search start-----
//            a-----Title of FILTER---
        ImageIcon iconFilter = new ImageIcon("D:\\projectJava\\src\\GUI\\img\\filter.png");
        Image imgFilter = iconFilter.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
        iconFilter = new ImageIcon(imgFilter);
        JLabel titleFilter = new JLabel("Lọc tìm kiếm",iconFilter,JLabel.CENTER);
        titleFilter.setFont(new Font("nunito",Font.BOLD,22));
        JPanel containTitleFilter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        containTitleFilter.setPreferredSize(new Dimension(300,50));
        containTitleFilter.add(titleFilter);


//            b------Date limit of filter------
        JLabel limitDateFrom = new JLabel("Từ ngày");
        dateChooserFrom = new JDateChooser();
        dateChooserFrom.setFont(new Font("nunito",Font.PLAIN,16));
        dateChooserFrom.setDateFormatString("yyyy-MM-dd");
        dateChooserFrom.setDate(new Date());
        JPanel containDateFrom = new JPanel(new GridLayout(2,1,0,0));
        containDateFrom.setPreferredSize(new Dimension(140,65));
        containDateFrom.add(limitDateFrom);
        containDateFrom.add(dateChooserFrom);


        JLabel limitDateTo = new JLabel("Đến ngày");
        dateChooserTo = new JDateChooser();
        dateChooserTo.setFont(new Font("nunito",Font.PLAIN,16));
        dateChooserTo.setDateFormatString("yyyy-MM-dd");
        dateChooserTo.setDate(new Date());;

        JPanel containDateTo = new JPanel(new GridLayout(2,1,0,0));
        containDateTo.setPreferredSize(new Dimension(140,65));
        containDateTo.add(limitDateTo);
        containDateTo.add(dateChooserTo);

        JPanel containLimitDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        containLimitDate.setPreferredSize(new Dimension(300,65));
        containLimitDate.add(containDateFrom);
        containLimitDate.add(new JLabel(""));
        containLimitDate.add(containDateTo);



//            c-------Total limit of FIlter---
        ImageIcon totalIcon = new ImageIcon("D:\\projectJava\\src\\GUI\\img\\dollar.png");
        Image imgMoney = totalIcon.getImage();
        imgMoney = imgMoney.getScaledInstance(20,20,Image.SCALE_SMOOTH);
        totalIcon = new ImageIcon(imgMoney);
        JLabel titleLimitTotal = new JLabel("Khoảng total",totalIcon,JLabel.LEFT);
//        titleLimitTotal.setPreferredSize(new Dimension(150,30));
        limitTotalFrom = new JTextField(4);
        limitTotalFrom.setPreferredSize(new Dimension(0,25));
        limitTotalTo = new JTextField(4);
        limitTotalTo.setPreferredSize(new Dimension(0,25));
        JPanel containLimitTotal = new JPanel(new FlowLayout(FlowLayout.LEFT,4,0));
        containLimitTotal.setBorder(new EmptyBorder(20,0,0,0));
        containLimitTotal.setPreferredSize(new Dimension(300,60));
        containLimitTotal.add(titleLimitTotal);
        containLimitTotal.add(limitTotalFrom);
        containLimitTotal.add(new JLabel("đến"));
        containLimitTotal.add(limitTotalTo);



//        d------Choose Employee of Filter---
        ImageIcon employeesIcon = new ImageIcon("D:\\projectJava\\src\\GUI\\img\\nhanvien.png");
        Image imgEmployee = employeesIcon.getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH);
        employeesIcon = new ImageIcon(imgEmployee);
        JLabel titleContainEmployeeFilter = new JLabel("Nhân viên ",employeesIcon,JLabel.LEFT);
        employeeToFilter = new JComboBox();

        List<Employee> allEmployee;
        allEmployee = employeeService.findAllEmployee();
        listEmployeeComboboxItem = new ArrayList<ComboboxItem>();
        listEmployeeComboboxItem.add(new ComboboxItem());
        for(int i = 0; i < allEmployee.size();i++){
            listEmployeeComboboxItem.add(new ComboboxItem());
            listEmployeeComboboxItem.get(i).setId(allEmployee.get(i).getId());
            listEmployeeComboboxItem.get(i).setValue(allEmployee.get(i).getName());
            employeeToFilter.addItem(listEmployeeComboboxItem.get(i).getValue());
        }

        employeeToFilter.setPreferredSize(new Dimension(140,32));
        employeeToFilter.setSelectedItem(null);
        JPanel containEmployeeFilter = new JPanel(new FlowLayout());
        containEmployeeFilter.add(titleContainEmployeeFilter);
        containEmployeeFilter.add(new JLabel("    "));
        containEmployeeFilter.add(employeeToFilter);


//        e------choose Account of Filter---
        ImageIcon userIcon = new ImageIcon("D:\\projectJava\\src\\GUI\\img\\user.png");
        Image imgUser = userIcon.getImage().getScaledInstance(19,19,Image.SCALE_SMOOTH);
        userIcon = new ImageIcon(imgUser);
        titleContainAccountFilter = new JLabel("Tên tài khoản ",userIcon,JLabel.LEFT);
        accountToFilter = new JComboBox();
        List<Account> allAccount;
        try {
            allAccount = accountBUS.getAllAccounts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        listAccountComboboxItem = new ArrayList<ComboboxItem>();
        listAccountComboboxItem.add(new ComboboxItem());
        listAccountComboboxItem.get(0).setId(-1);
        listAccountComboboxItem.get(0).setValue("Khách vãn lai");
        accountToFilter.addItem(listAccountComboboxItem.get(0).getValue());
        for(int i = 0; i < allAccount.size();i++){
            listAccountComboboxItem.add(new ComboboxItem());
            listAccountComboboxItem.get(i+1).setId(allAccount.get(i).getId());
            listAccountComboboxItem.get(i+1).setValue(allAccount.get(i).getUsername());
            accountToFilter.addItem(listAccountComboboxItem.get(i+1).getValue());
        }

        accountToFilter.setSelectedItem(null);
        accountToFilter.setPreferredSize(new Dimension(120,32));
        JPanel containAccountFilter = new JPanel(new FlowLayout());
        containAccountFilter.add(titleContainAccountFilter);
        containAccountFilter.add(new JLabel(""));
        containAccountFilter.add(accountToFilter);


//        f----choose Computer of FIlter
        ImageIcon computerIcon = new ImageIcon("D:\\projectJava\\src\\GUI\\img\\monitor.png");
        Image imgComputerIcon = computerIcon.getImage();
        imgComputerIcon = imgComputerIcon.getScaledInstance(20,20,Image.SCALE_SMOOTH);
        computerIcon = new ImageIcon(imgComputerIcon);
        titleComputerFilter = new JLabel("Chọn máy",computerIcon,JLabel.CENTER);
        titleComputerFilter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0,0,0,1)),
                BorderFactory.createEmptyBorder(0,0,0,24)
        ));
        computersToFilter = new JComboBox();
        computersToFilter.setPreferredSize(new Dimension(120,32));
        List<Computer> allComputer;
        try {
            allComputer = computerBUS.getAllComputers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        listComputerComboboxItem = new ArrayList<>(allComputer.size());
        for(int i = 0; i < allComputer.size();i++){
            listComputerComboboxItem.add(new ComboboxItem());
            listComputerComboboxItem.get(i).setId(allComputer.get(i).getId());
            listComputerComboboxItem.get(i).setValue(allComputer.get(i).getName());
            computersToFilter.addItem(listComputerComboboxItem.get(i).getValue());
        }

        computersToFilter.setSelectedItem(null);
        JPanel containComputerFilter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        containComputerFilter.setPreferredSize(new Dimension(245,40));
        containComputerFilter.add(titleComputerFilter);
        containComputerFilter.add(computersToFilter);


//        g------button cancel and shearch ----
        btnCancel = new JButton("Cancel");
        btnCancel.setPreferredSize(new Dimension(100,35));
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.setBackground(new Color(255,128,0));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0,0,0,1)),
                BorderFactory.createEmptyBorder(0,0,0,0)
        ));
        btnSearch = new JButton("Search");
        btnSearch.setPreferredSize(new Dimension(100,35));
        btnSearch.setFocusPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.setBackground(new Color(42,121,255));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0,0,0,1)),
                BorderFactory.createEmptyBorder(0,0,0,0)
        ));
        JPanel containActionInFilter = new JPanel(new FlowLayout());
        containActionInFilter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0,0,0,1)),
                BorderFactory.createEmptyBorder(30,0,0,0)
        ));
        containActionInFilter.add(btnCancel);
        containActionInFilter.add(new JLabel("  "));
        containActionInFilter.add(btnSearch);


        JPanel managerInvoiceFilter = new JPanel(new FlowLayout());
        managerInvoiceFilter.setPreferredSize(new Dimension(350,498));
        managerInvoiceFilter.setBorder(BorderFactory.createMatteBorder(0,0,0,2,new Color(42,121,255)));
        managerInvoiceFilter.add(containTitleFilter);
        managerInvoiceFilter.add(containLimitDate);
        managerInvoiceFilter.add(containLimitTotal);
        managerInvoiceFilter.add(containComputerFilter);
        managerInvoiceFilter.add(containAccountFilter);
        managerInvoiceFilter.add(containEmployeeFilter);
        managerInvoiceFilter.add((containActionInFilter));

//        1--------Body Filter for search end------



//        2--------Body show list invoice-start-----

//            a----Hoa don ban----
        listInvoiceModelExport = new DefaultTableModel();
        String[] columnsInvoiceExport = {"ID","Tài khoản","Máy","Ngày tạo","Nhân viên","Thanh toán","Trạng thái","Tổng tiền"};
        listInvoiceModelExport.setColumnIdentifiers(columnsInvoiceExport);

        listInvoiceExport = new JTable();
        listInvoiceExport.setRowSelectionAllowed(true);
        listInvoiceExport.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listInvoiceExport.setComponentPopupMenu(operationForInvoice(listInvoiceExport,listInvoiceModelExport));
        listInvoiceExport.setModel(listInvoiceModelExport);
        listInvoiceExport.getTableHeader().setPreferredSize(new Dimension(0,40));
        listInvoiceExport.getTableHeader().setFont(new Font("nunito",Font.BOLD,17));
        listInvoiceExport.getColumnModel().getColumn(0).setPreferredWidth(10);
        listInvoiceExport.getColumnModel().getColumn(1).setPreferredWidth(100);
        listInvoiceExport.getColumnModel().getColumn(2).setPreferredWidth(100);
        listInvoiceExport.getColumnModel().getColumn(3).setPreferredWidth(100);
        listInvoiceExport.getColumnModel().getColumn(4).setPreferredWidth(100);
        listInvoiceExport.getColumnModel().getColumn(5).setPreferredWidth(100);
        listInvoiceExport.getColumnModel().getColumn(6).setPreferredWidth(100);
        listInvoiceExport.getColumnModel().getColumn(7).setPreferredWidth(100);
        listInvoiceExport.setRowHeight(30);



//        b----Hoa don nhap ---
        listInvoiceModelImport = new DefaultTableModel();
        String[] columnsInvoiceImport = {"ID","Ngày tạo","Nhân viên","Thanh toán","Trạng thái","Tổng tiền"};
        listInvoiceModelImport.setColumnIdentifiers(columnsInvoiceImport);
        listInvoiceImport = new JTable();
        listInvoiceImport.setRowSelectionAllowed(true);
        listInvoiceImport.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listInvoiceImport.setComponentPopupMenu(operationForInvoice(listInvoiceImport,listInvoiceModelImport));
        listInvoiceImport.setModel(listInvoiceModelImport);
        listInvoiceImport.getTableHeader().setPreferredSize(new Dimension(0,40));
        listInvoiceImport.getTableHeader().setFont(new Font("nunito",Font.BOLD,17));
        listInvoiceImport.setRowHeight(30);



        listInvoiceScrollPaneExport = new JScrollPane(listInvoiceExport);
        listInvoiceScrollPaneImport = new JScrollPane(listInvoiceImport);


        titleContainShowListInvoice = new JLabel("Danh sách hóa đơn bán",JLabel.CENTER);
        titleContainShowListInvoice.setFont(new Font("nunito",Font.BOLD,25));
        titleContainShowListInvoice.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0,0,0,1)),
                BorderFactory.createEmptyBorder(10,0,0,0)
        ));

        lbtotalMoneyAllInvoice = new JLabel("Tổng tiền: 0.0 VNĐ");
        lbtotalMoneyAllInvoice.setFont(new Font("nunito",Font.BOLD,20));
        JPanel bodyFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT,60,0));
        bodyFooter.add(lbtotalMoneyAllInvoice);
        bodyFooter.setPreferredSize(new Dimension(945,40));

        containShowListInvoice = new JPanel(new BorderLayout(30,20));
        containShowListInvoice.setPreferredSize(new Dimension(945,495));
        containShowListInvoice.add(titleContainShowListInvoice,BorderLayout.PAGE_START);
        containShowListInvoice.add(listInvoiceScrollPaneExport,BorderLayout.CENTER);
        containShowListInvoice.add(bodyFooter,BorderLayout.PAGE_END);
//        2-------Body show list invoice-end-----


        JPanel managerInvoiceBody = new JPanel(new BorderLayout());
        managerInvoiceBody.add(managerInvoiceFilter,BorderLayout.LINE_START);
        managerInvoiceBody.add(containShowListInvoice,BorderLayout.CENTER);


//        MANAGER BODY END

        JPanel managerInvoiceContentPane = new JPanel();
        managerInvoiceContentPane.setLayout(new BorderLayout());
        managerInvoiceContentPane.setBorder(BorderFactory.createMatteBorder(0,2,2,2,new Color(42,121,255)));
        managerInvoiceContentPane.add(managerInvoiceHeader,BorderLayout.PAGE_START);
        managerInvoiceContentPane.add(managerInvoiceBody,BorderLayout.CENTER);
        this.add(managerInvoiceContentPane,BorderLayout.CENTER);
        this.setVisible(true);
    }



    //phan xu ly su kien
    public void event(){
        actionShowInvoiceImport.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                actionShowInvoiceImport.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE),
                        BorderFactory.createEmptyBorder(0,0,5,0)
                ));
                actionShowInvoiceExport.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0,0,0,1)),
                        BorderFactory.createEmptyBorder(0,0,0,0)
                ));

                titleContainShowListInvoice.setText("Danh sách hóa đơn nhập");
                computersToFilter.setSelectedItem(null);
                titleComputerFilter.setEnabled(false);
                computersToFilter.setEnabled(false);
                titleContainAccountFilter.setEnabled(false);
                accountToFilter.setSelectedItem(null);
                accountToFilter.setEnabled(false);
                employeeToFilter.setSelectedItem(null);

                containShowListInvoice.remove(listInvoiceScrollPaneExport);
                containShowListInvoice.add(listInvoiceScrollPaneImport);

                List<Invoice> invoices = invoiceBUS.findAllByType(Invoice.InvoiceType.IMPORT);
                loadJTable(Invoice.InvoiceType.IMPORT,invoices);
            }
        });



        actionShowInvoiceExport.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                actionShowInvoiceExport.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE),
                        BorderFactory.createEmptyBorder(0,0,5,0)
                ));
                actionShowInvoiceImport.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0,0,0,1)),
                        BorderFactory.createEmptyBorder(0,0,0,0)
                ));

                titleContainShowListInvoice.setText("Danh sách hóa đơn bán");
                computersToFilter.setEnabled(true);
                titleComputerFilter.setEnabled(true);
                titleContainAccountFilter.setEnabled(true);
                accountToFilter.setEnabled(true);
                computersToFilter.setSelectedItem(null);
                accountToFilter.setSelectedItem(null);
                employeeToFilter.setSelectedItem(null);

                containShowListInvoice.remove(listInvoiceScrollPaneImport);
                containShowListInvoice.add(listInvoiceScrollPaneExport);
                List<Invoice> invoices = invoiceBUS.findAllByType(Invoice.InvoiceType.EXPORT);
                loadJTable(Invoice.InvoiceType.EXPORT,invoices);
            }
        });





        btnCreateInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateInvoiceGUI createInvoice = new CreateInvoiceGUI();
                String[] options = {"Hóa đơn bán","Hóa đơn nhập"};
                int userOption = JOptionPane.showOptionDialog(null,"Hóa đơn muốn tạo ?","Options create invoice ",JOptionPane.UNDEFINED_CONDITION,JOptionPane.QUESTION_MESSAGE,null,options,null);
                if(userOption == 0){
                    createInvoice.showDiaLog(Invoice.InvoiceType.EXPORT);
                    createInvoice.setOnsave((type,invoices)->{
                        loadJTable(type,invoices);
                    });
                }
                else if(userOption == 1){
                    createInvoice.showDiaLog(Invoice.InvoiceType.IMPORT);
                    createInvoice.setOnsave((type,invoices)->{
                        loadJTable(type,invoices);
                    });
                }
                else {
                    createInvoice.setVisible(false);
                    createInvoice.jDialog.dispose();
                }
            }
        });



        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateChooserFrom.setDate(new Date());
                dateChooserTo.setDate(new Date());
                limitTotalFrom.setText("");
                limitTotalTo.setText("");
                computersToFilter.setSelectedItem(null);
                employeeToFilter.setSelectedItem(null);
                accountToFilter.setSelectedItem(null);

                Invoice.InvoiceType type = getTypeInvoice();
                List<Invoice> invoices = invoiceBUS.findAllByType(type);
                if(getTypeInvoice() == Invoice.InvoiceType.EXPORT)
                    loadJTable(Invoice.InvoiceType.EXPORT,invoices);
                else
                    loadJTable(Invoice.InvoiceType.IMPORT,invoices);
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField getDateFrom = (JTextField) dateChooserFrom.getDateEditor().getUiComponent();
                JTextField getDateTo = (JTextField) dateChooserTo.getDateEditor().getUiComponent();
                int countLossInforFilter = 0;
                if (getDateFrom.getText().equals("dd-mm-yyyy") || getDateFrom.getText().equals("")) {
                    countLossInforFilter += 1;
                }
                if (getDateTo.getText().equals("dd-mm-yyyy") || getDateTo.getText().equals("")) {
                    countLossInforFilter += 1;
                }

                if (countLossInforFilter != 0) {
                    JOptionPane.showMessageDialog(null, "Bạn phải nhập đủ thông tin tìm kiếm", "message", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    Invoice.InvoiceType type = getTypeInvoice();
                    InforFilter inforFilter = getInforFilter(type);//lay thong tin can search
                    List<Invoice> listInvoiceSearch;
                    if (invoiceBUS.ValidateInforFilter(inforFilter)) {

                        listInvoiceSearch = invoiceBUS.findInvoiceByInforFilter(type, inforFilter);
                        loadJTable(type, listInvoiceSearch);
                        if (listInvoiceSearch.size() == 0) {
                            JOptionPane.showMessageDialog(null, "Không có hóa đơn nào");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Thông tin lọc hóa đơn chưa chính xác", "message", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

    }


    public InforFilter getInforFilter(Invoice.InvoiceType type){
        int accountID,computerID,employeeID;
        if(type == Invoice.InvoiceType.EXPORT){
            if(accountToFilter.getSelectedItem() == null)
                accountID = 0;
            else
                accountID = listAccountComboboxItem.get(accountToFilter.getSelectedIndex()).getId();
            if (computersToFilter.getSelectedItem() == null)
                computerID = 0;
            else
                computerID = listComputerComboboxItem.get(computersToFilter.getSelectedIndex()).getId();
        }
        else {
            accountID = computerID = 0;
        }
        if(employeeToFilter.getSelectedItem() == null)
            employeeID = 0;
        else
            employeeID = listEmployeeComboboxItem.get(employeeToFilter.getSelectedIndex()).getId();
        String strdateFrom = ((JTextField)dateChooserFrom.getDateEditor().getUiComponent()).getText();
        String strdateTo = ((JTextField)dateChooserTo.getDateEditor().getUiComponent()).getText();
        return new InforFilter(strdateFrom,strdateTo,limitTotalFrom.getText(),limitTotalTo.getText(),computerID,employeeID,accountID);
    }


    public void loadJTable(Invoice.InvoiceType type,List<Invoice> invoices){
        double totalMoneyAllInvoice = 0.0;
        if(type == Invoice.InvoiceType.EXPORT){
            listInvoiceModelExport.setRowCount(0);
            for (Invoice value : invoices) {
                totalMoneyAllInvoice += value.getTotal();
                String userNameAccount = null, computerName = null;
                String nameEmployee = employeeService.findEmployeeById(value.getCreatedBy()).getName();
                try {
                    if (value.getCreatedToAccountId() == null) {
                        userNameAccount = "Khách vãng lai";
                    } else {
                        userNameAccount = accountBUS.findById(value.getCreatedToAccountId()).getUsername();
                    }
                    var computer = computerBUS.getComputerById(value.getComputerId());
                            computerName =computer ==null?"Ddax xoas":computer.getName();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                listInvoiceModelExport.addRow(new Object[]{value.getId(), userNameAccount, computerName, value.getCreatedAt(), nameEmployee, value.explainIsPaid(), value.getStatus(), value.getTotal()});
            }

        }
        else {
            listInvoiceModelImport.setRowCount(0);
            for (Invoice value : invoices) {
                totalMoneyAllInvoice += value.getTotal();
                String nameEmployee = employeeService.findEmployeeById(value.getCreatedBy()).getName();
                listInvoiceModelImport.addRow(new Object[]{value.getId(), value.getCreatedAt(), nameEmployee, value.explainIsPaid(), value.getStatus(), value.getTotal()});
            }
        }
        lbtotalMoneyAllInvoice.setText("Tổng tiền: " + Helper.formatMoney(totalMoneyAllInvoice));

    }



    public Invoice.InvoiceType getTypeInvoice(){
        if(titleContainShowListInvoice.getText().equalsIgnoreCase("danh sách hóa đơn bán")){
            return Invoice.InvoiceType.EXPORT;
        }
        return Invoice.InvoiceType.IMPORT;
    }



    public JPopupMenu operationForInvoice(JTable jtabel,DefaultTableModel model) {
        List<Invoice> invoices = invoiceBUS.findAll();
        JMenuItem delete = new JMenuItem("Xoá");
        delete.setForeground(Color.RED);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jtabel.getSelectedRowCount() == 1) {
                    int confirmDeleteInvoice = JOptionPane.showConfirmDialog(null, "Bạn muốn xóa hóa đơn này ?");
                    if (confirmDeleteInvoice == JOptionPane.NO_OPTION) {
                        return;
                    } else if (confirmDeleteInvoice == JOptionPane.YES_OPTION) {
                        int indexRowSelected = jtabel.getSelectedRow();
                        int idInvoiceSelected = (int) jtabel.getValueAt(indexRowSelected, 0);
                        invoiceBUS.deleteInvoice(idInvoiceSelected);
                        model.removeRow(indexRowSelected);
                        JOptionPane.showMessageDialog(null, "Xóa thành công", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Không thể xóa nhiều hóa đơn cùng lúc", "Anounce", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JMenuItem edit = new JMenuItem("Sửa");
        edit.setFont(new Font("nunito", Font.BOLD, 16));
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateInvoiceGUI createInvoiceGUI = new CreateInvoiceGUI();
                loadInvoice(createInvoiceGUI);
                createInvoiceGUI.btnAddInvoice.setVisible(false);
                createInvoiceGUI.btnSaveInvoice.setVisible(true);
            }
        });


        JMenuItem showDetailInvoice = new JMenuItem("Xem chi tiết");
        showDetailInvoice.setFont(new Font("nunito", Font.BOLD, 16));
        delete.setFont(new Font("nunito", Font.BOLD, 16));

        showDetailInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateInvoiceGUI createInvoiceGUI = new CreateInvoiceGUI();
                loadInvoice(createInvoiceGUI);
                createInvoiceGUI.btnSaveInvoice.setVisible(false);
                createInvoiceGUI.btnAddInvoice.setVisible(false);
            }
        });

        JPopupMenu listOperation = new JPopupMenu();
        listOperation.add(delete);
        listOperation.add(edit);
        listOperation.add(showDetailInvoice);
        return  listOperation;
    }


    public void loadInvoice(CreateInvoiceGUI createInvoiceGUI){
//        CreateInvoiceGUI createInvoiceGUI = new CreateInvoiceGUI();
        Invoice.InvoiceType type = getTypeInvoice();
        JTable jTable;
        createInvoiceGUI.showDiaLog(type);
        //thay doi tieu de "Tao hoa don ban" thanh "Chi tiet hoa don ban"
        if(type == Invoice.InvoiceType.EXPORT){
            createInvoiceGUI.titleCreateInvoice.setText("Chi tiết hóa đơn bán");
            jTable = listInvoiceExport;
        }
        else{
            createInvoiceGUI.titleCreateInvoice.setText("Chi tiết hóa đơn nhập");
            jTable = listInvoiceImport;
        }
        //lay thong tin invoice
        //goi toi table luu du thong tin hoa don, lay id cua hoa don, goi toi ham tim hoa don bang id,
        int rowIsSelected = jTable.getSelectedRow();//lay hang duoc select
        int idInvoiceIsSelected = (int)jTable.getModel().getValueAt(rowIsSelected,0);//lay id cua hoa don
        Invoice invoiceIsSelect = invoiceBUS.findInvoiceById(idInvoiceIsSelected);//lay hoa don tu csdl len

        //lay detailInvoice cua invoice do
        InvoiceDetailBUS invoiceDetailBUS = new InvoiceDetailBUS();
        List<InvoiceDetail> listInvoiceDetail = invoiceDetailBUS.findByInvoiceId(idInvoiceIsSelected);
        if(type == Invoice.InvoiceType.EXPORT){
            for (InvoiceDetail invoiceDetail : listInvoiceDetail) {
                Product product = productBUS.findProductById(invoiceDetail.getProductId());
                createInvoiceGUI.listProductInvoiceModel.addRow(new Object[]{invoiceDetail.getProductId(), product.getName(), invoiceDetail.getQuantity(), invoiceDetail.getPrice(), invoiceDetail.getPrice() * invoiceDetail.getQuantity()});
            }

            createInvoiceGUI.listComputerID.setSelectedIndex(invoiceIsSelect.getComputerId()-1);
            if(invoiceIsSelect.getCreatedToAccountId() == null){
                createInvoiceGUI.listAccountID.setSelectedItem(0);//neu ma id cua tai khoan la null thi set mac dinh do la khach hang van lai
            }
            else{
                createInvoiceGUI.listAccountID.setSelectedIndex(invoiceIsSelect.getCreatedToAccountId());
            }
        }
        else{
            for (InvoiceDetail invoiceDetail : listInvoiceDetail) {
                Product product = productBUS.findProductById(invoiceDetail.getProductId());
                createInvoiceGUI.listProductInvoiceModel.addRow(new Object[]{product.getId(), product.getName(), invoiceDetail.getQuantity(), invoiceDetail.getPrice(), invoiceDetail.getPrice() * invoiceDetail.getQuantity()});
                createInvoiceGUI.lbTotalInvoice.setText(String.valueOf(createInvoiceGUI.caculateTotalMoney()) + "VNĐ");
            }
            createInvoiceGUI.listComputerID.setSelectedItem(null);
            createInvoiceGUI.listAccountID.setSelectedItem(null);
        }
        createInvoiceGUI.lbTotalInvoice.setText(String.valueOf(createInvoiceGUI.caculateTotalMoney()) + "VNĐ");

        //do du lieu len
        createInvoiceGUI.dateCreate.setDate(invoiceIsSelect.getCreatedAt());
        createInvoiceGUI.listEmployeeID.setSelectedIndex(invoiceIsSelect.getCreatedBy()-1);
        //neu isPaid la true tuc la hoa don da duoc thanh toan roi
        createInvoiceGUI.isPaid.setSelected(invoiceIsSelect.isPaid());
        createInvoiceGUI.status.setSelectedItem(invoiceIsSelect.getStatus());
        createInvoiceGUI.idInvoiceSelected.setText(String.valueOf(idInvoiceIsSelected));//lay id cua hoa don muon edit de luu vao lable
        createInvoiceGUI.setOnsave((a,b)->{
            loadJTable(a,b);
        });
    }

    public static void main(String[] args){
        ServiceProvider.init();
        InvoiceManageGUI quanlyhoadon = new InvoiceManageGUI();
        JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());
        frame.add(quanlyhoadon,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
