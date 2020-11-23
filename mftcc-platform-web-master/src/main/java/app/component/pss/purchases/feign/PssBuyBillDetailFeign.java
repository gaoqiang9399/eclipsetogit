package  app.component.pss.purchases.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.purchases.entity.PssBuyBillDetail;
import app.util.toolkit.Ipage;

/**
* Title: PssBuyBillDetailBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Aug 24 22:07:54 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssBuyBillDetailFeign {
	
	@RequestMapping(value = "/pssBuyBillDetail/insert")
	public void insert(@RequestBody PssBuyBillDetail pssBuyBillDetail) throws Exception;
	
	@RequestMapping(value = "/pssBuyBillDetail/delete")
	public void delete(@RequestBody PssBuyBillDetail pssBuyBillDetail) throws Exception;
	
	@RequestMapping(value = "/pssBuyBillDetail/update")
	public void update(@RequestBody PssBuyBillDetail pssBuyBillDetail) throws Exception;
	
	@RequestMapping(value = "/pssBuyBillDetail/getById")
	public PssBuyBillDetail getById(@RequestBody PssBuyBillDetail pssBuyBillDetail) throws Exception;
	
	@RequestMapping(value = "/pssBuyBillDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssBuyBillDetail") PssBuyBillDetail pssBuyBillDetail) throws Exception;

	@RequestMapping(value = "/pssBuyBillDetail/getAll")
	public List<PssBuyBillDetail> getAll(@RequestBody PssBuyBillDetail pssBuyBillDetail) throws Exception;
	
	@RequestMapping(value = "/pssBuyBillDetail/getCount")
	public int getCount(@RequestBody PssBuyBillDetail pssBuyBillDetail) throws Exception;
}
