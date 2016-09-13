/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.luoo.mywork.modules.sys.service;

import com.luoo.mywork.common.persistence.Page;
import com.luoo.mywork.common.service.CrudService;
import com.luoo.mywork.modules.sys.dao.SysStaffAssessTemplateDetailDao;
import com.luoo.mywork.modules.sys.entity.SysStaffAssessTemplateDetail;
import com.luoo.mywork.modules.sys.entity.User;
import com.luoo.mywork.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * 单表增删改查Service
 * @author zhangcan
 * @version 2016-07-26
 */
@Service
@Transactional(readOnly = true)
public class SysStaffAssessTemplateDetailService extends CrudService<SysStaffAssessTemplateDetailDao, SysStaffAssessTemplateDetail> {

	public SysStaffAssessTemplateDetail get(String id) {
		return super.get(id);
	}
	
	public List<SysStaffAssessTemplateDetail> findList(SysStaffAssessTemplateDetail sysStaffAssessTemplateDetail) {
		return super.findList(sysStaffAssessTemplateDetail);
	}
	
	public Page<SysStaffAssessTemplateDetail> findPage(Page<SysStaffAssessTemplateDetail> page, SysStaffAssessTemplateDetail sysStaffAssessTemplateDetail) {
		return super.findPage(page, sysStaffAssessTemplateDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(SysStaffAssessTemplateDetail sysStaffAssessTemplateDetail) {
		if (sysStaffAssessTemplateDetail.getIsNewRecord()){
			User user = UserUtils.getUser();
			sysStaffAssessTemplateDetail.setCreateDate(new Date());
			sysStaffAssessTemplateDetail.setUser(user);
		}
		super.save(sysStaffAssessTemplateDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysStaffAssessTemplateDetail sysStaffAssessTemplateDetail) {
		super.delete(sysStaffAssessTemplateDetail);
	}
	
	public List<SysStaffAssessTemplateDetail> queryByTemplateId(String templateId){
		return super.dao.queryByTemplateId(templateId);
	}
	@Transactional(readOnly = false)
	public void deleteAll(String templateId) {
		 super.dao.deleteAll(templateId);
	}
}