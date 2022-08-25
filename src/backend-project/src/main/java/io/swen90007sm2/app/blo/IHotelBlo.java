package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.param.CreateHotelParam;

public interface IHotelBlo {

    /**
     * create a new hotel
     * @param hotelierId userId, owner of the hotel
     * @param createHotelParam request param
     */
    void doCreateHotel(String hotelierId, CreateHotelParam createHotelParam);
}
