package app.component.pfs.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinInv;
import app.util.toolkit.Ipage;

/**
 * Title: CusFinInvBoImplImpl.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Aug 30 05:26:58 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface CusFinInvFeign {

	@RequestMapping(value = "/cusFinInv/insert")
	public void insert(@RequestBody CusFinInv cusFinInv) throws Exception ;

	@RequestMapping(value = "/cusFinInv/delete")
	public void delete(@RequestBody CusFinInv cusFinInv) throws Exception ;

	@RequestMapping(value = "/cusFinInv/update")
	public void update(@RequestBody CusFinInv cusFinInv) throws Exception ;

	@RequestMapping(value = "/cusFinInv/getById")
	public CusFinInv getById(@RequestBody CusFinInv cusFinInv) throws Exception ;

	@RequestMapping(value = "/cusFinInv/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("cusFinInv") CusFinInv cusFinInv) throws Exception ;

}
