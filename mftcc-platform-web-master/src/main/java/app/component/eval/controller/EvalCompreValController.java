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

import app.component.eval.entity.EvalCompreVal;
import app.component.eval.feign.EvalCompreValFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: EvalCompreValController.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Thu Mar 31 06:43:39 GMT 2016
 **/
@Controller
@RequestMapping("/evalCompreVal")
public class EvalCompreValController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private EvalCompreValFeign evalCompreValFeign;
	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(Map<String, Object> dataMap, String tableId, EvalCompreVal evalCompreVal)
			throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", evalCompreValFeign.getAll(evalCompreVal), null, true);
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
		FormData formeval2007 = formService.getFormData("eval2007");
		EvalCompreVal evalCompreVal = new EvalCompreVal();
		Ipage ipage = this.getIpage();
		List<EvalCompreVal> evalCompreValList = (List<EvalCompreVal>) evalCompreValFeign
				.findByPage(ipage, evalCompreVal).getResult();
		model.addAttribute("evalCompreValList", evalCompreValList);
		model.addAttribute("formeval2007", formeval2007);
		model.addAttribute("query", "");
		return "/component/eval/getListPage";
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
		FormData formeval2007 = formService.getFormData("eval2007");
		EvalCompreVal evalCompreVal = new EvalCompreVal();
		List<EvalCompreVal> evalCompreValList = evalCompreValFeign.getAll(evalCompreVal);
		model.addAttribute("evalCompreValList", evalCompreValList);
		model.addAttribute("formeval2007", formeval2007);
		model.addAttribute("query", "");
		return "/component/eval/getListPage";
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
			FormData formeval2007 = formService.getFormData("eval2007");
			getFormValue(formeval2007, getMapByJson(ajaxData));
			if (this.validateFormData(formeval2007)) {
				EvalCompreVal evalCompreVal = new EvalCompreVal();
				setObjValue(formeval2007, evalCompreVal);
				evalCompreValFeign.insert(evalCompreVal);
				getTableData(dataMap, tableId, evalCompreVal);// 获取列表
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
	@SuppressWarnings("static-access")
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String evalAppNo, String adjParm, String evalScenceNo)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			EvalCompreVal evalCompreVal = new EvalCompreVal();
			evalCompreVal = (EvalCompreVal) JSONObject.toBean(jb, EvalCompreVal.class);
			Double finScorePercent = evalCompreVal.getFinScorePercent() / 100;
			evalCompreVal.setFinScorePercent(finScorePercent);
			evalCompreVal.setDlScorePercent(evalCompreVal.getDlScorePercent() / 100);
			evalCompreVal.setDxScorePercent(evalCompreVal.getDxScorePercent() / 100);
			evalCompreVal.setEvalAppNo(evalAppNo);
			evalCompreVal.setEvalScenceNo(evalScenceNo);
			JSONObject adjJb = JSONObject.fromObject(adjParm);
			evalCompreValFeign.update(evalCompreVal, adjJb);
			dataMap.put("entityData", evalCompreVal);
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
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String evalAppNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formeval2007 = formService.getFormData("eval2007");
		EvalCompreVal evalCompreVal = new EvalCompreVal();
		evalCompreVal.setEvalAppNo(evalAppNo);
		evalCompreVal = evalCompreValFeign.getById(evalCompreVal);
		getObjValue(formeval2007, evalCompreVal, formData);
		if (evalCompreVal != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String evalAppNo, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalCompreVal evalCompreVal = new EvalCompreVal();
		evalCompreVal.setEvalAppNo(evalAppNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			evalCompreVal = (EvalCompreVal) JSONObject.toBean(jb, EvalCompreVal.class);
			evalCompreValFeign.delete(evalCompreVal);
			getTableData(dataMap, tableId, evalCompreVal);// 获取列表
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
