package app.tech.oscache.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.core.domain.screen.OptionsList;

@FeignClient("mftcc-platform-factor")
public interface CodeUtilsFeign {
	@RequestMapping("/codeUtils/getOpinionTypeList")
	public List<OptionsList> getOpinionTypeList(@RequestParam("activityType") String activityType, @RequestParam("taskCouldRollBack") String taskCouldRollBack, @RequestBody Map<String, String> opinionTypeMap) throws Exception;
}
