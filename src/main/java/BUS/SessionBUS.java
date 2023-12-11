package BUS;

import DAO.Interface.ISessionDAO;
import DTO.Computer;
import Io.Server;
import Io.Socket;
import Utils.Helper;
import Utils.Interval;
import lombok.Setter;
import DTO.Account;
import DTO.ComputerUsage;
import DTO.Session;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionBUS {
    @Setter
    private ISessionDAO sessionDAO;
    @Setter
    private ComputerUsageBUS computerUsageBUS;
    @Setter
    private AccountBUS accountBUS;
    @Setter
    private ComputerBUS computerBUS;

    public boolean checkIfSessionExist(Integer machineId) throws SQLException {
        var session = sessionDAO.findByComputerId(machineId);
        return session != null;
    }

    public boolean checkIfSessionExist(Account account) throws SQLException {
        var session = sessionDAO.findByAccountId(account.getId());
        return session != null;
    }

    // chức năng đăng xuất
    public void logout(Integer machineId) {

        Session session = null;
        Account account = null;
        Computer computer = null;
        try {
            session = sessionDAO.findByComputerId(machineId);
            account =session.getUsingBy()!=null? accountBUS.findById(session.getUsingBy()):null;
            computer = computerBUS.getComputerById(machineId);
            session.setUsingComputer(computer);
            session.setUsingByAccount(account);
            var computerUsage = ComputerUsage.builder()
                    .computerID(machineId)
                    .endAt(new java.util.Date(System.currentTimeMillis()))
                    .createdAt(new java.util.Date(session.getStartTime().getTime()))
                    .isEmployeeUsing(session.getUsingByAccount() != null && session.getUsingByAccount().getRole() != Account.Role.USER)
                    .usedByAccountId(session.getUsingBy())
                    .build();

            tinhTien(session, computerUsage);
            if (session.getUsingByAccount() != null) {
                var newBalance = account.getBalance() - computerUsage.getTotalMoney();
                newBalance = newBalance < 100 ? 0 : newBalance;
                account.setBalance(newBalance);
                accountBUS.update(account);
            }
            sessionDAO.delete(session.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // chức năng tắt máy
    public void shutDown(Integer computerId) {
        try {
            var session = sessionDAO.findByComputerId(computerId);

            if (session != null) {
                logout(computerId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // chức năng tính tiền
    private void tinhTien(Session session, ComputerUsage computerUsage) throws SQLException {
        if (!computerUsage.isEmployeeUsing()) {
            var machine = computerBUS.getComputerById(session.getComputerID());
            if (machine == null) {
                throw new RuntimeException("Machine not found");
            }
            var cost = machine.getPrice();
            var usedTime = session.getUsedTime(); // in seconds
            double usedTimeInHour = usedTime * 1.0 / 3600;
            var price = Math.round(usedTimeInHour * cost);
            computerUsage.setTotalMoney(price);
        }
        computerUsageBUS.create(computerUsage);
    }

    // chức năng tạo phiên sử dụng máy tính bằng cách đăng nhập
    public Session createSession(Account account, Integer machineId) {

        var session = Session.builder()
                .serviceCost(0)
                .usedCost(0)
                .usedTime(0)
                .startTime(new java.sql.Timestamp(System.currentTimeMillis()))
                .usingBy(account.getId())
                .computerID(machineId)
                .prepaidAmount(account.getBalance())
                .usingByAccount(account)
                .build();

        try {
            var machine = computerBUS.getComputerById(machineId);
            if (machine == null) {
                throw new RuntimeException("Machine not found");
            }
            var cost = machine.getPrice(); // per hour
            // totalTime in seconds
            int totalTime = (int) ((account.getBalance() * 1.0f / cost) * 3600);
            session.setTotalTime(totalTime);
            session.setUsingComputer(machine);
            return sessionDAO.create(session);
        } catch (SQLException e) {
            return null;
        }
    }

    private static final int GAP = 60;//1 minute

    // chức năng tạo phiên bằng cách trả sau (bỏ)
    public Session createSession(int machineId) { // loại trả sau
        var session = Session.builder()
                .serviceCost(0)
                .usedCost(0)
                .usedTime(0)
                .startTime(new java.sql.Timestamp(System.currentTimeMillis()))
                .usingBy(null)
                .computerID(machineId)
                .prepaidAmount(0)
                .totalTime(-1)
                .build();

        try {
            var machine = computerBUS.getComputerById(machineId);
            if (machine == null) {
                throw new RuntimeException("Machine not found");
            }
            session.setUsingComputer(machine);
            var newSession = sessionDAO.create(session);
            var client = Server.getInstance().getClients().stream().filter(c -> c.getMachineId() == machineId).findFirst().orElseThrow();
            client.emit("openNewSession", newSession);
            var intervalId = startSession(newSession, client);
            client.setIntervalId(intervalId);
            return newSession;
        } catch (SQLException e) {
            return null;
        }
    }

    public int startSession(Session session, Socket client) {

        var intervalId = Interval.setInterval(
                (cleanUp) -> {

                    try {
                        try {
                            client.emit("updateSession", new Session(this.increaseUsedTime(session)));
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                            if (e.getMessage().equals("Time out")) {
                                client.emit("timeOut", null);
                                Helper.showSystemNoitification("Hết giờ", "Máy " + session.getComputerID() + " hết thời gian! ", TrayIcon.MessageType.INFO);
                                cleanUp.run();

                                return;  // stop interval
                            }
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                },
                10 * 1000
        );
        client.setIntervalId(intervalId);
        return intervalId;
    }

    // chức năng tạo phiên trả trước
    public Session createSession(int prePaidAmount, int machineId) { // loại trả trước
        var session = Session.builder()
                .serviceCost(0)
                .usedCost(0)
                .usedTime(0)
                .startTime(new java.sql.Timestamp(System.currentTimeMillis()))
                .usingBy(null)
                .computerID(machineId)
                .prepaidAmount(prePaidAmount)
                .build();

        try {
            var machine = computerBUS.getComputerById(machineId);
            if (machine == null) {
                throw new RuntimeException("Machine not found");
            }
            var cost = machine.getPrice(); // per hour
            // totalTime in seconds
            int totalTime = (int) ((prePaidAmount * 1.0f / cost) * 3600);
            session.setTotalTime(totalTime);
            session.setUsingComputer(machine);
            var newSession = sessionDAO.create(session);
            var client = Server.getInstance().getClients().stream().filter(c -> c.getMachineId() == machineId).findFirst().orElseThrow();
            client.emit("openNewSession", newSession);
            client.setIntervalId(startSession(newSession, client));

            return newSession;
        } catch (SQLException e) {
            return null;
        }
    }


    // chức năng tăng thời gian sử dụng
    public Session increaseUsedTime(Session session) throws SQLException {
        session.setUsedTime(session.getUsedTime() + GAP);
        var computerCost = session.getUsingComputer().getPrice();
        var gapCost = computerCost * GAP / 3600;
        session.setUsedCost(session.getUsedCost() + gapCost);
        if (session.getTotalTime() > 0) {

            session.setPrepaidAmount(session.getPrepaidAmount() - gapCost);
            if (session.getUsedTime() >= session.getTotalTime()) {
                handleTimeOut(session);
                throw new RuntimeException("Time out");
            }
        }

        return this.update(session);
    }

    public Session update(Session session) throws SQLException {
        return sessionDAO.update(session);
    }

    public void closeSession(int machineId) throws SQLException {
        var session = sessionDAO.findByComputerId(machineId);
        if (session == null) {
            throw new RuntimeException("Session not found");
        }
        var client = Server.getInstance().getClients().stream().filter(c -> c.getMachineId() == machineId).findFirst().orElseThrow();
        Interval.clearInterval(client.getIntervalId());
        handleTimeOut(session);
        client.emit("timeOut", null);
    }

    // xử lý khi het thoi gian (bỏ)
    private void handleTimeOut(Session session) throws SQLException {
        var computerUsage = ComputerUsage.builder()
                .computerID(session.getComputerID())
                .endAt(new java.util.Date(System.currentTimeMillis()))
                .createdAt(new java.util.Date(session.getStartTime().getTime()))
                .isEmployeeUsing(session.getUsingByAccount() != null && session.getUsingByAccount().getRole() != Account.Role.USER)
                .usedByAccountId(session.getUsingBy())
                .build();

        tinhTien(session, computerUsage);
        if (session.getUsingByAccount() != null) {
            var account = session.getUsingByAccount();
            var newBalance = account.getBalance() - computerUsage.getTotalMoney();
            newBalance = newBalance < 100 ? 0 : newBalance;
            account.setBalance(newBalance);
            accountBUS.update(account);
        }

        sessionDAO.delete(session.getId());
    }

    public Session findByComputerId(int machineId) throws SQLException {
        return sessionDAO.findByComputerId(machineId);
    }

    public List<Session> findAll() {
        try {
            return sessionDAO.findAll();
        } catch (
                SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
