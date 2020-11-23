package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusLegalMember;
import app.util.toolkit.Ipage;

/**
* Title: MfCusLegalMemberBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Jun 01 09:37:30 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusLegalMemberFeign {
	
	@RequestMapping("/mfCusLegalMember/insert")
	public void insert(@RequestBody MfCusLegalMember mfCusLegalMember) throws Exception;
	
	@RequestMapping("/mfCusLegalMember/delete")
	public void delete(@RequestBody MfCusLegalMember mfCusLegalMember) throws Exception;
	
	@RequestMapping("/mfCusLegalMember/update")
	public void update(@RequestBody MfCusLegalMember mfCusLegalMember) throws Exception;
	
	@RequestMapping("/mfCusLegalMember/getById")
	public MfCusLegalMember getById(@RequestBody MfCusLegalMember mfCusLegalMember) throws Exception;
	
	@RequestMapping("/mfCusLegalMember/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
