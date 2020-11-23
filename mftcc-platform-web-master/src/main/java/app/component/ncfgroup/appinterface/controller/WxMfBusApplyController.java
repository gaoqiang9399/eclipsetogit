package app.component.ncfgroup.appinterface.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import app.base.User;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.ncfgroup.appinterface.feign.WxMfBusApplyFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pactinterface.PactInterfaceFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfSysKindAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 05 14:31:11 CST 2017
 **/
@Controller
@RequestMapping("/wxMfBusApply")
public class WxMfBusApplyController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfSysKindBo
	@Autowired
	private WxMfBusApplyFeign wxMfBusApplyFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	// 全局变量
	// 异步参数
	// 申请信息appId
	// 客户手机号
	// 客户号
	// 产品编号
	// 业务阶段
	// 分页页码
	// 合同号
	// 借据号

	// 申请类型(通过 1 微信 2App)
	/**
	 * 期限类型1-月 2-天
	 */

	/**
	 * AJAX插入或更新银行卡账户信息
	 * 
	 * @author MaHao
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertBankNumAjax")
	public Map<String, Object> insertBankNumAjax(String ajaxData) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		dataMap.put("regNo", User.getRegNo(request));
		dataMap.put("regName", User.getRegName(request));
		dataMap.put("orgNo", User.getOrgNo(request));
		dataMap.put("orgName", User.getOrgName(request));
		
		try {
			Gson gson = new Gson();
			Map<String, Object> map = gson.fromJson(ajaxData, new TypeToken<Map<String, String>>() {
			}.getType());// getMapByJson(ajaxData);
			// 插入或更新 银行卡信息
			dataMap = wxMfBusApplyFeign.insertOrUpdateBankInfo(map);
		} catch (Exception e) {
			//logger.error("插入或更新银行卡账户信息错误", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * AJAX插入或更新银行卡账户信息(无三方验证)
	 * 
	 * @author MaHao
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertBankNumForNoValidationAjax")
	public Map<String, Object> insertBankNumForNoValidationAjax(String ajaxData) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("regNo", User.getRegNo(request));
		dataMap.put("regName", User.getRegName(request));
		dataMap.put("orgNo", User.getOrgNo(request));
		dataMap.put("orgName", User.getOrgName(request));
		try {
			Gson gson = new Gson();
			Map<String, Object> map = gson.fromJson(ajaxData, new TypeToken<Map<String, String>>() {
			}.getType());// getMapByJson(ajaxData);
			// 插入或更新 银行卡信息
			dataMap = wxMfBusApplyFeign.insertOrUpdateBankInfo(map);
		} catch (Exception e) {
			//logger.error("插入或更新银行卡账户信息错误", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 根据客户手机号获取当前客户的银行卡信息
	 * 
	 * @author MaHao
	 * @param cusTel 
	 * @param cusNo 
	 * @param useType 
	 * @param pageNo 
	 * @param pageSize 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getBankCardInfoAjax")
	public Map<String, Object> getBankCardInfoAjax(String cusTel, String cusNo, String useType, Integer pageNo, Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			dataMap = wxMfBusApplyFeign.getBankCardInfo(ipage, cusTel, cusNo, useType);
		} catch (Exception e) {
			//logger.error("查询银行信息错误", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * AJAX插入或更新信用卡账户信息
	 * 
	 * @author MaHao
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCreditCardInfoAjax")
	@ResponseBody
	public Map<String, Object> updateCreditCardInfoAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		dataMap.put("regNo", User.getRegNo(request));
		dataMap.put("regName", User.getRegName(request));
		dataMap.put("orgNo", User.getOrgNo(request));
		dataMap.put("orgName", User.getOrgName(request));
		
		try {
			Gson gson = new Gson();
			Map<String, Object> map = gson.fromJson(ajaxData, new TypeToken<Map<String, String>>() {
			}.getType());// getMapByJson(ajaxData);
			// 插入或更新 银行卡信息
			dataMap = wxMfBusApplyFeign.updateCreditCardInfo(map);
		} catch (Exception e) {
			//logger.error("插入银行信息错误", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 根据客户手机号获取当前客户的信用卡信息
	 * 
	 * @author MaHao
	 * @param cusTel 
	 * @param cusNo 
	 * @param useType 
	 * @param pageSize 
	 * @param pageNo 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCreditCardInfoAjax")
	@ResponseBody
	public Map<String, Object> getCreditCardInfoAjax(String ajaxData, String cusTel, String cusNo, String useType, Integer pageSize, Integer pageNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			dataMap = wxMfBusApplyFeign.getCreditCardInfo(ipage, cusTel, cusNo, useType);
		} catch (Exception e) {
			//logger.error("查询银行信息错误", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 获取产品的状态
	 * @param cusNo 
	 * @param kindNo 
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkIsInBusApplyAjax")
	public Map<String, Object> checkIsInBusApplyAjax(String cusNo, String kindNo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = wxMfBusApplyFeign.isInBusApply(cusNo, kindNo);
		} catch (Exception e) {
			//logger.error("判断此产品是否在业务申请中和合同履行中出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 获取产品的状态
	 * @param cusNo 
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkIsInBusApplyNewAjax")
	public Map<String, Object> checkIsInBusApplyNewAjax( String cusNo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = wxMfBusApplyFeign.isInBusApplyNew(cusNo);
		} catch (Exception e) {
			//logger.error("判断此产品是否在业务申请中和合同履行中出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 获取app版本的信息
	 * @param appVer 
	 * @param channelSourceNo 
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkVersionAjax")
	public Map<String, Object> checkVersionAjax(String appVer, String channelSourceNo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = wxMfBusApplyFeign.checkAppVersion(appVer, channelSourceNo);
		} catch (Exception e) {
			//logger.error("获取版本号出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}


	/**
	 * 获取授信总额，在帐金额及剩余额度
	 * @param cusTel 
	 * @param cusNo 
	 * @param pactNo 
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBusCreditInfoAjax")
	public Map<String, Object> getBusCreditInfoAjax(String ajaxData, String cusTel, String cusNo, String pactNo) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = wxMfBusApplyFeign.getBusCreditInfo(cusTel, cusNo, pactNo);
		} catch (Exception e) {
			//logger.error("获取授信总额，在帐金额及剩余额度错误", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 申请提现(网信)
	 * @param busFincApp 
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/applyWithdrawAjax")
	public Map<String, Object> applyWithdrawAjax(@RequestBody MfBusFincApp busFincApp) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = wxMfBusApplyFeign.insertApplyWithdraw(busFincApp, request);
		} catch (Exception e) {
			//logger.error("申请提现出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 获取合同列表状态(网信)
	 * @param cusNo 
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPactListStsAjax")
	public Map<String, Object> getPactListStsAjax(String cusNo) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = wxMfBusApplyFeign.getPactListSts(cusNo);
		} catch (Exception e) {
			//logger.error("申请提现出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 
	 * @param cusNo 
	 * @param pageSize 
	 * @param pageNo 
	 * @Title: getPactAndFincForFlagAjax @Description: app端在旧的我的借款 列表基础上修改 （应为状态标识变了
	 * flag：1,，受理中（申请，申请提交，还没审批通过）2，可提现（合同，审批通过，应为没有签约了，所以直接有合同，但没有借据）
	 * 3、放款中（借据，放款申请提交，但是还没有复核） 4、已否决（借据被否决） 5、还款中（借据，放款复核通过，完结前）
	 * 6、已完结（借据，借据完结） @param @return @param @throws Exception 参数 @return String
	 * 返回类型 @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/getPactAndFincForFlagAjax")
	public Map<String, Object> getPactAndFincForFlagAjax( String cusNo, Integer pageSize, Integer pageNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);
			ipage.setPageSize(10);
			dataMap = wxMfBusApplyFeign.findByPageForFlag(ipage, cusNo);
		} catch (Exception e) {
			//logger.error("移动端获取融资信息列表出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 根据合同号获取借据列表列表
	 * @param fincId 
	 * @param pageSize 
	 * @param pageNo 
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFincListByCusNoAjax")
	public Map<String, Object> getFincListByCusNoAjax(String fincId, Integer pageSize, Integer pageNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);
			ipage.setPageSize(10);
			dataMap = wxMfBusApplyFeign.findByPageForFinc(ipage, fincId);
		} catch (Exception e) {
			//logger.error("根据客户号获取借据列表出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 根据借据号获取还款计划列表
	 * @param fincId 
	 * @param pageSize 
	 * @param pageNo 
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMfRepayPlanAjax")
	public Map<String, Object> getMfRepayPlanAjax(String fincId, Integer pageSize, Integer pageNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);
			ipage.setPageSize(10);
			dataMap = wxMfBusApplyFeign.getMfRepayPlanByFincId(ipage, fincId);
		} catch (Exception e) {
			//logger.error("根据借据号获取还款计划列表出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 根据借据号获取当前期应还金额
	 * @param fincId 
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCurTermRepayAmtDetailAjax")
	public Map<String, Object> getCurTermRepayAmtDetailAjax(String fincId) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = wxMfBusApplyFeign.getCurTermRepayAmtDetail(fincId);
		} catch (Exception e) {
			//logger.error("根据借据号获取还款计划列表出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 
	 * @param busFincApp 
	 * @Title: loanIsApproveAjax @Description: 放款是否正在审批 @param @return 参数 @return
	 * String 返回类型 @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/getLoanIsApproveAjax")
	public Map<String, Object> getLoanIsApproveAjax(@RequestBody MfBusFincApp busFincApp) {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = wxMfBusApplyFeign.doLoanIsApprove(busFincApp);
		} catch (Exception e) {
			//logger.error("放款是否正在审批出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

}
