package system_operations.clan;

import domain.Clan;
import exceptions.ServerValidationException;
import system_operations.AbstractSO;

public class SaveClanSO extends AbstractSO {

    @Override
    protected void preconditions(Object arg) throws Exception {
        if (!(arg instanceof Clan clan)) {
            throw new Exception("Poslati objekat nije odgovarajuceg tipa");
        }

        if (clan.getClanID() <= 0) {
            throw new ServerValidationException("ID clana nije ispravan");
        }

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

    @Override
    protected void executeOperation(Object arg) throws Exception {
        REPOSITORY.update((Clan) arg);
    }
}
