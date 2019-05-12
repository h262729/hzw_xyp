package com.hzw.web.base.upload.dao;

import com.hzw.base.jpa.BaseRepository;
import com.hzw.web.base.upload.bean.BImage;
import org.springframework.stereotype.Repository;

@Repository
public interface BImageDao extends BaseRepository<BImage, String> {
}
