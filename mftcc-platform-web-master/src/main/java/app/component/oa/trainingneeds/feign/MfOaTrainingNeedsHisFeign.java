package app.component.oa.trainingneeds.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.oa.trainingneeds.entity.MfOaTrainingNeedsHis;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaTrainingNeedsHisFeign {
	@RequestMapping("/MfOaTrainingNeedsHis/insert")
	public void insert(@RequestBody MfOaTrainingNeedsHis mfOaTrainingNeedsHis) throws Exception;

	@RequestMapping("/MfOaTrainingNeedsHis/delete")
	public void delete(@RequestBody MfOaTrainingNeedsHis mfOaTrainingNeedsHis) throws Exception;

	@RequestMapping("/MfOaTrainingNeedsHis/update")
	public void update(@RequestBody MfOaTrainingNeedsHis mfOaTrainingNeedsHis) throws Exception;

	@RequestMapping("/MfOaTrainingNeedsHis/getById")
	public MfOaTrainingNeedsHis getById(@RequestBody MfOaTrainingNeedsHis mfOaTrainingNeedsHis) throws Exception;

	@RequestMapping("/MfOaTrainingNeedsHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}
