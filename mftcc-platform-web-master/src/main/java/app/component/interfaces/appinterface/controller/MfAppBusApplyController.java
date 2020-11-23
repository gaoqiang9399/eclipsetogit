package app.component.interfaces.appinterface.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

import com.alibaba.fastjson.JSON;
import com.core.domain.screen.FormActive;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.app.entity.MfCusAndApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.prdct.entity.MfSysKind;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;

/**
 * 移动端客户业务申请管理的Action类
 * 
 * @author Mahao
 * @date 2017-07-30
 */
@Controller
@RequestMapping("/mfAppBusApply")
public class MfAppBusApplyController extends BaseFormBean {

	// bo 先使用原bo 之后在替换为interface
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;

	// 异步参数
	/**
	 * 客户列表业务申请页面 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String opInfo) throws Exception {
		ActionContext.initialize(request, response);
		// 处理登陆操作员信息
		if (StringUtil.isNotBlank(opInfo)) {
			Gson gson = new Gson();
			Map<String, Object> map = gson.fromJson(opInfo, new TypeToken<Map<String, Object>>() {
			}.getType());
			String brNo = (String) map.get("brNo");
			String brName = (String) map.get("brName");
			String opNo = (String) map.get("opNo");
			String opName = (String) map.get("opName");
			String[] roleNo = ((String) map.get("roleNo")).split("\\|");
			request.getSession().setAttribute("orgNo", brNo);
			request.getSession().setAttribute("orgName", brName);
			request.getSession().setAttribute("regNo", opNo);
			request.getSession().setAttribute("regName", opName);
			request.getSession().setAttribute("roleNo", roleNo);

		}
		// 前台自定义筛选组件的条件项，从数据字典缓存获取。
		CodeUtils codeUtils = new CodeUtils();
		JSONArray cusTypeJsonArray = codeUtils.getJSONArrayByKeyName("CUS_TYPE");
		this.getHttpRequest().setAttribute("cusTypeJsonArray", cusTypeJsonArray);
		JSONArray gdCusTypeJsonArray = codeUtils.getJSONArrayByKeyName("GD_CUS_TYPE");
		this.getHttpRequest().setAttribute("gdCusTypeJsonArray", gdCusTypeJsonArray);
		JSONArray appStsJsonArray = codeUtils.getJSONArrayByKeyName("APP_STS");
		this.getHttpRequest().setAttribute("appStsJsonArray", appStsJsonArray);
		JSONArray kindTypeJsonArray = codeUtils.getJSONArrayByKeyName("KIND_NO");
		this.getHttpRequest().setAttribute("kindTypeJsonArray", kindTypeJsonArray);
		return "/component/interfaces/appinterface/MfAppBusApply_List";
	}

	/**
	 * 将操作员信息放到会话信息中
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setSessionAjax")
	@ResponseBody
	public Map<String, Object> setSessionAjax(String opInfo) throws Exception {
		ActionContext.initialize(request, response);
		// 处理登陆操作员信息
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (StringUtil.isNotBlank(opInfo)) {
				Gson gson = new Gson();
				Map<String, Object> map = gson.fromJson(opInfo, new TypeToken<Map<String, Object>>() {
				}.getType());
				String brNo = (String) map.get("brNo");
				String brName = (String) map.get("brName");
				String opNo = (String) map.get("opNo");
				String opName = (String) map.get("opName");
				String[] roleNo = ((String) map.get("roleNo")).split("\\|");
				request.getSession().setAttribute("orgNo", brNo);
				request.getSession().setAttribute("orgName", brName);
				request.getSession().setAttribute("regNo", opNo);
				request.getSession().setAttribute("regName", opName);
				request.getSession().setAttribute("roleNo", roleNo);
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", "未传入操作员信息");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "会话放入操作员信息失败");
		}
		return dataMap;
	}

	/**
	 * 客户列表业务申请列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax1")
	@ResponseBody
	public Map<String, Object> findByPageAjax1(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusAndApply mfCusAndApply = new MfCusAndApply();
		try {
			mfCusAndApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusAndApply.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfCusAndApply.setCriteriaList(mfCusAndApply, ajaxData);// 我的筛选
			// this.getRoleConditions(mfCusAndApply,"1000000065");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = appInterfaceFeign.getCusAndApplyList(ipage, mfCusAndApply);
			List<MfCusAndApply> list = (List<MfCusAndApply>) ipage.getResult();
			CodeUtils codeUtils = new CodeUtils();
			Map<String, ParmDic> map = codeUtils.getMapObjByKeyName("CUS_TYPE");
			if (null != list) {
				for (int i = 0; i < list.size(); i++) {
					// 把客户类型全部转换为汉字描述
					MfCusAndApply ca = list.get(i);
					/*
					 * ParmDic pd = map.get(ca.getCusType());
					 * ca.setCusType(pd.getOptName());
					 */
					// 有申请日期的格式化日期
					if (!StringUtil.isBlank(ca.getAppTime())) {
						String formatDate = DateUtil.getStr(ca.getAppTime());
						ca.setAppTime(formatDate);
					}

				}
			}
			// JsonTableUtil jtu = new JsonTableUtil();
			// String tableHtml =
			// jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(),
			// ipage,true);
			// ipage.setResult(tableHtml);
			dataMap.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获取申请摘要信息
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-3-28 下午4:08:04
	 */
	@RequestMapping(value = "/getBusInfoAjax")
	@ResponseBody
	public Map<String, Object> getBusInfoAjax(String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("showFlag", "none");// showFlag填写过申请信息的标志none-没有业务信息
										// apply-展示申请信息 pact-展示合同信息，默认不展示任何业务信息
		try {
			String repayTypeStr = "";
			String vouTypeStr = "";
			String rateUnit = "";
			if (StringUtil.isNotEmpty(appId)) {
				MfBusApply mfBusApply = new MfBusApply();
				mfBusApply.setAppId(appId);
				mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
				if (mfBusApply != null) {
					dataMap.put("showFlag", "apply");
					mfBusApply.setAppTimeShow(DateUtil.getShowDateTime(mfBusApply.getAppTime().substring(0, 8)));
					mfBusApply.setFincAmt(MathExtend.moneyStr(mfBusApply.getAppAmt() / 10000));
					mfBusApply = appInterfaceFeign.processPactDataForApply(mfBusApply);// 处理利率格式
					// 获取该客户除当前业务之外的其他的业务列表
					MfBusApply mfBusApplyTmp = new MfBusApply();
					mfBusApplyTmp.setCusNo(mfBusApply.getCusNo());
					mfBusApplyTmp.setAppId(mfBusApply.getAppId());
					List<MfBusApply> mfBusApplyList = appInterfaceFeign.getOtherApplyList(mfBusApplyTmp);
					dataMap.put("mfBusApplyList", mfBusApplyList);
					dataMap.put("mfbusInfo", mfBusApply);
					// 钉钉 详情页面展示申请信息，不展示合同中的信息
					/*
					 * if(mfBusApply.getAppSts().equals(BizPubParm.APP_STS_PASS)
					 * ){ dataMap.put("showFlag", "pact"); MfBusPact mfBusPact =
					 * new MfBusPact(); mfBusPact =
					 * pactInterfaceFeign.getByAppId(mfBusApply.getAppId());
					 * mfBusPact =
					 * pactInterfaceFeign.processDataForPact(mfBusPact);//处理利率格式
					 * mfBusPact.setFincAmt(MathExtend.moneyStr(mfBusPact.
					 * getPactAmt()/10000)); dataMap.put("mfbusInfo",
					 * mfBusPact); }
					 */
					// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
					Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
					rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
					// 还款方式
					Map<String, ParmDic> repayTypeMap = new CodeUtils().getMapObjByKeyName("REPAY_TYPE");
					repayTypeStr = repayTypeMap.get(mfBusApply.getRepayType()).getOptName();
					Map<String, ParmDic> vouTypeMap = new CodeUtils().getMapObjByKeyName("VOU_TYPE");
					String[] vouTypeArray = mfBusApply.getVouType().split("\\|");
					if (null != vouTypeArray && vouTypeArray.length > 0) {
						for (int i = 0; i < vouTypeArray.length; i++) {
							String vouItem = vouTypeArray[i];
							if (StringUtil.isNotBlank(vouItem)) {
								String vouItemStr = vouTypeMap.get(vouItem).getOptName();
								vouTypeStr = vouTypeStr + vouItemStr + "|";
							}
						}
					}
				}
			}
			if (vouTypeStr.length() > 0) {
				vouTypeStr = vouTypeStr.substring(0, vouTypeStr.length() - 1);
			}
			dataMap.put("rateUnit", rateUnit);
			dataMap.put("repayTypeStr", repayTypeStr);
			dataMap.put("vouTypeStr", vouTypeStr);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 申请产品业务页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputQuery")
	public String inputQuery(Model model, String kindNo, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		CodeUtils codeUtils = new CodeUtils();
		// 根据产品种类编号获取产品信息
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
		String vouType = mfSysKind.getVouTypeDef();
		mfSysKind.setVouType(vouType);// 表单显示默认的担保方式
		String busModel = mfSysKind.getBusModel();// 业务模式
		model.addAttribute("vouType", vouType);
		model.addAttribute("busModel", busModel);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		MfBusApply mfBusApply = new MfBusApply();

		String formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.apply_input, null, null, User.getRegNo(request));
		FormData formapply0007_query = formService.getFormData(formId);
		FormActive[] formActives = formapply0007_query.getFormActives();
		for (int i = 0; i < formActives.length; i++) {
			String parmKey = formActives[i].getFieldSize();// 字典项
			if (!StringUtil.isBlank(parmKey)) {
				List<ParmDic> pList = (List<ParmDic>) codeUtils.getCacheByKeyName(parmKey);
				formActives[i].setOptionArray(pList);
			}
		}
		mfBusApply.setFincRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getFincRate(),
				Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
		mfBusApply.setOverRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getOverFltRateDef(),
				Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
		mfBusApply.setCmpdRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getCmpFltRateDef(),
				Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));

		getObjValue(formapply0007_query, mfBusApply);
		getObjValue(formapply0007_query, mfSysKind);
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfSysKind.getRateType()).getRemark();
		this.changeFormProperty(formapply0007_query, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formapply0007_query, "overRate", "unit", rateUnit);
		this.changeFormProperty(formapply0007_query, "cmpdRate", "unit", rateUnit);
		// 处理还款方式
		List<OptionsList> repayTypeList = getRepayTypeList(mfSysKind.getRepayType(), mfSysKind.getRepayTypeDef());
		this.changeFormProperty(formapply0007_query, "repayType", "optionArray", repayTypeList);
		String formapply0007_queryJson = new Gson().toJson(formapply0007_query);
		model.addAttribute("formapply0007_queryJson", formapply0007_queryJson);
		model.addAttribute("formapply0007_queryJson", formapply0007_queryJson);
		model.addAttribute("formapply0007_query", formapply0007_query);
		model.addAttribute("query", "");
		return "/component/interfaces/appinterface/MfAppBusApply_InputQuery";
	}

	/**
	 * 根据选择产品动态生成对应数据表单 担保方式 获取产品下支持的担保方式
	 * {@link app.component.prdct.action.MfSysKindAction#getVouTypeSelectByNoAjax()}
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/chooseFormAjax")
	@ResponseBody
	public Map<String, Object> chooseFormAjax(String cusNo, String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CodeUtils codeUtils = new CodeUtils();
		try {
			MfSysKind mfSysKind = new MfSysKind();
			mfSysKind = prdctInterfaceFeign.getSysKindById(kindNo);
			String cmpdRateType = mfSysKind.getCmpdRateType();
			String vouType = mfSysKind.getVouTypeDef();
			vouType = mfSysKind.getVouType();
			mfSysKind.setVouType(vouType);// 表单显示默认的担保方式
			String busModel = mfSysKind.getBusModel();// 业务模式

			String formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.apply_input, null, null, User.getRegNo(request));
			FormData formapply0007_query = formService.getFormData(formId);
			FormActive[] formActives = formapply0007_query.getFormActives();
			List<ParmDic> vouTypeList = new ArrayList<ParmDic>();
			for (int i = 0; i < formActives.length; i++) {
				String parmKey = formActives[i].getFieldSize();// 字典项
				if (!StringUtil.isBlank(parmKey)) {
					List<ParmDic> pList = (List<ParmDic>) codeUtils.getCacheByKeyName(parmKey);
					// 担保方式 选项依赖与产品
					if ("VOU_TYPE".equals(parmKey)) {
						for (ParmDic parmDic : pList) {
							if (mfSysKind != null && StringUtil.isNotEmpty(vouType)
									&& vouType.indexOf(parmDic.getOptCode()) == -1) {
								continue;
							}
							vouTypeList.add(parmDic);
						}
					}
					formActives[i].setOptionArray(pList);
				}
			}
			this.changeFormProperty(formapply0007_query, "vouType", "optionArray", vouTypeList);
			// 根据客户号获取客户信息
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
			// 保理申请
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply.setCusNo(cusNo);
			mfBusApply.setCusName(mfCusCustomer.getCusName());
			mfBusApply.setTermType(mfSysKind.getTermType());
			mfBusApply.setFincRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getFincRate(),
					Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
			mfBusApply.setOverRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getOverFltRateDef(),
					Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
			mfBusApply.setCmpdRate(MathExtend.showRateMethod(mfSysKind.getRateType(), mfSysKind.getCmpFltRateDef(),
					Integer.parseInt(mfSysKind.getYearDays()), Integer.parseInt(mfSysKind.getRateDecimalDigits())));
			mfBusApply.setAppTime(DateUtil.getDateTime());
			mfBusApply.setAppTimeShow(DateUtil.getDate());
			// 这里初始化申请ID了，所以新增时在service层没有再生成ID
			mfBusApply.setAppId(WaterIdUtil.getWaterId("app"));
			mfBusApply.setCusMngName(User.getRegName(request));
			mfBusApply.setCusMngNo(User.getRegNo(request));
			mfBusApply.setManageOpNo1(User.getRegNo(request));
			mfBusApply.setManageOpName1(User.getRegName(request));
			mfBusApply.setBusModel(mfSysKind.getBusModel());
			mfBusApply.setKindName(mfSysKind.getKindName());
			// 处理金额，double大于十位会变成科学计数法
			DecimalFormat df = new DecimalFormat(",##0.00");
			if ((mfSysKind.getMinAmt() != null) && (mfSysKind.getMaxAmt() != null)) {
				double minAmtD = mfSysKind.getMinAmt();
				double maxAmtD = mfSysKind.getMaxAmt();
				String minAmt = df.format(minAmtD);// 融资金额下限
				String maxAmt = df.format(maxAmtD);// 融资金额上限
				String amt = minAmt + "-" + maxAmt + "元";
				this.changeFormProperty(formapply0007_query, "appAmt", "alt", amt);
				dataMap.put("minAmt", String.valueOf(minAmtD));
				dataMap.put("maxAmt", String.valueOf(maxAmtD));
			}
			// 处理期限
			if (null != mfSysKind.getTermType()) {
				String termType = mfSysKind.getTermType();// 合同期限 1月 2日
				int minTerm = mfSysKind.getMinTerm();// 合同期限下限
				int maxTerm = mfSysKind.getMaxTerm();// 合同期限上限
				String term = "";
				String termUnit = "";
				if ("1".equals(termType)) {
					term = minTerm + "个月-" + maxTerm + "个月";
					termUnit = "月";
				} else if ("2".equals(termType)) {
					term = minTerm + "日-" + maxTerm + "日";
					termUnit = "日";
				}else {
				}
				dataMap.put("minTerm", String.valueOf(minTerm));
				dataMap.put("maxTerm", String.valueOf(maxTerm));
				dataMap.put("termType", termType);
				dataMap.put("termUnit", termUnit);
				this.changeFormProperty(formapply0007_query, "term", "alt", term);
			}
			getObjValue(formapply0007_query, mfSysKind);
			getObjValue(formapply0007_query, mfBusApply);
			// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
			Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
			String rateUnit = rateTypeMap.get(mfSysKind.getRateType()).getRemark();
			this.changeFormProperty(formapply0007_query, "fincRate", "unit", rateUnit);
			this.changeFormProperty(formapply0007_query, "overRate", "unit", rateUnit);
			this.changeFormProperty(formapply0007_query, "cmpdRate", "unit", rateUnit);
			// 处理还款方式
			List<OptionsList> repayTypeList = getRepayTypeList(mfSysKind.getRepayType(), mfSysKind.getRepayTypeDef());
			this.changeFormProperty(formapply0007_query, "repayType", "optionArray", repayTypeList);

			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String htmlStr = jsonFormUtil.getJsonStr(formapply0007_query, "bootstarpTag", "");
			// 获取线下产品的列表
			mfSysKind = new MfSysKind();
			mfSysKind.setKindProperty("2");
			mfSysKind.setUseFlag("1");
			mfSysKind.setCusType(mfCusCustomer.getCusType());
			mfSysKind.setBrNo(User.getOrgNo(request));
			mfSysKind.setRoleNoArray(User.getRoleNo(request));
			List<MfSysKind> mfSysKindList = prdctInterfaceFeign.getSysKindList(mfSysKind);
			this.changeFormProperty(formapply0007_query, "kindNo", "optionArray", mfSysKindList);
			String jsonArrayStr = prdctInterfaceFeign.getFincUse(kindNo);
			com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(jsonArrayStr);
			// 共同借款人
			// JSONArray coborrower =
			// cusInterface.getCobBorrower(mfCusCustomer.getCusNo());
			// 贷款投向选择组件
			dataMap.put("fincUse", jsonArray);
			this.changeFormProperty(formapply0007_query, "fincUse", "optionArray", jsonArray);
			// dataMap.put("coborrower", coborrower);
			dataMap.put("mfSysKindList", mfSysKindList);
			dataMap.put("cmpdRateType", cmpdRateType);
			dataMap.put("flag", "success");
			// dataMap.put("htmlStr", htmlStr);
			this.changeFormProperty(formapply0007_query, "kindNo", "initValue", kindNo);
			// 给表单里的字段排序
			Arrays.sort(formActives, new Comparator<FormActive>() {
				@Override
				public int compare(FormActive o1, FormActive o2) { // 先比较行，行相同比较列
					if (o1.getRow() == o2.getRow()) {
						if (o1.getFieldCol() == o2.getFieldCol()) {
							return 0;
						} else if (o1.getFieldCol() <= o2.getFieldCol()) {
							return -1;
						} else {
							return 1;
						}
					}
					if (o1.getRow() <= o2.getRow()) {
						return -1;
					} else {
						return 1;
					}
				}
			});
			String formapply0007_queryJson = new Gson().toJson(formapply0007_query);
			dataMap.put("formapply0007_queryJson", formapply0007_queryJson);
			Map<String, Object> map = creditApplyInterfaceFeign.getByCusNoAndKindNo(cusNo, kindNo);
			dataMap.putAll(map);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 申请融资业务
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertForApplyAjax_query")
	@ResponseBody
	public Map<String, Object> insertForApplyAjax_query(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);

			// formId =
			// prdctInterfaceFeign.getFormId(map.get("kindNo").toString(),
			// WKF_NODE.apply_input, null, null);
			String formId = map.get("formId").toString();
			FormData formapply0007_query = formService.getFormData(formId);

			getFormValue(formapply0007_query, map);
			MfBusApply mfBusApply = new MfBusApply();
			setObjValue(formapply0007_query, mfBusApply);
			String vouType = mfBusApply.getVouType();
			if (StringUtil.isNotEmpty(vouType) && "|".equals(vouType.substring(0, 1))) {
				mfBusApply.setVouType(vouType.substring(1, vouType.length()));
			}
			mfBusApply = appInterfaceFeign.insertApplyForApp(mfBusApply);
			dataMap.put("appId", mfBusApply.getAppId());
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获取还款方式选择项列表
	 * 
	 * @param repayType
	 * @return
	 * @throws Exception
	 *             List<OptionsList>
	 * @author zhs
	 * @date 2017-7-19 下午4:08:35
	 */
	private List<OptionsList> getRepayTypeList(String repayType, String repayTypeDef) throws Exception {
		String[] repayTypeArray = repayType.split("\\|");
		Map<String, String> dicMap = new CodeUtils().getMapByKeyName("REPAY_TYPE");
		List<OptionsList> repayTypeList = new ArrayList<OptionsList>();
		if (StringUtil.isNotEmpty(repayTypeDef)) {
			OptionsList op = new OptionsList();
			op.setOptionLabel(dicMap.get(repayTypeDef));
			op.setOptionValue(repayTypeDef);
			repayTypeList.add(op);
		}
		for (int i = 0; i < repayTypeArray.length; i++) {
			if (StringUtil.isNotEmpty(repayTypeDef)) {
				if (repayTypeArray[i].equals(repayTypeDef)) {
					continue;
				}
			}
			OptionsList op = new OptionsList();
			op.setOptionLabel(dicMap.get(repayTypeArray[i]));
			op.setOptionValue(repayTypeArray[i]);
			repayTypeList.add(op);
		}
		return repayTypeList;
	}

}
