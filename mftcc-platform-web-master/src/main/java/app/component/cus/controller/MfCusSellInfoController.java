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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusSellInfo;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusSellInfoFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusSellInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Mar 13 10:12:58 CST 2017
 **/
@Controller
@RequestMapping("/mfCusSellInfo")
public class MfCusSellInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusSellInfoBo
	@Autowired
	private MfCusSellInfoFeign mfCusSellInfoFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	// 全局变量
	// 异步参数
	// 表单变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/cus/MfCusSellInfo_List";
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
		MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
		try {
			mfCusSellInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusSellInfo.setCriteriaList(mfCusSellInfo, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusSellInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusSellInfo", mfCusSellInfo));
			ipage = mfCusSellInfoFeign.findByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData, String cusNo, String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusSellInfoAction").getAddModel();
			}
			FormData formcusSellBase = formService.getFormData(formId);
			getFormValue(formcusSellBase, getMapByJson(ajaxData));
			if (this.validateFormData(formcusSellBase)) {
				MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
				setObjValue(formcusSellBase, mfCusSellInfo);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusSellInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				mfCusSellInfo.setCusName(mfCusCustomer.getCusName());
				mfCusSellInfoFeign.insert(mfCusSellInfo);

				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusSellInfo.getCusNo(),
						mfCusSellInfo.getRelNo());// 更新客户信息完整度
				JsonTableUtil jtu = new JsonTableUtil();
				cusNo = mfCusSellInfo.getCusNo();
				relNo = mfCusSellInfo.getRelNo();
				mfCusSellInfo.setCusNo(cusNo);
				mfCusSellInfo.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusSellInfo", mfCusSellInfo));
				String tableHtml = jtu.getJsonStr("tablecussell0001", "tableTag",
						(List<MfCusSellInfo>) mfCusSellInfoFeign.findByPage(ipage).getResult(), null, true);
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
		FormData formcussell0002 = formService.getFormData("cussell0002");
		getFormValue(formcussell0002, getMapByJson(ajaxData));
		MfCusSellInfo mfCusSellInfoJsp = new MfCusSellInfo();
		setObjValue(formcussell0002, mfCusSellInfoJsp);
		MfCusSellInfo mfCusSellInfo = mfCusSellInfoFeign.getById(mfCusSellInfoJsp);
		if (mfCusSellInfo != null) {
			try {
				mfCusSellInfo = (MfCusSellInfo) EntityUtil.reflectionSetVal(mfCusSellInfo, mfCusSellInfoJsp,
						getMapByJson(ajaxData));
				mfCusSellInfoFeign.update(mfCusSellInfo);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusSellInfoAction").getAddModel();
			}
			FormData formcussell0002 = formService.getFormData(formId);
			getFormValue(formcussell0002, map);
			if (this.validateFormData(formcussell0002)) {
				setObjValue(formcussell0002, mfCusSellInfo);
				mfCusSellInfoFeign.update(mfCusSellInfo);

				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusSellInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

				String tableId = "tablecussell0001";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfCusSellInfoAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}
				cusNo = mfCusSellInfo.getCusNo();
				mfCusSellInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusSellInfo", mfCusSellInfo));
				String tableHtml = jtu.getJsonStr(tableId, "tableTag",
						(List<MfCusSellInfo>) mfCusSellInfoFeign.findByPage(ipage).getResult(), null, true);
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
	public Map<String, Object> getByIdAjax(String sellId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcussell0002 = formService.getFormData("cussell0002");
		MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
		mfCusSellInfo.setSellId(sellId);
		mfCusSellInfo = mfCusSellInfoFeign.getById(mfCusSellInfo);
		getObjValue(formcussell0002, mfCusSellInfo, formData);
		if (mfCusSellInfo != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdForOneAjax")
	@ResponseBody
	public Map<String, Object> getByIdForOneAjax(String sellId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcussell0002 = formService.getFormData("cussell0002");
		MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
		mfCusSellInfo.setSellId(sellId);
		mfCusSellInfo = mfCusSellInfoFeign.getById(mfCusSellInfo);
		getObjValue(formcussell0002, mfCusSellInfo, formData);
		if (mfCusSellInfo != null) {
			dataMap.put("flag", "success");
			// 经销区域
			String sellArea = new CodeUtils().getMapByKeyName("SALE_AREA").get(mfCusSellInfo.getSellArea());
			dataMap.put("sellArea", sellArea);
			// 行业分类
			String sellWayclass = new CodeUtils().getMapByKeyName("TRADE").get(mfCusSellInfo.getSellWayclass());
			dataMap.put("sellWayclass", sellWayclass);
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
	public Map<String, Object> deleteAjax(String sellId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
		mfCusSellInfo.setSellId(sellId);
		try {
			mfCusSellInfoFeign.delete(mfCusSellInfo);
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
	public String input(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcussell0002 = null;
		MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
		mfCusSellInfo.setCusNo(cusNo);
		mfCusSellInfo.setRelNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusSellInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusSellInfoAction表单信息没有查询到");
		} else {
			formcussell0002 = formService.getFormData(formId);
			if (formcussell0002.getFormId() == null) {
				// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusSellInfoAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcussell0002);
				getObjValue(formcussell0002, mfCusSellInfo);
			}
		}
		model.addAttribute("formcussell0002", formcussell0002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusSellInfo_Insert";
	}

	/**
	 * 
	 * 方法描述： 业务新增客户信息表单页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-9-23 上午10:33:48
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo, String relNo, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
		FormData formcussell0002 = null;
		mfCusSellInfo.setCusNo(cusNo);
		mfCusSellInfo.setRelNo(relNo);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusSellInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("产品类型为"+kindNo+"的MfCusSellInfoAction表单信息没有查询到");
		} else {
			formcussell0002 = formService.getFormData(formId);
			if (formcussell0002.getFormId() == null) {
				// logger.error("产品类型为"+kindNo+"的MfCusSellInfoAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcussell0002);
				getObjValue(formcussell0002, mfCusSellInfo);
			}
		}
		model.addAttribute("formcussell0002", formcussell0002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusSellInfo_Insert";
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
		FormData formcussell0002 = formService.getFormData("cussell0002");
		getFormValue(formcussell0002);
		MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
		setObjValue(formcussell0002, mfCusSellInfo);
		mfCusSellInfoFeign.insert(mfCusSellInfo);
		getObjValue(formcussell0002, mfCusSellInfo);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusSellInfo", mfCusSellInfo));
		List<MfCusSellInfo> mfCusSellInfoList = (List<MfCusSellInfo>) mfCusSellInfoFeign.findByPage(ipage).getResult();
		model.addAttribute("formcussell0002", formcussell0002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusSellInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String sellId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcussell0001 = formService.getFormData("cussell0001");
		MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
		mfCusSellInfo.setSellId(sellId);
		mfCusSellInfo = mfCusSellInfoFeign.getById(mfCusSellInfo);

		String cusType = "";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusSellInfo.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusType = mfCusCustomer.getCusType();

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusSellInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			if ("1".equals(mfCusFormConfig.getShowType())) {
				formId = mfCusFormConfig.getShowModelDef();
			} else {
				formId = mfCusFormConfig.getAddModelDef();
			}
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+cusType+"的MfCusShareholderAction表单信息没有查询到");
		} else {
			formcussell0001 = formService.getFormData(formId);
			if (formcussell0001.getFormId() == null) {
				// logger.error("客户类型为"+cusType+"的MfCusShareholderAction表单form"+formId+".xml文件不存在");
			} else {
				getFormValue(formcussell0001);
				getObjValue(formcussell0001, mfCusSellInfo);
			}
		}
		model.addAttribute("formcussell0001", formcussell0001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusSellInfo_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String sellId) throws Exception {
		ActionContext.initialize(request, response);
		MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
		mfCusSellInfo.setSellId(sellId);
		mfCusSellInfoFeign.delete(mfCusSellInfo);
		return getListPage(model);
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String sellId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusSellInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusSellInfoAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger", "3");

			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcussell0003 = formService.getFormData(formId);
			MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
			mfCusSellInfo.setSellId(sellId);
			mfCusSellInfo = mfCusSellInfoFeign.getById(mfCusSellInfo);
			getObjValue(formcussell0003, mfCusSellInfo, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcussell0003, "propertySeeTag", "");
			if (mfCusSellInfo != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusSellInfo);
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
	public Map<String, Object> updateByOneAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData formcussell0003 = formService.getFormData(formId);
		getFormValue(formcussell0003, getMapByJson(ajaxData));
		MfCusSellInfo mfCusSellInfoNew = new MfCusSellInfo();
		setObjValue(formcussell0003, mfCusSellInfoNew);
		mfCusSellInfo.setSellId(mfCusSellInfoNew.getSellId());
		mfCusSellInfo = mfCusSellInfoFeign.getById(mfCusSellInfo);
		if (mfCusSellInfo != null) {
			try {
				mfCusSellInfo = (MfCusSellInfo) EntityUtil.reflectionSetVal(mfCusSellInfo, mfCusSellInfoNew,
						getMapByJson(ajaxData));
				mfCusSellInfoFeign.update(mfCusSellInfo);
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
		FormData formcussell0002 = formService.getFormData("cussell0002");
		getFormValue(formcussell0002);
		boolean validateFlag = this.validateFormData(formcussell0002);
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
		FormData formcussell0002 = formService.getFormData("cussell0002");
		getFormValue(formcussell0002);
		boolean validateFlag = this.validateFormData(formcussell0002);
	}

}
