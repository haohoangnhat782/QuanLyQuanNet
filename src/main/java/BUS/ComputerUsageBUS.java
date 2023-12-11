package BUS;

import DAO.Interface.IComputerUsageDAO;
import lombok.Setter;
import DTO.ComputerUsage;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ComputerUsageBUS {
    @Setter
    private IComputerUsageDAO computerUsageDAO;
    @Setter
    private EmployeeBUS employeeBUS;
    @Setter
    private AccountBUS accountBUS;
    @Setter
    private ComputerBUS computerBUS;

    public ComputerUsage create(ComputerUsage computerUsage) throws SQLException {
         return computerUsageDAO.create(computerUsage);
    }
    public ComputerUsage createForEmployee(Date startAt, Date endAt,int accountId) throws SQLException {
        var employee = employeeBUS.findEmployeeByAccountID(accountId);
        var salaryPerHour = employee.getSalaryPerHour();
        var salaryPerMinute = salaryPerHour / 60;
        var minuteDiff = (endAt.getTime() - startAt.getTime()) / 1000 / 60;
        var totalMoney = salaryPerMinute * minuteDiff;
        ComputerUsage computerUsage = ComputerUsage.builder()
                .createdAt(startAt)
                .endAt(endAt)
                .isEmployeeUsing(true)
                .usedByAccountId(accountId)
                .totalMoney(totalMoney)
                .computerID(null)
                .build();
        return create(computerUsage);
    }
public List<ComputerUsage> getAll()  {
    try {
        var list = computerUsageDAO.findAll();

        return includeDetail(list);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
    public List<ComputerUsage> includeDetail(List<ComputerUsage> list){
        list.forEach(computerUsage -> {
            if (computerUsage.getUsedByAccountId() != null) {
                try {
                    computerUsage.setUsedBy(accountBUS.findById(computerUsage.getUsedByAccountId()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                var computer = computerUsage.getComputerID()==null?null: computerBUS.getComputerById(computerUsage.getComputerID());
                computerUsage.setComputer(computer);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return list;
    }
    public List<ComputerUsage> findByFilter(DTO.ComputerUsageFilter filter) throws Exception {
        var raw =computerUsageDAO.findByFilter(filter );
        return includeDetail(raw);
    }
    public ComputerUsage update(ComputerUsage computerUsage) throws SQLException {
        return computerUsageDAO.update(computerUsage);
    }
    public boolean delete(Integer integer) throws SQLException {
        return computerUsageDAO.delete(integer);
    }
    public ComputerUsage findById(Integer integer) throws SQLException {
        return computerUsageDAO.findById(integer);
    }

 
}
