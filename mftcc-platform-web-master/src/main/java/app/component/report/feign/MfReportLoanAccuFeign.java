package app.component.report.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.report.entity.MfLoanAccountBean;

@FeignClient("mftcc-platform-factor")
public interface MfReportLoanAccuFeign {

	/**
	 * 
	 * 方法描述：  贷款累放累收统计月报表数据列表
	 * @return
	 * List<MfLoanAccountBean>
	 * @author lwq
	 * @date 2017-7-17 下午5:58:37
	 */
	@RequestMapping(value = "/mfReportLoanAccu/getLoanAccuReportList")
	public List<MfLoanAccountBean> getLoanAccuReportList(@RequestBody Map<String,String> parmMap);
}
