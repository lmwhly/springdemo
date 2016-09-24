/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/luoo/mywork">MyWork</a> All rights reserved.
 */
package com.luoo.mywork.modules.sys.web;

import com.google.common.collect.Maps;
import com.luoo.mywork.common.config.Global;
import com.luoo.mywork.common.security.shiro.session.SessionDAO;
import com.luoo.mywork.common.servlet.ValidateCodeServlet;
import com.luoo.mywork.common.utils.CacheUtils;
import com.luoo.mywork.common.utils.CookieUtils;
import com.luoo.mywork.common.utils.IdGen;
import com.luoo.mywork.common.utils.StringUtils;
import com.luoo.mywork.common.web.BaseController;
import com.luoo.mywork.modules.portal.entity.PortalInfo;
import com.luoo.mywork.modules.portal.entity.PortalItem;
import com.luoo.mywork.modules.portal.entity.PortalRef;
import com.luoo.mywork.modules.portal.entity.PortalWidget;
import com.luoo.mywork.modules.portal.service.PortalService;
import com.luoo.mywork.modules.sys.security.FormAuthenticationFilter;
import com.luoo.mywork.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.luoo.mywork.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录Controller
 * @author Luoo
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private SessionDAO sessionDAO;

	@Autowired
	private PortalService portalService;
	
	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
			Principal principal = UserUtils.getPrincipal();

//		// 默认页签模式
//		String tabmode = CookieUtils.getCookie(request, "tabmode");
//		if (tabmode == null){
//			CookieUtils.setCookie(response, "tabmode", "1");
//		}
		
		if (logger.isDebugEnabled()){
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null && !principal.isMobileLogin()){
			return "redirect:" + adminPath;
		}
//		String view;
//		view = "/WEB-INF/views/modules/sys/sysLogin.jsp";
//		view = "classpath:";
//		view += "jar:file:/D:/GitHub/luoo/src/main/webapp/WEB-INF/lib/luoo.jar!";
//		view += "/"+getClass().getName().replaceAll("\\.", "/").replace(getClass().getSimpleName(), "")+"view/sysLogin";
//		view += ".jsp";
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
//			return "redirect:" + adminPath;
			return "redirect:" + adminPath+"/success";
		}

		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		
		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
			message = "用户或密码错误, 请重试.";
		}

		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
		
		if (logger.isDebugEnabled()){
			logger.debug("login fail, active session size: {}, message: {}, exception: {}", 
					sessionDAO.getActiveSessions(false).size(), message, exception);
		}
		
		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)){
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
		}
		
		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
		
		// 如果是手机登录，则返回JSON字符串
		if (mobile){
//	        return renderString(response, model);
	        return "";
		}
		
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(Model model,HttpServletRequest request, HttpServletResponse response) {
		Principal principal = UserUtils.getPrincipal();

		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(principal.getLoginName(), false, true);
		
		if (logger.isDebugEnabled()){
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)){
				CookieUtils.setCookie(response, "LOGINED", "true");
			}else if (StringUtils.equals(logined, "true")){
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}
		
		// 如果是手机登录，则返回JSON字符串
		if (principal.isMobileLogin()){
			if (request.getParameter("login") != null){
				return renderString(response, principal);
			}
			if (request.getParameter("index") != null){
				return "modules/sys/sysIndex";
			}
			return "redirect:" + adminPath + "/login";
		}
		
//		// 登录成功后，获取上次登录的当前站点ID
//		UserUtils.putCache("siteId", StringUtils.toLong(CookieUtils.getCookie(request, "siteId")));

