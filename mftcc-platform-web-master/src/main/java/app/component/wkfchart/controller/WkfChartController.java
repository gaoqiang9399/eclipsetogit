package app.component.wkfchart.controller;

import app.base.SpringUtil;
import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.component.app.entity.MfBusApplyHis;
import app.component.app.feign.MfBusApplyFeign;
import app.component.app.feign.MfBusApplyHisFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCusCreditContractHis;
import app.component.auth.feign.MfCusCreditContractHisFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.calc.charge.feign.MfBusChargeFeeFeign;
import app.component.calc.fee.entity.MfBusChargeFee;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.compensatory.feign.MfBusCompensatoryApproveFeign;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.doc.entity.DocManage;
import app.component.docinterface.DocInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.extension.entity.MfBusExtensionApply;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.receaccount.entity.MfBusFincAppMain;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdct.entity.MfKindForm;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.sys.entity.SysOrg;
import app.component.sysInterface.SysInterfaceFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.wkf.feign.ResolveHisProcessUtilFeign;
import app.tech.wkf.feign.ResolveProcessUtilFeign;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 贷款计算器
 * 
 * @author Tangxj
 */
@RestController
@RequestMapping("/wkfChart")
public class WkfChartController extends BaseFormBean {
	@Autowired
	private DocInterfaceFeign docInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private SysInterfaceFeign sysInterfaceFeign;
	@Autowired
	private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private ResolveProcessUtilFeign resolveProcessUtilFeign;
	@Autowired
	private ResolveHisProcessUtilFeign resolveHisProcessUtilFeign;
	@Autowired
	private MfCusCreditContractHisFeign mfCusCreditContractHisFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private MfBusApplyFeign mfBusApplyFeign;

	@Autowired
	private MfBusCompensatoryApproveFeign   mfBusCompensatoryApproveFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;

	@Autowired
	private MfBusApplyHisFeign  mfBusApplyHisFeign;
	@Autowired
	private MfBusChargeFeeFeign mfBusChargeFeeFeign;

	/**
	 * 
	 * 方法描述： 获取业务流程图
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-10-23 下午4:39:44
	 */
	@RequestMapping(value = "/getWkfChartInfoAjax")
	@ResponseBody
	public Map<String, Object> getWkfChartInfoAjax(String appNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> tmpMap = new HashMap<String, Object>();

		JSONArray jsonArray = resolveProcessUtilFeign.resolveProcess(appNo);
		jsonArray = dealFlowDecisionJion(jsonArray);
		// 处理业务流程中有是否走审批的判定节点
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appNo);// 申请信息
		if (mfBusApply != null) {// 业务流程图
			jsonArray = auditNodeControl(jsonArray, mfBusApply);// 处理业务流程中审批判定节点造成的流程节点不显示问题

			jsonArray = storageConfirmNodeControl(jsonArray, mfBusApply);// 处理担保登记判定节点造成的后续流程节点不展示问题
			jsonArray = certiInfoDecisionHandler(jsonArray, mfBusApply);// 处理权证登记判定节点造成的后续流程节点不展示问题
		}
		jsonArray = cusCreditAuditNodeControl(jsonArray,appNo);
		MfBusExtensionApply mfBusExtensionApply = pactInterfaceFeign.getMfBusExtensionApply(appNo);
		if (mfBusExtensionApply != null) {
			jsonArray = cusAuditNodeControl(jsonArray, mfBusExtensionApply.getApplyProcessId(), "extension");// 展期业务流程中审批判定节点造成的流程节点不显示问题处理
		}
		MfBusFincAppMain mfBusFincAppMain = new MfBusFincAppMain();
		mfBusFincAppMain.setFincMainId(appNo);
		mfBusFincAppMain = pactInterfaceFeign.getMfBusFincAppMain(mfBusFincAppMain);
		if(mfBusFincAppMain!=null){
			MfBusApply mfBusApplyTmp = appInterfaceFeign.getMfBusApplyByAppId(mfBusFincAppMain.getAppId());
			jsonArray = auditNodeControl(jsonArray, mfBusApplyTmp);
		}

