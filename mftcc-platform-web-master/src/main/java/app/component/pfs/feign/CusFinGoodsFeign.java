package app.component.pfs.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinGoods;
import app.util.toolkit.Ipage;

/**
 * Title: CusFinGoodsBoImplImpl.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Aug 30 05:23:43 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface CusFinGoodsFeign {

	@RequestMapping(value = "/cusFinGoods/insert")
	public void insert(@RequestBody CusFinGoods cusFinGoods) throws Exception;

	@RequestMapping(value = "/cusFinGoods/delete")
	public void delete(@RequestBody CusFinGoods cusFinGoods) throws Exception;

	@RequestMapping(value = "/cusFinGoods/update")
	public void update(@RequestBody CusFinGoods cusFinGoods) throws Exception;

	@RequestMapping(value = "/cusFinGoods/getById")
	public CusFinGoods getById(@RequestBody CusFinGoods cusFinGoods) throws Exception;

	@RequestMapping(value = "/cusFinGoods/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("cusFinGoods") CusFinGoods cusFinGoods) throws Exception;

}
