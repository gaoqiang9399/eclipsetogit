
package app.component.collateral.controller;

import app.base.User;
import app.base.cacheinterface.BusiCacheInterface;
import app.component.accnt.entity.MfAccntRepayDetail;
import app.component.accnt.feign.MfAccntRepayDetailFeign;
import java.lang.reflect.Field;
import app.component.alliance.feign.MfBusAllianceFeign;
import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.component.app.entity.MfBusApplyHis;
import app.component.app.entity.MfBusApplySecond;
import app.component.app.feign.MfBusApplyFeign;
import app.component.app.feign.MfBusApplyHisFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.assure.entity.MfAssureInfo;
import app.component.assure.feign.MfAssureInfoFeign;
import app.component.assureinterface.AssureInterfaceFeign;
import app.component.auth.entity.MfCusCreditAdjustApply;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCusCreditConfig;
import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.feign.MfCusCreditAdjustApplyFeign;
import app.component.auth.feign.MfCusCreditApplyFeign;
import app.component.auth.feign.MfCusCreditConfigFeign;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.channel.feign.MfChannelBusFeign;
import app.component.channel.fund.entity.MfFundChannelFinc;
import app.component.collateral.carcheck.entity.MfCarCheckForm;
import app.component.collateral.carcheck.feign.MfCarCheckFormFeign;
import app.component.collateral.entity.KeepInfo;
import app.component.collateral.entity.MfCollateralTable;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.entity.EvalInfo;
import app.component.collateral.entity.MfCollateralClass;
import app.component.collateral.entity.MfPleRepoApply;
import app.component.collateral.entity.MfBusCollateralRel;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.entity.FairInfo;
import app.component.collateral.entity.CertiInfo;
import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.collateral.entity.CollateralConstant;
import app.component.collateral.entity.PledgeBaseInfoBill;
import app.component.collateral.entity.InsInfo;
import app.component.collateral.entity.ChkInfo;
import app.component.collateral.entity.MfCollateralInsuranceClaims;
import app.component.collateral.feign.EvalInfoFeign;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.collateral.feign.MfBusCollateralRelFeign;
import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.collateral.feign.MfCollateralTableFeign;
import app.component.collateral.feign.FairInfoFeign;
import app.component.collateral.feign.InsInfoFeign;
import app.component.collateral.feign.CertiInfoFeign;
import app.component.collateral.feign.ChkInfoFeign;
import app.component.collateral.feign.MfCollateralClassFeign;
import app.component.collateral.feign.MfPleRepoApplyFeign;
import app.component.collateral.feign.MfCollateralInsuranceClaimsFeign;
import app.component.collateral.feign.PledgeBaseInfoBillFeign;
import app.component.collateral.maintenance.entity.MfMaintenance;
import app.component.collateral.maintenance.feign.MfMaintenanceFeign;
import app.component.collateral.movable.entity.MfMoveableCheckInventoryInfo;
import app.component.collateral.movable.feign.MfMoveableCheckInventoryInfoFeign;
import app.component.common.ApplyEnum;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.EntityUtil;
import app.component.coreservice.feign.SendToCoreFeign;
import app.component.cus.entity.*;
import app.component.cus.feign.MfBusGpsRegFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pact.extension.entity.MfBusExtensionApply;
import app.component.pact.extension.feign.MfBusExtensionApplyFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.pledge.entity.MfHighGuaranteeContract;
import app.component.pledge.feign.MfHighGuaranteeContractFeign;
import app.component.prdct.entity.MfKindFlow;
import app.component.prdct.entity.MfKindForm;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.rules.entity.NumberBigBean;
import app.component.rulesinterface.RulesInterfaceFeign;
import app.component.sys.entity.SysTaskInfo;
import app.component.sys.feign.SysTaskInfoFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.WorkflowDwrFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.DataUtil;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormActive;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.WFUtil;
import com.dhcc.workflow.api.task.Task;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.TreeMap;
import java.util.ListIterator;

/**
 * Title: MfBusCollateralRelController.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Wed Apr 12 14:37:16 CST 2017
 **/
@Controller
@RequestMapping("/mfBusCollateralRel")
public class MfBusCollateralRelController extends BaseFormBean {
	private Logger logger = LoggerFactory.getLogger(MfBusCollateralRelController.class);
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private BusiCacheInterface busiCacheInterface;
	@Autowired
	private MfBusCollateralRelFeign mfBusCollateralRelFeign;
	@Autowired
	private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private AssureInterfaceFeign assureInterfaceFeign;
	@Autowired
	private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;
	@Autowired
	private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;
	@Autowired
	private MfCollateralTableFeign mfCollateralTableFeign;
	@Autowired
	private FairInfoFeign fairInfoFeign;
	@Autowired
	private InsInfoFeign insInfoFeign;
	@Autowired
	private CertiInfoFeign certiInfoFeign;
	@Autowired
	private EvalInfoFeign evalInfoFeign;
	@Autowired
	private ChkInfoFeign chkInfoFeign;
	@Autowired
	private MfCollateralClassFeign mfCollateralClassFeign;
	@Autowired
	private MfPleRepoApplyFeign mfPleRepoApplyFeign;
	@Autowired
	private MfCarCheckFormFeign mfCarCheckFormFeign;
	@Autowired
	private MfBusGpsRegFeign mfBusGpsRegFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private RulesInterfaceFeign rulesInterfaceFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private PledgeBaseInfoBillFeign pledgeBaseInfoBillFeign;
	@Autowired
	private MfMoveableCheckInventoryInfoFeign mfMoveableCheckInventoryInfoFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private WorkflowDwrFeign workflowDwrFeign;
	@Autowired
	private MfAssureInfoFeign assureInfoFeign;
	@Autowired
	private MfCusCreditContractFeign mfCusCreditContractFeign;
	@Autowired
	private MfAccntRepayDetailFeign mfAccntRepayDetailFeign;
	@Autowired
	private MfCusCreditApplyFeign   mfCusCreditApplyFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private MfBusApplyFeign   mfBusApplyFeign;
	@Autowired
	private MfChannelBusFeign mfChannelBusFeign ;
	@Autowired
	private  MfBusPactFeign   mfBusPactFeign;
	@Autowired
	private  MfCusCreditAdjustApplyFeign   mfCusCreditAdjustApplyFeign;
	@Autowired
	private  MfMaintenanceFeign   mfMaintenanceFeign;
	@Autowired
	private  MfCollateralInsuranceClaimsFeign   mfCollateralInsuranceClaimsFeign;
	@Autowired
	private MfHighGuaranteeContractFeign mfHighGuaranteeContractFeign;
	@Autowired
	private MfBusApplyHisFeign mfBusApplyHisFeign;
	@Autowired
	private MfBusAllianceFeign mfBusAllianceFeign;
	@Autowired
	private MfBusExtensionApplyFeign mfBusExtensionApplyFeign;

	@Autowired
	private SendToCoreFeign sendToCoreFeign;
	@Autowired
	private SysTaskInfoFeign sysTaskInfoFeign;
	@Autowired
	private MfCusCreditConfigFeign mfCusCreditConfigFeign;

	private Gson gson = new Gson();
	/**
	 * 列表打开页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/collateral/MfBusCollateralRel_List";
	}

	/***
	 * 列表数据查询
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
															String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
		try {
			mfBusCollateralRel.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusCollateralRel.setCriteriaList(mfBusCollateralRel, ajaxData);// 我的筛选
			// this.getRoleConditions(mfBusCollateralRel,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = mfBusCollateralRelFeign.findByPage(ipage, mfBusCollateralRel);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
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
	@ResponseBody public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formbuscollateralrel0002 = formService.getFormData("buscollateralrel0002");
			getFormValue(formbuscollateralrel0002, getMapByJson(ajaxData));
			if (this.validateFormData(formbuscollateralrel0002)) {
				MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
				setObjValue(formbuscollateralrel0002, mfBusCollateralRel);
				mfBusCollateralRelFeign.insert(mfBusCollateralRel);
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
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formbuscollateralrel0002 = formService.getFormData("buscollateralrel0002");
		getFormValue(formbuscollateralrel0002, getMapByJson(ajaxData));
		MfBusCollateralRel mfBusCollateralRelJsp = new MfBusCollateralRel();
		setObjValue(formbuscollateralrel0002, mfBusCollateralRelJsp);
		MfBusCollateralRel mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRelJsp);
		if (mfBusCollateralRel != null) {
			try {
				mfBusCollateralRel = (MfBusCollateralRel) EntityUtil.reflectionSetVal(mfBusCollateralRel,
						mfBusCollateralRelJsp, getMapByJson(ajaxData));
				mfBusCollateralRelFeign.update(mfBusCollateralRel);
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
	@RequestMapping("/updateAjax")
	@ResponseBody public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formbuscollateralrel0002 = formService.getFormData("buscollateralrel0002");
			getFormValue(formbuscollateralrel0002, getMapByJson(ajaxData));
			if (this.validateFormData(formbuscollateralrel0002)) {
				MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
				setObjValue(formbuscollateralrel0002, mfBusCollateralRel);
				mfBusCollateralRelFeign.update(mfBusCollateralRel);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody public Map<String, Object> getByIdAjax(String busCollateralId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
		mfBusCollateralRel.setBusCollateralId(busCollateralId);
		mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
		if("".equals(mfBusCollateralRel.getCollateralAmt())||mfBusCollateralRel.getCollateralAmt()==null){
			mfBusCollateralRel.setCollateralAmt(0.00);
		}else{
			mfBusCollateralRel.setCollateralAmt(MathExtend.divide(mfBusCollateralRel.getCollateralAmt(), 10000,2));
		}
		if("".equals(mfBusCollateralRel.getCollateralRate())||mfBusCollateralRel.getCollateralRate()==null){
			mfBusCollateralRel.setCollateralRate(0.00);
		}else{
			Double collateralRate = MathExtend.multiply(mfBusCollateralRel.getCollateralRate(), 100);
			collateralRate= MathExtend.divide(collateralRate, 1, 2);
			if(collateralRate>100.0){
				collateralRate=100.0;
			}
			mfBusCollateralRel.setCollateralRate(collateralRate);
		}
		dataMap.put("collateralRel", mfBusCollateralRel);
		dataMap.put("flag", "success");
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 更新担保信息，并获得担保信息
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-6-29 下午8:30:11
	 */
	@RequestMapping("/getCollateralByUpdateAjax")
	@ResponseBody public Map<String, Object> getCollateralByUpdateAjax(String busCollateralId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
		mfBusCollateralRel.setBusCollateralId(busCollateralId);
		mfBusCollateralRel = mfBusCollateralRelFeign.getCollateralByUpdate(mfBusCollateralRel);
		if (mfBusCollateralRel != null) {
			Double collateralAmt = mfBusCollateralRelFeign.getAssureAmtShow(mfBusCollateralRel.getCollateralAmt(),
					mfBusCollateralRel.getAppId(),mfBusCollateralRel.getCollateralType());
			mfBusCollateralRel.setCollateralAmt(collateralAmt);
			String assureAmtShowType = new CodeUtils().getSingleValByKey("ASSURE_AMT_SHOW_TYPE");
			Double collateralRate = mfBusCollateralRel.getCollateralRate();
			if("4".equals(assureAmtShowType) || "5".equals(assureAmtShowType)){// 参数为5时;单人担保情况下若担保金额大于申请金额则展示申请金额，否则展示担保金额;多人担保下展示担保金额最大且不能超过申请金额的那条。否则展示申请金额
				collateralRate = mfBusCollateralRelFeign.getCollateralRate(mfBusCollateralRel);
				collateralRate = MathExtend.multiply(collateralRate, 100);
				mfBusCollateralRel.setCollateralRate(collateralRate);
			}
			dataMap.put("collateralRel", mfBusCollateralRel);
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
	@ResponseBody public Map<String, Object> deleteAjax(String ajaxData, String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
		mfBusCollateralRel.setId(id);
		try {
			mfBusCollateralRelFeign.delete(mfBusCollateralRel);
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
	 * 新增页面
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formbuscollateralrel0002 = formService.getFormData("buscollateralrel0002");
		model.addAttribute("formbuscollateralrel0002", formbuscollateralrel0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfBusCollateralRel_Insert";
	}

	/***
	 * 新增
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formbuscollateralrel0002 = formService.getFormData("buscollateralrel0002");
		getFormValue(formbuscollateralrel0002);
		MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
		setObjValue(formbuscollateralrel0002, mfBusCollateralRel);

		mfBusCollateralRelFeign.insert(mfBusCollateralRel);
		getObjValue(formbuscollateralrel0002, mfBusCollateralRel);
		this.addActionMessage(model, "保存成功");
		List<MfBusCollateralRel> mfBusCollateralRelList = (List<MfBusCollateralRel>) mfBusCollateralRelFeign
				.findByPage(this.getIpage(), mfBusCollateralRel).getResult();
		model.addAttribute("mfBusCollateralRelList", mfBusCollateralRelList);
		model.addAttribute("formbuscollateralrel0002", formbuscollateralrel0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfBusCollateralRel_Insert";
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
		FormData formbuscollateralrel0001 = formService.getFormData("buscollateralrel0001");
		getFormValue(formbuscollateralrel0001);
		MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
		mfBusCollateralRel.setId(id);
		mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
		if("".equals(mfBusCollateralRel.getCollateralAmt())||mfBusCollateralRel.getCollateralAmt()==null){
			mfBusCollateralRel.setCollateralAmt(0.00);
		}else{
			mfBusCollateralRel.setCollateralAmt(MathExtend.divide(mfBusCollateralRel.getCollateralAmt(), 10000,2));
		}
		if("".equals(mfBusCollateralRel.getCollateralRate())||mfBusCollateralRel.getCollateralRate()==null){
			mfBusCollateralRel.setCollateralRate(0.00);
		}else{
			Double collateralRate = MathExtend.multiply(mfBusCollateralRel.getCollateralRate(), 100);
			collateralRate= MathExtend.divide(collateralRate, 1, 2);
			if(collateralRate>100.0){
				collateralRate=100.0;
			}
			mfBusCollateralRel.setCollateralRate(collateralRate);
		}
		getObjValue(formbuscollateralrel0001, mfBusCollateralRel);
		model.addAttribute("formbuscollateralrel0001", formbuscollateralrel0001);
		model.addAttribute("query", "");
		return "/component/collateral/MfBusCollateralRel_Detail";
	}

	/**
	 * 删除
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String id) throws Exception {
		ActionContext.initialize(request, response);
		MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
		mfBusCollateralRel.setId(id);
		mfBusCollateralRelFeign.delete(mfBusCollateralRel);
		return getListPage();
	}

	/**
	 *
	 * 方法描述： 业务流程中登记押品节点 跳转押品登记页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-11 下午5:25:53
	 */
	@RequestMapping("/collateralBaseInput")
	public String collateralBaseInput(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlcollateralbase0004 = formService.getFormData("dlcollateralbase0004");
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		if (mfBusApply != null) {
			getObjValue(formdlcollateralbase0004, mfBusApply);
			model.addAttribute("formdlcollateralbase0004", formdlcollateralbase0004);
		}
		model.addAttribute("query", "");
		return "/component/collateral/CollateralBase_Insert";
	}

	/**
	 *
	 * 方法描述： 押品新增页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param formId
	 * @param classModel
	 * @param appNo 流程申请号(相当于mfBusApply.getWkfAppId()，工作流预留传值)，此功能业务流程及展期流程都在用，统一用此参数查询任务(Task)
	 * @date 2017-4-28 上午11:51:59
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertInput")
	public String insertInput(Model model, String cusNo, String entrFlag,
							  String appId, String extensionApplyId,String companyId, String formId, String classModel, String appNo,String ifSupportSkipFlag) throws Exception {
		ActionContext.initialize(request, response);
		String entrance = request.getParameter("entrance");
		if(StringUtil.isEmpty(ifSupportSkipFlag)){
			ifSupportSkipFlag = BizPubParm.YES_NO_N;
		}
		Gson gson = new Gson();
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> oldMap = new HashMap<String, Object>();//保后变更存放原申请信息
		String busModel = "";//业务模式
		JSONObject json = new JSONObject();
		MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
		dataMap.put("cusNo", mfCusCustomer.getCusNo());
		dataMap.put("cusName", mfCusCustomer.getCusName());
		String busEndDate = "";
		FormData formdlpledgebaseinfo0004 = null;
        String supportSkipFlag = BizPubParm.YES_NO_N;
		String alliance = "0";
		if (!"credit".equals(entrFlag) && !"credit".equals(entrance)) {
			entrance = "businesss";
			MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			model.addAttribute("appAmt",MathExtend.moneyStr(mfBusApply.getAppAmt()));
			model.addAttribute("kindNo",mfBusApply.getKindNo());
			model.addAttribute("mfBusApply", mfBusApply);
            busModel = mfBusApply.getBusModel();
            if (BizPubParm.TERM_TYPE_MONTH.equals(mfBusApply.getTermType())) {
				busEndDate = DateUtil.getShowDateTime(DateUtil.getDateStr(mfBusApply.getRegDate(), mfBusApply.getTerm()));
			} else if (BizPubParm.TERM_TYPE_DAY.equals(mfBusApply.getTermType())) {
				busEndDate = DateUtil.getShowDateTime(DateUtil.addByDay(mfBusApply.getRegDate(), mfBusApply.getTerm()));
			}else {
			}
			model.addAttribute("busEndDate", busEndDate);
			String[] vouTypeArray = mfBusApply.getVouType().split("\\|");
			String vouType = vouTypeArray[0];

			model.addAttribute("vouType", vouType);

			//意义相同，不同叫法的担保方式使用1.1
			String vouTypeShort = vouType.split("\\.")[0];
			model.addAttribute("vouTypeShort", vouTypeShort);
			if (BizPubParm.VOU_TYPE_1.equals(vouType)||BizPubParm.VOU_TYPE_1.equals(vouTypeShort)) {
				// 1.信用担保允许跳过担保信息节点
				supportSkipFlag = BizPubParm.YES_NO_Y;
			}
			Map<String,String> vouTypeMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
			if(vouTypeMap!=null){
				model.addAttribute("vouTypeName", vouTypeMap.get(vouType));
			}
			if (BizPubParm.VOU_TYPE_2.equals(vouTypeArray[0])) {// 如果第一个是保证担保方式，担保登记时默认展示保证信息登记表单
				dataMap.put("mfBusApply",mfBusApply);
				initAssureInfoForm(dataMap, vouTypeArray);
				formdlpledgebaseinfo0004 = (FormData) dataMap.get("formdlpledgebaseinfo0004");
				// 追加押品的时候，根据产品设置担保方式选择
				if ("collateral".equals(entrFlag)) {
					List<OptionsList> vouTypeList = new ArrayList<OptionsList>();
					Map<String, String> dicMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
					OptionsList op = new OptionsList();
					op.setOptionLabel(dicMap.get(vouType));
					op.setOptionValue(vouType);
					vouTypeList.add(op);
					// 追加押品的时候，根据产品设置担保方式选择
					MfBusAppKind mfBusAppKind = new MfBusAppKind();
					mfBusAppKind.setAppId(mfBusApply.getAppId());
					mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
					String vouType2 = mfBusAppKind.getVouType();
					vouTypeArray = vouType2.split("\\|");
					for (int i = 0; i < vouTypeArray.length; i++) {
						if (!BizPubParm.VOU_TYPE_1.equals(vouTypeArray[i])&&!BizPubParm.VOU_TYPE_1.equals(vouTypeArray[i].split("\\.")[0])) {
							if (vouTypeArray[i].equals(vouType)) {
								continue;
							}
							op = new OptionsList();
							op.setOptionLabel(dicMap.get(vouTypeArray[i]));
							op.setOptionValue(vouTypeArray[i]);
							op.setOptionId(WaterIdUtil.getWaterId());
							vouTypeList.add(op);
						}
					}

					//保后变更时，带出原申请担保信息
					/*if(mfBusApply!=null&&"1".equals(mfBusApply.getChangeFlag())){
						List<MfAssureInfo> list  =  mfBusCollateralRelFeign.getAssureListByAppid(mfBusApply.getAppIdOld());
						if(list!=null&&list.size()>0){
							MfAssureInfo mfAssureInfo = list.get(0);
							getObjValue(formdlpledgebaseinfo0004, mfAssureInfo);
						}
					}*/
					this.changeFormProperty(formdlpledgebaseinfo0004 , "pledgeMethod", "optionArray", vouTypeList);
				}
				// 默认证件类型
			} else if(BizPubParm.VOU_TYPE_10.equals(vouTypeArray[0])){//担保类型为：最高额担保合同
				dataMap.put("mfBusApply",mfBusApply);
				formdlpledgebaseinfo0004 = formService.getFormData("highGrtContract0001");
				MfHighGuaranteeContract mfHighGuaranteeContract = new MfHighGuaranteeContract();
				mfHighGuaranteeContract.setCusNo(mfCusCustomer.getCusNo());
				mfHighGuaranteeContract.setCusName(mfCusCustomer.getCusName());
				getObjValue(formdlpledgebaseinfo0004,mfHighGuaranteeContract);
				List<OptionsList> vouTypeList = new ArrayList<OptionsList>();
				Map<String, String> dicMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
				OptionsList op = new OptionsList();
				if ("collateral".equals(entrFlag)) {// 如果是追加押品
					op.setOptionLabel(dicMap.get(vouType));
					op.setOptionValue(vouType);
					vouTypeList.add(op);
					// 追加押品的时候，根据产品设置担保方式选择
					MfBusAppKind mfBusAppKind = new MfBusAppKind();
					mfBusAppKind.setAppId(mfBusApply.getAppId());
					mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
					String vouType2 = mfBusAppKind.getVouType();
					vouTypeArray = vouType2.split("\\|");
					for (int i = 0; i < vouTypeArray.length; i++) {
						if (!BizPubParm.VOU_TYPE_1.equals(vouTypeArray[i])&&!BizPubParm.VOU_TYPE_1.equals(vouTypeArray[i].split("\\.")[0])) {
							if (vouTypeArray[i].equals(vouType)) {
								continue;
							}
							op = new OptionsList();
							op.setOptionLabel(dicMap.get(vouTypeArray[i]));
							op.setOptionValue(vouTypeArray[i]);
							op.setOptionId(WaterIdUtil.getWaterId());
							vouTypeList.add(op);
						}
					}
				}else{//如果是担保信息采集
					for (int i = 0; i < vouTypeArray.length; i++) {
						op = new OptionsList();
						op.setOptionLabel(dicMap.get(vouTypeArray[i]));
						op.setOptionValue(vouTypeArray[i]);
						vouTypeList.add(op);
					}
				}
				this.changeFormProperty(formdlpledgebaseinfo0004 , "pledgeMethod", "optionArray", vouTypeList);
			}else {// 如果第一个不是保证担保方式，担保登记时默认展示押品登记表单
				String brNo = User.getOrgNo(request);
				String opNo = User.getRegNo(request);
				dataMap = mfBusCollateralRelFeign.getCollateralFormInfo(cusNo, appId, entrFlag, entrance,vouTypeArray[0],brNo,opNo);
				json.put("collClass", dataMap.get("collClass"));
                String classId = dataMap.get("classId").toString();

				//保后变更时，带出原申请担保信息
                /*if(mfBusApply!=null&&"1".equals(mfBusApply.getChangeFlag())){
                    List<PledgeBaseInfo> list  =  mfBusCollateralRelFeign.getPledgeBaseInfoListByAppId(mfBusApply.getAppIdOld());
                    if(list!=null&&list.size()>0) {
                        PledgeBaseInfo pledgeBaseInfo = list.get(0);
                        classId = pledgeBaseInfo.getClassId();
						oldMap = objectToMap(pledgeBaseInfo);
						oldMap.remove("pledgeShowNo");
						oldMap.remove("keepStatus");
                    }
                }*/

				// 获得押品动态表单
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId, "PledgeBaseInfoAction", "");
				if (mfCollateralFormConfig != null) {
					formId = mfCollateralFormConfig.getAddModelDef();
					model.addAttribute("classId", classId);
					formdlpledgebaseinfo0004 = formService.getFormData(formId);
					if (formdlpledgebaseinfo0004.getFormId() == null) {
						formdlpledgebaseinfo0004 = formService.getFormData(mfCollateralFormConfig.getAddModel());
					}
					getFormValue(formdlpledgebaseinfo0004);
					getObjValue(formdlpledgebaseinfo0004, dataMap);
					getObjValue(formdlpledgebaseinfo0004, oldMap);
				}

				String vouTypeListS = gson.toJson(dataMap.get("vouTypeList"));
				List<OptionsList> vouTypeListL = (List<OptionsList>) gson.fromJson(vouTypeListS, List.class);
				List<OptionsList> vouTypeList =new ArrayList<OptionsList>();

				for (int i = 0; i < vouTypeListL.size(); i++) {
					String eS = gson.toJson(vouTypeListL.get(i));
					eS = eS.replaceAll("optionLabel", "optionlabel");
					OptionsList e = gson.fromJson(eS, OptionsList.class);
					vouTypeList.add(e);
                }
//				List<OptionsList> vouTypeList = (List<OptionsList>)JSONArray.toList(vouTypeListA, OptionsList.class);
				this.changeFormProperty(formdlpledgebaseinfo0004, "pledgeMethod", "optionArray", vouTypeList);
				this.changeFormProperty(formdlpledgebaseinfo0004, "assetProperty", "initValue", "2");//业务过程中新增的默认就是客户押品
				dataMap.remove("vouTypeList");
                if (StringUtil.isNotEmpty(appNo)) {
                    // 担保方式为信用时跳过担保信息节点, 前台页面以此值判断当前节点是担保信息节点并展示跳过按钮, 否则会出现无限跳过流程节点的bug
                    TaskImpl task = wkfInterfaceFeign.getTask(appNo, null);
                    if (task != null) {
                        String nodeNo_data = task.getActivityName();
                        if(!"pledge_reg".equals(nodeNo_data) && !"extension_pledge".equals(nodeNo_data)){
                            supportSkipFlag = BizPubParm.YES_NO_N;
                        }
                    }
                }
			}
			//保后变更时
			if(mfBusApply!=null&&"1".equals(mfBusApply.getChangeFlag())){
				ifSupportSkipFlag =  BizPubParm.YES_NO_Y;
			}

		} else {
			MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
			mfCusCreditApply.setCreditAppId(appId);
			mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
			MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
			mfCusCreditConfig.setCreditId(mfCusCreditApply.getTemplateCreditId());
			mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
			if(mfCusCreditConfig!=null){
				busModel = mfCusCreditConfig.getBusModel();
			}
			String brNo = User.getOrgNo(request);
			String opNo = User.getRegNo(request);
			dataMap = mfBusCollateralRelFeign.getCollateralFormInfo(cusNo, appId, entrFlag,entrance, "",brNo,opNo);
			json.put("collClass", dataMap.get("collClass"));
			if (!BizPubParm.VOU_TYPE_2.equals(dataMap.get("vouType"))) {
				// 获得押品动态表单
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
						.getByPledgeImPawnType(dataMap.get("classId").toString(), "PledgeBaseInfoAction", "");
				if (mfCollateralFormConfig != null) {
					formId = mfCollateralFormConfig.getAddModelDef();
					String classId = dataMap.get("classId").toString();
					model.addAttribute("classId", classId);
					formdlpledgebaseinfo0004 = formService.getFormData(formId);
					if (formdlpledgebaseinfo0004.getFormId() == null) {
						formdlpledgebaseinfo0004 = formService.getFormData(mfCollateralFormConfig.getAddModel());
					}
					getFormValue(formdlpledgebaseinfo0004);
					getObjValue(formdlpledgebaseinfo0004, dataMap);
				}
//				List<OptionsList> vouTypeList = (List<OptionsList>) dataMap.get("vouTypeList");
//				JSONArray vouTypeList = (JSONArray) JSONArray.fromObject(dataMap.get("vouTypeList"));

				String vouTypeListS = gson.toJson(dataMap.get("vouTypeList"));
				List<OptionsList> vouTypeListL = (List<OptionsList>) gson.fromJson(vouTypeListS, List.class);
				List<OptionsList> vouTypeList =new ArrayList<OptionsList>();
				for (int i = 0; i < vouTypeListL.size(); i++) {
					String eS = gson.toJson(vouTypeListL.get(i));
					eS = eS.replaceAll("optionLabel", "optionlabel");
					OptionsList e = gson.fromJson(eS, OptionsList.class);
					vouTypeList.add(e);
				}
				this.changeFormProperty(formdlpledgebaseinfo0004, "pledgeMethod", "optionArray", vouTypeList);
				dataMap.remove("vouTypeList");
				String vouTypes = dataMap.get("vouTypes").toString();
				String[] vouTypeArray = vouTypes.split("\\|");
				initAssureInfoForm(dataMap, vouTypeArray);
			} else {
				dataMap.remove("vouTypeList");
				String vouTypes = dataMap.get("vouTypes").toString();
				String[] vouTypeArray = vouTypes.split("\\|");
				initAssureInfoForm(dataMap, vouTypeArray);
				formdlpledgebaseinfo0004 = (FormData) dataMap.get("formdlpledgebaseinfo0004");
				this.changeFormProperty(formdlpledgebaseinfo0004, "assureAmt", "initValue",String.valueOf(mfCusCreditApply.getCreditSum()));
				//保证方式的时候保存方法应该走保证的保存方法
				String vouType = (String) dataMap.get("vouType");
				model.addAttribute("vouType", vouType);
			}
		}
		// 客户选择组件
		/**
		 *YXY 修改客户选择组件：之前选择组件没分页，客户多时页面加载极为缓慢
		 */
//		List<MfCusCustomer> cusList = mfCusCustomerFeign.getAllCus(cusNo);
//		JSONArray cusArray = JSONArray.fromObject(cusList);
//		for (int i = 0; i < cusArray.size(); i++) {
//			cusArray.getJSONObject(i).put("id", cusArray.getJSONObject(i).getString("cusNo"));
//			cusArray.getJSONObject(i).put("name", cusArray.getJSONObject(i).getString("cusName"));
//		}
//		json.put("cus", cusArray);

