package app.tech.wkf.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.dhcc.workflow.api.ProcessDefinition;
import com.dhcc.workflow.api.task.Task;
import com.dhcc.workflow.pvm.internal.model.ProcessDefinitionImpl;

import app.component.wkf.entity.AppBranch;
import app.component.wkf.entity.AppRole;
import app.component.wkf.entity.AppUser;
import app.component.wkf.entity.WkfApprovalUser;
import app.component.wkf.feign.WfExecutionBoFeign;
import app.component.wkf.feign.WkfApprovalRoleFeign;
import app.component.wkf.feign.WkfApprovalUserFeign;
import app.component.wkf.feign.WkfBranchBoFeign;
import app.component.wkf.feign.WkfDesignFeign;
import app.component.wkf.feign.WkfGroupBoFeign;
import app.component.wkf.feign.WkfInterfaceFeign;
import app.component.wkf.feign.WkfUserBoFeign;
import app.tech.wkf.entity.WfExecution;
import app.tech.wkf.tools.Dom4jXML;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wkfDesgin")
public class WkfDesginController extends BaseFormBean {
	private static final long serialVersionUID = 8659398196134479613L;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@Autowired
	private WkfGroupBoFeign wkfGroupDao;
	@Autowired
	private WkfUserBoFeign wkfUserDao;
	@Autowired
	private WkfBranchBoFeign wkfBranchDao;
	@Autowired
	private WfExecutionBoFeign wfExecutionBo;
	@Autowired
	private WkfApprovalRoleFeign wkfApprovalRoleFeign;
	@Autowired
	private WkfApprovalUserFeign wkfApprovalUserFeign;

	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private WkfDesignFeign wkfDesginFeign;

	// private File upload;
	private String uploadFileName;
	private String query;
	private String roleId;
	private String roleName;
	private String userId;
	private String userName;
	private JSONArray jsonArray;
	private JSONObject json;
	// private String jsonData;
	// private String appNo;
	// private String appWorkflowNo;

	@RequestMapping(value = "/showView")
	public String showView() throws Exception {
		ActionContext.initialize(request, response);
		return "/tech/wkf/processDetail_List";
	}

	@ResponseBody
	@RequestMapping(value = "/getRoles")
	public JSONArray getRoles() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (query == null) {
            query = "";
        }
		query = new String(query.getBytes("ISO-8859-1"), "UTF-8");
		map.put("roleId", query);
		map.put("roleName", query);
		List<AppRole> roleList = wkfGroupDao.list(map);
		// List<String> list = new ArrayList<String>();
		jsonArray = new JSONArray();
		for (AppRole au : roleList) {
			// list.add(au.getRoleId() + ";" + au.getRoleName());
			JSONObject obj = new JSONObject();
			obj.put("id", au.getRoleId());
			obj.put("text", au.getRoleName());
			jsonArray.add(obj);
		}

