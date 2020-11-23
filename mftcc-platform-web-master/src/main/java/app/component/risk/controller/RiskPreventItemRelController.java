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

import app.component.risk.entity.RiskPreventItemRel;
import app.component.risk.feign.RiskPreventItemRelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: RiskPreventItemRelAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Thu Mar 03 07:18:05 GMT 2016
 **/
@Controller
@RequestMapping("/riskPreventItemRel")
public class RiskPreventItemRelController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private RiskPreventItemRelFeign riskPreventItemRelFeign;


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
		FormData formrisk0010 = formService.getFormData("risk0010");
		RiskPreventItemRel riskPreventItemRel = new RiskPreventItemRel();
		Ipage ipage = this.getIpage();
		List<RiskPreventItemRel> riskPreventItemRelList = (List<RiskPreventItemRel>) riskPreventItemRelFeign
				.findByPage(ipage, riskPreventItemRel).getResult();
		model.addAttribute("riskPreventItemRelList", riskPreventItemRelList);
		model.addAttribute("formrisk0010", formrisk0010);
		model.addAttribute("query", "");
		return "/component/risk/RiskPreventItemRel_List";
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
		FormData formrisk0010 = formService.getFormData("risk0010");
		RiskPreventItemRel riskPreventItemRel = new RiskPreventItemRel();
		List<RiskPreventItemRel> riskPreventItemRelList = riskPreventItemRelFeign.getAll(riskPreventItemRel);
		model.addAttribute("riskPreventItemRelList", riskPreventItemRelList);
		model.addAttribute("formrisk0010", formrisk0010);
		model.addAttribute("query", "");
		return "/component/risk/RiskPreventItemRel_List";
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
			FormData formrisk0010 = formService.getFormData("risk0010");
			getFormValue(formrisk0010, getMapByJson(ajaxData));
			if (this.validateFormData(formrisk0010)) {
				RiskPreventItemRel riskPreventItemRel = new RiskPreventItemRel();
				setObjValue(formrisk0010, riskPreventItemRel);
				riskPreventItemRelFeign.insert(riskPreventItemRel);
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag",
						riskPreventItemRelFeign.getAll(riskPreventItemRel), null, true);
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
			FormData formrisk0010 = formService.getFormData("risk0010");
			getFormValue(formrisk0010, getMapByJson(ajaxData));
			if (this.validateFormData(formrisk0010)) {
				RiskPreventItemRel riskPreventItemRel = new RiskPreventItemRel();
				setObjValue(formrisk0010, riskPreventItemRel);
				riskPreventItemRelFeign.update(riskPreventItemRel);
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag",
						riskPreventItemRelFeign.getAll(riskPreventItemRel), null, true);
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
	public Map<String, Object> getByIdAjax(String ajaxData, String itemNo, String genNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrisk0010 = formService.getFormData("risk0010");
		RiskPreventItemRel riskPreventItemRel = new RiskPreventItemRel();
		riskPreventItemRel.setItemNo(itemNo);
		riskPreventItemRel.setGenNo(genNo);
		riskPreventItemRel = riskPreventItemRelFeign.getById(riskPreventItemRel);
		getObjValue(formrisk0010, riskPreventItemRel, formData);
		if (riskPreventItemRel != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String itemNo, String genNo, String tableId)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RiskPreventItemRel riskPreventItemRel = new RiskPreventItemRel();
		riskPreventItemRel.setItemNo(itemNo);
		riskPreventItemRel.setGenNo(genNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			riskPreventItemRel = (RiskPreventItemRel) JSONObject.toBean(jb, RiskPreventItemRel.class);
			riskPreventItemRelFeign.delete(riskPreventItemRel);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, "tableTag", riskPreventItemRelFeign.getAll(riskPreventItemRel),
					null, true);
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
