package app.component.oa.budget.controller;

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
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.common.OaParmEnum;
import app.component.oa.budget.entity.MfOaBudgetDetail;
import app.component.oa.budget.entity.MfOaBudgetMst;
import app.component.oa.budget.feign.MfOaBudgetMstFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfOaBudgetMstAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Jun 09 10:50:44 CST 2017
 **/
@Controller
@RequestMapping("/mfOaBudgetMst")
public class MfOaBudgetMstController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfOaBudgetMstFeign mfOaBudgetMstFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/oa/budget/MfOaBudgetMst_List";
	}

	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaBudgetMst mfOaBudgetMst = new MfOaBudgetMst();
			mfOaBudgetMst.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaBudgetMst.setCustomSorts(ajaxData);// 排序参数
			mfOaBudgetMst.setCriteriaList(mfOaBudgetMst, ajaxData);// 我的筛选
			// this.getRoleConditions(mfOaCons,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfOaBudgetMst", mfOaBudgetMst));
			;
			// 自定义查询Bo方法
			ipage = mfOaBudgetMstFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}

		return dataMap;
	}

	@RequestMapping("/input")
	public String input(String budgetId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfOaBudgetMst mfOaBudgetMst = new MfOaBudgetMst();
		String query="";
		if (StringUtil.isNotEmpty(budgetId)) {
			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("budgetId", budgetId);
			List<MfOaBudgetDetail> mfOaBudgetDetailList = mfOaBudgetMstFeign.getMfOaBudgerDetailList(parmMap);

			mfOaBudgetMst.setBudgetId(budgetId);
			mfOaBudgetMst = mfOaBudgetMstFeign.getById(mfOaBudgetMst);
			if (!OaParmEnum.BUDGET_APP_STS.SQZ.getNum().equals(mfOaBudgetMst.getAppSts())) {
				query = "query";
				for (MfOaBudgetDetail detail : mfOaBudgetDetailList) {
					detail.setBudgetType(OaParmEnum.BUDGET_TYPE.getTypeDesc(detail.getBudgetType()));
				}
			}
		} else {
			List<MfOaBudgetDetail> mfOaBudgetDetailList = new ArrayList<MfOaBudgetDetail>();
			model.addAttribute("mfOaBudgetDetailList", mfOaBudgetDetailList);
		}
		FormData formbudget0002 = new FormService().getFormData("budget0002");
		getObjValue(formbudget0002, mfOaBudgetMst);
		model.addAttribute("formbudget0002", formbudget0002);
		model.addAttribute("mfOaBudgetMst", mfOaBudgetMst);
		model.addAttribute("query", query);
		return "/component/oa/budget/MfOaBudgetMst_Insert";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData, String ajaxDataList) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formbudget0002 = new FormService().getFormData("budget0002");
			Map<String, Object> map = getMapByJson(ajaxData);
			JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
			dataMap.put("list", jsonArray);
			getFormValue(formbudget0002, map);
			if (this.validateFormData(formbudget0002)) {
				MfOaBudgetMst mfOaBudgetMst = new MfOaBudgetMst();
				setObjValue(formbudget0002, mfOaBudgetMst);
				mfOaBudgetMst.setCurrentSessionRegNo(User.getRegNo(request));
				mfOaBudgetMst.setCurrentSessionRegName(User.getRegName(request));
				mfOaBudgetMst.setCurrentSessionOrgNo(User.getOrgNo(request));
				mfOaBudgetMst.setCurrentSessionOrgName(User.getOrgName(request));
				dataMap.put("mfOaBudgetMst", mfOaBudgetMst);
				String result = mfOaBudgetMstFeign.insert(dataMap);
				// getTableData();//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", result);
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
	 * 方法描述： 预算流程启用
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-6-9 下午4:12:20
	 */
	@ResponseBody
	@RequestMapping("/startProcessAjax")
	public Map<String, Object> startProcessAjax(String budgetId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// MfOaBudgetMst mfOaBudgetMst = new MfOaBudgetMst();
			mfOaBudgetMstFeign.startProcess(budgetId);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL_LEAVE.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
			throw e;
		}
		return dataMap;

	}

	/**
	 * 方法描述： 进入审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-6-13 下午3:11:06
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(String budgetId, String activityType, Model model) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 获取业务与客户信息
		MfOaBudgetMst mfOaBudgetMst = new MfOaBudgetMst();
		mfOaBudgetMst.setBudgetId(budgetId);
		mfOaBudgetMst = mfOaBudgetMstFeign.getById(mfOaBudgetMst);

		Map<String, String> parmMap = new HashMap<String, String>();
		parmMap.put("budgetId", budgetId);
		List<MfOaBudgetDetail> mfOaBudgetDetailList = mfOaBudgetMstFeign.getMfOaBudgerDetailList(parmMap);
		for (MfOaBudgetDetail detail : mfOaBudgetDetailList) {
			detail.setBudgetType(OaParmEnum.BUDGET_TYPE.getTypeDesc(detail.getBudgetType()));
		}
		// 获取审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(budgetId, null);
		String nodeId = taskAppro.getActivityName();
		dataMap.put("nodeId", nodeId);
		FormData formbudget0001 = new FormService().getFormData("budget0001");
		getObjValue(formbudget0001, mfOaBudgetMst);
		// 处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), null);
		this.changeFormProperty(formbudget0001, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("mfOaBudgetMst", mfOaBudgetMst);
		model.addAttribute("mfOaBudgetDetailList", mfOaBudgetDetailList);
		model.addAttribute("taskAppro", taskAppro);
		model.addAttribute("nodeId", nodeId);
		model.addAttribute("formbudget0001", formbudget0001);
		model.addAttribute("opinionTypeList", opinionTypeList);
		model.addAttribute("query", "");
		return "/component/oa/budget/Wkf_BudgetViewPoint";
	}

	/**
	 * 方法描述： 预算审批提交
	 * @return
	 * @throws Exception
	 * String
	 * @author Javelin
	 * @date 2017-6-9 下午4:12:57
	 */
	@ResponseBody
	@RequestMapping("/commitProcessAjax")  
	public Map<String, Object> commitProcessAjax(String ajaxData,String taskId,String budgetId,String transition,String nextUser) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap=getMapByJson(ajaxData);
		dataMap.put("opNo", User.getRegNo(request));
		dataMap.put("orgNo", User.getOrgNo(request));
		dataMap.put("regName", User.getRegName(request));
		try{
			Result res = mfOaBudgetMstFeign.commitProcess(taskId,budgetId,transition,nextUser,dataMap);
			dataMap = new HashMap<String, Object>();
			if(res.isSuccess()){
				dataMap.put("flag", "success");
				if(res.isEndSts()){
					dataMap.put("msg", res.getMsg());
				}else{
					dataMap.put("msg", res.getMsg());
				}
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SUBMIT.getMessage());
			throw e;
		}
		return dataMap;
		
	}

	/**
	 * 方法描述： 进入审批历史页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-6-16 下午5:07:22
	 */
	@RequestMapping("/approvalHis")
	public String approvalHis(Model model,String budgetId) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("budgetId", budgetId);
		model.addAttribute("query", "");
		return "/component/oa/budget/MfOaBudgetMst_ApprovalHis";
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
			MfOaBudgetMst mfOaBudgetMst = new MfOaBudgetMst();
			FormData formbudget0002 = new FormService().getFormData("budget0002");
			getFormValue(formbudget0002, getMapByJson(ajaxData));
			if (this.validateFormData(formbudget0002)) {
				mfOaBudgetMst = new MfOaBudgetMst();
				setObjValue(formbudget0002, mfOaBudgetMst);
				mfOaBudgetMstFeign.update(mfOaBudgetMst);
				// getTableData();//获取列表
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
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getByIdAjax")  
	public Map<String, Object> getByIdAjax(String budgetId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Map<String,Object> formData = new HashMap<String,Object>();
		FormData formbudget0002 = new FormService().getFormData("budget0002");
		MfOaBudgetMst mfOaBudgetMst = new MfOaBudgetMst();
		mfOaBudgetMst.setBudgetId(budgetId);
		mfOaBudgetMst = mfOaBudgetMstFeign.getById(mfOaBudgetMst);
		getObjValue(formbudget0002, mfOaBudgetMst,formData);
		if(mfOaBudgetMst!=null){
			dataMap.put("flag", "success");
		}else{
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
	public Map<String, Object> deleteAjax(String budgetId,String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaBudgetMst mfOaBudgetMst = new MfOaBudgetMst();
			mfOaBudgetMst.setBudgetId(budgetId);
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfOaBudgetMst = (MfOaBudgetMst) JSONObject.toBean(jb, MfOaBudgetMst.class);
			mfOaBudgetMstFeign.delete(mfOaBudgetMst);
			// getTableData();//获取列表
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
}
