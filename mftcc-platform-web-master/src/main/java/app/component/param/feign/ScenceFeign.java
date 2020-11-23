package app.component.param.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.param.entity.Scence;
import app.util.toolkit.Ipage;

/**
 * Title: ScenceBoImplImpl.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Wed Jan 20 03:11:13 GMT 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface ScenceFeign {

	@RequestMapping(value = "/scence/insert")
	public void insert(@RequestBody Scence scence) throws Exception;

	@RequestMapping(value = "/scence/delete")
	public void delete(@RequestBody Scence scence) throws Exception;

	@RequestMapping(value = "/scence/update")
	public void update(@RequestBody Scence scence) throws Exception;

	@RequestMapping(value = "/scence/updateUseFlag")
	public void updateUseFlag(@RequestBody Scence scence) throws Exception;

	@RequestMapping(value = "/scence/getById")
	public Scence getById(@RequestBody Scence scence) throws Exception;

	@RequestMapping(value = "/scence/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("scence") Scence scence) throws Exception;

	@RequestMapping(value = "/scence/findByType")
	public List<Scence> findByType(@RequestBody Scence scence) throws Exception;

	@RequestMapping(value = "/scence/getAll")
	public List<Scence> getAll() throws Exception;

}
