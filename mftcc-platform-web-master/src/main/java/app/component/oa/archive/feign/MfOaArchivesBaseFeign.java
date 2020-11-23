package app.component.oa.archive.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.core.domain.screen.OptionsList;

import app.component.oa.archive.entity.MfOaArchivesBase;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaArchivesBaseFeign {
	@RequestMapping("/MfOaArchivesBase/insert")
	public void insert(@RequestBody MfOaArchivesBase mfOaArchivesBase) throws Exception;

	@RequestMapping("/MfOaArchivesBase/delete")
	public void delete(@RequestBody MfOaArchivesBase mfOaArchivesBase) throws Exception;

	@RequestMapping("/MfOaArchivesBase/update")
	public void update(@RequestBody MfOaArchivesBase mfOaArchivesBase) throws Exception;

	@RequestMapping("/MfOaArchivesBase/getById")
	public MfOaArchivesBase getById(@RequestBody MfOaArchivesBase mfOaArchivesBase) throws Exception;

	@RequestMapping("/MfOaArchivesBase/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfOaArchivesBase/getCount")
	public int getCount() throws Exception;

	@RequestMapping("/MfOaArchivesBase/getOpStsList")
	public List<OptionsList> getOpStsList() throws Exception;

	@RequestMapping("/MfOaArchivesBase/getOpStsAllList")
	public List<OptionsList> getOpStsAllList() throws Exception;

	@RequestMapping("/MfOaArchivesBase/getNamePage")
	public Ipage getNamePage(@RequestBody Ipage ipage,@RequestParam("opSts") String opSts) throws Exception;

}
