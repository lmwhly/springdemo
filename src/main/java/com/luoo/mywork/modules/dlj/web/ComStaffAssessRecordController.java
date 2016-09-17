/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.luoo.mywork.modules.dlj.web;

import com.alibaba.fastjson.JSONObject;
import com.luoo.mywork.common.config.Global;
import com.luoo.mywork.common.persistence.Page;
import com.luoo.mywork.common.utils.StringUtils;
import com.luoo.mywork.common.web.BaseController;
import com.luoo.mywork.modules.dlj.entity.ComStaffAssessRecord;
import com.luoo.mywork.modules.dlj.entity.ComStaffAssessRecordDetail;
import com.luoo.mywork.modules.dlj.service.ComStaffAssessRecordDetailService;
import com.luoo.mywork.modules.dlj.service.ComStaffAssessRecordService;
import com.luoo.mywork.modules.sys.entity.SysStaffAssessTemplateDefine;
import com.luoo.mywork.modules.sys.entity.SysStaffAssessTemplateDetail;
import com.luoo.mywork.modules.sys.entity.User;
import com.luoo.mywork.modules.sys.service.SysStaffAssessTemplateDefineService;
import com.luoo.mywork.modules.sys.service.SysStaffAssessTemplateDetailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * 员工考核记录Controller
 * @author zhangcan
 * @version 2016-08-11
 */
@Controller
@RequestMapping(value = "${adminPath}/dlj/comStaffAssessRecord")
public class ComStaffAssessRecordController extends BaseController {

	@Autowired
	private ComStaffAssessRecordService comStaffAssessRecordService;
	@Autowired
	private ComStaffAssessRecordDetailService comStaffAssessRecordDetailService;
	@Autowired
	private SysStaffAssessTemplateDefineService sysStaffAssessTemplateDefineService;
	@Autowired
	private SysStaffAssessTemplateDetailService sysStaffAssessTemplateDetailService;

