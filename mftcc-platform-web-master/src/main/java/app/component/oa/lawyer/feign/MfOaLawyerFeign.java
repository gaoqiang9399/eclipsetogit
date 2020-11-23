package app.component.oa.lawyer.feign;

import app.component.oa.archive.entity.MfOaArchivesBase;
import app.component.oa.lawyer.entity.MfOaLawyer;
import app.util.toolkit.Ipage;
import com.core.domain.screen.OptionsList;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("mftcc-platform-factor")
public interface MfOaLawyerFeign {
	@RequestMapping("/mfOaLawyer/insert")
	public void insert(@RequestBody MfOaLawyer mfOaLawyer) throws Exception;

	@RequestMapping("/mfOaLawyer/delete")
	public void delete(@RequestBody MfOaLawyer mfOaLawyer) throws Exception;

	@RequestMapping("/mfOaLawyer/update")
	public void update(@RequestBody MfOaLawyer mfOaLawyer) throws Exception;

	@RequestMapping("/mfOaLawyer/getById")
	public MfOaLawyer getById(@RequestBody MfOaLawyer mfOaLawyer) throws Exception;

	@RequestMapping("/mfOaLawyer/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfOaLawyer/findHireLawyerByPageAjax")
	public Ipage findHireLawyerByPageAjax(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfOaLawyer/getCount")
	public int getCount() throws Exception;



}
