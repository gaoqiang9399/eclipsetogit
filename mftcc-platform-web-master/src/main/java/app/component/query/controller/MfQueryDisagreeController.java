package app.component.query.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.app.entity.MfBusApplyHis;
import app.component.app.entity.MfBusReconsiderApply;
import app.component.app.feign.MfBusApplyFeign;
import app.component.app.feign.MfBusReconsiderApplyFeign;
import app.component.common.BizPubParm;
import app.component.prdct.entity.MfSysKind;
import app.component.wkf.AppConstant;
import app.component.wkfBusInterface.WkfBusInterfaceFeign;
import cn.mftcc.util.StringUtil;
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
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.auth.entity.MfCusCreditApply;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calcinterface.CalcInterfaceFeign;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.examinterface.ExamInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.query.feign.MfQueryDisagreeFeign;
import app.component.sys.entity.SysUser;
import app.component.sysextendinterface.SysExtendInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 否决业务查询
 * 
 * @author WangChao
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("/mfQueryDisagree")
public class MfQueryDisagreeController extends BaseFormBean {

	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private CalcInterfaceFeign calcInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
	@Autowired
	private SysExtendInterfaceFeign sysExtendInterfaceFeign;
	@Autowired
	private ExamInterfaceFeign examInterfaceFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfQueryDisagreeFeign mfQueryDisagreeFeign;
	@Autowired
	private WkfBusInterfaceFeign wkfBusInterfaceFeign;

	@Autowired
    private MfBusReconsiderApplyFeign   mfBusReconsiderApplyFeign;

    @Autowired
    private MfBusApplyFeign mfBusApplyFeign;

	/**
	 * 查询列表页面
	 * 
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-8-2 下午6:36:26
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/query/queryDisagree_getListPage";
	}

	/**
	 * 列表打开页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getReconListPage")
	public String getReconListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/query/queryDisagree_reconListPage";
	}
	/**
	 * 列表页面数据
	 * 
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-8-3 上午11:22:03
	 */
	@RequestMapping(value = "/findDisagreeListByPage")
	@ResponseBody
	public Map<String, Object> findDisagreeListByPage(Integer pageNo, Integer pageSize, String tableId,
			String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);

		MfBusApply apply = new MfBusApply();
		apply.setCustomQuery(ajaxData);// 自定义查询参数赋值
		apply.setCustomSorts(ajaxData);// 自定义排序参数赋值
		apply.setCriteriaList(apply, ajaxData);// 我的筛选

		Ipage ipage = this.getIpage();
		ipage.setPageSize(pageSize);
		ipage.setPageNo(pageNo);// 异步传页面翻页参数
		// 自定义查询Bo方法
		ipage.setParams(this.setIpageParams("mfBusApply",apply));
		ipage = mfQueryDisagreeFeign.findDisagreeByPage(ipage);
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
		ipage.setResult(tableHtml);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("ipage", ipage);
		dataMap.put("flag", "success");

