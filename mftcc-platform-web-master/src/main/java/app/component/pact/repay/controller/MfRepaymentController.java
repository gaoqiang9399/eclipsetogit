/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MfRepaymentAction.java
 * 包名： app.component.pact.repay.action
 * 说明：
 * @author zhs
 * @date 2016-9-2 上午11:14:55
 * @version V1.0
 */
package app.component.pact.repay.controller;

import app.base.User;
import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.calccoreinterface.CalcPrepaymentInterfaceFeign;
import app.component.collateralinterface.CollateralInterfaceFeign;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pact.receaccount.entity.MfBusReceBal;
import app.component.pact.receaccount.entity.MfBusReceTransfer;
import app.component.pact.receaccount.feign.MfBusFincAppMainFeign;
import app.component.pact.receaccount.feign.MfBusReceBalFeign;
import app.component.pact.receaccount.feign.MfBusReceTransferFeign;
import app.component.pact.repay.entity.MfRepaymentBean;
import app.component.pact.repay.entity.MfRepaymentBuyBean;
import app.component.pact.repay.entity.MfReceivableBean;
import app.component.pact.repay.entity.MfLsbqRapaymentBean;
import app.component.pact.repay.entity.MfPrepaymentBean;
import app.component.pact.repay.feign.MfRepaymentFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.JsonStrHandling;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.HttpClientUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
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

/**
 * 类名： MfRepaymentAction 描述：
 * 
 * @author zhs
 * @date 2016-9-2 上午11:14:55
 *
 *
 */
