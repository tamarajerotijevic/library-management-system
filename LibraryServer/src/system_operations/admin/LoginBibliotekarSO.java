package system_operations.admin;

import domain.Bibliotekar;
import exceptions.ServerValidationException;
import java.util.List;
import system_operations.AbstractSO;

public class LoginBibliotekarSO extends AbstractSO {

    private Bibliotekar loggedBibliotekar;

    @Override
    protected void preconditions(Object arg) throws Exception {
        if (!(arg instanceof Bibliotekar bibliotekar)) {
            throw new Exception("Послати објекат није одговарајућег типа");
        }

        if (bibliotekar.getKorisnickoIme() == null || bibliotekar.getKorisnickoIme().isBlank()) {
            throw new ServerValidationException("Корисничко име је обавезно");
        }

        if (bibliotekar.getSifra() == null || bibliotekar.getSifra().isBlank()) {
            throw new ServerValidationException("Лозинка је обавезна");
        }
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        Bibliotekar bibli = (Bibliotekar) arg;

        String whereSection = "WHERE korisnickoIme = '" + bibli.getKorisnickoIme()
                + "' AND sifra = '" + bibli.getSifra() + "'";

        List<Bibliotekar> list = REPOSITORY.getByCondition(new Bibliotekar(), whereSection);
        if (list.isEmpty()) {
            throw new ServerValidationException("Корисничко име и шифра нису исправни");
        }

        loggedBibliotekar = list.get(0);
    }

    public Bibliotekar getLoggedBibliotekar() {
        return loggedBibliotekar;
    }
}
