package app.component.collateral.controller;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusApplyFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.collateral.entity.*;
import app.component.collateral.feign.*;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.EntityUtil;
import app.component.nmd.entity.ParmDic;
import app.component.prdct.entity.MfKindFlow;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.sys.entity.SysUser;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Title: CertiInfoController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Mar 08 11:15:48 CST 2017
 **/
@Controller
@RequestMapping("/certiInfo")
public class CertiInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private CertiInfoFeign certiInfoFeign;
	@Autowired
	private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfBusApplyFeign mfBusApplyFeign;
	@Autowired
	private MfBusCollateralRelFeign mfBusCollateralRelFeign;
	@Autowired
	private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(Map<String, Object> dataMap,String tableId, CertiInfo certiInfo) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", certiInfoFeign.getAll(certiInfo), null, true);
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
	public String getListPage(Model model,String appId) throws Exception {
		ActionContext.initialize(request, response);
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		model.addAttribute("appId", appId);
		model.addAttribute("cusNo", mfBusApply.getCusNo());
		CertiInfo certiInfo = new CertiInfo();
		certiInfo.setAppId(appId);
		certiInfo.setCollateralType("account");
		List<CertiInfo> certiInfoList = certiInfoFeign.getListByAppId(certiInfo);
		model.addAttribute("certiInfoList", certiInfoList);

		//自定义查询Bo方法
		MfKindFlow mfKindFlow = new MfKindFlow();
		mfKindFlow.setModelType(BizPubParm.MODEL_TYPE_BASE);
		mfKindFlow.setCategory(BizPubParm.FLOW_CATEGORY_3);
		mfKindFlow.setFlowApprovalNo("certiInfo_reg_approval");
		mfKindFlow = prdctInterfaceFeign.getKindFlow(mfKindFlow);
		if(mfKindFlow != null && BizPubParm.YES_NO_Y.equals(mfKindFlow.getUseFlag())){//走审批流程
			model.addAttribute("ifApproval", "1");
		}else{
			model.addAttribute("ifApproval", "0");
		}
		return "/component/collateral/CertiInfo_List";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Ipage ipage,Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String appId,String collateralType) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CertiInfo certiInfo = new CertiInfo();
		try {
			certiInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			certiInfo.setCustomSorts(ajaxData);//自定义排序参数赋值
			certiInfo.setCriteriaList(certiInfo, ajaxData);//我的筛选
			certiInfo.setAppId(appId);
			certiInfo.setCollateralType(collateralType);
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("certiInfo", certiInfo));
			ipage = certiInfoFeign.getListByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
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
		FormData formdlcollateralcerti0002 = formService.getFormData("dlcollateralcerti0002");
		CertiInfo certiInfo = new CertiInfo();
		List<CertiInfo> certiInfoList = certiInfoFeign.getAll(certiInfo);
		model.addAttribute("certiInfoList", certiInfoList);
		model.addAttribute("formdlcollateralcerti0002", formdlcollateralcerti0002);
		model.addAttribute("query", "");
		return "/component/collateral/CertiInfo_List";
	}

	/**
	 * 校验保险编号是否重复
	 */
	@RequestMapping("/validateDupCertiNoAjax")
	@ResponseBody
	public Map<String, Object> validateDupCertiNoAjax(String ajaxData) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (ajaxData.isEmpty()) {
			dataMap.put("result", "1");
		} else {
			String validate = certiInfoFeign.validateDupCertiNoAjax(ajaxData);
			if ("0".equals(validate)) {
				dataMap.put("result", "0");
			} else {
				dataMap.put("result", "1");
			}
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
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formdlcertiinfo0002 = formService.getFormData("dlcertiinfo0002");
			getFormValue(formdlcertiinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcertiinfo0002)) {
				CertiInfo certiInfo = new CertiInfo();
				setObjValue(formdlcertiinfo0002, certiInfo);
				@SuppressWarnings("unused")
				String collateralNo = certiInfo.getCollateralId();
				String certiId = WaterIdUtil.getWaterId();
				certiInfo.setCertiId(certiId);
				certiInfoFeign.insert(certiInfo);

				/*
				 * 当query为"query"或者ifBizManger为"0"时，解析的表单中不可单字段编辑；
				 * 当ifBizManger为"1"或""时，解析的表单中设置的可编辑的字段可以单字段编辑；
				 * 当ifBizManger为"2"时，解析的表单中所有非只读的字段可以单字段编辑；
				 */
				request.setAttribute("ifBizManger", "3");
				// 获得基本信息的展示表单ID，并将列表解析
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
						.getByPledgeImPawnType(certiInfo.getClassId(), "CertiInfoAction", "");
				String formId = null;
				if (mfCollateralFormConfig == null) {

				} else {
					formId = mfCollateralFormConfig.getShowModelDef();
				}
				FormData formdlcertiinfo0004 = formService.getFormData(formId);
				if (formdlcertiinfo0004.getFormId() == null) {
					// logger.error("押品类型为" +
					// mfCollateralFormConfig.getFormType() +
					// "的CertiInfoController表单form" + formId
					// + ".xml文件不存在");
				}
				getFormValue(formdlcertiinfo0004);
				getObjValue(formdlcertiinfo0004, certiInfo);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formdlcertiinfo0004, "propertySeeTag", "");
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStrFlag", "1");

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
	 * 方法描述： 业务中担保证明登记保存
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-7-31 上午11:47:37
	 */
	@RequestMapping("/insertBusAjax")
	@ResponseBody
	public Map<String, Object> insertBusAjax(String appId, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			String opNo = User.getRegNo(request);
			String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.storage_confirm, null, null, User.getRegNo(request));
			FormData formdlcertiinfo0002 = formService.getFormData(formId);
			getFormValue(formdlcertiinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcertiinfo0002)) {
				CertiInfo certiInfo = new CertiInfo();
				setObjValue(formdlcertiinfo0002, certiInfo);
				if (StringUtil.isNotEmpty(certiInfo.getCertiId())) {
					certiInfoFeign.updateBuss(certiInfo, appId,opNo);
					dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
				} else {
					@SuppressWarnings("unused")
					String collateralNo = certiInfo.getCollateralId();
					String certiId = WaterIdUtil.getWaterId();
					certiInfo.setCertiId(certiId);
					certiInfoFeign.insertBus(certiInfo, appId);
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}
				dataMap.put("flag", "success");
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
	 * 方法描述： 如果业务流程中跳过担保证明登记，则直接提交到下一个业务节点
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-7-31 下午12:54:43
	 */
	@RequestMapping("/submitBussProcessAjax")
	@ResponseBody
	public Map<String, Object> submitBussProcessAjax(String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			certiInfoFeign.submitBussProcess(appId,User.getRegNo(request));
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
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
	@RequestMapping("/updateForListAjax")
	@ResponseBody
	public Map<String, Object> updateForListAjax(String ajaxData,String appId,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formdlcertiinfo0003 = formService.getFormData("dlcertiinfo0003");
			getFormValue(formdlcertiinfo0003, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcertiinfo0003)) {
				CertiInfo certiInfo = new CertiInfo();
				setObjValue(formdlcertiinfo0003, certiInfo);
				certiInfoFeign.update(certiInfo);
				certiInfo = new CertiInfo();
				certiInfo.setAppId(appId);
				List<CertiInfo> certiInfoList = certiInfoFeign.getListByAppId(certiInfo);
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag", certiInfoList, null, true);
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
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		CertiInfo certiInfo = new CertiInfo();
		try {
			FormData formdlcertiinfo0003 = formService.getFormData("dlcertiinfo0002");
			getFormValue(formdlcertiinfo0003, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcertiinfo0003)) {
				certiInfo = new CertiInfo();
				setObjValue(formdlcertiinfo0003, certiInfo);
				certiInfoFeign.update(certiInfo);

				// getTableData();//获取列表

				// PledgeBase pledgeBase = new PledgeBase();
				// pledgeBase.setPledgeId(certiInfo.getCollateralId());
				// pledgeBase = pledgeBaseFeign.getById(pledgeBase);

				// 获得基本信息的展示表单ID，并将表单解析
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
						.getByPledgeImPawnType(certiInfo.getClassId(), "CertiInfoAction", "");
				String tableId = null;
				if (mfCollateralFormConfig == null) {

				} else {
					tableId = mfCollateralFormConfig.getShowModelDef();
				}

				if (StringUtil.isEmpty(tableId)) {
					// logger.error("押品类型为" +
					// mfCollateralFormConfig.getFormType() +
					// "的CertiInfoController列表table"
					// + tableId + ".xml文件不存在");
				}

				String collateralNo = certiInfo.getCollateralId();
				certiInfo = new CertiInfo();
				certiInfo.setCollateralId(collateralNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				@SuppressWarnings("unchecked")
				String tableHtml = jtu.getJsonStr("table" + tableId, "tableTag",
						(List<CertiInfo>) certiInfoFeign.findByPage(ipage, certiInfo).getResult(), null, true);
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
	public Map<String, Object> getByIdAjax(String certiId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdlcollateralcerti0002 = formService.getFormData("dlcollateralcerti0002");
		CertiInfo certiInfo = new CertiInfo();
		certiInfo.setCertiId(certiId);
		certiInfo = certiInfoFeign.getById(certiInfo);
		getObjValue(formdlcollateralcerti0002, certiInfo, formData);
		if (certiInfo != null) {
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
	public Map<String, Object> deleteAjax(String certiId, String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CertiInfo certiInfo = new CertiInfo();
		certiInfo.setCertiId(certiId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			certiInfo = (CertiInfo) JSONObject.toBean(jb, CertiInfo.class);
			certiInfoFeign.delete(certiInfo);
			getTableData(dataMap ,tableId, certiInfo);// 获取列表
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
	 * @描述 业务流程中登记权证信息（抵质押信息）
	 * @参数 [model, appId, collateralNo, classId]
	 * @返回值 java.lang.String
	 * @创建人  shenhaobing
	 * @创建时间 2019/9/26
	 * @修改人和其它信息
	 */
	@RequestMapping("/inputBus")
	public String inputBus(Model model, String appId, String collateralNo, String classId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String nodeNo = WKF_NODE.storage_confirm.getNodeNo();// 功能节点编号
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		String busModel = mfBusApply.getBusModel();
		//如果只有一个押品，直接跳转登记权证页面，如果多个押品则跳转列表支持为每个押品登记权证信息
		String pleNumFlag = BizPubParm.YES_NO_N;//业务下是否关联多个押品信息 0一个1多个
		String collateralType = "pledge";
		if ("34".equals(busModel)){//融资租赁
			collateralType = "lease";
		}else if ("13".equals(busModel)){//保理业务
			collateralType = "account";
		}else {
		}

		String pledgeNo = "";
		String returnUrl = "/component/collateral/CertiInfo_InsertBus";
		//获取这笔业务下的押品
		List<PledgeBaseInfo> pleList = pledgeBaseInfoFeign.getByAppId(appId,collateralType);
		if (pleList!=null&&pleList.size()==1){
			pleNumFlag = BizPubParm.YES_NO_N;
			pledgeNo = pleList.get(0).getPledgeNo();
			CertiInfo certiInfo = new CertiInfo();
			certiInfo.setCollateralId(pledgeNo);
			certiInfo = certiInfoFeign.getById(certiInfo);
			if (certiInfo==null){
				certiInfo = new CertiInfo();
				certiInfo.setCollateralId(collateralNo);
				certiInfo.setClassId(classId);
				certiInfo.setRecDate(DateUtil.getDate());
				certiInfo.setDealType("1");// 交易类型质押
			}
			String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.storage_confirm, null, null, User.getRegNo(request));
			FormData formdlcertiinfo0002 = formService.getFormData(formId);
			getFormValue(formdlcertiinfo0002);
			getObjValue(formdlcertiinfo0002, certiInfo);

			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("pactId", mfBusApply.getPactId());
			dataMap.put("vouType", mfBusApply.getVouType());
			model.addAttribute("dataMap", dataMap);
			model.addAttribute("appId", appId);
			model.addAttribute("nodeNo", nodeNo);
			model.addAttribute("formdlcertiinfo0002", formdlcertiinfo0002);
			model.addAttribute("query", "");
			model.addAttribute("collateralType", collateralType);
			returnUrl =  "/component/collateral/CertiInfo_InsertBus";
		}else if (pleList!=null&&pleList.size()>1){
			pleNumFlag = BizPubParm.YES_NO_Y;

			CertiInfo certiInfo = new CertiInfo();
			certiInfo.setAppId(appId);
			certiInfo.setCollateralType(collateralType);
			List<CertiInfo> certiInfoList = certiInfoFeign.getListByAppId(certiInfo);
			boolean flag = true;
			for (int i = 0; i < certiInfoList.size(); i++) {
				//审批状态 0-未提交 1-流程中 2-审批通过
				if ("1".equals(certiInfoList.get(i).getCertiSts())) {
					flag = false;
					break;
				}
			}
			if (flag) {
				model.addAttribute("flag", "success");
			} else {
				model.addAttribute("flag", "error");
			}
			model.addAttribute("nodeNo", nodeNo);
			model.addAttribute("appId", appId);
			model.addAttribute("cusNo", mfBusApply.getCusNo());
			model.addAttribute("pactId",mfBusApply.getPactId());
			model.addAttribute("collateralType", collateralType);
			model.addAttribute("certiInfoList", certiInfoList);
			returnUrl = "component/collateral/CertiInfo_InputList";
		}else {
		}
		return returnUrl;
	}

	/**
	 * 合同视角单独的"账款转让"按钮
	 * @param model
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/inputCertiInfo")
	public String inputCertiInfo(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		CertiInfo certiInfo = new CertiInfo();
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		certiInfo.setCertiId(WaterIdUtil.getWaterId());
		FormData formdlcertiinfo0002 = formService.getFormData("plCertiReceTransBase");
		getFormValue(formdlcertiinfo0002);
		getObjValue(formdlcertiinfo0002, certiInfo);

		//获取这笔业务下的押品
		List<PledgeBaseInfo> pleList = pledgeBaseInfoFeign.getByAppId(appId,"account");
		JSONArray pleItems = JSONArray.fromObject(pleList);
		for (int i = 0; i < pleItems.size(); i++) {
			pleItems.getJSONObject(i).put("id", pleItems.getJSONObject(i).getString("pledgeNo"));
			pleItems.getJSONObject(i).put("name", pleItems.getJSONObject(i).getString("pledgeName"));
		}
		model.addAttribute("pleItems", pleItems);
		model.addAttribute("certiId", certiInfo.getCertiId());
		model.addAttribute("appId", appId);
		model.addAttribute("cusNo", mfBusApply.getCusNo());
		model.addAttribute("kindNo", mfBusApply.getKindNo());
		model.addAttribute("vouType", mfBusApply.getVouType());
		model.addAttribute("formdlcertiinfo0002", formdlcertiinfo0002);
		model.addAttribute("query", "");
		return "/component/collateral/CertiInfo_InsertAlone";
	}

	/**
	 *
	 * 方法描述： 合同页面担保登记按钮下的担保证明登记保存
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2018-7-19 上午16:47:37
	 */
	@RequestMapping("/insertAloneAjax")
	@ResponseBody
	public Map<String, Object> insertAloneAjax(String appId, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			String opNo = User.getRegNo(request);
			FormData formdlcertiinfo0002 = formService.getFormData(formId);
			getFormValue(formdlcertiinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcertiinfo0002)) {
				CertiInfo certiInfo = new CertiInfo();
				setObjValue(formdlcertiinfo0002, certiInfo);
				dataMap = certiInfoFeign.insertAlone(certiInfo, appId);

				//重新查询列表回显在页面
				CertiInfo certiInfoT = new CertiInfo();
				certiInfoT.setAppId(appId);
				certiInfoT.setCollateralType("account");
				List<CertiInfo> certiInfoList = certiInfoFeign.getListByAppId(certiInfoT);
				JsonTableUtil jtu = new JsonTableUtil();
				String htmlStr = jtu.getJsonStr("tablecertiReceTransBaseList", "tableTag", certiInfoList, null, true);
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}

	@RequestMapping("/getCertiInfoById")
	public String getCertiInfoById(Model model, String certiId,String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		CertiInfo certiInfo = new CertiInfo();
		certiInfo.setCertiId(certiId);
		certiInfo = certiInfoFeign.getById(certiInfo);
		FormData formdlcertiinfo0002 = formService.getFormData("plCertiReceTransBase");
		getFormValue(formdlcertiinfo0002);
		getObjValue(formdlcertiinfo0002, certiInfo);

		//获取这笔业务下的押品
		List<PledgeBaseInfo> pleList = pledgeBaseInfoFeign.getByAppId(appId,"account");
		JSONArray pleItems = JSONArray.fromObject(pleList);
		for (int i = 0; i < pleItems.size(); i++) {
			pleItems.getJSONObject(i).put("id", pleItems.getJSONObject(i).getString("pledgeNo"));
			pleItems.getJSONObject(i).put("name", pleItems.getJSONObject(i).getString("pledgeName"));
		}

		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		model.addAttribute("pleItems", pleItems);
		model.addAttribute("certiId", certiId);
		model.addAttribute("certiInfo", certiInfo);
		model.addAttribute("appId", appId);
		model.addAttribute("cusNo", mfBusApply.getCusNo());
		model.addAttribute("kindNo", mfBusApply.getKindNo());
		model.addAttribute("vouType", mfBusApply.getVouType());
		model.addAttribute("formdlcertiinfo0002", formdlcertiinfo0002);
		model.addAttribute("query", "");
		return "/component/collateral/CertiInfo_updateAlone";
	}

	/**
	 *
	 * 方法描述： 合同页面账款转让按钮下的账款转让保存
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2018-7-19 上午16:47:37
	 */
	@RequestMapping("/updateAloneAjax")
	@ResponseBody
	public Map<String, Object> updateAloneAjax(String appId, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			String opNo = User.getRegNo(request);
			FormData formdlcertiinfo0002 = formService.getFormData(formId);
			getFormValue(formdlcertiinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcertiinfo0002)) {
				CertiInfo certiInfo = new CertiInfo();
				setObjValue(formdlcertiinfo0002, certiInfo);
				dataMap = certiInfoFeign.updateAlone(certiInfo, appId);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}

	@RequestMapping("/submitProcess")
	@ResponseBody
	public Map<String,Object> submitProcess(String appId, String certiIds) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			CertiInfo certiInfo = new CertiInfo();
			certiInfo.setCertiIdList(Arrays.asList(certiIds.split(",")));
			dataMap = certiInfoFeign.submitProcess(certiInfo, appId);
			//重新查询列表回显在页面
			CertiInfo certiInfoT = new CertiInfo();
			certiInfoT.setAppId(appId);
			certiInfoT.setCollateralType("account");
			List<CertiInfo> certiInfoList = certiInfoFeign.getListByAppId(certiInfoT);
			JsonTableUtil jtu = new JsonTableUtil();
			String htmlStr = jtu.getJsonStr("tablecertiReceTransBaseList", "tableTag", certiInfoList, null, true);
			dataMap.put("htmlStr", htmlStr);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}


	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String ajaxData, String appId,String wkfCertiId, String taskId, String hideOpinionType, String activityType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(wkfCertiId, taskId);// 当前审批节点task
		//项目信息
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);


		CertiInfo certiInfo = new CertiInfo();
		certiInfo.setWkfCertiId(wkfCertiId);
		List<CertiInfo> certiInfoList = certiInfoFeign.getCertiInfoListAll(certiInfo);
		//求和统计
		Double certiAmount =0.00;//合同/票面总额
		Double receAmt =0.00;//应收账款总额
		Double receTransAmt =0.00;//转让总额
		for(CertiInfo certiInfoTmp:certiInfoList){
			certiInfoTmp.setAppId(appId);
			if(certiInfoTmp.getCertiAmount()==null){
				certiInfoTmp.setCertiAmount(0d);
			}
			certiAmount = certiAmount+certiInfoTmp.getCertiAmount();
			if(certiInfoTmp.getReceAmt()==null){
				certiInfoTmp.setReceAmt(0d);
			}
			receAmt = receAmt + certiInfoTmp.getReceAmt();
			if(certiInfoTmp.getReceTransAmt()==null){
				certiInfoTmp.setReceTransAmt(0d);
			}
			receTransAmt = receTransAmt+certiInfoTmp.getReceTransAmt();
		}

		certiInfo.setAppId(appId);
		certiInfo.setCertiAmount(certiAmount);
		certiInfo.setReceAmt(receAmt);
		certiInfo.setReceTransAmt(receTransAmt);

		FormData formcertiInfo = formService.getFormData("plCertiApprovalBase");
		getObjValue(formcertiInfo, mfBusApply);
		getObjValue(formcertiInfo, certiInfo);

		CodeUtils codeUtils = new CodeUtils();
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", taskAppro.getActivityName());// 当前节点编号
		opinionTypeMap.put("opinionType","OPINION_TYPE");
		List<OptionsList> opinionTypeList = codeUtils.getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formcertiInfo, "opinionType", "optionArray", opinionTypeList);
		//获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		request.setAttribute("appId", appId);
		request.setAttribute("cusNo", mfBusApply.getCusNo());
		request.setAttribute("certiInfo", certiInfo);
		request.setAttribute("ajaxData", ajaxData);
		request.setAttribute("wkfCertiId", wkfCertiId);
		request.setAttribute("nodeNo", taskAppro.getActivityName());
		model.addAttribute("formcertiInfo", formcertiInfo);
		model.addAttribute("query", "");
		model.addAttribute("certiInfoList", certiInfoList);
		return "/component/collateral/CertiWkfViewPoint";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String taskId, String appId, String wkfCertiId, String transition, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CertiInfo certiInfo = new CertiInfo();
		dataMap = getMapByJson(ajaxData);
		FormData formcertiInfo = formService.getFormData((String) dataMap.get("formId"));
		getFormValue(formcertiInfo, dataMap);
		setObjValue(formcertiInfo, certiInfo);
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
		try {

			dataMap.put("certiInfo", certiInfo);
			Result res = certiInfoFeign.doCommit(taskId, appId,wkfCertiId, opinionType, approvalOpinion, transition, User.getRegNo(request), nextUser, dataMap);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", res.getMsg());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", res.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
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

		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"CertiInfoAction", "");
		String formId = null;
		if (mfCollateralFormConfig == null) {

		} else {
			formId = mfCollateralFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的CertiInfoController表单信息没有查询到");
		} else {
			CertiInfo certiInfo = new CertiInfo();
			certiInfo.setCollateralId(collateralNo);
			certiInfo.setClassId(classId);
			certiInfo.setRecDate(DateUtil.getDate());
			certiInfo.setDealType(BizPubParm.DEAL_TYPE_1);// 交易类型质押
			if (BizPubParm.VOU_TYPE_3.equals(pledgeBaseInfo.getPledgeMethod())) {
				certiInfo.setDealType(BizPubParm.DEAL_TYPE_3);// 交易类型抵押
			} else if (BizPubParm.VOU_TYPE_5.equals(pledgeBaseInfo.getPledgeMethod())) {
				certiInfo.setDealType(BizPubParm.DEAL_TYPE_2);// 交易类型转让
			}else {
			}
			FormData formdlcertiinfo0002 = formService.getFormData(formId);
			if (formdlcertiinfo0002.getFormId() == null) {
				// logger.error("押品类型为" + mfCollateralFormConfig.getFormType() +
				// "的CertiInfoController表单form" + formId
				// + ".xml文件不存在");
			} else {
				getFormValue(formdlcertiinfo0002);
				getObjValue(formdlcertiinfo0002, certiInfo);
				model.addAttribute("formdlcertiinfo0002", formdlcertiinfo0002);
			}
		}

		model.addAttribute("query", "");
		return "/component/collateral/CertiInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String certiId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		CertiInfo certiInfo = new CertiInfo();
		certiInfo.setCertiId(certiId);
		certiInfo = certiInfoFeign.getById(certiInfo);

		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(certiInfo.getCollateralId());
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();
		certiInfo.setClassId(classId);// 修改时候用
		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"CertiInfoAction", "");
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
			// logger.error("押品类型为" + classId +
			// "的CertiInfoController表单信息没有查询到");
		} else {
			FormData formdlcertiinfo0003 = formService.getFormData(formId);
			if (formdlcertiinfo0003.getFormId() == null) {
				// logger.error("押品类型为" + classId + "的CertiInfoController表单form"
				// + formId + ".xml文件不存在");
			} else {
				getFormValue(formdlcertiinfo0003);
				getObjValue(formdlcertiinfo0003, certiInfo);
				model.addAttribute("formdlcertiinfo0003", formdlcertiinfo0003);
			}
		}

		model.addAttribute("query", "");
		return "/component/collateral/CertiInfo_Detail";
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData, String formId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap = getMapByJson(ajaxData);
		String certiId = (String) dataMap.get("certiId");
		CertiInfo certiInfo = new CertiInfo();
		certiInfo.setCertiId(certiId);
		certiInfo = certiInfoFeign.getById(certiInfo);

		if (StringUtil.isEmpty(formId)) {
			String collateralNo = certiInfo.getCollateralId();
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(collateralNo);
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			String classId = pledgeBaseInfo.getClassId();
			formId = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId, "CertiInfoAction", "")
					.getShowModelDef();
		} else {
			if (formId.indexOf("form") == -1) {
			} else {
				formId = formId.substring(4);
			}
		}
		FormData formdlcertiinfo0004 = formService.getFormData(formId);
		getFormValue(formdlcertiinfo0004, getMapByJson(ajaxData));
		CertiInfo certiInfoJsp = new CertiInfo();
		setObjValue(formdlcertiinfo0004, certiInfoJsp);
		// certiInfo = certiInfoFeign.getById(certiInfoJsp);
		if (certiInfo != null) {
			try {
				certiInfo = (CertiInfo) EntityUtil.reflectionSetVal(certiInfo, certiInfoJsp, getMapByJson(ajaxData));
				certiInfoFeign.update(certiInfo);

				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAndDocommit")
	@ResponseBody
	public Map<String, Object> insertAndDocommit(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlcertiinfo0002 = formService.getFormData("dlcertiinfo0002");
			getFormValue(formdlcertiinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcertiinfo0002)) {
				CertiInfo certiInfo = new CertiInfo();
				setObjValue(formdlcertiinfo0002, certiInfo);
				@SuppressWarnings("unused")
				String collateralNo = certiInfo.getCollateralId();
				String certiId = WaterIdUtil.getWaterId();
				certiInfo.setCertiId(certiId);
				Result result = certiInfoFeign.insertAndDocommit(certiInfo);
				if (result == null) {
					dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
				} else {
					dataMap.put("msg", result.getMsg());

				}
				dataMap.put("flag", "success");
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


	//获取需要抵质押落实的押品
	public  List<PledgeBaseInfo> getCertiPledge (String appId) throws Exception{
		List<MfBusCollateralDetailRel> mfBusCollateralDetailRelList = mfBusCollateralDetailRelFeign.getCollateralListByAppId(appId);
		List<PledgeBaseInfo> pledgeBaseInfoList = new ArrayList<PledgeBaseInfo>();
		for (int i = 0; i < mfBusCollateralDetailRelList.size(); i++) {
			MfBusCollateralDetailRel mfBusCollateralDetailRel = mfBusCollateralDetailRelList.get(i);
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(mfBusCollateralDetailRel.getCollateralId());
			pledgeBaseInfo =pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			if(pledgeBaseInfo!=null){
				if(!"1".equals(pledgeBaseInfo.getPledgeType())){
					continue;
				}
				if(!"C".equals(pledgeBaseInfo.getClassFirstNo())){
					pledgeBaseInfo.setExtDic20(mfBusCollateralDetailRel.getIfShouli());
					pledgeBaseInfoList.add(pledgeBaseInfo);
				}

			}
		}
		for (int i = 0; i < pledgeBaseInfoList.size(); i++) {
			PledgeBaseInfo  pledgeBaseInfo =pledgeBaseInfoList.get(i);
			CertiInfo certiInfoPledge = new CertiInfo();
			certiInfoPledge.setCollateralId(pledgeBaseInfo.getPledgeNo());
			certiInfoPledge = certiInfoFeign.getByCollateralId(certiInfoPledge);

			if(certiInfoPledge!=null){
				pledgeBaseInfo.setExtDic01("1");
			}else{
				pledgeBaseInfo.setExtDic01("0");
			}
			if("B".equals(pledgeBaseInfo.getClassFirstNo())){//房产
				StringBuffer pledgeInfo = new StringBuffer("坐落于 ");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getPleSource())){
					pledgeInfo.append(pledgeBaseInfo.getPleSource());
				}
				pledgeInfo.append(" 的不动产(《房屋所有权证》编号为：");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getPleCertificateOwner())){
					pledgeInfo.append(pledgeBaseInfo.getPleCertificateOwner());
				}
				pledgeInfo.append(" 、《国有土地使用证》编号为：");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getLandDeedNo())){
					pledgeInfo.append(pledgeBaseInfo.getLandDeedNo());
				}
				pledgeBaseInfo.setPledgeInfo(pledgeInfo.toString());
			}else if("17061310382529920".equals(pledgeBaseInfo.getClassId())){//机动车
				StringBuffer pledgeInfo = new StringBuffer("车牌号为  ");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getExtOstr02())){
					pledgeInfo.append(pledgeBaseInfo.getExtOstr02());
				}
				pledgeInfo.append(" 的 ");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getExtOstr01())){
					pledgeInfo.append(pledgeBaseInfo.getExtOstr01());
				}
				pledgeInfo.append(" 汽车");
				pledgeBaseInfo.setPledgeInfo(pledgeInfo.toString());
			} else if("17061310062728416".equals(pledgeBaseInfo.getClassId())||"17061310354911618".equals(pledgeBaseInfo.getClassId())){//通用设备|专用设备
				StringBuffer pledgeInfo = new StringBuffer("编号为 ");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getPledgeShowNo())){
					pledgeInfo.append(pledgeBaseInfo.getPledgeShowNo());
				}
				pledgeInfo.append(" 号的发票项下的机器设备   ");
				pledgeBaseInfo.setPledgeInfo(pledgeInfo.toString());
			} else if("17061310422342021".equals(pledgeBaseInfo.getClassId())){//存单
				StringBuffer pledgeInfo = new StringBuffer("存于 ");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getExtOstr02())){
					pledgeInfo.append(pledgeBaseInfo.getExtOstr02());
				}
				pledgeInfo.append("  支行的帐号为   ");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getExtOstr03())){
					pledgeInfo.append(pledgeBaseInfo.getExtOstr03());
				}
				pledgeInfo.append(" 的存单 ");
				pledgeBaseInfo.setPledgeInfo(pledgeInfo.toString());
			}else if("17062215324584638".equals(pledgeBaseInfo.getClassId())){//流动库存
				StringBuffer pledgeInfo = new StringBuffer("权属证名称： ");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getExtOstr04())){
					pledgeInfo.append(pledgeBaseInfo.getExtOstr04());
				}
				pledgeInfo.append("  权属证编号：   ");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getExtOstr03())){
					pledgeInfo.append(pledgeBaseInfo.getExtOstr03());
				}
				pledgeBaseInfo.setPledgeInfo(pledgeInfo.toString());
			}else if("17061310520592426".equals(pledgeBaseInfo.getClassId())){//股权
				StringBuffer pledgeInfo = new StringBuffer("股票名称： ");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getPledgeName())){
					pledgeInfo.append(pledgeBaseInfo.getPledgeName());
				}
				pledgeInfo.append("  出质股票数量：   ");
				if(pledgeBaseInfo.getPleAmount()!=null){
					pledgeInfo.append(pledgeBaseInfo.getPleAmount());
				}
				pledgeBaseInfo.setPledgeInfo(pledgeInfo.toString());
			}else if("17061310520592426".equals(pledgeBaseInfo.getClassId())){//股权
				StringBuffer pledgeInfo = new StringBuffer("股票名称： ");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getPledgeName())){
					pledgeInfo.append(pledgeBaseInfo.getPledgeName());
				}
				pledgeInfo.append("  出质股票数量：   ");
				if(pledgeBaseInfo.getPleAmount()!=null){
					pledgeInfo.append(pledgeBaseInfo.getPleAmount());
				}
				pledgeBaseInfo.setPledgeInfo(pledgeInfo.toString());
			}else if("17062215321140637".equals(pledgeBaseInfo.getClassId())&&"4".equals(pledgeBaseInfo.getInsBeneFiciary())){//软件著作权
				StringBuffer pledgeInfo = new StringBuffer("登记号为 ");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getExtOstr43())){
					pledgeInfo.append(pledgeBaseInfo.getExtOstr43());
				}
				pledgeInfo.append(pledgeBaseInfo.getExtOstr43()).append("  号的 ");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getPledgeName())){
					pledgeInfo.append(pledgeBaseInfo.getPledgeName());
				}
				pledgeBaseInfo.setPledgeInfo(pledgeInfo.toString());
			}else if("17062215321140637".equals(pledgeBaseInfo.getClassId())&&"1".equals(pledgeBaseInfo.getInsBeneFiciary())){//专利
				StringBuffer pledgeInfo = new StringBuffer("专利名称 ");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getPledgeName())){
					pledgeInfo.append(pledgeBaseInfo.getPledgeName());
				}
				pledgeInfo.append(",专利证书号: ");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getExtOstr38())){
					pledgeInfo.append(pledgeBaseInfo.getExtOstr38());
				}
				pledgeBaseInfo.setPledgeInfo(pledgeInfo.toString());
			}else if("17062215321140637".equals(pledgeBaseInfo.getClassId())&&"5".equals(pledgeBaseInfo.getInsBeneFiciary())){//权利
				StringBuffer pledgeInfo = new StringBuffer("名称: ");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getPledgeName())){
					pledgeInfo.append(pledgeBaseInfo.getPledgeName());
				}
				pledgeInfo.append(",注册证号码: ");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getExtOstr44())){
					pledgeInfo.append(pledgeBaseInfo.getExtOstr44());
				}
				pledgeBaseInfo.setPledgeInfo(pledgeInfo.toString());
			}else{
				if ("C".equals(pledgeBaseInfo.getClassFirstNo())){

				}

				StringBuffer pledgeInfo = new StringBuffer("抵质押物名称： ");
				if(StringUtil.isNotEmpty(pledgeBaseInfo.getPledgeName())){
					pledgeInfo.append(pledgeBaseInfo.getPledgeName());
				}
				pledgeBaseInfo.setPledgeInfo(pledgeInfo.toString());
			}
		}
		return pledgeBaseInfoList;
	}
	/**
	 * 方法描述:业务流程中进行押品权证信息登记
	 * @param model
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputCertiInfoList")
	public String inputCertiInfoList(Model model,String cusNo,String appId,String type) throws Exception {
		ActionContext.initialize(request, response);

		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);

		CertiInfo certiInfo = new CertiInfo();
		certiInfo.setAppId(appId);
		List<CertiInfo> certiInfoList = certiInfoFeign.getListByAppId(certiInfo);

		String nodeNo = WKF_NODE.storage_confirm.getNodeNo();// 功能节点编号
		if(type==null || "".equals(type)){
			type="1";
		}
		boolean flag = true;
		for (int i = 0; i < certiInfoList.size(); i++) {
			//审批状态 0-未提交 1-流程中 2-审批通过
			if ("1".equals(certiInfoList.get(i).getCertiSts())) {
				flag = false;
				break;
			}
		}
		if (flag) {
			model.addAttribute("flag", "success");
		} else {
			model.addAttribute("flag", "error");
		}
		List<PledgeBaseInfo> pledgeBaseInfoList = this.getCertiPledge(appId);
				model.addAttribute("type", type);
        model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("appId", appId);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("pactId",mfBusApply.getPactId());
		/*if(mfBusApply!=null) {
			model.addAttribute("pactId", mfBusApply.getPactId());
			model.addAttribute("fincId", mfBusApply.getFincId());
		}*/
		model.addAttribute("certiInfoList", certiInfoList);
		model.addAttribute("pledgeBaseInfoList", pledgeBaseInfoList);
		return "component/collateral/CertiInfo_InputList";
	}

	/**
	 * @描述 业务流程中权证登记节点，登记多个押品的权证信息新增页面
	 * @参数 [model, appId, cusNo]
	 * @返回值 java.lang.String
	 * @创建人  shenhaobing
	 * @创建时间 2019/9/26
	 * @修改人和其它信息
	 */
	@RequestMapping("/inputForList")
	public String inputForList(Model model,String appId,String cusNo,String collateralType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String formId = "dlcertiinfo0003";
		FormData formdlcertiinfo0003 = formService.getFormData(formId);
		getFormValue(formdlcertiinfo0003);
		model.addAttribute("formdlcertiinfo0003", formdlcertiinfo0003);
		model.addAttribute("cusNo",cusNo);
		model.addAttribute("appId",appId);
		model.addAttribute("collateralType",collateralType);
		model.addAttribute("query", "");
		return "/component/collateral/CertiInfo_InsertForList";
	}
	/**
	 * @描述 业务流程中权证登记节点，登记多个押品的权证信息新增页面
	 * @参数 [model, appId, cusNo]
	 * @返回值 java.lang.String
	 * @创建人  shenhaobing
	 * @创建时间 2019/9/26
	 * @修改人和其它信息
	 */
	@RequestMapping("/inputForListNew")
	public String inputForListNew(Model model,String appId,String cusNo,String collateralType,String pledgeNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String formId = "dlcertiinfo0003";
		FormData formdlcertiinfo0003 = formService.getFormData(formId);
		getFormValue(formdlcertiinfo0003);
		model.addAttribute("formdlcertiinfo0003", formdlcertiinfo0003);
		if(StringUtil.isNotEmpty(pledgeNo)){
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(pledgeNo);
			pledgeBaseInfo =pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			model.addAttribute("pledgeBaseInfo", pledgeBaseInfo);
			model.addAttribute("pledgeName", pledgeBaseInfo.getPledgeName());
		}
		model.addAttribute("cusNo",cusNo);
		model.addAttribute("appId",appId);
		model.addAttribute("collateralType",collateralType);
		model.addAttribute("pledgeNo",pledgeNo);
		model.addAttribute("query", "");
		return "/component/collateral/CertiInfo_InsertForListNew";
	}
	/**
	 * @描述 业务流程中权证登记节点，登记多个押品的权证信息新增页面
	 * @参数 [model, appId, cusNo]
	 * @返回值 java.lang.String
	 * @创建人  shenhaobing
	 * @创建时间 2019/9/26
	 * @修改人和其它信息
	 */
	@RequestMapping("/editForListNew")
	public String editForListNew(Model model,String certiId,String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String formId = "dlcertiinfo0003";
		FormData formdlcertiinfo0003 = formService.getFormData(formId);
		getFormValue(formdlcertiinfo0003);
		model.addAttribute("formdlcertiinfo0003", formdlcertiinfo0003);
		if(StringUtil.isNotEmpty(certiId)){
			CertiInfo certiInfo = new CertiInfo();
			certiInfo.setCertiId(certiId);
			certiInfo = certiInfoFeign.getById(certiInfo);
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(certiInfo.getCollateralId());
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			getObjValue(formdlcertiinfo0003, pledgeBaseInfo);
			getObjValue(formdlcertiinfo0003, certiInfo);
		}
		model.addAttribute("query", "");
		model.addAttribute("appId", appId);
		model.addAttribute("formdlcertiinfo0003", formdlcertiinfo0003);
		return "/component/collateral/CertiInfo_DetailForList";
	}
	/**
	 * AJAX新增
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertForListAjax")
	@ResponseBody
	public Map<String, Object> insertForListAjax(String ajaxData,String appId,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formdlcertiinfo0003 = formService.getFormData("dlcertiinfo0003");
			getFormValue(formdlcertiinfo0003, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcertiinfo0003)) {
				CertiInfo certiInfo = new CertiInfo();
				setObjValue(formdlcertiinfo0003, certiInfo);
				@SuppressWarnings("unused")
				String collateralNo = certiInfo.getCollateralId();
				String certiId = WaterIdUtil.getWaterId();
				certiInfo.setCertiId(certiId);
				certiInfoFeign.insert(certiInfo);

				certiInfo = new CertiInfo();
				certiInfo.setAppId(appId);
				List<CertiInfo> certiInfoList = certiInfoFeign.getListByAppId(certiInfo);
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag", certiInfoList, null, true);
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
	 * Ajax异步删除
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/shouLiAjax")
	@ResponseBody
	public Map<String, Object> shouLiAjax(String pledgeNo,String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
			mfBusCollateralRel.setAppId(appId);
			mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
			if(mfBusCollateralRel!=null){
				MfBusCollateralDetailRel mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
				mfBusCollateralDetailRel.setCollateralId(pledgeNo);
				mfBusCollateralDetailRel.setBusCollateralId(mfBusCollateralRel.getBusCollateralId());
				mfBusCollateralDetailRel = mfBusCollateralDetailRelFeign.getById(mfBusCollateralDetailRel);
				if(mfBusCollateralDetailRel!=null){
					MfBusCollateralDetailRel mfBusCollateralDetailRelUpdate = new MfBusCollateralDetailRel();
					mfBusCollateralDetailRelUpdate.setId(mfBusCollateralDetailRel.getId());
					mfBusCollateralDetailRelUpdate.setIfShouli("1");
					mfBusCollateralDetailRelFeign.update(mfBusCollateralDetailRelUpdate);
				}

			}
//
//			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
//			pledgeBaseInfo.setPledgeNo(pledgeNo);
//			pledgeBaseInfo.setExtDic20("1");
//			pledgeBaseInfoFeign.updateAjax(pledgeBaseInfo);
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
	 * Ajax异步删除
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax2")
	@ResponseBody
	public Map<String, Object> deleteAja2(String appId,String collateralType,String certiId, String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CertiInfo certiInfo = new CertiInfo();
		certiInfo.setCertiId(certiId);
		try {
			certiInfoFeign.delete(certiInfo);
			certiInfo = new CertiInfo();
			certiInfo.setAppId(appId);
			certiInfo.setCollateralType(collateralType);
			List<CertiInfo> certiInfoList = certiInfoFeign.getListByAppId(certiInfo);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, "tableTag", certiInfoList, null, true);
			dataMap.put("tableData", tableHtml);
			List<PledgeBaseInfo> pledgeBaseInfoList =this.getCertiPledge(appId);
			String tableHtml2 = jtu.getJsonStr("tablepledgeBaseInfoList", "tableTag", pledgeBaseInfoList, null, true);
			dataMap.put("tableData", tableHtml);
			dataMap.put("tableData2", tableHtml2);
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
	 *
	 * 方法描述： 跳转选择客户押品弹框
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 徐孟涛
	 * @date 2018-11-8 下午15:10:55
	 */
	@RequestMapping("/getPledgeInfoPageForSelect")
	public String getPledgeInfoPageForSelect(Model model, String cusNo, String appId, String pledgeMethod)
			throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("appId", appId);
		model.addAttribute("pledgeMethod", pledgeMethod);
		model.addAttribute("pledgeMethod", pledgeMethod);
		return "/component/collateral/pledgeInfo_select";
	}
	/**
	 * @描述 提交
	 * @参数 [appId, type, taskId]
	 * @返回值 java.util.Map<java.lang.String,java.lang.Object>
	 * @创建人  shenhaobing
	 * @创建时间 2019/9/26
	 * @修改人和其它信息
	 */
	@ResponseBody
	@RequestMapping(value = "/submitAppr")
	public Map<String, Object> submitAppr(String appId,String collateralType,String type,String taskId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CertiInfo certiInfo = new CertiInfo();
		certiInfo.setAppId(appId);
		certiInfo.setCollateralType(collateralType);
		List<CertiInfo> certiInfoList = certiInfoFeign.getListByAppId(certiInfo);
		List<MfBusCollateralDetailRel> mfBusCollateralDetailRelList = mfBusCollateralDetailRelFeign.getCollateralListByAppId(appId);
		for (int i = 0; i < mfBusCollateralDetailRelList.size(); i++) {
			MfBusCollateralDetailRel mfBusCollateralDetailRel = mfBusCollateralDetailRelList.get(i);
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(mfBusCollateralDetailRel.getCollateralId());
			pledgeBaseInfo =pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			if(pledgeBaseInfo!=null){
				if(!"1".equals(mfBusCollateralDetailRel.getIfShouli())&&"1".equals(mfBusCollateralDetailRel.getIfRegister())){
					if("C".equals(pledgeBaseInfo.getClassFirstNo())){
						break;
					}
					dataMap.put("flag", "error");
					dataMap.put("msg", "反担保"+pledgeBaseInfo.getPledgeName()+"未受理权证信息，请完善后在进行提交！");
					return dataMap;
				}
			}
		}
		String opNo = User.getRegNo(request);
		String opName =User.getRegName(request);
		String brNo = User.getOrgNo(request);
		String appNo = appId+"_certi";
		String msg = "";
		msg = certiInfoFeign.startProcess(appNo,opNo,opName,brNo,appId);
		dataMap.put("flag", "success");
		dataMap.put("msg", msg);
		return dataMap;
	}

	/**
	 *
	 * 方法描述 权证信息提交审批
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author
	 */
	@ResponseBody
	@RequestMapping(value = "/approveSubmitAjax")
	public Map<String, Object> approveSubmitAjax( String ajaxData, String taskId,String opinionType,String approvalOpinion,
												 String transition, String nextUser,String appId,String appNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(ajaxData!=null) {
            dataMap = getMapByJson(ajaxData);
        }
		dataMap.put("opinionType",opinionType);
		dataMap.put("approvalOpinion",approvalOpinion);
		dataMap.put("orgNo", User.getOrgNo(request));
		dataMap.put("opNo", User.getRegNo(request));
		dataMap.put("opName", User.getRegName(request));
		Result res;
		try{
			res = certiInfoFeign.commitProcess(taskId,appNo, appId, transition!=null?transition:"", nextUser!=null?nextUser:"", dataMap);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					dataMap.put("msg", res.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return dataMap;
	}
	@RequestMapping("/getViewPoint2")
	public String getViewPoint2(Model model,String appNo,String cusNo,String appId){
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CertiInfo certiInfo = new CertiInfo();
		try {
			certiInfo.setAppId(appId);
			List<CertiInfo> certiInfoList = certiInfoFeign.getListByAppId(certiInfo);
			certiInfo = certiInfoList.get(0);
			TaskImpl taskAppro = wkfInterfaceFeign.getTask(appNo, null);
			String nodeId = taskAppro.getActivityName();
			dataMap.put("nodeId", nodeId);
			MfBusApply mfBusApply  = new MfBusApply();
			mfBusApply.setAppId(appId);
			mfBusApply = mfBusApplyFeign.getById(mfBusApply);
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(certiInfo.getCollateralId());
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			JSONArray userJsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", pledgeBaseInfo.getRegCusNo());
			jsonObject.put("name", pledgeBaseInfo.getRegCusName());
			userJsonArray.add(jsonObject);
			model.addAttribute("ajaxData", userJsonArray.toString());
			/*FormData formdlcertiinfo0003 = new FormService().getFormData("dlcertiinfo0003");
			getObjValue(formdlcertiinfo0003, certiInfoList.get(0));*/
			//this.changeFormProperty(formdlcertiinfo0003, "opinionType", "optionArray", null);
			FormData formapplyCertiInfo = new FormService().getFormData("applyCertiInfo");
			getObjValue(formapplyCertiInfo, mfBusApply);
			// 处理审批意见类型
			String activityType = taskAppro.getActivityType();

			String nodeNo = taskAppro.getName();

			// 处理审批意见类型
			Map<String, String> opinionTypeMap = new HashMap<String, String>();
			opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
			opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
			List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
			this.changeFormProperty(formapplyCertiInfo, "opinionType", "optionArray", opinionTypeList);

			// 处理批复期限的展示单位。
			Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
			String termUnit = termTypeMap.get(mfBusApply.getTermType()).getRemark();
			this.changeFormProperty(formapplyCertiInfo, "term", "unit", termUnit);
			//批复利率单位显示
			Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
			String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
			this.changeFormProperty(formapplyCertiInfo, "fincRate", "unit", rateUnit);
			//this.changeFormProperty(formapplyCertiInfo, "opinionType", "optionArray", null);
            cusNo = mfBusApply.getCusNo();
            model.addAttribute("nodeNo", nodeNo);
			model.addAttribute("mfBusApply",mfBusApply);
			model.addAttribute("formapplyCertiInfo",formapplyCertiInfo);
			model.addAttribute("taskId",taskAppro.getId());
			model.addAttribute("appId", certiInfo.getAppId());
			model.addAttribute("cusNo", cusNo);
			model.addAttribute("appNo", appNo);
			//model.addAttribute("formdlcertiinfo0003", formdlcertiinfo0003);
			model.addAttribute("certiInfo",certiInfoList.get(0));
			model.addAttribute("certiInfoList", certiInfoList);
			model.addAttribute("query", "");
			model.addAttribute("pactId",mfBusApply.getPactId());
		}catch (Exception e){
			e.printStackTrace();
		}

		return "/component/collateral/certiInfo_aprove_viewPoint";
	}
	@ResponseBody
	@RequestMapping(value = "/getPledgeMethod")
	public Map<String, Object> getPledgeMethod(String pledgeMethod) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, ParmDic> vouTypeMap = new CodeUtils().getMapObjByKeyName("VOU_TYPE");
		if (vouTypeMap.containsKey(pledgeMethod)){
			String vouTypeName = vouTypeMap.get(pledgeMethod).getOptName();
			dataMap.put("dealType", pledgeMethod);
			dataMap.put("dealTypeName", vouTypeName);
			dataMap.put("flag", "success");
		}else {
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getCertiListHtmlAjax")
	@ResponseBody
	public Map<String, Object> getCertiListHtmlAjax(String appId,String collateralType,
													String certiId, String ajaxData,
													String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CertiInfo certiInfo = new CertiInfo();
		try {
			certiInfo.setAppId(appId);
			certiInfo.setCollateralType(collateralType);
			List<CertiInfo> certiInfoList = certiInfoFeign.getListByAppId(certiInfo);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, "tableTag", certiInfoList, null, true);
			dataMap.put("tableData", tableHtml);
			List<PledgeBaseInfo> pledgeBaseInfoList = this.getCertiPledge(appId);
			if(pledgeBaseInfoList.size()>0){
				dataMap.put("ifHasList", "1");
			}else{
				dataMap.put("ifHasList", "0");
			}
			String tableHtml2 = jtu.getJsonStr("tablepledgeBaseInfoList", "tableTag", pledgeBaseInfoList, null, true);
			dataMap.put("tableData2", tableHtml2);
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
