package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusBorrowerInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfCusBorrowerInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Feb 05 16:03:52 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusBorrowerInfoFeign {
	
	@RequestMapping("/mfCusBorrowerInfo/insert")
	public void insert(@RequestBody MfCusBorrowerInfo mfCusBorrowerInfo) throws Exception;
	
	@RequestMapping("/mfCusBorrowerInfo/delete")
	public void delete(@RequestBody MfCusBorrowerInfo mfCusBorrowerInfo) throws Exception;
	
	@RequestMapping("/mfCusBorrowerInfo/update")
	public void update(@RequestBody MfCusBorrowerInfo mfCusBorrowerInfo) throws Exception;
	
	@RequestMapping("/mfCusBorrowerInfo/getById")
	public MfCusBorrowerInfo getById(@RequestBody MfCusBorrowerInfo mfCusBorrowerInfo) throws Exception;
	
	@RequestMapping("/mfCusBorrowerInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCusBorrowerInfo/getAllBorrowerByCusNo")
	public List<MfCusBorrowerInfo> getAllBorrowerByCusNo(@RequestBody MfCusBorrowerInfo mfCusBorrowerInfo) throws Exception;
	
	@RequestMapping("/mfCusBorrowerInfo/getByCusNo")
	public List<MfCusBorrowerInfo> getByCusNo(@RequestBody MfCusBorrowerInfo mfCusBorrowerInfo) throws Exception;
	

}
