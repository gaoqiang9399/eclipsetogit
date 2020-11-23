package app.component.rec.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.rec.entity.RecallConfig;
import app.util.toolkit.Ipage;

/**
 * Title: RecallConfigBo.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Mar 15 09:20:56 GMT 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface RecallConfigFeign {
	@RequestMapping(value = "/RecallConfig/insert")
	public void insert(@RequestBody RecallConfig recallConfig) throws ServiceException;

	@RequestMapping(value = "/RecallConfig/delete")
	public void delete(@RequestBody RecallConfig recallConfig) throws ServiceException;

	@RequestMapping(value = "/RecallConfig/update")
	public void update(@RequestBody RecallConfig recallConfig) throws ServiceException;

	@RequestMapping(value = "/RecallConfig/getById")
	public RecallConfig getById(@RequestBody RecallConfig recallConfig) throws ServiceException;

	@RequestMapping(value = "/RecallConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("recallConfig") RecallConfig recallConfig) throws ServiceException;

	@RequestMapping(value = "/RecallConfig/getAll")
	public List<RecallConfig> getAll(@RequestBody RecallConfig recallConfig) throws ServiceException;

	@RequestMapping(value = "/RecallConfig/updateDefSts")
	public void updateDefSts(@RequestBody RecallConfig recallConfig) throws ServiceException;
}
