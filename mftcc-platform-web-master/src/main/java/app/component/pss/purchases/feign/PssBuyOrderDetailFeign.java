package  app.component.pss.purchases.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.purchases.entity.PssBuyOrderDetail;
import app.util.toolkit.Ipage;

/**
* Title: PssBuyOrderDetailBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Aug 10 17:41:38 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssBuyOrderDetailFeign {
	
	@RequestMapping(value = "/pssBuyOrderDetail/insert")
	public void insert(@RequestBody PssBuyOrderDetail pssBuyOrderDetail) throws Exception;
	
	@RequestMapping(value = "/pssBuyOrderDetail/delete")
	public void delete(@RequestBody PssBuyOrderDetail pssBuyOrderDetail) throws Exception;
	
	@RequestMapping(value = "/pssBuyOrderDetail/update")
	public void update(@RequestBody PssBuyOrderDetail pssBuyOrderDetail) throws Exception;
	
	@RequestMapping(value = "/pssBuyOrderDetail/getById")
	public PssBuyOrderDetail getById(@RequestBody PssBuyOrderDetail pssBuyOrderDetail) throws Exception;
	
	@RequestMapping(value = "/pssBuyOrderDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssBuyOrderDetail") PssBuyOrderDetail pssBuyOrderDetail) throws Exception;

	@RequestMapping(value = "/pssBuyOrderDetail/getAll")
	public List<PssBuyOrderDetail> getAll(@RequestBody PssBuyOrderDetail pssBuyOrderDetail) throws Exception;
	
	@RequestMapping(value = "/pssBuyOrderDetail/getCount")
	public int getCount(@RequestBody PssBuyOrderDetail pssBuyOrderDetail) throws Exception;
}
