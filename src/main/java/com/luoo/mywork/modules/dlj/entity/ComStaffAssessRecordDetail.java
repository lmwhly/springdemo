/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.luoo.mywork.modules.dlj.entity;

import com.luoo.mywork.common.persistence.DataEntity;
import com.luoo.mywork.modules.sys.entity.SysStaffAssessTemplateDetail;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


/**
 * 员工考核记录明细Entity
 * @author zhangcan
 * @version 2016-08-29
 */
public class ComStaffAssessRecordDetail extends DataEntity<ComStaffAssessRecordDetail> {
	
	private static final long serialVersionUID = 1L;
	private Long detailId;		// detail_id
	private SysStaffAssessTemplateDetail detailTemplate;		// detail_template_id
	private Long recordId;		// record_id
	private Integer scoreValue;		// score_value
	private String remark;		// remark
	private String ext1;		// ext1
	private String ext2;		// ext2
	private String ext3;		// ext3
	
	public ComStaffAssessRecordDetail() {
		super();
	}

	public ComStaffAssessRecordDetail(String id){
		super(id);
	}

	@NotNull(message="detail_id不能为空")
	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}
	
	public SysStaffAssessTemplateDetail getDetailTemplate() {
		return detailTemplate;
	}

	public void setDetailTemplate(SysStaffAssessTemplateDetail detailTemplate) {
		this.detailTemplate = detailTemplate;
	}
	
	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	
	public Integer getScoreValue() {
		return scoreValue;
	}

	public void setScoreValue(Integer scoreValue) {
		this.scoreValue = scoreValue;
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
	
}