import BUS.AccountBUS;
import BUS.ComputerUsageBUS;
import BUS.EmployeeBUS;
import GUI.Server.MainUI;
import Utils.ServiceProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TestComputerUsageService {
    static ComputerUsageBUS computerUsageBUS;
    @BeforeAll
    public static void init(){
        ServiceProvider.init();
        computerUsageBUS = ServiceProvider.getInstance().getService(ComputerUsageBUS.class);
        var accountService = ServiceProvider.getInstance().getService(AccountBUS.class);
        var account =accountService.login("employee1", "employee1");
        var employee = ServiceProvider.getInstance().getService(EmployeeBUS.class).findEmployeeByAccountID(account.getId());
        MainUI.setCurrentUser(employee);
    }
    @Test
    @DisplayName("Test create computer usage for employee")
    public void testCreateComputerUsage(){
        assertDoesNotThrow(()->{
            var start =Utils.randomDate("13/01/2021", "13/04/2023");
                    var end = Utils.addRandomHour(start, 2, 10);
            computerUsageBUS.createForEmployee(
                   start,
                    end,
                    5
            );
        });
        assertDoesNotThrow(()->{
            var start =Utils.randomDate("13/01/2021", "13/04/2023");
            var end = Utils.addRandomHour(start, 2, 10);
            computerUsageBUS.createForEmployee(
                    start,
                    end,
                    6
            );
        });
        assertDoesNotThrow(()->{
            var start =Utils.randomDate("13/09/2021", "13/04/2023");
            var end = Utils.addRandomHour(start, 2, 10);
            computerUsageBUS.createForEmployee(
                    start,
                    end,
                    6
            );
        });
    }
}
