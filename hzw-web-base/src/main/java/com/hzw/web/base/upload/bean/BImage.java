package com.hzw.web.base.upload.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类 - 图片管理
 */
@Entity
@Table(name = "b_image")
public class BImage implements Serializable {
    //图片表id
    private String id;
    //所属目标名称
    private String target;
    //字节大小
    private Long size;
    //图片后缀
    private String suffix;
    //默认保存模式(1:本地保存)
    private Integer mode = 1;
    //文件校验md5
    private String md5Code;
    //存储url
    private String url;
    //上传时间
    private Date createTime;
    //所属目标id
    private Long targetId;
    //名称
    private String name;


    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "uuid")  // 主键生成策略
    @Column(name = "id", unique = true, nullable = false, length = 32)
    public String getId() {
        return id;
    }

    @Column(name = "target")
    public String getTarget() {
        return target;
    }

    @Column(name = "size")
    public Long getSize() {
        return size;
    }

    @Column(name = "suffix")
    public String getSuffix() {
        return suffix;
    }

    @Column(name = "mode")
    public Integer getMode() {
        return mode;
    }

    @Column(name = "md5_code")
    public String getMd5Code() {
        return md5Code;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    @Column(name = "target_id")
    public Long getTargetId() {
        return targetId;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public void setMd5Code(String md5Code) {
        this.md5Code = md5Code;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
