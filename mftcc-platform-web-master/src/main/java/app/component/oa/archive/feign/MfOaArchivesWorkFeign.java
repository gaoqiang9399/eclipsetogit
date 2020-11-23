package app.component.oa.archive.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.oa.archive.entity.MfOaArchivesWork;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaArchivesWorkFeign {
	@RequestMapping("/MfOaArchivesWork/insert")
	public void insert(@RequestBody MfOaArchivesWork mfOaArchivesWork) throws Exception;

	@RequestMapping("/MfOaArchivesWork/delete")
	public void delete(@RequestBody MfOaArchivesWork mfOaArchivesWork) throws Exception;

	@RequestMapping("/MfOaArchivesWork/update")
	public void update(@RequestBody MfOaArchivesWork mfOaArchivesWork) throws Exception;

	@RequestMapping("/MfOaArchivesWork/getById")
	public MfOaArchivesWork getById(@RequestBody MfOaArchivesWork mfOaArchivesWork) throws Exception;

	@RequestMapping("/MfOaArchivesWork/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfOaArchivesWork/getByBaseId")
	public List<MfOaArchivesWork> getByBaseId(@RequestBody MfOaArchivesWork mfOaArchivesWork);

}
