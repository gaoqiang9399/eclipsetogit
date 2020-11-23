package app.component.pact.feign;

import java.util.List;
import java.util.Map;

import app.component.pact.entity.MfBusPactHis;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.app.entity.MfBusApply;
import app.component.model.entity.ElecSignDoc;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.wkf.entity.WkfApprovalUser;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
 * Title: MfBusPactBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 27 14:34:25 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusPactFeign {

	@RequestMapping(value = "/mfBusPact/insert")
	public void insert(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/delete")
	public void delete(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/update")
	public void update(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/updatePactByOne")
	public void updatePactByOne(@RequestBody MfBusPact mfBusPact,@RequestParam("dataMap") Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/mfBusPact/getById")
	public MfBusPact getById(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/getByPactNo")
	public List<MfBusPact> getByPactNo(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/getByAppId")
	public MfBusPact getByAppId(@RequestBody String appId) throws Exception;

	@RequestMapping(value = "/mfBusPact/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusPact/findCheckByPage")
	public Ipage findCheckByPage(@RequestBody Ipage ipage) throws Exception;
	@RequestMapping(value = "/mfBusPact/updateForCheck")
	public void updateForCheck(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/findSignPactByPage")
	public Ipage findSignPactByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfBusPact/findBankRepayByPage")
	public Ipage findBankRepayByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfBusPact/findLoanAfterByPage")
	public Ipage findLoanAfterByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusPact/updatePactSts")
	public void updatePactSts(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/getPactByCusNo")
	public List<MfBusPact> getPactByCusNo(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/updatePact")
	public void updatePact(@RequestBody Map<String, Object> parmMap) throws Exception;

	@RequestMapping(value = "/mfBusPact/updatePactForApp")
	public void updatePactForApp(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/updatePactForPad")
	public void updatePactForPad(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/getBusStatisticalData")
	public Map<String, Object> getBusStatisticalData(@RequestBody Map<String, Object> parmMap) throws Exception;

	@RequestMapping(value = "/mfBusPact/getOtherPactList")
	public List<MfBusPact> getOtherPactList(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/getMfBusPactList")
	public List<MfBusPact> getMfBusPactList(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/getMfBusPactByCusmngNo")
	public Map<String, Object> getMfBusPactByCusmngNo(@RequestBody String CusmngNo) throws Exception;

	@RequestMapping(value = "/mfBusPact/listAllNotSync")
	public List<MfBusPact> listAllNotSync() throws Exception;

	@RequestMapping(value = "/mfBusPact/listAllSync")
	public List<MfBusPact> listAllSync() throws Exception;

	@RequestMapping(value = "/mfBusPact/getMfBusPactByPleId")
	public MfBusPact getMfBusPactByPleId(@RequestBody String pleId) throws Exception;
	
	@RequestMapping(value = "/mfBusPact/getBusPactOverList")
	public Ipage getBusPactOverList(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfBusPact/getBusDataCleanList")
	public Ipage getBusDataCleanList(@RequestBody Ipage ipage,@RequestParam("mfBusPact") MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/processDataForPact")
	public MfBusPact processDataForPact(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/disProcessDataForPact")
	public MfBusPact disProcessDataForPact(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/submitPactApprovalPass")
	public Map<String, String> submitPactApprovalPass(@RequestBody MfBusPactHis mfBusPactHis) throws Exception;

	@RequestMapping(value = "/mfBusPact/getPactByCusState")
	public List<MfBusPact> getPactByCusState(@RequestBody Map<String, Object> parmMap) throws Exception;

	@RequestMapping(value = "/mfBusPact/getCaptitalAmtConfirmList")
	public Ipage getCaptitalAmtConfirmList(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusPact/doDisagree2")
	public void doDisagree2(@RequestBody String appId, @RequestParam("regNo") String regNo) throws Exception;

	@RequestMapping(value = "/mfBusPact/getOverduePactList")
	public List<MfBusPact> getOverduePactList() throws Exception;
	
	@RequestMapping(value = "/mfBusPact/delSubmitPactForLf")
	public void delSubmitPactForLf(@RequestBody String appId) throws Exception;

	@RequestMapping(value = "/mfBusPact/getBussFlowApproveInfo")
	public Map<String, Object> getBussFlowApproveInfo(@RequestBody String pactId) throws Exception;

	@RequestMapping(value = "/mfBusPact/doFincRateFloat")
	public void doFincRateFloat(@RequestBody MfBusPact mfBusPact) throws Exception;


	@RequestMapping(value = "/mfBusPact/doBaseRateOld")
	public MfBusPact doBaseRateOld(@RequestBody MfBusPact mfBusPact) throws Exception;


	@RequestMapping(value = "/mfBusPact/doBaseRateBusOld")
	public MfBusApply doBaseRateBusOld(@RequestBody MfBusApply mfBusApply) throws Exception;


	@RequestMapping(value = "/mfBusPact/getPactInfoByBean")
	public Map<String, String> getPactInfoByBean(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/mfBusPact/getRepayMode",method=RequestMethod.POST)
	public JSONArray getRepayMode() throws Exception;

	@RequestMapping(value = "/mfBusPact/getUserForFristTask")
	public List<WkfApprovalUser> getUserForFristTask(@RequestBody String pactId,@RequestParam("regNo") String regNo) throws Exception;

	@RequestMapping(value = "/mfBusPact/getUserForTask")
	public List<WkfApprovalUser> getUserForTask(@RequestBody String processId,@RequestParam("nodeName") String nodeName,@RequestParam("opNo") String opNo) throws Exception;

	@RequestMapping(value = "/mfBusPact/getTodayData")
	public Map<String, Object> getTodayData(@RequestBody Map<String, Object> paramMap) throws Exception;

	@RequestMapping(value = "/mfBusPact/getMfBusPactByExten")
	public MfBusPact getMfBusPactByExten(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/getMultiBusList")
	public List<MfBusPact> getMultiBusList(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/getBusinessSituationByCusNo")
	public Map<String, Object> getBusinessSituationByCusNo(@RequestBody String cusNo) throws Exception;

	@RequestMapping(value = "/mfBusPact/getLoanBalByCusNo")
	public Map<String, Object> getLoanBalByCusNo(@RequestBody String cusNo) throws Exception;

	@RequestMapping(value = "/mfBusPact/findLoanAfterByCusNo")
	public List<MfBusPact> findLoanAfterByCusNo(@RequestBody String cusNo) throws Exception;

	@RequestMapping(value = "/mfBusPact/getBusPactApprovingListByOpNo")
	public List<MfBusPact> getBusPactApprovingListByOpNo(@RequestBody String opNo) throws Exception;

	@RequestMapping(value = "/mfBusPact/findByPageList")
	public List<MfBusPact> findByPageList(@RequestBody Ipage ipage,@RequestParam("mfBusPact") MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/getBusPacts")
	public List<MfBusPact> getBusPacts(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/findPactBatchByPage")
	public Ipage findPactBatchByPage(@RequestBody Ipage ipg) throws Exception;

	@RequestMapping(value = "/mfBusPact/updateForAddAmt")
	public void updateForAddAmt(@RequestBody Map<String, Object> pactMap) throws Exception;

	@RequestMapping(value = "/mfBusPact/findPactByPageForPad")
	public Ipage findPactByPageForPad(@RequestBody Ipage ipage,@RequestParam("mfBusPact") MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/getPactElecSignDoc")
	public ElecSignDoc getPactElecSignDoc(@RequestBody String appId) throws Exception;

	@RequestMapping(value = "/mfBusPact/doElecSignDoc")
	public void doElecSignDoc(@RequestBody ElecSignDoc eleDoc) throws Exception;

	@RequestMapping(value = "/mfBusPact/getEntrustElecSignDoc")
	public ElecSignDoc getEntrustElecSignDoc(@RequestBody String appId) throws Exception;

	@RequestMapping(value = "/mfBusPact/getElecFile")
	public String getElecFile(@RequestBody String pactId, @RequestParam("tempNo") String tempNo) throws Exception;

	@RequestMapping(value = "/mfBusPact/getEQBElecPactData")
	public Map<String, String> getEQBElecPactData(@RequestBody MfBusApply mfBusApply) throws Exception;

	@RequestMapping(value = "/mfBusPact/doOtherPactNoZM")
	public void doOtherPactNoZM(@RequestBody String appId,@RequestParam("pactId") String pactId) throws Exception;

	@RequestMapping(value = "/mfBusPact/doElecSignPactZM")
	public void doElecSignPactZM(@RequestBody String appId,@RequestParam("pactId") String pactId);

	@RequestMapping(value = "/mfBusPact/getNoLoanPactInfo")
	public Ipage getNoLoanPactInfo(@RequestBody Ipage ipage,@RequestParam("mfBusPact") MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/getCreditPact")
	public Map<String, Object> getCreditPact(@RequestBody MfBusPact mfBusPact) throws Exception;

	@RequestMapping(value = "/mfBusPact/getCreditPactList")
	public List<MfBusPact> getCreditPactList(@RequestBody MfBusPact mfBusPact) throws Exception;

	/**
	 * 方法描述： 渠道系统合同签约列表
	 * 
	 * @param ipage
	 * @return
	 * @throws ServiceException Ipage
	 * @author 沈浩兵
	 * @date 2018-3-17 下午5:50:54
	 */
	@RequestMapping(value = "/mfBusPact/findTrenchSignPactByPage")
	public Ipage findTrenchSignPactByPage(Ipage ipage) throws Exception;

	

	/**
	 * 方法描述：  费用合同查询列表
	 * 
	 * @param ipage
	 * @return
	 * @throws ServiceException Ipage
	 * @author 段泽宇
	 * @date 2018-5-5 上午10:40:54
	 */
	@RequestMapping(value = "/mfBusPact/findFeePactByPageAjax")
	public Ipage findFeePactByPageAjax(@RequestBody Ipage ipage);
	/**
	 * 
	 * 方法描述： 获取已完结的合同列表
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author zhs
	 * @date 2018年6月5日 上午10:15:27
	 */
	@RequestMapping(value = "/mfBusPact/getFinishedPactListByPage")
	public Ipage getFinishedPactListByPage(@RequestBody Ipage ipage) throws Exception;
	@RequestMapping(value = "/doElecSignPactWzty",method=RequestMethod.POST)
	public boolean doElecSignPactWzty(@RequestParam(value="appId") String appId,@RequestParam(value="templateNo")String templateNo);
    /**
     * 
     * 方法描述：合同签约节点 合同开始日期修改 获取合同结束日期
     * @param parmMap
     * @return 
     * Map<String,Object>
     * @author wd
     * @date 2018年6月30日 上午11:22:01
     */
	@RequestMapping(value = "/mfBusPact/getPactEndDateInfoMap")
	public Map<String, Object> getPactEndDateInfoMap(@RequestBody Map<String, String> parmMap)throws Exception;


	

	@RequestMapping(value = "/mfBusPact/findAgenciesCusByPageAjax")
	public Ipage findAgenciesCusByPageAjax(@RequestBody Ipage ipage)throws Exception;
	


	/**
	 * 方法描述： 提前解约合同保存(主要针对额度可循环的合同，所有的借据都已完结了，合同还没有到授信结束日期的，可以进行提前解约终止合同)
	 * @param mfBusPact
	 * @return
	 * @throws Exception
	 * @author zhs
	 * @date 2018年7月4日 下午16:22:01
	 */
	@RequestMapping(value = "/mfBusPact/pactPreEnd")
	public Map<String,Object> pactPreEnd(@RequestBody MfBusPact mfBusPact) throws Exception;
	/**
	 *
	 * 方法描述：获取合同可解约的标志
	 * @param mfBusPact
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-4-17 下午5:43:57
	 */
	@RequestMapping(value = "/mfBusPact/getPactEndFlag")
	public String getPactEndFlag(@RequestBody MfBusPact mfBusPact) throws Exception;
	/**
	 * 方法描述： 获取核心企业成交客户
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 仇招
	 * @date 2018年7月16日 下午7:57:36
	 */
	@RequestMapping(value = "/mfBusPact/findCoreCompanyCusByPageAjax")
	public Ipage findCoreCompanyCusByPageAjax(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 方法描述： 获取可以进行账款转让的合同
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusPact/getPactListForReceTrans")
	public Ipage getPactListForReceTrans(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 方法描述： 获取可以进行融资额度确认的合同
	 * @param ipage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusPact/getPactListForFincConfirm")
	public Ipage getPactListForFincConfirm(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 方法描述:凤安项目-合作社向凤安平台推送待放款借据
	 * @param pactInfosJson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusPact/pushPactInfosAjax")
	public Map<String,Object> pushPactInfosAjax(String pactInfosJson) throws Exception;

    @RequestMapping(value = "/mfBusPact/findWarehouseOrgCusByPageAjax")
    public Ipage findWarehouseOrgCusByPageAjax(@RequestBody Ipage ipage) throws Exception;


    /**
     * @param fincId
     * @return
     * @throws Exception
     * @desc 根据借据号判断还款计划是否可进行修改  贷后
     * @author zkq
     */
    @RequestMapping(value = "/mfBusPact/doIsUpdatePrePlan")
    public Map<String, Object> doIsUpdatePrePlan(@RequestBody String fincId) throws Exception;
	/**
	 * 货值预警
	 * @param ipage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusPact/collateralAmtWarnList")
	public Ipage collateralAmtWarnList(@RequestBody Ipage ipage) throws Exception;
	/**
	*@Description: 根据银行账号主键查询
	*@Author: lyb
	*@date: 2019/7/29
	*/
	@RequestMapping(value = "/mfBusPact/getMfBusPactListByBankId")
	public int getMfBusPactListByBankId(@RequestBody String bankId) throws Exception;

	/**
	 * @Description 根据客户号获取其下面所有已放款未完结的合同信息
	 * @Author zhaomingguang
	 * @DateTime 2019/9/20 18:34
	 * @Param 
	 * @return 
	 */
	@RequestMapping(value = "/mfBusPact/findByPageByCusNo")
	Ipage findByPageByCusNo(Ipage ipage) throws Exception;

	/**
	 * @Description 根据客户号判断该客户下面是否有正在执行的贷款信息
	 * @Author zhaomingguang
	 * @DateTime 2019/10/8 10:36
	 * @Param 
	 * @return 
	 */
	@RequestMapping(value = "/mfBusPact/checkHavePactByCusNo")
	Map<String,Object> checkHavePactByCusNo(MfBusPact mfBusPact)throws Exception;

	@RequestMapping(value = "/mfBusPact/getListByAccount")
	public Ipage getListByAccount(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfBusPact/getFeeCollectSource")
    Ipage getFeeCollectSource(@RequestBody Ipage ipage)throws ServiceException;

	@RequestMapping(value = "/mfBusPact/getPactList")
	Ipage getPactList(@RequestBody Ipage ipage)throws ServiceException;
	@RequestMapping(value = "/mfBusPact/getPactSumByCusNo")
	Double getPactSumByCusNo(@RequestBody MfBusPact mfBusPact)throws ServiceException;

	@RequestMapping(value = "/mfBusPact/getVouAfterTrackByCusNo")
    List<MfBusPact> getVouAfterTrackByCusNo(@RequestBody MfBusPact mfBusPact)throws Exception;
}

