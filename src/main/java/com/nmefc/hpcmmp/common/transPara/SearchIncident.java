package com.nmefc.hpcmmp.common.transPara;

import java.util.Date;

/**
 * @author:zlyfs date:2019/8/19
 * @description:
 */
public class SearchIncident {
    private Integer id;

    private Date gmtCreateStart;
    private Date gmtCreateEnd;

    private Date gmtModifiedStart;
    private Date gmtModifiedEnd;

    private Boolean isDelete;

    private Integer initiator;

    private Integer category;

    private Byte level;

    private Long assetId;

    private Date startTimeStart;
    private Date startTimeEnd;

    private String cause;

    private String impact;

    private String processes;

    private Date finishTimeStart;
    private Date finishTimeEnd;

    private Integer billState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getGmtCreateStart() {
        return gmtCreateStart;
    }
    public Date getGmtCreateEnd() {
        return gmtCreateEnd;
    }

    public void setGmtCreateStart(Date gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
    }
    public void setGmtCreateEnd(Date gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
    }

    public Date getGmtModifiedStart() {
        return gmtModifiedStart;
    }
    public Date getGmtModifiedEnd() {
        return gmtModifiedEnd;
    }

    public void setGmtModifiedStart(Date gmtModifiedStart) {
        this.gmtModifiedStart = gmtModifiedStart;
    }
    public void setGmtModifiedEnd(Date gmtModifiedEnd) {
        this.gmtModifiedEnd = gmtModifiedEnd;
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

    public Date getStartTimeStart() {
        return startTimeStart;
    }
    public Date getStartTimeEnd() {
        return startTimeEnd;
    }

    public void setStartTimeStart(Date startTimeStart) {
        this.startTimeStart = startTimeStart;
    }
    public void setStartTimeEnd(Date startTimeEnd) {
        this.startTimeEnd = startTimeEnd;
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

    public Date getFinishTimeStart() {
        return finishTimeStart;
    }
    public Date getFinishTimeEnd() {
        return finishTimeEnd;
    }
    public void setFinishTimeStart(Date finishTimeStart) {
        this.finishTimeStart = finishTimeStart;
    }
    public void setFinishTimeEnd(Date finishTimeEnd) {
        this.finishTimeEnd = finishTimeEnd;
    }
    public Integer getBillState() {
        return billState;
    }

    public void setBillState(Integer billState) {
        this.billState = billState;
    }
}
