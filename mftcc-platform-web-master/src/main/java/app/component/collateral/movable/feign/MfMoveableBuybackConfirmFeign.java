package  app.component.collateral.movable.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.movable.entity.MfMoveableBuybackConfirm;
import app.util.toolkit.Ipage;

/**
* Title: MfMoveableBuybackConfirmBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 20 14:24:03 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveableBuybackConfirmFeign {
	
	@RequestMapping(value = "/mfMoveableBuybackConfirm/insert")
	public void insert(@RequestBody MfMoveableBuybackConfirm mfMoveableBuybackConfirm) throws Exception;
	
	@RequestMapping(value = "/mfMoveableBuybackConfirm/delete")
	public void delete(@RequestBody MfMoveableBuybackConfirm mfMoveableBuybackConfirm) throws Exception;
	
	@RequestMapping(value = "/mfMoveableBuybackConfirm/update")
	public void update(@RequestBody MfMoveableBuybackConfirm mfMoveableBuybackConfirm) throws Exception;
	
	@RequestMapping(value = "/mfMoveableBuybackConfirm/getById")
	public MfMoveableBuybackConfirm getById(@RequestBody MfMoveableBuybackConfirm mfMoveableBuybackConfirm) throws Exception;
	
	@RequestMapping(value = "/mfMoveableBuybackConfirm/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfMoveableBuybackConfirm") MfMoveableBuybackConfirm mfMoveableBuybackConfirm) throws Exception;
	
}
