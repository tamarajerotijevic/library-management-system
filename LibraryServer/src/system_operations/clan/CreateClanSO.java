package system_operations.clan;

import domain.Clan;
import exceptions.ServerValidationException;
import java.util.List;
import system_operations.AbstractSO;

public class CreateClanSO extends AbstractSO {

    @Override
    protected void preconditions(Object arg) throws Exception {
        if (!(arg instanceof Clan clan)) {
            throw new Exception("Poslati objekat nije odgovarajuceg tipa");
        }

        validateClan(clan);
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        Clan clan = (Clan) arg;
        clan.setClanID(nextClanId());
        REPOSITORY.create(clan);
    }

    private int nextClanId() throws Exception {
        List<Clan> members = REPOSITORY.getByCondition(new Clan(), "");
        int maxId = 0;
        for (Clan member : members) {
            if (member.getClanID() > maxId) {
                maxId = member.getClanID();
            }
        }
        return maxId + 1;
    }

    private void validateClan(Clan clan) throws ServerValidationException {
        if (clan.getIme() == null || clan.getIme().isBlank()) {
            throw new ServerValidationException("Ime clana je obavezno");
        }
        if (clan.getPrezime() == null || clan.getPrezime().isBlank()) {
            throw new ServerValidationException("Prezime clana je obavezno");
        }
        if (clan.getEmail() == null || clan.getEmail().isBlank() || !clan.getEmail().contains("@")) {
            throw new ServerValidationException("Email clana nije ispravan");
        }
        if (clan.getMesto() == null || clan.getMesto().getMestoID() <= 0) {
            throw new ServerValidationException("Mesto clana je obavezno");
        }
    }
}
