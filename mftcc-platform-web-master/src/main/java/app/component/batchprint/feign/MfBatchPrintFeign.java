package app.component.batchprint.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.batchprint.MfUseMoneyQuery;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfBatchPrintFeign {
	@RequestMapping(value = "/batchprint/useMoney/findByPage")
	public Ipage useMoneyFindByPage(@RequestBody Ipage ipg) throws Exception;
	
	@RequestMapping(value = "/batchprint/useMoney/getUseMoneyByFincId")
	public MfUseMoneyQuery getUseMoneyByFincId(MfUseMoneyQuery mfUseMoneyQuery) throws Exception;

}
