package Payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LoginPayload implements Serializable {
    public static final long serialVersionUID = 32432455L;
    private String username;
    private String password;


}