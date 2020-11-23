package app.component.pact.extension.controller;

import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusAppKindFeign;
import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.EntityUtil;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusFollowPact;
import app.component.pact.entity.MfBusPact;
import app.component.pact.extension.entity.MfBusExtensionApply;
import app.component.pact.extension.entity.MfBusExtensionApproveHis;
import app.component.pact.extension.entity.MfBusExtensionResultDetail;
import app.component.pact.extension.feign.MfBusExtensionApplyFeign;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.prdct.entity.MfSysKind;
import app.component.prdct.feign.MfSysKindFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.TaskFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.dhcc.workflow.pvm.internal.util.StringUtil;
import config.YmlConfig;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfBusExtensionApplyAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 06 11:05:09 CST 2017
 **/
@Controller
@RequestMapping("/mfBusExtensionApply")
public class MfBusExtensionApplyController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfBusExtensionApplyBo
	@Autowired
	private MfBusExtensionApplyFeign mfBusExtensionApplyFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private TaskFeign taskFeign;
	@Autowired
	private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
    private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private MfSysKindFeign mfSysKindFeign;
	@Autowired
	private MfBusAppKindFeign mfBusAppKindFeign;

	// 全局变量
	// 异步参数

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/pact/extension/MfBusExtensionApply_List";
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		try {
			mfBusExtensionApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusExtensionApply.setCriteriaList(mfBusExtensionApply, ajaxData);// 我的筛选
			// mfBusExtensionApply.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfBusExtensionApply,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfBusExtensionApplyFeign.findByPage(ipage, mfBusExtensionApply);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> ajaxDataMap = getMapByJson(ajaxData);

			MfBusFincApp finc = new MfBusFincApp();
			finc.setFincId((String) ajaxDataMap.get("fincId"));
			finc = mfBusFincAppFeign.getById(finc);

			String SUPPORT_OVERDUE_EXT = new CodeUtils().getSingleValByKey("SUPPORT_OVERDUE_EXT");// 是否支持逾期数据展期: 1-是, 0-否
			if ("0".equals(SUPPORT_OVERDUE_EXT) && "1".equals(finc.getOverdueSts())) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "逾期的贷款不允许展期");
				return dataMap;
			}


			//判断展期期限是否大于产品最大期限
			String extenTerm = (String) ajaxDataMap.get("extenTerm");
			String kindNo = finc.getKindNo();
			MfSysKind mfSysKind = new MfSysKind();
			mfSysKind.setKindNo(kindNo);
			mfSysKind = mfSysKindFeign.getById(mfSysKind);
			if(StringUtil.isEmpty(extenTerm)||Integer.parseInt(extenTerm)>mfSysKind.getMaxFincTerm()){
				dataMap.put("flag", "error");
				dataMap.put("msg", "展期期限不能大于产品最大期限");
				return dataMap;
			}

			String REPAY_TYPE_EXT = new CodeUtils().getSingleValByKey("REPAY_TYPE_EXT");// 支持展期的还款方式, 逗号分隔, 字典项详见REPAY_TYPE
			if (REPAY_TYPE_EXT != null && REPAY_TYPE_EXT.indexOf(finc.getRepayType()) < 0) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "该还款方式不支持展期");
				return dataMap;
			}

			FormData formextensionapply0002 = formService.getFormData("extensionapply0001");
			getFormValue(formextensionapply0002, ajaxDataMap);
			if (this.validateFormData(formextensionapply0002)) {
				MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
				setObjValue(formextensionapply0002, mfBusExtensionApply);
				mfBusExtensionApply = mfBusExtensionApplyFeign.insert(mfBusExtensionApply);
				dataMap.put("extensionApply", mfBusExtensionApply);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
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
		FormData formextensionapply0002 = formService.getFormData("extensionapply0002");
		getFormValue(formextensionapply0002, getMapByJson(ajaxData));
		MfBusExtensionApply mfBusExtensionApplyJsp = new MfBusExtensionApply();
		setObjValue(formextensionapply0002, mfBusExtensionApplyJsp);
		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		mfBusExtensionApply.setExtensionApplyId(mfBusExtensionApplyJsp.getExtensionApplyId());
		mfBusExtensionApply = mfBusExtensionApplyFeign.getById(mfBusExtensionApply);
		if (mfBusExtensionApply != null) {
			try {
				mfBusExtensionApply = (MfBusExtensionApply) EntityUtil.reflectionSetVal(mfBusExtensionApply,
						mfBusExtensionApplyJsp, getMapByJson(ajaxData));
				mfBusExtensionApplyFeign.update(mfBusExtensionApply);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
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
			FormData formextensionapply0002 = formService.getFormData("extensionapply0002");
			getFormValue(formextensionapply0002, getMapByJson(ajaxData));
			if (this.validateFormData(formextensionapply0002)) {
				MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
				setObjValue(formextensionapply0002, mfBusExtensionApply);
				mfBusExtensionApplyFeign.update(mfBusExtensionApply);
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
			throw new Exception(e.getMessage());
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
	public Map<String, Object> getByIdAjax(String formId, String query, String fincId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formextensionapply0002 = formService.getFormData(formId);
		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		mfBusExtensionApply.setFincId(fincId);
		dataMap = mfBusExtensionApplyFeign.getExtensionDetailInfo(mfBusExtensionApply);
//		mfBusExtensionApply = (MfBusExtensionApply) dataMap.get("extensionApply");
		mfBusExtensionApply = (MfBusExtensionApply)JsonStrHandling.handlingStrToBean( dataMap.get("extensionApply"), MfBusExtensionApply.class);

		

		if (!(BizPubParm.EXTENSION_APP_STS_APPLY.equals(mfBusExtensionApply.getExtenAppSts()))) {
			// 一旦提交审批，则设置为query，解析出的申请表单不可单字段编辑
			query = "query";
			/**
			 * 上次审批意见类型。 如果上次审批的审批意见类型为发回补充资料，设置query为"",表单可编辑,要件可上传
			 */
			if (BizPubParm.EXTENSION_APP_STS_DEALER.equals(mfBusExtensionApply.getExtenAppSts())) {
				query = "";
			}
		}
		getObjValue(formextensionapply0002, mfBusExtensionApply);

		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);

		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
		this.changeFormProperty(formextensionapply0002, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formextensionapply0002, "extenFincRate", "unit", rateUnit);
		// 处理期限的展示单位。
		Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
		String termUnit = termTypeMap.get(mfBusFincApp.getTermType()).getRemark();
		this.changeFormProperty(formextensionapply0002, "extenTerm", "unit", termUnit);
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String htmlStr = jsonFormUtil.getJsonStr(formextensionapply0002, "propertySeeTag", query);
		dataMap.put("htmlStr", htmlStr);
		dataMap.put("query", query);
		dataMap.put("ifBizManger", "3");
		if (mfBusExtensionApply != null) {
			dataMap.put("extensionApply", mfBusExtensionApply);
			dataMap.put("flag", "success");
		} else {
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String extensionApplyId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		mfBusExtensionApply.setExtensionApplyId(extensionApplyId);
		try {
			mfBusExtensionApplyFeign.delete(mfBusExtensionApply);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String fincId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formextensionapply0001 = formService.getFormData("extensionapply0001");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fincId", fincId);
		paramMap.put("opNo", User.getRegNo(request));
		paramMap.put("opName", User.getRegName(request));
		dataMap = mfBusExtensionApplyFeign.initInputData(paramMap);
		MfBusExtensionApply mfBusExtensionApply = (MfBusExtensionApply)JsonStrHandling.handlingStrToBean(dataMap.get("mfBusExtensionApply"), MfBusExtensionApply.class);
		MfBusFincApp mfBusFincApp = (MfBusFincApp)JsonStrHandling.handlingStrToBean(dataMap.get("mfBusFincApp"), MfBusFincApp.class);
		getObjValue(formextensionapply0001, mfBusExtensionApply);
		getObjValue(formextensionapply0001, mfBusFincApp);
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
		this.changeFormProperty(formextensionapply0001, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formextensionapply0001, "extenFincRate", "unit", rateUnit);
		// 处理期限的展示单位。
		Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
		String termUnit = termTypeMap.get(mfBusFincApp.getTermType()).getRemark();
		this.changeFormProperty(formextensionapply0001, "extenTerm", "unit", termUnit);
		model.addAttribute("mfBusExtensionApply", mfBusExtensionApply);
		model.addAttribute("formextensionapply0001", formextensionapply0001);
		model.addAttribute("query", "");
		return "/component/pact/extension/MfBusExtensionApply_Insert";
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
		FormData formextensionapply0002 = formService.getFormData("extensionapply0002");
		getFormValue(formextensionapply0002);
		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		setObjValue(formextensionapply0002, mfBusExtensionApply);
		mfBusExtensionApplyFeign.insert(mfBusExtensionApply);
		getObjValue(formextensionapply0002, mfBusExtensionApply);
		this.addActionMessage(model, "保存成功");
		List<MfBusExtensionApply> mfBusExtensionApplyList = (List<MfBusExtensionApply>) mfBusExtensionApplyFeign
				.findByPage(this.getIpage(), mfBusExtensionApply).getResult();
		model.addAttribute("mfBusExtensionApplyList", mfBusExtensionApplyList);
		model.addAttribute("formextensionapply0002", formextensionapply0002);
		model.addAttribute("query", "");
		return "/component/pact/extension/MfBusExtensionApply_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String extensionApplyId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formextensionapply0002 = formService.getFormData("extensionapply0001");
		getFormValue(formextensionapply0002);
		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		mfBusExtensionApply.setExtensionApplyId(extensionApplyId);
		mfBusExtensionApply = mfBusExtensionApplyFeign.getById(mfBusExtensionApply);
		getObjValue(formextensionapply0002, mfBusExtensionApply);
		model.addAttribute("extensionApplyId", extensionApplyId);
		model.addAttribute("formextensionapply0002", formextensionapply0002);
		model.addAttribute("query", "");
		return "/component/pact/extension/MfBusExtensionApply_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String extensionApplyId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		mfBusExtensionApply.setExtensionApplyId(extensionApplyId);
		mfBusExtensionApplyFeign.delete(mfBusExtensionApply);
		return getListPage(model);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formextensionapply0002 = formService.getFormData("extensionapply0002");
		getFormValue(formextensionapply0002);
		boolean validateFlag = this.validateFormData(formextensionapply0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formextensionapply0002 = formService.getFormData("extensionapply0002");
		getFormValue(formextensionapply0002);
		boolean validateFlag = this.validateFormData(formextensionapply0002);
	}

	/**
	 * 
	 * 方法描述： 根据借据号获得正在进行的展期业务信息
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-9-6 下午5:34:28
	 */
	@RequestMapping(value = "/getExtensionInfoByFincIdAjax")
	@ResponseBody
	public Map<String, Object> getExtensionInfoByFincIdAjax(String fincId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		try {
			mfBusExtensionApply.setFincId(fincId);
			dataMap = mfBusExtensionApplyFeign.getExtensionInfoByFincId(mfBusExtensionApply);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_DATA_CREDIT.getMessage("当前业务节点的参数"));
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述：获得展期业务流程信息
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-9-6 下午5:26:13
	 */
	@RequestMapping(value = "/getTaskInfoAjax")
	@ResponseBody
	public Map<String, Object> getTaskInfoAjax(String wkfAppId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		try {
			mfBusExtensionApply.setWkfAppId(wkfAppId);
			mfBusExtensionApply = mfBusExtensionApplyFeign.getById(mfBusExtensionApply);
			dataMap.put("approveNodeName", mfBusExtensionApply.getApproveNodeName());
			dataMap.put("approvePartName", mfBusExtensionApply.getApprovePartName());
			// 根据业务流程编号
			TaskImpl task = wkfInterfaceFeign.getTask(wkfAppId, null);
			if (task == null) {// 表示业务已经结束
				dataMap.put("wkfFlag", "0");
			} else {// 表示业务尚未结束
				dataMap.put("wkfFlag", "1");
				String url=taskFeign.openApproveUrl(task.getId());
//				String url = SysTaskInfoUtil.openApproveUrl(task.getId());
				String title = task.getDescription();
				dataMap.put("result", task.getResult());
				dataMap.put("url", url);
				dataMap.put("title", title);
				dataMap.put("nodeName", task.getActivityName());
				dataMap.put("extenAppSts", mfBusExtensionApply.getExtenAppSts());// 状态
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_DATA_CREDIT.getMessage("当前业务节点的参数"));
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 原业务押品和展期业务建立关联关系
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-9-7 上午10:26:49
	 */
	@RequestMapping(value = "/setExtensionPledgeRelationAjax")
	@ResponseBody
	public Map<String, Object> setExtensionPledgeRelationAjax(String skipFlag, String pledgeId, String wkfAppId)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		try {
			dataMap.put("skipFlag", skipFlag);
			dataMap.put("pledgeId", pledgeId);
			dataMap.put("wkfAppId", wkfAppId);
			dataMap.put("regNo", User.getRegNo(request));
			mfBusExtensionApply = mfBusExtensionApplyFeign.setExtensionPledgeRelation(dataMap);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_DATA_CREDIT.getMessage("当前业务节点的参数"));
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述：打开展期尽调报告页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-9-7 上午11:14:43
	 */
	@RequestMapping(value = "/investigationInput")
	public String investigationInput(Model model, String extensionApplyId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formextensionapply0002 = formService.getFormData("extensionapply0001");
		getFormValue(formextensionapply0002);
		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		mfBusExtensionApply.setExtensionApplyId(extensionApplyId);
		dataMap = mfBusExtensionApplyFeign.investigationInputData(mfBusExtensionApply);
		mfBusExtensionApply = (MfBusExtensionApply)JsonStrHandling.handlingStrToBean( dataMap.get("mfBusExtensionApply"), MfBusExtensionApply.class);
//		mfBusExtensionApply = (MfBusExtensionApply) dataMap.get("mfBusExtensionApply");
		getObjValue(formextensionapply0002, mfBusExtensionApply);
		MfBusFincApp mfBusFincApp = (MfBusFincApp)JsonStrHandling.handlingStrToBean( dataMap.get("mfBusFincApp"), MfBusFincApp.class);
//		MfBusFincApp mfBusFincApp = (MfBusFincApp) dataMap.get("mfBusFincApp");
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
		this.changeFormProperty(formextensionapply0002, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formextensionapply0002, "extenFincRate", "unit", rateUnit);
		// 处理期限的展示单位。
		Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
		String termUnit = termTypeMap.get(mfBusFincApp.getTermType()).getRemark();
		this.changeFormProperty(formextensionapply0002, "extenTerm", "unit", termUnit);
		String nodeNo = WKF_NODE.extension_investigation.getNodeNo();// 功能节点编号
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("formextensionapply0002", formextensionapply0002);
		model.addAttribute("mfBusExtensionApply", mfBusExtensionApply);
		model.addAttribute("extensionApplyId", extensionApplyId);
		model.addAttribute("query", "");
		return "/component/pact/extension/MfBusExtensionInvestigation_Input";
	}

	/**
	 * 
	 * 方法描述： 保存尽调报告
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-9-7 上午11:56:09
	 */
	@RequestMapping(value = "/saveInvestigationReportAjax")
	@ResponseBody
	public Map<String, Object> saveInvestigationReportAjax(String ajaxData,String nodeNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		try {

			Map<String, Object> formDataMap = getMapByJson(ajaxData);
			//判断当前节点是否与流程相符
			TaskImpl taskApprove = wkfInterfaceFeign.getTask((String)formDataMap.get("wkfAppId"), "");
			if(!nodeNo.equals(String.valueOf(taskApprove.getActivityName()))){//不相等则节点不在此环节
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FIRST_CHECK_APPROVEFLOW.getMessage());
				return dataMap;
			}

			FormData formextensionapply0002 = formService.getFormData("extensionapply0001");
			getFormValue(formextensionapply0002, formDataMap);
			setObjValue(formextensionapply0002, mfBusExtensionApply);
			// 添加展期结果信息
			MfBusExtensionResultDetail mfBusExtensionResultDetail = new MfBusExtensionResultDetail();
			setObjValue(formextensionapply0002, mfBusExtensionResultDetail);
			formDataMap.put("mfBusExtensionResultDetail", mfBusExtensionResultDetail);
			formDataMap.put("mfBusExtensionApply", mfBusExtensionApply);
			dataMap = mfBusExtensionApplyFeign.doConfirmInvestigationReport(formDataMap);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_DATA_CREDIT.getMessage("当前业务节点的参数"));
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 跳转展期业务审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-9-7 下午2:27:09
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String extensionApplyId, String activityType,String hideOpinionType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formextensionApprove = formService.getFormData("extensionApprove");
		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		mfBusExtensionApply.setExtensionApplyId(extensionApplyId);
		dataMap = mfBusExtensionApplyFeign.initApproveData(mfBusExtensionApply);
//		mfBusExtensionApply = (MfBusExtensionApply) dataMap.get("mfBusExtensionApply");
		mfBusExtensionApply = (MfBusExtensionApply)JsonStrHandling.handlingStrToBean( dataMap.get("mfBusExtensionApply"), MfBusExtensionApply.class);
		getObjValue(formextensionApprove, mfBusExtensionApply);
		model.addAttribute("mfBusExtensionApply", mfBusExtensionApply);
		MfBusFincApp mfBusFincApp = (MfBusFincApp)JsonStrHandling.handlingStrToBean( dataMap.get("mfBusFincApp"), MfBusFincApp.class);
//		MfBusFincApp mfBusFincApp = (MfBusFincApp) dataMap.get("mfBusFincApp");
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
		this.changeFormProperty(formextensionApprove, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formextensionApprove, "extenFincRate", "unit", rateUnit);
		this.changeFormProperty(formextensionApprove, "extenFincRateShow", "unit", rateUnit);
		// 处理期限的展示单位。
		Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
		String termUnit = termTypeMap.get(mfBusFincApp.getTermType()).getRemark();
		this.changeFormProperty(formextensionApprove, "extenTerm", "unit", termUnit);
		this.changeFormProperty(formextensionApprove, "extenTermShow", "unit", termUnit);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(extensionApplyId, null);// 当前审批节点task
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo1 = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo1);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(),opinionTypeMap);
		this.changeFormProperty(formextensionApprove, "opinionType", "optionArray", opinionTypeList);
		String nodeNo = WKF_NODE.extension_approve.getNodeNo();// 功能节点编号
		model.addAttribute("nodeNo", nodeNo);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes(extensionApplyId, User.getRegNo(request));
		model.addAttribute("befNodesjsonArray", befNodesjsonArray);
		Map<String,String> parmMap = new HashMap<String,String>();
		parmMap.put("appId",mfBusFincApp.getAppId());
		String calcIntstFlag = prdctInterfaceFeign.getCalcIntstFlag(parmMap);
		model.addAttribute("calcIntstFlag", calcIntstFlag);
		model.addAttribute("extensionApplyId", extensionApplyId);
		model.addAttribute("mfBusExtensionApply", mfBusExtensionApply);
		model.addAttribute("formextensionApprove", formextensionApprove);
		model.addAttribute("query", "");
		return "/component/pact/extension/WkfExtensionViewPoint";
	}

	/**
	 * 
	 * 方法描述： 保存展期业务审批审批意见并提交审批
	 * 
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-9-7 下午5:55:28
	 */
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String taskId, String transition, String nextUser)
			throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formDataMap = getMapByJson(ajaxData);
		//判断当前节点是否可进行审批
		TaskImpl taskApprove = wkfInterfaceFeign.getTask((String)formDataMap.get("extensionApplyId"), "");
		if(!taskId.equals(String.valueOf(taskApprove.getDbid()))){//不相等则审批不在此环节
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FIRST_CHECK_APPROVEFLOW.getMessage());
			return dataMap;
		}

		FormData formextensionApprove = formService.getFormData("extensionApprove");
		getFormValue(formextensionApprove, formDataMap);
		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		MfBusExtensionApproveHis mfBusExtensionApproveHis = new MfBusExtensionApproveHis();
		setObjValue(formextensionApprove, mfBusExtensionApply);
		setObjValue(formextensionApprove, mfBusExtensionApproveHis);
		Result res;
		try {
			formDataMap.put("opNo", User.getRegNo(request));
			formDataMap.put("orgNo", User.getOrgNo(request));
			formDataMap.put("mfBusExtensionApply", mfBusExtensionApply);
			formDataMap.put("mfBusExtensionApproveHis", mfBusExtensionApproveHis);
			res = mfBusExtensionApplyFeign.doCommit(taskId, transition, nextUser, formDataMap);
			dataMap = new HashMap<String, Object>();
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
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述：跳转合同签约页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-9-7 下午7:42:46
	 */
	@RequestMapping(value = "/extensionContractSign")
	public String extensionContractSign(Model model, String extensionApplyId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formextensionContractSign = formService.getFormData("extensionContractSign");
		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		mfBusExtensionApply.setExtensionApplyId(extensionApplyId);
		dataMap = mfBusExtensionApplyFeign.initContractSignData(mfBusExtensionApply);
		MfBusPact mfBusPact = (MfBusPact)JsonStrHandling.handlingStrToBean(dataMap.get("mfBusPact"), MfBusPact.class);
//		MfBusPact mfBusPact = (MfBusPact) dataMap.get("mfBusPact");
		MfBusExtensionResultDetail mfBusExtensionResultDetail = (MfBusExtensionResultDetail)JsonStrHandling.handlingStrToBean(dataMap.get("mfBusExtensionResultDetail"), MfBusExtensionResultDetail.class);
//		MfBusExtensionResultDetail mfBusExtensionResultDetail = (MfBusExtensionResultDetail) dataMap
//				.get("mfBusExtensionResultDetail");
		getObjValue(formextensionContractSign, mfBusExtensionResultDetail);
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
		this.changeFormProperty(formextensionContractSign, "extenFincRate", "unit", rateUnit);
		// 处理期限的展示单位。
		Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
		String termUnit = termTypeMap.get(mfBusPact.getTermType()).getRemark();
		this.changeFormProperty(formextensionContractSign, "extenTerm", "unit", termUnit);
		String nodeNo = WKF_NODE.extension_contract_sign.getNodeNo();// 功能节点编号

		//展期签约展示从合同
		List<MfBusFollowPact> mfBusFollowPactList = new ArrayList<MfBusFollowPact>();
		Double pactAmt = mfBusPact.getPactAmt();
		String appId = mfBusPact.getAppId();
		mfBusFollowPactList = mfBusCollateralDetailRelFeign.getMfBusFollowPactList(appId, pactAmt);
		if (mfBusFollowPactList.size() == 0) {
			mfBusFollowPactList = null;
		}
		mfBusExtensionApply.setAppId(appId);
		model.addAttribute("mfBusFollowPactList", mfBusFollowPactList);
		// 从合同展示号 是否启用  1:启用
		String followPactNoShowSts = new CodeUtils().getSingleValByKey("FOLLOW_PACTNO_SHOW_STS");
		model.addAttribute("followPactNoShowSts", followPactNoShowSts);
		model.addAttribute("mfBusExtensionApply", mfBusExtensionApply);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("formextensionContractSign", formextensionContractSign);
		model.addAttribute("extensionApplyId", extensionApplyId);
		model.addAttribute("query", "");
		model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
		return "/component/pact/extension/MfExtensionContractSign_input";
	}

	/**
	 * 
	 * 方法描述：展期合同签约保存
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-9-8 上午9:32:51
	 */
	@RequestMapping(value = "/saveContractSignAjax")
	@ResponseBody
	public Map<String, Object> saveContractSignAjax(String ajaxData, String skipFlag,String nodeNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formDataMap = getMapByJson(ajaxData);
		//判断当前节点是否与流程相符
		TaskImpl taskApprove = wkfInterfaceFeign.getTask((String)formDataMap.get("wkfAppId"), "");
		if(!nodeNo.equals(String.valueOf(taskApprove.getActivityName()))){//不相等则节点不在此环节
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FIRST_CHECK_APPROVEFLOW.getMessage());
			return dataMap;
		}

		FormData formextensionContractSign = formService.getFormData("extensionContractSign");
		getFormValue(formextensionContractSign, getMapByJson(ajaxData));
		if (this.validateFormData(formextensionContractSign) || BizPubParm.YES_NO_Y.equals(skipFlag)) {
			formDataMap.put("skipFlag", skipFlag);
			formDataMap.put("regNo", User.getRegNo(request));
			dataMap = mfBusExtensionApplyFeign.saveContractSign(formDataMap);
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", this.getFormulavaliErrorMsg());
		}
		return dataMap;
	}


	/**
	 * 方法描述：本次展期终止页面
	 *
	 * @return
	 * @throws Exception String
	 * @author zkq
	 * @date 20190710 下午6:55:42
	 */
	@RequestMapping(value = "/inputDisagreeBussExtension")
	public String inputDisagreeBussExtension(Model model, String appId, String pactId, String fincId, String extensionApplyId, String busFlag) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		//展期拒绝表单
		FormData formdisagreeBuss = formService.getFormData("disagreeBussExtension");

		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		mfBusExtensionApply.setExtensionApplyId(extensionApplyId);
		mfBusExtensionApply = mfBusExtensionApplyFeign.getById(mfBusExtensionApply);

		mfBusExtensionApply.setDisagreeType("2");//终止业务否决
		mfBusExtensionApply.setDisagreeOpName(User.getRegName(request));
		mfBusExtensionApply.setDisagreeOpNo(User.getRegNo(request));
		mfBusExtensionApply.setDisagreeTime(DateUtil.getDateTime());
		mfBusExtensionApply.setDisagreeDate(DateUtil.getDate());

		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(appId);
		mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
		mfBusExtensionApply.setExtenFincRate(MathExtend.showRateMethod(mfBusExtensionApply.getRateType(), mfBusExtensionApply.getExtenFincRate(), Integer.parseInt(mfBusAppKind.getYearDays()), Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));

		getObjValue(formdisagreeBuss, mfBusExtensionApply);
		//处理期限的展示单位。
		Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
		String termUnit = termTypeMap.get(mfBusExtensionApply.getTermType()).getRemark();
		this.changeFormProperty(formdisagreeBuss, "extenTerm", "unit", termUnit);
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusExtensionApply.getRateType()).getRemark();
		this.changeFormProperty(formdisagreeBuss, "extenFincRate", "unit", rateUnit);
		model.addAttribute("formdisagreeBuss", formdisagreeBuss);
		model.addAttribute("query", "");
		model.addAttribute("appId", appId);
		model.addAttribute("pactId", pactId);
		model.addAttribute("fincId", fincId);
		model.addAttribute("extensionApplyId", extensionApplyId);
		model.addAttribute("busFlag", busFlag);
		model.addAttribute("cusNo", mfBusExtensionApply.getCusNo());
		return "/component/app/inputDisagreeBuss";
	}


	/**
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 * @desc 终止本次展期
	 * @Author zkq
	 * @date 20190711
	 */
	@RequestMapping(value = "/disagreeExtensionAjax")
	@ResponseBody
	public Map<String, Object> disagreeExtensionAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdisagreeBuss = formService.getFormData("disagreeBussExtension");
		getFormValue(formdisagreeBuss, getMapByJson(ajaxData));
		if (this.validateFormData(formdisagreeBuss)) {
			MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
			setObjValue(formdisagreeBuss, mfBusExtensionApply);
			dataMap = mfBusExtensionApplyFeign.doDisagreeExtensionBuss(mfBusExtensionApply);
		}
		return dataMap;
	}


	/**
	 * AJAX获取查看
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdNewAjax")
	@ResponseBody
	public Map<String, Object> getByIdNewAjax(String formId, String query, String extensionId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		request.setAttribute("ifBizManger", "3");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formextensionapply0002 = formService.getFormData(formId);
		MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
		mfBusExtensionApply.setExtensionApplyId(extensionId);
		dataMap = mfBusExtensionApplyFeign.getExtensionDetailInfoNew(mfBusExtensionApply);
//		mfBusExtensionApply = (MfBusExtensionApply) dataMap.get("extensionApply");
		mfBusExtensionApply = (MfBusExtensionApply) JsonStrHandling.handlingStrToBean(dataMap.get("extensionApply"), MfBusExtensionApply.class);


		if (!(BizPubParm.EXTENSION_APP_STS_APPLY.equals(mfBusExtensionApply.getExtenAppSts()))) {
			// 一旦提交审批，则设置为query，解析出的申请表单不可单字段编辑
			query = "query";
			/**
			 * 上次审批意见类型。 如果上次审批的审批意见类型为发回补充资料，设置query为"",表单可编辑,要件可上传
			 */
			if (BizPubParm.EXTENSION_APP_STS_DEALER.equals(mfBusExtensionApply.getExtenAppSts())) {
				query = "";
			}
		}
		getObjValue(formextensionapply0002, mfBusExtensionApply);

		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(mfBusExtensionApply.getFincId());
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);

		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusFincApp.getRateType()).getRemark();
		this.changeFormProperty(formextensionapply0002, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formextensionapply0002, "extenFincRate", "unit", rateUnit);
		// 处理期限的展示单位。
		Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
		String termUnit = termTypeMap.get(mfBusFincApp.getTermType()).getRemark();
		this.changeFormProperty(formextensionapply0002, "extenTerm", "unit", termUnit);
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String htmlStr = jsonFormUtil.getJsonStr(formextensionapply0002, "propertySeeTag", query);
		dataMap.put("htmlStr", htmlStr);
		dataMap.put("query", query);
		dataMap.put("ifBizManger", "3");
		if (mfBusExtensionApply != null) {
			dataMap.put("extensionApply", mfBusExtensionApply);
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

}
