package system_operations.clan;

import domain.Clan;
import java.util.LinkedList;
import java.util.List;
import system_operations.AbstractSO;
import util.Utility;

public class GetMembersByConditionSO extends AbstractSO {

    private List<Clan> members;

    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        List<Object> criteria = (List<Object>) arg;
        String memberTerm = criteria != null && criteria.size() > 0 && criteria.get(0) instanceof String
                ? ((String) criteria.get(0)).trim() : "";
        String placeTerm = criteria != null && criteria.size() > 1 && criteria.get(1) instanceof String
                ? ((String) criteria.get(1)).trim() : "";

        List<String> conditions = new LinkedList<>();

        if (!Utility.isStringNullOrBlank(memberTerm)) {
            StringBuilder memberCondition = new StringBuilder();
            memberCondition.append("(c.ime LIKE '%").append(memberTerm).append("%' ");
            memberCondition.append("OR c.prezime LIKE '%").append(memberTerm).append("%'");

            try {
                int clanId = Integer.parseInt(memberTerm);
                memberCondition.append(" OR c.clanID = ").append(clanId);
            } catch (NumberFormatException ex) {
            }

            memberCondition.append(")");
            conditions.add(memberCondition.toString());
        }

        if (!Utility.isStringNullOrBlank(placeTerm)) {
            conditions.add("m.naziv LIKE '%" + placeTerm + "%' ");
        }

        String where = conditions.isEmpty()
                ? ""
                : "WHERE " + String.join(" AND ", conditions);

        where += " ORDER BY c.clanID ASC";

        List<Clan> result = REPOSITORY.getByCondition(new Clan(), where);
        members = result == null ? new LinkedList<>() : result;
    }

    public List<Clan> getMembers() {
        return members;
    }
}
