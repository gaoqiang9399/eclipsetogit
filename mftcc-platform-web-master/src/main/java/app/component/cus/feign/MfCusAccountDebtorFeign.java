package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusAccountDebtor;
import app.util.toolkit.Ipage;

/**
* Title: MfCusAccountDebtorBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jul 17 12:15:38 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusAccountDebtorFeign {
	
	@RequestMapping(value = "/mfCusAccountDebtor/insert")
	public void insert(@RequestBody MfCusAccountDebtor mfCusAccountDebtor) throws Exception;
	
	@RequestMapping(value = "/mfCusAccountDebtor/delete")
	public void delete(@RequestBody MfCusAccountDebtor mfCusAccountDebtor) throws Exception;
	
	@RequestMapping(value = "/mfCusAccountDebtor/update")
	public void update(@RequestBody MfCusAccountDebtor mfCusAccountDebtor) throws Exception;
	
	@RequestMapping(value = "/mfCusAccountDebtor/getById")
	public MfCusAccountDebtor getById(@RequestBody MfCusAccountDebtor mfCusAccountDebtor) throws Exception;
	
	@RequestMapping(value = "/mfCusAccountDebtor/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfCusAccountDebtor/getAll")
	public List<MfCusAccountDebtor> getAll(@RequestBody MfCusAccountDebtor mfCusAccountDebtor) throws Exception ;
	
}
