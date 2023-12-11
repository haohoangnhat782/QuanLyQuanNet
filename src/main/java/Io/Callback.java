package Io;

import java.io.Serializable;
import java.sql.SQLException;

@FunctionalInterface
public interface Callback {
    void invoke(Socket socket,Serializable arg) ;
}
