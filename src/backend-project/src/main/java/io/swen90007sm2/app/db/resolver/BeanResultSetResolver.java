package io.swen90007sm2.app.db.resolver;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.ResultSet;

/**
 * resolve ResultSet into a java bean
 *
 * @author xiaotian
 */
public class BeanResultSetResolver<T> implements IResultSetResolver<T>{

    private final Class<T> clazz;

    public BeanResultSetResolver(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T resolveResultSet(ResultSet resultSet) throws Exception {
        if (resultSet.next()) {
            T beanObject = clazz.getDeclaredConstructor().newInstance();
            // get class byte metadata
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
            // get all fields
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                Object resultSetObject = resultSet.getObject(descriptor.getName());
                descriptor.getWriteMethod().invoke(beanObject, resultSetObject);
            }

            return beanObject;
        }

        return null;
    }
}
