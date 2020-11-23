package app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusCorpLoan;
import app.util.toolkit.Ipage;

/**
* Title: MfCusCorpLoanBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Jun 13 17:44:07 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusCorpLoanFeign {
	@RequestMapping(value="/mfCusCorpLoan/insert")
	public void insert(@RequestBody MfCusCorpLoan mfCusCorpLoan) throws Exception;
	@RequestMapping(value="/mfCusCorpLoan/delete")
	public void delete(@RequestBody MfCusCorpLoan mfCusCorpLoan) throws Exception;
	@RequestMapping(value="/mfCusCorpLoan/update")
	public void update(@RequestBody MfCusCorpLoan mfCusCorpLoan) throws Exception;
	@RequestMapping(value="/mfCusCorpLoan/getById")
	public MfCusCorpLoan getById(@RequestBody MfCusCorpLoan mfCusCorpLoan) throws Exception;
	@RequestMapping(value="/mfCusCorpLoan/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
