package app.component.sec.controller;

import java.util.ArrayList;
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

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.base.User;
import app.component.common.DateUtil;
import app.component.common.EntityUtil;
import app.component.sec.entity.SecUserMarkInfo;
import app.component.sec.feign.SecUserMarkInfoFeign;
import app.util.toolkit.Ipage;

@Controller
@RequestMapping(value ="/secUserMarkInfo")
public class SecUserMarkInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入SecUserMarkInfoFeign
	@Autowired
	private SecUserMarkInfoFeign secUserMarkInfoFeign;
	// 全局变量
	private SecUserMarkInfo secUserMarkInfo;
	private List<SecUserMarkInfo> secUserMarkInfoList;
	// private String userId;
	// private String tableType;
	// private String tableId;
	// private int pageNo;
	private String query;
	// 异步参数
	// private String ajaxData;
	private Map<String, Object> dataMap;
	// 表单变量
	private FormData formsec0011;
	private FormData formsec0012;
	private FormService formService = new FormService();

	public SecUserMarkInfoController() {
		query = "";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", query);
		return "/component/sec/SecUserMarkInfo_List";
	}

	@RequestMapping(value = "/getStatistical")
	public String getStatistical(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", query);
		return "/component/sec/SecUserMarkInfo_Statistical";
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formsec0012 = formService.getFormData("sec0012");
		model.addAttribute("formsec0012", formsec0012);
		model.addAttribute("query", query);
		return "/component/sec/SecUserMarkInfo_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formsec0012 = formService.getFormData("sec0012");
		getFormValue(formsec0012);
		secUserMarkInfo = new SecUserMarkInfo();
		setObjValue(formsec0012, secUserMarkInfo);
		secUserMarkInfoFeign.insert(secUserMarkInfo);
		getObjValue(formsec0012, secUserMarkInfo);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("secUserMarkInfo", secUserMarkInfo));
		secUserMarkInfoList = (List<SecUserMarkInfo>) secUserMarkInfoFeign.findByPage(ipage, secUserMarkInfo).getResult();
		model.addAttribute("formsec0012", formsec0012);
		model.addAttribute("secUserMarkInfo", secUserMarkInfo);
		model.addAttribute("secUserMarkInfoList", secUserMarkInfoList);
		model.addAttribute("query", query);
		return "/component/sec/SecUserMarkInfo_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String userId) throws Exception {
		ActionContext.initialize(request, response);
		formsec0011 = formService.getFormData("sec0011");
		getFormValue(formsec0011);
		secUserMarkInfo = new SecUserMarkInfo();
		secUserMarkInfo.setUserId(userId);
		secUserMarkInfo = secUserMarkInfoFeign.getById(secUserMarkInfo);
		getObjValue(formsec0011, secUserMarkInfo);
		model.addAttribute("formsec0011", formsec0011);
		model.addAttribute("secUserMarkInfo", secUserMarkInfo);
		model.addAttribute("query", query);
		return "/component/sec/SecUserMarkInfo_Detail";
	}

	@ResponseBody
	@RequestMapping(value = "/getAllLoginAjax")
	public Map<String, Object> getAllLoginAjax() throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		String loginDate = User.getSysDate(request);
		Map<String, String> map = new HashMap<String, String>();
		map.put("loginDate", loginDate);
		map.put("beforeDate", DateUtil.addByDay(loginDate, -1));
		Map<String, Long> maps = secUserMarkInfoFeign.getAllLogin(map);
		//以前lease_base项目是Long， 但是spring cloud项目中的mybatis中报转换类型异常，因此从web工程开始修改。
