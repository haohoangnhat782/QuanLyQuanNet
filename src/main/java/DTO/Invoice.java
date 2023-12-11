package DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//import java.lang.reflect.Field;

import java.io.Serial;
import java.io.Serializable;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice implements Serializable {
    @Serial
    private static final long serialVersionUID = 264603467L;

    public Invoice(int createdToAccountId, Account createdToAccount, int computerId, Computer createdToComputer, Double total, Date createdAt,Status status,boolean isPaid, int createdBy, Employee createdByEmployee, InvoiceType type) {
        this.createdToAccountId = createdToAccountId;
        this.createdToAccount = createdToAccount;
        this.computerId = computerId;
        this.createdToComputer = createdToComputer;
        this.total = total;
        this.deletedAt = createdAt;
        this.status = status;
        this.isPaid = isPaid;
        this.createdBy = createdBy;
        this.createdByEmployee = createdByEmployee;
        this.type = type;
    }

    public enum InvoiceType {
          IMPORT,
            EXPORT,
    ;

        @Override
        public String toString() {
            return switch (this) {
                case IMPORT -> "Hoá đơn nhập hàng";
                case EXPORT -> "Hoá đơn bán hàng";
            };
        }
    }
    public enum Status {
        WAITING_FOR_ACCEPT,
        ACCEPTED,
        REJECTED,
        DONE

        ;
        @Override
        public String toString() {
            return switch (this) {
                case WAITING_FOR_ACCEPT -> "Chờ duyệt";
                case ACCEPTED -> "Đã duyệt";
                case REJECTED -> "Đã từ chối";
                case DONE -> "Đã hoàn thành";
            };
        }
    }

    private int id;
    private Integer createdToAccountId = null   ;

    private Account createdToAccount;
    private Integer computerId = null;
    private Computer createdToComputer;

    private double total = 0.0f;
    private Date createdAt = new Date();
   
    private Status status = Status.WAITING_FOR_ACCEPT;
    private boolean isPaid = false;
    private int createdBy;
    private Employee createdByEmployee;

   
    private InvoiceType type;
    private Date deletedAt;
    private String note;
    private List<InvoiceDetail> invoiceDetails;
    public String explainIsPaid(){

        return this.isPaid?"Đã thanh toán":"Chưa thanh toán";
    }
    public void setStatus(Integer status) {
        switch (status) {
            case 1 -> this.status = Status.WAITING_FOR_ACCEPT;
            case 2 -> this.status = Status.ACCEPTED;
            case 4 -> this.status = Status.REJECTED;
            case 3 -> this.status = Status.DONE;
        }
    }

    public void setIsPaid(boolean paid) {
        isPaid = paid;
    }
    public void setType(Integer type) {
        switch (type) {
            case 1 -> this.type = InvoiceType.IMPORT;
            case 2 -> this.type = InvoiceType.EXPORT;
        }
    }

}
