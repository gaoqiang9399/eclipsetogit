package app.component.pact.receaccount.controller;

import app.base.User;
import app.component.accnt.entity.MfAccntRepayDetail;
import app.component.accnt.feign.MfAccntRepayDetailFeign;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.busviewinterface.BusViewInterfaceFeign;
import app.component.calccoreinterface.CalcRepaymentInterfaceFeign;
import app.component.collateral.entity.CollateralConstant;
import app.component.collateral.entity.MfCollateralClass;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.feign.MfCollateralClassFeign;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateral.feign.MfPleRepoApplyFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.receaccount.entity.MfBusReceBal;
import app.component.pact.receaccount.entity.MfBusReceBaseInfo;
import app.component.pact.receaccount.entity.MfBusReceTransfer;
import app.component.pact.receaccount.entity.MfReceBusinessContractInfo;
import app.component.pact.receaccount.feign.MfBusReceBalFeign;
import app.component.pact.receaccount.feign.MfBusReceBaseInfoFeign;
import app.component.pact.receaccount.feign.MfBusReceTransferFeign;
import app.component.pact.receaccount.feign.MfReceBusinessContractInfoFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
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
 * Title: MfBusReceTransferController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 18:19:17 CST 2017
 **/
@Controller
@RequestMapping("/mfBusReceBaseInfo")
public class MfBusReceBaseInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusReceBaseInfoFeign mfBusReceBaseInfoFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private BusViewInterfaceFeign busViewInterfaceFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;
	@Autowired
	private MfCollateralClassFeign mfCollateralClassFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfReceBusinessContractInfoFeign mfReceBusinessContractInfoFeign;
	@Autowired
	private MfBusReceBalFeign mfBusReceBalFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private MfAccntRepayDetailFeign mfAccntRepayDetailFeign;
	@Autowired
	private CalcRepaymentInterfaceFeign calcRepaymentInterfaceFeign;
	@Autowired
	private MfBusReceTransferFeign mfBusReceTransferFeign;
	@Autowired
	private MfPleRepoApplyFeign mfPleRepoApplyFeign;


	@RequestMapping(value = "/input")
	public String input(Model model,String cusNo,String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		JSONObject json = new JSONObject();
		//客户信息
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		//获取应收账款的类别列表
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
		mfCollateralClass.setClassFirstNo("E");
		mfCollateralClass.setUseFlag(CollateralConstant.USED);
		List<MfCollateralClass> list = mfCollateralClassFeign.getAll(mfCollateralClass);
		JSONArray collClassArray = new JSONArray();
		for(MfCollateralClass collateralClass:list){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id",collateralClass.getClassId());
			jsonObject.put("name",collateralClass.getClassSecondName());
			collClassArray.add(jsonObject);
		}
		//取第一个作为默认值
		mfCollateralClass = new MfCollateralClass();
		if(list!=null && list.size()>0) {
			mfCollateralClass = list.get(0);
			// 获得押品动态表单
			MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(mfCollateralClass.getClassId(), "PledgeBaseInfoAction", "");
			if (mfCollateralFormConfig != null) {
				String formId = mfCollateralFormConfig.getAddModelDef();
				model.addAttribute("classId", mfCollateralClass.getClassId());
				FormData formrecebaseinfo = formService.getFormData(formId);
				if (formrecebaseinfo.getFormId() == null) {
					formrecebaseinfo = formService.getFormData(mfCollateralFormConfig.getAddModel());
				}
				MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
				mfBusReceBaseInfo.setReceSts(BizPubParm.RECE_STS_0);
				mfBusReceBaseInfo.setCusNo(cusNo);
				mfBusReceBaseInfo.setAppId(appId);
				mfBusReceBaseInfo.setCusName(mfCusCustomer.getCusName());
				mfBusReceBaseInfo.setClassId(mfCollateralClass.getClassId());
				getFormValue(formrecebaseinfo);
				getObjValue(formrecebaseinfo, mfBusReceBaseInfo);
				model.addAttribute("formrecebaseinfo", formrecebaseinfo);
			}
		}
		//获取改客户的应收账款商务合同列表
		MfReceBusinessContractInfo mfReceBusinessContractInfo = new MfReceBusinessContractInfo();
		mfReceBusinessContractInfo.setCusNo(cusNo);
		List<MfReceBusinessContractInfo> receBusinessContractInfoList = mfReceBusinessContractInfoFeign.getList(mfReceBusinessContractInfo);
		JSONArray recePactArray = JSONArray.fromObject(receBusinessContractInfoList);
		for(int i=0;i<recePactArray.size();i++){
			recePactArray.getJSONObject(i).put("id",recePactArray.getJSONObject(i).getString("recePactId"));
			recePactArray.getJSONObject(i).put("name", recePactArray.getJSONObject(i).getString("recePactNo"));
		}
		json.put("collClassArray",collClassArray);
		json.put("recePactArray",recePactArray);
		model.addAttribute("appId", appId);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("ajaxData",json.toString());
		model.addAttribute("query", "");
		return "component/pact/receaccount/MfBusReceBaseInfo_Insert";
	}
	/**
	 * AJAX新增
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String appId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String formId = (String) getMapByJson(ajaxData).get("formId");
			FormData formrecebaseinfo = formService.getFormData(formId);
			getFormValue(formrecebaseinfo, getMapByJson(ajaxData));
			if (this.validateFormData(formrecebaseinfo)) {
				MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
				setObjValue(formrecebaseinfo, mfBusReceBaseInfo);
				mfBusReceBaseInfo.setReceSts(BizPubParm.RECE_STS_0);//登记
				if(StringUtil.isNotEmpty(appId)){
					mfBusReceBaseInfo.setReceSts(BizPubParm.RECE_STS_1);//已使用
				}
				mfBusReceBaseInfo = mfBusReceBaseInfoFeign.insert(mfBusReceBaseInfo);
				dataMap.put("mfBusReceBaseInfo", mfBusReceBaseInfo);
				dataMap.put("receId",mfBusReceBaseInfo.getReceId());
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
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
	 * 方法描述:业务流程中进行账款登记
	 * @param model
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputForBus")
	public String inputForBus(Model model,String cusNo,String appId) throws Exception {
		ActionContext.initialize(request, response);
		//根据申请编号，获取业务关联下的应收账款总额、转让总额以及转让余额
		MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
		mfBusReceBaseInfo.setAppId(appId);
		List<MfBusReceBaseInfo> mfBusReceBaseInfoList = mfBusReceBaseInfoFeign.getMfBusReceBaseInfoList(mfBusReceBaseInfo);

		model.addAttribute("appId", appId);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("mfBusReceBaseInfoList", mfBusReceBaseInfoList);
		model.addAttribute("query", "");
		return "component/pact/receaccount/MfBusReceBaseInfo_InputList";
	}

	/**
	 *
	 * 方法描述 业务流程中进行账款登记保存方法
	 * @param [ajaxData, appId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author zhs
	 * @date 2018/9/1 11:37
	 */
	@RequestMapping(value = "/insertForBusAjax")
	@ResponseBody
	public Map<String, Object> insertForBusAjax(String ajaxData,String appId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String formId = (String) getMapByJson(ajaxData).get("formId");
			FormData formrecebaseinfo = formService.getFormData(formId);
			getFormValue(formrecebaseinfo, getMapByJson(ajaxData));
			if (this.validateFormData(formrecebaseinfo)) {
				MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
				setObjValue(formrecebaseinfo, mfBusReceBaseInfo);
				mfBusReceBaseInfo.setReceSts(BizPubParm.RECE_STS_1);//已使用
				dataMap =  mfBusReceBaseInfoFeign.insertForBus(mfBusReceBaseInfo);
				dataMap.put("mfBusReceBaseInfo", mfBusReceBaseInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
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
		FormData formrecebaseinfo = formService.getFormData("assethandle0002");
		getFormValue(formrecebaseinfo, getMapByJson(ajaxData));
		MfBusReceBaseInfo mfBusReceBaseInfoJsp = new MfBusReceBaseInfo();
		setObjValue(formrecebaseinfo, mfBusReceBaseInfoJsp);
		MfBusReceBaseInfo mfBusReceBaseInfo = mfBusReceBaseInfoFeign.getById(mfBusReceBaseInfoJsp);
		if (mfBusReceBaseInfo != null) {
			try {
				mfBusReceBaseInfo = (MfBusReceBaseInfo) EntityUtil.reflectionSetVal(mfBusReceBaseInfo,
						mfBusReceBaseInfoJsp, getMapByJson(ajaxData));
				mfBusReceBaseInfoFeign.update(mfBusReceBaseInfo);
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
			FormData formrecebaseinfo = formService.getFormData("assethandle0002");
			getFormValue(formrecebaseinfo, getMapByJson(ajaxData));
			if (this.validateFormData(formrecebaseinfo)) {
				MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
				setObjValue(formrecebaseinfo, mfBusReceBaseInfo);
				mfBusReceBaseInfoFeign.update(mfBusReceBaseInfo);
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
	 * 账款列表打开页面请求(账款入口)
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getReceListPage")
	public String getReceListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "component/pact/receaccount/MfBusReceBaseInfo_ReceList";
	}
	/**
	 * 账款列表打开页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFincMainViewPage")
	public String getFincMainViewPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "component/pact/receaccount/MfBusReceBaseInfo_ReceList";
	}
	/***
	 *
	 *方法描述：获取账款列表(账款入口)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findReceByPageAjax")
	@ResponseBody
	public Map<String, Object> findReceByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
		try {
			mfBusReceBaseInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusReceBaseInfo.setCriteriaList(mfBusReceBaseInfo, ajaxData);// 我的筛选
			mfBusReceBaseInfo.setCustomSorts(ajaxData);//自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusReceBaseInfo", mfBusReceBaseInfo));
			// 自定义查询Bo方法
			ipage = mfBusReceBaseInfoFeign.findReceByPage(ipage);
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
	@RequestMapping(value = "/getReceAbstractInfoAjax")
	@ResponseBody
	public Map<String,Object> getReceAbstractInfoAjax(String appId,String busEntrance) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			//根据申请编号，获取业务关联下的应收账款总额、转让总额以及转让余额
			MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
			mfBusReceBaseInfo.setAppId(appId);
			mfBusReceBaseInfo = mfBusReceBaseInfoFeign.getReceAbstractInfo(mfBusReceBaseInfo);
			if(mfBusReceBaseInfo!=null){
				dataMap.put("appId",appId);
				dataMap.put("cusNo",mfBusReceBaseInfo.getCusNo());
				dataMap.put("busEntrance",busEntrance);
				//转化成万为单位，并格式化金额
				dataMap.put("receAmtSum","0.00");
				dataMap.put("receTransAmtSum","0.00");
				dataMap.put("receTransBalSum","0.00");
				if(mfBusReceBaseInfo.getReceAmt()!=null){
					dataMap.put("receAmtSum",MathExtend.moneyStr(mfBusReceBaseInfo.getReceAmt()/10000));
				}
				if(mfBusReceBaseInfo.getReceTransAmt()!=null){
					dataMap.put("receTransAmtSum",MathExtend.moneyStr(mfBusReceBaseInfo.getReceTransAmt()/10000));
				}
				if(mfBusReceBaseInfo.getReceTransBal()!=null){
					dataMap.put("receTransBalSum",MathExtend.moneyStr(mfBusReceBaseInfo.getReceTransBal()/10000));
				}
				dataMap.put("receEndDateMin",DateUtil.getShowDateTime(mfBusReceBaseInfo.getReceEndDate()));
				dataMap.put("flag", "success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述 跳转至账款详情页面
	 * @param [appId,fincMainId,busEntrance]
	 * @return java.lang.String
	 * @author zhs
	 * @date 2018/8/29 17:42
	 */
	@RequestMapping(value = "/getReceDetail")
	public String getReceDetail(Model model,String appId,String fincMainId,String busEntrance) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//业务申请信息
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		// 动态业务视图参数封装
		Map<String, String> parmMap = new HashMap<String, String>();
		parmMap.put("appId", mfBusApply.getAppId());
		parmMap.put("pactId", mfBusApply.getPactId());
		parmMap.put("cusNo", mfBusApply.getCusNo());
		parmMap.put("fincMainId", fincMainId);
		parmMap.put("busModel", mfBusApply.getBusModel());
		parmMap.put("busEntrance", busEntrance);
		String generalClass = "account";
		String busClass = mfBusApply.getBusModel();

		Map<String, Object> busViewMap = busViewInterfaceFeign.getBusViewMap(generalClass,busClass, busEntrance, parmMap);
		String query="";
		if(busEntrance.indexOf("approve")!=-1){//如果是审批环节，账款详情页面不允许修改
			query="query";
		}
		model.addAttribute("parmMap", parmMap);
		model.addAttribute("appId", appId);
		model.addAttribute("busEntrance", busEntrance);
		model.addAttribute("query", query);
		model.addAttribute("mfBusApply", mfBusApply);
		if (busViewMap.isEmpty()) {
			return "";
		}else{
			dataMap.putAll(busViewMap);
			model.addAttribute("dataMap", dataMap);
			return "component/pact/receaccount/MfBusReceBaseInfo_DynaDetail";
		}
	}


	/**
	 *
	 * 方法描述 获取详情页面账款列表
	 * @param [appId, tableId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author zhs
	 * @date 2018/8/30 11:06
	 */
	@RequestMapping(value = "/getReceListAjax")
	@ResponseBody
	public Map<String,Object> getReceListAjax(String appId,String tableId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			//根据申请编号，获取业务关联下的应收账款总额、转让总额以及转让余额
			MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
			mfBusReceBaseInfo.setAppId(appId);
			List<MfBusReceBaseInfo> mfBusReceBaseInfoList = mfBusReceBaseInfoFeign.getMfBusReceBaseInfoList(mfBusReceBaseInfo);
			JsonTableUtil jtu = new JsonTableUtil();
			String htmlStr = jtu.getJsonStr(tableId, "tableTag", mfBusReceBaseInfoList, null, true);
			dataMap.put("htmlStr",htmlStr);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述 获取详情页面账款列表下拉表单
	 * @param [receId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author zhs
	 * @date 2018/8/30 14:41
	 */
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String,Object> listShowDetailAjax(String receId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			//获取账款信息
			MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
			mfBusReceBaseInfo.setReceId(receId);
			mfBusReceBaseInfo = mfBusReceBaseInfoFeign.getById(mfBusReceBaseInfo);
			//获取表单编号
			MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType("17061115550593210", "PledgeBaseInfoAction", "E");
			String formId = "";
			String baseFormId = mfCollateralFormConfig.getShowModelDef();
			FormData receBaseInfoForm = formService.getFormData(baseFormId);
			if (receBaseInfoForm.getFormId() == null) {
				receBaseInfoForm = formService.getFormData(mfCollateralFormConfig.getShowModel());
			}
			getFormValue(receBaseInfoForm);
			getObjValue(receBaseInfoForm, mfBusReceBaseInfo);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String htmlStr = jsonFormUtil.getJsonStr(receBaseInfoForm, "propertySeeTag", "");
			if(mfBusReceBaseInfo!=null){
				dataMap.put("formHtml", htmlStr);
				dataMap.put("flag", "success");
			}else{
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 方法描述 根据receId 删除应收基本信息
	 * @param [receId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author zhs
	 * @date 2018/8/30 15:52
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String,Object> deleteAjax(String receId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			//获取账款信息
			MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
			mfBusReceBaseInfo.setReceId(receId);
			mfBusReceBaseInfoFeign.delete(mfBusReceBaseInfo);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_DELETE_CONTENT.getMessage(e.getMessage()));
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述 根据receId获取账款信息
	 * @param [receId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author zhs
	 * @date 2018/9/1 15:17
	 */
	@RequestMapping(value = "/getReceBaseInfoAjax")
	@ResponseBody
	public Map<String,Object> getReceBaseInfoAjax(String receId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			//获取账款信息
			MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
			mfBusReceBaseInfo.setReceId(receId);
			mfBusReceBaseInfo = mfBusReceBaseInfoFeign.getById(mfBusReceBaseInfo);
			//格式化日期（receBeginDate、receEndDate、receTransDate）
			if(mfBusReceBaseInfo!=null){
				mfBusReceBaseInfo.setReceBeginDate(DateUtil.getShowDateTime(mfBusReceBaseInfo.getReceBeginDate()));
				mfBusReceBaseInfo.setReceEndDate(DateUtil.getShowDateTime(mfBusReceBaseInfo.getReceEndDate()));
				mfBusReceBaseInfo.setReceTransDate(DateUtil.getShowDateTime(mfBusReceBaseInfo.getReceTransDate()));
				dataMap.put("receAmt",MathExtend.moneyStr(mfBusReceBaseInfo.getReceAmt()));
				dataMap.put("receTransAmt",MathExtend.moneyStr(mfBusReceBaseInfo.getReceTransAmt()));
				dataMap.put("receTransBal",MathExtend.moneyStr(mfBusReceBaseInfo.getReceTransBal()));
				dataMap.put("mfBusReceBaseInfo",mfBusReceBaseInfo);
				dataMap.put("mfBusReceBaseInfo",mfBusReceBaseInfo);
				dataMap.put("flag", "success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_DELETE_CONTENT.getMessage(e.getMessage()));
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述 根据账款id获取账款信息，跳转至账款视角
	 * @param [model, receId, appId, busEntrance]
	 * @return java.lang.String
	 * @author zhs
	 * @date 2018/9/3 17:28
	 */
	@RequestMapping(value = "/getReceDetailById")
	public String getReceDetailById(Model model,String receId,String appId,String fincId,String busEntrance) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//获取账款信息
		MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
		mfBusReceBaseInfo.setReceId(receId);
		mfBusReceBaseInfo = mfBusReceBaseInfoFeign.getById(mfBusReceBaseInfo);
		//业务申请信息
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(mfBusReceBaseInfo.getAppId());
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = pactInterfaceFeign.getFincAppById(mfBusFincApp);

		// 动态业务视图参数封装
		Map<String, String> parmMap = new HashMap<String, String>();
		parmMap.put("appId", mfBusReceBaseInfo.getAppId());
		if (mfBusFincApp!=null){
			model.addAttribute("fincSts", mfBusFincApp.getFincSts());
			parmMap.put("fincSts", mfBusFincApp.getFincSts());
		}
		parmMap.put("cusNo", mfBusReceBaseInfo.getCusNo());
		parmMap.put("fincId", fincId);
		parmMap.put("receId", receId);
		parmMap.put("busEntrance", busEntrance);
		String generalClass = "account";
		String busClass = mfBusApply.getBusModel();

		Map<String, Object> busViewMap = busViewInterfaceFeign.getBusViewMap(generalClass,busClass, busEntrance, parmMap);
		// 判断客户表单信息是否允许编辑
		String query = cusInterfaceFeign.validateCusFormModify(mfBusApply.getCusNo(), mfBusApply.getAppId(),BizPubParm.FORM_EDIT_FLAG_BUS,User.getRegNo(request));
		//factor传的是"",到web端就变成了null
		if(query==null) {
			query="";
		}
		model.addAttribute("parmMap", parmMap);
		model.addAttribute("mfBusFincApp", mfBusFincApp);
		model.addAttribute("busEntrance", busEntrance);
		model.addAttribute("query", query);
		model.addAttribute("fincId", fincId);
		model.addAttribute("receId", receId);
		model.addAttribute("mfBusApply", mfBusApply);
		if (busViewMap.isEmpty()) {
			return "";
		}else{
			dataMap.putAll(busViewMap);
			model.addAttribute("dataMap", dataMap);
			return "component/pact/receaccount/MfBusReceBaseInfo_Detail";
		}
	}


	@RequestMapping(value = "/getBtnControlFlagAjax")
	@ResponseBody
	public Map<String,Object> getBtnControlFlagAjax(String receId,String fincId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			boolean repayFlag =false;
			boolean jiefuFlag = false;
			if(StringUtil.isNotEmpty(fincId)){
				MfBusFincApp mfBusFincApp = new MfBusFincApp();
				mfBusFincApp.setFincId(fincId);
				mfBusFincApp = pactInterfaceFeign.getFincAppById(mfBusFincApp);
				if (mfBusFincApp!=null){
					if ("5".equals(mfBusFincApp.getFincSts())){
						repayFlag =true;
					}
					//根据借据号获取账款结余
					MfBusReceBal mfBusReceBal = new MfBusReceBal();
					mfBusReceBal.setFincId(fincId);
					mfBusReceBal =mfBusReceBalFeign.getById(mfBusReceBal);
					if (mfBusReceBal!=null){
						if (mfBusReceBal.getBalAmt()!=null&&mfBusReceBal.getBalAmt()>0){
							jiefuFlag=true;
						}
					}
				}
			}
			//允许账款反转让标志
			boolean buyBackFlag = false;
			//根据账款id查询账款的融资余额
			MfBusReceTransfer mfBusReceTransfer = new MfBusReceTransfer();
			mfBusReceTransfer.setReceId(receId);
			mfBusReceTransfer = mfBusReceTransferFeign.getById(mfBusReceTransfer);
			//根据转让id 查询账款下的融资列表
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setTransId(mfBusReceTransfer.getTransId());
			List<MfBusFincApp> list = pactInterfaceFeign.getFincAppList(mfBusFincApp);
			Double loanBal = 0.00;
			Double yhlx = 0.00;//应还未还利息
			for(MfBusFincApp fincApp:list){
				loanBal = loanBal + fincApp.getLoanBal();
			}
			//判断账款是否反转中或已反转
			MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
			mfBusReceBaseInfo.setReceId(receId);
			mfBusReceBaseInfo = mfBusReceBaseInfoFeign.getById(mfBusReceBaseInfo);
			if(loanBal==0.00 && BizPubParm.RECE_STS_2.equals(mfBusReceBaseInfo.getReceSts())){//账款下的所有融资均已还完且账款在已转让状态才允许反转
				buyBackFlag=true;
			}
			//反转让确认按钮控制标志
			boolean buyBackConfirmFlag = false;
			if(BizPubParm.RECE_STS_8.equals(mfBusReceBaseInfo.getReceSts())){//反转让确认状态时，页面按钮才允许操作
				buyBackConfirmFlag = true;
			}
			dataMap.put("repayFlag", repayFlag);
			dataMap.put("jiefuFlag", jiefuFlag);
			dataMap.put("buyBackFlag", buyBackFlag);
			dataMap.put("buyBackConfirmFlag", buyBackConfirmFlag);
			dataMap.put("flag","success");
		}catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}

		return dataMap;
	}
	/**
	 *
	 * 方法描述 获取应收账款表单详情信息
	 * @param [receId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author zhs
	 * @date 2018/9/3 17:52
	 */

	@RequestMapping(value = "/getReceDetailFormAjax")
	@ResponseBody
	public Map<String,Object> getReceDetailFormAjax(String receId,String formId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			//获取账款信息
			MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
			mfBusReceBaseInfo.setReceId(receId);
			mfBusReceBaseInfo = mfBusReceBaseInfoFeign.getById(mfBusReceBaseInfo);
			FormData formrecebaseinfo = formService.getFormData(formId);
			getFormValue(formrecebaseinfo);
			getObjValue(formrecebaseinfo, mfBusReceBaseInfo);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String htmlStr = jsonFormUtil.getJsonStr(formrecebaseinfo, "propertySeeTag", "");
			dataMap.put("htmlStr", htmlStr);
			dataMap.put("flag","success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_DELETE_CONTENT.getMessage(e.getMessage()));
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 方法描述： 获取账款下的还款解付历史
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-7-27 下午4:34:52
	 */
	@RequestMapping(value = "/getAccntRepayDetailListAjax")
	@ResponseBody
	public Map<String, Object> getAccntRepayDetailListAjax(String receId,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAccntRepayDetail mfAccntRepayDetail = new MfAccntRepayDetail();
		mfAccntRepayDetail.setTransferId(receId);
		List<MfAccntRepayDetail> mfAccntRepayDetailList = mfAccntRepayDetailFeign.getList(mfAccntRepayDetail);
		Ipage ipage = this.getIpage();
		// 自定义查询Bo方法
		JsonTableUtil jtu = new JsonTableUtil();
		String htmlStr = jtu.getJsonStr(tableId, "thirdTableTag", mfAccntRepayDetailList, ipage, true);
		dataMap.put("htmlStr",htmlStr);
		dataMap.put("flag","success");
		return dataMap;
	}
    /**
     *
     * 方法描述 获取详情页面还款记录
     * @param [receId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author zhs
     * @date 2018/8/30 14:41
     */
    @RequestMapping(value = "/listShowDetailAjaxForRepay")
    @ResponseBody
    public Map<String,Object> listShowDetailAjaxForRepay(String repayDetailId) throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormService formService = new FormService();
        try {
            //获取账款信息
            MfAccntRepayDetail mfAccntRepayDetail = new MfAccntRepayDetail();
            mfAccntRepayDetail.setRepayDetailId(repayDetailId);
            mfAccntRepayDetail = mfAccntRepayDetailFeign.getById(mfAccntRepayDetail);
            //获取表单编号
           // MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType("17061115550593210", "PledgeBaseInfoAction", "E");
            String formId = "accntrepay0001";
            FormData receBaseInfoForm = formService.getFormData(formId);
            getFormValue(receBaseInfoForm);
            getObjValue(receBaseInfoForm, mfAccntRepayDetail);
            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            String htmlStr = jsonFormUtil.getJsonStr(receBaseInfoForm, "propertySeeTag", "");
            if(mfAccntRepayDetail!=null){
                dataMap.put("formHtml", htmlStr);
                dataMap.put("flag", "success");
            }else{
                dataMap.put("msg", "获取详情失败");
                dataMap.put("flag", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw new Exception(e.getMessage());
        }
        return dataMap;
    }
}
