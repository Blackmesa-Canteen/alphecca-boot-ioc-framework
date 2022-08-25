package io.swen90007sm2.app.db.bean;

/**
 * helper bean to perform batch operations in database CRUD template
 */
public class BatchBean {

    private String sql;
    private Object[] params;

    public BatchBean() {
    }

    public BatchBean(String sql, Object... params) {
        this.sql = sql;
        this.params = params;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
