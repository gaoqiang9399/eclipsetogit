package app.component.pfs.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinRecOth;
import app.util.toolkit.Ipage;

/**
 * Title: CusFinRecOthBoImplImpl.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Aug 30 05:35:46 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface CusFinRecOthFeign {

	@RequestMapping(value = "/cusFinRecOth/insert")
	public void insert(@RequestBody CusFinRecOth cusFinRecOth) throws Exception;

	@RequestMapping(value = "/cusFinRecOth/delete")
	public void delete(@RequestBody CusFinRecOth cusFinRecOth) throws Exception;

	@RequestMapping(value = "/cusFinRecOth/update")
	public void update(@RequestBody CusFinRecOth cusFinRecOth) throws Exception;

	@RequestMapping(value = "/cusFinRecOth/getById")
	public CusFinRecOth getById(@RequestBody CusFinRecOth cusFinRecOth) throws Exception;

	@RequestMapping(value = "/cusFinRecOth/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("cusFinRecOth") CusFinRecOth cusFinRecOth) throws Exception;

}
