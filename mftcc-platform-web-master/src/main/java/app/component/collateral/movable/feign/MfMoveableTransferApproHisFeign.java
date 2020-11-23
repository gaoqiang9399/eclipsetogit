package  app.component.collateral.movable.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.movable.entity.MfMoveableTransferApproHis;
import app.util.toolkit.Ipage;

/**
* Title: MfMoveableTransferApproHisBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Jun 09 16:34:57 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveableTransferApproHisFeign {
	
	@RequestMapping(value = "/mfMoveableTransferApproHis/insert")
	public void insert(@RequestBody MfMoveableTransferApproHis mfMoveableTransferApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveableTransferApproHis/delete")
	public void delete(@RequestBody MfMoveableTransferApproHis mfMoveableTransferApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveableTransferApproHis/update")
	public void update(@RequestBody MfMoveableTransferApproHis mfMoveableTransferApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveableTransferApproHis/getById")
	public MfMoveableTransferApproHis getById(@RequestBody MfMoveableTransferApproHis mfMoveableTransferApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveableTransferApproHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfMoveableTransferApproHis") MfMoveableTransferApproHis mfMoveableTransferApproHis) throws Exception;
	
}
