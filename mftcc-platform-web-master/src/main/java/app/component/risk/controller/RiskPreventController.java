package app.component.risk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.risk.entity.RiskPrevent;
import app.component.risk.entity.RiskItemThreashode;
import app.component.risk.entity.RiskItem;
import app.component.risk.entity.RiskPreventItemRel;
import app.component.risk.entity.RiskPreventSceGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.nmd.entity.ParmDic;
import app.component.nmd.entity.ParmKey;
import app.component.nmd.feign.ParmDicFeign;
import app.component.nmd.feign.ParmKeyFeign;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.component.risk.feign.RiskItemFeign;
import app.component.risk.feign.RiskItemThreashodeFeign;
import app.component.risk.feign.RiskPreventFeign;
import app.component.risk.feign.RiskPreventItemRelFeign;
import app.component.risk.feign.RiskPreventSceGenFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/riskPrevent")
public class RiskPreventController extends BaseFormBean {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private RiskPreventFeign riskPreventFeign;
	@Autowired
	private RiskItemFeign riskItemFeign;
	@Autowired
	private RiskPreventItemRelFeign riskPreventItemRelFeign;
	@Autowired
	private ParmDicFeign parmDicFeign;
	@Autowired
	private ParmKeyFeign parmKeyFeign;
	@Autowired
	private RiskItemThreashodeFeign riskItemThreashodeFeign;
	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;
	@Autowired
	private RiskPreventSceGenFeign riskPreventSceGenFeign;

	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String pageStr) throws Exception {
		ActionContext.initialize(request, response);
		List<RiskPrevent> riskPrevents = riskPreventFeign.getAllSceRiskDimList();
		model.addAttribute("riskPrevents", riskPrevents);
		model.addAttribute("pageStr", pageStr);
		model.addAttribute("query", "");
		return "/component/risk/RiskPreventConfig";
	}

	@RequestMapping(value = "/getLimitDimes")
	public String getLimitDimes(Model model) throws Exception {
		ActionContext.initialize(request, response);
		List<ParmKey> limiDimes = riskPreventFeign.getLimitParmKey();
		model.addAttribute("limiDimes", limiDimes);
		model.addAttribute("query", "");
		return "/component/risk/getLimitDimes";
	}

	@RequestMapping(value = "/getThreadHold")
	public String getThreadHold(Model model, RiskPrevent riskPrevent) throws Exception {
		ActionContext.initialize(request, response);
		String relNo = riskPrevent.getRelNo();
		String itemNo = riskPrevent.getItemNo();
		RiskItemThreashode hode = new RiskItemThreashode();
		hode.setItemNo(itemNo);
		Ipage ipage = getIpage();
		List<RiskItemThreashode> threadHolds = (List<RiskItemThreashode>) riskItemThreashodeFeign
				.getListPage(ipage, hode).getResult();
		for (RiskItemThreashode h : threadHolds) {
			if ("2".equals(h.getThreashodeType())) {
				String keyName = h.getDicKeyName().substring(0, h.getDicKeyName().indexOf("-"));
				ParmDic p = new ParmDic();
				p.setKeyName(keyName);
				List<ParmDic> parmDics = parmDicFeign.findlist(p);
				h.setParmDic(parmDics);
			}
			String threadHodeType = h.getThreashodeType();
			ParmDic type = new ParmDic();
			type.setKeyName("RISK_THREADHOLD_TYPE");
			List<ParmDic> types = parmDicFeign.findlist(type);
			for (ParmDic typeDic : types) {
				if (typeDic.getOptCode().equals(threadHodeType)) {
					h.setThreashodeType(typeDic.getOptName());
					break;
				}
			}

		}
		riskPrevent.setRiskItemThreashodes(threadHolds);
		RiskItem riskItem = new RiskItem();
		riskItem.setItemNo(itemNo);
		riskItem = riskItemFeign.getById(riskItem);
		riskPrevent.setItemSqlDesc(riskItem.getItemSqlDesc());
		riskPrevent.setFormularDesc(riskItem.getItemFormulaDesc());

		model.addAttribute("query", "");
		return "/component/risk/RiskPreventGetThreadHoldForItem";
	}

	@RequestMapping(value = "/saveNoSelectedItem")
	@ResponseBody
	public Map<String, Object> saveNoSelectedItem(Model model,RiskPrevent riskPrevent, String notSelectedItemNo, String pageStr) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		StringBuffer notSelectedTrs = new StringBuffer("");
		StringBuffer selectedTrs = new StringBuffer("");
		try {
			
			String genNo = riskPrevent.getGenNo();
			String[] itemNos = notSelectedItemNo.substring(1).split("\\|");
			for (String itemNo : itemNos) {
				if (itemNo != "") {
					RiskPreventItemRel rel = new RiskPreventItemRel();
					rel.setGenNo(genNo);
					rel.setBaseItemNo(itemNo);
					// RiskItem item = new RiskItem();
					// item.setItemNo(itemNo);
					// item = riskItemFeign.getById(item);
					// rel.setItemDesc(item.getItemDesc());
					// rel.setRiskLevel("1");
					rel.setUseInd("1");

					RiskPreventItemRel rTemp = riskPreventItemRelFeign.getById(rel);
					if (rTemp == null) {
						riskPreventItemRelFeign.insert(rel);
					} else {
						rel.setUseInd("1");
						rel.setItemNo(rTemp.getItemNo());
						riskPreventItemRelFeign.update(rel);
					}
				}
			}

			List<String> classList = riskPreventFeign.getSelectedRiskPreventClass(riskPrevent.getGenNo());

			String riskPreventClassStr = "";
			if (classList.size() > 0 && classList != null) {
				for (int i = 0; i < classList.size(); i++) {
					riskPreventClassStr += classList.get(i) + "|";
				}
				riskPrevent.setRiskPreventClassStr(riskPreventClassStr.substring(0, riskPreventClassStr.length() - 1));
				riskPrevent.setPageStr(pageStr);
				List<RiskPrevent> selectedRiskPrevent = riskPreventFeign.getSelectedRiskItems(riskPrevent);
				List<RiskItem> notSelecteedRiskItem = riskPreventFeign.getNotSelectedRiskItems(riskPrevent);

				for (RiskItem notSe : notSelecteedRiskItem) {
					notSelectedTrs.append("<tr>").append("<td align=\"left\">")
							.append("<input type=\"checkbox\" value=\"" + notSe.getItemNo() + "\">").append("</td>")
							.append("<td align=\"left\">").append(notSe.getItemName()).append("</td>").append("</tr>");

				}

				for (RiskPrevent sel : selectedRiskPrevent) {
					createSelItemHtml(selectedTrs, sel);

				}
				dataMap.put("notSelectedTrs", notSelectedTrs.toString());
				dataMap.put("selectedTrs", selectedTrs.toString());
			}
			dataMap.put("classList", classList);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存拦截项"));

		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存拦截项"));
			throw e;
		}
		return dataMap;
	}

	private void createSelItemHtml(StringBuffer selStr, RiskPrevent sel) {
		String itemDesc = sel.getItemDesc();
		String itemName = sel.getItemName();
		String itemNo = sel.getItemNo();
		if (itemDesc.length() > 6) {
			itemDesc = itemDesc.substring(0, 6) + "...";
		}
		if (itemName.length() > 6) {
			itemName = itemName.substring(0, 6) + "...";
		}
		ParmDic riskLevelDic = new ParmDic();
		riskLevelDic.setKeyName("RISK_PREVENT_LEVEL");
		riskLevelDic.setOptCode(sel.getRiskLevel());
		riskLevelDic = parmDicFeign.getById(riskLevelDic);
		selStr.append("<tr relNo=" + sel.getRelNo() + " id=" + sel.getRelNo() + " itemNo=" + itemNo + ">")
				.append("<td align=\"center\">")
				.append("<input type=\"checkbox\" value=\"" + sel.getItemNo() + "\" checked=checked>").append("</td>")
				.append("<td align=\"center\" onclick=\"openTip(this,\'" + sel.getItemName() + "\');\">" + itemName
						+ "</td>")
				.append("<td align=\"center\"  style=\"width: 30%\" itemDesc=" + sel.getItemDesc()
						+ "><label onclick=\"openTip(this,\'" + sel.getItemDesc()
						+ "\');\" style=\"display:inline-block;width:100px;\">" + itemDesc + "</label></td>")
				.append("</tr>");
	}

	/**
	 * 
	 * 方法描述： 处理已配的拦截项列表
	 * 
	 * @param sel
	 *            void
	 * @author zhs
	 * @date 2016-10-28 上午11:53:05
	 */
	private void dealSelItem(RiskPrevent sel) {
		String itemDesc = sel.getItemDesc();
		String itemName = sel.getItemName();
		if (itemDesc.length() > 6) {
			itemDesc = itemDesc.substring(0, 6) + "...";
		}
		if (itemName.length() > 6) {
			itemName = itemName.substring(0, 6) + "...";
		}
		ParmDic riskLevelDic = new ParmDic();
		riskLevelDic.setKeyName("RISK_PREVENT_LEVEL");
		riskLevelDic.setOptCode(sel.getRiskLevel());
		riskLevelDic = parmDicFeign.getById(riskLevelDic);
		sel.setRiskLevelName(riskLevelDic.getOptName());
		sel.setItemNameSub(itemName);
		sel.setItemDescSub(itemDesc);
	}

	@RequestMapping(value = "/saveEditedThreadHold")
	@ResponseBody
	public Map<String, Object>  saveEditedThreadHold(Model model, RiskPrevent riskPrevent) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String itemNo = riskPrevent.getItemNo();
			String threadHoldName = riskPrevent.getThreadHoldName();
			String threadHoldValue = riskPrevent.getThreadHoldVal();
			RiskItemThreashode threadHold = new RiskItemThreashode();
			threadHold.setItemNo(itemNo);
			threadHold.setThreashodeName(threadHoldName);
			threadHold = riskItemThreashodeFeign.getById(threadHold);
			String threadHoldType = threadHold.getThreashodeType();
			if ("1".equals(threadHoldType)) { // 数值
				threadHold.setThreashodeValue(threadHoldValue);
			} else if ("2".equals(threadHoldType)) {// 字典项
				threadHold.setDicKeyValue(threadHoldValue);
			}else {
			}
			riskItemThreashodeFeign.update(threadHold);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存拦截项"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存拦截项"));
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/saveEditedItem")
	@ResponseBody
	public Map<String, Object> saveEditedItem(Model model, RiskPrevent riskPrevent) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			
			String relNo = riskPrevent.getRelNo();
			String itemDesc = riskPrevent.getItemDesc();
			String itemLevel = riskPrevent.getRiskLevel();
			RiskPreventItemRel rir = new RiskPreventItemRel();
			rir.setRelNo(relNo);
			rir = riskPreventItemRelFeign.getByRelNo(rir);
			rir.setItemDesc(itemDesc);
			rir.setRiskLevel(itemLevel);
			riskPreventItemRelFeign.update(rir);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存拦截项"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存拦截项"));
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/saveSelectedItem")
	@ResponseBody
	public Map<String, Object> saveSelectedItem(Model model, RiskPrevent riskPrevent,String notSelectedItemNo, String pageStr) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		StringBuffer notSelectedTrs = new StringBuffer("");
		StringBuffer selectedTrs = new StringBuffer("");
		try {
			String genNo = riskPrevent.getGenNo();
			String[] itemNos = notSelectedItemNo.substring(1).split("\\|");
			for (String itemNo : itemNos) {
				if (itemNo != "") {
					RiskPreventItemRel rel = new RiskPreventItemRel();
					rel.setGenNo(genNo);
					rel.setItemNo(itemNo);
					rel = riskPreventItemRelFeign.getById(rel);
					rel.setUseInd("0");
					riskPreventItemRelFeign.update(rel);
				}
			}
			List<String> classList = riskPreventFeign.getSelectedRiskPreventClass(riskPrevent.getGenNo());
			riskPrevent.setPageStr(pageStr);
			String riskPreventClassStr = "";
			if (classList.size() > 0 && classList != null) {
				for (int i = 0; i < classList.size(); i++) {
					riskPreventClassStr += classList.get(i) + "|";
				}
				riskPrevent.setRiskPreventClassStr(riskPreventClassStr.substring(0, riskPreventClassStr.length() - 1));
				List<RiskPrevent> selectedRiskPrevent = riskPreventFeign.getSelectedRiskItems(riskPrevent);
				List<RiskItem> notSelecteedRiskItem = riskPreventFeign.getNotSelectedRiskItems(riskPrevent);

				for (RiskItem notSe : notSelecteedRiskItem) {
					notSelectedTrs.append("<tr>").append("<td align=\"left\">")
							.append("<input type=\"checkbox\" value=\"" + notSe.getItemNo() + "\">").append("</td>")
							.append("<td align=\"left\">").append(notSe.getItemName()).append("</td>").append("</tr>");

				}

				for (RiskPrevent sel : selectedRiskPrevent) {
					createSelItemHtml(selectedTrs, sel);

				}
				dataMap.put("notSelectedTrs", notSelectedTrs.toString());
				dataMap.put("selectedTrs", selectedTrs.toString());
			}
			dataMap.put("classList", classList);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存拦截项"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存拦截项"));
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/getItems")
	@ResponseBody
	public Map<String, Object> getItems(Model model, String pageStr, RiskPrevent riskPrevent) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		StringBuffer notSelectedTrs = new StringBuffer("");
		StringBuffer selectedTrs = new StringBuffer("");
		
		riskPrevent.setPageStr(pageStr);
		try {
			List<RiskPrevent> selectedRiskPrevent = riskPreventFeign.getSelectedRiskItems(riskPrevent);
			List<RiskItem> notSelecteedRiskItem = riskPreventFeign.getNotSelectedRiskItems(riskPrevent);

			for (RiskItem notSe : notSelecteedRiskItem) {
				notSelectedTrs.append("<tr>").append("<td align=\"left\">")
						.append("<input type=\"checkbox\" value=\"" + notSe.getItemNo() + "\">").append("</td>")
						.append("<td align=\"left\">").append(notSe.getItemName()).append("</td>").append("</tr>");

			}

			for (RiskPrevent sel : selectedRiskPrevent) {
				createSelItemHtml(selectedTrs, sel);
			}
			dataMap.put("notSelectedTrs", notSelectedTrs.toString());
			dataMap.put("selectedTrs", selectedTrs.toString());
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("查询拦截项"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("查询拦截项"));
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/insertConfig")
	@ResponseBody
	public Map<String, Object> insertConfig(Model model, RiskPrevent riskPrevent) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String parmDicStr = riskPrevent.getDimeParmKeysStr();
			String javaParmDicStr = riskPrevent.getDicJavaNamesStr();

			List<String> parms = new ArrayList<String>();
			List<String> parmJava = new ArrayList<String>();

			String[] parmStrs = parmDicStr.split("\\|");
			String[] javaParmStrs = javaParmDicStr.split("\\|");

			for (String par : parmStrs) {
				parms.add(par);
				String parJavaName = par.toLowerCase();
				String[] parJavaStrs = parJavaName.split("_");
				parJavaName = "";
				for (int i = 0; i < parJavaStrs.length; i++) {
					if (i == 0) {
						parJavaName += parJavaStrs[i];
					} else {
						parJavaName += parJavaStrs[i].substring(0, 1).toUpperCase()
								+ parJavaStrs[i].substring(1, parJavaStrs[i].length());

					}

				}
				parmJava.add(parJavaName);
			}

			// for(String parjava:javaParmStrs) {
			// parmJava.add(parjava);
			// }

			riskPrevent.setDimes(parms);
			riskPrevent.setDicJavaNames(parmJava);
			riskPreventFeign.insertRiskPreventSceConfig(riskPrevent);

			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("风险拦截配置保存"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("风险拦截配置保存"));
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/getSceGenItem")
	public String getSceGenItem(Model model, String pageStr, RiskPrevent riskPrevent) throws Exception {
		ActionContext.initialize(request,
				response);
		String parmDicStr = riskPrevent.getDimeParmKeysStr();
		String genNo = riskPrevent.getGenNo();
		model.addAttribute("genNo", genNo);
		String parmDics[] = parmDicStr.split("\\|");
		List<String> parms = new ArrayList<String>();
		for(String parm:parmDics) {
			parms.add(parm);
		}
		riskPrevent.setDimes(parms);
		riskPrevent.setPageStr(pageStr);
		String dimeChnName = "";
		CodeUtils cu = new CodeUtils();
		for(int i=0;i<parmDics.length;i++) {
			String chn ="";
			if("KIND_NO".equals(parmDics[i])){
				chn = "产品种类"; 
			}else if("CUS_TYPE".equals(parmDics[i])){
				chn = "客户类型"; 
			}else{
				ParmKey p = new ParmKey();
				p.setKeyName(parmDics[i]);
				chn = parmKeyFeign.getById(p).getKeyChnName();
			}
			if(i<parmDics.length-1) {
				dimeChnName +=chn+"-";
			}else {
				dimeChnName+=chn;
			}
			
		}
		model.addAttribute("dimeChnName", dimeChnName);
		//dimeChnName = new String(dimeChnName.trim().getBytes("ISO-8859-1"), "GBK");
//		riskPrevents = riskPreventFeign.getRiskPreventSceGens(riskPrevent);
		RiskPreventSceGen  riskPreventSceGen = new RiskPreventSceGen();
		 riskPreventSceGen.setGenNo(riskPrevent.getGenNo());
		 riskPreventSceGen = riskPreventSceGenFeign.getById(riskPreventSceGen);
		//获取拦截项类别列表
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("RISK_PREVENT_CLASS");
		List<ParmDic> riskClassparmDicList = (List<ParmDic>)nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		model.addAttribute("riskClassparmDicList", riskClassparmDicList);
		StringBuffer notSelectedTrsTmp = new StringBuffer("");
		StringBuffer selectedTrsTmp = new StringBuffer("");
		//获取该模型下已配置的拦截项类别
		List<String> classList = riskPreventFeign.getSelectedRiskPreventClass(riskPrevent.getGenNo());
		
		String riskPreventClassStr = "";
		if(classList.size()>0&&classList!=null){
			for(int i=0;i<classList.size();i++){
				riskPreventClassStr += classList.get(i)+"|";
			}
			riskPrevent.setRiskPreventClassStr(riskPreventClassStr.substring(0, riskPreventClassStr.length()-1));
			//获取可配和已配拦截项信息
			List<RiskPrevent> selectedRiskPrevent = riskPreventFeign.getSelectedRiskItems(riskPrevent);
			List<RiskItem> notSelecteedRiskItem = riskPreventFeign.getNotSelectedRiskItems(riskPrevent);
			for(RiskPrevent sel:selectedRiskPrevent){
				dealSelItem(sel);
			}
			model.addAttribute("selectedRiskPrevent", selectedRiskPrevent);
			model.addAttribute("notSelecteedRiskItem", notSelecteedRiskItem);
		}
		JSONArray array = JSONArray.fromObject(classList);
		JSONObject json = new JSONObject();
		json.put("classList", array);
		
		model.addAttribute("query", "");
		return "/component/risk/RiskPreventSceGen";
	}

}
