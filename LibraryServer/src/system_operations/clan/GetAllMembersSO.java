package system_operations.clan;

import domain.Clan;
import java.util.List;
import system_operations.AbstractSO;

public class GetAllMembersSO extends AbstractSO {

    private List<Clan> members;

    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        members = REPOSITORY.getByCondition(new Clan(), "ORDER BY c.clanID ASC");
    }

    public List<Clan> getMembers() {
        return members;
    }
}
