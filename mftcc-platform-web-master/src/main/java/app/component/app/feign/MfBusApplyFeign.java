package  app.component.app.feign;


import app.base.ServiceException;
import app.component.app.entity.MfBusApply;
import app.component.app.entity.MfBusApplyHis;
import app.component.app.entity.MfBusApplySecond;
import app.component.app.entity.MfCusAndApply;
import app.component.cus.entity.MfCusCustomer;
import app.component.pact.entity.MfBusPact;
import app.component.prdct.entity.MfSysKind;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import com.core.domain.screen.OptionsList;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
* Title: MfBusApplyBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat May 21 10:40:47 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusApplyFeign{
	
	/**
	 * 
	 * 方法描述：业务申请表保存，带有费用的
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author zhs
	 * @date 2017-12-14 下午6:24:46
	 */
	@RequestMapping(value = "/mfBusApply/insertApply")
	public MfBusApply insertApply(@RequestBody  Map<String, Object> parmMap) throws Exception;
	/**
	 *
	 * 方法描述：业务申请（授信首次发起申请）
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author zhs
	 * @date 2017-12-14 下午6:24:46
	 */
	@RequestMapping(value = "/mfBusApply/insertApplyForCredit")
	public MfBusApply insertApplyForCredit(@RequestBody  Map<String, Object> parmMap) throws Exception;
	@RequestMapping(value = "/mfBusApply/insert")
	public MfBusApply insert(@RequestBody MfBusApply mfBusApply) throws ServiceException;
	
	@RequestMapping(value = "/mfBusApply/delete")
	public void delete(@RequestBody MfBusApply mfBusApply) throws ServiceException;
	
	@RequestMapping(value = "/mfBusApply/update")
	public void update(@RequestBody MfBusApply mfBusApply) throws ServiceException;

	@RequestMapping(value = "/mfBusApply/updateMap")
	public void updateMap(@RequestBody Map<String, Object> paramMap) throws ServiceException;
	
	@RequestMapping(value = "/mfBusApply/getById")
	public MfBusApply getById(@RequestBody MfBusApply mfBusApply) throws ServiceException;
	
	@RequestMapping(value = "/mfBusApply/getByIdXin")
	public MfBusApply getByIdXin(@RequestBody MfBusApply mfBusApply) throws ServiceException;
	
	@RequestMapping(value = "/mfBusApply/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfBusApply") MfBusApply mfBusApply) throws ServiceException;

	/**
	 * 业务审批通过，将审批后的业务数据回写至申请表，并提交业务流程
	 * @param mfBusApplyHis
	 * @return
	 * @throws ServiceException
	 * @author WangChao
	 * @date 2017-7-1 下午7:26:08
	 */
	@RequestMapping(value = "/mfBusApply/submitApplyApprovalPass")
	public Map<String, String> submitApplyApprovalPass(@RequestBody MfBusApplyHis mfBusApplyHis) throws Exception;

