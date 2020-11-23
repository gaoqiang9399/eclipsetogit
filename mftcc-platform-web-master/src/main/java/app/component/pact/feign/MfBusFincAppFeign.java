package app.component.pact.feign;

import app.base.ServiceException;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calc.fee.entity.MfBusFeeDetail;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.pact.entity.*;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Title: MfBusFincAppBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue May 31 18:10:07 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusFincAppFeign {

	@RequestMapping(value = "/mfBusFincApp/insert")
	public void insert(@RequestBody MfBusFincApp mfBusFincApp) throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/delete")
	public void delete(@RequestBody MfBusFincApp mfBusFincApp) throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/update")
	public void update(@RequestBody MfBusFincApp mfBusFincApp) throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/getById")
	public MfBusFincApp getById(@RequestBody MfBusFincApp mfBusFincApp) throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/findFincListByPage")
	public Ipage findFincListByPage(@RequestBody Ipage ipage, @RequestParam("mfBusFincApp") MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getByIdNewFinc")
	public MfBusFincApp getByIdNewFinc(@RequestBody MfBusFincApp mfBusFincApp) throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/findByPageForCash")
	public Ipage findByPageForCash(@RequestBody Ipage ipage)
			throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/getList")
	public List<MfBusFincApp> getList(@RequestBody MfBusFincApp mfBusFincApp) throws ServiceException;
	
	@RequestMapping(value = "/mfBusFincApp/getCount")
	public int getCount(MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/delete1")
	public void delete1(@RequestBody MfBusFincAppHis mfBusFincAppHis) throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/doInprocess")
	public MfBusFincApp doInprocess(@RequestBody String fincId, @RequestParam("firstApprovalUser") String firstApprovalUser,@RequestParam("orgNo") String orgNo)
			throws Exception;

	@RequestMapping(value = "/mfBusFincApp/doInprocessWithUser")
	public MfBusFincApp doInprocessWithUser(@RequestBody String fincId, @RequestParam("firstApprovalUser") String firstApprovalUser,
			@RequestParam("opNo") String opNo, @RequestParam("opName") String opName) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/updateProcess")
	public Result updateProcess(@RequestParam("taskId") String taskId, @RequestParam("appNo") String appNo,
			@RequestParam("opinionType") String opinionType, @RequestParam("transition") String transition,
			@RequestParam("opNo") String opNo, @RequestParam("nextUser") String nextUser, @RequestBody Map<String, Object> dataMap)
			throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getMfBusFincAppChild")
	public MfBusFincAppChild getMfBusFincAppChild(@RequestBody MfBusFincApp mfBusFincAppTmp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/doCheckFincBal")
	//public Map<String, String> doCheckFincBal(@RequestBody String pactId, @RequestParam("fincAppAmt") double fincAppAmt) throws ServiceException;
	public Map<String, String> doCheckFincBal(@RequestBody MfBusFincApp mfBusFincApp,@RequestParam("pactId") String pactId)
			throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/doCheckFincBal2")
	public Map<String, String> doCheckFincBal2(@RequestBody String pactId, @RequestParam("fincAppAmt") double fincAppAmt)
			throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/getFincListForUnRepay")
	public List<MfBusFincApp> getFincListForUnRepay(@RequestBody MfBusFincApp mfBusFincApp) throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/doFincReviewAjax")
	public void doFincReviewAjax(@RequestBody MfBusFincApp mfBusFincApp) throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/insertFincApp")
	public MfBusFincApp insertFincApp(@RequestBody MfBusFincApp mfBusFincApp, @RequestParam("parmMap") Map<String, Object> parmMap)
			throws Exception;

	@RequestMapping(value = "/mfBusFincApp/insertFincAppWithUser")
	public MfBusFincApp insertFincAppWithUser(@RequestBody MfBusFincApp mfBusFincApp,
			@RequestParam("parmMap") Map<String, Object> parmMap, @RequestParam("opNo") String opNo, @RequestParam("opName") String opName)
			throws Exception;

	@RequestMapping(value = "/mfBusFincApp/doFincReview")
	public Map<String, Object> doFincReview(@RequestBody MfBusFincApp mfBusFincApp,
			 @RequestParam("busFeeDetailList") List<MfBusFeeDetail> busFeeDetailList)
			throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/getFincListByFincId")
	public List<MfBusFincAppHis> getFincListByFincId(@RequestBody MfBusFincAppHis mfBusFincAppHis)
			throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/updateProcessLoan")
	public Result updateProcessLoan(@RequestParam("taskId") String taskId, @RequestParam("fincId") String fincId,
			@RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("transition") String transition,
			@RequestParam("regNo") String regNo, @RequestParam("nextUser") String nextUser, @RequestBody MfBusFincApp mfBusFincApp,
			@RequestParam("mfBusAppFeeList") List<MfBusAppFee> mfBusAppFeeList) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/updateMfBusFincApp")
	public void updateMfBusFincApp(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getBankInFoByBankId")
	public MfCusBankAccManage getBankInFoByBankId(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/disProcessDataForFinc")
	public MfBusFincApp disProcessDataForFinc(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/disProcessDataForFincShow")
	public MfBusFincApp disProcessDataForFincShow(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/disProcessDataForFincChildShow")
	public MfBusFincAppChild disProcessDataForFincChildShow(@RequestBody MfBusFincAppChild mfBusFincAppChild)
			throws Exception;

	@RequestMapping(value = "/mfBusFincApp/submitOnlyFincBusiness")
	public Map<String, String> submitOnlyFincBusiness(@RequestBody MfBusFincAppHis mfBusFincAppHis) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/doInsertFincApp")
	public MfBusFincAppChild doInsertFincApp(@RequestBody MfBusFincApp mfBusFincApp,
			@RequestParam("parmMap") Map<String, Object> parmMap) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/doInsertFincAppWithUser")
	public MfBusFincAppChild doInsertFincAppWithUser(@RequestBody Map<String, Object> parmMap, @RequestParam("opNo") String opNo, @RequestParam("opName") String opName)
			throws Exception;

	@RequestMapping(value = "/mfBusFincApp/updateFincProcess")
	public void updateFincProcess(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getBankInfo")
	public MfBusFincApp getBankInfo(@RequestBody MfBusFincApp mfBusFincApp, @RequestParam("mfBusPact") MfBusPact mfBusPact)
			throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getRepayToDateByPage")
	public Ipage getRepayToDateByPage(@RequestBody Ipage ipage) throws Exception;
	@RequestMapping(value = "/mfBusFincApp/getPactToDateByPage")
	public Ipage getPactToDateByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getRepayOverDateByPage")
	public Ipage getRepayOverDateByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getFincAppShowIdListByPactId")
	public List<MfBusFincApp> getFincAppShowIdListByPactId(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getByinvoiceId")
	public List<MfBusFincApp> getByinvoiceId(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/findLoanAfterByPage")
	public Ipage findLoanAfterByPage(@RequestBody Ipage ipage) throws ServiceException;
	@RequestMapping(value = "/mfBusFincApp/findCapitalImplementWarningByPage")
	public Ipage findCapitalImplementWarningByPage(@RequestBody Ipage ipage) throws ServiceException;
	@RequestMapping(value = "/mfBusFincApp/findCapitalImplementDetailByPage")
	public Ipage findCapitalImplementDetailByPage(@RequestBody Ipage ipage) throws ServiceException;
	@RequestMapping(value = "/mfBusFincApp/findCapitalImplementDetailExcel")
	public List<MfCapitalImplementDetail> findCapitalImplementDetailExcel(@RequestBody MfCapitalImplementDetail mfCapitalImplementDetail) throws ServiceException;
	@RequestMapping(value = "/mfBusFincApp/getCapitalDetaiListByFincId")
	public List<MfCapitalImplementDetail> getCapitalDetaiListByFincId(@RequestBody MfCapitalImplementDetail mfCapitalImplementDetail) throws ServiceException;
	/*20190304 yxl筛选符合条件的贷后借据列表*/
	@RequestMapping(value = "/mfBusFincApp/findLoanAfterByPage1")
	public Ipage findLoanAfterByPage1(@RequestBody Ipage ipage) throws ServiceException;
	@RequestMapping(value = "/mfBusFincApp/findFincByPage")
	public Ipage findFincByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/findExamineStateByPage")
	public Ipage findExamineStateByPage(@RequestBody Ipage ipage)
			throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/findExamineStateForCusByPage")
	public Ipage findExamineStateForCusByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/updateFincappAndChiled")
	public void updateFincappAndChiled(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getReturnFeeList")
	public Ipage getReturnFeeList(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getPreRepayFincList")
	public Ipage getPreRepayFincList(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getTaskList")
	public List<MfBusFincApp> getTaskList(@RequestParam("opNo") String opNo) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/findInterestAccruedByPage")
	public Ipage findInterestAccruedByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/findLoanAfterByPageForTrh")
	public Ipage findLoanAfterByPageForTrh(@RequestBody Ipage ipage)
			throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/getAllLoanBalByBean")
	public String getAllLoanBalByBean(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getPutoutAmtByCusNo")
	public String getPutoutAmtByCusNo(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getFiveClassLoanBal")
	public List<Map<String, String>> getFiveClassLoanBal(@RequestBody Map<String, String> map) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getPactPutoutSts")
	public String getPactPutoutSts(@RequestParam("pactId") String pactId) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getPutOutHisList")
	public List<MfBusFincApp> getPutOutHisList(@RequestBody MfBusFincApp mfBusFincApp) throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/getAllLoanBal")
	public String getAllLoanBal(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/delSubmitFincForLf")
	public String delSubmitFincForLf(@RequestParam("appId") String appId) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getRefundFincList")
	public Ipage getRefundFincList(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getDefundFincList")
	public Ipage getDefundFincList(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getAllLoanbalByCusNo")
	public String getAllLoanbalByCusNo(@RequestBody String cusNo) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/findNoTransferListByPage")
	public Ipage findNoTransferListByPage(@RequestBody Ipage ipage)
			throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getMfBusFincAppByExten")
	public MfBusFincApp getMfBusFincAppByExten(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/insertTransferAcc")
	public MfBusFincApp insertTransferAcc(@RequestBody MfBusFincApp mfBusFincApp,
			@RequestParam("parmMap") Map<String, Object> parmMap) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/doInsertFincAppForApp")
	public MfBusFincAppChild doInsertFincAppForApp(@RequestBody MfBusFincApp mfBusFincApp,
			@RequestParam("parmMap") Map<String, Object> parmMap) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getFincAppListByPactId")
	public List<MfBusFincApp> getFincAppListByPactId(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;
	@RequestMapping(value = "/mfBusFincApp/getMfBusFincAppListAllByPactId")
	public List<MfBusFincApp> getMfBusFincAppListAllByPactId(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getByPactId")
	public List<MfBusFincApp> getByPactId(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getPutOutSituationByDay")
	public MfBusFincApp getPutOutSituationByDay(@RequestParam("date") String date) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getPutOutSituationByMonth")
	public MfBusFincApp getPutOutSituationByMonth(@RequestParam("date") String date) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getPutOutSituationByCusMngNo")
	public List<MfBusFincApp> getPutOutSituationByCusMngNo(@RequestParam("cusMngNo") String cusMngNo) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getMultiBusList")
	public List<MfBusFincApp> getMultiBusList(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

    @RequestMapping(value = "/mfBusFincApp/getMultiBusFinishList")
    public List<MfBusFincApp> getMultiBusFinishList(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;


	@RequestMapping(value = "/mfBusFincApp/getOverdueFincListByCusMngNo")
	public List<MfBusFincApp> getOverdueFincListByCusMngNo(@RequestParam("cusMngNo") String cusMngNo) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getFincListByCusMngNoAndSts")
	public List<MfBusFincApp> getFincListByCusMngNoAndSts(@RequestBody String cusMngNo, @RequestParam("fincSts") String fincSts)
			throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getFincHandingByCusNo")
	public List<MfBusFincApp> getFincHandingByCusNo(@RequestParam("cusNo") String cusNo) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/findFincApprovalStsListByPage")
	public Ipage findFincApprovalStsListByPage(@RequestBody Ipage ipg)
			throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getPutoutCountByDay")
	public MfBusFincApp getPutoutCountByDay(@RequestParam("date") String date) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getPutoutCountByMonth")
	public MfBusFincApp getPutoutCountByMonth(@RequestParam("date") String date) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getBusFincApprovingListByOpNo")
	public List<MfBusFincApp> getBusFincApprovingListByOpNo(@RequestParam("opNo") String opNo) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/doCommitForApp")
	public Result doCommitForApp(@RequestBody Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/doAutomaticLoan")
	public Map<String, Object> doAutomaticLoan(@RequestParam("appId") String appId) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getTodayPutOutCountByBeginDate")
	public Ipage getTodayPutOutCountByBeginDate(@RequestBody Ipage ipage, @RequestParam("mfBusFincApp") MfBusFincApp mfBusFincApp)
			throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getMaxfincShowNoByPactNo")
	public String getMaxfincShowNoByPactNo(@RequestParam("pactNo") String pactNo) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/doAutomaticRepayPlan")
	public Map<String, Object> doAutomaticRepayPlan(@RequestParam("fincId") String fincId,@RequestParam("regNo") String regNo) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getIsAllFincEnd")
	public String getIsAllFincEnd(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getPutoutInfoByFincId")
	public Map<String, Object> getPutoutInfoByFincId(@RequestParam("fincId") String fincId) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getPhoneCollect")
	public Ipage getPhoneCollect(@RequestBody Ipage ipage)
			throws Exception;

	@RequestMapping(value = "/mfBusFincApp/updateForAddAmt")
	public void updateForAddAmt(@RequestBody Map<String, Object> pactMap) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/doGenerateLoanInfo")
	public Map<String, Object> doGenerateLoanInfo(@RequestBody String appId) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/findFincAppByPageForB")
	public Ipage findFincAppByPageForB(@RequestBody Ipage ipage, @RequestParam("cusMngNo") String cusMngNo) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getAccUpdListPage")
	public Ipage getAccUpdListPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getMfBusFincAppForB")
	public Ipage getMfBusFincAppForB(@RequestBody Ipage ipage, @RequestParam("paramMap") Map<String, String> paramMap)
			throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getFincAppVariables")
	public Map<String, Object> getFincAppVariables(@RequestBody Map<String, Object> dataMap, @RequestParam("appNo") String appNo)
			throws Exception;

	@RequestMapping(value = "/mfBusFincApp/doAutomaticLoanForPad")
	public Map<String, Object> doAutomaticLoanForPad(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getAppIdStrByFincSts")
	public Map<String, Object> getAppIdStrByFincSts(@RequestParam("fincSts") String fincSts) throws Exception;

	/**
	 * 方法描述： 根据条件获得借据信息，finc_sts IN ${fincSts} 借据状态参数为('5','6')或其他
	 * 
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception Ipage
	 * @author 沈浩兵
	 * @date 2018-3-12 下午2:18:09
	 */
	@RequestMapping(value = "/mfBusFincApp/findTrenchFincByPage")
	public Ipage findTrenchFincByPage(Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述： 修改存出保证金信息
	 * @param mfBusFincApp
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2018年4月24日 下午1:09:00
	 */
	@RequestMapping(value = "/mfBusFincApp/updateDepositOutById",method=RequestMethod.POST)
	public void updateDepositOutById(@RequestBody MfBusFincApp mfBusFincApp) throws ServiceException;
	
	/**
	 * 方法描述：查询客户贷款余额，根据客户号和基地类型
	 * 
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusFincApp/findLoanBalByCusNoAndBaseType")
	public double findLoanBalByCusNoAndBaseType(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;
	/**
	 * 方法描述： 根据客户号查找所有借据
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception
	 * List<MfBusFincApp>
	 * @author 仇招
	 * @date 2018年6月6日 下午4:24:46
	 */
	@RequestMapping(value = "/mfBusFincApp/getByCusNo")
	public List<MfBusFincApp> getByCusNo(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;
	
	/**
	 * 方法描述： 根据客户号查找多选数据源
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception
	 * List<MfBusFincApp>
	 * @author ldy
	 * @date 2018年8月20日 下午4:24:46
	 */
	@RequestMapping(value = "/mfBusFincApp/getJSONArrayByCusNo")
	public JSONArray getJSONArrayByCusNo(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;


	@RequestMapping(value = "/mfBusFincApp/getFincListByCusNoAndSts")
	public List<MfBusFincApp> getFincListByCusNoAndSts(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;


	@RequestMapping(value = "/mfBusFincApp/findAgenciesChengJiaoByPageAjax")
	public Ipage findAgenciesChengJiaoByPageAjax(@RequestBody Ipage ipage)throws Exception;


	/**
	 *
	 * 方法描述： 拒绝放款保存
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2018-2-27 下午4:13:47
	 */
	@RequestMapping(value = "/mfBusFincApp/doDisagreeFinc")
    public Map<String,Object> doDisagreeFinc(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;
	/**
	 * 方法描述： 核心企业成交业务
	 * @param ipage
	 * @return
	 * Ipage
	 * @author 仇招
	 * @date 2018年7月16日 下午8:23:14
	 */
	@RequestMapping(value = "/mfBusFincApp/findCoreCompanyChengJiaoByPageAjax")
	public Ipage findCoreCompanyChengJiaoByPageAjax(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 方法描述：校验放款申请金额和应收账款余额的大小，如果申请金额大于余额不允许放款
	 * @param putoutAmt
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusFincApp/validatePutoutAmt")
	public  Map<String,Object> validatePutoutAmt(@RequestParam("putoutAmt") String putoutAmt, @RequestParam("appId") String appId) throws Exception;
	/**
	 * 方法描述： 根据融资主表id查找所有借据
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception
	 * List<MfBusFincApp>
	 * @author ywh
	 * @date 2018年8月20日 下午4:24:46
	 */
	@RequestMapping(value = "/mfBusFincApp/getByFincMainId")
	public List<MfBusFincApp> getByFincMainId(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	
	/**
	 * 方法描述： 查询银行合同还款
	 * @param ipage
	 */
	@RequestMapping(value = "/mfFundChannelRepayPlan/getContractExpiresByPage")
	public Ipage getContractExpiresByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusFincApp/validateBorrowCode")
	Map<String,Object> validateBorrowCode(@RequestParam(value = "borrowCode") String borrowCode)throws Exception;

	/**
	 * 方法描述： 放款申请获取下一步提醒
	 * @return
	 * String
	 * @author YuShuai
	 * @date 2018年8月30日 下午9:51:18
	 */
	@RequestMapping(value = "/mfBusFincApp/getNextFlowRemid")
	public String getNextFlowRemid(@RequestBody MfBusFincAppChild mfBusFincAppChild)throws Exception;
	
	/**
	 * 方法描述： 根据渠道编号获取已放款业务信息，并计算分润金额
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 仇招
	 * @date 2018年9月5日 下午4:05:07
	 */
	@RequestMapping(value = "/mfBusFincApp/findByTrench")
	public Ipage findByTrench(@RequestBody Ipage ipage)throws Exception;
	/**
	 * 方法描述： 根据资金机构获取已放款业务信息，并计算分润金额
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 仇招
	 * @date 2018年9月7日 下午3:42:18
	 */
	@RequestMapping(value = "/mfBusFincApp/findByAgencies")
	public Ipage findByAgencies(@RequestBody Ipage ipage)throws Exception;
	/**
	 * 方法描述：  获取提前还款借据列表数据
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 仇招
	 * @date 2018年9月10日 下午2:25:27
	 */
	@RequestMapping(value = "/mfBusFincApp/findEarlyRepayByPage")
	public Ipage findEarlyRepayByPage(@RequestBody Ipage ipage)throws Exception;
	/**
	 * 方法描述： 根据渠道编号获取逾期放款列表
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 仇招
	 * @date 2018年9月10日 下午9:51:54
	 */
	@RequestMapping(value = "/mfBusFincApp/findOverdueByPage")
	public Ipage findOverdueByPage(@RequestBody Ipage ipage)throws Exception;

	/**
	*@desc 贷后未完结借据查询
	*@author lwq        
	*@date 2018/9/15 10:25
	*@parm [ipage]
	*@return app.util.toolkit.Ipage
	**/
	@RequestMapping(value = "/mfBusFincApp/findLoanAfteringByPage")
	public Ipage findLoanAfteringByPage(@RequestBody Ipage ipage) throws ServiceException;

	/**
	 * @方法描述： 导出贷后数据
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception
	 * List<MfBusFincApp>
	 * @author 仇招
	 * @date 2018年9月17日 下午9:33:38
	 */
	@RequestMapping(value = "/mfBusFincApp/findByPageAjaxExcel")
	public List<MfBusFincApp> findByPageAjaxExcel(@RequestBody MfBusFincApp mfBusFincApp)throws Exception;
	@RequestMapping(value = "/mfBusFincApp/findOverdueByPageAjaxExcel")
	public List<MfBusFincApp> findOverdueByPageAjaxExcel(@RequestBody MfBusFincApp mfBusFincApp)throws Exception;
    
	/**
	 * 
	 * <p>Title: getBankAgricultural</p>  
	 * <p>Description:吉时与项目的银行代扣功能 </p>  
	 * @param ipage
	 * @return
	 * @throws ServiceException  
	 * @author zkq  
	 * @date 2018年10月16日 上午11:16:18
	 */
	@RequestMapping(value = "/mfBusFincApp/getBankAgricultural")
	public List<Map<String, String>> getBankAgricultural(@RequestBody Map<String, String> map) throws ServiceException;

	@RequestMapping(value = "/mfBusFincApp/doInsertFincAppForBatchWithUser")
	public MfBusFincAppChild doInsertFincAppForBatchWithUser(@RequestBody Map<String, Object> parmMap, @RequestParam("opNo") String opNo, @RequestParam("opName") String opName) throws Exception;
	@RequestMapping(value = "/mfBusFincApp/deleteForBatch")
	public Map<String,Object> deleteForBatch(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;
	@RequestMapping(value = "/mfBusFincApp/updateRepayPlanMergeFlag")
	public Map<String,Object> updateRepayPlanMergeFlag(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

    @RequestMapping(value = "/mfBusFincApp/findWarehouseOrgChengJiaoByPageAjax")
    public Ipage findWarehouseOrgChengJiaoByPageAjax(@RequestBody Ipage ipage)throws Exception;
    @RequestMapping(value = "/mfBusFincApp/getLoanInfoByCusNo")
    public  Map<String,Object> getLoanInfoByCusNo(@RequestParam("cusNo") String cusNo)throws Exception;

	@RequestMapping(value = "/mfBusFincApp/getYingHuanByCusNo")
	public  Map<String,Object> getYingHuanByCusNo(@RequestParam("fincId") String fincId,@RequestParam("repayDate") String repayDate)throws Exception;
	@RequestMapping(value = "/mfBusFincApp/getRepaymentOrderStrName")
	public  String getRepaymentOrderStrName(@RequestParam("fincId") String fincId)throws Exception;

	@RequestMapping(value = "/mfBusFincApp/doBatchRepaymentOperate")
	public Map<String, String> doBatchRepaymentOperate(@RequestBody Map<String, Object> parmMap) throws Exception;
	/**
	 * 方法描述： 根据客户号查找所有借据(分页供列表选择组件使用)
	 * @return
	 * @throws Exception
	 * @author ywh
	 * @date 2018年6月6日 下午4:24:46
	 */
	@RequestMapping(value = "/mfBusFincApp/getFincSelectByCus")
	public Ipage getFincSelectByCus(@RequestBody Ipage ipage) throws Exception;


	@RequestMapping(value = "/mfBusFincApp/getFincInststEndDateInfoMap")
	public Map<String, Object> getFincInststEndDateInfoMap(@RequestBody Map<String, String> parmMap) throws Exception;


    @RequestMapping(value = "/mfBusFincApp/getextenEndDateInfoMap")
    public Map<String, Object> getextenEndDateInfoMap(@RequestBody Map<String, String> parmMap) throws Exception;


    /**
     * @param parmMap 申请号  合同开始日期   合同期限    合同结束日
     * @return
     * @throws Exception
     * @desc 由开始日期, 期限(借据配置的最小期限与合同期限与借据配置的最大期限, 二者取最小与
     *借据最小期限做比较 如不满足取另一个)  ----------->>  推结束日期
     * @author zkq
     * @date 20190627
     */
    @RequestMapping(value = "/mfBusFincApp/doFincInitBeginAndEndDateAndTermByDateTerm")
    public Map<String, Object> doFincInitBeginAndEndDateAndTermByDateTerm(@RequestBody Map<String, Object> parmMap) throws Exception;


    /**
     * @param parmMap
     * @return 期限   借据实际结束日期
     * @throws Exception
     * @desc 借据展示结束日期  借据开始日期   appId
     * @author zkq
     * @date 20190628
     */
    @RequestMapping(value = "/mfBusFincApp/getTermByIntstEndDateAndIntstBeginDate")
    public Map<String, Object> getTermByIntstEndDateAndIntstBeginDate(@RequestBody Map<String, Object> parmMap) throws Exception;

	/**
	 * @Description 新贷后检查根据借据维度展示数据
	 * @Author zhaomingguang
	 * @DateTime 2019/9/19 10:19
	 * @Param 
	 * @return 
	 */
	@RequestMapping(value = "/mfBusFincApp/findExamineStateForFincByPageNew")
	Ipage findExamineStateForFincByPageNew(Ipage ipage);

	/**
	 * @Description 根据借据号获取借据信息
	 * @Author zhaomingguang
	 * @DateTime 2019/9/21 10:03
	 * @Param
	 * @return
	 */
	@RequestMapping(value = "/mfBusFincApp/getMfBusFincAppById")
    MfBusFincApp getMfBusFincAppById(MfBusFincApp mfBusFincApp);

    @RequestMapping(value = "/mfBusFincApp/updateBankId")
    public void updateBankId(@RequestBody Map<String,Object> parmMap) throws Exception;
    @RequestMapping(value = "/mfBusFincApp/getCapitalById")
    public MfCapitalImplementWarning getCapitalById(@RequestBody MfCapitalImplementWarning mfCapitalImplementWarning) throws Exception;
    @RequestMapping(value = "/mfBusFincApp/insertCapitalAjax")
    public MfCapitalImplementDetail insertCapitalAjax(@RequestBody MfCapitalImplementDetail mfCapitalImplementDetail) throws Exception;
    @RequestMapping(value = "/mfBusFincApp/getCapitalDetailById")
    public MfCapitalImplementDetail getCapitalDetailById(@RequestBody MfCapitalImplementDetail mfCapitalImplementDetail) throws Exception;
	/**
	 * @Description 获取所有可进行结案的借据信息
	 * @Author fuchen
	 * @DateTime 2020/03/14 10:03
	 * @Param
	 * @return
	 */
	@RequestMapping(value = "/mfBusFincApp/getClosingManage")
	public Ipage getClosingManage(@RequestBody Ipage ipage) throws ServiceException;

	/**
	 * @Description 获取所有可进行代偿的借据信息
	 * @Author fuchen
	 * @DateTime 2020/03/14 10:03
	 * @Param
	 * @return
	 */
	@RequestMapping(value = "/mfBusFincApp/getCompensatoryApply")
	public Ipage getCompensatoryApply(@RequestBody Ipage ipage) throws ServiceException;

    @RequestMapping(value = "/mfBusFincApp/getFeeCollectInfo")
	public Map<String,Object> getFeeCollectInfo(@RequestBody MfBusPact mfBusPact,@RequestParam("date") String date)throws Exception;
	@RequestMapping(value = "/mfBusFincApp/noPutout")
	public void noPutout(@RequestParam("appId") String appId, @RequestParam("fincId") String appNfincId) throws Exception;



	@RequestMapping(value = "/mfBusFincApp/putOutCancel")
	public Result putOutCancel(@RequestBody Map<String, Object> parmMap)throws Exception;
}
