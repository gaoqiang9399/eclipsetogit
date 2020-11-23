package app.component.oa.archive.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.oa.archive.entity.MfOaArchivesFamily;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaArchivesFamilyFeign {
	@RequestMapping("/MfOaArchivesFamily/insert")
	public void insert(@RequestBody MfOaArchivesFamily mfOaArchivesFamily) throws Exception;

	@RequestMapping("/MfOaArchivesFamily/delete")
	public void delete(@RequestBody MfOaArchivesFamily mfOaArchivesFamily) throws Exception;

	@RequestMapping("/MfOaArchivesFamily/update")
	public void update(@RequestBody MfOaArchivesFamily mfOaArchivesFamily) throws Exception;

	@RequestMapping("/MfOaArchivesFamily/getById")
	public MfOaArchivesFamily getById(@RequestBody MfOaArchivesFamily mfOaArchivesFamily) throws Exception;

	@RequestMapping("/MfOaArchivesFamily/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfOaArchivesFamily/getByBaseId")
	public List<MfOaArchivesFamily> getByBaseId(@RequestBody MfOaArchivesFamily mfOaArchivesFamily) throws Exception;
}