//	/**
//	 * 
//	 * 方法描述：保理业务提交 
//	 * @param appId
//	 * @throws ServiceException
//	 * void
//	 * @author zhs
//	 * @date 2016-6-25 上午9:46:34
//	 */
//	public void applySubmit(@RequestBody String appId) throws ServiceException;
	
	/**
	 * 
	 * 方法描述： 根据客户号获取该客户最近的在办申请
	 * @param mfBusApply
	 * @return
	 * @throws ServiceException
	 * MfBusApply
	 * @author zhs
	 * @date 2016-7-23 上午9:20:58
	 */
	@RequestMapping(value = "/mfBusApply/getRecentAppByCusNo")
	public MfBusApply getRecentAppByCusNo(@RequestBody MfBusApply mfBusApply) throws ServiceException;
	
	@RequestMapping(value = "/mfBusApply/getCusAndApply")
	public Ipage getCusAndApply(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfBusApply/getCusAndApplyForCash")
	public Ipage getCusAndApplyForCash(@RequestBody Ipage ipage,@RequestParam("mfCusAndApply") MfCusAndApply mfCusAndApply) throws Exception;
	
	/**
	 * @author czk
	 * @Description: 在业务流中增加参与方，并且提交工作流
	 * date 2016-8-26
	 * @param mfCusCustomer
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusApply/doInsertBusPart")
	public MfCusCustomer doInsertBusPart(@RequestBody MfCusCustomer mfCusCustomer,@RequestParam("appId") String appId) throws Exception;
	
	@RequestMapping(value = "/mfBusApply/getAllAppByCusNo")
	public List<MfBusApply> getAllAppByCusNo(@RequestBody String cusNo) throws ServiceException;

	/**
	 * 
	 * 方法描述： 获取除当前业务之外的其他业务
	 * @param mfBusApply
	 * @return
	 * @throws ServiceException
	 * List<MfBusApply>
	 * @author zhs
	 * @date 2017-2-4 上午10:02:48
	 */
	@RequestMapping(value = "/mfBusApply/getOtherApplyList")
	public List<MfBusApply> getOtherApplyList(@RequestBody MfBusApply mfBusApply)throws ServiceException;

	/**
	 * 方法描述： 获取线上注册客户的信息，待分单使用
	 * @param ipage
	 * @param mfCusAndApply
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfBusApply/getCusAndApplyOnline")
	public Ipage getCusAndApplyOnline(@RequestBody Ipage ipage,@RequestParam("mfCusAndApply")  MfCusAndApply mfCusAndApply) throws ServiceException;

	/**
	 * 方法描述：分单功能
	 * @param mfBusApply
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfBusApply/assginBill")
	public void assginBill(@RequestBody MfBusApply mfBusApply)throws ServiceException;

	/**
	 * 方法描述：获取客户的所有申请业务
	 * @param mfBusApply
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfBusApply/getApplyList")
	public List<MfBusApply> getApplyList(@RequestBody MfBusApply mfBusApply) throws ServiceException;

	@RequestMapping(value = "/mfBusApply/insert1")
	public MfBusApply insert1(@RequestBody MfBusApply mfBusApply)throws ServiceException;

	/**
	 * 
	 * 方法描述： 获取客户的多笔业务列表信息
	 * @param mfBusApply
	 * @return
	 * @throws ServiceException
	 * List<MfBusApply>
	 * @author zhs
	 * @date 2017-2-27 上午10:17:07
	 */
	@RequestMapping(value = "/mfBusApply/getMultiBusList")
	public List<MfBusApply> getMultiBusList(@RequestBody MfBusApply mfBusApply) throws ServiceException;
	/**
	 * 
	 * 方法描述： 根据押品编号获取申请信息
	 * @param pleId
	 * @return
	 * @throws ServiceException
	 * MfBusApply
	 * @author zhs
	 * @date 2017-3-30 下午6:42:55
	 */
	@RequestMapping(value = "/mfBusApply/getMfBusApplyByPleId")
	public MfBusApply getMfBusApplyByPleId(@RequestBody String pleId)throws ServiceException;

	/**
	 * 
	 * 方法描述： 单字段编辑
	 * @param dataMap
	 * @throws ServiceException
	 * void
	 * @author zhs
	 * @date 2017-4-20 下午3:59:08
	 */
	@RequestMapping(value = "/mfBusApply/updateByOne")
	public void updateByOne(@RequestBody Map<String,Object> dataMap) throws Exception;
	
	@RequestMapping(value = "/mfBusApply/getByCusMngNo")
	public List<MfCusAndApply> getByCusMngNo(@RequestBody String cusMngNo) throws ServiceException;
	
	@RequestMapping(value = "/mfBusApply/getBusCountByCusMngNo")
	public int getBusCountByCusMngNo(@RequestBody MfBusApply mfBusApply) throws Exception;

	/**
	 * 
	 * 方法描述： 查看客户已提交的业务个数 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * int
	 * @author zhs
	 * @date 2017-5-20 下午6:31:08
	 */
	@RequestMapping(value = "/mfBusApply/getBusSubmitCnt")
	public int getBusSubmitCnt(@RequestBody String cusNo) throws Exception;
	/**
	 * 方法描述： 根据合同号获取当前申请信息
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusApply/getByPactId")
	public MfBusApply getByPactId(@RequestBody MfBusApply mfBusApply)throws Exception;

	/**
	 * 
	 * 方法描述： 获取业务详情页面信息
	 * @param mfBusApply
	 * @return
	 * Map<String,Object>
	 * @author zhs
	 * @date 2017-6-14 上午10:04:19
	 */
	@RequestMapping(value = "/mfBusApply/getBusDetailInfo")
	public Map<String, Object> getBusDetailInfo(@RequestBody MfBusApply mfBusApply) throws Exception;
	
	/**
	 * @Description:更新时,金额,期限范围验证 
	 * @param mfBusApply
	 * @return
	 * @author: 李伟
	 * @date: 2017-7-22 上午11:57:52
	 */
	@RequestMapping(value = "/mfBusApply/validateBuaApply",produces = "text/html;charset=UTF-8")
	public String validateBuaApply(@RequestBody MfBusApply mfBusApply);
	
	/**
	 * 根据客户号和产品编号查询 
	 * @param mfBusApply
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfBusApply/findByCusNoAndKindNo")
	public List<MfBusApply> findByCusNoAndKindNo(@RequestBody MfBusApply mfBusApply) throws ServiceException;
	/**
	 * 方法描述： 处理一些合同的字段
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author YuShuai
	 * @date 2017-6-15 下午9:09:10
	 */
	@RequestMapping(value = "/mfBusApply/processDataForApply")
	public MfBusApply  processDataForApply(@RequestBody MfBusApply mfBusApply)throws Exception;
	
	/**
	 * 方法描述： 通过逾期利率浮动和复利利率浮动 计算出 逾期利率和复利利率
	 * @param paramMap
	 * @throws Exception
	 * void
	 * @author WD
	 * @date 2017-7-30 下午6:10:46
	 */
	@RequestMapping(value = "/mfBusApply/doDealRateFloat")
	public MfBusApply doDealRateFloat(@RequestBody Map<String,Object> paramMap)throws Exception;

	/**
	 * 终止业务<br>
	 * 此功能用于所有阶段，请修改者知悉<br>
	 * 进件、签约，终止业务按钮<br>
	 * @param mfBusApply
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-8-2 下午3:26:58
	 */
	@RequestMapping(value = "/mfBusApply/doDisagree")
	public Map<String, Object> doDisagree(@RequestBody MfBusApply mfBusApply) throws Exception;

	@RequestMapping(value = "/mfBusApply/getRepayTypeList")
	public List<OptionsList> getRepayTypeList(@RequestParam("repayType")String repayType,
			@RequestParam("repayTypeDef")String repayTypeDef) throws Exception;
	@RequestMapping(value = "/mfBusApply/getTaskAppList")
	public List<MfBusApply> getTaskAppList(@RequestBody String opNo) throws Exception;
	/**
	 * 渠道商所关联的客户的业务申请
	 * @param ipage
	 * @param mfCusAndApply
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusApply/getCusAndApplyForTrench")
	public Ipage getCusAndApplyForTrench(@RequestBody Ipage ipage)  throws Exception;

	/**
	 * 方法描述： 只插入申请表内容
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author YuShuai
	 * @date 2017-8-23 下午3:47:55
	 */
	@RequestMapping(value = "/mfBusApply/insertAjax")
	public MfBusApply insertAjax(@RequestBody MfBusApply mfBusApply)throws Exception;

	/**
	 * 方法描述： 根据客户号和状态获取申请信息
	 * @param parmBusApply
	 * @return
	 * MfBusApply
	 * @author YuShuai
	 * @date 2017-8-23 下午4:42:06
	 */
	@RequestMapping(value = "/mfBusApply/getByCusNoAppSts")
	public MfBusApply getByCusNoAppSts(@RequestBody MfBusApply parmBusApply)throws Exception;

	/**
	 * 
	 * 方法描述： 审批状态查询列表
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author zhs
	 * @date 2017-8-30 下午5:27:15
	 */
	@RequestMapping(value = "/mfBusApply/findApprovalStsListByPage")
	public Ipage findApprovalStsListByPage(@RequestBody Ipage ipage)throws Exception;
	/**
	 * 方法描述：根据客户号和产品种类获取最新一条申请信息
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * @author Jiasl
	 */
	@RequestMapping(value = "/mfBusApply/getRecentAppByCusNoandKindNo")
	public MfBusApply getRecentAppByCusNoandKindNo(@RequestBody MfBusApply mfBusApply) throws Exception;
	/**
	 * 
	 * 方法描述： 保存申请信息，移动服务使用
	 * @param mfBusApply
	 * @return
	 * @throws ServiceException
	 * MfBusApply
	 * @author 沈浩兵
	 * @date 2017-10-13 上午11:48:06
	 */
	@RequestMapping(value = "/mfBusApply/insertForApp")
	public MfBusApply insertForApp(@RequestBody MfBusApply mfBusApply) throws ServiceException;

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
	@RequestMapping(value = "/mfBusApply/getBusApplyApprovingListByOpNo")
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
	@RequestMapping(value = "/mfBusApply/insertOrderApply")
	public Map<String,Object> insertOrderApply(@RequestBody Map<String,Object> paramMap) throws Exception;
	/**
	 * 
	 * 方法描述： 调用规则获得业务审批结果。初步设想返回0审批不通过1审批通过
	 * @param appId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-27 下午3:46:19
	 */
	@RequestMapping(value = "/mfBusApply/getApproveResultByRules")
	public Map<String,Object> getApproveResultByRules(@RequestBody String appId) throws Exception;
	/**
	 * 方法描述：检验押品保险总额是否不小于业务申请额度
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusApply/checkInsAmountInfo")
	public boolean checkInsAmountInfo(@RequestBody String appId)throws Exception;
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
	@RequestMapping(value = "/mfBusApply/getApplyListBySearch")
	public List<MfCusAndApply> getApplyListBySearch(@RequestBody String opNo,@RequestParam("search")  String search) throws Exception;
	@RequestMapping(value = "/mfBusApply/getBusSubmitCntByCusNo")
	public int getBusSubmitCntByCusNo(@RequestBody MfBusApply mfBusApply) throws Exception;

	/**
	 * 
	 * 方法描述： 只插入申请表内容,适用平安普惠
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author 沈浩兵
	 * @date 2018-1-23 上午9:57:29
	 */
	@RequestMapping(value = "/mfBusApply/insertApplyByOnlyForPaph")
	public MfBusApply insertApplyByOnlyForPaph(@RequestBody MfBusApply mfBusApply)throws Exception;

	/**
	 * 方法描述： 根据渠道相关信息获得业务信息
	 * 
	 * @param ipage
	 * @return
	 * @throws Exception Ipage
	 * @author 沈浩兵
	 * @date 2018-3-8 下午3:33:13
	 */
	@RequestMapping(value = "/mfBusApply/findTrenchBussByPage")
	public Ipage findTrenchBussByPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述： 根据业务申请号和节点编号跳转到指定的节点
	 * @param mfBusApply
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2018年5月10日 上午12:32:50
	 */
	@RequestMapping(value = "/mfBusApply/doCommitToNodeByNodeNo")
	public void doCommitToNodeByNodeNo(@RequestBody MfBusApply mfBusApply) throws Exception;
	
	@RequestMapping(value = "/mfBusApply/getApplyCount")
	public int getApplyCount(@RequestBody MfBusApply mfBusApply) throws Exception;
	
	@RequestMapping(value = "/mfBusApply/getApplyListByCusNoAndFinc")
	public List<MfBusApply> getApplyListByCusNoAndFinc(@RequestBody MfBusApply mfBusApply) throws Exception;
	/**

	 *@描述 根据名义利率获得执行利率

	 *@参数

	 *@返回值

	 *@创建人  shenhaobing

	 *@创建时间  2018/7/22

	 *@修改人和其它信息

	 */
	@RequestMapping(value = "/mfBusApply/getFincRateByNominalRate")
	public Map<String,Object> getFincRateByNominalRate(@RequestBody MfBusApply mfBusApply) throws Exception;
	
	@RequestMapping(value = "/mfBusApply/getMfBusApplyByCreditPactId")
	public List<MfBusApply> getMfBusApplyByCreditPactId(@RequestBody MfBusApply mfBusApply) throws Exception;
	/**
	 * 方法描述： 查询业务办理中数据 
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 仇招
	 * @date 2018年8月27日 下午9:47:53
	 */
	@RequestMapping(value = "/mfBusApply/findBusHandlingByPageAjax")
	public Ipage findBusHandlingByPageAjax(@RequestBody Ipage ipage) throws Exception;
	/**
	 * @方法描述： 进件列表导出excel
	 * @param mfCusAndApply
	 * @return
	 * @throws Exception
	 * List<MfCusAndApply>
	 * @author 仇招
	 * @date 2018年9月17日 下午7:31:08
	 */
	@RequestMapping(value = "/mfBusApply/findBusHandlingByPageAjaxExcel")
	public List<MfCusAndApply> findBusHandlingByPageAjaxExcel(@RequestBody MfCusAndApply mfCusAndApply) throws Exception;

	/**
	 * @方法描述： 51业务查询
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 段泽宇
	 * @date 2018年10月16日 下午14:31:08
	 */
	@RequestMapping(value = "/mfBusApply/findBusinessByPage")
	public Ipage findBusinessByPage(@RequestBody Ipage ipage) throws Exception;
	/*20190320 yxl 新增收费确认判断*/
	@RequestMapping(value = "/mfBusApply/doChargConfirmation")
	public Map<String,Object> doChargConfirmation(@RequestBody MfBusPact mfBusPact) throws Exception;
	/**
	 *
	 * 功能描述:通过产品信息 获取该产品还能融资多少金额（后台产品总金额上限-已经申请金额）
	 * @param: [mfSysKind]
	 * @return: double
	 * @auther: wd
	 * @date: 2019/6/21 10:31
	 * 
	 */
	@RequestMapping(value = "/mfBusApply/getCanApplyAmt")
	public double getCanApplyAmt(@RequestBody MfSysKind mfSysKind) throws Exception;


    @RequestMapping(value = "/mfBusApply/creditPushProcessSubmit")
    public Result creditPushProcessSubmit(@RequestBody MfBusApply mfBusApply) throws Exception;

    /**
     * @param parmMap
     * @return
     * @throws Exception
     * @desc 获取市场报价利率
     * @author zkq
     * @date 20191011
     */
    @RequestMapping(value = "/mfBusApply/getLprRate")
    public Map<String, Object> getLprRate(@RequestBody Map<String, Object> parmMap) throws Exception;


    /**
     * @param parmMap
     * @return
     * @throws Exception
     * @desc 获取系统基础利率
     * @author zkq
     * @date 20191011
     */
    @RequestMapping(value = "/mfBusApply/getMfSysRate")
    public Map<String, Object> getMfSysRate(@RequestBody Map<String, Object> parmMap) throws Exception;


    /**
     * @param parmMap
     * @return
     * @throws Exception
     * @desc 获取市场报价利率
     * @author zkq
     * @date 20191011
     */
    @RequestMapping(value = "/mfBusApply/getLprRateByRateNo")
    public Map<String, Object> getLprRateByRateNo(@RequestBody Map<String, Object> parmMap) throws Exception;
	/**
	 * @描述 客户经理查询
	 * @参数 [ipage]
	 * @返回值 app.util.toolkit.Ipage
	 * @创建人  shenhaobing
	 * @创建时间 2019/10/28
	 * @修改人和其它信息
	 */
	@RequestMapping(value = "/mfBusApply/findCusManageListByPage")
	public Ipage findCusManageListByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusApply/getCoborrForCus")
	public MfCusCustomer getCoborrForCus(@RequestBody MfBusApply mfBusApply) throws Exception;

	@RequestMapping("/mfBusApply/getFamilyByIdNum")
	public List<MfCusCustomer> getFamilyByIdNum(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	@RequestMapping("/mfBusApply/getMfBusApplyByCredit")
	public MfBusApply getMfBusApplyByCredit(@RequestBody MfBusApply mfBusApply) throws Exception;

	@RequestMapping(value = "/mfBusApply/getPactAndApply")
	public Ipage getPactAndApply(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusApply/getCreditAndApplyRisk")
	public Ipage getCreditAndApplyRisk(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusApply/getCreditAndApplyRiskList")
	public List<MfCusAndApply> getCreditAndApplyRiskList(@RequestBody MfCusAndApply mfCusAndApply) throws Exception;

	@RequestMapping(value = "/mfBusApply/insertLoanChange")
	public MfBusApply insertLoanChange(@RequestBody MfBusApply mfBusApply) throws ServiceException;

	@RequestMapping(value = "/mfBusApply/getPactAndApplyList")
	public List<MfCusAndApply> getPactAndApplyList(@RequestBody MfCusAndApply mfCusAndApply) throws Exception;

	@RequestMapping(value = "/mfBusApply/findLoanFrontByPage")
	public Ipage findLoanFrontByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusApply/getMfBusApplyListForArchive")
	Ipage getMfBusApplyListForArchive(@RequestBody Ipage ipage)throws ServiceException;
	/**
	 *
	 * 方法描述： 终止业务前校验是否退费完成
	 * @param appId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 */
	@RequestMapping(value = "/mfBusApply/valiAmtRules")
	public Map<String,Object> valiAmtRules(@RequestParam("appId")  String appId) throws Exception;

	@RequestMapping(value = "/mfBusApply/getIfUploadCredit")
    boolean getIfUploadCredit(@RequestParam("cusNo")String cusNo, @RequestParam("appId")String appId, @RequestParam("nodeNo")String nodeNo)throws Exception;
	@RequestMapping(value = "/mfBusApply/getIfFirstLoan")
    String getIfFirstLoan(@RequestParam("cusNo")String cusNo)throws Exception;

	@RequestMapping(value = "/mfBusApply/getSecondByAppId")
    MfBusApplySecond getSecondByAppId(@RequestParam("appId") String appId)throws Exception;

	@RequestMapping(value = "/mfBusApply/checkIfEval")
    boolean checkIfEval(@RequestParam("relNo")String relNo, @RequestParam("type")String type)throws Exception;

	@RequestMapping(value = "/mfBusApply/getLastedByCusNo")
	String getLastedByCusNo(@RequestBody  MfBusApply mfBusApply)throws Exception;

	@RequestMapping(value = "/mfBusApply/findByPageMeetingPlan")
    Ipage findByPageMeetingPlan(@RequestBody Ipage ipage)throws Exception;

	@RequestMapping(value = "/mfBusApply/getMeetingTimeBus")
	List<MfBusApply> getMeetingTimeBus(@RequestBody MfBusApply mfBusApply)throws Exception;
}
