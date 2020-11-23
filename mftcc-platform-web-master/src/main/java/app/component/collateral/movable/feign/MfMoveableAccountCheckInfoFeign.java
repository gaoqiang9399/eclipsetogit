package  app.component.collateral.movable.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.collateral.movable.entity.MfMoveableAccountCheckInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfMoveableAccountCheckInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Jun 16 18:03:18 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveableAccountCheckInfoFeign {
	
	@RequestMapping(value = "/mfMoveableAccountCheckInfo/insert")
	public void insert(@RequestBody MfMoveableAccountCheckInfo mfMoveableAccountCheckInfo) throws Exception;
	
	@RequestMapping(value = "/mfMoveableAccountCheckInfo/delete")
	public void delete(@RequestBody MfMoveableAccountCheckInfo mfMoveableAccountCheckInfo) throws Exception;
	
	@RequestMapping(value = "/mfMoveableAccountCheckInfo/update")
	public void update(@RequestBody MfMoveableAccountCheckInfo mfMoveableAccountCheckInfo) throws Exception;
	
	@RequestMapping(value = "/mfMoveableAccountCheckInfo/getById")
	public MfMoveableAccountCheckInfo getById(@RequestBody MfMoveableAccountCheckInfo mfMoveableAccountCheckInfo) throws Exception;
	
	@RequestMapping(value = "/mfMoveableAccountCheckInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
