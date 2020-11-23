package app.component.finance.othreport.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.othreport.entity.GeneralLedgerShow;
@FeignClient("mftcc-platform-fiscal")
public interface GeneralLedgerFeign {
	
	/**
	 * 方法描述： 获取总账报表数据
	 * @param map
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author Javelin
	 * @date 2017-2-22 下午2:33:17
	 */
	@RequestMapping(value = "/generalLedger/getGeneralLedgerData", method = RequestMethod.POST)
	public List<GeneralLedgerShow> getGeneralLedgerData(@RequestBody Map<String,String> map,@RequestParam("finBooks") String finBooks) throws Exception;
	
}
