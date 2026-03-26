package system_operations;

import repository.Repository;
import repository.db.DbRepository;
import repository.db.impl.RepositoryDbGeneric;

public abstract class AbstractSO {

    protected final Repository REPOSITORY = new RepositoryDbGeneric();

    public final void execute(Object arg) throws Exception {
        try {
            preconditions(arg);
            startTransaction();
            executeOperation(arg);
            commitTransaction();
        } catch (Exception ex) {
            rollbackTransaction();
            throw ex;
        } finally {
            endTransaction();
        }
    }

    protected abstract void preconditions(Object arg) throws Exception;

    protected abstract void executeOperation(Object arg) throws Exception;

    private void startTransaction() throws Exception {
        ((DbRepository) REPOSITORY).connect();
    }

    private void commitTransaction() throws Exception {
        ((DbRepository) REPOSITORY).commit();
    }

    private void rollbackTransaction() throws Exception {
        ((DbRepository) REPOSITORY).rollback();
    }

    private void endTransaction() throws Exception {
        ((DbRepository) REPOSITORY).disconnect();
    }
}
