package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusGoods;
import app.util.toolkit.Ipage;

/**
* Title: MfCusGoodsBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Mar 09 14:17:42 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusGoodsFeign {
	
	@RequestMapping("/mfCusGoods/insert")
	public void insert(@RequestBody MfCusGoods mfCusGoods) throws Exception;
	
	@RequestMapping("/mfCusGoods/delete")
	public void delete(@RequestBody MfCusGoods mfCusGoods) throws Exception;
	
	@RequestMapping("/mfCusGoods/update")
	public void update(@RequestBody MfCusGoods mfCusGoods) throws Exception;
	
	@RequestMapping("/mfCusGoods/getById")
	public MfCusGoods getById(@RequestBody MfCusGoods mfCusGoods) throws Exception;
	
	@RequestMapping("/mfCusGoods/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
