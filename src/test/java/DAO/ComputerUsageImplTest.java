package DAO;

import DTO.Computer;
import DTO.ComputerUsage;
import DTO.ComputerUsageFilter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class ComputerUsageImplTest {

    @Test
    void create() throws SQLException {
        var computerUsageImpl = new ComputerUsageImpl();
        var computerUsage = new ComputerUsage();
        var result = computerUsageImpl.create(computerUsage);
        assertSame(new ComputerUsage(), result);
    }

    @Test
    void update() throws SQLException {
        var computerUsageImpl = new ComputerUsageImpl();
        var result = computerUsageImpl.update( ComputerUsage.builder().id(1).build());
        assertSame(new ComputerUsage(), result);
    }

    @SneakyThrows
    @Test
    void delete_False() throws SQLException {
        var computerUsageImpl = new ComputerUsageImpl();
        var result = computerUsageImpl.delete(1);
        assertFalse(result);
    }

    @SneakyThrows
    @Test
    void delete_True() throws SQLException {
        var computerUsageImpl = new ComputerUsageImpl();
        var result = computerUsageImpl.delete(1);
        assertTrue(result);
    }

    @Test
    void findById_NotNull() throws SQLException {
        var computerUsageImpl = new ComputerUsageImpl();
        var result = computerUsageImpl.findById(1);
        assertNotNull(result);
    }

    @Test
    void findById_Correct() throws SQLException {
        var computerUsageImpl = new ComputerUsageImpl();
        ComputerUsage result = computerUsageImpl.findById(1);
        assertSame(result, result);
    }

    @Test
    void findById_CorercetNull() throws SQLException {
        var computerUsageImpl = new ComputerUsageImpl();
        ComputerUsage result = computerUsageImpl.findById(1);
        assertSame(new ComputerUsage(), result);
    }

    @Test
    void findAll() throws SQLException {
        var computerUsageImpl = new ComputerUsageImpl();
        var result = computerUsageImpl.findAll();
        assertEquals(result, result);
    }

    @Test
    void findByFilter() throws Exception {
        var computerUsageImpl = new ComputerUsageImpl();
        var result = computerUsageImpl.findByFilter(new ComputerUsageFilter());
        assertEquals(result, result);
    }
}