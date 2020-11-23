package  app.component.pss.sales.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.sales.entity.PssSaleReturnBillDetail;
import app.util.toolkit.Ipage;

/**
* Title: PssSaleReturnBillDetailBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Sep 14 15:32:40 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssSaleReturnBillDetailFeign {
	
	@RequestMapping(value = "/pssSaleReturnBillDetail/insert")
	public void insert(@RequestBody PssSaleReturnBillDetail pssSaleReturnBillDetail) throws Exception;
	
	@RequestMapping(value = "/pssSaleReturnBillDetail/delete")
	public void delete(@RequestBody PssSaleReturnBillDetail pssSaleReturnBillDetail) throws Exception;
	
	@RequestMapping(value = "/pssSaleReturnBillDetail/update")
	public void update(@RequestBody PssSaleReturnBillDetail pssSaleReturnBillDetail) throws Exception;
	
	@RequestMapping(value = "/pssSaleReturnBillDetail/getById")
	public PssSaleReturnBillDetail getById(@RequestBody PssSaleReturnBillDetail pssSaleReturnBillDetail) throws Exception;
	
	@RequestMapping(value = "/pssSaleReturnBillDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssSaleReturnBillDetail") PssSaleReturnBillDetail pssSaleReturnBillDetail) throws Exception;

	@RequestMapping(value = "/pssSaleReturnBillDetail/getAll")
	public List<PssSaleReturnBillDetail> getAll(@RequestBody PssSaleReturnBillDetail pssSaleReturnBillDetail) throws Exception;
	
	@RequestMapping(value = "/pssSaleReturnBillDetail/getCount")
	public int getCount(@RequestBody PssSaleReturnBillDetail pssSaleReturnBillDetail) throws Exception;
}
