package DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account implements java.io.Serializable {
    public static Account getInstance() {
        return new Account();
    }
    @Serial
    private static final long serialVersionUID = 67566435324L;
    public enum Role {
        ADMIN,
        MANAGER,
        EMPLOYEE,
        USER;

        @Override
        public String toString() {
            return switch (this) {
                case ADMIN -> "Admin";
                case MANAGER -> "Quản lý";
                case EMPLOYEE -> "Nhân viên";
                case USER -> "Khách hàng";
            };
        }
        public boolean isGreaterThan(Role role){
            return role.ordinal() > this.ordinal();
        }
        public boolean isLessThan(Role role){
            return role.ordinal()< this.ordinal();
        }
    }

    private int id;


    private String username;
    private String password;

    private Role role = Role.USER;
    private double balance = 0;
    private java.util.Date createdAt = new java.util.Date();
    private java.util.Date deletedAt;

    private List<ComputerUsage> usingHistory;
    private List<Invoice> invoices;
    private Session currentSession = null;
    private Employee employee;

    public void setRole(Integer role) {
        this.role = switch (role) {
            case 0 -> Role.ADMIN;
            case 1 -> Role.MANAGER;
            case 2 -> Role.EMPLOYEE;
            default -> Role.USER;
        };
    }

}
