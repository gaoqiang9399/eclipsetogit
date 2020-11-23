 package app.component.calc.charge.controller;

import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.component.app.entity.MfBusApplySecond;
import app.component.app.feign.MfBusApplyFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.calc.charge.feign.MfBusChargeFeeFeign;
import app.component.calc.core.feign.MfRepayPlanFeign;
import app.component.calc.fee.entity.MfBusChargeFee;
import app.component.calc.fee.entity.MfBusChargeFeeHis;
import app.component.calc.fee.feign.MfBusAppFeeFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.cus.cusInvoicemation.entity.MfCusInvoiceMation;
import app.component.cus.cusinvoicemation.feign.MfCusInvoiceMationFeign;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.feign.MfCusCorpBaseInfoFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.model.entity.MfSysTemplate;
import app.component.model.entity.MfTemplateBizConfig;
import app.component.model.feign.MfSysTemplateFeign;
import app.component.model.feign.MfTemplateBizConfigFeign;
import app.component.modelinterface.ModelInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.component.prdct.entity.MfKindNodeTemplate;
import app.component.prdct.feign.MfKindNodeTemplateFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.entity.Result;
import app.component.wkfBusInterface.WkfBusInterfaceFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
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
 * @类名： MfBusChargeFeeController
 * 
 * @描述：缴款通知书审核
 * @author jialei
 */
@Controller
@RequestMapping("/mfBusChargeFee")
public class MfBusChargeFeeController extends BaseFormBean {
	@Autowired
	private MfBusChargeFeeFeign mfBusChargeFeeFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfBusApplyFeign mfBusApplyFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private MfSysTemplateFeign mfSysTemplateFeign;
	@Autowired
	private MfKindNodeTemplateFeign mfKindNodeTemplateFeign;
	@Autowired
	private MfTemplateBizConfigFeign mfTemplateBizConfigFeign;
	@Autowired
	private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private MfCusInvoiceMationFeign mfCusInvoiceMationFeign;
	/**
	 *
	 * 方法描述： 跳转缴款通知登记页面
	 * @param model
	 * @param appId
	 * @return
	 * @throws Exception
	 * String
	 * @author 贾磊
	 */
	@RequestMapping(value = "/chargeFee")
	public String chargeFee(Model model, String appId,String entranceNo,String chargeId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String scNo = WKF_NODE.fee.getScenceTypeDoc();// 要件场景
		String nodeNo = WKF_NODE.charge_fee.getNodeNo();// 功能节点编号
		FormData formzgcjktzs0001 = null;
		MfBusChargeFee mfBusChargeFeeQuery = null;
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		String busModel = mfBusApply.getBusModel();
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setAppId(appId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);

		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(appId);
		mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);

