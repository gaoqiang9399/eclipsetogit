package app.component.cus.controller;

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

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.entity.MfCusBankAcceptanceBill;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusBankAcceptanceBillFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusTableFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusBankAcceptanceBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Feb 07 10:34:26 CST 2017
 **/
@Controller
@RequestMapping("/mfCusBankAcceptanceBill")
public class MfCusBankAcceptanceBillController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusBankAcceptanceBillFeign mfCusBankAcceptanceBillFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusTableFeign mfCusTableFeign;

	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String  getListPage(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		MfCusBankAcceptanceBill mfCusBankAcceptanceBill = new MfCusBankAcceptanceBill();
		mfCusBankAcceptanceBill.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusBankAcceptanceBill", mfCusBankAcceptanceBill));
		List<MfCusBankAcceptanceBill> mfCusBankAcceptanceBillList = (List<MfCusBankAcceptanceBill>)mfCusBankAcceptanceBillFeign.findByPage(ipage).getResult();
		model.addAttribute("mfCusBankAcceptanceBillList", mfCusBankAcceptanceBillList);
		return "/component/cus/MfCusBankAcceptanceBill_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusBankAcceptanceBill mfCusBankAcceptanceBill = new MfCusBankAcceptanceBill();
		try {
			mfCusBankAcceptanceBill.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusBankAcceptanceBill.setCriteriaList(mfCusBankAcceptanceBill, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusBankAcceptanceBill,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfCusBankAcceptanceBill", mfCusBankAcceptanceBill));
			ipage = mfCusBankAcceptanceBillFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcusbankbill0002 = formService.getFormData("cusbankbill0002");
			getFormValue(formcusbankbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcusbankbill0002)) {
				MfCusBankAcceptanceBill mfCusBankAcceptanceBill = new MfCusBankAcceptanceBill();
				setObjValue(formcusbankbill0002, mfCusBankAcceptanceBill);

				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusBankAcceptanceBill.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusBankAcceptanceBill.setCusName(cusName);
				mfCusBankAcceptanceBillFeign.insert(mfCusBankAcceptanceBill);

				// getTableData();
				mfCusBankAcceptanceBill = new MfCusBankAcceptanceBill();
				mfCusBankAcceptanceBill.setCusNo(mfCusCustomer.getCusNo());
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusBankAcceptanceBill", mfCusBankAcceptanceBill));
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr("tablecusbankbill0001", "tableTag",
						(List<MfCusBankAccManage>) mfCusBankAcceptanceBillFeign
								.findByPage(ipage).getResult(),
						null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");

				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusbankbill0002 = formService.getFormData("cusbankbill0002");
		getFormValue(formcusbankbill0002, getMapByJson(ajaxData));
		MfCusBankAcceptanceBill mfCusBankAcceptanceBillJsp = new MfCusBankAcceptanceBill();
		setObjValue(formcusbankbill0002, mfCusBankAcceptanceBillJsp);
		MfCusBankAcceptanceBill mfCusBankAcceptanceBill = mfCusBankAcceptanceBillFeign.getById(mfCusBankAcceptanceBillJsp);
		if (mfCusBankAcceptanceBill != null) {
			try {
				mfCusBankAcceptanceBill = (MfCusBankAcceptanceBill) EntityUtil.reflectionSetVal(mfCusBankAcceptanceBill,
						mfCusBankAcceptanceBillJsp, getMapByJson(ajaxData));
				mfCusBankAcceptanceBillFeign.update(mfCusBankAcceptanceBill);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
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
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		try{
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusBankAcceptanceBillAction").getAddModel();
			}
			FormData formcusbankbill0002 = formService.getFormData(formId);
			
			getFormValue(formcusbankbill0002, map);
			if(this.validateFormData(formcusbankbill0002)){
				MfCusBankAcceptanceBill mfCusBankAcceptanceBill = new MfCusBankAcceptanceBill();
				setObjValue(formcusbankbill0002, mfCusBankAcceptanceBill);
				mfCusBankAcceptanceBillFeign.update(mfCusBankAcceptanceBill);
				
				String cusNo = mfCusBankAcceptanceBill.getCusNo();
				mfCusBankAcceptanceBill = new MfCusBankAcceptanceBill();
				mfCusBankAcceptanceBill.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusBankAcceptanceBill", mfCusBankAcceptanceBill));
				JsonTableUtil jtu = new JsonTableUtil();
				String  tableHtml = jtu.getJsonStr("tablecusbankbill0001","tableTag", (List<MfCusBankAccManage>)mfCusBankAcceptanceBillFeign.findByPage(ipage).getResult(), null,true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag","1");
				
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusbankbill0002 = formService.getFormData("cusbankbill0002");
		MfCusBankAcceptanceBill mfCusBankAcceptanceBill = new MfCusBankAcceptanceBill();
		mfCusBankAcceptanceBill.setId(id);
		mfCusBankAcceptanceBill = mfCusBankAcceptanceBillFeign.getById(mfCusBankAcceptanceBill);
		getObjValue(formcusbankbill0002, mfCusBankAcceptanceBill, formData);
		if (mfCusBankAcceptanceBill != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusBankAcceptanceBill mfCusBankAcceptanceBill = new MfCusBankAcceptanceBill();
		mfCusBankAcceptanceBill.setId(id);
		try {
			mfCusBankAcceptanceBillFeign.delete(mfCusBankAcceptanceBill);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfCusBankAcceptanceBill mfCusBankAcceptanceBill = new MfCusBankAcceptanceBill();
		mfCusBankAcceptanceBill.setCusNo(cusNo);
		String formId="";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusBankAcceptanceBillAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
//			logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusBankAcceptanceBillAction表单信息没有查询到");
		} else {
			FormData formcusbankbill0002 = formService.getFormData(formId);
			if (formcusbankbill0002.getFormId() == null) {
//				logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusBankAcceptanceBillAction表单form" + formId
//						+ ".xml文件不存在");
			} else {
				getFormValue(formcusbankbill0002);
				mfCusBankAcceptanceBill.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusbankbill0002, mfCusBankAcceptanceBill);
			}
			model.addAttribute("formcusbankbill0002", formcusbankbill0002);
			model.addAttribute("query", "");
		}
		return "/component/cus/MfCusBankAcceptanceBill_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusbankbill0002 = formService.getFormData("cusbankbill0002");
		getFormValue(formcusbankbill0002);
		MfCusBankAcceptanceBill mfCusBankAcceptanceBill = new MfCusBankAcceptanceBill();
		setObjValue(formcusbankbill0002, mfCusBankAcceptanceBill);
		mfCusBankAcceptanceBillFeign.insert(mfCusBankAcceptanceBill);
		getObjValue(formcusbankbill0002, mfCusBankAcceptanceBill);
		this.addActionMessage(null, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusBankAcceptanceBill", mfCusBankAcceptanceBill));
		List<MfCusBankAcceptanceBill> mfCusBankAcceptanceBillList = (List<MfCusBankAcceptanceBill>) mfCusBankAcceptanceBillFeign
				.findByPage(ipage).getResult();
		model.addAttribute("mfCusBankAcceptanceBillList", mfCusBankAcceptanceBillList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusBankAcceptanceBill_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusbankbill0002 = formService.getFormData("cusbankbill0002");
		getFormValue(formcusbankbill0002);
		MfCusBankAcceptanceBill mfCusBankAcceptanceBill = new MfCusBankAcceptanceBill();
		mfCusBankAcceptanceBill.setId(id);
		mfCusBankAcceptanceBill = mfCusBankAcceptanceBillFeign.getById(mfCusBankAcceptanceBill);
		getObjValue(formcusbankbill0002, mfCusBankAcceptanceBill);
		model.addAttribute("formcusbankbill0002", formcusbankbill0002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusBankAcceptanceBill_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model,String id) throws Exception {
		ActionContext.initialize(request, response);
		MfCusBankAcceptanceBill mfCusBankAcceptanceBill = new MfCusBankAcceptanceBill();
		mfCusBankAcceptanceBill.setId(id);
		mfCusBankAcceptanceBillFeign.delete(mfCusBankAcceptanceBill);
		return getListPage(model, id);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusbankbill0002 = formService.getFormData("cusbankbill0002");
		getFormValue(formcusbankbill0002);
		boolean validateFlag = this.validateFormData(formcusbankbill0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusbankbill0002 = formService.getFormData("cusbankbill0002");
		getFormValue(formcusbankbill0002);
		boolean validateFlag = this.validateFormData(formcusbankbill0002);
	}
}
