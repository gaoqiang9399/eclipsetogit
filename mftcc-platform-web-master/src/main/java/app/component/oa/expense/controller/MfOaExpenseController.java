package app.component.oa.expense.controller;

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
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.api.model.Transition;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.nmd.entity.ParmDic;
import app.component.oa.expense.entity.MfOaExpense;
import app.component.oa.expense.entity.MfOaExpenseList;
import app.component.oa.expense.entity.MfOaExpenseWkf;
import app.component.oa.expense.feign.MfOaExpenseFeign;
import app.component.oa.expense.feign.MfOaExpenseListFeign;
import app.component.wkf.entity.Result;
import app.component.wkf.entity.WkfApprovalOpinion;
import app.component.wkf.feign.TaskFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;

/**
 * Title: MfOaExpenseAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Dec 19 09:18:12 CST 2016
 **/
@Controller
@RequestMapping(value = "/mfOaExpense")
public class MfOaExpenseController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfOaExpenseFeign mfOaExpenseFeign;
	@Autowired
	private MfOaExpenseListFeign mfOaExpenseListFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private TaskFeign taskFeign;
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		JSONArray expenseStsJsonArray = new CodeUtils().getJSONArrayByKeyName("EX_APP_STS");
		this.getHttpRequest().setAttribute("expenseStsJsonArray", expenseStsJsonArray);
		model.addAttribute("query", "");
		return "/component/oa/expense/MfOaExpense_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		try {
			MfOaExpense mfOaExpense = new MfOaExpense();
			mfOaExpense.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaExpense.setCriteriaList(mfOaExpense, ajaxData);// 我的筛选
			mfOaExpense.setCustomSorts(ajaxData);// 自定义排序参数赋值
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageNo(pageSize);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfOaExpense", mfOaExpense));
			ipage = mfOaExpenseFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * @param ajaxData 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		try {
			FormData formexpense0002 = new FormService().getFormData("expense0002");
			getFormValue(formexpense0002, getMapByJson(ajaxData));
			if (this.validateFormData(formexpense0002)) {
				MfOaExpense mfOaExpense = new MfOaExpense();
				setObjValue(formexpense0002, mfOaExpense);
				String expense = mfOaExpense.getExpenseId();
				String messageString = mfOaExpenseFeign.IUById(mfOaExpense);
				if (expense == (null) || "".equals(expense)) {
					dataMap.put("msg", messageString);
				} else {
					dataMap.put("msg", messageString);
				}
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping("/approvalHis")
	public String approvalHis(Model model,String expenseId) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("expenseId", expenseId);
		return "/component/oa/expense/MfOaExpense_ApprovalHis";
	}

	/**
	 * 业务概况中的审批意见
	 * @param expenseId 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getApplyApprovalOpinionList")
	public Map<String, Object> getApplyApprovalOpinionList(String expenseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		CodeUtils cu = new CodeUtils();
		List<ParmDic> pdList = (List<ParmDic>) cu.getCacheByKeyName("APPROVAL_RESULT");
		List<WkfApprovalOpinion> ideaList = wkfInterfaceFeign.getWkfTaskHisList(expenseId);
		JSONArray zTreeNodes = JSONArray.fromObject(ideaList);
		for (int i = 0; i < zTreeNodes.size(); i++) {
			zTreeNodes.getJSONObject(i).put("id", zTreeNodes.getJSONObject(i).getString("execution"));
			zTreeNodes.getJSONObject(i).put("name", zTreeNodes.getJSONObject(i).getString("description"));
			zTreeNodes.getJSONObject(i).put("pId", "0");
			for (ParmDic parmDic : pdList) {
				if (parmDic.getOptCode().equals(zTreeNodes.getJSONObject(i).getString("result"))) {
					zTreeNodes.getJSONObject(i).put("optName", parmDic.getOptName());
					break;
				}
			}
		}
		dataMap.put("zTreeNodes", zTreeNodes);
		return dataMap;
	}

	/**
	 * 报销单的审批处理
	 * @param ajaxData 
	 * @param appNo 
	 * 
	 * @return
	 * @throw Exception
	 */
	@RequestMapping("/submitForUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitForUpdateAjax(String ajaxData, String appNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		try {
			dataMap = getMapByJson(ajaxData);
			FormData formexpense0004 = new FormService().getFormData("expense0004");
			getFormValue(formexpense0004, getMapByJson(ajaxData));
			MfOaExpenseWkf mfOaExpenseWkf = new MfOaExpenseWkf();
			setObjValue(formexpense0004, mfOaExpenseWkf);
			String taskId = (String) dataMap.get("taskId");
			MfOaExpense mfOaExpense = new MfOaExpense();
			mfOaExpense.setExpenseId(appNo);
			mfOaExpense = mfOaExpenseFeign.getById(mfOaExpense);
			// 根据任务编号获得节点的转移详情集合
			Result res = mfOaExpenseFeign.updateForSubmit(taskId, appNo, mfOaExpenseWkf.getOpinionType(),
					mfOaExpenseWkf.getApprovalOpinion(), taskFeign.getTransitionsStr(taskId),
					User.getRegNo(this.getHttpRequest()), "", mfOaExpense);
			dataMap.put("flag", "success");
			dataMap.put("msg", res.getMsg());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @param ajaxData 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		FormData formexpense0002 = new FormService().getFormData("expense0002");
		getFormValue(formexpense0002, getMapByJson(ajaxData));
		MfOaExpense mfOaExpenseJsp = new MfOaExpense();
		setObjValue(formexpense0002, mfOaExpenseJsp);
		MfOaExpense mfOaExpense = mfOaExpenseFeign.getById(mfOaExpenseJsp);
		if (mfOaExpense != null) {
			try {
				mfOaExpense = (MfOaExpense) EntityUtil.reflectionSetVal(mfOaExpense, mfOaExpenseJsp,
						getMapByJson(ajaxData));
				mfOaExpenseFeign.update(mfOaExpense);
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
	 * @param ajaxData 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		try {
			MfOaExpense mfOaExpense = new MfOaExpense();
			FormData formexpense0002 = new FormService().getFormData("expense0002");
			getFormValue(formexpense0002, getMapByJson(ajaxData));
			if (this.validateFormData(formexpense0002)) {
				mfOaExpense = new MfOaExpense();
				setObjValue(formexpense0002, mfOaExpense);
				mfOaExpenseFeign.update(mfOaExpense);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * @param expenseId 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	public Map<String, Object> getByIdAjax(String expenseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		FormData formexpense0002 = new FormService().getFormData("expense0002");
		MfOaExpense mfOaExpense = new MfOaExpense();
		mfOaExpense.setExpenseId(expenseId);
		mfOaExpense = mfOaExpenseFeign.getById(mfOaExpense);
		getObjValue(formexpense0002, mfOaExpense, formData);
		if (mfOaExpense != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * @param expenseId 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	public Map<String, Object> deleteAjax(String expenseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		MfOaExpense mfOaExpense = new MfOaExpense();
		mfOaExpense.setExpenseId(expenseId);
		try {
			mfOaExpenseFeign.delete(mfOaExpense);
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
	 * @param expenseId 
	 * @param model 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(String expenseId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfOaExpense mfOaExpense = new MfOaExpense();
		mfOaExpense.setExpenseId(expenseId);
		mfOaExpense = mfOaExpenseFeign.queryById(mfOaExpense);
		model.addAttribute("mfOaExpense", mfOaExpense);
		model.addAttribute("query", "");
		if (expenseId == null) {
			return "/component/oa/expense/MfOaExpense_Insert";
		} else if ("1".equals(mfOaExpense.getExpenseSts())) {
			return "/component/oa/expense/MfOaExpense_find";
		} else {
			FormData formexpense0002 = new FormService().getFormData("expense0003");
			getObjValue(formexpense0002, mfOaExpense);
			model.addAttribute("formexpense0002", formexpense0002);
			return "/component/oa/expense/MfOaExpense_Detail";
		}

	}

	/**
	 * 方法描述： 中汇鑫得费用报销新增申请页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lcl
	 * @param expenseId 
	 * @param model 
	 * @date 2017-6-15 上午11:16:21
	 */
	@RequestMapping("/inputForApply")
	public String inputForApply(String expenseId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		if(expenseId == null){
			expenseId = "";
		}
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		Map<String, Object> paramMap = new  HashMap<String, Object>(); 
		paramMap.put("expenseId", expenseId);
		paramMap.put("opNo", User.getRegNo(request));
		paramMap.put("opName", User.getRegName(request));
		paramMap.put("brNo", User.getOrgNo(request));
		paramMap.put("brName", User.getOrgName(request));
		dataMap = mfOaExpenseFeign.getExpesionById(paramMap);
		MfOaExpense mfOaExpense = (MfOaExpense)JsonStrHandling.handlingStrToBean(dataMap.get("mfOaExpense"), MfOaExpense.class);
		List<MfOaExpenseList> mfOaExpenseListList = (List<MfOaExpenseList>) dataMap.get("mfOaExpenseListList");
		model.addAttribute("mfOaExpenseListList", mfOaExpenseListList);
		model.addAttribute("mfOaExpense", mfOaExpense);
		model.addAttribute("query", "");
		if ("insert".equals(dataMap.get("flag"))) {
			FormData formexpense0005 = new FormService().getFormData("expense0005");
			getObjValue(formexpense0005, mfOaExpense);
			model.addAttribute("formexpense0005", formexpense0005);
			return "/component/oa/expense/MfOaExpense_inputForApply";
		} else {
			FormData formexpense0007 = new FormService().getFormData("expense0007");
			getObjValue(formexpense0007, mfOaExpense);
			model.addAttribute("formexpense0007", formexpense0007);
			return "/component/oa/expense/MfOaExpense_inputForDetail";
		}
	}

	@RequestMapping("/getViewPoint")
	public String getViewPoint(String appNo, Model model) throws Exception {
		ActionContext.initialize(request, response);
		try {
			Map<String, Object> dataMap = new  HashMap<String, Object>();
			String expenseId = appNo;// 从待办打开页面时参数丢失,暂时用appNo代替
			Map<String, Object> paramMap = new  HashMap<String, Object>(); 
			paramMap.put("expenseId", expenseId);
			paramMap.put("opNo", User.getRegNo(request));
			paramMap.put("opName", User.getRegName(request));
			paramMap.put("brNo", User.getOrgNo(request));
			paramMap.put("brName", User.getOrgName(request));
			dataMap = mfOaExpenseFeign.getExpesionById(paramMap);
			MfOaExpense mfOaExpense = (MfOaExpense)JsonStrHandling.handlingStrToBean( dataMap.get("mfOaExpense"), MfOaExpense.class);
//			MfOaExpense mfOaExpense = (MfOaExpense) dataMap.get("mfOaExpense");
//			List<MfOaExpenseList> mfOaExpenseListList = (List<MfOaExpenseList>)JsonStrHandling.handlingStrToBean( dataMap.get("mfOaExpenseListList"), ArrayList.class);
			List<MfOaExpenseList> mfOaExpenseListList = (List<MfOaExpenseList>) dataMap.get("mfOaExpenseListList");
			TaskImpl taskAppro = wkfInterfaceFeign.getTask(expenseId, null);
			String activityType = taskAppro.getActivityType();
			FormData formexpense0006;
			if ("manager_approval".equals(taskAppro.getName())) {
				formexpense0006 = new FormService().getFormData("expense0006");
			} else {
				formexpense0006 = new FormService().getFormData("expense0008");
			}
			getObjValue(formexpense0006, mfOaExpense);
			// 处理审批意见类型
			List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,taskAppro.getCouldRollback(), null);
			this.changeFormProperty(formexpense0006, "opinionType", "optionArray", opinionTypeList);
			model.addAttribute("formexpense0006", formexpense0006);
			model.addAttribute("activityType", activityType);
			model.addAttribute("expenseId", expenseId);
			model.addAttribute("appNo", appNo);
			model.addAttribute("mfOaExpense", mfOaExpense);
			model.addAttribute("mfOaExpenseListList", mfOaExpenseListList);
			model.addAttribute("query", "");
		} catch (Exception e) {
			throw e;
		}
		return "/component/oa/expense/MfOaExpense_ViewPoint";
	}

	/**
	 * 方法描述：
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lcl
	 * @param ajaxData 
	 * @param ajaxDataList 
	 * @date 2017-6-16 下午3:57:53
	 */
	@RequestMapping("/insertForApplyAjax")
	public Map<String, Object> insertForApplyAjax(String ajaxData, String ajaxDataList) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		try {
			FormData formexpense0005 = new FormService().getFormData("expense0005");
			dataMap = getMapByJson(ajaxData);
			JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
			dataMap.put("list", jsonArray);
			getFormValue(formexpense0005, dataMap);
			if (this.validateFormData(formexpense0005)) {
				MfOaExpense mfOaExpense = new MfOaExpense();
				setObjValue(formexpense0005, mfOaExpense);
				dataMap = mfOaExpenseFeign.insert(mfOaExpense, dataMap);
				// getTableData();//获取列表
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping("/startProcessAjax")
	public Map<String, Object> startProcessAjax(String expenseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		try {
			MfOaExpense mfOaExpense = new MfOaExpense();
			mfOaExpense.setExpenseId(expenseId);
			mfOaExpense = mfOaExpenseFeign.getById(mfOaExpense);
			dataMap = mfOaExpenseFeign.insertForSubmit(mfOaExpense);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("提交"));
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 中汇鑫德审批意见保存
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lcl
	 * @param ajaxData 
	 * @param expenseId 
	 * @param taskId 
	 * @date 2017-6-16 下午4:16:36
	 */
	@RequestMapping("/commitProcessAjax")
	public Map<String, Object> commitProcessAjax(String ajaxData, String expenseId, String taskId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		try {
			FormData formexpense0006 = new FormService().getFormData("expense0006");
			getFormValue(formexpense0006, getMapByJson(ajaxData));
			MfOaExpenseWkf mfOaExpenseWkf = new MfOaExpenseWkf();
			setObjValue(formexpense0006, mfOaExpenseWkf);
			dataMap = getMapByJson(ajaxData);
			MfOaExpense mfOaExpense = new MfOaExpense();
			mfOaExpense.setExpenseId(expenseId);
			mfOaExpense = mfOaExpenseFeign.getById(mfOaExpense);
			// 根据任务编号获得节点的转移详情集合
			List<Transition> transitions = taskFeign.getTransitions(taskId);
			Result res = mfOaExpenseFeign.updateForApplySubmit(taskId, expenseId, mfOaExpenseWkf.getOpinionType(),
					mfOaExpenseWkf.getApprovalOpinion(), transitions.get(0).getName(),
					User.getRegNo(this.getHttpRequest()), "", mfOaExpense, dataMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", res.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/***
	 * 新增
	 * @param model 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formexpense0002 = new FormService().getFormData("expense0002");
		getFormValue(formexpense0002);
		MfOaExpense mfOaExpense = new MfOaExpense();
		setObjValue(formexpense0002, mfOaExpense);
		mfOaExpenseFeign.insert(mfOaExpense);
		getObjValue(formexpense0002, mfOaExpense);
		this.addActionMessage(model,"保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfOaExpense", mfOaExpense));
		List<MfOaExpense> mfOaExpenseList = (List<MfOaExpense>) mfOaExpenseFeign.findByPage(ipage).getResult();
		model.addAttribute("formexpense0002", formexpense0002);
		model.addAttribute("mfOaExpense", mfOaExpense);
		model.addAttribute("mfOaExpenseList", mfOaExpenseList);
		model.addAttribute("query", "");
		return "/component/oa/expense/MfOaExpense_Insert";
	}

	/**
	 * 查询
	 * @param expenseId 
	 * @param model 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String expenseId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formexpense0001 = new FormService().getFormData("expense0001");
		getFormValue(formexpense0001);
		MfOaExpense mfOaExpense = new MfOaExpense();
		mfOaExpense.setExpenseId(expenseId);
		mfOaExpense = mfOaExpenseFeign.getById(mfOaExpense);
		getObjValue(formexpense0001, mfOaExpense);
		model.addAttribute("formexpense0001", formexpense0001);
		model.addAttribute("mfOaExpense", mfOaExpense);
		model.addAttribute("query", "");
		return "/component/oa/expense/MfOaExpense_Detail";
	}

	/**
	 * 删除
	 * @param expenseId 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String expenseId,Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfOaExpense mfOaExpense = new MfOaExpense();
		mfOaExpense.setExpenseId(expenseId);
		mfOaExpenseFeign.delete(mfOaExpense);
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
		FormData formexpense0002 = new FormService().getFormData("expense0002");
		getFormValue(formexpense0002);
		boolean validateFlag = this.validateFormData(formexpense0002);
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
		FormData formexpense0002 = new FormService().getFormData("expense0002");
		getFormValue(formexpense0002);
		boolean validateFlag = this.validateFormData(formexpense0002);
	}

}
