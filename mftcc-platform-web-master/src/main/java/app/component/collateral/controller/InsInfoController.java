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
import cn.mftcc.util.DateUtil;
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

import app.component.collateral.entity.CertiInfo;
import app.component.collateral.entity.EvalInfo;
import app.component.collateral.entity.InsInfo;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.feign.InsInfoFeign;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONObject;

/**
 * Title: InsInfoController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Mar 15 13:19:29 CST 2017
 **/
@Controller
@RequestMapping("/insInfo")
public class InsInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private InsInfoFeign insInfoFeign;
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
	private void getTableData(Map<String, Object> dataMap, String tableId, InsInfo insInfo) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", insInfoFeign.getAll(insInfo), null, true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		InsInfo insInfo = new InsInfo();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("insInfo",insInfo));
		@SuppressWarnings("unchecked")
		List<InsInfo> insInfoList = (List<InsInfo>) insInfoFeign.findByPage(ipage).getResult();
		model.addAttribute("insInfoList", insInfoList);
		model.addAttribute("query", "");
		return "/component/collateral/InsInfo_List";
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
		InsInfo insInfo = new InsInfo();
		List<InsInfo> insInfoList = insInfoFeign.getAll(insInfo);
		model.addAttribute("insInfoList", insInfoList);
		model.addAttribute("query", "");
		return "/component/collateral/InsInfo_List";
	}

	/**
	 * 校验保险编号是否重复
	 */
	@RequestMapping("/validateDupInsNoAjax")
	@ResponseBody
	public Map<String, Object> validateDupInsNoAjax(String ajaxData) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (ajaxData.isEmpty()) {
			dataMap.put("result", "1");
		} else {
			String validate = insInfoFeign.validateDupInsNo(ajaxData);
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
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlinsinfo0002 = formService.getFormData("dlinsinfo0002");
			getFormValue(formdlinsinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlinsinfo0002)) {
				InsInfo insInfo = new InsInfo();
				String insId = WaterIdUtil.getWaterId();
				setObjValue(formdlinsinfo0002, insInfo);
				insInfo.setInsId(insId);
				String collateralNo = insInfo.getCollateralId();
				PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
                pledgeBaseInfo.setPledgeNo(collateralNo);
                pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
                if(pledgeBaseInfo!=null){
                    String extOstr33 = pledgeBaseInfo.getExtOstr33();
                    if("1".equals(extOstr33)&&"1".equals(insInfo.getInsType())){
                        dataMap.put("flag", "error");
                        dataMap.put("msg", "应收账款类型为特定不支持登记首次变更记录");
                        return  dataMap;
                    }
                }
				insInfoFeign.insert(insInfo);

				// 获得基本信息的展示表单ID，并将表单解析
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
						.getByPledgeImPawnType(insInfo.getClassId(), "InsInfoAction", "");
				String tableId = null;
				if (mfCollateralFormConfig == null) {

				} else {
					tableId = mfCollateralFormConfig.getShowModelDef();
				}

				if (StringUtil.isEmpty(tableId)) {
					// Log.error("押品类型为" + mfCollateralFormConfig.getFormType()
					// + "的InsInfoController列表table" + tableId
					// + ".xml文件不存在");
				}

				insInfo = new InsInfo();
				insInfo.setCollateralId(collateralNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("insInfo",insInfo));
				JsonTableUtil jtu = new JsonTableUtil();
				@SuppressWarnings("unchecked")
				String tableHtml = jtu.getJsonStr("table" + tableId, "tableTag",
						(List<CertiInfo>) insInfoFeign.findByPage(ipage).getResult(), null, true);
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
		InsInfo insInfo = new InsInfo();
		try {
			FormData formdlinsinfo0002 = formService.getFormData("dlinsinfo0002");
			getFormValue(formdlinsinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlinsinfo0002)) {
				setObjValue(formdlinsinfo0002, insInfo);
				insInfoFeign.update(insInfo);

				// 获得基本信息的展示表单ID，并将列表解析
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
						.getByPledgeImPawnType(insInfo.getClassId(), "InsInfoAction", "");
				String tableId = "";
				if (mfCollateralFormConfig == null) {

				} else {
					tableId = mfCollateralFormConfig.getShowModelDef();
				}

				if (StringUtil.isEmpty(tableId)) {
					// Log.error("押品类型为" + mfCollateralFormConfig.getFormType()
					// + "的EvalInfoController列表table" + tableId
					// + ".xml文件不存在");
				}

				String collateralNo = insInfo.getCollateralId();
				insInfo = new InsInfo();
				insInfo.setCollateralId(collateralNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("insInfo",insInfo));
				JsonTableUtil jtu = new JsonTableUtil();
				@SuppressWarnings("unchecked")
				String tableHtml = jtu.getJsonStr("table" + tableId, "tableTag",
						(List<EvalInfo>) insInfoFeign.findByPage(ipage).getResult(), null, true);
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
	public Map<String, Object> getByIdAjax(String insId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		InsInfo insInfo = new InsInfo();
		insInfo.setInsId(insId);
		insInfo = insInfoFeign.getById(insInfo);
		if (insInfo != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String insId, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		InsInfo insInfo = new InsInfo();
		insInfo.setInsId(insId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			insInfo = (InsInfo) JSONObject.toBean(jb, InsInfo.class);
			insInfoFeign.delete(insInfo);
			getTableData(dataMap, tableId, insInfo);// 获取列表
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
				"InsInfoAction", "");
		String formId = "";
		if (mfCollateralFormConfig == null) {

		} else {
			formId = mfCollateralFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的InsInfoController表单信息没有查询到");
		} else {
			InsInfo insInfo = new InsInfo();
			insInfo.setCollateralId(collateralNo);
			insInfo.setClassId(classId);
			insInfo.setRegCusId(User.getRegNo(request));
			insInfo.setRegCusName(User.getRegName(request));
			insInfo.setRegDate(DateUtil.getDate());
			FormData formdlinsinfo0002 = formService.getFormData(formId);
			if (formdlinsinfo0002.getFormId() == null) {
				// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
				// "的InsInfoController表单form" + formId
				// + ".xml文件不存在");
			} else {
				getFormValue(formdlinsinfo0002);
				getObjValue(formdlinsinfo0002, insInfo);
				model.addAttribute("formdlinsinfo0002", formdlinsinfo0002);
			}
		}
		model.addAttribute("query", "");
		return "/component/collateral/InsInfo_Insert";
	}

	/**
	 * 新增页面
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/changeReword")
	public String changeReword(Model model, String collateralNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();

		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(collateralNo);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();

		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"InsInfoAction", "");
		String formId = "";
		if (mfCollateralFormConfig == null) {

		} else {
			formId = mfCollateralFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的InsInfoController表单信息没有查询到");
		} else {
			InsInfo insInfo = new InsInfo();
			insInfo.setCollateralId(collateralNo);
			insInfo.setClassId(classId);
			insInfo.setRegCusId(User.getRegNo(request));
			insInfo.setRegCusName(User.getRegName(request));
			insInfo.setRegDate(DateUtil.getDate());
			insInfo.setInsType("2");
			FormData formdlinsinfo0002 = formService.getFormData(formId);
			if (formdlinsinfo0002.getFormId() == null) {
				// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
				// "的InsInfoController表单form" + formId
				// + ".xml文件不存在");
			} else {
				getFormValue(formdlinsinfo0002);
				getObjValue(formdlinsinfo0002, insInfo);
				model.addAttribute("formdlinsinfo0002", formdlinsinfo0002);
			}
		}
		model.addAttribute("query", "");
		return "/component/collateral/InsInfo_Insert";
	}

	/**
	 * 车管验车新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/inputCar")
	public String inputCar(Model model, String collateralNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();

		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(collateralNo);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();

		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"InsInfoAction", "");
		String formId = "";
		if (mfCollateralFormConfig == null) {

		} else {
			formId = mfCollateralFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的InsInfoController表单信息没有查询到");
		} else {
			InsInfo insInfo = new InsInfo();
			insInfo.setCollateralId(collateralNo);
			insInfo.setClassId(classId);
			FormData formdlinsinfo0002 = formService.getFormData(formId);
			if (formdlinsinfo0002.getFormId() == null) {
				// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
				// "的InsInfoController表单form" + formId
				// + ".xml文件不存在");
			} else {
				getFormValue(formdlinsinfo0002);
				getObjValue(formdlinsinfo0002, insInfo);
				model.addAttribute("formdlinsinfo0002", formdlinsinfo0002);
			}
		}
		model.addAttribute("query", "");
		return "/component/collateral/InsInfo_Insert";
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 这里得到的formId是带form字符串的，比如formcuscorp0001
		String formId = "";
		if (StringUtil.isEmpty(formId)) {
			// formId =
			// mfCollateralFormConfigFeign.getByPledgeImPawnType("base",
			// "insInfoAction").getShowModel();
		} else {
			if (formId.indexOf("form") == -1) {
			} else {
				formId = formId.substring(4);
			}
		}
		FormData formdlinsinfo0005 = formService.getFormData(formId);
		getFormValue(formdlinsinfo0005, getMapByJson(ajaxData));
		InsInfo insInfoJsp = new InsInfo();
		setObjValue(formdlinsinfo0005, insInfoJsp);
		InsInfo insInfo = insInfoFeign.getById(insInfoJsp);
		if (insInfo != null) {
			try {
				insInfo = (InsInfo) EntityUtil.reflectionSetVal(insInfo, insInfoJsp, getMapByJson(ajaxData));
				insInfoFeign.update(insInfo);
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
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String insId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();

		InsInfo insInfo = new InsInfo();
		insInfo.setInsId(insId);
		insInfo = insInfoFeign.getById(insInfo);

		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(insInfo.getCollateralId());
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();
		insInfo.setClassId(classId);// 修改时候用
		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"InsInfoAction", "");
		String formId = "";
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
			// "的InsInfoController表单信息没有查询到");
		} else {
			FormData formdlinsinfo0002 = formService.getFormData(formId);
			if (formdlinsinfo0002.getFormId() == null) {
				// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
				// "的InsInfoController表单form" + formId
				// + ".xml文件不存在");
			} else {
				getFormValue(formdlinsinfo0002);
				getObjValue(formdlinsinfo0002, insInfo);
				model.addAttribute("formdlinsinfo0002", formdlinsinfo0002);
			}
		}

		model.addAttribute("query", "");
		return "/component/collateral/InsInfo_Detail";
	}

	// 列表展示详情，单字段编辑
	@RequestMapping("/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String insId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		request.setAttribute("ifBizManger", "2");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		InsInfo insInfo = new InsInfo();
		insInfo.setInsId(insId);
		insInfo = insInfoFeign.getById(insInfo);
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(insInfo.getCollateralId());
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();
		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"InsInfoAction", "");
		String formId = "";
		String query = "";
		String appId = mfBusCollateralRelFeign.getAppIdByCollateralId(insInfo.getCollateralId());
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
			// "的InsInfoController列表详情表单信息没有查询到");
		}
		FormData formdlinsinfo0003 = formService.getFormData(formId);
		if (formdlinsinfo0003.getFormId() == null) {
			// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的InsInfoController列表详情表单信息没有查询到");
			dataMap.put("msg", "form" + formId + "获取失败");
			dataMap.put("flag", "error");
			return dataMap;
		}
		getObjValue(formdlinsinfo0003, insInfo, formData);
		String htmlStrCorp = jsonFormUtil.getJsonStr(formdlinsinfo0003, "propertySeeTag", query);
		dataMap.put("formHtml", htmlStrCorp);
		dataMap.put("flag", "success");
		dataMap.put("formData", insInfo);
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
		String insId = (String) dataMap.get("insId");
		InsInfo insInfo = new InsInfo();
		insInfo.setInsId(insId);
		insInfo = insInfoFeign.getById(insInfo);

		if (StringUtil.isEmpty(formId)) {
			String collateralNo = insInfo.getCollateralId();
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(collateralNo);
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			String classId = pledgeBaseInfo.getClassId();
			formId = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId, "InsInfoAction", "")
					.getListModelDef();
		} else {
			if (formId.indexOf("form") == -1) {
			} else {
				formId = formId.substring(4);
			}
		}

		// InsInfo insInfo = new InsInfo();
		FormData formdlinsinfo0003 = formService.getFormData(formId);
		getFormValue(formdlinsinfo0003, getMapByJson(ajaxData));
		InsInfo insInfoNew = new InsInfo();
		setObjValue(formdlinsinfo0003, insInfoNew);
		// insInfo.setInsId(insInfoNew.getInsId());
		// InsInfo insInfo = insInfoFeign.getById(insInfo);
		if (insInfo != null) {
			try {
				insInfo = (InsInfo) EntityUtil.reflectionSetVal(insInfo, insInfoNew, getMapByJson(ajaxData));
				insInfoFeign.update(insInfo);
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
