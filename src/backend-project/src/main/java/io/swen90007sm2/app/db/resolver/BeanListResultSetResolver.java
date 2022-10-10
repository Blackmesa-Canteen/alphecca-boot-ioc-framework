package io.swen90007sm2.app.db.resolver;

import com.google.common.base.CaseFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * parse resultset into list of beans
 *
 * @author xiaotian
 */
public class BeanListResultSetResolver<T> implements IResultSetResolver<List<T>>{

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanListResultSetResolver.class);
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
                try {
                    Object resultObject = resultSet.getObject(lowerUnderscoreName);
                    descriptor.getWriteMethod().invoke(beanObject, resultObject);
                } catch (SQLException e) {
                    LOGGER.warn("resolve result set exeception: " + e);
                }

            }

            list.add(beanObject);
        }

        return list;
    }
}
