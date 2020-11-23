package app.component.prdctinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.fee.entity.MfSysFeeItem;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.pact.entity.MfBusPact;
import app.component.prdct.entity.MfKindFlow;
import app.component.prdct.entity.MfKindForm;
import app.component.prdct.entity.MfKindNode;
import app.component.prdct.entity.MfKindNodeFee;
import app.component.prdct.entity.MfKindNodeIntercept;
import app.component.prdct.entity.MfKindNodeTemplate;
import app.component.prdct.entity.MfKindTableConfig;
import app.component.prdct.entity.MfSysKind;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: prdctinterface.java
 * Description:
 * @author:czk@dhcc.com.cn
 * @Thu May 26 18:39:25 CST 2016
 **/

/**
 * @author admin
 *
 */
@FeignClient("mftcc-platform-factor")
public interface PrdctInterfaceFeign {

	/**
	 * @author admin
	 * @Description: 根据产品号和产品名称进行模糊查询，此外需要传入useFlag。 date 2016-5-26
	 * @param mfSysKind
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/prdctInterface/getSysKindList")
	public List<MfSysKind> getSysKindList(@RequestBody MfSysKind mfSysKind) throws Exception;

	/**
	 * @author admin
	 * @Description: 根据产品号获得产品信息 date 2016-5-26
	 * @param kindNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/prdctInterface/getSysKindById")
	public MfSysKind getSysKindById(@RequestBody String kindNo) throws Exception;

	@RequestMapping(value = "/prdctInterface/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfSysKind") MfSysKind mfSysKind) throws Exception;

	/**
	 *
	 * 方法描述： 更新产品信息
	 *
	 * @param mfSysKind
	 * @throws Exception
	 *             void
	 * @author zhs
	 * @date 2017-4-22 下午6:15:57
	 */
	@RequestMapping(value = "/prdctInterface/update")
	public void update(@RequestBody MfSysKind mfSysKind) throws Exception;

	/**
	 * 方法描述： 获取配置的表单
	 *
	 * @param kindNo
	 *            产品种类编号
	 * @param nodeNo
	 *            节点编号
	 * @return
	 * @throws Exception
	 *             List<MfKindForm>
	 * @author YuShuai
	 * @date 2017-7-1 下午3:25:42
	 */
	@RequestMapping(value = "/prdctInterface/getInitKindFormList")
	public List<MfKindForm> getInitKindFormList(@RequestBody String kindNo, @RequestParam("nodeNo") String nodeNo,
												@RequestParam("defFlag") String defFlag) throws Exception;

	/**
	 * 方法描述： 获取单个表单的formId
	 *
	 * @param kindNo
	 * @param nodeNo
	 * @param appId
	 * @param fincId
	 * @return
	 * @throws Exception
	 *             String
	 * @author YuShuai
	 * @date 2017-7-11 上午11:19:54
	 */
	@RequestMapping(value = "/prdctInterface/getFormId")
	public String getFormId(@RequestParam("kindNo") String kindNo, @RequestBody WKF_NODE node, @RequestParam("appId") String appId,
							@RequestParam("fincId") String fincId,@RequestParam("regNo") String regNo) throws Exception;
	/**
	 * 方法描述： 获取表单配置数据
	 *
	 * @param kindNo
	 * @param nodeNo
	 * @return
	 * @throws Exception
	 * @author zhs
	 * @date 2018-11-22 下午15:50:54
	 */
	@RequestMapping(value = "/prdctInterface/getMfKindForm")
	public MfKindForm getMfKindForm(@RequestParam("kindNo") String kindNo, @RequestParam("nodeNo") String nodeNo) throws Exception;

	/**
	 * 方法描述： 根据当前操作员获取可以操作的产品种类列表
	 *
	 * @param user
	 * @return
	 * @throws Exception
	 *             List<MfSysKind>
	 * @author YuShuai
	 * @date 2017-7-2 下午5:39:04
	 */
	@RequestMapping(value = "/prdctInterface/getSysKindListByUser")
	public List<MfSysKind> getSysKindListByUser(@RequestBody JSONObject user) throws Exception;

	/**
	 * 方法描述： 获取节点拦截项列表
	 *
	 * @param mfKindNodeIntercept
	 * @return
	 * @throws Exception
	 *             List<MfKindNodeIntercept>
	 * @author Javelin
	 * @date 2017-7-4 上午11:01:00
	 */
	@RequestMapping(value = "/prdctInterface/getMfKindNodeInterceptList")
	public List<MfKindNodeIntercept> getMfKindNodeInterceptList(@RequestBody MfKindNodeIntercept mfKindNodeIntercept)
			throws Exception;

