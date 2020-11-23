package app.component.pact.receaccount.controller;

import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusAppKindFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.assure.entity.MfAssureInfo;
import app.component.assure.entity.MfBusApplyAssureInfo;
import app.component.assureinterface.AssureInterfaceFeign;
import app.component.busviewinterface.BusViewInterfaceFeign;
import app.component.calc.core.entity.MfRepayHistory;
import app.component.calc.core.feign.MfRepayHistoryFeign;
import app.component.calccoreinterface.CalcPrepaymentInterfaceFeign;
import app.component.collateralinterface.CollateralInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.examine.entity.MfExamineHis;
import app.component.examine.feign.MfExamineHisFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pact.receaccount.entity.MfBusFincAmtConfirm;
import app.component.pact.receaccount.entity.MfBusReceTransfer;
import app.component.pact.receaccount.entity.MfBusFincAppMain;
import app.component.pact.receaccount.entity.MfBusReceBal;
import app.component.pact.receaccount.entity.MfBusReceBaseInfo;
import app.component.pact.receaccount.feign.MfBusFincAmtConfirmFeign;
import app.component.pact.receaccount.feign.MfBusReceTransferFeign;
import app.component.pact.receaccount.feign.MfBusReceBaseInfoFeign;
import app.component.pact.receaccount.feign.MfBusReceBalFeign;
import app.component.pact.receaccount.feign.MfBusFincAppMainFeign;
import app.component.pact.repay.entity.MfRepaymentBean;
import app.component.pact.repay.entity.MfRepaymentBuyBean;
import app.component.pact.repay.feign.MfRepaymentFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdct.entity.MfKindFlow;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.rec.entity.RecallBase;
import app.component.recinterface.RecInterfaceFeign;
import app.component.sys.entity.SysTaskInfo;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.TaskFeign;
import app.component.wkfBusInterface.WkfBusInterfaceFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.api.task.Task;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfBusFincAppMainController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 18:19:17 CST 2017
 **/
