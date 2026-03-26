package system_operations.evidencijapozajmice;

import domain.EvidencijaPozajmice;
import domain.StavkaPozajmice;
import exceptions.ServerValidationException;
import system_operations.AbstractSO;

public class CreateEvidencijaPozajmiceSO extends AbstractSO {

    @Override
    protected void preconditions(Object arg) throws Exception {
        if (!(arg instanceof EvidencijaPozajmice ev)) {
            throw new Exception("Poslati objekat nije odgovarajuceg tipa");
        }

        validateHeader(ev);
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        EvidencijaPozajmice ev = (EvidencijaPozajmice) arg;
        recalculateTotals(ev);
        REPOSITORY.create(ev);

        int rb = 1;
        for (StavkaPozajmice sp : ev.getStavkePozajmice()) {
            sp.setEvidencijaPozajmice(ev);
            if (sp.getRb() <= 0) {
                sp.setRb(rb++);
            }
            REPOSITORY.create(sp);
        }
    }

    private void recalculateTotals(EvidencijaPozajmice ev) {
        int activeCount = 0;
        double totalFine = 0;

        for (StavkaPozajmice sp : ev.getStavkePozajmice()) {
            sp.azurirajKaznu();
            activeCount++;
            totalFine += sp.getIznosKazne();
        }

        ev.setBrojStavkiIznajmljivanja(activeCount);
        ev.setUkupanIznosKazne(totalFine);
    }

    private void validateHeader(EvidencijaPozajmice ev) throws ServerValidationException {
        if (ev.getDatumIznajmljivanja() == null) {
            throw new ServerValidationException("Datum iznajmljivanja je obavezan");
        }
        if (ev.getBibliotekar() == null || ev.getBibliotekar().getBibliotekarID() <= 0) {
            throw new ServerValidationException("Bibliotekar je obavezan");
        }
        if (ev.getClan() == null || ev.getClan().getClanID() <= 0) {
            throw new ServerValidationException("Clan je obavezan");
        }
        if (ev.getStavkePozajmice() == null || ev.getStavkePozajmice().isEmpty()) {
            throw new ServerValidationException("Evidencija mora imati bar jednu stavku");
        }

        for (StavkaPozajmice sp : ev.getStavkePozajmice()) {
            if (sp.getRokZaVracanje() == null) {
                throw new ServerValidationException("Rok za vracanje je obavezan za svaku stavku");
            }
            if (sp.getRokZaVracanje().before(ev.getDatumIznajmljivanja())) {
                throw new ServerValidationException("Rok za vracanje ne sme biti pre datuma iznajmljivanja");
            }
            if (sp.getDatumVracanja() != null && sp.getDatumVracanja().before(ev.getDatumIznajmljivanja())) {
                throw new ServerValidationException("Datum vracanja ne sme biti pre datuma iznajmljivanja");
            }
        }
    }
}
