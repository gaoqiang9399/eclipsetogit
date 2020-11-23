package app.component.pfs.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinDebt;
import app.util.toolkit.Ipage;

/**
 * Title: CusFinDebtBoImplImpl.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Aug 30 05:22:19 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface CusFinDebtFeign {

	@RequestMapping(value = "/cusFinDebt/insert")
	public void insert(@RequestBody CusFinDebt cusFinDebt) throws Exception;

	@RequestMapping(value = "/cusFinDebt/delete")
	public void delete(@RequestBody CusFinDebt cusFinDebt) throws Exception;

	@RequestMapping(value = "/cusFinDebt/update")
	public void update(@RequestBody CusFinDebt cusFinDebt) throws Exception;

	@RequestMapping(value = "/cusFinDebt/getById")
	public CusFinDebt getById(@RequestBody CusFinDebt cusFinDebt) throws Exception;

	@RequestMapping(value = "/cusFinDebt/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("cusFinDebt") CusFinDebt cusFinDebt) throws Exception;

}
