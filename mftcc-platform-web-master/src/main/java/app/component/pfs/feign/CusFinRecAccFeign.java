package app.component.pfs.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinRecAcc;
import app.util.toolkit.Ipage;

/**
 * Title: CusFinRecAccBoImplImpl.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Aug 30 05:34:11 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface CusFinRecAccFeign {

	@RequestMapping(value = "/cusFinRecAcc/insert")
	public void insert(@RequestBody CusFinRecAcc cusFinRecAcc) throws Exception;

	@RequestMapping(value = "/cusFinRecAcc/delete")
	public void delete(@RequestBody CusFinRecAcc cusFinRecAcc) throws Exception;

	@RequestMapping(value = "/cusFinRecAcc/update")
	public void update(@RequestBody CusFinRecAcc cusFinRecAcc) throws Exception;

	@RequestMapping(value = "/cusFinRecAcc/getById")
	public CusFinRecAcc getById(@RequestBody CusFinRecAcc cusFinRecAcc) throws Exception;

	@RequestMapping(value = "/cusFinRecAcc/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("cusFinRecAcc") CusFinRecAcc cusFinRecAcc) throws Exception;

}
