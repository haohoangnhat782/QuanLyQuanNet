package GUI.Server;

import Io.Server;
import Io.SocketController;
import Utils.Constants;
import Utils.Helper;
import Utils.ServiceProvider;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Helper.initUI();
     ServiceProvider.init();
        var socketServer=  Server.initInstance(Constants.SOCKET_PORT);
        SocketController socketController = new SocketController(socketServer);
        socketController.startListen();
        new LoginGUI();
    }
}
