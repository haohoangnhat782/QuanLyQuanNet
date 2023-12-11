package Utils;

import GUI.Server.Account.AccountGUI;
import GUI.Server.Computer.ComputerManageGUI;
import GUI.Server.ComputerUsage.ComputerUsageGUI;
import GUI.Server.Employee.ManagerEmployee;
import GUI.Server.EmployeeSalary.EmployeeSalary;
import GUI.Server.Home.Home;
import GUI.Server.Invoice.CreateInvoiceGUI;
import GUI.Server.Invoice.InvoiceManageGUI;
import GUI.Server.Personal.PersonalSetting;
import GUI.Server.Product.ProductGUI;
import lombok.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class Constants {
    @Getter
    @Setter
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static
    class Tab{
        private String key;
        private String title;
        private ImageIcon icon = null;
        private JPanel contentPanel;
        private List<Tab> children =null;

    }

    private static List<Tab> tabs = null;
    private static JPanel createPanelWithText(String text) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.BLUE);

        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
    public static List<Tab> getTabs() {
        if (tabs!=null) return tabs;

        tabs = new ArrayList<>();
        tabs= new ArrayList<>();
        tabs.add(
               Tab.builder().title("Trang chủ").key("home").contentPanel(new Home()).build()
        );
        tabs.add(
               Tab.builder().title("Quản lý").key("manage").children(new ArrayList<>(
                        List.of(
                               Tab.builder().title("Quản lý tài khoản").key("manage-account").contentPanel(new AccountGUI()).build(),
                               Tab.builder().title("Quản lý sản phẩm").key("manage-product").contentPanel(new ProductGUI()).build(),
                               Tab.builder().title("Quản lý nhân viên").key("manage-employee").contentPanel(new ManagerEmployee()).build(),
                                Tab.builder().title("Quản lý hoá đơn").key("manage-invoice").contentPanel(new InvoiceManageGUI()).build(),
                                Tab.builder().title("Quản lý máy").key("manage-computer").contentPanel(new ComputerManageGUI()).build()
                        )
                )).build()
        );
        tabs.add(Tab.builder().title("Thống kê").key("thongke")
                        .children(new ArrayList<>(
                                List.of(
                                        Tab.builder().title("Doanh thu từ máy").key("thongke-account").contentPanel(new ComputerUsageGUI()).build(),
                                        Tab.builder().title("Thống kê lương nhân viên").key("thongke-emloyee-salary").contentPanel(new EmployeeSalary()).build()
                                )
                        ))
                .build());
        tabs.add(Tab.builder().title("Cá nhân").key("canhan").contentPanel(new PersonalSetting()).build());
        return tabs;

    }
    public static final int SOCKET_PORT = 1234;

}
