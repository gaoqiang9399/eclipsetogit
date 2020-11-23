package  app.component.pss.sales.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.pss.sales.entity.PssSaleOriginalPic;
import app.util.toolkit.Ipage;

/**
* Title: PssSaleOriginalPicBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Sep 11 14:42:40 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssSaleOriginalPicFeign {
	
	@RequestMapping(value = "/pssSaleOriginalPic/insert")
	public void insert(@RequestBody PssSaleOriginalPic pssSaleOriginalPic) throws ServiceException;
	
	@RequestMapping(value = "/pssSaleOriginalPic/delete")
	public void delete(@RequestBody PssSaleOriginalPic pssSaleOriginalPic) throws ServiceException;
	
	@RequestMapping(value = "/pssSaleOriginalPic/update")
	public void update(@RequestBody PssSaleOriginalPic pssSaleOriginalPic) throws ServiceException;
	
	@RequestMapping(value = "/pssSaleOriginalPic/getById")
	public PssSaleOriginalPic getById(@RequestBody PssSaleOriginalPic pssSaleOriginalPic) throws ServiceException;
	
	@RequestMapping(value = "/pssSaleOriginalPic/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssSaleOriginalPic") PssSaleOriginalPic pssSaleOriginalPic) throws ServiceException;

	@RequestMapping(value = "/pssSaleOriginalPic/getAll")
	public List<PssSaleOriginalPic> getAll(@RequestBody PssSaleOriginalPic pssSaleOriginalPic) throws ServiceException;
}
