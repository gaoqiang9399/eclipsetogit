package app.component.oa.archive.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.oa.archive.entity.MfOaArchivesRewards;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaArchivesRewardsFeign {
	@RequestMapping("/MfOaArchivesRewards/insert")
	public void insert(@RequestBody MfOaArchivesRewards mfOaArchivesRewards) throws Exception;

	@RequestMapping("/MfOaArchivesRewards/delete")
	public void delete(@RequestBody MfOaArchivesRewards mfOaArchivesRewards) throws Exception;

	@RequestMapping("/MfOaArchivesRewards/update")
	public void update(@RequestBody MfOaArchivesRewards mfOaArchivesRewards) throws Exception;

	@RequestMapping("/MfOaArchivesRewards/getById")
	public MfOaArchivesRewards getById(@RequestBody MfOaArchivesRewards mfOaArchivesRewards) throws Exception;

	@RequestMapping("/MfOaArchivesRewards/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfOaArchivesRewards/getByBaseId")
	public List<MfOaArchivesRewards> getByBaseId(@RequestBody MfOaArchivesRewards mfOaArchivesRewards);

}
