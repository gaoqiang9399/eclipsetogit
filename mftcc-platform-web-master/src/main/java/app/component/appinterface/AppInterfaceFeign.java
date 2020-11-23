package  app.component.appinterface;

import java.util.List;
import java.util.Map;

import app.component.app.entity.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.core.domain.screen.FormData;

import app.base.ServiceException;
import app.component.assure.entity.MfAssureInfo;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.cus.entity.MfCusAssureCompany;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: AppInterface.java
* Description:
* @author:LiuYF@dhcc.com.cn
* @Mon May 16 20:50:51 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface AppInterfaceFeign {

	@RequestMapping(value = "/appInterface/getMfBusApplyByAppId")
	public MfBusApply getMfBusApplyByAppId(@RequestParam("appId") String appId) throws Exception;

	@RequestMapping(value = "/appInterface/getMfBusApply")
	public MfBusApply getMfBusApply(@RequestBody MfBusApply mfBusApply) throws Exception;

	@RequestMapping(value = "/appInterface/getByPrimaryAppId")
	public MfBusApply getByPrimaryAppId(@RequestBody MfBusApply mfBusApply) throws Exception;
	/**
	 *
	 * 方法描述： 根据客户号获取该客户最近的在办申请
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author zhs
	 * @date 2016-7-23 上午9:18:54
	 */
	@RequestMapping(value = "/appInterface/getRecentAppByCusNo")
	public MfBusApply getRecentAppByCusNo(@RequestParam("cusNo") String cusNo)throws Exception;
	/**
	 *
	 * 方法描述： 根据客户号和产品种类获取最新一条申请信息
	 * @param cusNo kindNo
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author @author Jiasl
	 * @date 2017-10-11 上午9:18:54
	 */
	@RequestMapping(value = "/appInterface/getRecentAppByCusNoandKindNo")
	public MfBusApply getRecentAppByCusNoandKindNo(@RequestParam("cusNo") String cusNo,@RequestParam("kindNo") String kindNo)throws Exception;

	@RequestMapping(value = "/appInterface/updateApply")
	public void updateApply(@RequestBody MfBusApply mfBusApply)throws Exception;

	/**
	 * @author czk
	 * @Description: 查询客户参与的所有业务，包括该客户做融资客户、仓储方等的所有业务
	 * date 2016-10-14
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/appInterface/getAllApplyByCusNo")
	public List<MfBusApply> getAllApplyByCusNo(@RequestParam("cusNo") String cusNo) throws Exception;
	/**
	 *
	 * 方法描述： 获得申请-产品关联信息
	 * @param mfBusAppKind
	 * @return
	 * @throws Exception
	 * MfBusAppKind
	 * @author 沈浩兵
	 * @date 2016-12-6 下午4:33:19
	 */
	@RequestMapping(value = "/appInterface/getMfBusAppKind")
	public MfBusAppKind getMfBusAppKind(@RequestBody MfBusAppKind mfBusAppKind) throws Exception;

	/**
	 *
	 * 方法描述： 获取除当前业务之外的其他业务
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * List<MfBusApply>
	 * @author zhs
	 * @date 2017-2-4 上午9:38:44
	 */
	@RequestMapping(value = "/appInterface/getOtherApplyList")
	public List<MfBusApply> getOtherApplyList(@RequestBody MfBusApply mfBusApply)throws Exception;

	/**
	 *
	 * 方法描述： 获取客户的所有申请
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * List<MfBusApply>
	 * @author zhs
	 * @date 2017-2-9 上午9:38:44
	 */
	@RequestMapping(value = "/appInterface/getApplyList")
	public List<MfBusApply> getApplyList(@RequestBody MfBusApply mfBusApply)throws Exception;

	/**
	 *
	 * 方法描述： 根据appId获取客户的申请历史列表
	 * @param appId
	 * @return
	 * @throws Exception
	 * List<MfBusApplyHis>
	 * @author zhang_dlei
	 * @date 2017-06-22  上午9:38:44
	 */
	@RequestMapping(value = "/appInterface/getApplyHisListByAppId")
	public List<MfBusApplyHis> getApplyHisListByAppId(@RequestBody MfBusApplyHis mfBusApplyHis)throws Exception;


	/**
	 * 方法描述：线上客户申请业务
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/appInterface/insertForApply")
	public boolean insertForApply(@RequestBody Map<String, String> paramMap)throws Exception;

	/**
	 * 方法描述：线上客户申请业务
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/appInterface/insertMfBusApply")
	public boolean insertMfBusApply(@RequestBody MfBusApply mfBusApply)throws Exception;

	/**
	 *
	 * 方法描述： 获取客户的多笔业务列表信息
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * List<MfBusApply>
	 * @author zhs
	 * @date 2017-2-28 下午4:29:57
	 */
	@RequestMapping(value = "/appInterface/getMultiBusList")
	public List<MfBusApply> getMultiBusList(@RequestBody MfBusApply mfBusApply)throws Exception;

	/**
	 *
	 * 方法描述：删除申请历史表
	 * @param MfBusApplyHis
	 * @return
	 * @throws Exception
	 *
	 * @author LH
	 * @date 2017-3-18 下午14:29:57
	 */
	@RequestMapping(value = "/appInterface/deleteMfBusApplyHis")
	public void deleteMfBusApplyHis(@RequestBody MfBusApplyHis mfBusApplyHis)throws Exception;

	/**
	 *
	 * 方法描述：删除申请类型
	 * @param MfBusAppKind
	 * @return
	 * @throws Exception
	 *
	 * @author LH
	 * @date 2017-3-18 下午14:29:57
	 */
	@RequestMapping(value = "/appInterface/deleteMfBusAppKind")
	public void deleteMfBusAppKind(@RequestBody MfBusAppKind mfBusAppKind)throws Exception;
	/**
	 *
	 * 方法描述： 根据押品编号获取申请信息
	 * @param pleId
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author zhs
	 * @date 2017-3-30 下午6:41:55
	 */
	@RequestMapping(value = "/appInterface/getMfBusApplyByPleId")
	public MfBusApply getMfBusApplyByPleId(@RequestParam("pleId") String pleId) throws Exception;

	@RequestMapping(value = "/appInterface/getByCusMngNo")
	public List<MfCusAndApply> getByCusMngNo(@RequestParam("cusMngNo") String cusMngNo) throws Exception;

	@RequestMapping(value = "/appInterface/getBusCountByCusMngNo")
	public int getBusCountByCusMngNo(@RequestBody MfBusApply mfBusApply) throws Exception;
	/**
	 *
	 * 方法描述： app端业务申请
	 * @param mfBusApply
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @return
	 * @date 2017-5-20 上午9:29:07
	 */
	@RequestMapping(value = "/appInterface/insertApplyForApp")
	public MfBusApply insertApplyForApp(@RequestBody MfBusApply mfBusApply) throws Exception;


	/**
	 *
	 * 方法描述： 查看客户已提交的业务个数
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * int
	 * @author zhs
	 * @date 2017-5-20 下午6:29:42
	 */
	@RequestMapping(value = "/appInterface/getBusSubmitCnt")
	public int getBusSubmitCnt(@RequestParam("cusNo")  String cusNo)throws Exception;

	/**
	 * 方法描述： 初始化表单
	 * @param map
	 * @return
	 * @throws Exception
	 * FormData
	 * @author YuShuai
	 * @date 2017-6-7 下午4:47:32
	 *  当前获取表单功能不再使用，改为使用{@link PrdctInterface#getFormId}获取表单 wangchao 2017-07-17
	 */

	@RequestMapping(value = "/appInterface/getInitForm")
	public FormData getInitForm(@RequestParam("kindNo")  String kindNo,@RequestParam("formStage")  String formStage,@RequestParam("cusType")  String cusType)throws Exception;
	/**
	 *
	 * 方法描述： 获得分页进件列表
	 * @param ipage
	 * @param mfCusAndApply
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 沈浩兵
	 * @date 2017-6-14 下午2:44:43
	 */
	@RequestMapping(value = "/appInterface/getCusAndApplyList")
	public Ipage getCusAndApplyList(@RequestBody Ipage ipage,@RequestParam("mfCusAndApply") MfCusAndApply mfCusAndApply) throws Exception;

	/**
	 * 提交至业务审批流程
	 * @param appId 申请号
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @param firstApprovalUser
	 * @date 2017-6-16 下午5:55:30
	 */
	@RequestMapping(value = "/appInterface/submitProcess")
	public MfBusApply submitProcess(@RequestParam("appId")   String appId) throws Exception;
	/**
	 *
	 * 方法描述：提交至业务审批流程
	 * @param appId
	 * @param firstApprovalUser
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author zhs
	 * @date 2017-12-7 下午6:55:35
	 */
	@RequestMapping(value = "/appInterface/submitProcess")
	public MfBusApply submitProcess(@RequestParam("appId") String appId,@RequestParam("firstApprovalUser")  String firstApprovalUser) throws Exception;
	/**
	 * 提交至业务审批流程
	 * @param appId 申请号
	 * @return
	 * @throws Exception
	 * @author ywh
	 * @param firstApprovalUser
	 * @param firstApprovalUser
	 * @date 2017-10-31下午5:55:30
	 */
	@RequestMapping(value = "/appInterface/submitProcessWithUser")
	public MfBusApply submitProcessWithUser(@RequestParam("appId") String appId,@RequestParam("opNo") String opNo,@RequestParam("opName") String opName,@RequestParam("brNo") String brNo,@RequestParam("firstApprovalUser")  String firstApprovalUser) throws Exception;

	/**
	 * 初始化审批动态表单
	 * @param appId 申请号
	 * @param fincId 借据号
	 * @param node 审批编号，{@link WKF_NODE#apply_approval}, {@link WKF_NODE#contract_approval}, {@link WKF_NODE#putout_approval}选其一
	 * @param defaultFormId 默认表单编号，用于功能初期无基础配置时防报错
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-6-16 下午8:51:30
	 */

	@RequestMapping(value = "/appInterface/getAuditForm")
	public FormData getAuditForm(@RequestParam("appId") String appId,@RequestParam("fincId")  String fincId,@RequestParam("node")  WKF_NODE node,@RequestParam("defaultFormId")  String defaultFormId,@RequestParam("regNo") String regNo) throws Exception;

	/**
	 * 方法描述： 处理申请的数据
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author zhang_dlei
	 * @date 2017-06-21 下午3:43:29
	 */
	@RequestMapping(value = "/appInterface/disProcessData")
	public MfBusApply disProcessData(@RequestBody MfBusApply mfBusApply) throws Exception;

	/**
	 *
	 * 方法描述： 审批提交（审批意见保存）
	 * @param taskId
	 * @param appId
	 * @param opinionType
	 * @param approvalOpinion
	 * @param transition
	 * @param regNo
	 * @param nextUser
	 * @param mfBusApply
	 * @param dataMap 给工作流传递的参数
	 * @return
	 * Result
	 * @author zhang_dlei
	 * @date 2017-06-21 上午11:03:23
	 */
	@RequestMapping(value = "/appInterface/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("appId")  String appId,@RequestParam("opinionType")  String opinionType,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("transition")  String transition,@RequestParam("opNo")  String opNo,@RequestParam("nextUser") String nextUser,@RequestParam("mfBusApply")  MfBusApply mfBusApply,@RequestParam("mfBusAppFeeList")  List<MfBusAppFee> mfBusAppFeeList,@RequestParam("dataMap")  Map<String, Object> dataMap) throws Exception;

	/***
	 * 方法描述： 根据客户号和产品编号查询
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * @author zhang_dlei
	 */
	@RequestMapping(value = "/appInterface/findByCusNoAndKindNo")
	public List<MfBusApply> findByCusNoAndKindNo(@RequestBody MfBusApply mfBusApply)throws Exception;
    /**
     *
     * 方法描述：通过appId更新业务设置关联表信息
     * @param upBusAppKind
     * void
     * @author WD
     * @date 2017-7-25 下午8:13:30
     */
	@RequestMapping(value = "/appInterface/updateMfBusAppKind")
	public void updateMfBusAppKind(@RequestBody MfBusAppKind upBusAppKind)throws Exception;


	/**
	 *
	 * 方法描述： 通过逾期利率浮动和复利利率浮动 计算出 逾期利率和复利利率
	 * @param mfBusApply
	 * @param mfBusAppKind
	 * @throws Exception
	 * void
	 * @author WD
	 * @date 2017-7-31 下午2:23:10
	 */
	@RequestMapping(value = "/appInterface/doDealRateFloat")
	public void doDealRateFloat(@RequestBody MfBusApply mfBusApply,@RequestParam("mfBusAppKind") MfBusAppKind mfBusAppKind)throws Exception;

	/**
	 * 处理申请的数据
	 *
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-8-3 上午11:39:00
	 */
	@RequestMapping(value = "/appInterface/processDataForApply")
	public MfBusApply processDataForApply(@RequestBody MfBusApply mfBusApply) throws Exception;

	/**
	 * 处理插入时候的数据
	 *
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-8-3 下午2:21:07
	 */
	@RequestMapping(value = "/appInterface/disProcessDataForApply")
	public MfBusApply disProcessDataForApply(@RequestBody MfBusApply mfBusApply) throws Exception;

	@RequestMapping(value = "/appInterface/insertMfBusApplyHis")
	public void insertMfBusApplyHis(@RequestBody MfBusApplyHis mfBusApplyHis) throws Exception;

	/**
	 * 业务审批通过，将审批后的业务数据回写至申请表，并提交业务流程
	 *
	 * @param mfBusApplyHis
	 * @return
	 * @throws ServiceException
	 * @author WangChao
	 * @throws Exception
	 * @date 2017-8-3 下午5:40:03
	 */
	@RequestMapping(value = "/appInterface/submitApplyApprovalPass")
	public Map<String, String> submitApplyApprovalPass(@RequestBody MfBusApplyHis mfBusApplyHis) throws Exception;
	/**
	 * 处理一些合同的字段
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-31 下午5:42:44
	 */
	@RequestMapping(value = "/appInterface/processPactDataForApply")
	public MfBusApply processPactDataForApply(@RequestBody MfBusApply mfBusApply) throws Exception;

	@RequestMapping(value = "/appInterface/getTaskAppList")
	public List<MfBusApply> getTaskAppList(@RequestBody String opNo) throws Exception;

	/**
	 *
	 * 方法描述： 获取还款方式月规则引擎对应方式List
	 * @param mfBusRepayRules
	 * @return
	 * @throws Exception
	 * List<MfBusRepayRules>
	 * @author 栾好威
	 * @date 2017-8-29 下午6:22:08
	 */
	@RequestMapping(value = "/appInterface/getMfBusRepayRulesList")
	public List<MfBusRepayRules> getMfBusRepayRulesList(@RequestBody MfBusRepayRules mfBusRepayRules) throws Exception;

	/**
	 *
	 * 方法描述： 获取还款方式月规则引擎对应方式
	 * @param mfBusRepayRules
	 * @return
	 * @throws Exception
	 * MfBusRepayRules
	 * @author 栾好威
	 * @date 2017-8-30 上午12:01:19
	 */
	@RequestMapping(value = "/appInterface/getMfBusRepayRules")
	public MfBusRepayRules getMfBusRepayRules(@RequestBody MfBusRepayRules mfBusRepayRules) throws Exception;

	/**
	 *
	 * 方法描述： 查询审批历史按照登记时间升序排序
	 * @param mfBusApplyHis
	 * @return
	 * @throws Exception
	 * List<MfBusApplyHis>
	 * @author lwq
	 * @date 2017-9-28 上午10:46:29
	 */
	@RequestMapping(value = "/appInterface/getListByAppIdForOffice")
	public List<MfBusApplyHis> getListByAppIdForOffice(@RequestBody MfBusApplyHis mfBusApplyHis)throws Exception;
	/**
	 *
	 * 方法描述： 移动服务业务申请保存
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author 沈浩兵
	 * @date 2017-10-13 下午3:34:30
	 */
	@RequestMapping(value = "/appInterface/insertApplyForMobileService")
	public MfBusApply insertApplyForMobileService(@RequestBody MfBusApply mfBusApply) throws Exception;
	/**
	 *
	 * 方法描述： 获得申请列表，分页
	 * @param ipage
	 * @param mfBusApply
	 * @return
	 * @throws ServiceException
	 * Ipage
	 * @author 沈浩兵
	 * @date 2017-10-17 下午12:02:05
	 */
	@RequestMapping(value = "/appInterface/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfBusApply") MfBusApply mfBusApply) throws Exception;
	/**
	 *
	 * 方法描述： 根据操作员编号获得待审批业务列表
	 * @param opNo
	 * @return
	 * @throws ServiceException
	 * List<MfBusApply>
	 * @author 沈浩兵
	 * @date 2017-10-18 上午9:23:34
	 */
	@RequestMapping(value = "/appInterface/getBusApplyApprovingListByOpNo")
	public List<MfBusApply> getBusApplyApprovingListByOpNo(@RequestBody String opNo) throws Exception;

	/**
	 * 方法描述： 第三方app订单推送给供应链系统,在系统中进行业务申请
	 * 包括合同信息、身份信息、个人信息、银行账户信息和合作机构初审报告。
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-20 下午2:50:58
	 */
	@RequestMapping(value = "/appInterface/insertOrderApply")
	public Map<String,Object> insertOrderApply(@RequestBody Map<String,Object> paramMap) throws Exception;

	/**
	 * 方法描述： 根据业务申请号获得业务状态和审批意见说明
	 * @param appId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-20 下午3:49:11
	 */
	@RequestMapping(value = "/appInterface/getApplyStateByAppId")
	public Map<String,Object> getApplyStateByAppId(@RequestBody String appId) throws Exception;
	/**
	 *
	 * 方法描述： 移动端审批意见保存
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * Result
	 * @author 沈浩兵
	 * @date 2017-10-23 下午3:32:57
	 */
	@RequestMapping(value = "/appInterface/doCommitForApp")
	public Result doCommitForApp(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 添加担保额度占用明细
	 *
	 * @param appId
	 * @param mfAssureInfo
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-10-25 下午1:55:54
	 */
	@RequestMapping(value = "/appInterface/addAssureDetail")
	public void addAssureDetail(@RequestBody String appId,@RequestParam("mfAssureInfo")  MfAssureInfo mfAssureInfo) throws Exception;

	/**
	 * 完全恢复担保额度占用
	 *
	 * @param appId
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-10-25 下午3:32:04
	 */
	@RequestMapping(value = "/appInterface/recoveryAllAssureAmt")
	public void recoveryAllAssureAmt(@RequestBody String appId) throws Exception;

	/**
	 * 恢复担保额度占用
	 * @param appId
	 * @param repayAmt
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-10-25 下午5:07:00
	 */
	@RequestMapping(value = "/appInterface/recoveryAssureAmt")
	public void recoveryAssureAmt(@RequestBody String appId,@RequestParam("repayAmt")  Double repayAmt) throws Exception;

	/**
	 * 担保额度占用更新
	 * @param appId
	 * @param pactId
	 * @param pactAmt
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-10-25 下午8:03:56
	 */
	@RequestMapping(value = "/appInterface/updateAssureAmt")
	public void updateAssureAmt(@RequestBody String appId,@RequestParam("pactId")  String pactId,@RequestParam("pactAmt")  Double pactAmt) throws Exception;
	/**
	 * 验押品保险总额是否不小于业务申请额度
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/appInterface/checkInsAmountInfo")
	public boolean checkInsAmountInfo(@RequestBody String appId)throws Exception;

	/**
	 * 查询已占用担保额度
	 *
	 * @param assureCompanyId {@link MfCusAssureCompany#assureCompanyId}
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-11-2 下午3:25:48
	 */
	@RequestMapping(value = "/appInterface/getOccupyAmt")
	public Double getOccupyAmt(@RequestBody String assureCompanyId) throws Exception;

	/**
	 *
	 * 方法描述： 业务保存时，将产品信息与业务关联 ，插入表中
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * MfBusAppKind
	 * @author 沈浩兵
	 * @date 2017-11-4 下午12:36:50
	 */
	@RequestMapping(value = "/appInterface/insertBusAppKind")
	public MfBusAppKind insertBusAppKind(@RequestBody MfBusApply mfBusApply)throws Exception;

	/**
	 *
	 * 方法描述： 只插入申请表内容
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author 沈浩兵
	 * @date 2017-11-4 下午12:49:44
	 */
	@RequestMapping(value = "/appInterface/insertApplyByOnly")
	public MfBusApply insertApplyByOnly(@RequestBody MfBusApply mfBusApply)throws Exception;
	/**
	 *
	 * 方法描述：根据查询条件获取申请列表
	 * @param opNo
	 * @param search
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-27 下午3:43:42
	 */
	@RequestMapping(value = "/appInterface/getApplyListBySearch")
	public List<MfCusAndApply> getApplyListBySearch(@RequestBody String opNo,@RequestParam("search")  String search) throws Exception;

	/**
	 * 方法描述： 删除申请表信息
	 * @param mfBusApply
	 * @throws Exception
	 * void
	 * @author YuShuai
	 * @date 2017-12-19 下午7:12:19
	 */
	@RequestMapping(value = "/appInterface/deleteMfBusApplyHis")
	public void deleteMfBusApplyHis(@RequestBody MfBusApply mfBusApply)throws Exception;

	@RequestMapping(value = "/appInterface/getBusTransList")
	public Ipage getBusTransList(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/appInterface/getApplyListAjax")
	public Ipage getApplyListAjax(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/appInterface/getMfBusApplySecondByAppId")
    MfBusApplySecond getMfBusApplySecondByAppId(@RequestBody MfBusApplySecond mfBusApplySecond) throws Exception;
}
