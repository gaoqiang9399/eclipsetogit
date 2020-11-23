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

import app.base.User;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusType;
import app.component.cus.feign.MfCusCorpBaseInfoFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.eval.entity.EvalScenceAdjRel;
import app.component.eval.entity.EvalScenceConfig;
import app.component.eval.entity.EvalScenceDlRel;
import app.component.eval.entity.EvalScenceDxRel;
import app.component.eval.entity.EvalScenceFinRel;
import app.component.eval.entity.EvalScenceRestrictRel;
import app.component.eval.entity.EvalScoreGradeConfig;
import app.component.eval.entity.MfEvalGradeCard;
import app.component.eval.feign.EvalScenceAdjRelFeign;
import app.component.eval.feign.EvalScenceConfigFeign;
import app.component.eval.feign.EvalScenceDlRelFeign;
import app.component.eval.feign.EvalScenceDxRelFeign;
import app.component.eval.feign.EvalScenceFinRelFeign;
import app.component.eval.feign.EvalScenceRestrictRelFeign;
import app.component.eval.feign.EvalScoreGradeConfigFeign;
import app.component.eval.feign.MfEvalGradeCardFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: EvalScenceConfigController.java Description:
 * 
 * @author:jzh@dhcc.com.cn
 * @Thu Mar 17 02:46:38 GMT 2016
 **/
