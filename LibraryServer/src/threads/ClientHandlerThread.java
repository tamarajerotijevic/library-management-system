package threads;

import controller.ServerController;
import domain.Bibliotekar;
import domain.Clan;
import domain.EvidencijaPozajmice;
import domain.Smena;
import intercomm.Communication;
import intercomm.Operation;
import intercomm.Request;
import intercomm.Response;
import java.net.Socket;
import java.util.List;

public class ClientHandlerThread extends Thread {

    private final Socket socket;
    private final int clientNumber;
    private final ServerThread server;
    private Bibliotekar loggedBibliotekar;

    public ClientHandlerThread(ServerThread server, Socket socket, int clientNumber) {
        this.server = server;
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        System.out.println("Client handler thread " + clientNumber + " started");

        while (!socket.isClosed()) {
            try {
                Request request = (Request) Communication.getInstance().receive(socket);
                Response response = handleRequest(request);
                Communication.getInstance().send(socket, response);
            } catch (Exception ex) {
                System.out.println("Client " + clientNumber + " disconnected");
                logout();
            }
        }
    }

    private Response handleRequest(Request request) {
        Response response = new Response();
        response.setOperation(request.getOperation());

        Object result = null;

        try {
            Operation operation = request.getOperation();
            Object arg = request.getArgument();

            switch (operation) {
                case LOGIN_BIBLIOTEKAR -> {
                    loggedBibliotekar = ServerController.getInstance().loginBibliotekar((Bibliotekar) arg);
                    result = loggedBibliotekar;
                    server.login(this);
                }
                case CREATE_CLAN -> ServerController.getInstance().createClan((Clan) arg);
                case GET_ALL_MEMBERS -> result = ServerController.getInstance().getAllMembers();
                case GET_MEMBERS_BY_CONDITION -> result = ServerController.getInstance().getMembersByCondition((List<Object>) arg);
                case GET_MEMBER_BY_ID -> result = ServerController.getInstance().getMemberByID((int) arg);
                case SAVE_CLAN -> ServerController.getInstance().saveClan((Clan) arg);
                case DELETE_CLAN -> ServerController.getInstance().deleteClan((Clan) arg);

                case CREATE_EVIDENCIJA_POZAJMICE -> ServerController.getInstance().createEvidencijaPozajmice((EvidencijaPozajmice) arg);
                case GET_LOAN_RECORDS_BY_CONDITION -> result = ServerController.getInstance().getLoanRecordsByCondition((List<Object>) arg);
                case GET_LOAN_RECORD_BY_ID -> result = ServerController.getInstance().getLoanRecordByID((int) arg);
                case SAVE_EVIDENCIJA_POZAJMICE -> ServerController.getInstance().saveEvidencijaPozajmice((EvidencijaPozajmice) arg);

                case CREATE_SMENA -> ServerController.getInstance().createSmena((Smena) arg);

                case GET_ALL_BOOKS -> result = ServerController.getInstance().getAllBooks();
                case GET_ALL_PLACES -> result = ServerController.getInstance().getAllPlaces();
                case GET_ALL_BIBLIOTEKARI -> result = ServerController.getInstance().getAllBibliotekari();
                default -> throw new IllegalStateException("Nepoznata operacija: " + operation);
            }
        } catch (Exception ex) {
            response.setException(ex);
        }

        response.setResult(result);
        return response;
    }

    public void logout() {
        try {
            server.logout(this);
            socket.close();
        } catch (Exception e) {
            System.out.println("Logout error: " + e.getMessage());
        }
    }

    public Bibliotekar getLoggedBibliotekar() {
        return loggedBibliotekar;
    }
}