		// 授信添加担保信息时，不处理押品、费用、文档内容
		if (!"credit".equals(entrFlag) && !"collateral".equals(entrFlag)) {
			String nodeNo = WKF_NODE.pledge_reg.getNodeNo();// 功能节点编号
			if (StringUtil.isNotEmpty(extensionApplyId)) {
				nodeNo = WKF_NODE.extension_pledge.getNodeNo();// 功能节点编号
			}
			model.addAttribute("nodeNo", nodeNo);
		}
		// 保证方式为企业法人担保
		JSONArray cusRelComArray = new JSONArray();
		// 保证方式为自然人担保
		JSONArray cusRelPersArray = new JSONArray();
		List<ParmDic> cusPersRelList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("CUS_PERS_REL");
		JSONArray cusPersRelArray = JSONArray.fromObject(cusPersRelList);
		for (int i = 0; i < cusPersRelArray.size(); i++) {
			JSONObject cusRelJSONObject = new JSONObject();
			cusRelJSONObject.put("id", cusPersRelArray.getJSONObject(i).getString("optCode"));
			cusRelJSONObject.put("name", cusPersRelArray.getJSONObject(i).getString("optName"));
			String remark = cusPersRelArray.getJSONObject(i).getString("remark");
			if ("1".equals(remark)) {// 1-企业法人担保
				cusRelComArray.add(cusRelJSONObject);
			} else {// 自然人担保
				cusRelPersArray.add(cusRelJSONObject);
			}
		}
		if (cusRelComArray.size() > 0) {// 杭州微溪(为企业法人担保时，和保证人的关系，下拉框改成：法人，股东，其他)
			json.put("cusRelComType", cusRelComArray);
			dataMap.put("changeCusRelFlag", "1");
		} else {
			json.put("cusRelComType", cusRelPersArray);
			dataMap.put("changeCusRelFlag", "0");
		}

		String projectName =ymlConfig.getSysParams().get("sys.project.name");
        if(StringUtil.isNotEmpty(ifSupportSkipFlag) && BizPubParm.YES_NO_Y.equals(ifSupportSkipFlag)){
            supportSkipFlag = BizPubParm.YES_NO_Y;
        }


