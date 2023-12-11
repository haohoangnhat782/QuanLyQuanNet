package GUI.Server.Employee;

import BUS.*;
import DTO.Account;
import DTO.Employee;
import GUI.Server.MainUI;

import BUS.EmployeeBUS;
import Utils.ServiceProvider;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class ManagerEmployee extends JPanel {
    private List<Employee> list;
    private EmployeeBUS employeeService;
    private AccountBUS accountBUS;

    private Employee employee = Employee.builder().id(0).name("").accountID(null).salaryPerHour(0).phoneNumber("").address("").createdAt(new Date()).deletedAt(null).build();
    private JPanel managerEmployeeContentPane;
    private JPanel containTitleManagerEmployee;
    private JLabel titleManagerEmployee;
    private JLabel titleContainShowListEmployee;
    private JButton btnSearch;
    private JPanel containShowListEmployee;
    private JLabel idNV;
    private JComboBox<TaiKhoanComboBoxItem> inputIdNV;

    public record TaiKhoanComboBoxItem(Integer id, String username) {
        @Override
        public String toString() {
            return username;
        }

    }

    private JPanel panelBody;
    private GridBagLayout layoutBody;
    private GridBagConstraints gbcBody;
    private JPanel containIdNV;
    private JLabel nameNV;
    private JTextField inputNameNV;
    private JPanel containNameNV;
    private JLabel luongNV;
    private JTextField inputLuongNV;
    private JPanel containLuongNV;
    private JLabel sdtNV;
    private JTextField inputSdtNV;
    private JPanel containSdtNV;
    private JLabel diachiNV;
    private JTextField inputDiachiNV;
    private JPanel containDiachiNV;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private DefaultTableModel listEmployeeModel;
    private JTable listEmployee;
    private JScrollPane listEmployeeScrollPane;

    public ManagerEmployee() {
        this.employeeService = ServiceProvider.getInstance().getService(EmployeeBUS.class);
        this.accountBUS = ServiceProvider.getInstance().getService(AccountBUS.class);

        this.setLayout(new BorderLayout());
        try {
            this.initManagerEmployee();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void sizeInComputer(JPanel jpanel) {
        jpanel.setPreferredSize(new Dimension(1200, 650));
    }

    public void setMarginJLabel(int top, int left, int buttom, int right, JLabel jlabel) {
        jlabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 1)),
                BorderFactory.createEmptyBorder(top, left, buttom, right)
        ));
    }

    public void setPaddingJButton(int top, int left, int buttom, int right, JButton jbutton) {
        jbutton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 1)),
                BorderFactory.createEmptyBorder(top, left, buttom, right)
        ));
    }

    public void setPlaceHoder(String textPlaceHoder, JTextField jtextField) {
        jtextField.setText(textPlaceHoder);
        jtextField.setFont(new Font("nunito", Font.PLAIN, 16));
    }

    public void initManagerEmployee() throws SQLException {
        //header-title
        titleManagerEmployee = new JLabel("Quản lý nhân viên");
        titleManagerEmployee.setFont(new Font("nunito", Font.BOLD, 30));
        titleManagerEmployee.setForeground(Color.WHITE);
        setMarginJLabel(0, 20, 0, 0, titleManagerEmployee);
        containTitleManagerEmployee = new JPanel();
        containTitleManagerEmployee.setLayout(new FlowLayout(FlowLayout.LEFT));
        containTitleManagerEmployee.setPreferredSize(new Dimension(1198, 100));
        containTitleManagerEmployee.setBackground(new Color(42, 121, 255));
        containTitleManagerEmployee.add(titleManagerEmployee);
        list = new ArrayList<>();
        list = employeeService.findAllEmployee();

        //body
        panelBody = new JPanel();
        layoutBody = new GridBagLayout();
        panelBody.setLayout(layoutBody);
        gbcBody = new GridBagConstraints();

        gbcBody.fill = GridBagConstraints.HORIZONTAL;
        gbcBody.gridx = 0;
        gbcBody.gridy = 0;
        idNV = new JLabel("Id tài khoản", JLabel.LEFT);
        // Input idNV = new Input("");
        inputIdNV = new JComboBox<>();
        var accounts = accountBUS.getAllAccounts().stream().filter(a -> a.getRole().isGreaterThan(Account.Role.USER));
        var model = new DefaultComboBoxModel<TaiKhoanComboBoxItem>();
        model.addElement(new TaiKhoanComboBoxItem(null, "Không chọn"));
        accounts.forEach(a -> model.addElement(new TaiKhoanComboBoxItem(a.getId(), a.getUsername())));
        inputIdNV.setModel(model);
        inputIdNV.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                var item = (TaiKhoanComboBoxItem) e.getItem();
                var selectedRow = listEmployee.getSelectedRow();
                int employeeId;
                if (selectedRow != -1) {
                    employeeId = (int) listEmployeeModel.getValueAt(selectedRow, 0);
                } else {
                    employeeId = -1;
                }
                var exist = list.stream().anyMatch(l ->l.getAccountID()!=null&& l.getAccountID().equals(item.id()) && l.getId() != employeeId);
                if (exist) {
                    JOptionPane.showMessageDialog(this, "Tài khoản đã được sử dụng bởi nhân viên khác");
                    inputIdNV.setSelectedIndex(0);
                } else {
                    employee.setAccountID(item.id());
                }
            }
        });
