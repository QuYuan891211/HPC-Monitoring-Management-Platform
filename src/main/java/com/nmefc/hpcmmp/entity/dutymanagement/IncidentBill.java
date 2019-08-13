package com.nmefc.hpcmmp.entity.dutymanagement;

import com.nmefc.hpcmmp.workflowEngine.entity.Bill;

import java.util.Date;

public class IncidentBill extends Bill{
    private Integer id;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isDelete;

    private Integer initiator;

    private Integer category;

    private Byte level;

    private Long assetId;

    private Date startTime;

    private String cause;

    private String impact;

    private String processes;

    private Date finishTime;

    private Integer billState;

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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getInitiator() {
        return initiator;
    }

    public void setInitiator(Integer initiator) {
        this.initiator = initiator;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause == null ? null : cause.trim();
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact == null ? null : impact.trim();
    }

    public String getProcesses() {
        return processes;
    }

    public void setProcesses(String processes) {
        this.processes = processes == null ? null : processes.trim();
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getBillState() {
        return billState;
    }

    public void setBillState(Integer billState) {
        this.billState = billState;
    }
}