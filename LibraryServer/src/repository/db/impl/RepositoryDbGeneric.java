package repository.db.impl;

import domain.GenericEntity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import repository.db.DbConnectionFactory;
import repository.db.DbRepository;

public class RepositoryDbGeneric implements DbRepository<GenericEntity> {

    @Override
    public void create(GenericEntity arg) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();

        String query = "INSERT INTO " + arg.getTableName()
                + " (" + arg.getAttributeNames() + ")"
                + " VALUES (" + arg.getAttributeValues() + ")";

        try (Statement statement = connection.createStatement()) {
            statement.execute(query, Statement.RETURN_GENERATED_KEYS);

            if (arg.shouldAssignGeneratedId()) {
                try (ResultSet rsKey = statement.getGeneratedKeys()) {
                    if (rsKey.next()) {
                        int id = rsKey.getInt(1);
                        arg.setID(id);
                    }
                }
            }
        }
    }

    @Override
    public void update(GenericEntity arg) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();

        String query = "UPDATE " + arg.getTableName()
                + " SET " + arg.setAttributeValues()
                + " WHERE " + arg.getQueryCondition();

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }

    @Override
    public void delete(GenericEntity arg, String whereSection) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        String query = "DELETE FROM " + arg.getTableName() + " " + whereSection;

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }

    @Override
    public List<GenericEntity> getByCondition(GenericEntity arg, String whereSection) throws Exception {
        List<GenericEntity> entities = new LinkedList<>();
        Connection connection = DbConnectionFactory.getInstance().getConnection();

        String query = arg.getSelectAllQuery() + " " + whereSection;

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                entities.add(arg.getEntityFromResultSet(resultSet));
            }
        }

        return entities;
    }
}
