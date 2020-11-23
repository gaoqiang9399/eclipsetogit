package  app.component.cus.whitename.feign;

import app.component.cus.whitename.entity.MfCusWhitename;
import app.util.toolkit.Ipage;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


/**
* Title: MfCusWhitenameBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed May 02 14:12:41 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusWhitenameFeign {
	@RequestMapping(value = "/mfCusWhitename/insert")
	public void insert(@RequestBody MfCusWhitename mfCusWhitename) throws Exception;
	
	@RequestMapping(value = "/mfCusWhitename/delete")
	public void delete(@RequestBody MfCusWhitename mfCusWhitename) throws Exception;
	
	@RequestMapping(value = "/mfCusWhitename/update")
	public void update(@RequestBody MfCusWhitename mfCusWhitename) throws Exception;
	
	@RequestMapping(value = "/mfCusWhitename/getById")
	public MfCusWhitename getById(@RequestBody MfCusWhitename mfCusWhitename) throws Exception;
	
	@RequestMapping(value = "/mfCusWhitename/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfCusWhitename/getByIdNumAndCusType")
	public MfCusWhitename getByIdNumAndCusType(@RequestBody MfCusWhitename mfCusWhitename) throws Exception;

	@RequestMapping(value = "/mfCusWhitename/getByIdNumAndCusTypeExistsId")
	public MfCusWhitename getByIdNumAndCusTypeExistsId(@RequestBody MfCusWhitename mfCusWhitename) throws Exception;
	
	@RequestMapping(value = "/mfCusWhitename/getByTel")
	public MfCusWhitename getByTel(@RequestBody MfCusWhitename mfCusWhitename) throws Exception;
	
	
	
}
