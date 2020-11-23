package app.component.cus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import app.component.cus.entity.MfCusLegalEquityInfo;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusLegalEquityInfoFeign;
import app.component.cus.feign.MfCusTableFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;

/**
 * Title: MfCusLegalEquityInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Jun 03 15:20:52 CST 2017
 **/
@Controller
@RequestMapping("/mfCusLegalEquityInfo")
public class MfCusLegalEquityInfoController extends BaseFormBean {
	private Logger logger = LoggerFactory.getLogger(MfCusLegalEquityInfoController.class);
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusLegalEquityInfoBo
	@Autowired
	private MfCusLegalEquityInfoFeign mfCusLegalEquityInfoFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusTableFeign mfCusTableFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @param cusNo
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
		mfCusLegalEquityInfo.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusLegalEquityInfo", mfCusLegalEquityInfo));
		List<MfCusLegalEquityInfo> mfCusLegalEquityInfoList = (List<MfCusLegalEquityInfo>) mfCusLegalEquityInfoFeign
				.findByPage(ipage).getResult();
		model.addAttribute("mfCusLegalEquityInfo", mfCusLegalEquityInfo);
		model.addAttribute("mfCusLegalEquityInfoList", mfCusLegalEquityInfoList);
		model.addAttribute("query", "");
		return "component/cus/MfCusLegalEquityInfo_List";
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
		MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
		try {
			mfCusLegalEquityInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusLegalEquityInfo.setCriteriaList(mfCusLegalEquityInfo, ajaxData);// 我的筛选
			// mfCusLegalEquityInfo.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusLegalEquityInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusLegalEquityInfo", mfCusLegalEquityInfo));
			ipage = mfCusLegalEquityInfoFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusEquityInfoAction").getAddModel();
			}
			FormData formcusequ00003 = formService.getFormData(formId);

