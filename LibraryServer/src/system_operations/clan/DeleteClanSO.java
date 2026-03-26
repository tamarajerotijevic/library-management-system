package system_operations.clan;

import domain.Clan;
import domain.EvidencijaPozajmice;
import exceptions.ServerValidationException;
import java.util.List;
import system_operations.AbstractSO;

public class DeleteClanSO extends AbstractSO {

    @Override
    protected void preconditions(Object arg) throws Exception {
        if (!(arg instanceof Clan clan)) {
            throw new Exception("Poslati objekat nije odgovarajuceg tipa");
        }

        if (clan.getClanID() <= 0) {
            throw new ServerValidationException("ID clana nije ispravan");
        }
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        Clan clan = (Clan) arg;

        List<EvidencijaPozajmice> records = REPOSITORY.getByCondition(
                new EvidencijaPozajmice(),
            "WHERE ep.clanID = " + clan.getClanID()
        );

        if (records != null && !records.isEmpty()) {
            throw new ServerValidationException("Систем не може да обрише члана библиотеке");
        }

        REPOSITORY.delete(clan, "WHERE clanID = " + clan.getClanID());
    }
}
