package app.component.oa.entrymanagement.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.oa.entrymanagement.entity.MfOaEntryManagement;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaEntryManagementFeign {
	@RequestMapping("/MfOaEntryManagement/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfOaEntryManagement/insert")
	public void insert(@RequestBody MfOaEntryManagement mfOaEntryManagement) throws Exception;

	@RequestMapping("/MfOaEntryManagement/submitEntryApplyProcess")
	public MfOaEntryManagement submitEntryApplyProcess(@RequestBody MfOaEntryManagement mfOaEntryManagement)
			throws Exception;

	@RequestMapping("/MfOaEntryManagement/getById")
	public MfOaEntryManagement getById(@RequestBody MfOaEntryManagement mfOaEntryManagement) throws Exception;

	@RequestMapping("/MfOaEntryManagement/delete")
	public void delete(@RequestBody MfOaEntryManagement mfOaEntryManagement) throws Exception;

	@RequestMapping("/MfOaEntryManagement/update")
	public void update(@RequestBody MfOaEntryManagement mfOaEntryManagement) throws Exception;

	@RequestMapping("/MfOaEntryManagement/getCount")
	public int getCount() throws Exception;

	@RequestMapping("/MfOaEntryManagement/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("transition") String transition,@RequestParam("nextUser") String nextUser,
			@RequestBody Map<String, Object> formDataMap) throws Exception;
}
