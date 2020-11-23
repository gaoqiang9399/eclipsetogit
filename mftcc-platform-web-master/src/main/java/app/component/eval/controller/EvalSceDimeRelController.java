package app.component.eval.controller;

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

import app.component.eval.entity.EvalSceDimeRel;
import app.component.eval.feign.EvalSceDimeRelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: EvalSceDimeRelController.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Thu Mar 31 12:04:33 GMT 2016
 **/
@Controller
@RequestMapping("/evalSceDimeRel")
public class EvalSceDimeRelController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private EvalSceDimeRelFeign evalSceDimeRelFeign;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(Map<String, Object> dataMap, String tableId, EvalSceDimeRel evalSceDimeRel)
			throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", evalSceDimeRelFeign.getAll(evalSceDimeRel), null, true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formeval3002 = formService.getFormData("eval3002");
		EvalSceDimeRel evalSceDimeRel = new EvalSceDimeRel();
		Ipage ipage = this.getIpage();
		List<EvalSceDimeRel> evalSceDimeRelList = (List<EvalSceDimeRel>) evalSceDimeRelFeign
				.findByPage(ipage, evalSceDimeRel).getResult();
		model.addAttribute("evalSceDimeRelList", evalSceDimeRelList);
		model.addAttribute("formeval3002", formeval3002);
		model.addAttribute("query", "");
		return "/component/eval/EvalSceDimeRel_List";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListAll")
	public String getListAll(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formeval3002 = formService.getFormData("eval3002");
		EvalSceDimeRel evalSceDimeRel = new EvalSceDimeRel();
		List<EvalSceDimeRel> evalSceDimeRelList = evalSceDimeRelFeign.getAll(evalSceDimeRel);
		model.addAttribute("evalSceDimeRelList", evalSceDimeRelList);
		model.addAttribute("formeval3002", formeval3002);
		model.addAttribute("query", "");
		return "/component/eval/EvalSceDimeRel_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formeval3002 = formService.getFormData("eval3002");
			getFormValue(formeval3002, getMapByJson(ajaxData));
			if (this.validateFormData(formeval3002)) {
				EvalSceDimeRel evalSceDimeRel = new EvalSceDimeRel();
				setObjValue(formeval3002, evalSceDimeRel);
				evalSceDimeRelFeign.insert(evalSceDimeRel);
				getTableData(dataMap, tableId, evalSceDimeRel);// 获取列表
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
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formeval3002 = formService.getFormData("eval3002");
			getFormValue(formeval3002, getMapByJson(ajaxData));
			if (this.validateFormData(formeval3002)) {
				EvalSceDimeRel evalSceDimeRel = new EvalSceDimeRel();
				setObjValue(formeval3002, evalSceDimeRel);
				evalSceDimeRelFeign.update(evalSceDimeRel);
				getTableData(dataMap, tableId, evalSceDimeRel);// 获取列表
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
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String scenceNo, String optCode) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formeval3002 = formService.getFormData("eval3002");
		EvalSceDimeRel evalSceDimeRel = new EvalSceDimeRel();
		evalSceDimeRel.setScenceNo(scenceNo);
		evalSceDimeRel.setOptCode(optCode);
		evalSceDimeRel = evalSceDimeRelFeign.getById(evalSceDimeRel);
		getObjValue(formeval3002, evalSceDimeRel, formData);
		if (evalSceDimeRel != null) {
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
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String scenceNo, String optCode, String tableId)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalSceDimeRel evalSceDimeRel = new EvalSceDimeRel();
		evalSceDimeRel.setScenceNo(scenceNo);
		evalSceDimeRel.setOptCode(optCode);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			evalSceDimeRel = (EvalSceDimeRel) JSONObject.toBean(jb, EvalSceDimeRel.class);
			evalSceDimeRelFeign.delete(evalSceDimeRel);
			getTableData(dataMap, tableId, evalSceDimeRel);// 获取列表
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