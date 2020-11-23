package app.component.interfaces.appinterface.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.doc.entity.DocManage;
import app.component.docinterface.DocInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.wkf.feign.ResolveProcessUtilFeign;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

/**
 * 审批流程
 * 
 * @author Tangxj
 */
@Controller
@RequestMapping("/dingWkfChart")
public class DingWkfChartController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private DocInterfaceFeign docInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
	@Autowired
	private ResolveProcessUtilFeign resolveProcessUtilFeign;

	@RequestMapping(value = "/getWkfChartInfoAjax")
	@ResponseBody
	public Map<String, Object> getWkfChartInfoAjax(String appNo, String formType, String isApp, String query)
			throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		JSONArray jsonArray = resolveProcessUtilFeign.resolveProcess(appNo);

		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(appNo);
		mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
		// 处理初始节点没有值的情况
		String termType = "";
		String rateType = "";
		String activityType = "";
		String fiveclassId = "";
		String examHisId = "";
		for (int j = 0; j < jsonArray.size(); j++) {
			jsonArray.getJSONObject(j).put("appAmt", "1000.00");
			String approveIdea = "";
			if (jsonArray.getJSONObject(j).containsKey("approveIdea")) {
				approveIdea = jsonArray.getJSONObject(j).getString("approveIdea");
			}
			// 暂时处理appValue的value项空值问题
			if (jsonArray.getJSONObject(j).get("appValue") != null) {
				JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(j).get("appValue");
				if (!(jsonObject.get("termType") instanceof JSONNull || jsonObject.get("termType") == null)) {
					termType = jsonObject.get("termType").toString();
				}
				if (jsonObject.get("replyAmt") instanceof JSONNull || jsonObject.get("replyAmt") == null
						|| "".equals(String.valueOf(jsonObject.get("replyAmt")))) {
					jsonObject.put("replyAmt", "0.00");
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
				if ("1".equals(formType)) {
					jsonObject.put("creditSum", MathExtend.moneyStr(String.valueOf(jsonObject.get("creditSum"))));
				} else if ("2".equals(formType)) {
				} else if ("3".equals(formType)) {
					jsonObject.put("pactAmt", MathExtend.moneyStr(String.valueOf(jsonObject.get("pactAmt"))));
					jsonObject.put("putoutAmt", MathExtend.moneyStr(String.valueOf(jsonObject.get("putoutAmt"))));
				} else if ("4".equals(formType)) {
					jsonObject.put("pactAmt", MathExtend.moneyStr(String.valueOf(jsonObject.get("pactAmt"))));
				} else if ("5".equals(formType)) {
					if (StringUtil.isEmpty((String) jsonObject.get("rateType"))) {

					} else {
						rateType = jsonObject.get("rateType").toString();
					}
					jsonObject.put("replyAmt", MathExtend.moneyStr(String.valueOf(jsonObject.get("replyAmt"))));
					jsonObject.put("appAmt", MathExtend.moneyStr(String.valueOf(jsonObject.get("appAmt"))));
					jsonObject.put("fincRate",
							MathExtend.moneyStr(MathExtend.showRateMethod(rateType,
									Double.valueOf(jsonObject.get("fincRate").toString()),
									Integer.parseInt(mfBusAppKind.getYearDays()),
									Integer.parseInt(mfBusAppKind.getRateDecimalDigits()))));
					jsonObject.put("replyFincRate", MathExtend.moneyStr(jsonObject.get("replyFincRate").toString()));
				} else if ("6".equals(formType)) {
					activityType = String.valueOf(jsonObject.get("activityType"));
					fiveclassId = String.valueOf(jsonObject.get("fiveclassId"));
				} else if ("7".equals(formType)) {
					activityType = String.valueOf(jsonObject.get("activityType"));
					examHisId = String.valueOf(jsonObject.get("examHisId"));
				} else if ("8".equals(formType)) {
					jsonObject.put("rebateAmt", MathExtend.moneyStr(String.valueOf(jsonObject.get("rebateAmt"))));
					jsonObject.put("rebateRepayAmt",
							MathExtend.moneyStr(String.valueOf(jsonObject.get("rebateRepayAmt"))));
					jsonObject.put("rebateRepayInt",
							MathExtend.moneyStr(String.valueOf(jsonObject.get("rebateRepayInt"))));
				} else if ("9".equals(formType)) {
				} else if ("10".equals(formType)) {
					jsonObject.put("receAmt", MathExtend.moneyStr(String.valueOf(jsonObject.get("receAmt"))));
					jsonObject.put("receBal", MathExtend.moneyStr(String.valueOf(jsonObject.get("receBal"))));
					jsonObject.put("tranAmt", MathExtend.moneyStr(String.valueOf(jsonObject.get("tranAmt"))));
				} else if ("11".equals(formType)) {
					jsonObject.put("adjustedWorth",
							MathExtend.moneyStr(String.valueOf(jsonObject.get("adjustedWorth"))));
				} else if ("12".equals(formType)) {
					jsonObject.put("accruedInterest",
							MathExtend.moneyStr(String.valueOf(jsonObject.get("accruedInterest"))));
					jsonObject.put("fincPrepayBal",
							MathExtend.moneyStr(String.valueOf(jsonObject.get("fincPrepayBal"))));
				} else if ("13".equals(formType)) {
					// 否决复议审批
				} else if ("14".equals(formType)) {
					jsonObject.put("appAmt", MathExtend.moneyStr(String.valueOf(jsonObject.get("appAmt"))));
					jsonObject.put("intstAmt", MathExtend.moneyStr(String.valueOf(jsonObject.get("intstAmt"))));
					jsonObject.put("penaltyAmt", MathExtend.moneyStr(String.valueOf(jsonObject.get("penaltyAmt"))));
					jsonObject.put("deductAmt", MathExtend.moneyStr(String.valueOf(jsonObject.get("deductAmt"))));
				}else {
				}
				jsonArray.getJSONObject(j).put("appValue", jsonObject);
				String html = getApproveDetailHtml(formType, jsonObject, termType, rateType, approveIdea);
				jsonArray.getJSONObject(j).put("appHtml", html);
			}
		}

		// 如果是审批流程，处理审批过程中的附件
		if (("false").equals(isApp)) {
			// 遍历每个完成的节点
			for (int i = 0; i < jsonArray.size(); i++) {
				if ("start".equals(jsonArray.getJSONObject(i).getString("type"))
						|| "end".equals(jsonArray.getJSONObject(i).getString("type"))) {
					continue;
				}
				if (jsonArray.getJSONObject(i).containsKey("state")
						&& "completed".equals(jsonArray.getJSONObject(i).getString("state"))) {
					// 根据操作员号和业务编号查询相应的附件信息
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("regNo", jsonArray.getJSONObject(i).getString("user"));
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
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appNo);// 申请信息
		if (mfBusApply != null) {// 业务流程图
			jsonArray = auditNodeControl(jsonArray, mfBusApply);// 处理业务流程中审批判定节点造成的流程节点不显示问题
		}
		String creditProcessId = creditApplyInterfaceFeign.getCreditProcessId(appNo);
		jsonArray = cusAuditNodeControl(jsonArray, creditProcessId);// 处理授信流程中审批判定节点造成的流程节点不显示问题

		dataMap.put("jsonArray", jsonArray);
		dataMap.put("flag", "success");

		// 纵向审批历史流程图展示使用
		// 现阶段根据传递的type，获取不同的表单
		// 1授信审批creditapplyhiswkf
		// 2评级审批历史evalapplyhiswkf
		// 3放款审批fincapplyhiswkf
		// 4合同审批pactapplyhiswkf
		// 5业务审批approvalapplyhiswkf
		// 6五级分类审批fiveclassapphiswkf
		// 7贷后检查审批examapprovhiswkf
		// 8折让检查审批rebatehiswkf
		// 9押品争议检查审批disputedhiswkf
		// 10转让检查审批recetranapprohiswkf
		// 11调价检查审批modifyhiswkf
		// 12反转让检查审批plerepoapprovehiswkf
		// 13巡库审批patrolapprovehiswkf
		// 14提前还款申请审批prerepayapplyhiswkf
		// 15资产保全审批assetapprovehiswkf
		String formId = "";
		if ("1".equals(formType)) {
			formId = "creditapplyhiswkf";
		} else if ("2".equals(formType)) {
			formId = "evalapplyhiswkf";
		} else if ("3".equals(formType)) {
			formId = "fincapplyhiswkf";
		} else if ("4".equals(formType)) {
			formId = "pactapplyhiswkf";
		} else if ("5".equals(formType)) {
			formId = "approvalapplyhiswkf";
		} else if ("6".equals(formType)) {
			formId = "fiveclassapphiswkf";
		} else if ("7".equals(formType)) {
			formId = "examapprovhiswkf";
		} else if ("8".equals(formType)) {
			formId = "rebatehiswkf";
		} else if ("9".equals(formType)) {
			formId = "disputedhiswkf";
		} else if ("10".equals(formType)) {
			formId = "recetranapprohiswkf";
		} else if ("11".equals(formType)) {
			formId = "modifyhiswkf";
		} else if ("12".equals(formType)) {
			formId = "plerepoapprovehiswkf";
		} else if ("13".equals(formType)) {
			formId = "patrolapprovehiswkf";
		} else if ("14".equals(formType)) {
			formId = "prerepayapplyhiswkf";
		} else if ("15".equals(formType)) {
			formId = "assetapprovehiswkf";
		}else {
		}
		FormData formcommon = formService.getFormData(formId);
		// 期限单位格式化
		if (!"".equals(formId) && termType != null && !"".equals(termType)) {
			CodeUtils codeUtils = new CodeUtils();
			Map<String, ParmDic> termTypeMap = codeUtils.getMapObjByKeyName("TERM_TYPE");
			String termUnit = termTypeMap.get(termType).getRemark();
			if ("5".equals(formType)) {
				this.changeFormProperty(formcommon, "term", "unit", termUnit);
				this.changeFormProperty(formcommon, "replyTerm", "unit", termUnit);
			} else if ("4".equals(formType)) {
				this.changeFormProperty(formcommon, "term", "unit", termUnit);
			} else if ("3".equals(formType)) {
				this.changeFormProperty(formcommon, "termMonth", "unit", termUnit);
			}else {
			}
		}
		// 处理五级分类审批意见格式
		if ("6".equals(formType) && fiveclassId != null) {
			// 获得审批节点信息
			TaskImpl taskAppro = wkfInterfaceFeign.getTask(fiveclassId, null);
			// 处理审批意见类型
			List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
					taskAppro.getCouldRollback(), null);
			this.changeFormProperty(formcommon, "opinionType", "optionArray", opinionTypeList);
		}
		// 处理贷后检查审批意见格式
		if ("7".equals(formType) && examHisId != null) {
			// 获得审批节点信息
			TaskImpl taskAppro = wkfInterfaceFeign.getTask(examHisId, null);
			// 处理审批意见类型
			List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
					taskAppro.getCouldRollback(), null);
			this.changeFormProperty(formcommon, "opinionType", "optionArray", opinionTypeList);
		}
		if (!"".equals(formId) && rateType != null && !"".equals(rateType)) {
			// 利率单位格式化
			CodeUtils codeUtils = new CodeUtils();
			Map<String, ParmDic> termTypeMap = codeUtils.getMapObjByKeyName("RATE_TYPE");
			String rateUnit = termTypeMap.get(rateType).getRemark();
			if ("5".equals(formType)) {
				this.changeFormProperty(formcommon, "fincRate", "unit", rateUnit);
				this.changeFormProperty(formcommon, "replyFincRate", "unit", rateUnit);
			}
		}

		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String htmlStr = jsonFormUtil.getJsonStr(formcommon, "formTag", query);
		dataMap.put("formId", formId);
		dataMap.put("formHtml", htmlStr);
		dataMap.put("isHtml", "false");

		return dataMap;
	}

	/**
	 * 钉钉审批历史详情
	 * 
	 * @param formType
	 *            审批历史
	 * @return
	 * @author MaHao
	 * @param jsonObject
	 *            数据
	 * @param rateType
	 * @param termType
	 * @param approveIdea
	 * @throws Exception
	 * @date 2017-8-31 下午12:32:21
	 */
	private String getApproveDetailHtml(String formType, JSONObject jsonObject, String termType, String rateType,
			String approveIdea) throws Exception {
		String termUnit = "";// 期限单位
		String rateUnit = "";// 利率单位
		CodeUtils codeUtils = new CodeUtils();
		if (StringUtil.isNotBlank(termType)) {
			Map<String, ParmDic> termTypeMap = codeUtils.getMapObjByKeyName("TERM_TYPE");
			termUnit = termTypeMap.get(termType).getRemark();
		}
		if (StringUtil.isNotBlank(rateType)) {
			Map<String, ParmDic> termTypeMap = codeUtils.getMapObjByKeyName("RATE_TYPE");
			rateUnit = termTypeMap.get(rateType).getRemark();
		}
		String repayTypeStr = "";
		if (jsonObject.containsKey("repayType") && StringUtil.isNotBlank(jsonObject.getString("repayType"))) {
			Map<String, ParmDic> repayTypeMap = codeUtils.getMapObjByKeyName("REPAY_TYPE");
			repayTypeStr = repayTypeMap.get(jsonObject.getString("repayType")).getOptName();
		}
		String html = "";
		if ("5".equals(formType)) {// 融资审批历史
			html = "<div class =\"item-approve-detail\">" + "    <div class=\"item-approve-line\">"
					+ "        <div class=\"item-approve-line-item\" style=\"width: 45%;\">"
					+ "            <div class=\"gery-mid-text\">申请金额</div>" + "            <div>"
					+ jsonObject.getString("appAmt") + "元</div>" + "        </div>"
					+ "        <div class=\"item-approve-line-item\" style=\"width: 30%;\">"
					+ "            <div class=\"gery-mid-text\">申请期限</div>" + "            <div>"
					+ jsonObject.getString("term") + termUnit + "</div>" + "        </div>"
					+ "        <div class=\"item-approve-line-item\" >"
					+ "            <div class=\"gery-mid-text\">申请利率</div>" + "            <div>"
					+ jsonObject.getString("fincRate") + rateUnit + "</div>" + "        </div>" + "	</div>"
					+ "    <div class=\"item-approve-line\">"
					+ "        <div class=\"item-approve-line-item\" style=\"width: 45%;\">"
					+ "            <div class=\"gery-mid-text\">批复金额</div>" + "            <div>"
					+ jsonObject.getString("replyAmt") + "元</div>" + "        </div>"
					+ "        <div class=\"item-approve-line-item\" style=\"width: 30%;\">"
					+ "            <div class=\"gery-mid-text\">批复期限</div>" + "            <div>"
					+ jsonObject.getString("replyTerm") + termUnit + "</div>" + "        </div>"
					+ "        <div class=\"item-approve-line-item\" >"
					+ "            <div class=\"gery-mid-text\">批复利率</div>" + "            <div>"
					+ jsonObject.getString("replyFincRate") + rateUnit + "</div>" + "        </div>" + "	</div>"
					+ "    <div style=\"overflow: hidden;\">" + "        <div >"
					+ "            <div class=\"gery-mid-text item-approve-detail-title\" >审批意见：</div>"
					+ "            <div class=\"item-approve-detail-desc\" >" + approveIdea + "</div>"
					+ "        </div>             " + "	</div>" + "</div>";
		} else if ("4".equals(formType)) {// 合同审批历史
			html = "<div class =\"item-approve-detail\">" + "    <div class=\"item-approve-line\">"
					+ "        <div class=\"item-approve-line-item\" style=\"width: 45%;\">"
					+ "            <div class=\"gery-mid-text\">合同金额</div>" + "            <div>"
					+ jsonObject.getString("pactAmt") + "元</div>" + "        </div>"
					+ "        <div class=\"item-approve-line-item\" style=\"width: 30%;\">"
					+ "            <div class=\"gery-mid-text\">还款方式</div>" + "            <div>" + repayTypeStr
					+ "</div>" + "        </div>" + "        <div class=\"item-approve-line-item\" >"
					+ "            <div class=\"gery-mid-text\">合同期限</div>" + "            <div>"
					+ jsonObject.getString("term") + termUnit + "</div>" + "        </div>" + "	</div>"
					+ "    <div style=\"overflow: hidden;\">" + "        <div >"
					+ "            <div class=\"gery-mid-text item-approve-detail-title\" >审批意见：</div>"
					+ "            <div class=\"item-approve-detail-desc\" >" + approveIdea + "</div>"
					+ "        </div>             " + "	</div>" + "</div>";
		} else if ("3".equals(formType)) {// 放款
			html = "<div class =\"item-approve-detail\">" + "    <div class=\"item-approve-line\">"
					+ "        <div class=\"item-approve-line-item\" style=\"width: 45%;\">"
					+ "            <div class=\"gery-mid-text\">合同金额</div>" + "            <div>"
					+ jsonObject.getString("pactAmt") + "元</div>" + "        </div>"
					+ "        <div class=\"item-approve-line-item\" style=\"width: 30%;\">"
					+ "            <div class=\"gery-mid-text\">支用金额</div>" + "            <div>"
					+ jsonObject.getString("putoutAmt") + "元</div>" + "        </div>"
					+ "        <div class=\"item-approve-line-item\" >"
					+ "            <div class=\"gery-mid-text\">支用期限</div>" + "            <div>"
					+ jsonObject.getString("termMonth") + termUnit + "</div>" + "        </div>" + "	</div>"
					+ "    <div style=\"overflow: hidden;\">" + "        <div >"
					+ "            <div class=\"gery-mid-text item-approve-detail-title\" >审批意见：</div>"
					+ "            <div class=\"item-approve-detail-desc\" >" + approveIdea + "</div>"
					+ "        </div>             " + "	</div>" + "</div>";
		}else {
		}
		return html;
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

		String applyProcessId = mfBusAppKind.getApplyProcessId();// 业务审批流程id
		String pactProcessId = mfBusAppKind.getPactProcessId();// 合同审批流程id
		String fincProcessId = mfBusAppKind.getFincProcessId();// 放款审批流程id

		String apply_approval_nextName = null;// 业务审批下一节点
		String apply_approval_forkName = null;// 业务审批判定节点

		String contract_approval_nextName = null;// 合同审批下一节点
		String contract_approval_forkName = null;// 合同审批判定节点

		String putout_approval_nextName = null;// 放款审批下一节点
		String putout_approval_forkName = null;// 放款审批判定节点

		Iterator<JSONObject> iter = jsonArray.iterator();
		while (iter.hasNext()) {
			JSONObject jsonObj = iter.next();
			String nextName = jsonObj.get("nextName").toString();// 下一节点
			String id = jsonObj.get("id").toString();

			if ("fork".equals(jsonObj.get("type"))) {// 判定节点
				if (nextName.indexOf(WKF_NODE.apply_approval.getNodeNo()) >= 0) {// 业务审批判定节点
																					// &&
																					// 业务审批流程未启用
					iter.remove();// 移除此判定节点
					apply_approval_nextName = nextName.replace(WKF_NODE.apply_approval.getNodeNo(), "").replace(",",
							"");
					apply_approval_forkName = id;
				} else if (nextName.indexOf(WKF_NODE.contract_approval.getNodeNo()) >= 0) {// 合同审批判定节点
																							// &&
																							// 合同审批流程未启用
					iter.remove();
					contract_approval_nextName = nextName.replace(WKF_NODE.contract_approval.getNodeNo(), "")
							.replace(",", "");
					contract_approval_forkName = id;
				} else if (nextName.indexOf(WKF_NODE.putout_approval.getNodeNo()) >= 0) {// 放款审批判定节点
																							// &&
																							// 放款审批流程未启用
					iter.remove();
					putout_approval_nextName = nextName.replace(WKF_NODE.putout_approval.getNodeNo(), "").replace(",",
							"");
					putout_approval_forkName = id;
				}else {
				}
			}
		}

		iter = jsonArray.iterator();
		while (iter.hasNext()) {
			JSONObject jsonObj = iter.next();
			String nextName = jsonObj.get("nextName").toString();// 下一节点

			if (nextName.equals(apply_approval_forkName)) {// 下一节点 = 业务审批判定节点
				if (StringUtil.isEmpty(applyProcessId)) {// 业务审批未启用
					jsonObj.put("nextName", apply_approval_nextName);// 指向业务审批的下一节点
				} else {
					jsonObj.put("nextName", WKF_NODE.apply_approval.getNodeNo());// 指向业务审批
				}
			} else if (nextName.equals(contract_approval_forkName)) {// 下一节点 =
																		// 合同审批判定节点
				if (StringUtil.isEmpty(pactProcessId)) {// 合同审批未启用
					jsonObj.put("nextName", contract_approval_nextName);// 指向合同审批的下一节点
				} else {
					jsonObj.put("nextName", WKF_NODE.contract_approval.getNodeNo());// 指向合同审批
				}
			} else if (nextName.equals(putout_approval_forkName)) {// 下一节点 =
																	// 放款审批判定节点
				if (StringUtil.isEmpty(fincProcessId)) {// 放款审批未启用
					jsonObj.put("nextName", putout_approval_nextName);// 指向放款审批的下一节点
				} else {
					jsonObj.put("nextName", WKF_NODE.putout_approval.getNodeNo());// 指向放款审批
				}
			}else {
			}
		}

		return jsonArray;
	}

	/**
	 * 处理审批节点前添加判定节点后，造成后续流程节点不展示的问题。<br>
	 * 仅处理业务主流程中的业务审批判定、合同审批判定、放款审批判定。<br>
	 * 
	 * @param jsonArray
	 * @param mfCusCreditApply
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-6-29 下午4:18:46
	 */
	@SuppressWarnings("unchecked")
	private JSONArray cusAuditNodeControl(JSONArray jsonArray, String creditProcessId) throws Exception {
		String credit_approval_nextName = null;// 授信审批下一节点
		String credit_approval_forkName = null;// 授信审批判定节点

		Iterator<JSONObject> iter = jsonArray.iterator();
		while (iter.hasNext()) {
			JSONObject jsonObj = iter.next();
			String nextName = jsonObj.get("nextName").toString();// 下一节点
			String id = jsonObj.get("id").toString();

			if ("fork".equals(jsonObj.get("type"))) {// 判定节点
				if (nextName.indexOf(WKF_NODE.credit_approval.getNodeNo()) >= 0) {// 授信审批判定节点
																					// &&
																					// 授信审批流程未启用
					iter.remove();// 移除此判定节点
					credit_approval_nextName = nextName.replace(WKF_NODE.credit_approval.getNodeNo(), "").replace(",",
							"");
					credit_approval_forkName = id;
				}
			}
		}

		iter = jsonArray.iterator();
		while (iter.hasNext()) {
			JSONObject jsonObj = iter.next();
			String nextName = jsonObj.get("nextName").toString();// 下一节点

			if (nextName.equals(credit_approval_forkName)) {// 下一节点 = 授信审批判定节点
				if (StringUtil.isEmpty(creditProcessId)) {// 授信审批未启用
					jsonObj.put("nextName", credit_approval_nextName);// 指向授信审批的下一节点
				} else {
					jsonObj.put("nextName", WKF_NODE.credit_approval.getNodeNo());// 指向授信审批
				}
			}
		}

		return jsonArray;
	}

}
