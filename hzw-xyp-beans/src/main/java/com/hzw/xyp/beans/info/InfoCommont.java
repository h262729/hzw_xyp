package com.hzw.xyp.beans.info;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类 - 资讯评论
 */
@Entity
@Table(name = "t_info_commont")
public class InfoCommont implements Serializable {

    //资讯评论表
    private long id;
    //所属资讯id
    private long infoId;
    //评论内容
    private String content;
    //创建人id
    private long createUserId;
    //创建人名称
    private String createUserName;
    //有效状态
    private Integer status;
    //更新时间
    private Date updateTime;
    //创建时间
    private Date createTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    @Column(name = "info_id")
    public long getInfoId() {
        return infoId;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    @Column(name = "create_user_id")
    public long getCreateUserId() {
        return createUserId;
    }

    @Column(name = "create_user_name")
    public String getCreateUserName() {
        return createUserName;
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

    public void setInfoId(long infoId) {
        this.infoId = infoId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
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
