package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.Photo;

import java.util.List;

/**
 * @author xiaotian
 */
public interface IPhotoDao {

    /**
     * find Photo metadata object from db
     * @param photoId photoId/photoname
     * @return Photo ORM object
     */
    Photo findPhotoByPhotoId(String photoId);

    /**
     * insert a new photo into db
     * @param photo photo entity contains photo metadata
     */
    void insertPhoto(Photo photo);
}