@Controller
@RequestMapping("/mfRepayment")
public class MfRepaymentController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfRepayHistoryBo
	@Autowired
	private MfRepaymentFeign mfRepaymentFeign;
    @Autowired
    private PactInterfaceFeign pactInterfaceFeign;
    @Autowired
    private AppInterfaceFeign appInterfaceFeign;
    @Autowired
    private CollateralInterfaceFeign collateralInterfaceFeign;
    @Autowired
    private MfBusReceTransferFeign mfBusReceTransferFeign;
    @Autowired
    private MfBusReceBalFeign mfBusReceBalFeign;
    @Autowired
    private CalcPrepaymentInterfaceFeign calcPrepaymentInterfaceFeign;
    @Autowired
    private MfBusFincAppMainFeign mfBusFincAppMainFeign;
    @Autowired
    private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private YmlConfig ymlConfig;
    @Autowired
    private MfBusPactFeign mfBusPactFeign;
    @Autowired
    private CusInterfaceFeign cusInterfaceFeign;
    //处理日志
    private Logger logger = LoggerFactory.getLogger(MfRepaymentController.class);

	/**
	 * 方法描述： 获取还款页面的还款数据
	 */
	@RequestMapping(value = "/repaymentTrialJsp")
	public String repaymentTrialJsp(Model model, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		// 还款页面数据取值 wd
		Map<String, Object> resultMap = mfRepaymentFeign.doRepaymentJsp(fincId);
		Map<String, Object> resultMapPrepayment = mfRepaymentFeign.doPrepaymentJsp(fincId);
		// 客户信息
		MfCusCustomer mfCusCustomer = (MfCusCustomer) JsonStrHandling.handlingStrToBean(resultMap.get("mfCusCustomer"), MfCusCustomer.class);


		// 借据信息
		MfBusFincApp mfBusFincApp = (MfBusFincApp) JsonStrHandling.handlingStrToBean(resultMap.get("mfBusFincApp"), MfBusFincApp.class);


		// 还款信息
		MfRepaymentBean mfRepaymentBean = (MfRepaymentBean) JsonStrHandling.handlingStrToBean(resultMap.get("mfRepaymentBean"), MfRepaymentBean.class);
		MfPrepaymentBean mfPrepaymentBean  = (MfPrepaymentBean) JsonStrHandling.handlingStrToBean(resultMapPrepayment.get("mfPrepaymentBean"), MfPrepaymentBean.class);

		// 还款计划信息
		JSONArray mfReceivableList = JSONArray.fromObject(resultMap.get("mfReceivableBeans"));
//		List<MfReceivableBean> mfReceivableList = (List<MfReceivableBean>) JsonStrHandling.handlingStrToBean(resultMap.get("mfReceivableBeans"), List.class);


		// 相关参数实体
		MfBusAppKind mfBusAppKind  = (MfBusAppKind) JsonStrHandling.handlingStrToBean(resultMap.get("mfBusAppKind"), MfBusAppKind.class);

		String lsbqHuanBenFlag = (String)resultMap.get("lsbqHuanBenFlag");

		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");

		String borrowCodeType = new CodeUtils().getSingleValByKey("BORROW_CODE_TYPE");
		String onlinePayType = new CodeUtils().getSingleValByKey("APP_ONLINE_PAY_TYPE");// 判断贷后还款方式是否显示 0：不显示; 1:显示
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfBusFincApp", mfBusFincApp);
		model.addAttribute("mfRepaymentBean", mfRepaymentBean);
		model.addAttribute("mfReceivableList", mfReceivableList);
		model.addAttribute("mfBusAppKind", mfBusAppKind);
		model.addAttribute("onlinePayType", onlinePayType);
		model.addAttribute("rateUnit", rateTypeMap.get(mfBusFincApp.getRateType()).getRemark());
		model.addAttribute("query", "");
		model.addAttribute("lsbqHuanBenFlag",lsbqHuanBenFlag);
		model.addAttribute("mfPrepaymentBean", mfPrepaymentBean);
		model.addAttribute("borrowCodeType", borrowCodeType);
		return "component/pact/repay/MfRepayment_MfRepaymentTrialJsp";
	}
	/**
	 * 方法描述：还款登记
	 */
	@RequestMapping(value = "/repaymentRegistrationJsp")
	public String repaymentRegistrationJsp(Model model, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
		FormData formrepaymentregistration = formService.getFormData("repaymentregistration");
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfBusFincApp.getCusNo());
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		getObjValue(formrepaymentregistration, mfCusCustomer);
		//展示利率根据利率类型换算 1-年利率(%),2-月利率(‰),3-日利率(%%),4-月利率(%)
		if(mfBusFincApp.getFincRate() != null){
			if (StringUtil.isEmpty(mfBusFincApp.getRateType())) {
				// 根据申请appId获取系统参数信息
				MfBusAppKind mfBusAppKind = new MfBusAppKind();
				mfBusAppKind.setAppId(mfBusFincApp.getAppId());
				mfBusAppKind = appInterfaceFeign.getMfBusAppKind(mfBusAppKind);
				mfBusFincApp.setFincRateShow(String.valueOf(MathExtend.showRateMethod(mfBusFincApp.getRateType(), mfBusFincApp.getFincRate(), Integer.parseInt(mfBusAppKind.getYearDays()),Integer.parseInt(mfBusAppKind.getRateDecimalDigits()))));
			}
			getObjValue(formrepaymentregistration, mfBusFincApp);
			model.addAttribute("yiHuanPrcp", mfBusFincApp.getLoanBal());
		}
		model.addAttribute("formrepaymentregistration", formrepaymentregistration);
		model.addAttribute("query", "");
		return "component/pact/repay/MfRepaymentRegistration";
	}
	/**
	 *
	 * 方法描述： 获取还款页面的还款数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @date 2017-05-30上午11:31:20
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/repaymentJsp")
	public String repaymentJsp(Model model, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		// 还款页面数据取值 wd
		Map<String, Object> resultMap = mfRepaymentFeign.doRepaymentJsp(fincId);
		
		// 客户信息
		MfCusCustomer mfCusCustomer = (MfCusCustomer) JsonStrHandling.handlingStrToBean(resultMap.get("mfCusCustomer"), MfCusCustomer.class);


		// 借据信息
		MfBusFincApp mfBusFincApp = (MfBusFincApp) JsonStrHandling.handlingStrToBean(resultMap.get("mfBusFincApp"), MfBusFincApp.class);

		// 还款信息
		MfRepaymentBean mfRepaymentBean = (MfRepaymentBean) JsonStrHandling.handlingStrToBean(resultMap.get("mfRepaymentBean"), MfRepaymentBean.class);

		// 还款计划信息
		JSONArray mfReceivableList = JSONArray.fromObject(resultMap.get("mfReceivableBeans"));
//		List<MfReceivableBean> mfReceivableList = (List<MfReceivableBean>) JsonStrHandling.handlingStrToBean(resultMap.get("mfReceivableBeans"), List.class);


		// 相关参数实体
		MfBusAppKind mfBusAppKind  = (MfBusAppKind) JsonStrHandling.handlingStrToBean(resultMap.get("mfBusAppKind"), MfBusAppKind.class);

		String lsbqHuanBenFlag = (String)resultMap.get("lsbqHuanBenFlag");
		//三方放款使用
		String thirdRepaymentFlag=(String)resultMap.get("thirdRepaymentFlag"); 
		
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");

		String onlinePayType = new CodeUtils().getSingleValByKey("APP_ONLINE_PAY_TYPE");// 判断贷后还款方式是否显示 0：不显示; 1:显示
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfBusFincApp", mfBusFincApp);
		model.addAttribute("mfRepaymentBean", mfRepaymentBean);
		model.addAttribute("mfReceivableList", mfReceivableList);
		model.addAttribute("mfBusAppKind", mfBusAppKind);
		model.addAttribute("onlinePayType", onlinePayType);
		model.addAttribute("rateUnit", rateTypeMap.get(mfBusFincApp.getRateType()).getRemark());
		model.addAttribute("query", "");
		model.addAttribute("lsbqHuanBenFlag",lsbqHuanBenFlag);
		model.addAttribute("thirdRepaymentFlag",thirdRepaymentFlag);
		model.addAttribute("projectName",ymlConfig.getSysParams().get("sys.project.name"));
		return "component/pact/repay/MfRepayment_MfRepaymentJsp";
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
	@RequestMapping(value = "/repaymentJspForBuy")
	public String repaymentJspForBuy(Model model, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		// 还款页面数据取值 wd
		Map<String, Object> resultMap = mfRepaymentFeign.doRepaymentJsp(fincId);

		// 客户信息
		MfCusCustomer mfCusCustomer = (MfCusCustomer) JsonStrHandling.handlingStrToBean(resultMap.get("mfCusCustomer"), MfCusCustomer.class);


		// 借据信息
		MfBusFincApp mfBusFincApp = (MfBusFincApp) JsonStrHandling.handlingStrToBean(resultMap.get("mfBusFincApp"), MfBusFincApp.class);
        //获取合同信息
        MfBusPact mfBusPact = new MfBusPact();
        mfBusPact.setPactId(mfBusFincApp.getPactId());
        mfBusPact =  pactInterfaceFeign.getById(mfBusPact);
        //获取申请信息
        MfBusApply mfBusApply = new MfBusApply();
        mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(mfBusFincApp.getAppId());

        // 还款信息
		MfRepaymentBean mfRepaymentBean = (MfRepaymentBean) JsonStrHandling.handlingStrToBean(resultMap.get("mfRepaymentBean"), MfRepaymentBean.class);

		// 还款计划信息
		JSONArray mfReceivableList = JSONArray.fromObject(resultMap.get("mfReceivableBeans"));
//		List<MfReceivableBean> mfReceivableList = (List<MfReceivableBean>) JsonStrHandling.handlingStrToBean(resultMap.get("mfReceivableBeans"), List.class);


		// 相关参数实体
		MfBusAppKind mfBusAppKind  = (MfBusAppKind) JsonStrHandling.handlingStrToBean(resultMap.get("mfBusAppKind"), MfBusAppKind.class);

		String lsbqHuanBenFlag = (String)resultMap.get("lsbqHuanBenFlag");
		//三方放款使用
		String thirdRepaymentFlag=(String)resultMap.get("thirdRepaymentFlag");

		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");


		//分装买方还款数据
        MfRepaymentBuyBean mfRepaymentBuyBean = new MfRepaymentBuyBean();
        mfRepaymentBuyBean.setCusName(mfCusCustomer.getCusName());
        mfRepaymentBuyBean.setAppName(mfBusFincApp.getAppName());
        mfRepaymentBuyBean.setPactAmt(mfBusPact.getPactAmt());
        mfRepaymentBuyBean.setFincId(fincId);
        mfRepaymentBuyBean.setRepayDate(DateUtil.getDate());
        mfRepaymentBuyBean.setFincAmt(mfBusFincApp.getPutoutAmt());
        mfRepaymentBuyBean.setFincLoanBal(mfBusFincApp.getLoanBal());
        mfRepaymentBuyBean.setRepayDate(DateUtil.getDate());
        mfRepaymentBuyBean.setRateAmt(MathExtend.add(mfRepaymentBean.getShiShouLiXi(),mfRepaymentBean.getShiShouFaXi() ));
        //查询应收账款

//        MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
//        mfBusCollateralRel.setAppId(mfBusApply.getAppId());
//        mfBusCollateralRel.setAppId(mfBusApply.getAppId());
//		mfBusCollateralRel.setCollateralType("account");
//        mfBusCollateralRel = collateralInterfaceFeign.getBusCollateralRel(mfBusCollateralRel);
//        List<MfBusCollateralDetailRel> list = new ArrayList<MfBusCollateralDetailRel>();
//        List<PledgeBaseInfo> pledgeBaseInfoList = new ArrayList<PledgeBaseInfo>();
//        if (mfBusCollateralRel!=null){
//            list = collateralInterfaceFeign.getCollateralDetailRelList(mfBusApply.getAppId());
//            if (list!=null){
//                for (int i = 0; i < list.size(); i++) {
//                    PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
//                    pledgeBaseInfo.setPledgeNo(list.get(i).getCollateralId());
//                    pledgeBaseInfo = collateralInterfaceFeign.getById(pledgeBaseInfo);
//                    if (pledgeBaseInfo!=null){
//                        if ("E".equals(pledgeBaseInfo.getClassFirstNo())){
//                            pledgeBaseInfoList.add(pledgeBaseInfo);
//                        }
//                    }
//                }
//            }
//        }
//        //买方名称
//        if (StringUtil.isNotEmpty(mfBusApply.getCusNoCore())){
//            mfRepaymentBuyBean.setBuyName(mfBusApply.getCusNoCore());
//            mfRepaymentBuyBean.setBuyNo(mfBusApply.getCusNameCore());
//        }else {
//            for (int i = 0; i < pledgeBaseInfoList.size(); i++) {
//				mfRepaymentBuyBean.setBuyName(pledgeBaseInfoList.get(i).getCounterparty());
//            }
//        }
        FormService formService = new FormService();
        FormData formbuyerRepaymentBase = formService.getFormData("buyerRepaymentBase");
        //根据借据中的转让子表id获取应收账款信息
        MfBusReceTransfer mfBusReceTransfer = new MfBusReceTransfer();
        mfBusReceTransfer.setTransId(mfBusFincApp.getTransId());
        mfBusReceTransfer = mfBusReceTransferFeign.getById(mfBusReceTransfer);
        if (mfBusReceTransfer!=null){
            mfRepaymentBuyBean.setBuyName(mfBusReceTransfer.getReceBuyer());
            getObjValue(formbuyerRepaymentBase, mfBusReceTransfer);
        }
		getObjValue(formbuyerRepaymentBase, mfRepaymentBuyBean);
		model.addAttribute("formbuyerRepaymentBase", formbuyerRepaymentBase);
		model.addAttribute("query", "");
//		JsonTableUtil jtu = new JsonTableUtil();
//		Ipage ipage = this.getIpage();
//		String tableHtml = jtu.getJsonStr("tablebuyrepaypleageinfo", "thirdTableTag", pledgeBaseInfoList, ipage, true);
//		JSONObject json = new JSONObject();
//		json.put("tableHtml",tableHtml);
//		model.addAttribute("ajaxData",json.toString());
		String onlinePayType = new CodeUtils().getSingleValByKey("APP_ONLINE_PAY_TYPE");// 判断贷后还款方式是否显示 0：不显示; 1:显示
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfBusFincApp", mfBusFincApp);
		model.addAttribute("mfRepaymentBean", mfRepaymentBean);
		model.addAttribute("mfReceivableList", mfReceivableList);
		model.addAttribute("mfBusAppKind", mfBusAppKind);
		model.addAttribute("onlinePayType", onlinePayType);
		model.addAttribute("rateUnit", rateTypeMap.get(mfBusFincApp.getRateType()).getRemark());
		model.addAttribute("query", "");
		model.addAttribute("lsbqHuanBenFlag",lsbqHuanBenFlag);
		model.addAttribute("thirdRepaymentFlag",thirdRepaymentFlag);
		return "component/pact/repay/MfRepayment_MfRepaymentJspForBuy";
	}/**
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
	@RequestMapping(value = "/repaymentJspForAccount")
	public String repaymentJspForAccount(Model model, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		// 还款页面数据取值 wd
		Map<String, Object> resultMap = mfRepaymentFeign.doRepaymentJsp(fincId);

		// 客户信息
		MfCusCustomer mfCusCustomer = (MfCusCustomer) JsonStrHandling.handlingStrToBean(resultMap.get("mfCusCustomer"), MfCusCustomer.class);


		// 借据信息
		MfBusFincApp mfBusFincApp = (MfBusFincApp) JsonStrHandling.handlingStrToBean(resultMap.get("mfBusFincApp"), MfBusFincApp.class);
		//获取合同信息
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(mfBusFincApp.getPactId());
		mfBusPact =  pactInterfaceFeign.getById(mfBusPact);
		//获取申请信息
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(mfBusFincApp.getAppId());

		// 还款信息
		MfRepaymentBean mfRepaymentBean = (MfRepaymentBean) JsonStrHandling.handlingStrToBean(resultMap.get("mfRepaymentBean"), MfRepaymentBean.class);

		// 还款计划信息
		JSONArray mfReceivableList = JSONArray.fromObject(resultMap.get("mfReceivableBeans"));
//		List<MfReceivableBean> mfReceivableList = (List<MfReceivableBean>) JsonStrHandling.handlingStrToBean(resultMap.get("mfReceivableBeans"), List.class);


		// 相关参数实体
		MfBusAppKind mfBusAppKind  = (MfBusAppKind) JsonStrHandling.handlingStrToBean(resultMap.get("mfBusAppKind"), MfBusAppKind.class);

		String lsbqHuanBenFlag = (String)resultMap.get("lsbqHuanBenFlag");
		//三方放款使用
		String thirdRepaymentFlag=(String)resultMap.get("thirdRepaymentFlag");

		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");


		//分装买方还款数据
		MfRepaymentBuyBean mfRepaymentBuyBean = new MfRepaymentBuyBean();
		mfRepaymentBuyBean.setCusName(mfCusCustomer.getCusName());
		mfRepaymentBuyBean.setAppName(mfBusFincApp.getAppName());
		mfRepaymentBuyBean.setPactAmt(mfBusPact.getPactAmt());
		mfRepaymentBuyBean.setFincId(fincId);
		mfRepaymentBuyBean.setRepayDate(DateUtil.getDate());
		mfRepaymentBuyBean.setFincAmt(mfBusFincApp.getPutoutAmt());
		mfRepaymentBuyBean.setFincLoanBal(mfBusFincApp.getLoanBal());
		mfRepaymentBuyBean.setRepayDate(DateUtil.getDate());
		mfRepaymentBuyBean.setRateAmt(MathExtend.add(mfRepaymentBean.getShiShouLiXi(),mfRepaymentBean.getShiShouFaXi() ));
		mfRepaymentBuyBean.setRepayType("1");
		FormService formService = new FormService();
		FormData formaccountRepaymentBase = formService.getFormData("accountRepaymentBase");

		//根据借据中的转让子表id获取应收账款信息
		MfBusReceTransfer mfBusReceTransfer = new MfBusReceTransfer();
		mfBusReceTransfer.setTransId(mfBusFincApp.getTransId());
		mfBusReceTransfer = mfBusReceTransferFeign.getById(mfBusReceTransfer);
		getObjValue(formaccountRepaymentBase, mfBusFincApp);
		if (mfBusReceTransfer!=null){
			mfRepaymentBuyBean.setBuyName(mfBusReceTransfer.getReceBuyer());
			getObjValue(formaccountRepaymentBase, mfBusReceTransfer);
		}
		getObjValue(formaccountRepaymentBase, mfRepaymentBuyBean);
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
		JSONArray accountRepayType2  = new JSONArray();
		Map<String,String> parmMap = new HashMap<String, String>();
		parmMap.put("fincId",fincId);
		Map<String,Object> resultMap2 = new HashMap<String, Object>();
		resultMap2 = calcPrepaymentInterfaceFeign.doCheckTiQianHuanKuan(parmMap);
		if (resultMap2!=null){
			if ("success".equals((String) resultMap2.get("flag"))){

			}else {
				if (accountRepayType!=null){
					for (int i = 0; i < opinionTypeList.size(); i++) {
						OptionsList optionsList = opinionTypeList.get(i);
						if("2".equals(optionsList.getOptionId())){
							opinionTypeList.remove(optionsList);
						}
					}
				}
			}
		}
		this.changeFormProperty(formaccountRepaymentBase, "repayType", "optionArray", opinionTypeList);

		JSONObject ajaxData = new JSONObject();
		ajaxData.put("accountRepayType",accountRepayType);
		model.addAttribute("ajaxData", ajaxData.toString());
		model.addAttribute("accountRepayType", accountRepayType);
		model.addAttribute("formaccountRepaymentBase", formaccountRepaymentBase);
		model.addAttribute("query", "");
		String onlinePayType = new CodeUtils().getSingleValByKey("APP_ONLINE_PAY_TYPE");// 判断贷后还款方式是否显示 0：不显示; 1:显示
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfBusFincApp", mfBusFincApp);
		model.addAttribute("mfRepaymentBean", mfRepaymentBean);
		model.addAttribute("mfReceivableList", mfReceivableList);
		model.addAttribute("mfBusAppKind", mfBusAppKind);
		model.addAttribute("onlinePayType", onlinePayType);
		model.addAttribute("rateUnit", rateTypeMap.get(mfBusFincApp.getRateType()).getRemark());
		model.addAttribute("query", "");
		model.addAttribute("lsbqHuanBenFlag",lsbqHuanBenFlag);
		model.addAttribute("thirdRepaymentFlag",thirdRepaymentFlag);
		return "component/pact/repay/MfRepayment_MfRepaymentJspForAccount";
	}
	/**
	 *
	 * 方法描述：获取提前还款 利息 和 违约金
	 *
	 * @param parmMap.put(@RequestBody
	 *            "fincId",fincId);//借据号 parmMap.put(@RequestBody "repayDate",
	 *            repayDate);//还款日期 parmMap.put(@RequestBody "tiQianHuanBen",
	 *            tiQianHuanBen);//提前还本 金额
	 * @return Map<String,Object>
	 * @author WD
	 * @date 2017-7-12 下午2:54:57
	 */
	@RequestMapping(value = "/doCalcLiXiTiQianHuanKuan")
	@ResponseBody
	public Map<String, Object> doCalcLiXiTiQianHuanKuan(String fincId,String repayDate,String  tiQianHuanBen) throws Exception {
		Map<String, String> parmMap = new HashMap<String, String>();
		parmMap.put("fincId",fincId);
		parmMap.put("repayDate",repayDate);
		parmMap.put("tiQianHuanBen",tiQianHuanBen.replaceAll(",",""));
		return calcPrepaymentInterfaceFeign.doCalcLiXiTiQianHuanKuan(parmMap);
	}
	/**
	 *
	 * 方法描述： 获取尾款结付还款页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @date 2017-05-30上午11:31:20
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/tailPayment")
	public String tailPayment(Model model, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formtailRepaymentBase = formService.getFormData("tailRepaymentBase");
		if(StringUtil.isNotEmpty(fincId)){
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setFincId(fincId);
			mfBusFincApp = pactInterfaceFeign.getFincAppById(mfBusFincApp);
			if (mfBusFincApp!=null){
				getObjValue(formtailRepaymentBase, mfBusFincApp);
				//根据借据中的转让子表id获取应收账款信息
				MfBusReceTransfer mfBusReceTransfer = new MfBusReceTransfer();
				mfBusReceTransfer.setTransId(mfBusFincApp.getTransId());
				mfBusReceTransfer = mfBusReceTransferFeign.getById(mfBusReceTransfer);
				if (mfBusReceTransfer!=null){
					getObjValue(formtailRepaymentBase, mfBusReceTransfer);
				}
				//判断融资下是否存在逾期数据
				String overFlag = "0";
				String fincMainId = mfBusFincApp.getFincMainId();
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
				//获取结余金额
				//根据借据号获取账款结余
				MfBusReceBal mfBusReceBal = new MfBusReceBal();
				mfBusReceBal.setFincId(fincId);
				mfBusReceBal =mfBusReceBalFeign.getById(mfBusReceBal);
				if (mfBusReceBal!=null){
					getObjValue(formtailRepaymentBase, mfBusReceBal);
				}
			}
		}

		model.addAttribute("formtailRepaymentBase", formtailRepaymentBase);
		model.addAttribute("query", "");
		return "component/pact/repay/MfRepayment_MfReceBal";
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
	@RequestMapping(value = "/repaymentAjax")
	@ResponseBody
	public Map<String, Object> repaymentAjax(String ajaxData, String ajaxList) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<String, String>();
		try {
			Map<String, Object> parmMap = getMapByJson(ajaxData);
			JSONArray jsonArray = JSONArray.fromObject(ajaxList);
			List<MfReceivableBean> planList = (List<MfReceivableBean>) JSONArray.toList(jsonArray,
					new MfReceivableBean(), new JsonConfig());
			parmMap.put("brNo", User.getOrgNo(request));
			parmMap.put("brName", User.getOrgName(request));
			parmMap.put("opNo", User.getRegNo(request));
			parmMap.put("opName", User.getRegName(request));
			parmMap.put("planList", planList);
			resMap = mfRepaymentFeign.doRepaymentOperate(parmMap);
			if (resMap.containsKey("flag")) {
				if ("error".equals(resMap.get("flag"))) {
					dataMap.put("flag", "error");
					dataMap.put("msg", resMap.get("msg"));
					return dataMap;
				}
			}
			dataMap.put("flag", "success");
			dataMap.put("getInfoUrl", "MfRepayHistoryAction_getById.action?repayId=" + resMap.get("repayId"));
			dataMap.put("repayId", resMap.get("repayId"));
			dataMap.put("wkfRepayId", resMap.get("wkfRepayId"));
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("还款"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("还款"));
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 还款等级
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @date 2017-05-25下午2:12:27
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/repaymentRegistrationAjax")
	@ResponseBody
	public Map<String, Object> repaymentRegistrationAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<String, String>();
		try {
			Map<String, Object> parmMap = getMapByJson(ajaxData);
			parmMap.put("brNo", User.getOrgNo(request));
			parmMap.put("brName", User.getOrgName(request));
			parmMap.put("opNo", User.getRegNo(request));
			parmMap.put("opName", User.getRegName(request));
			resMap = mfRepaymentFeign.doRepaymentRegistrationOperate(parmMap);
			if (resMap.containsKey("flag")) {
				if ("error".equals(resMap.get("flag"))) {
					dataMap.put("flag", "error");
					dataMap.put("msg", resMap.get("msg"));
					return dataMap;
				}
			}
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("还款登记"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("还款登记"));
			throw e;
		}
		return dataMap;
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
	@RequestMapping(value = "/repaymentAjaxForBuy")
	@ResponseBody
	public Map<String, Object> repaymentAjaxForBuy(String ajaxData, String ajaxList) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<String, String>();
		try {
			Map<String, Object> parmMap = getMapByJson(ajaxData);
//			JSONArray jsonArray = JSONArray.fromObject(ajaxList);
//			parmMap.put("pledgeBaseInfoList", jsonArray);
			Map<String,String> parmMap2 = new HashMap<String,String>();
			parmMap2.put("fincId", (String) parmMap.get("fincId"));
			String  repayAmt = (String) parmMap.get("repayAmt");
            parmMap2.put("repayAmt", repayAmt.replaceAll(",",""));
			String  repayDate = (String) parmMap.get("repayDate");
			parmMap2.put("repayDate",repayDate.replaceAll("-",""));
			parmMap2.put("repaySubject", (String) parmMap.get("repaySubject"));
            parmMap2.put("balAgainstType", "0");//多余的还款金额是否存放到结余中状态  0 多还的金额不做处理  1 多余的金额放到结余表中
            parmMap2.put("repayFlag", "2");
            parmMap2.put("ticketFlag", (String) parmMap.get("ticketFlag"));
			parmMap.put("parmMap2",parmMap2);
			resMap = mfRepaymentFeign.doRepaymentOperateForBuy(parmMap);
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
	 * 方法描述： 账款卖方还款处理方法
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @param ajaxList
	 * @date 2017-05-25下午2:12:27
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/repaymentAjaxForAccount")
	@ResponseBody
	public Map<String, Object> repaymentAjaxForAccount(String ajaxData, String ajaxList) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<String, String>();
		try {
			Map<String, Object> parmMap = getMapByJson(ajaxData);
			Map<String,String> parmMap2 = new HashMap<String,String>();
			parmMap2.put("fincId", (String) parmMap.get("fincId"));
			String  repayAmt = (String) parmMap.get("benjinAmt");
			if(StringUtil.isNotEmpty(repayAmt)){
				parmMap2.put("repayAmt", repayAmt.replaceAll(",",""));
			}
			String  repayDate = (String) parmMap.get("repayDate");
			parmMap2.put("repayDate",repayDate.replaceAll("-",""));
			parmMap2.put("repaySubject", (String) parmMap.get("repaySubject"));
			parmMap2.put("repayType", (String) parmMap.get("repayType"));
			parmMap2.put("ticketFlag", (String) parmMap.get("ticketFlag"));
			parmMap2.put("balAgainstType", "1");//多余的还款金额是否存放到结余中状态  0 多还的金额不做处理  1 多余的金额放到结余表中
			parmMap2.put("repayFlag", "2");
			parmMap.put("parmMap2",parmMap2);
			resMap = mfRepaymentFeign.doRepaymentOperateForAccount(parmMap);
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
     *
     * 方法描述： 尾款结付处理方法
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
    public Map<String, Object> tailAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, String> resMap = new HashMap<String, String>();
        try {
            Map<String, Object> parmMap = getMapByJson(ajaxData);
            resMap = mfRepaymentFeign.doTailOperate(parmMap);
            dataMap.put("flag", "success");
            dataMap.put("getInfoUrl", "MfRepayHistoryAction_getById.action?repayId=" + resMap.get("repayId"));
            dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("尾款结付"));
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("尾款结付"));
            throw e;
        }
        return dataMap;
    }
    /**
     * 
     * 方法描述：三方还款调用的方法
     * @param ajaxData
     * @param ajaxList
     * @return
     * @throws Exception 
     * Map<String,Object>
     * @author wd
     * @date 2018年7月6日 下午6:28:10
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/thirdRepaymentAjax")
	@ResponseBody
	public Map<String, Object> thirdRepaymentAjax(String ajaxData, String ajaxList) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<String, String>();
		try {
			Map<String, Object> parmMap = getMapByJson(ajaxData);
			JSONArray jsonArray = JSONArray.fromObject(ajaxList);
			List<MfReceivableBean> planList = (List<MfReceivableBean>) JSONArray.toList(jsonArray,
					new MfReceivableBean(), new JsonConfig());
			parmMap.put("brNo", User.getOrgNo(request));
			parmMap.put("brName", User.getOrgName(request));
			parmMap.put("opNo", User.getRegNo(request));
			parmMap.put("opName", User.getRegName(request));
			/**
			 * 	 parmMap 中  fincId 借据号
	         *   repayDate 还款日期
	         *  repayAmt  还款金额
			 *   repayAmtFlag 还款金额状态  0 只传还款总金额  1 传还款总金额 以及还款金额明细列表
	         *   repayAmtList 还款金额明细列表  只有还款金额状态 repayAmtFlag = 1 时 才会传入还款金额明细列表(对应每一期还款的金额)List<MfReceivableBean>
			 */
			parmMap.put("repayAmtList", planList);
			parmMap.put("repayAmtFlag", "1");
			parmMap.put("repayAmt",parmMap.get("shiShouZongJi"));
			String repayDate=String.valueOf(parmMap.get("repayDate"));
			repayDate=DateUtil.getYYYYMMDD(repayDate);
			parmMap.put("repayDate",repayDate);
			resMap = mfRepaymentFeign.doRepayment(parmMap);
			dataMap.put("code", resMap.get("code"));
			dataMap.put("msg", resMap.get("msg"));
			dataMap.put("getInfoUrl", "MfRepayHistoryAction_getById.action?repayId=" + resMap.get("repayId"));
			dataMap.put("repayId", resMap.get("repayId"));
			dataMap.put("wkfRepayId", resMap.get("wkfRepayId"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("code", "9999");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("还款"));
			throw e;
		}
		return dataMap;
	}
	
	
	/**
	 * 
	 * 方法描述： 还款日期修改是调用的方法
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @param fincId 
	 * @param repayDate 
	 */
	@RequestMapping(value = "/rapayDateChangeAjax")
	@ResponseBody
	public Map<String, Object> rapayDateChangeAjax(String fincId, String repayDate) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("fincId", fincId);
			parmMap.put("repayDate", repayDate);
			dataMap = mfRepaymentFeign.doRapayDateChange(parmMap);
			dataMap.put("projectName",ymlConfig.getSysParams().get("sys.project.name"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 还款日期修改是调用的方法
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @param fincId
	 * @param repayDate
	 */
	@RequestMapping(value = "/rapayDateChangeTrialAjax")
	@ResponseBody
	public Map<String, Object> rapayDateChangeTrialAjax(String fincId, String repayDate) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("fincId", fincId);
			parmMap.put("repayDate", repayDate);
			dataMap = mfRepaymentFeign.doRapayDateChangeTrial(parmMap);
			dataMap.put("projectName",ymlConfig.getSysParams().get("sys.project.name"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}




	/**
	 * 
	 * 方法描述： 提前还款时 提前还款利息 和 违约金的计算
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @param fincId 
	 * @param repayDate 
	 * @param tiQianHuanBen 
	 */
	@RequestMapping(value = "/calcLiXiTiQianHuanKuanAjax")
	@ResponseBody
	public Map<String, Object> calcLiXiTiQianHuanKuanAjax(String fincId, String repayDate, String tiQianHuanBen) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("fincId", fincId);// 借据号
			parmMap.put("repayDate", repayDate);// 还款日期
			parmMap.put("tiQianHuanBen", tiQianHuanBen);// 提前还本 金额
			// 获取提前还款 利息 和 违约金
			dataMap = mfRepaymentFeign.doCalcLiXiTiQianHuanKuan(parmMap);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 逾期批量处理方法
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 */
	@ResponseBody
	@RequestMapping(value = "/batchOverClass")
	public Map<String, Object> batchOverClass(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
            String factorServiceUrl = PropertiesUtil.getWebServiceProperty("factorserviceURL");
            factorServiceUrl = factorServiceUrl + "/overbatch/doOverBatchRerun.json";
            Map<String, String> parmMap = new HashMap<String, String>();
            String result = HttpClientUtil.sendPostJson(parmMap, factorServiceUrl);
            dataMap = JSONObject.fromObject(result);
            if ("000000".equals(dataMap.get("errorCode"))) {
                dataMap.put("flag", "success");
            }
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 提前还款时 判断该笔借据是否允许提前还款
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 */
	@RequestMapping(value = "/checkTiQianHuanKuanAjax")
	@ResponseBody
	public Map<String, Object> checkTiQianHuanKuanAjax(String fincId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("fincId", fincId);// 借据号
			dataMap = mfRepaymentFeign.doCheckTiQianHuanKuan(parmMap);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获取提前还款页面的还款数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @date 2017-07-01上午11:31:20
	 */
	@RequestMapping(value = "/mfPrepaymentJsp")
    public String mfPrepaymentJsp(Model model, String fincId, String mfBusEditorRepayplanFlag) throws Exception {
		ActionContext.initialize(request, response);
		// 还款页面数据取值 wd
		Map<String, Object> resultMap = mfRepaymentFeign.doPrepaymentJsp(fincId);
		// 客户信息
		MfCusCustomer mfCusCustomer = (MfCusCustomer) JsonStrHandling.handlingStrToBean(resultMap.get("mfCusCustomer"), MfCusCustomer.class);
//		MfCusCustomer mfCusCustomer = (MfCusCustomer) resultMap.get("mfCusCustomer");
		// 借据信息
		MfBusFincApp mfBusFincApp = (MfBusFincApp) JsonStrHandling.handlingStrToBean(resultMap.get("mfBusFincApp"), MfBusFincApp.class);
//		MfBusFincApp mfBusFincApp = (MfBusFincApp) resultMap.get("mfBusFincApp");
		// 还款信息
		MfPrepaymentBean mfPrepaymentBean  = (MfPrepaymentBean) JsonStrHandling.handlingStrToBean(resultMap.get("mfPrepaymentBean"), MfPrepaymentBean.class);
//		MfPrepaymentBean mfPrepaymentBean = (MfPrepaymentBean) resultMap.get("mfPrepaymentBean");
		// 应收信息
		JSONArray mfRepayPlanList = JSONArray.fromObject(resultMap.get("mfRepayPlanList"));
//		List<MfRepayPlan> mfRepayPlanList = (List<MfRepayPlan>) resultMap.get("mfRepayPlanList");
		// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfBusFincApp", mfBusFincApp);
		model.addAttribute("mfRepayPlanList", mfRepayPlanList);
		model.addAttribute("mfPrepaymentBean", mfPrepaymentBean);
		model.addAttribute("rateTypeMap", rateTypeMap);
		model.addAttribute("rateUnit", rateTypeMap.get(mfBusFincApp.getRateType()).getRemark());
		model.addAttribute("query", "");
		model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
        model.addAttribute("mfBusEditorRepayplanFlag", mfBusEditorRepayplanFlag);
		return "component/pact/repay/MfRepayment_MfPrepaymentJsp";
	}

	/**
	 * 
	 * 方法描述： 提前还款 还款处理方法
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author wd
	 * @date 2017-07-06下午2:12:27
	 */
	@RequestMapping(value = "/prepaymentAjax")
	@ResponseBody
	public Map<String, Object> prepaymentAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<String, String>();
		try {
			Map<String, Object> parmMap = getMapByJson(ajaxData);
			parmMap.put("brNo", User.getOrgNo(request));
			parmMap.put("brName", User.getOrgName(request));
			parmMap.put("opNo", User.getRegNo(request));
			parmMap.put("opName", User.getRegName(request));
			resMap = mfRepaymentFeign.doPrepaymentOperate(parmMap);
			String flag = String.valueOf(resMap.get("flag"));
			if ("error".equals(flag)) {
				dataMap.put("flag", "error");
				dataMap.put("msg", resMap.get("msg"));
			} else {
				dataMap.put("flag", "success");
				dataMap.put("getInfoUrl", "MfRepayHistoryAction_getById.action?repayId=" + resMap.get("repayId"));
				dataMap.put("repayId", resMap.get("repayId"));
				dataMap.put("wkfRepayId", resMap.get("wkfRepayId"));
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("提前还款"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");

			Map<String,String> parmMap = new HashMap<String,String>();
			parmMap.put("operation", "提前还款");
			parmMap.put("reason", e.getMessage());
			dataMap.put("msg", MessageEnum.FAILED_OPERATION_CONTENT.getMessage(parmMap));
		}
		return dataMap;
	}
	
	/**利随本清正常还款相关处理  利随本清计算方式 为 按还款本金计算利息 使用 start********/
	/**
	 * 
	 * 方法描述：利随本清正常还款相关处理  利随本清计算方式 为 按还款本金计算利息
	 * @return
	 * @throws Exception
	 * String  
	 * @author wd
	 */
	@RequestMapping(value = "/checkLsbqHuanKuanAjax")
	@ResponseBody
	public Map<String, Object> checkLsbqHuanKuanAjax(String fincId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String,String> parmMap=new HashMap<String,String>();
			parmMap.put("fincId",fincId);//借据号
			dataMap= mfRepaymentFeign.doCheckLsbqHuanKuan(parmMap);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}
    /**
     * 
     * 方法描述：获取利随本清还款页面的还款数据
     * @return
     * @throws Exception 
     * String
     * @author WD
     * @date 2018-4-10 上午10:28:21
     */
	@RequestMapping(value = "/mfLsbqRaymentJsp")
	public String mfLsbqRaymentJsp(Model model,String fincId) throws Exception {
		ActionContext.initialize(request, response);  
		Map<String, Object> resultMap = mfRepaymentFeign.doLsbqRapaymentJsp(fincId);
		// 客户信息
		MfCusCustomer mfCusCustomer = (MfCusCustomer) JsonStrHandling.handlingStrToBean(resultMap.get("mfCusCustomer"), MfCusCustomer.class);
		// 借据信息
		MfBusFincApp mfBusFincApp = (MfBusFincApp) JsonStrHandling.handlingStrToBean(resultMap.get("mfBusFincApp"), MfBusFincApp.class);
		// 还款信息
		MfLsbqRapaymentBean mfLsbqRapaymentBean = (MfLsbqRapaymentBean) JsonStrHandling.handlingStrToBean(resultMap.get("mfLsbqRapaymentBean"), MfLsbqRapaymentBean.class);
		// 应收信息
		JSONArray mfRepayPlanList = JSONArray.fromObject(resultMap.get("mfRepayPlanList"));
		//处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
		Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfBusFincApp", mfBusFincApp);
		model.addAttribute("mfRepayPlanList", mfRepayPlanList);
		model.addAttribute("mfLsbqRapaymentBean", mfLsbqRapaymentBean);
		model.addAttribute("rateTypeMap", rateTypeMap);
		model.addAttribute("rateUnit", rateTypeMap.get(mfBusFincApp.getRateType()).getRemark());
		model.addAttribute("query", "");
		return "component/pact/repay/MfRepayment_MfLsbqRaymentJsp";
	}
	/**
	 * 
	 * 方法描述：通过本金计算利随本清还款相关信息
	 * @return
	 * @throws Exception
	 * String  
	 * @author wd
	 */
	@RequestMapping(value = "/calcLiXiLsbqHuanKuanAjax")
	@ResponseBody
	public Map<String, Object> calcLiXiLsbqHuanKuanAjax(String fincId, String repayDate, String huanKuanBenJin) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String,String> parmMap=new HashMap<String,String>();
			parmMap.put("fincId",fincId);//借据号
			parmMap.put("repayDate", repayDate);//还款日期
			parmMap.put("huanKuanBenJin", huanKuanBenJin);//提前还本 金额  
			//获取利随本清 利息 
			dataMap= mfRepaymentFeign.doCalcLiXiLsbqHuanKuan(parmMap);	
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 
	 * 方法描述：  利随本清还款  还款处理方法
	 * @return
	 * @throws Exception
	 * String
	 * @author wd
	 * @date 2017-07-06下午2:12:27
	 */
	@RequestMapping(value = "/lsbqRapaymentAjax")
	@ResponseBody
	public Map<String, Object> lsbqRapaymentAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> resMap = new HashMap<String, String>();
		try {
				Map<String, Object> parmMap = getMapByJson(ajaxData);
				parmMap.put("brNo", User.getOrgNo(request));
				parmMap.put("brName", User.getOrgName(request));
				parmMap.put("opNo", User.getRegNo(request));
				parmMap.put("opName", User.getRegName(request));	
				resMap = mfRepaymentFeign.doLsbqRaymentOperate(parmMap);
				String flag=String.valueOf(resMap.get("flag"));
				if("error".equals(flag)){
					dataMap.put("flag", "error");
					dataMap.put("msg", resMap.get("msg"));
				}else{					
					dataMap.put("flag", "success");
					dataMap.put("getInfoUrl", "MfRepayHistoryAction_getById.action?repayId="+resMap.get("repayId"));
					dataMap.put("repayId", resMap.get("repayId"));
					dataMap.put("wkfRepayId", resMap.get("wkfRepayId"));
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("利随本清还款"));
				}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("利随本清还款"));
			throw e;
		}
		return dataMap;
	}


    /**
     * 方法描述： 获取正在放款还款的业务信息
     *
     * @return
     * @throws Exception
     * @author zkq
     * @date 20190930
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getLoanPaymentBusMapInfo")
    public String getLoanPaymentBusMapInfo(Model model) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, String> resultMap = new HashMap<String, String>();
        //结果list
        List<Map<String, String>> dataMapList = new ArrayList<>();
        try {
            String factorServiceUrl = PropertiesUtil.getWebServiceProperty("factorserviceURL");
            factorServiceUrl = factorServiceUrl + "/overbatch/getLoanPaymentBusMapInfo.json";
            Map<String, String> parmMap = new HashMap<String, String>();
            String result = HttpClientUtil.sendPostJson(parmMap, factorServiceUrl);
            if (result == null) {
                model.addAttribute("flag", "false");
            }
            resultMap = JSONObject.fromObject(result);
            //借据对象
            MfBusFincApp mfBusFincApp = new MfBusFincApp();
            //合同对象
            MfBusPact mfBusPact = new MfBusPact();
            //遍历map
            for (Map.Entry<String, String> mMap : resultMap.entrySet()) {
                mfBusFincApp = new MfBusFincApp();
                mfBusFincApp.setFincId(mMap.getKey());
                mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
                Map<String, String> dataMap = new HashMap<>();
                if (mfBusFincApp != null) {
                    //正在还款的数据
                    dataMap.put("busNo", mfBusFincApp.getFincShowId());//业务编号
                    dataMap.put("cusName", mfBusFincApp.getCusName());//客户名称
                    dataMap.put("appName", mfBusFincApp.getAppName());//项目名称
                    dataMap.put("busType", "还款操作");//业务类型
                    dataMap.put("fincId", mfBusFincApp.getFincId());//借据号
                } else {
                    //正在放款的数据
                    mfBusPact = new MfBusPact();
                    mfBusPact.setPactNo(mMap.getKey());
                    mfBusPact = mfBusPactFeign.getById(mfBusPact);
                    dataMap.put("busNo", mfBusPact.getPactNo());//业务编号
                    dataMap.put("cusName", mfBusPact.getCusName());//客户名称
                    dataMap.put("appName", mfBusPact.getAppName());//项目名称
                    dataMap.put("busType", "放款操作");//业务类型
                    dataMap.put("fincId", "");//借据号
                }
                dataMapList.add(dataMap);
            }
            model.addAttribute("flag", "true");
            model.addAttribute("loanPaymentBusMapInfoList", dataMapList);
        } catch (Exception e) {
            model.addAttribute("flag", "false");
            logger.info("获取正在放款还款的业务信息出错：" + e.getMessage());
            e.printStackTrace();
        }
        return "component/pact/repay/MfRepayPlan_LoanPaymentBusMapInfoList";
    }


	@RequestMapping(value = "/doRemoveBusMapByFincIdAjax")
	@ResponseBody
	public Map<String, Object> doRemoveBusMapByFincId(String fincId, String busNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String factorServiceUrl = PropertiesUtil.getWebServiceProperty("factorserviceURL");
			factorServiceUrl = factorServiceUrl + "/overbatch/doRemoveBusMapByFincId.json";
			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("fincId", fincId);
			parmMap.put("busNo", busNo);
			String result = HttpClientUtil.sendPostJson(parmMap, factorServiceUrl);
			if (result == null) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "解锁业务失败");
			}
			dataMap = JSONObject.fromObject(result);
			String flag = String.valueOf(dataMap.get("flag"));
			if (!"success".equals(flag)) {
				dataMap.put("flag", "error");
				dataMap.put("msg", dataMap.get("msg"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("解锁业务失败"));
			throw e;
		}
		return dataMap;
	}


}
