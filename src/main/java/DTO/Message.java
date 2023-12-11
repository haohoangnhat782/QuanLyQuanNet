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
public class Message {
    public enum FROM {
        SERVER,
        CLIENT,
    }
    private Integer id = null;
    private int sessionId ;
    private Session session;
    private String content;
    private FROM fromType;
    private Date createdAt = new Date();
    public void setFromType(Integer fromType) {
        this.fromType = FROM.values()[fromType];
    }
  
}
