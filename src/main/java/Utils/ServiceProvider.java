package Utils;

import DAO.*;
import DAO.Interface.*;
import BUS.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ServiceProvider {
    private static ServiceProvider instance;
    private ServiceProvider(){

    }
    public static ServiceProvider getInstance(){
        if(instance == null){
            instance = new ServiceProvider();
        }
        return instance;
    }
    private Map<Class<?>, Class<?>> serviceImplMap  = new HashMap<>();
    private   Map<Class<?>, Object> serviceInstanceCache = new HashMap<>();
    public <T> ServiceProvider register(Class<T> service, Class<? extends T> impl){
        if (serviceImplMap .containsKey(service))
            return this;
        serviceImplMap .put(service, impl);
        return this;
    }
    public <T> ServiceProvider register(Class<T> service, T instance){
        if (serviceInstanceCache.containsKey(service))
            return this;
        serviceInstanceCache.put(service, instance);
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
                    try {
                        f.setAccessible(true);
                        f.set(impl, serviceInstanceCache.get(f.getType()));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    public <T> T getService(Class<T> service){
        if (!serviceInstanceCache.containsKey(service)) {
            throw new RuntimeException("Ko thấy class " + service.getName());
        }
        return (T) serviceInstanceCache.get(service);
    }
    public static void init(){
        ServiceProvider.getInstance()
                .register(PoolConnection.class, new PoolConnection())
                .register(IInvoiceDetailDAO.class, InvoiceDetailDAOImpl.class)
                .register(IAccountDAO.class, AccountDAOImpl.class)
                .register(IMessageDAO.class, MessageDAOImpl.class)
                .register(IEmployeeDAO.class,EmployeeDAOImpl.class)
                .register(ISessionDAO.class, SessionDAOImpl.class)
                .register(IComputerDAO.class, ComputerDAOImpl.class)
                .register(IComputerUsageDAO.class, ComputerUsageImpl.class)
                .register(AccountBUS.class, AccountBUS.class)
                .register(IAccountDAO.class, AccountDAOImpl.class)
                .register(ComputerBUS.class, ComputerBUS.class)
                .register(SessionBUS.class, SessionBUS.class)
                .register(ComputerUsageBUS.class, ComputerUsageBUS.class)
                .register(MessageBUS.class, MessageBUS.class)
                .register(IInvoiceDAO.class,InvoiceDAOImpl.class)
                .register(InvoiceBUS.class, InvoiceBUS.class)
                .register(IInvoiceDetailDAO.class,InvoiceDetailDAOImpl.class)
                .register(InvoiceDetailBUS.class, InvoiceDetailBUS.class)
                .register(EmployeeBUS.class, EmployeeBUS.class)
                .register(IProductDAO.class, ProductDAOImpl.class)
                .register(ProductBUS.class, ProductBUS.class)
                .build();
    }
}