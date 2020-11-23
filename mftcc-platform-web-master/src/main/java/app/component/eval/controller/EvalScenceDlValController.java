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

import app.component.eval.entity.EvalScenceDlVal;
import app.component.eval.feign.EvalScenceDlValFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: EvalScenceDlValAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Thu Mar 31 06:37:10 GMT 2016
 **/
@Controller
@RequestMapping("/evalScenceDlVal")
public class EvalScenceDlValController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private EvalScenceDlValFeign evalScenceDlValFeign;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(Map<String, Object> dataMap,String tableId,EvalScenceDlVal evalScenceDlVal) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", evalScenceDlValFeign.getAll(evalScenceDlVal), null,
				true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formeval2001 = formService.getFormData("eval2001");
		EvalScenceDlVal evalScenceDlVal = new EvalScenceDlVal();
		Ipage ipage = this.getIpage();
		List<EvalScenceDlVal> evalScenceDlValList = (List<EvalScenceDlVal>) evalScenceDlValFeign
				.findByPage(ipage, evalScenceDlVal).getResult();
		model.addAttribute("evalScenceDlValList", evalScenceDlValList);
		model.addAttribute("formeval2001", formeval2001);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceDlVal_List";
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
		FormData formeval2001 = formService.getFormData("eval2001");
		EvalScenceDlVal evalScenceDlVal = new EvalScenceDlVal();
		List<EvalScenceDlVal> evalScenceDlValList = evalScenceDlValFeign.getAll(evalScenceDlVal);
		model.addAttribute("evalScenceDlValList", evalScenceDlValList);
		model.addAttribute("formeval2001", formeval2001);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceDlVal_List";
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
			FormData formeval2001 = formService.getFormData("eval2001");
			getFormValue(formeval2001, getMapByJson(ajaxData));
			if (this.validateFormData(formeval2001)) {
				EvalScenceDlVal evalScenceDlVal = new EvalScenceDlVal();
				setObjValue(formeval2001, evalScenceDlVal);
				evalScenceDlValFeign.insert(evalScenceDlVal);
				getTableData(dataMap,tableId,evalScenceDlVal);// 获取列表
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
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formeval2001 = formService.getFormData("eval2001");
			getFormValue(formeval2001, getMapByJson(ajaxData));
			if (this.validateFormData(formeval2001)) {
				EvalScenceDlVal evalScenceDlVal = new EvalScenceDlVal();
				setObjValue(formeval2001, evalScenceDlVal);
				evalScenceDlValFeign.update(evalScenceDlVal);
				getTableData(dataMap,tableId,evalScenceDlVal);// 获取列表
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
	public Map<String, Object> getByIdAjax(String evalScenceNo, String evalAppNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formeval2001 = formService.getFormData("eval2001");
		EvalScenceDlVal evalScenceDlVal = new EvalScenceDlVal();
		evalScenceDlVal.setEvalScenceNo(evalScenceNo);
		evalScenceDlVal.setEvalAppNo(evalAppNo);
		evalScenceDlVal = evalScenceDlValFeign.getById(evalScenceDlVal);
		getObjValue(formeval2001, evalScenceDlVal, formData);
		if (evalScenceDlVal != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String evalScenceNo, String evalAppNo,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalScenceDlVal evalScenceDlVal = new EvalScenceDlVal();
		evalScenceDlVal.setEvalScenceNo(evalScenceNo);
		evalScenceDlVal.setEvalAppNo(evalAppNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			evalScenceDlVal = (EvalScenceDlVal) JSONObject.toBean(jb, EvalScenceDlVal.class);
			evalScenceDlValFeign.delete(evalScenceDlVal);
			getTableData(dataMap,tableId,evalScenceDlVal);// 获取列表
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
