package app.component.cus.controller;

import app.component.app.entity.MfBusAssureDetail;
import app.component.app.feign.MfBusAssureDetailFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.assure.entity.MfAssureInfo;
import app.component.busviewinterface.BusViewInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusAssureCompany;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusType;
import app.component.cus.feign.MfCusAssureCompanyFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusTypeFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Title: MfCusAssureCompanyAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Oct 23 10:57:47 CST 2017
 **/
@Controller
@RequestMapping("/mfCusAssureCompany")
@SuppressWarnings({ "rawtypes" })
public class MfCusAssureCompanyController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusAssureCompanyFeign mfCusAssureCompanyFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private BusViewInterfaceFeign busViewInterfaceFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private MfCusTypeFeign mfCusTypeFeign;

	@Autowired
	private MfBusAssureDetailFeign mfBusAssureDetailFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/cus/MfCusAssureCompany_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, int pageNo, String tableId, String tableType)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusAssureCompany mfCusAssureCompany = new MfCusAssureCompany();
		try {
			mfCusAssureCompany.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusAssureCompany.setCriteriaList(mfCusAssureCompany, ajaxData);// 我的筛选
			mfCusAssureCompany.setCustomSorts(ajaxData);// 自定义排序
			// this.getRoleConditions(mfCusAssureCompany,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Feign方法

			ipage.setParams(this.setIpageParams("mfCusAssureCompany", mfCusAssureCompany));
			ipage = mfCusAssureCompanyFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
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
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData cusAssureCompanyBase = formService.getFormData(formId);
			getFormValue(cusAssureCompanyBase, getMapByJson(ajaxData));
			if (this.validateFormData(cusAssureCompanyBase)) {
				MfCusAssureCompany mfCusAssureCompany = new MfCusAssureCompany();
				setObjValue(cusAssureCompanyBase, mfCusAssureCompany);
				mfCusAssureCompanyFeign.insert(mfCusAssureCompany);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());

			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
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
	public Map<String, Object> updateAjaxByOne(String formId, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map map = getMapByJson(ajaxData);
		formId = (String) map.get("formId");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassurecompany0002 = formService.getFormData(formId);
		getFormValue(formassurecompany0002, getMapByJson(ajaxData));
		MfCusAssureCompany mfCusAssureCompanyJsp = new MfCusAssureCompany();
		setObjValue(formassurecompany0002, mfCusAssureCompanyJsp);
		MfCusAssureCompany mfCusAssureCompany = mfCusAssureCompanyFeign.getById(mfCusAssureCompanyJsp);

		if (ajaxData.indexOf("marginAmt") >= 0) {// 保证金金额修改
			Double marginAmt = mfCusAssureCompanyJsp.getMarginAmt();// 保证金金额
			Double magnificationMax = mfCusAssureCompany.getMagnificationMax();// 允许最大放大倍数

			Double usableAmt = MathExtend.multiply(marginAmt, magnificationMax);// 可使用金额
			Double occupyAmt = appInterfaceFeign.getOccupyAmt(mfCusAssureCompany.getAssureCompanyId());// 已担保金额

			if (usableAmt < occupyAmt) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("content", "");
				paramMap.put("reason", "保证金金额乘以放款倍数不能小于已担保金额" + occupyAmt);

				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage(paramMap));

				return dataMap;
			}

			usableAmt = MathExtend.subtract(usableAmt, occupyAmt);
			mfCusAssureCompany.setUsableAmt(usableAmt);
		}

		if (mfCusAssureCompany == null) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		} else {
			try {
				mfCusAssureCompany = (MfCusAssureCompany) EntityUtil.reflectionSetVal(mfCusAssureCompany,
						mfCusAssureCompanyJsp, getMapByJson(ajaxData));
				mfCusAssureCompanyFeign.update(mfCusAssureCompany);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "保存失败");
				throw e;
			}
		}

		return dataMap;
	}

	@RequestMapping(value = "/getUsableAmtAjax")
	@ResponseBody
	public Map<String, Object> getUsableAmtAjax(String assureCompanyId) throws Exception {
		MfCusAssureCompany mfCusAssureCompany = new MfCusAssureCompany();
		mfCusAssureCompany.setAssureCompanyId(assureCompanyId);
		mfCusAssureCompany = mfCusAssureCompanyFeign.getById(mfCusAssureCompany);

		Double marginAmt = mfCusAssureCompany.getMarginAmt();// 保证金金额
		Double magnificationMax = mfCusAssureCompany.getMagnificationMax();// 允许最大放大倍数

		Double usableAmt = MathExtend.multiply(marginAmt, magnificationMax);// 可使用金额
		Double occupyAmt = appInterfaceFeign.getOccupyAmt(mfCusAssureCompany.getAssureCompanyId());// 已担保金额

		usableAmt = MathExtend.subtract(usableAmt, occupyAmt);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("usableAmt", MathExtend.moneyStr(usableAmt));

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
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formassurecompany0002 = formService.getFormData("assurecompany0002");
			getFormValue(formassurecompany0002, getMapByJson(ajaxData));
			if (this.validateFormData(formassurecompany0002)) {
				MfCusAssureCompany mfCusAssureCompany = new MfCusAssureCompany();
				setObjValue(formassurecompany0002, mfCusAssureCompany);
				mfCusAssureCompanyFeign.update(mfCusAssureCompany);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
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
	public Map<String, Object> getByIdAjax(String assureCompanyId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formassurecompany0002 = formService.getFormData("assurecompany0002");
		MfCusAssureCompany mfCusAssureCompany = new MfCusAssureCompany();
		mfCusAssureCompany.setAssureCompanyId(assureCompanyId);
		mfCusAssureCompany = mfCusAssureCompanyFeign.getById(mfCusAssureCompany);
		getObjValue(formassurecompany0002, mfCusAssureCompany, formData);
		if (mfCusAssureCompany != null) {
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
	public Map<String, Object> deleteAjax(String assureCompanyId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusAssureCompany mfCusAssureCompany = new MfCusAssureCompany();
		mfCusAssureCompany.setAssureCompanyId(assureCompanyId);
		try {
			mfCusAssureCompanyFeign.delete(mfCusAssureCompany);
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
	 * 列表新增，添加客户数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInput")
	public String cusInput(Model model, String cusType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String formId = "";
		String ajaxData = "";
		// 明细类别
		MfCusType mfCusType = null;
		if (StringUtil.isEmpty(cusType)) {
			// 明细类别
			List<MfCusType> ctList = cusInterfaceFeign.getAllList();
			for (MfCusType ct : ctList) {
				if (BizPubParm.CUS_BASE_TYPE_DANBAO.equals(ct.getBaseType())) {
					mfCusType = ct;
					break;
				}
			}
            cusType = mfCusType.getTypeNo();
		} else {
			mfCusType = new MfCusType();
			mfCusType.setTypeNo(cusType);
			mfCusType = cusInterfaceFeign.getMfCusTypeByTypeNo(mfCusType);
		}

        if (StringUtil.isNotEmpty(cusType)) {
            MfCusFormConfig mc = mfCusFormConfigFeign.getByCusType(cusType, "MfCusAssureCompanyAction");
            if (mc != null) {
                formId = mc.getAddModelDef();
            }
        } else {
            formId = "cusAssureCompanyBase";
        }
        FormData formassurecompany0002 = formService.getFormData(formId);
		JSONArray subTypeArray = new JSONArray();
		if (mfCusType != null) {
			this.changeFormProperty(formassurecompany0002, "cusType", "initValue", mfCusType.getTypeNo());

			String[] subTypes = mfCusType.getSubType().split("\\|");
			Map<String, String> dicMap = new CodeUtils().getMapByKeyName("CUS_SUB_TYPE");
			for (int i = 0; i < subTypes.length; i++) {
				JSONObject tmpObject = new JSONObject();
				tmpObject.put("id", subTypes[i]);
				tmpObject.put("name", dicMap.get(subTypes[i]));
				subTypeArray.add(tmpObject);
			}
		}

		JSONObject json = new JSONObject();
		json.put("cusSubType", subTypeArray);
		ajaxData = json.toString();
		
		CodeUtils cu = new CodeUtils();
		JSONArray resultMap = new JSONArray();// 获取证件类型
		JSONArray map = cu.getJSONArrayByKeyName("ID_TYPE");
		for (int i = 0; i < map.size(); i++) {
			JSONObject obj = map.getJSONObject(i);
			String optCode = obj.getString("optCode");
			if (BizPubParm.ID_TYPE_CERT.equals(optCode) || BizPubParm.ID_TYPE_CREDIT.equals(optCode)
					|| BizPubParm.ID_TYPE_LICENCE.equals(optCode)) {
				obj.put("id", optCode);
				obj.put("name", obj.getString("optName"));
				resultMap.add(obj);
			}
		}
		model.addAttribute("idTypeMap", resultMap);
		model.addAttribute("formassurecompany0002", formassurecompany0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/cus/MfCusAssureCompany_cusInput";
	}

	/**
	 * 资料完善，担保公司信息块
	 * 
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-10-24 上午10:09:19
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String cusNo) throws Exception {
		Logger logger = LoggerFactory.getLogger(MfCusAssureCompanyController.class);
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String formId = "";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusAssureCompanyAction");
		if (mfCusFormConfig != null) {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusAssureCompanyAction表单信息没有查询到");
		} else {
			FormData cusAssureCompanyBase = formService.getFormData(formId);
			if (cusAssureCompanyBase.getFormId() == null) {
				logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusAssureCompanyAction表单form" + formId
						+ ".xml文件不存在");
			} else {
				getFormValue(cusAssureCompanyBase);

				MfCusAssureCompany assureCompany = new MfCusAssureCompany();
				assureCompany.setAssureCompanyName(mfCusCustomer.getCusName());
				assureCompany.setAssureCompanyId(mfCusCustomer.getCusNo());
				CodeUtils cu = new CodeUtils();
				JSONArray resultMap = new JSONArray();// 获取证件类型
				JSONArray map = cu.getJSONArrayByKeyName("ID_TYPE");
				for (int i = 0; i < map.size(); i++) {
					JSONObject obj = map.getJSONObject(i);
					String optCode = obj.getString("optCode");
					if (BizPubParm.ID_TYPE_CERT.equals(optCode) || BizPubParm.ID_TYPE_CREDIT.equals(optCode)
							|| BizPubParm.ID_TYPE_LICENCE.equals(optCode)) {
						obj.put("id", optCode);
						obj.put("name", obj.getString("optName"));
						resultMap.add(obj);
					}
				}
				model.addAttribute("idTypeMap", resultMap.toString());

				getObjValue(cusAssureCompanyBase, assureCompany);
				model.addAttribute("cusAssureCompanyBase", cusAssureCompanyBase);
				model.addAttribute("query", "");
			}
		}

		return "/component/cus/MfCusAssureCompany_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String assureCompanyId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formassurecompany0001 = formService.getFormData("assurecompany0001");
		getFormValue(formassurecompany0001);
		MfCusAssureCompany mfCusAssureCompany = new MfCusAssureCompany();
		mfCusAssureCompany.setAssureCompanyId(assureCompanyId);
		mfCusAssureCompany = mfCusAssureCompanyFeign.getById(mfCusAssureCompany);
		getObjValue(formassurecompany0001, mfCusAssureCompany);
		model.addAttribute("formassurecompany0001", formassurecompany0001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusAssureCompany_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData cusAssureCompanyBase = formService.getFormData("assurecompany0002");
		getFormValue(cusAssureCompanyBase);
		boolean validateFlag = this.validateFormData(cusAssureCompanyBase);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formassurecompany0002 = formService.getFormData("assurecompany0002");
		getFormValue(formassurecompany0002);
		boolean validateFlag = this.validateFormData(formassurecompany0002);
	}

	/**
	 * 担保公司视角<br>
	 * assurecompany0001<br>
	 * MfCusCustomerAction_getById.action;cusNo-cusNo;onClick-MfCusAssureCompany_List.getDetailPage(this);
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/assureCompanyView")
	public String assureCompanyView(Model model, String cusNo, String busEntrance) throws Exception {
		ActionContext.initialize(request, response);
		try {
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			MfCusType mfCusType = new MfCusType();
			mfCusType.setTypeNo(mfCusCustomer.getCusType());
			mfCusType = mfCusTypeFeign.getById(mfCusType);
			String cusSubTypeName = new CodeUtils().getMapByKeyName("CUS_TYPE").get(mfCusCustomer.getCusType());
			String generalClass = "cus";
			// String busClass = mfCusCustomer.getCusBaseType();

			Map<String, String> parmMap = new HashMap<String, String>();
			String baseType = mfCusType.getBaseType();
			parmMap.put("baseType", baseType);
			parmMap.put("cusType", mfCusCustomer.getCusType());
			// parmMap.put("busClass", busClass);
			parmMap.put("cusNo", cusNo);
			parmMap.put("assureCompanyId", cusNo);
			parmMap.put("operable", "operable");// 底部显示待完善信息块
			parmMap.put("cusSubTypeName", cusSubTypeName);
			parmMap.put("docParm", "cusNo=" + cusNo + "&relNo=" + cusNo + "&scNo=" + BizPubParm.SCENCE_TYPE_DOC_CUS);
			Map<String, Object> cusViewMap = busViewInterfaceFeign.getCommonViewMap(generalClass, busEntrance, parmMap);

			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("cusNo", cusNo);
			dataMap.put("baseType", baseType);
			dataMap.putAll(cusViewMap);
			model.addAttribute("dataMap", dataMap);
			model.addAttribute("param", parmMap);
			model.addAttribute("cusNo", cusNo);
			model.addAttribute("cusType", mfCusCustomer.getCusType());
			model.addAttribute("baseType", baseType);
			model.addAttribute("query", "");
		} catch (Exception e) {
			// logger.error("获取担保公司详情视角失败", e);
			throw e;
		}
		return "/component/cus/commonview/MfCusCustomer_ComView";
	}

	@RequestMapping(value = "/getCusSubType")
	@ResponseBody
	public Map<String, Object> getCusSubType(String cusType) throws Exception {
		// 明细类别
		MfCusType mfCusType = new MfCusType();
		mfCusType.setTypeNo(cusType);
		mfCusType = cusInterfaceFeign.getMfCusTypeByTypeNo(mfCusType);
		String[] subTypes = mfCusType.getSubType().split("\\|");
		JSONArray subTypeArray = new JSONArray();
		JSONObject tmpObject = null;
		Map<String, String> dicMap = new CodeUtils().getMapByKeyName("CUS_SUB_TYPE");
		for (int i = 0; i < subTypes.length; i++) {
			tmpObject = new JSONObject();
			tmpObject.put("id", subTypes[i]);
			tmpObject.put("name", dicMap.get(subTypes[i]));
			subTypeArray.add(tmpObject);
		}

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("items", subTypeArray);

		return dataMap;
	}

	@RequestMapping(value = "/getAssureCompanyAjax")
	@ResponseBody
	public Map<String, Object> getAssureCompanyAjax(int pageNo, String cusType, String ajaxData, String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			MfCusAssureCompany mfCusAssureCompany = new MfCusAssureCompany();
			mfCusAssureCompany.setCustomQuery(ajaxData);// 自定义查询参数赋值
			ipage.setParams(this.setIpageParams("mfCusAssureCompany", mfCusAssureCompany));
			ipage = mfCusAssureCompanyFeign.findByPage(ipage);

			List<MfAssureInfo> mfAssureInfoList = mfCusCustomerFeign.getAssureListByAppid(appId);// 已填加的担保人

			// 去掉已添加的担保公司
			List<Map<String, Object>> list = (List<Map<String, Object>>) ipage.getResult();
			Iterator<Map<String, Object>> iter = list.iterator();
			while (iter.hasNext()) {
				Map assureCompany = iter.next();
				for (MfAssureInfo assure : mfAssureInfoList) {
					if (assure.getAssureNo().equals(assureCompany.get("cusNo"))) {
						iter.remove();
					}
				}
			}

			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}

		return dataMap;
	}

	@RequestMapping(value = "/getAssureData")
	@ResponseBody
	public Map<String, Object> getAssureData() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusAssureCompany mfCusAssureCompany = new MfCusAssureCompany();

		List<MfCusAssureCompany> mfCusAssureCompanyList = mfCusAssureCompanyFeign
				.getMfCusAssureCompanyList(mfCusAssureCompany);
		JSONArray array = new JSONArray();
		if (mfCusAssureCompanyList != null && mfCusAssureCompanyList.size() > 0) {
			for (int i = 0; i < mfCusAssureCompanyList.size(); i++) {
				JSONObject obj = new JSONObject();
				obj.put("id", mfCusAssureCompanyList.get(i).getAssureCompanyId());
				obj.put("name", mfCusAssureCompanyList.get(i).getAssureCompanyName());
				array.add(obj);
			}
			dataMap.put("mfCusAssureCompanyList", mfCusAssureCompanyList);
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("items", array.toString());
		return dataMap;
	}
	@RequestMapping(value = "/getAssureInfoByIdNumAjax")
	@ResponseBody
	public Map<String, Object> getAssureInfoByIdNumAjax(String idNum) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusAssureCompany mfCusAssureCompany = new MfCusAssureCompany();
		try{
			if(StringUtil.isEmpty(idNum)){
				dataMap.put("existFlag", "0");
				dataMap.put("flag", "success");
				return dataMap;
			}
			mfCusAssureCompany.setIdNum(idNum);
			List<MfCusAssureCompany> mfCusAssureCompanyList = mfCusAssureCompanyFeign.getAssureInfoByIdNum(mfCusAssureCompany);
			if(mfCusAssureCompanyList.size()>0 && mfCusAssureCompanyList != null){
				mfCusAssureCompany  = mfCusAssureCompanyList.get(0);
				dataMap.put("existFlag", "1");
				dataMap.put("assureInfo", mfCusAssureCompany);
			}else{
				dataMap.put("existFlag", "0");
			}
			dataMap.put("flag", "success");
		}catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		
		return dataMap;
	}

	/**
	 * 获取历史数据统计
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getTotalData")
	@ResponseBody
	public Map<String, Object> getTotalData(String assureCompanyId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfBusAssureDetailFeign.getTotalData(assureCompanyId);
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
	 * 担保明细页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAssureDetailListPage")
	public String getDetailListPage(Model model, String assureCompanyId) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("assureCompanyId", assureCompanyId);
		return "/component/cus/MfCusAssureCompany_assureDetailList";
	}

	@ResponseBody
	@RequestMapping(value = "/getAssureDetailListAjax")
	public Map<String, Object> getAssureDetailListAjax(String ajaxData, String tableId, String tableType, Integer pageNo, Integer pageSize, String assureCompanyId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAssureDetail mfBusAssureDetail = new MfBusAssureDetail();
		try {
			mfBusAssureDetail.setAssureCompanyId(assureCompanyId);
			mfBusAssureDetail.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusAssureDetail.setCriteriaList(mfBusAssureDetail, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfBusAssureDetail", mfBusAssureDetail));
			//自定义查询Feign方法
			ipage = mfBusAssureDetailFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
}
