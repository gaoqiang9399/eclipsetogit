package  app.component.collateral.movable.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.movable.entity.MfMoveableBuybackApproHis;
import app.util.toolkit.Ipage;

/**
* Title: MfMoveableBuybackApproHisBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 20 09:01:37 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveableBuybackApproHisFeign {
	
	@RequestMapping(value = "/mfMoveableBuybackApproHis/insert")
	public void insert(@RequestBody MfMoveableBuybackApproHis mfMoveableBuybackApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveableBuybackApproHis/delete")
	public void delete(@RequestBody MfMoveableBuybackApproHis mfMoveableBuybackApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveableBuybackApproHis/update")
	public void update(@RequestBody MfMoveableBuybackApproHis mfMoveableBuybackApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveableBuybackApproHis/getById")
	public MfMoveableBuybackApproHis getById(@RequestBody MfMoveableBuybackApproHis mfMoveableBuybackApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveableBuybackApproHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfMoveableBuybackApproHis") MfMoveableBuybackApproHis mfMoveableBuybackApproHis) throws Exception;
	
}
