package Io;

import BUS.ProductBUS;
import DTO.Product;
import Utils.Helper;
import Utils.ServiceProvider;
import lombok.Getter;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class Server extends ServerSocket {
    @Getter
    private final LinkedList<Io.Socket> clients = new LinkedList<>();
    private final Map<String, LinkedList<Callback>> eventHandlers = new java.util.HashMap<>();
    private final LinkedList<Callback> onConnection = new LinkedList<>();
    private final LinkedList<Callback> onDisconnection = new LinkedList<>();

    protected Server(SocketImpl impl) {
        super(impl);
    }

    public Server() throws IOException {
    }
    public void removeClient(int computerId){
        clients.removeIf(c->c.getMachineId()==computerId);
    }
    public void emitSelf(String eventType, Serializable data) {
        for (var callback : eventHandlers.get(eventType)) {
            callback.invoke(null, data);
        }
    }

    public Server(int port) throws IOException {
        super(port);
    }

    private static Server instance;

    public static Server initInstance(int port) throws IOException {
        instance = new Server(port);
        return instance;
    }

    public static Server getInstance() {
        if (instance == null) {
            throw new RuntimeException("Server is not initialized");
        }
        return instance;
    }

    public Server(int port, int backlog) throws IOException {
        super(port, backlog);
    }

    public void on(String eventType, Callback callback) {
        if (eventType.equals("onConnection")) {
            onConnection.add(callback);
            return;
        }
        if (eventType.equals("onDisconnection")) {
            onDisconnection.add(callback);
            return;
        }
        if (!eventHandlers.containsKey(eventType)) {
            eventHandlers.put(eventType, new LinkedList<Callback>());

        }
        eventHandlers.get(eventType).add(callback);
        for (var socket : clients) {
            socket.on(eventType, callback);
        }
    }

    public Server(int port, int backlog, InetAddress bindAddr) throws IOException {
        super(port, backlog, bindAddr);
    }

    public void listen() throws IOException {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Io.Socket client = new Io.Socket(accept());
                    clients.add(client);


                    for (Callback callback : onConnection) {
                        callback.invoke(client, client);
                    }
                    for (String eventType : eventHandlers.keySet()) {
                        for (Callback callback : eventHandlers.get(eventType)) {
                            client.on(eventType, callback);
                        }
                    }
                    for (Callback callback : onDisconnection) {
                        client.on("onDisconnection", callback);
                    }
                    client.on("identify", (t, arg) -> {
                        client.setMachineId((int) arg);
                        Helper.showSystemNoitification("Máy "+client.getMachineId()+" đã kết nối!", "", TrayIcon.MessageType.INFO);
                        ArrayList<Product> listProduct = null;
                        try {
                            listProduct = new ArrayList<>(ServiceProvider.getInstance().getService(ProductBUS.class).findAll());
                            client.emit("listProduct", listProduct);

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void emit(String eventType, Serializable arg) {
        for (Io.Socket client : clients) {
            client.emit(eventType, arg);
        }
    }

}
