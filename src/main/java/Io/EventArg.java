package Io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
class EventArg implements Serializable {
    @Serial
    private static final long serialVersionUID = 343402343577L;
    private String eventType;
    private Serializable arg;
}