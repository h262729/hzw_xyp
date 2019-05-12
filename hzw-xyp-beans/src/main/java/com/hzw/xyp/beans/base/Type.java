package com.hzw.xyp.beans.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类 - 分类
 */
@Entity
@Table(name = "t_type")
public class Type implements Serializable {

    //分类表
    private long id;
    //父级id
    private long parentId;
    //分类名称
    private String name;
    //排序
    private Integer seq = 0;
    //有效状态
    private Integer status = 1;
    //更新时间
    private Date updateTime;
    //创建时间
    private Date createTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    @Column(name = "parent_id")
    public long getParentId() {
        return parentId;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "seq")
    public Integer getSeq() {
        return seq;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