	/**
	 * 方法描述： 节点下挂费用列表 生成对应的费用计划
	 *
	 * @param appId
	 *            申请号
	 * @param nodeNo
	 *            节点编号(@RequestBody 审批节点可以置空，将所有费用生成计划)
	 * @throws Exception
	 *             void
	 * @author Javelin
	 * @date 2017-7-3 下午3:03:09
	 */
	@RequestMapping(value = "/prdctInterface/createNodeBindFeePlan")
	public void createNodeBindFeePlan(@RequestParam("appId") String appId, @RequestParam("nodeNo") String nodeNo) throws Exception;

	/**
	 * 方法描述： 节点下挂费用列表 生成对应的费用计划
	 *
	 * @param appId
	 *            申请号
	 * @param nodeNo
	 *            节点编号(@RequestBody 审批节点可以置空，将所有费用生成计划)
	 * @throws Exception
	 *             void
	 * @author Javelin
	 * @date 2017-7-3 下午3:03:09
	 */
	@RequestMapping(value = "/prdctInterface/createNodeBindFeePlanWithUser")
	public void createNodeBindFeePlanWithUser(@RequestParam("appId") String appId, @RequestParam("nodeNo") String nodeNo,
											  @RequestParam("opNo") String opNo, @RequestParam("opName") String opName, @RequestParam("brNo") String brNo,
											  @RequestParam("brName") String brName) throws Exception;

	/**
	 * 方法描述： 收取节点下挂有的费用
	 *
	 * @param parmMap
	 *            appId 申请号 nodeNo 节点编号
	 * @throws Exception
	 *             void
	 * @author Javelin
	 * @date 2017-8-4 下午3:07:13
	 */
	@RequestMapping(value = "/prdctInterface/doTakeNodeBindFee")
	public void doTakeNodeBindFee(@RequestBody Map<String, String> parmMap) throws Exception;

	/**
	 *
	 * 方法描述： 获取产品下的审批流程列表
	 *
	 * @param mfKindFlow
	 * @return
	 * @throws Exception
	 *             List<MfKindFlow>
	 * @author zhs
	 * @date 2017-7-5 下午7:19:19
	 */
	@RequestMapping(value = "/prdctInterface/getKindFlowList")
	public List<MfKindFlow> getKindFlowList(@RequestBody MfKindFlow mfKindFlow) throws Exception;

	/**
	 *
	 * 方法描述：删除某一产品下所有节点上的某一个费用配置
	 *
	 * @param mfKindNodeFee
	 * @throws Exception
	 *             void
	 * @author zhs
	 * @date 2017-7-8 下午6:56:30
	 */
	@RequestMapping(value = "/prdctInterface/deleteNodeFee")
	public void deleteNodeFee(@RequestBody MfKindNodeFee mfKindNodeFee) throws Exception;

	/**
	 * 方法描述： 获取该产品种类下的贷款用途
	 *
	 * @param kindNo
	 * @return
	 * @throws Exception
	 *             JSONArray
	 * @author YuShuai
	 * @date 2017-7-17 下午5:06:32
	 */
	@RequestMapping(value = "/prdctInterface/getFincUse",produces="text/html;charset=utf-8")
	public String getFincUse(@RequestBody String kindNo) throws Exception;

	/**
	 * 根据产品编号获得产品下配置的费用项
	 *
	 * @param mfSysKindList
	 * @author MaHao
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/prdctInterface/getFeeItemMap")
	public Map<String, Object> getFeeItemMap(@RequestBody List<MfSysKind> mfSysKindList) throws Exception;

	/**
	 * @Description:数据字典中根据编号获取名称(@RequestBody 适用于单选,@RequestParam多选)
	 * @param vouType
	 * @param keyName
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-7-24 上午11:45:31
	 */
	@RequestMapping(value = "/prdctInterface/getTypeNameByTypeValue",produces="text/html;charset=UTF-8")
	public String getTypeNameByTypeValue(@RequestParam("typeValue") String typeValue, @RequestParam("keyName") String keyName) throws Exception;

	/**
	 * @Description:数据字典过滤
	 * @param typeValue
	 * @param keyName
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-7-24 下午6:45:54
	 */
	@RequestMapping(value = "/prdctInterface/filterDictoryData")
	public JSONArray filterDictoryData(@RequestParam("typeValue") String typeValue, @RequestParam("keyName") String keyName) throws Exception;