		dataMap.put("jsonArray", jsonArray);
		dataMap.put("flag", "success");
		return dataMap;
	}

	private JSONArray cusCreditAuditNodeControl(JSONArray jsonArray, String appNo) throws Exception{
        String creditPrimaryProcessId = creditApplyInterfaceFeign.getCreditPrimaryProcessId(appNo);
        jsonArray = cusAuditNodeControl(jsonArray, creditPrimaryProcessId, "primaryApprove");// 处理授信流程中预审审批判定节点造成的流程节点不显示问题
        String creditProcessId = creditApplyInterfaceFeign.getCreditProcessId(appNo);
		jsonArray = cusAuditNodeControl(jsonArray, creditProcessId, "credit");// 处理授信流程中审批判定节点造成的流程节点不显示问题
        String creditPactPrimaryProcessId = creditApplyInterfaceFeign.getCreditPactPrimaryProcessId(appNo);
        jsonArray = cusAuditNodeControl(jsonArray, creditPactPrimaryProcessId, "creditPactPrimary");// 处理授信流程中预审审批判定节点造成的流程节点不显示问题
        String creditPactProcessId = creditApplyInterfaceFeign.getCreditPactProcessId(appNo);
		jsonArray = cusAuditNodeControl(jsonArray, creditPactProcessId, "creditApprove");// 处理授信流程中审批判定节点造成的流程节点不显示问题
		String ifRepeatPact = creditApplyInterfaceFeign.getIfRepeatPact(appNo);
		if(StringUtil.isNotBlank(ifRepeatPact)){
			jsonArray = cusAuditNodeControl(jsonArray, ifRepeatPact, "ifRepeatPact");
		}
		String firstAppFlag = creditApplyInterfaceFeign.getCreditApplyId(appNo);
		jsonArray = cusAuditNodeControl(jsonArray, firstAppFlag, "firstApply");// 处理授信流程中审批判定首笔担保节点的流程节点不显示问题

        YmlConfig ymlConfig = (YmlConfig)SpringUtil.getBean(YmlConfig.class);
		String projectName = ymlConfig.getSysParams().get("sys.project.name");
		return jsonArray;
	}

	/**
	 * 
	 * 方法描述： 获取历史流程图
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @param isApp
	 * @param formType
	 * @param termType
	 * @param fiveclassId
	 * @param activityType
	 * @param examHisId
	 * @param rateType
	 * @param query
	 * @date 2017-10-23 下午4:15:06
	 */
	@RequestMapping(value = "/getHisWkfChartInfoAjax")
	public Map<String, Object> getHisWkfChartInfoAjax(String appNo, String isApp, String formType, String termType,
			String fiveclassId, String activityType, String examHisId, String rateType, String query) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("flag","success");
        String approveType = request.getParameter("approveType");
		//审批历史展示形式1-图文形式，2-列表展示
		String approveHisShowFlag = new CodeUtils().getSingleValByKey("APPROVE_HIS_SHOW_FLAG");
		dataMap.put("approveHisShowFlag",approveHisShowFlag);
		if("2".equals(approveHisShowFlag)){
			String tableId = "tablewfHistTaskListBase";
			if(StringUtil.isNotEmpty(approveType)){
				//表单编号参数
				MfKindForm mfKindForm =prdctInterfaceFeign.getMfKindForm("base",approveType);

				if(mfKindForm!=null){
				   tableId = StringUtil.isNotEmpty(mfKindForm.getListModel())?mfKindForm.getListModel():mfKindForm.getListModelBase();
				}
			}
			if(StringUtil.isEmpty(tableId)){
				tableId = "tablewfHistTaskListBase";
			}
			//审批历史列表
			List<Map<String,String>> resList = resolveHisProcessUtilFeign.getWfHistTaskList(appNo,formType,approveType);

			for (int i = 0; i < resList.size(); i++) {
				Map<String, String> task = resList.get(i);
				String result = task.get("result");

				if ("rollback".equals(result)) {// 发回重审
					Map<String, String> next_task = resList.get(i + 1);// 下一岗位记录

					if (next_task.get("id").indexOf("supplement") >= 0) {// 下一个岗位为补充资料
						task.put("result", "supplement");// 意见类型由'发回重审'改为'退回补充资料', 详见字典项 WKF_APPROVE_RESULT
					}
				}
			}

			if(resList!=null && resList.size()>0){
				JsonTableUtil jtu = new JsonTableUtil();
				String htmlStr = jtu.getJsonStr(tableId, "tableTag", resList, null, true);
				dataMap.put("htmlStr",htmlStr);
			}else{
				dataMap.put("flag","error");
			}
		}else {
			JSONArray jsonArray = resolveHisProcessUtilFeign.resolveProcess(appNo);

			for (int i = 0; i < jsonArray.size(); i++) {
				Map<String, String> task = jsonArray.getJSONObject(i);
				String result = task.get("result");

				if ("rollback".equals(result)) {// 发回重审
					Map<String, String> next_task = jsonArray.getJSONObject(i + 1);// 下一岗位记录

					if (next_task.get("id").indexOf("supplement") >= 0) {// 下一个岗位为补充资料
						task.put("result", "supplement");// 意见类型由'发回重审'改为'退回补充资料', 详见字典项 WKF_APPROVE_RESULT
					}
				}
			}

			//处理审批过程中的附件
			jsonArray = getApprovalNodeAttachment(jsonArray, appNo);
			dataMap.put("jsonArray", jsonArray);
			dataMap = getVerticalFlowData(appNo,jsonArray,approveType, activityType, query);

			dataMap.put("flag", "success");
		}
		return dataMap;
	}

	private JSONArray dealFlowDecisionJion(JSONArray jsonArray) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray jsonArrayTmp = new JSONArray();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			map.put(jsonObj.getString("id"), jsonObj);
		}
		// 开始节点
		if(map.get("start")==null){
			return jsonArray;
		}
		jsonArrayTmp.add(map.get("start"));
		map.put("curJsonObj", map.get("start"));
		getNextNode(jsonArrayTmp, map);
		System.out.println(jsonArrayTmp);
		return jsonArrayTmp;

	}
	private JSONArray removeDup(JSONArray jsonArray) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray jsonArrayTmp = new JSONArray();
		String flag = "0";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			String id =jsonObj.getString("id");
			String nextName =jsonObj.getString("nextName");
			for (int j = 0; j < jsonArrayTmp.size(); j++) {
				JSONObject jsonObj2 = jsonArrayTmp.getJSONObject(j);
				String id2 =jsonObj2.getString("id");
				String nextName2 =jsonObj2.getString("nextName");
				if (StringUtil.isNotEmpty(id)&&id.equals(id2)&&StringUtil.isNotEmpty(nextName)&&nextName.equals(nextName2)){
					flag = "1";
				}
			}
			if ("0".equals(flag)){
				jsonArrayTmp.add(jsonObj);

			}else {
				flag = "0";
			}
		}

		return jsonArrayTmp;

	}
	/**
	 * 
	 * 方法描述： 获取下一个节点
	 * 
	 * @param jsonArray
	 * @param map
	 * @return
	 * @throws Exception
	 *             JSONObject
	 * @author zhs
	 * @date 2017-12-12 下午2:21:35
	 */
	private JSONObject getNextNode(JSONArray jsonArray, Map<String, Object> map) throws Exception {
		JSONObject jsonObject = new JSONObject();
		Map<String, String> pmsBizMap = getUserPmsBizsMap(request);
		// 当前节点
		JSONObject jsonObj = (JSONObject) map.get("curJsonObj");
		String id = jsonObj.get("id").toString();
		if("kind_table_val".equals(id)){
			System.out.println("123");
		}
		// 根据当前节点找下一个节点
		String[] nextNameArray = jsonObj.getString("nextName").split(",");
		for (int i = 0; i < nextNameArray.length; i++) {
			String nextName = nextNameArray[i];
			JSONObject nextJsonObj = (JSONObject) map.get(nextName);
			if (nextJsonObj==null||"end".equals(nextJsonObj.getString("type"))) {// 结束
				jsonArray.add(nextJsonObj);
				//break;
			} else if ((!"fork".equals(jsonObj.getString("type")) && "decision".equals(nextJsonObj.getString("type")))
					|| "decision".equals(jsonObj.getString("type"))) {// 下一个节点是分支节点
				// 权限处理
				String[] decisionNodeNextNameArray = nextJsonObj.getString("nextName").split(",");
				// 按照当前操作员的权限排序
				List<String> noPmsBizNodeList = new ArrayList<String>();
				map.put("decisionNodeNextNameLen", decisionNodeNextNameArray.length);
				for (String tempName : decisionNodeNextNameArray) {
					if (pmsBizMap.containsKey(tempName)) {
						jsonArray.remove(jsonObj);// 移除原来的当前节点
						jsonObj.put("nextName", tempName);
						jsonArray.add(jsonObj);// 添加新的当前节点
						jsonArray.add(map.get(tempName));// 添加下一个节点
						jsonObj = (JSONObject) map.get(tempName);
						map.put("curJsonObj", jsonObj);
						getNextNode(jsonArray, map);
						jsonObj = (JSONObject) map.get("curJsonObj");
					} else {
						noPmsBizNodeList.add(tempName);
					}
				}
				for (String tempName : noPmsBizNodeList) {
					jsonArray.remove(jsonObj);// 移除原来的当前节点
					jsonObj.put("nextName", tempName);
					jsonArray.add(jsonObj);// 添加新的当前节点
					jsonArray.add(map.get(tempName));// 添加下一个节点
					jsonObj = (JSONObject) map.get(tempName);
					map.put("curJsonObj", jsonObj);
					getNextNode(jsonArray, map);
				}
			} else if ("fork".equals(jsonObj.getString("type")) && "decision".equals(nextJsonObj.getString("type"))) {
				// 权限处理
				String[] decisionNodeNextNameArray = nextJsonObj.getString("nextName").split(",");
				// 按照当前操作员的权限排序
				List<String> noPmsBizNodeList = new ArrayList<String>();
				map.put("decisionNodeNextNameLen", decisionNodeNextNameArray.length);
				boolean replaceFlag = false;
				for (String tempName : decisionNodeNextNameArray) {
					String tempNameTmp = tempName;
					if (pmsBizMap.containsKey(tempName)) {
						if (!replaceFlag) {
							tempName = jsonObj.getString("nextName").replace(nextName, tempName);
							JSONObject curJsonObj = (JSONObject) map.get("curJsonObj");
							jsonArray.remove(curJsonObj);
							String curNextName = curJsonObj.getString("nextName");
							curJsonObj.put("nextName", curNextName.replace(curNextName, tempNameTmp));
							map.put("curJsonObj", curJsonObj);
							jsonArray.add(curJsonObj);// 添加新的当前节点
							replaceFlag = true;
						}
						jsonArray.remove(jsonObj);// 移除原来的当前节点
						jsonObj.put("nextName", tempName);
						jsonArray.add(jsonObj);// 添加新的当前节点
						jsonArray.add(map.get(tempNameTmp));// 添加下一个节点
						jsonObj = (JSONObject) map.get(tempNameTmp);
						map.put("curJsonObj", jsonObj);
						getNextNode(jsonArray, map);
						jsonObj = (JSONObject) map.get("curJsonObj");
					} else {
						noPmsBizNodeList.add(tempName);
					}
				}
				for (String tempName : noPmsBizNodeList) {
					String tempNameTmp = tempName;
					if (!replaceFlag) {
						tempName = jsonObj.getString("nextName").replace(nextName, tempName);
						JSONObject curJsonObj = (JSONObject) map.get("curJsonObj");
						jsonArray.remove(curJsonObj);
						String curNextName = curJsonObj.getString("nextName");
						curJsonObj.put("nextName", curNextName.replace(curNextName, tempNameTmp));
						map.put("curJsonObj", curJsonObj);
						jsonArray.add(curJsonObj);// 添加新的当前节点
						replaceFlag = true;
					}
					jsonArray.remove(jsonObj);// 移除原来的当前节点
					jsonObj.put("nextName", tempName);
					jsonArray.add(jsonObj);// 添加新的当前节点
					jsonArray.add(map.get(tempNameTmp));// 添加下一个节点
					jsonObj = (JSONObject) map.get(tempNameTmp);
					map.put("curJsonObj", jsonObj);
					getNextNode(jsonArray, map);
				}
			} else if ("join".equals(nextJsonObj.getString("type"))) {// 聚合
				int len = (int) map.get("decisionNodeNextNameLen") - 1;
				if (len != 0) {
					map.put("decisionNodeNextNameLen", len);
				} else {
					jsonArray.remove(jsonObj);
					jsonObj.put("nextName", nextJsonObj.getString("nextName"));
					jsonArray.add(jsonObj);
					jsonArray.add(map.get(nextJsonObj.getString("nextName")));// 添加下一个节点
					map.put("curJsonObj", map.get(nextJsonObj.getString("nextName")));
					getNextNode(jsonArray, map);
				}
			} else if ("fork".equals(jsonObj.getString("type"))) {// 判定节点
				jsonArray.add(nextJsonObj);
				map.put("curJsonObj", nextJsonObj);
				//if (i == nextNameArray.length - 1) {
					getNextNode(jsonArray, map);
			//	}
			} else {
				jsonArray.add(nextJsonObj);
				map.put("curJsonObj", nextJsonObj);
				getNextNode(jsonArray, map);
			}
		}
		return jsonObject;
	}

	/**
	 * 
	 * 方法描述： 处理流程中的业务变量
	 * 
	 * @param jsonArray
	 * @param appNo
	 * @return
	 * @throws Exception
	 *             JSONArray
	 * @author zhs
	 * @date 2017-9-5 下午2:43:52
	 */
	private Map<String,Object> dealBussFlowAppValue(JSONArray jsonArray, String appNo) throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		// 处理初始节点没有值的情况
		for (int j = 0; j < jsonArray.size(); j++) {
			// 暂时处理appValue的value项空值问题
			if (jsonArray.getJSONObject(j).get("appValue") != null) {
				JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(j).get("appValue");
				if (jsonObject.get("replyAmt") instanceof JSONNull || jsonObject.get("replyAmt") == null
						|| "".equals(String.valueOf(jsonObject.get("replyAmt")))) {
					jsonObject.put("replyAmt", "0.00");
				}
				if (jsonObject.get("appAmt") instanceof JSONNull || jsonObject.get("appAmt") == null
						|| "".equals(String.valueOf(jsonObject.get("appAmt")))) {
					jsonObject.put("appAmt", "0.00");
				}
				if (jsonObject.get("pactAmt") instanceof JSONNull || jsonObject.get("pactAmt") == null
						|| "".equals(String.valueOf(jsonObject.get("pactAmt")))) {
					jsonObject.put("pactAmt", "0.00");
				}
				if (jsonObject.get("fincRate") instanceof JSONNull || jsonObject.get("fincRate") == null
						|| "".equals(String.valueOf(jsonObject.get("fincRate")))) {
					jsonObject.put("fincRate", "0.00");
				}
				if (jsonObject.get("replyTerm") instanceof JSONNull || jsonObject.get("replyTerm") == null) {
					jsonObject.put("replyTerm", "");
				}
				if (jsonObject.get("approvalOpinion") instanceof JSONNull
						|| jsonObject.get("approvalOpinion") == null) {
					jsonObject.put("approvalOpinion", "");
				}
				if (jsonObject.get("opinionType") instanceof JSONNull || jsonObject.get("opinionType") == null
						|| "".equals(String.valueOf(jsonObject.get("opinionType")))) {
					jsonObject.put("opinionType", "1");
				}
				if (jsonObject.get("fiveclass") instanceof JSONNull || jsonObject.get("fiveclass") == null
						|| "".equals(String.valueOf(jsonObject.get("fiveclass")))) {
					jsonObject.put("fiveclass", "1");
				}
				if (jsonObject.get("approvalGrade") instanceof JSONNull || jsonObject.get("approvalGrade") == null
						|| "".equals(String.valueOf(jsonObject.get("approvalGrade")))) {
					jsonObject.put("approvalGrade", "0");
				}
				if (jsonObject.get("replyFincRate") instanceof JSONNull || jsonObject.get("replyFincRate") == null
						|| "".equals(String.valueOf(jsonObject.get("replyFincRate")))) {
					jsonObject.put("replyFincRate", "0");
				}
				if (jsonObject.get("riskLevel") instanceof JSONNull || jsonObject.get("riskLevel") == null
						|| "".equals(jsonObject.get("riskLevel").toString())) {
					jsonObject.put("riskLevel", "0");
				}
				if (jsonObject.get("receAmt") instanceof JSONNull || jsonObject.get("receAmt") == null
						|| "".equals(jsonObject.get("receAmt").toString())) {
					jsonObject.put("receAmt", "0");
				}
				if (jsonObject.get("creditSum") instanceof JSONNull || jsonObject.get("creditSum") == null
						|| "".equals(jsonObject.get("creditSum").toString())) {
					jsonObject.put("creditSum", "0");
				}
				if (jsonObject.get("receBal") instanceof JSONNull || jsonObject.get("receBal") == null
						|| "".equals(jsonObject.get("receBal").toString())) {
					jsonObject.put("receBal", "0");
				}
				if (jsonObject.get("tranAmt") instanceof JSONNull || jsonObject.get("tranAmt") == null
						|| "".equals(jsonObject.get("tranAmt").toString())) {
					jsonObject.put("tranAmt", "0");
				}
				if (jsonObject.get("rebateAmt") instanceof JSONNull || jsonObject.get("rebateAmt") == null
						|| "".equals(jsonObject.get("rebateAmt").toString())) {
					jsonObject.put("rebateAmt", "0");
				}
				if (jsonObject.get("rebateRepayAmt") instanceof JSONNull || jsonObject.get("rebateRepayAmt") == null
						|| "".equals(jsonObject.get("rebateRepayAmt").toString())) {
					jsonObject.put("rebateRepayAmt", "0");
				}
				if (jsonObject.get("rebateRepayInt") instanceof JSONNull || jsonObject.get("rebateRepayInt") == null
						|| "".equals(jsonObject.get("rebateRepayInt").toString())) {
					jsonObject.put("rebateRepayInt", "0");
				}
				if (jsonObject.get("rebateRecesDes") instanceof JSONNull || jsonObject.get("rebateRecesDes") == null
						|| "".equals(jsonObject.get("rebateRecesDes").toString())) {
					jsonObject.put("rebateRecesDes", "");
				}
				if (jsonObject.get("disputedInitiatorName") instanceof JSONNull
						|| jsonObject.get("disputedInitiatorName") == null) {
					jsonObject.put("disputedInitiatorName", "");
				}
				if (jsonObject.get("remark") instanceof JSONNull || jsonObject.get("remark") == null
						|| "".equals(String.valueOf(jsonObject.get("remark")))) {
					jsonObject.put("remark", "1");
				}
				if (jsonObject.get("disputedDetail") instanceof JSONNull || jsonObject.get("disputedDetail") == null) {
					jsonObject.put("disputedDetail", "1");
				}
				if (jsonObject.get("adjustedReason") instanceof JSONNull || jsonObject.get("adjustedReason") == null) {
					jsonObject.put("adjustedReason", "");
				}
				if (jsonObject.get("adjustedBasis") instanceof JSONNull || jsonObject.get("adjustedBasis") == null) {
					jsonObject.put("adjustedBasis", "");
				}
				if (jsonObject.get("adjustedWorth") instanceof JSONNull || jsonObject.get("adjustedWorth") == null
						|| "".equals(String.valueOf(jsonObject.get("adjustedWorth")))) {
					jsonObject.put("adjustedWorth", "0");
				}
				if (jsonObject.get("accruedInterest") instanceof JSONNull || jsonObject.get("accruedInterest") == null
						|| "".equals(String.valueOf(jsonObject.get("accruedInterest")))) {
					jsonObject.put("accruedInterest", "0");
				}
				if (jsonObject.get("fincPrepayBal") instanceof JSONNull || jsonObject.get("fincPrepayBal") == null
						|| "".equals(String.valueOf(jsonObject.get("fincPrepayBal")))) {
					jsonObject.put("fincPrepayBal", "0");
				}
				if (jsonObject.get("creditTerm") instanceof JSONNull || jsonObject.get("creditTerm") == null) {
					jsonObject.put("creditTerm", "");
				}
				if (jsonObject.get("isCeilingLoop") instanceof JSONNull || jsonObject.get("isCeilingLoop") == null) {
					jsonObject.put("isCeilingLoop", "");
				}
				jsonArray.getJSONObject(j).put("appValue", jsonObject);
			}
		}
		resMap.put("jsonArray",jsonArray);
		return resMap;
	}
	private Map<String,Object> dealFlowBussVariable(JSONArray jsonArray, String appNo,String approveType) throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		// 处理初始节点没有值的情况
		String termType="";
		String rateType = "";
		for (int j = 0; j < jsonArray.size(); j++) {
			// 暂时处理appValue的value项空值问题
			if (jsonArray.getJSONObject(j).get("appValue") != null) {
				JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(j).get("appValue");
				if (jsonObject.get("termType") instanceof JSONNull || jsonObject.get("termType") == null
						|| "".equals(String.valueOf(jsonObject.get("termType")))) {
					termType = jsonObject.get("termType").toString();
				}
				if (jsonObject.get("replyAmt") instanceof JSONNull || jsonObject.get("replyAmt") == null
						|| "".equals(String.valueOf(jsonObject.get("replyAmt")))) {
					jsonObject.put("replyAmt", "0.00");
				}
				if (jsonObject.get("appAmt") instanceof JSONNull || jsonObject.get("appAmt") == null
						|| "".equals(String.valueOf(jsonObject.get("appAmt")))) {
					jsonObject.put("appAmt", "0.00");
				}
				if (jsonObject.get("pactAmt") instanceof JSONNull || jsonObject.get("pactAmt") == null
						|| "".equals(String.valueOf(jsonObject.get("pactAmt")))) {
					jsonObject.put("pactAmt", "0.00");
				}
				if (jsonObject.get("fincRate") instanceof JSONNull || jsonObject.get("fincRate") == null
						|| "".equals(String.valueOf(jsonObject.get("fincRate")))) {
					jsonObject.put("fincRate", "0.00");
				}


				if (jsonObject.get("opinionType") instanceof JSONNull || jsonObject.get("opinionType") == null
						|| "".equals(String.valueOf(jsonObject.get("opinionType")))) {
					jsonObject.put("opinionType", "1");
				}
				if (jsonObject.get("fiveclass") instanceof JSONNull || jsonObject.get("fiveclass") == null
						|| "".equals(String.valueOf(jsonObject.get("fiveclass")))) {
					jsonObject.put("fiveclass", "1");
				}
				if (jsonObject.get("approvalGrade") instanceof JSONNull || jsonObject.get("approvalGrade") == null
						|| "".equals(String.valueOf(jsonObject.get("approvalGrade")))) {
					jsonObject.put("approvalGrade", "0");
				}
				if (jsonObject.get("replyFincRate") instanceof JSONNull || jsonObject.get("replyFincRate") == null
						|| "".equals(String.valueOf(jsonObject.get("replyFincRate")))) {
					jsonObject.put("replyFincRate", "0");
				}
				if (jsonObject.get("riskLevel") instanceof JSONNull || jsonObject.get("riskLevel") == null
						|| "".equals(jsonObject.get("riskLevel").toString())) {
					jsonObject.put("riskLevel", "0");
				}
				if (jsonObject.get("receAmt") instanceof JSONNull || jsonObject.get("receAmt") == null
						|| "".equals(jsonObject.get("receAmt").toString())) {
					jsonObject.put("receAmt", "0");
				}
				if (jsonObject.get("creditSum") instanceof JSONNull || jsonObject.get("creditSum") == null
						|| "".equals(jsonObject.get("creditSum").toString())) {
					jsonObject.put("creditSum", "0");
				}
				if (jsonObject.get("receBal") instanceof JSONNull || jsonObject.get("receBal") == null
						|| "".equals(jsonObject.get("receBal").toString())) {
					jsonObject.put("receBal", "0");
				}
				if (jsonObject.get("tranAmt") instanceof JSONNull || jsonObject.get("tranAmt") == null
						|| "".equals(jsonObject.get("tranAmt").toString())) {
					jsonObject.put("tranAmt", "0");
				}
				if (jsonObject.get("rebateAmt") instanceof JSONNull || jsonObject.get("rebateAmt") == null
						|| "".equals(jsonObject.get("rebateAmt").toString())) {
					jsonObject.put("rebateAmt", "0");
				}
				if (jsonObject.get("rebateRepayAmt") instanceof JSONNull || jsonObject.get("rebateRepayAmt") == null
						|| "".equals(jsonObject.get("rebateRepayAmt").toString())) {
					jsonObject.put("rebateRepayAmt", "0");
				}
				if (jsonObject.get("rebateRepayInt") instanceof JSONNull || jsonObject.get("rebateRepayInt") == null
						|| "".equals(jsonObject.get("rebateRepayInt").toString())) {
					jsonObject.put("rebateRepayInt", "0");
				}


				if (jsonObject.get("remark") instanceof JSONNull || jsonObject.get("remark") == null
						|| "".equals(String.valueOf(jsonObject.get("remark")))) {
					jsonObject.put("remark", "1");
				}
				if (jsonObject.get("disputedDetail") instanceof JSONNull || jsonObject.get("disputedDetail") == null) {
					jsonObject.put("disputedDetail", "1");
				}


				if (jsonObject.get("adjustedWorth") instanceof JSONNull || jsonObject.get("adjustedWorth") == null
						|| "".equals(String.valueOf(jsonObject.get("adjustedWorth")))) {
					jsonObject.put("adjustedWorth", "0");
				}
				if (jsonObject.get("accruedInterest") instanceof JSONNull || jsonObject.get("accruedInterest") == null
						|| "".equals(String.valueOf(jsonObject.get("accruedInterest")))) {
					jsonObject.put("accruedInterest", "0");
				}
				if (jsonObject.get("fincPrepayBal") instanceof JSONNull || jsonObject.get("fincPrepayBal") == null
						|| "".equals(String.valueOf(jsonObject.get("fincPrepayBal")))) {
					jsonObject.put("fincPrepayBal", "0");
				}
				if("primary_apply_approval".equals(approveType)){//业务初选表单类型
					if (StringUtil.isEmpty((String) jsonObject.get("rateType"))) {
					} else {
						rateType = jsonObject.get("rateType").toString();
					}
					MfBusApply mfBusApply = new  MfBusApply();
					mfBusApply.setPrimaryAppId(appNo);
					mfBusApply = appInterfaceFeign.getByPrimaryAppId(mfBusApply);
					MfBusAppKind mfBusAppKind = new MfBusAppKind();
					mfBusAppKind.setAppId(mfBusApply.getAppId());
					mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
					jsonObject.put("fincRate", MathExtend.showRateMethod(rateType, jsonObject.get("fincRate").toString(), mfBusAppKind.getYearDays(), Integer.valueOf(mfBusAppKind.getRateDecimalDigits())));

				}else if ("apply_approval".equals(approveType)) {//业务审批表单类型
					if (StringUtil.isEmpty((String) jsonObject.get("rateType"))) {
					} else {
						rateType = jsonObject.get("rateType").toString();
					}

					MfBusAppKind mfBusAppKind = new MfBusAppKind();
					mfBusAppKind.setAppId(appNo);
					mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
					jsonObject.put("fincRate", MathExtend.showRateMethod(rateType, jsonObject.get("fincRate").toString(), mfBusAppKind.getYearDays(), Integer.valueOf(mfBusAppKind.getRateDecimalDigits())));
					jsonObject.put("replyFincRate",MathExtend.showRateMethod(rateType, jsonObject.get("replyFincRate").toString(), mfBusAppKind.getYearDays(), Integer.valueOf(mfBusAppKind.getRateDecimalDigits())));
				} else if("risk_dispose_approval".equals(approveType) || "risk_dispose_approval".equals(approveType)){//风险处置
					CodeUtils cu = new CodeUtils();
					Map<String,String> map = cu.getMapByKeyName("manage_type");
					String manageType = "";
					String [] manageTypeArray = jsonObject.get("manageType").toString().split("\\|");
					for (String type : manageTypeArray) {
						if(type != null && !"".equals(type)){
							manageType = manageType + map.get(type) + "|";
						}
					}
					jsonObject.put("manageType", manageType);
				}else if("sale_apply_approval".equals(approveType) || "lease_apply_approval".equals(approveType)){
					if(jsonObject.get("transactionPrice") instanceof JSONNull || jsonObject.get("transactionPrice") == null){
						jsonObject.put("transactionPrice", "未登记");
					}
				}else {
				}
				jsonArray.getJSONObject(j).put("appValue", jsonObject);
			}
		}
		resMap.put("jsonArray",jsonArray);
		resMap.put("termType",termType);
		resMap.put("rateType",rateType);
		return resMap;
	}

	private JSONArray getApprovalNodeAttachment(JSONArray jsonArray, String appNo) throws Exception {
		// 审批状态
		String state = "";
		// 遍历每个完成的节点
		for (int i = 0; i < jsonArray.size(); i++) {
			if ("start".equals(jsonArray.getJSONObject(i).getString("type")) || "end".equals(jsonArray.getJSONObject(i).getString("type"))) {
				continue;
			}
			if (jsonArray.getJSONObject(i).containsKey("state")){
			   state = jsonArray.getJSONObject(i).getString("state");
			   if("completed".equals(state) || "rollback".equals(state)){
					// 根据操作员号和业务编号查询相应的附件信息
					Map<String, String> paramMap = new HashMap<String, String>();
					if(StringUtil.isNotEmpty(jsonArray.getJSONObject(i).getString("user"))&&!"null".equals(jsonArray.getJSONObject(i).getString("user"))){
						paramMap.put("regNo", jsonArray.getJSONObject(i).getString("user"));
					}
					paramMap.put("relNo", appNo);
					paramMap.put("scNo", jsonArray.getJSONObject(i).getString("id"));
					List<DocManage> list = docInterfaceFeign.getApprovalDoc(paramMap);
					if (list != null && list.size() > 0) {
						JSONArray docManageArray = JSONArray.fromObject(list);
						jsonArray.getJSONObject(i).put("attachment", docManageArray);
					}
				}
			}
		}
		return jsonArray;
	}

	/**
	 * 处理审批节点前添加判定节点后，造成后续流程节点不展示的问题。<br>
	 * 仅处理业务主流程中的业务审批判定、合同审批判定、放款审批判定。<br>
	 * 
	 * @param jsonArray
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-6-29 下午4:18:46
	 */
	@SuppressWarnings("unchecked")
	private JSONArray auditNodeControl(JSONArray jsonArray, MfBusApply mfBusApply) throws Exception {
		// 当前业务的产品信息
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(mfBusApply.getAppId());
		mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);

		String primaryApplyProcessId = mfBusAppKind.getPrimaryApplyProcessId();// 业务初选审批流程id
		String applyProcessId = mfBusAppKind.getApplyProcessId();// 业务审批流程id
		String primaryPactProcessId = mfBusAppKind.getPrimaryPactProcessId();// 合同审批流程id
		String pactProcessId = mfBusAppKind.getPactProcessId();// 合同审批流程id
		String fincProcessId = mfBusAppKind.getFincProcessId();// 放款审批流程id
		String pledgeProcessId = mfBusAppKind.getPledgeProcessId();// 抵质押审批流程id
		Double deposit = mfBusApply.getDeposit();//定金
		Double appAmt = mfBusApply.getAppAmt();//申请金额
		String channelSourceNo = mfBusApply.getChannelSourceNo();//渠道编号
		String ifWarrant=mfBusAppKind.getIfWarrant();//权证审批
		String loanKind=mfBusApply.getLoanKind();//贷款类型借新还旧
		String isCreditFlag = mfBusApply.getIsCreditFlag();//是否由授信发起业务
		String changeFlag = mfBusApply.getChangeFlag();//是否由保后申请业务
		String isRelCredit = mfBusApply.getIsRelCredit();//是否授信下
		String primary_apply_approval_nextName = null;// 业务初选审批下一节点
		String primary_apply_approval_forkName = null;// 业务初选审批判定节点

		String apply_approval_nextName = null;// 业务审批下一节点
		String apply_approval_forkName = null;// 业务审批判定节点

		String primary_contract_approval_nextName = null;// 合同审批下一节点
		String primary_contract_approval_forkName = null;// 合同审批判定节点

		String contract_approval_nextName = null;// 合同审批下一节点
		String contract_approval_forkName = null;// 合同审批判定节点

		String putout_approval_nextName = null;// 放款审批下一节点
		String putout_approval_forkName = null;// 放款审批判定节点

		String finc_approval_nextName = null;// 保理放款审批下一节点
		String finc_approval_forkName = null;// 保理放款审批判定节点

		String pledge_approval_nextName = null;// 抵质押审批下一节点
		String pledge_approval_forkName = null;// 抵质押审批判定节点

		String pay_deposit_nextName = null;// 缴纳定金下一节点
		String pay_deposit_forkName = null;// 缴纳定金判定节点
		String if_credit_nextName = null;// 是否授信发起申请下一节点
		String if_credit_forkName = null;//是否授信发起申请站判定节点
		String isQzNextName = null;// 权证审批判断下一节点
		String isQzForkName = null;// 权证审批判断定节点
		String chargConfirmNextName = null;// 借新还旧判断下一节点
		String chargConfirmForkName = null;// 借新还旧判断节点
		String changeFlagNextName1 = null;// 保后变更下一节点
		String changeFlagForkName1 = null;// 保后变更判断节点
		String changeFlagNextName2 = null;// 保后变更下一节点
		String changeFlagForkName2 = null;// 保后变更判断节点
		String changeFlagNextName3 = null;// 保后变更下一节点
		String changeFlagForkName3 = null;// 保后变更判断节点
		String isRelCreditNextName1 = null;// 是否授信下业务
		String isRelCreditForkName1 = null;//是否授信下业务判定节点
		String isRelCreditNextName2= null;// 是否授信下业务
		String isRelCreditForkName2 = null;//是否授信下业务判定节点

		Iterator<JSONObject> iter = jsonArray.iterator();
		while (iter.hasNext()) {
			JSONObject jsonObj = iter.next();
			String nextName = jsonObj.get("nextName").toString();// 下一节点
			String id = jsonObj.get("id").toString();

			if ("fork".equals(jsonObj.get("type"))) {// 判定节点
				if (nextName.indexOf(WKF_NODE.primary_apply_approval.getNodeNo()) >= 0) {// 业务初选审批判定节点 && 业务初选审批流程未启用
					iter.remove();// 移除此判定节点
					primary_apply_approval_nextName = nextName.replace(WKF_NODE.primary_apply_approval.getNodeNo(), "").replace(",", "");
					primary_apply_approval_forkName = id;

				}else if (id.indexOf(WKF_NODE.change_flag_1.getNodeNo()) >= 0) {
					iter.remove();
					changeFlagNextName1 = nextName.replace(WKF_NODE.apply_approval.getNodeNo(), "").replace(",", "");
					changeFlagForkName1 = id;
				}else if (id.indexOf(WKF_NODE.change_flag_2.getNodeNo()) >= 0) {
					iter.remove();
					changeFlagNextName2 = nextName.replace(WKF_NODE.contract_sign.getNodeNo(), "").replace(",", "");
					changeFlagForkName2 = id;
				}else if (id.indexOf(WKF_NODE.change_flag_3.getNodeNo()) >= 0) {
					iter.remove();
					changeFlagNextName3 = nextName.replace(WKF_NODE.change_flag_end.getNodeNo(), "").replace(",", "");
					changeFlagForkName3 = id;
				}else if (id.indexOf(WKF_NODE.is_rel_credit.getNodeNo()) >= 0) {
					iter.remove();
//					isRelCreditNextName1 = nextName.replace(WKF_NODE.pledge_reg.getNodeNo(), "").replace(",", "");
//					isRelCreditForkName1 = id;
				}else if (id.indexOf(WKF_NODE.is_rel_credit1.getNodeNo()) >= 0) {
					iter.remove();
//					isRelCreditNextName2 = nextName.replace(WKF_NODE.contract_sign.getNodeNo(), "").replace(",", "");
//					isRelCreditForkName2 = id;
				} else if (nextName.indexOf(WKF_NODE.apply_approval.getNodeNo()) >= 0) {// 业务审批判定节点&&业务审批流程未启用
					iter.remove();// 移除此判定节点
					apply_approval_nextName = nextName.replace(WKF_NODE.apply_approval.getNodeNo(), "").replace(",", "");
					apply_approval_forkName = id;
				} else if (nextName.indexOf(WKF_NODE.primary_contract_approval.getNodeNo()) >= 0) {// 合同审批判定节点&& 合同审批流程未启用
					iter.remove();
					primary_contract_approval_nextName = nextName.replace(WKF_NODE.primary_contract_approval.getNodeNo(), "").replace(",", "");
					primary_contract_approval_forkName = id;
				} else if (nextName.indexOf(WKF_NODE.contract_approval.getNodeNo()) >= 0) {// 合同审批判定节点&& 合同审批流程未启用
					iter.remove();
					contract_approval_nextName = nextName.replace(WKF_NODE.contract_approval.getNodeNo(), "").replace(",", "");
					contract_approval_forkName = id;
				} else if (nextName.indexOf(WKF_NODE.putout_approval.getNodeNo()) >= 0) {// 放款审批判定节点&&放款审批流程未启用
					iter.remove();
					putout_approval_nextName = nextName.replace(WKF_NODE.putout_approval.getNodeNo(), "").replace(",", "");
					putout_approval_forkName = id;
				} else if (nextName.indexOf(WKF_NODE.finc_approval.getNodeNo()) >= 0) {//保理融资放款审批流程判定节点&&放款审批流程未启用
					iter.remove();
					finc_approval_nextName = nextName.replace(WKF_NODE.finc_approval.getNodeNo(), "").replace(",", "");
					finc_approval_forkName = id;
				} else if (nextName.indexOf(WKF_NODE.pay_deposit.getNodeNo()) >= 0) {//缴纳定金节点
					iter.remove();
					pay_deposit_nextName = nextName.replace(WKF_NODE.pay_deposit.getNodeNo(), "").replace(",", "");
					pay_deposit_forkName = id;
				}else if (nextName.indexOf(WKF_NODE.certidInfo_appr.getNodeNo()) >= 0) {// 权证审批判定节点&& 权证审批流程未启用
					iter.remove();
					isQzNextName = nextName.replace(WKF_NODE.certidInfo_appr.getNodeNo(), "").replace(",", "");
					isQzForkName = id;
				}else if (nextName.indexOf(WKF_NODE.charg_confirmation.getNodeNo()) >= 0) {// 借新还旧判断节点是否启用收费确认
					iter.remove();
					chargConfirmNextName = nextName.replace(WKF_NODE.charg_confirmation.getNodeNo(), "").replace(",", "");
					chargConfirmForkName = id;
				}else {
				}
			}
		}

		iter = jsonArray.iterator();
		while (iter.hasNext()) {
			JSONObject jsonObj = iter.next();
			String nextName = jsonObj.get("nextName").toString();// 下一节点
			if (nextName.equals(primary_apply_approval_forkName)) {// 下一节点 = 业务初选审批判定节点
				if (StringUtil.isEmpty(primaryApplyProcessId)) {// 业务初选审批未启用
					jsonObj.put("nextName", primary_apply_approval_nextName);// 指向业务初选审批的下一节点
				} else {
					jsonObj.put("nextName", WKF_NODE.primary_apply_approval.getNodeNo());// 指向业务审批
				}
			}else if (nextName.equals(apply_approval_forkName)) {// 下一节点 = 业务审批判定节点
				if (StringUtil.isEmpty(applyProcessId)) {// 业务审批未启用
					jsonObj.put("nextName", apply_approval_nextName);// 指向业务审批的下一节点
				} else {
					jsonObj.put("nextName", WKF_NODE.apply_approval.getNodeNo());// 指向业务审批
				}

			} else if(nextName.equals(primary_contract_approval_forkName)) {// 下一节点 =
				// 合同审批判定节点
				if (StringUtil.isEmpty(primaryPactProcessId)) {// 合同审批未启用
					jsonObj.put("nextName", primary_contract_approval_nextName);// 指向合同审批的下一节点
				} else {
					jsonObj.put("nextName", WKF_NODE.primary_contract_approval.getNodeNo());// 指向合同审批
				}
			}else if (nextName.equals(contract_approval_forkName)) {// 下一节点 =
				if (StringUtil.isEmpty(pactProcessId)) {// 合同审批未启用
					jsonObj.put("nextName", contract_approval_nextName);// 指向合同审批的下一节点
				} else {
					jsonObj.put("nextName", WKF_NODE.contract_approval.getNodeNo());// 指向合同审批
				}
			}else if (nextName.equals(putout_approval_forkName)) {// 下一节点 =
				// 放款审批判定节点
				if (StringUtil.isEmpty(fincProcessId)) {// 放款审批未启用
					jsonObj.put("nextName", putout_approval_nextName);// 指向放款审批的下一节点
				} else {
					jsonObj.put("nextName", WKF_NODE.putout_approval.getNodeNo());// 指向放款审批
				}
			}else if (nextName.equals(changeFlagForkName1)) {// 下一节点 =
				if (!"1".equals(changeFlag)) {// 不是保后变更
					jsonObj.put("nextName", changeFlagNextName1);
				} else {
					jsonObj.put("nextName", WKF_NODE.apply_approval.getNodeNo());
				}
			}else if (nextName.equals(changeFlagForkName2)) {// 下一节点 =
				if (!"1".equals(changeFlag)) {// 不是保后变更
					jsonObj.put("nextName", changeFlagNextName2);
				} else {
					jsonObj.put("nextName", WKF_NODE.contract_sign.getNodeNo());
				}
			}else if (nextName.equals(changeFlagForkName3)) {// 下一节点 =
				if (!"1".equals(changeFlag)) {// 不是保后变更
					jsonObj.put("nextName", changeFlagNextName3);
				} else {
					jsonObj.put("nextName", WKF_NODE.change_flag_end.getNodeNo());
				}
			}else if (nextName.equals("is_credit_flag")) {// 下一节点 =
				// 放款审批判定节点
				if (!"1".equals(isCreditFlag)) {
					if ("1".equals(isRelCredit)) {
						jsonObj.put("nextName",WKF_NODE.credit_resp.getNodeNo());
					} else {
						jsonObj.put("nextName", WKF_NODE.pledge_reg.getNodeNo());
					}
				} else {
					jsonObj.put("nextName", WKF_NODE.contract_sign.getNodeNo());// 指向放款审批
				}
			}else if (nextName.indexOf("is_rel_credit")>0) {// 下一节点 =
				if ("1".equals(isRelCredit)) {
					jsonObj.put("nextName",WKF_NODE.credit_resp.getNodeNo());
				} else {
					jsonObj.put("nextName", WKF_NODE.pledge_reg.getNodeNo());
				}
			}else if (nextName.equals("is1_rel_credit")) {// 下一节点 =
				if ("1".equals(isRelCredit)) {
					jsonObj.put("nextName", WKF_NODE.contract_sign.getNodeNo());
				} else {
					if (!"1".equals(changeFlag)) {// 不是保后变更
						jsonObj.put("nextName", WKF_NODE.batch_printing.getNodeNo());
					} else {
						jsonObj.put("nextName", WKF_NODE.contract_sign.getNodeNo());
					}
				}
			}
			else if (nextName.equals(finc_approval_forkName)) {// 下一节点 =
				// 保理放款审批判定节点
				if (StringUtil.isEmpty(fincProcessId)) {// 放款审批未启用
					jsonObj.put("nextName", finc_approval_nextName);// 指向放款审批的下一节点
				} else {
					jsonObj.put("nextName", WKF_NODE.finc_approval.getNodeNo());// 指向放款审批
				}
			}else if (nextName.equals(pledge_approval_forkName)) {// 下一节点 =
				// 抵质押审批判定节点
				if (StringUtil.isEmpty(pledgeProcessId)) {// 抵质押审批未启用
					jsonObj.put("nextName", pledge_approval_nextName);// 指向抵质押审批的下一节点
				}
			}else if (nextName.equals(pay_deposit_forkName)) {// 下一节点 =
				//缴纳定金判定节点
				if (deposit==0) {//定金为0
					jsonObj.put("nextName", pay_deposit_nextName);// 指向缴纳定金的下一节点
				} else {
					jsonObj.put("nextName", WKF_NODE.pay_deposit.getNodeNo());// 指向缴纳定金
				}
			}else if (nextName.indexOf("service_station") >= 0||nextName.indexOf("service_station") >= 0) {//是否服务站
					MfCusCustomer mfCusCustomerQ = new MfCusCustomer();
					mfCusCustomerQ.setCusNo(mfBusApply.getChannelSourceNo());
					mfCusCustomerQ =cusInterfaceFeign.getMfCusCustomerById(mfCusCustomerQ);
					if (mfCusCustomerQ!=null){
						if("402".equals(mfCusCustomerQ.getCusType())){
							if (appAmt>50000){
								jsonObj.put("nextName",WKF_NODE.primary_apply_approval.getNodeNo());// 指向缴纳定金的下一节点
							}else{
								if (StringUtil.isEmpty(applyProcessId)) {// 业务审批未启用
									jsonObj.put("nextName", apply_approval_nextName);// 指向业务审批的下一节点
								} else {
									jsonObj.put("nextName", WKF_NODE.apply_approval.getNodeNo());// 指向业务审批
								}
							}
						}else{
							jsonObj.put("nextName",WKF_NODE.primary_apply_approval.getNodeNo());// 指向缴纳定金的下一节点
						}
					}	else {
						jsonObj.put("nextName",WKF_NODE.primary_apply_approval.getNodeNo());// 指向缴纳定金的下一节点
					}

			}else if(nextName.equals(isQzForkName)) {// 下一节点 =
				// 权证审批判定节点
				if (StringUtil.isEmpty(ifWarrant)) {// 权证审批未启用
					jsonObj.put("nextName", isQzNextName);// 指向权证审批的下一节点
				} else {
					jsonObj.put("nextName", WKF_NODE.certidInfo_appr.getNodeNo());// 指向权证审批
				}
			}
			else if(nextName.equals(isQzForkName)) {// 下一节点 =
				// 权证审批判定节点
				if (StringUtil.isEmpty(ifWarrant)) {// 权证审批未启用
					jsonObj.put("nextName", isQzNextName);// 指向权证审批的下一节点
				} else {
					jsonObj.put("nextName", WKF_NODE.certidInfo_appr.getNodeNo());// 指向权证审批
				}
			}else if(nextName.equals(chargConfirmForkName)) {// 下一节点 =
				// 借新还旧判定节点
				if (StringUtil.isEmpty(loanKind) || !"3".equals(loanKind)) {// 借新还旧不启用
					jsonObj.put("nextName", chargConfirmNextName);// 指向借新还旧的下一节点
				} else {
					jsonObj.put("nextName", WKF_NODE.charg_confirmation.getNodeNo());// 指向收费确认
				}
			}else {
			}
		}

		return jsonArray;
	}

	/**
	 * 处理担保登记前添加判定节点后，造成后续流程节点不展示的问题。<br>
	 * 此判定节点无法在一开始时决定，担保登记节点永远展示。<br>
	 *
	 * @param jsonArray
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-6-29 下午4:18:46
	 */
	@SuppressWarnings("unchecked")
	private JSONArray storageConfirmNodeControl(JSONArray jsonArray, MfBusApply mfBusApply) throws Exception {
		String storage_confirm_forkName = null;// 担保登记判定节点

		Iterator<JSONObject> iter = jsonArray.iterator();
		while (iter.hasNext()) {
			JSONObject jsonObj = iter.next();
			String nextName = jsonObj.get("nextName").toString();// 下一节点
			String id = jsonObj.get("id").toString();

			if ("fork".equals(jsonObj.get("type"))) {// 判定节点
				if (nextName.indexOf(WKF_NODE.storage_confirm.getNodeNo()) >= 0) {// 担保登记判定节点
					iter.remove();// 移除此判定节点
					storage_confirm_forkName = id;
				}
			}
		}

		iter = jsonArray.iterator();
		while (iter.hasNext()) {
			JSONObject jsonObj = iter.next();
			String nextName = jsonObj.get("nextName").toString();// 下一节点

			if (nextName.equals(storage_confirm_forkName)) {// 下一节点 = 担保登记判定节点
				jsonObj.put("nextName", WKF_NODE.storage_confirm.getNodeNo());// 指向担保登记
			}
		}

		return jsonArray;
	}
	/**
	 * 处理权证登记前添加判定节点后，造成后续流程节点不展示的问题。<br>
	 * 此判定节点无法在一开始时决定，权证登记节点永远展示。<br>
	 *
	 * @param jsonArray
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-6-29 下午4:18:46
	 */
	@SuppressWarnings("unchecked")
	private JSONArray certiInfoDecisionHandler(JSONArray jsonArray, MfBusApply mfBusApply) throws Exception {
		String storage_confirm_forkName = null;// 权证判定节点
		String storage_confirm_forkName1= null;// 权证判定节点

		Iterator<JSONObject> iter = jsonArray.iterator();
		while (iter.hasNext()) {
			JSONObject jsonObj = iter.next();
			String nextName = jsonObj.get("nextName").toString();// 下一节点
			String id = jsonObj.get("id").toString();

			if ("fork".equals(jsonObj.get("type"))) {// 判定节点
				if (nextName.indexOf(WKF_NODE.certidInfo_reg.getNodeNo()) >= 0) {// 权证登记判定节点
					iter.remove();// 移除此判定节点
					storage_confirm_forkName = id;
				}
			}
		}

		iter = jsonArray.iterator();
		while (iter.hasNext()) {
			JSONObject jsonObj = iter.next();
			String nextName = jsonObj.get("nextName").toString();// 下一节点

			if (nextName.equals(storage_confirm_forkName)) {// 下一节点 = 权证登记判定节点
				jsonObj.put("nextName", WKF_NODE.certidInfo_reg.getNodeNo());// 指向权证登记登记
			}
		}

		return jsonArray;
	}
	/**
	 * 处理审批节点前添加判定节点后，造成后续流程节点不展示的问题。<br>
	 * 仅处理业务主流程中的业务审批判定、合同审批判定、放款审批判定。<br>
	 * 
	 * @param jsonArray
	 * @param appNo
	 * @param flowType
	 *            业务流程类型 credit-授信业务流程； extension-展期业务流程
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-6-29 下午4:18:46
	 */
	@SuppressWarnings("unchecked")
	private JSONArray cusAuditNodeControl(JSONArray jsonArray, String appNo, String flowType) throws Exception {
		String credit_approval_nextName = null;// 授信审批下一节点
		String credit_approval_forkName = null;// 授信审批判定节点
		String extension_approve_nextName = null;// 展期业务审批下一节点
		String extension_approve_forkName = null;// 展期业务审批判定节点
        String primary_credit_approval_nextName = null;// 立项初选审批下一节点
        String primary_credit_approval_forkName = null;// 立项初选审批判定节点
        String first_apply_nextName = null;// 立项初选审批下一节点
        String first_apply_forkName = null;// 立项初选审批判定节点
		String if_repeat_pact_nextName = null;//
		String if_repeat_pact_forkName = null;//
		Iterator<JSONObject> iter = jsonArray.iterator();
		while (iter.hasNext()) {
			JSONObject jsonObj = iter.next();
			String nextName = jsonObj.get("nextName").toString();// 下一节点
			String id = jsonObj.get("id").toString();

			if ("fork".equals(jsonObj.get("type"))) {// 判定节点
				if ("credit".equals(flowType)) {
					if (nextName.indexOf(WKF_NODE.credit_approval.getNodeNo()) >= 0) {// 授信审批判定节点
						// &&
						// 授信审批流程未启用
						iter.remove();// 移除此判定节点
						credit_approval_nextName = nextName.replace(WKF_NODE.credit_approval.getNodeNo(), "")
								.replace(",", "");
						credit_approval_forkName = id;
					}

				}
				if ("primaryApprove".equals(flowType)) {
					if (nextName.indexOf("credit_primary_approval") >= 0) {// 授信审批判定节点
						// &&
						// 授信审批流程未启用
						iter.remove();// 移除此判定节点
						primary_credit_approval_nextName = nextName.replace("credit_primary_approval", "").replace(",", "");
						primary_credit_approval_forkName = id;
					}
				}
				if ("collateral".equals(flowType)) {
					if (nextName.indexOf("collateral") >= 0) {// 授信审批判定节点
						// &&
						// 授信审批流程未启用
						iter.remove();// 移除此判定节点
						credit_approval_nextName = nextName.replace("collateral", "")
								.replace(",", "");
						credit_approval_forkName = id;
					}
				}
				if ("creditApprove".equals(flowType)) {
					if (nextName.indexOf(WKF_NODE.credit_pact_approval.getNodeNo()) >= 0) {// 授信审批判定节点
						// &&
						// 授信审批流程未启用
						iter.remove();// 移除此判定节点
						credit_approval_nextName = nextName.replace(WKF_NODE.credit_pact_approval.getNodeNo(), "")
								.replace(",", "");
						credit_approval_forkName = id;
					}
				}
				if ("creditPactPrimary".equals(flowType)) {
					if (nextName.indexOf(WKF_NODE.primary_credit_pact_approval.getNodeNo()) >= 0) {// 授信合同清稿审批判定节点
						// &&
						// 授信审批流程未启用
						iter.remove();// 移除此判定节点
						credit_approval_nextName = nextName.replace(WKF_NODE.primary_credit_pact_approval.getNodeNo(), "")
								.replace(",", "");
						credit_approval_forkName = id;
					}
				}
				if ("extension".equals(flowType)) {
					if (nextName.indexOf(WKF_NODE.extension_approve.getNodeNo()) >= 0) {// 展期业务审批判定节点
						// &&
						// 展期业务审批流程未启用
						iter.remove();// 移除此判定节点
						extension_approve_nextName = nextName.replace(WKF_NODE.extension_approve.getNodeNo(), "")
								.replace(",", "");
						extension_approve_forkName = id;
					}
				}
				if ("firstApply".equals(flowType)) {
					if (nextName.indexOf(WKF_NODE.first_apply.getNodeNo()) >= 0) {// 首笔业务申请审批判定节点
						iter.remove();// 移除此判定节点
						first_apply_nextName = nextName.replace(WKF_NODE.first_apply.getNodeNo(), "")
								.replace(",", "");
						first_apply_forkName= id;
					}
				}
				if ("ifRepeatPact".equals(flowType)) {
					if (nextName.indexOf(WKF_NODE.protocolPrint.getNodeNo()) >= 0) {// 首笔业务申请审批判定节点
						iter.remove();// 移除此判定节点
						if_repeat_pact_nextName = nextName.replace(WKF_NODE.protocolPrint.getNodeNo(), "")
								.replace(",", "");
						if_repeat_pact_forkName= id;
					}
				}
			}
		}

		iter = jsonArray.iterator();
		while (iter.hasNext()) {
			JSONObject jsonObj = iter.next();
			String nextName = jsonObj.get("nextName").toString();// 下一节点
			if ("credit".equals(flowType)) {
				if (nextName.equals(credit_approval_forkName)) {// 下一节点 =
					// 授信审批判定节点
					if (StringUtil.isEmpty(appNo)) {// 授信审批未启用
						jsonObj.put("nextName", credit_approval_nextName);// 指向授信审批的下一节点
					} else {
						jsonObj.put("nextName", WKF_NODE.credit_approval.getNodeNo());// 指向授信审批
					}
				}
			}
			if ("primaryApprove".equals(flowType)) {
				if (nextName.equals(primary_credit_approval_forkName)) {// 下一节点 =
					// 授信审批判定节点
					if (StringUtil.isEmpty(appNo)) {// 授信审批未启用
						jsonObj.put("nextName", primary_credit_approval_nextName);// 指向授信审批的下一节点
					} else {
						jsonObj.put("nextName","credit_primary_approval");// 指向授信审批
					}
				}
			}
			if ("collateral".equals(flowType)) {
				if (nextName.equals(credit_approval_forkName)) {// 下一节点 =
					// 授信审批判定节点
					if ("100002".equals(appNo)) {// 授信审批未启用
						jsonObj.put("nextName", credit_approval_nextName);// 指向授信审批的下一节点
					} else {
						jsonObj.put("nextName", "collateral");// 指向授信审批
					}
				}
			}
			if ("creditApprove".equals(flowType)) {
				if (nextName.equals(credit_approval_forkName)) {// 下一节点 =
					// 授信审批判定节点
					if (StringUtil.isEmpty(appNo)) {// 授信审批未启用
						jsonObj.put("nextName", credit_approval_nextName);// 指向授信审批的下一节点
					} else {
						jsonObj.put("nextName", WKF_NODE.credit_pact_approval.getNodeNo());// 指向授信审批
					}
				}
			}
			if ("creditPactPrimary".equals(flowType)) {
				if (nextName.equals(credit_approval_forkName)) {// 下一节点 =
					// 授信审批判定节点
					if (StringUtil.isEmpty(appNo)) {// 授信审批未启用
						jsonObj.put("nextName", credit_approval_nextName);// 指向授信审批的下一节点
					} else {
						jsonObj.put("nextName", WKF_NODE.primary_credit_pact_approval.getNodeNo());// 指向授信审批
					}
				}
			}
			if ("firstApply".equals(flowType)) {
				if (nextName.equals(first_apply_forkName)) {// 下一节点 =
					// 授信审批判定节点
					if (!"1".equals(appNo)) {// 授信审批未启用
						jsonObj.put("nextName", first_apply_nextName);// 指向授信审批的下一节点
					} else {
						jsonObj.put("nextName", WKF_NODE.first_apply.getNodeNo());// 指向授信审批
					}
				}
			}
			if ("extension".equals(flowType)) {
				if (nextName.equals(extension_approve_forkName)) {// 下一节点 =
					// 展期业务审批判定节点
					if (StringUtil.isEmpty(appNo)) {// 展期业务审批未启用
						jsonObj.put("nextName", extension_approve_nextName);// 指向展期业务审批的下一节点
					} else {
						jsonObj.put("nextName", WKF_NODE.extension_approve.getNodeNo());// 指向展期业务审批
					}
				}
			}
			if ("ifRepeatPact".equals(flowType)) {
				if (nextName.equals(if_repeat_pact_forkName)) {// 下一节点 =
					// 授信审批判定节点
					if ("1".equals(appNo)) {// 授信审批未启用
						jsonObj.put("nextName",WKF_NODE.protocolPrint.getNodeNo() );// 指向授信审批的下一节点
					} else {
						jsonObj.put("nextName", if_repeat_pact_nextName);// 指向授信审批
					}
				}
			}
		}

		return jsonArray;
	}

	private Map<String,Object> getVerticalFlowData(String appNo,JSONArray jsonArray, String approveType,String activityType,String query) throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		FormService formService = new FormService();
		CodeUtils codeUtils = new CodeUtils();
		String projectName = ymlConfig.getSysParams().get("sys.project.name");
		resMap.put("projectName",projectName);
		MfBusApply mfBusApply1 = appInterfaceFeign.getMfBusApplyByAppId(appNo);
		//循环处理各个审批环节的表单数据
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			Map<String, Object> appValueMap = jsonObj.getJSONObject("appValue");
			String id = jsonObj.getString("id");
			if(i<jsonArray.size()-1&&("credit_approval".equals(approveType))&&("credit_supplement".equals(id)||"dep_manager".equals(id))){
				JSONObject jsonObj2 = jsonArray.getJSONObject(i+1);
				appValueMap = jsonObj2.getJSONObject("appValue");
			}
			if(i<jsonArray.size()-1&&("charge-fee".equals(approveType))&&("supplement_data".equals(id))){
				JSONObject jsonObj2 = jsonArray.getJSONObject(i+1);
				appValueMap = jsonObj2.getJSONObject("appValue");
			}
			if(("charge-fee".equals(approveType))&&("fee_account_confirm".equals(id))){
				if(i<jsonArray.size()-1){
					JSONObject jsonObj2 = jsonArray.getJSONObject(i+1);
					appValueMap = jsonObj2.getJSONObject("appValue");
					appValueMap.put("accountAmt",appValueMap.get("actualReceivedAmt"));
				}else{
					appValueMap.put("accountAmt",appValueMap.get("actualReceivedAmt"));
				}
			}

			//期限类型
			String termType = "";
			if(appValueMap.containsKey("termType")){
				termType = String.valueOf(appValueMap.get("termType"));
			}
			//期限单位
			String termUnit="";
			if(StringUtil.isNotEmpty(termType)){
				Map<String, ParmDic> termTypeMap = codeUtils.getMapObjByKeyName("TERM_TYPE");
				termUnit = termTypeMap.get(termType).getRemark();
			}
			//利率类型
			String rateType = "";
			if(appValueMap.containsKey("rateType")){
				rateType = String.valueOf(appValueMap.get("rateType"));
			}
			//利率单位
			String rateUnit="";
			if(StringUtil.isNotEmpty(rateType)){
				// 利率单位格式化
				Map<String, ParmDic> termTypeMap = codeUtils.getMapObjByKeyName("RATE_TYPE");
				rateUnit = termTypeMap.get(rateType).getRemark();
			}

			// 处理五级分类审批意见格式
			List<OptionsList> opinionTypeListFiveClass=null;
			if ("fiveclass_approval".equals(approveType)) {
				String fiveclassId = "";
				if(appValueMap.containsKey("fiveclassId")){
					fiveclassId = String.valueOf(appValueMap.get("fiveclassId"));
				}
				if(StringUtil.isNotEmpty(fiveclassId)){
					// 获得审批节点信息
					TaskImpl taskAppro = wkfInterfaceFeign.getTask(fiveclassId, null);
					// 处理审批意见类型
					opinionTypeListFiveClass = codeUtils.getOpinionTypeList(activityType,taskAppro.getCouldRollback(), null);
				}
			}

			// 处理贷后检查审批意见格式
			List<OptionsList> opinionTypeListExamHis=null;
			if ("exam_approval".equals(approveType)) {
				String examHisId = "";
				if (appValueMap.containsKey("examHisId")) {
					examHisId = String.valueOf(appValueMap.get("examHisId"));
				}
				if (StringUtil.isNotEmpty(examHisId)) {
					TaskImpl taskAppro = wkfInterfaceFeign.getTask(examHisId, null);
					String couldRollback = null;
					if (taskAppro != null) {
						couldRollback = taskAppro.getCouldRollback();
					}
					// 处理审批意见类型
					opinionTypeListExamHis = new CodeUtils().getOpinionTypeList(activityType, couldRollback, null);
				}
			}
            //获取表单编号
            String formId="";
            if(StringUtil.isNotEmpty(approveType)){
				//找该审批环节的表单，找不到时，查询approveType对应的表单
				String kindNo = "base";
            	String activityName = jsonObj.getString("id").toString();
				MfKindForm mfKindForm = prdctInterfaceFeign.getMfKindForm(kindNo,activityName);
				if(mfKindForm==null){
					mfKindForm = prdctInterfaceFeign.getMfKindForm(kindNo,approveType);
				}else{
					formId = StringUtil.isNotEmpty(mfKindForm.getShowModel()) ? mfKindForm.getShowModel():mfKindForm.getShowModelBase();
					if(StringUtil.isEmpty(formId)){
						mfKindForm = prdctInterfaceFeign.getMfKindForm(kindNo,approveType);
					}
				}
				if(mfKindForm!=null){
					formId = StringUtil.isNotEmpty(mfKindForm.getShowModel()) ? mfKindForm.getShowModel():mfKindForm.getShowModelBase();
				}
			}
			if(mfBusApply1 != null && BizPubParm.BUS_MODEL_12.equals(mfBusApply1.getBusModel())){
				formId = "approvalapplyhiswkf_GCDB";
			}
            jsonArray.getJSONObject(i).put("formHtml", "");
            jsonArray.getJSONObject(i).put("formId", "");
            if(StringUtil.isNotEmpty(formId)){//如果表单编号为空
                FormData formcommon = formService.getFormData(formId);
				getObjValue(formcommon, appValueMap);
                // 期限单位格式化
                if("primary_apply_approval".equals(approveType)){
					MfBusApply mfBusApply = new  MfBusApply();
					mfBusApply.setPrimaryAppId(appNo);
					mfBusApply = appInterfaceFeign.getByPrimaryAppId(mfBusApply);
					MfBusAppKind mfBusAppKind = new MfBusAppKind();
					mfBusAppKind.setAppId(mfBusApply.getAppId());
					mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
					String fincRate = "0.00";
					if(appValueMap.containsKey("fincRate")){
						fincRate = String.valueOf(appValueMap.get("fincRate"));
					}
					fincRate= MathExtend.showRateMethod(rateType, fincRate, mfBusAppKind.getYearDays(), Integer.valueOf(mfBusAppKind.getRateDecimalDigits()));
					this.changeFormProperty(formcommon, "fincRate", "initValue", fincRate);
					this.changeFormProperty(formcommon, "fincRate", "unit", rateUnit);
					this.changeFormProperty(formcommon, "term", "unit", termUnit);
				}else if ("apply_approval".equals(approveType)) {
                	if(StringUtil.isNotEmpty(appNo)){
						 id = jsonObj.getString("id");
						if(i<jsonArray.size()-1&&"dep_manager".equals(id)){
							JSONObject jsonObj2 = jsonArray.getJSONObject(i+1);
							appValueMap = jsonObj2.getJSONObject("appValue");
						}

						MfBusAppKind mfBusAppKind = new MfBusAppKind();
						mfBusAppKind.setAppId(appNo);
						mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
						String fincRate = "0.00",replyFincRate="0.00";
						if(appValueMap.containsKey("fincRate")){
							fincRate = String.valueOf(appValueMap.get("fincRate"));
						}
						if(appValueMap.containsKey("replyFincRate")){
							replyFincRate = String.valueOf(appValueMap.get("replyFincRate"));
						}
						fincRate= MathExtend.showRateMethod(rateType, fincRate, mfBusAppKind.getYearDays(), Integer.valueOf(mfBusAppKind.getRateDecimalDigits()));
						replyFincRate= MathExtend.showRateMethod(rateType, replyFincRate, mfBusAppKind.getYearDays(), Integer.valueOf(mfBusAppKind.getRateDecimalDigits()));
						this.changeFormProperty(formcommon, "fincRate", "initValue", fincRate);
						this.changeFormProperty(formcommon, "replyFincRate", "initValue", replyFincRate);
						this.changeFormProperty(formcommon, "replyAmt", "initValue", appValueMap.get("replyAmt"));
						this.changeFormProperty(formcommon, "replyTerm", "initValue", appValueMap.get("replyTerm"));
						this.changeFormProperty(formcommon, "term", "unit", termUnit);
						this.changeFormProperty(formcommon, "replyTerm", "unit", termUnit);
						this.changeFormProperty(formcommon, "fincRate", "unit", rateUnit);
						this.changeFormProperty(formcommon, "replyFincRate", "unit", rateUnit);
					}
                } else if ("contract_approval".equals(approveType)) {
                    this.changeFormProperty(formcommon, "term", "unit", termUnit);
                } else if ("putout_approval".equals(approveType)) {
                    this.changeFormProperty(formcommon, "termMonth", "unit", termUnit);
                }else if("risk_dispose_approval".equals(approveType) || "risk_dispose_approval".equals(approveType)){//风险处置
					CodeUtils cu = new CodeUtils();
					Map<String,String> map = cu.getMapByKeyName("manage_type");
					String manageType = String.valueOf(appValueMap.get("manageType"));
					if(StringUtil.isNotEmpty(manageType)){
						String [] manageTypeArray = manageType.split("\\|");
						for (String type : manageTypeArray) {
							if(type != null && !"".equals(type)){
								manageType = manageType + map.get(type) + "|";
							}
						}
					}
					appValueMap.put("manageType", manageType);
				}else if("sale_apply_approval".equals(approveType) || "lease_apply_approval".equals(approveType)){
					String transactionPrice = String.valueOf(appValueMap.get("transactionPrice"));
                	if(StringUtil.isEmpty(transactionPrice)){
						appValueMap.put("transactionPrice", "未登记");
					}
				}else if ("charge-fee".equals(approveType)) {
					if(StringUtil.isNotEmpty(appNo)){
//						id = jsonObj.getString("id");
//						if("fee_account_confirm".equals(id)){
//							MfBusChargeFee mfBusChargeFee = new  MfBusChargeFee();
//							mfBusChargeFee.setChargeId(appNo);
//							mfBusChargeFee = mfBusChargeFeeFeign.getById(mfBusChargeFee);
//							if(mfBusChargeFee!=null){
//								mfBusChargeFee.setAccountAmt(mfBusChargeFee.getActualReceivedAmt());
//								getObjValue(formcommon, mfBusChargeFee);
//								//this.changeFormProperty(formcommon, "accountAmt", "initValue", mfBusChargeFee.getActualReceivedAmt());
//							}
//						}

					}
				}else {
				}
                if(opinionTypeListFiveClass!=null && opinionTypeListFiveClass.size()>0){
                    this.changeFormProperty(formcommon, "opinionType", "optionArray", opinionTypeListFiveClass);
                }
                if(opinionTypeListExamHis!=null && opinionTypeListExamHis.size()>0){
                    this.changeFormProperty(formcommon, "opinionType", "optionArray", opinionTypeListExamHis);
                }
                JsonFormUtil jsonFormUtil = new JsonFormUtil();
                String htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", "");
                jsonArray.getJSONObject(i).put("formHtml", htmlStr);
                jsonArray.getJSONObject(i).put("formId", formId);
            }
		}
		resMap.put("jsonArray", jsonArray);
		resMap.put("isHtml", "false");
		return resMap;
	}
	

	private Map<String, String> getUserPmsBizsMap(HttpServletRequest request) throws Exception {
		String[] roleNoStr = (String[]) request.getSession().getAttribute("roleNo");
		String roleNo = "";
		Map<String, String> pmsBizMap = new HashMap<String, String>();
		String pmBizNo = "";
		CodeUtils codeUtils = new CodeUtils();

		for (int i = 0; i < roleNoStr.length; i++) {
			if (roleNoStr[i] != null && !"".equals(roleNoStr[i])) {
				roleNo = roleNoStr[i];
				pmBizNo = (String) codeUtils.getObjCache4Pms(roleNo);
				if (pmBizNo != null && !"".equals(pmBizNo)) {
					String[] pmBizNoArr = pmBizNo.split(",");
					for (String pmsBizNo : pmBizNoArr) {
						pmsBizMap.put(pmsBizNo, pmsBizNo);
					}
				}
			}
		}
		return pmsBizMap;
	}

	/**
	 * 审批列表下拉展示审批详情信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String,Object> listShowDetailAjax(String taskId,String appNo,String formType,String approveType) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			Map<String,String> appValueMap = resolveHisProcessUtilFeign.getWfHistTaskInfoById(taskId,approveType);
			String detailFormId = appValueMap.get("detailFormId");
			if(StringUtil.isNotEmpty(detailFormId)){
                FormData formapprovedetail = formService.getFormData(detailFormId);
                if(StringUtil.isEmpty(formapprovedetail.getFormId())){
					dataMap.put("msg", MessageEnum.NO_FILE.getMessage("表单文件form"+detailFormId+".xml"));
					dataMap.put("flag", "error");
					return dataMap;
				}
                //处理表单特殊值的展示（期限添加单位，利率进行转换等）
                dealFormValue(formapprovedetail,appValueMap,appNo,formType);
                getObjValue(formapprovedetail, appValueMap);
                request.setAttribute("ifBizManger", "3");
                JsonFormUtil jsonFormUtil = new JsonFormUtil();
                String htmlStr = jsonFormUtil.getJsonStr(formapprovedetail, "propertySeeTag", "query");
                if (appValueMap != null) {
                    dataMap.put("formHtml", htmlStr);
                    dataMap.put("flag", "success");
                } else {
                    dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("获取详情失败"));
                    dataMap.put("flag", "error");
                }
            }else{
			    Map<String,String> parmMap = new HashMap<String,String>();
			    parmMap.put("operation","获取详情失败");
			    parmMap.put("reason","没有配置表单");
                dataMap.put("msg", MessageEnum.FAILED_OPERATION_CONTENT.getMessage(parmMap));
                dataMap.put("flag", "error");
            }
		}catch (Exception e){
			e.printStackTrace();
            Map<String,String> parmMap = new HashMap<String,String>();
            parmMap.put("operation","获取详情失败");
            parmMap.put("reason",e.getMessage());
			dataMap.put("msg",MessageEnum.FAILED_OPERATION_CONTENT.getMessage(parmMap));
			dataMap.put("flag", "error");
		}
		return dataMap;
	}


	private void dealFormValue(FormData formapprovedetail,Map<String,String> appValueMap,String appNo,String formType) throws Exception{
		if(appValueMap.containsKey("term")){//处理期限单位
			String term = appValueMap.get("term");
			if(StringUtil.isNotEmpty(term)){
				if(appValueMap.containsKey("termType")){//如果期限类型存在
					String termType = appValueMap.get("termType");
					if(StringUtil.isNotEmpty(termType)){
						Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
						String termUnit = termTypeMap.get(termType).getRemark();
						appValueMap.put("term",term+termUnit);
					}
				}
			}
		}
		if(appValueMap.containsKey("replyTerm")){//处理批复期限单位
			String replyTerm = appValueMap.get("replyTerm");
			if(StringUtil.isNotEmpty(replyTerm)){
				if(appValueMap.containsKey("termType")){//如果期限类型存在
					String termType = appValueMap.get("termType");
					if(StringUtil.isNotEmpty(termType)){
						Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
						String termUnit = termTypeMap.get(termType).getRemark();
						appValueMap.put("replyTerm",replyTerm+termUnit);
					}
				}
			}
		}
		if(appValueMap.containsKey("fincRate")){//处理利率
			String fincRate = appValueMap.get("fincRate");
			if(StringUtil.isNotEmpty(fincRate)){
				if(appValueMap.containsKey("rateType")){//如果利率类型存在,处理利率单位以及利率转换
					String rateType = appValueMap.get("rateType");
					if(StringUtil.isNotEmpty(rateType)){
						Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
						String rateTypeUnit = rateTypeMap.get(rateType).getRemark();
						MfBusAppKind mfBusAppKind = null;
						if("05".equals(formType)){//业务初选表单
							MfBusApply mfBusApply = new  MfBusApply();
							mfBusApply.setPrimaryAppId(appNo);
							mfBusApply = appInterfaceFeign.getByPrimaryAppId(mfBusApply);
							mfBusAppKind = new MfBusAppKind();
							mfBusAppKind.setAppId(mfBusApply.getAppId());
							mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
						}else if("5".equals(formType)){//业务审批
							mfBusAppKind = new MfBusAppKind();
							mfBusAppKind.setAppId(appNo);
							mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
						}else {
						}
						if(mfBusAppKind!=null){
							fincRate = MathExtend.showRateMethod(rateType,fincRate, mfBusAppKind.getYearDays(), Integer.valueOf(mfBusAppKind.getRateDecimalDigits()));
							appValueMap.put("fincRate",fincRate);
							this.changeFormProperty(formapprovedetail,"fincRate","unit",rateTypeUnit);
							if(appValueMap.containsKey("replyFincRate")){//处理批复利率
								String replyFincRate = appValueMap.get("replyFincRate");
								if(StringUtil.isNotEmpty(replyFincRate)){
									replyFincRate = MathExtend.showRateMethod(rateType,replyFincRate, mfBusAppKind.getYearDays(), Integer.valueOf(mfBusAppKind.getRateDecimalDigits()));
									appValueMap.put("replyFincRate",replyFincRate);
									this.changeFormProperty(formapprovedetail,"replyFincRate","unit",rateTypeUnit);
								}

							}
						}
					}
				}
			}
		}
	}
}
