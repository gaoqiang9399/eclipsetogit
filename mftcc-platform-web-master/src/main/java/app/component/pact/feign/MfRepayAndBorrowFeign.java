package  app.component.pact.feign;

import app.util.toolkit.Ipage;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.pact.entity.MfRepayAndBorrow;

/**
* Title: MfRepayAndBorrowBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Jul 13 09:59:18 CST 2018
**/

@FeignClient("mftcc-platform-factor")
public interface MfRepayAndBorrowFeign {
	
	@RequestMapping(value = "/mfRepayAndBorrow/insert")
	public void insert(MfRepayAndBorrow mfRepayAndBorrow) throws Exception;
	
	@RequestMapping(value = "/mfRepayAndBorrow/delete")
	public void delete(MfRepayAndBorrow mfRepayAndBorrow) throws Exception;
	
	@RequestMapping(value = "/mfRepayAndBorrow/update")
	public void update(MfRepayAndBorrow mfRepayAndBorrow) throws Exception;
	
	@RequestMapping(value = "/mfRepayAndBorrow/getById")
	public MfRepayAndBorrow getById(MfRepayAndBorrow mfRepayAndBorrow) throws Exception;
	
	@RequestMapping(value = "/mfRepayAndBorrow/findByPage")
	public Ipage findByPage(Ipage ipage) throws Exception;
	
}
