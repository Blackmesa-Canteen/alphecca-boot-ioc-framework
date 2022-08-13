package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.app.dao.IPhotoDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.Photo;

import java.util.List;

@Dao
public class PhotoDao implements IPhotoDao {

    @Override
    public int findTotalCount() {
        Long totalRows = CRUDTemplate.executeQueryWithOneRes(
                Long.class,
                "SELECT count(*) FROM photo");

        return (totalRows != null) ? totalRows.intValue() : 0;
    }

    @Override
    public List<Photo> findPhotosByPage(Integer start, Integer rows) {
        List<Photo> photos = CRUDTemplate.executeQueryWithMultiRes(
                Photo.class,
                "SELECT * FROM photo OFFSET ? LIMIT ?",
                start,
                rows
        );

        return photos;
    }

    @Override
    public Photo findPhotoByPhotoId(String photoId) {
        return CRUDTemplate.executeQueryWithOneRes(
                Photo.class,
                "SELECT * FROM photo WHERE photo_id = ?",
                photoId
        );
    }

    @Override
    public void insertPhoto(Photo photo) {
        CRUDTemplate.executeNonQuery(
                "INSERT INTO photo (photo_id, user_id, description, photo_url) values (?, ?, ?, ?)",
                photo.getPhotoId(),
                photo.getUserId(),
                photo.getDescription(),
                photo.getPhotoUrl()
        );
    }
}
