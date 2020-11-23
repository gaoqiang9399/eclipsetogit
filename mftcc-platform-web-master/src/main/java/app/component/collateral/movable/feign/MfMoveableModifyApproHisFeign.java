package  app.component.collateral.movable.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.movable.entity.MfMoveableModifyApproHis;
import app.util.toolkit.Ipage;

/**
* Title: MfMoveableModifyApproHisBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Jun 12 20:27:07 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveableModifyApproHisFeign {
	
	@RequestMapping(value = "/mfMoveableModifyApproHis/insert")
	public void insert(@RequestBody MfMoveableModifyApproHis mfMoveableModifyApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveableModifyApproHis/delete")
	public void delete(@RequestBody MfMoveableModifyApproHis mfMoveableModifyApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveableModifyApproHis/update")
	public void update(@RequestBody MfMoveableModifyApproHis mfMoveableModifyApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveableModifyApproHis/getById")
	public MfMoveableModifyApproHis getById(@RequestBody MfMoveableModifyApproHis mfMoveableModifyApproHis) throws Exception;
	
	@RequestMapping(value = "/mfMoveableModifyApproHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfMoveableModifyApproHis") MfMoveableModifyApproHis mfMoveableModifyApproHis) throws Exception;
	
}
