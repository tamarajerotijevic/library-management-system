package system_operations.sifarnici;

import domain.Bibliotekar;
import java.util.List;
import system_operations.AbstractSO;

public class GetAllBibliotekariSO extends AbstractSO {

    private List<Bibliotekar> bibliotekari;

    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        bibliotekari = REPOSITORY.getByCondition(new Bibliotekar(), "");
    }

    public List<Bibliotekar> getBibliotekari() {
        return bibliotekari;
    }
}
