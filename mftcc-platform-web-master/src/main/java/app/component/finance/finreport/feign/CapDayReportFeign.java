package app.component.finance.finreport.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.finreport.entity.CapDayReportBean;

/**
 * 现金日记账业务接口
 * 
 * @author Yanght
 *
 */
@FeignClient("mftcc-platform-fiscal")
public interface CapDayReportFeign {

	/**
	 * 方法描述： 获取资金日报表数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 *             List<CapDayReportBean>
	 * @author Javelin
	 * @date 2017-5-20 下午5:20:08
	 */
	@RequestMapping(value = "/capDayReport/getCapDayReportData", method = RequestMethod.POST)
	public List<CapDayReportBean> getCapDayReportData(@RequestBody Map<String, String> paramMap,@RequestParam("finBooks") String finBooks) throws Exception;
}
