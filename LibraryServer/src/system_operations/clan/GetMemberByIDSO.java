package system_operations.clan;

import domain.Clan;
import java.util.List;
import system_operations.AbstractSO;

public class GetMemberByIDSO extends AbstractSO {

    private Clan member;

    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        int id = (int) arg;
        List<Clan> list = REPOSITORY.getByCondition(new Clan(), "WHERE c.clanID = " + id);
        member = list == null || list.isEmpty() ? null : list.get(0);
    }

    public Clan getMember() {
        return member;
    }
}
