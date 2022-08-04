package io.swen90007sm2.app.model.entity;

import javax.validation.constraints.NotNull;

public class DemoEntity {

    @NotNull
    Long id;
    String name;

    public DemoEntity() {
    }

    public DemoEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
