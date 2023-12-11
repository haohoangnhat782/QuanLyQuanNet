package Io;

import BUS.*;
import Payload.LoginPayload;
import Utils.Helper;
import Utils.Interval;
import Utils.ServiceProvider;
import DTO.Message;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;

public class SocketController {
    private final SessionBUS sessionBUS;
    private final AccountBUS accountBUS;
    private final Server server;
    private final MessageBUS messageBUS;
    private final ComputerBUS computerBUS;
    private final InvoiceBUS invoiceBUS;

    public SocketController(Server server) {
        this.server = server;
        sessionBUS = ServiceProvider.getInstance().getService(SessionBUS.class);
        accountBUS = ServiceProvider.getInstance().getService(AccountBUS.class);
        messageBUS = ServiceProvider.getInstance().getService(MessageBUS.class);
        computerBUS = ServiceProvider.getInstance().getService(ComputerBUS.class);
        invoiceBUS = ServiceProvider.getInstance().getService(InvoiceBUS.class);
    }

    public void startListen() throws IOException {
        server.listen();
        server.on("login", this::onLogin);
        server.on("message", this::onMessage);
        server.on("changePassword", this::onChangePassword);
        server.on("logout", this::onLogout);
        server.on("shutdown", this::onShutDown);
        server.on("order", this::onOrder);

    }

    private void onOrder(Socket socket, Serializable invoice) {
        Helper.showSystemNoitification("Thông báo", "Có đơn hàng mới", TrayIcon.MessageType.INFO);
        invoiceBUS.order((DTO.CreateInvoiceInputDTO) invoice);
        System.out.println(invoice);
    }

    private void onChangePassword(Socket socket, Serializable serializable) {
        try {
            var session = sessionBUS.findByComputerId(socket.getMachineId());
            var account = accountBUS.findById(session.getUsingBy());
            accountBUS.changePassword(account.getId(), serializable.toString());
            server.emit("infoMessage", "Đổi mật khẩu thành công");
        } catch (SQLException e) {
            e.printStackTrace();
            server.emit("errorMessage", "Đổi mật khẩu thất bại");
        }
    }
    public void onMessage(Socket client, Serializable data) {
        try {
            var session = sessionBUS.findByComputerId(client.getMachineId());
            var computer = computerBUS.getComputerById(client.getMachineId());
            if (session == null) {
                server.emit("errorMessage", "Lỗi máy tính");
                return;
            }
            var message = Message.builder().id(null).content((String) data).fromType(Message.FROM.CLIENT).createdAt(new Date()).sessionId(session.getId()).build();
            messageBUS.create(message);
            Helper.showSystemNoitification("Tin nhắn từ máy " + computer.getName(), (String) data, TrayIcon.MessageType.INFO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void onLogin(Socket client, Serializable data) {
        try {
            LoginPayload loginPayload = (LoginPayload) data;
            var account = accountBUS.login(loginPayload.getUsername(), loginPayload.getPassword());

            if (sessionBUS.checkIfSessionExist(client.getMachineId())) {
                server.emit("errorMessage", "Lỗi máy tính");
                return;
            }
            if (account != null) {
                if (sessionBUS.checkIfSessionExist(account)) {
                    server.emit("errorMessage", "Tài khoản của bạn đang được sử dụng ở máy khác");
                    return;
                }
                if (account.getBalance() <= 100) {
                    server.emit("errorMessage", "Tài khoản của bạn không đủ tiền");
                    return;
                }
                var session = sessionBUS.createSession(account, client.getMachineId());

                server.emit("loginSuccess", session);
                Helper.showSystemNoitification("Máy " + client.getMachineId() + " đã đăng nhập!", "", TrayIcon.MessageType.INFO);
                client.setIntervalId(this.sessionBUS.startSession(session, client));
            } else {
                server.emit("errorMessage", "Sai tên đăng nhập hoặc mật khẩu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void onLogout(Socket socket, Serializable serializable) {
        this.sessionBUS.logout(socket.getMachineId());
        Helper.showSystemNoitification("Máy " + socket.getMachineId() + " đã đăng xuất!", "", TrayIcon.MessageType.INFO);
        server.emitSelf("statusChange", null);
    }
    private void onShutDown(Socket socket, Serializable serializable) {
        Server.getInstance().removeClient(socket.getMachineId());
        Helper.showSystemNoitification("Máy " + socket.getMachineId() + " đã ngắt kết nối!", "", TrayIcon.MessageType.INFO);
        this.sessionBUS.shutDown(socket.getMachineId());
        Interval.clearInterval(socket.getIntervalId());
        System.out.println("ok");
        server.emitSelf("statusChange", null);
    }
}
