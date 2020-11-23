package app.component.calccoreinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.core.entity.MfBusFeePlan;
import app.component.calc.core.entity.MfBusFeePlanHistory;
import app.component.calc.core.entity.MfBusFeePlanHistoryDetail;
import app.component.calc.core.entity.MfFeePlanParameter;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusFincAppChild;
import app.component.pact.extension.entity.MfBusExtensionResultDetail;
import app.component.prdct.entity.MfKindNodeFee;

/**
 * 费用接口
 * @author WD
 *
 */
@FeignClient("mftcc-platform-factor")
public interface CalcFeePlanInterfaceFeign {
	/**
	 * 方法描述：插入费用计划
	 * @param mfBusFeePlans
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcFeePlanInterface/insertFeePlan")
	public Map<String , Object> insertFeePlan(@RequestBody List<MfBusFeePlan> mfBusFeePlans)throws Exception;
	/**
	 * 方法描述：根据业务编号获取每期费用合计
	 * @param mfBusFeePlan
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcFeePlanInterface/getFeeAmtSum")
	public List<Map<String , Object>> getFeeAmtSum(@RequestBody MfBusFeePlan mfBusFeePlan)throws Exception;
	/**
	 * 方法描述：业务id和费用编号获取费用计划 (appId,itemNo)
	 * 方法使用时  只需要 传入 appId 和  费用项编号
	 * @param itemNo 费用项编号 支持多编号需要"1|2|3|"</br>
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcFeePlanInterface/getFeePlan")
	public List<MfBusFeePlan> getFeePlan(@RequestBody MfFeePlanParameter mfFeePlanParameter)throws Exception;
	/**
	 * 方法描述：业务编号和费用编号获取费用计划,获取还款计划视图数据 目前只支持 一次性收取 比率计算方式为 基准金额*比率*时间（天）
	 * @param itemNo 费用项编号 支持多编号需要"|0|1"
	 * @param appId		申请Id
	 * @param pactId	合同Id
	 * @param fincId	借据Id
	 * @author MaHao
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcFeePlanInterface/getBusFeePlanView")
	public List<MfBusFeePlan> getBusFeePlanView(@RequestBody MfFeePlanParameter mfFeePlanParameter)
			throws Exception;
	/**
	 * 方法描述：根据参数获取自定义费用计划
	 * @param mfFeePlanParameter
	 * @param  amount         基准金额即担保金额<br>
	 * @param  planBeginDate  开始时间<br>
	 * @param  planEndDate    结束时间<br>
	 * @param  takenMode      收取方式    1-一次收取;3-按年收取;4-按季收取;5-按月收取<br>
	 * @param  computeMode    计算方式    1-固额;2-按比例<br>
	 * @param  fixedQuota     固定额度<br>
	 * @param  rate           比例/费率<br>
	 * @param  decimalDigits  保留小数位数（四舍五入）  2-两位;1-一位;0-不保留<br>
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 */
	@RequestMapping(value = "/calcFeePlanInterface/getDefinitionFee")
	public Map<String, Object> getDefinitionFee(@RequestBody MfFeePlanParameter mfFeePlanParameter) throws Exception;
	/**
	 * 方法描述：根据参数获取担保费用计划
	 * @param mfFeePlanParameter
	 * @param  amount         基准金额即担保金额<br>
	 * @param  planBeginDate  开始时间<br>
	 * @param  planEndDate    结束时间<br>
	 * @param  takenMode      收取方式    1-一次收取;3-按年收取;4-按季收取;5-按月收取<br>
	 * @param  computeMode    计算方式    1-固额;2-按比例<br>
	 * @param  fixedQuota     固定额度<br>
	 * @param  rate           比例/费率<br>
	 * @param  rateType       费率类型    1-年费率;2-月费率<br>
	 * @param  decimalDigits  保留小数位数（四舍五入）2-两位;1-一位;0-不保留<br>
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 */
	@RequestMapping(value = "/calcFeePlanInterface/getGuaranteeFee")
	public Map<String, Object> getGuaranteeFee(@RequestBody MfFeePlanParameter mfFeePlanParameter) throws Exception;
	/**
	 * 
	 * 方法描述：String kindNo,String appId,String takeNode,String itemType
	 * @param appId  itemType 必须传
	 * @return  itemNo  0|1|2
	 * @throws Exception 
	 * String
	 * @author WD
	 * @date 2017-7-29 上午9:53:27
	 */
	@RequestMapping(value = "/calcFeePlanInterface/getItemNoByAppId")
	public String getItemNoByAppId(@RequestBody Map<String,String> parmMap) throws Exception;
	
