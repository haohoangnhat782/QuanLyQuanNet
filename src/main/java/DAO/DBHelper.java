package DAO;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {
    private static DBHelper instance;

    private static DBHelper getInstance() throws SQLException {
        instance = instance == null|| instance.connection.isClosed() ? new DBHelper() : instance;
        if (instance.connection.isClosed()){
            instance = new DBHelper();
        }
        return instance;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().connection;
    }

    private static final String SERVER = "localhost";
    private static final String DATABASE_NAME = "NetCF";
    private static final String USER_NAME = "sa";

    private static final String PASSWORD = "123"; // ok r á

    private  Connection connection = null;


    public DBHelper() throws SQLException  {
        String url = String
                .format("jdbc:sqlserver://%s;databaseName=%s;trustServerCertificate=true;encrypt=true;", SERVER, DATABASE_NAME);
        connection = DriverManager.getConnection(url, USER_NAME, PASSWORD);
    }
    public static String getConnectionString(){
        return  String
                .format("jdbc:sqlserver://%s;databaseName=%s;trustServerCertificate=true;encrypt=true;", SERVER, DATABASE_NAME);
    }

    public static <T>List<T> toList(ResultSet resultSet, Class<T> clazz) throws SQLException {//Class<T> clazz là truyền vào một class cụ thể, trong đó clazz là một lớp cụ thể
        Field[] fields = clazz.getDeclaredFields();//dung de lay tat ca cac thuoc tinh cua lop clazz truyen vao
        List<T> list = new ArrayList<T>();//tạo một mảng đọngo chứa các đói tượng cụ thể của class đó
        while (resultSet.next()){//duyệt từng dòng trong cở sở dữ liệu
            try {
                T t = clazz.getConstructor().newInstance();//tạo một đối tượng cụ thể của class truyền vào
                for (Field field : fields) {//duyệt từng thuộc tính của class
                    if (field.getName().equals("serialVersionUID")) {
                        continue;
                    }
                   try {
                       var value = resultSet.getObject(field.getName());
                       field.setAccessible(true);
                       if(value == null){
                           continue;
                       }
                       if (field.getType().isEnum()){
                            int ordinal = (int) value;
                            value = field.getType().getEnumConstants()[ordinal];
                            field.set(t,value);
                            continue;
                       }
                          field.set(t,value);
                   }catch (Exception ignored) {
                       if (field.getType().isEnum()){
                           ignored.printStackTrace();
                       }
                   }
                }
                list.add(t);
            }catch (Exception exception){
                exception.printStackTrace();
                System.exit(0);
            }
        }
        resultSet.close();
        return list;
    }

}
