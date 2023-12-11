

package DTO;


import lombok.*;

import java.io.Serial;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Product implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID =214560262412L;

    public enum ProductType {
        FOOD,
        DRINK,
        CARD,
        ;

        @Override
        public String toString() {
            return switch (this) {
                case FOOD -> "Đồ ăn";
                case DRINK -> "Đồ uống";
                case CARD -> "Thẻ";
            };
        }
    }


    private Integer id;

    private String name;


    private double price;


    private ProductType type;


    private int stock;

    private String description;


    private String image;


    private Date createdAt = new Date();

    private Date deletedAt;

    private List<InvoiceDetail> invoiceDetails;

    public void setType(Integer productType) {
        this.type = ProductType.values()[productType];
    }
}