	/**
	 * 
	 * 方法描述： 一次性费用收取 调用的方法(改方法用于一次性费用收取时使用)
	 * 方法使用时  必须传 入   业务id  appId或者 pactId 或者 fincId</br>
	 *                 费用编号   itemNo字符串 1|2|3</br>
	 *                 操作员信息 opNo opName brNo brName</br>
	 * 
	 * @param feeParmMap
	 * @return 
	 * Map<String,Object>
	 * @author WD
	 * @date 2017-7-29 下午2:37:05
	 */
	@RequestMapping(value = "/calcFeePlanInterface/doTakeFeeByOneTime")
	public Map<String,Object> doTakeFeeByOneTime(@RequestBody Map<String,String> feeParmMap) throws Exception;
	
	/**
	 * 
	 * 方法描述：退费方法 传入的参数是 业务Id        fincId</br>
	 *                        费用计划id     feePlanId</br> 
	 *                        费用编号                  itemNo  字符串 1|2|3</br>                         
	 * @param feeParmMap
	 * @return
	 * @throws Exception 
	 * Map<String,Object>
	 * @author WD
	 * @date 2017-8-5 上午9:28:21
	 */
	@RequestMapping(value = "/calcFeePlanInterface/doReturnFeeByInfo")
	public Map<String,Object> doReturnFeeByInfo(@RequestBody Map<String,String> feeParmMap) throws Exception;

	/**
	 * 
	 * 方法描述：通过借据号和收费 退费 状态 以及 outFlag 和收取时间（格式 （'1','2'）） 获取费用计划列表
	 * @return 
	 * List<MfBusFeePlan>
	 * @author WD
	 * @date 2017-8-7 下午5:56:34
	 */
	@RequestMapping(value = "/calcFeePlanInterface/getMfBusFeePlanByfeeBean")
	public List<MfBusFeePlan> getMfBusFeePlanByfeeBean(@RequestBody MfBusFeePlan mfBusFeePlan)throws Exception;

	/**
	 * 方法描述：生成退费费用计划
	 * @param nodeNo :节点编号
	 * @param appId：业务申请id
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcFeePlanInterface/insertReturnFeePlan")
	public void insertReturnFeePlan(@RequestBody String appId,@RequestParam("nodeNo")  String nodeNo) throws Exception;
	/**
	 * 方法描述：生成费用计划
	 * 使用须知：方法使用时 必须传mfFeePlanParameter对象中的 MfBusApply，mfBusPact，mfBusFincAppChild 中的一个 且只能传入一个</br>
	 * mfFeePlanParameter对象中的itemNos 费用项编号 支持多编号需要"1|2|3|"</br>
	 */
	@RequestMapping(value = "/calcFeePlanInterface/insertFeePlan")
	public void insertFeePlan(@RequestBody MfFeePlanParameter mfFeePlanParameter) throws Exception;
	/**
	 * 方法描述：根据参数获取按照还款计划期数收取费用
	 * @param mfFeePlanParameter
	 * @param  amount         基准金额即担保金额<br>
	 * @param  computeMode    计算方式    1-固额;2-按比例<br>
	 * @param  fixedQuota     固定额度<br>
	 * @param  rate           比例/费率<br>
	 * @param  repayPeriods   还款计划期数
	 * @param  decimalDigits  保留小数位数（四舍五入）2-两位;1-一位;0-不保留<br>
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 */
	@RequestMapping(value = "/calcFeePlanInterface/getRepayPeriodsCost")
	public Map<String, Object> getRepayPeriodsCost(@RequestBody MfFeePlanParameter mfFeePlanParameter) throws Exception;
	/**
	 * 
	 * 方法描述：还款计划页面 获取费用计划展示 使用 
	 * @param repayPlanList
	 * @param mfBusFeePlan
	 * @return
	 * @throws Exception 
	 * Object
	 * @author WD
	 * @date 2017-8-19 下午4:01:59
	 */
	@RequestMapping(value = "/calcFeePlanInterface/getMfBusFeePlanViewByfeeBean")
	public List<MfBusFeePlan> getMfBusFeePlanViewByfeeBean(@RequestBody List<MfRepayPlan> repayPlanList,@RequestParam("mfBusFincApp") MfBusFincApp mfBusFincApp)throws Exception;
	/**
	 * 
	 * 方法描述：处理 费用计划 删除原来的 在还款计划 节点生成的费用收取计划 生成新的 费用计划
	 * @param repayPlanList
	 * @param mfBusFincApp 
	 * void
	 * @author WD
	 * @date 2017-8-20 上午10:42:41  
	 */
	@RequestMapping(value = "/calcFeePlanInterface/doDealFeePlanByRepayPlanList")
	public Map<String,Object> doDealFeePlanByRepayPlanList(@RequestBody List<MfRepayPlan> repayPlanList,@RequestParam("mfBusFincApp") MfBusFincApp mfBusFincApp)throws Exception;
	
