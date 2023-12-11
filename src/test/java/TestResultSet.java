

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Person{
    private int id;
    private String name ="";

    public Person() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}


public class TestResultSet {
    public static void main(String[] args) throws ClassNotFoundException {
        Connection connection = null;
        //forclass SQLServerDriver
//        Class.forName("com.mysql.jdbc.Driver");
        try {
            connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/test","root","");
            //person is a table in database
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from person");
            List<Person> list = toList(resultSet,Person.class);
        }catch (Exception exception){
            exception.printStackTrace();
            System.exit(0);
        }
    }
    public static <T extends Person>List<T> toList(ResultSet resultSet,Class<T> clazz) throws SQLException {
        Field[] fields = clazz.getDeclaredFields();
        List<T> list = new ArrayList<T>();
        while (resultSet.next()){
            try {
                T t = clazz.getConstructor().newInstance();
                for (Field field : fields) {
                    String setMethodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    Method setMethod = clazz.getMethod(setMethodName, field.getType());
                    setMethod.invoke(t, resultSet.getObject(field.getName()));
                }
                list.add(t);
            }catch (Exception exception){
                exception.printStackTrace();
                System.exit(0);
            }
        }
        return list;
    }
}