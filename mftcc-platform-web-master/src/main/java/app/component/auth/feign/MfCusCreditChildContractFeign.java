package  app.component.auth.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.auth.entity.MfCusCreditChildContract;
import app.util.toolkit.Ipage;

/**
* Title: MfCusCreditChildContractBo.java
* Description:客户授信申请业务操作控制
* @author:LJW
* @Mon Feb 27 10:43:09 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusCreditChildContractFeign {
	
	@RequestMapping(value = "/mfCusCreditChildContract/insert")
	public MfCusCreditChildContract insert(@RequestBody MfCusCreditChildContract mfCusCreditChildContract) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditChildContract/delete")
	public void delete(@RequestBody MfCusCreditChildContract mfCusCreditChildContract) throws Exception;
	

	@RequestMapping(value = "/mfCusCreditChildContract/update")
	public void update(@RequestBody MfCusCreditChildContract mfCusCreditChildContract) throws Exception;
	
	
	@RequestMapping(value = "/mfCusCreditChildContract/getById")
	public MfCusCreditChildContract getById(@RequestBody MfCusCreditChildContract mfCusCreditChildContract) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditChildContract/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCusCreditChildContract") MfCusCreditChildContract mfCusCreditChildContract) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditChildContract/getMfCusCreditChildContractList")
	public List<MfCusCreditChildContract> getMfCusCreditChildContractList(@RequestBody  MfCusCreditChildContract mfCusCreditChildContract) throws Exception;
	/**
	 * 
	 * 方法描述：  验证资金机构的调整的项目额度不能小于融资业务中已占用的项目额度
	 * @param creditAppId
	 * @param cusNoFund
	 * @param appAmt
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年5月3日 上午11:41:17
	 */
	@RequestMapping(value = "/mfCusCreditChildContract/checkCusNoFundAdjustAmt")
	public Map<String, Object> checkCusNoFundAdjustAmt(@RequestParam("creditAppId") String creditAppId,@RequestParam("cusNoFund") String cusNoFund,@RequestParam("adjustAmt") Double adjustAmt) throws Exception;
	/**
	 * 
	 * 方法描述： 资金机构项目合同签约
	 * @param mfCusCreditChildContract
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2018年5月11日 上午10:33:59
	 */
	@RequestMapping(value = "/mfCusCreditChildContract/childPactSign")
	public void childPactSign(@RequestBody MfCusCreditChildContract mfCusCreditChildContract) throws Exception;
	/**
	 * 
	 * 方法描述： 插入合同信息
	 * @param mfCusCreditChildContract
	 * @return
	 * @throws Exception
	 * MfCusCreditChildContract
	 * @author 沈浩兵
	 * @date 2018年5月30日 下午7:22:30
	 */
	@RequestMapping(value = "/mfCusCreditChildContract/insertPactInfo")
	public MfCusCreditChildContract insertPactInfo(@RequestBody MfCusCreditChildContract mfCusCreditChildContract) throws Exception;
	
}
