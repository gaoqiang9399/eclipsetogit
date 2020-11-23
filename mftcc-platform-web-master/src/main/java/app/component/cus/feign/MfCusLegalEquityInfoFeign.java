package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusLegalEquityInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfCusLegalEquityInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jun 03 15:20:52 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusLegalEquityInfoFeign {
	
	@RequestMapping("/mfCusLegalEquityInfo/insert")
	public void insert(@RequestBody MfCusLegalEquityInfo mfCusLegalEquityInfo) throws Exception;
	
	@RequestMapping("/mfCusLegalEquityInfo/insertForApp")
	public void insertForApp(@RequestBody MfCusLegalEquityInfo mfCusLegalEquityInfo) throws Exception;
	
	@RequestMapping("/mfCusLegalEquityInfo/delete")
	public void delete(@RequestBody MfCusLegalEquityInfo mfCusLegalEquityInfo) throws Exception;
	
	@RequestMapping("/mfCusLegalEquityInfo/update")
	public void update(@RequestBody MfCusLegalEquityInfo mfCusLegalEquityInfo) throws Exception;
	
	@RequestMapping("/mfCusLegalEquityInfo/getById")
	public MfCusLegalEquityInfo getById(@RequestBody MfCusLegalEquityInfo mfCusLegalEquityInfo) throws Exception;
	
	@RequestMapping("/mfCusLegalEquityInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCusLegalEquityInfo/findByEntity")
	public List<MfCusLegalEquityInfo> findByEntity(@RequestBody MfCusLegalEquityInfo mfCusLegalEquityInfo) throws Exception;
	
}
