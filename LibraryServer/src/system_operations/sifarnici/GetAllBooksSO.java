package system_operations.sifarnici;

import domain.Knjiga;
import java.util.List;
import system_operations.AbstractSO;

public class GetAllBooksSO extends AbstractSO {

    private List<Knjiga> books;

    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        books = REPOSITORY.getByCondition(new Knjiga(), "");
    }

    public List<Knjiga> getBooks() {
        return books;
    }
}