	/**
	 * 
	 * 方法描述：通过fincId和 节点 名称 获取该结点下 要收取的费用字符串 1|2|5
	 * @return
	 * @throws Exception 
	 * String
	 * @author WD
	 * @date 2017-8-24 下午9:02:45
	 */
	@RequestMapping(value = "/calcFeePlanInterface/getNodeItemNosByFincId")
	public String getNodeItemNosByFincId(@RequestBody String fincId,@RequestParam("node") String node) throws Exception;

	/**
	 * 插入费用收取总表
	 * @param mfBusFeePlanHistory
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcFeePlanInterface/insertMfBusFeePlanHistory")
	public  void insertMfBusFeePlanHistory(@RequestBody MfBusFeePlanHistory mfBusFeePlanHistory)throws Exception;
	/**
	 * 插入费用收取明细表
	 * @param mfBusFeePlanHistoryDetail
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcFeePlanInterface/insertMfBusFeePlanHistoryDetail")
	public  void insertMfBusFeePlanHistoryDetail(@RequestBody MfBusFeePlanHistoryDetail mfBusFeePlanHistoryDetail)throws Exception;
	/**
	 * 修改费用计划
	 * @param mfBusFeePlan
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcFeePlanInterface/updateMfBusFeePlan")
	public  void updateMfBusFeePlan(@RequestBody MfBusFeePlan mfBusFeePlan)throws Exception;
	@RequestMapping(value = "/calcFeePlanInterface/getBusFeePlanList")
	public List<MfBusFeePlan> getBusFeePlanList(@RequestBody MfBusFeePlan mfBusFeePlan)
			throws Exception ;
	/**
	 * 
	 * 方法描述：展期生成费用计划相关处理(费用计划拼接处理 期数增加)
	 * @param repayPlanList
	 * @param mfBusFincApp
	 * @param extensionResultDetail 
	 * void
	 * @author WD
	 * @date 2017-9-23 下午4:42:52
	 */
	@RequestMapping(value = "/calcFeePlanInterface/doDealExtensionFeePlanByRepayPlanList")
	public void doDealExtensionFeePlanByRepayPlanList(@RequestBody List<MfRepayPlan> repayPlanList, @RequestParam("mfBusFincApp") MfBusFincApp mfBusFincApp,
			@RequestParam("mfBusExtensionResultDetail")  MfBusExtensionResultDetail mfBusExtensionResultDetail)throws Exception ;
	/**
	 * 
	 * 方法描述：通过借据号 费用计划状态 费用计划类型  费用收取时间 通过期号分组获取费用计划
	 * @param mfBusFeePlan
	 * @return 
	 * List<MfBusFeePlan>
	 * @author WD
	 * @date 2018-1-3 下午2:41:09
	 */
	@RequestMapping(value = "/calcFeePlanInterface/getMfBusFeePlanByfeeBeanGroupByTerm")
	public List<MfBusFeePlan> getMfBusFeePlanByfeeBeanGroupByTerm(@RequestBody MfBusFeePlan mfBusFeePlan)throws Exception ;
	/**
	 * 
	 * 方法描述：从费用计划表中 判断放款时是否存在收取费用（一次性费用） 如果收取   那么返回费用相关信息 费用计划列表  费用总和
	 * @param mfBusFincAppChild  parmMap</br>
	 * @return 	feeMap.put("feeFlag", "1");//该节点一次性相关费用  0 不存在费用  1 存在费用 </br>
@RequestParam("				feeMap.put("feePlan"") 				feeMap.put("feePlan",@RequestParam("busFeePlanList") busFeePlanList);//费用计划列表</br>
@RequestParam("				feeMap.put("feeSum"") 				feeMap.put("feeSum",@RequestParam("feeSum")  feeSum);//费用总和</br>
	 * @throws Exception 
	 * Map<String,Object>
	 * @author WD
	 * @date 2018-1-4 上午10:56:19
	 */
	@RequestMapping(value = "/calcFeePlanInterface/getBusFeeByPutOut")
	public Map<String, Object> getBusFeeByPutOut(
@RequestParam("mfBusFincAppChild") 			MfBusFincAppChild mfBusFincAppChild,@RequestParam("parmMap")  Map<String, String> parmMap)throws Exception ;
	
