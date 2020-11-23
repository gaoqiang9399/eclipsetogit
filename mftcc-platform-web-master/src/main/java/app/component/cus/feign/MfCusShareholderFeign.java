package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusShareholder;
import app.util.toolkit.Ipage;
/**
* Title: MfCusShareholderBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon May 23 11:31:15 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusShareholderFeign {
	
	@RequestMapping("/mfCusShareholder/insert")
	public MfCusShareholder insert(@RequestBody MfCusShareholder mfCusShareholder) throws Exception;
	
	@RequestMapping("/mfCusShareholder/insertForApp")
	public MfCusShareholder insertForApp(@RequestBody MfCusShareholder mfCusShareholder) throws Exception;
	
	@RequestMapping("/mfCusShareholder/delete")
	public void delete(@RequestBody MfCusShareholder mfCusShareholder) throws Exception;
	
	@RequestMapping("/mfCusShareholder/update")
	public void update(@RequestBody MfCusShareholder mfCusShareholder) throws Exception;
	
	@RequestMapping("/mfCusShareholder/getById")
	public MfCusShareholder getById(@RequestBody MfCusShareholder mfCusShareholder) throws Exception;
	
	@RequestMapping("/mfCusShareholder/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCusShareholder/findByPage2")
	public List<MfCusShareholder> findByPage2(@RequestBody MfCusShareholder mfCusShareholder) throws Exception;
	
	@RequestMapping("/mfCusShareholder/getByCusNo")
	public List<MfCusShareholder> getByCusNo(@RequestBody MfCusShareholder mfCusShareholder) throws Exception;
	
	@RequestMapping("/mfCusShareholder/getShareholderByCusNo")
	public double getShareholderByCusNo(@RequestBody MfCusShareholder mfCusShareholder) throws Exception;
}
