package app.component.collateral.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.app.entity.MfBusApplyHis;
import app.component.app.feign.MfBusApplyHisFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.collateral.feign.MfBusCollateralRelFeign;
import app.component.common.BizPubParm;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.wkf.AppConstant;
import config.YmlConfig;
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

import app.component.collateral.entity.ChkInfo;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.feign.ChkInfoFeign;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONObject;

/**
 * Title: ChkInfoController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Mar 08 10:21:44 CST 2017
 **/
@Controller
@RequestMapping("/chkInfo")
public class ChkInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private ChkInfoFeign chkInfoFeign;
	@Autowired
	private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private MfBusCollateralRelFeign mfBusCollateralRelFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private MfBusApplyHisFeign mfBusApplyHisFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */

	private void getTableData(Map<String, Object> dataMap, String tableId, ChkInfo chkInfo) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", chkInfoFeign.getAll(chkInfo), null, true);
		dataMap.put("tableData", tableHtml);
	}

	@RequestMapping("/getInsertPage")
	public String getInsertPage(Model model, String collateralId, String classId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String chkId = WaterIdUtil.getWaterId();
		ChkInfo ci = new ChkInfo();
		ci.setChkId(chkId);
		ci.setCollateralId(collateralId);
		ci.setClassId(classId);
		ci.setChkDate(cn.mftcc.util.DateUtil.getDate());
		FormData formdlchkinfo0002 = formService.getFormData("dlchkinfo0002");
		getObjValue(formdlchkinfo0002, ci);
		model.addAttribute("formdlchkinfo0002", formdlchkinfo0002);
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
		FormData formdlchkinfo0002 = formService.getFormData("dlchkinfo0002");
		ChkInfo chkInfo = new ChkInfo();
		List<ChkInfo> chkInfoList = chkInfoFeign.getAll(chkInfo);
		model.addAttribute("chkInfoList", chkInfoList);
		model.addAttribute("formdlchkinfo0002", formdlchkinfo0002);
		model.addAttribute("query", "");
		return "/component/collateral/ChkInfo_List";
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
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlchkinfo0002 = formService.getFormData("dlchkinfo0002");
			getFormValue(formdlchkinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlchkinfo0002)) {
				ChkInfo chkInfo = new ChkInfo();
				setObjValue(formdlchkinfo0002, chkInfo);
				String chkId = WaterIdUtil.getWaterId();
				chkInfo.setChkId(chkId);
				String collateralId = chkInfo.getCollateralId();
				chkInfoFeign.insert(chkInfo);

				// getTableData();//获取列表

				// 获得基本信息的展示表单ID，并将列表解析
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
						.getByPledgeImPawnType(chkInfo.getClassId(), "ChkInfoAction", "");
				String tableId = null;
				if (mfCollateralFormConfig == null) {
					// Log.error("押品评估配置信息没有找到。");
				} else {
					tableId = mfCollateralFormConfig.getShowModelDef();
				}

				if (StringUtil.isEmpty(tableId)) {
					// Log.error("押品类型为" + mfCollateralFormConfig.getFormType()
					// + "的ChkInfoController列表table" + tableId
					// + ".xml文件不存在");
				}

				chkInfo = new ChkInfo();
				chkInfo.setCollateralId(collateralId);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("chkInfo",chkInfo));
				JsonTableUtil jtu = new JsonTableUtil();
				@SuppressWarnings("unchecked")
                List<ChkInfo> chkInfoList = (List<ChkInfo>) chkInfoFeign.findByPage(ipage).getResult();
				String tableHtml = jtu.getJsonStr("table" + tableId, "tableTag",
                        chkInfoList, null, true);
				dataMap.put("htmlStr", tableHtml);
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
			FormData formdlchkinfo0003 = formService.getFormData("dlchkinfo0002");
			getFormValue(formdlchkinfo0003, getMapByJson(ajaxData));
			if (this.validateFormData(formdlchkinfo0003)) {
				ChkInfo chkInfo = new ChkInfo();
				setObjValue(formdlchkinfo0003, chkInfo);
				chkInfoFeign.update(chkInfo);

				// getTableData();//获取列表
				// 获得基本信息的展示表单ID，并将列表解析
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
						.getByPledgeImPawnType(chkInfo.getClassId(), "ChkInfoAction", "");
				String tableId = "";
				if (mfCollateralFormConfig == null) {

				} else {
					tableId = mfCollateralFormConfig.getShowModelDef();
				}

				if (StringUtil.isEmpty(tableId)) {
					// Log.error("押品类型为" + mfCollateralFormConfig.getFormType()
					// + "的ChkInfoController列表table" + tableId
					// + ".xml文件不存在");
				}

				String collateralNo = chkInfo.getCollateralId();
				chkInfo = new ChkInfo();
				chkInfo.setCollateralId(collateralNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("chkInfo",chkInfo));
				JsonTableUtil jtu = new JsonTableUtil();
				@SuppressWarnings("unchecked")
				String tableHtml = jtu.getJsonStr("table" + tableId, "tableTag",
						(List<ChkInfo>) chkInfoFeign.findByPage(ipage).getResult(), null, true);
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
	public Map<String, Object> getByIdAjax(String chkId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdlchkinfo0002 = formService.getFormData("dlchkinfo0002");
		ChkInfo chkInfo = new ChkInfo();
		chkInfo.setChkId(chkId);
		chkInfo = chkInfoFeign.getById(chkInfo);
		getObjValue(formdlchkinfo0002, chkInfo, formData);
		if (chkInfo != null) {
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
	public Map<String, Object> deleteAjax(String chkId, String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ChkInfo chkInfo = new ChkInfo();
		chkInfo.setChkId(chkId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			chkInfo = (ChkInfo) JSONObject.toBean(jb, ChkInfo.class);
			chkInfoFeign.delete(chkInfo);
			getTableData(dataMap, tableId, chkInfo);// 获取列表
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

		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"ChkInfoAction", "");

		String formId = null;
		if (mfCollateralFormConfig == null) {

		} else {
			formId = mfCollateralFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的ChkInfoController表单信息没有查询到");
		} else {
			ChkInfo chkInfo = new ChkInfo();
			chkInfo.setCollateralId(collateralNo);
			chkInfo.setClassId(classId);
			FormData formdlchkinfo0002 = formService.getFormData(formId);
			if (formdlchkinfo0002.getFormId() == null) {
				// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
				// "的ChkInfoController表单form" + formId
				// + ".xml文件不存在");
			} else {
				getFormValue(formdlchkinfo0002);
				getObjValue(formdlchkinfo0002, chkInfo);
				model.addAttribute("formdlchkinfo0002", formdlchkinfo0002);
			}
		}

		model.addAttribute("query", "");
		return "/component/collateral/ChkInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String chkId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();

		ChkInfo chkInfo = new ChkInfo();
		chkInfo.setChkId(chkId);
		chkInfo = chkInfoFeign.getById(chkInfo);

		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(chkInfo.getCollateralId());
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();
		chkInfo.setClassId(classId);// 修改时候用
		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"ChkInfoAction", "");
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
			FormData formdlchkinfo0003 = formService.getFormData(formId);
			if (formdlchkinfo0003.getFormId() == null) {
				// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
				// "的EvalInfoController表单form" + formId
				// + ".xml文件不存在");
			} else {
				getFormValue(formdlchkinfo0003);
				getObjValue(formdlchkinfo0003, chkInfo);
				model.addAttribute("formdlchkinfo0003", formdlchkinfo0003);
			}
		}

		model.addAttribute("query", "");
		return "/component/collateral/ChkInfo_Detail";
	}

	// 列表展示详情，单字段编辑
	@RequestMapping("/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String chkId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		request.setAttribute("ifBizManger", "2");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		ChkInfo chkInfo = new ChkInfo();
		chkInfo.setChkId(chkId);
		chkInfo = chkInfoFeign.getById(chkInfo);

		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(chkInfo.getCollateralId());
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();
		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"ChkInfoAction", "");
		String formId = null;
		String query = "";
		String appId = mfBusCollateralRelFeign.getAppIdByCollateralId(chkInfo.getCollateralId());
		if(StringUtil.isNotEmpty(appId)){
			if(!appId.contains("SX")){
				MfBusApply mfBusApply = new MfBusApply();
				mfBusApply.setAppId(appId);
				mfBusApply = appInterfaceFeign.getMfBusApply(mfBusApply);
				//判断押品表单信息是否允许编辑
				query = cusInterfaceFeign.validateCusFormModify(mfBusApply.getCusNo(),mfBusApply.getAppId(),BizPubParm.FORM_EDIT_FLAG_PLE,User.getRegNo(request));
			}
			if(query==null) {
				query="";
			}
		}
		if (mfCollateralFormConfig == null) {
		} else {
			formId = mfCollateralFormConfig.getListModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的ChkInfoController列表详情表单信息没有查询到");
		}
		FormData formdlchkinfo0003 = formService.getFormData(formId);
		if (formdlchkinfo0003.getFormId() == null) {
			// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的ChkInfoController列表详情表单信息没有查询到");
			dataMap.put("msg", "form" + formId + "获取失败");
			dataMap.put("flag", "error");
			return dataMap;
		}
		getObjValue(formdlchkinfo0003, chkInfo, formData);
		String htmlStrCorp = jsonFormUtil.getJsonStr(formdlchkinfo0003, "propertySeeTag", query);
		dataMap.put("formHtml", htmlStrCorp);
		dataMap.put("flag", "success");
		dataMap.put("formData", chkInfo);
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
		String chkId = (String) dataMap.get("chkId");
		ChkInfo chkInfo = new ChkInfo();
		chkInfo.setChkId(chkId);
		chkInfo = chkInfoFeign.getById(chkInfo);

		if (StringUtil.isEmpty(formId)) {
			String collateralNo = chkInfo.getCollateralId();
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(collateralNo);
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			String classId = pledgeBaseInfo.getClassId();
			formId = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId, "ChkInfoAction", "")
					.getListModelDef();
		} else {
			if (formId.indexOf("form") == -1) {
			} else {
				formId = formId.substring(4);
			}
		}

		// chkInfo = new ChkInfo();
		FormData formdlchkinfo0003 = formService.getFormData(formId);
		getFormValue(formdlchkinfo0003, getMapByJson(ajaxData));
		ChkInfo chkInfoNew = new ChkInfo();
		setObjValue(formdlchkinfo0003, chkInfoNew);
		// chkInfo.setChkId(chkInfoNew.getChkId());
		// chkInfo = chkInfoFeign.getById(chkInfo);
		if (chkInfo != null) {
			try {
				chkInfo = (ChkInfo) EntityUtil.reflectionSetVal(chkInfo, chkInfoNew, getMapByJson(ajaxData));
				chkInfoFeign.update(chkInfo);
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

}
