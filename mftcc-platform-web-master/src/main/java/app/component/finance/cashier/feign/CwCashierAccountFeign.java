package app.component.finance.cashier.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.cashier.entity.CwCashierAccount;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;

/**
 * Title: CwCashierAccountBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Mar 27 16:39:02 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwCashierAccountFeign {

	@RequestMapping(value = "/cwCashierAccount/insert", method = RequestMethod.POST)
	public R insert(@RequestBody CwCashierAccount cwCashierAccount,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwCashierAccount/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwCashierAccount cwCashierAccount,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwCashierAccount/update", method = RequestMethod.POST)
	public void update(@RequestBody CwCashierAccount cwCashierAccount,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwCashierAccount/getById", method = RequestMethod.POST)
	public CwCashierAccount getById(@RequestBody CwCashierAccount cwCashierAccount,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwCashierAccount/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("finBooks") String finBooks) throws Exception;

}
