package BUS;

import DAO.Interface.IInvoiceDAO;
import DAO.InvoiceDAOImpl;
import Utils.Helper;
import DAO.Interface.IInvoiceDetailDAO;
import DTO.CreateInvoiceInputDTO;
import GUI.Server.MainUI;
import lombok.Setter;
import DTO.InforFilter;
import DTO.Invoice;
import DTO.InvoiceDetail;

import java.sql.SQLException;
import java.util.List;

public class InvoiceBUS {
    @Setter
    private IInvoiceDAO invoiceDAO;
    @Setter
   private IInvoiceDetailDAO invoiceDetailDAO;
    @Setter
    private ProductBUS productBUS;

    public List<Invoice> findAll()  {
        try {
            return invoiceDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Invoice> findAllByType(Invoice.InvoiceType type)  {
        try {
            return invoiceDAO.findAllByType(type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public boolean ValidateInforFilter(InforFilter inforFilter){
        try {
            int total = Integer.parseInt(inforFilter.getTotalFrom());
            total = Integer.parseInt(inforFilter.getTotalTo());
        }catch (Exception e){
            return false;
        }
        //neu nguoi dung nhap ngay vao ma khong dung theo format("yyyy-mm-dd") thi se tra ve false;
        if(!Helper.ValidateDate(inforFilter.getDateFrom()) || !Helper.ValidateDate((inforFilter.getDateTo()))){
            return  false;
        }
//        trong khung tìm kiếm có hai ngày,"từ ngày" và "đến ngày", nếu "đến ngày" mà nhỏ hơn "từ ngày" thì trả về false
        if(!Helper.compareDate(inforFilter.getDateFrom(), inforFilter.getDateTo())) {
            return false;
        }
        if(!inforFilter.getTotalFrom().equals(""))
            if(!Helper.isNumber(inforFilter.getTotalFrom()))
                return false;

        if(!inforFilter.getTotalTo().equals(""))
            if(!Helper.isNumber(inforFilter.getTotalTo()))
                return false;
        if(!inforFilter.getTotalFrom().equals("") && !inforFilter.getTotalTo().equals(""))
            return !(Double.parseDouble(inforFilter.getTotalTo()) < Double.parseDouble(inforFilter.getTotalFrom()));
        return true;
    }


    public List<Invoice> findInvoiceByInforFilter(Invoice.InvoiceType type,InforFilter inforFilter){
        try {
            return invoiceDAO.findInvoiceByInforFilter(type,inforFilter);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteInvoice(Integer integer){
        try {
            invoiceDAO.delete(integer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Invoice findInvoiceById(Integer integer) {
        try {
            return invoiceDAO.findById(integer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public Invoice order(CreateInvoiceInputDTO createInvoiceInputDTO) {
        var newInvoice = Invoice.builder()
                .computerId(createInvoiceInputDTO.getComputerId())
                .createdToAccountId(createInvoiceInputDTO.getAccountId())
                .type(Invoice.InvoiceType.EXPORT)
                .createdAt(new java.util.Date())
                .createdBy(MainUI.getCurrentUser().getId())
                .status(Invoice.Status.WAITING_FOR_ACCEPT)
                .isPaid(false)
                .note(createInvoiceInputDTO.getNote())
                .deletedAt(null)
                .build();

        try {
            var invoice = invoiceDAO.create(newInvoice);
            createInvoiceInputDTO.getInvoiceDetailDTOList().forEach(invoiceDetailDTO -> {
                var product = productBUS.findProductById(invoiceDetailDTO.getProductId());
                if(product.getStock()>0) {
                    if (product.getStock()<invoiceDetailDTO.getQuantity()){
                        throw new RuntimeException("Khong du");
                    }
                    product.setStock(product.getStock()-invoiceDetailDTO.getQuantity());
                    try {
                        productBUS.update(product);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                var newInvoiceDetail = InvoiceDetail.builder()
                        .invoiceId(invoice.getId())
                        .productId(invoiceDetailDTO.getProductId())
                        .quantity(invoiceDetailDTO.getQuantity())
                        .price(product.getPrice())
                        .build();

                try {
                    invoiceDetailDAO.create(newInvoiceDetail);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            return invoice;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Invoice createInvoice(Invoice invoice){
        try {
            return new InvoiceDAOImpl().create(invoice);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Invoice updateInvoice(Invoice invoice){
        try {
            return invoiceDAO.update(invoice);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int countExportInvoiceSellByEmployeeId(int employeeId) {
        try {
            return invoiceDAO.findByEmployeeId(employeeId, Invoice.InvoiceType.EXPORT).size();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
