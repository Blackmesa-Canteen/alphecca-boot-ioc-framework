package io.swen90007sm2.app.db.factory;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Component;
import io.swen90007sm2.app.dao.IBaseDao;
import io.swen90007sm2.app.dao.ICustomerDao;
import io.swen90007sm2.app.dao.IPhotoDao;
import io.swen90007sm2.app.model.entity.BaseEntity;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.entity.Photo;

@Component
public class DaoFactory {

    @AutoInjected
    static ICustomerDao customerDao;

    @AutoInjected
    static IPhotoDao photoDao;


    public static IBaseDao<? extends BaseEntity> getDao(Class<?> clazz) {

        if (clazz.equals(Customer.class)) {
            return customerDao;
        } else if (clazz.equals(Photo.class)) {
            return photoDao;
        }
        return null;
    }
}
