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

import app.component.eval.entity.EvalScoreGradeConfig;
import app.component.eval.feign.EvalScoreGradeConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import net.sf.json.JSONObject;

/**
 * Title: EvalScoreGradeConfigAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Wed Apr 27 06:10:07 GMT 2016
 **/
@Controller
@RequestMapping("/evalScoreGradeConfig")
public class EvalScoreGradeConfigController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private EvalScoreGradeConfigFeign evalScoreGradeConfigFeign;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(Map<String, Object> dataMap, String tableId, EvalScoreGradeConfig evalScoreGradeConfig)
			throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", evalScoreGradeConfigFeign.getAll(evalScoreGradeConfig),
				null, true);
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
		FormData formeval0007 = formService.getFormData("eval0007");
		EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("evalScoreGradeConfig",evalScoreGradeConfig));
		List<EvalScoreGradeConfig> evalScoreGradeConfigList = (List<EvalScoreGradeConfig>) evalScoreGradeConfigFeign
				.findByPage(ipage).getResult();
		model.addAttribute("formeval0007", formeval0007);
		model.addAttribute("evalScoreGradeConfigList", evalScoreGradeConfigList);
		model.addAttribute("query", "");
		return "/component/eval/EvalScoreGradeConfig_List";
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
		FormData formeval0007 = formService.getFormData("eval0007");
		EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
		List<EvalScoreGradeConfig> evalScoreGradeConfigList = evalScoreGradeConfigFeign.getAll(evalScoreGradeConfig);
		model.addAttribute("formeval0007", formeval0007);
		model.addAttribute("evalScoreGradeConfigList", evalScoreGradeConfigList);
		model.addAttribute("query", "");
		return "/component/eval/EvalScoreGradeConfig_List";
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
			FormData formeval0007 = formService.getFormData("eval0007");
			getFormValue(formeval0007, getMapByJson(ajaxData));
			if (this.validateFormData(formeval0007)) {
				EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
				setObjValue(formeval0007, evalScoreGradeConfig);
				evalScoreGradeConfigFeign.insert(evalScoreGradeConfig);
				getTableData(dataMap, tableId, evalScoreGradeConfig);// 获取列表
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

	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formeval0007 = formService.getFormData("eval0007");
			JSONObject jb = JSONObject.fromObject(ajaxData);
			getFormValue(formeval0007, jb);
			if (this.validateFormData(formeval0007)) {
				EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
				setObjValue(formeval0007, evalScoreGradeConfig);
				evalScoreGradeConfigFeign.update(evalScoreGradeConfig);
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
	 * 方法描述： 列表上直接
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-3-4 下午2:44:09
	 */
	@RequestMapping(value = "/updateBytableAjax")
	@ResponseBody
	public Map<String, Object> updateBytableAjax(String configNo, Double opScore1, Double opScore2) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
			evalScoreGradeConfig.setConfigNo(configNo);
			evalScoreGradeConfig.setOpScore1(opScore1);
			evalScoreGradeConfig.setOpScore2(opScore2);
			evalScoreGradeConfigFeign.update(evalScoreGradeConfig);

			evalScoreGradeConfig = new EvalScoreGradeConfig();
			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("evalScoreGradeConfig",evalScoreGradeConfig));
			ipage = evalScoreGradeConfigFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr("tableeval0007", "tableTag", (List<?>) ipage.getResult(), ipage, true);
			dataMap.put("tableHtml", tableHtml);
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
	 * 
	 * 方法描述： 更新综合评价信息
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-3-3 下午1:50:08
	 */
	@RequestMapping(value = "/updateEvalAssessAjax")
	@ResponseBody
	public Map<String, Object> updateEvalAssessAjax(String ajaxData, String formId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
		try {
			FormData formeval = formService.getFormData(formId);
			getFormValue(formeval, getMapByJson(ajaxData));
			if (this.validateFormData(formeval)) {
				setObjValue(formeval, evalScoreGradeConfig);
				evalScoreGradeConfigFeign.update(evalScoreGradeConfig);
				if (!"".equals(evalScoreGradeConfig.getCreditAmt()) && evalScoreGradeConfig.getCreditAmt() != null) {
					evalScoreGradeConfig.setCreditAmt(MathExtend.divide(evalScoreGradeConfig.getCreditAmt(), 10000, 2));
				}
				dataMap.put("gradeConfig", evalScoreGradeConfig);
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
	public Map<String, Object> getByIdAjax(String evalScenceNo, String configNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formeval0007 = formService.getFormData("eval0007");
		EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
		evalScoreGradeConfig.setEvalScenceNo(evalScenceNo);
		evalScoreGradeConfig.setConfigNo(configNo);
		evalScoreGradeConfig = evalScoreGradeConfigFeign.getById(evalScoreGradeConfig);
		getObjValue(formeval0007, evalScoreGradeConfig, formData);
		if (evalScoreGradeConfig != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 是否已配置该分数等级
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-3-3 下午2:56:16
	 */
	@RequestMapping(value = "/getIfEvalLevelConfigAjax")
	@ResponseBody
	public Map<String, Object> getIfEvalLevelConfigAjax(String evalLevel) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
		evalScoreGradeConfig.setEvalLevel(evalLevel);
		evalScoreGradeConfig = evalScoreGradeConfigFeign.getById(evalScoreGradeConfig);
		if (evalScoreGradeConfig != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获得详情
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-3-3 上午10:12:06
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String formId, String configNo,String cusType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formeval = formService.getFormData(formId);
		EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
		evalScoreGradeConfig.setConfigNo(configNo);
		evalScoreGradeConfig.setCusType(cusType);
		evalScoreGradeConfig = evalScoreGradeConfigFeign.getById(evalScoreGradeConfig);
		getObjValue(formeval, evalScoreGradeConfig);
		model.addAttribute("formeval", formeval);
		model.addAttribute("formId", formId);
		model.addAttribute("query", "");
		return "/component/eval/EvalScoreGrade_Detail";
	}

	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData,String tableId,String evalScenceNo,String configNo) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
		evalScoreGradeConfig.setEvalScenceNo(evalScenceNo);
		evalScoreGradeConfig.setConfigNo(configNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			evalScoreGradeConfig = (EvalScoreGradeConfig)JSONObject.toBean(jb, EvalScoreGradeConfig.class);
			evalScoreGradeConfigFeign.delete(evalScoreGradeConfig);
			getTableData( dataMap, tableId, evalScoreGradeConfig);//获取列表
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}


	@RequestMapping(value = "/getListPage1")
	public String getListPage1(Model model, String cusType) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("cusType",cusType);
		model.addAttribute("query", "");
		return "component/cus/MfCusConfigEvalScore_List";

	}



	@RequestMapping(value = "/getEvalScoreGradeConfig")
	@ResponseBody
	public Map<String, Object> getEvalScoreGradeConfig(Integer pageNo, Integer pageSize, String tableId,String ajaxData,String cusType,String tableType) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> map  =  new HashMap<>();
		EvalScoreGradeConfig    evalScoreGradeConfig  =   new EvalScoreGradeConfig();
		try {
			evalScoreGradeConfig.setCustomQuery(ajaxData);// 自定义查询参数赋值
			evalScoreGradeConfig.setCustomSorts(ajaxData);
			evalScoreGradeConfig.setCriteriaList(evalScoreGradeConfig, ajaxData);// 我的筛选
			evalScoreGradeConfig.setCusType(cusType);
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("evalScoreGradeConfig",evalScoreGradeConfig));

			ipage = evalScoreGradeConfigFeign.findByPage(ipage);

			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return  dataMap;
	}
}