		this.changeFormProperty(formdlpledgebaseinfo0004, "pledgeNo", "initValue",WaterIdUtil.getWaterId());
		this.changeFormProperty(formdlpledgebaseinfo0004, "busModel", "initValue",busModel);
		FormData formHouseEval = formService.getFormData("houseEvalBase");
		FormData formHouseEvalMan = formService.getFormData("houseEvalBaseMan");
		this.changeFormProperty(formHouseEval, "cusNo", "initValue", mfCusCustomer.getCusNo());
		this.changeFormProperty(formHouseEval, "cusName", "initValue", mfCusCustomer.getCusName());
		this.changeFormProperty(formHouseEvalMan, "cusNo", "initValue", mfCusCustomer.getCusNo());
		this.changeFormProperty(formHouseEvalMan, "cusName", "initValue", mfCusCustomer.getCusName());
		model.addAttribute("formHouseEval", formHouseEval);
		model.addAttribute("formHouseEvalMan", formHouseEvalMan);
		model.addAttribute("supportSkipFlag", supportSkipFlag);
		json.put("cusRelPersType", cusRelPersArray);
		dataMap.put("companyId", companyId);
		dataMap.put("cusType", mfCusCustomer.getCusType());
		String ajaxData = json.toString();
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("projectName", projectName);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("formdlpledgebaseinfo0004", formdlpledgebaseinfo0004);
		model.addAttribute("appId", appId);
		model.addAttribute("formId", formId);
		model.addAttribute("entrFlag", entrFlag);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("alliance", alliance);
		model.addAttribute("busModel", busModel);
		model.addAttribute("entrance", entrance);
		model.addAttribute("classModel", classModel);
		model.addAttribute("extensionApplyId", extensionApplyId);
		model.addAttribute("query", "");
		model.addAttribute("classFirstNo", "A,B,C,D");
		return "/component/collateral/CollateralBase_Insert";
	}

	/**
	 *@Description: 查询担保比率是否达到100%发起业务
	 *@Author: lyb
	 *@date: 2018/9/18
	 */
	@RequestMapping(value = "/getCollateralRate")
	@ResponseBody
	public Map<String, Object> getCollateralRate(String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		String kindNo = mfBusApply.getKindNo();
		Boolean rateFlag = false;
		if("1020".equals(kindNo) || "1027".equals(kindNo)){
			//保理业务不判断押品  融租也不想判断
			rateFlag = true;
		}else {
			MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
			mfBusCollateralRel.setAppId(appId);
			mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
			String assureAmtShowType = new CodeUtils().getSingleValByKey("ASSURE_AMT_SHOW_TYPE");
			Double collateralRate = 0d;

			if("4".equals(assureAmtShowType)){
				collateralRate = mfBusCollateralRelFeign.getCollateralRate(mfBusCollateralRel);
				collateralRate = MathExtend.multiply(collateralRate, 100);
			}else{
				collateralRate = MathExtend.multiply(mfBusCollateralRel.getCollateralRate(), 100);
			}
			collateralRate= MathExtend.divide(collateralRate, 1, 2);
			if(collateralRate>=100.0){
				rateFlag = true;
			}else{
				rateFlag = false;
			}
		}
		dataMap.put("rateFlag",rateFlag);
		return dataMap;
	}

	@SuppressWarnings("unchecked")
	private void initAssureInfoForm(Map<String, Object> dataMap, String[] vouTypeArray) throws Exception {
		FormService formService = new FormService();
		MfAssureInfo mfAssureInfo = new MfAssureInfo();
		mfAssureInfo.setCusNo(StringUtil.KillBlankAndTrim((String) dataMap.get("cusNo"), ""));
		mfAssureInfo.setCusName(StringUtil.KillBlankAndTrim((String) dataMap.get("cusName"), ""));
		String nodeNo =  "assureInfoAdd";
		if(("credit".equals(dataMap.get("entrFlag"))||("collateral".equals(dataMap.get("entrFlag"))))&&BizPubParm.BUS_MODEL_12.equals(dataMap.get("busModel"))){
			nodeNo = "creditAssureInfoAdd";
		}
		MfKindForm mfKindForm  = prdctInterfaceFeign.getInitKindForm("base", nodeNo, ApplyEnum.BUDGET_DEFFLAG_TYPE.DEFFLAG1.getCode());
		String formId="";
		if(mfKindForm!=null){
			formId = mfKindForm.getAddModel();
		}
		if(dataMap.containsKey("mfBusApply")){
			MfBusApply mfBusApply  = (MfBusApply)dataMap.get("mfBusApply");
			mfAssureInfo.setAssureAmt(mfBusApply.getAppAmt());
			String busModel = mfBusApply.getBusModel();
			if(BizPubParm.BUS_MODEL_12.equals(busModel)){
				MfBusApplySecond mfBusApplySecond = new MfBusApplySecond();
				mfBusApplySecond.setAppId(mfBusApply.getAppId());
				mfBusApplySecond = appInterfaceFeign.getMfBusApplySecondByAppId(mfBusApplySecond);
				if(mfBusApplySecond != null){
					mfAssureInfo.setBond(mfBusApplySecond.getAssureAmt());
				}
				formId = "assure_GCDB";
			}
		}
		FormData formdlpledgebaseinfo0004 = formService.getFormData(formId);
		getObjValue(formdlpledgebaseinfo0004, mfAssureInfo);
		List<OptionsList> vouTypeList = new ArrayList<OptionsList>();
		Map<String, String> dicMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
		OptionsList op = new OptionsList();
		op.setOptionLabel(dicMap.get(BizPubParm.VOU_TYPE_2));
		op.setOptionValue(BizPubParm.VOU_TYPE_2);
		vouTypeList.add(op);
		for (int i = 0; i < vouTypeArray.length; i++) {
			if (!BizPubParm.VOU_TYPE_1.equals(vouTypeArray[i])) {
				if (BizPubParm.VOU_TYPE_2.equals(vouTypeArray[i])) {
					continue;
				}
				op = new OptionsList();
				op.setOptionLabel(dicMap.get(vouTypeArray[i]));
				op.setOptionValue(vouTypeArray[i]);
				vouTypeList.add(op);
			}
		}
		this.changeFormProperty(formdlpledgebaseinfo0004, "pledgeMethod", "optionArray", vouTypeList);
		if(dataMap.containsKey("busModel")){
			this.changeFormProperty(formdlpledgebaseinfo0004, "busModel", "initValue",dataMap.get("busModel"));
		}
		// 保证方式
		List<ParmDic> assureTypeList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("ASSURE_TYPE");
		if (assureTypeList != null && assureTypeList.size() > 0) {
			this.changeFormProperty(formdlpledgebaseinfo0004, "assureType", "initValue",
					assureTypeList.get(0).getOptCode());
		}
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String formHtml = jsonFormUtil.getJsonStr(formdlpledgebaseinfo0004, "bootstarpTag", "");
		dataMap.put("formHtml", formHtml);
		dataMap.put("formdlpledgebaseinfo0004", formdlpledgebaseinfo0004);
		// 客户类型
		List<ParmDic> dicList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("CUS_TYPE");
		String corpCusTypeStr = "";
		String perCusTypeStr = "";
		String assureAmtCusTypeStr = "";
		for (ParmDic parmObj : dicList) {
			//企业法人担保出企业客户和担保公司客户
			if (parmObj.getRemark().equals(BizPubParm.CUS_BASE_TYPE_CORP)) {
				corpCusTypeStr = corpCusTypeStr + parmObj.getOptCode() + ",";
			} else if(parmObj.getRemark().equals(BizPubParm.CUS_BASE_TYPE_PERSON)){//个人客户
				perCusTypeStr = perCusTypeStr + parmObj.getOptCode() + ",";
			}else {
			}
		}

		if (perCusTypeStr.length() > 1) {
			dataMap.put("perCusTypeStr", perCusTypeStr.substring(0, perCusTypeStr.length() - 1));
			assureAmtCusTypeStr = perCusTypeStr.substring(0, perCusTypeStr.length() - 1);
		}
		if (corpCusTypeStr.length() > 1) {
			dataMap.put("corpCusTypeStr", corpCusTypeStr.substring(0, corpCusTypeStr.length() - 1));
			if(StringUtil.isEmpty(assureAmtCusTypeStr)){
				assureAmtCusTypeStr = corpCusTypeStr.substring(0, corpCusTypeStr.length() - 1);
			}else{
				assureAmtCusTypeStr = assureAmtCusTypeStr + "," + corpCusTypeStr.substring(0, corpCusTypeStr.length() - 1);
			}
		}
		dataMap.put("assureAmtCusTypeStr", assureAmtCusTypeStr);
		// 个人证件类型
		dicList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("PERS_ID_TYPE");
		String perIdTypeStr = "";
		for (ParmDic parmObj : dicList) {
			perIdTypeStr = perIdTypeStr + parmObj.getOptCode() + ",";
		}
		dataMap.put("perIdTypeStr", perIdTypeStr);
		// 企业证件类型
		dicList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("CORP_ID_TYPE");
		String corpIdTypeStr = "";
		for (ParmDic parmObj : dicList) {
			corpIdTypeStr = corpIdTypeStr + parmObj.getOptCode() + ",";
		}
		dataMap.put("perIdTypeStr", perIdTypeStr.substring(0, perIdTypeStr.length() - 1));
		dataMap.put("corpIdTypeStr", corpIdTypeStr.substring(0, corpIdTypeStr.length() - 1));
	}

	/**
	 *
	 * 方法描述： 切换担保方式异步刷新表单
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-7-21 下午3:11:17
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping("/refreshFormByVouTypeAjax")
	@ResponseBody public Map<String, Object> refreshFormByVouTypeAjax(String ajaxData, String entrFlag, String appId, String vouType,String companyId)
			throws Exception {
		ActionContext.initialize(request, response);
		String entrance = request.getParameter("entrance");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormService formService = new FormService();
			dataMap = getMapByJson(ajaxData);
			String cusNo="";
			if(dataMap.containsKey("cusNo")){
				cusNo = dataMap.get("cusNo").toString();
			}
			FormData formdlpledgebaseinfo0004 = null;
			if (!"credit".equals(entrFlag) && !StringUtil.isEmpty(entrFlag)&&!"credit".equals(entrance) ) {
				entrance = "businesss";
				MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
				String[] vouTypeArray = mfBusApply.getVouType().split("\\|");
				dataMap.put("vouTypeArray", vouTypeArray);
				if (BizPubParm.VOU_TYPE_2.equals(vouType)) {// 如果是保证担保方式，担保登记时默认展示保证信息登记表单
					if ("collateral".equals(entrFlag)) {
						MfBusAppKind mfBusAppKind = new MfBusAppKind();
						mfBusAppKind.setAppId(mfBusApply.getAppId());
						mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
						//vouType = mfBusAppKind.getVouType();
						vouTypeArray = mfBusAppKind.getVouType().split("\\|");
					}
					dataMap.put("mfBusApply",mfBusApply);
					initAssureInfoForm(dataMap, vouTypeArray);
					formdlpledgebaseinfo0004 = (FormData) dataMap.get("formdlpledgebaseinfo0004");
				} else if(BizPubParm.VOU_TYPE_10.equals(vouType)){//最高额担保合同方式
					dataMap.put("mfBusApply",mfBusApply);
					formdlpledgebaseinfo0004 = formService.getFormData("highGrtContract0001");
					MfHighGuaranteeContract mfHighGuaranteeContract = new MfHighGuaranteeContract();
					mfHighGuaranteeContract.setCusNo(mfBusApply.getCusNo());
					mfHighGuaranteeContract.setCusName(mfBusApply.getCusName());
					getObjValue(formdlpledgebaseinfo0004,mfHighGuaranteeContract);
					List<OptionsList> vouTypeList = new ArrayList<OptionsList>();
					Map<String, String> dicMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
					OptionsList op = new OptionsList();
					if ("collateral".equals(entrFlag)) {// 如果是追加押品
						op.setOptionLabel(dicMap.get(vouType));
						op.setOptionValue(vouType);
						vouTypeList.add(op);
						// 追加押品的时候，根据产品设置担保方式选择
						MfBusAppKind mfBusAppKind = new MfBusAppKind();
						mfBusAppKind.setAppId(mfBusApply.getAppId());
						mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
						String vouType2 = mfBusAppKind.getVouType();
						vouTypeArray = vouType2.split("\\|");
						for (int i = 0; i < vouTypeArray.length; i++) {
							if (!BizPubParm.VOU_TYPE_1.equals(vouTypeArray[i])) {
								if (vouTypeArray[i].equals(vouType)) {
									continue;
								}
								op = new OptionsList();
								op.setOptionLabel(dicMap.get(vouTypeArray[i]));
								op.setOptionValue(vouTypeArray[i]);
								op.setOptionId(WaterIdUtil.getWaterId());
								vouTypeList.add(op);
							}
						}
					}else{//如果是担保信息采集
						for (int i = 0; i < vouTypeArray.length; i++) {
							op = new OptionsList();
							op.setOptionLabel(dicMap.get(vouTypeArray[i]));
							op.setOptionValue(vouTypeArray[i]);
							vouTypeList.add(op);
						}
					}
					this.changeFormProperty(formdlpledgebaseinfo0004 , "pledgeMethod", "optionArray", vouTypeList);
				}else {// 如果不是保证担保方式，担保登记时默认展示押品登记表单
					String brNo = User.getOrgNo(request);
					String opNo = User.getRegNo(request);
					dataMap = mfBusCollateralRelFeign.getCollateralFormInfo(cusNo, appId, entrFlag,entrance, vouType,brNo,opNo);
					// 获得押品动态表单
					MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(dataMap.get("classId").toString(), "PledgeBaseInfoAction", "");
					if (mfCollateralFormConfig != null) {
						String formId = mfCollateralFormConfig.getAddModelDef();
						formdlpledgebaseinfo0004 = formService.getFormData(formId);
						if (formdlpledgebaseinfo0004.getFormId() == null) {
							formdlpledgebaseinfo0004 = formService.getFormData(mfCollateralFormConfig.getAddModel());
						}
						getFormValue(formdlpledgebaseinfo0004);
						getObjValue(formdlpledgebaseinfo0004, dataMap);
					}
					List<OptionsList> vouTypeList = JSONArray.fromObject(dataMap.get("vouTypeList"));
					List<OptionsList> vouTypeListTmp = new ArrayList<OptionsList>();
					OptionsList op= new OptionsList();
					for (int i = 0; i < vouTypeList.size(); i++) {
						op= new OptionsList();
						JSONObject jsonObject = JSONObject.fromObject(vouTypeList.get(i));
						op.setOptionLabel(jsonObject.getString("optionLabel"));
						op.setOptionValue(jsonObject.getString("optionValue"));
						op.setOptionId(jsonObject.getString("optionId"));
						vouTypeListTmp.add(op);
					}
					this.changeFormProperty(formdlpledgebaseinfo0004, "pledgeMethod", "optionArray", vouTypeListTmp);
					this.changeFormProperty(formdlpledgebaseinfo0004, "assetProperty", "initValue", "2");//业务过程中新增的默认就是客户押品
					dataMap.remove("vouTypeList");
				}
			} else if ("credit".equals(entrFlag) && StringUtil.isNotEmpty(entrFlag)&&"credit".equals(entrance) ){
				if (!BizPubParm.VOU_TYPE_2.equals(vouType)) {
					String brNo = User.getOrgNo(request);
					String opNo = User.getRegNo(request);
					dataMap = mfBusCollateralRelFeign.getCollateralFormInfo(cusNo, appId, entrFlag,entrance, vouType,brNo,opNo);
					// 获得押品动态表单
					MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(dataMap.get("classId").toString(), "PledgeBaseInfoAction", "");
					if (mfCollateralFormConfig != null) {
						String formId = mfCollateralFormConfig.getAddModelDef();
						formdlpledgebaseinfo0004 = formService.getFormData(formId);
						if (formdlpledgebaseinfo0004.getFormId() == null) {
							formdlpledgebaseinfo0004 = formService.getFormData(mfCollateralFormConfig.getAddModel());
						}
						getFormValue(formdlpledgebaseinfo0004);
						getObjValue(formdlpledgebaseinfo0004, dataMap);
					}
					List<OptionsList> vouTypeList = JSONArray.fromObject(dataMap.get("vouTypeList"));
					List<OptionsList> vouTypeListTmp = new ArrayList<OptionsList>();
					OptionsList op= new OptionsList();
					for (int i = 0; i < vouTypeList.size(); i++) {
						op= new OptionsList();
						JSONObject jsonObject = JSONObject.fromObject(vouTypeList.get(i));
						op.setOptionLabel(jsonObject.getString("optionLabel"));
						op.setOptionValue(jsonObject.getString("optionValue"));
						op.setOptionId(jsonObject.getString("optionId"));
						vouTypeListTmp.add(op);
					}
					this.changeFormProperty(formdlpledgebaseinfo0004, "pledgeMethod", "optionArray", vouTypeListTmp);
					dataMap.remove("vouTypeList");
					if(!dataMap.containsKey("pledgeShowNo")){//如果没有押品编号，用流水号
						dataMap.put("pledgeShowNo", WaterIdUtil.getWaterId());
					}
				} else {
					String brNo = User.getOrgNo(request);
					String opNo = User.getRegNo(request);
					dataMap = mfBusCollateralRelFeign.getCollateralFormInfo(cusNo, appId, entrFlag,entrance, vouType,brNo,opNo);
					List<ParmDic> vouTypeArrayTmp = (List<ParmDic>) new CodeUtils().getCacheByKeyName("VOU_TYPE");
					List<ParmDic> creditVouTypeArray = (List<ParmDic>) new CodeUtils().getCacheByKeyName("CREDIT_VOU_TYPE");
					if (creditVouTypeArray!=null&&creditVouTypeArray.size()>0){
						vouTypeArrayTmp = new ArrayList<ParmDic>();
						vouTypeArrayTmp = creditVouTypeArray;
					}
					String vouTypesTmp = "";
					for (ParmDic parmDic : vouTypeArrayTmp) {
						if (!BizPubParm.VOU_TYPE_1.equals(parmDic.getOptCode())) {
							vouTypesTmp = vouTypesTmp + parmDic.getOptCode() + "|";
						}
					}
					String[] vouTypeArray = vouTypesTmp.split("\\|");
					initAssureInfoForm(dataMap, vouTypeArray);
					formdlpledgebaseinfo0004 = (FormData) dataMap.get("formdlpledgebaseinfo0004");
				}
			}else{
				String brNo = User.getOrgNo(request);
				String opNo = User.getRegNo(request);
				dataMap = mfBusCollateralRelFeign.getCollateralFormInfo(cusNo, appId, entrFlag,entrance, vouType,brNo,opNo);
				if (!BizPubParm.VOU_TYPE_2.equals(vouType)) {
					// 获得押品动态表单
					MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(dataMap.get("classId").toString(), "PledgeBaseInfoAction", "");
					if (mfCollateralFormConfig != null) {
						String formId = mfCollateralFormConfig.getAddModelDef();
						formdlpledgebaseinfo0004 = formService.getFormData(formId);
						if (formdlpledgebaseinfo0004.getFormId() == null) {
							formdlpledgebaseinfo0004 = formService.getFormData(mfCollateralFormConfig.getAddModel());
						}
						getFormValue(formdlpledgebaseinfo0004);
						getObjValue(formdlpledgebaseinfo0004, dataMap);
					}
					List<OptionsList> vouTypeList = JSONArray.fromObject(dataMap.get("vouTypeList"));
					List<OptionsList> vouTypeListTmp = new ArrayList<OptionsList>();
					OptionsList op= new OptionsList();
					for (int i = 0; i < vouTypeList.size(); i++) {
						op= new OptionsList();
						JSONObject jsonObject = JSONObject.fromObject(vouTypeList.get(i));
						op.setOptionLabel(jsonObject.getString("optionLabel"));
						op.setOptionValue(jsonObject.getString("optionValue"));
						op.setOptionId(jsonObject.getString("optionId"));
						vouTypeListTmp.add(op);
					}
					this.changeFormProperty(formdlpledgebaseinfo0004, "pledgeMethod", "optionArray", vouTypeListTmp);
					dataMap.remove("vouTypeList");
					if(!dataMap.containsKey("pledgeShowNo")){//如果没有押品编号，用流水号
						dataMap.put("pledgeShowNo", WaterIdUtil.getWaterId());
					}
				} else {
					List<ParmDic> vouTypeArrayTmp = (List<ParmDic>) new CodeUtils().getCacheByKeyName("VOU_TYPE");
					String vouTypesTmp = "";
					for (ParmDic parmDic : vouTypeArrayTmp) {
						if (!BizPubParm.VOU_TYPE_1.equals(parmDic.getOptCode())) {
							vouTypesTmp = vouTypesTmp + parmDic.getOptCode() + "|";
						}
					}
					String[] vouTypeArray = vouTypesTmp.split("\\|");
					initAssureInfoForm(dataMap, vouTypeArray);
					formdlpledgebaseinfo0004 = (FormData) dataMap.get("formdlpledgebaseinfo0004");
				}
			}

			if (!BizPubParm.VOU_TYPE_2.equals(vouType)) {
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				if(!dataMap.containsKey("pledgeNo")) {
					this.changeFormProperty(formdlpledgebaseinfo0004, "pledgeNo", "initValue", WaterIdUtil.getWaterId());
				}
				FormData formHouseEval = formService.getFormData("houseEvalBase");
				FormData formHouseEvalMan = formService.getFormData("houseEvalBaseMan");
				if(dataMap.containsKey("cusNo")){
					MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(dataMap.get("cusNo").toString());
					this.changeFormProperty(formHouseEval, "cusNo", "initValue", mfCusCustomer.getCusNo());
					this.changeFormProperty(formHouseEval, "cusName", "initValue", mfCusCustomer.getCusName());
					this.changeFormProperty(formHouseEvalMan, "cusNo", "initValue", mfCusCustomer.getCusNo());
					this.changeFormProperty(formHouseEvalMan, "cusName", "initValue", mfCusCustomer.getCusName());
				}

				String houseEvalBaseHtml = jsonFormUtil.getJsonStr(formHouseEval, "bootstarpTag", "");
				String houseEvalBaseManHtml = jsonFormUtil.getJsonStr(formHouseEvalMan, "bootstarpTag", "");
				dataMap.put("houseEvalBaseHtml", houseEvalBaseHtml);
				dataMap.put("houseEvalBaseManHtml", houseEvalBaseManHtml);

				String formHtml = jsonFormUtil.getJsonStr(formdlpledgebaseinfo0004, "bootstarpTag", "");
				dataMap.put("formHtml", formHtml);
			}
			dataMap.put("vouType", vouType);
			dataMap.put("companyId", companyId);

			// 授信添加担保信息时，不处理押品、费用、文档内容
			if (!"credit".equals(entrFlag) && !"collateral".equals(entrFlag)) {
				String nodeNo = WKF_NODE.pledge_reg.getNodeNo();// 功能节点编号
				dataMap.put("nodeNo", nodeNo);
			}
			dataMap.remove("formdlpledgebaseinfo0004");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
		}
		return dataMap;
	}

	/**
	 * 方法描述： 押品更新表单
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author YuShuai
	 * @date 2017-6-14 下午9:12:37
	 */
	@RequestMapping("/updateInput")
	public String updateInput(Model model, String pledgeNo, String cusNo, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(pledgeNo);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);

		MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
		String vouType = pledgeBaseInfo.getPledgeMethod();
		dataMap.put("classId", pledgeBaseInfo.getClassId());
		dataMap.put("classSecondName", pledgeBaseInfo.getClassSecondName());
		dataMap.put("cusNo", cusNo);
		dataMap.put("cusName", mfCusCustomer.getCusName());
		dataMap.put("certificateName", mfCusCustomer.getCusName());
		dataMap.put("pledgeMethod", vouType);
		dataMap.put("keepStatus", "0");// 未入库
		dataMap.put("refFlag", "0");// 是否被关联
		dataMap.put("delflag", "0");// 未删除
		dataMap.put("pledgeNo", pledgeBaseInfo.getPledgeNo());
		dataMap.put("pledgeName", pledgeBaseInfo.getPledgeName());// 未删除

		dataMap.put("pledgeShowNo", pledgeBaseInfo.getPledgeShowNo());
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
				.getByPledgeImPawnType(pledgeBaseInfo.getClassId(), "PledgeBaseInfoAction", "");
		if (mfCollateralFormConfig != null) {
			String formId = mfCollateralFormConfig.getAddModelDef();
			if ("17061214582342011".equals(pledgeBaseInfo.getClassId())
					&& BizPubParm.BUS_MODEL_12.equals(mfBusApply.getBusModel())) {
				formId = "fangchanbjbase0001";
			}
			FormData formdlpledgebaseinfo0004 = formService.getFormData(formId);
			if (formdlpledgebaseinfo0004.getFormId() == null) {
				formdlpledgebaseinfo0004 = formService.getFormData(mfCollateralFormConfig.getAddModel());
			}
			getFormValue(formdlpledgebaseinfo0004);
			getObjValue(formdlpledgebaseinfo0004, dataMap);
			model.addAttribute("formdlpledgebaseinfo0004", formdlpledgebaseinfo0004);
		}
		model.addAttribute("query", "");
		return "/component/collateral/CollateralBase_updateInput";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/updatePledgeInfoAjax")
	@ResponseBody public Map<String, Object> updatePledgeInfoAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			FormData formdlpledgebaseinfo0004 = formService.getFormData(formId);
			getFormValue(formdlpledgebaseinfo0004, map);
			setObjValue(formdlpledgebaseinfo0004, pledgeBaseInfo);
			pledgeBaseInfoFeign.update(pledgeBaseInfo);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 流程中添加押品评估信息
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 谢静霞
	 * @date 2017-5-24 上午10:53:11
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/insertEvalInput")
	public String insertEvalInput(Model model, String cusNo, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String scNo = WKF_NODE.pre_evaluation.getScenceTypeDoc();// 要件场景
		String nodeNo = WKF_NODE.pre_evaluation.getNodeNo();// 功能节点编号

		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
		List<MfCollateralClass> list = mfCollateralClassFeign.getAll(mfCollateralClass);
		String vouType = "";
		String classId = "";
		if (null != list && list.size() > 1) {
			if (BizPubParm.BUS_MODEL_12.equals(mfBusApply.getBusModel())) {
				for (MfCollateralClass parmCollateralClass : list) {
					if ("17061309580618115".equals(parmCollateralClass.getClassId())) {
						classId = parmCollateralClass.getClassId();
						vouType = parmCollateralClass.getVouType();
						mfCollateralClass = parmCollateralClass;
						break;
					}
				}
			} else {
				mfCollateralClass = list.get(0);
				vouType = mfCollateralClass.getVouType();
				classId = mfCollateralClass.getClassId();
			}
		}
		if (StringUtil.isEmpty(vouType)) {
			vouType = BizPubParm.VOU_TYPE_3;
		}
		dataMap.put("classSecondName", mfCollateralClass.getClassSecondName());
		dataMap.put("classId", classId);
		dataMap.put("cusNo", cusNo);
		dataMap.put("appName", mfBusApply.getAppName());// 未删除
		dataMap.put("cusName", mfCusCustomer.getCusName());
		dataMap.put("certificateName", mfCusCustomer.getCusName());
		dataMap.put("pledgeMethod", vouType);
		dataMap.put("keepStatus", "0");// 未入库
		dataMap.put("refFlag", "0");// 是否被关联
		dataMap.put("delflag", "0");// 未删除
		if (BizPubParm.BUS_MODEL_12.equals(mfBusApply.getBusModel())) {
			dataMap.put("pledgeName", "房屋");
		}
		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
				.getByPledgeImPawnType(mfCollateralClass.getClassId(), "PledgeBaseInfoAction", "");
		if (mfCollateralFormConfig != null) {
			mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);

			String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.pre_evaluation, appId, null, User.getRegNo(request));
			FormData formdlpledgebaseinfo0004 = formService.getFormData(formId);

			getFormValue(formdlpledgebaseinfo0004);
			getObjValue(formdlpledgebaseinfo0004, mfBusApply);
			getObjValue(formdlpledgebaseinfo0004, dataMap);
			model.addAttribute("formdlpledgebaseinfo0004", formdlpledgebaseinfo0004);
		}
		JSONObject json = new JSONObject();
		JSONArray collClassArray = JSONArray.fromObject(list);
		for (int i = 0; i < collClassArray.size(); i++) {
			collClassArray.getJSONObject(i).put("id", collClassArray.getJSONObject(i).getString("classId"));
			collClassArray.getJSONObject(i).put("name", collClassArray.getJSONObject(i).getString("classSecondName"));
		}
		json.put("collClass", collClassArray);
		String ajaxData = json.toString();
		model.addAttribute("scNo", scNo);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/collateral/CollateralBaseAndEval_Insert";
	}




	/**
	 *
	 * 方法描述： 押品主体，跳转押品新增表单
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-12 下午5:49:06
	 */
	@RequestMapping("/collateralInput")
	public String collateralInput(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlcollateralbase0004 = formService.getFormData("dlcollateralbase0004");
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		Task task = wkfInterfaceFeign.getTask(appId, null);
		String nodeNo = task.getActivityName();
		if (mfBusApply != null) {
			getObjValue(formdlcollateralbase0004, mfBusApply);
			model.addAttribute("formdlcollateralbase0004", formdlcollateralbase0004);
		}
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("query", "");
		return "/component/collateral/Collateral_Insert";
	}

	/**
	 *
	 * 方法描述： 授信业务登记担保信息，跳转押品登记页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-18 上午11:20:38
	 */
	@RequestMapping("/collateralBaseInputCredit")
	public String collateralBaseInputCredit(Model model, String classId, String classSecondName, String cusNo,
											String vouType, String action) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
		dataMap.put("classId", classId);
		dataMap.put("classSecondName", classSecondName);
		dataMap.put("cusNo", cusNo);
		dataMap.put("cusName", mfCusCustomer.getCusName());
		dataMap.put("certificateName", mfCusCustomer.getCusName());
		dataMap.put("pledgeMethod", vouType);
		dataMap.put("keepStatus", "0");// 未入库
		dataMap.put("refFlag", "0");// 是否被关联
		dataMap.put("delflag", "0");// 未删除
		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				action, "");
		String formId = "";
		if (mfCollateralFormConfig != null) {
			formId = mfCollateralFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的PledgeBaseInfoAction表单信息没有查询到");
		} else {
			FormData formdlpledgebaseinfo0004 = formService.getFormData(formId);
			if (formdlpledgebaseinfo0004.getFormId() == null) {
				// Log.error("押品类型为" + mfCollateralFormConfig.getFormType() +
				// "的PledgeBaseInfoAction表单form" + formId
				// + ".xml文件不存在");
			} else {
				getFormValue(formdlpledgebaseinfo0004);
				getObjValue(formdlpledgebaseinfo0004, dataMap);
				model.addAttribute("formdlpledgebaseinfo0004", formdlpledgebaseinfo0004);
			}
		}
		model.addAttribute("query", "");
		return "/component/collateral/CollateralBaseCredit_Insert";
	}

	/**
	 *
	 * 方法描述： 押品主体，新增授信押品
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-18 下午4:59:31
	 */
	@RequestMapping("/collateralInputCredit")
	public String collateralInputCredit(Model model, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcollateralcredit0004 = formService.getFormData("collateralcredit0004");
		// 获取客户信息
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		mfCusCreditApply.setCusNo(cusNo);
		mfCusCreditApply = creditApplyInterfaceFeign.getByCusNoAndOrederFirst(mfCusCreditApply);
		String appId = mfCusCreditApply.getCreditAppId();
		mfCusCreditApply.setCusName(mfCusCustomer.getCusName());
		// request.setAttribute("opType", "CREDIT_APPLY");
		getObjValue(formcollateralcredit0004, mfCusCreditApply);
		model.addAttribute("appId", appId);
		model.addAttribute("formcollateralcredit0004", formcollateralcredit0004);
		model.addAttribute("query", "");
		return "/component/collateral/CollateralCredit_Insert";
	}

	/**
	 *
	 * 方法描述： 跳转押品详情
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param cusNo
	 * @param relId
	 * @param entrance
	 * @param appId
	 * @param fincId
	 * @param busEntrance
	 * @date 2017-4-12 下午3:13:03
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/getCollateralInfo")
	public String getCollateralInfo(Model model, String cusNo, String relId, String entrance, String appId,
									String fincId, String busEntrance, String creditAppId, String collateralType, String fincMainId, String extensionApplyId) throws Exception {
		ActionContext.initialize(request, response);
		SysTaskInfo sti = new SysTaskInfo();
		List<SysTaskInfo> stiList = null;
		MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
		mfBusCollateralRel.setAppId(relId);
		String jumpCreditAppId = creditAppId;
		String ifEditFlag = "1";//是否可删除或者新增押品
		if(StringUtil.isNotEmpty(relId) && relId.contains("adj")){//如果是授信调整，根据授信申请ID去查担保信息
			MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
			mfCusCreditAdjustApply.setAdjustAppId(relId);
			mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
			if(mfCusCreditAdjustApply != null){
				creditAppId = mfCusCreditAdjustApply.getCreditAppId();
				mfBusCollateralRel.setAppId(creditAppId);
                relId = creditAppId;
                appId = creditAppId;
			}
		}
		if(StringUtil.isEmpty(collateralType)){
			collateralType="pledge";
		}
		mfBusCollateralRel.setCollateralType(collateralType);
		mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
		String receEndDate = mfBusCollateralRel.getReceEndDate();
		if(StringUtil.isNotEmpty(receEndDate)){
			receEndDate = DateUtil.getShowDateTime(receEndDate);
		}
		mfBusCollateralRel.setReceEndDate(receEndDate);
		model.addAttribute("mfBusCollateralRel", mfBusCollateralRel);
		if (mfBusCollateralRel != null) {
			String evalFlag = mfBusCollateralRelFeign.getEvalFlag(mfBusCollateralRel);
			String instockAllflag = mfBusCollateralRelFeign.getAllCollaInstockflag(mfBusCollateralRel);
			String outstockAllflag = mfBusCollateralRelFeign.getAllCollaOutstockflag(mfBusCollateralRel);
			model.addAttribute("evalFlag", evalFlag);
			model.addAttribute("instockAllflag", instockAllflag);
			model.addAttribute("outstockAllflag", outstockAllflag);

			if (!"credit".equals(entrance)) {
				MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
				model.addAttribute("mfBusApply", mfBusApply);
				model.addAttribute("busModel", mfBusApply.getBusModel());
				if (mfBusApply != null) {
					if(StringUtil.isNotEmpty(mfBusApply.getIsCreditFlag()) && BizPubParm.YES_NO_Y.equals(mfBusApply.getIsCreditFlag())){
						MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
						mfCusCreditApply.setCreditAppId(mfBusApply.getCreditAppId());
						mfCusCreditApply = creditApplyInterfaceFeign.getCusCreditApply(mfCusCreditApply);
						if(mfCusCreditApply != null && StringUtil.isNotEmpty(mfCusCreditApply.getCreditApproveId())){
							sti.setBizPkNo(mfCusCreditApply.getCreditApproveId());
							sti.setPasMaxNo("1");// 审批任务
							sti.setPasMinNo("103");// 授信审批
							stiList = sysTaskInfoFeign.getAllTask(sti);
							if(stiList != null && stiList.size()>0){
								ifEditFlag = "0";
							}
						}
					}else{
						sti.setBizPkNo(mfBusApply.getAppId());
						sti.setPasMaxNo("1");// 审批任务
						sti.setPasMinNo("110");// 业务审批
						stiList = sysTaskInfoFeign.getAllTask(sti);
						if(stiList != null && stiList.size()>0){
							ifEditFlag = "0";
						}

					}
					if("supplement_data".equals(mfBusApply.getNodeNo())){
						ifEditFlag = "1";
					}
					mfBusApply.setFincAmt(MathExtend.moneyStr(mfBusApply.getAppAmt() / 10000));
					if (BizPubParm.APP_STS_PASS.equals(mfBusApply.getAppSts())) {
						MfBusPact mfBusPact = new MfBusPact();
						mfBusPact = pactInterfaceFeign.getByAppId(appId);
						mfBusPact.setFincAmt(MathExtend.moneyStr(mfBusPact.getUsableFincAmt() / 10000));
						String busSts = mfBusPact.getPactSts();
						String isAllFincEnd = pactInterfaceFeign.getIsAllFincEnd(mfBusPact);
						model.addAttribute("busSts", busSts);
						model.addAttribute("isAllFincEnd", isAllFincEnd);
						model.addAttribute("mfBusPact", mfBusPact);
						model.addAttribute("pactId", mfBusPact.getPactId());
					}
					String busEndDate = "";
					if (BizPubParm.TERM_TYPE_MONTH.equals(mfBusApply.getTermType())) {
						busEndDate = DateUtil
								.getShowDateTime(DateUtil.getDateStr(mfBusApply.getRegDate(), mfBusApply.getTerm()));
					} else if (BizPubParm.TERM_TYPE_DAY.equals(mfBusApply.getTermType())) {
						busEndDate = DateUtil
								.getShowDateTime(DateUtil.addByDay(mfBusApply.getRegDate(), mfBusApply.getTerm()));
					}else {
					}
					model.addAttribute("busEndDate", busEndDate);
					// 页面担保金额展示
					Double collateralAmt = mfBusCollateralRelFeign
							.getAssureAmtShow(MathExtend.divide(mfBusCollateralRel.getCollateralAmt(), 10000, 2), mfBusApply.getAppId(),mfBusCollateralRel.getCollateralType());
					mfBusCollateralRel.setCollateralAmt(collateralAmt);
					String assureAmtShowType = new CodeUtils().getSingleValByKey("ASSURE_AMT_SHOW_TYPE");
					Double collateralRate = 0d;
					if("4".equals(assureAmtShowType) || "5".equals(assureAmtShowType)){
						collateralRate = mfBusCollateralRelFeign.getCollateralRate(mfBusCollateralRel);
						collateralRate = MathExtend.multiply(collateralRate, 100);
					}else{
						collateralRate = MathExtend.multiply(mfBusCollateralRel.getCollateralRate(), 100);
					}
					collateralRate= MathExtend.divide(collateralRate, 1, 2);
					if(collateralRate>100.0){
						collateralRate=100.0;
					}
					mfBusCollateralRel.setCollateralRate(collateralRate);
					MfCusCustomer mfCusCustomer = new MfCusCustomer();
					mfCusCustomer.setCusNo(mfBusApply.getCusNo());
					mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
					String headImg = mfCusCustomer.getHeadImg();
					String ifUploadHead = mfCusCustomer.getIfUploadHead();
					model.addAttribute("headImg", headImg);
					model.addAttribute("ifUploadHead", ifUploadHead);
					model.addAttribute("baseType", mfCusCustomer.getCusBaseType());
				}
				if (!"".equals(StringUtil.KillEmpty(fincId))) {
					MfBusFincApp mfBusFincApp = new MfBusFincApp();
					mfBusFincApp.setFincId(fincId);
					MfBusFincApp resultFincApp = pactInterfaceFeign.getFincAppById(mfBusFincApp);
					if (resultFincApp != null) {
						String fincSts = resultFincApp.getFincSts();
						model.addAttribute("fincSts", fincSts);
					}
				}
			}else{//授信视角进担保页面

				//授信阶段将担保金额该为万元计数
				Double collateralAmt=DataUtil.div(mfBusCollateralRel.getCollateralAmt(), 10000D, 2);
				mfBusCollateralRel.setCollateralAmt(collateralAmt);
				Double collateralRate = MathExtend.multiply(mfBusCollateralRel.getCollateralRate(), 100);
				collateralRate= MathExtend.divide(collateralRate, 1, 2);
				if(collateralRate>100.0){
					collateralRate=100.0;
				}
				mfBusCollateralRel.setCollateralRate(collateralRate);

				MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
				mfCusCreditApply.setCreditAppId(creditAppId);
				mfCusCreditApply = creditApplyInterfaceFeign.getCusCreditApply(mfCusCreditApply);
				if(mfCusCreditApply != null && StringUtil.isNotEmpty(mfCusCreditApply.getCreditApproveId())){
					sti.setBizPkNo(mfCusCreditApply.getCreditApproveId());
					sti.setPasMaxNo("1");// 审批任务
					sti.setPasMinNo("103");// 合同审批
					stiList = sysTaskInfoFeign.getAllTask(sti);
					if(stiList != null && stiList.size()>0){
						ifEditFlag = "0";
					}
					if("credit_supplement".equals(mfCusCreditApply.getApproveNodeNo())){
						ifEditFlag = "1";
					}

				}
				if(mfCusCreditApply != null && StringUtil.isNotEmpty(mfCusCreditApply.getTemplateCreditId())){
					MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
					mfCusCreditConfig.setCreditId(mfCusCreditApply.getTemplateCreditId());
					mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
					if(mfCusCreditConfig!=null){
						model.addAttribute("busModel",mfCusCreditConfig.getBusModel());
					}
				}
			}
			String scNo = BizPubParm.SCENCE_TYPE_DOC_PLE_REG;
			model.addAttribute("scNo", scNo);
			Map<String, Object> dataMap = mfBusCollateralRelFeign.getCollateralRelTrace(mfBusCollateralRel);
			String tranFlag = String.valueOf(dataMap.get("tranFlag"));
			String repoFlag = String.valueOf(dataMap.get("repoFlag"));
			model.addAttribute("tranFlag", tranFlag);
			model.addAttribute("repoFlag", repoFlag);
			if ("1".equals(repoFlag)) {
				MfPleRepoApply mfPleRepoApply = new MfPleRepoApply();
				mfPleRepoApply.setBusPleId(mfBusCollateralRel.getBusCollateralId());
				mfPleRepoApply = mfPleRepoApplyFeign.getById(mfPleRepoApply);
				model.addAttribute("mfPleRepoApply", mfPleRepoApply);
			}
		}
		String readOnly = request.getParameter("readOnly");
		//要件的权限
		String queryFile = cusInterfaceFeign.validateCusFormModify(mfBusCollateralRel.getCusNo(),mfBusCollateralRel.getAppId(),BizPubParm.FORM_EDIT_FLAG_FILE,User.getRegNo(request));
		if(queryFile==null) {
			queryFile="";
		}

		//要件的展示方式：0块状1列表
		List<Object> parmDics = busiCacheInterface.getParmDicList("DOC_SHOW_TYPE");
		for (Object o :parmDics){
			ParmDic p = (ParmDic) o;
			String docShowType = p.getOptCode();
			model.addAttribute("docShowType", docShowType);
		}
		model.addAttribute("queryFile", queryFile);
		model.addAttribute("readOnly",readOnly);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("appId", appId);
		model.addAttribute("relId", relId);
		model.addAttribute("fincId", fincId);
		model.addAttribute("fincMainId", fincMainId);
		model.addAttribute("entrance", entrance);
		model.addAttribute("busEntrance", busEntrance);
		model.addAttribute("creditAppId", jumpCreditAppId);
		model.addAttribute("jumpCreditAppId", jumpCreditAppId);
		model.addAttribute("collateralType", collateralType);
		if("0".equals(ifEditFlag)){
			model.addAttribute("query", "query");
		}else{
			model.addAttribute("query", "");
		}
		model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
		model.addAttribute("operable","operable");
		model.addAttribute("extensionApplyId", extensionApplyId);
		model.addAttribute("ifEditFlag", ifEditFlag);
		// 申请及合同视图,查看客户授信担保信息
		if("1".equals(readOnly)){
			return "/component/collateral/MfBusCollateralRel_Detail_readOnly";
		}
		return "/component/collateral/MfBusCollateralRel_Detail";
	}

	/**
	 *
	 * 方法描述： 适用应收账款融资和保理业务 获得担保信息整体处于的状态， 用于控制担保信息中功能操作是否可用。
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-12 上午11:36:39
	 */
	@RequestMapping("/getCollateralRelStsByIdAjax")
	@ResponseBody public Map<String, Object> getCollateralRelStsByIdAjax(String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
			mfBusCollateralRel.setAppId(appId);
			mfBusCollateralRel = mfBusCollateralRelFeign.getCollateralRelStsById(mfBusCollateralRel);
			dataMap = mfBusCollateralRelFeign.getCollateralRelTrace(mfBusCollateralRel);
			dataMap.put("collaSts", mfBusCollateralRel.getCollaSts());
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 适用动产质押 获得担保信息整体处于的状态， 用于控制担保信息中功能操作是否可用。
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-6-20 下午3:48:05
	 */
	@RequestMapping("/getCollateralRelStsForMoveableAjax")
	@ResponseBody public Map<String, Object> getCollateralRelStsForMoveableAjax(String appId) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
			mfBusCollateralRel.setAppId(appId);
			mfBusCollateralRel = mfBusCollateralRelFeign.getCollateralRelStsForMoveable(mfBusCollateralRel);
			dataMap.put("collaSts", mfBusCollateralRel.getCollaSts());
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述：
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-14 下午5:02:33
	 */
	@RequestMapping("/getApproveCollateralInfo")
	public String getApproveCollateralInfo(Model model, String appId, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
		mfBusCollateralRel.setAppId(appId);
		mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		String headImg = mfCusCustomer.getHeadImg();
		String ifUploadHead = mfCusCustomer.getIfUploadHead();
		String scNo = BizPubParm.SCENCE_TYPE_DOC_PLE_REG;
		// 页面担保金额展示
		Double collateralAmt = mfBusCollateralRelFeign
				.getAssureAmtShow(MathExtend.divide(mfBusCollateralRel.getCollateralAmt(), 10000, 2), appId,mfBusCollateralRel.getCollateralType());
		mfBusCollateralRel.setCollateralAmt(collateralAmt);
		String assureAmtShowType = new CodeUtils().getSingleValByKey("ASSURE_AMT_SHOW_TYPE");
		Double collateralRate = 0d;
		if("4".equals(assureAmtShowType) || "5".equals(assureAmtShowType)){
			collateralRate = mfBusCollateralRelFeign.getCollateralRate(mfBusCollateralRel);
			collateralRate = MathExtend.multiply(collateralRate, 100);
		}else{
			collateralRate = MathExtend.multiply(mfBusCollateralRel.getCollateralRate(), 100);
		}
		if(collateralRate>100.0){
			collateralRate=100.0;
		}
		collateralRate= MathExtend.divide(collateralRate, 1, 2);
		mfBusCollateralRel.setCollateralRate(collateralRate);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfBusCollateralRel", mfBusCollateralRel);
		model.addAttribute("headImg", headImg);
		model.addAttribute("ifUploadHead", ifUploadHead);
		model.addAttribute("scNo", scNo);
		model.addAttribute("appId", appId);
		model.addAttribute("query", "");
		return "/component/collateral/MfBusCollateralRel_DetailApprove";
	}

	/**
	 *
	 * 方法描述： 获得业务押品信息
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-12 下午4:07:13
	 */
	@RequestMapping("/getCollateralInfoAjax")
	@ResponseBody public Map<String, Object> getCollateralInfoAjax(String appId, String entrance,String classNo,String collateralType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if(StringUtil.isNotEmpty(classNo)){//传入获取的押品大类，如果没有传入则默认是之前的
				dataMap.put("classNo", classNo);
			}else{
				dataMap.put("classNo", "A");
			}
			dataMap.put("entrance", entrance);

			dataMap.put("appId", appId);
			dataMap.put("regNo", User.getRegNo(request));
			if(StringUtil.isNotEmpty(collateralType)){
				dataMap.put("collateralType", collateralType);
			}
			dataMap = mfBusCollateralRelFeign.getBusCollateralDetailById(dataMap);
		} catch (Exception e) {
			// logger.error("获得业务押品信息出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 方法描述： 获取业务有没有关联押品
	 * @param appId
	 * @param entrance
	 * @param classNo
	 * @param collateralType
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年8月30日 下午6:40:18
	 */
	@RequestMapping("/getCollateralInfoListAjax")
	@ResponseBody public Map<String, Object> getCollateralInfoListAjax(String appId,String creditType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if(BizPubParm.CREDIT_TYPE_ADJUST.equals(creditType) || (StringUtil.isNotEmpty(appId) && appId.contains("adj"))){//授信调整
				MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
				mfCusCreditAdjustApply.setAdjustAppId(appId);
				mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
				if(mfCusCreditAdjustApply == null){
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
					return dataMap;
				}
				appId = mfCusCreditAdjustApply.getCreditAppId();
			}
			MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
			mfBusCollateralRel.setAppId(appId);
			List<MfBusCollateralRel> mfBusCollateralRelList = mfBusCollateralRelFeign.getCollateralInfoListAjax(mfBusCollateralRel);
			if(mfBusCollateralRelList != null && mfBusCollateralRelList.size() > 0){
				dataMap.put("skip", BizPubParm.YES_NO_Y);
			}else{
				dataMap.put("skip", BizPubParm.YES_NO_N);
			}
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 获得引用的押品是否全部入库
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-17 下午2:18:51
	 */
	@RequestMapping("/getInstockFlagAndSubmitAjax")
	@ResponseBody public Map<String, Object> getInstockFlagAndSubmitAjax(String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
			mfBusCollateralRel.setAppId(appId);
			dataMap = mfBusCollateralRelFeign.getInstockFlagAndSubmit(mfBusCollateralRel);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 获得押品是否全部入库
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-17 下午3:29:43
	 */
	@RequestMapping("/getAllInstockFlagAjax")
	@ResponseBody public Map<String, Object> getAllInstockFlagAjax(String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
			mfBusCollateralRel.setAppId(appId);
			mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
			String instockflag = mfBusCollateralRelFeign.getAllCollaInstockflag(mfBusCollateralRel);
			dataMap.put("instockflag", instockflag);
			dataMap.put("collateralRel", mfBusCollateralRel);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 获得押品是否全部出库
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-17 下午4:07:59
	 */
	@RequestMapping("/getAllOutstockFlagAjax")
	@ResponseBody public Map<String, Object> getAllOutstockFlagAjax(String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
			mfBusCollateralRel.setAppId(appId);
			mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
			String outstockflag = mfBusCollateralRelFeign.getAllCollaOutstockflag(mfBusCollateralRel);
			dataMap.put("outstockflag", outstockflag);
			dataMap.put("collateralRel", mfBusCollateralRel);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 获得押品是否全部出库,全部出库更新担保信息
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-24 下午2:15:55
	 */
	@RequestMapping("/getAllOutstockFlagAndUpdateAjax")
	@ResponseBody public Map<String, Object> getAllOutstockFlagAndUpdateAjax(String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
			mfBusCollateralRel.setAppId(appId);
			String outstockflag = mfBusCollateralRelFeign.getAllCollaOutstockflagAndUpdate(mfBusCollateralRel);
			dataMap.put("outstockflag", outstockflag);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 获得业务关联押品
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-13 下午2:18:22
	 */
	@RequestMapping("/getCollateralListHtmlAjax")
	@ResponseBody public Map<String, Object> getCollateralListHtmlAjax(String relId,String collateralType,String entrance,String cusNo,String busModel) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			TreeMap<String, Object> htmlMap = new TreeMap<String, Object>();
			TreeMap<String, Object> tablehtmlMap = new TreeMap<String, Object>();
			TreeMap<String, String> vouTypeMap = new TreeMap<String, String>();
			Map<String, Object> beanMap = new HashMap<String, Object>();
			Map<String, Object> tableListMap = new HashMap<String, Object>();
			if(StringUtil.isEmpty(collateralType)){
				collateralType = "pledge";
			}
			//request.setAttribute("ifBizManger", "3");
			List<MfBusCollateralDetailRel> list = mfBusCollateralDetailRelFeign.getCollateralDetailRelList(relId,collateralType);
			MfBusCollateralDetailRel collateralDetailRel = new MfBusCollateralDetailRel();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					collateralDetailRel = list.get(i);
					String baseFormId = "";
					String collateralId = collateralDetailRel.getCollateralId();
					dataMap.put(collateralId, collateralDetailRel);
					PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
					pledgeBaseInfo.setPledgeNo(collateralId);
					pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
					FormData collateralBaseForm = null;
					// 如果抵质押信息有值
					if (pledgeBaseInfo != null) {
						MfCollateralFormConfig collateralFormConfig = mfCollateralFormConfigFeign
								.getByPledgeImPawnType(pledgeBaseInfo.getClassId(), "PledgeBaseInfoAction", "");
						if (collateralFormConfig != null) {
							baseFormId = collateralFormConfig.getShowModelDef();
							collateralBaseForm = formService.getFormData(baseFormId);
							if (collateralBaseForm.getFormId() == null) {
								collateralBaseForm = formService.getFormData(collateralFormConfig.getShowModel());
							}
						}
						if(StringUtil.isNotEmpty(collateralDetailRel.getIfRegister())){
							pledgeBaseInfo.setPledgeType(collateralDetailRel.getIfRegister());
						}
						if("C".equals(pledgeBaseInfo.getClassFirstNo())){
							PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
							pledgeBaseInfoBill.setIsHis("0");
							pledgeBaseInfoBill.setPledgeNo(pledgeBaseInfo.getPledgeNo());
							List<PledgeBaseInfoBill> pledgeBaseInfoBillList = pledgeBaseInfoBillFeign.getBillListByPledgeNo(pledgeBaseInfoBill);
							double billSum = 0.00;
							for (int j = 0; j < pledgeBaseInfoBillList.size(); j++) {
								if(pledgeBaseInfoBillList.get(j).getSingleWeight()!=null){
									billSum =MathExtend.add(billSum,pledgeBaseInfoBillList.get(j).getSingleWeight());
								}
							}
							if(pledgeBaseInfo.getExtNum12()!=null&&billSum<pledgeBaseInfo.getExtNum12()){
								pledgeBaseInfo.setExtDic20("1");
							}
						}
						getObjValue(collateralBaseForm, pledgeBaseInfo);
						JsonFormUtil jsonFormUtil = new JsonFormUtil();
						String htmlStr = jsonFormUtil.getJsonStr(collateralBaseForm, "propertySeeTag", "");
						htmlMap.put(collateralId, htmlStr);
						// 封装押品状态描述
						CodeUtils cu = new CodeUtils();
						String extOstr01 = cu.getMapByKeyName("KEEP_STATUS").get(pledgeBaseInfo.getKeepStatus());
						pledgeBaseInfo.setExtOstr01(extOstr01);
						beanMap.put(collateralId, pledgeBaseInfo);
						vouTypeMap.put(collateralId, pledgeBaseInfo.getPledgeMethod());
						MfCollateralTable mfCollateralTable = new MfCollateralTable();
						mfCollateralTable.setCollateralNo(collateralId);
						List<MfCollateralTable> collateralTableList = mfCollateralTableFeign.getList(mfCollateralTable);//信息块是根据押品，每个押品都有信息块（如押品评估，每个押品都有评估信息块）
						if (collateralTableList != null && collateralTableList.size() > 0) {
							dataMap.put("allFullFlag", BizPubParm.YES_NO_Y);
							for (int j = 0; j < collateralTableList.size(); j++) {
								mfCollateralTable = collateralTableList.get(j);
								if (BizPubParm.YES_NO_N.equals(mfCollateralTable.getDataFullFlag())) {
									dataMap.put("allFullFlag", BizPubParm.YES_NO_N);
									break;
								}
							}
						}
						tableListMap.put(collateralId, collateralTableList);
					}
					// 获取业务关联的保证信息
					MfAssureInfo mfAssureInfo = new MfAssureInfo();
					mfAssureInfo.setId(collateralId);
					mfAssureInfo = assureInterfaceFeign.getById(mfAssureInfo);
					if (mfAssureInfo != null) {
						String formId = "assure0002";
						if(StringUtil.isNotEmpty(busModel) && BizPubParm.BUS_MODEL_12.equals(busModel)){
							formId = "assureDetail_GCDB";
						}
						if(StringUtil.isNotEmpty(busModel) && BizPubParm.BUS_MODEL_12.equals(busModel)&&"credit".equals(entrance)){
							formId = "assure00025detail";
						}
						collateralBaseForm = formService.getFormData(formId);
						getObjValue(collateralBaseForm, mfAssureInfo);
						if ("credit".equals(entrance)) {
							//此保证人作为保证人所得到的集合
							MfAssureInfo assureInfo = new MfAssureInfo();
							assureInfo.setAssureNo(mfAssureInfo.getAssureNo());
							List<MfAssureInfo> assureInfos = assureInfoFeign.findAssureInfoById(assureInfo);
							for (MfAssureInfo mfAssureInfoais : assureInfos) {
								mfAssureInfoais.setIsGuarantee("反担保人");
							}
							//此保证人作为借款客户的担保信息
							MfAssureInfo assureInfoCus = new MfAssureInfo();
							assureInfoCus.setCusNo(mfAssureInfo.getAssureNo());
							List<MfAssureInfo> assureInfoCuss = assureInfoFeign.findAssureInfoById(assureInfoCus);
							for (MfAssureInfo mfAssureInfocus : assureInfoCuss) {
								mfAssureInfocus.setIsGuarantee("借款人");
							}
							assureInfos.addAll(assureInfoCuss);
							//保证人配偶的信息查询begin
							MfCusFamilyInfo mfCusFamilyInfoQry = new MfCusFamilyInfo ();
							mfCusFamilyInfoQry.setCusNo(mfAssureInfo.getAssureNo());
							mfCusFamilyInfoQry.setIsAnotherCus(BizPubParm.YES_NO_Y);
							mfCusFamilyInfoQry.setRelative("1");
							mfCusFamilyInfoQry = cusInterfaceFeign.getMfCusFamilyInfo(mfCusFamilyInfoQry);
							if (mfCusFamilyInfoQry != null){
								String coupleNo = mfCusFamilyInfoQry.getAnotherCusNo();
								//此保证人的配偶作为保证人所得到的集合
								MfAssureInfo assureInfoCoupleNo = new MfAssureInfo();
								assureInfoCoupleNo.setAssureNo(coupleNo);
								List<MfAssureInfo> assureInfoCoupleNos = assureInfoFeign.findAssureInfoById(assureInfoCoupleNo);
								for (MfAssureInfo mfAssureInfoais : assureInfoCoupleNos) {
									mfAssureInfoais.setIsGuarantee("配偶反担保人");
								}
								//此保证人的配偶作为借款客户的担保信息
								MfAssureInfo assureInfoCusCoupleNo = new MfAssureInfo();
								assureInfoCusCoupleNo.setCusNo(coupleNo);
								List<MfAssureInfo> assureInfoCusCoupleNos = assureInfoFeign.findAssureInfoById(assureInfoCusCoupleNo);
								for (MfAssureInfo mfAssureInfocus : assureInfoCusCoupleNos) {
									mfAssureInfocus.setIsGuarantee("配偶借款人");
								}
								assureInfoCoupleNos.addAll(assureInfoCusCoupleNos);

								//合并配偶的担保借款集合
								assureInfos.addAll(assureInfoCoupleNos);
							}
//							//保证人配偶的信息查询end
//							List<MfAssureInfo> assureInfos2 = FengZhuangAssureInfo(assureInfos);
//							JsonTableUtil jtu = new JsonTableUtil();
//							String tableFormId = "tablecreditAssureInfoList";
//							String assureInfosStr = jtu.getJsonStr(tableFormId, "tableTag", assureInfos2, null, true);
//							// 找到该客户的客户号
//							tablehtmlMap.put(collateralId, assureInfosStr);
						}

						Map<String, Object> tmpMap = new HashMap<String, Object>();
						if ("2".equals(mfAssureInfo.getAssureType())) {
							tmpMap = cusInterfaceFeign.getCusPersInfo(mfAssureInfo.getAssureNo());
							MfCusPersBaseInfo mfCusPersBaseInfo=(MfCusPersBaseInfo)JsonStrHandling.handlingStrToBean(tmpMap.get("baseInfo"),MfCusPersBaseInfo.class);
							// 邮政编码
							String postalCode = "";
							if (mfCusPersBaseInfo != null) {
								postalCode = mfCusPersBaseInfo.getPostalCode();
								getObjValue(collateralBaseForm, mfCusPersBaseInfo);
							}
							MfCusPersonJob mfCusPersonJob=(MfCusPersonJob)JsonStrHandling.handlingStrToBean(tmpMap.get("jobInfo"),MfCusPersonJob.class);
							if (mfCusPersonJob != null) {
								mfCusPersonJob.setPostalCode(postalCode);
								getObjValue(collateralBaseForm, mfCusPersonJob);
							}
						}else if("1".equals(mfAssureInfo.getAssureType())){
							MfCusCorpBaseInfo mfCusCorpBaseInfo =cusInterfaceFeign.getCusCorpByCusNo(mfAssureInfo.getAssureNo());
							getObjValue(collateralBaseForm,mfCusCorpBaseInfo);
						}
						this.changeFormProperty(collateralBaseForm, "cusName", "initValue", mfAssureInfo.getCusName());
						this.changeFormProperty(collateralBaseForm, "ext1", "initValue", mfAssureInfo.getExt1());
						this.changeFormProperty(collateralBaseForm, "ext2", "initValue", mfAssureInfo.getExt2());
						this.changeFormProperty(collateralBaseForm, "ext3", "initValue", mfAssureInfo.getExt3());
						this.changeFormProperty(collateralBaseForm, "ext4", "initValue", mfAssureInfo.getExt4());
						this.changeFormProperty(collateralBaseForm, "ext5", "initValue", mfAssureInfo.getExt5());
						JsonFormUtil jsonFormUtil = new JsonFormUtil();
						String htmlStr = jsonFormUtil.getJsonStr(collateralBaseForm, "propertySeeTag", "");
						htmlMap.put(collateralId, htmlStr);

						beanMap.put(collateralId, mfAssureInfo);
						vouTypeMap.put(collateralId, BizPubParm.VOU_TYPE_2);
					}
					//如果有最高额担保合同信息
					MfHighGuaranteeContract mfHighGuaranteeContract = new MfHighGuaranteeContract();
					mfHighGuaranteeContract.setHighGrtContractId(collateralId);
					mfHighGuaranteeContract = mfHighGuaranteeContractFeign.getById(mfHighGuaranteeContract);
					if(mfHighGuaranteeContract!=null){
						collateralBaseForm = formService.getFormData("highGrtContract0002");
						JsonFormUtil jsonFormUtil = new JsonFormUtil();
						getObjValue(collateralBaseForm,mfHighGuaranteeContract);
						String htmlStr = jsonFormUtil.getJsonStr(collateralBaseForm, "propertySeeTag", "");
						htmlMap.put(collateralId, htmlStr);
						beanMap.put(collateralId, mfHighGuaranteeContract);
						vouTypeMap.put(collateralId, BizPubParm.VOU_TYPE_10);
					}
				}
				//授信的担保业务才进行列表的查询
				String    assureInfosStrXin = "";
				//借款客户的配偶的担保信息
				String  cusCoupleAssInfo = "";
				if ("credit".equals(entrance)) {
					MfAssureInfo assureInfoCusNo = new MfAssureInfo();
					assureInfoCusNo.setAssureNo(cusNo);
					List<MfAssureInfo> assureInfoCusNos = assureInfoFeign.findAssureInfoById(assureInfoCusNo);
					for (MfAssureInfo mfAssureInfocus : assureInfoCusNos) {
						mfAssureInfocus.setIsGuarantee("反担保人");
					}

					MfCusFamilyInfo mfCusFamilyInfoQry = new MfCusFamilyInfo ();
					mfCusFamilyInfoQry.setCusNo(cusNo);
					mfCusFamilyInfoQry.setIsAnotherCus(BizPubParm.YES_NO_Y);
					mfCusFamilyInfoQry.setRelative("1");
					mfCusFamilyInfoQry = cusInterfaceFeign.getMfCusFamilyInfo(mfCusFamilyInfoQry);
					if (mfCusFamilyInfoQry != null){
						String cusCoupleNo = mfCusFamilyInfoQry.getAnotherCusNo();
						assureInfoCusNo.setAssureNo(cusCoupleNo);
						List<MfAssureInfo> assureInfoCusCoupleNos = assureInfoFeign.findAssureInfoById(assureInfoCusNo);
						for (MfAssureInfo mfAssureInfocus : assureInfoCusCoupleNos) {
							mfAssureInfocus.setIsGuarantee("配偶反担保人");
						}
						assureInfoCusNos.addAll(assureInfoCusCoupleNos);
					}
//					List<MfAssureInfo> assureInfoCusNo2s = FengZhuangAssureInfo(assureInfoCusNos);
//
//					JsonTableUtil jtuXin = new JsonTableUtil();
//					String tableFormIdXin = "tablecreditAssureInfoCusList";
//					assureInfosStrXin = jtuXin.getJsonStr(tableFormIdXin, "tableTag", assureInfoCusNo2s, null, true);
				}
				dataMap.put("assureInfosStrXin", assureInfosStrXin);
				dataMap.put("htmlMap", htmlMap);
				dataMap.put("beanMap", beanMap);
				dataMap.put("vouTypeMap", vouTypeMap);
				dataMap.put("tableListMap", tableListMap);
				dataMap.put("tablehtmlMap", tablehtmlMap);
				dataMap.put("entrance", entrance);
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}




	/**
	 *
	 * <p>Title: FengZhuangAssureInfo</p>
	 * <p>Description:封装保证人所需要的参数 </p>
	 * @param assureInfos
	 * @return
	 * @throws Exception
	 * @author zkq
	 * @date 2018年8月30日 上午10:28:06
	 */
	public List<MfAssureInfo> FengZhuangAssureInfo(List<MfAssureInfo> assureInfos) throws Exception {

		//过滤调是同一笔业务的多个担保人的数据
		Map<String,String> assureCheckMap = null;
		if (assureInfos != null && assureInfos.size() > 0) {
			assureCheckMap = new HashMap<String,String>();
			//运用迭代器可以移除集合的内容
			ListIterator<MfAssureInfo> listIterator = assureInfos.listIterator();
			while (listIterator.hasNext()) {
				MfAssureInfo mfAssureInfo2 = listIterator.next();
				// 查询出保证信息押品关联id
				MfBusCollateralDetailRel mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
				mfBusCollateralDetailRel.setCollateralId(mfAssureInfo2.getId());
				MfBusCollateralDetailRel mfBusCollateralDetailRel2 = mfBusCollateralDetailRelFeign
						.getById(mfBusCollateralDetailRel);
				// 查询出与授信业务相关联的appId通过业务押品关联表
				// 1.判断押品是否被删除
				if (mfBusCollateralDetailRel2 != null) {
					if (assureCheckMap.containsKey(mfBusCollateralDetailRel2.getBusCollateralId())){
						listIterator.remove();
						continue;
					}else {
						assureCheckMap.put(mfBusCollateralDetailRel2.getBusCollateralId(),mfBusCollateralDetailRel2.getBusCollateralId());
					}
					MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
					mfBusCollateralRel.setBusCollateralId(mfBusCollateralDetailRel2.getBusCollateralId());
					MfBusCollateralRel mfBusCollateralRel2 = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
					// 2.判断押品录入的时候是否关联业务
					if (mfBusCollateralRel2 != null) {
						// 通过授信申请的appId判断押品的保证信息是否是从授信入口进行录入的
						MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
						mfCusCreditApply.setCreditAppId(mfBusCollateralRel2.getAppId());
						MfCusCreditApply cusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
						if (cusCreditApply != null) {

							// 通过授信申请的APPID查询授信合同号
							MfCusCreditContract mfCusCreditContract = new MfCusCreditContract();
							mfCusCreditContract.setCreditAppId(mfBusCollateralRel2.getAppId());
							MfCusCreditContract cusCreditContract = mfCusCreditContractFeign
									.getById(mfCusCreditContract);
							mfAssureInfo2.setIsValid(cusCreditApply.getIsValid());
							Double   loanBal = 0.0;
							if (cusCreditContract != null) {
								// 授信起止时间
								mfAssureInfo2.setCreditStartDate(cusCreditContract.getBeginDate());
								mfAssureInfo2.setCreditEndDate(cusCreditContract.getEndDate());
								mfAssureInfo2.setAssureAmt(cusCreditContract.getCreditSum());
								//授信编号
								String creditPactNo = cusCreditContract.getPactNo();
								mfAssureInfo2.setContractId(creditPactNo);

								String creditPactId = cusCreditContract.getId();
								// 授信号合同id是否为空
								if (!"".equals(creditPactId) && StringUtil.isNotEmpty(creditPactId)) {
									MfBusApply mfBusApply = new MfBusApply();
									mfBusApply.setCreditPactId(creditPactId);
									List<MfBusApply> mfBusApplys = mfBusApplyFeign
											.getMfBusApplyByCreditPactId(mfBusApply);
									// 判断客户是否申请过业务

									if (mfBusApplys != null && mfBusApplys.size() > 0) {
										for (MfBusApply busApply : mfBusApplys) {
											String busPactId = busApply.getPactId();
											//判断该客户是否和银行签订过合同
											if (!"".equals(busPactId) && StringUtil.isNotEmpty(busPactId)) {
												MfFundChannelFinc mfFundChannelFinc = new MfFundChannelFinc();
												mfFundChannelFinc.setPactId(busPactId);
												MfFundChannelFinc mfFundChannelFincByPactId = mfChannelBusFeign
														.getMfFundChannelFincByPactId(mfFundChannelFinc);
												if (mfFundChannelFincByPactId != null) {
													loanBal  +=	mfFundChannelFincByPactId.getLoanBal();
												} else {
													MfBusPact busPact = mfBusPactFeign.getByAppId(busApply.getAppId());
													loanBal  +=  busPact.getPactAmt();
												}
											} else {
												loanBal  += 0.0;
											}
										}
										mfAssureInfo2.setLoanBal(loanBal);
									} else {
										mfAssureInfo2.setLoanBal(loanBal);
									}
								} else {
									mfAssureInfo2.setLoanBal(loanBal);
								}

							} else {
								mfAssureInfo2.setLoanBal(loanBal);
								mfAssureInfo2.setAssureAmt(0.0);
							}

							// 根据客户号查询出借款客户的证件号码
							MfCusCustomer mfCusCustomer = new MfCusCustomer();
							mfCusCustomer.setCusNo(mfAssureInfo2.getCusNo());
							MfCusCustomer mfCusCustomer2 = mfCusCustomerFeign.getById(mfCusCustomer);
							if (mfCusCustomer2 != null) {
								mfAssureInfo2.setCusIdNum(mfCusCustomer2.getIdNum());
							}

						} else {
							//此保证信息不是从授信入口录入的移除(业务)
							listIterator.remove();
						}

					} else {
						//此保证信息不是从授信入口录入的移除(资产)
						listIterator.remove();
					}
				} else {
					//此保证信息已经被删除
					listIterator.remove();
				}

			}

		}
		return assureInfos;
	}

	@RequestMapping("/updateInfoAjax")
	@ResponseBody public Map<String, Object> updateInfoAjax(String appId,String relId, String collateralType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String query = "";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> htmlMap = new HashMap<String, Object>();
			Map<String, Object> beanMap = new HashMap<String, Object>();
			if(StringUtil.isEmpty(collateralType)){
				collateralType = "pledge";
			}
			List<MfBusCollateralDetailRel> list = mfBusCollateralDetailRelFeign.getCollateralDetailRelList(relId,collateralType);
			MfBusCollateralDetailRel collateralDetailRel = new MfBusCollateralDetailRel();
			MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			// 业务提交流程中或审批完成押品信息不能编辑
			if (mfBusApply != null) {
				if (BizPubParm.APP_STS_PROCESS.equals(mfBusApply.getAppSts())
						|| BizPubParm.APP_STS_SEND_BACK.equals(mfBusApply.getAppSts())
						|| BizPubParm.APP_STS_PASS.equals(mfBusApply.getAppSts())) {
					query = "query";
				}
			}
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					collateralDetailRel = list.get(i);
					String baseFormId = "";
					String collateralId = collateralDetailRel.getCollateralId();
					PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
					pledgeBaseInfo.setPledgeNo(collateralId);
					pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
					MfCollateralFormConfig collateralFormConfig = mfCollateralFormConfigFeign
							.getByPledgeImPawnType(pledgeBaseInfo.getClassId(), "PledgeBaseInfoAction", "");
					FormData collateralBaseForm = null;
					if (collateralFormConfig != null) {
						baseFormId = collateralFormConfig.getShowModelDef();
						collateralBaseForm = formService.getFormData(baseFormId);
						if (collateralBaseForm.getFormId() == null) {
							collateralBaseForm = formService.getFormData(collateralFormConfig.getShowModel());
						}
					}
					getObjValue(collateralBaseForm, pledgeBaseInfo);
					JsonFormUtil jsonFormUtil = new JsonFormUtil();
					String htmlStr = jsonFormUtil.getJsonStr(collateralBaseForm, "propertySeeTag", query);
					htmlMap.put(collateralId, htmlStr);
					beanMap.put(collateralId, pledgeBaseInfo);
				}
			}
			dataMap.put("htmlMap", htmlMap);
			dataMap.put("beanMap", beanMap);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/getPledgeListHtmlAjax")
	@ResponseBody
	public Map<String, Object> getPledgeListHtmlAjax(String appId, String pledgeNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String query = "";
		try {
			MfBusPact mfBusPact = pactInterfaceFeign.getByAppId(appId);
			// 业务提交流程中或审批完成押品信息不能编辑
			if (mfBusPact != null) {
				if (BizPubParm.PACT_STS_FINISH.equals(mfBusPact.getPactSts())
						|| BizPubParm.PACT_STS_SEAL.equals(mfBusPact.getPactSts())) {
					query = "query";
				}
			}
			if(appId != null && !appId.contains("SX")){
				MfBusApply mfBusApply = new MfBusApply();
				mfBusApply.setAppId(appId);
				mfBusApply = appInterfaceFeign.getMfBusApply(mfBusApply);
				//判断押品表单信息是否允许编辑
				query = cusInterfaceFeign.validateCusFormModify(mfBusApply.getCusNo(),mfBusApply.getAppId(),BizPubParm.FORM_EDIT_FLAG_PLE,User.getRegNo(request));
			}
			if(query==null) {
				query="";
			}
			request.setAttribute("ifBizManger", "3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			JsonTableUtil jtu = new JsonTableUtil();
			MfCollateralTable mfCollateralTable = new MfCollateralTable();
			mfCollateralTable.setCollateralNo(pledgeNo);
			List<MfCollateralTable> collateralTableList = mfCollateralTableFeign.getList(mfCollateralTable);
			if (collateralTableList != null && collateralTableList.size() > 0) {
				for (int i = 0; i < collateralTableList.size(); i++) {
					mfCollateralTable = collateralTableList.get(i);
					if ("0".equals(mfCollateralTable.getDataFullFlag())) {
						continue;
					}
					String action = mfCollateralTable.getAction();
					String htmlStr = "";
					String tableId = "table" + mfCollateralTable.getShowModelDef();
					String formId = mfCollateralTable.getShowModelDef();
					FormData formcommon = formService.getFormData(formId);
					if (formcommon.getFormId() == null) {
						if (formId.indexOf("_") != -1) {
							Integer num = formId.indexOf("_");
							formId = formId.substring(0, num);
						}
						formcommon = formService.getFormData(formId);
					}
					getFormValue(formcommon);
					if ("PledgeBaseInfoAction".equals(action)) {
						PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
						pledgeBaseInfo.setPledgeNo(pledgeNo);
						pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
						getObjValue(formcommon, pledgeBaseInfo);

						//获取采集信息表单信息
						Map<String, Object> dataMap_ = mfBusCollateralRelFeign.getInfoCollectFormInfo("", "", "", "", pledgeBaseInfo.getPledgeMethod(), "collateral");
						List<Map> list_tmp = (List<Map>) dataMap_.get("collClass");
						List<OptionsList> vouTypeList = new ArrayList<OptionsList>();
						for (Map map : list_tmp) {
							OptionsList options = new OptionsList();
							options.setOptionLabel((String) map.get("classSecondName"));
							options.setOptionValue((String) map.get("classId"));
							vouTypeList.add(options);
						}
						this.changeFormProperty(formcommon, "classId", "optionArray", vouTypeList);

						htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
					} else if ("PledgeBaseInfoBillAction".equals(action)) {// table
						PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
						pledgeBaseInfoBill.setPledgeNo(pledgeNo);
						pledgeBaseInfoBill.setIsHis("0");
						Ipage ipage = this.getIpage();
						ipage.setPageSize(1000);
						ipage.setParams(this.setIpageParams("pledgeBaseInfoBill",pledgeBaseInfoBill));
						htmlStr = jtu.getJsonStr(tableId, "tableTag", (List<PledgeBaseInfoBill>) pledgeBaseInfoBillFeign
								.findByPageNotRegister(ipage).getResult(), null, true);
					} else if ("EvalInfoAction".equals(action)) {// table
						EvalInfo evalInfo = new EvalInfo();
						evalInfo.setCollateralId(pledgeNo);
						Ipage ipage = this.getIpage();
						ipage.setParams(this.setIpageParams("evalInfo",evalInfo));
						htmlStr = jtu.getJsonStr(tableId, "tableTag",
								(List<EvalInfo>) evalInfoFeign.findByPage(ipage).getResult(), null, true);
					} else if ("FairInfoAction".equals(action)) {// form
						FairInfo fairInfo = new FairInfo();
						fairInfo.setCollateralId(pledgeNo);
						fairInfo = fairInfoFeign.getByCollateralId(fairInfo);
						getObjValue(formcommon, fairInfo);
						htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
					} else if ("MfBusGpsRegAction".equals(action)) { // GPS安装
						MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
						mfBusGpsReg.setRelNo(pledgeNo);
						Ipage ipage = this.getIpage();
						ipage.setParams(this.setIpageParams("mfBusGpsReg", mfBusGpsReg));
						List<MfBusGpsReg> mfBusGpsReglist = (List<MfBusGpsReg>) mfBusGpsRegFeign.findByPage(ipage).getResult();
						htmlStr = jtu.getJsonStr(tableId, "tableTag", mfBusGpsReglist, null, true);
					} else if ("MfCarCheckFormAction".equals(action)) { // 验车单
						MfCarCheckForm mfCarCheckForm = new MfCarCheckForm();
						mfCarCheckForm.setPledgeNo(pledgeNo);
						Ipage ipage = this.getIpage();
						ipage.setParams(this.setIpageParams("mfCarCheckForm", mfCarCheckForm));
						List<MfCarCheckForm> mfCarCheckFormList = (List<MfCarCheckForm>) mfCarCheckFormFeign.findByPage(ipage).getResult();
						tableId = "tablecarCheckFormList";
						htmlStr = jtu.getJsonStr(tableId,"tableTag", mfCarCheckFormList, null, true);
					}  else if ("InsInfoAction".equals(action)) {// table
						InsInfo insInfo = new InsInfo();
						insInfo.setCollateralId(pledgeNo);
						Ipage ipage = this.getIpage();
						ipage.setParams(this.setIpageParams("insInfo",insInfo));
						htmlStr = jtu.getJsonStr(tableId, "tableTag",
								(List<InsInfo>) insInfoFeign.findByPage(ipage).getResult(), null, true);
					} else if ("CertiInfoAction".equals(action)) {// form
						CertiInfo certiInfo = new CertiInfo();
						certiInfo.setCollateralId(pledgeNo);
						certiInfo = certiInfoFeign.getByCollateralId(certiInfo);
						getObjValue(formcommon, certiInfo);
						htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", "");
					} else if ("ChkInfoAction".equals(action)) {// table
						ChkInfo chkInfo = new ChkInfo();
						chkInfo.setCollateralId(pledgeNo);
						Ipage ipage = this.getIpage();
						ipage.setParams(this.setIpageParams("chkInfo",chkInfo));
						htmlStr = jtu.getJsonStr(tableId, "tableTag",
								(List<ChkInfo>) chkInfoFeign.findByPage(ipage).getResult(), null, true);
					} else if ("MfMoveableCheckInventoryInfoAction".equals(action)) {// form
						MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo = new MfMoveableCheckInventoryInfo();
						mfMoveableCheckInventoryInfo.setPledgeNo(pledgeNo);
						mfMoveableCheckInventoryInfo = mfMoveableCheckInventoryInfoFeign
								.getById(mfMoveableCheckInventoryInfo);
						formcommon = formService.getFormData(formId);
						getFormValue(formcommon);
						getObjValue(formcommon, mfMoveableCheckInventoryInfo);
						htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
					}else if("MfMaintenanceAction".equals(action)){
						MfMaintenance mfMaintenance = new MfMaintenance();
						mfMaintenance.setCollateralId(pledgeNo);
						Ipage ipage = this.getIpage();
						ipage.setParams(this.setIpageParams("mfMaintenance", mfMaintenance));
						htmlStr = jtu.getJsonStr(tableId, "tableTag",
								(List<MfMaintenance>) mfMaintenanceFeign.findByPage(ipage).getResult(), null, true);
					}else if("MfCollateralInsuranceClaimsAction".equals(action)){
						MfCollateralInsuranceClaims mfCollateralInsuranceClaims2 = new MfCollateralInsuranceClaims();
						mfCollateralInsuranceClaims2.setCollateralId(pledgeNo);
						Ipage ipage = this.getIpage();
						ipage.setParams(this.setIpageParams("mfCollateralInsuranceClaims", mfCollateralInsuranceClaims2));
						htmlStr =jtu.getJsonStr(tableId, "tableTag",(List<MfCollateralInsuranceClaims>) mfCollateralInsuranceClaimsFeign.findByPage(ipage).getResult(), null, true);
					}else if ("MfAccntRepayDetailAction".equals(action)) {// table
						MfAccntRepayDetail mfAccntRepayDetail = new MfAccntRepayDetail();
						mfAccntRepayDetail.setTransferId(pledgeNo);
						Ipage ipage = this.getIpage();
						ipage.setParams(this.setIpageParams("mfAccntRepayDetail",mfAccntRepayDetail));
						htmlStr = jtu.getJsonStr(tableId, "tableTag",
								(List<MfAccntRepayDetail>) mfAccntRepayDetailFeign.findByPage(ipage).getResult(), null, true);
					}else {
					}

					collateralTableList.get(i).setHtmlStr(htmlStr);
				}
			}
			dataMap.put("collateralTableList", collateralTableList);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
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
	 *
	 * 方法描述： 获得新增的押品的基本信息表单html
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-13 下午7:20:41
	 */
	@RequestMapping("/getBaseCollateralHtmlAjax")
	@ResponseBody public Map<String, Object> getBaseCollateralHtmlAjax(String collateralId,String entrance,String cusNo,String num,String busModel) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			request.setAttribute("ifBizManger", "3");
			String query = "query";
			//判断押品表单信息是否允许编辑
			String appId = mfBusCollateralRelFeign.getAppIdByCollateralId(collateralId);
			MfBusApply mfBusApply = new MfBusApply();
			String formId = null;
			//此处为授信流程时，appId存的是授信相关信息
			if (StringUtil.isNotEmpty(appId)) {
				mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
				if(mfBusApply!=null){
					query = cusInterfaceFeign.validateCusFormModify(mfBusApply.getCusNo(), mfBusApply.getAppId(), BizPubParm.FORM_EDIT_FLAG_PLE, User.getRegNo(request));
				}
				if (query == null) {
					query = "query";
				}
			}
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(collateralId);
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			if (pledgeBaseInfo != null) {
				MfCollateralFormConfig collateralFormConfig = mfCollateralFormConfigFeign
						.getByPledgeImPawnType(pledgeBaseInfo.getClassId(), "PledgeBaseInfoAction", "");
				String baseFormId = "";
				if (collateralFormConfig != null) {
					baseFormId = collateralFormConfig.getShowModelDef();
				}
				FormData collateralBaseForm = formService.getFormData(baseFormId);
				if (collateralBaseForm.getFormId() == null) {
					collateralBaseForm = formService.getFormData(collateralFormConfig.getShowModel());
				}
				getFormValue(collateralBaseForm);
				getObjValue(collateralBaseForm, pledgeBaseInfo);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(collateralBaseForm, "propertySeeTag", query);
				MfCollateralTable mfCollateralTable = new MfCollateralTable();
				mfCollateralTable.setCollateralNo(collateralId);
				List<MfCollateralTable> collateralTableList = mfCollateralTableFeign.getList(mfCollateralTable);
				dataMap.put("collateralTableList", collateralTableList);
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("classId", pledgeBaseInfo.getClassId());
				dataMap.put("vouType", pledgeBaseInfo.getPledgeMethod());
				// 封装押品状态描述
				CodeUtils cu = new CodeUtils();
				String extOstr01 = cu.getMapByKeyName("KEEP_STATUS").get(pledgeBaseInfo.getKeepStatus());
				pledgeBaseInfo.setExtOstr01(extOstr01);
				dataMap.put("pledgeBaseInfo", pledgeBaseInfo);
			}
			// 获取业务关联的保证信息
			MfAssureInfo mfAssureInfo = new MfAssureInfo();
			mfAssureInfo.setId(collateralId);
			mfAssureInfo = assureInterfaceFeign.getById(mfAssureInfo);
			if (mfAssureInfo != null) {
				formId = "assure0002";
				if(StringUtil.isNotEmpty(busModel) && BizPubParm.BUS_MODEL_12.equals(busModel)&&"credit".equals(entrance)){
					formId = "assure00025detail";
				}
				if(StringUtil.isNotEmpty(busModel) && BizPubParm.BUS_MODEL_12.equals(busModel)&&!"credit".equals(entrance)){
					formId = "assureDetail_GCDB";
				}
				FormData collateralBaseForm = formService.getFormData(formId);
				if ("credit".equals(entrance)) {
					MfAssureInfo assureInfo = new MfAssureInfo();
					assureInfo.setAssureNo(mfAssureInfo.getAssureNo());
					List<MfAssureInfo> assureInfos = assureInfoFeign.findAssureInfoById(assureInfo);

					for (MfAssureInfo mfAssureInfoais : assureInfos) {
						mfAssureInfoais.setIsGuarantee("反担保人");
					}
					//此保证人作为借款客户的担保信息
					MfAssureInfo assureInfoCus = new MfAssureInfo();
					assureInfoCus.setCusNo(mfAssureInfo.getAssureNo());
					List<MfAssureInfo> assureInfoCuss = assureInfoFeign.findAssureInfoById(assureInfoCus);
					for (MfAssureInfo mfAssureInfocus : assureInfoCuss) {
						mfAssureInfocus.setIsGuarantee("借款人");
					}
					//合并两个集合

					assureInfos.addAll(assureInfoCuss);


//					List<MfAssureInfo> assureInfos2 = FengZhuangAssureInfo(assureInfos);
//					JsonTableUtil jtu = new JsonTableUtil();
//					String tableFormId = "tablecreditAssureInfoList";
//					String assureInfosStr = jtu.getJsonStr(tableFormId, "tableTag", assureInfos2, null, true);
//					// 找到该客户的客户号
//					dataMap.put("assureInfosStr", assureInfosStr);
				}


				getObjValue(collateralBaseForm, mfAssureInfo);


				Map<String, Object> tmpMap = new HashMap<String, Object>();
				if ("2".equals(mfAssureInfo.getAssureType())) {
					tmpMap = cusInterfaceFeign.getCusPersInfo(mfAssureInfo.getAssureNo());
					MfCusPersBaseInfo mfCusPersBaseInfo=(MfCusPersBaseInfo)JsonStrHandling.handlingStrToBean(tmpMap.get("baseInfo"),MfCusPersBaseInfo.class);
					// 邮政编码
					String postalCode = "";
					if (mfCusPersBaseInfo != null) {
						postalCode = mfCusPersBaseInfo.getPostalCode();
						getObjValue(collateralBaseForm, mfCusPersBaseInfo);
					}
					MfCusPersonJob mfCusPersonJob=(MfCusPersonJob)JsonStrHandling.handlingStrToBean(tmpMap.get("jobInfo"),MfCusPersonJob.class);
					if (mfCusPersonJob != null) {
						mfCusPersonJob.setPostalCode(postalCode);
						getObjValue(collateralBaseForm, mfCusPersonJob);
					}
				}
				this.changeFormProperty(collateralBaseForm, "cusName", "initValue", mfAssureInfo.getCusName());
				this.changeFormProperty(collateralBaseForm, "ext1", "initValue", mfAssureInfo.getExt1());
				this.changeFormProperty(collateralBaseForm, "ext2", "initValue", mfAssureInfo.getExt2());
				this.changeFormProperty(collateralBaseForm, "ext3", "initValue", mfAssureInfo.getExt3());
				this.changeFormProperty(collateralBaseForm, "ext4", "initValue", mfAssureInfo.getExt4());
				this.changeFormProperty(collateralBaseForm, "ext5", "initValue", mfAssureInfo.getExt5());

				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(collateralBaseForm, "propertySeeTag", query);
				/*dataMap.put("tablehtmlMap", tablehtmlMap);*/
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("vouType", "2");
			}
			//如果有最高额担保合同信息
			MfHighGuaranteeContract mfHighGuaranteeContract = new MfHighGuaranteeContract();
			mfHighGuaranteeContract.setHighGrtContractId(collateralId);
			mfHighGuaranteeContract = mfHighGuaranteeContractFeign.getById(mfHighGuaranteeContract);
			if(mfHighGuaranteeContract!=null){
				FormData collateralBaseForm = formService.getFormData("highGrtContract0002");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				getObjValue(collateralBaseForm,mfHighGuaranteeContract);
				String htmlStr = jsonFormUtil.getJsonStr(collateralBaseForm, "propertySeeTag", "");
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("vouType", "10");
				dataMap.put("mfHighGuaranteeContract", "mfHighGuaranteeContract");
			}
			dataMap.put("entrance", entrance);
			dataMap.put("flag", "success");
			dataMap.put("num",num);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 获得押品新增表单内容
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-2 下午7:06:52
	 */
	@RequestMapping("/getAddPledgeBaseHtmlAjax")
	@ResponseBody public Map<String, Object> getAddPledgeBaseHtmlAjax(String pledgeNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(pledgeNo);
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			MfCollateralFormConfig collateralFormConfig = mfCollateralFormConfigFeign
					.getByPledgeImPawnType(pledgeBaseInfo.getClassId(), "PledgeBaseInfoAction", "");
			String baseFormId = collateralFormConfig.getAddModelDef();
			FormData collateralBaseForm = formService.getFormData(baseFormId);
			if (collateralBaseForm.getFormId() == null) {
				collateralBaseForm = formService.getFormData(collateralFormConfig.getAddModel());
			}
			getFormValue(collateralBaseForm);
			// 担保方式
			List<OptionsList> vouTypeList = new ArrayList<OptionsList>();
			Map<String, ParmDic> vouTypeMap = new CodeUtils().getMapObjByKeyName("VOU_TYPE");
			OptionsList op = new OptionsList();
			op.setOptionLabel(vouTypeMap.get(BizPubParm.VOU_TYPE_4).getOptName());
			op.setOptionValue(BizPubParm.VOU_TYPE_4);
			vouTypeList.add(op);
			vouTypeMap.remove(BizPubParm.VOU_TYPE_4);
			for (String key : vouTypeMap.keySet()) {
				ParmDic parmDic = vouTypeMap.get(key);
				if (!BizPubParm.VOU_TYPE_1.equals(parmDic.getOptCode())
						&& !BizPubParm.VOU_TYPE_2.equals(parmDic.getOptCode())) {
					op = new OptionsList();
					op.setOptionLabel(parmDic.getOptName());
					op.setOptionValue(parmDic.getOptCode());
					vouTypeList.add(op);
				}
			}
			// 设置表单元素不可编辑
			FormActive[] list = collateralBaseForm.getFormActives();
			for (int i = 0; i < list.length; i++) {
				FormActive formActive = list[i];
				formActive.setReadonly("1");
				list[i].setOnclick("disabled");//设置时间不可点击
				list[i].setUnit("");
			}
			getObjValue(collateralBaseForm, pledgeBaseInfo);
			changeFormProperty(collateralBaseForm, "pledgeMethod", "optionArray", vouTypeList);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String htmlStr = jsonFormUtil.getJsonStr(collateralBaseForm, "bootstarpTag", "");
			dataMap.put("htmlStr", htmlStr);
			MfCollateralClass mfCollateralClass = new MfCollateralClass();
			List<MfCollateralClass> collateralClasslist = mfCollateralClassFeign.getAll(mfCollateralClass);
			List<MfCollateralClass> collateralClasslistNew = new ArrayList<MfCollateralClass>();

			// 根据担保方式过滤押品类别
			for (int i = 0; i < collateralClasslist.size(); i++) {
				boolean flag = false;
				MfCollateralClass mfCollateralClass2 = collateralClasslist.get(i);
				// 押品类别的担保方式等于当前担保方式范围之内的
				for (int j = 0; j < vouTypeList.size(); j++) {
					if (mfCollateralClass2.getVouType().indexOf(vouTypeList.get(j).getOptionValue()) != -1) {
						flag = true;
					}
				}
				if (flag) {
					collateralClasslistNew.add(mfCollateralClass2);
				}
			}
			// 押品选择组件
			JSONArray collClassArray = JSONArray.fromObject(collateralClasslistNew);
			for (int i = 0; i < collClassArray.size(); i++) {
				collClassArray.getJSONObject(i).put("id", collClassArray.getJSONObject(i).getString("classId"));
				collClassArray.getJSONObject(i).put("name",
						collClassArray.getJSONObject(i).getString("classSecondName"));
			}
			dataMap.put("collClass", collClassArray);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 业务流程中登记押品信息保存
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-11 下午5:25:48
	 */
	@RequestMapping("/insertCollateralAjax")
	@ResponseBody public Map<String, Object> insertCollateralAjax(String ajaxData, String isQuote, String appId,
																  String extensionApplyId, String entrFlag, String skipFlag,String collateralType,String parel,String nodeNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			//判断当前节点是否可进行审批
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply.setAppId(appId);
			mfBusApply = mfBusApplyFeign.getById(mfBusApply);
			MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
			mfBusExtensionApply.setAppId(appId);
			List<MfBusExtensionApply> mfBusExtensionApplyList = mfBusExtensionApplyFeign.getByAppId(mfBusExtensionApply);
			TaskImpl taskApprove = null;
			if(mfBusExtensionApplyList !=null && mfBusExtensionApplyList.size()>0){
				taskApprove = wkfInterfaceFeign.getTask(mfBusExtensionApplyList.get(0).getWkfAppId(), "");
			}else if(mfBusApply!=null){
				taskApprove = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), "");
			}else {
			}
			if("business".equals(entrFlag) && !nodeNo.equals(taskApprove.getActivityName())){
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FIRST_CHECK_APPROVEFLOW.getMessage());
				return dataMap;
			}
			if(StringUtil.isEmpty(collateralType)){
				collateralType = "pledge";
			}
			dataMap = getMapByJson(ajaxData);
			String classId = String.valueOf(dataMap.get("classId"));
			String pledgeShowNo = String.valueOf(dataMap.get("pledgeShowNo"));
			// 检查押品展示号重复
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeShowNo(pledgeShowNo);
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			if (pledgeBaseInfo != null && !"1".equals(isQuote)) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_PLEDGESHOWNO_REPETITION.getMessage());
				return dataMap;
			}
			MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,"PledgeBaseInfoAction", "");
			String formId = "";
			if (mfCollateralFormConfig != null) {
				formId = mfCollateralFormConfig.getAddModelDef();
			}
			FormData formdlcollateralbase0004 = formService.getFormData(formId);
			if (formdlcollateralbase0004.getFormId() == null) {
				formId = mfCollateralFormConfig.getAddModel();
				formdlcollateralbase0004 = formService.getFormData(formId);
			}
			getFormValue(formdlcollateralbase0004, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formdlcollateralbase0004) || BizPubParm.YES_NO_Y.equals(skipFlag)) {
				pledgeBaseInfo = new PledgeBaseInfo();
				setObjValue(formdlcollateralbase0004, pledgeBaseInfo);
				MfCollateralClass mfCollateralClass = new MfCollateralClass();
				mfCollateralClass.setClassId(classId);
				mfCollateralClass = mfCollateralClassFeign.getById(mfCollateralClass);
				pledgeBaseInfo.setClassFirstNo(mfCollateralClass.getClassFirstNo());
				pledgeBaseInfo.setClassSecondName(mfCollateralClass.getClassSecondName());
				//因存单没有押品评估，在此处对现金及其等价物(存单)进行押品价值计算。
				if("A01".equals(mfCollateralClass.getClassSecondNo())){
					pledgeBaseInfo.setPleValue(MathExtend.multiply(pledgeBaseInfo.getPleOriginalValue(),MathExtend.divide(pledgeBaseInfo.getPledgeRate(),100)));
				}
				dataMap.put("appId", appId);
				dataMap.put("extensionApplyId", extensionApplyId);
				dataMap.put("isQuote", isQuote);
				dataMap.put("entrFlag", entrFlag);
				dataMap.put("skipFlag", skipFlag);
				dataMap.put("collateralType", collateralType);
				EvalInfo evalInfo = new EvalInfo();
				setObjValue(formdlcollateralbase0004, evalInfo);
				if (evalInfo.getEvalDate() != null && !"".equals(evalInfo.getEvalDate())) {
					dataMap.put("evalInfo", evalInfo);
				}
				String opNo = User.getRegNo(request);
				dataMap.put("pledgeBaseInfo", new Gson().toJson(pledgeBaseInfo));
				dataMap.put("opNo", opNo);
				String projectName =ymlConfig.getSysParams().get("sys.project.name");
				dataMap = mfBusCollateralRelFeign.insertCollateral(dataMap);
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
	 *
	 * 方法描述：车管验车中关联押品信息
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 段泽宇
	 * @date 2018-7-6 下午5:25:48
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/relationCollateral")
	@ResponseBody public Map<String, Object> relationCollateral(String isQuote, String appId,
																String extensionApplyId, String entrFlag, String skipFlag,String pledgeNo,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			//客户信息
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			String pledgeNoT = pledgeNo;
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(pledgeNo);
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			if(mfCusCustomer!=null){
				pledgeBaseInfo.setCusNo(mfCusCustomer.getCusNo());
				pledgeBaseInfo.setCusName(mfCusCustomer.getCusName());
				//押品下关联信息修改客户名称
				pledgeBaseInfoFeign.updateRelCusName(pledgeBaseInfo);
			}
			String classId = pledgeBaseInfo.getClassId();
			MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
					"PledgeBaseInfoAction", "");
			String formId = "";
			String baseFormId = mfCollateralFormConfig.getShowModelDef();
			FormData collateralBaseForm = formService.getFormData(baseFormId);
			if (collateralBaseForm.getFormId() == null) {
				collateralBaseForm = formService.getFormData(mfCollateralFormConfig.getShowModel());
			}
			getFormValue(collateralBaseForm);
			// 担保方式
			List<OptionsList> vouTypeList = new ArrayList<OptionsList>();
			Map<String, ParmDic> vouTypeMap = new CodeUtils().getMapObjByKeyName("VOU_TYPE");
			OptionsList op = new OptionsList();
			op.setOptionLabel(vouTypeMap.get(BizPubParm.VOU_TYPE_4).getOptName());
			op.setOptionValue(BizPubParm.VOU_TYPE_4);
			vouTypeList.add(op);
			vouTypeMap.remove(BizPubParm.VOU_TYPE_4);
			for (String key : vouTypeMap.keySet()) {
				ParmDic parmDic = vouTypeMap.get(key);
				if (!BizPubParm.VOU_TYPE_1.equals(parmDic.getOptCode())
						&& !BizPubParm.VOU_TYPE_2.equals(parmDic.getOptCode())) {
					op = new OptionsList();
					op.setOptionLabel(parmDic.getOptName());
					op.setOptionValue(parmDic.getOptCode());
					vouTypeList.add(op);
				}
			}
			getObjValue(collateralBaseForm, pledgeBaseInfo);
			changeFormProperty(collateralBaseForm, "pledgeMethod", "optionArray", vouTypeList);
			List<String> htmlStrList = new ArrayList<String>();
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String htmlStrPledge = jsonFormUtil.getJsonStr(collateralBaseForm, "propertySeeTag", "");
			htmlStrList.add(htmlStrPledge);
			// 查询已经录入信息的表单
			MfCollateralTable mfCollateralTableNew = new MfCollateralTable();
			mfCollateralTableNew.setCollateralNo(pledgeNo);
			List<MfCollateralTable> collateralTableList = mfCollateralTableFeign.getList(mfCollateralTableNew);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableId = "";

			for (int i = 0; i < collateralTableList.size(); i++) {
				/*if (collateralTableList.get(i).getDataFullFlag().equals("0")) {
					continue;
				}*/
				String action = collateralTableList.get(i).getAction();
				String htmlStr = "";
				tableId = "table" + collateralTableList.get(i).getShowModelDef();
				formId = collateralTableList.get(i).getShowModelDef();
				FormData formcommon = formService.getFormData(formId);
				if (formcommon.getFormId() == null) {
					if (formId.indexOf("_") != -1) {
						Integer num = formId.indexOf("_");
						formId = formId.substring(0, num);
					}
				}
				if ("PledgeBaseInfoAction".equals(action)) {
					formcommon = formService.getFormData(formId);
					getFormValue(formcommon);
					getObjValue(formcommon, pledgeBaseInfo);
					htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", "");
				}else if ("MfCarCheckFormAction".equals(action)) { // 验车单
					formcommon = formService.getFormData(formId);
					getFormValue(formcommon);
					MfCarCheckForm mfCarCheckForm = new MfCarCheckForm();
					mfCarCheckForm.setPledgeNo(pledgeNo);
					mfCarCheckForm = mfCarCheckFormFeign.getById(mfCarCheckForm);
					getObjValue(formcommon, mfCarCheckForm);
					htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", "");
					if(mfCarCheckForm == null){
						htmlStr = " ";
					}
				} else if ("InsInfoAction".equals(action)) {// table
					InsInfo insInfo = new InsInfo();
					insInfo.setCollateralId(pledgeNo);
					Ipage ipage = this.getIpage();
					ipage.setParams(this.setIpageParams("insInfo", insInfo));
					htmlStr = jtu.getJsonStr(tableId, "tableTag",
							(List<InsInfo>) insInfoFeign.findByPage(ipage).getResult(), null, true);
					List<InsInfo> insInfoList = insInfoFeign.getListByCollateralId(insInfo);
					if(null == insInfoList || insInfoList.size()==0){
						htmlStr = " ";
					}
				} else if ("MfBusGpsRegAction".equals(action)) { // GPS安装
					MfBusGpsReg mfBusGpsReg = new MfBusGpsReg();
					mfBusGpsReg.setRelNo(pledgeNo);
					Ipage ipage = this.getIpage();
					ipage.setParams(this.setIpageParams("mfBusGpsReg", mfBusGpsReg));
					htmlStr = jtu.getJsonStr(tableId, "tableTag",
							(List<MfBusGpsReg>) mfBusGpsRegFeign.findByPage(ipage).getResult(), null, true);
					List<MfBusGpsReg> mfBusGpsRegList = mfBusGpsRegFeign.getListById(mfBusGpsReg);
					if(null == mfBusGpsRegList || mfBusGpsRegList.size()==0){
						htmlStr = " ";
					}
				}else {
				}
				collateralTableList.get(i).setHtmlStr(htmlStr);
			}
			dataMap.put("collateralTableList", collateralTableList);
			if (mfCollateralFormConfig != null) {
				formId = mfCollateralFormConfig.getAddModelDef();
			}
			FormData formdlcollateralbase0004 = formService.getFormData(formId);
			if (formdlcollateralbase0004.getFormId() == null) {
				formId = mfCollateralFormConfig.getAddModel();
				formdlcollateralbase0004 = formService.getFormData(formId);
			}
			MfCollateralClass mfCollateralClass = new MfCollateralClass();
			mfCollateralClass.setClassId(classId);
			mfCollateralClass = mfCollateralClassFeign.getById(mfCollateralClass);
			pledgeBaseInfo.setClassFirstNo(mfCollateralClass.getClassFirstNo());
			pledgeBaseInfo.setClassSecondName(mfCollateralClass.getClassSecondName());
			MfCollateralTable mfCollateralTable = new MfCollateralTable();
			mfCollateralTable.setCollateralNo(pledgeBaseInfo.getPledgeNo());
			mfCollateralTable.setCollateralType(mfCollateralFormConfig.getFormType());
			mfCollateralTable.setDataFullFlag("0");
			if("PledgeBaseInfoAction".equals(mfCollateralFormConfig.getAction())){
				mfCollateralTable.setDataFullFlag("1");
			}
			mfCollateralTable.setDelFlag("0");
			mfCollateralTable.setTableName(mfCollateralFormConfig.getTableName());
			mfCollateralTableFeign.update(mfCollateralTable);
			dataMap.put("appId", appId);
			dataMap.put("extensionApplyId", extensionApplyId);
			dataMap.put("isQuote", isQuote);
			dataMap.put("entrFlag", entrFlag);
			dataMap.put("skipFlag", skipFlag);
			EvalInfo evalInfo = new EvalInfo();
			getObjValue(formdlcollateralbase0004, pledgeBaseInfo);
			setObjValue(formdlcollateralbase0004, evalInfo);
			if (evalInfo.getEvalDate() != null && !"".equals(evalInfo.getEvalDate())) {
				dataMap.put("evalInfo", evalInfo);
			}
			String opNo = User.getRegNo(request);
			dataMap.put("pledgeBaseInfo", pledgeBaseInfo);
			dataMap.put("opNo", opNo);
			dataMap.put("keepStatus","0");
			dataMap.put("cusNo", cusNo);
			dataMap.put("pledgeNo", pledgeNo);
			dataMap.put("pledgeMethod", pledgeBaseInfo.getPledgeMethod());
			MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
			MfBusCollateralDetailRel mfBusCollateralDetailRel = new MfBusCollateralDetailRel();
			PledgeBaseInfo pledgeBaseInfo2 = new PledgeBaseInfo();
			mfBusCollateralRel.setAppId(appId);
			mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
			//如果换押品  接触之前押品的关联关系
			if(mfBusCollateralRel!=null){
				mfBusCollateralDetailRel.setBusCollateralId(mfBusCollateralRel.getBusCollateralId());
				mfBusCollateralDetailRel = mfBusCollateralDetailRelFeign.getById(mfBusCollateralDetailRel);
				pledgeBaseInfo2.setPledgeNo(mfBusCollateralDetailRel.getCollateralId());
				pledgeBaseInfo2 = pledgeBaseInfoFeign.getById(pledgeBaseInfo2);
				pledgeBaseInfoFeign.updateRelCusNameRelieve(pledgeBaseInfo2);
				mfBusCollateralDetailRelFeign.delete(mfBusCollateralDetailRel);
				mfBusCollateralRelFeign.delete(mfBusCollateralRel);
			}
			dataMap = mfBusCollateralRelFeign.insertCollateral(dataMap);
			dataMap.put("pledgeNoT", pledgeNoT);
			dataMap.put("flag", "success");
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
	 * 方法描述：主担保方式是信用担保时，跳过担保登记业务节点
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-9-12 下午1:20:43
	 */
	@RequestMapping("/submitBussProcessAjax")
	@ResponseBody public Map<String, Object> submitBussProcessAjax(String extensionApplyId, String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 不是展期业务时，直接返回不提交流程
			if (StringUtil.isEmpty(extensionApplyId)) {
				//非信用担保方式时校验是否添加住担保方式类别担保信息
				String allowFlag = BizPubParm.YES_NO_Y;
				CodeUtils cu = new CodeUtils();
				//跳过担保时是否已添加担保验证
				String isCheckAddPledge = cu.getSingleValByKey("IS_CHECK_ADD_PLEDGE");
				if (BizPubParm.YES_NO_Y.equals(isCheckAddPledge)){
					Map<String, Object> resultMap = mfBusCollateralRelFeign.getAllowSkipPledgeRegFlag(appId,"");
					if(!resultMap.isEmpty()){
						allowFlag = resultMap.get("allowFlag").toString();
					}
				}

				if(BizPubParm.YES_NO_Y.equals(allowFlag)){
					dataMap = mfBusCollateralRelFeign.submitBussProcess(appId,User.getRegNo(request));
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", "没有添加业务主担保方式类型的担保信息，不允许跳过");
				}
			} else {
				dataMap.put("flag", "success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 授信跳过担保
	 * @param appId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年8月30日 上午10:29:01
	 */
	@RequestMapping("/submitCreditProcessAjax")
	@ResponseBody public Map<String, Object> submitCreditProcessAjax(String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfBusCollateralRelFeign.submitCreditProcessAjax(appId,User.getRegNo(request));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 业务流中添加押品评估信息
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 谢静霞
	 * @date 2017-5-24 上午11:11:10
	 */
	@RequestMapping("/insertCollateralAndEvalAjax")
	@ResponseBody public Map<String, Object> insertCollateralAndEvalAjax(String ajaxData, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = getMapByJson(ajaxData);
			@SuppressWarnings("unused")
			String classId = String.valueOf(dataMap.get("classId"));

			FormData formdlcollateralbase0004 = formService.getFormData((String) dataMap.get("formId"));
			getFormValue(formdlcollateralbase0004, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollateralbase0004)) {
				PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
				EvalInfo eval = new EvalInfo();
				setObjValue(formdlcollateralbase0004, pledgeBaseInfo);
				setObjValue(formdlcollateralbase0004, eval);
				dataMap.put("appId", appId);
				eval.setMarketValue(Double.valueOf(
						MathExtend.add(getMapByJson(ajaxData).get("marketValue").toString().replace(",", ""), "0.00")));
				dataMap.put("pledgeBaseInfo",pledgeBaseInfo);
				dataMap.put("evalInfo",eval);
				dataMap = mfBusCollateralRelFeign.insertCollateralAndEval(dataMap);
				dataMap.put("flag", "success");
				MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
				Task task = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);

				Map<String, String> map = new HashMap<String, String>();
				map.put("node", task.getDescription());
				dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL_TONEXT.getMessage(map));
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
	 *
	 * 方法描述： 授信登记押品信息
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-18 下午2:55:50
	 */
	@RequestMapping("/insertCollateralCreditAjax")
	@ResponseBody public Map<String, Object> insertCollateralCreditAjax(String formId, String ajaxData, String appId)
			throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcollateralcredit0004 = formService.getFormData(formId);
			getFormValue(formcollateralcredit0004, getMapByJson(ajaxData));
			if (this.validateFormData(formcollateralcredit0004)) {
				PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
				setObjValue(formcollateralcredit0004, pledgeBaseInfo);
				dataMap = getMapByJson(ajaxData);
				dataMap.put("appId", appId);
				String opNo = User.getRegNo(request);
				dataMap.put("pledgeBaseInfo", pledgeBaseInfo);
				dataMap.put("opNo", opNo);
				dataMap = mfBusCollateralRelFeign.insertCollateral(dataMap);
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
	 *
	 * 方法描述： 解除押品关联关系
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-14 上午9:23:34
	 */
	@RequestMapping("/releaseCollateralAjax")
	@ResponseBody public Map<String, Object> releaseCollateralAjax(String relId, String collateralId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap.put("relId", relId);
			dataMap.put("pledgeNo", collateralId);
			mfBusCollateralRelFeign.deleteCollateralRel(dataMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_DELCOLLAERTALREL.getMessage());
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
	 * 方法描述：跳转到押品置换页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-13 下午8:52:54
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/replaceCollateralInput")
	public String replaceCollateralInput(Model model, String cusNo, String collateralId_old,String appId,String entrance) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
		List<MfCollateralClass> list = mfCollateralClassFeign.getAll(mfCollateralClass);
		String vouType = "";
		if (null != list && list.size() > 1) {
			mfCollateralClass = list.get(0);
			vouType = mfCollateralClass.getVouType();
			model.addAttribute("vouType", vouType);
		}
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(collateralId_old);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classSecondName = pledgeBaseInfo.getClassSecondName();
		vouType = pledgeBaseInfo.getPledgeMethod();
		dataMap.put("classSecondName", classSecondName);
		dataMap.put("cusNo", cusNo);
		dataMap.put("cusName", mfCusCustomer.getCusName());
		dataMap.put("certificateName", mfCusCustomer.getCusName());
		dataMap.put("pledgeMethod", pledgeBaseInfo.getPledgeMethod());
		dataMap.put("keepStatus", "0");// 未入库
		dataMap.put("refFlag", "0");// 是否被关联
		dataMap.put("delflag", "0");// 未删除
		// 调用规则引擎生成押品展示编号
		NumberBigBean numberBigBean = new NumberBigBean();
		numberBigBean.setCusNo(cusNo);
		numberBigBean.setNoType(BizPubParm.NO_TYPE_COLLATERAL);
		numberBigBean = rulesInterfaceFeign.getNumberBigBean(numberBigBean);
		String pledgeShowNo = WaterIdUtil.getWaterId();
		if (numberBigBean.getResultNo() != null && !"".equals(numberBigBean.getResultNo())) {
			pledgeShowNo = numberBigBean.getResultNo();
		}
		dataMap.put("pledgeShowNo", pledgeShowNo);
		JSONObject json = new JSONObject();
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		String busModel = "";
		if (mfBusApply != null) {
			busModel = mfBusApply.getBusModel();
			request.setAttribute("busModel", busModel);
		}
		// 押品类型
		JSONArray collClassArray = mfBusCollateralRelFeign.getCollClassByBusModel(busModel);

		//根据担保方式过滤押品类别
		JSONArray collClassArrayNew =  new JSONArray();
		for (int i = 0; i < collClassArray.size(); i++) {
			JSONObject mfCollateralClass2 = (JSONObject) collClassArray.get(i);
			//押品类别的担保方式等于当前担保方式范围之内的
			if (mfCollateralClass2.getString("vouType").indexOf(vouType)!=-1) {
				collClassArrayNew.add(mfCollateralClass2);
			}
		}
		JSONObject jsonObject = (JSONObject) collClassArrayNew.get(0);
		dataMap.put("classId", String.valueOf(jsonObject.get("id")));
		json.put("collClass", collClassArrayNew);
		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
				.getByPledgeImPawnType(String.valueOf(jsonObject.get("id")), "PledgeBaseInfoAction", "");
		FormData formdlcollateralbase0004 = null;
		if (mfCollateralFormConfig != null) {
			String formId = mfCollateralFormConfig.getAddModelDef();
			formdlcollateralbase0004 = formService.getFormData(formId);
			if (formdlcollateralbase0004.getFormId() == null) {
				formdlcollateralbase0004 = formService.getFormData(mfCollateralFormConfig.getAddModel());
			}
			getFormValue(formdlcollateralbase0004);
			getObjValue(formdlcollateralbase0004, dataMap);
			model.addAttribute("formdlcollateralbase0004", formdlcollateralbase0004);
			model.addAttribute("formId", formId);
		}
		List<OptionsList> vouTypeList = new ArrayList<OptionsList>();
		Map<String, String> dicMap = new CodeUtils().getMapByKeyName("VOU_TYPE");
		OptionsList op = new OptionsList();
		op.setOptionLabel(dicMap.get(vouType));
		op.setOptionValue(vouType);
		vouTypeList.add(op);
		this.changeFormProperty(formdlcollateralbase0004, "pledgeMethod", "optionArray", vouTypeList);
		// 客户选择组件
		List<MfCusCustomer> cusList = mfCusCustomerFeign.getAllCus("");
		JSONArray cusArray = JSONArray.fromObject(cusList);
		for (int i = 0; i < cusArray.size(); i++) {
			cusArray.getJSONObject(i).put("id", cusArray.getJSONObject(i).getString("cusNo"));
			// 新版组件修改
			cusArray.getJSONObject(i).put("name", cusArray.getJSONObject(i).getString("cusName"));
		}
		json.put("cus", cusArray);

		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("appId", appId);
		model.addAttribute("collateralId_old", collateralId_old);
		model.addAttribute("entrance", entrance);
		model.addAttribute("vouType", vouType);
		model.addAttribute("pledgeMethod", pledgeBaseInfo.getPledgeMethod());
		return "/component/collateral/ReplaceCollateral_Insert";
	}

	/**
	 *
	 * 方法描述：押品置换保存
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-13 下午8:59:08
	 */
	@RequestMapping("/repCollateralAjax")
	@ResponseBody public Map<String, Object> repCollateralAjax(String ajaxData, String appId, String isQuote, String entrFlag,
															   String collateralId_old) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = getMapByJson(ajaxData);
			String classId = String.valueOf(dataMap.get("classId"));
			MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
					"PledgeBaseInfoAction", "");
			String formId = "";
			if (mfCollateralFormConfig != null) {
				formId = mfCollateralFormConfig.getAddModelDef();
			}
			FormData formdlcollateralbase0004 = formService.getFormData(formId);
			getFormValue(formdlcollateralbase0004, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollateralbase0004)) {
				PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
				setObjValue(formdlcollateralbase0004, pledgeBaseInfo);
				dataMap.put("collateralId_old", collateralId_old);
				dataMap.put("appId", appId);
				dataMap.put("isQuote", isQuote);
				dataMap.put("entrFlag", entrFlag);
				dataMap.put("pledgeBaseInfo", pledgeBaseInfo);
				dataMap = mfBusCollateralRelFeign.replaceCollateral(dataMap);
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
	 *
	 * 方法描述： 业务流程中跳转页面入库页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-14 下午6:17:50
	 */
	@RequestMapping("/collateralInstockAffirm")
	public String collateralInstockAffirm(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcollateralinstockAffirm0001 = formService.getFormData("collateralinstockAffirm0001");
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		if (mfBusApply != null) {
			getObjValue(formcollateralinstockAffirm0001, mfBusApply);
			model.addAttribute("formcollateralinstockAffirm0001", formcollateralinstockAffirm0001);
		}
		model.addAttribute("query", "");
		return "/component/collateral/MfBusCollateral_instockAffirm";
	}

	/**
	 *
	 * 方法描述： 跳转押品批量入库表单
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-17 下午3:36:33
	 */
	@RequestMapping("/inStockBatchInput")
	public String inStockBatchInput(Model model,String appId,String fincId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		KeepInfo ki = new KeepInfo();
		// ki.setCollateralMethod(vouType);
		ki.setKeepType(CollateralConstant.KEEP_IN);
		ki.setRegDate(DateUtil.getDate());
		FormData formbatchkeepinfo0007 = formService.getFormData("batchkeepinfo0007");
		getObjValue(formbatchkeepinfo0007, ki);

		String processId=BizPubParm.INSTOCK_APPROVE_WKF_NO;
		MfKindFlow mfKindFlow = new MfKindFlow();
		mfKindFlow.setModelType(BizPubParm.MODEL_TYPE_BASE);
		mfKindFlow.setCategory(BizPubParm.FLOW_CATEGORY_4);
		mfKindFlow.setFlowApprovalNo("inStock");
		mfKindFlow = prdctInterfaceFeign.getKindFlow(mfKindFlow);
		if(mfKindFlow != null && BizPubParm.YES_NO_Y.equals(mfKindFlow.getUseFlag())){
			if(StringUtil.isNotEmpty(mfKindFlow.getFlowId())){
				processId = mfKindFlow.getFlowId();
			}
		}
		model.addAttribute("processId", processId);
		model.addAttribute("formbatchkeepinfo0007", formbatchkeepinfo0007);
		model.addAttribute("query", "");
		model.addAttribute("appId", appId);
		model.addAttribute("fincId", fincId);
		return "/component/collateral/InStockBatch_Insert";
	}

	/**
	 *
	 * 方法描述： 批量入库保存
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-17 下午4:24:45
	 */
	@RequestMapping("/instockBatchAjax")
	@ResponseBody public Map<String, Object> instockBatchAjax(String ajaxData, String appId, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formbatchkeepinfo0007 = formService.getFormData("batchkeepinfo0007");
			getFormValue(formbatchkeepinfo0007, getMapByJson(ajaxData));
			if (this.validateFormData(formbatchkeepinfo0007)) {
				KeepInfo keepInfo = new KeepInfo();
				setObjValue(formbatchkeepinfo0007, keepInfo);
				dataMap.put("appId", appId);
				keepInfo.setFincId(fincId);
				keepInfo.setCurrentSessionOrgNo(User.getOrgNo(request));
				keepInfo.setCurrentSessionOrgName(User.getOrgNo(request));
				keepInfo.setCurrentSessionRegNo(User.getRegName(request));
				keepInfo.setCurrentSessionRegName(User.getRegName(request));
				dataMap.put("keepInfo",keepInfo);
				keepInfo = mfBusCollateralRelFeign.inOrOutStockCollateralBatch(dataMap);
				dataMap.put("flag", "success");
				dataMap.put("keepInfo", keepInfo);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", keepInfo.getApproveNodeName());
				paramMap.put("opNo", keepInfo.getApprovePartName());
				if (BizPubParm.YES_NO_Y.equals(keepInfo.getIsExamineInOutStock())) {
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				} else {
					dataMap.put("msg", "批量入库成功");
				}
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
	 *
	 * 方法描述： 押品入库。流程节点中跳转押品入库页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2018-1-3 下午2:03:50
	 */
	@RequestMapping("/inStockBatchInputForBuss")
	public String inStockBatchInputForBuss(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		KeepInfo ki = new KeepInfo();
		// ki.setCollateralMethod(vouType);
		ki.setAppId(appId);
		ki.setKeepType(CollateralConstant.KEEP_IN);
		ki.setRegDate(DateUtil.getDate());
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.pledge_inStockBatch, null, null, User.getRegNo(request));
		FormData formbatchkeepinfo0007 = formService.getFormData(formId);
		getObjValue(formbatchkeepinfo0007, ki);
		String processId = BizPubParm.INSTOCK_APPROVE_WKF_NO;
		model.addAttribute("processId", processId);
		model.addAttribute("formbatchkeepinfo0007", formbatchkeepinfo0007);
		model.addAttribute("query", "");
		model.addAttribute("appId", appId);
		return "/component/collateral/InStockBatch_InsertForBuss";
	}

	/**
	 *
	 * 方法描述： 流程节点中押品入库保存
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2018-1-3 下午2:11:09
	 */
	@RequestMapping("/instockBatchForBussAjax")
	@ResponseBody public Map<String, Object> instockBatchForBussAjax(String appId, String ajaxData, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.pledge_inStockBatch, null,
					null, User.getRegNo(request));
			FormData formbatchkeepinfo0007 = formService.getFormData(formId);
			getFormValue(formbatchkeepinfo0007, getMapByJson(ajaxData));
			if (this.validateFormData(formbatchkeepinfo0007)) {
				KeepInfo keepInfo = new KeepInfo();
				setObjValue(formbatchkeepinfo0007, keepInfo);
				keepInfo.setFincId(fincId);
				keepInfo.setAppId(appId);
				keepInfo.setCurrentSessionOrgNo(User.getOrgNo(request));
				keepInfo.setCurrentSessionOrgName(User.getOrgNo(request));
				keepInfo.setCurrentSessionRegNo(User.getRegName(request));
				keepInfo.setCurrentSessionRegName(User.getRegName(request));
				dataMap.put("keepInfo",keepInfo);
				keepInfo = mfBusCollateralRelFeign.inOrOutStockBatchForBuss(keepInfo);
				dataMap.put("flag", "success");
				dataMap.put("keepInfo", keepInfo);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", keepInfo.getApproveNodeName());
				paramMap.put("opNo", keepInfo.getApprovePartName());
				if (BizPubParm.YES_NO_Y.equals(keepInfo.getIsExamineInOutStock())) {
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				} else {
					dataMap.put("msg", "批量入库成功");
				}
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
	 *
	 * 方法描述： 跳转押品批量出库页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-17 下午3:59:45
	 */
	@RequestMapping("/outStockBatchInput")
	public String outStockBatchInput(Model model,String appId,String fincId,String authCycle) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		KeepInfo keepInfo = new KeepInfo();
		String nowData = DateUtil.getDate();
		keepInfo.setUpdateDate(nowData);
		keepInfo.setKeepType(CollateralConstant.KEEP_OUT);
		keepInfo.setOperateDate(nowData);
		keepInfo.setKeepDesc("");
		FormData formbatchkeepout0008 = formService.getFormData("batchkeepout0008");
		getObjValue(formbatchkeepout0008, keepInfo);

		String processId=BizPubParm.INSTOCK_APPROVE_WKF_NO;
		MfKindFlow mfKindFlow = new MfKindFlow();
		mfKindFlow.setModelType(BizPubParm.MODEL_TYPE_BASE);
		mfKindFlow.setCategory(BizPubParm.FLOW_CATEGORY_4);
		mfKindFlow.setFlowApprovalNo("outStock");
		mfKindFlow = prdctInterfaceFeign.getKindFlow(mfKindFlow);
		if(mfKindFlow != null && BizPubParm.YES_NO_Y.equals(mfKindFlow.getUseFlag())){
			if(StringUtil.isNotEmpty(mfKindFlow.getFlowId())){
				processId = mfKindFlow.getFlowId();
			}
		}
		model.addAttribute("formbatchkeepout0008", formbatchkeepout0008);
		model.addAttribute("processId", processId);
		model.addAttribute("query", "");
		model.addAttribute("appId", appId);
		model.addAttribute("fincId", fincId);
		model.addAttribute("authCycle", authCycle);
		return "/component/collateral/OutStockBatch_Insert";
	}

	/**
	 *
	 * 方法描述： 批量出库保存
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-17 下午4:25:37
	 */
	@RequestMapping("/outstockBatchAjax")
	@ResponseBody public Map<String, Object> outstockBatchAjax(String ajaxData, String appId, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formbatchkeepout0008 = formService.getFormData("batchkeepout0008");
			getFormValue(formbatchkeepout0008, getMapByJson(ajaxData));
			if (this.validateFormData(formbatchkeepout0008)) {
				KeepInfo keepInfo = new KeepInfo();
				setObjValue(formbatchkeepout0008, keepInfo);
				dataMap.put("appId", appId);
				dataMap.put("flag", "out");
				keepInfo.setFincId(fincId);
				keepInfo.setCurrentSessionOrgNo(User.getOrgNo(request));
				keepInfo.setCurrentSessionOrgName(User.getOrgNo(request));
				keepInfo.setCurrentSessionRegNo(User.getRegName(request));
				keepInfo.setCurrentSessionRegName(User.getRegName(request));
				dataMap.put("keepInfo",keepInfo);
				keepInfo = mfBusCollateralRelFeign.inOrOutStockCollateralBatch(dataMap);
				dataMap.put("flag", "success");
				dataMap.put("keepInfo", keepInfo);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", keepInfo.getApproveNodeName());
				paramMap.put("opNo", keepInfo.getApprovePartName());
				if (BizPubParm.YES_NO_Y.equals(keepInfo.getIsExamineInOutStock())) {
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				} else {
					dataMap.put("msg", "出库成功");
				}
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
	 * 方法描述： 公证信息表单
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author YuShuai
	 * @date 2017-6-2 上午10:40:26
	 */
	@RequestMapping("/pleNotarizationForm")
	public String pleNotarizationForm(Model model, String pactId, String pledgeNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		// 根据申请号关联押品 如果押品有多个处列表的页面 如果押品有一个直接出表单 默认是列表
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(pledgeNo);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		try {
			String cusNo = pledgeBaseInfo.getCusNo();
			MfCusCustomer mfbCustomer = new MfCusCustomer();
			mfbCustomer.setCusNo(cusNo);
			mfbCustomer = cusInterfaceFeign.getMfCusCustomerById(mfbCustomer);
			String cusType = mfbCustomer.getCusType();
			String pledgeMethod = pledgeBaseInfo.getPledgeMethod();
			model.addAttribute("cusType", cusType);
			model.addAttribute("pledgeMethod", pledgeMethod);
			FairInfo fairInfo = new FairInfo();
			fairInfo.setCollateralId(pledgeNo);
			fairInfo.setClassId(pledgeBaseInfo.getClassId());
			FormData formPle00001 = formService.getFormData("dlfairinfo0002");
			getFormValue(formPle00001);
			getObjValue(formPle00001, fairInfo);
			model.addAttribute("formPle00001", formPle00001);
			// 办理公证的场景号
			String scNo = BizPubParm.SCENCE_TYPE_DOC_NOTARIZATION;
			model.addAttribute("scNo", scNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("pactId", pactId);
		model.addAttribute("query", "");
		return "/component/collateral/pleNotarizationForm";
	}

	/**
	 * 方法描述： 公证信息出表单还是列表
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author YuShuai
	 * @date 2017-6-3 上午10:55:31
	 */
	@RequestMapping("/pleNotarizationList")
	public String pleNotarizationList(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		// 根据申请号关联押品 如果押品有多个处列表的页面 如果押品有一个直接出表单 默认是列表
		Map<String, Object> map = mfBusCollateralRelFeign.getPleInfoListOrForm(appId);
		String result = "";
		try {
			result = (String) map.get("mapping");
			model.addAttribute("result", result);
			Boolean listFlag = (Boolean) map.get("List");
			String cusNo = (String) map.get("cusNo");
			MfCusCustomer mfbCustomer = new MfCusCustomer();
			mfbCustomer.setCusNo(cusNo);
			mfbCustomer = cusInterfaceFeign.getMfCusCustomerById(mfbCustomer);
			String cusType = mfbCustomer.getCusType();
			model.addAttribute("cusType", cusType);
			if (!listFlag) {// 出form表单的数据 公证
				String pledgeMethod = (String) map.get("pledgeMethod");
				model.addAttribute("pledgeMethod", pledgeMethod);
				String collateralNo = (String) map.get("collateralNo");
				String classId = (String) map.get("classId");
				FairInfo fairInfo = new FairInfo();
				fairInfo.setCollateralId(collateralNo);
				fairInfo.setClassId(classId);
				FormData formPle00001 = formService.getFormData("dlfairinfo0002");
				getFormValue(formPle00001);
				getObjValue(formPle00001, fairInfo);
				model.addAttribute("formPle00001", formPle00001);
				// 办理公证的场景号
				String scNo = BizPubParm.SCENCE_TYPE_DOC_NOTARIZATION;
				model.addAttribute("scNo", scNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("query", "");
		return result;
	}

	/**
	 * 方法描述： 权证信息出表单还是列表
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author YuShuai
	 * @date 2017-6-3 上午10:55:31
	 */
	@RequestMapping("/pleWarrantList")
	public String pleWarrantList(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		// 根据申请号关联押品 如果押品有多个处列表的页面 如果押品有一个直接出表单 默认是列表
		Map<String, Object> map = mfBusCollateralRelFeign.getWarrInfoListOrForm(appId);
		String result = "";
		try {
			result = (String) map.get("mapping");
			model.addAttribute("result", result);
			Boolean listFlag = (Boolean) map.get("List");
			String cusNo = (String) map.get("cusNo");
			MfCusCustomer mfbCustomer = new MfCusCustomer();
			mfbCustomer.setCusNo(cusNo);
			mfbCustomer = cusInterfaceFeign.getMfCusCustomerById(mfbCustomer);
			String cusType = mfbCustomer.getCusType();
			model.addAttribute("cusType", cusType);
			if (!listFlag) {// 出form表单的数据 公证
				String pledgeMethod = (String) map.get("pledgeMethod");
				model.addAttribute("pledgeMethod", pledgeMethod);
				String collateralNo = (String) map.get("collateralNo");
				String classId = (String) map.get("classId");
				CertiInfo certiInfo = new CertiInfo();
				certiInfo.setCollateralId(collateralNo);
				certiInfo.setClassId(classId);
				FormData formPle00001 = formService.getFormData("dlcertiinfo0002");
				getFormValue(formPle00001);
				getObjValue(formPle00001, certiInfo);
				model.addAttribute("formPle00001", formPle00001);
				// 办理权证的场景号
				String scNo = BizPubParm.SCENCE_TYPE_DOC_WARRANT;
				model.addAttribute("scNo", scNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("query", "");
		return result;
	}

	/**
	 * 方法描述： 根据申请号获取和该笔业务关联的所有押品信息
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author YuShuai
	 * @date 2017-6-3 上午10:55:31
	 */
	@RequestMapping("/getPleListByApplyInfo")
	public String getPleListByApplyInfo(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);
		// 根据申请号关联押品 如果押品有多个处列表的页面 如果押品有一个直接出表单 默认是列表
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		String cusNo = mfBusApply.getCusNo();
		MfCusCustomer mfbCustomer = new MfCusCustomer();
		mfbCustomer.setCusNo(cusNo);
		mfbCustomer = cusInterfaceFeign.getMfCusCustomerById(mfbCustomer);
		String cusType = mfbCustomer.getCusType();
		model.addAttribute("mfbCustomer", mfbCustomer);
		model.addAttribute("cusType", cusType);
		model.addAttribute("query", "");
		return "/component/collateral/pleListByApplyInfo";
	}

	@RequestMapping("/pleWarrantForm")
	public String pleWarrantForm(Model model, String pledgeNo, String pactId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		// 根据申请号关联押品 如果押品有多个处列表的页面 如果押品有一个直接出表单 默认是列表
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(pledgeNo);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		model.addAttribute("pactId", pactId);
		try {
			String cusNo = pledgeBaseInfo.getCusNo();
			MfCusCustomer mfbCustomer = new MfCusCustomer();
			mfbCustomer.setCusNo(cusNo);
			mfbCustomer = cusInterfaceFeign.getMfCusCustomerById(mfbCustomer);
			String cusType = mfbCustomer.getCusType();
			String pledgeMethod = pledgeBaseInfo.getPledgeMethod();
			model.addAttribute("cusType", cusType);
			model.addAttribute("pledgeMethod", pledgeMethod);
			CertiInfo certiInfo = new CertiInfo();
			certiInfo.setCollateralId(pledgeNo);
			certiInfo.setClassId(pledgeBaseInfo.getClassId());
			FormData formPle00001 = formService.getFormData("dlcertiinfo0002");
			getFormValue(formPle00001);
			getObjValue(formPle00001, certiInfo);
			model.addAttribute("formPle00001", formPle00001);
			// 办理权证的场景号
			String scNo = BizPubParm.SCENCE_TYPE_DOC_WARRANT;
			model.addAttribute("scNo", scNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("query", "");
		return "/component/collateral/pleWarrantForm";
	}

	/**
	 *
	 * 方法描述： 获得业务中页面，过滤当前场景已使用的押品
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-8-15 下午2:24:09
	 */
	@RequestMapping("/getBussPledgeDataAjax")
	@ResponseBody public Map<String, Object> getBussPledgeDataAjax(String appId, String pledgeNoStr) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfBusCollateralRelFeign.getBussPledgeData(appId, pledgeNoStr);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 获得业务中页面，过滤当前场景已使用的押品
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 段泽宇
	 * @date 2018-8-17 下午2:24:09
	 */
	@RequestMapping("/getBussPledgeDataByCollateralTypeAjax")
	@ResponseBody public Map<String, Object> getBussPledgeDataByCollateralTypeAjax(String appId, String pledgeNoStr,String collateralType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfBusCollateralRelFeign.getBussPledgeDataByCollateralTypeAjax(appId, pledgeNoStr,collateralType);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 根据出入库状态，设置入库出库按钮是否可操作 出入库审批中时，展示“出库中”或“入库中”，且不可编辑
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-9-27 上午10:50:46
	 */
	@RequestMapping("/getInOutStockDisabledFlagAjax")
	@ResponseBody public Map<String, Object> getInOutStockDisabledFlagAjax(String appId, String collateralId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfBusCollateralRelFeign.getBussPledgeData(appId, collateralId);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 获得业务关联的押品是否进行了押品评估 0未添加押品评估1已添加押品评估
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-10-19 下午8:01:16
	 */
	@RequestMapping("/getPledgeEvalFlagAjax")
	@ResponseBody public Map<String, Object> getPledgeEvalFlagAjax(String appId, String collateralId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String flag = mfBusCollateralRelFeign.getPledgeEvalFlag(appId, collateralId);
			//获取到未入库的押品
			if("1".equals(flag) && appId != null){
				List<MfBusCollateralDetailRel> busCollateralDetailRelList = mfBusCollateralDetailRelFeign.getCollateralListByAppId(appId);
				if(busCollateralDetailRelList.size()>0){
					dataMap.put("busCollateralDetailRelList", busCollateralDetailRelList);
				}
			}
			dataMap.put("pledgeEvalFlag", flag);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 适用于业务节点中仅仅对评估价值做一下确认的节点。
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getJustConfirmEvalInfo")
	public String getJustConfirmEvalInfo(Model model, String cusNo, String appId, String vouType) throws Exception {
		FormService formService = new FormService();
		// 当前业务编号对应的表单
		String nodeNo = WKF_NODE.confirm_evaluation.getNodeNo();// 功能节点编号
		model.addAttribute("nodeNo", nodeNo);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		String collateralType = "pledge";
		List<MfBusCollateralDetailRel> list = mfBusCollateralDetailRelFeign.getCollateralDetailRelList(appId,collateralType);
		EvalInfo evalInfo = new EvalInfo();
		PledgeBaseInfo pledgeBaseInfo = null;
		if (list != null && list.size() > 0) {
			for (MfBusCollateralDetailRel mcd : list) {
				evalInfo.setCollateralId(mcd.getCollateralId());
				List<EvalInfo> evalInfoList = evalInfoFeign.getAll(evalInfo);// 押品评估信息
				for (EvalInfo eval : evalInfoList) {
					if (eval.getMarketValue() != null) {
						evalInfo = eval;
						break;
					}
				}

				if (evalInfo.getMarketValue() != null) {
					pledgeBaseInfo = new PledgeBaseInfo();
					pledgeBaseInfo.setPledgeNo(mcd.getCollateralId());
					pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);// 基本押品信息
					break;
				}
			}
		}
		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.confirm_evaluation, appId, null, User.getRegNo(request));
		FormData formdlpledgebaseinfo0004 = formService.getFormData(formId);// 表单变量并不重要

		// 获取押品类别需要数据
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
		List<MfCollateralClass> classList = mfCollateralClassFeign.getAll(mfCollateralClass);

		if (StringUtil.isEmpty(vouType)) {
			vouType = BizPubParm.VOU_TYPE_3;
			model.addAttribute("vouType", vouType);
		}
		JSONObject json = new JSONObject();
		JSONArray collClassArray = JSONArray.fromObject(classList);
		for (int i = 0; i < collClassArray.size(); i++) {
			collClassArray.getJSONObject(i).put("id", collClassArray.getJSONObject(i).getString("classId"));
			collClassArray.getJSONObject(i).put("name", collClassArray.getJSONObject(i).getString("classSecondName"));
		}
		json.put("collClass", collClassArray);
		String ajaxData = json.toString();
		String classId = pledgeBaseInfo.getClassId();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("classId", classId);
		// 赋值
		dataMap.put("appName", mfBusApply.getAppName());
		dataMap.put("cusName", mfBusApply.getCusName());
		dataMap.put("appId", appId);
		dataMap.put("cusNo", cusNo);
		dataMap.put("pledgeName", pledgeBaseInfo.getPledgeName());
		dataMap.put("classId", pledgeBaseInfo.getClassId());
		dataMap.put("classSecondName", pledgeBaseInfo.getClassSecondName());
		dataMap.put("pledgeNo", pledgeBaseInfo.getPledgeNo());
		dataMap.put("evalId", evalInfo.getEvalId());
		dataMap.put("marketValue", evalInfo.getMarketValue());
		dataMap.put("evalMethod", evalInfo.getEvalMethod());
		dataMap.put("evalAmount", evalInfo.getEvalAmount());
		dataMap.put("remarkAmtSection", evalInfo.getRemarkAmtSection());
		dataMap.put("remarkRateSection", evalInfo.getRemarkRateSection());
		dataMap.put("evalDate", evalInfo.getEvalDate());
		dataMap.put("mortRate", evalInfo.getMortRate());
		dataMap.put("validTerm", evalInfo.getValidTerm());
		dataMap.put("evalOrgName", evalInfo.getEvalOrgName());
		dataMap.put("confirmAmount", evalInfo.getConfirmAmount());
		dataMap.put("remark", evalInfo.getRemark());
		try {// 历史数据不能转换的问题
			if (evalInfo.getExt1() != null) {
				dataMap.put("ext1", Double.valueOf(evalInfo.getExt1()));
			}
			if (evalInfo.getExt2() != null) {
				dataMap.put("ext2", Double.valueOf(evalInfo.getExt2()));
			}
		} catch (Exception e) {

		}
		dataMap.put("keepStatus", pledgeBaseInfo.getKeepStatus());// 未入库
		dataMap.put("refFlag", pledgeBaseInfo.getRefFlag());// 是否被关联
		dataMap.put("delflag", pledgeBaseInfo.getDelflag());// 未删除
		getFormValue(formdlpledgebaseinfo0004);
		getObjValue(formdlpledgebaseinfo0004, mfBusApply);
		getObjValue(formdlpledgebaseinfo0004, dataMap);
		// 跳转到页面
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("formdlpledgebaseinfo0004", formdlpledgebaseinfo0004);
		model.addAttribute("query", "");
		return "/component/collateral/CollateralBaseAndEval_confirmEvalInfo";
	}

	/**
	 * 评估价值确认,提交流程，更改评估信息和押品信息
	 * @param vouType
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doJustConfirmEvalInfo")
	@ResponseBody public Map<String, Object> doJustConfirmEvalInfo(String ajaxData, String appId, String vouType) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			dataMap = getMapByJson(ajaxData);
			@SuppressWarnings("unused")
			String classId = String.valueOf(dataMap.get("classId"));

			FormData formdlcollateralbase0004 = formService.getFormData((String) dataMap.get("formId"));
			getFormValue(formdlcollateralbase0004, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollateralbase0004)) {
				PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
				EvalInfo eval = new EvalInfo();
				setObjValue(formdlcollateralbase0004, pledgeBaseInfo);
				setObjValue(formdlcollateralbase0004, eval);
				if (dataMap.get("ext1") != null) {
                    eval.setExt1(String.valueOf(dataMap.get("ext1")));
                }
				if (dataMap.get("ext2") != null) {
                    eval.setExt2(String.valueOf(dataMap.get("ext2")).replaceAll(",", ""));
                }
				pledgeBaseInfo.setPleValue(eval.getEvalAmount());
				pledgeBaseInfo.setLstOrgNo(User.getOrgNo(request));
				pledgeBaseInfo.setLstOrgName(User.getOrgName(request));
				pledgeBaseInfo.setLstRegNo(User.getRegNo(request));
				pledgeBaseInfo.setLstRegName(User.getRegName(request));
				pledgeBaseInfo.setPledgeMethod(vouType);
				pledgeBaseInfo.setClassId((String) dataMap.get("classId"));
				//
				MfCollateralClass mfCollateralClass = new MfCollateralClass();
				mfCollateralClass.setClassId((String) dataMap.get("classId"));
				mfCollateralClass = mfCollateralClassFeign.getById(mfCollateralClass);
				pledgeBaseInfo.setClassFirstNo(mfCollateralClass.getClassFirstNo());
				pledgeBaseInfo.setClassSecondName(mfCollateralClass.getClassSecondName());
				// 更新房屋评估价值
				MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
				mfBusCollateralRel.setAppId(appId);
				mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
				EvalInfo ei = new EvalInfo();
				ei.setCollateralId(pledgeBaseInfo.getPledgeNo());
				ei = evalInfoFeign.getLatest(ei);
				double collateralAmt = MathExtend.multiply(mfBusCollateralRel.getCollateralAmt(), 10000);
				double evalAmount = eval.getEvalAmount();// 这次评估价
				collateralAmt = collateralAmt + evalAmount - ei.getEvalAmount();
				mfBusCollateralRel.setCollateralAmt(collateralAmt);
				// 抵质压率计算
				double collateralRate = 0.00;
				MfBusApply mfBusApply;
				if (MathExtend.comparison(String.valueOf(collateralAmt), "0.00") == 1) {// 最后的评估价值大于0
					mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
					double appAmt = mfBusApply.getAppAmt();
					collateralRate = MathExtend.divide(collateralAmt, appAmt);
				} else {
					collateralRate = 0;
				}
				mfBusCollateralRel.setCollateralRate(collateralRate);
				mfBusCollateralRelFeign.update(mfBusCollateralRel);
				// 更新押品信息
				pledgeBaseInfoFeign.update(pledgeBaseInfo);

				dataMap.put("appId", appId);
				eval.setMarketValue(Double.valueOf(
						MathExtend.add(getMapByJson(ajaxData).get("marketValue").toString().replace(",", ""), "0.00")));
				if (dataMap.get("validTerm") != null && !"".equals(dataMap.get("validTerm"))) {
					eval.setValidTerm(new Integer(String.valueOf(dataMap.get("validTerm"))).intValue());
				}
				if (dataMap.get("mortRate") != null && !"".equals(dataMap.get("mortRate"))) {
					eval.setMortRate(Double
							.valueOf(MathExtend.add(dataMap.get("mortRate").toString().replace(",", ""), "0.00")));
				}
				// 更新评估信息
				evalInfoFeign.update(eval);
				// 提交业务流程
				mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
				Task task = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);
				String transition = workflowDwrFeign.findNextTransition(task.getId());
				wkfInterfaceFeign.doCommit(task.getId(), AppConstant.OPINION_TYPE_ARREE, "", transition,
						User.getRegNo(request), "");
				// 处理业务阶段
				TaskImpl task1 = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);
				// 获取提示信息
				Map<String, String> map = new HashMap<String, String>();
				map.put("node", task1.getDescription());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL_TONEXT.getMessage(map));
			}
		} catch (Exception e) {
			// logger.error("价值确认流程节点提交失败！", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SERVER.getMessage());
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 预约登记（天诚普惠车贷使用，主要展示押品（车辆）基本信息，完成预约回执单上传功能）
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-12-14 上午10:46:32
	 */
	@RequestMapping("/reservationReg")
	public String reservationReg(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String nodeNo = WKF_NODE.reservation_reg.getNodeNo();
		model.addAttribute("nodeNo", nodeNo);
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		String collateralType = "pledge";
		List<MfBusCollateralDetailRel> list = mfBusCollateralDetailRelFeign.getCollateralDetailRelList(appId,collateralType);
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		if (list != null && list.size() > 0) {
			MfBusCollateralDetailRel collateralDetailRel = list.get(0);
			pledgeBaseInfo.setPledgeNo(collateralDetailRel.getCollateralId());
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);// 基本押品信息
		}
		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.reservation_reg, appId, null, User.getRegNo(request));
		FormData formcommon = formService.getFormData(formId);
		getObjValue(formcommon, pledgeBaseInfo);
		this.changeFormProperty(formcommon, "appId", "initValue", mfBusApply.getAppId());
		this.changeFormProperty(formcommon, "wkfAppId", "initValue", mfBusApply.getWkfAppId());
		model.addAttribute("formcommon", formcommon);
		model.addAttribute("query", "");
		return "/component/collateral/MfBusCollateralRel_ReservationReg";
	}

	/**
	 *
	 * 方法描述： 主要展示车辆信息和gps设备信息，表单信息由三方推送或者手动录入
	 * @return
	 * @throws Exception
	 * String
	 * @author zhsupdate
	 * @date 2018-1-31 下午4:16:00
	 */
	@RequestMapping("/getGpsInfo")
	public String getGpsInfo(Model model, String appId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String nodeNo = WKF_NODE.third_gps.getNodeNo();
		model.addAttribute("nodeNo", nodeNo);

		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		String collateralType = "pledge";
		List<MfBusCollateralDetailRel> list= mfBusCollateralDetailRelFeign.getCollateralDetailRelList(appId,collateralType);
		PledgeBaseInfo pledgeBaseInfo =new PledgeBaseInfo();
		if(list!=null&&list.size()>0){
			MfBusCollateralDetailRel collateralDetailRel=list.get(0);
			pledgeBaseInfo.setPledgeNo(collateralDetailRel.getCollateralId());
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);//基本押品信息
		}
		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.third_gps, appId, null, User.getRegNo(request));
		FormData formcommon = formService.getFormData(formId);
		getObjValue(formcommon, pledgeBaseInfo);
		this.changeFormProperty(formcommon, "appId", "initValue", mfBusApply.getAppId());
		this.changeFormProperty(formcommon, "wkfAppId", "initValue", mfBusApply.getWkfAppId());
		model.addAttribute("formcommon", formcommon);
		model.addAttribute("query", "");
		return "/component/collateral/MfBusCollateralRel_gpsInfo";
	}


	/**
	 *
	 * 方法描述： 要件资料保存提交下一步业务流程
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-12-15 上午10:52:08
	 */
	@RequestMapping("/reservationRegAjax")
	@ResponseBody
	public Map<String, Object> reservationRegAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			@SuppressWarnings("rawtypes")
			Map map = getMapByJson(ajaxData);
			String appId = (String) map.get("appId");
			String wkfAppId = (String) map.get("wkfAppId");
			FormData formcommon = formService.getFormData((String) map.get("formId"));
			getFormValue(formcommon, map);
			if (this.validateFormData(formcommon)) {
				PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
				setObjValue(formcommon, pledgeBaseInfo);
				Result result = pledgeBaseInfoFeign.reservationReg(pledgeBaseInfo, appId, wkfAppId);
				if (result.isSuccess()) {
					dataMap.put("flag", "success");
					dataMap.put("msg", result.getMsg());
				} else {
					dataMap.put("flag", "error");
					dataMap.put("msg", result.getMsg());
				}
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述：信息采集跳转页面（应收账款、租赁物信息采集）
	 * @param model
	 * @param cusNo
	 * @param entrFlag
	 * @param appId
	 * @param extensionApplyId
	 * @param infoType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping("/infoCollectInput")
	public String infoCollectInput(Model model, String cusNo, String entrFlag, String appId, String extensionApplyId, String infoType) throws Exception {
		ActionContext.initialize(request, response);
		String entrance = request.getParameter("entrance");
		Gson gson = new Gson();
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JSONObject json = new JSONObject();
		MfCusCustomer mfCusCustomer = cusInterfaceFeign.getCusByCusNo(cusNo);
		dataMap.put("cusNo", mfCusCustomer.getCusNo());
		dataMap.put("cusName", mfCusCustomer.getCusName());
		String busEndDate = "";
		FormData formdlpledgebaseinfo0004 = null;
		if (!"credit".equals(entrFlag) && !"credit".equals(entrance)) {
			entrance = "businesss";
			MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			model.addAttribute("mfBusApply", mfBusApply);
			if (BizPubParm.TERM_TYPE_MONTH.equals(mfBusApply.getTermType())) {
				busEndDate = DateUtil.getShowDateTime(DateUtil.getDateStr(mfBusApply.getRegDate(), mfBusApply.getTerm()));
			} else if (BizPubParm.TERM_TYPE_DAY.equals(mfBusApply.getTermType())) {
				busEndDate = DateUtil.getShowDateTime(DateUtil.addByDay(mfBusApply.getRegDate(), mfBusApply.getTerm()));
			}else {
			}
			model.addAttribute("busEndDate", busEndDate);
			String[] vouTypeArray = mfBusApply.getVouType().split("\\|");
			String vouType = vouTypeArray[0];

			if("account".equals(infoType)){//应收账款信息采集
				vouType = BizPubParm.VOU_TYPE_5;
				model.addAttribute("classFirstNo", "E");
			}else if("lease".equals(infoType)){//租赁物信息采集
				vouType = BizPubParm.VOU_TYPE_6;
				model.addAttribute("classFirstNo", "F");
			}else {
			}
			model.addAttribute("vouType", vouType);
			//获取采集信息表单信息
			dataMap = mfBusCollateralRelFeign.getInfoCollectFormInfo(cusNo, appId,entrFlag,entrance,vouType,infoType);
			json.put("collClass", dataMap.get("collClass"));
			// 获得押品动态表单
			MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(dataMap.get("classId").toString(), "PledgeBaseInfoAction", "");
			if (mfCollateralFormConfig != null) {
				String formId = mfCollateralFormConfig.getAddModelDef();
				String classId = dataMap.get("classId").toString();
				model.addAttribute("classId", classId);
				formdlpledgebaseinfo0004 = formService.getFormData(formId);
				if (formdlpledgebaseinfo0004.getFormId() == null) {
					formdlpledgebaseinfo0004 = formService.getFormData(mfCollateralFormConfig.getAddModel());
				}
				getFormValue(formdlpledgebaseinfo0004);
				getObjValue(formdlpledgebaseinfo0004, dataMap);
				model.addAttribute("formId", formId);

				String vouTypeListS = gson.toJson(dataMap.get("vouTypeList"));
				List<OptionsList> vouTypeListL = (List<OptionsList>) gson.fromJson(vouTypeListS, List.class);
				List<OptionsList> vouTypeList =new ArrayList<OptionsList>();
				for (int i = 0; i < vouTypeListL.size(); i++) {
					String eS = gson.toJson(vouTypeListL.get(i));
					eS = eS.replaceAll("optionLabel", "optionlabel");
					OptionsList e = gson.fromJson(eS, OptionsList.class);
					vouTypeList.add(e);
				}
				this.changeFormProperty(formdlpledgebaseinfo0004, "pledgeMethod", "optionArray", vouTypeList);
				dataMap.remove("vouTypeList");
			}

			// 授信添加担保信息时，不处理押品、费用、文档内容
			if (!"credit".equals(entrFlag) && !"collateral".equals(entrFlag)) {
				MfBusExtensionApply mfBusExtensionApply = new MfBusExtensionApply();
				mfBusExtensionApply.setAppId(appId);
				List<MfBusExtensionApply> mfBusExtensionApplyList = mfBusExtensionApplyFeign.getByAppId(mfBusExtensionApply);
				TaskImpl taskApprove = null;
				if(mfBusExtensionApplyList !=null && mfBusExtensionApplyList.size()>0){
					taskApprove = wkfInterfaceFeign.getTask(mfBusExtensionApplyList.get(0).getWkfAppId(), "");
				}else {
					taskApprove = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), "");
				}
				model.addAttribute("nodeNo", taskApprove.getActivityName());
			}
		}
		// 客户选择组件
		List<MfCusCustomer> cusList = mfCusCustomerFeign.getAllCus(cusNo);
		JSONArray cusArray = JSONArray.fromObject(cusList);
		for (int i = 0; i < cusArray.size(); i++) {
			cusArray.getJSONObject(i).put("id", cusArray.getJSONObject(i).getString("cusNo"));
			cusArray.getJSONObject(i).put("name", cusArray.getJSONObject(i).getString("cusName"));
		}
		json.put("cus", cusArray);

		String ajaxData = json.toString();
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("formdlpledgebaseinfo0004", formdlpledgebaseinfo0004);
		model.addAttribute("appId", appId);
		model.addAttribute("entrFlag", entrFlag);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("entrance", entrance);
		model.addAttribute("extensionApplyId", extensionApplyId);
		model.addAttribute("query", "");
		model.addAttribute("collateralType",infoType);
		return "/component/collateral/CollateralBase_Insert";
	}

	@RequestMapping("/getByAppId")
	@ResponseBody
	public Map<String, Object> getByAppId(String appId,Integer pageNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
			mfBusCollateralRel.setAppId(appId);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setParams(this.setIpageParams("mfBusCollateralRel",mfBusCollateralRel));
			ipage = mfBusCollateralRelFeign.findByPageAppId(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 获取未出库押品信息
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author ldy
	 * @date 2018-12-18 下午8:01:16
	 */
	@RequestMapping("/getNotOutStockListAjax")
	@ResponseBody public Map<String, Object> getNotOutStockListAjax(String appId, String collateralId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			//获取到未出库的押品

			List<MfBusCollateralDetailRel> busCollateralDetailRelList = mfBusCollateralDetailRelFeign.getCollateralDetailRelList(appId,"");
			if(busCollateralDetailRelList.size()>0){
				dataMap.put("busCollateralDetailRelList", busCollateralDetailRelList);
			}
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 根据存单编号查询存单信息
	 *
	 * @param pleCertificateOwner 存单编号
	 * @return
	 */
	@RequestMapping("/depositQuery")
	@ResponseBody
	public Map<String, Object> depositQuery(String pleCertificateOwner) {
		ActionContext.initialize(request, response);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = sendToCoreFeign.call6232(pleCertificateOwner, "");// 根据存单编号查询存单信息

			if ("0000".equals(dataMap.get("sys12"))) {
				dataMap.put("flag", "success");

				if (dataMap.get("sys25") != null) {
					dataMap.put("sys25", dataMap.get("sys25").toString().trim());
				}

				String sys41 = (String) dataMap.get("sys41");// 存入金额(可用余额)
				sys41 = MathExtend.divide(sys41, "100");
				dataMap.put("sys41", sys41);

				String sys85 = (String) dataMap.get("sys85");// 存款利率(利率 活期或定期利率)
				sys85 = MathExtend.divide(sys85, "1000000");
				dataMap.put("sys85", sys85);

				String sys105j = (String) dataMap.get("sys105J");// 存期
				String sys1058 = (String) dataMap.get("sys1058");// 期限类型 Y M D
				sys105j = new Integer(sys105j).toString();// 去掉前面一大串零
				if ("Y".equals(sys1058)) {
					sys105j = sys105j + "年";
				} else if ("M".equals(sys1058)) {
					sys105j = sys105j + "个月";
				} else if ("D".equals(sys1058)) {
					sys105j = sys105j + "天";
				}else {
				}
				dataMap.put("sys105j", sys105j);

				String sys1053 = (String) dataMap.get("sys1053");// 存入日(开户日期)
				sys1053 = DateUtil.getShowDateTime(sys1053);
				dataMap.put("sys1053", sys1053);

				String sys1054 = (String) dataMap.get("sys1054");// 到期日期
				sys1054 = DateUtil.getShowDateTime(sys1054);
				dataMap.put("sys1054", sys1054);
			} else {
				Map<String, String> parmMap = new HashMap<String, String>();
				parmMap.put("operation", "查询存单信息");
				parmMap.put("reason", (String) dataMap.get("sys13"));

				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_OPERATION_CONTENT.getMessage(parmMap));
			}
		} catch (Exception e) {
			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("operation", "查询存单信息");
			parmMap.put("reason", e.getMessage());

			String msg = MessageEnum.FAILED_OPERATION_CONTENT.getMessage(parmMap);

			dataMap.put("flag", "error");
			dataMap.put("msg", msg);

			logger.error(msg, e);
		}

		return dataMap;
	}

    /**
     * 实体对象转成Map
     *
     * @param obj 实体对象
     * @return
     */
    private  Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
			logger.error("实体转Map出错", e);
        }
        return map;
    }

}
