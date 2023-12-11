package DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboboxItem {
    public ComboboxItem(int id,String value){
        this.id = id;
        this.value = value;
    }
    private int id;
    private String value;
    private Invoice.Status statusInvoice;
}
