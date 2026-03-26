package repository;

import java.util.List;

public interface Repository<T> {

    void create(T arg) throws Exception;

    void update(T arg) throws Exception;

    void delete(T arg, String whereSection) throws Exception;

    List<T> getByCondition(T arg, String condition) throws Exception;
}
