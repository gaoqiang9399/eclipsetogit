package app.component.app.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.app.entity.MfBusDocSupplement;
import app.component.app.feign.MfBusDocSupplementFeign;
import app.util.toolkit.Ipage;

@Controller
@RequestMapping("/mfBusDocSupplement")
public class MfBusDocSupplementController extends BaseFormBean {
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusDocSupplementFeign mfBusDocSupplementFeign;
	// 全局变量
	//异步参数
	//表单变量
	
	/**
	 *  列表打开页面请求
	 * @return
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model){
		return "component/app/MfBusDocSupplement_List";
		
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBusDocSupplement mfBusDocSupplement = new MfBusDocSupplement();
		try {
			mfBusDocSupplement.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusDocSupplement.setCriteriaList(mfBusDocSupplement, ajaxData);//我的筛选
			//this.getRoleConditions(appEval,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusDocSupplement", mfBusDocSupplement));
			//自定义查询Bo方法
			ipage = mfBusDocSupplementFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List<?>)ipage.getResult(), ipage,true);
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
	
	@RequestMapping(value = "/getById")
	public String getById(Model model,String cusNo,String pactNo,String pactId,String fincId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusDocSupplement mfBusDocSupplement = new MfBusDocSupplement();
		FormData formmfBusDocSupplementDetail = formService.getFormData("mfBusDocSupplementDetail");
		try {
			mfBusDocSupplement.setCusNo(cusNo);
			mfBusDocSupplement.setPactId(pactId);
			mfBusDocSupplement.setPactNo(pactNo);
			mfBusDocSupplement.setFincId(fincId);
			mfBusDocSupplement = mfBusDocSupplementFeign.getById(mfBusDocSupplement);
			
			getObjValue(formmfBusDocSupplementDetail, mfBusDocSupplement);
			
		} catch (Exception e) {
			dataMap.put("flag", "error");
			throw e;
		}
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("pactNo", pactNo);
		model.addAttribute("pactId", pactId);
		model.addAttribute("cusType", mfBusDocSupplement.getCusType());
		model.addAttribute("mfBusDocSupplement", mfBusDocSupplement);
		model.addAttribute("formmfBusDocSupplementDetail", formmfBusDocSupplementDetail);
		model.addAttribute("query", "");
		
		return "/component/app/MfBusDocSupplement_Detail";
	}



}
