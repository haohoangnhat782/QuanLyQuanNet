package GUI.Server.Invoice;

import DTO.Invoice;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface reloadInvoice {
    void loadJTable(Invoice.InvoiceType type, List<Invoice> invoices);
}
