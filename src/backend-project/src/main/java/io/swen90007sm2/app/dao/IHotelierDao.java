package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.entity.Hotelier;

import java.util.List;

public interface IHotelierDao extends IBaseDao<Hotelier> {

//    /**
//     * Find total record counts.
//     * @return
//     */
//    int findTotalCount();
//
//    /**
//     * find hoteliers by page
//     * @param start result starts from which row
//     * @param rows total rows needed
//     * @return list
//     */
//    List<Hotelier> findAllByPage(Integer start, Integer rows);
//
//
//
//    /**
//     * insert a new hotelier in db
//     * @param hotelier new Hotelier obj
//     * @return 1 if successed
//     */
//    int insertOne(Hotelier hotelier);
//
//    /**
//     * update Hotelier
//     * @param hotelier new obj
//     * @return num of influenced rows
//     */
//    int updateOne(Hotelier hotelier);
//
//    /**
//     * delete one hotelier by hotelierId
//     * @return rows
//     */
//    int deleteOne(Hotelier hotelier);
//
//    /**
//     * update password cypher
//     * ！！！ Deprecated, do not use it ！！！
//     * @param hotelierId target userId
//     * @param newCypher pwd cypher
//     * @return num of influenced rows
//     */
//    @Deprecated
//    int updatePasswordOne(String hotelierId, String newCypher);
//    /**
//     * Find Hotelier in database by userID
//     * @param userId is business specific Id, not database id, in this case, is userId
//     * @return Hotelier Entity
//     */
//    Hotelier findOneByBusinessId(String userId);
}
