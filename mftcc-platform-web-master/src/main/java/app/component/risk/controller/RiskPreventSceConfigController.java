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

import app.component.risk.entity.RiskPreventSceConfig;
import app.component.risk.feign.RiskPreventSceConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: RiskPreventSceConfigAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Thu Mar 03 07:11:49 GMT 2016
 **/
@Controller
@RequestMapping("/riskPreventSceConfig")
public class RiskPreventSceConfigController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private RiskPreventSceConfigFeign riskPreventSceConfigFeign;

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
		FormData formrisk0006 = formService.getFormData("risk0006");
		RiskPreventSceConfig riskPreventSceConfig = new RiskPreventSceConfig();
		Ipage ipage = this.getIpage();
		List<RiskPreventSceConfig> riskPreventSceConfigList = (List<RiskPreventSceConfig>) riskPreventSceConfigFeign
				.findByPage(ipage, riskPreventSceConfig).getResult();
		model.addAttribute("riskPreventSceConfigList", riskPreventSceConfigList);
		model.addAttribute("formrisk0006", formrisk0006);
		model.addAttribute("query", "");
		return "/component/risk/RiskPreventSceConfig_List";
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
		FormData formrisk0006 = formService.getFormData("risk0006");
		RiskPreventSceConfig riskPreventSceConfig = new RiskPreventSceConfig();
		List<RiskPreventSceConfig> riskPreventSceConfigList = riskPreventSceConfigFeign.getAll(riskPreventSceConfig);
		model.addAttribute("riskPreventSceConfigList", riskPreventSceConfigList);
		model.addAttribute("formrisk0006", formrisk0006);
		model.addAttribute("query", "");
		return "/component/risk/RiskPreventSceConfig_List";
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
			FormData formrisk0006 = formService.getFormData("risk0006");
			getFormValue(formrisk0006, getMapByJson(ajaxData));
			if (this.validateFormData(formrisk0006)) {
				RiskPreventSceConfig riskPreventSceConfig = new RiskPreventSceConfig();
				setObjValue(formrisk0006, riskPreventSceConfig);
				riskPreventSceConfigFeign.insert(riskPreventSceConfig);
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag",
						riskPreventSceConfigFeign.getAll(riskPreventSceConfig), null, true);
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
			FormData formrisk0006 = formService.getFormData("risk0006");
			getFormValue(formrisk0006, getMapByJson(ajaxData));
			if (this.validateFormData(formrisk0006)) {
				RiskPreventSceConfig riskPreventSceConfig = new RiskPreventSceConfig();
				setObjValue(formrisk0006, riskPreventSceConfig);
				riskPreventSceConfigFeign.update(riskPreventSceConfig);
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag",
						riskPreventSceConfigFeign.getAll(riskPreventSceConfig), null, true);
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
	public Map<String, Object> getByIdAjax(String ajaxData, String dime, String scNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrisk0006 = formService.getFormData("risk0006");
		RiskPreventSceConfig riskPreventSceConfig = new RiskPreventSceConfig();
		riskPreventSceConfig.setDime(dime);
		riskPreventSceConfig.setScNo(scNo);
		riskPreventSceConfig = riskPreventSceConfigFeign.getById(riskPreventSceConfig);
		getObjValue(formrisk0006, riskPreventSceConfig, formData);
		if (riskPreventSceConfig != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String dime, String scNo, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RiskPreventSceConfig riskPreventSceConfig = new RiskPreventSceConfig();
		riskPreventSceConfig.setDime(dime);
		riskPreventSceConfig.setScNo(scNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			riskPreventSceConfig = (RiskPreventSceConfig) JSONObject.toBean(jb, RiskPreventSceConfig.class);
			riskPreventSceConfigFeign.delete(riskPreventSceConfig);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, "tableTag",
					riskPreventSceConfigFeign.getAll(riskPreventSceConfig), null, true);
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

}
