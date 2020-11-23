package  app.component.collateral.movable.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.movable.entity.MfMoveablePatrolInvenApproHis;
import app.util.toolkit.Ipage;

/**
* Title: MfMoveablePatrolInvenApproHisBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 13 20:29:52 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveablePatrolInvenApproHisFeign {
	
	@RequestMapping(value = "/mfMoveablePatrolInvenApproHis/insert")
	public void insert(@RequestBody MfMoveablePatrolInvenApproHis mfMoveablePatrolInvenApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveablePatrolInvenApproHis/delete")
	public void delete(@RequestBody MfMoveablePatrolInvenApproHis mfMoveablePatrolInvenApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveablePatrolInvenApproHis/update")
	public void update(@RequestBody MfMoveablePatrolInvenApproHis mfMoveablePatrolInvenApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveablePatrolInvenApproHis/getById")
	public MfMoveablePatrolInvenApproHis getById(@RequestBody MfMoveablePatrolInvenApproHis mfMoveablePatrolInvenApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveablePatrolInvenApproHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfMoveablePatrolInvenApproHis") MfMoveablePatrolInvenApproHis mfMoveablePatrolInvenApproHis) throws Exception;
	
}
