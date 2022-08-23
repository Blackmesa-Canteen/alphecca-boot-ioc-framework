package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.Hotel;
import io.swen90007sm2.app.model.entity.Room;

public interface IRoomDao extends IBaseDao<Room>{

    /**
     * Find room by roomId
     * @param roomId is business specific Id, not database id
     * @return Customer Entity
     */
    Room findOneByBusinessId(String roomId);
}
