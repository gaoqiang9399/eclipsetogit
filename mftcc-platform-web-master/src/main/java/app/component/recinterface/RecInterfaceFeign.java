package app.component.recinterface;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.rec.entity.RecallBase;

@FeignClient("mftcc-platform-factor")
public interface RecInterfaceFeign {
	@RequestMapping(value = "/recInterface/saveRecallBase")
	public void saveRecallBase(@RequestBody String conNo) throws Exception;

	@RequestMapping(value = "/recInterface/getRecallBaseList")
	public List<RecallBase> getRecallBaseList(@RequestBody RecallBase recallBase) throws Exception;

	@RequestMapping(value = "/recInterface/getRecallTimes")
	public List<RecallBase> getRecallTimes(@RequestBody String conNo) throws Exception;
}
