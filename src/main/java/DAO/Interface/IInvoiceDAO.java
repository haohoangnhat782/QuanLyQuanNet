package DAO.Interface;

import DTO.InforFilter;
import DTO.Invoice;

import java.sql.SQLException;
import java.util.List;

public interface IInvoiceDAO extends IDAO<Invoice, Integer> {
    public List<Invoice> findAllByType(Invoice.InvoiceType type) throws SQLException;
    public  List<Invoice> findInvoiceByInforFilter(Invoice.InvoiceType type, InforFilter inforFilter) throws  SQLException;
    List<Invoice> findByEmployeeId(int employeeId, Invoice.InvoiceType type) throws SQLException;

}
