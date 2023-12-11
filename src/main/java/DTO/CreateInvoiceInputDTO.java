package DTO;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CreateInvoiceInputDTO implements Serializable{
    @Serial
    private static final long serialVersionUID = 74634344216L;
    private int computerId;
    private Integer accountId;
    private String note;
    private boolean isUsingBalance;
    private List<InvoiceDetailInputDTO> invoiceDetailDTOList;
}
