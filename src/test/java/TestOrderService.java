import DTO.CreateInvoiceInputDTO;
import DTO.InvoiceDetailInputDTO;
import GUI.Server.MainUI;
import Utils.ServiceProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import BUS.AccountBUS;
import BUS.EmployeeBUS;
import BUS.InvoiceBUS;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TestOrderService {
    static InvoiceBUS invoiceBUS;
    @BeforeAll
    public static void init(){
        ServiceProvider.init();
         invoiceBUS = ServiceProvider.getInstance().getService(InvoiceBUS.class);
        var accountService = ServiceProvider.getInstance().getService(AccountBUS.class);
        var account =accountService.login("employee1", "employee1");
        var employee = ServiceProvider.getInstance().getService(EmployeeBUS.class).findEmployeeByAccountID(account.getId());
        MainUI.setCurrentUser(employee);
    }
    @Test
    @DisplayName("Order a product")
    public void testOrderProduct(){
        assertDoesNotThrow(()->{
            var orderInput = CreateInvoiceInputDTO.builder()
                    .accountId(11)
                    .computerId(2)
                    .note("test")
                    .invoiceDetailDTOList(
                            List.of(
                                    InvoiceDetailInputDTO.builder().productId(9).quantity(1).build(),
                                    InvoiceDetailInputDTO.builder().productId(7).quantity(1).build()
                            )
                    ).build();
            var invoice = invoiceBUS.order(orderInput);
        });
    }
}
