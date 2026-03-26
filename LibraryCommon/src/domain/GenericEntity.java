package domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface GenericEntity {

    String getTableName();

    String getAttributeNames();

    String getAttributeValues() throws Exception;

    default boolean shouldAssignGeneratedId() {
        return true;
    }

    default void setID(int ID) throws Exception {
    }

    default int getID() throws Exception {
        return 0;
    }

    String setAttributeValues() throws Exception;

    default String getSelectAllQuery() {
        return "SELECT * from " + getTableName();
    }

    GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException;

    State getState();

    String getQueryCondition() throws Exception;
}
