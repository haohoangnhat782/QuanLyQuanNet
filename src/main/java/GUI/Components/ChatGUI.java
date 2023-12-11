/*
 * Created by JFormDesigner on Fri Apr 07 13:10:50 ICT 2023
 */

package GUI.Components;

import lombok.Getter;
import lombok.Setter;
import DTO.Message;

import java.awt.*;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Laffy
 */
public class ChatGUI extends JFrame {

    @Setter
    @Getter
    private List<Message> messages;
    private final Message.FROM sender;

    public  interface OnSendListener {
        void onSend(String content);
    }

    private OnSendListener onSendListener;

    public ChatGUI(List<Message> messages, Message.FROM sender) {
        this.messages = messages;
        this.sender = sender;
        initComponents();
        reDesign();
        reloadMessageHistory();
        initEvent();
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

    }


    private void reDesign() {
    }

    private void initEvent() {
        button1.addActionListener(e -> {
            if (inputTextArea.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "Không được để trống tin nhắn");
                return;
            }
            if (onSendListener != null) {
                onSendListener.onSend(inputTextArea.getText());
            }
            var now = new Date();
            var time = String .format("%02d:%02d:%02d", now.getHours(), now.getMinutes(), now.getSeconds());
            chattextArea2.append(String.format("Bạn ( lúc%s): %s\r\n", time, inputTextArea.getText()));
            inputTextArea.setText("");
        });
    }

    public void setOnSendListener(OnSendListener onSendListener) {
        this.onSendListener = onSendListener;
    }

    public void reloadMessageHistory() {
        chattextArea2.setText("");
        for (Message message : messages) {
            var time = message.getCreatedAt();
            //format HH:mm:ss
            String timeString = String.format("%02d:%02d:%02d", time.getHours(), time.getMinutes(), time.getSeconds());
            if (this.sender == message.getFromType()) {

                chattextArea2.append(String.format("Bạn ( lúc%s): %s\r\n", timeString, message.getContent()));
            } else {
                if (message.getFromType() == Message.FROM.SERVER) {
                    chattextArea2.append(String.format("Máy chủ (lúc %s): %s\r\n", timeString, message.getContent()));
                } else {
                    chattextArea2.append(String.format("Máy khách %s (%s): %s\r\n",message.getSession().getComputerID(), timeString, message.getContent()));
                }
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        button1 = new JButton();
        scrollPane1 = new JScrollPane();
        inputTextArea = new JTextArea();
        panel2 = new JPanel();
        scrollPane2 = new JScrollPane();
        chattextArea2 = new JTextArea();

        //======== this ========
        setPreferredSize(new Dimension(400, 500));
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setBorder(new EmptyBorder(5, 5, 5, 5));
            panel1.setLayout(new BorderLayout(5, 5));

            //---- button1 ----
            button1.setText("G\u1eedi");
            button1.setFont(new Font("Nunito", Font.BOLD, 14));
            panel1.add(button1, BorderLayout.EAST);

            //======== scrollPane1 ========
            {

                //---- inputTextArea ----
                inputTextArea.setColumns(1);
                inputTextArea.setPreferredSize(new Dimension(32, 32));
                inputTextArea.setFont(new Font("Nunito", Font.PLAIN, 14));
                scrollPane1.setViewportView(inputTextArea);
            }
            panel1.add(scrollPane1, BorderLayout.CENTER);
        }
        contentPane.add(panel1, BorderLayout.SOUTH);

        //======== panel2 ========
        {
            panel2.setBorder(new EmptyBorder(5, 5, 5, 5));
            panel2.setLayout(new BorderLayout());

            //======== scrollPane2 ========
            {

                //---- chattextArea2 ----
                chattextArea2.setEditable(false);
                chattextArea2.setFont(new Font("Nunito", Font.PLAIN, 14));
                scrollPane2.setViewportView(chattextArea2);
            }
            panel2.add(scrollPane2, BorderLayout.CENTER);
        }
        contentPane.add(panel2, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JButton button1;
    private JScrollPane scrollPane1;
    private JTextArea inputTextArea;
    private JPanel panel2;
    private JScrollPane scrollPane2;
    private JTextArea chattextArea2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on


}
