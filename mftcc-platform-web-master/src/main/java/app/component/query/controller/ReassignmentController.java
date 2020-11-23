package app.component.query.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.common.PasConstant;
import app.component.sys.entity.SysTaskInfo;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.SysTaskInfoFeign;
import app.component.sys.feign.SysUserFeign;
import cn.mftcc.util.WaterIdUtil;
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
import com.dhcc.workflow.api.task.Task;

import app.base.Criteria;
import app.component.query.entity.ReassignmentBean;
import app.component.query.feign.ReassignmentFeign;
import app.component.wkf.feign.TaskFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 融资审批、合同审批、放款审批的改派功能
 * 
 * @author WangChao
 */
@SuppressWarnings({ "unchecked" })
@Controller
@RequestMapping("/reassignment")
public class ReassignmentController extends BaseFormBean {
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private ReassignmentFeign reassignmentFeign;
	@Autowired
	private SysUserFeign sysUserFeign;
	@Autowired
	private SysTaskInfoFeign sysTaskInfoFeign;
	@Autowired
	private TaskFeign taskFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 *
	 * 普通节点任务改派列表，不支持会签节点改派
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model,String flowApprovalNo) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("flowApprovalNo", flowApprovalNo);
		return "/component/query/Reassignment_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType,String flowApprovalNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ReassignmentBean reassignmentBean = new ReassignmentBean();
		try {
			// 自定义查询参数赋值
			reassignmentBean.setCustomQuery(ajaxData);
			// 我的筛选
			reassignmentBean.setCriteriaList(reassignmentBean, ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("reassignmentBean", reassignmentBean));
			// 自定义查询Bo方法
			ipage = reassignmentFeign.findByPage(ipage);
			String tableHtml = new JsonTableUtil().getJsonStr(tableId, tableType,
					(List<ReassignmentBean>) ipage.getResult(), ipage, true);
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

	@RequestMapping("/changeUser")
	public String changeUser(Model model, String wkfTaskNo, String bizPkNo,String pasNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData reassignment0001 = formService.getFormData("reassignment0001");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("wkfTaskNo", wkfTaskNo);
		dataMap.put("bizPkNo", bizPkNo);
		dataMap.put("pasNo", pasNo);
		// 查找当前岗位角色人员
		Task task_ = wkfInterfaceFeign.getTask(bizPkNo, null);
		String[] next = new String[4];
		if (null != task_) {
			dataMap.put("description", task_.getDescription());
			 next = taskFeign.getNext(task_.getActivityType(),task_.getAppId(),task_.getName(),task_.getId());
		}
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		sysTaskInfo.setPasNo(pasNo);
		sysTaskInfo = sysTaskInfoFeign.getById(sysTaskInfo);
		if(null != sysTaskInfo){
			SysUser sysUser = new SysUser();
			sysUser.setOpNo(sysTaskInfo.getUserNo());
			sysUser = sysUserFeign.getById(sysUser);
			dataMap.put("opName", sysUser.getOpName());
		}
		getObjValue(reassignment0001, dataMap);

		JSONArray userJsonArray = new JSONArray();
		if (StringUtil.isNotEmpty(next[1])) {
			String[] nextUserDesc = wkfInterfaceFeign.getWkfUserForRoleArr(next[1].split(","));
			String[] ids = nextUserDesc[0].split(",");
			String[] names = nextUserDesc[1].split(",");
			for (int i = 0; i < ids.length; i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", ids[i]);
				jsonObject.put("name", names[i]);
				userJsonArray.add(jsonObject);
			}
		}else{
			List<SysUser> users = sysUserFeign.getAllUserList(new SysUser());
			for (int i = 0; i < users.size(); i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", users.get(i).getOpNo());
				jsonObject.put("name",users.get(i).getOpName());
				userJsonArray.add(jsonObject);
			}
		}
		String ajaxData = userJsonArray.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("reassignment0001", reassignment0001);
		model.addAttribute("query", "");

		return "/component/query/Reassignment_ChangeUser";
	}

	@RequestMapping("/reAssignAjax")
	@ResponseBody
	public Map<String, Object> reAssignAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = getMapByJson(ajaxData);
		try {
			String userId = (String) dataMap.get("userId");
			String pasNo = (String) dataMap.get("pasNo");
			String wkfTaskNo = (String) dataMap.get("wkfTaskNo");
			String bizPkNo = (String) dataMap.get("bizPkNo");
			reassignmentFeign.updateTaskChangeUser(pasNo, wkfTaskNo,bizPkNo, userId);
			SysUser sysUser  =  new SysUser();
			sysUser.setOpNo(userId);
			sysUser = sysUserFeign.getById(sysUser);
			dataMap.put("flag", "success");
			dataMap.put("msg", "指派成功! 该任务已改派给"+sysUser.getOpName()+"审批");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

}
