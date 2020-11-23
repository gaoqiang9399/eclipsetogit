package app.component.cus.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusAssetsDebtsFeign;
import app.component.cus.entity.MfCusAssetsDebts;
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

/**
 * Title: MfCusAssetsDebtsController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Oct 30 15:56:17 CST 2018
 **/
@Controller
@RequestMapping(value = "/mfCusAssetsDebts")
public class MfCusAssetsDebtsController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusAssetsDebtsFeign mfCusAssetsDebtsFeign;
	//全局变量
	private String query = "";//初始化query为空
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/cus/MfCusAssetsDebts_List";
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
		MfCusAssetsDebts mfCusAssetsDebts = new MfCusAssetsDebts();
		try {
			mfCusAssetsDebts.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusAssetsDebts.setCriteriaList(mfCusAssetsDebts, ajaxData);//我的筛选
			//mfCusAssetsDebts.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusAssetsDebts,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfCusAssetsDebts", mfCusAssetsDebts));
			//自定义查询Feign方法
			ipage = mfCusAssetsDebtsFeign.findByPage(ipage);
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
			FormData formcusAssetsDebtsDetail = formService.getFormData("cusAssetsDebtsDetail");
			getFormValue(formcusAssetsDebtsDetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcusAssetsDebtsDetail)){
				MfCusAssetsDebts mfCusAssetsDebts = new MfCusAssetsDebts();
				setObjValue(formcusAssetsDebtsDetail, mfCusAssetsDebts);
				mfCusAssetsDebtsFeign.insert(mfCusAssetsDebts);

				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				FormData formcusAssetsDebtsBase = formService.getFormData("cusAssetsDebtsBase");
				getFormValue(formcusAssetsDebtsBase, getMapByJson(ajaxData));
				String htmlStr = jsonFormUtil.getJsonStr(formcusAssetsDebtsBase, "propertySeeTag", "");
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
		FormData formcusAssetsDebtsDetail = formService.getFormData("cusAssetsDebtsBase");
		getFormValue(formcusAssetsDebtsDetail, getMapByJson(ajaxData));
		MfCusAssetsDebts mfCusAssetsDebtsJsp = new MfCusAssetsDebts();
		setObjValue(formcusAssetsDebtsDetail, mfCusAssetsDebtsJsp);
		MfCusAssetsDebts mfCusAssetsDebts = mfCusAssetsDebtsFeign.getById(mfCusAssetsDebtsJsp);
		if(mfCusAssetsDebts!=null){
			try{
				mfCusAssetsDebts = (MfCusAssetsDebts)EntityUtil.reflectionSetVal(mfCusAssetsDebts, mfCusAssetsDebtsJsp, getMapByJson(ajaxData));
				mfCusAssetsDebtsFeign.update(mfCusAssetsDebts);
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
		MfCusAssetsDebts mfCusAssetsDebts = new MfCusAssetsDebts();
		try{
			FormData formcusAssetsDebtsDetail = formService.getFormData("cusAssetsDebtsDetail");
			getFormValue(formcusAssetsDebtsDetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcusAssetsDebtsDetail)){
				mfCusAssetsDebts = new MfCusAssetsDebts();
				setObjValue(formcusAssetsDebtsDetail, mfCusAssetsDebts);
				mfCusAssetsDebtsFeign.update(mfCusAssetsDebts);

				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusAssetsDebts.getCusNo());

				FormData formcusAssetsDebtsBase = formService.getFormData("cusAssetsDebtsBase");
				if(formcusAssetsDebtsBase.getFormId() == null){
					//log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersBaseInfoAction表单form"+formId+".xml文件不存在");
				}
				getFormValue(formcusAssetsDebtsBase);
				getObjValue(formcusAssetsDebtsBase, mfCusCustomer);
				getObjValue(formcusAssetsDebtsBase, mfCusAssetsDebts);
				this.getHttpRequest().setAttribute("ifBizManger", "3");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcusAssetsDebtsBase,"propertySeeTag",query);

				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStrFlag","1");

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
		FormData formcusAssetsDebtsDetail = formService.getFormData("cusAssetsDebtsDetail");
		MfCusAssetsDebts mfCusAssetsDebts = new MfCusAssetsDebts();
		mfCusAssetsDebts.setId(id);
		mfCusAssetsDebts = mfCusAssetsDebtsFeign.getById(mfCusAssetsDebts);
		getObjValue(formcusAssetsDebtsDetail, mfCusAssetsDebts,formData);
		if(mfCusAssetsDebts!=null){
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
		MfCusAssetsDebts mfCusAssetsDebts = new MfCusAssetsDebts();
		mfCusAssetsDebts.setId(id);
		try {
			mfCusAssetsDebtsFeign.delete(mfCusAssetsDebts);
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
		FormData formcusAssetsDebtsDetail = formService.getFormData("cusAssetsDebtsDetail");
		MfCusAssetsDebts mfCusAssetsDebts = new MfCusAssetsDebts();
		mfCusAssetsDebts.setCusNo(cusNo);
		getObjValue(formcusAssetsDebtsDetail,mfCusAssetsDebts);
		model.addAttribute("formcusAssetsDebtsDetail", formcusAssetsDebtsDetail);
		model.addAttribute("query", "");
		return "/component/cus/MfCusAssetsDebts_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String cusNo) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusAssetsDebtsDetail = formService.getFormData("cusAssetsDebtsDetail");
		getFormValue(formcusAssetsDebtsDetail);
		MfCusAssetsDebts mfCusAssetsDebts = new MfCusAssetsDebts();
		mfCusAssetsDebts.setCusNo(cusNo);
		mfCusAssetsDebts = mfCusAssetsDebtsFeign.getById(mfCusAssetsDebts);
		getObjValue(formcusAssetsDebtsDetail, mfCusAssetsDebts);
		model.addAttribute("formcusAssetsDebtsDetail", formcusAssetsDebtsDetail);
		model.addAttribute("query", "");
		return "/component/cus/MfCusAssetsDebts_Detail";
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
