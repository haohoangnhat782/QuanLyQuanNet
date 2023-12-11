package DAO;

import DAO.Interface.IInvoiceDAO;
import DTO.InforFilter;
import DTO.Invoice;

import java.sql.*;
import java.util.List;

public class InvoiceDAOImpl extends BaseDAO implements IInvoiceDAO {


    @Override
    public Invoice update(Invoice invoice) throws SQLException {
        String sqlUpdateInvoice = """
                UPDATE invoice 
                SET computerId = ?,createdBy = ?,createdToAccountId = ?,isPaid = ?,status = ?,total = ?
                WHERE id = ?
                """;
        var stt = this.prepareStatement(sqlUpdateInvoice);
        if(invoice.getComputerId() == 0){//khi tao phieu nhap thi computer set null
            stt.setString(1,null);
        }
        else {
            stt.setInt(1, invoice.getComputerId());
        }

        if(invoice.getCreatedToAccountId() == 0){
            stt.setString(3,null);
        }
        else {
            stt.setInt(3, invoice.getCreatedToAccountId());
        }
        stt.setInt(2,invoice.getCreatedBy());
        System.out.print(invoice.isPaid());
        stt.setInt(4,invoice.isPaid() ? 1:0);
        stt.setInt(5,invoice.getStatus().ordinal());
        stt.setDouble(6,invoice.getTotal());
        stt.setInt(7,invoice.getId());
        var rowEffect = stt.executeUpdate();
        stt.close();
        return rowEffect == 1 ? findById(invoice.getId()):null;
    }







    @Override
    public List<Invoice> findByEmployeeId(int employeeId, Invoice.InvoiceType type) throws SQLException {
        var sql = "select * from Invoice where createdBy = ? and type = ? and deletedAt is null";
        try(var preparedStatement = this.prepareStatement(sql)) {
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setInt(2, type.ordinal());
            var resultSet = preparedStatement.executeQuery();
            return DBHelper.toList(resultSet, Invoice.class);
        }
    }


    @Override
    public boolean delete(Integer integer) throws SQLException {
        String sqlUpdateInvoiceById = """
                UPDATE invoice
                SET deletedAt = getdate()
                WHERE id = ?;
                """;
        var stt = this.prepareStatement(sqlUpdateInvoiceById);
        stt.setInt(1,integer);
        var rowEffect = stt.executeUpdate();
        stt.close();
        return rowEffect > 0 ? true:false;
    }

    @Override
    public Invoice findById(Integer integer) throws SQLException{
        String sqlSelectById = """
                select *
                from invoice
                where id = ? and deletedAt is null
                ORDER BY id DESC;
                """;
        var stt = this.prepareStatement(sqlSelectById);
        stt.setInt(1,integer);
        var rs = stt.executeQuery();
        var invoices = DBHelper.toList(rs,Invoice.class);
        stt.close();
        return invoices.size() > 0 ? invoices.get(0): null;
    }

    @Override
    public List<Invoice> findAll() throws SQLException {

        String sqlSelectALlRow = """
                    select * 
                    from Invoice 
                    where deletedAt is null
                    ORDER BY id DESC;
                    """;
        var stt =this.createStatement();
        var rs = stt.executeQuery(sqlSelectALlRow);
        var listInvoice = DBHelper.toList(rs, Invoice.class);
        stt.close();
        return listInvoice;
    }



    @Override
    public List<Invoice> findAllByType(Invoice.InvoiceType type) throws SQLException {
        String sqlSelectALlRow = """
                select *
                from Invoice
                where deletedAt is null and type = ?
                ORDER BY id DESC;
                 """;
        var stt =this.prepareStatement(sqlSelectALlRow);
        stt.setInt(1,type.ordinal());
        var rs = stt.executeQuery();
        var listInvoice = DBHelper.toList(rs, Invoice.class);
        stt.close();
        return listInvoice;
    }

