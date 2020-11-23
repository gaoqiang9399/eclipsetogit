package app.component.checkoff.controller;

import app.base.User;
import app.component.checkoff.entity.MfBusCheckoffs;
import app.component.checkoff.feign.MfBusCheckoffsFeign;
import app.component.common.EntityUtil;
import app.component.nmd.entity.ParmDic;
import app.component.sys.entity.SysUser;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfBusCheckoffsAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 18 11:23:06 CST 2017
 **/
@Controller
@RequestMapping("/mfBusCheckoffs")
public class MfBusCheckoffsController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfBusCheckoffsBo
	@Autowired
	private MfBusCheckoffsFeign mfBusCheckoffsFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/checkoff/MfBusCheckoffs_List";
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
			String ajaxData,Ipage ipage) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusCheckoffs mfBusCheckoffs = new MfBusCheckoffs();
		try {
			mfBusCheckoffs.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusCheckoffs.setCriteriaList(mfBusCheckoffs, ajaxData);// 我的筛选
			mfBusCheckoffs.setCustomSorts(ajaxData);// 自定义排序
			// this.getRoleConditions(mfBusCheckoffs,"1000000001");//记录级权限控制方法
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusCheckoffs", mfBusCheckoffs));
			// 自定义查询Bo方法
			ipage = mfBusCheckoffsFeign.findByPage(ipage);
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
			FormData formcheckoffdetail = formService.getFormData("checkoffadd");
			getFormValue(formcheckoffdetail, getMapByJson(ajaxData));
			if (this.validateFormData(formcheckoffdetail)) {
				MfBusCheckoffs mfBusCheckoffs = new MfBusCheckoffs();
				setObjValue(formcheckoffdetail, mfBusCheckoffs);

				mfBusCheckoffsFeign.insert(mfBusCheckoffs);
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
		FormData formcheckoffdetail = formService.getFormData("checkoffdetail");
		getFormValue(formcheckoffdetail, getMapByJson(ajaxData));
		MfBusCheckoffs mfBusCheckoffsJsp = new MfBusCheckoffs();
		setObjValue(formcheckoffdetail, mfBusCheckoffsJsp);
		MfBusCheckoffs mfBusCheckoffs = mfBusCheckoffsFeign.getById(mfBusCheckoffsJsp);
		if (mfBusCheckoffs != null) {
			try {
				mfBusCheckoffs = (MfBusCheckoffs) EntityUtil.reflectionSetVal(mfBusCheckoffs, mfBusCheckoffsJsp,
						getMapByJson(ajaxData));
				mfBusCheckoffsFeign.update(mfBusCheckoffs);
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
			FormData formcheckoffdetail = formService.getFormData("checkoffdetail");
			getFormValue(formcheckoffdetail, getMapByJson(ajaxData));
			if (this.validateFormData(formcheckoffdetail)) {
				MfBusCheckoffs mfBusCheckoffs = new MfBusCheckoffs();
				setObjValue(formcheckoffdetail, mfBusCheckoffs);
				mfBusCheckoffsFeign.update(mfBusCheckoffs);
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
	public Map<String, Object> getByIdAjax(String checkoffId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcheckoffdetail = formService.getFormData("checkoffdetail");
		MfBusCheckoffs mfBusCheckoffs = new MfBusCheckoffs();
		mfBusCheckoffs.setCheckoffId(checkoffId);
		mfBusCheckoffs = mfBusCheckoffsFeign.getById(mfBusCheckoffs);
		getObjValue(formcheckoffdetail, mfBusCheckoffs, formData);
		if (mfBusCheckoffs != null) {
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
	public Map<String, Object> deleteAjax(String checkoffId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusCheckoffs mfBusCheckoffs = new MfBusCheckoffs();
		mfBusCheckoffs.setCheckoffId(checkoffId);
		try {
			mfBusCheckoffs = mfBusCheckoffsFeign.getById(mfBusCheckoffs);
			mfBusCheckoffsFeign.delete(mfBusCheckoffs);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "删除失败！");
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
		FormData formcheckoffdetail = formService.getFormData("checkoffadd");
		Map<String, ParmDic> dicMap = new CodeUtils().getMapObjByKeyName("CHECKOFF_DATASOURCE");
		String dataSourceType = dicMap.get("1").getRemark(); // 核銷的數據來源 1-五級分類
																// 2-逾期天數

		model.addAttribute("dataSourceType", dataSourceType);
		model.addAttribute("formcheckoffdetail", formcheckoffdetail);
		model.addAttribute("query", "");
		return "/component/checkoff/MfBusCheckoffs_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model,Ipage ipage) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcheckoffdetail = formService.getFormData("checkoffdetail");
		getFormValue(formcheckoffdetail);
		MfBusCheckoffs mfBusCheckoffs = new MfBusCheckoffs();
		setObjValue(formcheckoffdetail, mfBusCheckoffs);
		mfBusCheckoffsFeign.insert(mfBusCheckoffs);
		getObjValue(formcheckoffdetail, mfBusCheckoffs);
		this.addActionMessage(model, "保存成功");
		ipage.setParams(this.setIpageParams("mfBusCheckoffs", mfBusCheckoffs));
		List<MfBusCheckoffs> mfBusCheckoffsList = (List<MfBusCheckoffs>) mfBusCheckoffsFeign
				.findByPage(ipage).getResult();
		model.addAttribute("formcheckoffadd", formcheckoffdetail);
		model.addAttribute("query", "");
		return "/component/checkoff/MfBusCheckoffs_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String checkoffId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);

		MfBusCheckoffs mfBusCheckoffs = new MfBusCheckoffs();
		mfBusCheckoffs.setCheckoffId(checkoffId);
		mfBusCheckoffs = mfBusCheckoffsFeign.getById(mfBusCheckoffs);

		String checkOffStatus = mfBusCheckoffs.getCheckoffStatus();
		FormData formcheckoffadd;
		if ("1".equals(checkOffStatus)) {
			Map<String, ParmDic> dicMap = new CodeUtils().getMapObjByKeyName("CHECKOFF_DATASOURCE");
			String dataSourceType = dicMap.get("1").getRemark(); // 核銷的數據來源
																	// 1-五級分類
																	// 2-逾期天數
			model.addAttribute("dataSourceType", dataSourceType);
			formcheckoffadd = formService.getFormData("checkoffs_edite");
		} else {

			formcheckoffadd = formService.getFormData("checkoffdetail_2");

		}
		getFormValue(formcheckoffadd);

		getObjValue(formcheckoffadd, mfBusCheckoffs);
		model.addAttribute("formcheckoffadd", formcheckoffadd);
		model.addAttribute("checkOffStatus", checkOffStatus);
		model.addAttribute("checkoffId", checkoffId);
		model.addAttribute("query", "");
		return "/component/checkoff/MfBusCheckoffs_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String checkoffId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfBusCheckoffs mfBusCheckoffs = new MfBusCheckoffs();
		mfBusCheckoffs.setCheckoffId(checkoffId);
		mfBusCheckoffsFeign.delete(mfBusCheckoffs);
		return getListPage(model);
	}


	@RequestMapping(value = "/findDataSourceByPageAjax")
	@ResponseBody
	public Map<String, Object> findDataSourceByPageAjax(String ajaxData,Integer pageSize,Integer pageNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数

			SysUser sysUser = new SysUser();
			sysUser.setCustomQuery(ajaxData);// 自定义查询参数赋值
			sysUser.setCriteriaList(sysUser, ajaxData);// 我的筛选
			sysUser.setCustomSorts(ajaxData);// 自定义排序

			ipage.setParams(this.setIpageParams("sysUser", sysUser));
			// 自定义查询Bo方法
			ipage = mfBusCheckoffsFeign.findDataSourceByPage(ipage);
			boolean noChoose = true; // 记录条件查询的查询条件是否存在
			noChoose = ajaxData.indexOf("roleNoTree") == -1; // 判断条件查询的查询条件是否是角色号
			List<MfBusCheckoffs> suList = (List<MfBusCheckoffs>) ipage.getResult();
			CodeUtils cu = new CodeUtils();
			List<ParmDic> pdList = (List<ParmDic>) cu.getCacheByKeyName("ROLE_NO");
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 核销申请提交
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkOffAppSubmitAjax")
	@ResponseBody
	public Map<String, Object> checkOffAppSubmitAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcheckoffdetail = formService.getFormData("checkoffdetail");
			getFormValue(formcheckoffdetail, getMapByJson(ajaxData));
			MfBusCheckoffs mfBusCheckoffs = new MfBusCheckoffs();
			setObjValue(formcheckoffdetail, mfBusCheckoffs);
			mfBusCheckoffs.setCurrentSessionOrgNo(User.getOrgNo(request));
			if (this.validateFormData(formcheckoffdetail)) {
				
				// 保存并提交申请审批流程
				MfBusCheckoffs checkoffs = mfBusCheckoffsFeign.checkOffAppSubmit(mfBusCheckoffs);
				dataMap.put("appSts", checkoffs.getCheckoffStatus());
				dataMap.put("node", "processaudit");
				dataMap.put("flag", "success");
				dataMap.put("msg", checkoffs.getExt1());// 临时用ext1字段做页面提示传值
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}

		} catch (Exception e) {
//			logger.error("申请审批流程提交出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * zhixing 执行核销
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exeCheckOffAjax")
	@ResponseBody
	public Map<String, Object> exeCheckOffAjax(String checkoffId) throws Exception {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String res = mfBusCheckoffsFeign.doExeCheckOff(checkoffId);
			dataMap.put("flag", "success");
			dataMap.put("msg", "核销成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "核销失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 核销收回
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkOffTakebackAjax")
	@ResponseBody
	public Map<String, Object> checkOffTakebackAjax(String checkoffId) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusCheckoffs checkoffs = new MfBusCheckoffs();
			checkoffs.setCheckoffId(checkoffId);
			checkoffs.setCheckoffStatus("5");
			mfBusCheckoffsFeign.checkOffBack(checkoffs);
			dataMap.put("flag", "success");
			dataMap.put("msg", "收回成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "收回失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}


}
