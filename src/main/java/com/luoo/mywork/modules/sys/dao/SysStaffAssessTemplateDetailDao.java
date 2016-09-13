/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.luoo.mywork.modules.sys.dao;

import com.luoo.mywork.common.persistence.CrudDao;
import com.luoo.mywork.common.persistence.annotation.MyBatisDao;
import com.luoo.mywork.modules.sys.entity.SysStaffAssessTemplateDetail;

import java.util.List;


/**
 * 单表增删改查DAO接口
 * @author zhangcan
 * @version 2016-07-26
 */
@MyBatisDao
public interface SysStaffAssessTemplateDetailDao extends CrudDao<SysStaffAssessTemplateDetail> {
	List<SysStaffAssessTemplateDetail> queryByTemplateId(String templateId);
	void deleteAll(String templateId);
}