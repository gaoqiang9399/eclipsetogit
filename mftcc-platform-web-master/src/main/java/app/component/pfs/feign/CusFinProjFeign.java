package app.component.pfs.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinProj;
import app.util.toolkit.Ipage;

/**
 * Title: CusFinProjBoImplImpl.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Aug 30 05:29:07 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface CusFinProjFeign {

	@RequestMapping(value = "/cusFinProj/insert")
	public void insert(@RequestBody CusFinProj cusFinProj) throws Exception;

	@RequestMapping(value = "/cusFinProj/delete")
	public void delete(@RequestBody CusFinProj cusFinProj) throws Exception;

	@RequestMapping(value = "/cusFinProj/update")
	public void update(@RequestBody CusFinProj cusFinProj) throws Exception;

	@RequestMapping(value = "/cusFinProj/getById")
	public CusFinProj getById(@RequestBody CusFinProj cusFinProj) throws Exception;

	@RequestMapping(value = "/cusFinProj/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("cusFinProj") CusFinProj cusFinProj) throws Exception;

}
