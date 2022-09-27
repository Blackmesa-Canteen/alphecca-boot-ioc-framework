package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.alpheccaboot.exception.NotImplementedException;
import io.swen90007sm2.app.common.util.TimeUtil;
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
    public List<Photo> findAllByPage(Integer start, Integer rows) {
        List<Photo> photos = CRUDTemplate.executeQueryWithMultiRes(
                Photo.class,
                "SELECT * FROM photo OFFSET ? LIMIT ?",
                start,
                rows
        );

        return photos;
    }

    @Override
    public Photo findOneByBusinessId(String photoId) {
        return CRUDTemplate.executeQueryWithOneRes(
                Photo.class,
                "SELECT * FROM photo WHERE photo_id = ?",
                photoId
        );
    }

    @Override
    public int insertOne(Photo photo) {
        int rows = CRUDTemplate.executeNonQuery(
                "INSERT INTO photo (id, photo_id, user_id, description, photo_url) values (?, ?, ?, ?, ?)",
                photo.getId(),
                photo.getPhotoId(),
                photo.getUserId(),
                photo.getDescription(),
                photo.getPhotoUrl()
        );

        return rows;
    }

    @Override
    public int updateOne(Photo photo) {
        int row = CRUDTemplate.executeNonQuery(
                "UPDATE photo SET description=?, photo_url=?, update_time=? WHERE photo_id = ?",
                photo.getDescription(),
                photo.getPhotoUrl(),
                new java.sql.Date(TimeUtil.now().getTime()),
                photo.getPhotoId()
        );

        return row;
    }

    @Override
    public void throwConcurrencyException(Photo entity) {
        throw new NotImplementedException();
    }

    @Override
    public int deleteOne(Photo photo) {
        int row = CRUDTemplate.executeNonQuery(
                "DELETE FROM photo WHERE photo_id = ?",
                photo.getPhotoId()
        );

        return row;
    }
}
