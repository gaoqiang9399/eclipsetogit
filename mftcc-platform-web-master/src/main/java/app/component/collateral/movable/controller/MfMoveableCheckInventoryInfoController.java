package app.component.collateral.movable.controller;

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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.collateral.entity.MfBusCollateralRel;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.feign.MfBusCollateralRelFeign;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.collateral.movable.entity.MfMoveableCheckInventoryInfo;
import app.component.collateral.movable.feign.MfMoveableCheckInventoryInfoFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.EntityUtil;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: MfMoveableCheckInventoryInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Jun 08 15:08:07 CST 2017
 **/
@Controller
@RequestMapping("/mfMoveableCheckInventoryInfo")
public class MfMoveableCheckInventoryInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfMoveableCheckInventoryInfoBo
	@Autowired
	private MfMoveableCheckInventoryInfoFeign mfMoveableCheckInventoryInfoFeign;
	@Autowired
	private MfBusCollateralRelFeign mfBusCollateralRelFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;
	@Autowired
	private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;

	// 全局变量
	// 异步参数
	// 表单变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/collateral/movable/MfMoveableCheckInventoryInfo_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo = new MfMoveableCheckInventoryInfo();
		try {
			mfMoveableCheckInventoryInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfMoveableCheckInventoryInfo.setCriteriaList(mfMoveableCheckInventoryInfo, ajaxData);// 我的筛选
			// mfMoveableCheckInventoryInfo.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfMoveableCheckInventoryInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfMoveableCheckInventoryInfoFeign.findByPage(ipage, mfMoveableCheckInventoryInfo);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
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
	 * 业务流程中添加质押客户保存
	 * 
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertBusAjax")
	@ResponseBody
	public Map<String, Object> insertBusAjax(String ajaxData, String appId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcheckinv0002 = formService.getFormData("checkInventoryBase");
			getFormValue(formcheckinv0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcheckinv0002)) {
				MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo = new MfMoveableCheckInventoryInfo();
				setObjValue(formcheckinv0002, mfMoveableCheckInventoryInfo);
				mfMoveableCheckInventoryInfoFeign.insertBus(mfMoveableCheckInventoryInfo, appId);
				MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
				dataMap.put("flag", "success");
				dataMap.put("appSts", mfBusApply.getAppSts());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 如果业务流程中跳过质押核库，则直接提交到下一个业务节点
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param appId
	 * @date 2017-7-31 下午2:07:17
	 */
	@RequestMapping(value = "/submitBussProcessAjax")
	@ResponseBody
	public Map<String, Object> submitBussProcessAjax(String ajaxData, String appId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			mfMoveableCheckInventoryInfoFeign.submitBussProcess(appId, User.getRegNo(request));
			MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			dataMap.put("flag", "success");
			dataMap.put("appSts", mfBusApply.getAppSts());
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
	 * 
	 * 方法描述： 押品详情中添加质押客户保存
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param appId
	 * @date 2017-7-31 下午2:18:21
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String appId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		request.setAttribute("ifBizManger", "2");
		try {
			FormData formcheckinv0002 = formService.getFormData("checkInventoryBase");
			getFormValue(formcheckinv0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcheckinv0002)) {
				MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo = new MfMoveableCheckInventoryInfo();
				setObjValue(formcheckinv0002, mfMoveableCheckInventoryInfo);
				mfMoveableCheckInventoryInfo.setCheckInventoryId(WaterIdUtil.getWaterId());	
				mfMoveableCheckInventoryInfoFeign.insert(mfMoveableCheckInventoryInfo, appId);

				// 获得基本信息的展示表单ID，并将表单解析
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(
						mfMoveableCheckInventoryInfo.getClassId(), "MfMoveableCheckInventoryInfoAction", "");
				String formId = null;
				if (mfCollateralFormConfig == null) {

				} else {
					formId = mfCollateralFormConfig.getShowModelDef();
				}
				formcheckinv0002 = formService.getFormData(formId);
				/*
				 * if (formcheckinv0002.getFormId() == null) { log.error("押品类型为"
				 * + mfCollateralFormConfig.getFormType() +
				 * "的FairInfoAction表单form" + formId + ".xml文件不存在"); }
				 */
				getFormValue(formcheckinv0002);
				getObjValue(formcheckinv0002, mfMoveableCheckInventoryInfo);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcheckinv0002, "propertySeeTag", "");

				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcheckinv0002 = formService.getFormData("checkinv0002");
		getFormValue(formcheckinv0002, getMapByJson(ajaxData));
		MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfoJsp = new MfMoveableCheckInventoryInfo();
		setObjValue(formcheckinv0002, mfMoveableCheckInventoryInfoJsp);
		MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo = new MfMoveableCheckInventoryInfo();
		mfMoveableCheckInventoryInfo.setCheckInventoryId(mfMoveableCheckInventoryInfoJsp.getCheckInventoryId());
		mfMoveableCheckInventoryInfo = mfMoveableCheckInventoryInfoFeign.getById(mfMoveableCheckInventoryInfo);
		if (mfMoveableCheckInventoryInfo != null) {
			try {
				mfMoveableCheckInventoryInfo = (MfMoveableCheckInventoryInfo) EntityUtil.reflectionSetVal(
						mfMoveableCheckInventoryInfo, mfMoveableCheckInventoryInfoJsp, getMapByJson(ajaxData));
				mfMoveableCheckInventoryInfoFeign.update(mfMoveableCheckInventoryInfo);
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcheckinv0002 = formService.getFormData("checkinv0002");
			getFormValue(formcheckinv0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcheckinv0002)) {
				MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo = new MfMoveableCheckInventoryInfo();
				setObjValue(formcheckinv0002, mfMoveableCheckInventoryInfo);
				mfMoveableCheckInventoryInfoFeign.update(mfMoveableCheckInventoryInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @param checkInventoryId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String checkInventoryId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcheckinv0002 = formService.getFormData("checkinv0002");
		MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo = new MfMoveableCheckInventoryInfo();
		mfMoveableCheckInventoryInfo.setCheckInventoryId(checkInventoryId);
		mfMoveableCheckInventoryInfo = mfMoveableCheckInventoryInfoFeign.getById(mfMoveableCheckInventoryInfo);
		getObjValue(formcheckinv0002, mfMoveableCheckInventoryInfo, formData);
		if (mfMoveableCheckInventoryInfo != null) {
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
	 * @param checkInventoryId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String checkInventoryId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo = new MfMoveableCheckInventoryInfo();
		mfMoveableCheckInventoryInfo.setCheckInventoryId(checkInventoryId);
		try {
			mfMoveableCheckInventoryInfoFeign.delete(mfMoveableCheckInventoryInfo);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 业务流程中跳转到添加押品核库
	 * 
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputBus")
	public String inputBus(Model model, String ajaxData, String appId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);

		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);

		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.check_inventory, null, null,
				User.getRegNo(request));
		FormData formcheckinv0002 = formService.getFormData(formId);

		String nodeNo = WKF_NODE.check_inventory.getNodeNo();// 功能节点编号
		MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
		mfBusCollateralRel.setAppId(appId);
		mfBusCollateralRel = mfBusCollateralRelFeign.getOriginalById(mfBusCollateralRel);
		MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo = new MfMoveableCheckInventoryInfo();
		mfMoveableCheckInventoryInfo.setBusPleId(mfBusCollateralRel.getBusCollateralId());
		getObjValue(formcheckinv0002, mfMoveableCheckInventoryInfo);
		getObjValue(formcheckinv0002, mfBusCollateralRel);
		model.addAttribute("formcheckinv0002", formcheckinv0002);
		model.addAttribute("query", "");
		return "/component/collateral/movable/MfMoveableCheckInventoryInfo_InsertBus";
	}

	/**
	 * 押品详情中跳转添加押品核库页面
	 * 
	 * @param collateralNo
	 * @param appId
	 * @param classId
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String collateralNo, String appId, String classId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String formId = "";
		if (appId != null && !"".equals(appId) && collateralNo == null) {
			MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			if (BizPubParm.VOU_TYPE_1.equals(mfBusApply.getVouType())) {
				dataMap.put("vouType", BizPubParm.VOU_TYPE_1);
				model.addAttribute("dataMap", dataMap);
				model.addAttribute("appId", appId);
				return "/component/collateral/movable/MfMoveableCheckInventoryInfo_InsertBus";
			}

			formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.check_inventory, null, null,
					User.getRegNo(request));
			FormData formcheckinv0002 = formService.getFormData(formId);

			String nodeNo = WKF_NODE.check_inventory.getNodeNo();// 功能节点编号
			MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
			mfBusCollateralRel.setAppId(appId);
			mfBusCollateralRel = mfBusCollateralRelFeign.getOriginalById(mfBusCollateralRel);
			MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo = new MfMoveableCheckInventoryInfo();
			if (mfBusCollateralRel != null) {
				mfMoveableCheckInventoryInfo.setBusPleId(mfBusCollateralRel.getBusCollateralId());
			}
			getObjValue(formcheckinv0002, mfMoveableCheckInventoryInfo);
			getObjValue(formcheckinv0002, mfBusCollateralRel);
			model.addAttribute("formcheckinv0002", formcheckinv0002);
			model.addAttribute("query", "");
			model.addAttribute("appId", appId);
			return "/component/collateral/movable/MfMoveableCheckInventoryInfo_InsertBus";
		} else {
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(collateralNo);
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			classId = pledgeBaseInfo.getClassId();

			MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
					"MfMoveableCheckInventoryInfoAction", "");

			if (mfCollateralFormConfig == null) {

			} else {
				formId = mfCollateralFormConfig.getAddModelDef();
			}
			if (StringUtil.isEmpty(formId)) {
				// Log.error("押品类型为"+mfCollateralFormConfig.getFormType()+"的InsInfoAction表单信息没有查询到");
			} else {
				MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo = new MfMoveableCheckInventoryInfo();
				mfMoveableCheckInventoryInfo.setBusPleId(collateralNo);
				FormData formcheckinv0002 = formService.getFormData(formId);
				if (formcheckinv0002.getFormId() == null) {
					// Log.error("押品类型为"+mfCollateralFormConfig.getFormType()+"的InsInfoAction表单form"+formId+".xml文件不存在");
				} else {
					getFormValue(formcheckinv0002);
					getObjValue(formcheckinv0002, mfMoveableCheckInventoryInfo);
					getObjValue(formcheckinv0002, pledgeBaseInfo);
					model.addAttribute("formcheckinv0002", formcheckinv0002);
				}
			}
			model.addAttribute("query", "");
			model.addAttribute("appId", appId);
			return "/component/collateral/movable/MfMoveableCheckInventoryInfo_Insert";
		}
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcheckinv0002 = formService.getFormData("checkinv0002");
		getFormValue(formcheckinv0002);
		MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo = new MfMoveableCheckInventoryInfo();
		setObjValue(formcheckinv0002, mfMoveableCheckInventoryInfo);
		// mfMoveableCheckInventoryInfoFeign.insert(mfMoveableCheckInventoryInfo);
		getObjValue(formcheckinv0002, mfMoveableCheckInventoryInfo);
		this.addActionMessage(model, "保存成功");
		List<MfMoveableCheckInventoryInfo> mfMoveableCheckInventoryInfoList = (List<MfMoveableCheckInventoryInfo>) mfMoveableCheckInventoryInfoFeign
				.findByPage(this.getIpage(), mfMoveableCheckInventoryInfo).getResult();
		model.addAttribute("formcheckinv0002", formcheckinv0002);
		model.addAttribute("query", "");
		return "/component/collateral/movable/MfMoveableCheckInventoryInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @param appId
	 * @param busPleId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String appId, String busPleId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcheckinvinfo0001 = formService.getFormData("checkinvinfo0001");
		getFormValue(formcheckinvinfo0001);
		MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
		mfBusCollateralRel.setAppId(appId);
		mfBusCollateralRel = mfBusCollateralRelFeign.getOriginalById(mfBusCollateralRel);
		MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo = new MfMoveableCheckInventoryInfo();
		mfMoveableCheckInventoryInfo.setBusPleId(busPleId);
		mfMoveableCheckInventoryInfo = mfMoveableCheckInventoryInfoFeign.getById(mfMoveableCheckInventoryInfo);
		getObjValue(formcheckinvinfo0001, mfMoveableCheckInventoryInfo);
		getObjValue(formcheckinvinfo0001, mfBusCollateralRel);
		model.addAttribute("formcheckinvinfo0001", formcheckinvinfo0001);
		model.addAttribute("query", "");
		return "/component/collateral/movable/MfMoveableCheckInventoryInfo_Detail";
	}

	/**
	 * 删除
	 * 
	 * @param checkInventoryId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String checkInventoryId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo = new MfMoveableCheckInventoryInfo();
		mfMoveableCheckInventoryInfo.setCheckInventoryId(checkInventoryId);
		mfMoveableCheckInventoryInfoFeign.delete(mfMoveableCheckInventoryInfo);
		return getListPage(model);
	}

}