//		java.math.BigDecimal cannot be cast to java.lang.Long (through reference chain: java.util.HashMap[\"TO_DAY_ERROR\"])","path":"/secUserMarkInfo/getAllLogin"}
		List<Object> datas = new ArrayList<Object>();
		Map<String, String> keyVal = new HashMap<String, String>();
		// 登记人
		keyVal.put("before", maps.get("BEFORE_DAY_USER") + "");
		keyVal.put("today", maps.get("TO_DAY_USER") + "");
		datas.add(keyVal);
		// 登记次数
		keyVal = new HashMap<String, String>();
		keyVal.put("before", maps.get("BEFORE_DAY_TIMES") + "");
		keyVal.put("today", maps.get("TO_DAY_TIMES") + "");
		datas.add(keyVal);
		// 发生审批业务
		keyVal = new HashMap<String, String>();
		keyVal.put("before", maps.get("BEFORE_DAY_APP") + "");
		keyVal.put("today", maps.get("TO_DAY_APP") + "");
		datas.add(keyVal);
		// 审批通过
		keyVal = new HashMap<String, String>();
		keyVal.put("before", maps.get("BEFORE_DAY_PASS") + "");
		keyVal.put("today", maps.get("TO_DAY_PASS") + "");
		datas.add(keyVal);
		// 登记错误
		keyVal = new HashMap<String, String>();
		keyVal.put("before", maps.get("BEFORE_DAY_ERROR") + "");
		keyVal.put("today", maps.get("TO_DAY_ERROR") + "");
		datas.add(keyVal);

		dataMap.put("flag", "success");
		dataMap.put("datas", datas);
		return dataMap;
	}



	/**
	*@Description: 解锁账户
	*@Author: lyb
	*@date: 2019/7/13
	*/
	@ResponseBody
	@RequestMapping(value = "/unlockAccountAjax")
	public Map<String, Object> unlockAccountAjax(String opNo) throws Exception{
		ActionContext.initialize(request,response);
		dataMap = new HashMap<String, Object>();
		try {
			secUserMarkInfo = new SecUserMarkInfo();
			secUserMarkInfo.setUserId(opNo);
			secUserMarkInfo.setPasswordState("0");
			secUserMarkInfo.setLoginErrorTimes(0);
			secUserMarkInfoFeign.unlockAccount(secUserMarkInfo);
			dataMap.put("flag", "success");
			dataMap.put("msg", "账户解锁成功");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "账户解锁失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		secUserMarkInfo = new SecUserMarkInfo();
		try {
			secUserMarkInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			// secUserMarkInfo.setCriteriaList(secUserMarkInfo, ajaxData);//我的筛选
			// this.getRoleConditions(secUserMarkInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("secUserMarkInfo", secUserMarkInfo));
			ipage = secUserMarkInfoFeign.findByPage(ipage, secUserMarkInfo);
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
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		try {
			formsec0012 = formService.getFormData("sec0012");
			getFormValue(formsec0012, getMapByJson(ajaxData));
			if (this.validateFormData(formsec0012)) {
				secUserMarkInfo = new SecUserMarkInfo();
				setObjValue(formsec0012, secUserMarkInfo);
				secUserMarkInfoFeign.insert(secUserMarkInfo);
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
	@ResponseBody
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		formsec0012 = formService.getFormData("sec0012");
		getFormValue(formsec0012, getMapByJson(ajaxData));
		SecUserMarkInfo secUserMarkInfoJsp = new SecUserMarkInfo();
		setObjValue(formsec0012, secUserMarkInfoJsp);
		secUserMarkInfo = secUserMarkInfoFeign.getById(secUserMarkInfoJsp);
		if (secUserMarkInfo != null) {
			try {
				secUserMarkInfo = (SecUserMarkInfo) EntityUtil.reflectionSetVal(secUserMarkInfo, secUserMarkInfoJsp, getMapByJson(ajaxData));
				secUserMarkInfoFeign.update(secUserMarkInfo);
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
	@ResponseBody
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		secUserMarkInfo = new SecUserMarkInfo();
		try {
			formsec0012 = formService.getFormData("sec0012");
			getFormValue(formsec0012, getMapByJson(ajaxData));
			if (this.validateFormData(formsec0012)) {
				secUserMarkInfo = new SecUserMarkInfo();
				setObjValue(formsec0012, secUserMarkInfo);
				secUserMarkInfoFeign.update(secUserMarkInfo);
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
	@ResponseBody
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(String userId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		dataMap = new HashMap<String, Object>();
		formsec0012 = formService.getFormData("sec0012");
		secUserMarkInfo = new SecUserMarkInfo();
		secUserMarkInfo.setUserId(userId);
		secUserMarkInfo = secUserMarkInfoFeign.getById(secUserMarkInfo);
		getObjValue(formsec0012, secUserMarkInfo, formData);
		if (secUserMarkInfo != null) {
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
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String userId) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		secUserMarkInfo = new SecUserMarkInfo();
		secUserMarkInfo.setUserId(userId);
		try {
			secUserMarkInfoFeign.delete(secUserMarkInfo);
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
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String userId) throws Exception {
		ActionContext.initialize(request, response);
		secUserMarkInfo = new SecUserMarkInfo();
		secUserMarkInfo.setUserId(userId);
		secUserMarkInfoFeign.delete(secUserMarkInfo);
		model.addAttribute("secUserMarkInfo", secUserMarkInfo);
		return getListPage(model);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formsec0012 = formService.getFormData("sec0012");
		getFormValue(formsec0012);
		model.addAttribute("formsec0012", formsec0012);
		boolean validateFlag = this.validateFormData(formsec0012);
		model.addAttribute("query", query);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formsec0012 = formService.getFormData("sec0012");
		getFormValue(formsec0012);
		model.addAttribute("formsec0012", formsec0012);
		boolean validateFlag = this.validateFormData(formsec0012);
		model.addAttribute("query", query);
	}
}
