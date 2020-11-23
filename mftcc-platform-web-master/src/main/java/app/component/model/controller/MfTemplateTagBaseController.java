package app.component.model.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.model.feign.MfNewToPageOfficeFeign;
import app.component.model.feign.MfNewTemplateTagBaseFeign;
import config.YmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.model.entity.MfTemplateTagBase;
import app.component.model.feign.MfTemplateTagBaseFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfTemplateTagBaseAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Jun 24 17:21:13 CST 2017
 **/
@Controller
@RequestMapping("/mfTemplateTagBase")
public class MfTemplateTagBaseController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfTemplateTagBaseBgetTagBaseListByPageo
	@Autowired
	private MfTemplateTagBaseFeign mfTemplateTagBaseFeign;
	@Autowired
	private MfNewTemplateTagBaseFeign mfNewTemplateTagBaseFeign;
	// 使用pageoffice的版本标识 0：老版本 1：新版本
	@Value("${mftcc.pageoffice.office_version:0}")
	private String pageOfficeSts;
	//全局变量
	//异步参数
	//表单变量
	
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		return "/component/model/MfTemplateTagBase_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfTemplateTagBase mfTemplateTagBase = new MfTemplateTagBase();
		try {
			mfTemplateTagBase.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfTemplateTagBase.setCriteriaList(mfTemplateTagBase, ajaxData);//我的筛选
			//mfTemplateTagBase.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfTemplateTagBase,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfTemplateTagBase",mfTemplateTagBase));
			if("0".equals(pageOfficeSts)){
				ipage = mfTemplateTagBaseFeign.getTagBaseListByPage(ipage);
			}else if("1".equals(pageOfficeSts)){
				ipage = mfNewTemplateTagBaseFeign.getTagBaseListByPage(ipage);
			}else {
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formtemplatetagebase0002 = formService.getFormData("templatetagebase0002");
			getFormValue(formtemplatetagebase0002, getMapByJson(ajaxData));
			if(this.validateFormData(formtemplatetagebase0002)){
		MfTemplateTagBase mfTemplateTagBase = new MfTemplateTagBase();
				setObjValue(formtemplatetagebase0002, mfTemplateTagBase);
				if("0".equals(pageOfficeSts)){
					mfTemplateTagBaseFeign.insert(mfTemplateTagBase);
				}else if("1".equals(pageOfficeSts)){
					mfNewTemplateTagBaseFeign.insert(mfTemplateTagBase);
				}else {
				}
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formtemplatetagebase0002 = formService.getFormData("templatetagebase0002");
		getFormValue(formtemplatetagebase0002, getMapByJson(ajaxData));
		MfTemplateTagBase mfTemplateTagBaseJsp = new MfTemplateTagBase();
		setObjValue(formtemplatetagebase0002, mfTemplateTagBaseJsp);
		MfTemplateTagBase mfTemplateTagBase = new MfTemplateTagBase();
		if("0".equals(pageOfficeSts)){
			mfTemplateTagBase = mfTemplateTagBaseFeign.getById(mfTemplateTagBaseJsp);
		}else if("1".equals(pageOfficeSts)){
			mfTemplateTagBase = mfNewTemplateTagBaseFeign.getById(mfTemplateTagBaseJsp);
		}else {
		}
		if(mfTemplateTagBase!=null){
			try{
				mfTemplateTagBase = (MfTemplateTagBase)EntityUtil.reflectionSetVal(mfTemplateTagBase, mfTemplateTagBaseJsp, getMapByJson(ajaxData));
				if("0".equals(pageOfficeSts)){
					mfTemplateTagBaseFeign.update(mfTemplateTagBase);
				}else if("1".equals(pageOfficeSts)){
					mfNewTemplateTagBaseFeign.update(mfTemplateTagBase);
				}else {
				}
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formtemplatetagebase0002 = formService.getFormData("templatetagebaseedit");
			getFormValue(formtemplatetagebase0002, getMapByJson(ajaxData));
			if(this.validateFormData(formtemplatetagebase0002)){
		MfTemplateTagBase mfTemplateTagBase = new MfTemplateTagBase();
				setObjValue(formtemplatetagebase0002, mfTemplateTagBase);
				if("0".equals(pageOfficeSts)){
					mfTemplateTagBaseFeign.update(mfTemplateTagBase);
				}else if("1".equals(pageOfficeSts)){
					mfNewTemplateTagBaseFeign.update(mfTemplateTagBase);
				}else {
				}
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String serialNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formtemplatetagebase0002 = formService.getFormData("templatetagebase0002");
		MfTemplateTagBase mfTemplateTagBase = new MfTemplateTagBase();
		mfTemplateTagBase.setSerialNo(serialNo);
		if("0".equals(pageOfficeSts)){
			mfTemplateTagBase = mfTemplateTagBaseFeign.getById(mfTemplateTagBase);
		}else if("1".equals(pageOfficeSts)){
			mfTemplateTagBase = mfNewTemplateTagBaseFeign.getById(mfTemplateTagBase);
		}else {
		}
		getObjValue(formtemplatetagebase0002, mfTemplateTagBase,formData);
		if(mfTemplateTagBase!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String serialNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfTemplateTagBase mfTemplateTagBase = new MfTemplateTagBase();
		mfTemplateTagBase.setSerialNo(serialNo);
		try {
			if("0".equals(pageOfficeSts)){
				mfTemplateTagBaseFeign.delete(mfTemplateTagBase);
			}else if("1".equals(pageOfficeSts)){
				mfNewTemplateTagBaseFeign.delete(mfTemplateTagBase);
			}else {
			}
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formtemplatetagebase0002 = formService.getFormData("templatetagebase0002");
		JSONArray paramTypeArray = new JSONArray();
		JSONArray bookmarkClassArray = new JSONArray();
		JSONObject json = new JSONObject();
		JSONObject tmpObject=null;
		String ajaxData = null;
		String bookData = null;
		Map<String,String>  dicMap = new CodeUtils().getMapByKeyName("PARAM_TYPE");
		for (Map.Entry<String, String> entry : dicMap.entrySet()) {
			tmpObject =new JSONObject();
			tmpObject.put("id", entry.getKey());
			tmpObject.put("name", entry.getValue());
			paramTypeArray.add(tmpObject);
		}
		json=new JSONObject();
		json.put("paramType", paramTypeArray);
		ajaxData = json.toString();
		/*  标签分组  BOOKMARK_CLASS */
		Map<String,String>  bookMap = new CodeUtils().getMapByKeyName("BOOKMARK_CLASS");
		for (Map.Entry<String, String> entry : bookMap.entrySet()) {
			tmpObject =new JSONObject();
			tmpObject.put("id", entry.getKey());
			tmpObject.put("name", entry.getValue());
			bookmarkClassArray.add(tmpObject);
		}
		json=new JSONObject();
		json.put("bookmarkClass", bookmarkClassArray);
		bookData = json.toString();

		model.addAttribute("formtemplatetagebase0002", formtemplatetagebase0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("bookData", bookData);
		model.addAttribute("query", "");
		return "/component/model/MfTemplateTagBase_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formtemplatetagebase0002 = formService.getFormData("templatetagebase0002");
		 getFormValue(formtemplatetagebase0002);
		MfTemplateTagBase  mfTemplateTagBase = new MfTemplateTagBase();
		 setObjValue(formtemplatetagebase0002, mfTemplateTagBase);
		if("0".equals(pageOfficeSts)){
			mfTemplateTagBaseFeign.insert(mfTemplateTagBase);
		}else if("1".equals(pageOfficeSts)){
			mfNewTemplateTagBaseFeign.insert(mfTemplateTagBase);
		}else {
		}
		 getObjValue(formtemplatetagebase0002, mfTemplateTagBase);
		 this.addActionMessage(model, "保存成功");
		 List<MfTemplateTagBase> mfTemplateTagBaseList = (List<MfTemplateTagBase>)mfTemplateTagBaseFeign.findByPage(this.getIpage(), mfTemplateTagBase).getResult();
		model.addAttribute("mfTemplateTagBaseList", mfTemplateTagBaseList);
		model.addAttribute("formtemplatetagebase0002", formtemplatetagebase0002);
		model.addAttribute("query", "");
		return "/component/model/MfTemplateTagBase_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String serialNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formtemplatetagebase0002 = formService.getFormData("templatetagebaseedit");
		 getFormValue(formtemplatetagebase0002);
		MfTemplateTagBase  mfTemplateTagBase = new MfTemplateTagBase();
		mfTemplateTagBase.setSerialNo(serialNo);
		if("0".equals(pageOfficeSts)){
			mfTemplateTagBase = mfTemplateTagBaseFeign.getTemplateTagBaseShowById(mfTemplateTagBase);
		}else if("1".equals(pageOfficeSts)){
			mfTemplateTagBase = mfNewTemplateTagBaseFeign.getTemplateTagBaseShowById(mfTemplateTagBase);
		}else {
		}
		 getObjValue(formtemplatetagebase0002, mfTemplateTagBase);
        JSONArray paramTypeArray = new JSONArray();
        JSONArray bookmarkClassArray = new JSONArray();
        JSONObject json = new JSONObject();
        JSONObject tmpObject=null;
        Map<String,String>  dicMap = new CodeUtils().getMapByKeyName("PARAM_TYPE");
        for (Map.Entry<String, String> entry : dicMap.entrySet()) {
            tmpObject =new JSONObject();
            tmpObject.put("id", entry.getKey());
            tmpObject.put("name", entry.getValue());
            paramTypeArray.add(tmpObject);
        }
        json=new JSONObject();
        json.put("paramType", paramTypeArray);
        String ajaxData = json.toString();
        /*  标签分组  BOOKMARK_CLASS */
        Map<String,String>  bookMap = new CodeUtils().getMapByKeyName("BOOKMARK_CLASS");
        for (Map.Entry<String, String> entry : bookMap.entrySet()) {
            tmpObject =new JSONObject();
            tmpObject.put("id", entry.getKey());
            tmpObject.put("name", entry.getValue());
            bookmarkClassArray.add(tmpObject);
        }
        json=new JSONObject();
        json.put("bookmarkClass", bookmarkClassArray);
        String bookData = json.toString();
		model.addAttribute("formtemplatetagebase0002", formtemplatetagebase0002);
		model.addAttribute("query", "");
		model.addAttribute("ajaxData", ajaxData);
        model.addAttribute("bookData", bookData);
		return "/component/model/MfTemplateTagBase_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String serialNo) throws Exception {
		ActionContext.initialize(request,
				response);
		MfTemplateTagBase mfTemplateTagBase = new MfTemplateTagBase();
		mfTemplateTagBase.setSerialNo(serialNo);
		if("0".equals(pageOfficeSts)){
			mfTemplateTagBaseFeign.delete(mfTemplateTagBase);
		}else if("1".equals(pageOfficeSts)){
			mfNewTemplateTagBaseFeign.delete(mfTemplateTagBase);
		}else {
		}
		return getListPage(model);
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formtemplatetagebase0002 = formService.getFormData("templatetagebase0002");
		 getFormValue(formtemplatetagebase0002);
		 boolean validateFlag = this.validateFormData(formtemplatetagebase0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formtemplatetagebase0002 = formService.getFormData("templatetagebase0002");
		 getFormValue(formtemplatetagebase0002);
		 boolean validateFlag = this.validateFormData(formtemplatetagebase0002);
	}
	/**
	 * 
	 * 方法描述： 更新启用状态
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-12-20 下午5:27:01
	 */
	@RequestMapping(value = "/updateUseFlagAjax")
	@ResponseBody
	public Map<String, Object> updateUseFlagAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfTemplateTagBase mfTemplateTagBase = new MfTemplateTagBase();
		try{
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			mfTemplateTagBase.setSerialNo(jobj.getString("serialNo"));
			if("0".equals(pageOfficeSts)){
				mfTemplateTagBase=mfTemplateTagBaseFeign.getById(mfTemplateTagBase);
				mfTemplateTagBase.setUseFlag(jobj.getString("useFlag"));
				mfTemplateTagBaseFeign.updateUseFlag(mfTemplateTagBase);
			}else if("1".equals(pageOfficeSts)){
				mfTemplateTagBase=mfNewTemplateTagBaseFeign.getById(mfTemplateTagBase);
				mfTemplateTagBase.setUseFlag(jobj.getString("useFlag"));
				mfNewTemplateTagBaseFeign.updateUseFlag(mfTemplateTagBase);
			}else {
			}
			String tagKeyName =mfTemplateTagBase.getTagKeyName();
			tagKeyName=tagKeyName.substring(1, tagKeyName.length()-1);
			mfTemplateTagBase.setTagKeyName(tagKeyName);
			List<MfTemplateTagBase> list = new ArrayList<MfTemplateTagBase>();
			list.add(mfTemplateTagBase);
			getTableDataList(list, tableId, dataMap);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	private void getTableDataList(List<MfTemplateTagBase> list, String tableId, Map<String, Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		Ipage ipage = this.getIpage();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", list, null,true);
		dataMap.put("tableData",tableHtml);
	}
	/**
	 * 
	 * 方法描述：获得分组最大顺序 
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2018-1-18 上午10:27:35
	 */
	@RequestMapping(value = "/getMaxSortAjax")
	@ResponseBody
	public Map<String, Object> getMaxSortAjax(String groupFlag) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfTemplateTagBase mfTemplateTagBase = new MfTemplateTagBase();
		try{
			Integer maxSort = 0;
			if("0".equals(pageOfficeSts)){
				 maxSort =mfTemplateTagBaseFeign.getMaxSort(groupFlag);
			}else if("1".equals(pageOfficeSts)){
				 maxSort =mfNewTemplateTagBaseFeign.getMaxSort(groupFlag);
			}else {
			}
			dataMap.put("maxSort", maxSort);
			dataMap.put("flag", "success");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
}