			getFormValue(formcusequ00003, map);
			if (this.validateFormData(formcusequ00003)) {
				MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
				setObjValue(formcusequ00003, mfCusLegalEquityInfo);
				if (mfCusLegalEquityInfo.getPushCapitalType().length() == 1) {
					mfCusLegalEquityInfo.setPushCapitalType(mfCusLegalEquityInfo.getPushCapitalType() + "|");
				}
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusLegalEquityInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusLegalEquityInfo.setCusName(cusName);
				logger.info("社会信用代码问题客户cusNo:"+mfCusLegalEquityInfo.getCusNo()+",idnum:"+mfCusCustomer.getIdNum()+"传入orgNo:"+mfCusLegalEquityInfo.getOrgNo());
				if(mfCusCustomer.getIdNum()!=null && mfCusCustomer.getIdNum().equals(mfCusLegalEquityInfo.getOrgNo())){
					dataMap.put("flag", "error");
					dataMap.put("msg", "社会信用代码不能与当前客户社会信用代码相同");
					return dataMap;
				}
				mfCusLegalEquityInfoFeign.insert(mfCusLegalEquityInfo);
				// getTableData();
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusLegalEquityInfo.getCusNo(),
						mfCusLegalEquityInfo.getRelNo());// 更新客户信息完整度
				String relNo = mfCusLegalEquityInfo.getRelNo();
				mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
				mfCusLegalEquityInfo.setCusNo(mfCusCustomer.getCusNo());
				mfCusLegalEquityInfo.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusLegalEquityInfo", mfCusLegalEquityInfo));
				String tableHtml = jtu.getJsonStr("tablecuslegalequ00001", "tableTag",
						(List<MfCusLegalEquityInfo>) mfCusLegalEquityInfoFeign.findByPage(ipage).getResult(), null,
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
		FormData formcusequ00002 = formService.getFormData("cusequ00002");
		getFormValue(formcusequ00002, getMapByJson(ajaxData));
		MfCusLegalEquityInfo mfCusLegalEquityInfoJsp = new MfCusLegalEquityInfo();
		setObjValue(formcusequ00002, mfCusLegalEquityInfoJsp);
		MfCusLegalEquityInfo mfCusLegalEquityInfo = mfCusLegalEquityInfoFeign.getById(mfCusLegalEquityInfoJsp);
		if (mfCusLegalEquityInfo != null) {
			try {
				mfCusLegalEquityInfo = (MfCusLegalEquityInfo) EntityUtil.reflectionSetVal(mfCusLegalEquityInfo,
						mfCusLegalEquityInfoJsp, getMapByJson(ajaxData));
				mfCusLegalEquityInfoFeign.update(mfCusLegalEquityInfo);
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
		try {

			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusLegalEquityInfoAction").getAddModel();
			}
			FormData formcusequ00003 = formService.getFormData(formId);

			getFormValue(formcusequ00003, map);
			if (this.validateFormData(formcusequ00003)) {
				MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
				setObjValue(formcusequ00003, mfCusLegalEquityInfo);
				mfCusLegalEquityInfoFeign.update(mfCusLegalEquityInfo);

				// getTableData();
				String cusNo = mfCusLegalEquityInfo.getCusNo();
				mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
				mfCusLegalEquityInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusLegalEquityInfo", mfCusLegalEquityInfo));
				String tableHtml = jtu.getJsonStr("tablecuslegalequ00001", "tableTag",
						(List<MfCusLegalEquityInfo>) mfCusLegalEquityInfoFeign.findByPage(ipage).getResult(), null,
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
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
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
	public Map<String, Object> getByIdAjax(String equityInfoId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusequ00002 = formService.getFormData("cusequ00002");
		MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
		mfCusLegalEquityInfo.setEquityInfoId(equityInfoId);
		mfCusLegalEquityInfo = mfCusLegalEquityInfoFeign.getById(mfCusLegalEquityInfo);
		getObjValue(formcusequ00002, mfCusLegalEquityInfo, formData);
		if (mfCusLegalEquityInfo != null) {
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
	public Map<String, Object> deleteAjax(String equityInfoId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
		mfCusLegalEquityInfo.setEquityInfoId(equityInfoId);
		try {
			mfCusLegalEquityInfoFeign.delete(mfCusLegalEquityInfo);
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
	 * @param cusNo
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusequ00002 = formService.getFormData("cusequ00002");

		MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
		mfCusLegalEquityInfo.setCusNo(cusNo);
		mfCusLegalEquityInfo.setRelNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		mfCusLegalEquityInfo.setCusName(mfCusCustomer.getCusName());
		mfCusLegalEquityInfo.setCusNo(mfCusCustomer.getCusNo());
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusLegalEquityInfoAction");
		String formId = null;
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusLegalEquityInfoAction表单信息没有查询到");
		} else {
			FormData formcusequ00003 = formService.getFormData(formId);
			if (formcusequ00003.getFormId() == null) {
				// logger.error("客户类型为" + mfCusCustomer.getCusType() +
				// "的MfCusLegalEquityInfoAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcusequ00003);
				getObjValue(formcusequ00003, mfCusLegalEquityInfo);
			}
			model.addAttribute("formcusequ00003", formcusequ00003);
		}
		JSONArray map = new CodeUtils().getJSONArrayByKeyName("PUSH_CAPITAL_TYPE");
		String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
		model.addAttribute("mfCusLegalEquityInfo", mfCusLegalEquityInfo);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfCusCustomer", mfCusFormConfig);
		model.addAttribute("formId", formId);
		model.addAttribute("ajaxData", items);
		model.addAttribute("query", "");
		return "component/cus/MfCusLegalEquityInfo_Insert";
	}

	/**
	 * 
	 * 方法描述： 业务新增客户信息表单页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @param cusNo
	 * @param relNo
	 * @param kindNo
	 * @date 2017-9-26 上午11:03:57
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String ajaxData, String cusNo, String relNo, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusequ00002 = formService.getFormData("cusequ00002");

		MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
		mfCusLegalEquityInfo.setCusNo(cusNo);
		mfCusLegalEquityInfo.setRelNo(relNo);

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		mfCusLegalEquityInfo.setCusName(mfCusCustomer.getCusName());
		mfCusLegalEquityInfo.setCusNo(mfCusCustomer.getCusNo());
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusLegalEquityInfoAction");
		String formId = null;
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("产品类型为" + kindNo + "的MfCusLegalEquityInfoAction表单信息没有查询到");
		} else {
			FormData formcusequ00003 = formService.getFormData(formId);
			if (formcusequ00003.getFormId() == null) {
				// logger.error("产品类型为" + kindNo + "的MfCusLegalEquityInfoAction表单form" + formId
				// + ".xml文件不存在");
			} else {
				getFormValue(formcusequ00003);
				getObjValue(formcusequ00003, mfCusLegalEquityInfo);
			}
			model.addAttribute("formcusequ00003", formcusequ00003);
		}
		JSONArray map = new CodeUtils().getJSONArrayByKeyName("PUSH_CAPITAL_TYPE");
		String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
		model.addAttribute("mfCusLegalEquityInfo", mfCusLegalEquityInfo);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfCusCustomer", mfCusFormConfig);
		model.addAttribute("formId", formId);
		model.addAttribute("ajaxData", items);
		model.addAttribute("query", "");
		return "component/cus/MfCusLegalEquityInfo_Insert";
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
		FormData formcusequ00002 = formService.getFormData("cusequ00002");
		getFormValue(formcusequ00002);
		MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
		setObjValue(formcusequ00002, mfCusLegalEquityInfo);
		mfCusLegalEquityInfoFeign.insert(mfCusLegalEquityInfo);
		getObjValue(formcusequ00002, mfCusLegalEquityInfo);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusLegalEquityInfo", mfCusLegalEquityInfo));
		List<MfCusLegalEquityInfo> mfCusLegalEquityInfoList = (List<MfCusLegalEquityInfo>) mfCusLegalEquityInfoFeign
				.findByPage(ipage).getResult();
		model.addAttribute("formcusequ00002", formcusequ00002);
		model.addAttribute("mfCusLegalEquityInfo", mfCusLegalEquityInfo);
		model.addAttribute("mfCusLegalEquityInfoList", mfCusLegalEquityInfoList);
		model.addAttribute("query", "");
		return "component/cus/MfCusLegalEquityInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @param equityInfoId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String equityInfoId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
		mfCusLegalEquityInfo.setEquityInfoId(equityInfoId);
		mfCusLegalEquityInfo = mfCusLegalEquityInfoFeign.getById(mfCusLegalEquityInfo);

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusLegalEquityInfo.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		JSONArray map = new CodeUtils().getJSONArrayByKeyName("PUSH_CAPITAL_TYPE");
		String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusLegalEquityInfoAction");
		String formId = null;
		if (mfCusFormConfig == null) {

		} else {
			if ("1".equals(mfCusFormConfig.getShowType())) {
				formId = mfCusFormConfig.getShowModelDef();
			} else {
				formId = mfCusFormConfig.getAddModelDef();
			}
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusLegalEquityInfoAction表单信息没有查询到");
		} else {
			FormData formcusequ00003 = formService.getFormData(formId);
			if (formcusequ00003.getFormId() == null) {
				// logger.error("客户类型为" + mfCusCustomer.getCusType() +
				// "的MfCusLegalEquityInfoAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcusequ00003);
				getObjValue(formcusequ00003, mfCusLegalEquityInfo);
			}
			model.addAttribute("formcusequ00003", formcusequ00003);
		}
		model.addAttribute("mfCusLegalEquityInfo", mfCusLegalEquityInfo);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfCusCustomer", mfCusFormConfig);
		model.addAttribute("formId", formId);
		model.addAttribute("ajaxData", items);
		model.addAttribute("query", "");
		return "component/cus/MfCusLegalEquityInfo_Detail";
	}

	/**
	 * 删除
	 * 
	 * @param cusNo
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String equityInfoId, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
		mfCusLegalEquityInfo.setEquityInfoId(equityInfoId);
		mfCusLegalEquityInfoFeign.delete(mfCusLegalEquityInfo);
		return getListPage(model, cusNo);
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String ajaxData, String cusNo, String equityInfoId)
			throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusLegalEquityInfoAction");
		String formId = null;
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusLegalEquityInfoAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger","3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusequ00004 = formService.getFormData(formId);
			MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
			mfCusLegalEquityInfo.setEquityInfoId(equityInfoId);
			mfCusLegalEquityInfo = mfCusLegalEquityInfoFeign.getById(mfCusLegalEquityInfo);
			getObjValue(formcusequ00004, mfCusLegalEquityInfo, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusequ00004, "propertySeeTag", "");
			if (mfCusLegalEquityInfo != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			// 返回出资方式json
			JSONArray map = new CodeUtils().getJSONArrayByKeyName("PUSH_CAPITAL_TYPE");
			String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
			dataMap.put("items", items);
			dataMap.put("formData", mfCusLegalEquityInfo);
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @param formId
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
		MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		FormData formcusequ00004 = formService.getFormData(formId);
		getFormValue(formcusequ00004, getMapByJson(ajaxData));
		MfCusLegalEquityInfo mfCusLegalEquityInfoNew = new MfCusLegalEquityInfo();
		setObjValue(formcusequ00004, mfCusLegalEquityInfoNew);
		mfCusLegalEquityInfo.setEquityInfoId(mfCusLegalEquityInfoNew.getEquityInfoId());
		mfCusLegalEquityInfo = mfCusLegalEquityInfoFeign.getById(mfCusLegalEquityInfo);
		if (mfCusLegalEquityInfo != null) {
			try {
				mfCusLegalEquityInfo = (MfCusLegalEquityInfo) EntityUtil.reflectionSetVal(mfCusLegalEquityInfo,
						mfCusLegalEquityInfoNew, getMapByJson(ajaxData));
				mfCusLegalEquityInfoFeign.update(mfCusLegalEquityInfo);
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
		FormData formcusequ00002 = formService.getFormData("cusequ00002");
		getFormValue(formcusequ00002);
		boolean validateFlag = this.validateFormData(formcusequ00002);
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
		FormData formcusequ00002 = formService.getFormData("cusequ00002");
		getFormValue(formcusequ00002);
		boolean validateFlag = this.validateFormData(formcusequ00002);
	}

}
