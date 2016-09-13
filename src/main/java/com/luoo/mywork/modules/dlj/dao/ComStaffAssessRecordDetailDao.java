/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.luoo.mywork.modules.dlj.dao;

import com.luoo.mywork.common.persistence.CrudDao;
import com.luoo.mywork.common.persistence.annotation.MyBatisDao;
import com.luoo.mywork.modules.dlj.entity.ComStaffAssessRecordDetail;

import java.util.List;


/**
 * 员工考核记录明细DAO接口
 * @author zhangcan
 * @version 2016-08-29
 */
@MyBatisDao
public interface ComStaffAssessRecordDetailDao extends CrudDao<ComStaffAssessRecordDetail> {
	void deleteByRecordId(Long recordId) ;
	List<ComStaffAssessRecordDetail> queryByRecordId(Long recordId);

}