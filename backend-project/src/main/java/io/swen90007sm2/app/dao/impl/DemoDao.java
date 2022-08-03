package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.app.dao.IDemoDao;

@Dao
public class DemoDao implements IDemoDao {

    @Override
    public DemoDao getDemoById(Long id) {
        return new DemoDao();
    }
}
