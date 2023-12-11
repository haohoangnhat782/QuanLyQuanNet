package DTO;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class InvoiceDetailInputDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 70089216L;
    private int productId;
    private int quantity;
    private Product product;
}
