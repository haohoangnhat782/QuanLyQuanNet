package BUS;

import DTO.Account;
import DTO.Computer;
import Utils.Helper;
import Utils.ServiceProvider;
import lombok.experimental.StandardException;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.sql.SQLException;

import static org.apache.batik.anim.timing.Trace.print;
import static org.junit.jupiter.api.Assertions.*;

class AccountBUSTest {

    @Test
    void ThemMotTaiKhoanDung() throws SQLException {
        ServiceProvider.init();
        var accountBUS= ServiceProvider.getInstance().getService(AccountBUS.class);
        Account ac = new Account();
        ac.setUsername("TaiKhoana1");
        ac.setPassword("123");
        ac.setRole(1);
        ac.setBalance(0);
        assertNotNull(accountBUS.create(ac));

    }
    @Test
    void ThemMotTaiKhoanMatKhauDeTrong() throws SQLException {
        ServiceProvider.init();
        var accountBUS= ServiceProvider.getInstance().getService(AccountBUS.class);
        Account ac = new Account();
        ac.setUsername("TaiKhoana2");
        ac.setPassword("");
        ac.setRole(1);
        ac.setBalance(0);
        assertNotNull(accountBUS.create(ac));

    }
    @Test
    void ThemMotTaiKhoanSoDuNhoHon0() throws SQLException {
        ServiceProvider.init();
        var accountBUS= ServiceProvider.getInstance().getService(AccountBUS.class);
        Account ac = new Account();
        ac.setUsername("TaiKhoana3");
        ac.setPassword("vv1");
        ac.setRole(2);
        ac.setBalance(-2);
        assertNotNull(accountBUS.create(ac));

    }
    @Test
    void ThemMotTaiKhoanBoTrongTaiKhoan() throws SQLException {
        ServiceProvider.init();
        var accountBUS= ServiceProvider.getInstance().getService(AccountBUS.class);

        Account ac = new Account();
        ac.setUsername("");
        ac.setPassword("vvva");
        ac.setRole(2);
        ac.setBalance(2);
        assertNotNull(accountBUS.create(ac));

    }
    @Test
    void ThemMotTaiKhoanTaiKhoanTrung() throws SQLException {
        ServiceProvider.init();
        var accountBUS= ServiceProvider.getInstance().getService(AccountBUS.class);

        Account ac = new Account();
        ac.setUsername("admin");
        var existedAccount = accountBUS.findByUsername(ac.getUsername());
        if (existedAccount != null) {
            System.out.print("Username existed");
        }

        ac.setPassword("vvva");
        ac.setRole(2);
        ac.setBalance(2);
        assertThrows(RuntimeException.class,()->accountBUS.create(ac));


    }
    @Test
    void ThemMotTaiKhoanSoDuChuaKitu() throws SQLException {
        ServiceProvider.init();
        var accountBUS= ServiceProvider.getInstance().getService(AccountBUS.class);

   String sodu="2a";
        if (!Helper.isNumber(sodu)) {
          System.out.print("So Du chua ki tu");

        }
        else{
         int soDUTk=   Integer.parseInt(sodu);
        Account ac = new Account();
        ac.setUsername("adminb1");
        ac.setPassword("vvva");
        ac.setRole(2);
        ac.setBalance(soDUTk);
        assertNotNull(accountBUS.create(ac));}


    }


}