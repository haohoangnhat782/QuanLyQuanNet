/*
 * Created by JFormDesigner on Tue Mar 28 22:12:58 ICT 2023
 */

package GUI.Server.Home;

import javax.swing.border.*;

import GUI.Components.ChatGUI;
import Io.Callback;
import Io.Server;
import Utils.Fonts;
import Utils.Helper;
import Utils.ServiceProvider;
import DTO.Computer;
import DTO.Message;
import BUS.ComputerBUS;
import BUS.MessageBUS;
import BUS.SessionBUS;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.swing.*;

/**
 * @author Laffy
 */
public class Home extends JPanel {
    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
       fetchComputers();
    }

    private List<JButton> computerButtons;
    private List<Computer> computers;
    private final ComputerBUS computerBUS;
    private final SessionBUS sessionBUS;
    private final MessageBUS messageBUS;


    public Home() {
        initComponents();
        computerBUS = ServiceProvider.getInstance().getService(ComputerBUS.class);
        sessionBUS = ServiceProvider.getInstance().getService(SessionBUS.class);
        messageBUS = ServiceProvider.getInstance().getService(MessageBUS.class);
        try {
            computers = computerBUS.getAllComputers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        reDesign();
        registerListener();
    }

    private void registerListener() {
        Server.getInstance().on("statusChange", (__, ___) -> {
            fetchComputers();
        });
        Server.getInstance().on("onDisconnection", (__, ___) -> {
            fetchComputers();
        });
    }


    private JPanel headerPanel;
    private JLabel headerLabel;

    private void reDesign() {
        headerPanel = new JPanel();
//        headerPanel.setBackground(new Color(0x00ffffff, true));
        headerPanel.setLayout(new FlowLayout());
        headerLabel = new JLabel("Quản lý nhanh máy trạm");
        headerLabel.setFont(Fonts.getFont(Font.BOLD, 30));
        headerPanel.add(headerLabel);
        headerLabel.setBorder(new EmptyBorder(20, 0, 0, 0));
        headerLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(headerPanel, BorderLayout.NORTH);

        fetchComputers();

    }

    private void fetchComputers() {
        computerPanel.removeAll();
        var offlineImg = Helper.getIcon("/icons/computerOff.png", 100, 100);
        var lockImg = Helper.getIcon("/icons/computerLocking.png", 100, 100);
        var onlineImg = Helper.getIcon("/icons/computerUsing.png", 100, 100);
        try {
            computers = computerBUS.getAllComputers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        computers = computerBUS.updateListComputerStatus(computers);
        computers.forEach(computer -> {
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem tatItem = new JMenuItem("Tắt máy");
            JMenuItem lockItem = new JMenuItem("Khoá máy");
            JMenuItem moMayTraTruoc = new JMenuItem("Mở máy trả trước");
            JMenuItem moMayTraSau = new JMenuItem("Mở máy trả sau");
            JMenuItem napTien = new JMenuItem("Nạp tiền");
            JMenuItem chiTiet = new JMenuItem("Chi tiết");
            JMenuItem tinNhan = new JMenuItem("Tin nhắn");
            tatItem.setFont(Fonts.getFont(Font.PLAIN, 15));
            moMayTraTruoc.setFont(Fonts.getFont(Font.PLAIN, 15));
            moMayTraSau.setFont(Fonts.getFont(Font.PLAIN, 15));
            napTien.setFont(Fonts.getFont(Font.PLAIN, 15));
            chiTiet.setFont(Fonts.getFont(Font.PLAIN, 15));
            tinNhan.setFont(Fonts.getFont(Font.PLAIN, 15));

            popupMenu.add(tatItem);
            popupMenu.add(moMayTraTruoc);
            popupMenu.add(moMayTraSau);
            popupMenu.addSeparator();
            popupMenu.add(chiTiet);
            popupMenu.add(tinNhan);
            popupMenu.addSeparator();
            popupMenu.add(napTien);
            moMayTraTruoc.addActionListener(e -> {
                var result = JOptionPane.showInputDialog(this, "Nhập số tiền trả trước");
                try {
                    int prePaid = Integer.parseInt(result);
                    if (prePaid <= 1000) throw new RuntimeException();
                    sessionBUS.createSession(prePaid, computer.getId());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Số tiền không hợp lệ");
                }
            });
            moMayTraSau.addActionListener(e -> {
                this.sessionBUS.createSession(computer.getId());
            });
            lockItem.addActionListener(e -> {
                try {
                    this.sessionBUS.closeSession(computer.getId());
                    Server.getInstance().emitSelf("statusChange", null);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            tinNhan.addActionListener(e -> {
                try {
                    var session = sessionBUS.findByComputerId(computer.getId());
                    var list = messageBUS.findAllBySessionId(session.getId());
                    list.forEach(m -> {
                        m.setSession(session);
                    });
                    var chatGUI = new ChatGUI(list, Message.FROM.SERVER);

                    chatGUI.setVisible(true);
                    // set as Dialog
                    var socketClient= Server.getInstance().getClients().stream().filter(c -> c.getMachineId() == computer.getId()).findFirst();
                    if (socketClient.isEmpty()){
                        throw new RuntimeException("Không tìm thấy máy");
                    }
                    chatGUI.setOnSendListener((message) -> {
                        try {
                            messageBUS.create(
                                    Message.builder()
                                            .sessionId(session.getId())
                                            .content(message)
                                            .fromType(Message.FROM.SERVER).
                                            createdAt(new Date()).build()
                            );
                            socketClient.get().emit("message", message);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    });

                    Callback onMessage =(client, message) -> {
                        if (client.getMachineId() != computer.getId()) return;
                        Message clientMessage = Message.builder()
                                .sessionId(session.getId())
                                .session(session)
                                .content(message.toString())
                                .fromType(Message.FROM.CLIENT).
                                        createdAt(new Date()).build();

                        chatGUI.getMessages().add(clientMessage);
                        chatGUI.reloadMessageHistory();
                    };
                    Server.getInstance().on("message", onMessage);
                    chatGUI.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            socketClient.get().removeListener("message", onMessage);
                        }
                    });
                    chatGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            });

            JButton button = new JButton();
            button.setComponentPopupMenu(popupMenu);
            button.addActionListener(e -> {
                // show on mouse location
                popupMenu.show(button, button.getWidth() / 2, button.getHeight() / 2);
            });
            button.setText(computer.getName());
//                // random 0 1 2
//                int random = (int) (Math.random() * 100) % 3+1  ;
            switch (computer.getStatus()) {
                case OFF -> {
                    button.setIcon(offlineImg);
                    tatItem.setEnabled(false);
                    tinNhan.setEnabled(false);
                    napTien.setEnabled(false);
                    lockItem.setEnabled(false);
                }
                case LOCKED -> {
                    button.setIcon(lockImg);
                    //#F56565
                    button.setForeground(new Color(0xff5656));
                    tinNhan.setEnabled(false);
                    napTien.setEnabled(false);
                    lockItem.setEnabled(false);
                    tatItem.setEnabled(true);

                }
                case USING -> {
                    //#68D391
                    //
                    button.setForeground(new Color(0x68d391));
                    button.setIcon(onlineImg);
                    moMayTraSau.setEnabled(false);
                    moMayTraTruoc.setEnabled(false);
                    tinNhan.setEnabled(true);
                    lockItem.setEnabled(true);
                    tatItem.setEnabled(true);
                }
            }
            button.setFont(Fonts.getFont(Font.BOLD, 20));
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setBackground(new Color(0x00ffffff, true));
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            tatItem.addActionListener(e -> {
                    Server.getInstance().getClients().stream().filter(c -> c.getMachineId() == computer.getId()).findFirst().ifPresent(c -> {
                        c.emit("forceLock", null);
                    });
            });
            computerPanel.add(button);
        });

        // refresh
        revalidate();
        repaint();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        panel2 = new JPanel();
        computerPanel = new JPanel();

        //======== this ========
        setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setLayout(new BorderLayout());
        }
        add(panel1, BorderLayout.EAST);

        //======== panel2 ========
        {
            panel2.setLayout(new BorderLayout());

            //======== computerPanel ========
            {
                computerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
                computerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
            }
            panel2.add(computerPanel, BorderLayout.CENTER);
        }
        add(panel2, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JPanel panel2;
    private JPanel computerPanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
