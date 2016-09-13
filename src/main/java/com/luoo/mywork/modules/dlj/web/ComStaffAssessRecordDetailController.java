/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.luoo.mywork.modules.dlj.web;

import com.luoo.mywork.common.config.Global;
import com.luoo.mywork.common.persistence.Page;
import com.luoo.mywork.common.utils.StringUtils;
import com.luoo.mywork.common.web.BaseController;
import com.luoo.mywork.modules.dlj.entity.ComStaffAssessRecordDetail;
import com.luoo.mywork.modules.dlj.service.ComStaffAssessRecordDetailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 员工考核记录明细Controller
 * @author zhangcan
 * @version 2016-08-29
 */
@Controller
@RequestMapping(value = "${adminPath}/dlj/comStaffAssessRecordDetail")
public class ComStaffAssessRecordDetailController extends BaseController {

	@Autowired
	private ComStaffAssessRecordDetailService comStaffAssessRecordDetailService;
	
	@ModelAttribute
	public ComStaffAssessRecordDetail get(@RequestParam(required=false) String id) {
		ComStaffAssessRecordDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comStaffAssessRecordDetailService.get(id);
		}
		if (entity == null){
			entity = new ComStaffAssessRecordDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("dlj:comStaffAssessRecordDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(ComStaffAssessRecordDetail comStaffAssessRecordDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComStaffAssessRecordDetail> page = comStaffAssessRecordDetailService.findPage(new Page<ComStaffAssessRecordDetail>(request, response), comStaffAssessRecordDetail);
		model.addAttribute("page", page);
		return "modules/dlj/comStaffAssessRecordDetailList";
	}

	@RequiresPermissions("dlj:comStaffAssessRecordDetail:view")
	@RequestMapping(value = "form")
	public String form(ComStaffAssessRecordDetail comStaffAssessRecordDetail, Model model) {
		model.addAttribute("comStaffAssessRecordDetail", comStaffAssessRecordDetail);
		return "modules/dlj/comStaffAssessRecordDetailForm";
	}

	@RequiresPermissions("dlj:comStaffAssessRecordDetail:edit")
	@RequestMapping(value = "save")
	public String save(ComStaffAssessRecordDetail comStaffAssessRecordDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, comStaffAssessRecordDetail)){
			return form(comStaffAssessRecordDetail, model);
		}
		comStaffAssessRecordDetailService.save(comStaffAssessRecordDetail);
		addMessage(redirectAttributes, "保存员工考核记录明细成功");
		return "redirect:"+ Global.getAdminPath()+"/dlj/comStaffAssessRecordDetail/?repage";
	}
	
	@RequiresPermissions("dlj:comStaffAssessRecordDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(ComStaffAssessRecordDetail comStaffAssessRecordDetail, RedirectAttributes redirectAttributes) {
		comStaffAssessRecordDetailService.delete(comStaffAssessRecordDetail);
		addMessage(redirectAttributes, "删除员工考核记录明细成功");
		return "redirect:"+Global.getAdminPath()+"/dlj/comStaffAssessRecordDetail/?repage";
	}

}