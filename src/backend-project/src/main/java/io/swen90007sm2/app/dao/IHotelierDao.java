package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.entity.Hotelier;

public interface IHotelierDao extends IBaseDao<Hotelier> {

    Hotelier findOneByBusinessId(String userId);

    int insertOne(Hotelier hotelier);
}
