package app.component.oa.becomequalified.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.oa.becomequalified.entity.MfOaBecomeQualified;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaBecomeQualifiedFeign {
	@RequestMapping("/MfOaBecomeQualified/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfOaBecomeQualified/insert")
	public void insert(@RequestBody MfOaBecomeQualified mfOaBecomeQualified) throws Exception;

	@RequestMapping("/MfOaBecomeQualified/submitProcess")
	public MfOaBecomeQualified submitProcess(@RequestBody MfOaBecomeQualified mfOaBecomeQualified) throws Exception;

	@RequestMapping("/MfOaBecomeQualified/getById")
	public MfOaBecomeQualified getById(@RequestBody MfOaBecomeQualified mfOaBecomeQualified) throws Exception;

	@RequestMapping("/MfOaBecomeQualified/delete")
	public void delete(@RequestBody MfOaBecomeQualified mfOaBecomeQualified) throws Exception;

	@RequestMapping("/MfOaBecomeQualified/update")
	public void update(@RequestBody MfOaBecomeQualified mfOaBecomeQualified) throws Exception;

	@RequestMapping("/MfOaBecomeQualified/getCount")
	public int getCount() throws Exception;

	@RequestMapping("/MfOaBecomeQualified/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("transition") String transition,@RequestParam("nextUser") String nextUser,
			 @RequestBody Map<String, Object> formDataMap) throws Exception;

	@RequestMapping("/MfOaBecomeQualified/getIfShowAddBtn")
	public String getIfShowAddBtn(String opNo) throws Exception;
}
