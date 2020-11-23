package  app.component.pss.purchases.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.purchases.entity.PssBuyReturnBillDetail;
import app.util.toolkit.Ipage;

/**
* Title: PssBuyReturnBillDetailBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Sep 14 14:22:26 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssBuyReturnBillDetailFeign {
	
	@RequestMapping(value = "/pssBuyReturnBillDetail/insert")
	public void insert(@RequestBody PssBuyReturnBillDetail pssBuyReturnBillDetail) throws Exception;
	
	@RequestMapping(value = "/pssBuyReturnBillDetail/delete")
	public void delete(@RequestBody PssBuyReturnBillDetail pssBuyReturnBillDetail) throws Exception;
	
	@RequestMapping(value = "/pssBuyReturnBillDetail/update")
	public void update(@RequestBody PssBuyReturnBillDetail pssBuyReturnBillDetail) throws Exception;
	
	@RequestMapping(value = "/pssBuyReturnBillDetail/getById")
	public PssBuyReturnBillDetail getById(@RequestBody PssBuyReturnBillDetail pssBuyReturnBillDetail) throws Exception;
	
	@RequestMapping(value = "/pssBuyReturnBillDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssBuyReturnBillDetail") PssBuyReturnBillDetail pssBuyReturnBillDetail) throws Exception;

	@RequestMapping(value = "/pssBuyReturnBillDetail/getAll")
	public List<PssBuyReturnBillDetail> getAll(@RequestBody PssBuyReturnBillDetail pssBuyReturnBillDetail) throws Exception;
	
	@RequestMapping(value = "/pssBuyReturnBillDetail/getCount")
	public int getCount(@RequestBody PssBuyReturnBillDetail pssBuyReturnBillDetail) throws Exception;
}
