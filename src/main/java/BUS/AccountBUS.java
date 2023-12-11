package BUS;

import DAO.Interface.IAccountDAO;
import GUI.Client.MainGUI;
import GUI.Server.Main;
import GUI.Server.MainUI;
import lombok.Setter;
import DTO.Account;

import java.sql.SQLException;
import java.util.List;

public class AccountBUS {
    @Setter
    private SessionBUS sessionBUS;
    @Setter
    private IAccountDAO accountDAO;


    public AccountBUS() {

    }
    public Account create(Account account) throws SQLException {
        var existedAccount = this.findByUsername(account.getUsername());
        if (existedAccount != null) {
            throw new RuntimeException("Username existed");
        }
     return   this.accountDAO.create(account);
    }

    public void update(Account account) throws SQLException {
        this.accountDAO.update(account);
    }

    public void delete(int integer) throws SQLException {
        this.accountDAO.delete(integer);
    }

    public Account findById(int integer) throws SQLException {
        return this.accountDAO.findById(integer);
    }



    public void withdraw(int integer, double amount) throws SQLException {
         var account = this.findById(integer);
            account.setBalance(account.getBalance() - amount);
            if (account.getBalance() < 0) {
                throw new RuntimeException("Not enough money");
            }
            this.update(account);
    }

    public List<Account> getAllAccounts() throws  SQLException {
        var accounts =this.accountDAO.findAll(MainUI.getCurrentUser().getAccount().getRole());
        var sessions = this.sessionBUS.findAll();
        sessions.forEach(s->{

            if(s.getUsingBy()!=null){
                var account = accounts.stream().filter(a->a.getId()==s.getUsingBy()).findFirst().orElse(null);
                if (account!=null) {
                    account.setCurrentSession(s);
                }
            }
        });
        return accounts;
    }

    // chuc nang dat lại mật khẩu
    public void resetPassword(int integer, String newPassword) throws SQLException {
        var account = this.findById(integer);
        account.setPassword(newPassword);
        this.update(account);
    }

    // chức năng đăng nhập
    public Account login(String username, String password)  {
        try {
            var account = this.accountDAO.findByUsername(username);
            if (account == null) {
                return  null;
            }
            if (account.getPassword().equals(password)) {
                return account;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
     
        return null;
    }
    public Account findByUsername(String username) throws SQLException {
      return this.accountDAO.findByUsername(username);

    }
    public void changePassword(int id, String newPassword)  {
        Account account = null;
        try {
            account = this.findById(id);
            account.setPassword(newPassword);
            this.update(account);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deposit(int id, int amount) throws SQLException {
        var account = this.findById(id);
        account.setBalance(account.getBalance() + amount);
        this.update(account);
    }


}
