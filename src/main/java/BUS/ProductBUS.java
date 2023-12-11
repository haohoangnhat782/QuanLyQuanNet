package BUS;

import DAO.Interface.IProductDAO;
import lombok.Setter;
import DTO.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductBUS {
    @Setter
    private IProductDAO productDAO;

    public ProductBUS() {
    }

    public List<Product> findAllProduct(){
        try {
            return this.productDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  List<Product> findProductByType(Product.ProductType type) {
        try {
            return this.productDAO.filterByTypeProduct(type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Product findProductById(int id){
        try {
         return   productDAO.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void create(Product product) throws SQLException {
        this.productDAO.create(product);
    }

    public void update(Product product) throws SQLException {
        this.productDAO.update(product);
    }

    public void delete(int integer) throws SQLException {
        this.productDAO.delete(integer);
    }

    public Product findById(int id) throws SQLException {
        return this.productDAO.findById(id);
    }

    public Product findByName(String name) throws SQLException {
        return this.productDAO.findByName(name);
    }

    public List<Product> findAll() throws SQLException {
        return this.productDAO.findAll();
    }

    public List<Product> filterByTypeProduct(Product.ProductType type) throws SQLException {
        return this.productDAO.filterByTypeProduct(type);
    }

    public List<Product> findListByName(String name) throws SQLException{
        return this.productDAO.findListByName(name);
    }
}