		// jsonArray = JSONArray.fromObject(roleList);
		return jsonArray;
	}

	@ResponseBody
	@RequestMapping(value = "/getUsers")
	public JSONArray getUsers() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		if (query == null) {
            query = "";
        }
		query = new String(query.getBytes("ISO-8859-1"), "UTF-8");
		map.put("roleId", query);
		map.put("roleName", query);
		List<AppUser> userList = wkfUserDao.list(map);
		jsonArray = new JSONArray();
		for (AppUser au : userList) {
			JSONObject obj = new JSONObject();
			obj.put("id", au.getUserId());
			obj.put("text", au.getUserName());
			jsonArray.add(obj);
		}
		return jsonArray;
	}

	@ResponseBody
	@RequestMapping(value = "/getApproveUrl")
	public JSONObject getApproveUrl(String appNo) throws Exception {
		// WkfInterface wkfInterface = (WkfInterface)
		// SourceTemplate.getSpringContextInstance().getBean("wkfInterface");
		Task task = wkfInterfaceFeign.getTask(appNo, null);
		// TaskService service = WFUtil.getTaskService();
		Map<String, String> map = new HashMap<>();
		map.put("id", task.getId());
		map.put("approveUrl", task.getApproveUrl());
		String approveUrl = wkfDesginFeign.getTaskURL(map);// service.getTaskURL(task.getId(),
															// task.getApproveUrl());
		json = new JSONObject();
		json.put("approveUrl", approveUrl);
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/getBranchs")
	public JSONArray getBranchs() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		if (query == null) {
            query = "";
        }
		map.put("branchId", query);
		map.put("branchName", query);
		List<AppBranch> list = wkfBranchDao.list(map);
		jsonArray = new JSONArray();
		for (AppBranch au : list) {
			JSONObject obj = new JSONObject();
			obj.put("id", au.getBranchId());
			obj.put("name", au.getBranchName());
			obj.put("pId", au.getParentId());
			jsonArray.add(obj);
		}
		return jsonArray;
	}

	@ResponseBody
	@RequestMapping(value = "/save",produces = {"text/html;charset=utf-8"})
	public String save(String jsonData) {
		Dom4jXML dom4jXML = new Dom4jXML();
		json = new JSONObject();
		try {
			Map<String, Object> map = dom4jXML.DOM4JCreateXML(jsonData);
			// ProcessService ps = new ProcessService();
			// String flagStr = ps.saveOrUpdate(map.get("id").toString(),
			// map.get("text").toString());
			String flagStr = wkfDesginFeign.saveOrUpdate(map.get("id").toString(), URLEncoder.encode(map.get("text").toString(),"UTF-8"));
			if ("success".equals(flagStr)) {
				json.put("flag", true);
				json.put("key", map.get("id").toString());
				json.put("msg", "保存成功!");
			} else {
				json.put("flag", false);
				json.put("msg", "保存失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	@ResponseBody
	@RequestMapping(value = "/saveAndStart",produces = {"text/html;charset=utf-8"})
	public String saveAndStart(String jsonData) {
		Dom4jXML dom4jXML = new Dom4jXML();
		json = new JSONObject();
		try {
			Map<String,Object> map = dom4jXML.DOM4JCreateXML(jsonData);
			
			String flagStr = wkfDesginFeign.saveAndStart(map.get("id").toString(), URLEncoder.encode(map.get("text").toString(),"UTF-8"));
			if("success".equals(flagStr)){
				json.put("flag", true);
				json.put("key", map.get("id").toString());
				json.put("msg", "保存成功!");
			}else{
				json.put("flag", false);
				json.put("msg", "保存失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	
	@ResponseBody
	@RequestMapping(value = "/open")
	public JSONObject open(MultipartFile upload) throws Exception {
		Dom4jXML dom4jXML = new Dom4jXML();
		SAXReader reader = new SAXReader();
		File f = null;
		try {
			f = File.createTempFile("tmp", null);
			upload.transferTo(f);
			json = dom4jXML.DOM4JResXML(reader.read(f));
			f.deleteOnExit();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/findByProcessInstanceId")
	public JSONObject findByProcessInstanceId(String appNo, String appWorkflowNo, String query) throws Exception {
		json = new JSONObject();
		jsonArray = new JSONArray();
		WfExecution wfExecution = new WfExecution();
		if ("".equals(query)) {
			query = null;
		}
		if ("".equals(appNo)) {
			appNo = null;
		}
		// else{
		// System.out.println(appWorkflowNo);
		// }
		if (query == null && appNo == null) {
			return json;
		}
		wfExecution.setExecution(query);
		wfExecution.setAppId(appNo);
		jsonArray = JSONArray.fromObject(wfExecutionBo.getById(wfExecution));
		Map<String, String> map = new HashMap<String, String>();
		for (int i = jsonArray.size() - 1; i >= 0; i--) {
			if (map.get(jsonArray.getJSONObject(i).getString("activityname")) == null) {
				map.put(jsonArray.getJSONObject(i).getString("activityname"),
						jsonArray.getJSONObject(i).getString("assignee"));
			} else {
				if ("signtask".equals(jsonArray.getJSONObject(i).getString("activityType"))) {
					map.put(jsonArray.getJSONObject(i).getString("activityname"),
							map.get(jsonArray.getJSONObject(i).getString("activityname")) + ","
									+ jsonArray.getJSONObject(i).getString("assignee"));
				}
				jsonArray.remove(i);
			}
		}

		if (jsonArray.size() == 0) {
			List<ProcessDefinitionImpl> list = wkfDesginFeign.processDefinitionKey(appWorkflowNo);
			if (list.size() > 0) {
				json.put("value", new Dom4jXML().DOM4JResXML(
						DocumentHelper.parseText(new String((list.get(list.size() - 1)).getValue(), "UTF-8"))));
			}
		} else {
			List<Integer> cutlist = new ArrayList<Integer>();
			int openCut = 0;
			for (int i = 0; i < jsonArray.size(); i++) {
				if (json.get(jsonArray.getJSONObject(i).getString("activityname")) != null) {
					json.getJSONObject(jsonArray.getJSONObject(i).getString("activityname")).put("state",
							jsonArray.getJSONObject(i).getString("state"));
				} else {
					JSONObject obj = new JSONObject();
					String state = jsonArray.getJSONObject(i).getString("state");
					// String assignee =
					// jsonArray.getJSONObject(i).getString("assignee");
					String assignee = map.get(jsonArray.getJSONObject(i).getString("activityname"));

					if ("rollbacked".equals(state)) {
						cutlist.add(i);
					}
					if ("open".equals(state)) {
						openCut = i;
					}
					String group = "";
					if (assignee == null || "".equals(assignee)) {
						assignee = "";
						Map<String, String> assMap = new HashMap<String, String>();
						assMap.put("appId", appNo);
						String roleNo = wkfApprovalRoleFeign.getByAppId(assMap);// 获取审批角色
						if (roleNo != null && !"".equals(roleNo)) {
							WkfApprovalUser wkfApprovalUser = new WkfApprovalUser();
							wkfApprovalUser.setWkfRoleNoArr(roleNo.split(","));
							List<WkfApprovalUser> appUserList = wkfApprovalUserFeign.getByWkfRoleNoArr(wkfApprovalUser);
							for (WkfApprovalUser wau : appUserList) {
								if (assignee.indexOf(wau.getDisplayname()) == -1) {
									assignee += wau.getDisplayname() + ",";
								}
							}
							if (!"".equals(assignee)) {
								assignee = assignee.substring(0, assignee.length() - 1);
							}
						}
					} else {
						WkfApprovalUser wkfApprovalUser = new WkfApprovalUser();
						if (assignee.indexOf(",") == -1) {
							wkfApprovalUser.setWkfUserName(assignee);
							List<WkfApprovalUser> appUserList = wkfApprovalUserFeign.getAllList(wkfApprovalUser);
							if (appUserList.size() > 0) {
								assignee = appUserList.get(0).getDisplayname();
							}
						} else {
							wkfApprovalUser.setWkfUserNameArr(assignee.split(","));
							List<WkfApprovalUser> appUserList = wkfApprovalUserFeign.getByWkfRoleNoArr(wkfApprovalUser);
							assignee = "";
							for (WkfApprovalUser wau : appUserList) {
								if (assignee.indexOf(wau.getDisplayname()) == -1) {
									if (assignee.indexOf(wau.getDisplayname()) == -1) {
										assignee += wau.getDisplayname() + ",";
									}
								}
							}
							if (!"".equals(assignee)) {
								assignee = assignee.substring(0, assignee.length() - 1);
							}
						}
					}
					obj.put("group", group);
					obj.put("assignee", assignee);
					obj.put("state", state);
					obj.put("index", i);
					json.put(jsonArray.getJSONObject(i).getString("activityname"), obj);
				}
			}

			if (cutlist.size() > 0) {
				Collections.sort(cutlist);
				Iterator it = json.keys();
				while (it.hasNext()) {
					String key = (String) it.next();
					JSONObject valueObj = json.getJSONObject(key);
					if (valueObj.getInt("index") <= cutlist.get(0) && valueObj.getInt("index") > openCut) {
						valueObj.put("state", "rollbacked");
					}
				}
			}

			if (jsonArray.size() > 0) {
				List<ProcessDefinitionImpl> list = wkfDesginFeign
						.processDefinitionId(jsonArray.getJSONObject(0).getString("procdefid"));
				if (list.size() > 0) {
					// System.out.println(new String((list.get(0)).getValue(),
					// "UTF-8"));
					// ProcessDefinitionImpl p =new
					// Gson().fromJson(list.get(0).toString(),
					// ProcessDefinitionImpl.class);
					json.put("value", new Dom4jXML()
							.DOM4JResXML(DocumentHelper.parseText(new String((list.get(0)).getValue(), "UTF-8"))));
					// json.put("value", new
					// Dom4jXML().DOM4JResXML(DocumentHelper.parseText(new
					// String(list.get(0).get("value"), "UTF-8"))));
				}
			}
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/download")
	public void download(String jsonData) throws Exception {
		Dom4jXML dom4jXML = new Dom4jXML();
		String xmlPath = request.getSession().getServletContext().getRealPath("/tech/wkf/xml");
		dom4jXML.doDownLoad(xmlPath + "/" + jsonData, jsonData, response);
	}

	@ResponseBody
	@RequestMapping(value = "/create")
	public JSONObject create(String jsonData) throws Exception {
		json = new JSONObject();
		Dom4jXML dom4jXML = new Dom4jXML();
		Map<String, Object> map = dom4jXML.DOM4JCreateXML(jsonData);
		String xmlPath = request.getSession().getServletContext().getRealPath("/tech/wkf/xml");
		dom4jXML.doCreateXmlFile(xmlPath, map.get("id").toString(), (Document) map.get("doc"));
		json.put("fileName", map.get("id") + ".wfdl.xml");
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public JSONArray list(String jsonData) throws Exception {
		jsonArray = new JSONArray();
		json = JSONObject.fromObject(jsonData);
		String description = json.getString("description");

		List<ProcessDefinitionImpl> pdlist = wkfDesginFeign.orderDesc();
		// .orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION).list();
		for (ProcessDefinition pd : pdlist) {
			if (!"".equals(description)) {
				if (!pd.getDescription().contains(description)) {
					continue;
				}
			}
			JSONObject obj = new JSONObject();
			obj.put("deploymentId", pd.getDeploymentId());
			obj.put("id", pd.getId());
			obj.put("name", pd.getName());
			obj.put("version", pd.getVersion());
			obj.put("description", pd.getDescription());
			obj.put("value", new Dom4jXML().DOM4JResXML(DocumentHelper.parseText(new String(pd.getValue(), "UTF-8"))));
			obj.put("key", pd.getKey());
			jsonArray.add(obj);
		}
		return jsonArray;
	}

	@ResponseBody
	@RequestMapping(value = "/listForShowView")
	public JSONObject listForShowView() throws Exception {
		json = new JSONObject();
		List<ProcessDefinitionImpl> pdlist = wkfDesginFeign.createProcessDefinitionQuery();
		Collections.sort(pdlist, new Comparator<ProcessDefinition>() {
			@Override
			public int compare(ProcessDefinition arg0, ProcessDefinition arg1) {
				if (arg0.getName().compareTo(arg1.getName()) == 0) {
					return arg0.getVersion() - arg1.getVersion();
				} else {
					return arg0.getName().compareTo(arg1.getName());
				}
			}
		});
		for (ProcessDefinition pd : pdlist) {
			if (json.getJSONObject(pd.getKey()).isNullObject()) {
				JSONObject o = new JSONObject();
				o.put("version", pd.getVersion());
				o.put("description", pd.getDescription());
				o.put("value",
						new Dom4jXML().DOM4JResXML(DocumentHelper.parseText(new String(pd.getValue(), "UTF-8"))));
				json.put(pd.getKey(), o);
			} else {
				int version = json.getJSONObject(pd.getKey()).getInt("version");
				if (version < pd.getVersion()) {
					json.getJSONObject(pd.getKey()).put("version", pd.getVersion());
					json.getJSONObject(pd.getKey()).put("description", pd.getDescription());
					json.getJSONObject(pd.getKey()).put("value",
							new Dom4jXML().DOM4JResXML(DocumentHelper.parseText(new String(pd.getValue(), "UTF-8"))));
				}
			}
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/findById",produces = {"text/html;charset=utf-8"})
	public String findById(String jsonData) throws Exception {
		Dom4jXML dom4jXML = new Dom4jXML();
		String xml = wkfDesginFeign.findById(jsonData);
		json = dom4jXML.DOM4JResXML(DocumentHelper.parseText(xml));
		return json.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/getBizByXml")
	public JSONObject getBizByXml() throws Exception {
		String filePath = getClass().getResource("/workflow.biz.cfg.xml").getPath();
		File file = new File(filePath);
		Dom4jXML dom4jXML = new Dom4jXML();
		json = dom4jXML.DOM4JResBizXML(file);
		return json;
	}
	// @RequestMapping(value = "/toChangeUser")
	// public String toChangeUser(String taskId, String appId) throws Exception
	// {
	// ActionContext.initialize(request, response);
	// formwkf0051 = formService.getFormData("wkf0051");
	// dataMap = new HashMap<String, Object>();
	// dataMap.put("taskId", taskId);
	// dataMap.put("appId", appId);
	// getObjValue(formwkf0051, dataMap);
	// // System.out.println(taskId+"-----"+userId+"-----"+appId+"-----");
	// return "/component/wkf/Task_ChangeUser";
	// }
}
