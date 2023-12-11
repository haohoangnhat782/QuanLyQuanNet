import DAO.AccountDAOImpl;
import DAO.Interface.IAccountDAO;
import DTO.Account;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import BUS.AccountBUS;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Test Account Service")
public class TestAccountService {
    static AccountBUS accountBUS;
    @BeforeAll
    public static void init(){
        ServiceBuilder.getInstance()
                .register(AccountBUS.class, AccountBUS.class)
                .register(IAccountDAO.class, AccountDAOImpl.class)
                .build();
        accountBUS = ServiceBuilder.getInstance().getService(AccountBUS.class);
    }
    @Test
    @DisplayName("Test get all accounts")
    public void testGetAllAccounts(){
        assertDoesNotThrow(()->{
            Collection<Account> accounts = accountBUS.getAllAccounts();
            assertNotNull(accounts);
            assertTrue(accounts.size() > 0);
        });
    }
    @Test
    @DisplayName("Test get account by id")
    public void testGetAccountById(){
        assertDoesNotThrow(()->{
            Account account = accountBUS.findById(1);
            assertNotNull(account);
            assertEquals(1, account.getId());
        });
    }
    @Test
    @DisplayName("Test get account by id not found")
    public void testGetAccountByIdNotFound() throws SQLException {
            Account account = accountBUS.findById(100000);
            assertNull(account);
    }
    @Test
    @DisplayName("Test create account")
    public void testCreateAccount() throws ParseException, SQLException {
        int count = accountBUS.getAllAccounts().size();
        Account account = new Account();
        account.setId(40);
        account.setUsername("test");
        account.setPassword("test");
        account.setBalance(1000);
        accountBUS.create(account);
        assertEquals(count + 1, accountBUS.getAllAccounts().size());
    }

}
