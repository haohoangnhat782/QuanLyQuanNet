package GUI.Client;

import GUI.Components.ImagePanel;
import GUI.Components.Input;
import Payload.LoginPayload;
import Utils.Fonts;
import Utils.Helper;
import DTO.Session;
import BUS.AccountBUS;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginGUI extends JFrame {
    private AccountBUS accountBUS;
    public LoginGUI() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


        // set vị trí cho khung đăng nhập
        // start
        int width = 400;
        int height = 300;
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;

        this.setSize(screenSize.width, screenSize.height);

        ImagePanel backgroundPanel = new ImagePanel();
        //get screen size
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        backgroundPanel.setImage(Helper.getIcon("/images/banner.png").getImage());

//       JPanel backgroundPanel = new JPanel();

        var layout = new FlowLayout();
        layout.setAlignment(FlowLayout.CENTER);
        backgroundPanel.setLayout(layout);

        int widthPageStartPanel = screenSize.width;
        JPanel pageStartPanel = new JPanel();
        pageStartPanel.setBackground(new Color(0,0,0,0));
        pageStartPanel.setPreferredSize(new Dimension(widthPageStartPanel, y));
        backgroundPanel.add(pageStartPanel);

        // login
        // start
        var bg = new Color(255,255,255,245);
        var loginLayout = new FlowLayout(FlowLayout.LEFT);
        JPanel loginPanel = new JPanel();
        loginPanel.setPreferredSize(new Dimension(width,height));
        loginPanel.setLayout(loginLayout);
        loginPanel.setBackground(bg);

        // Đăng Nhập
        // start
        JLabel logoLoginLabel = new JLabel("Đăng Nhập");
        logoLoginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLoginLabel.setFont(Fonts.getFont(Font.BOLD,30));
        logoLoginLabel.setPreferredSize(new Dimension(width-30,40));
        loginPanel.add(logoLoginLabel);
        // end


        JLabel statusLabel = new JLabel("Đăng nhập để sử dụng máy");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(Fonts.getFont(Font.ITALIC,13));
        statusLabel.setPreferredSize(new Dimension(width-30,20));
        loginPanel.add(statusLabel);

        // username
        // start
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(Fonts.getFont(Font.BOLD,18));
        usernameLabel.setBorder(new EmptyBorder(20,25,5,5));
        usernameLabel.setPreferredSize(new Dimension(width-30,40));
        loginPanel.add(usernameLabel);
        // end

        // txtUsername
        // start
        FlowLayout centerLayout = new FlowLayout();
        centerLayout.setAlignment(FlowLayout.CENTER);
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(centerLayout);
        usernamePanel.setPreferredSize(new Dimension(width-10,35));
        usernamePanel.setBackground(bg);

        Input txtUsername = new Input("Username");
        txtUsername.setFont(Fonts.getFont(Font.PLAIN,15));
//        txtUsername.setBorder(new EmptyBorder(0,5,0,0));
        txtUsername.setBackground(bg);
        txtUsername.setPreferredSize(new Dimension(width-50,30));
        usernamePanel.add(txtUsername);
        loginPanel.add(usernamePanel);
        // end

        // password
        // start
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(Fonts.getFont(Font.BOLD,18));
        passwordLabel.setBorder(new EmptyBorder(5,25,5,5));
        passwordLabel.setPreferredSize(new Dimension(width-30,20));
        loginPanel.add(passwordLabel);
        //end

        // txtPassword
        // start
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(centerLayout);
        passwordPanel.setPreferredSize(new Dimension(width-10,35));
        passwordPanel.setBackground(bg);

        JPasswordField txtPassword = new JPasswordField();
//        txtPassword.setBorder(new EmptyBorder(0,5,0,0));
        txtPassword.setPreferredSize(new Dimension(width-50,30));
        txtPassword.setBackground(bg);
        passwordPanel.add(txtPassword);
        loginPanel.add(passwordPanel);
        // end

        // button
        // start

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(centerLayout);
        buttonPanel.setPreferredSize(new Dimension(width-10,70));
        buttonPanel.setBackground(bg);
        JButton button = new JButton("Đăng Nhập");
        button.setSize(40,60);
//        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        Main.socket.emit("statusChange",null);
        button.addActionListener(e->{

            Main.socket.on("loginSuccess",(__,arg) -> {
                Session session = (Session) arg;
                Main.session = session;
                Main.socket.removeAllListeners("loginSuccess");
                //toDo: chuyển sang màn hình chính
                this.dispose();
                var mainGUI = new MainGUI();
                mainGUI.setVisible(true);
                Main.socket.emit("statusChange",null);
            });
            Main.socket.emit("login",
                    LoginPayload.builder()
                    .username(txtUsername.getText())
                    .password(new String(txtPassword.getPassword()))
                    .build());

        });
        Main.socket.on("openNewSession",(__,data)->{
            Session session = (Session) data;
            Main.session = session;
            Main.socket.removeAllListeners("openNewSession");
            //toDo: chuyển sang màn hình chính
            this.dispose();
            var mainGUI = new MainGUI();
            mainGUI.setVisible(true);
        });
        buttonPanel.add(button);
        loginPanel.add(buttonPanel);
        // end

        backgroundPanel.add(loginPanel);
        // end
        this.add(backgroundPanel);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {

        new LoginGUI();
    }
}
