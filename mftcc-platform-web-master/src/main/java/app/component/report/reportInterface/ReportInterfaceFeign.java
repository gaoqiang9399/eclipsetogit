package app.component.report.reportInterface;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("mftcc-platform-factor")
public interface ReportInterfaceFeign {
	@RequestMapping(value = "/reportInterface/pieceJavaBeanSql")
	public  Map<String,Object> pieceJavaBeanSql(@RequestBody String opNo,@RequestParam("reportId") String reportId) throws Exception;
}