		return dataMap;
	}

	/**
	 * 发起复议
	 * 
	 * @return
	 * @author WangChao
	 * @date 2017-8-3 上午11:22:18
	 */
	@RequestMapping(value = "/disagreeReconsider")
	@ResponseBody
	public Map<String, Object> disagreeReconsider(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
            mfBusApply.setCurrentSessionOrgNo(User.getOrgNo(request));
            dataMap = mfQueryDisagreeFeign.doDisagreeReconsider(mfBusApply);
            if(dataMap.get("result") != null && BizPubParm.YES_NO_Y.equals(dataMap.get("result"))){
                dataMap.put("msg", dataMap.get("msg"));
                dataMap.put("flag", "success");
            }else{
                dataMap.put("msg", MessageEnum.ERROR_SERVER);
                dataMap.put("flag", "error");
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_SERVER);
            throw e;
        }
        return dataMap;
    }


	/**
	 * 进入否决复议审批视角（审批页面）
	 * 
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-8-3 下午2:15:19
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String reconsiderId,String hideOpinionType, String activityType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);

		TaskImpl taskAppro = wkfInterfaceFeign.getTask(reconsiderId, null);// 当前审批节点task
		String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		model.addAttribute("scNo", scNo);
		model.addAttribute("nodeNo", nodeNo);
		MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setReconsiderId(reconsiderId);
        mfBusApply = appInterfaceFeign.getMfBusApply(mfBusApply);
		mfBusApply.setReplyFincRate(mfBusApply.getFincRate());
		mfBusApply = appInterfaceFeign.processDataForApply(mfBusApply);
		
		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.disagree_reconsider, mfBusApply.getAppId(),
				null, User.getRegNo(request));
		FormData form = formService.getFormData(formId);
		model.addAttribute("formId", formId);

		Double appAmt1 = mfBusApply.getAppAmt();
		String termShow = mfBusApply.getTermShow();
		String cmpdRateType = mfBusApply.getCmpFltRateShow();
		MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
		mfBusApplyHis.setAppId(mfBusApply.getAppId());
		List<MfBusApplyHis> list = new ArrayList<MfBusApplyHis>();
		list = appInterfaceFeign.getApplyHisListByAppId(mfBusApplyHis);

		if (list != null && list.size() > 0) {
			mfBusApplyHis = list.get(0);
			PropertyUtils.copyProperties(mfBusApply, mfBusApplyHis);
		}

		mfBusApply.setAppAmt1(appAmt1);
		mfBusApply.setTermShow(termShow);
		mfBusApply.setCmpFltRateShow(cmpdRateType);

		// 获得该申请相关的费用标准信息
		List<MfBusAppFee> mfBusAppFeeList = calcInterfaceFeign.getFeeItemList(mfBusApply.getAppId(), null, null);
		model.addAttribute("mfBusAppFeeList", mfBusAppFeeList);
		JSONObject json = new JSONObject();
		// 操作员
		List<SysUser> userList = sysExtendInterfaceFeign.getAllUsers();
		JSONArray userJsonArray = new JSONArray();
		for (int i = 0; i < userList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", userList.get(i).getOpNo());
			jsonObject.put("name", userList.get(i).getOpName());
			userJsonArray.add(jsonObject);
		}
		json.put("userJsonArray", userJsonArray);

		JSONArray map = new CodeUtils().getJSONArrayByKeyName("VOU_TYPE");
		for (int i = 0; i < map.size(); i++) {
			map.getJSONObject(i).put("id", map.getJSONObject(i).getString("optCode"));
			map.getJSONObject(i).put("name", map.getJSONObject(i).getString("optName"));
		}
		json.put("map", map);
		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		// 获得客户的授信额度信息
		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		mfCusCreditApply.setCusNo(mfBusApply.getCusNo());
		mfCusCreditApply = creditApplyInterfaceFeign.getByCusNoAndOrederFirst(mfCusCreditApply);
		Double tmpBal = 0.00;
		if (mfCusCreditApply != null) {
			tmpBal = mfCusCreditApply.getCreditSum();
		}

		// 获取核心企业授信可用额度
		mfCusCreditApply = new MfCusCreditApply();
		mfCusCreditApply.setCusNo(mfBusApply.getCusNoCore());
		mfCusCreditApply = creditApplyInterfaceFeign.getByCusNoAndOrederFirst(mfCusCreditApply);
		if (mfCusCreditApply == null) {
			mfCusCreditApply = new MfCusCreditApply();
			mfCusCreditApply.setAuthBal(0.00);
		} else {
			mfCusCreditApply.setAuthBal(mfCusCreditApply.getCreditSum());
		}
		mfCusCreditApply.setCreditSum(tmpBal);
		getObjValue(form, mfCusCreditApply);
		getObjValue(form, mfBusApply);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		opinionTypeMap.put("opinionType","APPLY_OPINION_TYPE");
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(form, "opinionType", "optionArray", opinionTypeList);

		// 添加贷后检查模型数据
		List<OptionsList> examList = examInterfaceFeign.getConfigMatchedByBussList(mfBusApply.getAppId(), "apply");
		this.changeFormProperty(form, "templateId", "optionArray", examList);

		// 处理期限的展示单位。
		Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
		String termUnit = termTypeMap.get(mfBusApply.getTermType()).getRemark();
		this.changeFormProperty(form, "term", "unit", termUnit);

		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
		this.changeFormProperty(form, "fincRate", "unit", rateUnit);
		this.changeFormProperty(form, "overRate", "unit", rateUnit);
		this.changeFormProperty(form, "cmpdRate", "unit", rateUnit);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes(reconsiderId, User.getRegNo(request));

		model.addAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("mfBusApply", mfBusApply);
		model.addAttribute("form", form);
		model.addAttribute("query", "");
		return "/component/query/queryDisagree_viewPoint";
	}

	/**
	 * 否决复议审批提交（审批意见保存）
	 * 
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-8-3 下午2:16:01
	 */
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String ajaxDataList, String appId, String taskId,
			String nextUser, String transition) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfBusApply busApply = new MfBusApply();
		busApply.setAppId(appId);
		busApply = appInterfaceFeign.getMfBusApply(busApply);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		FormData form = formService.getFormData((String) map.get("formId"));
		getFormValue(form, map);
		MfBusApply mfBusApply = new MfBusApply();
		setObjValue(form, mfBusApply);
		dataMap = getMapByJson(ajaxData);
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
		JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
		List<MfBusAppFee> mfBusAppFeeList = (List<MfBusAppFee>) JSONArray.toList(jsonArray, new MfBusAppFee(),
				new JsonConfig());

		Result res;
		try {
			dataMap.put("orgNo", User.getOrgNo(request));
			mfBusApply = appInterfaceFeign.disProcessDataForApply(mfBusApply);
			dataMap.put("mfBusApply", mfBusApply);
			dataMap.put("mfBusAppFeeList", mfBusAppFeeList);
			res = mfQueryDisagreeFeign.doCommit(taskId, busApply.getReconsiderId(), opinionType, approvalOpinion, transition,User.getRegNo(this.getHttpRequest()), nextUser, dataMap);
			if(res.isSuccess()){
				dataMap.put("flag", "success");
				if(res.isEndSts()){
					// 业务审批通过之后提交业务到下一个阶段，此处代码为 app.component.app.feign.impl.MfBusApplyBoImpl.submitApplyApprovalPass业务中迁出
					Result result = wkfBusInterfaceFeign.doCommitNextStepWithOpNo(busApply.getAppId(),busApply.getWkfAppId(),User.getRegNo(request));
					if (!result.isSuccess()) {
						dataMap.put("flag", "error");
					}
					dataMap.put("msg", result.getMsg());
				}else{
					dataMap.put("msg", res.getMsg());
				}
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",res.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
			throw e;
		}
		return dataMap;

	}

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
		MfBusApply mfBusApply = new MfBusApply();
		try {
			mfBusApply.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusApply.setCriteriaList(mfBusApply, ajaxData);//我的筛选
			mfBusApply.setSignSts(AppConstant.OPINION_TYPE_SUGGESTREFUSE);
			mfBusApply.setAppSts(BizPubParm.APP_STS_PROCESS);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusApply",mfBusApply));
			ipage = mfQueryDisagreeFeign.findByPage(ipage);
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

}
