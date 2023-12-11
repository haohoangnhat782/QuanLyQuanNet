package DAO;

import DAO.Interface.IComputerUsageDAO;
import DTO.ComputerUsage;
import DTO.ComputerUsageFilter;
import Utils.Helper;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ComputerUsageImpl extends BaseDAO implements IComputerUsageDAO {

    @Override
    public ComputerUsage create(ComputerUsage computerUsage) throws SQLException {

        var preparedStatement = this.prepareStatement("INSERT INTO ComputerUsage (usedByAccountId, computerID, isEmployeeUsing, createdAt, endAt, totalMoney) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setObject(1, computerUsage.getUsedByAccountId());
        preparedStatement.setObject(2, computerUsage.getComputerID());
        preparedStatement.setBoolean(3, computerUsage.isEmployeeUsing());
        preparedStatement.setTimestamp(4, new java.sql.Timestamp(computerUsage.getCreatedAt().getTime()));
        preparedStatement.setTimestamp(5, new java.sql.Timestamp(computerUsage.getEndAt().getTime()));
        preparedStatement.setDouble(6, computerUsage.getTotalMoney());
        preparedStatement.executeUpdate();
        var resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            computerUsage.setId(resultSet.getInt(1));
        }
        return computerUsage;
    }

    @Override
    public ComputerUsage update(ComputerUsage computerUsage) throws SQLException {
        var preparedStatement = this.prepareStatement("UPDATE ComputerUsage SET usedByAccountId = ?, computerID = ?, isEmployeeUsing = ?, createdAt = ?, endAt = ?, totalMoney = ? WHERE id = ?");
        preparedStatement.setObject(1, computerUsage.getUsedByAccountId());
        preparedStatement.setInt(2, computerUsage.getComputerID());
        preparedStatement.setBoolean(3, computerUsage.isEmployeeUsing());
        preparedStatement.setTime(4, new java.sql.Time(computerUsage.getCreatedAt().getTime()));
        preparedStatement.setTime(5, new java.sql.Time(computerUsage.getEndAt().getTime()));
        preparedStatement.setDouble(6, computerUsage.getTotalMoney());
        preparedStatement.setInt(7, computerUsage.getId());
        preparedStatement.executeUpdate();
        return computerUsage;
    }

    @Override
    public boolean delete(Integer integer) throws SQLException {
        var preparedStatement = this.prepareStatement("DELETE FROM ComputerUsage WHERE id = ?");
        preparedStatement.setInt(1, integer);
        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public ComputerUsage findById(Integer integer) throws SQLException {
        var preparedStatement = this.prepareStatement("SELECT * FROM ComputerUsage WHERE id = ?");
        preparedStatement.setInt(1, integer);
        var resultSet = preparedStatement.executeQuery();
        var list = DBHelper.toList(resultSet, ComputerUsage.class);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<ComputerUsage> findAll() throws SQLException {
        var preparedStatement = this.prepareStatement("SELECT * FROM ComputerUsage " +
                "where isEmployeeUsing = 0 " +
                " order by createdAt desc");
        var resultSet = preparedStatement.executeQuery();
        return DBHelper.toList(resultSet, ComputerUsage.class);
    }

    @Override
    public List<ComputerUsage> findByFilter(ComputerUsageFilter filter) throws Exception {
        String sql = "SELECT * FROM ComputerUsage WHERE 1 = 1 ";
        if (filter.getUsedByAccountId() != null) {
            sql += " AND usedByAccountId  " + (filter.getUsedByAccountId()==-1?"is NULL":"= " + filter.getUsedByAccountId());
        }
        if (filter.getComputerID() != null) {
            sql += " AND computerID = " + filter.getComputerID();
        }
        if (filter.getStartTo() != null) {
            sql += " AND endAt <= '" + Helper.toSqlDateString(filter.getStartTo()) + "'";
        }
        if (filter.getStartFrom() != null) {
            sql += " AND createdAt >= '" + Helper.toSqlDateString(filter.getStartFrom()) + "'";
        }
        if (filter.getIsEmployeeUsing()!=null){
            sql += " AND isEmployeeUsing = " + (filter.getIsEmployeeUsing()?1:0);
        }
        if (filter.getSortBy() != null) {
            sql += " ORDER BY " + filter.getSortBy();
        }

        try (var statement = this.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            return DBHelper.toList(resultSet, ComputerUsage.class);
        }
    }
}