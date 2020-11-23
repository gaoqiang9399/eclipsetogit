package app.component.finance.othreport.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.othreport.entity.DetailAccount;

@FeignClient("mftcc-platform-fiscal")
public interface DetailAccountFeign {
	/**
	 * 方法描述： 获取明细分类账报表数据
	 * 
	 * @param parmMap
	 * @return
	 * @throws Exception
	 *             List<DetailAccount>
	 * @author Javelin
	 * @date 2017-2-23 下午3:18:56
	 */
	@RequestMapping(value = "/detailAccount/getSeparateAccountData", method = RequestMethod.POST)
	public List<DetailAccount> getSeparateAccountData(@RequestBody Map<String, String> parmMap,@RequestParam("finBooks") String finBooks) throws Exception;
}
