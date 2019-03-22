package com.hzw.xyp.dao.admin;

import com.hzw.xyp.base.jpa.BaseRepository;
import com.hzw.xyp.beans.admin.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao extends BaseRepository<Admin, Long> {
}
