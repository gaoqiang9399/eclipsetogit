package app.component.collateral.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormActive;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.api.task.Task;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;

import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.assure.entity.MfAssureInfo;
import app.component.assureinterface.AssureInterfaceFeign;
import app.component.auth.entity.MfCusCreditApply;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.collateral.carcheck.entity.MfCarCheckForm;
import app.component.collateral.carcheck.feign.MfCarCheckFormFeign;
import app.component.collateral.entity.CertiInfo;
import app.component.collateral.entity.ChkInfo;
import app.component.collateral.entity.CollateralConstant;
import app.component.collateral.entity.EvalInfo;
import app.component.collateral.entity.FairInfo;
import app.component.collateral.entity.InsInfo;
import app.component.collateral.entity.KeepInfo;
import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.collateral.entity.MfBusCollateralRel;
import app.component.collateral.entity.MfCollateralClass;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.entity.MfCollateralTable;
import app.component.collateral.entity.MfPleRepoApply;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.entity.PledgeBaseInfoBill;
import app.component.collateral.feign.CertiInfoFeign;
import app.component.collateral.feign.ChkInfoFeign;
import app.component.collateral.feign.EvalInfoFeign;
import app.component.collateral.feign.FairInfoFeign;
import app.component.collateral.feign.InsInfoFeign;
import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.collateral.feign.MfBusCollateralRelFeign;
import app.component.collateral.feign.MfCollateralClassFeign;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateral.feign.MfCollateralTableFeign;
import app.component.collateral.feign.MfPleRepoApplyFeign;
import app.component.collateral.feign.PledgeBaseInfoBillFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.collateral.movable.entity.MfMoveableCheckInventoryInfo;
import app.component.collateral.movable.feign.MfMoveableCheckInventoryInfoFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfBusGpsReg;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.entity.MfCusPersonJob;
import app.component.cus.feign.MfBusGpsRegFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdct.entity.MfKindFlow;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.rules.entity.NumberBigBean;
import app.component.rulesinterface.RulesInterfaceFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.WorkflowDwrFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfBusCollateralRelController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Apr 12 14:37:16 CST 2017
 **/
@Controller
@RequestMapping("/mfRecievables")
public class MfRecievablesController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
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
	private MfPleRepoApplyFeign mfPleRepoApplyFeign;
	
	
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
			String fincId,String busEntrance,String creditAppId,String collateralType) throws Exception {
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		String headImg = mfCusCustomer.getHeadImg();
		String ifUploadHead = mfCusCustomer.getIfUploadHead();
		model.addAttribute("headImg", headImg);
		model.addAttribute("ifUploadHead", ifUploadHead);
		MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
		mfBusCollateralRel.setAppId(relId);
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
				if (mfBusApply != null) {
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
					Double collateralRate = MathExtend.multiply(mfBusCollateralRel.getCollateralRate(), 100);
                    collateralRate= MathExtend.divide(collateralRate, 1, 2);
                    if(collateralRate>100.0){
                         collateralRate=100.0;
                    }
                    mfBusCollateralRel.setCollateralRate(collateralRate);
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
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("appId", appId);
		model.addAttribute("baseType", mfCusCustomer.getCusBaseType());
		model.addAttribute("relId", relId);
		model.addAttribute("fincId", fincId);
		model.addAttribute("entrance", entrance);
		model.addAttribute("busEntrance", busEntrance);
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("collateralType", collateralType);
		model.addAttribute("query", "");
		return "/component/collateral/receivables/MfBusCollateralRel_receivables_Detail";
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
	
}
