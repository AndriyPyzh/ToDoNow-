package andriypyzh.dao.Implementation;

import java.sql.SQLException;
import java.util.List;

public abstract class GenericDao<T> {
    //create
    abstract void add(T entity);


    //read
    abstract T getById(int id);
    abstract T getByName(String name);


    //update
    abstract void update(T entity);


    //delete
    abstract void removeById(int id);
}