	/**
	 *
	 * 方法描述： 处理还款固定还款日
	 *
	 * @param mfBusPact
	 * @return
	 * @throws Exception
	 *             JSONArray
	 * @author 栾好威
	 * @date 2017-8-31 下午8:34:55
	 */
	@RequestMapping(value = "/prdctInterface/getRepayRulesMap")
	public JSONArray getRepayRulesMap(@RequestBody MfBusPact mfBusPact) throws Exception;

	/**
	 * 获取节点下费用列表 可传参数申请号，产品号，节点号
	 *
	 * @param mfKindNodeFee
	 * @return
	 * @author MaHao
	 * @throws Exception
	 */
	@RequestMapping(value = "/prdctInterface/getMfBusAppFeeList")
	public List<MfKindNodeFee> getMfBusAppFeeList(@RequestBody MfKindNodeFee mfKindNodeFee) throws Exception;

	/**
	 * 获取收取节点下的费用视图
	 *
	 * @param appId
	 *            业务申请号
	 * @param nodeNo
	 *            收取节点 参考BizPubParm.WKF_NODE里的节点编号
	 * @param amt
	 *            基准金额，用来计算比率使用
	 * @param beginDate
	 *            格式yyyyMMdd 时间 计算基准金额*时间（天）*比率
	 * @param endDate
	 * @author MaHao
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/prdctInterface/getBusFeePlanView")
	public List<MfKindNodeFee> getBusFeePlanView(@RequestParam("appId") String appId, @RequestParam("nodeNo") String nodeNo,
												 @RequestParam("amt") String amt, @RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate) throws Exception;

	@RequestMapping(value = "/prdctInterface/getMfKindFlowById")
	public MfKindFlow getMfKindFlowById(@RequestBody MfKindFlow mfKindFlow) throws Exception;

	/**
	 *
	 * 方法描述： 获取所有启动的业务流程的流程节点，并过滤重复的节点
	 *
	 * @return
	 * @throws Exception
	 *             JSONArray
	 * @author zhs
	 * @date 2017-8-9 下午3:07:05
	 */
	@RequestMapping(value = "/prdctInterface/getFlowNodeArray")
	public JSONArray getFlowNodeArray() throws Exception;

	/**
	 * 方法描述： 获取费用列表
	 *
	 * @param mfKindNodeFee
	 * @return
	 * @throws Exception
	 *             List<MfKindNodeFee>
	 * @author YuShuai
	 * @date 2017-8-10 下午5:25:34
	 */
	@RequestMapping(value = "/prdctInterface/getMfKindNodeFeeList")
	public List<MfKindNodeFee> getMfKindNodeFeeList(@RequestBody MfKindNodeFee mfKindNodeFee) throws Exception;

	/**
	 *
	 * 方法描述： 获取流程配置信息
	 *
	 * @param mfKindFlow
	 * @return
	 * @throws Exception
	 *             MfKindFlow
	 * @author zhs
	 * @date 2017-8-15 下午12:39:54
	 */
	@RequestMapping(value = "/prdctInterface/getKindFlow")
	public MfKindFlow getKindFlow(@RequestBody MfKindFlow mfKindFlow) throws Exception;

	/**
	 * 方法描述： 获取单个配置的表单
	 *
	 * @param kindNo
	 *            产品种类编号
	 * @param nodeNo
	 *            节点编号
	 * @return
	 * @throws Exception
	 *             MfKindForm
	 * @author YuShuai
	 * @date 2017-7-1 下午3:25:42
	 */
	@RequestMapping(value = "/prdctInterface/getInitKindForm")
	public MfKindForm getInitKindForm(@RequestParam("kindNo") String kindNo, @RequestParam("nodeNo") String nodeNo,
									  @RequestParam("defFalg") String defFalg) throws Exception;

	/**
	 *
	 * 方法描述：获取利息计算区间标志 1-算头不算尾 2-首尾都计算
	 *
	 * @param kindNo
	 * @return
	 * @throws Exception
	 *             parmMap kindNo,appId
	 * @author WD
	 * @date 2017-9-12 上午10:52:02
	 */
	@RequestMapping(value = "/prdctInterface/getCalcIntstFlag")
	public String getCalcIntstFlag(@RequestBody Map<String, String> parmMap) throws Exception;

