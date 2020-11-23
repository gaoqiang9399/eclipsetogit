package app.component.interfaces.mobileinterface.controller;

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
import com.core.struts.taglib.JsonTableUtil;

import app.component.interfaces.mobileinterface.entity.MfCusRecommend;
import app.component.interfaces.mobileinterface.feign.MfCusRecommendFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusRecommendAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Sep 15 15:33:18 CST 2017
 **/
@Controller
@RequestMapping("/mfCusRecommend")
public class MfCusRecommendController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusRecommendBo
	@Autowired
	private MfCusRecommendFeign mfCusRecommendFeign;
	// 全局变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String rdCusTel, String agenciesUid) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		model.addAttribute("rdCusTel", rdCusTel);
		String comReportFlag = new CodeUtils().getSingleValByKey("COM_REPORT_FLAG");// 判断跳转认证报告页面
																					// 1:网信;
																					// 0:杭州恩义
		String openPage = "component/interfaces/mobileinterface/MfCusRecommend_List";
		if ("0".equals(comReportFlag)) {
			openPage = "component/interfaces/mobileinterface/MfCusRecommend_List";
		} else if ("1".equals(comReportFlag)) {
			model.addAttribute("agenciesUid", agenciesUid);
			openPage = "component/interfaces/mobileinterface/MfCusRecommend_ListWx";
		}else {
		}
		return openPage;
	}

	/**
	 * 客户推荐统计客户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListCommentPage")
	public String getListCommentPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		String comReportFlag = new CodeUtils().getSingleValByKey("COM_REPORT_FLAG");// 判断跳转认证报告页面
		String openPage = "component/interfaces/mobileinterface/MfCusRecommend_ListComment";
		if ("0".equals(comReportFlag)) {
			openPage = "component/interfaces/mobileinterface/MfCusRecommend_ListComment";
		} else if ("1".equals(comReportFlag)) {
			openPage = "component/interfaces/mobileinterface/MfCusRecommend_ListWxComment";
		}else {
		}
		return openPage;
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
			String ajaxData, String rdCusTel, String agenciesUid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusRecommend mfCusRecommend = new MfCusRecommend();
		String redDate = request.getParameter("redDate");
		String endDate = request.getParameter("endDate");
		try {
			mfCusRecommend.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusRecommend.setCriteriaList(mfCusRecommend, ajaxData);// 我的筛选

			// mfCusRecommend.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusRecommend,"1000000001");//记录级权限控制方法
			if (StringUtil.isNotEmpty(rdCusTel)) {
				mfCusRecommend.setRdCusTel(rdCusTel);
			}

			if (StringUtil.isNotEmpty(agenciesUid)) {
				mfCusRecommend.setAgenciesUid(agenciesUid);
			}
			if (StringUtil.isNotEmpty(redDate)) {
				mfCusRecommend.setRedDate(redDate);// 登记
			}
			if (StringUtil.isNotEmpty(endDate)) {
				mfCusRecommend.setExt5(endDate);// 登记日期
			}
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfCusRecommendFeign.findByPage(ipage, mfCusRecommend);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/***
	 * 网信资金金机构推荐客户列表 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findWxByPageAjax")
	@ResponseBody
	public Map<String, Object> findWxByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData, String rdCusTel, String agenciesUid) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusRecommend mfCusRecommend = new MfCusRecommend();
		String redDate = request.getParameter("redDate");
		String endDate = request.getParameter("endDate");
		try {
			mfCusRecommend.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusRecommend.setCriteriaList(mfCusRecommend, ajaxData);// 我的筛选
			// mfCusRecommend.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusRecommend,"1000000001");//记录级权限控制方法
			if (StringUtil.isNotEmpty(agenciesUid)) {
				mfCusRecommend.setAgenciesUid(agenciesUid);
			}
			if (StringUtil.isNotEmpty(redDate)) {
				mfCusRecommend.setRedDate(redDate);// 登记
			}
			if (StringUtil.isNotEmpty(endDate)) {
				mfCusRecommend.setExt5(endDate);// 登记日期
			}
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfCusRecommendFeign.findWxByPage(ipage, mfCusRecommend);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/***
	 * 移动端我的分享列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCusRecommendListAjax")
	@ResponseBody
	public Map<String, Object> getCusRecommendListAjax(String rdCusTel) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusRecommend mfCusRecommend = new MfCusRecommend();
		try {
			if (StringUtil.isNotEmpty(rdCusTel)) {
				mfCusRecommend.setRdCusTel(rdCusTel);
			}
			dataMap = mfCusRecommendFeign.getCusRecommendList(mfCusRecommend);
			dataMap.put("errorCode", "00000");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/***
	 * 移动端我的分享列表(分页实现)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCusRecommendPageAjax")
	@ResponseBody
	public Map<String, Object> getCusRecommendPageAjax(Integer pageNo, Integer pageSize, String ajaxData,
			String rdCusTel) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusRecommend mfCusRecommend = new MfCusRecommend();
		try {
			if (StringUtil.isNotEmpty(rdCusTel)) {
				mfCusRecommend.setRdCusTel(rdCusTel);
			}
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(10);
			ipage = mfCusRecommendFeign.findByPage(ipage, mfCusRecommend);
			List mfCusRecommendList = (List) ipage.getResult();
			dataMap.put("mfCusRecommendList", mfCusRecommendList);
			dataMap.put("errorCode", "00000");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/***
	 * 根据时间获取推荐人数和推荐人借款数
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getperCountAndLoanAmtAjax")
	@ResponseBody
	public Map<String, Object> getperCountAndLoanAmtAjax(String rdCusTel, String agenciesUid) throws Exception {
		ActionContext.initialize(request, response);
		MfCusRecommend mfCusRecommend = new MfCusRecommend();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String redDate = request.getParameter("redDate");
		String endDate = request.getParameter("endDate");
		try {
			if (StringUtil.isNotEmpty(rdCusTel)) {
				mfCusRecommend.setRdCusTel(rdCusTel);
			}
			if (StringUtil.isNotEmpty(agenciesUid)) {
				mfCusRecommend.setAgenciesUid(agenciesUid);
			}
			if (StringUtil.isNotEmpty(redDate)) {
				mfCusRecommend.setRedDate(redDate);// 登记日期
			}
			if (StringUtil.isNotEmpty(endDate)) {
				mfCusRecommend.setExt5(endDate);// 登记日期
			}
			// 自定义查询Bo方法
			dataMap = mfCusRecommendFeign.getperCountAndLoanAmt(mfCusRecommend);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/***
	 * 网信专用 根据时间获取借款人和借款金额
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPutoutAmtAndNumAjax")
	@ResponseBody
	public Map<String, Object> getPutoutAmtAndNumAjax(String agenciesUid) throws Exception {
		ActionContext.initialize(request, response);
		MfCusRecommend mfCusRecommend = new MfCusRecommend();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String redDate = request.getParameter("redDate");
		String endDate = request.getParameter("endDate");
		try {
			if (StringUtil.isNotEmpty(agenciesUid)) {
				mfCusRecommend.setAgenciesUid(agenciesUid);
			}
			if (StringUtil.isNotEmpty(redDate)) {
				mfCusRecommend.setRedDate(redDate);// 登记日期
			}
			if (StringUtil.isNotEmpty(endDate)) {
				mfCusRecommend.setExt5(endDate);// 登记日期
			}
			// 自定义查询Bo方法
			dataMap = mfCusRecommendFeign.getPutoutAmtAndNum(mfCusRecommend);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/***
	 * 杭州恩义（客户手机号统计） 客户统计信息列表查询
	 * @param ipage 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findCommentByPageAjax")
	@ResponseBody
	public Map<String, Object> findCommentByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData, Ipage ipage) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusRecommend mfCusRecommend = new MfCusRecommend();
		try {
			mfCusRecommend.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusRecommend.setCriteriaList(mfCusRecommend, ajaxData);// 我的筛选
			// mfCusRecommend.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusRecommend,"1000000001");//记录级权限控制方法
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusRecommend", mfCusRecommend));
			// 自定义查询Bo方法
			ipage = mfCusRecommendFeign.findCommentByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/***
	 * 网信（资金机构统计） 客户统计信息列表查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findWxCommentByPageAjax")
	@ResponseBody
	public Map<String, Object> findWxCommentByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusRecommend mfCusRecommend = new MfCusRecommend();
		try {
			mfCusRecommend.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusRecommend.setCriteriaList(mfCusRecommend, ajaxData);// 我的筛选
			// mfCusRecommend.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusRecommend,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfCusRecommendFeign.findWxCommentByPage(ipage, mfCusRecommend);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/***
	 * 客户打款更新状态
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/remittanceMoneyAjax")
	@ResponseBody
	public Map<String, Object> remittanceMoneyAjax(String agenciesUid, String rdCusTel) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusRecommend mfCusRecommend = new MfCusRecommend();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String brokerage = request.getParameter("brokerage");
		String redDate = request.getParameter("redDate");
		try {
			if (StringUtil.isNotEmpty(rdCusTel)) {
				mfCusRecommend.setRdCusTel(rdCusTel);
			}
			if (StringUtil.isNotEmpty(agenciesUid)) {
				mfCusRecommend.setAgenciesUid(agenciesUid);
			}
			if (StringUtil.isNotEmpty(brokerage)) {
				mfCusRecommend.setExt2(brokerage);// 打款金额
			}
			if (StringUtil.isNotEmpty(redDate)) {
				mfCusRecommend.setRedDate(redDate);// 登记日期
			}
			// 自定义查询Bo方法
			dataMap = mfCusRecommendFeign.remittanceMoney(mfCusRecommend);
			dataMap.put("flag", "success");
			dataMap.put("msg", "打款成功");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
}
