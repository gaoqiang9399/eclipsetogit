/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MobileServiceBussInterface.java
 * 包名： app.component.interfacesinterface
 * 说明：移动服务业务模块相关接口
 * @author 沈浩兵
 * @date 2017-10-12 下午5:41:11
 * @version V1.0
 */ 
package app.component.interfacesinterface;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.app.entity.MfBusApply;
import app.component.assure.entity.MfAssureInfo;
import app.component.calc.core.entity.MfRepayHistory;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.examine.entity.MfExamineHis;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
 * 类名： MobileServiceBussInterface
 * 描述：业务相关接口
 * @author 沈浩兵
 * @date 2017-10-12 下午5:41:11
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MobileServiceBussInterfaceFeign {
	
	/**
	 * 
	 * 方法描述： 授信申请保存，1插入申请信息；2开启业务流程；
	 * @param mfBusApply 申请信息
	 * @param cusTel 客户手机号
	 * @param type 申请类型(通过 1微信  2App)
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-13 上午11:27:48
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/insertApply")
	public Map<String,Object> insertApply(@RequestBody MfBusApply mfBusApply,@RequestParam("cusTel") String cusTel,@RequestParam("type") String type) throws Exception;
	/**
	 * 
	 * 方法描述： 提现申请提交
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-13 下午2:17:21
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/insertApplyWithdrawals")
	public Map<String, Object> insertApplyWithdrawals(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;
	/**
	 * 
	 * 方法描述： 根据合同号从合同表中获得是否允许多次放款 0 不允许 1 允许多次放款
	 * @param pactId
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-10-13 下午3:05:20
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getManyPutOutAmtFlag")
	public String getManyPutOutAmtFlag(@RequestBody String pactId) throws Exception;
	/**
	 * 
	 * 方法描述： 根据产品编号获得提现期限数据源。产品编号暂时没有用
	 * 提现期限暂时没有设置功能，暂时封装固定数据源
	 * @param kindNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-13 下午4:27:25
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getApplyTermDataBykindNo")
	public JSONArray getApplyTermDataBykindNo(@RequestBody String kindNo) throws Exception;
	/**
	 * 
	 * 方法描述： 通过appId查询融资信息详情
	 * @param appId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author ywh
	 * @date 2017-10-13 下午6:27:25
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/appSelect")
	public Map<String, Object> appSelect(@RequestBody String appId,@RequestParam("regNo") String regNo) throws Exception;
	/**
	 * 
	 * 方法描述： 融资基本信息列表
	 * @param cusNo cusTel 两个参数传一个即可
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author ywh
	 * @date 2017-10-13 下午6:27:25
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/appFindByPage")
	public Map<String, Object> appFindByPage(@RequestBody String cusNo,@RequestParam("cusTel") String cusTel) throws Exception;
	/**
	 * 获取授信总额，在帐金额及剩余额度
	 * @param cusNo
	 * @param pactNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getBusCreditInfo")
	public Map<String, Object> getBusCreditInfo(@RequestBody String cusNo,@RequestParam("pactNo") String pactNo)throws Exception;
	/**
	 * 通过借据号查询还款计划列表 
	 * @param mfRepayPlan
	 * @return
	 * @throws Exception
	 * @author ywh
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getMfBusRepayPlanListById")
	public List<MfRepayPlan> getMfBusRepayPlanListById(@RequestBody MfRepayPlan mfRepayPlan )throws Exception;
	/**
	 * 
	 * 方法描述：获取未完结借据（移动端）
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception 
	 * String
	 * @author ywh
	 * @date 2017-10-14 上午10:29:47
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getFincAppListByPactId")
	public List<MfBusFincApp> getFincAppListByPactId(@RequestBody MfBusFincApp mfBusFincApp)throws Exception;
	/**
	 * 
	 * 方法描述： 根据产品编号获得是否允许同时发起多笔授信接口。产品编号暂时没有用
	 * 0不允许1允许
	 * @param kindNo
	 * @return
	 * @throws Exception
	 * String 
	 * @author 沈浩兵
	 * @date 2017-10-13 下午6:47:26
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getManyApplyFlagBykindNo")
	public String getManyApplyFlagBykindNo(@RequestBody String kindNo) throws Exception;
	
	/**
	 * 
	 * 方法描述： 根据日期获得当日的交易情况。包括交易总金额、交易笔数
	 * @param date
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-14 下午1:59:55
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getTransactionSituationByDay")
	public Map<String,Object> getTransactionSituationByDay(@RequestBody String date) throws Exception;
	/**
	 * 
	 * 方法描述： 根据日期获得当月的交易情况。包括交易总金额、交易笔数
	 * @param date
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-14 下午2:01:05
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getTransactionSituationByMonth")
	public Map<String,Object> getTransactionSituationByMonth(@RequestBody String date) throws Exception;
	/**
	 * 
	 * 方法描述： 根据操作员编号获得其交易情况。包括贷款余额、贷款总额、贷款笔数、逾期总额、期数
	 * @param opNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-16 下午3:44:59
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getTransactionSituationByOpNo")
	public Map<String,Object> getTransactionSituationByOpNo(@RequestBody String opNo) throws Exception;
	/**
	 * 通过借据号查询还款历史列表
	 * @param mfRepayPlan
	 * @return
	 * @throws Exception
	 * @author ywh
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getMfRepayHistoryListById")
	public List<MfRepayHistory> getMfRepayHistoryListById(@RequestBody String fincId )throws Exception;
	/**
	 * 通过合同号查询合同详情
	 * @param mfRepayPlan
	 * @return
	 * @throws Exception
	 * @author ywh
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getPactById")
	public MfBusPact getPactById(@RequestBody String pactId )throws Exception;
	/**
	 * 根据客户经理获取逾期借据列表
	 * @param mfRepayPlan
	 * @return
	 * @throws Exception
	 * @author ywh
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getOverdueFincListByCusMngNo")
	public List<MfBusFincApp> getOverdueFincListByCusMngNo(@RequestBody String cusMngNo )throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户经理获取逾期借据列表分页
	 * @param cusMngNo
	 * @return
	 * @throws Exception
	 * List<MfBusFincApp>
	 * @author YaoWenHao
	 * @date 2017-12-12 下午7:31:46
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getOverdueFincListByPage")
	public List<MfBusFincApp> getOverdueFincListByPage(@RequestBody String cusMngNo,@RequestParam("pageNo") String pageNo,@RequestParam("pageSize") String pageSize )throws Exception;
	/**
	 * 根据客户经理获借据列表
	 * @param cusMngNo
	 * @param finc_sts 在履行借据'5','6' 已完结借据'7'
	 * @return
	 * @throws Exception
	 * @author ywh
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getFincListByCusMngNoAndSts")
	public List<MfBusFincApp> getFincListByCusMngNoAndSts(@RequestBody String cusMngNo,@RequestParam("fincSts") String fincSts )throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获得其交易情况。包括贷款总额、贷款余额、贷款笔数、本期账单金额、本期应还本金、应还利息、罚息
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-17 上午9:12:48
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getBusinessSituationByCusNo")
	public Map<String,Object> getBusinessSituationByCusNo(@RequestBody String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 获得申请列表。不分页
	 * @param appSts 业务状态 参看BizPubParm 中APP_STS_SUB_PASS
	 * @param opNo 操作员编号
	 * @return
	 * @throws Exception
	 * List<MfBusApply>
	 * @author 沈浩兵
	 * @date 2017-10-17 上午11:53:58
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getMfBusApplyList")
	public List<MfBusApply> getMfBusApplyList(@RequestBody String appSts,@RequestParam("opNo") String opNo) throws Exception;
	
	/**
	 * 
	 * 方法描述： 获得申请列表，分页
	 * @param ipage
	 * @param appSts 业务状态 参看BizPubParm 中APP_STS_SUB_PASS
	 * @param opNo 操作员编号
	 * @return
	 * @throws ServiceException
	 * Ipage
	 * @author 沈浩兵
	 * @date 2017-10-17 下午12:04:41
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("appSts") String appSts,@RequestParam("opNo") String opNo) throws Exception;
	
	/**
	 * 
	 * 方法描述： 根据申请号获得申请业务详情
	 * @param appId
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author 沈浩兵
	 * @date 2017-10-17 下午12:12:10
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getMfBusApplyById")
	public MfBusApply getMfBusApplyById(@RequestBody String appId) throws Exception;
	/**
	 * 
	 * 方法描述： 修改申请信息
	 * @param mfBusApply
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-17 下午12:16:09
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/updateApplyInfo")
	public Map<String, Object> updateApplyInfo(@RequestBody MfBusApply mfBusApply) throws Exception;
	/**
	 * 
	 * 方法描述： 获得贷后检查分页列表,如果获得全部数据 examSts和fincId传null
	 * @param ipage
	 * @param fincId 
	 * @param examSts 
	 * @return
	 * @throws ServiceException
	 * Ipage
	 * @author 沈浩兵
	 * @date 2017-10-17 下午3:23:33
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/findMfExamineHisByPage")
	public Ipage findMfExamineHisByPage(@RequestBody Ipage ipage,@RequestParam("fincId") String fincId,@RequestParam("examSts") String examSts) throws Exception;
	/**
	 * 
	 * 方法描述： 根据借据号获得贷后检查历史，按照reg_time倒叙排序，第一条数据为最新一次贷后检查
	 * @param fincId
	 * @param examSts 
	 * @return
	 * @throws Exception
	 * List<MfExamineHis>
	 * @author 沈浩兵
	 * @date 2017-10-17 下午2:47:48
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getMfExamineHisList")
	public List<MfExamineHis> getMfExamineHisList(@RequestBody String fincId,@RequestParam("examSts") String examSts) throws Exception;
	
	/**
	 * 
	 * 方法描述： 根据贷后检查编号获得检查详情
	 * @param mfExamineHis
	 * @return
	 * @throws ServiceException
	 * MfExamineHis
	 * @author 沈浩兵
	 * @date 2017-10-17 下午3:10:52
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getMfExamineHisById")
	public MfExamineHis getMfExamineHisById(@RequestBody String examHisId) throws Exception;
	/**
	 * 
	 * 方法描述： 待审批列表。贷款申请审批、合同审批、放款审批等
	 * @param opNo
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-17 下午3:53:54
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getBusinessApprovingList")
	public Map<String,Object> getBusinessApprovingList(@RequestBody String opNo);
	/**
	 * 
	 * 方法描述： 待审批列表。贷款申请审批、合同审批、放款审批等分页
	 * @param opNo
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author ywh
	 * @date 2017-12-11 下午3:53:54
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getBusinessApprovingListByPage")
	public Map<String,Object> getBusinessApprovingListByPage(@RequestBody String pageNo,@RequestParam("pageSize") String pageSize,@RequestParam("opNo") String opNo);
	
	/**
	 * 
	 * 方法描述： 第三方app订单保存,业务申请
	 * 包括合同信息、身份信息、个人信息、银行账户信息和合作机构初审报告。
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-20 下午2:26:48
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/insertOrderApply")
	public Map<String,Object> insertOrderApply(@RequestBody Map<String,Object> paramMap) throws Exception;
	/**
	 * 
	 * 方法描述： 根据订单号（业务申请号）或的放款情况
	 * 放款状态0放款申请完成1放款申请拒绝
	 * 审批意见说明
	 * 还款计划列表。放款完成返回还款计划
	 * @param orderId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-20 下午3:49:11
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getLoanStateByOrderId")
	public Map<String,Object> getLoanStateByOrderId(@RequestBody String orderId) throws Exception;
	/**
	 * 
	 * 方法描述： 根据订单号（业务申请号）或的放款情况，只返回状态和审批意见说明,不返回还款计划
	 * 放款状态0放款申请完成1放款申请拒绝
	 * 审批意见说明
	 * @param orderId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-20 下午3:49:11
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getOnlyLoanStateByOrderId")
	public Map<String,Object> getOnlyLoanStateByOrderId(@RequestBody String orderId) throws Exception;
	/**
	 * 方法描述： 用钱宝将客户的还款信息（本金、利息、费用）推送给业务系统，
	 * 业务系统根据该信息更新业借款信息，并将结果反馈给合作机构。
	 * @param paramData
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-31 上午11:34:08
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/doRepayByOrderId")
	public Map<String,Object> doRepayByOrderId(@RequestBody String paramData) throws Exception;
	/**
	 * 
	 * 方法描述： 根据订单号获得签章合同模板文件服务相对路径和文件名
	 * @param orderId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * /factor/component/model/docword/
	 * 20170706183302598.docx
	 * @author 沈浩兵
	 * @date 2017-10-21 下午3:30:49
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getSignaturePactTemplateInfo")
	public Map<String,Object> getSignaturePactTemplateInfo(@RequestBody String orderId) throws Exception;
	/**
	 * 
	 * 方法描述： 移动端提交业务申请审批意见
	 * @param paramData 审批表单元素json串
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵paramMap
	 * @date 2017-10-23 下午2:52:48
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/doApplyCommit")
	public Map<String,Object> doApplyCommit(@RequestBody Map<String,Object> paramMap) throws Exception;
	/**
	 * 
	 * 方法描述： 移动端提交业务合同审批意见
	 * @param paramData
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-23 下午4:53:19
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/doPactCommit")
	public Map<String,Object> doPactCommit(@RequestBody String paramData);
	/**
	 * 
	 * 方法描述： 移动端提交业务放款审批意见
	 * @param paramData
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-24 下午4:46:50
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/doFincCommit")
	public Map<String,Object> doFincCommit(@RequestBody String paramData) throws Exception;
	/**
	 * 业务查询
	 * @param paramData 查询条件"cusName:xxx,org_name：xxx" 关于传值详情参考供应链筛选的设置
	 * andOr 0是and 1是or checked checked是否生效 NoValue 值为空  SingleValue单个值  BetweenValue两个值 type3 between 4,not between 0 :=
	 *例如：根据客户号查询传值["treeId":"my_filter","checked":true,"andOr":0,"condition":"cusName","dicType":"1","value":"张三","secondValue":"","noValue":false,"singleValue":false,"betweenValue":false,"listValue":false,"likeValue":true]
	 *例如：根据发放范围查询传值["treeId":"my_filter","checked":true,"andOr":0,"condition":"putoutAppDate","type":"3","dicType":"1","value":"20170807","secondValue":"20171025","noValue":false,"singleValue":false,"betweenValue":true,"listValue":false,"likeValue":fasle]
	 * [[{["treeId":"my_filter","checked":true,"andOr":0,"condition":"term","type":"3","dicType":"1","value":"20170807","secondValue":"20171025","noValue":false,"singleValue":false,"betweenValue":true,"listValue":false,"likeValue":false,"tId":"my_more_2","filterName":"ce"]}],{"customQuery":""},{"customSorts":"[]"}]
	 * cusName 客户名称 orgName业务主办putoutAppDate发放范围
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getBusPacts")
	public List<MfBusPact> getBusPacts(@RequestBody String paramData) throws Exception;
	/**
	 * 
	 * 方法描述：  判断此产品是否在业务申请中和合同履行中
	 * @param cusNo
	 * @param kindNo 产品编号
	 * @param terminalType终端类型
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-15 下午3:12:52
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/isInBusApply")
	public Map<String, Object> isInBusApply(@RequestBody String cusNo,@RequestParam("kindNo") String kindNo,@RequestParam("terminalType") String terminalType) throws Exception;	
	/**
	 * 判断是否可以提现接口
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getLoanIsApprove")
	public Map<String, Object> getLoanIsApprove(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;
	/**
	 * 根据借据号获取借据详情
	 * @param fincId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getMfBusFincAppByFincId")
	public MfBusFincApp getMfBusFincAppByFincId(@RequestBody String fincId)  throws Exception;	
	/**
	 * 
	* @Title: getPactAndFincForFlag
	* @Description: app端在旧的我的借款 列表基础上修改
	* （应为状态标识变了 flag：1,，受理中（申请，申请提交，还没审批通过）2，可提现（合同，审批通过，应为没有签约了，所以直接有合同，但没有借据）
	* 3、放款中（借据，放款申请提交，但是还没有复核）
	* 4、还款中（借据，放款复核通过，完结钱）
	* 5、已完结（借据，借据完结））  
	* @param @return Map<String, Object>
	* @param @throws Exception    参数  
	* @return     返回类型  
	* @throws
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getPactAndFincForFlag")
	public Map<String, Object> getPactAndFincForFlag(@RequestBody String cusNo,@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize)  throws Exception;
	/**
	 * 
	* @Title: getPactAndFincForFlag
	* @Description: app端在旧的我的借款 列表基础上修改
	* （应为状态标识变了 flag：1,，受理中（申请，申请提交，还没审批通过）2，可提现（合同，审批通过，应为没有签约了，所以直接有合同，但没有借据）
	* 3、放款中（借据，放款申请提交，但是还没有复核）
	* 4、还款中（借据，放款复核通过，完结钱）
	* 5、已完结（借据，借据完结））  
	* @param @return Map<String, Object>
	* @param @throws Exception    参数  
	* @return     返回类型  
	* @throws
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getPactAndFincForFlag")
	public Map<String, Object> getPactAndFincForFlag(@RequestBody String cusNo)  throws Exception;
	/**
	 * 根据业务申请号获取相关费用信息
	 * @param appId
	 * @param amt 基准金额
	 * @param term 期限 
	 * @param termType 期限类型
	 * @author ywh
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getBusFee")
	public Map<String, Object> getBusFee(@RequestBody String appId,@RequestParam("amt") String amt,@RequestParam("term") String term,@RequestParam("termType") String termType) throws Exception ;
	/**
	 * 
	* @Title: getRepayAmtByCusNo  
	* @Description: 根据客户号获取还款总额  
	* @param @param cusNo
	* @param @return
	* @param @throws Exception    参数  
	* @return Map<String,Object>    返回类型  
	* @throws
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getRepayAmtByCusNo")
	public Map<String, Object> getRepayAmtByCusNo(@RequestBody String cusNo) throws Exception;
	/**
	 * 
	* @Title: getRepayListByCusNo  
	* @Description: 根据客户号获取还款列表
	* @param @param cusNo
	* @param @return
	* @param @throws Exception    参数  
	* @return Map<String,Object>    返回类型  
	* @throws
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getRepayListByCusNo")
	public Map<String, Object> getRepayListByCusNo(@RequestBody String cusNo) throws Exception;
	
	/**
	 * 
	 * 方法描述： 根据订单号获得还款计划
	 * @param orderId
	 * @return
	 * @throws Exception
	 * List<MfRepayPlan>
	 * @author 沈浩兵
	 * @date 2017-10-31 上午11:05:14
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getRepayPlanListByOrderId")
	public Map<String, Object> getRepayPlanListByOrderId(@RequestBody String orderId)throws Exception;
	
	/**
	 * 
	 * 方法描述： 根据查询日期返回当日批量扣款报文文件流
	 * @param queryDate
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-31 下午2:13:31
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getBatchDebitFileByDay")
	public Map<String,Object> getBatchDebitFileByDay(@RequestBody String queryDate) throws Exception;
	/**
	 * 
	 * 方法描述： 处理引流方推送到系统的业务初审报告文件流写到本地磁盘
	 * @param orderId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-31 下午3:16:20
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/doApprovePresentation")
	public Map<String,Object> doApprovePresentation(@RequestBody String orderId,@RequestParam("inputStream") InputStream inputStream) throws Exception;
	
	/**
	 * 
	 * 方法描述： 根据客户号获得合同列表
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * List<MfBusPact>
	 * @author 沈浩兵
	 * @date 2017-11-5 上午11:57:56
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getMfBusPactListByCusNo")
	public List<MfBusPact> getMfBusPactListByCusNo(@RequestBody String cusNo)throws Exception;
	
	/**
	 * 
	 * 方法描述： 根据客户号获取借据信息列表
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * List<MfBusFincApp>
	 * @author 沈浩兵
	 * @date 2017-11-5 下午12:11:02
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getFincAppListByCusNo")
	public List<MfBusFincApp> getFincAppListByCusNo(@RequestBody String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 放款是否正在审批
	 * @param appId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-7 下午2:45:55
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getLoanIsApproveByAppid")
	public Map<String , Object> getLoanIsApproveByAppid(@RequestBody String appId,@RequestParam("regNo") String regNo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据申请号获取合同信息
	 * @param appId
	 * @return
	 * @throws Exception
	 * MfBusPact
	 * @author YaoWenHao
	 * @date 2017-11-13 下午4:09:32
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getMfBusPactByAppid")
	public MfBusPact getMfBusPactByAppid(@RequestBody String appId) throws Exception;
	/**
	 * 
	 * 方法描述： 根据合同号获得合同下所有的借据信息
	 * @param pactId
	 * @return
	 * @throws Exception
	 * List<MfBusFincApp>
	 * @author 沈浩兵
	 * @date 2017-11-14 上午9:24:37
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getAllFincAppListByPactId")
	public List<MfBusFincApp> getAllFincAppListByPactId(@RequestBody String pactId)throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获取进件列表
	 * @param cusNo
	 * @param PageNo
	 * @param pageSize
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-27 下午2:07:42
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getApplyList")
	public Map<String , Object> getApplyList(@RequestBody String opNo,@RequestParam("pageNo")  String pageNo,@RequestParam("pageSize")  String pageSize);
	/**
	 * 
	 * 方法描述： 根据客户号获取进件列表()
	 * @param cusNo
	 * @param PageNo
	 * @param pageSize
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-27 下午2:07:42
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getApplyListNew")
	public Map<String , Object> getApplyListNew (@RequestBody Map<String, String> parmMap);
	/**
	 * 
	 * 方法描述： B端插入申请
	 * @param mfBusApply
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-27 下午3:32:11
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/addApply")
	public Map<String,Object> addApply(@RequestBody MfBusApply mfBusApply);
	/**
	 * 根据查询条件获取申请列表
	 * 方法描述： 
	 * @param opNo
	 * @param search
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-27 下午3:44:43
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getApplyListBySearch")
	public Map<String,Object> getApplyListBySearch(@RequestBody String opNo,@RequestParam("search") String search);
	/**
	 * 
	 * 方法描述： 
	 * @param processKey流程Id
	 * @param obj 业务对象
	 * @param appNo 业务主键值
	 * @param primaryKeyName业务主键名称
	 * @param opNo 操作员
	 * @param title
	 * @param content
	 * @param optType 操作类型
	 * @param pasMaxNo业务大类
	 * @param pasMinNo消息小类
	 * @param opName 操作名称
	 * @param brNo
	 * @throws Exception
	 * void
	 * @author YaoWenHao
	 * @date 2017-11-28 下午3:49:01
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/startProcessWithUser")
	public Map<String,Object> startProcessWithUser(@RequestBody String processKey,@RequestParam("obj")  Object obj,@RequestParam("appNo") String appNo,@RequestParam("primaryKeyName")  String primaryKeyName,@RequestParam("opNo")  String opNo,@RequestParam("title")  String title,@RequestParam("content") String content,@RequestParam("optType")  String optType,@RequestParam("pasMaxNo")  String pasMaxNo,@RequestParam("pasMinNo")  String pasMinNo) throws Exception;
	/**
	 * 
	 * 方法描述： 获取业务审批流程图
	 * @param appId
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-28 下午5:12:48
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getBusFlow")
	public Map<String,Object> getBusFlow(@RequestBody String appId);
	/**
	 * 
	 * 方法描述： 获取审批历史
	 * @param appid
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-29 上午9:16:21
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getApproHis")
	public Map<String,Object> getApproHis(@RequestBody String appid);
	/**
	 * 
	 * 方法描述： 获取下一节点的审批人员
	 * @param appid
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-29 下午2:48:07
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getNextApprovers")
	public Map<String,Object> getNextApprovers(@RequestBody String appid,@RequestParam("regNo") String regNo);
	/**
	 * 
	 * 方法描述： 添加合同信息
	 * @param mfBusPact
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-29 下午5:03:27
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/addPactInfo")
	public Map<String,Object> addPactInfo(@RequestBody String appId);
	/**
	 * 
	 * 方法描述： 获取合同信息
	 * @param pactNo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-29 下午5:34:14
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getPactInf")
	public Map<String, Object> getPactInf(@RequestBody String pactId);
	/**
	 * 
	 * 方法描述： 根据申请号获取合同信息
	 * @param pactNo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-29 下午5:34:14
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getPactInfByAppId")
	public Map<String, Object> getPactInfByAppId(@RequestBody String appId);
	/**
	 * 
	 * 方法描述： 更新合同信息
	 * @param mfBusPact
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-30 上午9:32:52
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/updatePactInf")
	public Map<String, Object> updatePactInf(@RequestBody MfBusPact mfBusPact,@RequestParam("opNo") String opNo);
	/**
	 * 
	 * 方法描述： 新增借据信息
	 * @param mfBusFincApp
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-30 上午9:33:27
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/addDueInf")
	public  Map<String, Object> addDueInf(@RequestBody MfBusFincApp mfBusFincApp,@RequestParam("opNo") String opNo,@RequestParam("opName") String opName);
	/**
	 * 
	 * 方法描述： 获取借据信息
	 * @param fincId
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-30 上午9:47:25
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getDueInf")
	public  Map<String, Object> getDueInf(@RequestBody String fincId);
	/**
	 * 
	 * 方法描述： 批量加入还款计划
	 * @param repayPlanList
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-30 上午10:20:41
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/addPlanList")
	public Map<String, Object> addPlanList(@RequestBody List<MfRepayPlan> repayPlanList);
	
	/**
	 * 功能:获取还款计划outFlag;//还款计划状态（0 -未还款 1-已还完 2-部分还款 3-逾期）
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getPlanList")
	public Map<String, Object> getPlanList(@RequestBody String fincId);
	/**
	 * 
	 * 方法描述： pad获取签约合同列表(放款前) 
	 * @param cusName
	 * @param opNo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-7 上午11:19:28
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/findPactByPageForPad")
	public  Map<String, Object> findPactByPageForPad(@RequestBody String cusName,@RequestParam("opNo") String opNo,@RequestParam("pageNo") String pageNo,@RequestParam("pageSize") String pageSize);
	/**
	 * 
	 * 方法描述： pad获取签约合同列表(签约中) 
	 * @param cusName
	 * @param opNo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-7 上午11:19:28
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/findQYPactByPageForPad")
	public  Map<String, Object> findQYPactByPageForPad(@RequestBody Map<String, String> parmMap);
	/**
	 * 
	 * 方法描述： 试算还款计划
	 * @param beginDate开始日
	 * @param endDate结束日期
	 * @param putoutAmt贷款金额
	 * @param fincRate年利率
	 * @param repayType还款方式
	 * @param kindNo产品编号
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-8 下午4:22:03
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getMfRepayPlanListByAppId")
	public Map<String, Object> getMfRepayPlanListByAppId(String termType,String term,String putoutAmt, String fincRate,
@RequestParam("repayType") 			String repayType,@RequestParam("kindNo")  String kindNo,@RequestParam("appId") String appId);

	/**
	 * 
	 * 方法描述：B端根据操作员获取借据列表分页 
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author YaoWenHao
	 * @date 2017-12-11 上午10:34:25
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/findFincAppByPageForB")
	public Map<String, Object> findFincAppByPageForB(@RequestBody String pageNo,@RequestParam("pageSize") String pageSize,@RequestParam("cusMngNo") String cusMngNo);
	/**
	 * 
	 * 方法描述： 获取合同签约出账审批人员
	 * @param kindNo
	 * @param opNo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-14 上午10:22:35
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getUserForFristTaskWithUser")
	public Map<String, Object> getUserForFristTaskWithUser(@RequestBody String kindNo,@RequestParam("opNo") String opNo);
	/**
	 * 
	 * 方法描述： pad端担保登记 
	 * @param appId申请号
	 * @param vouType担保类型
	 * @param cusNo客户号
	 * @param opNo操作员
	 * @param mfAssureInfo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-14 下午4:58:16
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/insertMfAssureInfo")
	public Map<String, Object> insertMfAssureInfo(@RequestBody MfBusApply mfBusApply,@RequestParam("opNo") String opNo,@RequestParam("mfAssureInfo") MfAssureInfo mfAssureInfo);
	/**
	 * 
	 * 方法描述： ： pad端单独担保登记 
	 * @param appId
	 * @param opNo
	 * @param mfAssureInfo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-16 下午3:39:54
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/insertOnlyMfAssureInfo")
	public Map<String, Object> insertOnlyMfAssureInfo(@RequestBody String appId,@RequestParam("opNo") String opNo,@RequestParam("mfAssureInfo") MfAssureInfo mfAssureInfo);
	/**
	 * 
	 * 方法描述： Pad端提交申请到审批节点
	 * @param appId
	 * @param opNo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-14 下午5:20:51
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/doCommitApply")
	public Map<String, Object> doCommitApply(@RequestBody String appId,@RequestParam("opNo") String opNo);
	/**
	 * 
	 * 方法描述： Pad端提交申请到之指定人员审批节点
	 * @param appId
	 * @param opNo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-14 下午5:20:51
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/doCommitApplyToUser")
	public Map<String, Object> doCommitApplyToUser(@RequestBody String appId,@RequestParam("opNo") String opNo,@RequestParam("firstApprovalUser") String firstApprovalUser);
	/**
	 * 
	 * 方法描述： pad端根据获取业务的保证信息
	 * @param appId
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-15 下午2:40:58
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/findMfAssureInfoByAppId")
	public Map<String, Object> findMfAssureInfoByAppId(@RequestBody String appId);
	/**
	 * 
	 * 方法描述： pad获取申请的风险拦截
	 * @param appId
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-21 上午9:48:47
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getRiskBeanForNode")
	public Map<String, Object> getRiskBeanForNode(@RequestBody String appId);
	
	@RequestMapping(value = "/mobileServiceBussInterface/getNextUser")
	public Map<String, Object> getNextUser(@RequestBody String appId,@RequestParam("regNo") String regNo);
	/**
	 * 
	 * 方法描述： B端业务查询分页接口
	 * @param opNo
	 * @param pageNo
	 * @param pageSize
	 * @param paramMap
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-23 下午3:09:01
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getApplyListForB")
	public Map<String, Object> getApplyListForB(@RequestBody String opNo,@RequestParam("pageNo") String pageNo,@RequestParam("pageSize") String pageSize,@RequestParam("paramMap") Map<String, String> paramMap);
	/**
	 * 
	 * 方法描述： 获取审批的审批意见类型
	 * @param appId
	 * @param pactId
	 * @param fincId
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-25 下午2:51:12
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getOptionsListForB")
	public Map<String, Object> getOptionsListForB(@RequestBody String appId,@RequestParam("pactId") String pactId,@RequestParam("fincId") String fincId,@RequestParam("wkfTaskNo") String wkfTaskNo,@RequestParam("regNo") String regNo);
	/**
	 * 
	 * 方法描述： 获得借据子表信息
	 * @param fincChildId
	 * @return
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-12-26 上午11:43:15
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getMfBusFincAppChildById")
	public Map<String, Object> getMfBusFincAppChildById(@RequestBody String fincChildId);
	/**
	 * 
	 * 方法描述： 根据业务编号获得产品业务关联信息
	 * @param appId
	 * @return mfBusAppKind
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-12-26 上午11:46:01
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getMfBusAppKindByAppId")
	public Map<String, Object> getMfBusAppKindByAppId(@RequestBody String appId) throws Exception;
	/**
	 * 
	 * 方法描述： 业务查询页面详情
	 * @param fincId
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-27 上午10:24:54
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getFincDetail")
	public Map<String, Object> getFincDetail(@RequestBody String fincId);
	/**
	 * 
	 * 方法描述：  获取否决的申请
	 * @param pageNo
	 * @param pageSize
	 * @param opNo
	 * @param appSts
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-28 上午9:39:59
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getVetoApply")
	public Map<String , Object> getVetoApply(@RequestBody String pageNo,@RequestParam("pageSize") String pageSize,@RequestParam("opNo") String opNo,@RequestParam("appSts") String appSts);
	/**
	 * 
	 * 方法描述：  获取否决的合同
	 * @param pageNo
	 * @param pageSize
	 * @param opNo
	 * @param appSts
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-28 上午9:39:59
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getVetoPact")
	public Map<String , Object> getVetoPact(@RequestBody String pageNo,@RequestParam("pageSize") String pageSize,@RequestParam("opNo") String opNo);
	/**
	 * 
	 * 方法描述： 获取未放款合同
	 * @param pageNo
	 * @param pageSize
	 * @param opNo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-28 上午10:56:02
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getNoLoanPactInfo")
	public Map<String , Object> getNoLoanPactInfo(@RequestBody String pageNo,@RequestParam("pageSize") String pageSize,@RequestParam("opNo") String opNo);
	/**
	 * 
	 * 方法描述： 
	 * @param id 主键
	 * @param appId 申请号
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-29 下午3:56:07
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/deleteAssureInfo")
	public Map<String , Object> deleteAssureInfo(@RequestBody String id,@RequestParam("appId") String appId);
	
	/**
	 * 
	 * 方法描述： 根据关联编号获得当前审批岗位之前已审批通过的岗位。发回重审时选择发回审批岗位数据源
	 * @param relId 关联审批流程的编号.业务申请号、合同号、借据号等
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * 包含befNodesMap存放岗位编号、岗位名称键值对
	 * @author 沈浩兵
	 * @date 2018-1-3 上午9:34:22
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getBefNodesMapForApprove")
	public Map<String,Object> getBefNodesMapForApprove(@RequestBody String relId,@RequestParam("regNo") String regNo) throws Exception;
	/**
	 * 
	 * 方法描述： 获取融资审批页面相关
	 * @param appId 申请号 taskId 当前任务ID
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-10 上午9:57:40
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getViewPointForApply")
	public Map<String,Object> getViewPointForApply(@RequestBody String appId,@RequestParam("taskId") String taskId,@RequestParam("regNo") String regNo) throws Exception;
	/**
	 * 
	 * 方法描述： 获取合同审批页面相关
	 * @param pactId 申请号 taskId 当前任务ID
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-10 上午9:57:40
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getViewPointForPact")
	public Map<String,Object> getViewPointForPact(@RequestBody String pactId,@RequestParam("taskId") String taskId,@RequestParam("regNo") String regNo) throws Exception;
	/**
	 * 
	 * 方法描述： 业务流程向前推进一步
	 * @param appId
	 * @param wkfAppId
	 * @param opNo
	 * @return
	 * @throws Exception
	 * Map<String,Object> 
	 * errorCode 返回代码 00000成功 99999失败
	 * errorMsg 返回描述
	 * isEndSts 业务流程是否结束 true 结束 false 未结束
	 * @author 沈浩兵
	 * @date 2018-1-11 下午4:02:00
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/doCommitNextStep")
	public Map<String,Object> doCommitNextStep(@RequestBody String appId,@RequestParam("wkfAppId") String wkfAppId,@RequestParam("opNo") String opNo) throws Exception;
	/**
	 * 
	 * 方法描述： 还款到期列表数据请求
	 * @param opNo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-12 下午1:52:55
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getRepayToDateByPage")
	public Map<String,Object> getRepayToDateByPage(@RequestBody String opNo,@RequestParam("pageSize") String pageSize,@RequestParam("pageNo") String pageNo);
	/**
	 * 
	 * 方法描述： 合同到期列表数据请求
	 * @param opNo
	 * @param pageSize
	 * @param pageNo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-12 下午3:55:24
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getRepayToDateForPactByPage")
	public Map<String,Object> getRepayToDateForPactByPage(@RequestBody String opNo,@RequestParam("pageSize") String pageSize,@RequestParam("pageNo") String pageNo);
	/**
	 * 
	 * 方法描述： 还款逾期列表数据请求
	 * @param opNo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-12 下午1:52:55
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getRepayOverDateByPage")
	public Map<String,Object> getRepayOverDateByPage(@RequestBody String opNo,@RequestParam("pageSize") String pageSize,@RequestParam("pageNo") String pageNo);
	/**
	 * 
	 * 方法描述： 合同逾期列表数据请求
	 * @param opNo
	 * @param pageSize
	 * @param pageNo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-12 下午3:55:24
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getRepayOverDateForPactByPage")
	public Map<String,Object> getRepayOverDateForPactByPage(@RequestBody String opNo,@RequestParam("pageSize") String pageSize,@RequestParam("pageNo") String pageNo);
	/**
	 * 
	 * 方法描述： 押品逾期
	 * @param opNo
	 * @param pageSize
	 * @param pageNo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-12 下午4:22:29
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getPledgeToDatePage")
	public Map<String,Object> getPledgeToDatePage(@RequestBody String opNo,@RequestParam("pageSize") String pageSize,@RequestParam("pageNo") String pageNo);
	/**
	 * 
	 * 方法描述：  根据客户号已放款的合同 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-15 上午10:50:09
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/findLoanAfterByCusNo")
	public  Map<String, Object> findLoanAfterByCusNo(@RequestBody String cusNo);
	
	/**
	 * 
	 * 方法描述：pad端添加待放款列表（放款审批完成处于放款申请环节的数据） 
	 * @param opNo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-15 下午1:46:25
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getPendingLoanPact")
	public  Map<String, Object> getPendingLoanPact(@RequestBody String opNo,@RequestParam("pageSize") String pageSize,@RequestParam("pageNo") String pageNo);
	/**
	 * 
	 * 方法描述： 获取授信合同的授信总额和已用额度
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * List<MfBusPact>
	 * @author YaoWenHao
	 * @date 2018-1-15 下午5:35:25
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getCreditPact")
	public Map<String, Object> getCreditPact(@RequestBody String cusNo);
	/**
	 * 
	 * 方法描述： 获取客户授信合同的列表
	 * @param cusNo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-16 下午4:04:15
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getCreditPactList")
	public Map<String, Object> getCreditPactList(@RequestBody String cusNo);
	/**
	 * 
	 * 方法描述： 根据业务模式获取产品列表
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-17 上午11:43:08
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getKindListForXW")
	public Map<String, Object> getKindListForXW(@RequestBody String busModel);
	/**
	 * 
	 * 方法描述： pad端发起放款申请，一步到贷后，并调用核心放款
	 * @param mfBusFincApp
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-19 上午9:34:35
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/addFincAppForPad")
	public Map<String, Object> addFincAppForPad(@RequestBody MfBusFincApp mfBusFincApp);
	/**
	 * 
	 * 方法描述： 息费总额试算
	 * @param parmMap
	 * @param pactId合同id
	 * @param putoutAmt 申请金额
	 * @param term 期限
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-26 上午9:59:25
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getInvestFeeAmt")
	public Map<String, Object> getInvestFeeAmt(@RequestBody  Map<String, String > parmMap);
	/**
	 * 
	 * 方法描述： 获取产品详情
	 * @param kindNo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-28 下午2:08:17
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getMfSysKind")
	public Map<String, Object> getMfSysKind(@RequestBody String kindNo) ;

	/**
	 * 获取一笔借据一次结清所有应该金额罚息利息 数据
	 * @param paramMap
	 * @param cusNo
	 * @return
	 * @author MaHao
	 * @date 2018-1-26 下午4:29:35
	 */
	@RequestMapping(value = "/mobileServiceBussInterface/getRefundPayInfo")
	public Map<String, Object> getRefundPayInfo(Map<String, String> paramMap,
@RequestParam("fincId") 			String fincId,@RequestParam("cusNo") String cusNo);
	

}