	/**
	 * 
	 * 方法描述：通过借据号 和 费用收取时间 整体更新相关费用计划
	 * @param busFeePlan
	 * @throws Exception 
	 * void
	 * @author WD
	 * @date 2018-1-5 下午3:24:43
	 */
	@RequestMapping(value = "/calcFeePlanInterface/updateMfBusFeePlanByFeeCollectInfo")
	public void updateMfBusFeePlanByFeeCollectInfo(@RequestBody MfBusFeePlan busFeePlan)throws Exception;
	
	
	/**
	 * 
	 * 方法描述：通过 产品号,appid,和收费节点 收取时间  获取 还款计划 节点下 应该收取费用项 4|5|9
	 * @param mfKindNodeFee
	 * @return 
	 * String
	 * @author WD
	 * @date 2018-01-19 上午11:30:19
	 */
	@RequestMapping(value = "/calcFeePlanInterface/getNodeItemNosByBean")
	public String getNodeItemNosByBean(@RequestBody MfKindNodeFee mfKindNodeFee,@RequestParam("appId") String appId)throws Exception;
	/**
	 * 
	 * 方法描述：通过appId,费用收取时间 获取还款计划节点收取的费用信息
	 * @param busAppFee
	 * @return
	 * @throws Exception 
	 * String
	 * @author WD
	 * @date 2018-1-19 上午11:46:06
	 */
	@RequestMapping(value = "/calcFeePlanInterface/getNodeItemNosByMfBusAppfee")
	public List<MfBusAppFee> getNodeItemNosByMfBusAppfee(@RequestBody MfBusAppFee busAppFee,@RequestParam("mfBusFincApp") MfBusFincApp mfBusFincApp)throws Exception;
	
	@RequestMapping(value = "/calcFeePlanInterface/doFincFeePlan")
	public List<MfBusFeePlan> doFincFeePlan(@RequestBody Map<String, String> feeMapParm)throws Exception;
	
	@RequestMapping(value = "/calcFeePlanInterface/getFincBusFeeCollectData")
	public Map<String, Object> getFincBusFeeCollectData(@RequestBody String appId,@RequestParam("fincId") String fincId,@RequestParam("itemsNo") String itemsNo)throws Exception;
	
	@RequestMapping(value = "/calcFeePlanInterface/doFincBusFeeCollect")
	public void doFincBusFeeCollect(@RequestBody Map<String, String> feeMapParm)throws Exception;
}
