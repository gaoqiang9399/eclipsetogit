package app.component.pfs.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinList;
import app.util.toolkit.Ipage;

/**
 * Title: CusFinListBoImplImpl.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Mon Aug 29 09:43:44 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface CusFinListFeign {

	@RequestMapping(value = "/cusFinList/insert")
	public void insert(@RequestBody CusFinList cusFinList) throws Exception;

	@RequestMapping(value = "/cusFinList/delete")
	public void delete(@RequestBody CusFinList cusFinList) throws Exception;

	@RequestMapping(value = "/cusFinList/update")
	public void update(@RequestBody CusFinList cusFinList) throws Exception;

	@RequestMapping(value = "/cusFinList/getById")
	public CusFinList getById(@RequestBody CusFinList cusFinList) throws Exception;

	@RequestMapping(value = "/cusFinList/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("cusFinList") CusFinList cusFinList) throws Exception;

	@RequestMapping(value = "/cusFinList/getAll")
	public List<CusFinList> getAll(@RequestBody CusFinList cusFinList) throws Exception;

	@RequestMapping(value = "/cusFinList/getFlag")
	public boolean getFlag(@RequestBody CusFinList cusFinList) throws Exception;

}
