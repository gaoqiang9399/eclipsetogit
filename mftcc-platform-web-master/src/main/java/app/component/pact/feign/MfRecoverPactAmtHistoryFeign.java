package app.component.pact.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pact.entity.MfRecoverPactAmtHistory;
import app.util.toolkit.Ipage;

/**
 * Title: MfRecoverPactAmtHistoryBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sun Jul 23 15:21:36 CST 2017
 **/
@RequestMapping("/mfRecoverPactAmtHistory")
@FeignClient("mftcc-platform-factor")
public interface MfRecoverPactAmtHistoryFeign {

	@RequestMapping(value = "/mfRecoverPactAmtHistoryinsert")
	public void insert(@RequestBody MfRecoverPactAmtHistory mfRecoverPactAmtHistory) throws Exception;

	@RequestMapping(value = "/mfRecoverPactAmtHistorydelete")
	public void delete(@RequestBody MfRecoverPactAmtHistory mfRecoverPactAmtHistory) throws Exception;

	@RequestMapping(value = "/mfRecoverPactAmtHistoryupdate")
	public void update(@RequestBody MfRecoverPactAmtHistory mfRecoverPactAmtHistory) throws Exception;

	@RequestMapping(value = "/mfRecoverPactAmtHistorygetById")
	public MfRecoverPactAmtHistory getById(@RequestBody MfRecoverPactAmtHistory mfRecoverPactAmtHistory)
			throws Exception;

	@RequestMapping(value = "/mfRecoverPactAmtHistoryfindByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfRecoverPactAmtHistory") MfRecoverPactAmtHistory mfRecoverPactAmtHistory)
			throws Exception;

}
