package threads;

import constants.ServerConstants;
import forms.ServerForm;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class ServerThread extends Thread {

    private final ServerSocket serverSocket;
    private final List<ClientHandlerThread> clients;
    private final List<ClientHandlerThread> logged;
    private final ServerForm serverForm;
    private int clientNumber;

    public ServerThread() throws Exception {
        this(null);
    }

    public ServerThread(ServerForm serverForm) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream(ServerConstants.SERVER_CONFIG_FILE_PATH));

        int port = Integer.parseInt(properties.getProperty(ServerConstants.SERVER_CONFIG_PORT));
        serverSocket = new ServerSocket(port);
        clients = new LinkedList<>();
        logged = new LinkedList<>();
        this.serverForm = serverForm;
        clientNumber = 0;
    }

    @Override
    public void run() {
        System.out.println("Server started, waiting clients...");
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                ClientHandlerThread ch = new ClientHandlerThread(this, socket, clientNumber++);
                clients.add(ch);
                ch.start();
            }
        } catch (Exception ex) {
            System.out.println("Server stopped: " + ex.getMessage());
        }
    }

    public void stopServer() throws Exception {
        for (ClientHandlerThread ch : new LinkedList<>(clients)) {
            ch.logout();
        }
        serverSocket.close();
    }

    public void login(ClientHandlerThread ch) {
        if (!logged.contains(ch)) {
            logged.add(ch);
            System.out.println("Logged in: " + ch.getLoggedBibliotekar());
            if (serverForm != null) {
                serverForm.addBibliotekarToTable(ch.getLoggedBibliotekar());
            }
        }
    }

    public void logout(ClientHandlerThread ch) {
        clients.remove(ch);
        logged.remove(ch);
        if (serverForm != null) {
            serverForm.removeBibliotekarFromTable(ch.getLoggedBibliotekar());
        }
    }
}
