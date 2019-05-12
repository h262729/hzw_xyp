package com.hzw.xyp.dao.info;

import com.hzw.base.jpa.BaseRepository;
import com.hzw.xyp.beans.info.Info;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoDao extends BaseRepository<Info, Long> {
}
