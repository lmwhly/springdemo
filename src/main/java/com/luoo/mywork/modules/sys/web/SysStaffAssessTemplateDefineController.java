/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.luoo.mywork.modules.sys.web;

import com.alibaba.fastjson.JSONObject;
import com.luoo.mywork.common.config.Global;
import com.luoo.mywork.common.persistence.Page;
import com.luoo.mywork.common.utils.JsonUtil;
import com.luoo.mywork.common.utils.StringUtils;
import com.luoo.mywork.common.utils.excel.ImportExcel;
import com.luoo.mywork.common.web.BaseController;
import com.luoo.mywork.modules.sys.entity.SysStaffAssessTemplateDefine;
import com.luoo.mywork.modules.sys.entity.SysStaffAssessTemplateDetail;
import com.luoo.mywork.modules.sys.service.SysStaffAssessTemplateDefineService;
import com.luoo.mywork.modules.sys.service.SysStaffAssessTemplateDetailService;
import com.luoo.mywork.modules.sys.utils.UserUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 单表增删改查Controller
 * @author zhangcan
 * @version 2016-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysStaffAssessTemplateDefine")
public class SysStaffAssessTemplateDefineController extends BaseController {

	@Autowired
	private SysStaffAssessTemplateDefineService sysStaffAssessTemplateDefineService;
	@Autowired
	private SysStaffAssessTemplateDetailService sysStaffAssessTemplateDetailService;
	
	@ModelAttribute
	public SysStaffAssessTemplateDefine get(@RequestParam(required=false) String id) {
		SysStaffAssessTemplateDefine entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysStaffAssessTemplateDefineService.get(id);
		}
		if (entity == null){
			entity = new SysStaffAssessTemplateDefine();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysStaffAssessTemplateDefine:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysStaffAssessTemplateDefine sysStaffAssessTemplateDefine, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*Page<SysStaffAssessTemplateDefine> page = sysStaffAssessTemplateDefineService.findPage(new Page<SysStaffAssessTemplateDefine>(request, response), sysStaffAssessTemplateDefine);
		model.addAttribute("page", page);*/
		return "modules/sys/sysStaffAssessTemplateDefineList";
	}

	@RequiresPermissions("sys:sysStaffAssessTemplateDefine:view")
	@RequestMapping(value = "newlist")
	@ResponseBody
	public Object newlist(SysStaffAssessTemplateDefine sysStaffAssessTemplateDefine,HttpServletRequest request,HttpServletResponse response, @RequestBody JSONObject jsonObj) {

		Map<String, Object> map = new HashMap<String, Object>();
		String state = jsonObj.getString("state");
		String templateName = jsonObj.getString("templateName");
		map.put("state",state);
		map.put("templateName",templateName);


		int pageNumber = jsonObj.getIntValue("pageNumber");
		int pageSize = jsonObj.getIntValue("pageSize");

		try {

			Page<SysStaffAssessTemplateDefine> page = sysStaffAssessTemplateDefineService.findPage(new Page<SysStaffAssessTemplateDefine>(pageNumber, pageSize), sysStaffAssessTemplateDefine);
			return page;


		} catch (Exception e) {
			logger.error("系统异常e:{}", e);
		}

		return null;
	}

	@RequiresPermissions("sys:sysStaffAssessTemplateDefine:view")
	@RequestMapping(value = "form")
	public String form(SysStaffAssessTemplateDefine sysStaffAssessTemplateDefine, Model model) {
		model.addAttribute("sysStaffAssessTemplateDefine", sysStaffAssessTemplateDefine);
		return "modules/sys/sysStaffAssessTemplateDefineForm";
	}

