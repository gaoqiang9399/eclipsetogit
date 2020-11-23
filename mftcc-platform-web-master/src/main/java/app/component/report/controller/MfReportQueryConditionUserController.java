package app.component.report.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.sys.entity.SysUser;
import app.component.sysInterface.SysInterfaceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.finance.util.CwPublicUtil;
import app.component.report.entity.MfReportQueryConditionUser;
import app.component.report.feign.MfReportQueryConditionUserFeign;
import app.component.report.util.MfReportQueryConfigUtil;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: MfReportQueryConditionUserAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Aug 24 16:53:50 CST 2017
 **/
@Controller
@RequestMapping("/mfReportQueryConditionUser")
public class MfReportQueryConditionUserController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfReportQueryConditionUserBo
	@Autowired
	private MfReportQueryConditionUserFeign mfReportQueryConditionUserFeign;
	@Autowired
	private SysInterfaceFeign sysInterfaceFeign;
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
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/report/MfReportQueryConditionUser_List";
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReportQueryConditionUser mfReportQueryConditionUser = new MfReportQueryConditionUser();
		try {
			mfReportQueryConditionUser.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfReportQueryConditionUser.setCriteriaList(mfReportQueryConditionUser, ajaxData);// 我的筛选
			// mfReportQueryConditionUser.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfReportQueryConditionUser,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfReportQueryConditionUserFeign.findByPage(ipage, mfReportQueryConditionUser);
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formreportQueryConfigdetail = formService.getFormData("reportQueryConfigdetail");
			getFormValue(formreportQueryConfigdetail, getMapByJson(ajaxData));
			if (this.validateFormData(formreportQueryConfigdetail)) {
				MfReportQueryConditionUser mfReportQueryConditionUser = new MfReportQueryConditionUser();
				setObjValue(formreportQueryConfigdetail, mfReportQueryConditionUser);
				mfReportQueryConditionUserFeign.insert(mfReportQueryConditionUser);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
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
		FormData formreportQueryConfigdetail = formService.getFormData("reportQueryConfigdetail");
		getFormValue(formreportQueryConfigdetail, getMapByJson(ajaxData));
		MfReportQueryConditionUser mfReportQueryConditionUserJsp = new MfReportQueryConditionUser();
		setObjValue(formreportQueryConfigdetail, mfReportQueryConditionUserJsp);
		MfReportQueryConditionUser mfReportQueryConditionUser = mfReportQueryConditionUserFeign.getById(mfReportQueryConditionUserJsp);
		if (mfReportQueryConditionUser != null) {
			try {
				mfReportQueryConditionUser = (MfReportQueryConditionUser) EntityUtil.reflectionSetVal(
						mfReportQueryConditionUser, mfReportQueryConditionUserJsp, getMapByJson(ajaxData));
				mfReportQueryConditionUserFeign.update(mfReportQueryConditionUser);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
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
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formreportQueryConfigdetail = formService.getFormData("reportQueryConfigdetail");
			getFormValue(formreportQueryConfigdetail, getMapByJson(ajaxData));
			if (this.validateFormData(formreportQueryConfigdetail)) {
				MfReportQueryConditionUser mfReportQueryConditionUser = new MfReportQueryConditionUser();
				setObjValue(formreportQueryConfigdetail, mfReportQueryConditionUser);
				mfReportQueryConditionUserFeign.update(mfReportQueryConditionUser);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formreportQueryConfigdetail = formService.getFormData("reportQueryConfigdetail");
		MfReportQueryConditionUser mfReportQueryConditionUser = new MfReportQueryConditionUser();
		mfReportQueryConditionUser.setId(id);
		mfReportQueryConditionUser = mfReportQueryConditionUserFeign.getById(mfReportQueryConditionUser);
		getObjValue(formreportQueryConfigdetail, mfReportQueryConditionUser, formData);
		if (mfReportQueryConditionUser != null) {
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
	public Map<String, Object> deleteAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReportQueryConditionUser mfReportQueryConditionUser = new MfReportQueryConditionUser();
		mfReportQueryConditionUser.setId(id);
		try {
			mfReportQueryConditionUserFeign.delete(mfReportQueryConditionUser);
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formreportQueryConfigdetail = formService.getFormData("reportQueryConfigdetail");
		model.addAttribute("query", "");
		return "/component/report/MfReportQueryConditionUser_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formreportQueryConfig0001 = formService.getFormData("reportQueryConfig0001");
		getFormValue(formreportQueryConfig0001);
		MfReportQueryConditionUser mfReportQueryConditionUser = new MfReportQueryConditionUser();
		mfReportQueryConditionUser.setId(id);
		mfReportQueryConditionUser = mfReportQueryConditionUserFeign.getById(mfReportQueryConditionUser);
		getObjValue(formreportQueryConfig0001, mfReportQueryConditionUser);
		model.addAttribute("formreportQueryConfig0001", formreportQueryConfig0001);
		model.addAttribute("query", "");
		return "/component/report/MfReportQueryConditionUser_Detail";
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
		FormData formreportQueryConfigdetail = formService.getFormData("reportQueryConfigdetail");
		getFormValue(formreportQueryConfigdetail);
		boolean validateFlag = this.validateFormData(formreportQueryConfigdetail);
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
		FormData formreportQueryConfigdetail = formService.getFormData("reportQueryConfigdetail");
		getFormValue(formreportQueryConfigdetail);
		boolean validateFlag = this.validateFormData(formreportQueryConfigdetail);
	}

	/**
	 * @Description:查询事件
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-8-27 上午10:02:20
	 */
	@RequestMapping(value = "/reportQuery")
	@ResponseBody
	public Map<String, Object> reportQuery(Model model, String reportId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfReportQueryConditionUser mfReportQueryConditionUser = new MfReportQueryConditionUser();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			mfReportQueryConditionUser.setReportId(reportId);
			mfReportQueryConditionUser.setOpNo(User.getRegNo(request));
			mfReportQueryConditionUser.setOpName(User.getRegName(request));
			String querySqlCondition = mfReportQueryConditionUserFeign.reportQuery(mfReportQueryConditionUser);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
			dataMap.put("querySqlCondition", querySqlCondition);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * @Description:报表打开查询查询事件
	 * @return
	 * @throws Exception
	 * @author: ywh
	 * @param type 
	 * @param sqlCondition 
	 * @param reportId 
	 * @date: 2018-01-24 上午10:02:20
	 */
	@RequestMapping(value = "/reportOpenQuery")
	@ResponseBody
	public Map<String, Object> reportOpenQuery(Model model, String ajaxData, String type, String sqlCondition, String reportId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String querySqlCondition = "";
			Map<String,String> map1 = new HashMap();
			map1.put("sqlCondition", sqlCondition);
			map1.put("reportId", reportId);
			map1.put("type", reportId);
			map1.put("opNo", User.getRegNo(request));
			if ("java".equals(type)) {
				sqlCondition = mfReportQueryConditionUserFeign.reportOpenQuery(map1);
				String sql =  sqlCondition;
				JSONObject jsonObject = JSONObject.fromObject(map1);
				if (sql!= null) {
					jsonObject.put("sqlCondition", sql.toString());			
				}else {
					jsonObject.put("sqlCondition","");
				}
				querySqlCondition = jsonObject.toString();
			} else {
				sqlCondition = mfReportQueryConditionUserFeign.reportOpenQuery(map1);
				querySqlCondition = MfReportQueryConfigUtil.pieceTogetherSql(sqlCondition);
				// 增加数据权限
				querySqlCondition = getAuthSqlCondition(reportId, querySqlCondition);
			}
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
			dataMap.put("querySqlCondition", querySqlCondition);

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 增加数据权限
	 * @author zhangdongyang
	 * @param reportId
	 * @param querySqlCondition
	 * @date 2020/8/4 14:32
	 * @return java.lang.String
	 */
	private String getAuthSqlCondition(String reportId, String querySqlCondition) throws Exception {
		// 获取有权限的部门
		SysUser sysUser = new SysUser();
		sysUser.setLoginUser(User.getRegNo(request));
		Map<String, String> brNoOptNoByRoleType = sysInterfaceFeign.getBrNoOptNoByRoleType(sysUser);
		String allOrgNo = brNoOptNoByRoleType.get("allOrgNo");
		if (null != allOrgNo && allOrgNo.endsWith(",")) {
			allOrgNo = allOrgNo.substring(0, allOrgNo.length() - 1);
		}
		// 增加部门权限
		switch (reportId) {
			// 每月新增明细表
			case "report_zgc_16":
				querySqlCondition += " and d.br_no in (" + allOrgNo + ") ";
				break;
			// 权限内合同清单
			case "report_zgc_17":
			// 填报清单
			case "report_zgc_19":
			// 或有表
			case "report_zgc_23":
				querySqlCondition += " and a.br_no in (" + allOrgNo + ") ";
				break;
			// 签约情况
			case "report_zgc_15":
				querySqlCondition += " and b.br_no in (" + allOrgNo + ") ";
				break;
			default:
				break;
		}

		/*// 增加个人权限
		String funRoleType = (String) request.getSession().getAttribute("funRoleType");
		if("1".equals(funRoleType)){
			// 数据权限:本人
			// 根据报表id定制筛选条件
		}*/
		return querySqlCondition;
	}

	@RequestMapping(value = "/updateSqlCondition")
	@ResponseBody
	public Map<String, Object> updateSqlCondition(Model model, String ajaxData, String type, String sqlCondition, String reportId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String querySqlCondition = "";
			Map<String,String> map1 = new HashMap();
			map1.put("sqlCondition", sqlCondition);
			map1.put("reportId", reportId);
			map1.put("type", reportId);
			map1.put("opNo", User.getRegNo(request));
			mfReportQueryConditionUserFeign.updateSqlCondition(map1);
			dataMap.put("flag", "success");
			dataMap.put("querySqlCondition", querySqlCondition);

		} catch (Exception e) {
			dataMap.put("flag", "error");
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * @Description:保存查询条件
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @param reportId 
	 * @param sqlCondition 
	 * @date: 2017-8-25 上午10:18:22
	 */
	@RequestMapping(value = "/saveReoprtSqlCondition")
	@ResponseBody
	public Map<String, Object> saveReoprtSqlCondition(Model model, String ajaxData, String reportId, String sqlCondition) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfReportQueryConditionUser mfReportQueryConditionUser = new MfReportQueryConditionUser();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			mfReportQueryConditionUser.setReportId(reportId);
			mfReportQueryConditionUser.setConditonContent(sqlCondition);
			mfReportQueryConditionUser.setOpNo(User.getRegNo(request));
			mfReportQueryConditionUser.setOpName(User.getRegName(request));
			mfReportQueryConditionUserFeign.saveReoprtSqlCondition(mfReportQueryConditionUser);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * @Description:获取列表数据(1-部门 2-操作员 3-客户类型 4-业务品种 5-押品类别 6-信息来源)
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @param pageSize 
	 * @param pageNo 
	 * @param tableId 
	 * @param tableType 
	 * @date: 2017-8-27 上午10:46:22
	 */
	@RequestMapping(value = "/getReportSqlListDataAjax")
	@ResponseBody
	public Map<String, Object> getReportSqlListDataAjax(String ajaxData, Integer pageSize, Integer pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
 			formMap.put("opNo", User.getRegNo(request));
			Ipage ipage1 = this.getIpage();
			ipage1.setPageSize(pageSize);
			ipage1.setPageNo(pageNo);// 异步传页面翻页参数
			ipage1.setParams(this.setIpageParams("formMap",formMap));
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfReportQueryConditionUserFeign.getReportSqlListBean(ipage1);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * @Description:查询条件显示
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-8-27 下午5:45:27
	 */
	@RequestMapping(value = "/showFormConditionVal")
	@ResponseBody
	public Map<String, Object> showFormConditionVal(Model model, String reportId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = new HashMap<String, String>();
			formMap.put("reportId", reportId);
			formMap.put("opNo", User.getRegNo(request));
			// 自定义查询Bo方法
			String result = mfReportQueryConditionUserFeign.showFormConditionVal(formMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
			dataMap.put("result", result);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * @Description:查询条件显示
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @param reportId 
	 * @date: 2017-8-27 下午5:45:27
	 */
	@RequestMapping(value = "/pieceJavaBeanSql")
	@ResponseBody
	public Map<String, Object> pieceJavaBeanSql(Model model, String ajaxData, String reportId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 自定义查询Bo方法
			Map<String, Object> result = mfReportQueryConditionUserFeign
					.pieceJavaBeanSql(User.getRegNo(request), reportId);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
			JSONObject jsonObject = JSONObject.fromObject(result);
			dataMap.put("result", jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

}
