package app.component.interfaces.mobileinterface.controller;

import java.util.HashMap;
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
import com.google.gson.reflect.TypeToken;

import app.base.User;
import app.component.cus.entity.BankIdentify;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.interfaces.mobileinterface.feign.AppMfBusApplyFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pactinterface.PactInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfSysKindAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 05 14:31:11 CST 2017
 **/
@Controller
@RequestMapping("/appMfBusApply")
public class AppMfBusApplyController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfSysKindBo
	@Autowired
	private AppMfBusApplyFeign appMfBusApplyFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;

//	/***
//	 * 插入融资基本信息
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/appInsertAjax")
//	@ResponseBody
//	public Map<String, Object> appInsertAjax(String ajaxData, String cusTel, String type) throws Exception {
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		try {
//			String phoneIP = request.getHeader("X-Real-IP");
//			MfBusApply busApply = new MfBusApply();
//			busApply.setPhoneIP(phoneIP);
//			if (StringUtil.isNotEmpty(cusTel)) {
//				// 判断此客户是否锁定
//				if (CacheUtil.getMapByKey(CACHE_KEY.applyMap).containsKey(cusTel)) {
//					dataMap.put("errorCode", "99999");
//					dataMap.put("errorMsg", "业务正在申请中!");
//				} else {
//					// 锁定此客户
//					CacheUtil.putMapkeyInCache(cusTel, DateUtil.getDateTime(), CACHE_KEY.applyMap);
//					dataMap = appMfBusApplyFeign.insert(busApply, cusTel, type, request);
//					// 移除对此客户的锁定
//					CacheUtil.remMapKeyCache(cusTel, CACHE_KEY.applyMap);
//				}
//			} else {
//				dataMap = appMfBusApplyFeign.insertNew(busApply, type, request);
//			}
//		} catch (Exception e) {
//			// 移除对此客户的锁定
//			CacheUtil.remMapKeyCache(cusTel, CACHE_KEY.applyMap);
//			// logger.error("移动端插入融资信息出错", e);
//			dataMap.put("errorCode", "11111");
//			dataMap.put("errorMsg", "服务器异常");
//		}
//		return dataMap;
//	}

	/***
	 * 通过appId或cusTel查询融资信息详情
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/appSelectAjax")
	@ResponseBody
	public Map<String, Object> appSelectAjax(String appId) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfBusApplyFeign.getById(appId,User.getRegNo(request));
		} catch (Exception e) {
			// logger.error("移动端查询融资信息出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/***
	 * 融资基本信息列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/appFindByPageAjax")
	@ResponseBody
	public Map<String, Object> appFindByPageAjax(Integer pageSize, Integer pageNo, String cusNo, String cusTel,
			String stage) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);
			ipage.setPageSize(10);
			dataMap = appMfBusApplyFeign.findByPage(ipage, cusNo, cusTel, stage);
		} catch (Exception e) {
			// logger.error("移动端获取融资信息列表出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 验证是否客户申请融资 根据返回值中data的值判断 客户资料情况 0未注册 1未完善 2已完善
	 * 
	 * @return
	 */
	@RequestMapping(value = "/validateApplyAbleAjax")
	@ResponseBody
	public Map<String, Object> validateApplyAbleAjax(String cusTel, String cusNo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (StringUtil.isNotEmpty(cusTel)) {
				dataMap = appMfBusApplyFeign.validateApplyAble(cusTel);
			} else {
				dataMap = appMfBusApplyFeign.validateApplyAbleNew(cusNo);
			}

		} catch (Exception e) {
			// logger.error("验证客户资料是否完善出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 判断此产品是否在业务申请中和合同履行中
	 * 
	 * @return
	 */
	// public Map<String, Object> checkIsInBusApplyAjax(String ajaxData){
	// Map<String, Object> dataMap = new HashMap<String, Object>();
	// try {
	// dataMap = appMfBusApplyFeign.isInBusApply(cusNo,kindNo);
	// } catch (Exception e) {
	// logger.error("判断此产品是否在业务申请中和合同履行中出错",e);
	// dataMap.put("errorCode", "11111");
	// dataMap.put("errorMsg", "服务器异常");
	// }
	// return dataMap;
	// }

	/**
	 * 通过客户号获取合同列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getMfBusPactListByCusNoAjax")
	@ResponseBody
	public Map<String, Object> getMfBusPactListByCusNoAjax(String cusNo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfBusApplyFeign.getMfBusPactListByCusNo(cusNo);
		} catch (Exception e) {
			// logger.error("通过客户号获取合同列表出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看银行卡开户行信息
	 * 
	 * @return
	 * @author MaHao
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByBankNumAjax")
	@ResponseBody
	public Map<String, Object> getByBankNumAjax(String identifyNumber) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			BankIdentify bankIdentify = new BankIdentify();
			bankIdentify.setIdentifyNumber(identifyNumber);
			dataMap = cusInterfaceFeign.getDataMapById(bankIdentify);
			if (dataMap != null && "success".equals(dataMap.get("flag"))) {
				dataMap.put("errorCode", "00000");
			} else {
				dataMap.put("errorCode", "99999");
			}
		} catch (Exception e) {
			// logger.error("根据银行卡号获取银行开户行信息错误", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * AJAX插入或更新银行卡账户信息
	 * 
	 * @author MaHao
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertBankNumAjax")
	@ResponseBody
	public Map<String, Object> insertBankNumAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Gson gson = new Gson();
			Map<String, Object> map = gson.fromJson(ajaxData, new TypeToken<Map<String, Object>>() {
			}.getType());// getMapByJson(ajaxData);
			//
			if (StringUtil.isEmpty((String) map.get("cusNo"))) {
				dataMap = appMfBusApplyFeign.insertOrUpdateBankInfo(map, request);
			} else {
				dataMap = appMfBusApplyFeign.insertOrUpdateBankInfoNew(map, request);
			}
		} catch (Exception e) {
//			logger.error("插入银行信息错误", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 根据客户手机号或者客户号获取当前客户的银行卡信息
	 * 
	 * @author MaHao
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBankCardInfoAjax")
	@ResponseBody
	public Map<String, Object> getBankCardInfoAjax(Integer pageSize, Integer pageNo, String cusNo, String cusTel) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if (StringUtil.isNotEmpty(cusTel)) {
				dataMap = appMfBusApplyFeign.getBankCardInfo(ipage, cusTel);
			} else {
				dataMap = appMfBusApplyFeign.getBankCardInfoNew(ipage, cusNo);
			}
		} catch (Exception e) {
//			logger.error("查询银行信息错误", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 获取借款总额及剩余本金总和
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBusFincAppInfoAjax")
	@ResponseBody
	public Map<String, Object> getBusFincAppInfoAjax(String cusTel) {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfBusApplyFeign.getBusFincAppInfo(cusTel);
		} catch (Exception e) {
//			logger.error("查询银行信息错误", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 获取授信总额，在帐金额及剩余额度
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBusCreditInfoAjax")
	@ResponseBody
	public Map<String, Object> getBusCreditInfoAjax(String cusTel, String cusNo, String pactNo) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (StringUtil.isNotEmpty(cusTel)) {
				dataMap = appMfBusApplyFeign.getBusCreditInfo(cusTel, pactNo);
			} else {
				dataMap = appMfBusApplyFeign.getBusCreditInfoNew(cusNo, pactNo);
			}
		} catch (Exception e) {
//			logger.error("查询银行信息错误", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 申请提现
	 * 
	 * @return
	 */
	@RequestMapping(value = "/applyWithdrawAjax")
	@ResponseBody
	public Map<String, Object> applyWithdrawAjax(MfBusFincApp busFincApp) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfBusApplyFeign.insertApplyWithdraw(busFincApp, request);
		} catch (Exception e) {
//			logger.error("申请提现出错,申请信息" + new Gson().toJson(busFincApp), e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 
	 * @Title: loanIsApproveAjax @Description: 放款是否正在审批 @param @return
	 *         参数 @return String 返回类型 @throws
	 */
	@RequestMapping(value = "/getLoanIsApproveAjax")
	@ResponseBody
	public Map<String, Object> getLoanIsApproveAjax(MfBusFincApp busFincApp) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfBusApplyFeign.getLoanIsApproveBo(busFincApp);
		} catch (Exception e) {
//			logger.error("放款是否正在审批出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 获取提现费用
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBusFeeAjax")
	@ResponseBody
	public Map<String, Object> getBusFeeAjax(String appId,String amt,String term,String termType) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfBusApplyFeign.getBusFee(appId, amt, term, termType);
		} catch (Exception e) {
//			logger.error("移动端根据申请号获取费用信息出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "获取费用失败，请联系管理员");
		}
		return dataMap;
	}

	/**
	 * 获取费用明细
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getFeeInfoAjax")
	@ResponseBody
	public Map<String, Object> getFeeInfoAjax(String kindNo) {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfBusApplyFeign.getFeeInfo(kindNo);
		} catch (Exception e) {
//			logger.error("移动端根据产品号获取费用信息出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "获取费用失败，请联系管理员");
		}
		return dataMap;

	}

	/**
	 * 新增授信 20170718暂不使用 by MaHao
	 * 
	 * @return
	 * @throws Exception
	 */
	/*
	 * public Map<String, Object> insertCreditAjax(String ajaxData) throws
	 * Exception { FormService formService = new FormService();
	 * ActionContext.initialize(request, response); Map<String, Object> dataMap
	 * = new HashMap<String, Object>(); MfCusCreditApply mfCusCreditApply = new
	 * MfCusCreditApply(); try{
	 * dataMap=appMfBusApplyFeign.insertCreditStartProcess(cusTel,
	 * mfCusCreditApply, request);//getMapByJson(ajaxData); }catch(Exception e){
	 * e.printStackTrace(); dataMap.put("errorCode", "99999");
	 * dataMap.put("errorMsg", e.getMessage()); } return dataMap; }
	 */

	/**
	 * 获取客户经理信息 存在该客户经理则执行更新客户经理操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCusMngNameById")
	@ResponseBody
	public Map<String, Object> getCusMngNameById(Model model, String cusNo, String cusMngNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfBusApplyFeign.getCusMngNameById(cusNo, cusMngNo);
		} catch (Exception e) {
//			logger.error("获取客户经理信息错误", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 获取移动端基本配置信息，分享内容，分享链接，客服电话..
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAppSettingAjax")
	@ResponseBody
	public Map<String, Object> getAppSettingAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfBusApplyFeign.getAppSetting();
		} catch (Exception e) {
//			logger.error("获取移动端基本配置错误", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	@RequestMapping(value = "/getMfCusBankAccManageByIdAjax")
	@ResponseBody
	public Map<String, Object> getMfCusBankAccManageByIdAjax(String bankAccId) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfBusApplyFeign.getCusBankAccManageById(bankAccId);
		} catch (Exception e) {
//			logger.error("获取银行卡信息出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}
}
