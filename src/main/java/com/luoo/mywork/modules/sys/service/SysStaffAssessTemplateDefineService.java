/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.luoo.mywork.modules.sys.service;

import com.luoo.mywork.common.persistence.Page;
import com.luoo.mywork.common.service.CrudService;
import com.luoo.mywork.modules.sys.dao.SysStaffAssessTemplateDefineDao;
import com.luoo.mywork.modules.sys.entity.SysStaffAssessTemplateDefine;
import com.luoo.mywork.modules.sys.entity.User;
import com.luoo.mywork.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * 单表增删改查Service
 * @author zhangcan
 * @version 2016-07-22
 */
@Service
@Transactional(readOnly = true)
public class SysStaffAssessTemplateDefineService extends CrudService<SysStaffAssessTemplateDefineDao, SysStaffAssessTemplateDefine> {

	public SysStaffAssessTemplateDefine get(String id) {
		return super.get(id);
	}
	
	public List<SysStaffAssessTemplateDefine> findList(SysStaffAssessTemplateDefine sysStaffAssessTemplateDefine) {
		return super.findList(sysStaffAssessTemplateDefine);
	}
	
	public Page<SysStaffAssessTemplateDefine> findPage(Page<SysStaffAssessTemplateDefine> page, SysStaffAssessTemplateDefine sysStaffAssessTemplateDefine) {
		return super.findPage(page, sysStaffAssessTemplateDefine);
	}
	
	@Transactional(readOnly = false)
	public void save(SysStaffAssessTemplateDefine sysStaffAssessTemplateDefine) {
		if (sysStaffAssessTemplateDefine.getIsNewRecord()){
			User user = UserUtils.getUser();
			sysStaffAssessTemplateDefine.setCreateDate(new Date());
			sysStaffAssessTemplateDefine.setUser(user);
		}
		sysStaffAssessTemplateDefine.setDoneDate(new Date());
		super.save(sysStaffAssessTemplateDefine);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysStaffAssessTemplateDefine sysStaffAssessTemplateDefine) {
		super.delete(sysStaffAssessTemplateDefine);
	}
	
}