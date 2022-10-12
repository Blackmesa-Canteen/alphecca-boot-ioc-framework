package io.swen90007sm2.app.db.resolver;

import com.google.common.base.CaseFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.Transient;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

            // handle transient data
            Set<String> transientFieldNameSet = new HashSet<>();
            for(Field field : clazz.getDeclaredFields()){
                String name = field.getName();
                if (field.isAnnotationPresent(Transient.class)) {
                    transientFieldNameSet.add(name);
                }
            }

            for (PropertyDescriptor descriptor : propertyDescriptors) {
                // skip transient field name
                if (transientFieldNameSet.contains(descriptor.getName())) {
                    LOGGER.info("skipped transient field name: " + descriptor.getName());
                    continue;
                }

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
