/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.luoo.mywork.modules.dlj.service;

import com.luoo.mywork.common.persistence.Page;
import com.luoo.mywork.common.service.CrudService;
import com.luoo.mywork.modules.dlj.dao.ComStaffAssessRecordDao;
import com.luoo.mywork.modules.dlj.entity.ComStaffAssessRecord;
import com.luoo.mywork.modules.sys.entity.User;
import com.luoo.mywork.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * 员工考核记录Service
 * @author zhangcan
 * @version 2016-08-11
 */
@Service
@Transactional(readOnly = true)
public class ComStaffAssessRecordService extends CrudService<ComStaffAssessRecordDao, ComStaffAssessRecord> {

	public ComStaffAssessRecord get(String id) {
		return super.get(id);
	}
	
	public List<ComStaffAssessRecord> findList(ComStaffAssessRecord comStaffAssessRecord) {
		return super.findList(comStaffAssessRecord);
	}
	
	public Page<ComStaffAssessRecord> findPage(Page<ComStaffAssessRecord> page, ComStaffAssessRecord comStaffAssessRecord) {
		return super.findPage(page, comStaffAssessRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(ComStaffAssessRecord comStaffAssessRecord) {
		if (comStaffAssessRecord.getIsNewRecord()){
			User user = UserUtils.getUser();
			comStaffAssessRecord.setCreateDate(new Date());
			comStaffAssessRecord.setGradeUser(user);
		}
		super.save(comStaffAssessRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(ComStaffAssessRecord comStaffAssessRecord) {
		super.delete(comStaffAssessRecord);
	}
	
}