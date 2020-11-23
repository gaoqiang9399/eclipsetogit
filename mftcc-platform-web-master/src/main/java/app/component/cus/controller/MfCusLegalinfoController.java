package app.component.cus.controller;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusLegalinfo;
import app.component.cus.feign.MfCusLegalinfoFeign;
import app.util.toolkit.Ipage;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
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
 * Title: MfCusAssetsDebtsController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Oct 30 15:56:17 CST 2018
 **/
@Controller
@RequestMapping(value = "/mfCusLegalinfo")
public class MfCusLegalinfoController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusLegalinfoFeign mfCusLegalinfoFeign;

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/cus/MfCusLegalinfo_List";
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
		MfCusLegalinfo mfCusLegalinfo = new MfCusLegalinfo();
		try {
			mfCusLegalinfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusLegalinfo.setCriteriaList(mfCusLegalinfo, ajaxData);//我的筛选
			//mfCusAssetsDebts.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusAssetsDebts,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfCusLegalinfo", mfCusLegalinfo));
			//自定义查询Feign方法
			ipage = mfCusLegalinfoFeign.findByPage(ipage);
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
		try{
			FormData formcuslegalinfodetail = formService.getFormData("cuslegalinfodetail");
			getFormValue(formcuslegalinfodetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcuslegalinfodetail)){
				MfCusLegalinfo mfCusLegalinfo = new MfCusLegalinfo();
				setObjValue(formcuslegalinfodetail, mfCusLegalinfo);
				mfCusLegalinfoFeign.insert(mfCusLegalinfo);

				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				FormData formcuslegalinfobase = formService.getFormData("cuslegalinfobase");
				getFormValue(formcuslegalinfobase, getMapByJson(ajaxData));
				String htmlStr = jsonFormUtil.getJsonStr(formcuslegalinfobase, "propertySeeTag", "");
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStrFlag","1");
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
		FormData formcuslegalinfobase = formService.getFormData("cuslegalinfobase");
		getFormValue(formcuslegalinfobase, getMapByJson(ajaxData));
		MfCusLegalinfo mfCusLegalinfoJsp = new MfCusLegalinfo();

		setObjValue(formcuslegalinfobase, mfCusLegalinfoJsp);
		MfCusLegalinfo mfCusLegalinfo = mfCusLegalinfoFeign.getById(mfCusLegalinfoJsp);
		if(mfCusLegalinfo!=null){
			try{
				mfCusLegalinfo = (MfCusLegalinfo)EntityUtil.reflectionSetVal(mfCusLegalinfo, mfCusLegalinfoJsp, getMapByJson(ajaxData));
				mfCusLegalinfoFeign.update(mfCusLegalinfo);
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
		MfCusLegalinfo mfCusLegalinfo = new MfCusLegalinfo();
		try{
			FormData formcuslegalinfobase = formService.getFormData("cuslegalinfobase");
			getFormValue(formcuslegalinfobase, getMapByJson(ajaxData));
			if(this.validateFormData(formcuslegalinfobase)){
				mfCusLegalinfo = new MfCusLegalinfo();
				setObjValue(formcuslegalinfobase, mfCusLegalinfo);
				mfCusLegalinfoFeign.update(mfCusLegalinfo);
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcuslegalinfobase = formService.getFormData("cuslegalinfobase");
		MfCusLegalinfo mfCusLegalinfo = new MfCusLegalinfo();
		mfCusLegalinfo.setLegalinfoId(id);
		mfCusLegalinfo = mfCusLegalinfoFeign.getById(mfCusLegalinfo);
		getObjValue(formcuslegalinfobase, mfCusLegalinfo,formData);
		if(mfCusLegalinfo!=null){
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
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusLegalinfo mfCusLegalinfo = new MfCusLegalinfo();
		mfCusLegalinfo.setLegalinfoId(id);
		try {
			mfCusLegalinfoFeign.delete(mfCusLegalinfo);
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
	public String input(Model model,String cusNo) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcuslegalinfodetail = formService.getFormData("cuslegalinfodetail");
		MfCusLegalinfo mfCusLegalinfo = new MfCusLegalinfo();
		mfCusLegalinfo.setCusNo(cusNo);
		getObjValue(formcuslegalinfodetail,mfCusLegalinfo);
		model.addAttribute("formcuslegalinfodetail", formcuslegalinfodetail);
		model.addAttribute("query", "");
		return "/component/cus/MfCusLegalinfo_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcuslegalinfobase = formService.getFormData("cuslegalinfobase");
		getFormValue(formcuslegalinfobase);
		MfCusLegalinfo mfCusLegalinfo = new MfCusLegalinfo();
		mfCusLegalinfo.setLegalinfoId(id);
		mfCusLegalinfo = mfCusLegalinfoFeign.getById(mfCusLegalinfo);
		getObjValue(formcuslegalinfobase, mfCusLegalinfo);
		model.addAttribute("formcuslegalinfobase", formcuslegalinfobase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusLegalinfo_Detail";
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
		FormData formcusAssetsDebtsDetail = formService.getFormData("cusAssetsDebtsDetail");
		getFormValue(formcusAssetsDebtsDetail);
		boolean validateFlag = this.validateFormData(formcusAssetsDebtsDetail);
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
		FormData formcusAssetsDebtsDetail = formService.getFormData("cusAssetsDebtsDetail");
		getFormValue(formcusAssetsDebtsDetail);
		boolean validateFlag = this.validateFormData(formcusAssetsDebtsDetail);
	}
}
