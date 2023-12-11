import DAO.AccountDAOImpl;
import DAO.Interface.IAccountDAO;
import BUS.AccountBUS;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class TestDependencyInjection {
    public static void main(String[] args) throws ParseException, SQLException {
        ServiceBuilder.getInstance()
                .register(AccountBUS.class, AccountBUS.class)
                .register(IAccountDAO.class, AccountDAOImpl.class)
                .build();
        var accountService = ServiceBuilder.getInstance().getService(AccountBUS.class);
    }
}
class ServiceBuilder{
    private static ServiceBuilder instance;
    private ServiceBuilder(){
    }
    public static ServiceBuilder getInstance(){
        if(instance == null){
            instance = new ServiceBuilder();
        }
        return instance;
    }
    private Map<Class<?>, Class<?>> serviceImplMap  = new HashMap<>();
    private   Map<Class<?>, Object> serviceInstanceCache = new HashMap<>();
    public <T> ServiceBuilder register(Class<T> service, Class<? extends T> impl){
        if (serviceImplMap .containsKey(service))
           return this;
        serviceImplMap .put(service, impl);
        return this;
    }
    public void build(){

        for (Class<?> iService: serviceImplMap .keySet()){
            Class<?> impl = serviceImplMap .get(iService);
            try {
                Constructor<?> constructor = impl.getConstructor();
                Object instance = constructor.newInstance();
                serviceInstanceCache.put(iService, instance);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
        }

        for (Class<?> iService: serviceInstanceCache.keySet()){
            var impl = serviceInstanceCache.get(iService);
            Field[] fields = impl.getClass().getDeclaredFields();
            Arrays.stream(fields).forEach(f->{
                if(serviceImplMap .containsKey(f.getType())){
                   String setterMethodName = "set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
                     try {
                          Method setterMethod = impl.getClass().getMethod(setterMethodName, f.getType());
                            setterMethod.invoke(impl, serviceInstanceCache.get(f.getType()));
                     } catch (Exception e) {
                          e.printStackTrace();
                     }
                }else {
                    throw new RuntimeException("Dependency not found for " + f.getType().getName());
                }
            });
        }
    }
    public <T> T getService(Class<T> service){
        if (!serviceInstanceCache.containsKey(service)) {
            throw new RuntimeException("Service not found for " + service.getName());
        }
        return (T) serviceInstanceCache.get(service);
    }

}
