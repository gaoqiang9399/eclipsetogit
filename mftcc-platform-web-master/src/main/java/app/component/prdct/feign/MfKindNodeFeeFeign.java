package app.component.prdct.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.prdct.entity.MfKindNodeFee;
import app.util.toolkit.Ipage;

/**
 * Title: MfKindNodeFeeBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Jun 29 15:43:10 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfKindNodeFeeFeign {

	@RequestMapping(value = "/mfKindNodeFee/insert", method = RequestMethod.POST)
	public Map<String, Object> insert(@RequestBody MfKindNodeFee mfKindNodeFee) throws Exception;

	@RequestMapping(value = "/mfKindNodeFee/delete", method = RequestMethod.POST)
	public Map<String, Object> delete(@RequestBody MfKindNodeFee mfKindNodeFee) throws Exception;

	@RequestMapping(value = "/mfKindNodeFee/getById", method = RequestMethod.POST)
	public MfKindNodeFee getById(@RequestBody MfKindNodeFee mfKindNodeFee) throws Exception;

	@RequestMapping(value = "/mfKindNodeFee/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfKindNodeFee") MfKindNodeFee mfKindNodeFee) throws Exception;

	/**
	 * 方法描述： 获取节点下费用列表
	 * 
	 * @param mfKindNodeFee
	 * @return
	 * @throws Exception
	 *             List<MfKindNodeFee>
	 * @author Javelin
	 * @date 2017-6-29 下午4:14:54
	 */
	@RequestMapping(value = "/mfKindNodeFee/getMfBusAppFeeList", method = RequestMethod.POST)
	public List<MfKindNodeFee> getMfBusAppFeeList(@RequestBody MfKindNodeFee mfKindNodeFee) throws Exception;

	/**
	 * 方法描述： 修改费率
	 * 
	 * @param mfKindNodeFee
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-6-29 下午4:54:32
	 */
	@RequestMapping(value = "/mfKindNodeFee/updateFeeRate", method = RequestMethod.POST)
	public String updateFeeRate(@RequestBody MfKindNodeFee mfKindNodeFee) throws Exception;

	/**
	 * 方法描述： 节点下挂费用列表 生成对应的费用计划
	 * 
	 * @param mfKindNodeFee
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-7-3 下午3:06:07
	 */
	@RequestMapping(value = "/mfKindNodeFee/createNodeBindFeePlan", method = RequestMethod.POST)
	public String createNodeBindFeePlan(@RequestBody MfKindNodeFee mfKindNodeFee) throws Exception;

	/**
	 * 方法描述： 收取节点下挂有的费用
	 * 
	 * @param parmMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-8-4 下午3:06:05
	 */
	@RequestMapping(value = "/mfKindNodeFee/doTakeNodeBindFee", method = RequestMethod.POST)
	public String doTakeNodeBindFee(@RequestBody Map<String, String> parmMap) throws Exception;

	/**
	 * 
	 * 方法描述： 删除节点上的某一费用项
	 * 
	 * @param mfKindNodeFee
	 * @throws Exception
	 *             void
	 * @author zhs
	 * @date 2017-7-8 下午7:01:52
	 */
	@RequestMapping(value = "/mfKindNodeFee/deleteNodeFee", method = RequestMethod.POST)
	public void deleteNodeFee(@RequestBody MfKindNodeFee mfKindNodeFee) throws Exception;

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
	 *            格式yyyyMMdd 时间 计算基准金额*比率(@RequestBody 暂不使用期限（天）进行计算 20170726)
	 * @param endDate
	 * @author MaHao
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfKindNodeFee/getBusFeePlanView", method = RequestMethod.POST)
	public List<MfKindNodeFee> getBusFeePlanView(@RequestBody String appId, @RequestParam("nodeNo") String nodeNo,
			@RequestParam("amt") String amt, @RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate) throws Exception;

	/**
	 * 方法描述：获取产品节点下的收费项
	 * 
	 * @param mfKindNodeFee
	 *            必传：kindNo，optPower，nodeNo，moneyBackFlag,appId
	 * @return 1|2|3|
	 */
	@RequestMapping(value = "/mfKindNodeFee/getItemNosByBean", method = RequestMethod.POST)
	public String getItemNosByBean(@RequestBody MfKindNodeFee mfKindNodeFee) throws Exception;

	/**
	 * 方法描述： 获取应收费用列表
	 * 
	 * @param mfKindNodeFee
	 * @return
	 * @throws Exception
	 *             List<MfKindNodeFee>
	 * @author YuShuai
	 * @date 2017-8-10 下午5:27:25
	 */
	@RequestMapping(value = "/mfKindNodeFee/getMfKindNodeFeeList", method = RequestMethod.POST)
	public List<MfKindNodeFee> getMfKindNodeFeeList(@RequestBody MfKindNodeFee mfKindNodeFee) throws Exception;

	/**
	 * 
	 * 方法描述： getMfSysFeeItemList
	 * 
	 * @param mfKindNodeFee
	 * @return
	 * @throws Exception
	 *             List<MfKindNodeFee>
	 * @author 沈浩兵
	 * @date 2017-9-9 上午11:39:31
	 */
	@RequestMapping(value = "/mfKindNodeFee/getKindNodeFeeList", method = RequestMethod.POST)
	public List<MfKindNodeFee> getKindNodeFeeList(@RequestBody MfKindNodeFee mfKindNodeFee) throws Exception;
	
	@RequestMapping(value = "/mfKindNodeFee/deleteForPrdct", method = RequestMethod.POST)
	public Map<String, Object> deleteForPrdct(MfKindNodeFee mfKindNodeFee) throws Exception;

}