//		System.out.println("==========================a");
//		try {
//			byte[] bytes = com.luoo.mywork.common.utils.FileUtils.readFileToByteArray(
//					com.luoo.mywork.common.utils.FileUtils.getFile("c:\\sxt.dmp"));
//			UserUtils.getSession().setAttribute("kkk", bytes);
//			UserUtils.getSession().setAttribute("kkk2", bytes);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
////		for (int i=0; i<1000000; i++){
////			//UserUtils.getSession().setAttribute("a", "a");
////			request.getSession().setAttribute("aaa", "aa");
////		}
//		System.out.println("==========================b");



		String userId = UserUtils.getUser().getId();
		PortalRef portalRef = portalService.createOrGetPortalRef(userId);

		if (portalRef == null) {
			return "modules/sys/sysIndex";
		}

		PortalInfo portalInfo = portalRef.getPortalInfo();

		List<Integer> columnIndexes = portalService.getColumnIndexesByPortalInfoId(portalInfo);
		logger.debug("columnIndexes : {}", columnIndexes);

		if (!columnIndexes.contains(Integer.valueOf(1))) {
			columnIndexes.add(Integer.valueOf(1));
		}

		if (!columnIndexes.contains(Integer.valueOf(2))) {
			columnIndexes.add(Integer.valueOf(2));
		}

		if (!columnIndexes.contains(Integer.valueOf(3))) {
			columnIndexes.add(Integer.valueOf(3));
		}

		Collections.sort(columnIndexes);

		Map<Integer, List<PortalItem>> map = new LinkedHashMap<Integer, List<PortalItem>>();

		for (Integer columnIndex : columnIndexes) {
			PortalItem portalItem = new PortalItem();
			portalItem.setPortalInfo(portalInfo);
			portalItem.setColumnIndex(columnIndex);
			List<PortalItem> portalItems = portalService.getPortalItemByPortalInfoIdAndColumnIndex(portalItem);
			map.put(columnIndex, portalItems);
		}

		model.addAttribute("map", map);

		List<PortalWidget> portalWidgets = portalService.getAllPortalWidgets();
		model.addAttribute("portalWidgets", portalWidgets);



		return "modules/sys/sysIndex";
	}


	@RequestMapping("${adminPath}/save")
	public String save(@RequestParam(value = "id", required = false) String id,
					   @RequestParam(value = "portalWidgetId", required = false) Long portalWidgetId,
					   @RequestParam(value = "portalItemName", required = false) String portalItemName) {
		String userId = UserUtils.getUser().getId();
		PortalInfo portalInfo = portalService.copyOrGetPortalInfo(userId);

		PortalWidget portalWidget = portalService.getPortalWidgetById(portalWidgetId);
		PortalItem portalItem = null;

		if (id == null) {
			portalItem = new PortalItem();

			Integer columnIndex = (Integer) portalService.getMinColumnIndexOfPortalItemByPortalInfoId(portalInfo);

			if (columnIndex == null) {
				columnIndex = 0;
			}

			PortalItem portalItemParam = new PortalItem();
			portalItemParam.setColumnIndex(columnIndex);
			portalItemParam.setPortalInfo(portalInfo);
			Long rowIndexLong = (Long) portalService.getCountOfPortalItemByPortalInfoIdAndColumnIndex(portalItemParam);

			if (rowIndexLong == null) {
				rowIndexLong = 0L;
			}

			int rowIndex = rowIndexLong.intValue();
			portalItem.setColumnIndex(columnIndex);
			portalItem.setRowIndex(rowIndex);
			portalItem.setPortalInfo(portalInfo);
		} else {
			portalItem = portalService.createOrGetPortalItem(portalInfo, id);
		}

		portalItem.setName(portalItemName);
		portalItem.setPortalWidget(portalWidget);
		portalService.savePortalItem(portalItem);

		return "redirect:" + adminPath;
	}


	@RequestMapping("${adminPath}/updateOrder")
	public String updateOrder(@RequestParam(value="ids",required = false) List<String> ids,
							  @RequestParam(value="priorities",required = false) List<String> priorities) {
		String userId = UserUtils.getUser().getId();
		PortalInfo portalInfo = portalService.copyOrGetPortalInfo(userId);
		int index = 0;

		for (String id : ids) {
			PortalItem portalItem = portalService.createOrGetPortalItem(portalInfo, id);
			String[] array = priorities.get(index).split(":");
			int columnIndex = Integer.parseInt(array[0]);
			int rowIndex = Integer.parseInt(array[1]);
			portalItem.setColumnIndex(columnIndex);
			portalItem.setRowIndex(rowIndex);
			portalService.savePortalItem(portalItem);
			index++;
		}

		return "redirect:" + adminPath;
	}


	@RequestMapping("${adminPath}/remove")
	public String remove(@RequestParam("id") String id) {
		String userId = UserUtils.getUser().getId();
		PortalInfo portalInfo = portalService.copyOrGetPortalInfo(userId);
		PortalItem portalItem = portalService.createOrGetPortalItem(portalInfo, id);
		portalService.removePortalItem(portalItem);

		return "redirect:" + adminPath;
	}
	
	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
		if (StringUtils.isNotBlank(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		}else{
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:"+request.getParameter("url");
	}
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
}
