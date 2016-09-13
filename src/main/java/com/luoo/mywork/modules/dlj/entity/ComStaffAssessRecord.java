/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.luoo.mywork.modules.dlj.entity;

import com.luoo.mywork.common.persistence.DataEntity;
import com.luoo.mywork.modules.sys.entity.SysStaffAssessTemplateDefine;
import com.luoo.mywork.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.Date;


/**
 * 员工考核记录Entity
 * @author zhangcan
 * @version 2016-08-11
 */
public class ComStaffAssessRecord extends DataEntity<ComStaffAssessRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long recordId;		// record_id
	private SysStaffAssessTemplateDefine template;		// 考核模板ID
	private String assessMonth;		// 考核年月
	private Integer totalScore;		// 总分
	private String state;		// 状态
	private User targetUser;		// 被考核人
	private User gradeUser;		// 打分人
	private String remark;		// remark
	private String ext1;		// ext1
	private String ext2;		// ext2
	private String ext3;		// ext3
	private Date beginCreateDate;		// 开始 考核时间
	private Date endCreateDate;		// 结束 考核时间
	 
	public ComStaffAssessRecord() {
		super();
	}

	public ComStaffAssessRecord(String id){
		super(id);
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	
	public SysStaffAssessTemplateDefine getTemplate() {
		return template;
	}

	public void setTemplate(SysStaffAssessTemplateDefine template) {
		this.template = template;
	}
	
	@Length(min=0, max=6, message="考核年月长度必须介于 0 和 6 之间")
	public String getAssessMonth() {
		return assessMonth;
	}

	public void setAssessMonth(String assessMonth) {
		this.assessMonth = assessMonth;
	}
	
	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	
	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public User getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(User targetUser) {
		this.targetUser = targetUser;
	}
	
	public User getGradeUser() {
		return gradeUser;
	}

	public void setGradeUser(User gradeUser) {
		this.gradeUser = gradeUser;
	}
	
	@Length(min=0, max=512, message="remark长度必须介于 0 和 512 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=64, message="ext1长度必须介于 0 和 64 之间")
	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	
	@Length(min=0, max=64, message="ext2长度必须介于 0 和 64 之间")
	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	
	@Length(min=0, max=64, message="ext3长度必须介于 0 和 64 之间")
	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
		
}