package BUS;

import DAO.Interface.IComputerDAO;
import DAO.Interface.ISessionDAO;
import Io.Server;
import lombok.NoArgsConstructor;
import lombok.Setter;
import DTO.Computer;

import java.sql.SQLException;
import java.util.List;

@NoArgsConstructor
public class ComputerBUS {
    @Setter
    private ISessionDAO sessionDAO;
    @Setter
    private IComputerDAO computerDAO;
    public List<Computer> updateListComputerStatus(List<Computer> computers){

        computers= computers.stream().peek(c->{
            var isConnect = Server.getInstance().getClients().stream().filter(l->l.getMachineId()==c.getId()).findFirst().orElse(null)!=null;
            c.setStatus(isConnect?Computer.ComputerStatus.LOCKED.ordinal():Computer.ComputerStatus.OFF.ordinal());
        }).toList();
        return  computers.stream().peek(c->{
            try {
                c.setStatus((sessionDAO.findByComputerId(c.getId())==null?c.getStatus():Computer.ComputerStatus.USING).ordinal());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }
    public List<Computer> getAllComputers() throws SQLException {
        return computerDAO.findAll();
    }

    public Computer getComputerById(int id) throws SQLException {
        return computerDAO.findById(id);
    }
    public void updateComputer(Computer computer) throws SQLException {
        computerDAO.update(computer);
    }
    public void deleteComputer(int id) throws SQLException {
        computerDAO.delete(id);
    }
    public void addComputer(Computer computer) throws SQLException {
        computerDAO.create(computer);
    }

}
