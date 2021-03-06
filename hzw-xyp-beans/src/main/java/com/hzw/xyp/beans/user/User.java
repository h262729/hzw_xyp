package com.hzw.xyp.beans.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类 - 普通用户
 */
@Entity
@Table(name = "t_user")
public class User implements Serializable {

    //一般用户表
    private long id;
    //昵称
    private String nickname;
    //密码
    private String pwd;
    //联系电话
    private String mobile;
    //联系邮箱
    private String email;
    //头像
    private String imgs;
    //扩展信息
    private String props;
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

    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    @Column(name = "pwd")
    public String getPwd() {
        return pwd;
    }

    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @Column(name = "imgs")
    public String getImgs() {
        return imgs;
    }

    @Column(name = "props")
    public String getProps() {
        return props;
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public void setProps(String props) {
        this.props = props;
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
