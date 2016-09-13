/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.luoo.mywork.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luoo.mywork.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 单表增删改查Entity
 * @author zhangcan
 * @version 2016-07-22
 */
public class SysStaffAssessTemplateDefine extends DataEntity<SysStaffAssessTemplateDefine> {
	
	private static final long serialVersionUID = 1L;
	private String templateId;		// 模板ID
	private String templateName;		// 模板名称
	private Date doneDate;		// 修改时间
	private String state;		// 状态
	private User user;		// 操作员ID
	private String ext1;		// ext1
	private String ext2;		// ext2
	private String ext3;		// ext3
	
	public SysStaffAssessTemplateDefine() {
		super();
	}

	public SysStaffAssessTemplateDefine(String id){
		super(id);
	}

	@Length(min=1, max=12, message="模板ID长度必须介于 1 和 12 之间")
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	@Length(min=0, max=256, message="模板名称长度必须介于 0 和 256 之间")
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDoneDate() {
		return doneDate;
	}

	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=256, message="ext1长度必须介于 0 和 256 之间")
	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	
	@Length(min=0, max=256, message="ext2长度必须介于 0 和 256 之间")
	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	
	@Length(min=0, max=256, message="ext3长度必须介于 0 和 256 之间")
	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	
}