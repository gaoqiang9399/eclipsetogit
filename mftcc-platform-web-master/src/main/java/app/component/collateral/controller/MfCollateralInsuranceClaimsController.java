package app.component.collateral.controller;

import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.entity.EvalInfo;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.entity.InsInfo;
import app.component.collateral.entity.MfCollateralInsuranceClaims;
import app.component.collateral.feign.InsInfoFeign;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateral.feign.MfCollateralInsuranceClaimsFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfCollateralInsuranceClaimsController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Aug 15 15:28:06 CST 2018
 **/

@Controller
@RequestMapping("/mfCollateralInsuranceClaims")
public class MfCollateralInsuranceClaimsController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCollateralInsuranceClaimsFeign mfCollateralInsuranceClaimsFeign;
	@Autowired
	private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;
	@Autowired
	private InsInfoFeign insInfoFeign;

	/**
	 * 列表有翻页
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfCollateralInsuranceClaims mfCollateralInsuranceClaims = new MfCollateralInsuranceClaims();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCollateralInsuranceClaims",mfCollateralInsuranceClaims));
		@SuppressWarnings("unchecked")
		List<MfCollateralInsuranceClaims> mfCollateralInsuranceClaimsList = (List<MfCollateralInsuranceClaims>) mfCollateralInsuranceClaimsFeign.findByPage(ipage).getResult();
		model.addAttribute("mfCollateralInsuranceClaimsList", mfCollateralInsuranceClaimsList);
		model.addAttribute("query", "");
		return "/component/collateral/MfCollateralInsuranceClaims_List";
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
			FormData insuranceclaimsbase = formService.getFormData("insuranceclaimsbase");
			getFormValue(insuranceclaimsbase, getMapByJson(ajaxData));
			if (this.validateFormData(insuranceclaimsbase)) {
				MfCollateralInsuranceClaims mfCollateralInsuranceClaims = new MfCollateralInsuranceClaims();
				setObjValue(insuranceclaimsbase, mfCollateralInsuranceClaims);
//				校验赔付金额是否大于保险金额
				Double compensationMoney = mfCollateralInsuranceClaims.getCompensationMoney();
				Double policyMoney = mfCollateralInsuranceClaims.getPolicyMoney();
				if(compensationMoney.compareTo(policyMoney) == 1){
					dataMap.put("flag", "error");
					dataMap.put("msg", "赔付金额不能大于保险金额！");
					return dataMap;
				}
				String claimsId = WaterIdUtil.getWaterId();
				mfCollateralInsuranceClaims.setId(claimsId);
				String collateralNo = mfCollateralInsuranceClaims.getCollateralId();
				//通过保险id查询出保险编号
				InsInfo insInfo = new InsInfo();
				insInfo.setInsId(mfCollateralInsuranceClaims.getPolicyNo());
				//查询保险信息
				insInfo = insInfoFeign.getById(insInfo);
				if(insInfo==null){
					dataMap.put("flag", "error");
					dataMap.put("msg", "找不到保险编号！");
					return dataMap;
				}
				mfCollateralInsuranceClaims.setPolicyNo(insInfo.getInsNo());
				mfCollateralInsuranceClaimsFeign.insert(mfCollateralInsuranceClaims);

				// 获得基本信息的展示表单ID，并将表单解析
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(mfCollateralInsuranceClaims.getClassId(), "MfCollateralInsuranceClaimsAction", "");

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

				mfCollateralInsuranceClaims = new MfCollateralInsuranceClaims();
				mfCollateralInsuranceClaims.setCollateralId(collateralNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCollateralInsuranceClaims",mfCollateralInsuranceClaims));
				JsonTableUtil jtu = new JsonTableUtil();
				@SuppressWarnings("unchecked")
				String tableHtml = jtu.getJsonStr("tableinsuranceclaimslist", "tableTag",(List<MfCollateralInsuranceClaims>) mfCollateralInsuranceClaimsFeign.findByPage(ipage).getResult(), null, true);
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
		MfCollateralInsuranceClaims mfCollateralInsuranceClaims = new MfCollateralInsuranceClaims();
		try {
			FormData insuranceclaimsbase = formService.getFormData("insuranceclaimsbase");
			getFormValue(insuranceclaimsbase, getMapByJson(ajaxData));
			if (this.validateFormData(insuranceclaimsbase)) {
				setObjValue(insuranceclaimsbase, mfCollateralInsuranceClaims);
				mfCollateralInsuranceClaimsFeign.update(mfCollateralInsuranceClaims);

				// 获得基本信息的展示表单ID，并将列表解析
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
						.getByPledgeImPawnType(mfCollateralInsuranceClaims.getClassId(), "MfCollateralInsuranceClaimsAction", "");
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

				String collateralNo = mfCollateralInsuranceClaims.getCollateralId();
				mfCollateralInsuranceClaims = new MfCollateralInsuranceClaims();
				mfCollateralInsuranceClaims.setCollateralId(collateralNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCollateralInsuranceClaims",mfCollateralInsuranceClaims));
				JsonTableUtil jtu = new JsonTableUtil();
				@SuppressWarnings("unchecked")
				String tableHtml = jtu.getJsonStr("table" + tableId, "tableTag",
						(List<EvalInfo>) mfCollateralInsuranceClaimsFeign.findByPage(ipage).getResult(), null, true);
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
	public Map<String, Object> getByIdAjax(String claimsId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCollateralInsuranceClaims mfCollateralInsuranceClaims = new MfCollateralInsuranceClaims();
		mfCollateralInsuranceClaims.setId(claimsId);
		mfCollateralInsuranceClaims = mfCollateralInsuranceClaimsFeign.getById(mfCollateralInsuranceClaims);
		if (mfCollateralInsuranceClaims != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
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
				"MfCollateralInsuranceClaimsAction", "");
		String formId = "";
		if (mfCollateralFormConfig == null) {

		} else {
			formId = mfCollateralFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的InsInfoController表单信息没有查询到");
		} else {
			MfCollateralInsuranceClaims mfCollateralInsuranceClaims = new MfCollateralInsuranceClaims();
			mfCollateralInsuranceClaims.setCollateralId(collateralNo);
			mfCollateralInsuranceClaims.setClassId(classId);
			FormData forminsuranceclaimsbase = formService.getFormData(formId);
			if (forminsuranceclaimsbase.getFormId() == null) {
				// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
				// "的InsInfoController表单form" + formId
				// + ".xml文件不存在");
			} else {
				getFormValue(forminsuranceclaimsbase);
				getObjValue(forminsuranceclaimsbase, mfCollateralInsuranceClaims);
				model.addAttribute("forminsuranceclaimsbase", forminsuranceclaimsbase);
			}
		}

		InsInfo insInfoSelect = new InsInfo();
		insInfoSelect.setCollateralId(collateralNo);

		// 获取押品编号下所有的保险单
		//更加押品编号查询底下保险信息
		List<InsInfo> insInfoList = insInfoFeign.getListByCollateralId(insInfoSelect);

		JSONArray insInfoMap = new JSONArray();
		for (InsInfo insInfo : insInfoList) {
			JSONObject obj = new JSONObject();
			obj.put("id", insInfo.getInsId());
			obj.put("name", insInfo.getInsNo());
			insInfoMap.add(obj);
		}
		model.addAttribute("insInfoMap", insInfoMap.toString());

		model.addAttribute("query", "");
		return "/component/collateral/MfCollateralInsuranceClaims_Insert";
	}

	/**
	 * 查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();

		MfCollateralInsuranceClaims mfCollateralInsuranceClaims = new MfCollateralInsuranceClaims();
		mfCollateralInsuranceClaims.setId(id);
		mfCollateralInsuranceClaims = mfCollateralInsuranceClaimsFeign.getById(mfCollateralInsuranceClaims);

		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(mfCollateralInsuranceClaims.getCollateralId());
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();
		mfCollateralInsuranceClaims.setClassId(classId);// 修改时候用
		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"MfCollateralInsuranceClaimsAction", "");
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
			FormData forminsuranceclaimsdetail = formService.getFormData(formId);
			if (forminsuranceclaimsdetail.getFormId() == null) {
				// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
				// "的InsInfoController表单form" + formId
				// + ".xml文件不存在");
			} else {
				getFormValue(forminsuranceclaimsdetail);
				getObjValue(forminsuranceclaimsdetail, mfCollateralInsuranceClaims);
				model.addAttribute("forminsuranceclaimsdetail", forminsuranceclaimsdetail);
			}
		}

		model.addAttribute("query", "");
		return "/component/collateral/MfCollateralInsuranceClaims_Detail";
	}

	// 列表展示详情，单字段编辑
	@RequestMapping("/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		request.setAttribute("ifBizManger", "2");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		MfCollateralInsuranceClaims mfCollateralInsuranceClaims = new MfCollateralInsuranceClaims();
		mfCollateralInsuranceClaims.setId(id);
		mfCollateralInsuranceClaims = mfCollateralInsuranceClaimsFeign.getById(mfCollateralInsuranceClaims);
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(mfCollateralInsuranceClaims.getCollateralId());
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();
		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"MfCollateralInsuranceClaimsAction", "");
		String formId = "";
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
		getObjValue(formdlinsinfo0003, mfCollateralInsuranceClaims, formData);
		String htmlStrCorp = jsonFormUtil.getJsonStr(formdlinsinfo0003, "propertySeeTag", "");
		dataMap.put("formHtml", htmlStrCorp);
		dataMap.put("flag", "success");
		dataMap.put("formData", mfCollateralInsuranceClaims);
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateByOneAjax")
	@ResponseBody
	public Map<String, Object> updateByOneAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 这里得到的formId是带form字符串的，比如formcuscorp0001
		String formId = "";
		formId = getMapByJson(ajaxData).get("formId").toString();
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
		MfCollateralInsuranceClaims mfCollateralInsuranceClaimsJsp = new MfCollateralInsuranceClaims();
		setObjValue(formdlinsinfo0005, mfCollateralInsuranceClaimsJsp);
		MfCollateralInsuranceClaims mfCollateralInsuranceClaims = mfCollateralInsuranceClaimsFeign.getById(mfCollateralInsuranceClaimsJsp);
		if (mfCollateralInsuranceClaims != null) {
			try {
				mfCollateralInsuranceClaims = (MfCollateralInsuranceClaims) EntityUtil.reflectionSetVal(mfCollateralInsuranceClaims, mfCollateralInsuranceClaimsJsp, getMapByJson(ajaxData));
				//校验赔付金额是否大于保险金额
				Double compensationMoney = mfCollateralInsuranceClaims.getCompensationMoney();
				Double policyMoney = mfCollateralInsuranceClaims.getPolicyMoney();
				if(compensationMoney.compareTo(policyMoney) == 1){
					dataMap.put("flag", "error");
					dataMap.put("msg", "赔付金额不能大于保险金额！");
					return dataMap;
				}
				//更新
				mfCollateralInsuranceClaimsFeign.update(mfCollateralInsuranceClaims);
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
	 * 根据保单编号 查询 InsuranceInfo 保险信息中的起止时间和金额
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getInsInfo")
	@ResponseBody
	public Map<String, Object> getInsInfo(String insId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(insId==null||"".equals(insId)){
			dataMap.put("flag", "error");
			dataMap.put("msg", "保单编号不能为空");
		}
		FormService formService = new FormService();

		InsInfo insInfoSelect = new InsInfo();
		insInfoSelect.setInsId(insId);
		//查询保险信息
		InsInfo insInfo = insInfoFeign.getById(insInfoSelect);

		if(null == insInfo ){
			dataMap.put("flag", "error");
			dataMap.put("msg", "保单编号不合法");
		}else{
			dataMap.put("insInfo",insInfo);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		}
		return dataMap;
	}
}
