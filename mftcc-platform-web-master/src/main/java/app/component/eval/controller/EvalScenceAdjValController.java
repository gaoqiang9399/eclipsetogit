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

import app.component.common.EntityUtil;
import app.component.eval.entity.EvalScenceAdjVal;
import app.component.eval.feign.EvalScenceAdjValFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: EvalScenceAdjValController.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Apr 05 06:46:34 GMT 2016
 **/
@Controller
@RequestMapping("/evalScenceAdjVal")
public class EvalScenceAdjValController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private EvalScenceAdjValFeign evalScenceAdjValFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/eval/EvalScenceAdjVal_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalScenceAdjVal evalScenceAdjVal = new EvalScenceAdjVal();
		try {
			evalScenceAdjVal.setCustomQuery(ajaxData);// 自定义查询参数赋值
			evalScenceAdjVal.setCriteriaList(evalScenceAdjVal, ajaxData);// 我的筛选
			// this.getRoleConditions(evalScenceAdjVal,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = evalScenceAdjValFeign.findByPage(ipage, evalScenceAdjVal);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formeval0002 = formService.getFormData("eval0002");
			getFormValue(formeval0002, getMapByJson(ajaxData));
			if (this.validateFormData(formeval0002)) {
				EvalScenceAdjVal evalScenceAdjVal = new EvalScenceAdjVal();
				setObjValue(formeval0002, evalScenceAdjVal);
				evalScenceAdjValFeign.insert(evalScenceAdjVal);
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
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formeval0002 = formService.getFormData("eval0002");
		getFormValue(formeval0002, getMapByJson(ajaxData));
		EvalScenceAdjVal evalScenceAdjValJsp = new EvalScenceAdjVal();
		setObjValue(formeval0002, evalScenceAdjValJsp);
		EvalScenceAdjVal evalScenceAdjVal = evalScenceAdjValFeign.getById(evalScenceAdjValJsp);
		if (evalScenceAdjVal != null) {
			try {
				evalScenceAdjVal = (EvalScenceAdjVal) EntityUtil.reflectionSetVal(evalScenceAdjVal, evalScenceAdjValJsp,
						getMapByJson(ajaxData));
				evalScenceAdjValFeign.update(evalScenceAdjVal);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
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
	@ResponseBody public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formeval0002 = formService.getFormData("eval0002");
			getFormValue(formeval0002, getMapByJson(ajaxData));
			if (this.validateFormData(formeval0002)) {
				EvalScenceAdjVal evalScenceAdjVal = new EvalScenceAdjVal();
				setObjValue(formeval0002, evalScenceAdjVal);
				evalScenceAdjValFeign.update(evalScenceAdjVal);
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
	 * 
	 * 方法描述： 更新
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-12-23 上午10:36:50
	 */
	@RequestMapping("/updateEvalSceAdjValAjax")
	@ResponseBody public Map<String, Object> updateEvalSceAdjValAjax(String ajaxData,String evalAppNo,String evalScenceNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalScenceAdjVal evalScenceAdjVal = new EvalScenceAdjVal();
		try {
			evalScenceAdjVal.setEvalAppNo(evalAppNo);
			evalScenceAdjVal.setEvalScenceNo(evalScenceNo);
			JSONObject jb = JSONObject.fromObject(ajaxData);
			evalScenceAdjValFeign.updateEvalScenceAdjVal(evalScenceAdjVal, jb);
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
	@ResponseBody public Map<String, Object> getByIdAjax(String ajaxData,String evalAppNo,String evalScenceNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formeval0002 = formService.getFormData("eval0002");
		EvalScenceAdjVal evalScenceAdjVal = new EvalScenceAdjVal();
		evalScenceAdjVal.setEvalScenceNo(evalScenceNo);
		evalScenceAdjVal.setEvalAppNo(evalAppNo);
		evalScenceAdjVal = evalScenceAdjValFeign.getById(evalScenceAdjVal);
		getObjValue(formeval0002, evalScenceAdjVal, formData);
		if (evalScenceAdjVal != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String evalAppNo,String evalScenceNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalScenceAdjVal evalScenceAdjVal = new EvalScenceAdjVal();
		evalScenceAdjVal.setEvalScenceNo(evalScenceNo);
		evalScenceAdjVal.setEvalAppNo(evalAppNo);
		try {
			evalScenceAdjValFeign.delete(evalScenceAdjVal);
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formeval0002 = formService.getFormData("eval0002");
		model.addAttribute("formeval0002", formeval0002);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceAdjVal_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formeval0002 = formService.getFormData("eval0002");
		getFormValue(formeval0002);
		EvalScenceAdjVal evalScenceAdjVal = new EvalScenceAdjVal();
		setObjValue(formeval0002, evalScenceAdjVal);
		evalScenceAdjValFeign.insert(evalScenceAdjVal);
		getObjValue(formeval0002, evalScenceAdjVal);
		this.addActionMessage(model, "保存成功");
		List<EvalScenceAdjVal> evalScenceAdjValList = (List<EvalScenceAdjVal>) evalScenceAdjValFeign
				.findByPage(this.getIpage(), evalScenceAdjVal).getResult();
		model.addAttribute("evalScenceAdjValList", evalScenceAdjValList);
		model.addAttribute("formeval0002", formeval0002);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceAdjVal_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String evalAppNo,String evalScenceNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formeval0001 = formService.getFormData("eval0001");
		getFormValue(formeval0001);
		EvalScenceAdjVal evalScenceAdjVal = new EvalScenceAdjVal();
		evalScenceAdjVal.setEvalScenceNo(evalScenceNo);
		evalScenceAdjVal.setEvalAppNo(evalAppNo);
		evalScenceAdjVal = evalScenceAdjValFeign.getById(evalScenceAdjVal);
		getObjValue(formeval0001, evalScenceAdjVal);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceAdjVal_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String evalAppNo,String evalScenceNo) throws Exception {
		ActionContext.initialize(request, response);
		EvalScenceAdjVal evalScenceAdjVal = new EvalScenceAdjVal();
		evalScenceAdjVal.setEvalScenceNo(evalScenceNo);
		evalScenceAdjVal.setEvalAppNo(evalAppNo);
		evalScenceAdjValFeign.delete(evalScenceAdjVal);
		return getListPage();
	}

}
