package com.hzw.xyp.beans.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类 - 标签
 */
@Entity
@Table(name = "t_label")
public class Label implements Serializable {

    //标签表
    private long id;
    //所属分类id
    private long typeId = -1;
    //标签名称
    private String name;
    //是否为管理员创建
    private Integer createIsAdmin = 0;
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

    @Column(name = "type_id")
    public long getTypeId() {
        return typeId;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "create_is_admin")
    public Integer getCreateIsAdmin() {
        return createIsAdmin;
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

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreateIsAdmin(Integer createIsAdmin) {
        this.createIsAdmin = createIsAdmin;
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
