/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.luoo.mywork.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.luoo.mywork.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 单表增删改查Entity
 * @author zhangcan
 * @version 2016-07-26
 */
public class SysStaffAssessTemplateDetail extends DataEntity<SysStaffAssessTemplateDetail> {
	
	private static final long serialVersionUID = 1L;
	private Long detailTemplateId;		// 模板明细ID
	private SysStaffAssessTemplateDetail parent;		// 父模板明细ID
	private Long templateDefineId;		// 模板定义ID
	private String templateContent;		// 模板描述内容
	private Integer seqNum;		// 序号
	private Integer startRow;		// 开始行
	private Integer startCol;		// 开始列
	private Integer endRow;		// 结束行
	private Integer endCol;		// 结束列
	private String pointType;		// 计分制：'1'得分,'2'扣分
	private Integer point;		// 分值
	private User user;		// 导入人ID
	private String ext1;		// ext1
	private String ext2;		// ext2
	private String ext3;		// ext3
	
	public SysStaffAssessTemplateDetail() {
		super();
	}

	public SysStaffAssessTemplateDetail(String id){
		super(id);
	}

	@NotNull(message="模板明细ID不能为空")
	public Long getDetailTemplateId() {
		return detailTemplateId;
	}

	public void setDetailTemplateId(Long detailTemplateId) {
		this.detailTemplateId = detailTemplateId;
	}
	
	@JsonBackReference
	public SysStaffAssessTemplateDetail getParent() {
		return parent;
	}

	public void setParent(SysStaffAssessTemplateDetail parent) {
		this.parent = parent;
	}
	
	public Long getTemplateDefineId() {
		return templateDefineId;
	}

	public void setTemplateDefineId(Long templateDefineId) {
		this.templateDefineId = templateDefineId;
	}
	
	@Length(min=0, max=256, message="模板描述内容长度必须介于 0 和 256 之间")
	public String getTemplateContent() {
		return templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
	
	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	
	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	
	public Integer getStartCol() {
		return startCol;
	}

	public void setStartCol(Integer startCol) {
		this.startCol = startCol;
	}
	
	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}
	
	public Integer getEndCol() {
		return endCol;
	}

	public void setEndCol(Integer endCol) {
		this.endCol = endCol;
	}
	
	@Length(min=0, max=2, message="计分制：'1'得分,'2'扣分长度必须介于 0 和 2 之间")
	public String getPointType() {
		return pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	
	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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