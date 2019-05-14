package com.nmefc.hpcmmp.ActivitiTest;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: QuYuan
 * @Description: 自定义类
 * @Date: Created in 21:07 2019/5/14
 * @Modified By:
 */
public class PayBillBean implements Serializable{
    private Integer cost;
    private Date date;
    private String name;

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
