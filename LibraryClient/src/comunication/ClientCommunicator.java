package comunication;

import intercomm.Communication;
import intercomm.Request;
import intercomm.Response;
import java.net.Socket;

public class ClientCommunicator {

    private static ClientCommunicator instance;
    private final Socket socket;

    private ClientCommunicator() throws Exception {
        this.socket = new Socket("localhost", 9000);
    }

    public static ClientCommunicator getInstance() throws Exception {
        if (instance == null) {
            instance = new ClientCommunicator();
        }
        return instance;
    }

    public Response sendRequestReceiveResponse(Request request) throws Exception {
        Communication.getInstance().send(socket, request);
        return (Response) Communication.getInstance().receive(socket);
    }
}
