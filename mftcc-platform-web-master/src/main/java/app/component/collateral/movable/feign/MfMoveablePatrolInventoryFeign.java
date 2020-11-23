package  app.component.collateral.movable.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.movable.entity.MfMoveablePatrolInventory;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfMoveablePatrolInventoryBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 13 20:28:17 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveablePatrolInventoryFeign {
	
	@RequestMapping(value = "/mfMoveablePatrolInventory/insert")
	public MfMoveablePatrolInventory insert(@RequestBody MfMoveablePatrolInventory mfMoveablePatrolInventory) throws Exception;
	
	@RequestMapping(value = "/mfMoveablePatrolInventory/delete")
	public void delete(@RequestBody MfMoveablePatrolInventory mfMoveablePatrolInventory) throws Exception;
	
	@RequestMapping(value = "/mfMoveablePatrolInventory/update")
	public void update(@RequestBody MfMoveablePatrolInventory mfMoveablePatrolInventory) throws Exception;
	
	@RequestMapping(value = "/mfMoveablePatrolInventory/getById")
	public MfMoveablePatrolInventory getById(@RequestBody MfMoveablePatrolInventory mfMoveablePatrolInventory) throws Exception;
	
	@RequestMapping(value = "/mfMoveablePatrolInventory/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfMoveablePatrolInventory") MfMoveablePatrolInventory mfMoveablePatrolInventory) throws Exception;
	/**
	 * 
	 * 方法描述： 巡库审批意见保存提交
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * Result
	 * @author 沈浩兵
	 * @date 2017-6-14 上午9:56:49
	 */
	@RequestMapping(value = "/mfMoveablePatrolInventory/doCommit")
	public Result doCommit(@RequestBody Map<String,Object> dataMap) throws Exception;
}
