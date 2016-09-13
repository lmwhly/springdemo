/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.luoo.mywork.modules.dlj.dao;


import com.luoo.mywork.common.persistence.CrudDao;
import com.luoo.mywork.common.persistence.annotation.MyBatisDao;
import com.luoo.mywork.modules.dlj.entity.ComStaffAssessRecord;

/**
 * 员工考核记录DAO接口
 * @author zhangcan
 * @version 2016-08-11
 */
@MyBatisDao
public interface ComStaffAssessRecordDao extends CrudDao<ComStaffAssessRecord> {
	
}