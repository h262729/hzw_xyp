package com.hzw.xyp.beans.info;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类 - 资讯评论回复
 */
@Entity
@Table(name = "t_info_commont_reply")
public class InfoCommontReply implements Serializable {

    //资讯评论回复表
    private long id;
    //所属资讯id
    private long infoId;
    //所属评论id
    private long infoCommontId;
    //二级以上回复id
    private long parentId;
    //评论回复内容
    private String content;
    //被回复人id
    private long replyUserId;
    //被回复人名称
    private String replyUserName;
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

    @Column(name = "info_commont_id")
    public long getInfoCommontId() {
        return infoCommontId;
    }

    @Column(name = "parent_id")
    public long getParentId() {
        return parentId;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    @Column(name = "reply_user_id")
    public long getReplyUserId() {
        return replyUserId;
    }

    @Column(name = "reply_user_name")
    public String getReplyUserName() {
        return replyUserName;
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

    public void setInfoCommontId(long infoCommontId) {
        this.infoCommontId = infoCommontId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setReplyUserId(long replyUserId) {
        this.replyUserId = replyUserId;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
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
