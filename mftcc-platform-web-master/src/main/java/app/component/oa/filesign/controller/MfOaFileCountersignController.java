package app.component.oa.filesign.controller;

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
import app.component.oa.filesign.entity.MfOaFileCountersign;
import app.component.oa.filesign.feign.MfOaFileCountersignFeign;
import app.component.sys.entity.SysUser;
import app.component.sysextendinterface.SysExtendInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfOaFileCountersignAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Jun 12 16:40:24 CST 2017
 **/
@Controller
@RequestMapping(value = "/mfOaFileCountersign")
public class MfOaFileCountersignController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfOaFileCountersignFeign mfOaFileCountersignFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private SysExtendInterfaceFeign sysExtendInterfaceFeign;
	

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/oa/filesign/MfOaFileCountersign_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaFileCountersign mfOaFileCountersign = new MfOaFileCountersign();
			mfOaFileCountersign.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaFileCountersign.setCriteriaList(mfOaFileCountersign, ajaxData);// 我的筛选
			// this.getRoleConditions(mfOaFileCountersign,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfOaFileCountersign", mfOaFileCountersign));
			ipage = mfOaFileCountersignFeign.findByPage(ipage);
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
	 * 方法描述： 进入审批历史页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-6-16 下午5:07:22
	 */
	@RequestMapping("/approvalHis")
	public String approvalHis(Model model,String fileNo) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("fileNo", fileNo);
		model.addAttribute("query", "");
		return "/component/oa/filesign/MfOaFileCountersign_ApprovalHis";
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
		FormData formfilesign0002 = new FormService().getFormData("filesign0002");
		String fileNo = WaterIdUtil.getWaterId();
		MfOaFileCountersign mfOaFileCountersign = new MfOaFileCountersign();
		mfOaFileCountersign.setFileNo(fileNo);
		getObjValue(formfilesign0002, mfOaFileCountersign);
		model.addAttribute("formfilesign0002", formfilesign0002);
		model.addAttribute("mfOaFileCountersign", mfOaFileCountersign);
		model.addAttribute("fileNo", fileNo);
		model.addAttribute("query", "");
		return "/component/oa/filesign/MfOaFileCountersign_Insert";
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
			FormData formfilesign0002 = new FormService().getFormData("filesign0002");
			getFormValue(formfilesign0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfilesign0002)) {
				MfOaFileCountersign mfOaFileCountersign = new MfOaFileCountersign();
				setObjValue(formfilesign0002, mfOaFileCountersign);
				String result = mfOaFileCountersignFeign.insert(mfOaFileCountersign);
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
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String fileNo, Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formfilesign0002 = new FormService().getFormData("filesign0002");
		getFormValue(formfilesign0002);
		MfOaFileCountersign mfOaFileCountersign = new MfOaFileCountersign();
		mfOaFileCountersign.setFileNo(fileNo);
		mfOaFileCountersign = mfOaFileCountersignFeign.getById(mfOaFileCountersign);
		getObjValue(formfilesign0002, mfOaFileCountersign);
		model.addAttribute("formfilesign0002", formfilesign0002);
		model.addAttribute("mfOaFileCountersign", mfOaFileCountersign);
		model.addAttribute("fileNo", fileNo);
		model.addAttribute("query", "query");
		return "/component/oa/filesign/MfOaFileCountersign_Detail";
	}

	/**
	 * 方法描述： 审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-6-13 上午10:02:02
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(String fileNo, String activityType, Model model,String hideOpinionType, String taskId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 获取业务与客户信息
		MfOaFileCountersign mfOaFileCountersign = new MfOaFileCountersign();
		mfOaFileCountersign.setFileNo(fileNo);
		mfOaFileCountersign = mfOaFileCountersignFeign.getById(mfOaFileCountersign);
		// 获取审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(fileNo, null);
		String nodeId = taskAppro.getActivityName();
		dataMap.put("nodeId", nodeId);
		/*if ("responsible".equals(nodeId)) {// 负责人指定会签人员，与是否会签
			// 获取所有操作员
			List<SysUser> userList = sysExtendInterfaceFeign.getAllUsers();
			JSONArray userJsonArray = new JSONArray();
			for (int i = 0; i < userList.size(); i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", userList.get(i).getOpNo());
				jsonObject.put("name", userList.get(i).getOpName());
				userJsonArray.add(jsonObject);
			}
			model.addAttribute("ajaxData", userJsonArray.toString());
		}*/
		FormData formfilesign0001 = new FormService().getFormData("filesign0001");
		getObjValue(formfilesign0001, mfOaFileCountersign);
		// 处理审批意见类型
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		//hideOpinionType = hideOpinionType + "@4";//隐藏发回重审
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeId);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formfilesign0001, "opinionType", "optionArray", opinionTypeList);
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("fileNo", fileNo);
		model.addAttribute("activityType", activityType);
		model.addAttribute("formfilesign0001", formfilesign0001);
		model.addAttribute("mfOaFileCountersign", mfOaFileCountersign);
		model.addAttribute("query", "");
		return "/component/oa/filesign/Wkf_FileSignViewPoint";
	}

	/**
	 * 方法描述： 审批提交
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @param taskId
	 * @param fileNo
	 * @param transition
	 * @param nextUser
	 * @date 2017-6-9 下午4:12:57
	 */
	@ResponseBody
	@RequestMapping("/commitProcessAjax")
	public Map<String, Object> commitProcessAjax(String ajaxData, String taskId, String appNo, String transition,
			String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap = getMapByJson(ajaxData);
		dataMap.put("orgNo", User.getOrgNo(request));
		dataMap.put("opNo", User.getRegNo(request));
		dataMap.put("opName", User.getRegName(request));
		Result res;
		try {
			res = mfOaFileCountersignFeign.commitProcess(taskId, appNo, transition, nextUser, dataMap);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					dataMap.put("msg", res.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
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
		FormData formfilesign0002 = new FormService().getFormData("filesign0002");
		getFormValue(formfilesign0002, getMapByJson(ajaxData));
		MfOaFileCountersign mfOaFileCountersignJsp = new MfOaFileCountersign();
		setObjValue(formfilesign0002, mfOaFileCountersignJsp);
		MfOaFileCountersign mfOaFileCountersign = mfOaFileCountersignFeign.getById(mfOaFileCountersignJsp);
		if (mfOaFileCountersign != null) {
			try {
				mfOaFileCountersign = (MfOaFileCountersign) EntityUtil.reflectionSetVal(mfOaFileCountersign,
						mfOaFileCountersignJsp, getMapByJson(ajaxData));
				mfOaFileCountersignFeign.update(mfOaFileCountersign);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
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
		MfOaFileCountersign mfOaFileCountersign = new MfOaFileCountersign();
		try {
			FormData formfilesign0002 = new FormService().getFormData("filesign0002");
			getFormValue(formfilesign0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfilesign0002)) {
				mfOaFileCountersign = new MfOaFileCountersign();
				setObjValue(formfilesign0002, mfOaFileCountersign);
				mfOaFileCountersignFeign.update(mfOaFileCountersign);
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
	public Map<String, Object> getByIdAjax(String fileNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formfilesign0002 = new FormService().getFormData("filesign0002");
		MfOaFileCountersign mfOaFileCountersign = new MfOaFileCountersign();
		mfOaFileCountersign.setFileNo(fileNo);
		mfOaFileCountersign = mfOaFileCountersignFeign.getById(mfOaFileCountersign);
		getObjValue(formfilesign0002, mfOaFileCountersign, formData);
		if (mfOaFileCountersign != null) {
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
	public Map<String, Object> deleteAjax(String fileNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaFileCountersign mfOaFileCountersign = new MfOaFileCountersign();
			mfOaFileCountersign.setFileNo(fileNo);
			mfOaFileCountersignFeign.delete(mfOaFileCountersign);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
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
		FormData formfilesign0002 = new FormService().getFormData("filesign0002");
		getFormValue(formfilesign0002);
		MfOaFileCountersign mfOaFileCountersign = new MfOaFileCountersign();
		setObjValue(formfilesign0002, mfOaFileCountersign);
		mfOaFileCountersignFeign.insert(mfOaFileCountersign);
		getObjValue(formfilesign0002, mfOaFileCountersign);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfOaFileCountersign", mfOaFileCountersign));
		List<MfOaFileCountersign> mfOaFileCountersignList = (List<MfOaFileCountersign>) mfOaFileCountersignFeign
				.findByPage(ipage).getResult();
		model.addAttribute("mfOaFileCountersignList", mfOaFileCountersignList);
		model.addAttribute("formfilesign0002", formfilesign0002);
		model.addAttribute("mfOaFileCountersign", mfOaFileCountersign);
		model.addAttribute("query", "");
		return "/component/oa/filesign/MfOaFileCountersign_Insert";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String fileNo, Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfOaFileCountersign mfOaFileCountersign = new MfOaFileCountersign();
		mfOaFileCountersign.setFileNo(fileNo);
		mfOaFileCountersignFeign.delete(mfOaFileCountersign);
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
		FormData formfilesign0002 = new FormService().getFormData("filesign0002");
		getFormValue(formfilesign0002);
		boolean validateFlag = this.validateFormData(formfilesign0002);
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
		FormData formfilesign0002 = new FormService().getFormData("filesign0002");
		getFormValue(formfilesign0002);
		boolean validateFlag = this.validateFormData(formfilesign0002);
	}

}
