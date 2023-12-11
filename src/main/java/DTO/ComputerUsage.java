package DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ComputerUsage {

    private int id;
    private Integer usedByAccountId = null;
    private Account usedBy;
    private Integer computerID;
    private Computer computer;
    private boolean isEmployeeUsing;

    private Date createdAt;
    private Date endAt;
    private double totalMoney;
}
