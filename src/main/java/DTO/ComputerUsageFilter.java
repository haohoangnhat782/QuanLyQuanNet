package DTO;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComputerUsageFilter {
    private Integer computerID = null;
    private Integer usedByAccountId = null;
    private String sortBy = " createdAt desc ";

    private Date startFrom = null;
    private Date startTo = null;
    private Boolean isEmployeeUsing = false;
}
