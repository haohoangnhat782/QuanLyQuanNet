/*
 * Created by JFormDesigner on Wed Mar 22 21:45:12 ICT 2023
 */

package GUI.Server.Account;

import javax.swing.border.*;

import GUI.Server.Main;
import GUI.Server.MainUI;
import Utils.Helper;
import lombok.Getter;
import DTO.Account;

import java.awt.*;
import java.util.Arrays;
import javax.swing.*;

/**
 * @author HuuHoang
 */
public class AccountDetailGUI extends JDialog {

    @Getter
    private Account account;
    private Mode mode;

    public enum Mode {
        EDIT, READ_ONLY, CREATE
    }

    public AccountDetailGUI(Window owner, Account account, Mode mode) {
        this.account = account;
        this.mode = mode;
        initComponents();
        reDesign();
        initEvent();
        setModal(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
    }

    private boolean getAccountFromInput() {
        if (textField2.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống");
            return false;
        }
        if (textField3.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống");
            return false;
        }
        if (!Helper.isNumber(textField4.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Số dư không hợp lệ");
            return false;
        }
        if (Integer.parseInt(textField4.getText())<1000) {
            JOptionPane.showMessageDialog(this, "Số dư không được nhỏ hơn 1000");
            return false;
        }


        this.account.setUsername(textField2.getText());
        this.account.setPassword(textField3.getText());
        this.account.setRole(((Account.Role) roleComboBox.getSelectedItem()).ordinal());
        this.account.setBalance(Double.parseDouble(textField4.getText()));
        return true;
    }

    @Getter
    private int status = JOptionPane.CANCEL_OPTION;

    private void initEvent() {

        cancel.addActionListener(e -> {
            this.status = JOptionPane.CANCEL_OPTION;

            dispose();
        });
        ok.addActionListener(e -> {
            this.status = JOptionPane.OK_OPTION;
            if ((mode == Mode.CREATE || mode == Mode.EDIT) && this.getAccountFromInput()) {
                dispose();
            }
        });

    }

    public AccountDetailGUI(Window owner) {
        this(owner, Account.builder().username("").password("").build(), Mode.CREATE);
    }

    public AccountDetailGUI(Window owner, Account account) {
        this(owner, account, Mode.EDIT);
    }

    private void reDesign() {

        var userRole = MainUI.getCurrentUser().getAccount().getRole();
        var lowerRole = Arrays.stream(Account.Role.values()).filter(r->r.isLessThan(userRole)).toArray();

        roleComboBox.setModel(new DefaultComboBoxModel<>(lowerRole));
        textField1.setEditable(false);
        switch (mode) {
            case CREATE -> {
                label1.setText("Tạo tài khoản");
                cancel.setText("Hủy");
                ok.setText("Tạo");
            }
            case EDIT -> {
                label1.setText("Chỉnh sửa tài khoản");
                cancel.setText("Hủy");
                ok.setText("Lưu");
                textField1.setText(account.getId() + "");
                textField2.setText(account.getUsername());
                textField3.setText(account.getPassword());
                textField4.setText(account.getBalance() + "");
                roleComboBox.setSelectedIndex(roleComboBox.getItemCount() - 1);

            }
            case READ_ONLY -> {
                label1.setText("Thông tin tài khoản");
                cancel.setText("Đóng");
                ok.setText("Ok");
                textField2.setEditable(false);
                textField3.setEditable(false);
                textField4.setEditable(false);
                roleComboBox.setEnabled(false);


            }
        }
        roleComboBox.addItemListener(e -> {
        });
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        label1 = new JLabel();
        panel7 = new JPanel();
        panel2 = new JPanel();
        label2 = new JLabel();
        textField1 = new JTextField();
        panel8 = new JPanel();
        label6 = new JLabel();
        roleComboBox = new JComboBox();
        panel3 = new JPanel();
        label3 = new JLabel();
        textField2 = new JTextField();
        panel4 = new JPanel();
        label4 = new JLabel();
        textField3 = new JPasswordField();
        panel5 = new JPanel();
        label5 = new JLabel();
        textField4 = new JTextField();
        panel6 = new JPanel();
        cancel = new JButton();
        ok = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(420, 450));
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout());

            //---- label1 ----
            label1.setText("Ch\u1ec9nh s\u1eeda t\u00e0i kho\u1ea3n");
            label1.setFont(new Font("Nunito Medium", Font.BOLD, 20));
            panel1.add(label1);
        }
        contentPane.add(panel1);

