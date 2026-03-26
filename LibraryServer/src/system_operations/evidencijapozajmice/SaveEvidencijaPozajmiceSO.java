package system_operations.evidencijapozajmice;

import domain.EvidencijaPozajmice;
import domain.State;
import domain.StavkaPozajmice;
import exceptions.ServerValidationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import system_operations.AbstractSO;

public class SaveEvidencijaPozajmiceSO extends AbstractSO {

    @Override
    protected void preconditions(Object arg) throws Exception {
        if (!(arg instanceof EvidencijaPozajmice ev)) {
            throw new Exception("Poslati objekat nije odgovarajuceg tipa");
        }

        if (ev.getEvidencijaPozajmiceID() <= 0) {
            throw new ServerValidationException("ID evidencije nije ispravan");
        }

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

    @Override
    protected void executeOperation(Object arg) throws Exception {
        EvidencijaPozajmice ev = (EvidencijaPozajmice) arg;
        recalculateTotals(ev);

        if (ev.getState() == State.CHANGED) {
            REPOSITORY.update(ev);
        }

        List<StavkaPozajmice> items = ev.getStavkePozajmice();
        deleteMissingItems(ev, items);

        for (StavkaPozajmice sp : items) {
            sp.setEvidencijaPozajmice(ev);
            switch (sp.getState()) {
                case UNCHANGED -> {
                }
                case CREATED -> REPOSITORY.create(sp);
                case CHANGED -> REPOSITORY.update(sp);
                case DELETED -> {
                }
            }
        }
    }

    private void deleteMissingItems(EvidencijaPozajmice ev, List<StavkaPozajmice> incomingItems) throws Exception {
        List<StavkaPozajmice> existingItems = REPOSITORY.getByCondition(new StavkaPozajmice(),
                "WHERE sp.evidencijaPozajmiceID = " + ev.getEvidencijaPozajmiceID());

        Set<Integer> incomingRb = new HashSet<>();
        for (StavkaPozajmice sp : incomingItems) {
            incomingRb.add(sp.getRb());
        }

        for (StavkaPozajmice existing : existingItems) {
            if (!incomingRb.contains(existing.getRb())) {
                String where = "WHERE evidencijaPozajmiceID = " + ev.getEvidencijaPozajmiceID()
                        + " AND rb = " + existing.getRb();
                REPOSITORY.delete(existing, where);
            }
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
}
