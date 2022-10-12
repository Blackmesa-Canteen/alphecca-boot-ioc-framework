package io.swen90007sm2.app.db.resolver;

import com.google.common.base.CaseFormat;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * resolve ResultSet into a java bean
 *
 * @author xiaotian
 */
public class BeanResultSetResolver<T> implements IResultSetResolver<T>{

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanResultSetResolver.class);

    private final Class<T> clazz;

    public BeanResultSetResolver(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    @SuppressWarnings("Unchecked")
    public T resolveResultSet(ResultSet resultSet) throws Exception {
        if (resultSet.next()) {
            // handle Built-in objects, such as Integer for counting results
            if (clazz.getName().equals(Long.class.getName()) || clazz.getName().equals(Integer.class.getName())) {
                return (T) resultSet.getObject(1);
            }
            T beanObject = clazz.getDeclaredConstructor().newInstance();

            // handle transient data
            Set<String> transientFieldNameSet = new HashSet<>();
            for(Field field : clazz.getDeclaredFields()){
                String name = field.getName();
                if (field.isAnnotationPresent(Transient.class)) {
                    transientFieldNameSet.add(name);
                }
            }

            // get class byte metadata
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
            // get all fields
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                // skip transient field name
                if (transientFieldNameSet.contains(descriptor.getName())) {
                    LOGGER.info("skipped transient field name: " + descriptor.getName());
                    continue;
                }

                // change java bean field camel type to database lower underscore type
                String lowerUnderscoreName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, descriptor.getName());

                try{
                    Object resultSetObject = resultSet.getObject(lowerUnderscoreName);
                    if (resultSetObject != null) {
                        descriptor.getWriteMethod().invoke(beanObject, resultSetObject);
                    }
                } catch (SQLException e) {
                    LOGGER.warn("resolve result set exeception: " + e);
                }

            }

            return beanObject;
        }

        return null;
    }
}
