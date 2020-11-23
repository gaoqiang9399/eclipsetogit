package app.component.risk.controller;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.risk.entity.RiskBizItemRel;
import app.component.risk.feign.MfKindNodeRiskFeign;
import app.component.risk.feign.RiskPreventFeign;
import app.component.rules.entity.MfRulesProRelation;
import app.component.rulesinterface.RulesInterfaceFeign;
import app.tech.oscache.CodeUtils;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/riskForApp")
public class RiskForAppController extends BaseFormBean {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private RiskPreventFeign riskPreventFeign;
	@Autowired
	private MfKindNodeRiskFeign mfKindNodeRiskFeign;
	@Autowired
	private RulesInterfaceFeign rulesInterfaceFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private CreditApplyInterfaceFeign creditApplyInterfaceFeign;

	@RequestMapping(value = "/prevent")
	public String prevent(Model model, String relNo) {
		ActionContext.initialize(request, response);
		RiskBizItemRel riskBizItemRel = new RiskBizItemRel();
		riskBizItemRel.setRelNo(relNo);
		List<RiskBizItemRel> rels = this.riskPreventFeign.findByRelNo(riskBizItemRel);
		for (RiskBizItemRel rel : rels) {
			String riskLevel = rel.getRiskLevel();
			if ("1".equals(riskLevel)) {
				rel.setRiskLevel("低风险");
			} else if ("2".equals(riskLevel)) {
				rel.setRiskLevel("高风险");
			}else {
			}
		}
		model.addAttribute("rels", rels);
		model.addAttribute("query", "");
		return "/component/risk/RiskPreventForApp";
	}

	@RequestMapping(value = "/preventList")
	public String preventList(Model model, String relNo) throws Exception {
		ActionContext.initialize(request, response);
		RiskBizItemRel riskBizItemRel = new RiskBizItemRel();
		riskBizItemRel.setRelNo(relNo);
		List<RiskBizItemRel> rels = riskPreventFeign.findByRelNo(riskBizItemRel);
		Map<String, String> riskLevelMap = new CodeUtils().getMapByKeyName("RISK_PREVENT_LEVEL");
		for (RiskBizItemRel rel : rels) {
			String riskLevel = riskLevelMap.get(rel.getRiskLevel());
			if (riskLevel == null) {

			} else {
				rel.setRiskLevelShow(riskLevel);
			}
		}
		model.addAttribute("rels", rels);
		model.addAttribute("query", "");
		return "/component/risk/RiskPreventForAppList";
	}

	@RequestMapping(value = "/preventListForCusEval")
	public String preventListForCusEval(Model model, String relNo,String nodeNo) throws Exception {
		ActionContext.initialize(request, response);
		RiskBizItemRel riskBizItemRel = new RiskBizItemRel();
		riskBizItemRel.setRelNo(relNo);
		riskBizItemRel.setNodeNo(nodeNo);
		List<RiskBizItemRel> rels = riskPreventFeign.findByRelNo(riskBizItemRel);
		Map<String, String> riskLevelMap = new CodeUtils().getMapByKeyName("RISK_PREVENT_LEVEL");
		for (RiskBizItemRel rel : rels) {
			String riskLevel = riskLevelMap.get(rel.getRiskLevel());
			if (riskLevel == null) {

			} else {
				rel.setRiskLevelShow(riskLevel);
			}
		}
		model.addAttribute("rels", rels);
		model.addAttribute("query", "");
		return "/component/risk/RiskPreventForCusList";
	}
	@RequestMapping(value = "/getPreventListByNodeNo")
	public String getPreventListByNodeNo(Model model, String relNo, String nodeNo) throws Exception {
		ActionContext.initialize(request, response);
		RiskBizItemRel riskBizItemRel = new RiskBizItemRel();
		riskBizItemRel.setRelNo(relNo);
		riskBizItemRel.setNodeNo(nodeNo);
		List<RiskBizItemRel> rels = riskPreventFeign.findByRelNo(riskBizItemRel);
		Map<String, String> riskLevelMap = new CodeUtils().getMapByKeyName("RISK_PREVENT_LEVEL");
		for (RiskBizItemRel rel : rels) {
			String riskLevel = riskLevelMap.get(rel.getRiskLevel());
			if (riskLevel == null) {

			} else {
				rel.setRiskLevelShow(riskLevel);
			}
		}
		model.addAttribute("rels", rels);
		model.addAttribute("query", "");
		return "/component/risk/RiskPreventForAppList";
	}

	/**
	 * 方法描述： 准入拦截
	 * 
	 * @return String
	 * @author Javelin
	 * @date 2017-7-10 下午4:43:42
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getNodeRiskDataForBeginAjax")
	@ResponseBody
	public Map<String, Object> getNodeRiskDataForBeginAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		JSONObject json = JSONObject.fromObject(ajaxData);
		Map<String, String> parmMap = (Map<String, String>) JSONObject.toBean(json, Map.class);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfRulesProRelation mfRulesProRelation = new MfRulesProRelation();
			mfRulesProRelation.setBusLink(parmMap.get("nodeNo"));
			mfRulesProRelation.setUseFlag(BizPubParm.USE_FLAG_1);
			mfRulesProRelation.setKindNo(parmMap.get("kindNo"));
			mfRulesProRelation = rulesInterfaceFeign.getMfRulesProRelation(mfRulesProRelation);
            parmMap.put("opNo", User.getRegNo(request));
			if (mfRulesProRelation == null) {// 继续匹配无产品的通用配置
				mfRulesProRelation = new MfRulesProRelation();
				mfRulesProRelation.setBusLink(parmMap.get("nodeNo"));
				mfRulesProRelation.setUseFlag(BizPubParm.USE_FLAG_1);
				mfRulesProRelation = rulesInterfaceFeign.getMfRulesProRelation(mfRulesProRelation);
			}

			if (mfRulesProRelation != null) {
				parmMap.put("rulesNo", mfRulesProRelation.getRulesNo());
				dataMap = mfKindNodeRiskFeign.getRiskBeanForNode(parmMap);
			}
			if (dataMap.get("exsitRefused") != null && (boolean) dataMap.get("exsitRefused")) {
				String msg = StringUtil.join(((List<String>) dataMap.get("refuselist")).toArray(), ",");
				dataMap.put("msg", msg);
			}
			/*授信改造后废弃*/
//			if(StringUtil.isNotEmpty(parmMap.get("cusNo")) && !"CREDIT_APPLY".endsWith(parmMap.get("nodeNo"))){
//				// 1.客户授信检查
//				MfCusCustomer mfCusCustomer = new MfCusCustomer();
//				mfCusCustomer.setCusNo(parmMap.get("cusNo"));
//				mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
//				MfBusApply mfBusApply = new MfBusApply();
//				mfBusApply.setKindNo(parmMap.get("kindNo"));
//				Map<String,Object> resultMap = creditApplyInterfaceFeign.checkCredit(mfBusApply,mfCusCustomer.getCusBaseType(),parmMap.get("cusNo"));
//				if("error".equals(resultMap.get("flag").toString())){
//					dataMap.put("isCredit", BizPubParm.YES_NO_N);
//				}else{
//					dataMap.put("isCredit", BizPubParm.YES_NO_Y);
//				}
//			}

			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}

		return dataMap;
	}

}
