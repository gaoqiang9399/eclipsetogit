package app.component.risk.aftwarn.controller;

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

import app.component.common.EntityUtil;
import app.component.risk.aftwarn.entity.MfWarningParm;
import app.component.risk.aftwarn.feign.MfWarningParmFeign;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * Title: MfWarningParmAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jun 01 17:50:53 CST 2016
 **/
@Controller
@RequestMapping("/mfWarningParm")
public class MfWarningParmController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfWarningParmBo
	@Autowired
	private MfWarningParmFeign mfWarningParmFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/risk/aftwarn/MfWarningParm_List";
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
		MfWarningParm mfWarningParm = new MfWarningParm();
		mfWarningParm.setParmType("1");
		try {
			List<MfWarningParm> mfWarningParmList = mfWarningParmFeign.getList(mfWarningParm);
			dataMap.put("flag", "success");
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfWarningParmList, null, true);
			dataMap.put("tableData", tableHtml);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
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
			FormData formwarnparm0002 = formService.getFormData("warnparm0002");
			getFormValue(formwarnparm0002, getMapByJson(ajaxData));
			if (this.validateFormData(formwarnparm0002)) {
				MfWarningParm mfWarningParm = new MfWarningParm();
				setObjValue(formwarnparm0002, mfWarningParm);
				mfWarningParmFeign.insert(mfWarningParm);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
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
		FormData formwarnparm0002 = formService.getFormData("warnparm0002");
		getFormValue(formwarnparm0002, getMapByJson(ajaxData));
		MfWarningParm mfWarningParmJsp = new MfWarningParm();
		setObjValue(formwarnparm0002, mfWarningParmJsp);
		MfWarningParm mfWarningParm = mfWarningParmFeign.getById(mfWarningParmJsp);
		if (mfWarningParm != null) {
			try {
				mfWarningParm = (MfWarningParm) EntityUtil.reflectionSetVal(mfWarningParm, mfWarningParmJsp,
						getMapByJson(ajaxData));
				mfWarningParmFeign.update(mfWarningParm);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
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
			FormData formwarnparm0002 = formService.getFormData("warnparm0002");
			getFormValue(formwarnparm0002, getMapByJson(ajaxData));
			if (this.validateFormData(formwarnparm0002)) {
				MfWarningParm mfWarningParm = new MfWarningParm();
				setObjValue(formwarnparm0002, mfWarningParm);
				mfWarningParmFeign.update(mfWarningParm);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/updateListAjax")
	@ResponseBody
	public Map<String, Object> updateListAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfWarningParm mfWarningParm = new MfWarningParm();
		try {
			JSONArray jsonArray = JSONArray.fromObject(ajaxData);
			List<MfWarningParm> mfWarningParmAjaxList = (List<MfWarningParm>) JSONArray.toList(jsonArray,
					new MfWarningParm(), new JsonConfig());
			mfWarningParmFeign.updateList(mfWarningParmAjaxList);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
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
	public Map<String, Object> getByIdAjax(String parmId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formwarnparm0002 = formService.getFormData("warnparm0002");
		MfWarningParm mfWarningParm = new MfWarningParm();
		mfWarningParm.setParmId(parmId);
		mfWarningParm = mfWarningParmFeign.getById(mfWarningParm);
		getObjValue(formwarnparm0002, mfWarningParm, formData);
		if (mfWarningParm != null) {
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
	public Map<String, Object> deleteAjax(String parmId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfWarningParm mfWarningParm = new MfWarningParm();
		mfWarningParm.setParmId(parmId);
		try {
			mfWarningParmFeign.delete(mfWarningParm);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
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
		FormData formwarnparm0002 = formService.getFormData("warnparm0002");
		model.addAttribute("formwarnparm0002", formwarnparm0002);
		model.addAttribute("query", "");
		return "/component/risk/aftwarn/MfWarningParm_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formwarnparm0002 = formService.getFormData("warnparm0002");
		getFormValue(formwarnparm0002);
		MfWarningParm mfWarningParm = new MfWarningParm();
		setObjValue(formwarnparm0002, mfWarningParm);
		mfWarningParmFeign.insert(mfWarningParm);
		getObjValue(formwarnparm0002, mfWarningParm);
		this.addActionMessage(model, "保存成功");
		List<MfWarningParm> mfWarningParmList = (List<MfWarningParm>) mfWarningParmFeign.findByPage(this.getIpage(), mfWarningParm)
				.getResult();
		model.addAttribute("mfWarningParmList", mfWarningParmList);
		model.addAttribute("formwarnparm0002", formwarnparm0002);
		model.addAttribute("query", "");
		return "/component/risk/aftwarn/MfWarningParm_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String parmId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formwarnparm0002 = formService.getFormData("warnparm0001");
		getFormValue(formwarnparm0002);
		MfWarningParm mfWarningParm = new MfWarningParm();
		mfWarningParm.setParmId(parmId);
		mfWarningParm = mfWarningParmFeign.getById(mfWarningParm);
		getObjValue(formwarnparm0002, mfWarningParm);
		model.addAttribute("mfWarningParm", mfWarningParm);
		model.addAttribute("formwarnparm0002", formwarnparm0002);
		model.addAttribute("query", "");
		return "/component/risk/aftwarn/MfWarningParm_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String parmId) throws Exception {
		ActionContext.initialize(request, response);
		MfWarningParm mfWarningParm = new MfWarningParm();
		mfWarningParm.setParmId(parmId);
		mfWarningParmFeign.delete(mfWarningParm);
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formwarnparm0002 = formService.getFormData("warnparm0002");
		getFormValue(formwarnparm0002);
		boolean validateFlag = this.validateFormData(formwarnparm0002);
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
		FormData formwarnparm0002 = formService.getFormData("warnparm0002");
		getFormValue(formwarnparm0002);
		boolean validateFlag = this.validateFormData(formwarnparm0002);
	}

}
