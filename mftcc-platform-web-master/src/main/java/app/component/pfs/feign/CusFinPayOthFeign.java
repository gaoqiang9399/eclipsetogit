package app.component.pfs.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinPayOth;
import app.util.toolkit.Ipage;

/**
 * Title: CusFinPayOthBoImplImpl.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Aug 30 05:32:36 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface CusFinPayOthFeign {

	@RequestMapping(value = "/cusFinPayOth/insert")
	public void insert(@RequestBody CusFinPayOth cusFinPayOth) throws Exception ;

	@RequestMapping(value = "/cusFinPayOth/delete")
	public void delete(@RequestBody CusFinPayOth cusFinPayOth) throws Exception ;

	@RequestMapping(value = "/cusFinPayOth/update")
	public void update(@RequestBody CusFinPayOth cusFinPayOth) throws Exception ;

	@RequestMapping(value = "/cusFinPayOth/getById")
	public CusFinPayOth getById(@RequestBody CusFinPayOth cusFinPayOth) throws Exception ;

	@RequestMapping(value = "/cusFinPayOth/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("cusFinPayOth") CusFinPayOth cusFinPayOth) throws Exception ;

}