@Controller
@RequestMapping("/mfBusFincAppMain")
public class MfBusFincAppMainController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusFincAppMainFeign mfBusFincAppMainFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfBusReceTransferFeign mfBusReceTransferFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private WkfBusInterfaceFeign wkfBusInterfaceFeign;
	@Autowired
	private MfExamineHisFeign mfExamineHisFeign;
	@Autowired
	private RecInterfaceFeign recInterfaceFeign;
	@Autowired
	private CollateralInterfaceFeign collateralInterfaceFeign;
	@Autowired
	private BusViewInterfaceFeign busViewInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private AssureInterfaceFeign assureInterfaceFeign;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private MfBusReceBaseInfoFeign mfBusReceBaseInfoFeign;
	@Autowired
	private MfBusReceBalFeign mfBusReceBalFeign;
	@Autowired
	private MfRepaymentFeign mfRepaymentFeign;
	@Autowired
	private CalcPrepaymentInterfaceFeign calcPrepaymentInterfaceFeign;
	@Autowired
	private MfRepayHistoryFeign mfRepayHistoryFeign;
	@Autowired
	private MfBusFincAmtConfirmFeign mfBusFincAmtConfirmFeign;
	@Autowired
	private TaskFeign taskFeign;

	@Autowired
	private MfBusAppKindFeign mfBusAppKindFeign;

	/**
	 * 方法描述：跳转至融资列表页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception{
		ActionContext.initialize(request, response);
		JSONArray fincStsJsonArray = new CodeUtils().getJSONArrayByKeyName("FINC_STS");
		model.addAttribute("fincStsJsonArray", fincStsJsonArray);
		//办理阶段
		//根据业务模式获取所有的流程节点信息（过滤重复的）
		JSONArray flowNodeJsonArray = prdctInterfaceFeign.getFlowNodeArray();
		model.addAttribute("flowNodeJsonArray", flowNodeJsonArray);
		return "/component/pact/receaccount/MfBusFincAppMain_List";
	}
	/**
	 * 方法描述：跳转至转让账款融资视角页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageForAccount")
	public String getListPageForAccount(Model model) throws Exception{
		ActionContext.initialize(request, response);
		JSONArray fincStsJsonArray = new CodeUtils().getJSONArrayByKeyName("FINC_STS");
		model.addAttribute("fincStsJsonArray", fincStsJsonArray);
		return "/component/pact/receaccount/MfBusFincAppMain_ListForAccount";
	}
	/**
	 * 方法描述：获取融资列表数据
	 * @param pageNo
	 * @param pageSize
	 * @param tableId
	 * @param tableType
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findFincByPageAjax")
	@ResponseBody
	public Map<String,Object> findFincByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		try {
			mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusFincApp.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
			ipage = mfBusFincAppMainFeign.findFincByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 方法描述：获取融资列表数据
	 * @param pageNo
	 * @param pageSize
	 * @param tableId
	 * @param tableType
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findFincByPageAjaxForAccount")
	@ResponseBody
	public Map<String,Object> findFincByPageAjaxForAccount(Integer pageNo, Integer pageSize, String tableId, String tableType,String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		try {
			mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusFincApp.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
			ipage = mfBusFincAppMainFeign.findFincByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述：获取可以融资申请的合同列表
	 * @param pageNo
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPactListAjax")
	@ResponseBody
	public Map<String,Object> getPactListAjax(Integer pageNo,String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		MfBusFincAmtConfirm mfBusFincAmtConfirm = new MfBusFincAmtConfirm();
		try {
			mfBusFincAmtConfirm.setCustomQuery(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusFincAmtConfirm", mfBusFincAmtConfirm));
			ipage = mfBusFincAmtConfirmFeign.getPactListForReceFinc(ipage);
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
	 * 方法描述 获取可以进行融资额度确认的合同列表
	 * @param [pageNo, ajaxData]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author zhs
	 * @date 2018/9/14 16:54
	 */
	@RequestMapping(value = "/getPactListForFincConfirmAjax")
	@ResponseBody
	public Map<String,Object> getPactListForFincConfirmAjax(Integer pageNo,String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		MfBusPact mfBusPact = new MfBusPact();
		try {
			mfBusPact.setCustomQuery(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
			ipage = mfBusPactFeign.getPactListForFincConfirm(ipage);
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
	  * 方法描述 跳转至融资申请页面
	  * @param [model, pactId]
	  * @return java.lang.String
	  * @author zhs
	  * @date 2018/8/18 10:53
	  */
	@RequestMapping(value = "/input")
	public String input(Model model,String appId,String pactId,String confirmId,String fincMainId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		//融资额度确认信息
		MfBusFincAmtConfirm mfBusFincAmtConfirm = new MfBusFincAmtConfirm();
		mfBusFincAmtConfirm.setConfirmId(confirmId);
		mfBusFincAmtConfirm = mfBusFincAmtConfirmFeign.getById(mfBusFincAmtConfirm);
		mfBusFincAmtConfirm = mfBusFincAmtConfirmFeign.processDataForConfirm(mfBusFincAmtConfirm);
		//获取合同信息
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setAppId(mfBusFincAmtConfirm.getAppId());
		mfBusPact.setPactId(mfBusFincAmtConfirm.getPactId());
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
		// 功能节点编号
		String nodeNo = BizPubParm.WKF_NODE.putout_apply.getNodeNo();
		//获取formId
		String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), BizPubParm.WKF_NODE.putout_apply, null, null,User.getRegNo(request));
		FormData formrecefincappbase = formService.getFormData(formId);

		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setPactAmt(mfBusPact.getUsableFincAmt());
		mfBusFincApp.setTermMonth(mfBusPact.getTerm());
		mfBusFincApp.setOpName(User.getRegName(request));
		mfBusFincApp.setOpNo(User.getRegNo(request));
		mfBusFincApp.setPactId(mfBusPact.getPactId());
		mfBusFincApp.setChargeInterest(0);
		mfBusFincApp.setChargeFee(0);
		mfBusFincApp.setIntstEndDate(mfBusPact.getEndDate());
		//获取合同下已转让的应收账款列表，统计汇总金额
		MfBusReceTransfer mfBusReceTransfer = new MfBusReceTransfer();
		mfBusReceTransfer.setPactId(mfBusFincAmtConfirm.getPactId());
		mfBusReceTransfer.setTransSts("2");
		List<MfBusReceTransfer> mfBusReceTransferList = mfBusReceTransferFeign.getReceTransferList(mfBusReceTransfer);
		Double receAmt=0.00;
		Double receTransAmt=0.00;
		Double receTransBal=0.00;
		Double usableFincAmt=0.00;
		if(mfBusReceTransferList!=null && mfBusReceTransferList.size()>0){
			for(MfBusReceTransfer mfReceTransfer:mfBusReceTransferList){
				receAmt = receAmt+mfReceTransfer.getReceAmt();
				receTransAmt = receTransAmt+mfReceTransfer.getReceTransAmt();
				receTransBal = receTransBal+mfReceTransfer.getReceTransBal();
				usableFincAmt = usableFincAmt+mfReceTransfer.getUsableFincAmt();
				mfReceTransfer.setFincAmt(0.00);
				mfReceTransfer.setIntstEndDate(DateUtil.getShowDateTime(mfReceTransfer.getReceEndDate()));
			}
		}
		mfBusFincApp.setPutoutAmt(0.00);
 		mfBusFincApp.setReceAmt(receAmt);
		mfBusFincApp.setReceTransAmt(receTransAmt);
		mfBusFincApp.setReceTransBal(receTransBal);
		//合同下的可融资金额与之下的所有已转让账款的可融资金额之和取小
		if(usableFincAmt<mfBusFincAmtConfirm.getUsableFincAmt()){
			mfBusPact.setUsableFincAmt(usableFincAmt);
		}
		getObjValue(formrecefincappbase, mfBusPact);
		getObjValue(formrecefincappbase, mfBusFincApp);
		getObjValue(formrecefincappbase, mfBusFincAmtConfirm);

		// 放款申请表单中的还款方式修改时应收产品设置中的还款方式控制
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(mfBusPact.getAppId());
		mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
		JSONArray repayTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusAppKind.getRepayType(), "REPAY_TYPE");

		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
		this.changeFormProperty(formrecefincappbase, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formrecefincappbase, "overRate", "unit", rateUnit);
		this.changeFormProperty(formrecefincappbase, "cmpdRate", "unit", rateUnit);

		// 客户的账户列表
		MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
		mfCusBankAccManage.setCusNo(mfBusPact.getCusNo());
		mfCusBankAccManage.setUseFlag(BizPubParm.YES_NO_Y);
		mfCusBankAccManage.setDelFlag(BizPubParm.YES_NO_N);
		List<MfCusBankAccManage> mfCusBankAccManageList = cusInterfaceFeign.getMfCusBankAccListByCusNo(mfCusBankAccManage);
		JSONArray bankArray = JSONArray.fromObject(mfCusBankAccManageList);
		for (int i = 0; i < bankArray.size(); i++) {
			bankArray.getJSONObject(i).put("name",
					bankArray.getJSONObject(i).getString("accountNo").replaceAll("([\\d]{4})(?=\\d)", "$1 "));
		}
		JSONObject json = new JSONObject();
		json.put("repayTypeMap", repayTypeMap);
		json.put("bankArray", bankArray);
		model.addAttribute("ajaxData", json.toString());
		model.addAttribute("confirmId", confirmId);
		model.addAttribute("wkfAppId", mfBusPact.getWkfAppId());
		model.addAttribute("query", "");
		model.addAttribute("mfBusPact", mfBusPact);
		model.addAttribute("kindNo", mfBusPact.getKindNo());
		model.addAttribute("nodeNo", BizPubParm.WKF_NODE.putout_apply.getNodeNo());
		model.addAttribute("formrecefincappbase", formrecefincappbase);
		model.addAttribute("mfBusReceTransferList", mfBusReceTransferList);
		return "/component/pact/receaccount/MfBusFincAppMain_Insert";
	}



	/**
	 *
	 * 方法描述 保存融资申请主表以及子表信息(根据应收账款生成借据)
	 * @param [ajaxData,pactId,confirmId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author zhs
	 * @date 2018/8/16 16:24
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String,Object> insertAjax(String ajaxData,String receFincList,String pactId,String confirmId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {

			MfBusPact mfBusPact = new MfBusPact();
			mfBusPact.setPactId(pactId);
			mfBusPact = mfBusPactFeign.getById(mfBusPact);
			//融资额度确认信息
			MfBusFincAmtConfirm mfBusFincAmtConfirm = new MfBusFincAmtConfirm();
			mfBusFincAmtConfirm.setConfirmId(confirmId);
			mfBusFincAmtConfirm = mfBusFincAmtConfirmFeign.getById(mfBusFincAmtConfirm);

			Map map = getMapByJson(ajaxData);
			FormData formrecefincappbase = formService.getFormData((String) map.get("formId"));
			getFormValue(formrecefincappbase, map);
			if (this.validateFormData(formrecefincappbase)) {
				//校验申请金额与可融资金额的大小
				MfBusReceTransfer mfBusReceTransfer = new MfBusReceTransfer();
				mfBusReceTransfer.setPactId(pactId);
				mfBusReceTransfer = mfBusReceTransferFeign.getReceUsableFincAmt(mfBusReceTransfer);
				Double usableFincAmt=mfBusReceTransfer.getUsableFincAmt();
				if(mfBusFincAmtConfirm.getUsableFincAmt()<usableFincAmt){
					usableFincAmt = mfBusFincAmtConfirm.getUsableFincAmt();
				}
				MfBusFincApp mfBusFincApp = new MfBusFincApp();
				setObjValue(formrecefincappbase, mfBusFincApp);
				if(mfBusFincApp.getPutoutAmt()>usableFincAmt){//如果申请金额大于可融资金额
					dataMap.put("flag", "error");
					Map<String,String> parmMap = new HashMap<String,String>();
					parmMap.put("timeOne","融资申请金额");
					parmMap.put("timeTwo","可融资金额");
					dataMap.put("msg", MessageEnum.NOT_FORM_TIME.getMessage(parmMap));
				}else{
					mfBusFincApp.setCusNo(mfBusPact.getCusNo());
					mfBusFincApp.setRateType(mfBusPact.getRateType());
					mfBusFincApp.setAppId(mfBusPact.getAppId());
					map.put("confirmId",confirmId);
					map.put("mfBusFincApp",mfBusFincApp);
					map.put("receFincList",receFincList);
					dataMap = mfBusFincAppMainFeign.insert(map);
					dataMap.put("flag", "success");
				}
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("flag", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述 跳转至融资审批页面
	 * @param [model, appId, transMainId, taskId, hideOpinionType, activityType]
	 * @return java.lang.String
	 * @author zhs
	 * @date 2018/8/17 11:25
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model,String fincMainId, String taskId, String hideOpinionType, String activityType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(fincMainId, taskId);// 当前审批节点task
		//融资申请主信息
		MfBusFincAppMain mfBusFincAppMain = new MfBusFincAppMain();
		mfBusFincAppMain.setFincMainId(fincMainId);
		mfBusFincAppMain = mfBusFincAppMainFeign.getById(mfBusFincAppMain);
		//获取合同信息
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setAppId(mfBusFincAppMain.getAppId());
		mfBusPact = mfBusPactFeign.getById(mfBusPact);


		// 功能节点编号
		String nodeNo = BizPubParm.WKF_NODE.finc_approval.getNodeNo();
		//获取formId
		String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), BizPubParm.WKF_NODE.finc_approval, null, fincMainId,User.getRegNo(request));
		FormData formrecefincapprovalbase = formService.getFormData(formId);

		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setPactAmt(mfBusPact.getUsableFincAmt());
		mfBusFincApp.setOpName(User.getRegName(request));
		mfBusFincApp.setOpNo(User.getRegNo(request));
		mfBusFincApp.setPactId(mfBusPact.getPactId());
		mfBusFincApp.setChargeInterest(0);
		mfBusFincApp.setChargeFee(0);
		mfBusFincApp.setIntstEndDate(mfBusPact.getEndDate());
		//获取合同下此次融资申请的应收账款列表，统计汇总金额
		MfBusFincAppMain mfBusFincAppMainParm = new MfBusFincAppMain();
		mfBusFincAppMainParm.setPactId(mfBusPact.getPactId());
		mfBusFincAppMainParm.setFincMainId(fincMainId);
		List<MfBusReceTransfer> mfBusReceTransferList = mfBusFincAppMainFeign.getReceFincList(mfBusFincAppMainParm);
		Double receAmt=0.00;
		Double receTransAmt=0.00;
		Double receTransBal=0.00;
		Double usableFincAmt=0.00;
		if(mfBusReceTransferList!=null && mfBusReceTransferList.size()>0){
			for(MfBusReceTransfer mfReceTransfer:mfBusReceTransferList){
				receAmt = receAmt+mfReceTransfer.getReceAmt();
				receTransAmt = receTransAmt+mfReceTransfer.getReceTransAmt();
				receTransBal = receTransBal+mfReceTransfer.getReceTransBal();
				usableFincAmt = usableFincAmt+mfReceTransfer.getUsableFincAmt();
				MfBusFincApp mfBusFincAppTmp = new MfBusFincApp();
				mfBusFincAppTmp.setFincMainId(fincMainId);
				mfBusFincAppTmp.setTransId(mfReceTransfer.getTransId());
				mfBusFincAppTmp = mfBusFincAppFeign.getById(mfBusFincAppTmp);
				mfReceTransfer.setIntstEndDate(mfBusFincAppTmp.getIntstEndDate());
			}
		}
		mfBusFincApp.setReceAmt(receAmt);
		mfBusFincApp.setReceTransAmt(receTransAmt);
		mfBusFincApp.setReceTransBal(receTransBal);
		//合同下的可融资金额与之下的所有已转让账款的可融资金额之和取小
		if(usableFincAmt<mfBusPact.getUsableFincAmt()){
			mfBusPact.setUsableFincAmt(usableFincAmt);
		}
		mfBusFincApp.setPutoutAmt(mfBusFincAppMain.getFincAmt());
		getObjValue(formrecefincapprovalbase, mfBusFincApp);
		mfBusPact.setPutoutAmt(mfBusFincApp.getPutoutAmt());
		mfBusPact.setFincRate(mfBusFincAppMain.getFincRate());
		mfBusPact.setRateType(mfBusFincAppMain.getRateType());
		mfBusPact.setOverRate(mfBusFincAppMain.getOverRate());
		mfBusPact.setRepayType(mfBusFincAppMain.getRepayType());
		mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
		getObjValue(formrecefincapprovalbase, mfBusPact);


		CodeUtils codeUtils = new CodeUtils();

		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = codeUtils.getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
		this.changeFormProperty(formrecefincapprovalbase, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formrecefincapprovalbase, "overRate", "unit", rateUnit);
		this.changeFormProperty(formrecefincapprovalbase, "cmpdRate", "unit", rateUnit);

		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", taskAppro.getActivityName());// 当前节点编号
		List<OptionsList> opinionTypeList = codeUtils.getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formrecefincapprovalbase, "opinionType", "optionArray", opinionTypeList);

		//还款方式
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(mfBusPact.getAppId());
		mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
		JSONArray repayTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusAppKind.getRepayType(), "REPAY_TYPE");
		JSONObject json = new JSONObject();
		json.put("repayTypeMap", repayTypeMap);

		//获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));

		model.addAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("appId", mfBusFincAppMain.getAppId());
		model.addAttribute("cusNo", mfBusPact.getCusNo());
		model.addAttribute("pactId", mfBusPact.getPactId());
		model.addAttribute("fincMainId", fincMainId);
		model.addAttribute("ajaxData", json.toString());
		model.addAttribute("nodeNo", taskAppro.getActivityName());
		model.addAttribute("query", "");
		model.addAttribute("formrecefincapprovalbase", formrecefincapprovalbase);
		model.addAttribute("mfBusReceTransferList", mfBusReceTransferList);
		return "/component/pact/receaccount/ReceFincWkfViewPoint";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String taskId, String appId, String fincMainId, String transition, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		dataMap = getMapByJson(ajaxData);
		FormData formreceFincApproval = formService.getFormData((String) dataMap.get("formId"));
		getFormValue(formreceFincApproval, dataMap);
		setObjValue(formreceFincApproval, mfBusFincApp);
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
		try {
			dataMap.put("mfBusFincApp", mfBusFincApp);
			Result res = mfBusFincAppMainFeign.doCommit(taskId, appId,fincMainId, opinionType, approvalOpinion, transition, User.getRegNo(request), nextUser, dataMap);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					// 审批通过之后提交业务到下一个阶段
					MfBusFincAppMain mfBusFincAppMain = new MfBusFincAppMain();
					mfBusFincAppMain.setFincMainId(fincMainId);
					mfBusFincAppMain = mfBusFincAppMainFeign.getById(mfBusFincAppMain);
					Result result = wkfBusInterfaceFeign.doCommitNextStepWithUser(mfBusFincAppMain.getAppId(),mfBusFincAppMain.getWkfAppId(),User.getRegNo(request));
					if (!result.isSuccess()) {
						dataMap.put("flag", "error");
					}else{
						Task task = wkfInterfaceFeign.getTaskWithUser(mfBusFincAppMain.getWkfAppId(), null, User.getRegNo(request));
						MfBusFincAppMain mfBusFincAppMainUpd = new MfBusFincAppMain();
						mfBusFincAppMainUpd.setFincMainId(fincMainId);
						mfBusFincAppMainUpd.setNodeNo(task.getActivityName());
						mfBusFincAppMainUpd.setNodeName(task.getDescription());
						mfBusFincAppMainFeign.update(mfBusFincAppMainUpd);
					}
					dataMap.put("msg", result.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
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
	 * 查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String appId, String pactId,String fincId,String operable, String busEntrance,String fincMainId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		request.setAttribute("ifBizManger", "3");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//融资信息
		MfBusFincAppMain mfBusFincAppMain = new MfBusFincAppMain();
		mfBusFincAppMain.setFincMainId(fincMainId);
		mfBusFincAppMain = mfBusFincAppMainFeign.getById(mfBusFincAppMain);
		model.addAttribute("mfBusFincAppMain",mfBusFincAppMain);
		// 合同信息
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setAppId(appId);
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
		dataMap.put("guaranteeType", "loan");
		FormData formpact0004 = null;
		if (mfBusPact.getBusModel().equals(BizPubParm.BUS_MODEL_5)) {
			formpact0004 = formService.getFormData("pact0004bl");
		} else if (mfBusPact.getBusModel().equals(BizPubParm.BUS_MODEL_14)
				|| mfBusPact.getBusModel().equals(BizPubParm.BUS_MODEL_15)
				|| mfBusPact.getBusModel().equals(BizPubParm.BUS_MODEL_17)) {
			formpact0004 = formService.getFormData("pact0004db");
			dataMap.put("guaranteeType", "gua");
		} else {
			formpact0004 = formService.getFormData("pact0004");
		}

		getFormValue(formpact0004);
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
		this.changeFormProperty(formpact0004, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formpact0004, "overRate", "unit", rateUnit);
		this.changeFormProperty(formpact0004, "cmpdRate", "unit", rateUnit);
		model.addAttribute("rateUnit", rateUnit);
		// 金额格式化
		mfBusPact.setFincAmt(MathExtend.moneyStr(mfBusPact.getPactAmt() / 10000));
		if (mfBusPact.getUnrepayFincAmt() == null) {
			mfBusPact.setUnrepayFincAmt(0.00);
		}
		dataMap.put("pactBal", MathExtend.moneyStr(mfBusPact.getUnrepayFincAmt() / 10000));
		// 合同逾期天数
		int overDays = 0;
		dataMap.put("overDays", overDays);
		if (!StringUtil.isEmpty(mfBusPact.getEndDate())) {
			overDays = DateUtil.getIntervalDays(DateUtil.getStr(mfBusPact.getEndDate()),
					DateUtil.getDate("yyyy-MM-dd"));
		}
		if (overDays >= 0) {
			dataMap.put("overDays", overDays);
		}
		getObjValue(formpact0004, mfBusPact);
		// 期限单位格式化
		CodeUtils codeUtils = new CodeUtils();
		Map<String, ParmDic> termTypeMap = codeUtils.getMapObjByKeyName("TERM_TYPE");
		String termUnit = termTypeMap.get(mfBusPact.getTermType()).getRemark();
		this.changeFormProperty(formpact0004, "term", "unit", termUnit);

		pactId = mfBusPact.getPactId();
		// 检查历史信息
		MfExamineHis mfExamineHis = new MfExamineHis();
		mfExamineHis.setPactId(mfBusPact.getPactId());
		List<MfExamineHis> mfExamineHisList = mfExamineHisFeign.getMfExamineHisList(mfExamineHis);
		model.addAttribute("mfExamineHisList", mfExamineHisList);
		// 催收信息
		RecallBase recallBase = new RecallBase();
		recallBase.setPactId(mfBusPact.getPactId());
		recallBase.setCusNo(mfBusPact.getCusNo());
		List<RecallBase> recallBaseList = recInterfaceFeign.getRecallBaseList(recallBase);
		dataMap.put("hasRecallFlag", "0");
		if (recallBaseList != null && recallBaseList.size() > 0) {
			dataMap.put("hasRecallFlag", "1");
			recallBase.setRecallSts(BizPubParm.RECALL_STS_2);
			List<RecallBase> recallList = recInterfaceFeign.getRecallBaseList(recallBase);
			dataMap.put("recallingFlag", "0");
			if (recallList != null && recallList.size() > 0) {
				dataMap.put("recallingFlag", "1");
			}
		}
		model.addAttribute("recallBaseList", recallBaseList);
		JSONObject jb = JSONObject.fromObject(dataMap);
		model.addAttribute("recParam", jb);
		// 业务申请信息
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(mfBusPact.getAppId());
		// 客户信息
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfBusPact.getCusNo());
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		String headImg = mfCusCustomer.getHeadImg();
		String ifUploadHead = mfCusCustomer.getIfUploadHead();
		String busModel = mfBusApply.getBusModel();
		// 关联企业：核心企业、资金机构、仓储机构等信息
		String wareHouseCusNo = "0";
		String fundCusNo = "0";
		String coreCusNo = "0";
		if (busModel.equals(BizPubParm.BUS_MODEL_1) || busModel.equals(BizPubParm.BUS_MODEL_2)) {// 动产质押、仓单模式下登记
			// 仓储机构
			if (!StringUtil.isEmpty(mfBusApply.getCusNoWarehouse())) {// 仓储机构信息
				wareHouseCusNo = mfBusApply.getCusNoWarehouse();// 已登记
			} else {
				wareHouseCusNo = "0";// 未登记
			}
		} else if (busModel.equals(BizPubParm.BUS_MODEL_4)) {// 保兑仓模式下登记核心企业
			if (!StringUtil.isEmpty(mfBusApply.getCusNoCore())) {// 核心企业信息
				coreCusNo = mfBusApply.getCusNoCore();// 已登记
			} else {
				coreCusNo = "0";// 未登记
			}
		} else if (busModel.equals(BizPubParm.BUS_MODEL_5)) {// 保理（应收账款）模式下登记核心企业、资金机构
			if (!StringUtil.isEmpty(mfBusApply.getCusNoCore())) {// 核心企业信息
				coreCusNo = mfBusApply.getCusNoCore();// 已登记
			} else {
				coreCusNo = "0";// 未登记
			}
			if (!StringUtil.isEmpty(mfBusApply.getCusNoFund())) {// 资金机构信息
				fundCusNo = mfBusApply.getCusNoFund();// 已登记
			} else {
				fundCusNo = "0";// 未登记
			}
		}else {
		}
		model.addAttribute("headImg", headImg);
		model.addAttribute("ifUploadHead", ifUploadHead);
		model.addAttribute("busModel", busModel);
		model.addAttribute("wareHouseCusNo", wareHouseCusNo);
		model.addAttribute("coreCusNo", coreCusNo);
		model.addAttribute("fundCusNo", fundCusNo);


		// 动态业务视图参数封装
		Map<String, String> parmMap = new HashMap<String, String>();
		parmMap.put("pactId", pactId);
		parmMap.put("appId", mfBusPact.getAppId());
		parmMap.put("fincMainId", fincMainId);
		parmMap.put("primaryAppId", mfBusApply.getPrimaryAppId());
		parmMap.put("operable", operable);
		parmMap.put("wkfAppId", mfBusPact.getWkfAppId());
		parmMap.put("pactSts", mfBusPact.getPactSts());
		parmMap.put("applyProcessId", mfBusApply.getApplyProcessId());
		parmMap.put("primaryApplyProcessId", mfBusApply.getPrimaryApplyProcessId());
		parmMap.put("pactProcessId", mfBusPact.getPactProcessId());
		parmMap.put("vouType", mfBusPact.getVouType());
		parmMap.put("cusNo", mfBusApply.getCusNo());
		parmMap.put("busModel", mfBusApply.getBusModel());
		if (StringUtil.isEmpty(busEntrance)) {
			busEntrance = "5";// 如果入口为空，为审批视图
		}
		parmMap.put("busEntrance", busEntrance);
		// 获得应收账款转让状态
		String transferSts = collateralInterfaceFeign.getReceivablesTransferSts(mfBusPact.getAppId());
		request.setAttribute("transferSts", transferSts);
		parmMap.put("appId", mfBusPact.getAppId());
		String generalClass = "bus";
		if("2".equals(busEntrance)){
			busEntrance="pact";
		}else if("5".equals(busEntrance)){
			busEntrance="pact_approve";
		}else {
		}
		//判断是否允许融资详情界面是否允许操作还款，结付按钮
        String huankuanFlag ="0";
        String jiefuFlag ="0";
		//获取融资下的所有借据
        MfBusFincApp mfBusFincApp = new MfBusFincApp();
        mfBusFincApp.setFincMainId(fincMainId);
        List<MfBusFincApp> mfBusFincAppList = mfBusFincAppFeign.getByFincMainId(mfBusFincApp);
        if (mfBusFincAppList!=null){
            for (int i = 0; i < mfBusFincAppList.size(); i++) {
                MfBusFincApp mfBusFincAppNew = mfBusFincAppList.get(i);
                if (BizPubParm.FINC_STS_AGREE.equals(mfBusFincAppNew.getFincSts())){
                    huankuanFlag ="1";
                }
                //判断
				if(StringUtil.isNotEmpty(mfBusFincAppNew.getFincId())){
					//根据借据号获取账款结余
					MfBusReceBal mfBusReceBal = new MfBusReceBal();
					mfBusReceBal.setFincId(mfBusFincAppNew.getFincId());
					mfBusReceBal =mfBusReceBalFeign.getById(mfBusReceBal);
					if (mfBusReceBal!=null){
						if (mfBusReceBal.getBalAmt()!=null&&mfBusReceBal.getBalAmt()>0){
							jiefuFlag="1";
						}
					}
				}
            }
        }
		model.addAttribute("jiefuFlag", jiefuFlag);
		model.addAttribute("huankuanFlag", huankuanFlag);
		//TODO 合同设置busClass;
		String busClass=null;
		busClass = mfBusApply.getBusModel();
		Map<String, Object> busViewMap = busViewInterfaceFeign.getBusViewMap(generalClass,busClass, busEntrance, parmMap);
		// 判断客户表单信息是否允许编辑
		String query = cusInterfaceFeign.validateCusFormModify(mfBusApply.getCusNo(), mfBusApply.getAppId(),BizPubParm.FORM_EDIT_FLAG_BUS,User.getRegNo(request));
		//要件的权限
		String queryFile = cusInterfaceFeign.validateCusFormModify(mfBusApply.getCusNo(), mfBusApply.getAppId(),BizPubParm.FORM_EDIT_FLAG_FILE,User.getRegNo(request));
		//factor传的是"",到web端就变成了null
		if(query==null) {
			query="";
		}
		if(queryFile==null) {
			queryFile="";
		}

		//判断合同是否可以进行提前解约（1、额度可循环的未完结合同 2、合同下所有借据均已完结 ）
		String pactEndFlag = mfBusPactFeign.getPactEndFlag(mfBusPact);
		dataMap.put("pactEndFlag",pactEndFlag);

		model.addAttribute("dataMap", dataMap);
		model.addAttribute("transferSts", transferSts);
		model.addAttribute("parmMap", parmMap);
		model.addAttribute("mfBusPact", mfBusPact);
		model.addAttribute("busEntrance", busEntrance);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("query", query);
		model.addAttribute("queryFile", queryFile);
		model.addAttribute("formpact0004", formpact0004);
		model.addAttribute("mfBusApply", mfBusApply);
		if (busViewMap.isEmpty()) {
			return "";
		} else {
			dataMap.putAll(busViewMap);
			model.addAttribute("dataMap", dataMap);
			return "/component/pact/receaccount/MfBusFincAppMain_DynaDetail";
		}
	}

	@RequestMapping(value = "/getSummary")
	public String getSummary(Model model, String fincMainId,String appId, String pactId,String fincId,String operable, String busEntrance) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		request.setAttribute("ifBizManger", "3");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 合同信息
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setAppId(appId);
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		//业务申请信息
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(mfBusPact.getAppId());
		//融资信息
		MfBusFincAppMain mfBusFincAppMain = new MfBusFincAppMain();
		mfBusFincAppMain.setFincMainId(fincMainId);
		mfBusFincAppMain = mfBusFincAppMainFeign.getById(mfBusFincAppMain);

		// 动态业务视图参数封装
		Map<String, String> parmMap = new HashMap<String, String>();
		parmMap.put("fincMainId", fincMainId);
		parmMap.put("pactId", pactId);
		parmMap.put("appId", mfBusPact.getAppId());
		parmMap.put("primaryAppId", mfBusApply.getPrimaryAppId());
		parmMap.put("operable", operable);
		parmMap.put("wkfAppId", mfBusFincAppMain.getWkfAppId());
		parmMap.put("pactSts", mfBusPact.getPactSts());
		parmMap.put("applyProcessId", mfBusApply.getApplyProcessId());
		parmMap.put("primaryApplyProcessId", mfBusApply.getPrimaryApplyProcessId());
		parmMap.put("pactProcessId", mfBusPact.getPactProcessId());
		parmMap.put("vouType", mfBusPact.getVouType());
		parmMap.put("fincProcessId", mfBusPact.getFincProcessId());
		parmMap.put("cusNo", mfBusApply.getCusNo());
		parmMap.put("busModel", mfBusApply.getBusModel());
		parmMap.put("busEntrance", busEntrance);
		String generalClass = "bus";
		String busClass = mfBusApply.getBusModel();
		Map<String, Object> busViewMap = busViewInterfaceFeign.getBusViewMap(generalClass,busClass, busEntrance, parmMap);
		dataMap.putAll(busViewMap);

		// 判断客户表单信息是否允许编辑
		String query = cusInterfaceFeign.validateCusFormModify(mfBusApply.getCusNo(), mfBusApply.getAppId(),BizPubParm.FORM_EDIT_FLAG_BUS,User.getRegNo(request));
		//要件的权限控制
		String queryFile = cusInterfaceFeign.validateCusFormModify(mfBusApply.getCusNo(), mfBusApply.getAppId(),BizPubParm.FORM_EDIT_FLAG_FILE,User.getRegNo(request));
		//factor传的是"",到web端就变成了null
		if(query==null) {
			query="";
		}
		if(queryFile==null) {
			queryFile="";
		}
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("parmMap", parmMap);
		model.addAttribute("fincMainId", fincMainId);
		model.addAttribute("mfBusApply", mfBusApply);
		model.addAttribute("mfBusPact", mfBusPact);
		model.addAttribute("mfBusFincAppMain", mfBusFincAppMain);
		model.addAttribute("busEntrance", busEntrance);
		model.addAttribute("query", query);
		model.addAttribute("queryFile", queryFile);
		return "/component/pact/receaccount/MfBusFincAppMain_Detail";
	}


	/**
	 * 方法描述： 获取融资业务头部详情信息
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-7-27 下午4:34:52
	 */
	@RequestMapping(value = "/getFincMainHeadInfoAjax")
	@ResponseBody
	public Map<String, Object> getFincMainHeadInfoAjax(String appId, String pactId,String fincMainId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//融资信息
		MfBusFincAppMain mfBusFincAppMain = new MfBusFincAppMain();
		mfBusFincAppMain.setFincMainId(fincMainId);
		mfBusFincAppMain = mfBusFincAppMainFeign.getById(mfBusFincAppMain);
		dataMap.put("mfBusFincAppMain", mfBusFincAppMain);
		// 金额格式化
		dataMap.put("fincAmt",MathExtend.moneyStr(mfBusFincAppMain.getFincAmt() / 10000));
		dataMap.put("fincBal", MathExtend.moneyStr(mfBusFincAppMain.getFincBal() / 10000));

		// 合同信息
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setAppId(appId);
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		// 合同逾期天数
		int overDays = 0;
		dataMap.put("overDays", overDays);
		if (!StringUtil.isEmpty(mfBusPact.getEndDate())
				&& DateUtil.compareTo(mfBusPact.getEndDate(), DateUtil.getDate()) > 0) {
			overDays = DateUtil.getIntervalDays(DateUtil.getStr(mfBusPact.getEndDate()), DateUtil.getDate("yyyyMMdd"));
			if (overDays >= 0) {
				dataMap.put("overDays", overDays);
			}
		}
		//保理合作方式
		Map<String,String>  dicMap = new CodeUtils().getMapByKeyName("FACT_COOP_WAY");
		dataMap.put("factorCoopWay",dicMap.get(mfBusPact.getFactorCoopWay()));
		dataMap.put("mfBusPact", mfBusPact);
		// 业务申请信息
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(mfBusPact.getAppId());
		String busModel = mfBusApply.getBusModel();
		dataMap.put("mfBusApply", mfBusApply);
		String wareHouseCusNo = "0";
		String coreCusNo = "0";
		String fundCusNo = "0";
		// 关联企业：核心企业、资金机构、仓储机构等信息
		if (busModel.equals(BizPubParm.BUS_MODEL_1) || busModel.equals(BizPubParm.BUS_MODEL_2)) {// 动产质押、仓单模式下登记
			// 仓储机构
			if (!StringUtil.isEmpty(mfBusApply.getCusNoWarehouse())) {// 仓储机构信息
				wareHouseCusNo = mfBusApply.getCusNoWarehouse();// 已登记
			} else {
				wareHouseCusNo = "0";// 未登记
			}
		} else if (busModel.equals(BizPubParm.BUS_MODEL_4)) {// 保兑仓模式下登记核心企业
			if (!StringUtil.isEmpty(mfBusApply.getCusNoCore())) {// 核心企业信息
				coreCusNo = mfBusApply.getCusNoCore();// 已登记
			} else {
				coreCusNo = "0";// 未登记
			}
		} else if (busModel.equals(BizPubParm.BUS_MODEL_5)) {// 保理（应收账款）模式下登记核心企业、资金机构
			if (!StringUtil.isEmpty(mfBusApply.getCusNoCore())) {// 核心企业信息
				coreCusNo = mfBusApply.getCusNoCore();// 已登记
			} else {
				coreCusNo = "0";// 未登记
			}
			if (!StringUtil.isEmpty(mfBusApply.getCusNoFund())) {// 资金机构信息
				fundCusNo = mfBusApply.getCusNoFund();// 已登记
			} else {
				fundCusNo = "0";// 未登记
			}
		}else {
		}
		dataMap.put("wareHouseCusNo", wareHouseCusNo);
		dataMap.put("coreCusNo", coreCusNo);
		dataMap.put("fundCusNo", fundCusNo);
		// 处理多业务
		MfBusApply mfBusApplyTmp = new MfBusApply();
		mfBusApplyTmp.setCusNo(mfBusApply.getCusNo());
		List<MfBusApply> mfBusApplyList = appInterfaceFeign.getMultiBusList(mfBusApplyTmp);
		dataMap.put("moreApplyCount", mfBusApplyList.size());
		MfBusPact mfBusPactTmp = new MfBusPact();
		mfBusPactTmp.setCusNo(mfBusApply.getCusNo());
		List<MfBusPact> mfBusPacts = pactInterfaceFeign.getMultiBusList(mfBusPactTmp);
		dataMap.put("morePactCount", mfBusPacts.size());
		MfBusFincApp mfBusFincAppTmp = new MfBusFincApp();
		mfBusFincAppTmp.setCusNo(mfBusApply.getCusNo());
		List<MfBusFincApp> mfBusFincApps = pactInterfaceFeign.getMultiBusList(mfBusFincAppTmp);
		dataMap.put("moreFincCount", mfBusFincApps.size());

		MfAssureInfo mfAssureInfo = new MfAssureInfo();
		mfAssureInfo.setAssureNo(mfBusApply.getCusNo());
		List<MfBusApplyAssureInfo> mfBusApplyAssureInfos = assureInterfaceFeign.getMultiBusList(mfAssureInfo);
		dataMap.put("moreAssureCount", mfBusApplyAssureInfos.size());
		dataMap.put("flag","success");
		return dataMap;
	}
	/**
	 * 方法描述： 获取融资下的账款列表信息
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-7-27 下午4:34:52
	 */
	@RequestMapping(value = "/getReceListAjax")
	@ResponseBody
	public Map<String, Object> getReceListAjax(String fincMainId,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//获取融资下的借据信息
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincMainId(fincMainId);
		List<MfBusFincApp> mfBusFincAppList = mfBusFincAppFeign.getByFincMainId(mfBusFincApp);
		List<MfBusReceBaseInfo> mfBusReceBaseInfoList = new ArrayList<MfBusReceBaseInfo>();
		if (mfBusFincAppList!=null){
			for (int i = 0; i < mfBusFincAppList.size(); i++) {
				mfBusFincApp = mfBusFincAppList.get(i);
				String transId = mfBusFincApp.getTransId();
				if (StringUtil.isNotEmpty(transId)){
					MfBusReceTransfer mfBusReceTransfer = new 	MfBusReceTransfer();
					mfBusReceTransfer.setTransId(transId);
					mfBusReceTransfer = mfBusReceTransferFeign.getById(mfBusReceTransfer);
					if (mfBusReceTransfer!=null){
						String receId = mfBusReceTransfer.getReceId();
						MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
						mfBusReceBaseInfo.setReceId(receId);
						mfBusReceBaseInfo = mfBusReceBaseInfoFeign.getById(mfBusReceBaseInfo);
						if (mfBusReceBaseInfo!=null){
							mfBusReceBaseInfo.setPutoutAmt(mfBusFincApp.getPutoutAmt());
							mfBusReceBaseInfo.setIntstEndDate(mfBusFincApp.getIntstEndDate());
							mfBusReceBaseInfoList.add(mfBusReceBaseInfo);
						}
					}
				}
			}
		}
		Ipage ipage = this.getIpage();
		// 自定义查询Bo方法
		JsonTableUtil jtu = new JsonTableUtil();
		String htmlStr = jtu.getJsonStr(tableId, "thirdTableTag", mfBusReceBaseInfoList, ipage, true);
		dataMap.put("htmlStr",htmlStr);
		dataMap.put("flag","success");
		return dataMap;
	}

    /**
     * 获取应收账款融资主信息
     * @param fincMainId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getMfBusFincMainInfoAjax")
    @ResponseBody
    public Map<String,Object> getMfBusFincMainInfoAjax(String fincMainId) throws Exception{
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfBusFincAppMain mfBusFincAppMain = new MfBusFincAppMain();
        mfBusFincAppMain.setFincMainId(fincMainId);
        mfBusFincAppMain = mfBusFincAppMainFeign.getById(mfBusFincAppMain);
        dataMap.put("appId",mfBusFincAppMain.getAppId());
        dataMap.put("fincSts",mfBusFincAppMain.getFincSts());
        dataMap.put("fincSts",mfBusFincAppMain.getFincSts());
        dataMap.put("approveNodeName",mfBusFincAppMain.getApproveNodeName());
        dataMap.put("approvePartName",mfBusFincAppMain.getApprovePartName());
        return dataMap;
    }

    @RequestMapping(value = "/getReceFincMainDetailFormAjax")
    @ResponseBody
    public Map<String, Object> getReceFincMainDetailFormAjax(String fincMainId, String formId)
            throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //融资申请信息
        MfBusFincAppMain mfBusFincAppMain = new MfBusFincAppMain();
        mfBusFincAppMain.setFincMainId(fincMainId);
		mfBusFincAppMain = mfBusFincAppMainFeign.getById(mfBusFincAppMain);

		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(mfBusFincAppMain.getAppId());
		mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);

		mfBusFincAppMain.setFincRate(MathExtend.showRateMethod(mfBusFincAppMain.getRateType(), mfBusFincAppMain.getFincRate(), Integer.parseInt(mfBusAppKind.getYearDays()), Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));
		mfBusFincAppMain.setOverRate(MathExtend.showRateMethod(mfBusFincAppMain.getRateType(), mfBusFincAppMain.getOverRate(), Integer.parseInt(mfBusAppKind.getYearDays()), Integer.parseInt(mfBusAppKind.getRateDecimalDigits())));

		//合同信息
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setAppId(mfBusFincAppMain.getAppId());
		mfBusPact.setPactId(mfBusFincAppMain.getPactId());
        mfBusPact = mfBusPactFeign.getById(mfBusPact);
        mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
        // 判断客户表单信息是否允许编辑
        String query = cusInterfaceFeign.validateCusFormModify(mfBusPact.getCusNo(), mfBusPact.getAppId(), BizPubParm.FORM_EDIT_FLAG_BUS, User.getRegNo(request));
        //factor传的是"",到web端就变成了null
        if(query==null) {
            query="";
        }
        FormData formfincappmain = formService.getFormData(formId);
        getFormValue(formfincappmain);
        getObjValue(formfincappmain, mfBusPact);
        getObjValue(formfincappmain, mfBusFincAppMain);
        //利率单位处理
        // 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
        Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
        String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
        this.changeFormProperty(formfincappmain, "fincRate", "unit", rateUnit);
        this.changeFormProperty(formfincappmain, "overRate", "unit", rateUnit);
        this.changeFormProperty(formfincappmain, "cmpdRate", "unit", rateUnit);


        JsonFormUtil jsonFormUtil = new JsonFormUtil();
        String htmlStr = jsonFormUtil.getJsonStr(formfincappmain, "propertySeeTag", query);
        dataMap.put("htmlStr", htmlStr);
        dataMap.put("query", query);
        dataMap.put("flag", "success");
        return dataMap;
    }


	/**
	 *
	 * 方法描述： 获取买方还款页面的还款数据
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @date 2017-05-30上午11:31:20
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/repaymentJspForFincMain")
	public String repaymentJspForFincMain(Model model, String fincMainId) throws Exception {
		ActionContext.initialize(request, response);
		MfBusFincAppMain mfBusFincAppMain = new MfBusFincAppMain();
		mfBusFincAppMain.setFincMainId(fincMainId);
		mfBusFincAppMain = mfBusFincAppMainFeign.getById(mfBusFincAppMain);
		if (mfBusFincAppMain!=null){
			String appId = mfBusFincAppMain.getAppId();
			String pactId = mfBusFincAppMain.getPactId();
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply.setAppId(appId);
			mfBusApply = appInterfaceFeign.getMfBusApply(mfBusApply);
			MfBusPact mfBusPact = new MfBusPact();
			mfBusPact.setPactId(pactId);
			mfBusPact = pactInterfaceFeign.getById(mfBusPact);
			//分装买方还款数据
			MfRepaymentBuyBean mfRepaymentBuyBean = new MfRepaymentBuyBean();
			mfRepaymentBuyBean.setCusName(mfBusFincAppMain.getCusName());
			mfRepaymentBuyBean.setAppName(mfBusApply.getAppName());
			mfRepaymentBuyBean.setPactAmt(mfBusPact.getPactAmt());
			mfRepaymentBuyBean.setFincMainId(fincMainId);
			mfRepaymentBuyBean.setRepayDate(DateUtil.getDate());
			mfRepaymentBuyBean.setFincAmt(mfBusFincAppMain.getFincAmt());
			mfRepaymentBuyBean.setFincLoanBal(mfBusFincAppMain.getFincBal());
			mfRepaymentBuyBean.setRepayDate(DateUtil.getDate());
			//查询应收账款
			MfBusFincApp mfBusFincAppQuery = new MfBusFincApp();
			mfBusFincAppQuery.setFincMainId(fincMainId);
			List<MfBusFincApp> mfBusFincAppList = mfBusFincAppFeign.getByFincMainId(mfBusFincAppQuery);
			List<MfBusReceBaseInfo> mfBusReceBaseInfoList = new ArrayList<MfBusReceBaseInfo>();
			for (int i = 0; i < mfBusFincAppList.size(); i++) {
				mfBusFincAppQuery = mfBusFincAppList.get(i);
				if (BizPubParm.FINC_STS_AGREE.equals(mfBusFincAppQuery.getFincSts())){
					//获取转让字表
					String tranId = mfBusFincAppQuery.getTransId();
					MfBusReceTransfer mfBusReceTransfer = new MfBusReceTransfer();
					mfBusReceTransfer.setTransId(tranId);
					mfBusReceTransfer = mfBusReceTransferFeign.getById(mfBusReceTransfer);
					String receId = mfBusReceTransfer.getReceId();
					MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
					mfBusReceBaseInfo.setReceId(receId);
					mfBusReceBaseInfo = mfBusReceBaseInfoFeign.getById(mfBusReceBaseInfo);
					mfBusReceBaseInfo.setPutoutAmt(mfBusFincAppQuery.getPutoutAmt());
					// 还款页面数据取值 wd
					Map<String, Object> resultMap = mfRepaymentFeign.doRepaymentJsp(mfBusFincAppQuery.getFincId());
					// 还款信息
					mfBusReceBaseInfo.setFincId(mfBusFincAppQuery.getFincId());
					mfBusReceBaseInfo.setRepayObject("2");
					mfBusReceBaseInfo.setThisRepayDate(DateUtil.getShowDateTime(DateUtil.getDate()));
					mfBusReceBaseInfoList.add(mfBusReceBaseInfo);
				}
			}
			FormService formService = new FormService();
			FormData formbuyerRepaymentBase = formService.getFormData("fincMainRepaymentBase");
			getObjValue(formbuyerRepaymentBase, mfRepaymentBuyBean);
			model.addAttribute("formbuyerRepaymentBase", formbuyerRepaymentBase);
			model.addAttribute("query", "");
			JsonTableUtil jtu = new JsonTableUtil();
			Ipage ipage = this.getIpage();
			String tableHtml = jtu.getJsonStr("tablefincMainAccountBaseinfo", "thirdTableTag", mfBusReceBaseInfoList, ipage, true);
			JSONObject json = new JSONObject();
			json.put("tableHtml",tableHtml);
			model.addAttribute("ajaxData",json.toString());
			String onlinePayType = new CodeUtils().getSingleValByKey("APP_ONLINE_PAY_TYPE");// 判断贷后还款方式是否显示 0：不显示; 1:显示
			model.addAttribute("onlinePayType", onlinePayType);
			model.addAttribute("query", "");
		}

		return "component/pact/repay/MfRepayment_MfRepaymentJspForFincMain";
	}
	/**
	 *
	 * 方法描述： 还款处理方法
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @param ajaxList
	 * @date 2017-05-25下午2:12:27
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/doRepaymentOperateForBuyFincMain")
	@ResponseBody
	public Map<String, Object> doRepaymentOperateForBuyFincMain(String ajaxData, String ajaxList) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<String, String>();
		try {
			Map<String, Object> parmMap = getMapByJson(ajaxData);
			JSONArray jsonArray = JSONArray.fromObject(ajaxList);
			parmMap.put("repayInfoList", jsonArray);
			resMap = mfRepaymentFeign.doRepaymentOperateForBuyFincMain(parmMap);
			dataMap.put("flag", "success");
			dataMap.put("getInfoUrl", "MfRepayHistoryAction_getById.action?repayId=" + resMap.get("repayId"));
			dataMap.put("repayId", resMap.get("repayId"));
			dataMap.put("wkfRepayId", resMap.get("wkfRepayId"));
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("买方还款"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("买方还款"));
			throw e;
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 获取卖方还款页面的还款数据
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @date 2017-05-30上午11:31:20
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/repaymentJsp")
	public String repaymentJsp(Model model, String fincMainId) throws Exception {
		ActionContext.initialize(request, response);
		MfBusFincAppMain mfBusFincAppMain = new MfBusFincAppMain();
		mfBusFincAppMain.setFincMainId(fincMainId);
		mfBusFincAppMain = mfBusFincAppMainFeign.getById(mfBusFincAppMain);
		if (mfBusFincAppMain!=null){
			String appId = mfBusFincAppMain.getAppId();
			String pactId = mfBusFincAppMain.getPactId();
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply.setAppId(appId);
			mfBusApply = appInterfaceFeign.getMfBusApply(mfBusApply);
			MfBusPact mfBusPact = new MfBusPact();
			mfBusPact.setPactId(pactId);
			mfBusPact = pactInterfaceFeign.getById(mfBusPact);
			//分装买方还款数据
			MfRepaymentBuyBean mfRepaymentBuyBean = new MfRepaymentBuyBean();
			mfRepaymentBuyBean.setCusName(mfBusFincAppMain.getCusName());
			mfRepaymentBuyBean.setAppName(mfBusApply.getAppName());
			mfRepaymentBuyBean.setPactAmt(mfBusPact.getPactAmt());
			mfRepaymentBuyBean.setFincMainId(fincMainId);
			mfRepaymentBuyBean.setRepayDate(DateUtil.getDate());
			mfRepaymentBuyBean.setFincAmt(mfBusFincAppMain.getFincAmt());
			mfRepaymentBuyBean.setFincLoanBal(mfBusFincAppMain.getFincBal());
			mfRepaymentBuyBean.setRepayDate(DateUtil.getDate());
			//查询应收账款
			MfBusFincApp mfBusFincAppQuery = new MfBusFincApp();
			mfBusFincAppQuery.setFincMainId(fincMainId);
			List<MfBusFincApp> mfBusFincAppList = mfBusFincAppFeign.getByFincMainId(mfBusFincAppQuery);
			List<MfBusReceBaseInfo> mfBusReceBaseInfoList = new ArrayList<MfBusReceBaseInfo>();
			//判断是否允许提前还款
			JSONArray accountRepayType  =new CodeUtils().getJSONArrayByKeyName("ACCOUNT_REPAY_TYPE");
			List<OptionsList> opinionTypeList = new ArrayList<OptionsList>();
			if (accountRepayType!=null){
				for (int i = 0; i < accountRepayType.size(); i++) {
					JSONObject jsonObject = (JSONObject) accountRepayType.get(i);
					OptionsList optionsList = new OptionsList();
					optionsList.setOptionId(jsonObject.getString("optCode"));
					optionsList.setOptionLabel(jsonObject.getString("optName"));
					optionsList.setOptionValue(jsonObject.getString("optCode"));
					opinionTypeList.add(optionsList);
				}
			}
			boolean tiqianFlag = false;
			JSONArray accountRepayType2  = new JSONArray();
			for (int i = 0; i < mfBusFincAppList.size(); i++) {
				mfBusFincAppQuery = mfBusFincAppList.get(i);
				if (BizPubParm.FINC_STS_AGREE.equals(mfBusFincAppQuery.getFincSts())){
					Map<String,String> parmMap = new HashMap<String, String>();
					parmMap.put("fincId",mfBusFincAppQuery.getFincId());
					Map<String,Object> resultMap2 = new HashMap<String, Object>();
					resultMap2 = calcPrepaymentInterfaceFeign.doCheckTiQianHuanKuan(parmMap);
					if (resultMap2!=null) {
						if ("success".equals((String) resultMap2.get("flag"))) {

						} else {
							tiqianFlag=true;
						}
					}
					//获取转让字表
					String tranId = mfBusFincAppQuery.getTransId();
					MfBusReceTransfer mfBusReceTransfer = new MfBusReceTransfer();
					mfBusReceTransfer.setTransId(tranId);
					mfBusReceTransfer = mfBusReceTransferFeign.getById(mfBusReceTransfer);
					String receId = mfBusReceTransfer.getReceId();
					MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
					mfBusReceBaseInfo.setReceId(receId);
					mfBusReceBaseInfo = mfBusReceBaseInfoFeign.getById(mfBusReceBaseInfo);
					mfBusReceBaseInfo.setPutoutAmt(mfBusFincAppQuery.getPutoutAmt());
					// 还款页面数据取值 wd
					Map<String, Object> resultMap = mfRepaymentFeign.doRepaymentJsp(mfBusFincAppQuery.getFincId());
					// 还款信息
					MfRepaymentBean mfRepaymentBean = (MfRepaymentBean) JsonStrHandling.handlingStrToBean(resultMap.get("mfRepaymentBean"), MfRepaymentBean.class);
					mfBusReceBaseInfo.setRateAmt(MathExtend.add(mfRepaymentBean.getShiShouLiXi(),mfRepaymentBean.getShiShouFaXi() ));
					mfBusReceBaseInfo.setFincId(mfBusFincAppQuery.getFincId());
					mfBusReceBaseInfo.setRepayObject("2");
					mfBusReceBaseInfo.setIntstEndDate(mfBusFincAppQuery.getIntstEndDate());
					mfBusReceBaseInfo.setFincLoanBal(mfBusFincAppQuery.getLoanBal());
					mfBusReceBaseInfo.setThisRepayDate(DateUtil.getShowDateTime(DateUtil.getDate()));
					mfBusReceBaseInfoList.add(mfBusReceBaseInfo);
				}
			}
			if (tiqianFlag){
				for (int i = 0; i < opinionTypeList.size(); i++) {
					OptionsList optionsList = opinionTypeList.get(i);
					if("2".equals(optionsList.getOptionId())){
						opinionTypeList.remove(optionsList);
					}
				}
			}
			FormService formService = new FormService();
			FormData formbuyerRepaymentBase = formService.getFormData("fincMainRepayment");
			getObjValue(formbuyerRepaymentBase, mfRepaymentBuyBean);
			this.changeFormProperty(formbuyerRepaymentBase, "repayType", "optionArray", opinionTypeList);
			model.addAttribute("formbuyerRepaymentBase", formbuyerRepaymentBase);
			model.addAttribute("query", "");
			JsonTableUtil jtu = new JsonTableUtil();
			Ipage ipage = this.getIpage();
			String tableHtml = jtu.getJsonStr("tablefincMainRepaymentList", "thirdTableTag", mfBusReceBaseInfoList, ipage, true);
			JSONObject json = new JSONObject();
			json.put("tableHtml",tableHtml);
			model.addAttribute("ajaxData",json.toString());
			String onlinePayType = new CodeUtils().getSingleValByKey("APP_ONLINE_PAY_TYPE");// 判断贷后还款方式是否显示 0：不显示; 1:显示
			model.addAttribute("onlinePayType", onlinePayType);
			model.addAttribute("query", "");
		}

		return "component/pact/repay/MfRepayment_MfRepaymentJspForFincMainMai";
	}

	/**
	 *
	 * 方法描述： 还款处理方法
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @param ajaxList
	 * @date 2017-05-25下午2:12:27
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/doRepaymentOperateForBuyFincMainMai")
	@ResponseBody
	public Map<String, Object> doRepaymentOperateForBuyFincMainMai(String ajaxData, String ajaxList) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<String, String>();
		try {
			Map<String, Object> parmMap = getMapByJson(ajaxData);
			JSONArray jsonArray = JSONArray.fromObject(ajaxList);
			parmMap.put("repayInfoList", jsonArray);
			resMap = mfRepaymentFeign.doRepaymentOperateForBuyFincMainMai(parmMap);
			dataMap.put("flag", "success");
			dataMap.put("getInfoUrl", "MfRepayHistoryAction_getById.action?repayId=" + resMap.get("repayId"));
			dataMap.put("repayId", resMap.get("repayId"));
			dataMap.put("wkfRepayId", resMap.get("wkfRepayId"));
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("反转让"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("反转让"));
			throw e;
		}
		return dataMap;
	}
	/**
	 * 方法描述： 获取收款计划信息
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-7-28 上午10:25:14
	 */
	@RequestMapping(value = "/getMfRepayPlanListAjax")
	@ResponseBody
	public Map<String, Object> getMfRepayPlanListAjax(String fincMainId,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 获取收益计划 信息
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincMainId(fincMainId);
		List<MfBusFincApp> mfBusFincAppList = 	mfBusFincAppFeign.getByFincMainId(mfBusFincApp);
		List<MfRepayHistory> mfRepayHistoryArrayList = new ArrayList<MfRepayHistory>();
		if (mfBusFincAppList!=null){
			for (int i = 0; i < mfBusFincAppList.size(); i++) {
				mfBusFincApp = mfBusFincAppList.get(i);
				// 判断详情页面还款按钮的显隐
				mfBusFincApp = mfBusFincAppFeign.disProcessDataForFincShow(mfBusFincApp);
				MfBusReceTransfer mfBusReceTransfer = new MfBusReceTransfer();
				mfBusReceTransfer.setTransId(mfBusFincApp.getTransId());
				mfBusReceTransfer =mfBusReceTransferFeign.getById(mfBusReceTransfer);
				String receNo="";
				if (mfBusReceTransfer!=null){
					String receId = mfBusReceTransfer.getReceId();
					MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
					mfBusReceBaseInfo.setReceId(receId);
					mfBusReceBaseInfo = mfBusReceBaseInfoFeign.getById(mfBusReceBaseInfo);
					receNo = mfBusReceBaseInfo.getReceNo();
				}
				// 还款历史
				if (mfBusFincApp != null) {
					MfRepayHistory mfRepayHistory = new MfRepayHistory();
					mfRepayHistory.setFincId(mfBusFincApp.getFincId());
					List<MfRepayHistory> mfRepayHistoryList = mfRepayHistoryFeign.getList(mfRepayHistory);
					// 获取减免费用总和 只是用于展示 存放在 deductNormIntstSum 字段中展示
					for (MfRepayHistory repayHistory : mfRepayHistoryList) {
						Double duectSum = MathExtend.add(repayHistory.getDeductCmpdIntstSum(),
								repayHistory.getDeductNormIntstSum());
						duectSum = MathExtend.add(duectSum, repayHistory.getDeductOverIntstSum());
						duectSum = MathExtend.add(duectSum, repayHistory.getDeductFeeSum());
						duectSum = MathExtend.add(duectSum, repayHistory.getDeductPenaltySum());
						repayHistory.setDeductNormIntstSum(duectSum);
						if (repayHistory.getBalAmt() == null) {// 结余金额空处理
							repayHistory.setBalAmt(0.0);
						}
						repayHistory.setReceNo(receNo);
						mfRepayHistoryArrayList.add(repayHistory);
					}
				}
			}
		}

		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfRepayHistoryArrayList, null, true);
		dataMap.put("htmlStr", tableHtml);
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 获取融资详情尾款结付还款页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @date 2017-05-30上午11:31:20
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/tailPaymentForFincMain")
	public String tailPaymentForFincMain(Model model, String fincMainId) throws Exception {
		ActionContext.initialize(request, response);
		MfBusFincAppMain mfBusFincAppMain = new MfBusFincAppMain();
		mfBusFincAppMain.setFincMainId(fincMainId);
		mfBusFincAppMain = mfBusFincAppMainFeign.getById(mfBusFincAppMain);
		if (mfBusFincAppMain!=null){
			//判断融资下是否存在逾期数据
			String overFlag = "0";
			if (StringUtil.isNotEmpty(fincMainId)){
				MfBusFincApp mfBusFincAppQuery = new MfBusFincApp();
				mfBusFincAppQuery.setFincMainId(fincMainId);
				List<MfBusFincApp> mfBusFincAppList = mfBusFincAppFeign.getByFincMainId(mfBusFincAppQuery);
				if (mfBusFincAppList!=null){
					for (int i = 0; i < mfBusFincAppList.size(); i++) {
						mfBusFincAppQuery = mfBusFincAppList.get(i);
						if ("1".equals(mfBusFincAppQuery.getOverdueSts())){
							overFlag ="1";
							break;
						}
					}
				}
			}
			model.addAttribute("overFlag",overFlag);
			String appId = mfBusFincAppMain.getAppId();
			String pactId = mfBusFincAppMain.getPactId();
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply.setAppId(appId);
			mfBusApply = appInterfaceFeign.getMfBusApply(mfBusApply);
			MfBusPact mfBusPact = new MfBusPact();
			mfBusPact.setPactId(pactId);
			mfBusPact = pactInterfaceFeign.getById(mfBusPact);
			//分装买方还款数据
			MfRepaymentBuyBean mfRepaymentBuyBean = new MfRepaymentBuyBean();
			mfRepaymentBuyBean.setCusName(mfBusFincAppMain.getCusName());
			mfRepaymentBuyBean.setAppName(mfBusApply.getAppName());
			mfRepaymentBuyBean.setPactAmt(mfBusPact.getPactAmt());
			mfRepaymentBuyBean.setFincMainId(fincMainId);
			mfRepaymentBuyBean.setRepayDate(DateUtil.getDate());
			mfRepaymentBuyBean.setFincAmt(mfBusFincAppMain.getFincAmt());
			mfRepaymentBuyBean.setFincLoanBal(mfBusFincAppMain.getFincBal());
			mfRepaymentBuyBean.setRepayDate(DateUtil.getDate());
			//查询应收账款
			MfBusFincApp mfBusFincAppQuery = new MfBusFincApp();
			mfBusFincAppQuery.setFincMainId(fincMainId);
			List<MfBusFincApp> mfBusFincAppList = mfBusFincAppFeign.getByFincMainId(mfBusFincAppQuery);
			List<MfBusReceBaseInfo> mfBusReceBaseInfoList = new ArrayList<MfBusReceBaseInfo>();

			boolean tiqianFlag = false;
			JSONArray accountRepayType2  = new JSONArray();
			for (int i = 0; i < mfBusFincAppList.size(); i++) {
				mfBusFincAppQuery = mfBusFincAppList.get(i);
				Map<String,String> parmMap = new HashMap<String, String>();
				parmMap.put("fincId",mfBusFincAppQuery.getFincId());
				MfBusReceBaseInfo mfBusReceBaseInfo = new MfBusReceBaseInfo();
				//获取结余金额
				//根据借据号获取账款结余
				MfBusReceBal mfBusReceBal = new MfBusReceBal();
				mfBusReceBal.setFincId(mfBusFincAppQuery.getFincId());
				mfBusReceBal =mfBusReceBalFeign.getById(mfBusReceBal);
				double tailBalAmt =0.00;
				String  balId ="";
				if (mfBusReceBal!=null){
                    tailBalAmt=mfBusReceBal.getBalAmt();
					balId = mfBusReceBal.getBalId();
				}else {
					continue;
				}
				//获取转让字表
				String tranId = mfBusFincAppQuery.getTransId();
				MfBusReceTransfer mfBusReceTransfer = new MfBusReceTransfer();
				mfBusReceTransfer.setTransId(tranId);
				mfBusReceTransfer = mfBusReceTransferFeign.getById(mfBusReceTransfer);
				String receId = mfBusReceTransfer.getReceId();

				mfBusReceBaseInfo.setReceId(receId);
				mfBusReceBaseInfo = mfBusReceBaseInfoFeign.getById(mfBusReceBaseInfo);
				mfBusReceBaseInfo.setPutoutAmt(mfBusFincAppQuery.getPutoutAmt());
                mfBusReceBaseInfo.setTailBalAmt(tailBalAmt);
				// 还款页面数据取值 wd
				Map<String, Object> resultMap = mfRepaymentFeign.doRepaymentJsp(mfBusFincAppQuery.getFincId());
				// 还款信息
				MfRepaymentBean mfRepaymentBean = (MfRepaymentBean) JsonStrHandling.handlingStrToBean(resultMap.get("mfRepaymentBean"), MfRepaymentBean.class);
				mfBusReceBaseInfo.setRateAmt(MathExtend.add(mfRepaymentBean.getShiShouLiXi(),mfRepaymentBean.getShiShouFaXi() ));
				mfBusReceBaseInfo.setFincId(mfBusFincAppQuery.getFincId());
				mfBusReceBaseInfo.setRepayObject("2");
				mfBusReceBaseInfo.setBalId(balId);
				mfBusReceBaseInfoList.add(mfBusReceBaseInfo);
			}
			FormService formService = new FormService();
			FormData formfincMainTail = formService.getFormData("fincMainTail");
			getObjValue(formfincMainTail, mfRepaymentBuyBean);
			model.addAttribute("formfincMainTail", formfincMainTail);
			model.addAttribute("query", "");
			JsonTableUtil jtu = new JsonTableUtil();
			Ipage ipage = this.getIpage();
			String tableHtml = jtu.getJsonStr("tablefincMainTailList", "thirdTableTag", mfBusReceBaseInfoList, ipage, true);
			JSONObject json = new JSONObject();
			json.put("tableHtml",tableHtml);
			model.addAttribute("ajaxData",json.toString());
			String onlinePayType = new CodeUtils().getSingleValByKey("APP_ONLINE_PAY_TYPE");// 判断贷后还款方式是否显示 0：不显示; 1:显示
			model.addAttribute("onlinePayType", onlinePayType);
			model.addAttribute("query", "");
		}
		return "component/pact/repay/MfRepayment_MfReceBalForFincMain";
	}
	/**
	 *
	 * 方法描述： 批量尾款解付处理方法
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @param ajaxList
	 * @date 2017-05-25下午2:12:27
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/tailAjax")
	@ResponseBody
	public Map<String, Object> tailAjax(String ajaxData, String ajaxList) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<String, String>();
		try {
			Map<String, Object> parmMap = getMapByJson(ajaxData);
			JSONArray jsonArray = JSONArray.fromObject(ajaxList);
			parmMap.put("tailList", jsonArray);

			resMap = mfRepaymentFeign.doTailOperateForFincMain(parmMap);
			dataMap.put("flag", "success");
			dataMap.put("getInfoUrl", "MfRepayHistoryAction_getById.action?repayId=" + resMap.get("repayId"));
			dataMap.put("repayId", resMap.get("repayId"));
			dataMap.put("wkfRepayId", resMap.get("wkfRepayId"));
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("买方还款"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("买方还款"));
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/getTaskInfoAjax")
	@ResponseBody
	public Map<String, Object> getTaskInfoAjax(String wkfAppId,String fincMainId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String appOpNo = User.getRegNo(request);
		TaskImpl task = wkfInterfaceFeign.getTaskWithUser(wkfAppId, "",appOpNo);
		if(task == null){//表示业务已经结束
			dataMap.put("wkfFlag", "0");
		}else{//表示业务尚未结束
			//节点编号
			String nodeName = task.getActivityName();
			String currentUser = User.getRegNo(request);
			String assignee = taskFeign.getAssignee(task.getId());
			if(!"".equals(assignee)&&assignee!=null&&!currentUser.equals(assignee)){
				dataMap.put("assign", true);
			}
			dataMap.put("wkfFlag", "1");
			String url=taskFeign.openApproveUrl(task.getId());
			String title=task.getDescription();
			dataMap.put("result", task.getResult());
			dataMap.put("url", url);
			dataMap.put("title", title);
			dataMap.put("nodeName",nodeName);
		}
		return dataMap;
	}


	/**
	 *
	 * 方法描述：单独提交业务流程，并更新 业务状态(用于审批子流程完成之后，提交业务流程失败的情况，手动提交业务流程)
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-10-24 上午10:53:24
	 */
	@RequestMapping(value = "/commitBusProcessAjax")
	@ResponseBody
	public Map<String, Object> commitBusProcessAjax(String ajaxData,String fincMainId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("flag", "success");
		try {
			MfBusFincAppMain mfBusFincAppMain = new MfBusFincAppMain();
			mfBusFincAppMain.setFincMainId(fincMainId);
			mfBusFincAppMain = mfBusFincAppMainFeign.getById(mfBusFincAppMain);
//			logger.info("手动进行业务流程提交，申请号：{}，合同号：{}，业务流程编号：{}",mfBusApply.getAppId(),mfBusApply.getPactId(),mfBusApply.getWkfAppId());
			Result result = wkfBusInterfaceFeign.doCommitNextStepWithUser(mfBusFincAppMain.getAppId(), mfBusFincAppMain.getWkfAppId(),User.getRegNo(request));
			if(null!=result&&result.isSuccess()){
				dataMap.put("msg", result.getMsg());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", result.getMsg());
			}
		} catch (Exception e) {
//			logger.error("审批子流程完成,手动进行业务流程提交失败："+e.getMessage(),e);
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;
	}


	/********************************************************************************/
	//                         标准产品批量放款申请、审批相关 begin
	/********************************************************************************/
	/**
	 *
	 * 方法描述 业务流程中批量放款申请
	 * @param [model, pactId]
	 * @return java.lang.String
	 * @author zhs
	 * @date 2018/11/28 10:53
	 */
	@RequestMapping(value = "/inputBatchForBiz")
	public String inputBatchForBiz(Model model,String appId,String pactId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		// 获取合同数据
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setAppId(appId);
		mfBusPact.setPactId(pactId);
		mfBusPact = pactInterfaceFeign.getById(mfBusPact);
		mfBusPact = pactInterfaceFeign.processDataForPact(mfBusPact);

		TaskImpl taskAppro = wkfInterfaceFeign.getTask(mfBusPact.getWkfAppId(), null);
		// 功能节点编号
		String nodeNo =taskAppro.getActivityName();
		//获取formId
		String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), BizPubParm.WKF_NODE.putout_apply, null, null,User.getRegNo(request));
		FormData formfincappbatchbase = formService.getFormData(formId);

		//获取合同下的借据列表
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setPactId(pactId);
		mfBusFincApp.setFincSts(BizPubParm.FINC_STS_UN_SUBMIT);
		List<MfBusFincApp> fincAppBatchList = mfBusFincAppFeign.getList(mfBusFincApp);
		String fincMainId =  WaterIdUtil.getWaterId("FM");
		model.addAttribute("fincMainId", fincMainId);
		MfBusFincAppMain mfBusFincAppMain = new MfBusFincAppMain();
		if(fincAppBatchList!=null && fincAppBatchList.size()>0){
			mfBusFincAppMain.setPlanPutoutCnt(fincAppBatchList.size());
			mfBusFincAppMain.setMultiParam(fincAppBatchList.get(0).getMultiParam());

			fincMainId = fincAppBatchList.get(0).getFincMainId();
			if(StringUtil.isNotEmpty(fincMainId)){
				model.addAttribute("fincMainId", fincMainId);
			}
			Double usableFincAmt = mfBusPact.getUsableFincAmt();
			for(MfBusFincApp fincApp :fincAppBatchList){
				usableFincAmt = usableFincAmt -fincApp.getPutoutAmt();
			}
			mfBusPact.setUsableFincAmt(usableFincAmt);

		}

		mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincMainId(fincMainId);
		mfBusFincApp.setPactAmt(mfBusPact.getPactAmt());
		mfBusFincApp.setTermMonth(mfBusPact.getTerm());
		mfBusFincApp.setOpName(User.getRegName(request));
		mfBusFincApp.setOpNo(User.getRegNo(request));
		mfBusFincApp.setPactId(mfBusPact.getPactId());
		mfBusFincApp.setChargeInterest(0);
		mfBusFincApp.setChargeFee(0);
		mfBusFincApp.setMultiParam(mfBusFincAppMain.getMultiParam());
		mfBusFincApp.setIntstEndDate(mfBusPact.getEndDate());
		getObjValue(formfincappbatchbase, mfBusFincAppMain);
		getObjValue(formfincappbatchbase, mfBusPact);
		getObjValue(formfincappbatchbase, mfBusFincApp);

		// 放款申请表单中的还款方式修改时应收产品设置中的还款方式控制
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(mfBusPact.getAppId());
		mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
		JSONArray repayTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusAppKind.getRepayType(), "REPAY_TYPE");

		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
		this.changeFormProperty(formfincappbatchbase, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formfincappbatchbase, "overRate", "unit", rateUnit);
		this.changeFormProperty(formfincappbatchbase, "cmpdRate", "unit", rateUnit);

		// 客户的账户列表
		MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
		mfCusBankAccManage.setCusNo(mfBusPact.getCusNo());
		mfCusBankAccManage.setUseFlag(BizPubParm.YES_NO_Y);
		mfCusBankAccManage.setDelFlag(BizPubParm.YES_NO_N);
		List<MfCusBankAccManage> mfCusBankAccManageList = cusInterfaceFeign.getMfCusBankAccListByCusNo(mfCusBankAccManage);
		JSONArray bankArray = JSONArray.fromObject(mfCusBankAccManageList);
		for (int i = 0; i < bankArray.size(); i++) {
			bankArray.getJSONObject(i).put("name",
					bankArray.getJSONObject(i).getString("accountNo").replaceAll("([\\d]{4})(?=\\d)", "$1 "));
		}
		JSONObject json = new JSONObject();
		json.put("repayTypeMap", repayTypeMap);
		json.put("bankArray", bankArray);
		model.addAttribute("ajaxData", json.toString());
		model.addAttribute("wkfAppId", mfBusPact.getWkfAppId());
		model.addAttribute("query", "");
		model.addAttribute("mfBusPact", mfBusPact);
		model.addAttribute("kindNo", mfBusPact.getKindNo());
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("formfincappbatchbase", formfincappbatchbase);
		model.addAttribute("fincAppBatchList", fincAppBatchList);
		return "/component/pact/receaccount/MfBusFincAppMain_InsertBatchForBiz";
	}


	@RequestMapping(value = "/insertForBatchAjax")
	@ResponseBody
	public  Map<String,Object> insertForBatchAjax(String ajaxData,String pactId,String fincMainId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		dataMap.put("flag", "success");
		try {
			Map map = getMapByJson(ajaxData);
			FormData formrecefincappbase = formService.getFormData((String) map.get("formId"));
			getFormValue(formrecefincappbase, map);
			if (this.validateFormData(formrecefincappbase)) {
				//合同信息
				MfBusPact mfBusPact = new MfBusPact();
				mfBusPact.setPactId(pactId);
				mfBusPact = mfBusPactFeign.getById(mfBusPact);
				//校验申请金额与合同可融资金额的大小
				MfBusFincApp mfBusFincApp = new MfBusFincApp();
				mfBusFincApp.setFincMainId(fincMainId);
				List<MfBusFincApp> list = mfBusFincAppFeign.getByFincMainId(mfBusFincApp);
				Double putoutAmt = 0.00;
				int repayPlanMergeCnt = 0;
				if(list!=null && list.size()>0){
					for(MfBusFincApp fincApp:list){
						if(BizPubParm.YES_NO_Y.equals(fincApp.getRepayPlanMergeFlag())){
							repayPlanMergeCnt = repayPlanMergeCnt+1;
						}
						putoutAmt = putoutAmt+fincApp.getPutoutAmt();
					}
				}
				if(repayPlanMergeCnt==1){
					dataMap.put("flag", "error");
					Map<String,String> parmMap = new HashMap<String,String>();
					parmMap.put("timeOne","还款计划合并条数");
					parmMap.put("value","1条");
					dataMap.put("msg", MessageEnum.NOT_EQUAL_TIME.getMessage(parmMap));
				}else {
					MfBusFincAppMain mfBusFincAppMain = new MfBusFincAppMain();
					setObjValue(formrecefincappbase, mfBusFincAppMain);
					if(putoutAmt==0.00){
						dataMap.put("flag", "error");
						dataMap.put("msg", MessageEnum.FIRST_OPERATION.getMessage("出账明细的生成"));
					}else if(putoutAmt>mfBusPact.getUsableFincAmt()){//如果申请金额大于可融资金额
						dataMap.put("flag", "error");
						Map<String,String> parmMap = new HashMap<String,String>();
						parmMap.put("timeOne","申请金额");
						parmMap.put("timeTwo","可支用金额");
						dataMap.put("msg", MessageEnum.NOT_FORM_TIME.getMessage(parmMap));
					}else{
						mfBusFincAppMain.setCusNo(mfBusPact.getCusNo());
						mfBusFincAppMain.setCusName(mfBusPact.getCusName());
						mfBusFincAppMain.setAppId(mfBusPact.getAppId());
						mfBusFincAppMain.setFincMainId(fincMainId);
						mfBusFincAppMain.setPactId(mfBusPact.getPactId());
						mfBusFincAppMain.setFincAmt(putoutAmt);
						map.put("mfBusFincAppMain",mfBusFincAppMain);
						dataMap = mfBusFincAppMainFeign.insertForBatch(map);
						dataMap.put("flag", "success");
					}
				}
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述 跳转至批量放款申请审批页面
	 * @param [model, appId, transMainId, taskId, hideOpinionType, activityType]
	 * @return java.lang.String
	 * @author zhs
	 * @date 2018/11/30 11:25
	 */
	@RequestMapping(value = "/getViewPointForBatch")
	public String getViewPointForBatch(Model model,String fincMainId, String taskId, String hideOpinionType, String activityType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(fincMainId, taskId);// 当前审批节点task
		//融资申请主信息
		MfBusFincAppMain mfBusFincAppMain = new MfBusFincAppMain();
		mfBusFincAppMain.setFincMainId(fincMainId);
		mfBusFincAppMain = mfBusFincAppMainFeign.getById(mfBusFincAppMain);

		//获取合同信息
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setAppId(mfBusFincAppMain.getAppId());
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		//出账明细列表
		MfBusFincApp mfBusFincAppParm = new MfBusFincApp();
		mfBusFincAppParm.setFincMainId(fincMainId);
		List<MfBusFincApp> mfBusFincAppList = mfBusFincAppFeign.getByFincMainId(mfBusFincAppParm);

		// 功能节点编号
		String nodeNo = BizPubParm.WKF_NODE.finc_approval.getNodeNo();
		//获取formId
		String formId = prdctInterfaceFeign.getFormId(mfBusPact.getKindNo(), BizPubParm.WKF_NODE.finc_approval, null, fincMainId,User.getRegNo(request));
		FormData formbatchfincapprovalbase = formService.getFormData(formId);

		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setPactAmt(mfBusPact.getUsableFincAmt());
		mfBusFincApp.setOpName(User.getRegName(request));
		mfBusFincApp.setOpNo(User.getRegNo(request));
		mfBusFincApp.setPactId(mfBusPact.getPactId());
		mfBusFincApp.setChargeInterest(0);
		mfBusFincApp.setChargeFee(0);
		mfBusFincApp.setIntstEndDate(mfBusPact.getEndDate());
		//获取合同下此次融资申请的应收账款列表，统计汇总金额

		mfBusFincApp.setPutoutAmt(mfBusFincAppMain.getFincAmt());
		getObjValue(formbatchfincapprovalbase, mfBusFincApp);
		mfBusPact.setPutoutAmt(mfBusFincApp.getPutoutAmt());
		mfBusPact.setFincRate(mfBusFincAppMain.getFincRate());
		mfBusPact.setRateType(mfBusFincAppMain.getRateType());
		mfBusPact.setOverRate(mfBusFincAppMain.getOverRate());
		mfBusPact.setRepayType(mfBusFincAppMain.getRepayType());
		mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
		getObjValue(formbatchfincapprovalbase, mfBusPact);

		CodeUtils codeUtils = new CodeUtils();
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = codeUtils.getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
		this.changeFormProperty(formbatchfincapprovalbase, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formbatchfincapprovalbase, "overRate", "unit", rateUnit);
		this.changeFormProperty(formbatchfincapprovalbase, "cmpdRate", "unit", rateUnit);

		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", taskAppro.getActivityName());// 当前节点编号
		List<OptionsList> opinionTypeList = codeUtils.getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formbatchfincapprovalbase, "opinionType", "optionArray", opinionTypeList);

		//还款方式
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(mfBusPact.getAppId());
		mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
		JSONArray repayTypeMap = prdctInterfaceFeign.filterDictoryData(mfBusAppKind.getRepayType(), "REPAY_TYPE");
		JSONObject json = new JSONObject();
		json.put("repayTypeMap", repayTypeMap);

		//获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));

		model.addAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("appId", mfBusFincAppMain.getAppId());
		model.addAttribute("cusNo", mfBusPact.getCusNo());
		model.addAttribute("pactId", mfBusPact.getPactId());
		model.addAttribute("fincMainId", fincMainId);
		model.addAttribute("ajaxData", json.toString());
		model.addAttribute("nodeNo", taskAppro.getActivityName());
		model.addAttribute("query", "");
		model.addAttribute("formbatchfincapprovalbase", formbatchfincapprovalbase);
		model.addAttribute("mfBusFincAppList", mfBusFincAppList);
		return "/component/pact/receaccount/FincBatchWkfViewPoint";
	}



	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitUpdateForBatchAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateForBatchAjax(String ajaxData, String taskId, String appId, String fincMainId, String transition, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		dataMap = getMapByJson(ajaxData);
		FormData formreceFincApproval = formService.getFormData((String) dataMap.get("formId"));
		getFormValue(formreceFincApproval, dataMap);
		setObjValue(formreceFincApproval, mfBusFincApp);
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
		try {
			dataMap.put("mfBusFincApp", mfBusFincApp);
			Result res = mfBusFincAppMainFeign.doCommitForBatch(taskId, appId,fincMainId, opinionType, approvalOpinion, transition, User.getRegNo(request), nextUser, dataMap);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					// 审批通过之后提交业务到下一个阶段
					MfBusPact mfBusPact = new MfBusPact();
					mfBusPact.setAppId(appId);
					mfBusPact = mfBusPactFeign.getById(mfBusPact);
					Result result = wkfBusInterfaceFeign.doCommitNextStepWithUser(mfBusPact.getAppId(),mfBusPact.getWkfAppId(),User.getRegNo(request));
					if (!result.isSuccess()) {
						dataMap.put("flag", "error");
					}
					dataMap.put("msg", result.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
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
	 * 根据
	 * @param ajaxData
	 * @param pactId
	 * @param fincMainId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/generateFincAppListAjax")
	@ResponseBody
	public Map<String,Object> generateFincAppListAjax(String ajaxData,String pactId,String fincMainId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		dataMap.put("flag", "success");
		try {
			Map map = getMapByJson(ajaxData);
			FormData formrecefincappbase = formService.getFormData((String) map.get("formId"));
			getFormValue(formrecefincappbase, map);
			if (this.validateFormData(formrecefincappbase)) {
				//合同信息
				MfBusPact mfBusPact = new MfBusPact();
				mfBusPact.setPactId(pactId);
				mfBusPact = mfBusPactFeign.getById(mfBusPact);
				MfBusFincApp mfBusFincApp = new MfBusFincApp();
				setObjValue(formrecefincappbase, mfBusFincApp);
				if(mfBusPact.getUsableFincAmt()==0.00){
					dataMap.put("flag", "error");
					Map<String,String> parmMap = new HashMap<String,String>();
					parmMap.put("timeOne","可支用金额");
					parmMap.put("value","零");
					dataMap.put("msg", MessageEnum.NOT_EQUAL_TIME.getMessage(parmMap));
				}else if(mfBusFincApp.getUsableFincAmt()>mfBusPact.getUsableFincAmt()){//如果申请金额大于可融资金额
					dataMap.put("flag", "error");
					Map<String,String> parmMap = new HashMap<String,String>();
					parmMap.put("timeOne","申请金额");
					parmMap.put("timeTwo","可支用金额");
					dataMap.put("msg", MessageEnum.NOT_FORM_TIME.getMessage(parmMap));
				}else{
					mfBusFincApp.setFincMainId(fincMainId);
					mfBusFincApp.setPactId(pactId);
					map.put("mfBusFincApp",mfBusFincApp);
					dataMap = mfBusFincAppMainFeign.generateFincAppList(map);
					if("success".equals(dataMap.get("flag"))){
						//获取此次放款申请的明细列表
						mfBusFincApp = new MfBusFincApp();
						mfBusFincApp.setFincMainId(fincMainId);
						List<MfBusFincApp> list = mfBusFincAppFeign.getByFincMainId(mfBusFincApp);
						Double usableFincAmtT =0.00;
						if(list!=null && list.size()>0){
							for(MfBusFincApp fincApp:list){
								usableFincAmtT = usableFincAmtT + fincApp.getPutoutAmt();
							}
						}
						JsonTableUtil jtu = new JsonTableUtil();
						String tableHtml = jtu.getJsonStr("tablefincAppBatchListBase", "tableTag", list, null, true);
						dataMap.put("htmlStr", tableHtml);
						Double usableFincAmt = mfBusPact.getUsableFincAmt() - usableFincAmtT;
						dataMap.put("usableFincAmt",usableFincAmt);

						dataMap.put("planPutoutCnt", list.size());// 用于回写页面的'计划放款笔数', 避免页面填写不规范
					}
				}

			}else{
					dataMap.put("flag", "error");
					dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;
	}


	/********************************************************************************/
	//                         标准产品批量放款申请、审批相关 end
	/********************************************************************************/




}
