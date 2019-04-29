package com.hzw.xyp.beans.info;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 实体类 - 资讯评论点赞/被踩
 */
@Entity
@Table(name = "t_info_comment_evalute")
public class InfoCommentEvalute implements Serializable {

    //资讯评论点赞表
    private long id;
    //所属目标
    private String target;
    //所属目标id
    private long targetId;
    //点赞数量
    private Integer goodNum = 0;
    //被踩数量
    private Integer badNum = 0;
    //有效状态
    private Integer status = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    @Column(name = "target")
    public String getTarget() {
        return target;
    }

    @Column(name = "target_id")
    public long getTargetId() {
        return targetId;
    }

    @Column(name = "good_num")
    public Integer getGoodNum() {
        return goodNum;
    }

    @Column(name = "bad_num")
    public Integer getBadNum() {
        return badNum;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    public void setBadNum(Integer badNum) {
        this.badNum = badNum;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
