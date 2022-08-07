package io.swen90007sm2.app.db.resolver;

import com.google.common.base.CaseFormat;

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
                // change java bean field camel type to database lower underscore type
                String lowerUnderscoreName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, descriptor.getName());

                Object resultSetObject = resultSet.getObject(lowerUnderscoreName);
                descriptor.getWriteMethod().invoke(beanObject, resultSetObject);
            }

            return beanObject;
        }

        return null;
    }
}
