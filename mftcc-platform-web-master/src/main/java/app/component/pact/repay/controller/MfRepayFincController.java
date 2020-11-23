package app.component.pact.repay.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import app.component.common.EntityUtil;
import app.component.pact.entity.MfRepayFinc;
import app.component.pact.repay.feign.MfRepayFincFeign;
import app.util.toolkit.Ipage;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: MfRepayFincController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu May 23 10:44:41 CST 2019
 **/
@Controller
@RequestMapping(value = "/mfRepayFinc")
public class MfRepayFincController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfRepayFincFeign mfRepayFincFeign;

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/pact/repayapp/MfRepayFinc_List";
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
		MfRepayFinc mfRepayFinc = new MfRepayFinc();
		try {
			mfRepayFinc.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfRepayFinc.setCriteriaList(mfRepayFinc, ajaxData);//我的筛选
			//mfRepayFinc.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfRepayFinc,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfRepayFinc", mfRepayFinc));
			//自定义查询Feign方法
			ipage = mfRepayFincFeign.findByPage(ipage);
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
			FormData formmfRepayFincAdd = formService.getFormData("mfRepayFincAdd");
			getFormValue(formmfRepayFincAdd, getMapByJson(ajaxData));
			if(this.validateFormData(formmfRepayFincAdd)){
				MfRepayFinc mfRepayFinc = new MfRepayFinc();
				setObjValue(formmfRepayFincAdd, mfRepayFinc);
				mfRepayFincFeign.insert(mfRepayFinc);
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
		FormData formmfRepayFincAdd = formService.getFormData("mfRepayFincAdd");
		getFormValue(formmfRepayFincAdd, getMapByJson(ajaxData));
		MfRepayFinc mfRepayFincJsp = new MfRepayFinc();
		setObjValue(formmfRepayFincAdd, mfRepayFincJsp);
		MfRepayFinc mfRepayFinc = mfRepayFincFeign.getById(mfRepayFincJsp);
		if(mfRepayFinc!=null){
			try{
				mfRepayFinc = (MfRepayFinc)EntityUtil.reflectionSetVal(mfRepayFinc, mfRepayFincJsp, getMapByJson(ajaxData));
				mfRepayFincFeign.update(mfRepayFinc);
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
		MfRepayFinc mfRepayFinc = new MfRepayFinc();
		try{
			FormData formmfRepayFincAdd = formService.getFormData("mfRepayFincAdd");
			getFormValue(formmfRepayFincAdd, getMapByJson(ajaxData));
			if(this.validateFormData(formmfRepayFincAdd)){
				mfRepayFinc = new MfRepayFinc();
				setObjValue(formmfRepayFincAdd, mfRepayFinc);
				mfRepayFincFeign.update(mfRepayFinc);
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
	public Map<String, Object> getByIdAjax(String repayFincId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formmfRepayFincAdd = formService.getFormData("mfRepayFincAdd");
		MfRepayFinc mfRepayFinc = new MfRepayFinc();
		mfRepayFinc.setRepayFincId(repayFincId);
		mfRepayFinc = mfRepayFincFeign.getById(mfRepayFinc);
		getObjValue(formmfRepayFincAdd, mfRepayFinc,formData);
		if(mfRepayFinc!=null){
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
	public Map<String, Object> deleteAjax(String repayFincId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfRepayFinc mfRepayFinc = new MfRepayFinc();
		mfRepayFinc.setRepayFincId(repayFincId);
		try {
			mfRepayFincFeign.delete(mfRepayFinc);
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
	public String input(Model model) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formmfRepayFincAdd = formService.getFormData("mfRepayFincAdd");
		model.addAttribute("formmfRepayFincAdd", formmfRepayFincAdd);
		model.addAttribute("query", "");
		return "/component/pact/repayapp/MfRepayFinc_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String repayFincId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formmfRepayFincDetail = formService.getFormData("mfRepayFincDetail");
		getFormValue(formmfRepayFincDetail);
		MfRepayFinc mfRepayFinc = new MfRepayFinc();
		mfRepayFinc.setRepayFincId(repayFincId);
		mfRepayFinc = mfRepayFincFeign.getById(mfRepayFinc);
		getObjValue(formmfRepayFincDetail, mfRepayFinc);
		model.addAttribute("formmfRepayFincDetail", formmfRepayFincDetail);
		model.addAttribute("query", "");
		return "/component/pact/repayapp/MfRepayFinc_Detail";
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
		FormData formmfRepayFincAdd = formService.getFormData("mfRepayFincAdd");
		getFormValue(formmfRepayFincAdd);
		boolean validateFlag = this.validateFormData(formmfRepayFincAdd);
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
		FormData formmfRepayFincAdd = formService.getFormData("mfRepayFincAdd");
		getFormValue(formmfRepayFincAdd);
		boolean validateFlag = this.validateFormData(formmfRepayFincAdd);
	}
}
