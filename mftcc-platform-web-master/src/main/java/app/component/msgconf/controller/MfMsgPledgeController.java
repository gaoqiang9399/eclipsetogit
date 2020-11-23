package app.component.msgconf.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import app.component.collateral.entity.CollateralConstant;
import app.component.collateral.entity.MfCollateralClass;
import app.component.collateral.feign.MfCollateralClassFeign;
import app.component.msgconf.entity.MfMsgPledge;
import app.component.msgconf.entity.MfMsgVar;
import app.component.msgconf.feign.MfMsgPledgeFeign;
import app.component.msgconf.feign.MfMsgVarFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfMsgPledgeAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Jul 10 13:45:12 CST 2017
 **/
@Controller
@RequestMapping("/mfMsgPledge")
public class MfMsgPledgeController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfMsgPledgeFeign mfMsgPledgeFeign;
	@Autowired
	private MfMsgVarFeign mfMsgVarFeign;
	@Autowired
	private MfCollateralClassFeign mfCollateralClassFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @param mfMsgPledge
	 * @return
	 * @throws Exception
	 */
	private void getTableData(Map<String, Object> dataMap, String tableId, MfMsgPledge mfMsgPledge) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
        mfMsgPledge = mfMsgPledgeFeign.getById(mfMsgPledge);
        List<MfMsgPledge> mfMsgPledgeList = new ArrayList<>();
        mfMsgPledgeList.add(mfMsgPledge);
        String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag", mfMsgPledgeList, null, true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/msgconf/MfMsgPledge_List";
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfMsgPledge mfMsgPledge = new MfMsgPledge();
		try {
			mfMsgPledge.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfMsgPledge.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfMsgPledge.setCriteriaList(mfMsgPledge, ajaxData);// 我的筛选
			// this.getRoleConditions(mfMsgPledge,"1000000067");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			
			ipage.setParams(this.setIpageParams("mfMsgPledge", mfMsgPledge));
			// 自定义查询Bo方法
			ipage = mfMsgPledgeFeign.findByPage(ipage);
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
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0001");
		MfMsgPledge mfMsgPledge = new MfMsgPledge();
		List<MfMsgPledge> mfMsgPledgeList = mfMsgPledgeFeign.getAll(mfMsgPledge);
		model.addAttribute("formmfmsgpledge0001", formmfmsgpledge0001);
		model.addAttribute("mfMsgPledgeList", mfMsgPledgeList);
		model.addAttribute("query", "");
		return "/component/msgconf/MfMsgPledge_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String funType = (String) map.get("funType");
			if (null == funType || "".equals(funType)) {
				funType = "01";
			}
			FormData formmfmsgpledge0001=null;
			if ("01".equals(funType)) {
				 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0001");
			} else if ("02".equals(funType)) {
				 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0002");
			} else if ("03".equals(funType)) {
				 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0003");
			} else if ("04".equals(funType)) {
				 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0004");
			} else if ("05".equals(funType)) {
				 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0005");
			} else if ("06".equals(funType)) {
				 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0006");
			} else if ("07".equals(funType)) {
				 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0002");
			}else {
			}
			getFormValue(formmfmsgpledge0001, map);
			if (this.validateFormData(formmfmsgpledge0001)) {
				MfMsgPledge mfMsgPledge = new MfMsgPledge();
				setObjValue(formmfmsgpledge0001, mfMsgPledge);
				String id = WaterIdUtil.getWaterId();
				mfMsgPledge.setId(id);
				String args = getArgsStr(mfMsgPledge.getVarUsage(), mfMsgPledge.getModelContent());
				mfMsgPledge.setArgs(args);
				mfMsgPledge.setTriggerType("2");// 默认日终操作
				mfMsgPledgeFeign.insert(mfMsgPledge);
				getTableData(dataMap, tableId, mfMsgPledge);
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String funType = (String) map.get("funType");
			if (null == funType || "".equals(funType)) {
				funType = "01";
			}
			FormData formmfmsgpledge0001 = null;
			if ("01".equals(funType)) {
				 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0001");
			} else if ("02".equals(funType)) {
				 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0002");
			} else if ("03".equals(funType)) {
				 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0003");
			} else if ("04".equals(funType)) {
				 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0004");
			} else if ("05".equals(funType)) {
				 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0005");
			} else if ("06".equals(funType)) {
				 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0006");
			} else if ("07".equals(funType)) {
				 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0002");
			}else {
			}
			getFormValue(formmfmsgpledge0001, map);
			if (this.validateFormData(formmfmsgpledge0001)) {
				MfMsgPledge mfMsgPledge = new MfMsgPledge();
				setObjValue(formmfmsgpledge0001, mfMsgPledge);
				String args = getArgsStr(mfMsgPledge.getVarUsage(), mfMsgPledge.getModelContent());
				mfMsgPledge.setArgs(args);
				mfMsgPledge.setTriggerType("2");// 默认日终操作
				mfMsgPledgeFeign.update(mfMsgPledge);
				getTableData(dataMap, tableId, mfMsgPledge);
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
	 * AJAX更新启用状态保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateUseFlagAjax")
	@ResponseBody
	public Map<String, Object> updateUseFlagAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			MfMsgPledge mfMsgPledge = (MfMsgPledge) JSONObject.toBean(jsonObject, MfMsgPledge.class);
			mfMsgPledgeFeign.updateUseFlagById(mfMsgPledge);
			getTableData(dataMap, tableId, mfMsgPledge);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0001");
		MfMsgPledge mfMsgPledge = new MfMsgPledge();
		mfMsgPledge.setId(id);
		mfMsgPledge = mfMsgPledgeFeign.getById(mfMsgPledge);
		getObjValue(formmfmsgpledge0001, mfMsgPledge, formData);
		if (mfMsgPledge != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String id,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfMsgPledge mfMsgPledge = new MfMsgPledge();
		mfMsgPledge.setId(id);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfMsgPledge = (MfMsgPledge) JSONObject.toBean(jb, MfMsgPledge.class);
			mfMsgPledgeFeign.delete(mfMsgPledge);
			getTableData(dataMap, tableId, mfMsgPledge);// 获取列表
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

	@RequestMapping(value = "/input")
	public String input(Model model,String classId,String funType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfMsgPledge mfMsgPledge = new MfMsgPledge();
		mfMsgPledge.setClassId(classId);
		if (null == funType || "".equals(funType)) {
			funType = "01";
		}
		FormData formmfmsgpledge0001 = null;
		if ("01".equals(funType)) {
			 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0001");
		} else if ("02".equals(funType)) {
			 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0002");
		} else if ("03".equals(funType)) {
			 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0003");
		} else if ("04".equals(funType)) {
			 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0004");
		} else if ("05".equals(funType)) {
			 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0005");
		} else if ("06".equals(funType)) {
			 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0006");
		} else if ("07".equals(funType)) {
			 formmfmsgpledge0001 = formService.getFormData("mfmsgpledge0002");
		}else {
		}
		mfMsgPledge.setFunType(funType);
		getObjValue(formmfmsgpledge0001, mfMsgPledge);

		/*
		 * MfCollateralClass mcc = new MfCollateralClass();
		 * mcc.setClassId(classId); mcc.setClassSecondName(classSecondName);
		 * List<MfCollateralClass> list = new ArrayList<MfCollateralClass>();
		 * list.add(mcc);
		 */
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
		List<MfCollateralClass> list = mfCollateralClassFeign.getAll(mfCollateralClass);
		// 对押品类别列表进行处理
		List<MfCollateralClass> tempList = new ArrayList<MfCollateralClass>();
		for (MfCollateralClass mcc : list) {
			if (null == mcc.getClassFirstNo() || "".equals(mcc.getClassFirstNo())
					|| CollateralConstant.NOUSED.equals(mcc.getUseFlag())) {
				tempList.add(mcc);
				continue;
			}
		}
		list.removeAll(tempList);

		JSONObject json = new JSONObject();
		JSONArray collClassArray = JSONArray.fromObject(list);
		for (int i = 0; i < collClassArray.size(); i++) {
			collClassArray.getJSONObject(i).put("id", collClassArray.getJSONObject(i).getString("classId"));
			collClassArray.getJSONObject(i).put("name", collClassArray.getJSONObject(i).getString("classSecondName"));
		}
		json.put("collClass", collClassArray);

		// 发送对象
		JSONArray reciverTypeMap = new CodeUtils().getJSONArrayByKeyName("MSG_RECIVER_TYPE");
		String reciverTypeItems = reciverTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
		json.put("reciverTypeItems", reciverTypeItems);

		// 发送方式
		JSONArray sendTypeMap = new CodeUtils().getJSONArrayByKeyName("MSG_SEND_TYPE");
		String sendTypeItems = sendTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
		json.put("sendTypeItems", sendTypeItems);

		// 变量来源类型
		JSONArray varUsageMap = new CodeUtils().getJSONArrayByKeyName("MSG_VAR_USAGE");
		String varUserageItems = varUsageMap.toString().replaceAll("optName", "name").replace("optCode", "id");
		json.put("varUserageItems", varUserageItems);

		String ajaxData = json.toString();

		model.addAttribute("formmfmsgpledge0001", formmfmsgpledge0001);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/msgconf/MfMsgPledge_Insert";
	}

	@RequestMapping(value = "/inputModel")
	public String inputModel(Model model, String id,String varUsage) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formmfmsgpledgemodel0001 = formService.getFormData("mfmsgpledgemodel0001");
		MfMsgPledge mfMsgPledge = new MfMsgPledge();
		mfMsgPledge.setId(id);
		mfMsgPledge = mfMsgPledgeFeign.getById(mfMsgPledge);
		if(null != mfMsgPledge){
			getObjValue(formmfmsgpledgemodel0001, mfMsgPledge);
		}
		Map<String, String> vumap =new CodeUtils().getMapByKeyName("MSG_VAR_USAGE");
		MfMsgVar mfMsgVar = new MfMsgVar();
		List<MfMsgVar> mmvlist = null;
		Map<String, List<MfMsgVar>> vuListMap = new HashMap<String, List<MfMsgVar>>();
		String[] varUsageArr = varUsage.split(",");
		for(String vuStr:varUsageArr){
			if("".equals(vuStr)){
				continue;
			}
			mfMsgVar.setVarUsage(vuStr);
			mfMsgVar.setVarType("01");
			mmvlist = mfMsgVarFeign.getListByVarUsage(mfMsgVar);
			vuListMap.put(vuStr + "_" + vumap.get(vuStr), mmvlist);
		}

		JSONObject json = new JSONObject();
		
		json.put("vuListMap", JSONObject.fromObject(vuListMap));
		
		String ajaxData = json.toString();
		
		model.addAttribute("formmfmsgpledgemodel0001", formmfmsgpledgemodel0001);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/msgconf/MfMsgPledge_InsertModel";
	}

	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData,String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfMsgPledge mfMsgPledge = new MfMsgPledge();
		mfMsgPledge.setId(id);
		mfMsgPledge = mfMsgPledgeFeign.getById(mfMsgPledge);
		String funType = mfMsgPledge.getFunType();
		FormData formmfmsgpledge0001 = null;
		if ("01".equals(funType)) {
			 formmfmsgpledge0001 = formService.getFormData("mfmsgpledgeDetail0001");
		} else if ("02".equals(funType)) {
			 formmfmsgpledge0001 = formService.getFormData("mfmsgpledgeDetail0002");
		} else if ("03".equals(funType)) {
			 formmfmsgpledge0001 = formService.getFormData("mfmsgpledgeDetail0003");
		} else if ("04".equals(funType)) {
			 formmfmsgpledge0001 = formService.getFormData("mfmsgpledgeDetail0004");
		} else if ("05".equals(funType)) {
			 formmfmsgpledge0001 = formService.getFormData("mfmsgpledgeDetail0005");
		} else if ("06".equals(funType)) {
			 formmfmsgpledge0001 = formService.getFormData("mfmsgpledgeDetail0006");
		} else if ("07".equals(funType)) {
			 formmfmsgpledge0001 = formService.getFormData("mfmsgpledgeDetail0002");
		}else {
		}
		getObjValue(formmfmsgpledge0001, mfMsgPledge);

		/*
		 * MfCollateralClass mcc = new MfCollateralClass();
		 * mcc.setClassId(mfMsgPledge.getClassId());
		 * mcc.setClassSecondName(classSecondName); List<MfCollateralClass> list
		 * = new ArrayList<MfCollateralClass>(); list.add(mcc);
		 */
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
		List<MfCollateralClass> list = mfCollateralClassFeign.getAll(mfCollateralClass);
		// 对押品类别列表进行处理
		List<MfCollateralClass> tempList = new ArrayList<MfCollateralClass>();
		for (MfCollateralClass mcc : list) {
			if (null == mcc.getClassFirstNo() || "".equals(mcc.getClassFirstNo())
					|| CollateralConstant.NOUSED.equals(mcc.getUseFlag())) {
				tempList.add(mcc);
				continue;
			}
		}
		list.removeAll(tempList);
		JSONObject json = new JSONObject();
		JSONArray collClassArray = JSONArray.fromObject(list);
		for (int i = 0; i < collClassArray.size(); i++) {
			collClassArray.getJSONObject(i).put("id", collClassArray.getJSONObject(i).getString("classId"));
			collClassArray.getJSONObject(i).put("name", collClassArray.getJSONObject(i).getString("classSecondName"));
		}
		json.put("collClass", collClassArray);

		// 发送对象
		JSONArray reciverTypeMap = new CodeUtils().getJSONArrayByKeyName("MSG_RECIVER_TYPE");
		String reciverTypeItems = reciverTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
		json.put("reciverTypeItems", reciverTypeItems);

		// 发送方式
		JSONArray sendTypeMap = new CodeUtils().getJSONArrayByKeyName("MSG_SEND_TYPE");
		String sendTypeItems = sendTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
		json.put("sendTypeItems", sendTypeItems);

		// 变量来源类型
		JSONArray varUsageMap = new CodeUtils().getJSONArrayByKeyName("MSG_VAR_USAGE");
		String varUserageItems = varUsageMap.toString().replaceAll("optName", "name").replace("optCode", "id");
		json.put("varUserageItems", varUserageItems);

		ajaxData = json.toString();

		model.addAttribute("formmfmsgpledge0001", formmfmsgpledge0001);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/msgconf/MfMsgPledge_Detail";
	}

	private String getArgsStr(String varUsageTem, String modelContentTem) throws Exception {
		String argsStr = "";
		String varNameTem = "";
		String varUsageTemStr = "";
		Set<String> argsStrSet = new HashSet<String>();
		Map<String, String> argsStrMap = new HashMap<String, String>();
		// 对变量来源类型进行处理
		String[] strArr = varUsageTem.split("\\|");
		for (String str : strArr) {
			if (!"".equals(str)) {
				varUsageTemStr = varUsageTemStr + ",'" + str + "'";
			}
		}
		if (varUsageTemStr.startsWith(",")) {
			varUsageTemStr = varUsageTemStr.substring(1);
		}
		varUsageTemStr = "(" + varUsageTemStr + ")";
		MfMsgVar mfMsgVar = new MfMsgVar();
		mfMsgVar.setVarUsage(varUsageTemStr);
		List<MfMsgVar> mmvlist = mfMsgVarFeign.getListByVarUsages(mfMsgVar);
		for (MfMsgVar mmv : mmvlist) {
			if ("02".equals(mmv.getVarType())) {
				// argsStr = argsStr + "|" + mmv.getVarId();
				if (null != argsStrMap.get(mmv.getVarTb())) {
					argsStrMap.put(mmv.getVarTb(), argsStrMap.get(mmv.getVarTb()) + "|" + mmv.getVarId());
				} else {
					argsStrMap.put(mmv.getVarTb(), "|" + mmv.getVarId());
				}
				continue;
			}
			varNameTem = mmv.getVarName();
			varNameTem = "{" + varNameTem + "}";
			if (modelContentTem.contains(varNameTem)) {
				argsStr = argsStr + "|" + mmv.getVarId();
				argsStrSet.add(mmv.getVarTb());
			}
		}
		// set遍历
		for (String str : argsStrSet) {
			if (null != argsStrMap.get(str)) {
				argsStr = argsStr + argsStrMap.get(str);
			}
		}

		if (argsStr.startsWith("|")) {
			argsStr = argsStr.substring(1);
		}
		return argsStr;
	}


}
