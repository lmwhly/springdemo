/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.luoo.mywork.modules.sys.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.common.collect.Lists;
import com.luoo.mywork.common.beanvalidator.BeanValidators;
import com.luoo.mywork.common.config.Global;
import com.luoo.mywork.common.utils.StringUtils;
import com.luoo.mywork.common.utils.excel.ExportExcel;
import com.luoo.mywork.common.utils.excel.ImportExcel;
import com.luoo.mywork.common.web.BaseController;
import com.luoo.mywork.modules.sys.entity.Department;
import com.luoo.mywork.modules.sys.entity.User;
import com.luoo.mywork.modules.sys.service.SystemService;
import com.luoo.mywork.modules.sys.utils.JSPUtil;
import com.luoo.mywork.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller
 *
 * @author ThinkGem
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

    @Autowired
    private SystemService systemService;

    /**
     * 用户信息显示及保存
     *
     * @param user
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "info")
    public String info(User user, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

        User currentUser = UserUtils.getUser();
        if (StringUtils.isNotBlank(user.getName())) {
            if (Global.isDemoMode()) {
                model.addAttribute("message", "演示模式，不允许操作！");
                return "modules/sys/userInfo";
            }
            currentUser.setEmail(user.getEmail());
            currentUser.setPhone(user.getPhone());
            currentUser.setMobile(user.getMobile());
            currentUser.setRemarks(user.getRemarks());
            currentUser.setImgFile(user.getImgFile());
            systemService.updateUserInfo(currentUser);
            model.addAttribute("message", "保存用户信息成功");
        }
        model.addAttribute("user", currentUser);
        model.addAttribute("Global", new Global());
        return "modules/sys/userInfo";
    }


    /**
     * 修改个人用户密码
     *
     * @param oldPassword
     * @param newPassword
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "modifyPwd")
    public String modifyPwd(String oldPassword, String newPassword, Model model) {
        User user = UserUtils.getUser();
        if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {
            if (Global.isDemoMode()) {
                model.addAttribute("message", "演示模式，不允许操作！");
                return "modules/sys/userModifyPwd";
            }
            if (SystemService.validatePassword(oldPassword, user.getPassword())) {
                systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
                model.addAttribute("message", "修改密码成功");
            } else {
                model.addAttribute("message", "修改密码失败，旧密码错误");
            }
        }
        model.addAttribute("user", user);
        return "modules/sys/userModifyPwd";
    }


    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = {"index"})
    public String index(User user, Model model) {
        return "modules/sys/userIndex";
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = {"list", ""})
    public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<User> page = systemService.findUser(user);
        return "modules/sys/userList";
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "form")
    public String form(User user, Model model) {
        if (user.getCompany() == null || user.getCompany().getId() == null) {
            user.setCompany(UserUtils.getUser().getCompany());
        }
        if (user.getOffice() == null || user.getOffice().getId() == null) {
            user.setOffice(UserUtils.getUser().getOffice());
        }
        model.addAttribute("user", user);
        model.addAttribute("allRoles", systemService.findAllRole());
        return "modules/sys/userForm";
    }

    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "delete")
    public String delete(User user, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        if (UserUtils.getUser().getId().equals(user.getId())) {
            addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
        } else if (User.isAdmin(user.getId())) {
            addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
        } else {
            systemService.deleteUser(user);
            addMessage(redirectAttributes, "删除用户成功");
        }
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }


    /**
     * 导入用户数据
     *
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<User> list = ei.getDataList(User.class);
            for (User user : list) {
                try {
                    if ("true".equals(checkLoginName("", user.getLoginName()))) {
                        user.setPassword(SystemService.entryptPassword("123456"));
                        BeanValidators.validateWithException(validator, user);
                        systemService.saveUser(user);
                        successNum++;
                    } else {
                        failureMsg.append("<br/>登录名 " + user.getLoginName() + " 已存在; ");
                        failureNum++;
                    }
                } catch (ConstraintViolationException ex) {
                    failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：");
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                    for (String message : messageList) {
                        failureMsg.append(message + "; ");
                        failureNum++;
                    }
                } catch (Exception ex) {
                    failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：" + ex.getMessage());
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入用户失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }

    /**
     * 下载导入用户数据模板
     *
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "用户数据导入模板.xlsx";
            List<User> list = Lists.newArrayList();
            list.add(UserUtils.getUser());
            new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }

    /**
     * 验证登录名是否有效
     *
     * @param oldLoginName
     * @param loginName
     * @return
     */
    @ResponseBody
    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "checkLoginName")
    public String checkLoginName(String oldLoginName, String loginName) {
        if (loginName != null && loginName.equals(oldLoginName)) {
            return "true";
        } else if (loginName != null && systemService.getUserByLoginName(loginName) == null) {
            return "true";
        }
        return "false";
    }

    //根据传入的pageSize，offset参数决定查哪一页,根据其他参数决定查询哪些数据
    @RequestMapping( value = "testdata", method = RequestMethod.POST, produces = "application/json;charset=UTF-8" )
    @ResponseBody
    public Object channelDivideDetailsData( HttpServletRequest request, @RequestBody JSONObject jsonObj ) {
        String html = "[]";
        Map<String, Object> map = new HashMap<String, Object>();
//        String startDateStr = jsonObj.getString("startDate");
//        String endDateStr = jsonObj.getString("endDate");
//        String merName = jsonObj.getString("loginName");
        int pageSize = jsonObj.getIntValue("pageSize");
        int offset = jsonObj.getIntValue("offset");
        try {
            /*map.put("startDate", startDateStr);
            map.put("endDate", endDateStr);
            if(merName != null && merName != "") {
                map.put("merName", merName);
            }*/
            pageSize = 2;
            offset = 1;
            PageBounds pageBounds = JSPUtil.getPagerBoundsByParameter(pageSize, offset);

//            List<Department> list = systemService.findMyDepartment(map, pageBounds);

            List<Department> list = Lists.newArrayList();
            for (int i = 0; i < 2; i++) {
                Department oModel = new Department();
                oModel.setId(i + "");
                oModel.setName("销售部" + i);
                list.add(oModel);
            }



            if(list != null && list.size() > 0) {
                Map<String, Object> retMap = (Map<String, Object>) JSPUtil.pagelistToJSONMapNew((PageList<Department>) list);
                html = JSON.toJSONStringWithDateFormat(retMap, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
            }

        }
        catch(Exception e) {
            logger.error("系统异常e:{}", e);
        }

//        {"rows":[{"currentUser":{"admin":true,"company":{"area":{"currentUser":{"$ref":"$.rows[0].currentUser"},"delFlag":"0","id":"2","isNewRecord":false,"name":"山东省","parent":{"currentUser":{"$ref":"$.rows[0].currentUser"},"delFlag":"0","id":"1","isNewRecord":false,"parentId":"0","sort":30,"sqlMap":{}},"parentId":"1","parentIds":"0,1,","sort":30,"sqlMap":{}},"currentUser":{"$ref":".."},"delFlag":"0","id":"1","isNewRecord":false,"name":"山东省总公司","parent":{"currentUser":{"$ref":"$.rows[0].currentUser"},"delFlag":"0","id":"0","isNewRecord":false,"parentId":"0","sort":30,"sqlMap":{},"type":"2"},"parentId":"0","parentIds":"0,","sort":30,"sqlMap":{},"type":"2"},"createBy":{"admin":true,"currentUser":{"$ref":".."},"delFlag":"0","id":"1","isNewRecord":false,"loginFlag":"1","roleIdList":[],"roleList":[],"roleNames":"","sqlMap":{}},"createDate":"2013-05-27","currentUser":{"$ref":"@"},"delFlag":"0","email":"thinkgem@163.com","id":"1","isNewRecord":false,"loginDate":"2016-09-08","loginFlag":"1","loginIp":"127.0.0.1","loginName":"thinkgem","mobile":"8675","name":"系统管理员","no":"0001","office":{"area":{"currentUser":{"$ref":"$.rows[0].currentUser"},"delFlag":"0","id":"2","isNewRecord":false,"name":"山东省","parent":{"currentUser":{"$ref":"$.rows[0].currentUser"},"delFlag":"0","id":"1","isNewRecord":false,"parentId":"0","sort":30,"sqlMap":{}},"parentId":"1","parentIds":"0,1,","sort":30,"sqlMap":{}},"currentUser":{"$ref":".."},"delFlag":"0","id":"2","isNewRecord":false,"name":"公司领导","parent":{"currentUser":{"$ref":"$.rows[0].currentUser"},"delFlag":"0","id":"1","isNewRecord":false,"parentId":"0","sort":30,"sqlMap":{},"type":"2"},"parentId":"1","parentIds":"0,1,","sort":30,"sqlMap":{},"type":"2"},"oldLoginDate":"2016-09-08","oldLoginIp":"127.0.0.1","password":"e53573f56e9a729779c3dffd88b9e1058503ed7a564f9a420602fa2d","phone":"8675","photo":"/fileUpload/1473215841912.jpg","remarks":"最高管理员","roleIdList":["2","1"],"roleList":[{"currentUser":{"$ref":"$.rows[0].currentUser"},"dataScope":"2","delFlag":"0","enname":"hr","id":"2","isNewRecord":false,"menuIdList":[],"menuIds":"","menuList":[],"name":"公司管理员","officeIdList":[],"officeIds":"","officeList":[],"permissions":[],"roleType":"assignment","sqlMap":{},"useable":"1"},{"currentUser":{"$ref":"$.rows[0].currentUser"},"dataScope":"1","delFlag":"0","enname":"dept","id":"1","isNewRecord":false,"menuIdList":[],"menuIds":"","menuList":[],"name":"系统管理员","officeIdList":[],"officeIds":"","officeList":[],"permissions":[],"roleType":"assignment","sqlMap":{},"useable":"1"}],"roleNames":"公司管理员,系统管理员","sqlMap":{},"updateBy":{"admin":true,"currentUser":{"$ref":".."},"delFlag":"0","id":"1","isNewRecord":false,"loginFlag":"1","roleIdList":[],"roleList":[],"roleNames":"","sqlMap":{}},"updateDate":"2016-09-07"},"delFlag":"0","id":"4","isNewRecord":false,"name":"市场部","sqlMap":{}},{"currentUser":{"$ref":"$.rows[0].currentUser"},"delFlag":"0","id":"5","isNewRecord":false,"name":"技术部","sqlMap":{}}],"total":13}
        return html;
    }

    @RequestMapping(value = "data")
    @ResponseBody
    public PageResponse<Department> GetDepartment(Integer limit, Integer offset, String departmentname, String statu) {
        PageResponse<Department> list =   new PageResponse<Department>();
        List<Department> lstRes = Lists.newArrayList();
        for (int i = 0; i < 50; i++) {
            Department oModel = new Department();
            oModel.setId(i + "");
            oModel.setName("销售部" + i);
            lstRes.add(oModel);
        }

        int total = lstRes.size();
        list.setRecords(lstRes);
        list.setTotal(total);
        return list;
    }


    public class PageResponse<T> implements Serializable {
        private static final long serialVersionUID = 768549219446295665L;
        private Integer total;  //总条数
        private List<T> records; //当前页显示数据

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public List<T> getRecords() {
            return records;
        }

        public void setRecords(List<T> records) {
            this.records = records;
        }
    }

}
