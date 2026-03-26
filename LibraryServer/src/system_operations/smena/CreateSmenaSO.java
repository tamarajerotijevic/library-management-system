package system_operations.smena;

import domain.Smena;
import exceptions.ServerValidationException;
import system_operations.AbstractSO;

public class CreateSmenaSO extends AbstractSO {

    @Override
    protected void preconditions(Object arg) throws Exception {
        if (!(arg instanceof Smena smena)) {
            throw new Exception("Poslati objekat nije odgovarajuceg tipa");
        }

        if (smena.getNaziv() == null || smena.getNaziv().isBlank()) {
            throw new ServerValidationException("Naziv smene je obavezan");
        }

        if (smena.getVremeOd() == null || smena.getVremeDo() == null) {
            throw new ServerValidationException("Vreme od/do je obavezno");
        }

        if (!smena.getVremeOd().before(smena.getVremeDo())) {
            throw new ServerValidationException("VremeOd mora biti pre VremeDo");
        }
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        REPOSITORY.create((Smena) arg);
    }
}
