package  app.component.pss.sales.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.pss.sales.entity.PssSaleBillDetail;
import app.util.toolkit.Ipage;

/**
* Title: PssSaleBillDetailBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Sep 05 15:03:17 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssSaleBillDetailFeign {
	
	@RequestMapping(value = "/pssSaleBillDetail/insert")
	public void insert(@RequestBody PssSaleBillDetail pssSaleBillDetail) throws ServiceException;
	
	@RequestMapping(value = "/pssSaleBillDetail/delete")
	public void delete(@RequestBody PssSaleBillDetail pssSaleBillDetail) throws ServiceException;
	
	@RequestMapping(value = "/pssSaleBillDetail/update")
	public void update(@RequestBody PssSaleBillDetail pssSaleBillDetail) throws ServiceException;
	
	@RequestMapping(value = "/pssSaleBillDetail/getById")
	public PssSaleBillDetail getById(@RequestBody PssSaleBillDetail pssSaleBillDetail) throws ServiceException;
	
	@RequestMapping(value = "/pssSaleBillDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssSaleBillDetail") PssSaleBillDetail pssSaleBillDetail) throws ServiceException;

	@RequestMapping(value = "/pssSaleBillDetail/getAll")
	public List<PssSaleBillDetail> getAll(@RequestBody PssSaleBillDetail pssSaleBillDetail) throws ServiceException;
}
