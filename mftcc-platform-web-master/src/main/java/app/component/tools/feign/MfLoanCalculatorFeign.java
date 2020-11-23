package app.component.tools.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** 
 * 描述：提前还款
 * @author 徐明伟
 * @datetime 2017-11-17 下午3:07:47
 * @editnote 
 *	
 */
@FeignClient("mftcc-platform-factor")
public interface MfLoanCalculatorFeign {
	/**
	 * 一次性还款
	 *
	 * @param dataMap
	 * @param resultMap
	 * @return
	 * @throws Exception
	 * @author 徐明伟
	 * @datetime 2017-11-21 上午11:36:09
	 * @editnote 
	 *
	 */
	@RequestMapping(value = "/mfLoanCalculator/getPreRepayActInt")
    public Map<String,Object> getPreRepayActInt(@RequestBody Map<String,Object> dataMap,@RequestParam("resultMap") Map<String,Object> resultMap) throws Exception;
    /**
     * 部分还款
     *
     * @param dataMap
     * @param resultMap
     * @return
     * @throws Exception
     * @author 徐明伟
     * @datetime 2017-11-21 上午11:40:47
     * @editnote 
     *
     */
	@RequestMapping(value = "/mfLoanCalculator/getPrePartRepay")
    public Map<String,Object> getPrePartRepay(@RequestBody Map<String,Object> dataMap,@RequestParam("resultMap") Map<String,Object> resultMap) throws Exception;
	
	@RequestMapping(value = "/mfLoanCalculator/calculate")
    public Map<String,Object> calculate(@RequestBody Map<String,Object> dataMap) throws Exception;

	@RequestMapping(value = "/mfLoanCalculator/getEndDateInfoMap")
	public Map<String,Object> getEndDateInfoMap(@RequestBody Map<String,String> parmMap) throws Exception;
}
