package  app.component.collateral.movable.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.movable.entity.MfMoveableCompensation;
import app.util.toolkit.Ipage;

/**
* Title: MfMoveableCompensationBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 13 18:53:52 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveableCompensationFeign {
	
	@RequestMapping(value = "/mfMoveableCompensation/insert")
	public void insert(@RequestBody MfMoveableCompensation mfMoveableCompensation) throws Exception;
	
	@RequestMapping(value = "/mfMoveableCompensation/delete")
	public void delete(@RequestBody MfMoveableCompensation mfMoveableCompensation) throws Exception;
	
	@RequestMapping(value = "/mfMoveableCompensation/update")
	public void update(@RequestBody MfMoveableCompensation mfMoveableCompensation) throws Exception;
	@RequestMapping(value = "/mfMoveableCompensation/input")
	public String  input(@RequestBody MfMoveableCompensation mfMoveableCompensation) throws Exception;
	
	@RequestMapping(value = "/mfMoveableCompensation/getById")
	public MfMoveableCompensation getById(@RequestBody MfMoveableCompensation mfMoveableCompensation) throws Exception;
	
	@RequestMapping(value = "/mfMoveableCompensation/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfMoveableCompensation") MfMoveableCompensation mfMoveableCompensation) throws Exception;
	
}
