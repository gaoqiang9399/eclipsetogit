package app.component.vouafter.feign;

import app.component.vouafter.entity.MfVouAfterTrack;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfVouAfterTrackFeign {
	@RequestMapping("/MfVouAfterTrack/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfVouAfterTrack/insert")
	public void insert(@RequestBody MfVouAfterTrack mfVouAfterTrack) throws Exception;

	@RequestMapping("/MfVouAfterTrack/submitProcess")
	public MfVouAfterTrack submitProcess(@RequestBody MfVouAfterTrack mfVouAfterTrack) throws Exception;

	@RequestMapping("/MfVouAfterTrack/getById")
	public MfVouAfterTrack getById(@RequestBody MfVouAfterTrack mfVouAfterTrack) throws Exception;

	@RequestMapping("/MfVouAfterTrack/delete")
	public void delete(@RequestBody MfVouAfterTrack mfVouAfterTrack) throws Exception;

	@RequestMapping("/MfVouAfterTrack/update")
	public void update(@RequestBody MfVouAfterTrack mfVouAfterTrack) throws Exception;

	@RequestMapping("/MfVouAfterTrack/getCount")
	public int getCount() throws Exception;

	@RequestMapping("/MfVouAfterTrack/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("transition") String transition, @RequestParam("nextUser") String nextUser,
                           @RequestBody Map<String, Object> formDataMap) throws Exception;

	@RequestMapping("/MfVouAfterTrack/getMultiBusList")
    List<MfVouAfterTrack> getMultiBusList(@RequestBody MfVouAfterTrack mfVouAfterTrack)throws Exception;
}