    @Override
    public List<Invoice> findInvoiceByInforFilter(Invoice.InvoiceType type, InforFilter inforFilter) throws SQLException {
        int quantityQuestionMark = 4;
        String sqlSelectInvoiceByInforFilter = """
                 select *
                 from Invoice
                 where ((createdAt between ? and ?)
                 and (total between ? and  ?)
                """;
        if(inforFilter.getAccountID() != 0 && inforFilter.getAccountID() != -1 ){
            sqlSelectInvoiceByInforFilter += "and (createdToAccountId = ?) ";
        }

        if(inforFilter.getAccountID() == -1 ){//neu bang -1 thi la khach van lai
            sqlSelectInvoiceByInforFilter += "and  (createdToAccountId is NULL) ";
        }

        if(inforFilter.getComputerID() != 0){
            sqlSelectInvoiceByInforFilter+= " and (computerId = ?)";
        }
        if(inforFilter.getEmployeeID() != 0){
            sqlSelectInvoiceByInforFilter += "and (createdBy = ?)";
        }
        sqlSelectInvoiceByInforFilter+= """
                 and deletedAt is NULL
                 and type = ?)
                 ORDER BY id DESC;
                """;


        var stt = this.prepareStatement(sqlSelectInvoiceByInforFilter);
        stt.setString(1,inforFilter.getDateFrom());
        stt.setString(2,inforFilter.getDateTo());
        if(inforFilter.getTotalFrom().equals(""))
            stt.setDouble(3,0);
        else
            stt.setDouble(3,Double.parseDouble(inforFilter.getTotalFrom()));

        if(inforFilter.getTotalTo().equals(""))
            stt.setDouble(4,Integer.MAX_VALUE);//set totalTo la max
        else
            stt.setDouble(4,Double.parseDouble(inforFilter.getTotalTo()));

        if(inforFilter.getAccountID() != 0  && inforFilter.getAccountID() != -1){
            quantityQuestionMark+=1;
            stt.setInt(quantityQuestionMark,inforFilter.getAccountID());
        }
        if(inforFilter.getComputerID() != 0){
            quantityQuestionMark+=1;
            stt.setInt(quantityQuestionMark,inforFilter.getComputerID());
        }

        if(inforFilter.getEmployeeID() != 0){
            quantityQuestionMark+=1;
            stt.setInt(quantityQuestionMark,inforFilter.getEmployeeID());
        }
        System.out.println(stt.toString());
        quantityQuestionMark+=1;
        stt.setInt(quantityQuestionMark,type.ordinal());
        var rs = stt.executeQuery();
        var listInvoice = DBHelper.toList(rs,Invoice.class);
        stt.close();
        return listInvoice;
    }


    @Override
    public Invoice create(Invoice invoice) throws SQLException {
        System.out.print(invoice.toString());
        try(var stt = this.prepareStatement("insert into Invoice (computerId, createdAt, createdBy, createdToAccountId, deletedAt, isPaid, note, status, total, type) values (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            if(invoice.getComputerId() == 0){//khi tao phieu nhap thi computer set null
                stt.setString(1,null);
            }
            else {
                stt.setInt(1, invoice.getComputerId());
            }

            if(invoice.getCreatedToAccountId() == null|| invoice.getCreatedToAccountId() == 0){//khi tao phieu nhap thi computer set null
                stt.setObject(4,null);
            }
            else {
                stt.setInt(4, invoice.getCreatedToAccountId());
            }

            System.out.print(new java.sql.Timestamp(invoice.getCreatedAt().getTime()));
            stt.setTimestamp(2, new java.sql.Timestamp(invoice.getCreatedAt().getTime()));
            stt.setInt(3, invoice.getCreatedBy());
            stt.setNull(5, Types.TIMESTAMP);
            stt.setBoolean(6, invoice.isPaid());
            stt.setString(7, invoice.getNote());
            stt.setInt(8, invoice.getStatus().ordinal());
            stt.setDouble(9, invoice.getTotal());
            stt.setInt(10, invoice.getType().ordinal());

            var result = stt.executeUpdate();
            if (result > 0) {
                var resultSet = stt.getGeneratedKeys();
                if (resultSet.next()) {
                    var newId = resultSet.getInt(1);
                    return this.findById(newId);
                }
            }
        }
        return null;
    }
}

