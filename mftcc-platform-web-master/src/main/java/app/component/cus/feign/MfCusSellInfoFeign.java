package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusSellInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfCusSellInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Mar 13 10:12:58 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusSellInfoFeign {
	
	@RequestMapping("/mfCusSellInfo/insert")
	public void insert(@RequestBody MfCusSellInfo mfCusSellInfo) throws Exception;
	
	@RequestMapping("/mfCusSellInfo/delete")
	public void delete(@RequestBody MfCusSellInfo mfCusSellInfo) throws Exception;
	
	@RequestMapping("/mfCusSellInfo/update")
	public void update(@RequestBody MfCusSellInfo mfCusSellInfo) throws Exception;
	
	@RequestMapping("/mfCusSellInfo/getById")
	public MfCusSellInfo getById(@RequestBody MfCusSellInfo mfCusSellInfo) throws Exception;
	
	@RequestMapping("/mfCusSellInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
