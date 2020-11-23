package app.component.oa.archive.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.oa.archive.entity.MfOaArchivesEducation;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaArchivesEducationFeign {
	@RequestMapping("/MfOaArchivesEducation/insert")
	public void insert(@RequestBody MfOaArchivesEducation mfOaArchivesEducation) throws Exception;

	@RequestMapping("/MfOaArchivesEducation/delete")
	public void delete(@RequestBody MfOaArchivesEducation mfOaArchivesEducation) throws Exception;

	@RequestMapping("/MfOaArchivesEducation/update")
	public void update(@RequestBody MfOaArchivesEducation mfOaArchivesEducation) throws Exception;

	@RequestMapping("/MfOaArchivesEducation/getById")
	public MfOaArchivesEducation getById(@RequestBody MfOaArchivesEducation mfOaArchivesEducation) throws Exception;

	@RequestMapping("/MfOaArchivesEducation/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfOaArchivesEducation/getByBaseId")
	public List<MfOaArchivesEducation> getByBaseId(@RequestBody MfOaArchivesEducation mfOaArchivesEducation)
			throws Exception;
}
