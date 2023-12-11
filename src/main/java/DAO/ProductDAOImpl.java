package DAO;

import DAO.Interface.IProductDAO;
import DTO.Product;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ProductDAOImpl extends BaseDAO implements IProductDAO {

    @Override
    public Product create(Product product) throws SQLException {
        var preprapedStament = DBHelper.
                getConnection().
                prepareStatement("INSERT INTO Product(name, price, type, stock, description, image, createdAt, deletedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preprapedStament.setString(1, product.getName());
        preprapedStament.setDouble(2,product.getPrice());
        preprapedStament.setInt(3,product.getType().ordinal());
        preprapedStament.setInt(4,product.getStock());
        preprapedStament.setString(5, product.getDescription());
        preprapedStament.setString(6,product.getImage());
        preprapedStament.setDate(7,new java.sql.Date(new java.util.Date().getTime()));
        preprapedStament.setDate(8,null);
        var result = preprapedStament.executeUpdate();
        if (result > 0) {
            var resultSet = preprapedStament.getGeneratedKeys();
            if (resultSet.next()) {
                var newId = resultSet.getInt(1);
                return this.findById(newId);
            }
        }
        return null;
    }

    @Override
    public Product update(Product product) throws SQLException {
        var preparedStatement = this.prepareStatement("UPDATE product SET " +
                " name = ?, " +
                " price = ?, " +
                " type = ?, " +
                " stock = ?, " +
                " description = ?, " +
                " image = ?, " +
                " createdAt = ? " +
                " WHERE id = ? ");
        preparedStatement.setString(1,product.getName());
        preparedStatement.setDouble(2,product.getPrice());
        preparedStatement.setInt(3,product.getType().ordinal());
        preparedStatement.setInt(4,product.getStock());
        preparedStatement.setString(5,product.getDescription());
        preparedStatement.setString(6,product.getImage());
        preparedStatement.setDate(7,new java.sql.Date(product.getCreatedAt().getTime()));
        preparedStatement.setInt(8,product.getId());
        var result = preparedStatement.executeUpdate();
        preparedStatement.close();
        return result > 0 ? this.findById(product.getId()) : null;
    }

    @Override
    public boolean delete(Integer integer) throws SQLException {
        var preparedStatement = this.prepareStatement("UPDATE product SET deletedAt = ? WHERE id = ?");
        preparedStatement.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
        preparedStatement.setInt(2,integer);
        var result = preparedStatement.executeUpdate();
        preparedStatement.close();
        return result > 0;
    }

    @Override
    public Product findById(Integer integer) throws SQLException {
        var preparedStatement = this.prepareStatement("SELECT * FROM Product p WHERE p.id = ? and p.deletedAt is null");
        preparedStatement.setInt(1,integer);
        var resultSet = preparedStatement.executeQuery();
        var products = DBHelper.toList(resultSet,Product.class);
        preparedStatement.close();
        return products.size() > 0 ? products.get(0) : null;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        var statement = this.createStatement();
        var resultSet = statement.executeQuery("SELECT * FROM product p WHERE p.deletedAt is null");
        var products = DBHelper.toList(resultSet,Product.class);
        statement.close();
        return products;
    }

    @Override
    public Product findByName(String name) throws SQLException {
        var statement = this.prepareStatement("SELECT * FROM product p WHERE p.name = ? and p.deletedAt is null");
        statement.setString(1,name);
        var resultSet = statement.executeQuery();
        var products = DBHelper.toList(resultSet, Product.class);
        return products.size() > 0 ? products.get(0) : null;
    }

    @Override
    public List<Product> filterByTypeProduct(Product.ProductType type) throws SQLException {
        var statement = this.createStatement();
        var resultSet = statement.executeQuery("SELECT * FROM Product p WHERE p.type = " + type.ordinal() + " and p.deletedAt is null");
        var products = DBHelper.toList(resultSet,Product.class);
        statement.close();
        return products;
    }

    @Override
    public List<Product> findListByName(String name) throws SQLException {
        var statement = this.createStatement();
        var resultSet = statement.executeQuery("SELECT * FROM Product p WHERE p.name LIKE N'%"+ name +"%'AND p.deletedAt is null");
        var products = DBHelper.toList(resultSet,Product.class);
        statement.close();
        return products;
    }
}
