package app.component.finance.othreport.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.othreport.entity.ComBalanceBean;

@FeignClient("mftcc-platform-fiscal")
public interface ComBalanceFeign {
	/**
	 * 查询科目余额表需要的数据
	 * @param paramp
	 * @return
	 */
	@RequestMapping(value = "/comBalance/getComBalanceData", method = RequestMethod.POST)
	public List<ComBalanceBean> getComBalanceData(@RequestBody Map<String, String> paramp,@RequestParam("finBooks") String finBooks) throws Exception;
	
}
