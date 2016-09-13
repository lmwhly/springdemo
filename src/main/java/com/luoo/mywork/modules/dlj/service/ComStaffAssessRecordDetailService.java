/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.luoo.mywork.modules.dlj.service;

import com.luoo.mywork.common.persistence.Page;
import com.luoo.mywork.common.service.CrudService;
import com.luoo.mywork.modules.dlj.dao.ComStaffAssessRecordDetailDao;
import com.luoo.mywork.modules.dlj.entity.ComStaffAssessRecordDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 员工考核记录明细Service
 * @author zhangcan
 * @version 2016-08-29
 */
@Service
@Transactional(readOnly = true)
public class ComStaffAssessRecordDetailService extends CrudService<ComStaffAssessRecordDetailDao, ComStaffAssessRecordDetail> {

	public ComStaffAssessRecordDetail get(String id) {
		return super.get(id);
	}
	
	public List<ComStaffAssessRecordDetail> findList(ComStaffAssessRecordDetail comStaffAssessRecordDetail) {
		return super.findList(comStaffAssessRecordDetail);
	}
	
	public Page<ComStaffAssessRecordDetail> findPage(Page<ComStaffAssessRecordDetail> page, ComStaffAssessRecordDetail comStaffAssessRecordDetail) {
		return super.findPage(page, comStaffAssessRecordDetail);
	}
	
	public List<ComStaffAssessRecordDetail> queryByRecordId(Long recordId){
		return dao.queryByRecordId(recordId);
	}
	
	@Transactional(readOnly = false)
	public void save(ComStaffAssessRecordDetail comStaffAssessRecordDetail) {
		super.save(comStaffAssessRecordDetail);
	}
	@Transactional(readOnly = false)
	public void save(List<ComStaffAssessRecordDetail> list) {
		for (ComStaffAssessRecordDetail comStaffAssessRecordDetail : list) {
			super.save(comStaffAssessRecordDetail);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ComStaffAssessRecordDetail comStaffAssessRecordDetail) {
		super.delete(comStaffAssessRecordDetail);
	}
	
	@Transactional(readOnly = false)
	public void deleteByRecordId(Long recordId) {
		dao.deleteByRecordId(recordId);
	}
	
}