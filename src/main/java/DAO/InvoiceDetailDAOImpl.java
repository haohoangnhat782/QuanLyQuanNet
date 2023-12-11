package DAO;

import DAO.Interface.IInvoiceDetailDAO;
import DTO.InvoiceDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class InvoiceDetailDAOImpl extends BaseDAO implements IInvoiceDetailDAO {
    @Override
    public InvoiceDetail create(InvoiceDetail invoiceDetail) throws SQLException {
        var preparedStatement = this.prepareStatement("insert into InvoiceDetail (invoiceId, productId, quantity,price) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, invoiceDetail.getInvoiceId());
        preparedStatement.setInt(2, invoiceDetail.getProductId());
        preparedStatement.setInt(3, invoiceDetail.getQuantity());
        preparedStatement.setDouble(4, invoiceDetail.getPrice());
        var result = preparedStatement.executeUpdate();
        if (result > 0) {
            var resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                var newId = resultSet.getInt(1);
                return this.findById(newId);
            }
        }
        return null;
    }

    @Override
    public InvoiceDetail update(InvoiceDetail invoiceDetail) throws SQLException {
        return null;
    }


    @Override
    public boolean delete(Integer invoiceId) throws SQLException {
        String sql = """
                DELETE From InvoiceDetail where invoiceId = ?
                """;
        var stt = this.prepareStatement(sql);
        stt.setInt(1,invoiceId);
        var result = stt.executeUpdate();
        if(result!=0){
            return true;
        }
        return false;
    }

    @Override
    public InvoiceDetail findById(Integer integer) throws SQLException {
        var sql = "select * from InvoiceDetail where id = ?";
        var preparedStatement = this.prepareStatement(sql);
        preparedStatement.setInt(1, integer);
        var resultSet = preparedStatement.executeQuery();
        var list = DBHelper.toList(resultSet, InvoiceDetail.class);
        return list.size() > 0 ? list.get(0) : null;
    }


    @Override
    public List<InvoiceDetail> findAll() throws SQLException {
        return null;
    }

    @Override
    public List<InvoiceDetail> findAllByInvoiceId(Integer invoiceId) {
        String sqlFindByInvoiceId = """
                select * 
                from InvoiceDetail 
                where invoiceId = ?
                """;
        PreparedStatement stt = null;
        try {
            stt = this.prepareStatement(sqlFindByInvoiceId);
            stt.setInt(1,invoiceId);
            var result = stt.executeQuery();
            return DBHelper.toList(result,InvoiceDetail.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
