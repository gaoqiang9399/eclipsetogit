package  app.component.nmd.feign;

import app.util.toolkit.Ipage;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import app.component.nmd.entity.ParLoanuse;

/**
* Title: parLoanuseBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed May 02 14:12:41 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface ParLoanuseFeign {
	@RequestMapping(value = "/parLoanuse/insert")
	public void insert(@RequestBody ParLoanuse parLoanuse) throws Exception;
	
	@RequestMapping(value = "/parLoanuse/delete")
	public void delete(@RequestBody ParLoanuse parLoanuse) throws Exception;
	
	@RequestMapping(value = "/parLoanuse/update")
	public void update(@RequestBody ParLoanuse parLoanuse) throws Exception;
	
	@RequestMapping(value = "/parLoanuse/getById")
	public ParLoanuse getById(@RequestBody ParLoanuse parLoanuse) throws Exception;
	
	@RequestMapping(value = "/parLoanuse/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/parLoanuse/getFincUseSm")
	public List<ParLoanuse> getFincUseSm(@RequestBody ParLoanuse parLoanuse) throws Exception;
	
}
