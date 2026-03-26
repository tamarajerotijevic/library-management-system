package controller;

import domain.Bibliotekar;
import domain.Clan;
import domain.EvidencijaPozajmice;
import domain.Knjiga;
import domain.Mesto;
import domain.Smena;
import java.util.List;
import system_operations.admin.LoginBibliotekarSO;
import system_operations.clan.CreateClanSO;
import system_operations.clan.DeleteClanSO;
import system_operations.clan.GetAllMembersSO;
import system_operations.clan.GetMemberByIDSO;
import system_operations.clan.GetMembersByConditionSO;
import system_operations.clan.SaveClanSO;
import system_operations.evidencijapozajmice.CreateEvidencijaPozajmiceSO;
import system_operations.evidencijapozajmice.GetLoanRecordByIDSO;
import system_operations.evidencijapozajmice.GetLoanRecordsByConditionSO;
import system_operations.evidencijapozajmice.SaveEvidencijaPozajmiceSO;
import system_operations.sifarnici.GetAllBibliotekariSO;
import system_operations.sifarnici.GetAllBooksSO;
import system_operations.sifarnici.GetAllPlacesSO;
import system_operations.smena.CreateSmenaSO;

public class ServerController {

    private static ServerController instance;

    private ServerController() {
    }

    public static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }

    public Bibliotekar loginBibliotekar(Bibliotekar bibliotekar) throws Exception {
        LoginBibliotekarSO so = new LoginBibliotekarSO();
        so.execute(bibliotekar);
        return so.getLoggedBibliotekar();
    }

    public void createClan(Clan clan) throws Exception {
        CreateClanSO so = new CreateClanSO();
        so.execute(clan);
    }

    public List<Clan> getAllMembers() throws Exception {
        GetAllMembersSO so = new GetAllMembersSO();
        so.execute(null);
        return so.getMembers();
    }

    public List<Clan> getMembersByCondition(List<Object> criteria) throws Exception {
        GetMembersByConditionSO so = new GetMembersByConditionSO();
        so.execute(criteria);
        return so.getMembers();
    }

    public Clan getMemberByID(int id) throws Exception {
        GetMemberByIDSO so = new GetMemberByIDSO();
        so.execute(id);
        return so.getMember();
    }

    public void saveClan(Clan clan) throws Exception {
        SaveClanSO so = new SaveClanSO();
        so.execute(clan);
    }

    public void deleteClan(Clan clan) throws Exception {
        DeleteClanSO so = new DeleteClanSO();
        so.execute(clan);
    }

    public void createEvidencijaPozajmice(EvidencijaPozajmice evidencija) throws Exception {
        CreateEvidencijaPozajmiceSO so = new CreateEvidencijaPozajmiceSO();
        so.execute(evidencija);
    }

    public List<EvidencijaPozajmice> getLoanRecordsByCondition(List<Object> criteria) throws Exception {
        GetLoanRecordsByConditionSO so = new GetLoanRecordsByConditionSO();
        so.execute(criteria);
        return so.getRecords();
    }

    public EvidencijaPozajmice getLoanRecordByID(int id) throws Exception {
        GetLoanRecordByIDSO so = new GetLoanRecordByIDSO();
        so.execute(id);
        return so.getRecord();
    }

    public void saveEvidencijaPozajmice(EvidencijaPozajmice evidencija) throws Exception {
        SaveEvidencijaPozajmiceSO so = new SaveEvidencijaPozajmiceSO();
        so.execute(evidencija);
    }

    public void createSmena(Smena smena) throws Exception {
        CreateSmenaSO so = new CreateSmenaSO();
        so.execute(smena);
    }

    public List<Knjiga> getAllBooks() throws Exception {
        GetAllBooksSO so = new GetAllBooksSO();
        so.execute(null);
        return so.getBooks();
    }

    public List<Mesto> getAllPlaces() throws Exception {
        GetAllPlacesSO so = new GetAllPlacesSO();
        so.execute(null);
        return so.getPlaces();
    }

    public List<Bibliotekar> getAllBibliotekari() throws Exception {
        GetAllBibliotekariSO so = new GetAllBibliotekariSO();
        so.execute(null);
        return so.getBibliotekari();
    }
}
