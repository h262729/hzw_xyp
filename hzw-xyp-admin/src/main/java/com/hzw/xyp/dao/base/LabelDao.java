package com.hzw.xyp.dao.base;

import com.hzw.base.jpa.BaseRepository;
import com.hzw.xyp.beans.base.Label;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelDao extends BaseRepository<Label, Long> {
}
