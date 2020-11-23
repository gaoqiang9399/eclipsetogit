/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MfRepaymentAction.java
 * 包名： app.component.pact.repay.action
 * 说明：
 * @author zhs
 * @date 2016-9-2 上午11:14:55
 * @version V1.0
 */
package app.component.pact.repay.controller;

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
import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfCusCustomer;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.repay.entity.MfLsbqRapaymentBean;
import app.component.pact.repay.entity.MfPrepaymentBean;
import app.component.pact.repay.entity.MfReceivableBean;
import app.component.pact.repay.entity.MfRepaymentBean;
import app.component.pact.repay.feign.MfRepaymentFeign;
import app.tech.oscache.CodeUtils;
import app.util.JsonStrHandling;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 *
 * 类名： MfLiXiRepaymentController
 * 描述：展期前利息还款（利息还款 只还利息）
 * @author wd
 * @date 2018-07-26 15:14:55
 *
 */
@Controller
@RequestMapping("/mfLiXiRepayment")
public class MfLiXiRepaymentController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfRepayHistoryBo
	@Autowired
	private MfRepaymentFeign mfRepaymentFeign;
	/**
	 *
	 * 功能描述:展期前利息还款检查，判断展期前是否收取所有的利息
	 * @param: [fincId]
	 * @return: java.util.Map<java.lang.String,java.lang.Object>
	 * @auther: wd
	 * @date: 2018/7/26 16:01
	 *
	 */
	@RequestMapping(value = "/checkLiXiHuanKuanAjax")
	@ResponseBody
	public Map<String, Object> checkLiXiHuanKuanAjax(String fincId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String,String> parmMap=new HashMap<String,String>();
			parmMap.put("fincId",fincId);//借据号
			dataMap= mfRepaymentFeign.doCheckLiXiHuanKuan(parmMap);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获取还款页面的还款数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @date 2017-05-30上午11:31:20
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/repaymentJsp")
	public String repaymentJsp(Model model, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		// 还款页面数据取值 wd
		Map<String, Object> resultMap = mfRepaymentFeign.doLiXiRepaymentJsp(fincId);
		// 客户信息
		MfCusCustomer mfCusCustomer = (MfCusCustomer) JsonStrHandling.handlingStrToBean(resultMap.get("mfCusCustomer"), MfCusCustomer.class);
		// 借据信息
		MfBusFincApp mfBusFincApp = (MfBusFincApp) JsonStrHandling.handlingStrToBean(resultMap.get("mfBusFincApp"), MfBusFincApp.class);
		// 还款信息
		MfRepaymentBean mfRepaymentBean = (MfRepaymentBean) JsonStrHandling.handlingStrToBean(resultMap.get("mfRepaymentBean"), MfRepaymentBean.class);
		// 还款计划信息
		JSONArray mfReceivableList = JSONArray.fromObject(resultMap.get("mfReceivableBeans"));
//		List<MfReceivableBean> mfReceivableList = (List<MfReceivableBean>) JsonStrHandling.handlingStrToBean(resultMap.get("mfReceivableBeans"), List.class);
		// 相关参数实体
		MfBusAppKind mfBusAppKind  = (MfBusAppKind) JsonStrHandling.handlingStrToBean(resultMap.get("mfBusAppKind"), MfBusAppKind.class);
		String lsbqHuanBenFlag = (String)resultMap.get("lsbqHuanBenFlag");
		//三方放款使用
		String thirdRepaymentFlag=(String)resultMap.get("thirdRepaymentFlag");
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String onlinePayType = new CodeUtils().getSingleValByKey("APP_ONLINE_PAY_TYPE");// 判断贷后还款方式是否显示 0：不显示; 1:显示
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfBusFincApp", mfBusFincApp);
		model.addAttribute("mfRepaymentBean", mfRepaymentBean);
		model.addAttribute("mfReceivableList", mfReceivableList);
		model.addAttribute("mfBusAppKind", mfBusAppKind);
		model.addAttribute("onlinePayType", onlinePayType);
		model.addAttribute("rateUnit", rateTypeMap.get(mfBusFincApp.getRateType()).getRemark());
		model.addAttribute("query", "");
		model.addAttribute("lsbqHuanBenFlag",lsbqHuanBenFlag);
		model.addAttribute("thirdRepaymentFlag",thirdRepaymentFlag);
		return "component/pact/repay/MfRepayment_MfLiXiRepaymentJsp";
	}

	/**
	 * 
	 * 方法描述：利息还款处理方法
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @param ajaxList 
	 * @date 2017-05-25下午2:12:27
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/liXiRepaymentAjax")
	@ResponseBody
	public Map<String, Object> liXiRepaymentAjax(String ajaxData, String ajaxList) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<String, String>();
		try {
			Map<String, Object> parmMap = getMapByJson(ajaxData);
			JSONArray jsonArray = JSONArray.fromObject(ajaxList);
			List<MfReceivableBean> planList = (List<MfReceivableBean>) JSONArray.toList(jsonArray,
					new MfReceivableBean(), new JsonConfig());
			parmMap.put("brNo", User.getOrgNo(request));
			parmMap.put("brName", User.getOrgName(request));
			parmMap.put("opNo", User.getRegNo(request));
			parmMap.put("opName", User.getRegName(request));
			parmMap.put("planList", planList);
			resMap = mfRepaymentFeign.doLiXiRepaymentOperate(parmMap);
			dataMap.put("flag", "success");
			dataMap.put("getInfoUrl", "MfRepayHistoryAction_getById.action?repayId=" + resMap.get("repayId"));
			dataMap.put("repayId", resMap.get("repayId"));
			dataMap.put("wkfRepayId", resMap.get("wkfRepayId"));
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("还款"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("还款"));
			throw e;
		}
		return dataMap;
	}
}
