package controller;

import comunication.ClientCommunicator;
import domain.Bibliotekar;
import domain.Clan;
import domain.EvidencijaPozajmice;
import domain.Knjiga;
import domain.Mesto;
import domain.Smena;
import intercomm.Operation;
import intercomm.Request;
import intercomm.Response;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;

public class ClientController {

    private static ClientController instance;

    private ClientController() {
    }

    public static ClientController getInstance() {
        if (instance == null) {
            instance = new ClientController();
        }
        return instance;
    }

    private Object sendObjectReceiveResponse(Object argument, Operation operation) throws Exception {
        try {
            Request request = new Request(argument, operation);
            Response response = ClientCommunicator.getInstance().sendRequestReceiveResponse(request);

            if (response.getException() != null) {
                throw response.getException();
            }

            return response.getResult();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Сервер није доступан", "Грешка", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            return null;
        }
    }

    public Bibliotekar loginBibliotekar(Bibliotekar bibliotekar) throws Exception {
        return (Bibliotekar) sendObjectReceiveResponse(bibliotekar, Operation.LOGIN_BIBLIOTEKAR);
    }

    public void createClan(Clan clan) throws Exception {
        sendObjectReceiveResponse(clan, Operation.CREATE_CLAN);
    }

    public List<Clan> getAllMembers() throws Exception {
        return (List<Clan>) sendObjectReceiveResponse(null, Operation.GET_ALL_MEMBERS);
    }

    public List<Clan> getMembersByCondition(List<Object> criteria) throws Exception {
        return (List<Clan>) sendObjectReceiveResponse(criteria, Operation.GET_MEMBERS_BY_CONDITION);
    }

    public Clan getMemberByID(int id) throws Exception {
        return (Clan) sendObjectReceiveResponse(id, Operation.GET_MEMBER_BY_ID);
    }

    public void saveClan(Clan clan) throws Exception {
        sendObjectReceiveResponse(clan, Operation.SAVE_CLAN);
    }

    public void deleteClan(Clan clan) throws Exception {
        sendObjectReceiveResponse(clan, Operation.DELETE_CLAN);
    }

    public void createEvidencijaPozajmice(EvidencijaPozajmice evidencija) throws Exception {
        sendObjectReceiveResponse(evidencija, Operation.CREATE_EVIDENCIJA_POZAJMICE);
    }

    public List<EvidencijaPozajmice> getLoanRecordsByCondition(List<Object> criteria) throws Exception {
        return (List<EvidencijaPozajmice>) sendObjectReceiveResponse(criteria, Operation.GET_LOAN_RECORDS_BY_CONDITION);
    }

    public EvidencijaPozajmice getLoanRecordByID(int id) throws Exception {
        return (EvidencijaPozajmice) sendObjectReceiveResponse(id, Operation.GET_LOAN_RECORD_BY_ID);
    }

    public void saveEvidencijaPozajmice(EvidencijaPozajmice evidencija) throws Exception {
        sendObjectReceiveResponse(evidencija, Operation.SAVE_EVIDENCIJA_POZAJMICE);
    }

    public void createSmena(Smena smena) throws Exception {
        sendObjectReceiveResponse(smena, Operation.CREATE_SMENA);
    }

    public List<Knjiga> getAllBooks() throws Exception {
        return (List<Knjiga>) sendObjectReceiveResponse(null, Operation.GET_ALL_BOOKS);
    }

    public List<Mesto> getAllPlaces() throws Exception {
        return (List<Mesto>) sendObjectReceiveResponse(null, Operation.GET_ALL_PLACES);
    }

    public List<Bibliotekar> getAllBibliotekari() throws Exception {
        return (List<Bibliotekar>) sendObjectReceiveResponse(null, Operation.GET_ALL_BIBLIOTEKARI);
    }
}
