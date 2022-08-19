package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.app.common.util.TimeUtil;
import io.swen90007sm2.app.dao.IHotelierDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.Hotelier;

import java.util.List;

public class HotelierDao implements IHotelierDao {


    /**
     * Find total record counts.
     *
     * @return
     */
    @Override
    public int findTotalCount() {
        Long totalRows = CRUDTemplate.executeQueryWithOneRes(
                Long.class,
                "SELECT count(*) FROM hotelier");

        return (totalRows != null) ? totalRows.intValue() : 0;
    }

    /**
     * find hoteliers by page
     *
     * @param start result starts from which row
     * @param rows  total rows needed
     * @return list
     */
    @Override
    public List<Hotelier> findAllByPage(Integer start, Integer rows) {
        List<Hotelier> hoteliers = CRUDTemplate.executeQueryWithMultiRes(
                Hotelier.class,
                "SELECT * FROM hotelier OFFSET ? LIMIT ?",
                start,
                rows
        );

        return hoteliers;
    }

    /**
     * insert a new hotelier in db
     *
     * @param hotelier new Hotelier obj
     * @return 1 if successed
     */
    @Override
    public int insertOne(Hotelier hotelier) {
        int row = CRUDTemplate.executeNonQuery(
                "INSERT INTO hotelier (id, user_id, password, description, user_name) values (?, ?, ?, ?, ?)",
                hotelier.getId(),
                hotelier.getUserId(),
                hotelier.getPassword(),
                hotelier.getDescription(),
                hotelier.getUserName()
        );

        return row;
    }

    /**
     * update Hotelier
     *
     * @param hotelier new obj
     * @return num of influenced rows
     */
    @Override
    public int updateOne(Hotelier hotelier) {
        int row = CRUDTemplate.executeNonQuery(
                "UPDATE hotelier SET password = ?, description=?, user_name=?, avatar_url=?, update_time=? WHERE user_id = ?",
                hotelier.getPassword(),
                hotelier.getDescription(),
                hotelier.getUserName(),
                hotelier.getAvatarUrl(),
                new java.sql.Date(TimeUtil.now().getTime()),
                hotelier.getUserId()
        );

        return row;
    }

    /**
     * delete one hotelier by hotelierId
     *
     * @param hotelier
     * @return rows
     */
    @Override
    public int deleteOne(Hotelier hotelier) {
        int row = CRUDTemplate.executeNonQuery(
                "DELETE FROM hotelier WHERE user_id = ?",
                hotelier.getUserId()
        );

        return row;    }

    /**
     * update password cypher
     * ！！！ Deprecated, do not use it ！！！
     *
     * @param hotelierId target userId
     * @param newCypher  pwd cypher
     * @return num of influenced rows
     */
    @Override
    public int updatePasswordOne(String hotelierId, String newCypher) {
        int row = CRUDTemplate.executeNonQuery(
                "UPDATE hotelier SET password=?, update_time=? WHERE user_id = ?",
                newCypher,
                new java.sql.Date(TimeUtil.now().getTime()),
                hotelierId
        );

        return row;
    }

    /**
     * Find Hotelier in database by userID
     *
     * @param userId is business specific Id, not database id, in this case, is userId
     * @return Hotelier Entity
     */
    @Override
    public Hotelier findOneByBusinessId(String userId) {
        Hotelier hotelierBean = CRUDTemplate.executeQueryWithOneRes(
                Hotelier.class,
                "SELECT * FROM hotelier WHERE user_id = ?",
                userId
        );


        return hotelierBean;
    }
}
