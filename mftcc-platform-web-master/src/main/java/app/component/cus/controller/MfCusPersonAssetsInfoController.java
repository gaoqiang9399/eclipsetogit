package app.component.cus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mftcc.util.WaterIdUtil;
import config.YmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusPersonAssetsInfo;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusPersonAssetsInfoFeign;
import app.component.cus.feign.MfCusTableFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusPersonAssetsInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Apr 11 09:11:53 CST 2017
 **/
@Controller
@RequestMapping("/mfCusPersonAssetsInfo")
public class MfCusPersonAssetsInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusPersonAssetsInfoBo
	@Autowired
	private MfCusPersonAssetsInfoFeign mfCusPersonAssetsInfoFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	// 全局变量
	@Autowired
	private MfCusTableFeign mfCusTableFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private YmlConfig ymlConfig;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/cus/MfCusPersonAssetsInfo_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
		try {
			mfCusPersonAssetsInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusPersonAssetsInfo.setCriteriaList(mfCusPersonAssetsInfo, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusPersonAssetsInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusPersonAssetsInfo", mfCusPersonAssetsInfo));
			ipage = mfCusPersonAssetsInfoFeign.findByPage(ipage);
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
	 * @return LAND_CHARACTERISTICS
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusPersonAssetsInfoAction").getAddModel();
			}
			FormData formcusAssetsBase = formService.getFormData(formId);
			getFormValue(formcusAssetsBase, map);
			if (this.validateFormDataAnchor(formcusAssetsBase)) {
				MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
				setObjValue(formcusAssetsBase, mfCusPersonAssetsInfo);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusPersonAssetsInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusPersonAssetsInfo.setCusName(cusName);
				mfCusPersonAssetsInfo.setRelNo(mfCusCustomer.getCusNo());
				mfCusPersonAssetsInfoFeign.insert(mfCusPersonAssetsInfo);

				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusPersonAssetsInfo.getCusNo(),
						mfCusPersonAssetsInfo.getRelNo());// 更新客户信息完整度
				String cusNo = mfCusPersonAssetsInfo.getCusNo();
				String relNo = mfCusPersonAssetsInfo.getRelNo();
				mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
				mfCusPersonAssetsInfo.setCusNo(cusNo);
				mfCusPersonAssetsInfo.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusPersonAssetsInfo", mfCusPersonAssetsInfo));
				String tableFormId = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusPersonAssetsInfoAction").getListModel();
				if (StringUtil.isEmpty(tableFormId)) {
					tableFormId = "tablecuspersassets0001";
				}else{
					tableFormId = "table" + tableFormId;
				}
				String tableHtml = jtu.getJsonStr(tableFormId, "tableTag",
						(List<MfCusPersonAssetsInfo>) mfCusPersonAssetsInfoFeign.findByPage(ipage).getResult(), null,
						true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcuspersassets0002 = formService.getFormData("cuspersassets0002");
		getFormValue(formcuspersassets0002, getMapByJson(ajaxData));
		MfCusPersonAssetsInfo mfCusPersonAssetsInfoJsp = new MfCusPersonAssetsInfo();
		setObjValue(formcuspersassets0002, mfCusPersonAssetsInfoJsp);
		MfCusPersonAssetsInfo mfCusPersonAssetsInfo = mfCusPersonAssetsInfoFeign.getById(mfCusPersonAssetsInfoJsp);
		if (mfCusPersonAssetsInfo != null) {
			try {
				mfCusPersonAssetsInfo = (MfCusPersonAssetsInfo) EntityUtil.reflectionSetVal(mfCusPersonAssetsInfo,
						mfCusPersonAssetsInfoJsp, getMapByJson(ajaxData));
				mfCusPersonAssetsInfoFeign.update(mfCusPersonAssetsInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");

			mfCusCustomer.setCusNo(map.get("cusNo").toString());
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			String cusType = mfCusCustomer.getCusType();
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType(cusType, "MfCusPersonAssetsInfoAction").getAddModelDef();
			}
			FormData formcuspersassets0002 = formService.getFormData(formId);
			getFormValue(formcuspersassets0002, map);
			if (this.validateFormData(formcuspersassets0002)) {

				setObjValue(formcuspersassets0002, mfCusPersonAssetsInfo);
				mfCusPersonAssetsInfoFeign.update(mfCusPersonAssetsInfo);

				String cusNo = mfCusPersonAssetsInfo.getCusNo();
				String relNo = mfCusPersonAssetsInfo.getRelNo();
				mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
				mfCusPersonAssetsInfo.setCusNo(cusNo);
				mfCusPersonAssetsInfo.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusPersonAssetsInfo", mfCusPersonAssetsInfo));
				String tableFormId = mfCusFormConfigFeign.getByCusType(cusType, "MfCusPersonAssetsInfoAction").getListModel();
				if (StringUtil.isEmpty(tableFormId)) {
					tableFormId = "tablecuspersassets0001";
				}else{
					tableFormId = "table" + tableFormId;
				}
				String tableHtml = jtu.getJsonStr(tableFormId, "tableTag",
						(List<MfCusPersonAssetsInfo>) mfCusPersonAssetsInfoFeign.findByPage(ipage).getResult(), null,
						true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");

				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
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
	public Map<String, Object> getByIdAjax(String assetsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcuspersassets0002 = formService.getFormData("cuspersassets0002");
		MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
		mfCusPersonAssetsInfo.setAssetsId(assetsId);
		mfCusPersonAssetsInfo = mfCusPersonAssetsInfoFeign.getById(mfCusPersonAssetsInfo);
		getObjValue(formcuspersassets0002, mfCusPersonAssetsInfo, formData);
		if (mfCusPersonAssetsInfo != null) {
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
	public Map<String, Object> deleteAjax(String assetsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
		mfCusPersonAssetsInfo.setAssetsId(assetsId);
		try {
			mfCusPersonAssetsInfoFeign.delete(mfCusPersonAssetsInfo);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String cusNo, String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusAssetsBase = null;
		MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
		mfCusPersonAssetsInfo.setCusNo(cusNo);
		mfCusPersonAssetsInfo.setRelNo(relNo);
		mfCusPersonAssetsInfo.setAssetsId(WaterIdUtil.getWaterId());
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersonAssetsInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonAssetsInfoAction表单信息没有查询到");
		} else {
			formcusAssetsBase = formService.getFormData(formId);
			if (formcusAssetsBase.getFormId() == null) {
				// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonAssetsInfoAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcusAssetsBase);
				mfCusPersonAssetsInfo.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusAssetsBase, mfCusPersonAssetsInfo);
			}
		}
		String projectName= ymlConfig.getSysParams().get("sys.project.name");
		model.addAttribute("projectName", projectName);
		//默认所有权人是借款客户
		this.changeFormProperty(formcusAssetsBase, "assetsOwner", "initValue", mfCusCustomer.getCusName());

		FormData formHouseEval = formService.getFormData("houseEvalBase");
		FormData formHouseEvalMan = formService.getFormData("houseEvalBaseMan");
		this.changeFormProperty(formHouseEval, "cusNo", "initValue", mfCusCustomer.getCusNo());
		this.changeFormProperty(formHouseEval, "cusName", "initValue", mfCusCustomer.getCusName());
		this.changeFormProperty(formHouseEvalMan, "cusNo", "initValue", mfCusCustomer.getCusNo());
		this.changeFormProperty(formHouseEvalMan, "cusName", "initValue", mfCusCustomer.getCusName());
		model.addAttribute("formHouseEval", formHouseEval);
		model.addAttribute("formHouseEvalMan", formHouseEvalMan);

		model.addAttribute("formcusAssetsBase", formcusAssetsBase);
		model.addAttribute("query", "");
		model.addAttribute("cusType", mfCusCustomer.getCusType());
		return "/component/cus/MfCusPersonAssetsInfo_Insert";
	}

	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo, String relNo, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcuspersassets0002 = null;
		MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
		mfCusPersonAssetsInfo.setCusNo(cusNo);
		mfCusPersonAssetsInfo.setRelNo(relNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusPersonAssetsInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("产品类型为"+kindNo+"的MfCusPersonAssetsInfoAction表单信息没有查询到");
		} else {
			formcuspersassets0002 = formService.getFormData(formId);
			if (formcuspersassets0002.getFormId() == null) {
				// logger.error("产品类型为"+kindNo+"的MfCusPersonAssetsInfoAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcuspersassets0002);
				mfCusPersonAssetsInfo.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcuspersassets0002, mfCusPersonAssetsInfo);
			}
		}
		model.addAttribute("formcuspersassets0002", formcuspersassets0002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonAssetsInfo_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcuspersassets0002 = formService.getFormData("cuspersassets0002");
		getFormValue(formcuspersassets0002);
		MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
		setObjValue(formcuspersassets0002, mfCusPersonAssetsInfo);
		mfCusPersonAssetsInfoFeign.insert(mfCusPersonAssetsInfo);
		getObjValue(formcuspersassets0002, mfCusPersonAssetsInfo);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusPersonAssetsInfo", mfCusPersonAssetsInfo));
		List<MfCusPersonAssetsInfo> mfCusPersonAssetsInfoList = (List<MfCusPersonAssetsInfo>) mfCusPersonAssetsInfoFeign
				.findByPage(this.getIpage()).getResult();
		model.addAttribute("formcuspersassets0002", formcuspersassets0002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonAssetsInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String assetsId,String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
		String formId = "";

		mfCusPersonAssetsInfo.setAssetsId(assetsId);
		mfCusPersonAssetsInfo = mfCusPersonAssetsInfoFeign.getById(mfCusPersonAssetsInfo);
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		if(mfCusCustomer!=null){
			mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),"MfCusPersonAssetsInfoAction");
		}
		if(mfCusFormConfig!=null){
			formId = mfCusFormConfig.getAddModelDef();
		}else{
			formId = "cuspersassets0001";
		}
		FormData formcuspersassets0001 = formService.getFormData(formId);
		String flag = "update";
		getFormValue(formcuspersassets0001);

		getObjValue(formcuspersassets0001, mfCusPersonAssetsInfo);
		model.addAttribute("formcuspersassets0001", formcuspersassets0001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonAssetsInfo_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String assetsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
		mfCusPersonAssetsInfo.setAssetsId(assetsId);
		mfCusPersonAssetsInfoFeign.delete(mfCusPersonAssetsInfo);
		return getListPage(model);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcuspersassets0002 = formService.getFormData("cuspersassets0002");
		getFormValue(formcuspersassets0002);
		boolean validateFlag = this.validateFormData(formcuspersassets0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcuspersassets0002 = formService.getFormData("cuspersassets0002");
		getFormValue(formcuspersassets0002);
		boolean validateFlag = this.validateFormData(formcuspersassets0002);
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String assetsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersonAssetsInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonAssetsInfoAction表单信息没有查询到");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger", "3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
			mfCusPersonAssetsInfo.setAssetsId(assetsId);
			mfCusPersonAssetsInfo = mfCusPersonAssetsInfoFeign.getById(mfCusPersonAssetsInfo);
			FormData formcuspersassets0003 = formService.getFormData(formId);
			getObjValue(formcuspersassets0003, mfCusPersonAssetsInfo, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcuspersassets0003, "propertySeeTag", "");
			dataMap.put("formHtml", htmlStrCorp);
			dataMap.put("flag", "success");
			dataMap.put("formData", mfCusPersonAssetsInfo);
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateByOneAjax")
	@ResponseBody
	public Map<String, Object> updateByOneAjax(String ajaxData, String formId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
		// 这里得到的formId是带form字符串的，比如formcuscorp0001
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formcuspersassets0003 = formService.getFormData(formId);
		getFormValue(formcuspersassets0003, getMapByJson(ajaxData));
		MfCusPersonAssetsInfo mfCusPersonAssetsInfoNew = new MfCusPersonAssetsInfo();
		setObjValue(formcuspersassets0003, mfCusPersonAssetsInfoNew);
		mfCusPersonAssetsInfo.setAssetsId(mfCusPersonAssetsInfoNew.getAssetsId());
		mfCusPersonAssetsInfo = mfCusPersonAssetsInfoFeign.getById(mfCusPersonAssetsInfo);
		if (mfCusPersonAssetsInfo != null) {
			try {
				mfCusPersonAssetsInfo = (MfCusPersonAssetsInfo) EntityUtil.reflectionSetVal(mfCusPersonAssetsInfo,
						mfCusPersonAssetsInfoNew, getMapByJson(ajaxData));
				mfCusPersonAssetsInfoFeign.update(mfCusPersonAssetsInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

}
