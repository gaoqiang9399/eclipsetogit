package app.component.interfaces.appinterface.controller;

import java.util.Arrays;
import java.util.Comparator;
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

import com.core.domain.screen.FormActive;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFamilyInfo;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusPersonAssetsInfo;
import app.component.cus.entity.MfCusPersonJob;
import app.component.cus.feign.MfCusFamilyInfoFeign;
import app.component.cus.feign.MfCusPersonAssetsInfoFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.tech.oscache.CodeUtils;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;

/**
 * 钉钉 工作信息
 * 
 * @author MaHao
 * @date 2017-8-24 上午10:09:11
 */
@Controller
@RequestMapping("/dingCusPerson")
public class DingCusPersonController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	// 注入MfCusPersonJobBo
	/**
	 * 登记职业信息表单
	 */
	@Autowired
	private MfCusFamilyInfoFeign mfCusFamilyInfoFeign;
	@Autowired
	private MfCusPersonAssetsInfoFeign mfCusPersonAssetsInfoFeign;

	/**
	 * AJAX新增 职业信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertJobAjax")
	@ResponseBody
	public Map<String, Object> insertJobAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcusjob00002 = formService.getFormData("cusjob00002");
			getFormValue(formcusjob00002, getMapByJson(ajaxData));
			if (this.validateFormData(formcusjob00002)) {
				MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
				setObjValue(formcusjob00002, mfCusPersonJob);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusPersonJob.getCusNo());
				mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);;
				String cusName = mfCusCustomer.getCusName();
				mfCusPersonJob.setCusName(cusName);
				cusInterfaceFeign.insertCusOtherInfo("mf_cus_person_job", mfCusPersonJob);

				String infIntegrity = cusInterfaceFeign.updateInfIntegrity(mfCusPersonJob.getCusNo());// 更新客户信息完整度
				mfCusPersonJob = new MfCusPersonJob();
				mfCusPersonJob.setCusNo(mfCusCustomer.getCusNo());
				// Ipage ipage = this.getIpage();
				// JsonTableUtil jtu = new JsonTableUtil();
				// String tableHtml =
				// jtu.getJsonStr("tablecusjob00001","tableTag",
				// (List<MfCusGoods>)mfCusPersonJobFeign.findByPage(ipage,
				// mfCusPersonJob).getResult(), null,true);
				// dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("flag", "success");
				dataMap.put("infIntegrity", infIntegrity);
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
	 * 新增页面 职业信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputJob")
	public String inputJob(Model model, String cusNo, String formId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
		mfCusPersonJob.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);;
		MfCusFormConfig mfCusFormConfig = cusInterfaceFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusPersonJobAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		FormData formcusjob00002 = null;
		if (StringUtil.isEmpty(formId)) {
//			logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusGoodsAction表单信息没有查询到");
		} else {
			formcusjob00002 = formService.getFormData(formId);
			if (formcusjob00002.getFormId() == null) {
//				logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusGoodsAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcusjob00002);
				mfCusPersonJob.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusjob00002, mfCusPersonJob);
			}
		}
		// 表单数据转换为字符串
		if (null != formcusjob00002) {
			// 替换单位性质数据字典项
			CodeUtils codeUtils = new CodeUtils();
			List<ParmDic> unitKindList = (List<ParmDic>) codeUtils.getCacheByKeyName("UNIT_KIND");
			this.changeFormProperty(formcusjob00002, "corpKind", "optionArray", unitKindList);
			sortFormDataActives(formcusjob00002);
			String formcusjob00002Json = new Gson().toJson(formcusjob00002);
			model.addAttribute("formcusjob00002Json", formcusjob00002Json);
		}
		model.addAttribute("formcusjob00002", formcusjob00002);
		model.addAttribute("query", "");
		return "/component/interfaces/appinterface/DingCusPersonJob_Insert";
	}

	/**
	 * 按照表单设计器设计的xml表单排序字段，先横排再竖排，从上到下
	 * 
	 * @param formData
	 * @author MaHao
	 * @date 2017-8-24 上午11:07:47
	 */
	private void sortFormDataActives(FormData formData) {
		if (null != formData) {
			FormActive[] formActives = formData.getFormActives();
			// 给表单里的字段排序
			Arrays.sort(formActives, new Comparator<FormActive>() {
				@Override
				public int compare(FormActive o1, FormActive o2) { // 先比较行，行相同比较列
					if (o1.getRow() == o2.getRow()) {
						if (o1.getFieldCol() == o2.getFieldCol()) {
							return 0;
						} else if (o1.getFieldCol() <= o2.getFieldCol()) {
							return -1;
						} else {
							return 1;
						}
					}
					if (o1.getRow() <= o2.getRow()) {
						return -1;
					} else {
						return 1;
					}
				}
			});
		}
	}

	/**
	 * 新增页面 社会关系
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputFamily")
	public String inputFamily(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusfam00002 = formService.getFormData("cusfam00002");
		getFormValue(formcusfam00002);
		MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
		mfCusFamilyInfo.setCusNo(cusNo);
		getObjValue(formcusfam00002, mfCusFamilyInfo);
		// 表单数据转换为字符串
		if (null != formcusfam00002) {
			// 替换客户关系数据字典项
			CodeUtils codeUtils = new CodeUtils();
			List<ParmDic> cusPersRelList = (List<ParmDic>) codeUtils.getCacheByKeyName("CUS_PERS_REL");
			this.changeFormProperty(formcusfam00002, "relative", "optionArray", cusPersRelList);
			// 证件类型
			List<ParmDic> persIdTypeList = (List<ParmDic>) codeUtils.getCacheByKeyName("PERS_ID_TYPE");
			this.changeFormProperty(formcusfam00002, "idType", "optionArray", persIdTypeList);
			// 性别
			List<ParmDic> sexList = (List<ParmDic>) codeUtils.getCacheByKeyName("SEX");
			this.changeFormProperty(formcusfam00002, "sex", "optionArray", sexList);
			sortFormDataActives(formcusfam00002);
			String formcusjob00002Json = new Gson().toJson(formcusfam00002);
			model.addAttribute("formcusjob00002Json", formcusjob00002Json);
		}
		model.addAttribute("formcusfam00002", formcusfam00002);
		model.addAttribute("query", "");
		return "/component/interfaces/appinterface/DingCusFamilyInfo_Insert";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertFamilyAjax")
	@ResponseBody
	public Map<String, Object> insertFamilyAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcusfam00002 = formService.getFormData("cusfam00002");
			Map<String, Object> jsonMap = getMapByJson(ajaxData);
			getFormValue(formcusfam00002, jsonMap);
			if (this.validateFormData(formcusfam00002)) {
				MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
				String cusNoTmp = (String) jsonMap.get("cusNo");
				mfCusFamilyInfo.setCusNo(cusNoTmp);
				setObjValue(formcusfam00002, mfCusFamilyInfo);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusFamilyInfo.getCusNo());
				mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);;
				String cusName = mfCusCustomer.getCusName();
				mfCusFamilyInfo.setCusName(cusName);
				mfCusFamilyInfo.setId(WaterIdUtil.getWaterId("fam"));
				cusInterfaceFeign.insertCusOtherInfo("mf_cus_family_info", mfCusFamilyInfo);
				// getTableData(); //获取列表html pc使用，这里不需要

				String infIntegrity = cusInterfaceFeign.updateInfIntegrity(cusNoTmp);// 更新客户信息完整度
				String cusNo = mfCusCustomer.getCusNo();
				mfCusFamilyInfo = new MfCusFamilyInfo();
				mfCusFamilyInfo.setCusNo(cusNo);

				// Ipage ipage = this.getIpage();
				// JsonTableUtil jtu = new JsonTableUtil();
				// String tableHtml =
				// jtu.getJsonStr("tablecusfam00001","tableTag",
				// (List<MfCusHighInfo>)mfCusFamilyInfoFeign.findByPage(ipage,
				// mfCusFamilyInfo).getResult(), null,true);
				// dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
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
	@RequestMapping(value = "/updateFamilyByOneAjax")
	@ResponseBody
	public Map<String, Object> updateFamilyByOneAjax(String ajaxData, String formId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
		// 这里得到的formId是带form字符串的，比如formcuscorp0001
		if (StringUtil.isEmpty(formId)) {
			formId = cusInterfaceFeign.getByCusType("base", "MfCusFamilyInfoAction").getShowModel();
		} else {
			if (formId.indexOf("form") == -1) {
			} else {
				formId = formId.substring(4);
			}
		}
		FormData formcusfam00003 = formService.getFormData(formId);
		getFormValue(formcusfam00003, getMapByJson(ajaxData));
		MfCusFamilyInfo mfCusFamilyInfoNew = new MfCusFamilyInfo();
		setObjValue(formcusfam00003, mfCusFamilyInfoNew);
		mfCusFamilyInfo.setId(mfCusFamilyInfoNew.getId());
		mfCusFamilyInfo = cusInterfaceFeign.getCusFamilyInfo(mfCusFamilyInfo);
		if (mfCusFamilyInfo != null) {
			try {
				mfCusFamilyInfo = (MfCusFamilyInfo) EntityUtil.reflectionSetVal(mfCusFamilyInfo, mfCusFamilyInfoNew,
						getMapByJson(ajaxData));
				cusInterfaceFeign.updateCusOtherInfo("mf_cus_family_info", mfCusFamilyInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

	/**
	 * 新增页面 资产信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputAssets")
	public String inputAssets(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcuspersassets0002 = formService.getFormData("cuspersassets0002");
		getFormValue(formcuspersassets0002);
		MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
		mfCusPersonAssetsInfo.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		String cusName = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer).getCusName();
		mfCusPersonAssetsInfo.setCusName(cusName);
		getObjValue(formcuspersassets0002, mfCusPersonAssetsInfo);
		// flag = "insert";
		// 表单数据转换为字符串
		if (null != formcuspersassets0002) {
			// 替换资产类型数据字典项
			CodeUtils codeUtils = new CodeUtils();
			List<ParmDic> persAssetsTypeList = (List<ParmDic>) codeUtils.getCacheByKeyName("PERS_ASSETS_TYPE");
			this.changeFormProperty(formcuspersassets0002, "assetsType", "optionArray", persAssetsTypeList);
			// 房产类型
			List<ParmDic> perHouseTypeList = (List<ParmDic>) codeUtils.getCacheByKeyName("PER_HOUSE_TYPE");
			this.changeFormProperty(formcuspersassets0002, "houseType", "optionArray", perHouseTypeList);
			// 是否按揭
			List<ParmDic> acYesNoList = (List<ParmDic>) codeUtils.getCacheByKeyName("AC_YES_NO");
			this.changeFormProperty(formcuspersassets0002, "isMortgage", "optionArray", acYesNoList);
			sortFormDataActives(formcuspersassets0002);
			String formcusjob00002Json = new Gson().toJson(formcuspersassets0002);
			model.addAttribute("formcusjob00002Json", formcusjob00002Json);
		}
		model.addAttribute("formcuspersassets0002", formcuspersassets0002);
		model.addAttribute("query", "");
		return "/component/interfaces/appinterface/DingCusPersonAssetsInfo_Insert";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAssetsAjax")
	@ResponseBody
	public Map<String, Object> insertAssetsAjax(String ajaxData, String formId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = cusInterfaceFeign.getByCusType("base", "MfCusPersonAssetsInfoAction").getAddModel();
			}
			FormData formcuspersassets0002 = formService.getFormData(formId);
			getFormValue(formcuspersassets0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcuspersassets0002)) {
				MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
				setObjValue(formcuspersassets0002, mfCusPersonAssetsInfo);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusPersonAssetsInfo.getCusNo());
				mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);;
				String cusName = mfCusCustomer.getCusName();
				mfCusPersonAssetsInfo.setCusName(cusName);
				cusInterfaceFeign.insertCusOtherInfo("mf_cus_person_assets_info", mfCusPersonAssetsInfo);

				String cusNo = mfCusCustomer.getCusNo();
				mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
				mfCusPersonAssetsInfo.setCusNo(cusNo);
				// Ipage ipage = this.getIpage();
				// JsonTableUtil jtu = new JsonTableUtil();
				//
				// String tableHtml =
				// jtu.getJsonStr("tablecuspersassets0001","tableTag",
				// (List<MfCusPersonAssetsInfo>)mfCusPersonAssetsInfoFeign.findByPage(ipage,
				// mfCusPersonAssetsInfo).getResult(), null,true);
				// dataMap.put("htmlStr", tableHtml);
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
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAssetsByOneAjax")
	@ResponseBody
	public Map<String, Object> updateAssetsByOneAjax(String ajaxData,String formId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
		// 这里得到的formId是带form字符串的，比如formcuscorp0001
		if (StringUtil.isEmpty(formId)) {
			formId = cusInterfaceFeign.getByCusType("base", "MfCusPersonAssetsInfoAction").getShowModel();
		} else {
			if (formId.indexOf("form") == -1) {
			} else {
				formId = formId.substring(4);
			}
		}
		FormData formcuspersassets0003 = formService.getFormData(formId);
		getFormValue(formcuspersassets0003, getMapByJson(ajaxData));
		MfCusPersonAssetsInfo mfCusPersonAssetsInfoNew = new MfCusPersonAssetsInfo();
		setObjValue(formcuspersassets0003, mfCusPersonAssetsInfoNew);
		mfCusPersonAssetsInfo.setAssetsId(mfCusPersonAssetsInfoNew.getAssetsId());
		mfCusPersonAssetsInfo = cusInterfaceFeign.getMfCusPersonAssetsInfoById(mfCusPersonAssetsInfo);
		if (mfCusPersonAssetsInfo != null) {
			try {
				mfCusPersonAssetsInfo = (MfCusPersonAssetsInfo) EntityUtil.reflectionSetVal(mfCusPersonAssetsInfo,
						mfCusPersonAssetsInfoNew, getMapByJson(ajaxData));
				cusInterfaceFeign.updateCusOtherInfo("mf_cus_person_assets_info", mfCusPersonAssetsInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

}