	/**
	 *
	 * 方法描述： 获得业务流程下的所有节点配置信息
	 *
	 * @param mfKindNode
	 * @return
	 * @throws Exception
	 *             List<MfKindNode>
	 * @author 沈浩兵
	 * @date 2017-9-8 下午5:15:22
	 */
	@RequestMapping(value = "/prdctInterface/getMfKindNodeList")
	public List<MfKindNode> getMfKindNodeList(@RequestBody MfKindNode mfKindNode) throws Exception;

	/**
	 *
	 * 方法描述： 获得节点下配置的模板
	 *
	 * @param mfKindNodeTemplate
	 * @return
	 * @throws Exception
	 *             List<MfKindNodeTemplate>
	 * @author 沈浩兵
	 * @date 2017-9-8 下午5:41:35
	 */
	@RequestMapping(value = "/prdctInterface/getNodeTemplateList")
	public List<MfKindNodeTemplate> getNodeTemplateList(@RequestBody MfKindNodeTemplate mfKindNodeTemplate)
			throws Exception;

	/**
	 *
	 * 方法描述： 获得产品的可使用的费用项
	 *
	 * @param mfSysFeeItem
	 * @return
	 * @throws Exception
	 *             List<MfSysFeeItem>
	 * @author 沈浩兵
	 * @date 2017-9-9 上午10:02:15
	 */
	@RequestMapping(value = "/prdctInterface/getMfSysFeeItemList")
	public List<MfSysFeeItem> getMfSysFeeItemList(@RequestBody MfSysFeeItem mfSysFeeItem) throws Exception;

	/**
	 *
	 * 方法描述： 查询节点下配置费用信息
	 *
	 * @param mfKindNodeFee
	 * @return
	 * @throws Exception
	 *             List<MfKindNodeFee>
	 * @author 沈浩兵
	 * @date 2017-9-9 上午11:37:45
	 */
	@RequestMapping(value = "/prdctInterface/getNodeFeeList")
	public List<MfKindNodeFee> getNodeFeeList(@RequestBody MfKindNodeFee mfKindNodeFee) throws Exception;

	/**
	 * 方法描述： 获取mfKindForm实体
	 *
	 * @param mfKindForm
	 * @return
	 * @throws Exception
	 *             MfCusFormConfig
	 * @author YuShuai
	 * @date 2017-11-23 下午7:16:17
	 */
	@RequestMapping(value = "/prdctInterface/getMfkindForm")
	public MfKindForm getMfkindForm(@RequestBody MfKindForm mfKindForm) throws Exception;

	/**
	 * 方法描述： 获取需要校验的表单
	 *
	 * @param mfKindTableConfig
	 * @return
	 * @throws Exception
	 *             List<MfKindTableConfig>
	 * @author YuShuai
	 * @date 2017-11-25 下午5:07:38
	 */
	@RequestMapping(value = "/prdctInterface/getMfKindTableConfigList")
	public List<MfKindTableConfig> getMfKindTableConfigList(@RequestBody MfKindTableConfig mfKindTableConfig)
			throws Exception;

	/**
	 *
	 * 方法描述：合同借据结束日期展示 1-显示结束日期 2-显示结束日期减一天
	 *
	 * @return String
	 * @author WD
	 * @date 2017-12-12 下午2:11:36
	 */
	@RequestMapping(value = "/prdctInterface/getPactEndDateShowFlag")
	public String getPactEndDateShowFlag() throws Exception;

	@RequestMapping(value = "/prdctInterface/getKindNodeFee")
	public MfKindNodeFee getKindNodeFee(@RequestBody MfKindNodeFee mfKindNodeFee) throws Exception;

	/**
	 * @author ldy
	 * @Description: 根据产品号和产品名称进行模糊查询，此外需要传入cusType。 date 2018-6-02
	 * @param mfSysKind
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/prdctInterface/getKindListByCusType")
	public List<MfSysKind> getKindListByCusType(@RequestBody MfSysKind mfSysKind) throws Exception ;
	/**
	 *
	 *<p>Description:校验产品是否是该客户可办理的产品 </p> 
	 *@param mfSysKind
	 *@return
	 *@throws Exception
	 *@author 周凯强
	 *@date 2018年6月25日上午8:47:07
	 */
	@RequestMapping(value = "/prdctInterface/isCanProduct")
	public  List<MfSysKind>   isCanProduct(@RequestBody MfSysKind mfSysKind) throws  Exception;

}