	@RequiresPermissions("sys:sysStaffAssessTemplateDefine:edit")
	@RequestMapping(value = "save")
	public String save(SysStaffAssessTemplateDefine sysStaffAssessTemplateDefine, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysStaffAssessTemplateDefine)){
			return form(sysStaffAssessTemplateDefine, model);
		}
		if(sysStaffAssessTemplateDefine.getIsNewRecord()){
			sysStaffAssessTemplateDefine.setState("1");
		}else{
			SysStaffAssessTemplateDefine oldBean = sysStaffAssessTemplateDefineService.get(sysStaffAssessTemplateDefine.getTemplateId());
			if(sysStaffAssessTemplateDefine.getState().equals("1")&&(!"1".equals(oldBean.getState()))){
				model.addAttribute("message", "生效过的模板不能再次编辑");
				return form(sysStaffAssessTemplateDefine, model);
			}
		}
		sysStaffAssessTemplateDefineService.save(sysStaffAssessTemplateDefine);
		addMessage(redirectAttributes, "保存考核模板成功");
		return "redirect:"+ Global.getAdminPath()+"/sys/sysStaffAssessTemplateDefine/?repage";
	}
	
	@RequiresPermissions("sys:sysStaffAssessTemplateDefine:edit")
	@RequestMapping(value = "delete")
	public String delete(SysStaffAssessTemplateDefine sysStaffAssessTemplateDefine, RedirectAttributes redirectAttributes) {
		if(!"1".equals(sysStaffAssessTemplateDefine.getState())){
			addMessage(redirectAttributes, "不能删除生效过的考核模板");
		}else{
			sysStaffAssessTemplateDefineService.delete(sysStaffAssessTemplateDefine);
			addMessage(redirectAttributes, "删除考核模板成功");
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysStaffAssessTemplateDefine/?repage";
	}
	
    /**
     * 导入数据
     * @param file
     * @param redirectAttributes
     * @return
     */
	private static String TEMPLATE_ID = "TEMPLATE_ID";
	
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
        	String templateId = (String) UserUtils.getSession().getAttribute(TEMPLATE_ID);
        	SysStaffAssessTemplateDefine templateDefine = sysStaffAssessTemplateDefineService.get(templateId);
        	if(!templateDefine.getState().equals("1")){
        		throw new Exception("已生效的模板不能修改");
        	}
        	Map<String,SysStaffAssessTemplateDetail> map = new HashMap<>();
        	ListMap<String,SysStaffAssessTemplateDetail> parentMap = new ListMap<>();
        	CounterMap seqMap = new CounterMap();
            ImportExcel ei = new ImportExcel(file, 0, 0);
            int cellNum = ei.getLastCellNum()-1;
            int rowNum = ei.getLastDataRowNum();
            for (int i = 0; i <=rowNum; i++) {
            	Row r = ei.getRow(i);
            	SysStaffAssessTemplateDetail lastBean = null;
    			for (int j = 0; j <cellNum; j++) {
    				Cell c = r.getCell(j);
    				CellRangeAddress ca = null;
    				SysStaffAssessTemplateDetail bean = null;
    				//是否是合并单元格
    				if((ca  = ei.inMergedRegion(c))!=null){
    					//首行首列
    					if(ca.getFirstColumn()==j&&ca.getFirstRow()==i){
    						bean = new SysStaffAssessTemplateDetail();
    						bean.setStartRow(ca.getFirstRow());
    						bean.setEndRow(ca.getLastRow());
    						bean.setStartCol(ca.getFirstColumn());
    						bean.setEndCol(ca.getLastColumn());
    						bean.setParent(lastBean);
    						bean.setTemplateContent(getCellValue(c).toString());
    						bean.setTemplateDefineId(Long.parseLong(templateId));
    						bean.setSeqNum(seqMap.add(getKey(lastBean)));
    						bean.setExt1(String.valueOf(i));
    						bean.setExt2(String.valueOf(j));
    						map.put(getKey(ca),bean);
    						parentMap.add(getKey(lastBean), bean);
    					}
    					lastBean = map.get(getKey(ca));
    				}else{
    					//非合并单元格	
    					bean = new SysStaffAssessTemplateDetail();
    					bean.setStartRow(i);
    					bean.setEndRow(i);
    					bean.setStartCol(j);
    					bean.setEndCol(j);
    					bean.setParent(lastBean);
    					bean.setTemplateContent(getCellValue(c).toString());
    					bean.setTemplateDefineId(Long.parseLong(templateId));
    					bean.setSeqNum(seqMap.add(getKey(lastBean)));
						bean.setExt1(String.valueOf(i));
						bean.setExt2(String.valueOf(j));
    					map.put(getKey(bean), bean);
    					if(cellNum-1==j){
    						bean.setPoint((int) r.getCell(j+1).getNumericCellValue() );
    						bean.setPointType("d");
    					}
    					parentMap.add(getKey(lastBean), bean);
    					lastBean = bean;
    				}
    			}
    		}
            //先删掉原记录
            sysStaffAssessTemplateDetailService.deleteAll(templateId);
            List<SysStaffAssessTemplateDetail> list = parentMap.get("-1");
            saveTemplateDetail(list,parentMap);
            addMessage(redirectAttributes, "已成功导入");
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入失败！失败信息：" + e.getMessage());
            e.printStackTrace();
        }
       return  "redirect:"+Global.getAdminPath()+"/sys/sysStaffAssessTemplateDefine/?repage";
    }
    private void  saveTemplateDetail(List<SysStaffAssessTemplateDetail> list,ListMap<String,SysStaffAssessTemplateDetail> parentMap){
    	if(list==null){
    		return;
    	}
        for (SysStaffAssessTemplateDetail detailBean : list) {
        	sysStaffAssessTemplateDetailService.save(detailBean);
    		if(parentMap.get(getKey(detailBean))!=null){
    			List slist = parentMap.get(getKey(detailBean));
    			saveTemplateDetail(slist,parentMap);
        	}
		}
    }
    private static Object getCellValue(Cell cell){
		Object val = "";
		try{
			if (cell != null){
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
					val = cell.getNumericCellValue();
				}else if (cell.getCellType() == Cell.CELL_TYPE_STRING){
					val = cell.getStringCellValue();
				}else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA){
					val = cell.getCellFormula();
				}else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
					val = cell.getBooleanCellValue();
				}else if (cell.getCellType() == Cell.CELL_TYPE_ERROR){
					val = cell.getErrorCellValue();
				}
			}
		}catch (Exception e) {
			return val;
		}
		return val;
	}
    
    @ResponseBody
	@RequestMapping(value = "SaveTemplateId")
	public String saveTemplateId(String templateId) {
    	UserUtils.getSession().setAttribute(TEMPLATE_ID,templateId);
		return templateId;
	}

    private static String getKey(CellRangeAddress ca) {
    	return new StringBuffer().append(ca.getFirstRow()).append("-").append(ca.getLastRow()).append("-").append(ca.getFirstColumn()).append("-").append(ca.getLastColumn()).toString();
    }
    private static String getKey(SysStaffAssessTemplateDetail bean) {
    	if(bean==null){
    		return "-1";
    	}
    	return new StringBuffer().append(bean.getStartRow()).append("-").append(bean.getEndRow()).append("-").append(bean.getStartCol()).append("-").append(bean.getEndCol()).toString();
    }
    
	@RequestMapping(value = "TemplateView")
	public String templateView(String templateId,HttpServletRequest request, HttpServletResponse response, Model model){
		model.addAttribute("templateId", templateId);
		return "modules/sys/sysStaffAssessTemplateDefineView";
	}
	
    @ResponseBody
	@RequestMapping(value = "showDetail")
	public List<List<String>> showDetail(String templateId, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
    	List<SysStaffAssessTemplateDetail> list = sysStaffAssessTemplateDetailService.queryByTemplateId(templateId);
    	ArrayList<SysStaffAssessTemplateDetail> rootList = new ArrayList<SysStaffAssessTemplateDetail>();
    	for (SysStaffAssessTemplateDetail bean : list) {
    		if(bean.getParent().getDetailTemplateId()==-1){
    			rootList.add(bean.getSeqNum()-1, bean);
    		}
    	}
    	List<List<String>> resultList = getRowList(list);
    	
//    	JsonObject o = new JsonObject();
//    	for (Long key : map.keySet()) {
//			List l = map.get(key);
//			JsonArray arr = new JsonArray();
//			for (int i = 0; i < l.size(); i++) {
//				arr.add();
//			}
//		}
		return resultList;
	}
	
    private List<List< String>> getRowList(List<SysStaffAssessTemplateDetail> list) throws Exception{
    	 List<List< String>> resultList = new ArrayList<>();
    	for (SysStaffAssessTemplateDetail bean : list) {
    		int index = Integer.parseInt(bean.getExt1());
    		List<String> rowList = null;
    		if(index<resultList.size()){
    			rowList = resultList.get(index);
    		}
    		if(rowList==null||index>=resultList.size()){
    			rowList = new ArrayList<String>();
    			resultList.add(index,rowList);
    		}
    		rowList.add(JsonUtil.toJson(bean));
		}
    	return resultList ;
    }
    
    static class ListMap <K,E> extends HashMap <K, List<E>>{
		private static final long serialVersionUID = 1L;

		public E add(K key, E value) {
			return this.add(key, value,-1);
        }

		public E add(K key, E value, int i) {
        	List<E> list = super.get(key);
        	if(list==null){
        		list = new ArrayList<>();
        		super.put(key,list);
        	}
        	if(i<0){
        		list.add(value);
        	}else{
        		list.add(i,value);
        	}
        	return value;
		}
    }
    
    static class CounterMap extends HashMap<String,Integer>{
		private static final long serialVersionUID = 1L;
		
		public int add(String key){
			Integer i = super.get(key);
			if(i==null){
				super.put(key, 1);
			}else{
				super.put(key, i+1);
			}
			return super.get(key);
		}
    }
    public static void main(String[] args) {
    	/*JsonObject o = new JsonObject();
    	JsonObject o1 = new JsonObject();
    	o1.addProperty("o1.prop", "o1p");
    	o.add("object", o1);
    	o.addProperty("number", 999);
    	o.addProperty("string", "zhangcan");
    	o.addProperty("char", 'b');
    	o.addProperty("boolean", false);
    	System.out.println(o.toString());*/
	}
}