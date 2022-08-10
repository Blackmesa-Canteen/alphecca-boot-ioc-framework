package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.app.dao.IPhotoDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.Photo;

import java.util.List;

@Dao
public class PhotoDao implements IPhotoDao {

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
