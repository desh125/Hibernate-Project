package lk.ijse.orm.hibernate_project.dao;

public interface CrudDAO<T, Type> extends SuperDAO {

    Type Save(T t);

    T Get(Type type);

    void Update(T t);

    void Delete(T t);

}