package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.dao.impl.DemoDao;

public interface IDemoDao {
    DemoDao getDemoById(Long id);
}
