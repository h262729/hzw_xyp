package com.hzw.web.base.upload.service;

import com.alibaba.fastjson.JSONObject;
import com.hzw.base.exception.BusinessException;
import com.hzw.base.tools.StringTools;
import com.hzw.base.tools.others.EncryptUtil;
import com.hzw.web.base.upload.bean.BImage;
import com.hzw.web.base.upload.dao.BImageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 图片上传处理
 */
@Service
@Transactional(readOnly = true)
public class BImageService {
    private Logger logger = LoggerFactory.getLogger(BImageService.class);
    // 允许上传类型
    private static String[] allowUploadTypes = new String[]{"image/jpeg", "image/png"};
    /**
     * 图片上传
     * @param file  上传图片
     * @param target    所属目标对象
     * @param targetId  所属目标对象id
     * @param mode  文件存储模式 默认1为本地存储
     */
    @Transactional
    public JSONObject upload(MultipartFile file, String target, Long targetId, int mode) throws Exception {
        logger.info("上传图片处理开始, target > {}, targetId > {}", target, targetId);
        logger.info("target > " + target);
        logger.info("targetId > " + targetId);
        logger.info("file > " + file);
        logger.info("file > " + file.getOriginalFilename());
        logger.info("file > " + file.getName());
        logger.info("file > " + file.getContentType());
        logger.info("file > " + file.getSize());
        // 判断
        if(file == null){
            throw new BusinessException("图片上传失败,上传文件不存在！");
        }
        if(!this.isAllowUploadType(file.getContentType())){
            throw new BusinessException("图片上传失败,不支持该文件类型上传！");
        }

        // 获取文件md5, 用于查数据库判断该文件是否已上传过的
        // 防止文件的重复上传
        byte[] imgBytes = file.getBytes();
        String imgMd5 = EncryptUtil.getMD5(imgBytes);
        logger.info("imgMd5 > {}", imgMd5);

        BImage bean = dao.getByProperties("md5_code", imgMd5);
        if(bean == null){ // 文件不存在
            // 文件存储
            String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1); // 文件后缀
            String randomFileName = UUID.randomUUID().toString().replace("-", "") + "." + fileSuffix; // UUID随机生成文件名
            String storageFolderUrl = "images/" + target + "/" + targetId ; // 存储文件夹路径
            String storageFileUrl = storageFolderUrl + "/" + randomFileName;    // 存储文件路径
            if(mode == 1){ // 本地存储
                this.storageToLocal(file, storageFolderUrl, storageFileUrl);
            }else{ // 默认本地存储
                mode = 1;
                this.storageToLocal(file, storageFolderUrl, storageFileUrl);
            }

            // 保存新的上传文件数据
            bean = new BImage();
            bean.setMd5Code(imgMd5);
            bean.setMode(mode);
            bean.setName(file.getOriginalFilename());
            bean.setSuffix(fileSuffix);
            bean.setSize(file.getSize());
            bean.setUrl(storageFileUrl);
            bean.setTarget(target);
            bean.setTargetId(targetId);
            dao.saveAndUpdate(bean);
        }

        JSONObject result = new JSONObject();
        result.put("name", bean.getName()); // 文件名称
        result.put("url", bean.getUrl());  // 存储路径
        result.put("mode", bean.getMode()); // 文件存储模式
        return result;
    }

    /**
     * 判断是否为允许上传类型
     * @param type
     * @return
     */
    private boolean isAllowUploadType(String type){
        if(StringTools.isEmpty(type)){
            throw new BusinessException("未知文件上传类型");
        }
        for(String allowType : allowUploadTypes){
            if(allowType.equals(type)){
                return true;
            }
        }
        return false;
    }

    /**
     * 本地存储
     * @param file 上传的文件
     * @param folderUrl 存储文件的上级文件夹路径
     * @param fileUrl   存储文件路径
     */
    private void storageToLocal(MultipartFile file, String folderUrl, String fileUrl) throws IOException {
        // 1. 判断存储文件夹是否存在，不存在就创建
        File storageFolder = new File(this.uploadBaseUrl + folderUrl);
        if(!storageFolder.exists()){
            storageFolder.mkdirs();
        }
        // 2. 将上传文件移动到存储文件中
        File storageFile = new File(this.uploadBaseUrl + fileUrl);
        file.transferTo(storageFile);
    }

    @Autowired
    private BImageDao dao;

    @Value("${hzw.upload.base-url}")
    private String uploadBaseUrl;
}
