package com.nmefc.hpcmmp.entity.dutymanagement;

import java.util.Date;

public class IncidentState {
    private Integer id;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean gmtIsdelete;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getGmtIsdelete() {
        return gmtIsdelete;
    }

    public void setGmtIsdelete(Boolean gmtIsdelete) {
        this.gmtIsdelete = gmtIsdelete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}