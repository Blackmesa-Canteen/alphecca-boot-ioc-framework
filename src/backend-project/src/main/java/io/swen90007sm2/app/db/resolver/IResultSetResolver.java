package io.swen90007sm2.app.db.resolver;

import java.sql.ResultSet;

/**
 * resolve Result Set into Java been
 * @author xiaotian
 */
public interface IResultSetResolver<T> {

    /**
     * resolve a resultSet into a specific java bean type
     * @param resultSet sql result set
     * @return Bean or List of bean
     * @throws Exception
     */
    T resolveResultSet(ResultSet resultSet) throws Exception;
}
