package BUS;

import DAO.Interface.IComputerUsageDAO;
import DTO.ComputerUsage;
import Utils.ServiceProvider;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ComputerUsageBUSTest {

    @Test
    void DeleteMayTonTai() throws SQLException {
        ServiceProvider.init();
        var computerU = ServiceProvider.getInstance().getService(ComputerUsageBUS.class);
        assertNotNull(computerU.delete(19));

    }

    @Test
    void DeleteMayKhonTonTai() throws SQLException {
        ServiceProvider.init();
        var computerU = ServiceProvider.getInstance().getService(ComputerUsageBUS.class);
        assertNotNull(computerU.delete(1111111));

    }


}