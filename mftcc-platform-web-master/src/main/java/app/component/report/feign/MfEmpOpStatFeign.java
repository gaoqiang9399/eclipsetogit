package app.component.report.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.report.entity.MfEmpOpStatRepBean;

@FeignClient("mftcc-platform-factor")
public interface MfEmpOpStatFeign {
	@RequestMapping(value = "/mfEmpOpStat/getDateList")
	public List<MfEmpOpStatRepBean> getDateList(@RequestBody Map<String, String>map)throws Exception;

}
