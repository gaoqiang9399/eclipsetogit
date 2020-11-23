package app.component.param.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.param.entity.Scence;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-fileService")
public interface ParamFeign {
	@RequestMapping("/scence/insert")
	public Scence insert(@RequestBody Scence scence);
	@RequestMapping("/scence/delete")
	public Scence delete(@RequestBody Scence scence);
	@RequestMapping("/scence/update")
	public Scence update(@RequestBody Scence scence);
	@RequestMapping("/scence/updateUseFlag")
	public Scence updateUseFlag(@RequestBody Scence scence);
	@RequestMapping("/scence/getById")
	public Scence getById(@RequestBody Scence scence);
	@RequestMapping("/scence/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage);
	@RequestMapping("/scence/findByType")
	public List<Scence> findByType(@RequestBody Scence scence);
	@RequestMapping("/scence/getAll")
	public List<Scence> getAll();
}
