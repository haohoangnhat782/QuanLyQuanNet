package BUS;

import DAO.InvoiceDAOImpl;
import DAO.InvoiceDetailDAOImpl;
import DAO.ProductDAOImpl;
import DTO.Invoice;
import DTO.InvoiceDetail;

import java.sql.SQLException;
import java.util.List;

public class InvoiceDetailBUS {
    InvoiceDetailDAOImpl invoiceDetailDAO = new InvoiceDetailDAOImpl();
    ProductDAOImpl productDAO = new ProductDAOImpl();
    InvoiceDAOImpl invoiceDAO = new InvoiceDAOImpl();
    public InvoiceDetail createInvoiceDetail(InvoiceDetail invoiceDetail){
        try {
            var invoice = invoiceDAO.findById(invoiceDetail.getInvoiceId());
            var old = productDAO.findById(invoiceDetail.getProductId());
            if(old.getStock()>0){
                if(invoice.getType()== Invoice.InvoiceType.EXPORT){
                    if(old.getStock()<invoiceDetail.getQuantity()){
                        throw new RuntimeException("Khong du");
                    }
                    old.setStock(old.getStock()-invoiceDetail.getQuantity());

                }else {
                    old.setStock(old.getStock()+invoiceDetail.getQuantity());
                }
            }
            productDAO.update(old);

            return invoiceDetailDAO.create(invoiceDetail);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<InvoiceDetail> findByInvoiceId(Integer invoiceId){
        return invoiceDetailDAO.findAllByInvoiceId(invoiceId);
    }


    public void updateDetailInvoice(InvoiceDetail invoiceDetail){
        try {
            invoiceDetailDAO.update(invoiceDetail);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //delete tat ca cac hoa don co id la ?
    public boolean deleteDetailInvoice(Integer invoiceId){
        try {
            return invoiceDetailDAO.delete(invoiceId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
