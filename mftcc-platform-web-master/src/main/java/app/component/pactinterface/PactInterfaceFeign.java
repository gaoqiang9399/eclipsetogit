package app.component.pactinterface;

import java.util.List;
import java.util.Map;

import app.component.examine.entity.MfExaminePact;
import app.component.pact.entity.MfBusPact;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusFincAppChild;
import app.component.pact.entity.MfBusPactHis;
import app.component.pact.entity.MfBusFincAndRepay;
import app.component.pact.entity.MfBusFincAppHis;
import app.component.pact.entity.MfRecoverPactAmtHistory;
import app.component.pact.entity.MfRepayPlanTrial;
import app.component.pact.receaccount.entity.MfBusFincAppMain;
import app.component.pact.receaccount.entity.MfBusReceTransfer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.app.entity.MfBusApply;
import app.component.calc.core.entity.MfRepayAmt;
import app.component.calc.core.entity.MfRepayHistory;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.model.entity.ElecSignDoc;
import app.component.pact.extension.entity.MfBusExtensionApply;
import app.component.pact.extension.entity.MfBusExtensionResultDetail;
import app.component.pact.repay.entity.MfFincRepayDetail;
import app.component.prdct.entity.MfSysKind;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * Title: cusInterface.java Description:
 * 
 * @author:LiuYF@dhcc.com.cn
 * @Mon May 16 20:45:38 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface PactInterfaceFeign {

	@RequestMapping(value = "/pactInterface/getPactByPage")
	public Ipage getPactByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/pactInterface/doContractSignInit")
	public void doContractSignInit(@RequestParam("appId") String appId, @RequestParam("regNo") String regNo, 
			@RequestParam("regName") String regName, @RequestParam("orgNo") String orgNo,
			@RequestParam("orgName") String orgName) throws Exception;

	@RequestMapping(value = "/pactInterface/doContractSignInitWithUser")
	public void doContractSignInitWithUser(@RequestParam("appId") String appId, @RequestParam("opNo") String opNo,
			@RequestParam("opName") String opName, @RequestParam("brNo") String brNo, @RequestParam("brName") String brName) throws Exception;

	@RequestMapping(value = "/pactInterface/doPutoutApplyInit")
	public void doPutoutApplyInit(@RequestBody String appId) throws Exception;

	@RequestMapping(value = "/pactInterface/doLoanConfirmInit")
	public void doLoanConfirmInit(@RequestBody String appId, @RequestParam("pactId") String pactId, @RequestParam("fincId") String fincId)
			throws Exception;

	@RequestMapping(value = "/pactInterface/doCallServiceInit")
	public void doCallServiceInit(@RequestParam("appId") String appId, @RequestParam("pactId") String pactId, @RequestParam("fincId") String fincId)
			throws Exception;

	@RequestMapping(value = "/pactInterface/checkPeriodRepo")
	public boolean checkPeriodRepo(@RequestBody String pactId) throws Exception;

	@RequestMapping(value = "/pactInterface/checkNoEndPact")
	public boolean checkNoEndPact(@RequestBody String cusNo) throws Exception;

	@RequestMapping(value = "/pactInterface/checkOverduePact")
	public boolean checkOverduePact(@RequestBody String cusNo) throws Exception;

	@RequestMapping(value = "/pactInterface/updatePact")
	public void updatePact(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/pactInterface/updatePactForPad")
	public void updatePactForPad(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/pactInterface/getById")
	public MfBusPact getById(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/pactInterface/getFincAppById")
	public MfBusFincApp getFincAppById(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/getByAppId")
	public MfBusPact getByAppId(@RequestBody String appId) throws Exception;

	@RequestMapping(value = "/pactInterface/getBusStatisticalData")
	public Map<String, Object> getBusStatisticalData(@RequestParam("cusNo") String cusNo, @RequestParam("cusType") String cusType)
			throws Exception;

	@RequestMapping(value = "/pactInterface/getOtherPactList")
	public List<MfBusPact> getOtherPactList(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/pactInterface/getMfBusPactList")
	public List<MfBusPact> getMfBusPactList(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/pactInterface/getMfBusPactByCusmngNo")
	public Map<String, Object> getMfBusPactByCusmngNo(@RequestBody String CusmngNo) throws Exception;
	@RequestMapping(value = "/pactInterface/getMfBusPactByCusmngNoForFee")
	public Map<String, Object> getMfBusPactByCusmngNoForFee(@RequestBody String CusmngNo) throws Exception;

	@RequestMapping(value = "/pactInterface/deletePactByAppId")
	public void deletePactByAppId(@RequestBody String appId) throws Exception;

	@RequestMapping(value = "/pactInterface/deletePactHis")
	public void deletePactHis(@RequestBody MfBusPactHis mfBusPactHis) throws Exception;

	@RequestMapping(value = "/pactInterface/deleteMfBusFincApp")
	public void deleteMfBusFincApp(@RequestBody MfBusFincApp mfBusFincApp,
			@RequestParam("mfBusFincAppHis") MfBusFincAppHis mfBusFincAppHis) throws Exception;

	@RequestMapping(value = "/pactInterface/deleteMfFincRepayDetail")
	public void deleteMfFincRepayDetail(@RequestBody MfFincRepayDetail mfFincRepayDetail) throws Exception;

	@RequestMapping(value = "/pactInterface/deleteMfRepayHistory")
	public void deleteMfRepayHistory(@RequestBody MfRepayHistory mfRepayHistory) throws Exception;

/*
	@RequestMapping(value = "/pactInterface/getMfBusPactByPleId")
	public MfBusPact getMfBusPactByPleId(@RequestBody String pleId) throws Exception;
*/

	@RequestMapping(value = "/pactInterface/getFincAppList")
	public List<MfBusFincApp> getFincAppList(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/getFincListByFincId")
	public List<MfBusFincAppHis> getFincListByFincId(@RequestBody MfBusFincAppHis mfBusFincAppHis) throws Exception;

	@RequestMapping(value = "/pactInterface/insertFincApp")
	public void insertFincApp(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/updateFincApp")
	public void updateFincApp(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/updateFincAppChild")
	public void updateFincAppChild(@RequestBody MfBusFincAppChild mfBusFincAppChild) throws Exception;

	@RequestMapping(value = "/pactInterface/getByFincIdOrwkfId")
	public MfBusFincApp getByFincIdOrwkfId(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/submitProcess")
	public MfBusPact submitProcess(@RequestParam("pactId") String pactId, @RequestParam("firstApprovalUser") String firstApprovalUser,@RequestParam("regNo") String regNo, @RequestParam("regName") String regName, @RequestParam("orgNo") String orgNo)
			throws Exception;


	@RequestMapping(value = "/pactInterface/submitProcessWithUser")
	public MfBusPact submitProcessWithUser(@RequestParam("pactId") String pactId, @RequestParam("firstApprovalUser") String firstApprovalUser,
			@RequestParam("opNo") String opNo, @RequestParam("opName") String opName, @RequestParam("brNo") String brNo) throws Exception;

	@RequestMapping(value = "/pactInterface/doInprocess")
	public MfBusFincApp doInprocess(@RequestParam("appId") String appId, @RequestParam("firstApprovalUser") String firstApprovalUser, @RequestParam("orgNo") String orgNo)
			throws Exception;

	@RequestMapping(value = "/pactInterface/doInprocessWithUser")
	public MfBusFincApp doInprocessWithUser(@RequestParam("appId") String appId, @RequestParam("firstApprovalUser") String firstApprovalUser,
			@RequestParam("opNo") String opNo, @RequestParam("opName") String opName) throws Exception;

	@RequestMapping(value = "/pactInterface/processDataForFincApp")
	public MfBusFincApp processDataForFincApp(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/processDataForPact")
	public MfBusPact processDataForPact(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/pactInterface/getMfBusFincAppChildByBean")
	public MfBusFincAppChild getMfBusFincAppChildByBean(@RequestBody MfBusFincAppChild mfBusFincAppChild)
			throws Exception;

	@RequestMapping(value = "/pactInterface/getMfBusFincAppChildByInfo")
	public MfBusFincAppChild getMfBusFincAppChildByInfo(@RequestBody MfBusFincAppChild mfBusFincAppChild)
			throws Exception;

	@RequestMapping(value = "/pactInterface/updateFincProcess")
	public void updateFincProcess(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/applyWithdraw")
	public MfBusFincAppChild applyWithdraw(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/getFincAppShowIdListByPactId")
	public List<MfBusFincApp> getFincAppShowIdListByPactId(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/insert")
	public void insert(@RequestBody MfRecoverPactAmtHistory mfRecoverPactAmtHistory) throws Exception;

	@RequestMapping(value = "/pactInterface/getPactByCusState")
	public List<MfBusPact> getPactByCusState(@RequestBody Map<String, Object> parmMap) throws Exception;

	@RequestMapping(value = "/pactInterface/insertFincApp1")
	public void insertFincApp(@RequestBody MfBusFincApp mfBusFincApp, @RequestParam("parmMap") Map<String, Object> parmMap)
			throws Exception;

	@RequestMapping(value = "/pactInterface/insertFincAppWithUser")
	public MfBusFincApp insertFincAppWithUser(@RequestBody MfBusFincApp mfBusFincApp, @RequestParam("parmMap") Map<String, Object> parmMap,
			@RequestParam("opNo") String opNo, @RequestParam("opName") String opName) throws Exception;

	@RequestMapping(value = "/pactInterface/getMfRepayPlanList")
	public List<MfRepayPlan> getMfRepayPlanList(@RequestBody MfRepayPlan mfRepayPlan) throws Exception;

	@RequestMapping(value = "/pactInterface/getAllLoanBal")
	public String getAllLoanBal(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/updateFincappAndChiled")
	public void updateFincappAndChiled(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/getMfBusFincAppChildByBeanAll")
	public List<MfBusFincAppChild> getMfBusFincAppChildByBeanAll(@RequestBody MfBusFincAppChild mc) throws Exception;

	@RequestMapping(value = "/pactInterface/getMfBusFincAppChildByAppId")
	public MfBusFincAppChild getMfBusFincAppChildByAppId(@RequestBody MfBusFincAppChild mfBusFincAppChild)
			throws Exception;

	@RequestMapping(value = "/pactInterface/getListByPactId")
	public List<MfBusPactHis> getListByPactId(@RequestBody MfBusPactHis mfBusPactHis) throws ServiceException;

	@RequestMapping(value = "/pactInterface/getOverduePactList")
	public List<MfBusPact> getOverduePactList() throws Exception;

	@RequestMapping(value = "/pactInterface/disProcessDataForPact")
	public MfBusPact disProcessDataForPact(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/pactInterface/getTaskList")
	public List<MfBusFincAppChild> getTaskList(@RequestBody String opNo) throws Exception;

	@RequestMapping(value = "/pactInterface/doCommit")
	public Result doCommit(  @RequestParam("taskId")String taskId,  @RequestParam("pactId")String pactId,  @RequestParam("opinionType")String opinionType,
			@RequestParam("approvalOpinion")String approvalOpinion,  @RequestParam("transition")String transition,  @RequestParam("opNo")String opNo,
			@RequestParam("nextUser")String nextUser, @RequestBody MfBusPact mfBusPact,
			@RequestParam("mfBusAppFeeList") List<MfBusAppFee> mfBusAppFeeList, @RequestParam("dataMap") Map<String, Object> dataMap)
			throws Exception;

	@RequestMapping(value = "/pactInterface/getRepayOverDateByPage")
	public Ipage getRepayOverDateByPage(@RequestBody Ipage ipage, @RequestParam("mfBusFincAndRepay") MfBusFincAndRepay mfBusFincAndRepay,
			@RequestParam("scopeType") String scopeType, @RequestParam("endDate") String endDate) throws Exception;

	@RequestMapping(value = "/pactInterface/disProcessDataForFinc")
	public MfBusFincApp disProcessDataForFinc(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/updateProcess")
	public Result updateProcess( @RequestParam("taskId")String taskId,  @RequestParam("fincId")String fincId,
			@RequestParam("opinionType")String opinionType,  @RequestParam("approvalOpinion")String approvalOpinion,  @RequestParam("transition")String transition,
			@RequestParam("opNo") String opNo,  @RequestParam("nextUser")String nextUser, @RequestBody MfBusFincApp mfBusFincApp,
			@RequestParam("mfBusAppFeeList") List<MfBusAppFee> mfBusAppFeeList, @RequestParam("dataMap") Map<String, Object> dataMap)
			throws Exception;

	@RequestMapping(value = "/pactInterface/getRepayToDateByPage")
	public Ipage getRepayToDateByPage(@RequestBody Ipage ipage, @RequestParam("mfBusFincAndRepay") MfBusFincAndRepay mfBusFincAndRepay,
			@RequestParam("scopeType") String scopeType,  @RequestParam("endDate")String endDate) throws Exception;

	@RequestMapping(value = "/pactInterface/getAllLoanBalByBean")
	public String getAllLoanBalByBean(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/getFiveClassLoanBal")
	public List<Map<String, String>> getFiveClassLoanBal(@RequestBody Map<String, String> map) throws Exception;

	@RequestMapping(value = "/pactInterface/doContractSign")
	public void doContractSign(@RequestParam("appId") String appId, @RequestParam("regNo") String regNo, 
			@RequestParam("regName") String regName, @RequestParam("orgNo") String orgNo, 
			@RequestParam("orgName") String orgName) throws Exception;

	@RequestMapping(value = "/pactInterface/doContractSignForWx")
	public void doContractSignForWx(@RequestParam("appId") String appId, @RequestParam("regNo") String regNo, 
			@RequestParam("regName") String regName, @RequestParam("orgNo") String orgNo, 
			@RequestParam("orgName") String orgName) throws Exception;

	@RequestMapping(value = "/pactInterface/doContractSignOnConfigForWx")
	public void doContractSignOnConfigForWx(@RequestParam("appId") String appId, 
			@RequestParam("regNo") String regNo, @RequestParam("regName") String regName, 
			@RequestParam("orgNo") String orgNo, @RequestParam("orgName") String orgName) throws Exception;

	@RequestMapping(value = "/pactInterface/delSubmitPactForLf")
	public void delSubmitPactForLf(@RequestParam("appId") String appId, @RequestParam("regNo") String regNo, 
			@RequestParam("regName") String regName, @RequestParam("orgNo") String orgNo, 
			@RequestParam("orgName") String orgName) throws Exception;

	@RequestMapping(value = "/pactInterface/delSubmitFincForLf")
	public String delSubmitFincForLf(@RequestBody String appId) throws Exception;

	@RequestMapping(value = "/pactInterface/getPactInfoByBean")
	public Map<String, String> getPactInfoByBean(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/getAllLoanbalByCusNo")
	public String getAllLoanbalByCusNo(@RequestBody String cusNo) throws Exception;

	@RequestMapping(value = "/pactInterface/getPutoutAmtByCusNo")
	public String getPutoutAmtByCusNo(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/getByIdNewFinc")
	public MfBusFincApp getByIdNewFinc(@RequestBody MfBusFincApp mfBusFincApp) throws ServiceException;

	@RequestMapping(value = "/pactInterface/getMfBusExtensionApply")
	public MfBusExtensionApply getMfBusExtensionApply(@RequestBody String wkfAppId) throws Exception;

	@RequestMapping(value = "/pactInterface/getExtenProcessId")
	public String getExtenProcessId(@RequestBody String wkfAppId) throws Exception;

	@RequestMapping(value = "/pactInterface/submitExtensionProcess")
	public void submitExtensionProcess(@RequestBody String wkfAppId) throws Exception;

	@RequestMapping(value = "/pactInterface/getMfBusExtensionApply1")
	public MfBusExtensionApply getMfBusExtensionApply1(@RequestBody MfBusExtensionApply mfBusExtensionApply)
			throws Exception;

	@RequestMapping(value = "/pactInterface/getTodayData")
	public Map<String, Object> getTodayData(@RequestBody Map<String, Object> paramMap) throws Exception;

	@RequestMapping(value = "/pactInterface/getMfBusPactByCusNo")
	public List<MfBusPact> getMfBusPactByCusNo(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/pactInterface/getMfBusExtensionResultDetailByBean")
	public MfBusExtensionResultDetail getMfBusExtensionResultDetailByBean(
			@RequestBody MfBusExtensionResultDetail mfBusExtensionResultDetail) throws Exception;

	@RequestMapping(value = "/pactInterface/getExtensionResultDetailList")
	public List<MfBusExtensionResultDetail> getExtensionResultDetailList(
			@RequestBody MfBusExtensionResultDetail mfBusExtensionResultDetail) throws Exception;

	@RequestMapping(value = "/pactInterface/updateMfBusExtensionResultDetail")
	public void updateMfBusExtensionResultDetail(@RequestBody MfBusExtensionResultDetail extensionResultDetail)
			throws Exception;

	@RequestMapping(value = "/pactInterface/updateMfBusExtensionApply")
	public void updateMfBusExtensionApply(@RequestBody MfBusExtensionApply mfBusExtensionApplyUpd) throws Exception;

	@RequestMapping(value = "/pactInterface/getExtensionResultDetailByFincId")
	public MfBusExtensionResultDetail getExtensionResultDetailByFincId(
			@RequestBody MfBusExtensionResultDetail mfBusExtensionResultDetail) throws Exception;

	@RequestMapping(value = "/pactInterface/insertOrUpdateMfBusRepayInfo")
	public void insertOrUpdateMfBusRepayInfo(@RequestBody MfRepayAmt mfRepayAmt,
			@RequestParam("parmMap") Map<String, Object> parmMap) throws Exception;

	@RequestMapping(value = "/pactInterface/getMfBusExtensionResultDetailById")
	public MfBusExtensionResultDetail getMfBusExtensionResultDetailById(
			@RequestBody MfBusExtensionResultDetail mfBusExtensionResultDetail) throws Exception;

	@RequestMapping(value = "/pactInterface/findLoanAfterByPageForTrh")
	public List<MfBusFincApp> findLoanAfterByPageForTrh(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/applyWithdrawalsForMobileService")
	public MfBusFincAppChild applyWithdrawalsForMobileService(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/getMfBusFincAppListByPactId")
	public List<MfBusFincApp> getMfBusFincAppListByPactId(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/getPutOutSituationByDay")
	public MfBusFincApp getPutOutSituationByDay(@RequestBody String date) throws Exception;

	@RequestMapping(value = "/pactInterface/getPutOutSituationByMonth")
	public MfBusFincApp getPutOutSituationByMonth(@RequestBody String date) throws Exception;

	@RequestMapping(value = "/pactInterface/getPutOutSituationByCusMngNo")
	public List<MfBusFincApp> getPutOutSituationByCusMngNo(@RequestBody String cusMngNo) throws Exception;

	@RequestMapping(value = "/pactInterface/getMultiBusList")
	public List<MfBusPact> getMultiBusList(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/pactInterface/getMultiBusList1")
	public List<MfBusFincApp> getMultiBusList(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

    @RequestMapping(value = "/pactInterface/getMultiBusListFinish")
    public List<MfBusFincApp> getMultiBusListFinish(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

    @RequestMapping(value = "/pactInterface/getRepayHistoryListByCusNo")
    public List<MfRepayHistory> getRepayHistoryListByCusNo(@RequestBody MfRepayHistory mfRepayHistory) throws Exception;

	@RequestMapping(value = "/pactInterface/getOverdueFincListByCusMngNo")
	public List<MfBusFincApp> getOverdueFincListByCusMngNo(@RequestBody String cusMngNo) throws Exception;

	@RequestMapping(value = "/pactInterface/getFincListByCusMngNoAndSts")
	public List<MfBusFincApp> getFincListByCusMngNoAndSts(@RequestParam("cusMngNo") String cusMngNo, @RequestParam("fincSts") String fincSts)
			throws Exception;

	@RequestMapping(value = "/pactInterface/getTransactionSituationByOpNo")
	public Map<String, Object> getTransactionSituationByOpNo(@RequestBody String opNo) throws Exception;

	@RequestMapping(value = "/pactInterface/getBusinessSituationByCusNo")
	public Map<String, Object> getBusinessSituationByCusNo(@RequestBody String cusNo) throws Exception;

	@RequestMapping(value = "/pactInterface/getPutoutCountByDay")
	public MfBusFincApp getPutoutCountByDay(@RequestBody String date) throws Exception;

	@RequestMapping(value = "/pactInterface/getPutoutCountByMonth")
	public MfBusFincApp getPutoutCountByMonth(@RequestBody String date) throws Exception;

	@RequestMapping(value = "/pactInterface/findByPage")
	public List<MfBusPact> findByPage(@RequestBody Ipage ipage, @RequestParam("mfBusPact") MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/pactInterface/getBusPactApprovingListByOpNo")
	public List<MfBusPact> getBusPactApprovingListByOpNo(@RequestBody String opNo) throws Exception;

	@RequestMapping(value = "/pactInterface/getBusFincApprovingListByOpNo")
	public List<MfBusFincApp> getBusFincApprovingListByOpNo(@RequestBody String opNo) throws Exception;

	@RequestMapping(value = "/pactInterface/getPactStateByAppId")
	public Map<String, Object> getPactStateByAppId(@RequestBody String appId) throws Exception;

	@RequestMapping(value = "/pactInterface/getFincStateByAppId")
	public Map<String, Object> getFincStateByAppId(@RequestBody String appId) throws Exception;

	@RequestMapping(value = "/pactInterface/doPactCommitForApp")
	public Result doPactCommitForApp(@RequestBody Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/pactInterface/doFincCommitForApp")
	public Result doFincCommitForApp(@RequestBody Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/pactInterface/getBusPacts")
	public List<MfBusPact> getBusPacts(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/pactInterface/doAutomaticContractSign")
	public Map<String, Object> doAutomaticContractSign(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/pactInterface/doAutomaticLoan")
	public Map<String, Object> doAutomaticLoan(@RequestBody String appId) throws Exception;

	@RequestMapping(value = "/pactInterface/getMaxfincShowNoByPactNo")
	public String getMaxfincShowNoByPactNo(@RequestBody String pactNo) throws Exception;

	@RequestMapping(value = "/pactInterface/doAutomaticRepayPlan")
	public Map<String, Object> doAutomaticRepayPlan(@RequestBody String fincId,@RequestParam("regNo") String regNo) throws Exception;

	@RequestMapping(value = "/pactInterface/getIsAllFincEnd")
	public String getIsAllFincEnd(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/pactInterface/getPutoutInfoByFincId")
	public Map<String, Object> getPutoutInfoByFincId(@RequestBody String fincId) throws Exception;

	@RequestMapping(value = "/pactInterface/updateAmtForPact")
	public void updateAmtForPact(@RequestBody Map<String, Object> pactMap) throws Exception;

	@RequestMapping(value = "/pactInterface/updateAmtForFinc")
	public void updateAmtForFinc(@RequestBody Map<String, Object> pactMap) throws Exception;

	@RequestMapping(value = "/pactInterface/getPactElecSignDoc")
	public ElecSignDoc getPactElecSignDoc(@RequestBody String appId) throws Exception;

	@RequestMapping(value = "/pactInterface/doElecSignDoc")
	public void doElecSignDoc(@RequestBody ElecSignDoc eleDoc) throws Exception;

	@RequestMapping(value = "/pactInterface/findPactByPageForPad")
	public Ipage findPactByPageForPad(@RequestBody Ipage ipage, @RequestParam("mfBusPact") MfBusPact mfBusPact)
			throws ServiceException;

	@RequestMapping(value = "/pactInterface/doGenerateLoanInfo")
	public Map<String, Object> doGenerateLoanInfo(@RequestBody String appId) throws Exception;

	@RequestMapping(value = "/pactInterface/getEntrustElecSignDoc")
	public ElecSignDoc getEntrustElecSignDoc(@RequestBody String appId) throws Exception;

	@RequestMapping(value = "/pactInterface/findFincAppByPageForB")
	public Ipage findFincAppByPageForB(@RequestBody Ipage ipage, @RequestParam("cusMngNo") String cusMngNo) throws Exception;

	@RequestMapping(value = "/pactInterface/getByPactId")
	public List<MfRepayPlanTrial> getByPactId(@RequestBody String pactId) throws Exception;

	@RequestMapping(value = "/pactInterface/getEQBElecPactData")
	public Map<String, String> getEQBElecPactData(@RequestBody MfBusApply mfBusApply) throws Exception;

	@RequestMapping(value = "/pactInterface/getMfBusFincAppForB")
	public Ipage getMfBusFincAppForB(@RequestBody Ipage ipage, @RequestParam("paramMap") Map<String, String> paramMap)
			throws Exception;

	@RequestMapping(value = "/pactInterface/getNoLoanPactInfo")
	public Ipage getNoLoanPactInfo(@RequestBody Ipage ipage,@RequestParam("mfBusPact") MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/pactInterface/getListByDESC")
	public List<MfBusPactHis> getListByDESC(@RequestBody MfBusPactHis mfBusPactHis) throws Exception;

	@RequestMapping(value = "/pactInterface/updatePactApprove")
	public void updatePactApprove(@RequestBody MfBusPactHis mfBusPactHis) throws Exception;

	@RequestMapping(value = "/pactInterface/getFincListByFincIdDESC")
	public List<MfBusFincAppHis> getFincListByFincIdDESC(@RequestBody MfBusFincAppHis mfBusFincAppHis)
			throws Exception;

	@RequestMapping(value = "/pactInterface/updateFincApprove")
	public void updateFincApprove(@RequestBody MfBusFincAppHis mfBusFincAppHis) throws Exception;

	@RequestMapping(value = "/pactInterface/findLoanAfterByCusNo")
	public List<MfBusPact> findLoanAfterByCusNo(@RequestBody String cusNo) throws Exception;

	@RequestMapping(value = "/pactInterface/getCreditPact")
	public Map<String, Object> getCreditPact(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/pactInterface/getCreditPactList")
	public List<MfBusPact> getCreditPactList(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/pactInterface/getAppIdStrByFincSts")
	public Map<String, Object> getAppIdStrByFincSts(@RequestBody String fincSts) throws Exception;

	@RequestMapping(value = "/pactInterface/addFincAppForPad")
	public Map<String, Object> addFincAppForPad(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/doContractSignForApp")
	public void doContractSignForApp(@RequestParam("appId") String appId, @RequestParam("opNo") String opNo) throws Exception;
	@RequestMapping(value = "/pactInterface/getOverduePactIpage")
	public Ipage getOverduePactIpage(@RequestBody Ipage ipg) throws Exception;
	
	@RequestMapping(value = "/pactInterface/findByCusNo")
	public List<MfBusFincApp> findByCusNo(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/getReceFincList")
	public List<MfBusReceTransfer> getReceFincList(@RequestBody MfBusFincAppMain mfBusFincAppMain) throws Exception;
	
	@RequestMapping(value = "/pactInterface/getPutoutSumAmtByKindNo")
	public double getPutoutSumAmtByKindNo(@RequestBody MfSysKind mfSysKind)  throws Exception;

	@RequestMapping(value = "/pactInterface/getMfBusFincAppMain")
	public MfBusFincAppMain getMfBusFincAppMain(@RequestBody MfBusFincAppMain mfBusFincAppMain) throws Exception;

    @RequestMapping(value = "/pactInterface/doBusinessCommit")
    public Map<String,Object> doBusinessCommit(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/pactInterface/getAllLoanbalByCusNoNew")
	public String getAllLoanbalByCusNoNew(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;
	@RequestMapping(value = "/pactInterface/checkPutoutAmt")
	public Map<String,String> checkPutoutAmt(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	/**
	 * 贷后检查获取客户下的在保情况
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pactInterface/getExaminePact")
	public List<MfExaminePact> getExaminePact(@RequestBody String cusNo) throws Exception;
}
