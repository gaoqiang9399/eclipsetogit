package  app.component.collateral.movable.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.movable.entity.MfMoveableLowestWorthAdjust;
import app.util.toolkit.Ipage;

/**
* Title: MfMoveableLowestWorthAdjustBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Jun 16 16:03:57 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveableLowestWorthAdjustFeign {
	
	@RequestMapping(value = "/mfMoveableLowestWorthAdjust/insert")
	public void insert(@RequestBody MfMoveableLowestWorthAdjust mfMoveableLowestWorthAdjust) throws Exception;
	
	@RequestMapping(value = "/mfMoveableLowestWorthAdjust/delete")
	public void delete(@RequestBody MfMoveableLowestWorthAdjust mfMoveableLowestWorthAdjust) throws Exception;
	
	@RequestMapping(value = "/mfMoveableLowestWorthAdjust/update")
	public void update(@RequestBody MfMoveableLowestWorthAdjust mfMoveableLowestWorthAdjust) throws Exception;
	
	@RequestMapping(value = "/mfMoveableLowestWorthAdjust/getById")
	public MfMoveableLowestWorthAdjust getById(@RequestBody MfMoveableLowestWorthAdjust mfMoveableLowestWorthAdjust) throws Exception;
	
	@RequestMapping(value = "/mfMoveableLowestWorthAdjust/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfMoveableLowestWorthAdjust") MfMoveableLowestWorthAdjust mfMoveableLowestWorthAdjust) throws Exception;
	/**
	 * 
	 * 方法描述： 初始化押品最低监管价值调整
	 * @param busPleId
	 * @param pledgeNo
	 * @return
	 * @throws Exception
	 * MfMoveableLowestWorthAdjust
	 * @author 沈浩兵
	 * @date 2017-6-16 下午4:17:13
	 */
	@RequestMapping(value = "/mfMoveableLowestWorthAdjust/initLowestWorthAdjust")
	public MfMoveableLowestWorthAdjust initLowestWorthAdjust(@RequestBody String busPleId,@RequestParam("pledgeNo") String pledgeNo) throws Exception;
	
}
