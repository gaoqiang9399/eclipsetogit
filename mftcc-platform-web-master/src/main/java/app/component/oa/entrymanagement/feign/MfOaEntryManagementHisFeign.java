package app.component.oa.entrymanagement.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.oa.entrymanagement.entity.MfOaEntryManagementHis;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaEntryManagementHisFeign {
	@RequestMapping("/MfOaEntryManagementHis/insert")
	public void insert(@RequestBody MfOaEntryManagementHis mfOaEntryManagementHis) throws Exception;

	@RequestMapping("/MfOaEntryManagementHis/delete")
	public void delete(@RequestBody MfOaEntryManagementHis mfOaEntryManagementHis) throws Exception;

	@RequestMapping("/MfOaEntryManagementHis/update")
	public void update(@RequestBody MfOaEntryManagementHis mfOaEntryManagementHis) throws Exception;

	@RequestMapping("/MfOaEntryManagementHis/getById")
	public MfOaEntryManagementHis getById(@RequestBody MfOaEntryManagementHis mfOaEntryManagementHis) throws Exception;

	@RequestMapping("/MfOaEntryManagementHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}
