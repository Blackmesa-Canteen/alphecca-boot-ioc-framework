package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.Photo;

import java.util.List;

/**
 * @author xiaotian
 */
public interface IPhotoDao extends IBaseDao<Photo>{

    /**
     * Find total record counts.
     * @return
     */
    int findTotalCount();

    /**
     * find photos by page
     * @param start result starts from which row
     * @param rows total rows needed
     * @return list
     */
    List<Photo> findAllByPage(Integer start, Integer rows);

    /**
     * find Photo metadata object from db
     * @param photoId photoId/photoname
     * @return Photo ORM object
     */
    Photo findOneByBusinessId(String photoId);

    /**
     * insert a new photo into db
     * @param photo photo entity contains photo metadata
     */
    int insertOne(Photo photo);

    /**
     * update one photo
     * @param photo photoEntity
     * @return rows
     */
    @Override
    int updateOne(Photo photo);

    /**
     * delete one photo
     * @return rows
     */
    @Override
    int deleteOne(Photo photo);
}
