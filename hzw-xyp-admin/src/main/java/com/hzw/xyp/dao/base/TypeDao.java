package com.hzw.xyp.dao.base;

import com.hzw.base.jpa.BaseRepository;
import com.hzw.xyp.beans.base.Type;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDao extends BaseRepository<Type, Long> {
}
