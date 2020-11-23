package app.component.riskinterface;

import java.util.List;
import java.util.Map;

import app.component.risk.entity.RiskItem;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.risk.aftwarn.entity.MfWarningParm;
import app.component.risk.entity.RiskBizItemRel;

@FeignClient("mftcc-platform-factor")
public interface RiskInterfaceFeign {

	/**
	 * 调用风险拦截接口之前先调用此方法生成相关拦截项
	 * 
	 * @param scNo
	 *            拦截业务场景编号
	 * @param relNo
	 *            业务关联号
	 * @param object
	 *            参加风险拦截计算的实体对象
	 * @throws Exception
	 */
	@RequestMapping(value = "/riskInterface/createRiskItem")
	public void createRiskItem(@RequestBody String scNo, @RequestParam("relNo") String relNo, @RequestParam("object") Object object)
			throws Exception;

	/**
	 * @author czk
	 * @Description: 两个createRiskItem方法的作用是一致的，只是传入的参数形式不同 date 2016-9-6
	 * @param scNo
	 * @param relNo
	 * @param parmMap
	 * @throws Exception
	 */
	@RequestMapping(value = "/riskInterface/createRiskItem")
	public void createRiskItem(@RequestBody String scNo, @RequestParam("relNo") String relNo,
			@RequestParam("parmMap") Map<String, String> parmMap) throws Exception;

	/**
	 * 
	 * 方法描述： 生成风险拦截项拦截结果
	 * 
	 * @param dataMap
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-1-18 下午1:54:57
	 */
	@RequestMapping(value = "/riskInterface/insertRiskResult")
	public void insertRiskResult(@RequestBody Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/riskInterface/findByRelNo")
	public List<RiskBizItemRel> findByRelNo(@RequestParam("relNo") String relNo) throws Exception;

	/**
	 * 
	 * 方法描述： 根据风险拦截配置获得风险拦截项返回数据
	 * 
	 * @param dimeMap
	 * @return
	 * @throws Exception
	 *             RiskPreventSceGen
	 * @author 沈浩兵
	 * @date 2017-1-18 上午10:55:14
	 */
	@RequestMapping(value = "/riskInterface/getResultByRiskConfig")
	public Map<String, Object> getResultByRiskConfig(@RequestBody Map<String, Object> dimeMap) throws Exception;

	/**
	 * 方法描述： 根据准入配置获得准入项返回数据
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author YuShuai
	 * @date 2017-5-10 上午9:38:52
	 */
	@RequestMapping(value = "/riskInterface/getResultByAccesConfig")
	public Map<String, Object> getResultByAccesConfig(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 
	 * 方法描述：根据要件表单元素配置获得要件预览的数据
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author zhs
	 * @date 2017-5-16 下午4:48:36
	 */
	@RequestMapping(value = "/riskInterface/getResultByDocTypeConfig")
	public Map<String, Object> getResultByDocTypeConfig(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 
	 * 方法描述：根据预警parmId获取预警信息
	 * 
	 * @param parmId
	 * @return
	 * @throws Exception
	 *             MfWarningParm
	 * @author zhang_dlei
	 * @date 2017-07-06 下午4:48:36
	 */
	@RequestMapping(value = "/riskInterface/getMfWarningParmById")
	public MfWarningParm getMfWarningParmById(@RequestBody String parmId) throws Exception;

	/**
	 * 
	 * 方法描述： 根据拦截项编号获取拦截项信息
	 * 
	 * @param itemNo
	 * @return
	 * @throws Exception
	 *             RiskItem
	 * @author zhs
	 * @date 2017-7-7 上午11:51:59
	 */
	@RequestMapping(value = "/riskInterface/getRiskItem")
	public RiskItem getRiskItem(@RequestBody String itemNo) throws Exception;

	/**
	 * 方法描述： 根据节点设置调用规则引擎获取风险拦截数据
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 *             Map<String, Object> 返回结果：{list=[客户评级等级高于B评级,@RequestParam
	 *             ....],@RequestParam exsitRefused=false} exsitRefused ：
	 *             是否存在业务拒绝 Boolean list ：业务拒绝具体描述
	 * @author zhang_dlei
	 * @date 2017-7-1 下午2:43:37
	 */

	@RequestMapping(value = "/riskInterface/getRiskBeanForNode")
	public Map<String, Object> getRiskBeanForNode(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 方法描述： 根据节点设置调用规则引擎获取风险拦截数据（业务提交拦截）
	 * 
	 * @param dataMap
	 *            relNo 业务编号（必传） nodeNo 节点编号（必传） cusNo 客户号 appId 申请号 pledgeNo
	 *            押品号 pactId 合同号 fincId 借据号 rulesNo 规则引擎编号
	 * @return
	 * @throws Exception
	 *             Map<String, Object> 返回结果：{list=[客户评级等级高于B评级,@RequestParam
	 *             ....],@RequestParam exsitRefused=false} exsitRefused ：
	 *             是否存在业务拒绝 Boolean list ：业务拒绝具体描述
	 * @author Javelin
	 * @date 2017-7-1 下午4:09:35
	 */
	@RequestMapping(value = "/riskInterface/getNodeRiskData")
	public Map<String, Object> getNodeRiskData(@RequestBody Map<String, String> parmMap, @RequestParam("rulesNo") String rulesNo
			) throws Exception;;

}
