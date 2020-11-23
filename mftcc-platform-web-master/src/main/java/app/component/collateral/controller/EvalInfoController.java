package app.component.collateral.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.app.entity.MfBusApplyHis;
import app.component.app.feign.MfBusApplyFeign;
import app.component.app.feign.MfBusApplyHisFeign;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.entity.EvalInfo;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.collateral.feign.EvalInfoFeign;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.collateral.feign.MfBusCollateralRelFeign;
import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.collateral.feign.MfCollateralTableFeign;
import app.component.collateral.feign.FunGuGuFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.wkf.AppConstant;
import cn.mftcc.util.DateUtil;
import com.google.gson.Gson;
import config.YmlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.EntityUtil;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONObject;

/**
 * Title: EvalInfoController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Mar 08 11:20:33 CST 2017
 **/
@Controller
@RequestMapping("/evalInfo")
public class EvalInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private EvalInfoFeign evalInfoFeign;
	@Autowired
	private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;
	@Autowired
	private MfBusCollateralRelFeign mfBusCollateralRelFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;
	@Autowired
	private MfCollateralTableFeign mfCollateralTableFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private MfBusApplyHisFeign mfBusApplyHisFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
    private MfBusApplyFeign  mfBusApplyFeign;

	@Autowired
	private FunGuGuFeign funGuGuFeign;

	public static Logger log = LoggerFactory.getLogger(PledgeBaseInfoController.class);
	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */

	private void getTableData(Map<String, Object> dataMap, String tableId, EvalInfo evalInfo) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", evalInfoFeign.getAll(evalInfo), null, true);
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
	public String getListPage(Model model, String collateralNo) throws Exception {
		ActionContext.initialize(request, response);
		EvalInfo evalInfo = new EvalInfo();
		evalInfo.setCollateralId(collateralNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("evalInfo",evalInfo));
		List<EvalInfo> evalInfoList = (List<EvalInfo>) evalInfoFeign.findByPage(ipage).getResult();
		model.addAttribute("evalInfoList", evalInfoList);
		return "/component/collateral/EvalInfo_List";
	}

	@RequestMapping("/getInsertPage")
	public String getInsertPage(Model model, String collateralId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String evalId = WaterIdUtil.getWaterId();
		EvalInfo ei = new EvalInfo();
		ei.setEvalId(evalId);
		ei.setCollateralId(collateralId);
		FormData formdlcollateraleval0002 = formService.getFormData("dlcollateraleval0002");
		getObjValue(formdlcollateraleval0002, ei);
		model.addAttribute("formdlcollateraleval0002", formdlcollateraleval0002);
		model.addAttribute("query", "");
		return "/component/collateral/Insert_Page";
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
		FormData formdlcollateraleval0002 = formService.getFormData("dlcollateraleval0002");
		EvalInfo evalInfo = new EvalInfo();
		List<EvalInfo> evalInfoList = evalInfoFeign.getAll(evalInfo);
		model.addAttribute("formdlcollateraleval0002", formdlcollateraleval0002);
		model.addAttribute("evalInfoList", evalInfoList);
		model.addAttribute("query", "");
		return "/component/collateral/EvalInfo_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		@SuppressWarnings("rawtypes")
		Map map = getMapByJson(ajaxData);
		try {
			FormData formdlevalinfo0002 = formService.getFormData(map.get("formId").toString());
			getFormValue(formdlevalinfo0002, map);
			if (this.validateFormData(formdlevalinfo0002)) {
				EvalInfo evalInfo = new EvalInfo();
				setObjValue(formdlevalinfo0002, evalInfo);
				String collateralId = evalInfo.getCollateralId();
				evalInfoFeign.insert(evalInfo);

				PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
				pledgeBaseInfo.setPledgeNo(collateralId);
				pledgeBaseInfo = pledgeBaseInfoFeign.getPledgeBaseInfo(pledgeBaseInfo);
				// getTableData();//获取列表

				// PledgeBase pledgeBase = new PledgeBase();
				// pledgeBase.setPledgeId(evalInfo.getCollateralId());
				// pledgeBase = pledgeBaseFeign.getById(pledgeBase);

				// MfCollateralTable mfCollateralTable = new
				// MfCollateralTable();
				// mfCollateralTable.setCollateralNo(pledgeBase.getPledgeId());
				// mfCollateralTable.setCollateralType(pledgeBase.getPledgeType());
				// mfCollateralTable.setDataFullFlag("1");
				// mfCollateralTable.setTableName("certi_info");
				// mfCollateralTableFeign.update(mfCollateralTable);

				// 获得基本信息的展示表单ID，并将列表解析
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
						.getByPledgeImPawnType(evalInfo.getClassId(), "EvalInfoAction", "");
				String tableId = null;
				if (mfCollateralFormConfig == null) {
					// Log.error("押品评估配置信息没有找到。");
				} else {
					tableId = mfCollateralFormConfig.getShowModelDef();
				}

				if (StringUtil.isEmpty(tableId)) {
					// Log.error("押品类型为" + mfCollateralFormConfig.getFormType()
					// + "的EvalInfoController列表table" + tableId
					// + ".xml文件不存在");
				}

				evalInfo = new EvalInfo();
				evalInfo.setCollateralId(collateralId);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("evalInfo",evalInfo));
				JsonTableUtil jtu = new JsonTableUtil();
				
				String tableHtml = jtu.getJsonStr("table" + tableId, "tableTag",
						(List<EvalInfo>) evalInfoFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");

				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				dataMap.put("classId", pledgeBaseInfo.getClassId());
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
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlevalinfo0003 = formService.getFormData("dlevalinfo0002");
			getFormValue(formdlevalinfo0003, getMapByJson(ajaxData));
			if (this.validateFormData(formdlevalinfo0003)) {
				EvalInfo evalInfo = new EvalInfo();
				setObjValue(formdlevalinfo0003, evalInfo);
				evalInfoFeign.update(evalInfo);

				// getTableData();//获取列表

				// PledgeBase pledgeBase = new PledgeBase();
				// pledgeBase.setPledgeId(evalInfo.getCollateralId());
				// pledgeBase = pledgeBaseFeign.getById(pledgeBase);

				// 获得基本信息的展示表单ID，并将列表解析
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
						.getByPledgeImPawnType(evalInfo.getClassId(), "EvalInfoAction", "");
				String tableId = null;
				if (mfCollateralFormConfig == null) {

				} else {
					tableId = mfCollateralFormConfig.getShowModelDef();
				}

				if (StringUtil.isEmpty(tableId)) {
					// Log.error("押品类型为" + mfCollateralFormConfig.getFormType()
					// + "的EvalInfoController列表table" + tableId
					// + ".xml文件不存在");
				}

				String collateralNo = evalInfo.getCollateralId();
				evalInfo = new EvalInfo();
				evalInfo.setCollateralId(collateralNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("evalInfo",evalInfo));
				JsonTableUtil jtu = new JsonTableUtil();
				@SuppressWarnings("unchecked")
				String tableHtml = jtu.getJsonStr("table" + tableId, "tableTag",
						(List<EvalInfo>) evalInfoFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");

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
	public Map<String, Object> getByIdAjax(String evalId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdlcollateraleval0002 = formService.getFormData("dlcollateraleval0002");
		EvalInfo evalInfo = new EvalInfo();
		evalInfo.setEvalId(evalId);
		evalInfo = evalInfoFeign.getById(evalInfo);
		getObjValue(formdlcollateraleval0002, evalInfo, formData);
		if (evalInfo != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String evalId, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalInfo evalInfo = new EvalInfo();
		evalInfo.setEvalId(evalId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			evalInfo = (EvalInfo) JSONObject.toBean(jb, EvalInfo.class);
			evalInfoFeign.delete(evalInfo);
			getTableData(dataMap, tableId, evalInfo);// 获取列表
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
	public String input(Model model, String collateralNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();

		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(collateralNo);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();
		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,"EvalInfoAction", "");

		String formId = null;
		if (mfCollateralFormConfig == null) {

		} else {
			formId = mfCollateralFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的EvalInfoController表单信息没有查询到");
		} else {
			EvalInfo evalInfo = new EvalInfo();
			evalInfo.setCollateralId(collateralNo);
			String evalId = WaterIdUtil.getWaterId();
			evalInfo.setEvalId(evalId);
			evalInfo.setClassId(classId);
			FormData formdlevalinfo0002 = formService.getFormData(formId);
			if (formdlevalinfo0002.getFormId() == null) {
				// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
				// "的EvalInfoController表单form" + formId
				// + ".xml文件不存在");
			} else {

				getFormValue(formdlevalinfo0002);
				//流转类型押品评估模块 评估价值自动计算
				EvalInfo evalInfo1 = new EvalInfo();
				evalInfo1.setCollateralId(collateralNo);
				List<EvalInfo> list = evalInfoFeign.getAll(evalInfo1);
				if (list.size() > 0 && StringUtil.isNotEmpty(list.get(0).getExt1()) && "1".equals(list.get(0).getExt1())){
					evalInfo.setMortRate(list.get(0).getMortRate());
					evalInfo.setEvalAmount(list.get(0).getEvalAmount());
					evalInfo.setMortRate(list.get(0).getMortRate());
					evalInfo.setValidTerm(list.get(0).getValidTerm());
				}

				getObjValue(formdlevalinfo0002, evalInfo);
				model.addAttribute("formdlevalinfo0002", formdlevalinfo0002);
			}
		}

		model.addAttribute("query", "");
		return "/component/collateral/EvalInfo_Insert";
	}

	/**
	 * 流程新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/inputBus")
	public String inputBus(Model model, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();

		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setCusNo(cusNo);
		pledgeBaseInfo = pledgeBaseInfoFeign.getByCusNo(pledgeBaseInfo);

		EvalInfo evalInfo = new EvalInfo();
		evalInfo.setCollateralId(pledgeBaseInfo.getPledgeNo());
		String evalId = WaterIdUtil.getWaterId();
		evalInfo.setEvalId(evalId);

		FormData formdlevalinfo0002 = formService.getFormData("dlevalinfo0002");

		getFormValue(formdlevalinfo0002);
		getObjValue(formdlevalinfo0002, evalInfo);
		model.addAttribute("formdlevalinfo0002", formdlevalinfo0002);
		model.addAttribute("query", "");
		return "/component/collateral/EvalInfo_zhInsert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String evalId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();

		EvalInfo evalInfo = new EvalInfo();
		evalInfo.setEvalId(evalId);
		evalInfo = evalInfoFeign.getById(evalInfo);

		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(evalInfo.getCollateralId());
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();
		evalInfo.setClassId(classId);// 修改时候用
		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"EvalInfoAction", "");
		String formId = null;
		if (mfCollateralFormConfig == null) {

		} else {
			if ("1".equals(mfCollateralFormConfig.getShowType())) {
				formId = mfCollateralFormConfig.getShowModelDef();
			} else {
				formId = mfCollateralFormConfig.getAddModelDef();
			}
		}

		if (StringUtil.isEmpty(formId)) {
			// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的EvalInfoController表单信息没有查询到");
		} else {
			FormData formdlevalinfo0003 = formService.getFormData(formId);
			if (formdlevalinfo0003.getFormId() == null) {
				// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
				// "的EvalInfoController表单form" + formId
				// + ".xml文件不存在");
			} else {
				getFormValue(formdlevalinfo0003);
				getObjValue(formdlevalinfo0003, evalInfo);
				model.addAttribute("formdlevalinfo0003", formdlevalinfo0003);
			}
		}

		model.addAttribute("query", "");
		return "/component/collateral/EvalInfo_Detail";
	}

	/**
	 * 获取押品最近的一次评估
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getLatestEvalByPleNoAjax")
	@ResponseBody
	public Map<String, Object> getLatestEvalByPleNoAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String pledgeNo = request.getParameter("pledgeNo");
			EvalInfo evalInfo = new EvalInfo();
			evalInfo.setCollateralId(pledgeNo);
			evalInfo = evalInfoFeign.getLatest(evalInfo);
			dataMap.put("evalInfo", evalInfo);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

	// 列表展示详情，单字段编辑
	@RequestMapping("/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String evalId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
//		this.setIfBizManger("2");
		request.setAttribute("ifBizManger", "2");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		EvalInfo evalInfo = new EvalInfo();
		evalInfo.setEvalId(evalId);
		evalInfo = evalInfoFeign.getById(evalInfo);
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(evalInfo.getCollateralId());
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();
		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"EvalInfoAction", "");
		String appId = mfBusCollateralRelFeign.getAppIdByCollateralId(evalInfo.getCollateralId());
		MfBusApply mfBusApply = new MfBusApply();
		String formId = null;
		String query = "";
		if(StringUtil.isNotEmpty(appId)){
			if(!appId.contains("SX")) {
				mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
				//判断押品表单信息是否允许编辑
				query = cusInterfaceFeign.validateCusFormModify(mfBusApply.getCusNo(), mfBusApply.getAppId(), BizPubParm.FORM_EDIT_FLAG_PLE, User.getRegNo(request));
				if (query == null) {
					query = "";
				}
			}
			// 中汇北京 特定的评估表单
			if (mfBusApply != null && BizPubParm.BUS_MODEL_12.equals(mfBusApply.getBusModel())) {
				formId = "dlevaldetailbj0003";
			}
		}
		if (mfCollateralFormConfig == null) {
		} else {
			formId = mfCollateralFormConfig.getListModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的EvalInfoController列表详情表单信息没有查询到");
		}
		FormData formdlcollateraleval0003 = formService.getFormData(formId);
		if (formdlcollateraleval0003.getFormId() == null) {
			// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的EvalInfoController列表详情表单信息没有查询到");
			dataMap.put("msg", "form" + formId + "获取失败");
			dataMap.put("flag", "error");
			return dataMap;
		}
		getObjValue(formdlcollateraleval0003, evalInfo, formData);
		String htmlStrCorp = jsonFormUtil.getJsonStr(formdlcollateraleval0003, "propertySeeTag", query);
		dataMap.put("formHtml", htmlStrCorp);
		dataMap.put("flag", "success");
		dataMap.put("formData", evalInfo);
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 根据业务上次审批意见类型获得query
	 * @param appId
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-9-2 下午5:32:17
	 */
	@RequestMapping(value = "/getQueryBylastApproveType")
	public String getQueryBylastApproveType(String appId) throws Exception {
		MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
		mfBusApplyHis.setAppId(appId);
		/**
		 * 上次审批意见类型。
		 * 如果上次审批的审批意见类型为发回补充资料，设置query为"",表单可编辑,要件可上传
		 */
		List<MfBusApplyHis> list = new ArrayList<MfBusApplyHis>();
		list  = mfBusApplyHisFeign.getListByAppId(mfBusApplyHis);
		String query="query";
		if(list!=null&&list.size()>0){
			mfBusApplyHis = list.get(0);
			String lastApproveType=mfBusApplyHis.getApproveResult();
			if(StringUtil.isNotEmpty(lastApproveType)&&AppConstant.OPINION_TYPE_DEALER.equals(lastApproveType)){
				query = "";
			}
		}
		return query;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateByOneAjax")
	@ResponseBody
	public Map<String, Object> updateByOneAjax(String ajaxData, String formId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap = getMapByJson(ajaxData);
		String evalId = (String) dataMap.get("evalId");
		EvalInfo evalInfo = new EvalInfo();
		evalInfo.setEvalId(evalId);
		evalInfo = evalInfoFeign.getById(evalInfo);
		if (StringUtil.isEmpty(formId)) {
			String collateralNo = evalInfo.getCollateralId();
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(collateralNo);
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			String classId = pledgeBaseInfo.getClassId();
			formId = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId, "EvalInfoAction", "")
					.getListModelDef();
		} else {
			if (formId.indexOf("form") == -1) {
			} else {
				formId = formId.substring(4);
			}
		}

		// EvalInfo evalInfo = new EvalInfo();
		FormData formdlcollateraleval0003 = formService.getFormData(formId);
		getFormValue(formdlcollateraleval0003, getMapByJson(ajaxData));
		EvalInfo evalInfoNew = new EvalInfo();
		setObjValue(formdlcollateraleval0003, evalInfoNew);
		// evalInfo.setEvalId(evalInfoNew.getEvalId());
		// evalInfo = evalInfoFeign.getById(evalInfo);
		if (evalInfo != null) {
			try {
				evalInfo = (EvalInfo) EntityUtil.reflectionSetVal(evalInfo, evalInfoNew, getMapByJson(ajaxData));
				evalInfoFeign.update(evalInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 流程中押品评估节点登记押品评估信息 如果该业务有多个押品，按照默认排序带出第一个押品并进行评估，
	 * 页面上支持选择业务关联的的其他押品进行评估
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-12-15 上午8:59:33
	 */
	@RequestMapping("/inputForFlow")
	public String inputForFlow(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		String collateralType = "pledge";
		List<MfBusCollateralDetailRel> detailList = mfBusCollateralDetailRelFeign.getCollateralDetailRelList(appId,collateralType);
		EvalInfo evalInfo = new EvalInfo();
		String busPleId = "";
		if (detailList != null && detailList.size() > 0) {
			MfBusCollateralDetailRel mfBusCollateralDetailRel = detailList.get(0);
			busPleId = mfBusCollateralDetailRel.getBusCollateralId();
			pledgeBaseInfo.setPledgeNo(mfBusCollateralDetailRel.getCollateralId());
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			evalInfo.setCollateralId(mfBusCollateralDetailRel.getCollateralId());
			evalInfo = evalInfoFeign.getLatest(evalInfo);
		}
		if (evalInfo == null) {
			evalInfo = new EvalInfo();
			evalInfo.setCollateralId(pledgeBaseInfo.getPledgeNo());
			// evalId=WaterIdUtil.getWaterId();
			// evalInfo.setEvalId(evalId);
		}
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.pledge_eval, null, null, User.getRegNo(request));
		FormData formdlevalinfo0002 = formService.getFormData(formId);

		getFormValue(formdlevalinfo0002);
		getObjValue(formdlevalinfo0002, pledgeBaseInfo);
		getObjValue(formdlevalinfo0002, mfBusApply);
		getObjValue(formdlevalinfo0002, evalInfo);

		model.addAttribute("formdlevalinfo0002", formdlevalinfo0002);
		model.addAttribute("busPleId", busPleId);
		model.addAttribute("query", "");
		return "/component/collateral/EvalInfo_inputForFlow";
	}

	/**
	 * 
	 * 方法描述： 根据押品编号获得押品评估表单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-12-15 上午10:52:26
	 */
	@RequestMapping("/getPledgeEvalHtmlAjax")
	@ResponseBody
	public Map<String, Object> getPledgeEvalHtmlAjax(String collateralId,String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(collateralId);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);

		EvalInfo evalInfo = new EvalInfo();
		evalInfo.setCollateralId(collateralId);
		evalInfo = evalInfoFeign.getLatest(evalInfo);
		if (evalInfo == null) {
			evalInfo = new EvalInfo();
			evalInfo.setCollateralId(collateralId);
			// evalId=WaterIdUtil.getWaterId();
			// evalInfo.setEvalId(evalId);
		}
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.pledge_eval, null, null, User.getRegNo(request));
		FormData formdlevalinfo0002 = formService.getFormData(formId);

		getFormValue(formdlevalinfo0002);
		getObjValue(formdlevalinfo0002, pledgeBaseInfo);
		getObjValue(formdlevalinfo0002, mfBusApply);
		getObjValue(formdlevalinfo0002, evalInfo);

		String htmlStr = jsonFormUtil.getJsonStr(formdlevalinfo0002, "bootstarpTag", "");
		dataMap.put("htmlStr", htmlStr);
		dataMap.put("flag", "success");
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 流程中押品评估保存
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-12-15 上午11:53:50
	 */
	@RequestMapping("/insertBussAjax")
	@ResponseBody
	public Map<String, Object> insertBussAjax(String ajaxData, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		@SuppressWarnings("rawtypes")
		Map map = getMapByJson(ajaxData);
		try {
			FormData formdlevalinfo0002 = formService.getFormData(map.get("formId").toString());
			getFormValue(formdlevalinfo0002, map);
			if (this.validateFormData(formdlevalinfo0002)) {
				EvalInfo evalInfo = new EvalInfo();
				setObjValue(formdlevalinfo0002, evalInfo);
				@SuppressWarnings("unused")
				String collateralId = evalInfo.getCollateralId();
				evalInfoFeign.insertBuss(evalInfo, appId);
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
	 *
	 * 方法描述： 在线估值结果插入
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author cd
	 * @date 2019年8月20日 下午8:20:10
	 */
    @RequestMapping("/resultStorage")
    @ResponseBody
    public Map<String, Object> resultStorage(String ajaxData,String pledgeNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map map = getMapByJson(ajaxData);
        try {
            FormData formonlineDlevalinfo0002 = formService.getFormData(map.get("formId").toString());
            getFormValue(formonlineDlevalinfo0002, map);
            EvalInfo evalInfo = new EvalInfo();
            setObjValue(formonlineDlevalinfo0002, evalInfo);
            evalInfo.setCollateralId(pledgeNo);
            evalInfo.setEvalId(WaterIdUtil.getWaterId());
			evalInfo.setEvalState("1");
			evalInfo.setRemark("");
			evalInfo.setEvalType("1");
            evalInfoFeign.insert(evalInfo);
			log.info("####在线估值插入成功####");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage());
            throw e;
        }
        return dataMap;
    }

	/**
	 *
	 * 方法描述： 人工估值结果查询
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author cd
	 * @date 2019年8月20日 下午8:20:10
	 */
	@RequestMapping("/evalRequesSelect")
	@ResponseBody
	public Map<String, Object> evalRequesSelect(String evalId,String evalState) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> paramMap = new HashMap<String, String>();
		Map<String, Object> requestMap = new HashMap<String, Object>();
		EvalInfo evalInfo = new EvalInfo();
		String flag="";//查询状态
		try {
			evalInfo.setEvalId(evalId);
			evalInfo=evalInfoFeign.getById(evalInfo);
			//判断人工申请编号是不是超过2个月;
			int months = DateUtil.getMonths(evalInfo.getEvalDate(), DateUtil.getDate());
			if(months>=2){
				evalInfoFeign.delete(evalInfo);
				dataMap.put("flag", "1");
				dataMap.put("msg", "人工申请过期!");
				return dataMap;
			}
			if("2".equals(evalInfo.getEvalState())){
				Gson gson=new Gson();
				paramMap.put("cityCode",evalInfo.getExt2());
				paramMap.put("jobID",evalInfo.getEvalTaskNum());
				log.info("####调用**接口开始####");
				log.info("请求参数"+gson.toJson(paramMap));
				requestMap = funGuGuFeign.request3105(paramMap);
				log.info("返回结果"+gson.toJson(requestMap));
				Map<String, String> headMap = (Map<String, String>) requestMap.get("Head");
				if ("0000".equals(headMap.get("ResCode"))) {
					log.info("####成功####");
					Map<String, Object> bodyMap = (Map<String, Object>) requestMap.get("Body");
					log.info("评值数据"+gson.toJson(bodyMap));
					String statusCode = (String) bodyMap.get("statusCode");
					if ("IS-COMPLETED".equalsIgnoreCase(statusCode)) {
						evalInfo.setEvalMethod("03");
						String totalPrice = (String) bodyMap.get("totalPrice");
						double value = new Double(totalPrice);
						evalInfo.setEvalAmount(value*10000);
						String price = (String) bodyMap.get("price");
						double valuesPrice = new Double(price);
						evalInfo.setEvalPrice(valuesPrice);
						evalInfo.setEvalState("1");
						evalInfo.setEvalDate(DateUtil.getDate());
						evalInfo.setEvalOrgName("房估估");
						evalInfo.setRemark((String) bodyMap.get("message"));
						evalInfo.setEvalType("2");
						evalInfoFeign.update(evalInfo);
						dataMap.put("flag", "1");
						dataMap.put("msg", "估值成功，请点击查看详情！");
						log.info("####人工查询成功####");
					} else if("NOT-COMPLETED".equalsIgnoreCase(statusCode)){
						dataMap.put("msg", "估值未完成请耐心等待！");
						dataMap.put("flag", "2");
						return dataMap;
					}else {
						dataMap.put("msg",(String)bodyMap.get("message"));
						dataMap.put("flag", "0");
						return dataMap;
					}
				} else {
					dataMap.put("msg", headMap.get("ResMsg"));
					return dataMap;
				}
			}else{
				dataMap.put("flag", "0");
				dataMap.put("msg", "没有评估任务");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 工具栏打开房产评估页面
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openRealEstateEval")
	public String openRealEstateEval(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String formId = "RealEstateEval";
		FormData formRealEstateEval = formService.getFormData(formId);
		model.addAttribute("formRealEstateEval", formRealEstateEval);
		model.addAttribute("query", "");
		return "/component/collateral/realEstateEvalInfo";
	}

}
