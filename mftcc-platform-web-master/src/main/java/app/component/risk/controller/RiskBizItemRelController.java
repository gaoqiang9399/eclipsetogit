package app.component.risk.controller;

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
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.risk.entity.RiskBizItemRel;
import app.component.risk.feign.RiskBizItemRelFeign;
import app.component.riskinterface.RiskInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: RiskBizItemRelAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Fri Mar 18 06:37:03 GMT 2016
 **/
@Controller
@RequestMapping("/riskBizItemRel")
public class RiskBizItemRelController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private RiskBizItemRelFeign riskBizItemRelFeign;
	@Autowired
	private RiskInterfaceFeign riskInterfaceFeign;


	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formrisk0016 = formService.getFormData("risk0016");
		RiskBizItemRel riskBizItemRel = new RiskBizItemRel();
		Ipage ipage = this.getIpage();
		List<RiskBizItemRel> riskBizItemRelList = (List<RiskBizItemRel>) riskBizItemRelFeign.findByPage(ipage, riskBizItemRel).getResult();
		model.addAttribute("riskBizItemRelList", riskBizItemRelList);
		model.addAttribute("formrisk0016", formrisk0016);
		model.addAttribute("query", "");
		return "/component/risk/RiskBizItemRel_List";
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
		FormData formrisk0016 = formService.getFormData("risk0016");
		RiskBizItemRel riskBizItemRel = new RiskBizItemRel();
		List<RiskBizItemRel> riskBizItemRelList = riskBizItemRelFeign.getAll(riskBizItemRel);
		model.addAttribute("riskBizItemRelList", riskBizItemRelList);
		model.addAttribute("formrisk0016", formrisk0016);
		model.addAttribute("query", "");
		return "/component/risk/RiskBizItemRel_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrisk0016 = formService.getFormData("risk0016");
			getFormValue(formrisk0016, getMapByJson(ajaxData));
			if (this.validateFormData(formrisk0016)) {
				RiskBizItemRel riskBizItemRel = new RiskBizItemRel();
				setObjValue(formrisk0016, riskBizItemRel);
				riskBizItemRelFeign.insert(riskBizItemRel);
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag", riskBizItemRelFeign.getAll(riskBizItemRel), null,
						true);
				dataMap.put("tableData", tableHtml);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
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
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrisk0016 = formService.getFormData("risk0016");
			getFormValue(formrisk0016, getMapByJson(ajaxData));
			if (this.validateFormData(formrisk0016)) {
				RiskBizItemRel riskBizItemRel = new RiskBizItemRel();
				setObjValue(formrisk0016, riskBizItemRel);
				riskBizItemRelFeign.update(riskBizItemRel);
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag", riskBizItemRelFeign.getAll(riskBizItemRel), null,
						true);
				dataMap.put("tableData", tableHtml);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
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
	public Map<String, Object> getByIdAjax(String relNo, String itemNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrisk0016 = formService.getFormData("risk0016");
		RiskBizItemRel riskBizItemRel = new RiskBizItemRel();
		riskBizItemRel.setRelNo(relNo);
		riskBizItemRel.setItemNo(itemNo);
		riskBizItemRel = riskBizItemRelFeign.getById(riskBizItemRel);
		getObjValue(formrisk0016, riskBizItemRel, formData);
		if (riskBizItemRel != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String relNo, String itemNo, String tableId)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RiskBizItemRel riskBizItemRel = new RiskBizItemRel();
		riskBizItemRel.setRelNo(relNo);
		riskBizItemRel.setItemNo(itemNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			riskBizItemRel = (RiskBizItemRel) JSONObject.toBean(jb, RiskBizItemRel.class);
			riskBizItemRelFeign.delete(riskBizItemRel);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, "tableTag", riskBizItemRelFeign.getAll(riskBizItemRel), null,
					true);
			dataMap.put("tableData", tableHtml);
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
	 * 获取风险信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRiskInfoAjax")
	@ResponseBody
	public Map<String, Object> getRiskInfoAjax(String cusNo)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 2、风险级别信息
		List<RiskBizItemRel> riskBizItemRelList = riskInterfaceFeign.findByRelNo(cusNo);
		String riskLevel = "-1";
		for (int i = 0; i < riskBizItemRelList.size(); i++) {
			String riskLevelThis = riskBizItemRelList.get(i).getRiskLevel();
			// 风险级别越高，riskLevel越大；如果是风险级别是业务拒绝级，riskLevel为99
			if ("99".equals(riskLevelThis)) {
				riskLevel = riskLevelThis;
				break;
			} else {
				if (Integer.valueOf(riskLevelThis) > Integer.valueOf(riskLevel)) {
					riskLevel = riskLevelThis;
				}
			}
		}
		String riskName = "风险检查通过";
		if (!"-1".equals(riskLevel) && !"0".equals(riskLevel)) {
			Map<String, String> dicMap = new CodeUtils().getMapByKeyName("RISK_PREVENT_LEVEL");
			riskName = dicMap.get(riskLevel);
		}
		dataMap.put("riskLevel", riskLevel);
		dataMap.put("riskName", riskName);
		return dataMap;
	}

}
