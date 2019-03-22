package com.hzw.xyp.beans.info;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类 - 资讯
 */
@Entity
@Table(name = "t_info")
public class Info implements Serializable {

    //资讯表
    private long id;
    //资讯标题
    private String title;
    //所属分类id
    private long typeId;
    //拥有标签集ids(:xx:xx:)
    private String labelIds;
    //资讯内容
    private String content;
    //封面图片
    private String imgs;
    //来源
    private String source;
    //是否为管理员创建
    private Integer createIsAdmin;
    //创建人id
    private long createUserId;
    //创建人名称
    private String createUserName;
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

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Column(name = "type_id")
    public long getTypeId() {
        return typeId;
    }

    @Column(name = "label_ids")
    public String getLabelIds() {
        return labelIds;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    @Column(name = "imgs")
    public String getImgs() {
        return imgs;
    }

    @Column(name = "source")
    public String getSource() {
        return source;
    }

    @Column(name = "create_is_admin")
    public Integer getCreateIsAdmin() {
        return createIsAdmin;
    }

    @Column(name = "create_user_id")
    public long getCreateUserId() {
        return createUserId;
    }

    @Column(name = "create_user_name")
    public String getCreateUserName() {
        return createUserName;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public void setLabelIds(String labelIds) {
        this.labelIds = labelIds;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setCreateIsAdmin(Integer createIsAdmin) {
        this.createIsAdmin = createIsAdmin;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
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
