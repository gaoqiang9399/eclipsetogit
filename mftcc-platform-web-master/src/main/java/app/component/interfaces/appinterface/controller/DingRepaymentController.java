package app.component.interfaces.appinterface.controller;

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

import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;

import app.component.app.entity.MfBusAppKind;
import app.component.calccoreinterface.CalcRepaymentInterfaceFeign;
import app.component.cus.entity.MfCusCustomer;
import app.component.msgconf.entity.CuslendWarning;
import app.component.msgconfinterface.MsgConfInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincAndRepay;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.repay.entity.MfReceivableBean;
import app.component.pact.repay.entity.MfRepaymentBean;
import app.component.pactinterface.PactInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 钉钉还款信息
 * 
 * @author MaHao
 * @date 2017-8-15 上午10:50:24
 */
@Controller
@RequestMapping("/dingRepayment")
public class DingRepaymentController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private CalcRepaymentInterfaceFeign calcRepaymentInterfaceFeign;
	// 全局变量
	// 提前还款使用
	/**
	 * 到期日期
	 */
	private MsgConfInterfaceFeign msgConfInterfaceFeign;
	private PactInterfaceFeign pactInterfaceFeign;
	// 异步参数
	// 表单变量

	/**
	 * 还款到期列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRepayToDatePage")
	public String getRepayToDatePage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		return "/component/interfaces/appinterface/DingRepayment_List";

	}

	/**
	 * 还款到期列表数据请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRepayToDateAjax")
	@ResponseBody
	public Map<String, Object> getRepayToDateAjax(String ajaxData, String warningType, Integer pageNo, Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String pactWarning = "0";
		String repayWarning = "0";
		MfBusFincAndRepay fincAndRepay = new MfBusFincAndRepay();
		CuslendWarning cuslendWarning1 = new CuslendWarning();
		cuslendWarning1.setCuslendWarnNo("LOAN_TO_DATE_WARN");// 合同到期预警
		cuslendWarning1.setFlag("1");
		cuslendWarning1 = msgConfInterfaceFeign.getByIdAndFlag(cuslendWarning1);
		if (null != cuslendWarning1) {
			pactWarning = String.valueOf(cuslendWarning1.getCuslendDays());
		}
		CuslendWarning cuslendWarning2 = new CuslendWarning();
		cuslendWarning2.setCuslendWarnNo("REPAY_TO_DATE_WARN");// 还款到期预警
		cuslendWarning2.setFlag("1");
		cuslendWarning2 = msgConfInterfaceFeign.getByIdAndFlag(cuslendWarning2);
		if (null != cuslendWarning2) {
			repayWarning = String.valueOf(cuslendWarning2.getCuslendDays());
		}
		// 从页面传值
		// String warningType = request.getParameter("warningType");//预警类型 0 还款
		// 1合同
		String scopeType = "0";
		// 根据预警天数计算---合同结束日期
		String pactWarningDate = DateUtil.getDate();
		if (StringUtil.isNotEmpty(pactWarningDate)) {
			pactWarningDate = DateUtil.addByDay(Integer.parseInt(pactWarning));
		}
		String repayWarningDate = DateUtil.getDate();
		// 根据预警天数计算---还款结束日期
		if (StringUtil.isNotEmpty(repayWarningDate)) {
			repayWarningDate = DateUtil.addByDay(Integer.parseInt(repayWarning));
		}
		try {
			// 取出ajax数据
			Gson gson = new Gson();
			JSONArray jsonArray = gson.fromJson(ajaxData, JSONArray.class);
			if (jsonArray.get(0) instanceof JSONArray) {
				JSONArray jsonArraySub = jsonArray.getJSONArray(0);
				for (int i = 0; i < jsonArraySub.size(); i++) {
					JSONObject jsonObj = (JSONObject) jsonArraySub.get(i);
					if ("scope".equals((String) jsonObj.get("condition"))
							&& StringUtil.isNotEmpty((String) jsonObj.get("value"))) {
						scopeType = (String) jsonObj.get("value");
						if ("0".equals(scopeType)) {// 全部
							scopeType = "0";
						} else if ("1".equals(scopeType)) {// 合同到期
							scopeType = "1";
							fincAndRepay.setPactBeginDate(DateUtil.getDate());
							fincAndRepay.setPactEndDate(pactWarningDate);
						} else if ("2".equals(scopeType)) {// 还款到期
							scopeType = "2";
							fincAndRepay.setPlanBeginDate(DateUtil.getDate());
							fincAndRepay.setPlanEndDate(repayWarningDate);
						}else {
						}
					}
				}
			} else if (StringUtil.isNotEmpty(warningType)) {// 预警页面跳转过来
				if ("0".equals(warningType)) {// 还款到期
					scopeType = "2";
					fincAndRepay.setPlanBeginDate(DateUtil.getDate());
					fincAndRepay.setPlanEndDate(repayWarningDate);
				} else if ("1".equals(warningType)) {// 合同到期
					scopeType = "1";
					fincAndRepay.setPactBeginDate(DateUtil.getDate());
					fincAndRepay.setPactEndDate(pactWarningDate);
				}else {
				}

			}else {
			}
			fincAndRepay.setCustomQuery(ajaxData);// 自定义查询参数赋值
			fincAndRepay.setCriteriaList(fincAndRepay, ajaxData);// 我的筛选
			fincAndRepay.setCustomSorts(ajaxData);
			// this.getRoleConditions(mfPactFiveclass,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = pactInterfaceFeign.getRepayToDateByPage(ipage, fincAndRepay, scopeType, pactWarningDate);
			List<MfBusFincAndRepay> list = (List<MfBusFincAndRepay>) ipage.getResult();
			/*
			 * JsonTableUtil jtu = new JsonTableUtil(); String tableHtml =
			 * jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(),
			 * ipage,true); ipage.setResult(tableHtml);
			 */
			dataMap.put("data", list);
		} catch (Exception e) {
//			logger.error("获取还款到期列表失败", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("获取还款到期列表失败"));
		}
		return dataMap;
	}

	/**
	 * 还款逾期列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRepayOverDatePage")
	public String getRepayOverDatePage(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		return "/component/interfaces/appinterface/DingRepayOverDate_List";
	}

	/**
	 * 还款逾期列表数据请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRepayOverDateAjax")
	@ResponseBody
	public Map<String, Object> getRepayOverDateAjax(String ajaxData, String warningType, Integer pageNo, Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String pactWarning = "0";
		String repayWarning = "0";
		CuslendWarning cuslendWarning1 = new CuslendWarning();
		cuslendWarning1.setCuslendWarnNo("LOAN_OVER_DATE_WARN");// 合同逾期预警
		cuslendWarning1.setFlag("1");
		cuslendWarning1 = msgConfInterfaceFeign.getByIdAndFlag(cuslendWarning1);
		if (null != cuslendWarning1) {
			pactWarning = String.valueOf(cuslendWarning1.getCuslendDays());
		}

		CuslendWarning cuslendWarning2 = new CuslendWarning();
		cuslendWarning2.setCuslendWarnNo("REPAY_OVER_DATE_WARN");// 还款逾期预警
		cuslendWarning2.setFlag("1");
		cuslendWarning2 = msgConfInterfaceFeign.getByIdAndFlag(cuslendWarning2);
		if (null != cuslendWarning2) {
			repayWarning = String.valueOf(cuslendWarning2.getCuslendDays());
		}
		MfBusFincAndRepay fincAndRepay = new MfBusFincAndRepay();
		// String warningType = request.getParameter("warningType");//预警类型 0 还款
		// 1合同

		String scopeType = "0";
		// 根据预警天数计算---合同结束日期
		String pactWarningDate = DateUtil.getDate();
		if (StringUtil.isNotEmpty(pactWarningDate)) {
			pactWarningDate = DateUtil.addByDay(-Integer.parseInt(pactWarning));
		}
		String repayWarningDate = DateUtil.getDate();
		// 根据预警天数计算---还款结束日期
		if (StringUtil.isNotEmpty(repayWarningDate)) {
			repayWarningDate = DateUtil.addByDay(-Integer.parseInt(repayWarning));
		}
		try {
			// 取出ajax数据
			Gson gson = new Gson();
			JSONArray jsonArray = gson.fromJson(ajaxData, JSONArray.class);
			if (jsonArray.get(0) instanceof JSONArray) {
				JSONArray jsonArraySub = jsonArray.getJSONArray(0);
				for (int i = 0; i < jsonArraySub.size(); i++) {
					JSONObject jsonObj = (JSONObject) jsonArraySub.get(i);
					if ("scope".equals((String) jsonObj.get("condition"))
							&& StringUtil.isNotEmpty((String) jsonObj.get("value"))) {
						String scope = (String) jsonObj.get("value");
						if ("0".equals(scope)) {// 全部
							scopeType = "0";
						} else if ("1".equals(scope)) {// 合同逾期
							scopeType = "1";
							fincAndRepay.setPactEndDate(pactWarningDate);
						} else if ("2".equals(scope)) {// 还款逾期
							scopeType = "2";
							fincAndRepay.setPlanEndDate(repayWarningDate);
						}else {
						}
					}
				}
			} else if (StringUtil.isNotEmpty(warningType)) {// 预警页面跳转过来
				if ("0".equals(warningType)) {// 还款逾期
					scopeType = "2";
					fincAndRepay.setPlanBeginDate(DateUtil.getDate());
					fincAndRepay.setPlanEndDate(repayWarningDate);
				} else if ("1".equals(warningType)) {// 合同逾期
					scopeType = "1";
					fincAndRepay.setPactBeginDate(DateUtil.getDate());
					fincAndRepay.setPactEndDate(pactWarningDate);
				}else {
				}

			}else {
			}
			fincAndRepay.setCustomQuery(ajaxData);// 自定义查询参数赋值
			fincAndRepay.setCriteriaList(fincAndRepay, ajaxData);// 我的筛选
			fincAndRepay.setCustomSorts(ajaxData);
			// this.getRoleConditions(mfPactFiveclass,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = pactInterfaceFeign.getRepayOverDateByPage(ipage, fincAndRepay, scopeType, pactWarningDate);
			List<MfBusFincAndRepay> list = (List<MfBusFincAndRepay>) ipage.getResult();
			dataMap.put("data", list);
		} catch (Exception e) {
//			logger.error("获取还款到期列表失败", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("获取还款逾期列表失败"));
		}
		return dataMap;
	}

	/**
	 * 方法描述： 获取还款页面的还款数据
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-15 上午11:02:44
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRepaymentInfo")
	public String getRepaymentInfo(Model model, String fincId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		String repayDate = DateUtil.getDate("yyyyMMdd");
		// 还款页面数据取值 wd
		Map<String, Object> resultMap = calcRepaymentInterfaceFeign.doRepamentJsp(fincId,repayDate);
		// 客户信息
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer = (MfCusCustomer) resultMap.get("mfCusCustomer");
		
		// 借据信息
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp = (MfBusFincApp) resultMap.get("mfBusFincApp");
		
		// 还款信息
		MfRepaymentBean mfRepaymentBean = new MfRepaymentBean();
		mfRepaymentBean = (MfRepaymentBean) resultMap.get("mfRepaymentBean");
		
		// 还款计划信息
		List<MfReceivableBean> mfReceivableList = (List<MfReceivableBean>) resultMap.get("mfReceivableBeans");
		
		//相关参数实体
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind = (MfBusAppKind) resultMap.get("mfBusAppKind");
		//处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
		model.addAttribute("rateUnit", rateUnit);
		model.addAttribute("mfReceivableList", mfReceivableList);
		model.addAttribute("query", "");
		return "/component/interfaces/appinterface/DingRepaymentInfo";
	}


}
