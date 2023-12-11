package DTO;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Session implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 432430624324L;

    public Session(Session session){
        this.id = session.id;
        this.totalTime = session.totalTime;
        this.usedTime = session.usedTime;
        this.usedCost = session.usedCost;
        this.serviceCost = session.serviceCost;
        this.startTime = session.startTime;
        this.prepaidAmount = session.prepaidAmount;
        this.usingBy = session.usingBy;
        this.usingByAccount = session.usingByAccount;
        this.computerID = session.computerID;
        this.usingComputer = session.usingComputer;

    }

    private Integer id;

    private int totalTime=0; // seconds

    private int usedTime = 0; // seconds


    private double usedCost = 0; // VND


    private int serviceCost = 0; // VND

    private Date startTime = new Date();

    private double prepaidAmount = 0; // VND
    private Integer usingBy = null;
    private Account usingByAccount;

    private Integer computerID = null;
    private Computer usingComputer;


}
