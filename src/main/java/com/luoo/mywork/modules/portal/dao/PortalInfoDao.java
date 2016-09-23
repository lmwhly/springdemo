package com.luoo.mywork.modules.portal.dao;

import com.luoo.mywork.common.persistence.CrudDao;
import com.luoo.mywork.common.persistence.annotation.MyBatisDao;
import com.luoo.mywork.modules.portal.entity.PortalInfo;

/**
 * @author Luoo
 * @create 2016-09-23 16:18
 */

@MyBatisDao
public interface PortalInfoDao extends CrudDao<PortalInfo> {
}