	@ModelAttribute
	public ComStaffAssessRecord get(@RequestParam(required=false) String id) {
		ComStaffAssessRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comStaffAssessRecordService.get(id);
		}
		if (entity == null){
			entity = new ComStaffAssessRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("dlj:comStaffAssessRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(ComStaffAssessRecord comStaffAssessRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<ComStaffAssessRecord> page = comStaffAssessRecordService.findPage(new Page<ComStaffAssessRecord>(request, response), comStaffAssessRecord);
//		model.addAttribute("page", page);
		return "modules/dlj/comStaffAssessRecordList";
	}


	@RequiresPermissions("dlj:comStaffAssessRecord:view")
	@RequestMapping(value = "newlist")
	@ResponseBody
	public Object newlist(ComStaffAssessRecord comStaffAssessRecord,HttpServletRequest request,HttpServletResponse response, @RequestBody JSONObject jsonObj) {

		String state = jsonObj.getString("state");

//		comStaffAssessRecord.setState(state);

		int pageNumber = jsonObj.getIntValue("pageNumber");
		int pageSize = jsonObj.getIntValue("pageSize");

		try {

			Page<ComStaffAssessRecord> page = comStaffAssessRecordService.findPage(new Page<ComStaffAssessRecord>(pageNumber, pageSize), comStaffAssessRecord);
			return page;


		} catch (Exception e) {
			logger.error("系统异常e:{}", e);
		}

		return null;
	}

	@RequiresPermissions("dlj:comStaffAssessRecord:view")
	@RequestMapping(value = "form")
	public String form(ComStaffAssessRecord comStaffAssessRecord, Model model) {
		model.addAttribute("comStaffAssessRecord", comStaffAssessRecord);
		SysStaffAssessTemplateDefine template = new SysStaffAssessTemplateDefine();
		template.setState("2");
		List<SysStaffAssessTemplateDefine> templateList = sysStaffAssessTemplateDefineService.findList(template);
		model.addAttribute("templateList", templateList);
		return "modules/dlj/comStaffAssessRecordForm";
	}

	@RequiresPermissions("dlj:comStaffAssessRecord:edit")
	@RequestMapping(value = "save")
	public String save(ComStaffAssessRecord comStaffAssessRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, comStaffAssessRecord)){
			return form(comStaffAssessRecord, model);
		}
		comStaffAssessRecordService.save(comStaffAssessRecord);
		addMessage(redirectAttributes, "保存员工考核记录成功");
		return "redirect:"+ Global.getAdminPath()+"/dlj/comStaffAssessRecord/?repage";
	}
	
	@RequiresPermissions("dlj:comStaffAssessRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(ComStaffAssessRecord comStaffAssessRecord, RedirectAttributes redirectAttributes) {
		comStaffAssessRecordDetailService.deleteByRecordId(comStaffAssessRecord.getRecordId());
		comStaffAssessRecordService.delete(comStaffAssessRecord);
		addMessage(redirectAttributes, "删除员工考核记录成功");
		return "redirect:"+Global.getAdminPath()+"/dlj/comStaffAssessRecord/?repage";
	}
	@RequestMapping(value = "markView")
	public String markView(ComStaffAssessRecord comStaffAssessRecord,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) throws Exception{
		List<ComStaffAssessRecordDetail> recordList = comStaffAssessRecordDetailService.queryByRecordId(comStaffAssessRecord.getRecordId());
		model.addAttribute("comStaffAssessRecord", comStaffAssessRecord);
		model.addAttribute("recordList", recordList);
		return "modules/dlj/comStaffAssessView";
	}
	@RequestMapping(value = "update")
	public String update(ComStaffAssessRecord comStaffAssessRecord,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) throws Exception{
		List<ComStaffAssessRecordDetail> recordList = comStaffAssessRecordDetailService.queryByRecordId(comStaffAssessRecord.getRecordId());
		model.addAttribute("comStaffAssessRecord", comStaffAssessRecord);
		model.addAttribute("recordList", recordList);
		return "modules/dlj/comStaffAssessUpdate";
	}
	@RequestMapping(value = "templateMarkNew")
	public String templateMarkNew(ComStaffAssessRecord comStaffAssessRecord,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) throws Exception{
		model.addAttribute("comStaffAssessRecord", comStaffAssessRecord);
		return "modules/dlj/comStaffAssessGrade";
	}
	@RequestMapping(value = "markDetail", method = RequestMethod.POST)
	public String markDetail(String templateId,String assessMonth,String targetUserId, String assessType,
			HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if(assessMonth==null||"".equals(assessMonth)){
			throw new Exception("没有选择考核时段");
		}
		if(templateId==null||"".equals(templateId)){
			throw new Exception("没有选择考核模板");
		}
		if(assessType==null||"".equals(assessType)){
			throw new Exception("没有选择考核类型");
		}
		if(targetUserId==null||"".equals(targetUserId)){
			throw new Exception("没有选择被考核人");
		}
		try {
			List<SysStaffAssessTemplateDetail> templateList = sysStaffAssessTemplateDetailService.queryByTemplateId(templateId);
			int totalScore =0;
			List<ComStaffAssessRecordDetail> detailList = new ArrayList<>();
			for (SysStaffAssessTemplateDetail template : templateList) {
				if(template.getPoint()!=null&&template.getPoint()>0){
					int score = Integer.parseInt(request.getParameter(template.getDetailTemplateId()+"_value"));
					if(score<0){
						throw new Exception("打分项不能为负数");
					}
					if(score>template.getPoint().intValue()){
						throw new Exception("打分数不能大于分值");
					}
					String remark = request.getParameter(template.getDetailTemplateId()+"_remark");
					ComStaffAssessRecordDetail detailRecord = new ComStaffAssessRecordDetail();
					detailRecord.setDetailTemplate(template);
					detailRecord.setScoreValue(score);
					detailRecord.setRemark(remark);
					detailList.add(detailRecord);
					totalScore+=score;
				}
			}
			ComStaffAssessRecord record = new ComStaffAssessRecord();
			record.setTemplate(sysStaffAssessTemplateDefineService.get(templateId));
			record.setTotalScore(totalScore);
			//编辑状态
			record.setState("1");
			record.setRemark(request.getParameter("total_remark"));
			record.setAssessMonth(assessMonth);
			User targetUser = new User();
			targetUser.setId(targetUserId);
			record.setTargetUser(targetUser);
			record.setExt1(assessType);
			
			comStaffAssessRecordService.save(record);
			
			for (ComStaffAssessRecordDetail detailRecord : detailList) {
				detailRecord.setRecordId(record.getRecordId());
			}
			comStaffAssessRecordDetailService.save(detailList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list(new ComStaffAssessRecord(),request,response,model);
	}
	@RequestMapping(value = "updateDetail", method = RequestMethod.POST)
	public String updateDetail(ComStaffAssessRecord comStaffAssessRecord,
			HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws Exception{
		try {
			List<ComStaffAssessRecordDetail> recordList = comStaffAssessRecordDetailService.queryByRecordId(comStaffAssessRecord.getRecordId());
			int totalScore =0;
			for (ComStaffAssessRecordDetail record : recordList) {
				int score = Integer.parseInt(request.getParameter(record.getDetailId()+"_value"));
				if(score<0){
					throw new Exception("打分项不能为负数");
				}
				if(score>record.getDetailTemplate().getPoint().intValue()){
					throw new Exception("分数不能大于分值");
				}
				String remark = request.getParameter(record.getDetailId()+"_remark");
				record.setScoreValue(score);
				record.setRemark(remark);
				totalScore+=score;
			}
			comStaffAssessRecord.setTotalScore(totalScore);
			//编辑状态
			comStaffAssessRecord.setState("1");
			comStaffAssessRecord.setRemark(request.getParameter("total_remark"));
			comStaffAssessRecordService.save(comStaffAssessRecord);
			comStaffAssessRecordDetailService.save(recordList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list(new ComStaffAssessRecord(),request,response,model);
	}
	
	
}