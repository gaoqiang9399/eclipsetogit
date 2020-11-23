package  app.component.collateral.movable.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.movable.entity.MfMoveableCheckInventoryInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfMoveableCheckInventoryInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jun 08 15:08:07 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveableCheckInventoryInfoFeign {
	
	@RequestMapping(value = "/mfMoveableCheckInventoryInfo/insertBus")
	public void insertBus(@RequestBody MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo,@RequestParam("appId") String appId) throws Exception;
	
	@RequestMapping(value = "/mfMoveableCheckInventoryInfo/insert")
	public void insert(@RequestBody MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo,@RequestParam("appId") String appId) throws Exception;
	
	@RequestMapping(value = "/mfMoveableCheckInventoryInfo/delete")
	public void delete(@RequestBody MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo) throws Exception;
	
	@RequestMapping(value = "/mfMoveableCheckInventoryInfo/update")
	public void update(@RequestBody MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo) throws Exception;
	
	@RequestMapping(value = "/mfMoveableCheckInventoryInfo/getById")
	public MfMoveableCheckInventoryInfo getById(@RequestBody MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo) throws Exception;
	
	@RequestMapping(value = "/mfMoveableCheckInventoryInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfMoveableCheckInventoryInfo") MfMoveableCheckInventoryInfo mfMoveableCheckInventoryInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 如果业务流程中跳过担保证明登记，则直接提交到下一个业务节点
	 * @param appId
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-7-31 下午2:08:39
	 */
	@RequestMapping(value = "/mfMoveableCheckInventoryInfo/submitBussProcess")
	public void submitBussProcess(@RequestParam("appId") String appId,@RequestParam("regNo") String regNo) throws Exception;
	
}
