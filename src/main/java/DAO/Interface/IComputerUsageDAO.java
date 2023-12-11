package DAO.Interface;

import DTO.ComputerUsage;

import java.util.List;

public interface IComputerUsageDAO extends  IDAO<ComputerUsage, Integer> {
    public List<ComputerUsage> findByFilter(DTO.ComputerUsageFilter filter) throws Exception;

}
