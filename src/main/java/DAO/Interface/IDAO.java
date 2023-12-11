package DAO.Interface;

import DTO.Invoice;

import java.sql.SQLException;
import java.util.List;

public interface IDAO<T, ID> {
    T create(T t) throws SQLException;
    T update(T t) throws SQLException ;
    boolean delete(ID id) throws SQLException ;
    T findById(ID id)throws SQLException ;
    List<T> findAll()throws SQLException;
}

