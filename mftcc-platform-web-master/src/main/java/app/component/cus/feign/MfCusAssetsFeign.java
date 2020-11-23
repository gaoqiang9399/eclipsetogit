package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusAssets;
import app.util.toolkit.Ipage;

/**
* Title: MfCusAssetsBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue May 31 15:40:17 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusAssetsFeign {
	
	@RequestMapping(value = "/mfCusAssets/insert")
	public void insert(@RequestBody MfCusAssets mfCusAssets) throws Exception;
	
	@RequestMapping(value = "/mfCusAssets/insertForApp")
	public MfCusAssets insertForApp(@RequestBody MfCusAssets mfCusAssets) throws Exception;
	
	@RequestMapping(value = "/mfCusAssets/delete")
	public void delete(@RequestBody MfCusAssets mfCusAssets) throws Exception;
	
	@RequestMapping(value = "/mfCusAssets/update")
	public void update(@RequestBody MfCusAssets mfCusAssets) throws Exception;
	
	@RequestMapping(value = "/mfCusAssets/getById")
	public MfCusAssets getById(@RequestBody MfCusAssets mfCusAssets) throws Exception;
	
	@RequestMapping(value = "/mfCusAssets/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
