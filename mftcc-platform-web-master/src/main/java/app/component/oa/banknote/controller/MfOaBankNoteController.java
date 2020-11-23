package app.component.oa.banknote.controller;

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
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.oa.banknote.entity.MfOaBankNote;
import app.component.oa.banknote.feign.MfOaBankNoteFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
 * Title: MfOaBankNoteAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Jun 13 10:05:32 CST 2017
 **/
@Controller
@RequestMapping("/mfOaBankNote")
public class MfOaBankNoteController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfOaBankNoteFeign mfOaBankNoteFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		JSONArray bankBillTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("BANK_BILL_TYPE");
		JSONArray billPasJsonArray = new CodeUtils().getJSONArrayByKeyName("BILL_PAS");
		model.addAttribute("bankBillTypeJsonArray", bankBillTypeJsonArray);
		model.addAttribute("billPasJsonArray", billPasJsonArray);
		model.addAttribute("query", "");
		return "/component/oa/banknote/MfOaBankNote_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaBankNote mfOaBankNote = new MfOaBankNote();
			mfOaBankNote.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaBankNote.setCriteriaList(mfOaBankNote, ajaxData);// 我的筛选
			mfOaBankNote.setCustomSorts(ajaxData);// 自定义排序参数赋值
			// this.getRoleConditions(mfOaBankNote,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfOaBankNote", mfOaBankNote));
			// 自定义查询Bo方法
			ipage = mfOaBankNoteFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
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
	@ResponseBody
	@RequestMapping("/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formbanknote0001 = new FormService().getFormData("banknote0001");
			getFormValue(formbanknote0001, getMapByJson(ajaxData));
			if (this.validateFormData(formbanknote0001)) {
				MfOaBankNote mfOaBankNote = new MfOaBankNote();
				setObjValue(formbanknote0001, mfOaBankNote);
				dataMap = mfOaBankNoteFeign.insertForSubmit(mfOaBankNote);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 打开审批历史页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lcl
	 * @date 2017-6-19 下午4:31:53
	 */
	@RequestMapping("/approvalHis")
	public String approvalHis(Model model,String billId) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("billId", billId);
		model.addAttribute("query", "");
		return "/component/oa/banknote/MfOaBankNote_provide";
	}

	/**
	 * 方法描述： 打开审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lcl
	 * @date 2017-6-13 下午4:55:59
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(String billId,Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formbanknote0003 = new FormService().getFormData("banknote0003");
		MfOaBankNote mfOaBankNote = new MfOaBankNote();
		mfOaBankNote.setBillId(billId);
		mfOaBankNote = mfOaBankNoteFeign.getById(mfOaBankNote);
		getFormValue(formbanknote0003);
		getObjValue(formbanknote0003, mfOaBankNote);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(billId, null);
		String activityType = taskAppro.getActivityType();
		// 处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,taskAppro.getCouldRollback(), null);
		this.changeFormProperty(formbanknote0003, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("formbanknote0003", formbanknote0003);
		model.addAttribute("taskAppro", taskAppro);
		model.addAttribute("activityType", activityType);
		model.addAttribute("mfOaBankNote", mfOaBankNote);
		model.addAttribute("billId", billId);
		model.addAttribute("query", "");
		return "/component/oa/banknote/MfOaBankNote_ViewPoint";
	}

	/**
	 * 方法描述： 保存审批方法
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lcl
	 * @date 2017-6-14 下午5:55:09
	 */
	@ResponseBody
	@RequestMapping("/commitProcessAjax")
	public Map<String, Object> commitProcessAjax(String ajaxData,String taskId,String appNo,String transition) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formbanknote0003 = new FormService().getFormData("banknote0003");
			getFormValue(formbanknote0003, getMapByJson(ajaxData));
			MfOaBankNote mfOaBankNote = new MfOaBankNote();
			setObjValue(formbanknote0003, mfOaBankNote);
			dataMap = getMapByJson(ajaxData);
			String opinionType = String.valueOf(dataMap.get("opinionType"));
			String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
			Result res = mfOaBankNoteFeign.doSubmit(taskId, appNo, opinionType, approvalOpinion, transition,User.getRegNo(request), "", mfOaBankNote);
			dataMap.put("flag", "success");
			dataMap.put("msg", res.getMsg());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存审批");
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
	@ResponseBody
	@RequestMapping("/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formbanknote0002 = new FormService().getFormData("banknote0002");
		getFormValue(formbanknote0002, getMapByJson(ajaxData));
		MfOaBankNote mfOaBankNoteJsp = new MfOaBankNote();
		setObjValue(formbanknote0002, mfOaBankNoteJsp);
		MfOaBankNote mfOaBankNote = mfOaBankNoteFeign.getById(mfOaBankNoteJsp);
		if (mfOaBankNote != null) {
			try {
				mfOaBankNote = (MfOaBankNote) EntityUtil.reflectionSetVal(mfOaBankNote, mfOaBankNoteJsp,getMapByJson(ajaxData));
				mfOaBankNoteFeign.update(mfOaBankNote);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "修改失败");
				throw e;
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
	@RequestMapping("/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaBankNote mfOaBankNote = new MfOaBankNote();
			FormData formbanknote0002 = new FormService().getFormData("banknote0002");
			getFormValue(formbanknote0002, getMapByJson(ajaxData));
			if (this.validateFormData(formbanknote0002)) {
				mfOaBankNote = new MfOaBankNote();
				setObjValue(formbanknote0002, mfOaBankNote);
				mfOaBankNoteFeign.update(mfOaBankNote);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
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
	@ResponseBody
	@RequestMapping("/getByIdAjax")
	public Map<String, Object> getByIdAjax(String billId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formData = new HashMap<String, Object>();
		FormData formbanknote0002 = new FormService().getFormData("banknote0002");
		MfOaBankNote mfOaBankNote = new MfOaBankNote();
		mfOaBankNote.setBillId(billId);
		mfOaBankNote = mfOaBankNoteFeign.getById(mfOaBankNote);
		getObjValue(formbanknote0002, mfOaBankNote, formData);
		if (mfOaBankNote != null) {
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
	@RequestMapping("/deleteAjax")
	public Map<String, Object> deleteAjax(String billId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaBankNote mfOaBankNote = new MfOaBankNote();
		mfOaBankNote.setBillId(billId);
		try {
			mfOaBankNoteFeign.delete(mfOaBankNote);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
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
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formbanknote0001 = new FormService().getFormData("banknote0001");
		model.addAttribute("formbanknote0001", formbanknote0001);
		model.addAttribute("query", "");
		return "/component/oa/banknote/MfOaBankNote_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formbanknote0002 = new FormService().getFormData("banknote0002");
		getFormValue(formbanknote0002);
		MfOaBankNote mfOaBankNote = new MfOaBankNote();
		setObjValue(formbanknote0002, mfOaBankNote);
		mfOaBankNoteFeign.insert(mfOaBankNote);
		getObjValue(formbanknote0002, mfOaBankNote);
		// this.addActionMessage("保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfOaBankNote", mfOaBankNote));
		List<MfOaBankNote> mfOaBankNoteList = (List<MfOaBankNote>) mfOaBankNoteFeign.findByPage(ipage).getResult();
		model.addAttribute("formbanknote0002", formbanknote0002);
		model.addAttribute("mfOaBankNote", mfOaBankNote);
		model.addAttribute("mfOaBankNoteList", mfOaBankNoteList);
		model.addAttribute("query", "");
		return "/component/oa/banknote/MfOaBankNote_Insert";
	}

	/**
	 * 打开详情页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String billId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formbanknote0004 = new FormService().getFormData("banknote0004");
		getFormValue(formbanknote0004);
		MfOaBankNote mfOaBankNote = new MfOaBankNote();
		mfOaBankNote.setBillId(billId);
		mfOaBankNote = mfOaBankNoteFeign.getById(mfOaBankNote);
		getObjValue(formbanknote0004, mfOaBankNote);
		model.addAttribute("formbanknote0004", formbanknote0004);
		model.addAttribute("mfOaBankNote", mfOaBankNote);
		model.addAttribute("query", "");
		return "/component/oa/banknote/MfOaBankNote_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String billId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfOaBankNote mfOaBankNote = new MfOaBankNote();
		mfOaBankNote.setBillId(billId);
		mfOaBankNoteFeign.delete(mfOaBankNote);
		return getListPage(model);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateInsert")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormData formbanknote0002 = new FormService().getFormData("banknote0002");
		getFormValue(formbanknote0002);
		boolean validateFlag = this.validateFormData(formbanknote0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateUpdate")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormData formbanknote0002 = new FormService().getFormData("banknote0002");
		getFormValue(formbanknote0002);
		boolean validateFlag = this.validateFormData(formbanknote0002);
	}

}
