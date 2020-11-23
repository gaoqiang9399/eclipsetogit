package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusFarmerEconoInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfCusFarmerEconoInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Aug 16 10:29:42 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusFarmerEconoInfoFeign {
	
	@RequestMapping("/mfCusFarmerEconoInfo/insert")
	public void insert(@RequestBody MfCusFarmerEconoInfo mfCusFarmerEconoInfo) throws Exception;
	
	@RequestMapping("/mfCusFarmerEconoInfo/insertForApp")
	public MfCusFarmerEconoInfo insertForApp(@RequestBody MfCusFarmerEconoInfo mfCusFarmerEconoInfo) throws Exception;
	
	@RequestMapping("/mfCusFarmerEconoInfo/delete")
	public void delete(@RequestBody MfCusFarmerEconoInfo mfCusFarmerEconoInfo) throws Exception;
	
	@RequestMapping("/mfCusFarmerEconoInfo/update")
	public void update(@RequestBody MfCusFarmerEconoInfo mfCusFarmerEconoInfo) throws Exception;
	
	@RequestMapping("/mfCusFarmerEconoInfo/getById")
	public MfCusFarmerEconoInfo getById(@RequestBody MfCusFarmerEconoInfo mfCusFarmerEconoInfo) throws Exception;
	
	@RequestMapping("/mfCusFarmerEconoInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
