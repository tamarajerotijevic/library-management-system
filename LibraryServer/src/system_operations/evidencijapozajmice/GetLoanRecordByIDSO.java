package system_operations.evidencijapozajmice;

import domain.EvidencijaPozajmice;
import domain.StavkaPozajmice;
import java.util.List;
import system_operations.AbstractSO;

public class GetLoanRecordByIDSO extends AbstractSO {

    private EvidencijaPozajmice record;

    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        int id = (int) arg;
        List<EvidencijaPozajmice> list = REPOSITORY.getByCondition(new EvidencijaPozajmice(), "WHERE ep.evidencijaPozajmiceID = " + id);

        if (list == null || list.isEmpty()) {
            record = null;
            return;
        }

        record = list.get(0);

        List<StavkaPozajmice> items = REPOSITORY.getByCondition(new StavkaPozajmice(),
                "WHERE sp.evidencijaPozajmiceID = " + record.getEvidencijaPozajmiceID());
        double totalFine = 0;
        for (StavkaPozajmice sp : items) {
            sp.setEvidencijaPozajmice(record);
            sp.azurirajKaznu();
            totalFine += sp.getIznosKazne();
        }
        record.setStavkePozajmice(items);
        record.setBrojStavkiIznajmljivanja(items.size());
        record.setUkupanIznosKazne(totalFine);
    }

    public EvidencijaPozajmice getRecord() {
        return record;
    }
}
