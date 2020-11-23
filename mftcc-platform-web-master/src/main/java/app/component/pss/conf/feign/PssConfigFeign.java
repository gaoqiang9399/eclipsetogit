package app.component.pss.conf.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.pss.conf.entity.PssConfig;
import app.util.toolkit.Ipage;

/**
 * Title: PssConfigBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 16:00:07 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface PssConfigFeign {

	@RequestMapping(value = "/pssConfig/insert")
	public void insert(@RequestBody PssConfig pssConfig) throws ServiceException;

	@RequestMapping(value = "/pssConfig/delete")
	public void delete(@RequestBody PssConfig pssConfig) throws ServiceException;

	@RequestMapping(value = "/pssConfig/update")
	public void update(@RequestBody PssConfig pssConfig) throws ServiceException;

	@RequestMapping(value = "/pssConfig/getById")
	public PssConfig getById(@RequestBody PssConfig pssConfig) throws ServiceException;

	@RequestMapping(value = "/pssConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("pssConfig") PssConfig pssConfig) throws ServiceException;

	@RequestMapping(value = "/pssConfig/getAll")
	public List<PssConfig> getAll(@RequestBody PssConfig pssConfig) throws ServiceException;

	@RequestMapping(value = "/pssConfig/getOne")
	public PssConfig getOne() throws ServiceException;
}