		TaskImpl task = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);

		//计算应收取费用
		Double guaranteeAmt = 0.00;//应收担保费
		Double reviewAmt = 0.00;//应收评审费
		Double guaranteeAmtTax = 0.00;//不含税担保费
		Double reviewAmtTax = 0.00;//不含税评审费
		Double handAmt = 0.00;//应收手续费
		Double handAmtTax = 0.00;//不含税手续费
		Double guaranteeRate = 0.00;//担保费率
		Double handRate = 0.00;//手续费率
		Double bond = 0.00;//保证金
		Double sumAmt = 0.00;//应收总额
		StringBuffer guaranteeAmtMsg = new StringBuffer();
		StringBuffer reviewAmtMsg = new StringBuffer();
		StringBuffer guaranteeAmtTaxMsg = new StringBuffer();
		StringBuffer reviewAmtTaxMsg = new StringBuffer();
		StringBuffer handAmtMsg = new StringBuffer();
		StringBuffer handAmtTaxMsg = new StringBuffer();

        if(StringUtil.isNotEmpty(entranceNo) && BizPubParm.FEE_COLLECT_ALONE.equals(entranceNo)) {
        }else{
        	Map<String,Object> amtMap = mfBusChargeFeeFeign.getGuaranteeFee(appId);
        	if(amtMap != null){
        		// 担保费
        		if(amtMap.get("guaranteeAmt") != null){
					guaranteeAmt = (Double) amtMap.get("guaranteeAmt");
				}
				if(amtMap.get("guaranteeRate") != null){
					guaranteeRate = (Double) amtMap.get("guaranteeRate");
				}
				if(amtMap.get("guaranteeAmtTax") != null){
					guaranteeAmtTax = (Double) amtMap.get("guaranteeAmtTax");
				}
				// 保证金
				if(amtMap.get("bond") != null){
					bond = (Double) amtMap.get("bond");
				}
				//评审费
				if(amtMap.get("reviewAmt") != null){
					reviewAmt = (Double) amtMap.get("reviewAmt");
				}
				if(amtMap.get("reviewAmtTax") != null){
					reviewAmtTax = (Double) amtMap.get("reviewAmtTax");
				}
				// 手续费
				if(amtMap.get("handRate") != null){
					handRate = (Double) amtMap.get("handRate");
				}
				if(amtMap.get("handAmt") != null){
					handAmt = (Double) amtMap.get("handAmt");
				}
				if(amtMap.get("handAmtTax") != null){
					handAmtTax = (Double) amtMap.get("handAmtTax");
				}

				if(amtMap.get("guaranteeAmtMsg") != null){
					guaranteeAmtMsg = new StringBuffer().append(amtMap.get("guaranteeAmtMsg")) ;
				}
				if(amtMap.get("reviewAmtMsg") != null){
					reviewAmtMsg = new StringBuffer().append(amtMap.get("reviewAmtMsg")) ;
				}
				if(amtMap.get("guaranteeAmtTaxMsg") != null){
					guaranteeAmtTaxMsg = new StringBuffer().append(amtMap.get("guaranteeAmtTaxMsg")) ;
				}
				if(amtMap.get("reviewAmtTaxMsg") != null){
					reviewAmtTaxMsg = new StringBuffer().append(amtMap.get("reviewAmtTaxMsg")) ;
				}
				if(amtMap.get("handAmtMsg") != null){
					handAmtMsg = new StringBuffer().append(amtMap.get("handAmtMsg")) ;
				}
				if(amtMap.get("handAmtTaxMsg") != null){
					handAmtTaxMsg = new StringBuffer().append(amtMap.get("handAmtTaxMsg")) ;
				}

				guaranteeAmt = MathExtend.divide(guaranteeAmt, 1, 2);
				guaranteeRate = MathExtend.divide(guaranteeRate, 1, 2);
				guaranteeAmtTax = MathExtend.divide(guaranteeAmtTax, 1, 2);

				reviewAmt = MathExtend.divide(reviewAmt, 1, 2);
				reviewAmtTax = MathExtend.divide(reviewAmtTax, 1, 2);

				handAmt = MathExtend.divide(handAmt, 1, 2);
				handAmtTax = MathExtend.divide(handAmtTax, 1, 2);
				handRate = MathExtend.divide(handRate, 1, 2);
			}
        }
		MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
		mfBusChargeFee.setPactId(mfBusPact.getPactId());//合同号
		mfBusChargeFee.setPactNo(mfBusPact.getPactNo());//合同展示号
		mfBusChargeFee.setAppId(mfBusPact.getAppId());//申请号
		mfBusChargeFee.setKindName(mfBusPact.getKindName());
		mfBusChargeFee.setAppName(mfBusPact.getAppName());//项目名称
		mfBusChargeFee.setCusNo(mfBusPact.getCusNo());// 客户号
		mfBusChargeFee.setCusName(mfBusPact.getCusName());// 客户名
		mfBusChargeFee.setPactAmt(mfBusPact.getPactAmt());//合同金额
		mfBusChargeFee.setVouType(mfBusPact.getVouType());
		mfBusChargeFee.setBreedNo(mfBusPact.getBreedNo());
		mfBusChargeFee.setBreedName(mfBusPact.getBreedName());
		mfBusChargeFee.setTerm(mfBusPact.getTerm());
		mfBusChargeFee.setTermShow(mfBusPact.getTermShow());
		mfBusChargeFee.setGuaranteeAmt(guaranteeAmt);//应收担保费
		mfBusChargeFee.setGuaranteeRate(guaranteeRate);//担保费率
		mfBusChargeFee.setGuaranteeAmtTax(guaranteeAmtTax);//应收担保费

		mfBusChargeFee.setReviewAmt(reviewAmt);//应收评审费
		mfBusChargeFee.setReviewAmtTax(reviewAmtTax);//应收评审费

		mfBusChargeFee.setHandAmt(handAmt);//应收手续费
		mfBusChargeFee.setHandAmtTax(handAmtTax);//应收手续费
		mfBusChargeFee.setHandRate(handRate);//手续费率

		mfBusChargeFee.setAccountAmt(MathExtend.add(guaranteeAmt,reviewAmt));
		if(BizPubParm.BUS_MODEL_12.equals(busModel)){
			MfBusApplySecond mfBusApplySecond = mfBusApplyFeign.getSecondByAppId(appId);
			if(mfBusApplySecond!=null){
				mfBusChargeFee.setCollectAccount(mfBusApplySecond.getCollectAccount());
				mfBusChargeFee.setCollectAccName(mfBusApplySecond.getCollectAccName());
				mfBusChargeFee.setCollectBank(mfBusApplySecond.getCollectBank());
				mfBusChargeFee.setCollectAccId(mfBusApplySecond.getCollectAccId());
				mfBusChargeFee.setBondAccount(mfBusApplySecond.getBondAccount());
				mfBusChargeFee.setBondAccName(mfBusApplySecond.getBondAccName());
				mfBusChargeFee.setBondBank(mfBusApplySecond.getBondBank());
				mfBusChargeFee.setBondAccId(mfBusApplySecond.getBondAccId());
			}
			mfBusChargeFee.setBond(bond);
			sumAmt = MathExtend.add(guaranteeAmt,handAmt);
			sumAmt = MathExtend.add(sumAmt,bond);
			mfBusChargeFee.setAccountAmt(sumAmt);
			String invoiceMationId = mfBusApply.getInvoiceMationId();
			if(StringUtil.isNotEmpty(invoiceMationId)){
				MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
				mfCusInvoiceMation.setId(invoiceMationId);
				mfCusInvoiceMation = mfCusInvoiceMationFeign.getById(mfCusInvoiceMation);
				if(mfCusInvoiceMation != null){
					mfBusChargeFee.setPayTaxesNo(mfCusInvoiceMation.getTaxpayerNo());
					mfBusChargeFee.setArea(mfCusInvoiceMation.getAddress());
					mfBusChargeFee.setContactsTel(mfCusInvoiceMation.getTel());
					mfBusChargeFee.setPayBank(mfCusInvoiceMation.getBankName());
					mfBusChargeFee.setPayBankNo(mfCusInvoiceMation.getAccountNumber());
					mfBusChargeFee.setPayTaxesId(invoiceMationId);
				}
			}
		}
		//查询企业划型
		MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
		mfCusCorpBaseInfo.setCusNo(mfBusApply.getCusNo());
		mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
		if(mfCusCorpBaseInfo!=null){
			if(StringUtil.isEmpty(mfBusChargeFee.getWayClass())){
				mfBusChargeFee.setWayClass(mfCusCorpBaseInfo.getWayClass());
				mfBusChargeFee.setWayClassDesc(mfCusCorpBaseInfo.getWayClassDes());
			}
			if(StringUtil.isEmpty(mfBusChargeFee.getLocationPark())){
				mfBusChargeFee.setLocationPark(mfCusCorpBaseInfo.getLocationPark());
			}
			mfBusChargeFee.setEmpCnt(mfCusCorpBaseInfo.getEmpCnt());
			mfBusChargeFee.setEconType(null);
		}
		//默认申请状态0
		model.addAttribute("appSts", "0");
		String formId = "";
		if(StringUtil.isNotEmpty(entranceNo) && BizPubParm.FEE_COLLECT_ALONE.equals(entranceNo)){
			formId = "feechargebase";
			if(StringUtil.isEmpty(chargeId)){
				String warterId = WaterIdUtil.getWaterId();
                chargeId = warterId;
				//初始化缴款通知书
				this.initFeeChargeTemplate(warterId,mfBusPact);
				//查询是否为发回
				MfBusChargeFeeHis mfBusChargeFeeHis = new MfBusChargeFeeHis();
				mfBusChargeFeeHis.setPactId(mfBusPact.getPactId());
				mfBusChargeFeeHis.setFeeChargeType("1");
				List<MfBusChargeFeeHis> mfBusChargeFeeHisList = mfBusChargeFeeFeign.findAllHis(mfBusChargeFeeHis);
				if(mfBusChargeFeeHisList!=null&&mfBusChargeFeeHisList.size()>0){
					mfBusChargeFee.setInvoiceType(mfBusChargeFeeHisList.get(0).getInvoiceType());
					mfBusChargeFee.setRemark(mfBusChargeFeeHisList.get(0).getRemark());
					mfBusChargeFee.setBillRemark(mfBusChargeFeeHisList.get(0).getBillRemark());
				}
				mfBusChargeFee.setChargeId(warterId);
				formzgcjktzs0001 = formService.getFormData(formId);
				getObjValue(formzgcjktzs0001, mfBusChargeFee);
			}else{
				formzgcjktzs0001 = formService.getFormData(formId);
				getObjValue(formzgcjktzs0001, mfBusChargeFee);

				mfBusChargeFeeQuery = new MfBusChargeFee();
				mfBusChargeFeeQuery.setChargeId(chargeId);
				mfBusChargeFeeQuery = mfBusChargeFeeFeign.getById(mfBusChargeFeeQuery);
				if(mfBusChargeFeeQuery!=null){
					getObjValue(formzgcjktzs0001, mfBusChargeFeeQuery);
					model.addAttribute("appSts", mfBusChargeFeeQuery.getAppSts());

				}
				model.addAttribute("feeChargeType", mfBusChargeFeeQuery.getFeeChargeType());
			}

			nodeNo = BizPubParm.FEE_COLLECT_ALONE;// 功能节点编号
		}else{
			formId = "zgcjktzs0001";
			if(BizPubParm.BUS_MODEL_12.equals(busModel)){
				formId = "zgcjktzs0001_GCDB";
			}
			formzgcjktzs0001 = formService.getFormData(formId);
			getObjValue(formzgcjktzs0001, mfBusChargeFee);
			//暂存过的数据展示原信息
			mfBusChargeFeeQuery = new MfBusChargeFee();
			mfBusChargeFeeQuery.setAppId(appId);
			mfBusChargeFeeQuery.setFeeChargeType("1");
			mfBusChargeFeeQuery = mfBusChargeFeeFeign.getById(mfBusChargeFeeQuery);
			if(mfBusChargeFeeQuery!=null){
				getObjValue(formzgcjktzs0001, mfBusChargeFeeQuery);
			}else{
				//查询是否为发回
				MfBusChargeFee mfBusChargeFeeNew = new MfBusChargeFee();
				MfBusChargeFeeHis mfBusChargeFeeHis = new MfBusChargeFeeHis();
				mfBusChargeFeeHis.setPactId(mfBusPact.getPactId());
				mfBusChargeFeeHis.setFeeChargeType("1");
				List<MfBusChargeFeeHis> mfBusChargeFeeHisList = mfBusChargeFeeFeign.findAllHis(mfBusChargeFeeHis);
				if(mfBusChargeFeeHisList!=null&&mfBusChargeFeeHisList.size()>0){
					mfBusChargeFeeNew.setInvoiceType(mfBusChargeFeeHisList.get(0).getInvoiceType());
					mfBusChargeFeeNew.setRemark(mfBusChargeFeeHisList.get(0).getRemark());
					mfBusChargeFeeNew.setBillRemark(mfBusChargeFeeHisList.get(0).getBillRemark());
				}
				getObjValue(formzgcjktzs0001, mfBusChargeFeeNew);
			}

		}
		//获取是否免税默认值
		if(null == mfBusChargeFeeQuery || null == mfBusChargeFeeQuery.getTaxFlag()){
			String taxFlag = mfBusChargeFeeFeign.getTaxFlag(mfBusApply.getCusNo(),mfBusApply.getAppId());
			this.changeFormProperty(formzgcjktzs0001, "taxFlag", "initValue", taxFlag);
		}

		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusAppKind.getRateType()).getRemark();
		this.changeFormProperty(formzgcjktzs0001, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formzgcjktzs0001, "overRate", "unit", rateUnit);
		model.addAttribute("formzgcjktzs0001", formzgcjktzs0001);
		model.addAttribute("mfBusApply", mfBusApply);
		model.addAttribute("mfBusChargeFee", mfBusChargeFee);
		model.addAttribute("appId", appId);
		model.addAttribute("chargeId", chargeId);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("query", "");
		model.addAttribute("guaranteeAmt",guaranteeAmt);
		model.addAttribute("guaranteeAmtMsg",guaranteeAmtMsg);
		model.addAttribute("guaranteeAmtTaxMsg",guaranteeAmtTaxMsg);
		model.addAttribute("reviewAmt", reviewAmt);
		model.addAttribute("reviewAmtMsg", reviewAmtMsg);
		model.addAttribute("reviewAmtTaxMsg", reviewAmtTaxMsg);
		model.addAttribute("handAmtMsg", handAmtMsg);
		model.addAttribute("handAmtTaxMsg", handAmtTaxMsg);

		model.addAttribute("busModel", busModel);
		model.addAttribute("entranceNo", entranceNo);
		return "/component/app/MfBusChargeFee_input";
	}




	private void initFeeChargeTemplate(String waterId,MfBusPact mfBusPact) throws Exception{
		MfTemplateBizConfig tbc = new MfTemplateBizConfig();
		tbc.setTemBizNo(waterId);// 业务主键
		tbc.setKindNo(BizPubParm.FEE_COLLECT_ALONE);
		MfSysTemplate mfSysTemplate = null;
		tbc.setIfElectricSign(BizPubParm.YES_NO_N);
		tbc.setIfEsigned(BizPubParm.YES_NO_N);
		tbc.setIfInitTag(BizPubParm.YES_NO_N);

		tbc.setIfInitEsigner(BizPubParm.YES_NO_N);
		tbc.setStampSts("0");

		MfKindNodeTemplate mfKindNodeTemplate = new MfKindNodeTemplate();
		mfKindNodeTemplate.setKindNo(mfBusPact.getKindNo());
		mfKindNodeTemplate.setModelType(BizPubParm.MODEL_TYPE_NORMAL);
		mfKindNodeTemplate.setNodeNo(WKF_NODE.charge_fee.getNodeNo());
		List<MfKindNodeTemplate> list = mfKindNodeTemplateFeign.getNodeTemplateList(mfKindNodeTemplate);
		if(list != null && list.size()>0){
			mfKindNodeTemplate = list.get(0);

			mfSysTemplate= new MfSysTemplate();
			mfSysTemplate.setTemplateNo(mfKindNodeTemplate.getTemplateNo());
			mfSysTemplate = mfSysTemplateFeign.getById(mfSysTemplate);
			if(mfSysTemplate != null){
				tbc.setEsignSort(mfSysTemplate.getEsignSort());
				tbc.setTemplateType(mfSysTemplate.getTemplateType());// 模板类型
				tbc.setTemplateTypeName(mfSysTemplate.getTemplateTypeName());// 模板类型名称
				tbc.setTemplateNameZh(mfSysTemplate.getTemplateNameZh());// 模板文档展示名称
				tbc.setTemplateNameEn(mfSysTemplate.getTemplateNameEn());// 模板文档实际名称
				tbc.setTemplateSuffix(mfSysTemplate.getTemplateSuffix());//文档类型 1-word 2-excel
				tbc.setNodeName(mfSysTemplate.getTemplateNameZh());
				tbc.setFrontView(mfSysTemplate.getFrontView());
				tbc.setVideoInterview(mfSysTemplate.getVideoInterview());
				tbc.setIfStamp(mfSysTemplate.getIfStamp());
			}

			if (StringUtil.isEmpty(mfKindNodeTemplate.getOptPower())) {
				tbc.setOptPower("2");// 操作权限 1：查；2：签/改/保存
			} else {
				tbc.setOptPower(mfKindNodeTemplate.getOptPower());// 操作权限 1：查；2：签/改/保存
			}
			tbc.setIfMustInput(mfKindNodeTemplate.getIfMustInput());//是否必填 0-不必填 1-必填
			tbc.setNodeNo(BizPubParm.FEE_COLLECT_ALONE);// 节点编号
			tbc.setTemplateNo(mfKindNodeTemplate.getTemplateNo());// 模板编号
			tbc.setTemplateBizConfigId(WaterIdUtil.getWaterId());
			tbc.setAppId(mfBusPact.getAppId());
			if(StringUtil.isEmpty(tbc.getIfInitTag())){
				tbc.setIfInitTag(BizPubParm.YES_NO_N);
			}
			if(tbc.getEsignSort() == null){
				tbc.setEsignSort(mfSysTemplate.getEsignSort());
			}
			mfTemplateBizConfigFeign.insert(tbc);
//			modelInterfaceFeign.doWordReplaceToPdf(tbc);
		}

	}
	/**
	 *
	 * 方法描述：获取企业划型
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 贾磊
	 */
	@RequestMapping(value = "/chargeSubmitAjax")
	@ResponseBody
	public Map<String, Object> chargeSubmitAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			FormData formzgcjktzs0001 = formService.getFormData(formId);
			getFormValue(formzgcjktzs0001, getMapByJson(ajaxData));
			MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
			setObjValue(formzgcjktzs0001, mfBusChargeFee);
			if(mfBusChargeFee.getGuaranteeAmtFee()!=null){
				if(mfBusChargeFee.getGuaranteeAmtFee()>mfBusChargeFee.getPactAmt()){
					dataMap.put("flag", "error");
					dataMap.put("msg", "本次收费对应担保金额不能大于合同金额!");
					return  dataMap;
				}
			}
			mfBusChargeFee.setOpNo(User.getRegNo(request));
			mfBusChargeFee.setOpName(User.getRegName(request));
			mfBusChargeFee.setBrNo(User.getOrgNo(request));
			mfBusChargeFee.setBrName(User.getOrgName(request));
			map.put("opNo", User.getRegNo(request));
			map.put("mfBusChargeFee", mfBusChargeFee);
			Map<String,Object> resultMap = mfBusChargeFeeFeign.doChargeSubmit(map);
			if (resultMap!=null && "success".equals(resultMap.get("flag"))) {
				//生成缴款书
				mfBusChargeFeeFeign.doChargeReplaceToPdf((String)resultMap.get("chargeId"));
				dataMap.put("appId", map.get("appId"));
				dataMap.put("msg", resultMap.get("msg"));
				dataMap.put("flag", "success");
				dataMap.put("feeCollectType", resultMap.get("feeCollectType"));
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_SERVER.getMessage());
			}
		}catch (Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SERVER.getMessage());
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 缴款通知登记保存提交
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 贾磊
	 */
	@RequestMapping(value = "/getProjectSizeAjax")
	@ResponseBody
	public Map<String, Object> getProjectSizeAjax(String empCnt,String saleAmt,String wayClass,String assetsAmt) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("projectSize",mfBusChargeFeeFeign.getProjectSizeAjax(empCnt,saleAmt,wayClass,assetsAmt));
		dataMap.put("flag", "success");
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 缴款通知登记暂存按钮，插入信息不提交流程
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 贾磊
	 */
	@RequestMapping(value = "/chargeUpdateAjax")
	@ResponseBody
	public Map<String, Object> chargeUpdateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			FormData formzgcjktzs0001 = formService.getFormData(formId);
			getFormValue(formzgcjktzs0001, getMapByJson(ajaxData));
			MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
			setObjValue(formzgcjktzs0001, mfBusChargeFee);

			mfBusChargeFee.setOpNo(User.getRegNo(request));
			mfBusChargeFee.setOpName(User.getRegName(request));
			mfBusChargeFee.setBrNo(User.getOrgNo(request));
			mfBusChargeFee.setBrName(User.getOrgName(request));
			mfBusChargeFee = mfBusChargeFeeFeign.insert(mfBusChargeFee);
			//生成缴款书
			mfBusChargeFeeFeign.doChargeReplaceToPdf(mfBusChargeFee.getChargeId());
			dataMap.put("appId", map.get("appId"));
			dataMap.put("chargeId", mfBusChargeFee.getChargeId());
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			dataMap.put("flag", "success");
		}catch (Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SERVER.getMessage());
		}
		return dataMap;
	}

	/**
	 * 方法描述：申请审批流程提交
	 *
	 * @return
	 * @throws Exception String
	 * @author zhs
	 * @date 2016-6-7 下午5:58:30
	 */
	@RequestMapping(value = "/processSubmitAjax")
	@ResponseBody
	public Map<String, Object> processSubmitAjax(String ajaxData, String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			//提交申请审批流程
			MfBusChargeFee mfBusChargeFee = mfBusChargeFeeFeign.submitProcessWithUser(appId, User.getRegNo(request), User.getRegName(request), User.getOrgNo(request), "");
            dataMap.put("appSts", mfBusChargeFee.getAppSts());
			dataMap.put("node", "processaudit");
			dataMap.put("flag", "success");
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userRole", mfBusChargeFee.getApproveNodeName());
			paramMap.put("opNo", mfBusChargeFee.getApprovePartName());
			dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_PROCESS_COMMIT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 跳转缴款通知审批页面
	 * @param model
	 * @param chargeId
	 * @param taskId
	 * @return
	 * @throws Exception
	 * String
	 * @author 贾磊
	 */
	@RequestMapping(value = "/getChargeView")
	public String getChargeView(Model model, String chargeId,String taskId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String scNo = WKF_NODE.charge_fee.getScenceTypeDoc();// 要件场景
		String nodeNo = WKF_NODE.charge_fee.getNodeNo();// 功能节点编号

		MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
		mfBusChargeFee.setChargeId(chargeId);
		mfBusChargeFee = mfBusChargeFeeFeign.getById(mfBusChargeFee);

		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(mfBusChargeFee.getAppId());
		mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
		String busModel = mfBusAppKind.getBusModel();
		String formId = "";
		if(BizPubParm.YES_NO_Y.equals(mfBusChargeFee.getFeeChargeType())){
			formId = "zgcjktzs0002";
		}else{
			formId = "feechargeApprove";
		}
		if(BizPubParm.BUS_MODEL_12.equals(busModel)){
			formId = "zgcjktzslastapprove_GCDB";
		}
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(chargeId, taskId);
		//部门经理岗位表单支持修改应缴金额
		if(WKF_NODE.charge_last_approve.getNodeNo().equals(taskAppro.getName())){
			formId = prdctInterfaceFeign.getFormId(mfBusChargeFee.getKindNo(), WKF_NODE.charge_last_approve, null, null, User.getRegNo(request));
		}else if(WKF_NODE.supplement_data.getNodeNo().equals(taskAppro.getName())){//退回补充资料表单
			formId = prdctInterfaceFeign.getFormId(mfBusChargeFee.getKindNo(), WKF_NODE.fee_supplement_data, null, null, User.getRegNo(request));
		}else if(WKF_NODE.fee_account_confirm.getNodeNo().equals(taskAppro.getName())){//到账确认
			formId = prdctInterfaceFeign.getFormId(mfBusChargeFee.getKindNo(), WKF_NODE.fee_account_confirm, null, null, User.getRegNo(request));
			if(!BizPubParm.BUS_MODEL_12.equals(busModel)){
				mfBusChargeFee.setActualReceivedAmt(mfBusChargeFee.getAccountAmt());
			}
		}else if(WKF_NODE.gua_confirm_fee.getNodeNo().equals(taskAppro.getName())){//保函组
			formId = "collectconfirmapprove_GCDB_BHZ";
			Double actualReceivedAmt = mfBusChargeFee.getActualReceivedAmt();
			if(actualReceivedAmt == null){
				mfBusChargeFee.setActualReceivedAmt(0.00);
				mfBusChargeFee.setRefundAmt(0.00);
			}
		}
		FormData formzgcjktzs0002 = formService.getFormData(formId);
		getObjValue(formzgcjktzs0002, mfBusChargeFee);


		String activityType = taskAppro.getActivityType();
		// 处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), null);
		if(WKF_NODE.supplement_data.getNodeNo().equals(taskAppro.getName())){
			opinionTypeList.remove(opinionTypeList.get(3));
		}
		opinionTypeList.remove(opinionTypeList.get(2));
        opinionTypeList.remove(opinionTypeList.get(1));
		this.changeFormProperty(formzgcjktzs0002, "opinionType", "optionArray", opinionTypeList);

		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusAppKind.getRateType()).getRemark();
		this.changeFormProperty(formzgcjktzs0002, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formzgcjktzs0002, "overRate", "unit", rateUnit);
		model.addAttribute("formzgcjktzs0002", formzgcjktzs0002);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("busModel", busModel);
		model.addAttribute("query", "");
		model.addAttribute("cusNo", mfBusChargeFee.getCusNo());
		model.addAttribute("chargeId", chargeId);
		model.addAttribute("feeChargeType", mfBusChargeFee.getFeeChargeType());
		model.addAttribute("appId", mfBusChargeFee.getAppId());
		model.addAttribute("taskId", taskId);
		return "/component/app/MfBusChargeFee_approve";
	}


	/**
	 *
	 * 方法描述： 审批提交（审批意见保存）
	 * @return
	 * @throws Exception
	 * String
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/doSubmitAjax")
	@ResponseBody
	public Map<String, Object> doSubmitAjax(String ajaxData, String ajaxDataList, String taskId, String chargeId, String transition, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(chargeId, taskId);// 当前审批节点task
		Map map = getMapByJson(ajaxData);
		chargeId = (String) map.get("chargeId");
		MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
		mfBusChargeFee.setChargeId(chargeId);
		mfBusChargeFee = mfBusChargeFeeFeign.getById(mfBusChargeFee);
		FormData formapply0003 = formService.getFormData((String) map.get("formId"));
		getFormValue(formapply0003, map);
		setObjValue(formapply0003, mfBusChargeFee);
		if(StringUtil.isNotEmpty(mfBusChargeFee.getReceviedDate())){
			mfBusChargeFee.setReceviedDate(mfBusChargeFee.getReceviedDate().replaceAll("-",""));
		}
		dataMap = getMapByJson(ajaxData);
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
		JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
		try {
			dataMap.put("mfBusChargeFee", mfBusChargeFee);
			dataMap.put("approvalOpinion", approvalOpinion);
            if(WKF_NODE.gua_confirm_fee.getNodeNo().equals(taskAppro.getName()) && "1".equals(opinionType)){//保函组
                //实际到账金额>=应收总金额，否则系统进行拦截，不能保存
                if(mfBusChargeFee.getActualReceivedAmt() < mfBusChargeFee.getAccountAmt()){
                    dataMap.put("flag", "error");
                    dataMap.put("msg", "实际到账金额不能小于应收总金额！");
                    return dataMap;
                }
            }
			Result res = mfBusChargeFeeFeign.doCommit(taskId, chargeId, opinionType,approvalOpinion ,transition, User.getRegNo(request), nextUser, dataMap);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", res.getMsg());
				//退回补充资料再提交时重新生成缴款通知书
				if("supplement_data".equals(taskAppro.getActivityName())){
					mfBusChargeFeeFeign.doChargeReplaceToPdf(chargeId);
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
	 *
	 * 方法描述： 跳转缴款通知开启审批页面
	 * @param model
	 * @param chargeId
	 * @return
	 * @throws Exception
	 * String
	 * @author 贾磊
	 */
	@RequestMapping(value = "/getChargeStartView")
	public String getChargeStartView(Model model, String chargeId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String scNo = WKF_NODE.fee.getScenceTypeDoc();// 要件场景
		String nodeNo = WKF_NODE.fee_collect.getNodeNo();// 功能节点编号

		MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
		mfBusChargeFee.setChargeId(chargeId);
		mfBusChargeFee = mfBusChargeFeeFeign.getById(mfBusChargeFee);

		String formId = "zgcjktzs0003";
		FormData formzgcjktzs0003 = formService.getFormData(formId);
		getObjValue(formzgcjktzs0003, mfBusChargeFee);

		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(mfBusChargeFee.getAppId());
		mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);

		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		String rateUnit = rateTypeMap.get(mfBusAppKind.getRateType()).getRemark();
		this.changeFormProperty(formzgcjktzs0003, "fincRate", "unit", rateUnit);
		this.changeFormProperty(formzgcjktzs0003, "overRate", "unit", rateUnit);
		model.addAttribute("formzgcjktzs0003", formzgcjktzs0003);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("query", "");
		model.addAttribute("chargeId", chargeId);
		return "/component/app/charge_startApprove";
	}


	@RequestMapping(value = "/getById")
	public String getById(Model model, String chargeId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		MfBusChargeFee mfBusChargeFee = new MfBusChargeFee();
		try{
			mfBusChargeFee.setChargeId(chargeId);
			mfBusChargeFee = mfBusChargeFeeFeign.getById(mfBusChargeFee);
			FormData formfeeCollectDetail = formService.getFormData("feeCollectDetail");
			getObjValue(formfeeCollectDetail, mfBusChargeFee);

			model.addAttribute("formfeeCollectDetail", formfeeCollectDetail);
			model.addAttribute("query", "");
			model.addAttribute("nodeNo", BizPubParm.FEE_COLLECT_ALONE);
			model.addAttribute("chargeId", mfBusChargeFee.getChargeId());
//			model.addAttribute("resMap", map);
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		return "/component/pact/feerefund/MfBusFeeCollect_Detail";
	}


	@RequestMapping(value = "/calcSecondFeeAmt")
	@ResponseBody
	public Map<String, Object> calcSecondFeeAmt(String pactId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			dataMap = mfBusChargeFeeFeign.calcSecondFeeAmt(pactId);
			if (dataMap==null) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_SERVER.getMessage());
			}
		}catch (Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SERVER.getMessage());
		}
		return dataMap;
	}

	public void doChargeReplaceToPdf(String chargeId) throws Exception {
		mfBusChargeFeeFeign.doChargeReplaceToPdf(chargeId);
	}
}
