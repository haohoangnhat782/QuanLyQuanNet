package DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Computer implements Serializable {
    @Serial
    private static final long serialVersionUID = 746559035L;
    public enum ComputerType {
        Vip,
        Normal,
        ;

        @Override
        public String toString() {
            return switch (this) {
                case Vip -> "Máy VIP";
                case Normal -> "Máy thường";
            };
        }
    }

    public enum ComputerStatus {
        MAINTAINING,
        LOCKED,
        OFF,
        USING,
        ;

        @Override
        public String toString() {
            return switch (this) {
                case MAINTAINING -> "Đang bảo trì";
                case LOCKED -> "Đang khóa";
                case OFF -> "Đang tắt";
                case USING -> "Đang sử dụng";
            };
        }
    }


    private int id;


    private String name;
    private double price; // giá tiền trên 1 giờ
    private ComputerType type;
    private ComputerStatus status = ComputerStatus.OFF;
    private Date createdAt = new Date();
    private Date deletedAt = null;

    private List<ComputerUsage> computerUsages;
    private List<Invoice> invoices;
    private Session currentSession;
    public void setStatus(Integer status) {
        this.status = ComputerStatus.values()[status];
    }
    public void setType(Integer type) {
        this.type = ComputerType.values()[type];
    }
    public void setType(String type) {
        switch (type) {
            case "Máy VIP" -> this.type = ComputerType.Vip;
            case "Máy thường" -> this.type = ComputerType.Normal;
        }
    }
}
