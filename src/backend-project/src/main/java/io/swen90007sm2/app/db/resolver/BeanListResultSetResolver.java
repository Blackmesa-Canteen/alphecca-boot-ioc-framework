package io.swen90007sm2.app.db.resolver;

import com.google.common.base.CaseFormat;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * parse resultset into list of beans
 *
 * @author xiaotian
 */
public class BeanListResultSetResolver<T> implements IResultSetResolver<List<T>>{
    private final Class<T> clazz;

    public BeanListResultSetResolver(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<T> resolveResultSet(ResultSet resultSet) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
        // get all fields
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        List<T> list = new ArrayList<>();
        while (resultSet.next()) {
            T beanObject = clazz.getDeclaredConstructor().newInstance();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                // change java bean field camel type to database lower underscore type
                String lowerUnderscoreName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, descriptor.getName());
                Object resultObject = resultSet.getObject(lowerUnderscoreName);
                descriptor.getWriteMethod().invoke(beanObject, resultObject);
            }

            list.add(beanObject);
        }

        return list;
    }
}
