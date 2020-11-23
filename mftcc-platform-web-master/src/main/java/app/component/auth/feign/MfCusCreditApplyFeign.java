package  app.component.auth.feign;

import java.util.List;
import java.util.Map;

import app.component.auth.entity.MfBusBreedCredit;
import app.component.auth.entity.MfCusAgenciesCredit;
import app.component.cus.entity.MfBankNameArea;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCusCreditApplyHis;
import app.component.prdct.entity.MfSysKind;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfCusCreditApplyBo.java
* Description:客户授信申请业务操作控制
* @author:LJW
* @Mon Feb 27 10:43:09 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusCreditApplyFeign {
	
	@RequestMapping(value = "/mfCusCreditApply/insert")
	public MfCusCreditApply insert(@RequestBody MfCusCreditApply mfCusCreditApply) throws ServiceException;
	/**
	 * 
	 * 方法描述： 插入授信申请信息并提交流程
	 * @param mfCusCreditApply
	 * @param dataMap
	 * @return
	 * @throws ServiceException
	 * MfCusCreditApply
	 * @author 沈浩兵
	 * @date 2017-6-22 上午11:09:47
	 */
	@RequestMapping(value = "/mfCusCreditApply/insertStartProcess")
	public MfCusCreditApply insertStartProcess(@RequestBody Map<String,Object> dataMap) throws ServiceException;

	@RequestMapping(value = "/mfCusCreditApply/delete")
	public void delete(@RequestBody MfCusCreditApply mfCusCreditApply) throws ServiceException;
	
	/**
	 * 根据流程id删除授信请求数据
	 * @author LJW
	 * date 2017-3-13
	 */
	@RequestMapping(value = "/mfCusCreditApply/deleteByWkfAppId")
	public void deleteByWkfAppId(@RequestBody MfCusCreditApply mfCusCreditApply) throws ServiceException;
	/**
	 * 方法描述：授信调查提交（更新授信申请及授信产品信息--海马使用）
	 * @param dataMap
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfCusCreditApply/submitCreditInspectReport")
	public void submitCreditInspectReport(@RequestBody Map<String,Object> dataMap) throws ServiceException;

	@RequestMapping(value = "/mfCusCreditApply/update")
	public void update(@RequestBody MfCusCreditApply mfCusCreditApply) throws ServiceException;
	@RequestMapping(value = "/mfCusCreditApply/updateForConfig")
	public void updateForConfig(@RequestBody Map<String,Object> dataMap) throws Exception;

	/**
	 * 根据授信申请ID更新审批状态
	 * @author LJW
	 * date 2017-3-3
	 */
	@RequestMapping(value = "/mfCusCreditApply/updateByAppIdCreditSts")
	public void updateByAppIdCreditSts(@RequestBody MfCusCreditApply mfCusCreditApply) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditApply/getById")
	public MfCusCreditApply getById(@RequestBody MfCusCreditApply mfCusCreditApply) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditApply/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCusCreditApply") MfCusCreditApply mfCusCreditApply) throws ServiceException;
	
	/**
	 * 根据客户编号查询
	 * @author LJW
	 * date 2017-2-28
	 */
	@RequestMapping(value = "/mfCusCreditApply/getByCusNos")
	public List<MfCusCreditApply> getByCusNos(@RequestBody MfCusCreditApply mfCusCreditApply)throws ServiceException;
	
	/**
	 * 获取降序后的最新记录
	 * @author LJW
	 * date 2017-2-28
	 */
	@RequestMapping(value = "/mfCusCreditApply/getOrderDescFirst")
	public MfCusCreditApply getOrderDescFirst(@RequestBody MfCusCreditApply mfCusCreditApply) throws ServiceException;
	
	/**
	 * 根据流程id 更新
	 * @author LJW
	 * date 2017-2-28
	 */
	@RequestMapping(value = "/mfCusCreditApply/updateByWkfAppId")
	public void updateByWkfAppId(@RequestBody MfCusCreditApply mfCusCreditApply) throws ServiceException;
	
	/**
	 * 根据客户编号和最后修改时间降序后的第一条记录
	 * @author LJW
	 * date 2017-3-1
	 */
	@RequestMapping(value = "/mfCusCreditApply/getByCusNoAndOrederFirst")
	public MfCusCreditApply getByCusNoAndOrederFirst(@RequestBody MfCusCreditApply mfCusCreditApply) throws ServiceException;
	
	/**
	 * 根据业务流程id查询
	 * @author LJW
	 * date 2017-3-1
	 */
	@RequestMapping(value = "/mfCusCreditApply/getByWkfId")
	public MfCusCreditApply getByWkfId(@RequestBody MfCusCreditApply mfCusCreditApply) throws ServiceException;
	
	/**
	 * 将授信申请提交至审批流程
	 * @author LJW
	 * date 2017-3-3
	 */
	@RequestMapping(value = "/mfCusCreditApply/submitProcess")
	public MfCusCreditApply submitProcess(@RequestBody MfCusCreditApply mfCusCreditApply) throws ServiceException;
	
	/**
	 * 提交授信审批
	 * @author LJW
	 * date 2017-3-6
	 */
	@RequestMapping(value = "/mfCusCreditApply/doCommitApprove")
	public Result doCommitApprove(@RequestBody String taskId,@RequestParam("examHisId")  String examHisId,@RequestParam("opinionType")  String opinionType,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("transition")  String transition,@RequestParam("opNo")  String opNo,@RequestParam("nextUser") String nextUser,@RequestParam("mfCusCreditApplyHis") MfCusCreditApplyHis mfCusCreditApplyHis) throws ServiceException;
	
	/**
	 * 调整授信时插入新的授信数据
	 * @author LJW
	 * date 2017-3-10
	 */
	@RequestMapping(value = "/mfCusCreditApply/insertByUpdate")
	public void insertByUpdate(@RequestBody MfCusCreditApply mfCusCreditApply) throws ServiceException;
	/**
	 * 
	 * 方法描述： 初始化发起授信数据，
	 * 验证客户是否授信过,如果有过授信，当前日期是否在授信期限内，如果在授信期限内，返回使用的授信调整表单
	 * 如果从未授信过，返回授信新增页面。
	 * @param mfCusCreditApply
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-6-22 下午8:45:46
	 */
	@RequestMapping(value = "/mfCusCreditApply/initCusCreditedInput")
	public Map<String,Object> initCusCreditedInput(@RequestBody MfCusCreditApply mfCusCreditApply) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得授信尽调报告数据
	 * @param dataMap
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-6-25 下午3:38:07
	 */
	@RequestMapping(value = "/mfCusCreditApply/getCreditReportDataMap")
	public Map<String,Object> getCreditReportDataMap(@RequestBody Map<String,Object> dataMap) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditApply/getCreditApproveDataMap")
	public Map<String,Object> getCreditApproveDataMap(@RequestBody Map<String,Object> dataMap) throws ServiceException;
	@RequestMapping(value = "/mfCusCreditApply/getCreditPrimaryApproveDataMap")
	public Map<String,Object> getCreditPrimaryApproveDataMap(@RequestBody Map<String,Object> dataMap) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得最终的授信详情数据
	 * @param dataMap
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-6-27 下午2:22:50
	 */
	@RequestMapping(value = "/mfCusCreditApply/getCreditDetailDataMap")
	public Map<String,Object> getCreditDetailDataMap(@RequestBody Map<String,Object> dataMap) throws Exception;
	/**
	 * 
	 * 方法描述： 获得每次用信的授信详情数据
	 * @param dataMap
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-7-7 下午10:17:13
	 */
	@RequestMapping(value = "/mfCusCreditApply/getCreditDetailHisDataMap")
	public Map<String,Object> getCreditDetailHisDataMap(@RequestBody Map<String,Object> dataMap) throws ServiceException;
	/**
	 * 
	 * 方法描述： 检查授信申请业务流程是否结束
	 * @param cusNo
	 * @param wkfAppId
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-7-13 下午3:42:40
	 */
	@RequestMapping(value = "/mfCusCreditApply/checkWkfEndSts")
	public Map<String,Object> checkWkfEndSts(@RequestBody String cusNo,@RequestParam("wkfAppId") String wkfAppId,@RequestParam("creditModel") String creditModel) throws ServiceException;
	/**
	/**
	 *
	 * 方法描述： 检查授信申请业务流程是否结束(惠农贷)
	 * @param parmJson
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 段泽宇
	 * @date 2018-9-27 上午10:42:40
	 */
	@RequestMapping(value = "/mfCusCreditApply/checkWkfEndStsHND")
	public Map<String,Object> checkWkfEndStsHND(@RequestBody String parmJson) throws ServiceException;
	/**
	 *
	 * 方法描述： 
	 * @param dataMap
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-8-27 下午2:30:22
	 */
	@RequestMapping(value = "/mfCusCreditApply/getCreditTemplateDataMap")
	public Map<String,Object> getCreditTemplateDataMap(@RequestBody Map<String,Object> dataMap) throws ServiceException;
	/**
	 * 
	 * 方法描述： 查询是否保存后授信模板，尽调报告、授信信息
	 * @param dataMap
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-8-27 下午4:39:04
	 */
	@RequestMapping(value = "/mfCusCreditApply/getIfSaveCreditTemplateInfo")
	public Map<String,Object> getIfSaveCreditTemplateInfo(@RequestBody Map<String,Object> dataMap) throws ServiceException;
	

	@RequestMapping(value = "/mfCusCreditApply/updateCreditBusStage")
	public Result updateCreditBusStage(@RequestBody String creditAppId,@RequestParam("wkfAppId") String wkfAppId,@RequestParam("opNo") String opNo) throws Exception;
	
	@RequestMapping("/mfCusCreditApply/findByPageProject")
	public Ipage findByPageProject(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述： 获得授信数据
	 * @param cusNo
	 * @param creditModel
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年4月26日 上午11:13:46
	 */
	@RequestMapping(value = "/mfCusCreditApply/getCreditData")
	public Map<String,Object> getCreditData(@RequestBody String cusNo,@RequestParam("creditModel") String creditModel,@RequestParam("creditType") String creditType,@RequestParam("creditAppId") String creditAppId) throws Exception;
	
	/**
	 * 方法描述：其他lexington授信调查提交（更新授信申请及授信产品信息）
	 * @param dataMap
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfCusCreditApply/submitCreditInspectReportOther")
	public void submitCreditInspectReportOther(@RequestBody Map<String,Object> dataMap) throws ServiceException;

	/**
	 * 方法描述： 获取授信菜单列表分页数据
	 * @param ipage
	 * @return
	 * @throws ServiceException
	 * Ipage
	 * @author YuShuai
	 * @date 2018年6月23日 下午2:32:28
	 */
	@RequestMapping("/mfCusCreditApply/findListAjax")
	public Ipage findListAjax(@RequestBody Ipage ipage)throws ServiceException;

	@RequestMapping("/mfCusCreditApply/findListForArchiveAjax")
	public Ipage findListForArchiveAjax(@RequestBody Ipage ipage)throws ServiceException;
	/**
	 * 方法描述： 获取可授信的客户列表
	 * @param ipage
	 * @return
	 * @throws ServiceException
	 * Ipage
	 * @author YuShuai
	 * @date 2018年6月26日 下午4:49:41
	 */
	@RequestMapping("/mfCusCreditApply/getCreditUserList")
	public Ipage getCreditUserList(@RequestBody Ipage ipage)throws ServiceException;
	
	/**
	 * 方法描述： 获取授信信息
	 * @param creditAppId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YuShuai
	 * @date 2018年7月7日 下午4:06:12
	 */
	@RequestMapping("/mfCusCreditApply/getCreditInfoAjax")
	public Map<String, Object> getCreditInfoAjax(@RequestParam("creditAppId")String creditAppId) throws Exception;
	/**
	 * 方法描述： 获取客户可进行授信的产品
	 * @param mfSysKinds
	 * @return
	 * @throws Exception
	 * List<MfSysKind>
	 * @author YuShuai
	 * @param cusNo 
	 * @date 2018年7月11日 上午10:19:11
	 */
	@RequestMapping("/mfCusCreditApply/getApplyKindList")
	public List<MfSysKind> getApplyKindList(@RequestBody List<MfSysKind> mfSysKinds, @RequestParam("cusNo")String cusNo)throws Exception;
	/**
	 * 方法描述： 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2018年7月11日 上午11:41:01
	 */
	@RequestMapping("/mfCusCreditApply/getCreditProSum")
	public String getCreditProSum(@RequestParam("cusNo")String cusNo)throws Exception;
	
	/**
	 * 方法描述： 额度测算
	 * @param dataMap
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfCusCreditApply/creditlimitAmtCount")
	public Map<String,Object> creditlimitAmtCount(@RequestBody Map<String,Object> dataMap) throws ServiceException;
	
	/**
	 * 修改授信合同和申请表是否有效
	 * @param mfCusCreditApply
	 * @param KindNos
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfCusCreditApply/dealMfCusCreditValide")
	public void dealMfCusCreditValide(@RequestBody MfCusCreditApply mfCusCreditApply,@RequestParam("kindNos") String kindNos) throws Exception;

	/**
	 * 授信申请提交
	 * @param dataMap
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfCusCreditApply/submitInput")
	public MfCusCreditApply submitInput(@RequestBody Map<String, Object> dataMap) throws ServiceException;
	/**
	 * 获取立项尽调报告调查历史
	 * @param creditApId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfCusCreditApply/getInvestHistoryInit")
	public List<Map<String,Object>> getInvestHistoryInit(@RequestParam(name="creditAppId") String creditAppId) throws Exception;

    @RequestMapping(value = "/mfCusCreditApply/insertStop")
    public Map <String, Object> insertStop(@RequestBody MfCusCreditApply mfCusCreditApply) throws Exception;

    @RequestMapping(value = "/mfCusCreditApply/getCusCreditData")
    Map<String,Object> getCusCreditData(@RequestParam(name="cusNo") String cusNo) throws Exception;
	/**
	 *
	 * @param taskId 当前任务Id
	 *
	 * @param opinionType 意见类型
	 *
	 * @param approvalOpinion 审批意见
	 *
	 * @param transition 跳转路径
	 *
	 * @param opNo 操作员
	 *
	 * @param nextUser 下一环节审批人
	 *
	 * @return Result 成功返回 Result
	 */
	@RequestMapping(value = "/mfCusCreditApply/doCommitWkf")

	public Result doCommitWkf(@RequestBody  Map<String, Object> dataMap)throws ServiceException;

	@RequestMapping(value = "/mfCusCreditApply/calcQuotaAjax")
    Map<String,Object> calcQuotaAjax(@RequestBody  Map<String,Object> map)throws ServiceException;
	@RequestMapping(value = "/mfCusCreditApply/getByCreditAppId")
	List<MfCusAgenciesCredit> getByCreditAppId(@RequestBody  MfCusAgenciesCredit mfCusAgenciesCredit)throws ServiceException;
	@RequestMapping(value = "/mfCusCreditApply/getBreedByCreditAppId")
	List<MfBusBreedCredit> getBreedByCreditAppId(@RequestBody  MfBusBreedCredit mfBusBreedCredit)throws ServiceException;
	@RequestMapping(value = "/mfCusCreditApply/getMfBankNameAreaList")
	List<MfBankNameArea> getMfBankNameAreaList(@RequestBody MfBankNameArea mfBankNameArea) throws Exception;
	@RequestMapping(value = "/mfCusCreditApply/deleteAgencies")
	Map<String,Object> deleteAgencies(@RequestBody String id) throws Exception;
	@RequestMapping(value = "/mfCusCreditApply/deleteBreed")
	Map<String,Object> deleteBreed(@RequestBody String id) throws Exception;
	@RequestMapping(value = "/mfCusCreditApply/agenciesInsertAjax")
	MfCusAgenciesCredit agenciesInsertAjax(@RequestBody MfCusAgenciesCredit mfCusAgenciesCredit) throws Exception;
	@RequestMapping(value = "/mfCusCreditApply/getMfCusAgenciesCreditById")
	MfCusAgenciesCredit getMfCusAgenciesCreditById(@RequestBody MfCusAgenciesCredit mfCusAgenciesCredit) throws Exception;
	@RequestMapping(value = "/mfCusCreditApply/getMfBusBreedCreditById")
	MfBusBreedCredit getMfBusBreedCreditById(@RequestBody MfBusBreedCredit mfBusBreedCredit) throws Exception;
	@RequestMapping(value = "/mfCusCreditApply/agenciesUpdateAjax")
	MfCusAgenciesCredit agenciesUpdateAjax(@RequestBody MfCusAgenciesCredit mfCusAgenciesCredit) throws Exception;
	@RequestMapping(value = "/mfCusCreditApply/breedUpdateAjax")
	MfBusBreedCredit breedUpdateAjax(@RequestBody MfBusBreedCredit mfBusBreedCredit) throws Exception;
	@RequestMapping(value = "/mfCusCreditApply/breedInsertAjax")
	MfBusBreedCredit breedInsertAjax(@RequestBody MfBusBreedCredit mfBusBreedCredit) throws Exception;
}