@Controller
@RequestMapping("/evalScenceConfig")
public class EvalScenceConfigController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private EvalScenceConfigFeign evalScenceConfigFeign;
	@Autowired
	private EvalScenceFinRelFeign evalScenceFinRelFeign;
	@Autowired
	private EvalScenceDxRelFeign evalScenceDxRelFeign;
	@Autowired
	private EvalScenceDlRelFeign evalScenceDlRelFeign;
	@Autowired
	private EvalScenceAdjRelFeign evalScenceAdjRelFeign;
	@Autowired
	private EvalScenceRestrictRelFeign evalScenceRestrictRelFeign;
	@Autowired
	private EvalScoreGradeConfigFeign evalScoreGradeConfigFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private MfEvalGradeCardFeign mfEvalGradeCardFeign;
	@Autowired
	private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formeval0001 = formService.getFormData("eval0001");
		EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
		model.addAttribute("evalScenceConfig", evalScenceConfig);
		model.addAttribute("formeval0001", formeval0001);
		model.addAttribute("query", "");
		return "/component/eval/getListPage";
	}

	/**
	 * 
	 * 方法描述： 跳转评级场景列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-11-2 下午1:53:00
	 */
	@RequestMapping("/getSetingListPage")
	public String getSetingListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/eval/getSetingListPage";
	}

	/**
	 * 新增
	 */
	@RequestMapping("/input")
	public String input(Model model,String gradeType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formeval0001 = formService.getFormData("eval0001");
		EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
		evalScenceConfig.setUseFlag(BizPubParm.YES_NO_Y);
		evalScenceConfig.setGradeType(gradeType);
		getObjValue(formeval0001, evalScenceConfig);
		JSONArray wayClassMap = new CodeUtils().getJSONArrayByKeyName("TRADE");
		String wayClassItems = wayClassMap.toString().replaceAll("optName", "name").replace("optCode", "id");
		JSONArray projSizeMap = new CodeUtils().getJSONArrayByKeyName("PROJ_SIZE");
		String projSizeItems = projSizeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
		List<MfCusType> cusTypeList = cusInterfaceFeign.getAllList();
		JSONArray cusTypeArray = JSONArray.fromObject(cusTypeList);
        JSONArray cusTypeArrayNew = new JSONArray();
        for (int i = 0; i < cusTypeArray.size(); i++) {
            JSONObject js = new JSONObject();
            js.put("id", cusTypeArray.getJSONObject(i).getString("typeNo"));
            js.put("name", cusTypeArray.getJSONObject(i).getString("typeName"));
            cusTypeArrayNew.add(js);
        }
        dataMap.put("cusTypeArray", cusTypeArrayNew);
		dataMap.put("wayClassItems", wayClassItems);
		dataMap.put("projSizeItems", projSizeItems);
		dataMap.put("gradeType", gradeType);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("formeval0001", formeval0001);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceConfig_insert";
	}

	@RequestMapping("/getEvalScenceConfigSetting")
	public String getEvalScenceConfigSetting(Model model, String evalScenceNo) throws Exception {
		ActionContext.initialize(request, response);
		EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
		evalScenceConfig.setEvalScenceNo(evalScenceNo);
		evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
		model.addAttribute("evalScenceConfig", evalScenceConfig);
		model.addAttribute("evalScenceNo", evalScenceNo);
		model.addAttribute("query", "");
		//return "/component/eval/EvalScenceConfig_Setting";
		return "/component/eval/EvalScenceConfig";
	}

	/**
	 * 
	 * 方法描述： 获得详情
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-11-2 下午3:49:30
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String evalScenceNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formeval0001 = formService.getFormData("eval0001");
		getFormValue(formeval0001);
		EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
		evalScenceConfig.setEvalScenceNo(evalScenceNo);
		evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
		getObjValue(formeval0001, evalScenceConfig);
		JSONArray wayClassMap = new CodeUtils().getJSONArrayByKeyName("TRADE");
		String wayClassItems = wayClassMap.toString().replaceAll("optName", "name").replace("optCode", "id");
		JSONArray projSizeMap = new CodeUtils().getJSONArrayByKeyName("PROJ_SIZE");
		String projSizeItems = projSizeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
		List<MfCusType> cusTypeList = cusInterfaceFeign.getAllList();
		JSONArray cusTypeArray = JSONArray.fromObject(cusTypeList);
		JSONArray cusTypeArrayNew = new JSONArray();
		for (int i = 0; i < cusTypeArray.size(); i++) {
		    JSONObject js = new JSONObject();
            js.put("id", cusTypeArray.getJSONObject(i).getString("typeNo"));
            js.put("name", cusTypeArray.getJSONObject(i).getString("typeName"));
            cusTypeArrayNew.add(js);
		}
		dataMap.put("cusTypeArray", cusTypeArrayNew);
		dataMap.put("wayClassItems", wayClassItems);
		dataMap.put("projSizeItems", projSizeItems);
		model.addAttribute("formeval0001", formeval0001);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceConfig_query";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
		try {
			Ipage ipage = this.getIpage();
			evalScenceConfig.setCustomQuery(ajaxData);
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			ipage = evalScenceConfigFeign.findByPage(ipage, evalScenceConfig);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List<?>) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获得评级模型配置列表html
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-3-2 下午3:10:18
	 */
	@RequestMapping("/getTableHtmlAjax")
	@ResponseBody public Map<String, Object> getTableHtmlAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
		try {
			Ipage ipage = this.getIpage();
			ipage = evalScenceConfigFeign.findByPage(ipage, evalScenceConfig);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr("tableeval0001", "tableTag", (List<?>) ipage.getResult(), ipage, true);
			dataMap.put("tableHtml", tableHtml);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_REFRESH.getMessage());
			throw e;
		}
		return dataMap;
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
		FormData formeval0001 = formService.getFormData("eval0001");
		EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
		List<EvalScenceConfig> evalScenceConfigList = evalScenceConfigFeign.getAll(evalScenceConfig);
		model.addAttribute("formeval0001", formeval0001);
		model.addAttribute("evalScenceConfigList", evalScenceConfigList);
		model.addAttribute("query", "");
		return "/component/eval/getListPage";
	}

	@RequestMapping("/getSetting")
	public String getSetting(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formeval0001 = formService.getFormData("eval0001");
		EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
		List<EvalScenceConfig> evalScenceConfigList = evalScenceConfigFeign.getAll(evalScenceConfig);
		model.addAttribute("evalScenceConfigList", evalScenceConfigList);
		model.addAttribute("formeval0001", formeval0001);
		model.addAttribute("query", "");
		return "/component/eval/getSetting";
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
			FormData formeval0001 = formService.getFormData("eval0001");
			getFormValue(formeval0001, getMapByJson(ajaxData));
			if (this.validateFormData(formeval0001)) {
				EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
				setObjValue(formeval0001, evalScenceConfig);
				evalScenceConfig = evalScenceConfigFeign.insert(evalScenceConfig);
				//getListData(dataMap, evalScenceConfig.getEvalIndexTypeRel(), evalScenceConfig.getEvalScenceNo());
				dataMap.put("evalScenceNo", evalScenceConfig.getEvalScenceNo());
				dataMap.put("evalType", evalScenceConfig.getEvalType());
				dataMap.put("evalClass", evalScenceConfig.getEvalClass());
				dataMap.put("evalScenceConfig", evalScenceConfig);
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
	@ResponseBody public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formeval0001 = formService.getFormData("eval0001");
			getFormValue(formeval0001, getMapByJson(ajaxData));
			if (this.validateFormData(formeval0001)) {
				EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
				setObjValue(formeval0001, evalScenceConfig);
				evalScenceConfig.setEvalScenceLstDate(User.getSysDate(this.getHttpRequest()));
				evalScenceConfigFeign.update(evalScenceConfig);
				getListData(dataMap, evalScenceConfig.getEvalIndexTypeRel(), evalScenceConfig.getEvalScenceNo());
				dataMap.put("evalScenceConfig", evalScenceConfig);
				dataMap.put("evalScenceNo", evalScenceConfig.getEvalScenceNo());
				dataMap.put("evalType", evalScenceConfig.getEvalType());
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

	@RequestMapping("/updateStsAjax")
	@ResponseBody public Map<String, Object> updateStsAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			FormData formeval0001 = formService.getFormData("eval0001");
			getFormValue(formeval0001, jobj);
			EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
			setObjValue(formeval0001, evalScenceConfig);
			evalScenceConfig.setEvalScenceLstDate(User.getSysDate(this.getHttpRequest()));
			evalScenceConfigFeign.update(evalScenceConfig);
			getListData(dataMap, evalScenceConfig.getEvalIndexTypeRel(), evalScenceConfig.getEvalScenceNo());
			dataMap.put("evalScenceNo", evalScenceConfig.getEvalScenceNo());
			dataMap.put("evalType", evalScenceConfig.getEvalType());
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
	@ResponseBody public Map<String, Object> getByIdAjax(String evalScenceNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formeval0001 = formService.getFormData("eval0001");
		EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
		evalScenceConfig.setEvalScenceNo(evalScenceNo);
		evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
		getObjValue(formeval0001, evalScenceConfig, formData);
		if (evalScenceConfig != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * 获取场景应用的所有指标
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdForEvalListAjax")
	@ResponseBody public Map<String, Object> getByIdForEvalListAjax(String evalScenceNo, String evalIndexTypeRel) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
			evalScenceConfig.setEvalScenceNo(evalScenceNo);
			evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
			if (evalScenceConfig != null) {
				Double dlScorePercent = evalScenceConfig.getDlScorePercent();
				Double dxScorePercent = evalScenceConfig.getDxScorePercent();
				if (dlScorePercent != null && dlScorePercent != 0.0) {// 处理百分比选择%
					dlScorePercent *= 100;
				} else {
					dlScorePercent = 0.0;
				}
				if (dxScorePercent != null && dxScorePercent != 0.0) {
					dxScorePercent *= 100;
				} else {
					dxScorePercent = 0.0;
				}
				evalScenceConfig.setDxScorePercent(dxScorePercent);
				evalScenceConfig.setDlScorePercent(dlScorePercent);
				evalIndexTypeRel = evalScenceConfig.getEvalIndexTypeRel();
				dataMap.putAll(evalScenceConfigFeign.getListData(evalScenceNo));
				// getListData(evalIndexTypeRel,evalScenceConfig.getEvalScenceNo());
				dataMap.put("flag", "success");
				dataMap.put("scenceNo", evalScenceNo);
				dataMap.put("evalIndexTypeRel", evalIndexTypeRel);
				dataMap.put("entityData", evalScenceConfig);
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.NO_FILE.getMessage("该条记录已"));
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			e.printStackTrace();
		}
		return dataMap;
	}

	public void getListData(Map<String, Object> dataMap, String evalIndexTypeRel, String evalScenceNo)
			throws Exception {
		Map<String, Object> listData = new HashMap<String, Object>();
		for (String type : evalIndexTypeRel.split("|")) {
			if ("1".equals(type)) {// 财务评分指标
				EvalScenceFinRel evalScenceFinRel = new EvalScenceFinRel();
				List<EvalScenceFinRel> evalScenceFinRelList = evalScenceFinRelFeign.getForScenceNo(evalScenceFinRel);
				listData.put("evalScenceFinRel", evalScenceFinRelList);
			} else if ("2".equals(type)) {// 定量评分指标
				List<EvalScenceDlRel> evalScenceDlRelList = evalScenceDlRelFeign.getForScenceNo(evalScenceNo);
				listData.put("evalScenceDlRel", evalScenceDlRelList);
			} else if ("3".equals(type)) {// 定性评分指标
				List<EvalScenceDxRel> evalScenceDxRelList = evalScenceDxRelFeign.getForScenceNo(evalScenceNo);
				listData.put("evalScenceDxRel", evalScenceDxRelList);
			} else if ("4".equals(type)) {// 调整评分指标
				List<EvalScenceAdjRel> evalScenceAdjRelList = evalScenceAdjRelFeign.getForScenceNo(evalScenceNo);
				listData.put("evalScenceAdjRel", evalScenceAdjRelList);
			} else if ("5".equals(type)) {// 约束等级指标
				List<EvalScenceRestrictRel> evalScenceRestrictRelList = evalScenceRestrictRelFeign
						.getForScenceNo(evalScenceNo);
				listData.put("evalScenceRestrictRel", evalScenceRestrictRelList);
			}else {
			}
		}
		EvalScoreGradeConfig evalScoreGradeConfig = new EvalScoreGradeConfig();
		List<EvalScoreGradeConfig> evalScoreGradeConfigList = evalScoreGradeConfigFeign.getAll(evalScoreGradeConfig);
		listData.put("evalScoreGradeConfig", evalScoreGradeConfigList);
		MfEvalGradeCard mfEvalGradeCard = new MfEvalGradeCard();
		mfEvalGradeCard.setEvalScenceNo(evalScenceNo);
		List<MfEvalGradeCard> evalGradeCardList = mfEvalGradeCardFeign.getEvalGradeCardList(mfEvalGradeCard);
		listData.put("evalGradeCardList", evalGradeCardList);
		dataMap.put("listData", listData);
	}

	@RequestMapping("/deleteAjax")
	@ResponseBody public Map<String, Object> deleteAjax(String evalScenceNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
		evalScenceConfig.setEvalScenceNo(evalScenceNo);
		try {
			String result = cusInterfaceFeign.getScenceIsUsed(evalScenceNo);
			if ("0".equals(result)) {
				evalScenceConfigFeign.delete(evalScenceConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_SCENCE_EVAL.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-11-3 下午3:03:40
	 */
	@RequestMapping("/getScenceIsUsedAjax")
	@ResponseBody public Map<String, Object> getScenceIsUsedAjax(String evalScenceNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
		evalScenceConfig.setEvalScenceNo(evalScenceNo);
		try {
			String result = cusInterfaceFeign.getScenceIsUsed(evalScenceNo);
			if ("0".equals(result)) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_SCENCE_EVAL.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping("/getEvalScenceConfigData")
	@ResponseBody
	public Map<String, Object> getEvalScenceConfigData(String cusType,String cusNo,String cusBaseType,String useType,String gradeType,String evalClass) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> datamap = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (BizPubParm.CUS_TYPE_PERS.equals(cusBaseType)) {
				map.put("wayClass", null);
				map.put("projSize", null);
			} else {
				MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
				mfCusCorpBaseInfo.setCusNo(cusNo);
				mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
				if(mfCusCorpBaseInfo!=null){
					map.put("wayClass", mfCusCorpBaseInfo.getWayMaxClass());// 传大类
					map.put("projSize", mfCusCorpBaseInfo.getProjSize());
				}
			}
			map.put("cusType", cusType);
            map.put("useType",useType);
            map.put("gradeType",gradeType);
            map.put("evalClass",evalClass);
			datamap = evalScenceConfigFeign.getEvalScenceConfigDataMap(map);
			if (datamap != null&&!datamap.isEmpty()) {
				dataMap.put("flag", "success");
				dataMap.put("dataMap", datamap);
			} else {
				dataMap.put("flag", "error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/saveEvalIndecRelAjax")
	@ResponseBody
	public Map<String, Object> saveEvalIndecRelAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formeval0002 = formService.getFormData("eval0002");
			JSONObject jb = JSONObject.fromObject(ajaxData);
			getFormValue(formeval0002, jb);
			if (this.validateFormData(formeval0002)) {
				dataMap = getEvalIndecRelData(ajaxData, formeval0002);
				evalScenceConfigFeign.saveEvalIndecRel(dataMap);
				dataMap.put("flag", "success");
				dataMap.put("entityData", dataMap.get("evalScenceFinRel"));
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
	
	public Map<String, Object> getEvalIndecRelData(String ajaxData,FormData formData) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject jb = JSONObject.fromObject(ajaxData);
		String finCode = (String) jb.get("finCode");
		//勿删
		//String evalPropertyType = finCode.substring(finCode.length()-1, finCode.length());
		String evalPropertyType = BizPubParm.EVAL_INDEX_TYPE_FIN;
		if (BizPubParm.EVAL_INDEX_TYPE_FIN.equals(evalPropertyType)) {
			EvalScenceFinRel evalScenceFinRel = new EvalScenceFinRel();
			setObjValue(formData, evalScenceFinRel);
			if (StringUtil.isNotEmpty(evalScenceFinRel.getFinCode())) {
				evalScenceFinRel.setFinCode(evalScenceFinRel.getFinCode().substring(0, evalScenceFinRel.getFinCode().length()-1));
			}
			resultMap.put("evalScenceFinRel", evalScenceFinRel);
		}else if (BizPubParm.EVAL_INDEX_TYPE_DL.equals(evalPropertyType)) {
			EvalScenceDlRel evalScenceDlRel = new EvalScenceDlRel();
			setObjValue(formData, evalScenceDlRel);
			resultMap.put("evalScenceDlRel", evalScenceDlRel);
		}else if (BizPubParm.EVAL_INDEX_TYPE_DX.equals(evalPropertyType)) {
			EvalScenceDxRel evalScenceDxRel = new EvalScenceDxRel();
			setObjValue(formData, evalScenceDxRel);
			resultMap.put("evalScenceDxRel", evalScenceDxRel);
		}else if (BizPubParm.EVAL_INDEX_TYPE_ADJ.equals(evalPropertyType)) {
			EvalScenceAdjRel evalScenceAdjRel = new EvalScenceAdjRel();
			setObjValue(formData, evalScenceAdjRel);
			resultMap.put("evalScenceAdjRel", evalScenceAdjRel);
		}else if (BizPubParm.EVAL_INDEX_TYPE_RESTRICT.equals(evalPropertyType)) {
			EvalScenceRestrictRel evalScenceRestrictRel = new EvalScenceRestrictRel();
			setObjValue(formData, evalScenceRestrictRel);
			resultMap.put("evalScenceRestrictRel", evalScenceRestrictRel);
		}else {
		}
		resultMap.put("evalPropertyType", evalPropertyType);
		return resultMap;
	}
	/**
	 * 
	 * 方法描述： 获得评级模型配置的评分卡及指标信息
	 * @param evalScenceNo
	 * @param evalIndexTypeRel
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年5月29日 下午8:04:51
	 */
	@RequestMapping("/getEvalScenceIndexRelListAjax")
	@ResponseBody public Map<String, Object> getEvalScenceIndexRelListAjax(String evalScenceNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			EvalScenceConfig evalScenceConfig = new EvalScenceConfig();
			evalScenceConfig.setEvalScenceNo(evalScenceNo);
			evalScenceConfig = evalScenceConfigFeign.getById(evalScenceConfig);
			if (evalScenceConfig != null) {
				dataMap.putAll(evalScenceConfigFeign.getIndexRelListData(evalScenceNo));
				dataMap.put("flag", "success");
				dataMap.put("scenceNo", evalScenceNo);
				dataMap.put("entityData", evalScenceConfig);
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.NO_FILE.getMessage("该条记录已"));
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			e.printStackTrace();
		}
		return dataMap;
	}
}
