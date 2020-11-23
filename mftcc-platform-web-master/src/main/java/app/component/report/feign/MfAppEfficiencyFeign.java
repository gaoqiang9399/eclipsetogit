package app.component.report.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.report.entity.MfAppEfficiencyRepBean;

@FeignClient("mftcc-platform-factor")
public interface MfAppEfficiencyFeign {

	@RequestMapping(value = "/mfAppEfficiency/getAppList")
	public  List<MfAppEfficiencyRepBean>getAppList(@RequestBody Map<String, String>pMap)throws Exception;
}