//        inputIdNV.setPreferredSize(new Dimension(0,25));
        containIdNV = new JPanel(new FlowLayout());
        containIdNV.add(idNV);
        containIdNV.add(new JLabel("\n"));
        containIdNV.add(inputIdNV);
        panelBody.add(containIdNV, gbcBody);

        gbcBody.gridx = 1;
        gbcBody.gridy = 0;
        nameNV = new JLabel("Tên nhân viên", JLabel.LEFT);
        inputNameNV = new JTextField("", 15);
        inputNameNV.setPreferredSize(new Dimension(0, 25));
        containNameNV = new JPanel(new FlowLayout());
        containNameNV.add(nameNV);
        containNameNV.add(new JLabel("\n"));
        containNameNV.add(inputNameNV);
        panelBody.add(containNameNV, gbcBody);

        gbcBody.fill = GridBagConstraints.HORIZONTAL;
        gbcBody.gridx = 0;
        gbcBody.gridy = 1;
        luongNV = new JLabel("Lương (VND/h)", JLabel.LEFT);
        inputLuongNV = new JTextField("", 14);
        inputLuongNV.setPreferredSize(new Dimension(0, 25));
        containLuongNV = new JPanel(new FlowLayout());
        containLuongNV.add(luongNV);
        containLuongNV.add(new JLabel("\n"));
        containLuongNV.add(inputLuongNV);
        panelBody.add(containLuongNV, gbcBody);

        gbcBody.gridx = 1;
        gbcBody.gridy = 1;
        sdtNV = new JLabel("Số điện thoại", JLabel.LEFT);
        inputSdtNV = new JTextField("", 15);
        inputSdtNV.setPreferredSize(new Dimension(0, 25));
        containSdtNV = new JPanel(new FlowLayout());
        containSdtNV.add(sdtNV);
        containSdtNV.add(new JLabel("\n"));
        containSdtNV.add(inputSdtNV);
        panelBody.add(containSdtNV, gbcBody);

        gbcBody.gridx = 0;
        gbcBody.gridy = 2;
        gbcBody.fill = GridBagConstraints.HORIZONTAL;
        gbcBody.gridwidth = 2;
        diachiNV = new JLabel("Địa chỉ", JLabel.LEFT);
        inputDiachiNV = new JTextField("", 43);
        inputDiachiNV.setPreferredSize(new Dimension(0, 25));
        containDiachiNV = new JPanel(new FlowLayout());
        containDiachiNV.add(diachiNV);
        containDiachiNV.add(new JLabel("\n"));
        containDiachiNV.add(inputDiachiNV);
        panelBody.add(containDiachiNV, gbcBody);

        gbcBody.gridx = 0;
        gbcBody.gridy = 3;
        gbcBody.fill = GridBagConstraints.HORIZONTAL;
        gbcBody.gridwidth = 2;
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Refresh");
        menuItem.setFont(new Font("nunito", Font.BOLD, 16));
        menuItem.addActionListener(e->{
            list = employeeService.findAllEmployee();
           showTable();
        });
        popupMenu.add(menuItem);


        //tim kiem,them sua xoa
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setPreferredSize(new Dimension(100, 35));
        btnSearch.setFocusPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.setBackground(new Color(42, 121, 255));
        btnSearch.setForeground(Color.WHITE);
        setPaddingJButton(0, 0, 0, 0, btnSearch);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnAdd = new JButton("Thêm");
        btnAdd.setPreferredSize(new Dimension(100, 35));
        btnAdd.setFocusPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.setBackground(new Color(24, 169, 90));
        btnAdd.setForeground(Color.WHITE);
        setPaddingJButton(0, 0, 0, 0, btnAdd);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });


        btnEdit = new JButton("Sửa");
        btnEdit.setPreferredSize(new Dimension(100, 35));
        btnEdit.setFocusPainted(false);
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.setBackground(new Color(37, 54, 224));
        btnEdit.setForeground(Color.WHITE);
        setPaddingJButton(0, 0, 0, 0, btnEdit);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete = new JButton("Xóa");
        btnDelete.setPreferredSize(new Dimension(100, 35));
        btnDelete.setFocusPainted(false);
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.setBackground(new Color(231, 62, 62));
        btnDelete.setForeground(Color.WHITE);
        setPaddingJButton(0, 0, 0, 0, btnDelete);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        JPanel containActionInFilter = new JPanel(new FlowLayout());
        containActionInFilter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 1)),
                BorderFactory.createEmptyBorder(30, 0, 0, 0)
        ));
        containActionInFilter.add(btnSearch);
        containActionInFilter.add(new JLabel("                 "));
        containActionInFilter.add(btnAdd);
        containActionInFilter.add(new JLabel("  "));
        containActionInFilter.add(btnEdit);
        containActionInFilter.add(new JLabel("  "));
        containActionInFilter.add(btnDelete);

        JPanel managerEmployeeFilter = new JPanel(new BorderLayout());
        managerEmployeeFilter.setPreferredSize(new Dimension(1000, 500));
        managerEmployeeFilter.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(42, 121, 255)));
        managerEmployeeFilter.add(panelBody, BorderLayout.NORTH);
        managerEmployeeFilter.add(containActionInFilter, BorderLayout.CENTER);

        //List employee
        listEmployeeModel = new DefaultTableModel();
        listEmployeeModel.addColumn("ID");
        listEmployeeModel.addColumn("HỌ VÀ TÊN");
        listEmployeeModel.addColumn("ID TÀI KHOẢN");
        listEmployeeModel.addColumn("LƯƠNG (VND/H)");
        listEmployeeModel.addColumn("SỐ ĐIỆN THOẠI");
        listEmployeeModel.addColumn("ĐỊA CHỈ");
        showTable();

        listEmployee = new JTable();
        listEmployee.setModel(listEmployeeModel);
        listEmployee.getTableHeader().setPreferredSize(new Dimension(0, 40));
        listEmployee.getTableHeader().setFont(new Font("nunito", Font.BOLD, 17));
        listEmployee.getColumnModel().getColumn(0).setPreferredWidth(30);
        listEmployee.getColumnModel().getColumn(1).setPreferredWidth(100);
        listEmployee.getColumnModel().getColumn(2).setPreferredWidth(100);
        listEmployee.getColumnModel().getColumn(3).setPreferredWidth(100);
        listEmployee.getColumnModel().getColumn(4).setPreferredWidth(100);
        listEmployee.getColumnModel().getColumn(5).setPreferredWidth(200);
        listEmployee.setRowHeight(30);
        listEmployee.setBackground(new Color(153, 153, 153));
        listEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                super.mouseClicked(evt);
                onSelectedRowChange();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                onSelectedRowChange();
            }
        });

        listEmployeeScrollPane = new JScrollPane(listEmployee);
        titleContainShowListEmployee = new JLabel("Danh sách nhân viên", JLabel.CENTER);
        titleContainShowListEmployee.setFont(new Font("nunito", Font.BOLD, 25));
        titleContainShowListEmployee.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 1)),
                BorderFactory.createEmptyBorder(10, 0, 0, 0)
        ));

        containShowListEmployee = new JPanel(new BorderLayout(30, 20));
        containShowListEmployee.setPreferredSize(new Dimension(945, 400));
        containShowListEmployee.add(titleContainShowListEmployee, BorderLayout.PAGE_START);
        containShowListEmployee.add(listEmployeeScrollPane, BorderLayout.CENTER);
        managerEmployeeFilter.add(containShowListEmployee, BorderLayout.SOUTH);

        //body end
        managerEmployeeContentPane = new JPanel();
        managerEmployeeContentPane.setLayout(new BorderLayout());
        managerEmployeeContentPane.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 2, new Color(42, 121, 255)));
        managerEmployeeContentPane.add(containTitleManagerEmployee, BorderLayout.PAGE_START);
        managerEmployeeContentPane.add(managerEmployeeFilter, BorderLayout.CENTER);
        this.add(managerEmployeeContentPane, BorderLayout.CENTER);
        this.setVisible(true);
        listEmployee.setComponentPopupMenu(popupMenu);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag){
            if (MainUI.getCurrentUser().getAccount().getRole().isLessThan(Account.Role.MANAGER)){
                MainUI.getInstance().getSideBar().navigateTo("home");
                JOptionPane.showMessageDialog(MainUI.getInstance(), "Bạn không có quyền truy cập vào mục này");
                return;
            }
        }
        super.setVisible(aFlag);
        if(!aFlag) {
          return;
        }
        var model = new DefaultComboBoxModel<TaiKhoanComboBoxItem>();


        model.removeAllElements();
        model.addElement(new TaiKhoanComboBoxItem(null, "Không chọn"));
        List<Account> accounts = null;
        try {
            accounts = accountBUS.getAllAccounts().stream().filter(a -> a.getRole().isGreaterThan(Account.Role.USER)).toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        accounts.forEach(a -> model.addElement(new TaiKhoanComboBoxItem(a.getId(), a.getUsername())));
        inputIdNV.setModel(model);
    }
    public void showTable(List<Employee> list) {
        var model = (DefaultTableModel) this.listEmployeeModel;
        model.setRowCount(0);
        for (Employee e : list) {
            model.addRow(new Object[]{
                    e.getId(), e.getName(), e.getAccountID(), e.getSalaryPerHour(), e.getPhoneNumber(), e.getAddress(),
            });
        }
    }
    public void showTable() {
        showTable(this.list);

    }

    private void btnSearchActionPerformed(ActionEvent evt) {
        var searchTxt =JOptionPane.showInputDialog(null, "Nhập tên nhân viên, mã, hoặc số điện thoại", "Tìm kiếm nhân viên", JOptionPane.INFORMATION_MESSAGE);
        if(searchTxt == null) {
            return;
        }
       var fileredList =this.list.stream().filter(e -> e.getName().toLowerCase().contains(searchTxt.toLowerCase()) || String.valueOf(e.getId()).contains(searchTxt) || e.getPhoneNumber().contains(searchTxt)).toList();
        showTable(fileredList);
    }

    private void clearInput() {
        inputNameNV.setText("");
        inputLuongNV.setText("");
        inputSdtNV.setText("");
        inputIdNV.setSelectedIndex(0);
        inputDiachiNV.setText("");
    }

    private boolean validateInput() {
        try {
            Integer.parseInt(inputLuongNV.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lương Nhân Viên Phải Là Số", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (inputNameNV.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Tên Nhân Viên Không Được Để Trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (inputLuongNV.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Lương Nhân Viên Không Được Để Trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (inputSdtNV.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Số Điện Thoại Không Được Để Trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (inputDiachiNV.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Địa Chỉ Không Được Để Trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        Pattern phonePattern = Pattern.compile("^[0-9]{10}$");
        if (!phonePattern.matcher(inputSdtNV.getText().trim()).matches()) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;

    }

    private void btnAddActionPerformed(ActionEvent evt) {

        if (!validateInput()) {
            return;
        }

        int luong = Integer.parseInt(inputLuongNV.getText());




        if (luong < 0) {
            JOptionPane.showMessageDialog(null, "Lương không thể là số âm", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        employee.setName(inputNameNV.getText());
        employee.setSalaryPerHour(Integer.parseInt(inputLuongNV.getText()));
        employee.setPhoneNumber(inputSdtNV.getText());
        employee.setAddress(inputDiachiNV.getText());
        employee.setCreatedAt(new Date());
        try {
            employeeService.createEmployee(employee);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        list.clear();
        list = employeeService.findAllEmployee();
        showTable();
        clearInput();
    }


    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
        if (!validateInput()) {
            return;
        }
        var selectedRow = listEmployee.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int luong = Integer.parseInt(inputLuongNV.getText());




        if (luong < 0) {
            JOptionPane.showMessageDialog(null, "Lương không thể là số âm", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        var employeeId = (int) listEmployee.getValueAt(selectedRow, 0);
        employee.setId(employeeId);
        employee.setName(inputNameNV.getText());
        employee.setSalaryPerHour(Integer.parseInt(inputLuongNV.getText()));
        employee.setPhoneNumber(inputSdtNV.getText());
        employee.setAddress(inputDiachiNV.getText());
        try {
            employeeService.updateEmployee(employee);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        JOptionPane.showMessageDialog(this, "Sửa nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        list.clear();
        list = employeeService.findAllEmployee();
        showTable();
        clearInput();
        listEmployee.clearSelection();

    }

    public void onSelectedRowChange() {
        var selectedRow = listEmployee.getSelectedRow();
        if (selectedRow == -1) {
            inputIdNV.setSelectedIndex(0);
            inputNameNV.setText("");
            inputLuongNV.setText("");
            inputSdtNV.setText("");
            inputDiachiNV.setText("");
            return;
        }
        var employeeId = (int) listEmployee.getValueAt(selectedRow, 0);
        var employee = employeeService.findEmployeeById(employeeId);
        inputNameNV.setText(employee.getName());
        inputLuongNV.setText(String.valueOf(employee.getSalaryPerHour()));
        inputSdtNV.setText(employee.getPhoneNumber());
        inputDiachiNV.setText(employee.getAddress());
        var model = (DefaultComboBoxModel<TaiKhoanComboBoxItem>) inputIdNV.getModel();
        for (int i = 1; i < model.getSize(); i++) {
            if (model.getElementAt(i).id.equals(employee.getAccountID())) {
                inputIdNV.setSelectedIndex(i);
                return;
            }
        }
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) listEmployee.getModel();
        int indexRowSelected = listEmployee.getSelectedRow();
        if(indexRowSelected==-1){
            JOptionPane.showMessageDialog(this, "Chọn nhân viên để xóa", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        int idEmployeeSelected = (int) listEmployee.getValueAt(indexRowSelected, 0);
        var result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.NO_OPTION||result == JOptionPane.CLOSED_OPTION) {
            return;
        }
        try {
            employeeService.delete(idEmployeeSelected);
            JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        model.removeRow(indexRowSelected);
    }

    public static void main(String[] args) {

    }
}