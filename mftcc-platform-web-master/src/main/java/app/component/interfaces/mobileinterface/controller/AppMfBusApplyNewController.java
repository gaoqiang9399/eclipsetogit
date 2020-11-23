package app.component.interfaces.mobileinterface.controller;

import java.util.HashMap;
import java.util.Map;


import app.base.User;
import app.component.hzeyinterface.HzeyinterfaceFeign;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.interfaces.mobileinterface.feign.AppMfBusApplyNewFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfSysKindAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 05 14:31:11 CST 2017
 **/
@Controller
@RequestMapping("/appMfBusApplyNew")
public class AppMfBusApplyNewController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfSysKindBo
	@Autowired
	private AppMfBusApplyNewFeign appMfBusApplyNewFeign;
	@Autowired
	private HzeyinterfaceFeign hzeyinterfaceFeign;

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
			dataMap = appMfBusApplyNewFeign.findByPage(ipage, cusNo, cusTel, stage, User.getRegNo(request));
		} catch (Exception e) {
			// logger.error("移动端获取融资信息列表出错",e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	@RequestMapping(value = "/getPactAndFincAjax")
	@ResponseBody
	public Map<String, Object> getPactAndFincAjax(Integer pageSize, Integer pageNo, String cusNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);
			ipage.setPageSize(10);
			dataMap = appMfBusApplyNewFeign.findByPage1(ipage, cusNo);
		} catch (Exception e) {
			// logger.error("移动端获取融资信息列表出错",e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 
	 * @Title: getPactAndFincForFlagAjax @Description: app端在旧的我的借款 列表基础上修改
	 * （应为状态标识变了 flag：1,，受理中（申请，申请提交，还没审批通过）2，可提现（合同，审批通过，应为没有签约了，所以直接有合同，但没有借据）
	 * 3、放款中（借据，放款申请提交，但是还没有复核） 4、还款中（借据，放款复核通过，完结钱）
	 * 5、已完结（借据，借据完结）） @param @return @param @throws Exception 参数 @return String
	 * 返回类型 @throws
	 */
	@RequestMapping(value = "/getPactAndFincForFlagAjax")
	@ResponseBody
	public Map<String, Object> getPactAndFincForFlagAjax(Integer pageSize, Integer pageNo, String cusNo)
			throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);
			ipage.setPageSize(10);
			dataMap = appMfBusApplyNewFeign.findByPageForFlag(ipage, cusNo);
		} catch (Exception e) {
			// logger.error("移动端获取融资信息列表出错",e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 获取产品的状态
	 * 
	 * @return
	 */
	@RequestMapping(value = "/checkIsInBusApplyAjax")
	@ResponseBody
	public Map<String, Object> checkIsInBusApplyAjax(String cusNo,String kindNo,String terminalType) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfBusApplyNewFeign.isInBusApply(cusNo, kindNo, terminalType);
		} catch (Exception e) {
//			logger.error("判断此产品是否在业务申请中和合同履行中出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 
	 * @Title: checkIsInBusPactAjax @Description: 判断该客户是否有合同（已签约） @param @return
	 * 参数 @return String 返回类型 @throws
	 */
	@RequestMapping(value = "/checkIsInBusPactAjax")
	@ResponseBody
	public Map<String, Object> checkIsInBusPactAjax(String cusNo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfBusApplyNewFeign.isInBusPact(cusNo);
		} catch (Exception e) {
//			logger.error("判断该客户是否有合同（已签约）出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 
	 * @Title: appSelectAjax @Description:
	 * 通过客户号查询还款列表 @param @return @param @throws Exception 参数 @return String
	 * 返回类型 @throws
	 */
	@RequestMapping(value = "/appSelectRepayListAjax")
	@ResponseBody
	public Map<String, Object> appSelectRepayListAjax(String cusNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appMfBusApplyNewFeign.getRepayListByCusNo(cusNo);
		} catch (Exception e) {
//			logger.error("移动端通过客户号查询还款列表 出错", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

}
