package app.component.pact.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.app.entity.MfBusApply;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.app.feign.MfBusApplyFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusPact;
import app.component.pact.entity.MfBusPactChange;
import app.component.pact.entity.MfBusPactChangeHis;
import app.component.pact.feign.MfBusPactChangeFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import net.sf.json.JSONArray;

/**
 * Title: MfBusPactChangeController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Aug 14 14:38:18 CST 2018
 **/
@Controller
@RequestMapping(value ="/mfBusPactChange")
public class MfBusPactChangeController extends BaseFormBean{

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusPactChangeFeign mfBusPactChangeFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private MfBusApplyFeign mfBusApplyFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;

	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId,
								 String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusPactChange mfBusPactChange = new MfBusPactChange();
		try {
			mfBusPactChange.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusPactChange.setCriteriaList(mfBusPactChange, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusPactChange",mfBusPactChange));
			ipage = mfBusPactChangeFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}


	/**
	 * AJAX新增
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
			FormData formpactchangePactBase = formService.getFormData("changePactBase");
			getFormValue(formpactchangePactBase, getMapByJson(ajaxData));
			if (this.validateFormData(formpactchangePactBase)) {
				MfBusPactChange mfBusPactChange = new MfBusPactChange();
				setObjValue(formpactchangePactBase, mfBusPactChange);
				dataMap = mfBusPactChangeFeign.insert(mfBusPactChange);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
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
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(Model model, String changePactId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formchangePactBase = formService.getFormData("changePactBase");
		MfBusPactChange mfBusPactChange = new MfBusPactChange();
		mfBusPactChange.setPactId(changePactId);
		mfBusPactChange = mfBusPactChangeFeign.getById(mfBusPactChange);
		getObjValue(formchangePactBase, mfBusPactChange, formData);
		if (mfBusPactChange != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		dataMap.put("beginDate", mfBusPactChange.getBeginDate());
		dataMap.put("endDate", mfBusPactChange.getEndDate());
		dataMap.put("termShow", mfBusPactChange.getTermShow());
		return dataMap;
	}

	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		String formId = "changePactInit";
		FormData formchangePactBase = formService.getFormData(formId);
		model.addAttribute("formchangePactBase",formchangePactBase);
		model.addAttribute("query","");
		return "/component/pact/MfBusPactChange_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String changePactId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formchangePactBase = formService.getFormData("changePactBase");
		getFormValue(formchangePactBase);
		MfBusPactChange mfBusPactChange = new MfBusPactChange();
		mfBusPactChange.setChangePactId(changePactId);
		mfBusPactChange = mfBusPactChangeFeign.getById(mfBusPactChange);
		getObjValue(formchangePactBase, mfBusPactChange);
		return "MfBusPactChange_Detail";
	}

	/**
	 * 列表打开页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/pact/MfBusPactChange_List";
	}

	@ResponseBody
	@RequestMapping(value = "/getPactList")
	public Map<String, Object> getPactList(int pageNo,String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusPact mfBusPact = new MfBusPact();
			mfBusPact.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusPact.setCustomSorts(ajaxData);
			mfBusPact.setCriteriaList(mfBusPact, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusPact",mfBusPact));
			ipage = mfBusPactChangeFeign.getPactList(ipage);
			ipage.setParamsStr(ajaxData);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@ResponseBody
	@RequestMapping(value = "/getPactChangeApply")
	public Map<String,Object> getPactChangeApply(String pactNo) throws  Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		MfBusPactChange mfBusPactChange = new MfBusPactChange();
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		MfBusPact mfBusPact = new MfBusPact();
		try{
			mfBusPact.setPactNo(pactNo);
			mfBusPact = mfBusPactFeign.getById(mfBusPact);
			mfBusAppKind.setAppId(mfBusPact.getAppId());
			mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
			mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);
			String pactId = mfBusPact.getPactId();
			Object coborrNum = cusInterfaceFeign.getCobBorrower(mfBusPact.getCusNo()).toString();
			String formId = prdctInterfaceFeign.getFormId(BizPubParm.pact_change_type, WKF_NODE.pact_change_apply, null, null, User.getRegNo(request));
			FormData formpactChangeBase = formService.getFormData(formId);
			PropertyUtils.copyProperties(mfBusPactChange, mfBusPact);
			getObjValue(formpactChangeBase, mfBusPact);
			getObjValue(formpactChangeBase, mfBusPactChange);
			// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
			Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
			String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
			this.changeFormProperty(formpactChangeBase, "fincRate", "unit", rateUnit);
			this.changeFormProperty(formpactChangeBase, "overRate", "unit", rateUnit);
			this.changeFormProperty(formpactChangeBase, "cmpdRate", "unit", rateUnit);

			this.changeFormProperty(formpactChangeBase, "fincRateChange", "unit", rateUnit);
			this.changeFormProperty(formpactChangeBase, "overRateChange", "unit", rateUnit);
			this.changeFormProperty(formpactChangeBase, "cmpdRateChange", "unit", rateUnit);

			List<OptionsList> repayTypeList = mfBusApplyFeign.getRepayTypeList(mfBusAppKind.getRepayType(),mfBusAppKind.getRepayTypeDef());
			this.changeFormProperty(formpactChangeBase, "repayTypeChange", "optionArray", repayTypeList);

			//处理金额，double大于十位会变成科学计数法11111111
			DecimalFormat df = new DecimalFormat(",##0.00");
			if((mfBusAppKind.getMinAmt()!=null)&&(mfBusAppKind.getMaxAmt()!=null)){
				double minAmtD = MathExtend.subtract(mfBusPact.getPactAmt(),mfBusPact.getUsableFincAmt());
				double maxAmtD = mfBusPact.getPactAmt();
				String minAmt = df.format(minAmtD);//融资金额下限
				String maxAmt = df.format(maxAmtD);//融资金额上限
				String amt = minAmt+"-"+maxAmt+"元";
				this.changeFormProperty(formpactChangeBase,"pactAmtChange","alt",amt);
				dataMap.put("minAmt", String.valueOf(minAmtD));
				dataMap.put("maxAmt", String.valueOf(maxAmtD));
			}
			//处理期限
			if(null != mfBusAppKind.getTermType()){
				String termType = mfBusAppKind.getTermType();//合同期限 1月 2日
				int minTerm = mfBusAppKind.getMinTerm();//合同期限下限
				int maxTerm = mfBusAppKind.getMaxTerm();//合同期限上限
				String term="";
				if("1".equals(termType)){
					term = minTerm+"个月-"+maxTerm+"个月";
				}else if("2".equals(termType)){
					term = minTerm+"天-"+maxTerm+"天";
				}else {
				}
				dataMap.put("minTerm", String.valueOf(minTerm));
				dataMap.put("maxTerm", String.valueOf(maxTerm));
				dataMap.put("termType", termType);
				this.changeFormProperty(formpactChangeBase, "termChange","alt",term);
			}
			//利率范围
			if((mfBusAppKind.getMinFincRate()!=null)&&(mfBusAppKind.getMaxFincRate()!=null)){
				double minFincRateD = MathExtend.showRateMethod(mfBusAppKind.getRateType(),mfBusAppKind.getMinFincRate(),Integer.parseInt(mfBusAppKind.getYearDays()),Integer.parseInt(mfBusAppKind.getRateDecimalDigits()));
				double maxFincRateD = MathExtend.showRateMethod(mfBusAppKind.getRateType(),mfBusAppKind.getMaxFincRate(),Integer.parseInt(mfBusAppKind.getYearDays()),Integer.parseInt(mfBusAppKind.getRateDecimalDigits()));
				String minFincRate = df.format(minFincRateD);//融资利率下限
				String maxFincRate = df.format(maxFincRateD);//融资利率上限
				String fincRate = minFincRate+"-"+maxFincRate+rateUnit;
				this.changeFormProperty(formpactChangeBase,"fincRateChange","alt",fincRate);
				dataMap.put("minFincRate", String.valueOf(minFincRateD));
				dataMap.put("maxFincRate", String.valueOf(maxFincRateD));
			}


			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String htmlStr = jsonFormUtil.getJsonStr(formpactChangeBase,"bootstarpTag","");
			dataMap.put("flag","success");
			dataMap.put("kindNo",mfBusPact.getKindNo());
			dataMap.put("htmlStr",htmlStr);
		}catch (Exception e){
			dataMap.put("flag","error");
		}
		return dataMap;
	}


	/**
	 * 变更合同审批页面
	 * @param model
	 * @param pactChangeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model,String changePactId,String activityType,String hideOpinionType)throws Exception{

		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		MfBusPactChange mfBusPactChange = new MfBusPactChange();
		mfBusPactChange.setChangePactId(changePactId);
		mfBusPactChange = mfBusPactChangeFeign.getById(mfBusPactChange);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(changePactId, null);// 当前审批节点task
		String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号

		MfBusPactChangeHis mfBusPactChangeHis = new MfBusPactChangeHis();
		mfBusPactChangeHis.setChangePactId(changePactId);
		List<MfBusPactChangeHis> list = new ArrayList<MfBusPactChangeHis>();
		list = mfBusPactChangeFeign.getMfBusPactChangeHisByDESC(mfBusPactChangeHis);
		if (list != null && list.size() > 0) {
			mfBusPactChangeHis = list.get(0);
			PropertyUtils.copyProperties(mfBusPactChange, mfBusPactChangeHis);
		}
		// 处理原始的数据（利率转换）
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(mfBusPactChange.getPactId());
		mfBusPact = mfBusPactFeign.getById(mfBusPact);

		mfBusPactChange = mfBusPactChangeFeign.processDataForPact(mfBusPactChange);

		mfBusPact.setFincRate(mfBusPactChange.getFincRateChange());
		mfBusPact.setOverFloat(mfBusPactChange.getOverFloatChange());
		mfBusPact.setOverRate(mfBusPactChange.getOverRateChange());
		mfBusPact.setCmpdFloat(mfBusPactChange.getCmpdFloatChange());
		mfBusPact.setCmpdRate(mfBusPactChange.getCmpdRateChange());
		mfBusPact = mfBusPactFeign.processDataForPact(mfBusPact);

		mfBusPactChange.setFincRateChange(mfBusPact.getFincRate());
		mfBusPactChange.setOverFloatChange(mfBusPact.getOverFloat());
		mfBusPactChange.setOverRateChange(mfBusPact.getOverRate());
		mfBusPactChange.setCmpdFloatChange(mfBusPact.getCmpdFloat());
		mfBusPactChange.setCmpdRateChange(mfBusPact.getCmpdRate());

		String formId = prdctInterfaceFeign.getFormId(BizPubParm.pact_change_type,WKF_NODE.pact_change_approve,null, null, User.getRegNo(request));

		FormData formchangePact = formService.getFormData(formId);

		getObjValue(formchangePact, mfBusPact);
		getObjValue(formchangePact, mfBusPactChange);
		// 处理期限的展示单位。
		Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
		String termUnit = termTypeMap.get(mfBusPact.getTermType()).getRemark();
		this.changeFormProperty(formchangePact, "term", "unit", termUnit);
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位,月利率百分号
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusPact.getRateType()).getRemark();
		this.changeFormProperty(formchangePact, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formchangePact, "fincRateShow", "unit", rateUnit);
		this.changeFormProperty(formchangePact, "overRate", "unit", rateUnit);
		this.changeFormProperty(formchangePact, "cmpdRate", "unit", rateUnit);

		this.changeFormProperty(formchangePact, "fincRateChange", "unit", rateUnit);
		this.changeFormProperty(formchangePact, "overRateChange", "unit", rateUnit);
		this.changeFormProperty(formchangePact, "cmpdRateChange", "unit", rateUnit);

		mfBusAppKind.setAppId(mfBusPact.getAppId());
		mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
		List<OptionsList> repayTypeList = mfBusApplyFeign.getRepayTypeList(mfBusAppKind.getRepayType(),mfBusAppKind.getRepayTypeDef());
		this.changeFormProperty(formchangePact, "repayTypeChange", "optionArray", repayTypeList);

		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formchangePact, "opinionType", "optionArray", opinionTypeList);

		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskAppro.getId(), User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("formchangePact", formchangePact);
		model.addAttribute("changePactId", changePactId);
		model.addAttribute("mfBusPactChange", mfBusPactChange);
		model.addAttribute("taskId", taskAppro.getId());
		model.addAttribute("query", "");
		model.addAttribute("formId", formId);
		model.addAttribute("nodeNo", nodeNo);
		return "/component/pact/MfBusPactChangeView";
	}


	@RequestMapping("/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String changePactId, String ajaxDataList, String taskId,
												String transition, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusPactChange mfBusPactChangeTmp = new MfBusPactChange();
		mfBusPactChangeTmp.setChangePactId(changePactId);
		mfBusPactChangeTmp = mfBusPactChangeFeign.getById(mfBusPactChangeTmp);
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(mfBusPactChangeTmp.getPactId());
		mfBusPact = mfBusPactFeign.getById(mfBusPact);

		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData formpactChangeBase = formService.getFormData(formId);
		MfBusPactChange mfBusPactChange = new MfBusPactChange();
		getFormValue(formpactChangeBase, map);
		setObjValue(formpactChangeBase, mfBusPactChange);
		mfBusPactChange = mfBusPactChangeFeign.disProcessDataForPact(mfBusPactChange);
		mfBusPact.setFincRate(mfBusPactChange.getFincRateChange());
		mfBusPact.setOverFloat(mfBusPactChange.getOverFloatChange());
		mfBusPact.setOverRate(mfBusPactChange.getOverRateChange());
		mfBusPact.setCmpdFloat(mfBusPactChange.getCmpdFloatChange());
		mfBusPact.setCmpdRate(mfBusPactChange.getCmpdRateChange());
		mfBusPact = mfBusPactFeign.disProcessDataForPact(mfBusPact);
		mfBusPactChange.setFincRateChange(mfBusPact.getFincRate());
		mfBusPactChange.setOverFloatChange(mfBusPact.getOverFloat());
		mfBusPactChange.setOverRateChange(mfBusPact.getOverRate());
		mfBusPactChange.setCmpdFloatChange(mfBusPact.getCmpdFloat());
		mfBusPactChange.setCmpdRateChange(mfBusPact.getCmpdRate());
		try {
			dataMap = getMapByJson(ajaxData);
			String opinionType = String.valueOf(dataMap.get("opinionType"));
			String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusPactChange);
			dataMap.put("mfBusPactChange", mfBusPactChange);
			Result res = mfBusPactChangeFeign.doCommit(taskId, changePactId, opinionType, approvalOpinion, transition,
					User.getRegNo(request), nextUser, dataMap);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", res.getMsg());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", res.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;

	}

	@RequestMapping(value = "/getChangevalueAjax")
	@ResponseBody
	public Map<String, Object> getChangevalueAjax(String pactId,Double fincRateChange,String termType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String,Object>();
		MfBusPactChange mfBusPactChangeTmp = new MfBusPactChange();
		try {
			mfBusPactChangeTmp = mfBusPactChangeFeign.onfincRatechang(pactId,fincRateChange,termType);
			dataMap.put("mfBusPactChangeTmp",mfBusPactChangeTmp);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("msg",e.getMessage());
			throw e;
		}
		return dataMap;
	}
}
