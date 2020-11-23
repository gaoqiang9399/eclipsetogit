package app.component.interfaces.mobileinterface.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.interfaces.mobileinterface.entity.MfThirdServiceLog;
import app.component.interfaces.mobileinterface.feign.MfThirdServiceLogFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfThirdServiceLogAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 26 14:42:40 CST 2017
 **/
@Controller
@RequestMapping("/mfThirdServiceLog")
public class MfThirdServiceLogController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfThirdServiceLogBo
	@Autowired
	private MfThirdServiceLogFeign mfThirdServiceLogFeign;
	// 全局变量
	// 异步参数

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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfThirdServiceLog mfThirdServiceLog = new MfThirdServiceLog();
		try {
			mfThirdServiceLog.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfThirdServiceLog.setCriteriaList(mfThirdServiceLog, ajaxData);// 我的筛选
			// mfThirdServiceLog.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfThirdServiceLog,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfThirdServiceLogFeign.findByPage(ipage, mfThirdServiceLog);
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
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			mfThirdServiceLogFeign.insert(null);
			dataMap.put("flag", "success");
			dataMap.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 获取第三手机运营商方服务url
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getThirdServiceUrlAjax")
	@ResponseBody
	public Map<String, Object> getThirdServiceUrlAjax(String cusNo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdServiceLogFeign.getThirdServiceUrl(cusNo);
		} catch (Exception e) {
			// logger\.error("获取第三手机运营商方服务url出错",e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 检查手机运营商是否认证 (移动端接口)
	 * 
	 * @return
	 */
	@RequestMapping(value = "/checkThirdDataAjax")
	@ResponseBody
	public Map<String, Object> checkThirdDataAjax(String cusNo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdServiceLogFeign.checkThirdData(cusNo);
		} catch (Exception e) {
			// logger\.error("检查手机运营商是否认证 (移动端接口)出错",e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 获取 百融-反欺诈特殊名单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBrRuleSpecialListAjax")
	@ResponseBody
	public Map<String, Object> getBrRuleSpecialListAjax(String cusNo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdServiceLogFeign.getBrRuleSpecialList(cusNo);
		} catch (Exception e) {
			// logger\.error(" 获取百融-反欺诈特殊名单出错",e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 百融-月度收支等级整合报告
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBrAccountChangeCAjax")
	@ResponseBody
	public Map<String, Object> getBrAccountChangeCAjax(String cusNo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdServiceLogFeign.getBrAccountChangeC(cusNo);
		} catch (Exception e) {
			// logger\.error(" 获取百融-月度收支等级整合报告出错",e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 百融-多次申请规则评分
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBrRuleApplyLoanAjax")
	@ResponseBody
	public Map<String, Object> getBrRuleApplyLoanAjax(String cusNo) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdServiceLogFeign.getBrRuleApplyLoan(cusNo);

		} catch (Exception e) {
			e.printStackTrace();
			// logger\.error("获取百融-多次申请规则评分出错",e);
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

	/**
	 * 圣数-火眼黑名单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getFireEyesBlackAjax")
	@ResponseBody
	public Map<String, Object> getFireEyesBlackAjax(String cusNo) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdServiceLogFeign.getFireEyesBlack(cusNo);

		} catch (Exception e) {
			e.printStackTrace();
			// logger\.error("获取百融-多次申请规则评分出错",e);
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

	/**
	 * 贷后邦-反欺诈(废弃不用 by MaHao) 20171121之后使用{@link #getSauronDHBAjax}
	 * 
	 * @return
	 */

	@RequestMapping(value = "/getDhbGetSauronCAjax")
	@ResponseBody
	public Map<String, Object> getDhbGetSauronCAjax(String cusNo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdServiceLogFeign.getDhbGetSauronC(cusNo);

		} catch (Exception e) {
			e.printStackTrace();
			// logger\.error("获取贷后邦-反欺诈数据出错",e);
			dataMap.put("flag", "error");
		}

		return dataMap;
	}

	/**
	 * 获取索伦数据 贷后帮反欺诈
	 * 
	 * @return
	 * @author MaHao
	 * @date 2017-11-21 下午2:46:17
	 */
	@RequestMapping(value = "/getSauronDHBAjax")
	@ResponseBody
	public Map<String, Object> getSauronDHBAjax(String cusNo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdServiceLogFeign.getSauronDHB(cusNo);
		} catch (Exception e) {
			e.printStackTrace();
			// logger\.error("获取贷后邦-反欺诈数据出错",e);
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

	/**
	 * 天行_运营商三要素
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getMobileVerifyThreeAjax")
	@ResponseBody
	public Map<String, Object> getMobileVerifyThreeAjax(String cusNo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdServiceLogFeign.getMobileVerifyThree(cusNo);

		} catch (Exception e) {
			e.printStackTrace();
			// logger\.error("获取天行_运营商三要素数据出错",e);
			dataMap.put("flag", "error");
		}

		return dataMap;
	}

	/**
	 * 天行-银行卡四要素
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBankCardVerifyFourAjax")
	@ResponseBody
	public Map<String, Object> getBankCardVerifyFourAjax(String cusNo, String bankCard) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdServiceLogFeign.getBankCardVerifyFour(cusNo, bankCard);
		} catch (Exception e) {
			e.printStackTrace();
			// logger\.error("获取天行-银行卡四要素数据出错",e);
			dataMap.put("flag", "error");
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
	public Map<String, Object> getByIdAjax(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfThirdServiceLog mfThirdServiceLog = new MfThirdServiceLog();
		mfThirdServiceLog.setCusNo(cusNo);
		mfThirdServiceLog = mfThirdServiceLogFeign.getById(mfThirdServiceLog);
		if (mfThirdServiceLog != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
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
	public Map<String, Object> deleteAjax(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfThirdServiceLog mfThirdServiceLog = new MfThirdServiceLog();
		mfThirdServiceLog.setCusNo(cusNo);
		try {
			mfThirdServiceLogFeign.delete(mfThirdServiceLog);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 跳转认证信息页面获取的所有数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllThirdServiceInfoAjax")
	@ResponseBody
	public Map<String, Object> getAllThirdServiceInfoAjax(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdServiceLogFeign.getAllThirdServiceInfo(cusNo);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			// logger\.error("获取认证报告出错，客户号：{}，错误信息：",cusNo,e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 跳转认证信息页面获取的所有数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getWangxinReportAjax")
	@ResponseBody
	public Map<String, Object> getWangxinReportAjax(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdServiceLogFeign.getWangxinReport(cusNo);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 跳转运营商认证信息页面获取的所有数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllOperatorInfoAjax")
	@ResponseBody
	public Map<String, Object> getAllOperatorInfoAjax(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdServiceLogFeign.getOperatorReport(cusNo);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 
	 * @Title: getToken @Description: 杭州恩益，获取葫芦数据第三方认证token @param @return
	 *         参数 @return String 返回类型 @throws
	 */
	@RequestMapping(value = "/getHuLuDatasTokenAjax")
	@ResponseBody
	public Map<String, Object> getHuLuDatasTokenAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdServiceLogFeign.getHuLuDatasToken(ajaxData);
		} catch (Exception e) {
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * @Title: insertHuLuDatasServicePwdAjax @Description:
	 *         杭州恩益，葫芦数据第三方提交服务密码 @param @return 参数 @return String 返回类型 @throws
	 */
	@RequestMapping(value = "/insertHuLuDatasServicePwdAjax")
	@ResponseBody
	public Map<String, Object> insertHuLuDatasServicePwdAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdServiceLogFeign.insertHuLuDatasService(ajaxData);
		} catch (Exception e) {
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * @Title: updateHuLuDatasServiceAjax @Description:
	 *         杭州恩益，葫芦数据第三方重获短信 @param @return 参数 @return String 返回类型 @throws
	 */
	@RequestMapping(value = "/updateHuLuDatasServiceAjax")
	@ResponseBody
	public Map<String, Object> updateHuLuDatasServiceAjax(String ajaxData) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdServiceLogFeign.updateHuLuDatasService(ajaxData);
		} catch (Exception e) {
			// logger\.error(" 杭州恩益，葫芦数据重获短信验证码服务密码(移动端接口)出错",e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

	/**
	 * 杭州恩义手动输入验证码保存
	 * 
	 * @return
	 */
	@RequestMapping(value = "/saveZhimaCreditScoreAjax")
	@ResponseBody
	public Map<String, Object> saveZhimaCreditScoreAjax(String cusNo) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String zhimascore = request.getParameter("zhimascore");
			dataMap = mfThirdServiceLogFeign.saveZhimaCreditScore(cusNo, zhimascore);
		} catch (Exception e) {
			// logger\.error(" 杭州恩益，手动输入验证码保存失败",e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "服务器异常");
		}
		return dataMap;
	}

}
