package  app.component.auth.feign;



import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 类名： MfCusCreditInterfaceFeign
 * 描述：
 * @author FuChen
 * @date 2018年6月1日 上午11:12:47
 *
 *
 */

@FeignClient("mftcc-platform-fund-channel")
public interface MfCusCreditInterfaceFeign {
	
	/**
	 * 查询授信额度
	 * @param json 客户授信参数
	 * @throws Exception TODO
	 */
	@RequestMapping(value = "/mfFundChannel/queryCreditInfo")
	public  String queryCreditInfo(@RequestParam("projectName") String projectName,@RequestParam("json") String json) throws Exception;
}