        //======== panel7 ========
        {
            panel7.setLayout(new GridLayout(1, 2, 5, 5));

            //======== panel2 ========
            {
                panel2.setBorder(new EmptyBorder(2, 10, 0, 10));
                panel2.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- label2 ----
                label2.setText("S\u1ed1 t\u00e0i kho\u1ea3n:");
                label2.setFont(new Font("Nunito Medium", Font.PLAIN, 14));
                panel2.add(label2);

                //---- textField1 ----
                textField1.setPreferredSize(new Dimension(160, 30));
                textField1.setFont(new Font("Nunito", Font.PLAIN, 14));
                panel2.add(textField1);
            }
            panel7.add(panel2);

            //======== panel8 ========
            {
                panel8.setBorder(new EmptyBorder(2, 10, 0, 10));
                panel8.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- label6 ----
                label6.setText("Ch\u1ee9c v\u1ee5:");
                label6.setFont(new Font("Nunito Medium", Font.PLAIN, 14));
                panel8.add(label6);

                //---- roleComboBox ----
                roleComboBox.setPreferredSize(new Dimension(156, 30));
                roleComboBox.setFont(new Font("Nunito", Font.PLAIN, 14));
                panel8.add(roleComboBox);
            }
            panel7.add(panel8);
        }
        contentPane.add(panel7);

        //======== panel3 ========
        {
            panel3.setBorder(new EmptyBorder(2, 10, 0, 10));
            panel3.setLayout(new FlowLayout(FlowLayout.LEFT));

            //---- label3 ----
            label3.setText("T\u00ean \u0111\u0103ng nh\u1eadp:");
            label3.setFont(new Font("Nunito Medium", Font.PLAIN, 14));
            panel3.add(label3);

            //---- textField2 ----
            textField2.setPreferredSize(new Dimension(370, 30));
            textField2.setFont(new Font("Nunito", Font.PLAIN, 14));
            panel3.add(textField2);
        }
        contentPane.add(panel3);

        //======== panel4 ========
        {
            panel4.setBorder(new EmptyBorder(2, 10, 0, 10));
            panel4.setLayout(new FlowLayout(FlowLayout.LEFT));

            //---- label4 ----
            label4.setText("M\u1eadt kh\u1ea9u: ");
            label4.setFont(new Font("Nunito Medium", Font.PLAIN, 14));
            panel4.add(label4);

            //---- textField3 ----
            textField3.setPreferredSize(new Dimension(370, 30));
            textField3.setFont(new Font("Nunito", Font.PLAIN, 14));
            panel4.add(textField3);
        }
        contentPane.add(panel4);

        //======== panel5 ========
        {
            panel5.setBorder(new EmptyBorder(2, 10, 0, 10));
            panel5.setLayout(new FlowLayout(FlowLayout.LEFT));

            //---- label5 ----
            label5.setText("S\u1ed1 d\u01b0:");
            label5.setFont(new Font("Nunito Medium", Font.PLAIN, 14));
            panel5.add(label5);

            //---- textField4 ----
            textField4.setPreferredSize(new Dimension(370, 30));
            textField4.setFont(new Font("Nunito", Font.PLAIN, 14));
            panel5.add(textField4);
        }
        contentPane.add(panel5);

        //======== panel6 ========
        {
            panel6.setBorder(new EmptyBorder(20, 10, 0, 10));
            panel6.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 5));

            //---- cancel ----
            cancel.setText("text");
            cancel.setFont(new Font("Nunito Medium", Font.PLAIN, 14));
            cancel.setMaximumSize(new Dimension(90, 35));
            cancel.setMinimumSize(new Dimension(90, 35));
            cancel.setPreferredSize(new Dimension(90, 40));
            cancel.setBackground(new Color(0xf7fafc));
            panel6.add(cancel);

            //---- ok ----
            ok.setText("text");
            ok.setFont(new Font("Nunito Medium", Font.PLAIN, 14));
            ok.setMaximumSize(new Dimension(90, 35));
            ok.setMinimumSize(new Dimension(90, 35));
            ok.setPreferredSize(new Dimension(90, 40));
            ok.setBackground(new Color(0x63b3ed));
            panel6.add(ok);
        }
        contentPane.add(panel6);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JLabel label1;
    private JPanel panel7;
    private JPanel panel2;
    private JLabel label2;
    private JTextField textField1;
    private JPanel panel8;
    private JLabel label6;
    private JComboBox roleComboBox;
    private JPanel panel3;
    private JLabel label3;
    private JTextField textField2;
    private JPanel panel4;
    private JLabel label4;
    private JTextField textField3;
    private JPanel panel5;
    private JLabel label5;
    private JTextField textField4;
    private JPanel panel6;
    private JButton cancel;
    private JButton ok;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
