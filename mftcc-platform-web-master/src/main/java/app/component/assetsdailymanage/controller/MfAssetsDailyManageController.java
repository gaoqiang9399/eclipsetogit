package app.component.assetsdailymanage.controller;

import app.base.User;
import app.component.assetsdailymanage.entity.MfAssetsDailyManage;
import app.component.assetsdailymanage.feign.MfAssetsDailyManageFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfAssetsDailyManageController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Nov 02 16:12:44 CST 2018
 **/
@Controller
@RequestMapping(value = "/mfAssetsDailyManage")
public class MfAssetsDailyManageController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfAssetsDailyManageFeign mfAssetsDailyManageFeign;


	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/assetsdailymanage/MfAssetsDailyManage_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfAssetsDailyManage mfAssetsDailyManage = new MfAssetsDailyManage();
		try {
			mfAssetsDailyManage.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfAssetsDailyManage.setCriteriaList(mfAssetsDailyManage, ajaxData);//我的筛选
			//mfAssetsDailyManage.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfAssetsDailyManage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfAssetsDailyManage", mfAssetsDailyManage));
			//自定义查询Feign方法
			ipage = mfAssetsDailyManageFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
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
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonTableUtil jtu = new JsonTableUtil();
		try{
			FormData formassetsDailyManageBase = formService.getFormData("assetsDailyManageBase");
			getFormValue(formassetsDailyManageBase, getMapByJson(ajaxData));
			if(this.validateFormData(formassetsDailyManageBase)){
				MfAssetsDailyManage mfAssetsDailyManage = new MfAssetsDailyManage();
				setObjValue(formassetsDailyManageBase, mfAssetsDailyManage);
				mfAssetsDailyManageFeign.insert(mfAssetsDailyManage);
				/*List<MfAssetsDailyManage> mfAssetsDailyManagelList = mfAssetsDailyManageFeign.getListByAssetsManageId(mfAssetsDailyManage);
				String dailyManageHtml = jtu.getJsonStr("tableassetsdailymanageList", "tableTag",
						mfAssetsDailyManagelList, null, true);
				dataMap.put("dailyManageHtml", dailyManageHtml);*/
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/callBackAjax")
	@ResponseBody
	public Map<String, Object> callBackAjax(String assetsManageId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonTableUtil jtu = new JsonTableUtil();
		try{
				MfAssetsDailyManage mfAssetsDailyManage = new MfAssetsDailyManage();
				mfAssetsDailyManage.setAssetsManageId(assetsManageId);
				List<MfAssetsDailyManage> mfAssetsDailyManagelList = mfAssetsDailyManageFeign.getListByAssetsManageId(mfAssetsDailyManage);
				String dailyManageHtml = jtu.getJsonStr("tableassetsdailymanageList", "tableTag",
						mfAssetsDailyManagelList, null, true);
				dataMap.put("dailyManageHtml", dailyManageHtml);
				dataMap.put("flag", "success");
				dataMap.put("msg", "查询成功");

		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "查询失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassetsDailyManageBase = formService.getFormData("assetsDailyManageBase");
		getFormValue(formassetsDailyManageBase, getMapByJson(ajaxData));
		MfAssetsDailyManage mfAssetsDailyManageJsp = new MfAssetsDailyManage();
		setObjValue(formassetsDailyManageBase, mfAssetsDailyManageJsp);
		MfAssetsDailyManage mfAssetsDailyManage = mfAssetsDailyManageFeign.getById(mfAssetsDailyManageJsp);
		if(mfAssetsDailyManage!=null){
			try{
				mfAssetsDailyManage = (MfAssetsDailyManage)EntityUtil.reflectionSetVal(mfAssetsDailyManage, mfAssetsDailyManageJsp, getMapByJson(ajaxData));
				mfAssetsDailyManageFeign.update(mfAssetsDailyManage);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
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
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonTableUtil jtu = new JsonTableUtil();
		MfAssetsDailyManage mfAssetsDailyManage = new MfAssetsDailyManage();
		try{
			FormData formassetsDailyManageBase = formService.getFormData("assetsDailyManageBase");
			getFormValue(formassetsDailyManageBase, getMapByJson(ajaxData));
			if(this.validateFormData(formassetsDailyManageBase)){
				mfAssetsDailyManage = new MfAssetsDailyManage();
				setObjValue(formassetsDailyManageBase, mfAssetsDailyManage);
				mfAssetsDailyManageFeign.update(mfAssetsDailyManage);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
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
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String recordId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formassetsDailyManageBase = formService.getFormData("assetsDailyManageBase");
		MfAssetsDailyManage mfAssetsDailyManage = new MfAssetsDailyManage();
		mfAssetsDailyManage.setRecordId(recordId);
		mfAssetsDailyManage = mfAssetsDailyManageFeign.getById(mfAssetsDailyManage);
		getObjValue(formassetsDailyManageBase, mfAssetsDailyManage,formData);
		if(mfAssetsDailyManage!=null){
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
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String recordId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfAssetsDailyManage mfAssetsDailyManage = new MfAssetsDailyManage();
		mfAssetsDailyManage.setRecordId(recordId);
		try {
			mfAssetsDailyManageFeign.delete(mfAssetsDailyManage);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
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
	@RequestMapping("/input")
	public String input(Model model,String assetsManageId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formassetsDailyManageBase = formService.getFormData("assetsDailyManageBase");
		MfAssetsDailyManage mfAssetsDailyManage = new MfAssetsDailyManage();
		mfAssetsDailyManage.setOpNo(User.getRegNo(request));
		mfAssetsDailyManage.setOpName(User.getRegName(request));
		mfAssetsDailyManage.setAssetsManageId(assetsManageId);
		getObjValue(formassetsDailyManageBase,mfAssetsDailyManage);
		model.addAttribute("formassetsDailyManageBase", formassetsDailyManageBase);
		model.addAttribute("assetsManageId", assetsManageId);
		model.addAttribute("query", "");
		return "/component/assetsdailymanage/MfAssetsDailyManage_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String recordId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formassetsDailyManageDetail = formService.getFormData("assetsDailyManageDetail");
		getFormValue(formassetsDailyManageDetail);
		MfAssetsDailyManage mfAssetsDailyManage = new MfAssetsDailyManage();
		mfAssetsDailyManage.setRecordId(recordId);
		mfAssetsDailyManage = mfAssetsDailyManageFeign.getById(mfAssetsDailyManage);
		getObjValue(formassetsDailyManageDetail, mfAssetsDailyManage);
		model.addAttribute("formassetsDailyManageDetail", formassetsDailyManageDetail);
		model.addAttribute("query", "");
		return "/component/assetsdailymanage/MfAssetsDailyManage_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formassetsDailyManageBase = formService.getFormData("assetsDailyManageBase");
		getFormValue(formassetsDailyManageBase);
		boolean validateFlag = this.validateFormData(formassetsDailyManageBase);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formassetsDailyManageBase = formService.getFormData("assetsDailyManageBase");
		getFormValue(formassetsDailyManageBase);
		boolean validateFlag = this.validateFormData(formassetsDailyManageBase);
	}
}
