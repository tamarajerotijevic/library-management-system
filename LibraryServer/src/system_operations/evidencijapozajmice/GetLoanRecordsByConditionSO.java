package system_operations.evidencijapozajmice;

import domain.EvidencijaPozajmice;
import domain.StavkaPozajmice;
import java.util.LinkedList;
import java.util.List;
import system_operations.AbstractSO;
import util.Utility;

public class GetLoanRecordsByConditionSO extends AbstractSO {

    private List<EvidencijaPozajmice> records;

    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        String where = buildWhere((List<Object>) arg);

        List<EvidencijaPozajmice> headers = REPOSITORY.getByCondition(new EvidencijaPozajmice(), where);
        if (headers == null) {
            records = new LinkedList<>();
            return;
        }

        for (EvidencijaPozajmice ev : headers) {
            List<StavkaPozajmice> items = REPOSITORY.getByCondition(new StavkaPozajmice(),
                    "WHERE sp.evidencijaPozajmiceID = " + ev.getEvidencijaPozajmiceID());
            double totalFine = 0;
            for (StavkaPozajmice sp : items) {
                sp.setEvidencijaPozajmice(ev);
                sp.azurirajKaznu();
                totalFine += sp.getIznosKazne();
            }
            ev.setStavkePozajmice(items);
            ev.setBrojStavkiIznajmljivanja(items.size());
            ev.setUkupanIznosKazne(totalFine);
        }

        records = headers;
    }

    private String buildWhere(List<Object> criteria) {
        if (criteria == null || criteria.isEmpty()) {
            return "";
        }

        String evidencijaTerm = criteria.size() > 0 && criteria.get(0) instanceof String
                ? (String) criteria.get(0) : "";
        String bibliotekarTerm = criteria.size() > 1 && criteria.get(1) instanceof String
                ? (String) criteria.get(1) : "";
        String clanTerm = criteria.size() > 2 && criteria.get(2) instanceof String
                ? (String) criteria.get(2) : "";
        String knjigaTerm = criteria.size() > 3 && criteria.get(3) instanceof String
                ? (String) criteria.get(3) : "";

        List<String> conditions = new LinkedList<>();

        if (!Utility.isStringNullOrBlank(evidencijaTerm)) {
            conditions.add("ep.evidencijaPozajmiceID = " + Integer.parseInt(evidencijaTerm));
        }

        if (!Utility.isStringNullOrBlank(clanTerm)) {
            conditions.add("(c.ime LIKE '%" + clanTerm + "%' OR c.prezime LIKE '%" + clanTerm + "%')");
        }

        if (!Utility.isStringNullOrBlank(bibliotekarTerm)) {
            conditions.add("(b.ime LIKE '%" + bibliotekarTerm + "%' OR b.prezime LIKE '%" + bibliotekarTerm + "%')");
        }

        if (!Utility.isStringNullOrBlank(knjigaTerm)) {
            conditions.add("EXISTS ("
                    + "SELECT 1 FROM stavkapozajmice sp "
                    + "JOIN knjiga k ON sp.knjigaID = k.knjigaID "
                    + "WHERE sp.evidencijaPozajmiceID = ep.evidencijaPozajmiceID "
                    + "AND k.isbn LIKE '%" + knjigaTerm + "%'"
                    + ")");
        }

        if (conditions.isEmpty()) {
            return "";
        }

        return "WHERE " + String.join(" AND ", conditions);
    }

    public List<EvidencijaPozajmice> getRecords() {
        return records;
    }
}
