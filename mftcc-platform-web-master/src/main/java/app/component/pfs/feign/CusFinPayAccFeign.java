package app.component.pfs.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinPayAcc;
import app.util.toolkit.Ipage;

/**
 * Title: CusFinPayAccBoImplImpl.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Aug 30 05:30:42 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface CusFinPayAccFeign {

	@RequestMapping(value = "/cusFinPayAcc/insert")
	public void insert(@RequestBody CusFinPayAcc cusFinPayAcc) throws Exception;

	@RequestMapping(value = "/cusFinPayAcc/delete")
	public void delete(@RequestBody CusFinPayAcc cusFinPayAcc) throws Exception;

	@RequestMapping(value = "/cusFinPayAcc/getAll")
	public List<CusFinPayAcc> getAll(@RequestBody CusFinPayAcc cusFinPayAcc) throws Exception;

	@RequestMapping(value = "/cusFinPayAcc/update")
	public void update(@RequestBody CusFinPayAcc cusFinPayAcc) throws Exception;

	@RequestMapping(value = "/cusFinPayAcc/getById")
	public CusFinPayAcc getById(@RequestBody CusFinPayAcc cusFinPayAcc) throws Exception;

	@RequestMapping(value = "/cusFinPayAcc/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("cusFinPayAcc") CusFinPayAcc cusFinPayAcc) throws Exception;

}
