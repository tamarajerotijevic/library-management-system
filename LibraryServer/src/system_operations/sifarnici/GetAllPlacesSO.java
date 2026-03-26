package system_operations.sifarnici;

import domain.Mesto;
import java.util.List;
import system_operations.AbstractSO;

public class GetAllPlacesSO extends AbstractSO {

    private List<Mesto> places;

    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        places = REPOSITORY.getByCondition(new Mesto(), "");
    }

    public List<Mesto> getPlaces() {
        return places;
    }
}
