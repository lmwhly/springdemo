/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.luoo.mywork.modules.sys.web;

import com.luoo.mywork.common.config.Global;
import com.luoo.mywork.common.persistence.Page;
import com.luoo.mywork.common.utils.StringUtils;
import com.luoo.mywork.common.web.BaseController;
import com.luoo.mywork.modules.sys.entity.SysStaffAssessTemplateDetail;
import com.luoo.mywork.modules.sys.service.SysStaffAssessTemplateDetailService;
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
 * 单表增删改查Controller
 * @author zhangcan
 * @version 2016-07-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysStaffAssessTemplateDetail")
public class SysStaffAssessTemplateDetailController extends BaseController {

	@Autowired
	private SysStaffAssessTemplateDetailService sysStaffAssessTemplateDetailService;
	
	@ModelAttribute
	public SysStaffAssessTemplateDetail get(@RequestParam(required=false) String id) {
		SysStaffAssessTemplateDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysStaffAssessTemplateDetailService.get(id);
		}
		if (entity == null){
			entity = new SysStaffAssessTemplateDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysStaffAssessTemplateDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysStaffAssessTemplateDetail sysStaffAssessTemplateDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysStaffAssessTemplateDetail> page = sysStaffAssessTemplateDetailService.findPage(new Page<SysStaffAssessTemplateDetail>(request, response), sysStaffAssessTemplateDetail);
		model.addAttribute("page", page);
		return "modules/sys/sysStaffAssessTemplateDetailList";
	}

	@RequiresPermissions("sys:sysStaffAssessTemplateDetail:view")
	@RequestMapping(value = "form")
	public String form(SysStaffAssessTemplateDetail sysStaffAssessTemplateDetail, Model model) {
		model.addAttribute("sysStaffAssessTemplateDetail", sysStaffAssessTemplateDetail);
		return "modules/sys/sysStaffAssessTemplateDetailForm";
	}

	@RequiresPermissions("sys:sysStaffAssessTemplateDetail:edit")
	@RequestMapping(value = "save")
	public String save(SysStaffAssessTemplateDetail sysStaffAssessTemplateDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysStaffAssessTemplateDetail)){
			return form(sysStaffAssessTemplateDetail, model);
		}
		sysStaffAssessTemplateDetailService.save(sysStaffAssessTemplateDetail);
		addMessage(redirectAttributes, "保存模板明细成功");
		return "redirect:"+ Global.getAdminPath()+"/sys/sysStaffAssessTemplateDetail/?repage";
	}
	
	@RequiresPermissions("sys:sysStaffAssessTemplateDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(SysStaffAssessTemplateDetail sysStaffAssessTemplateDetail, RedirectAttributes redirectAttributes) {
		sysStaffAssessTemplateDetailService.delete(sysStaffAssessTemplateDetail);
		addMessage(redirectAttributes, "删除模板明细成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysStaffAssessTemplateDetail/?repage";
	}

}