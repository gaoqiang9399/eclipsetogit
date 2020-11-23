package app.component.rec.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.rec.entity.RecallConfigOut;
import app.util.toolkit.Ipage;

/**
 * Title: RecallConfigOutBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 23 17:09:28 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface RecallConfigOutFeign {
	@RequestMapping(value = "/RecallConfigOut/insert")
	public void insert(@RequestBody RecallConfigOut recallConfigOut) throws ServiceException;

	@RequestMapping(value = "/RecallConfigOut/insert")
	public void delete(@RequestBody RecallConfigOut recallConfigOut) throws ServiceException;

	@RequestMapping(value = "/RecallConfigOut/insert")
	public void update(@RequestBody RecallConfigOut recallConfigOut) throws ServiceException;

	@RequestMapping(value = "/RecallConfigOut/insert")
	public RecallConfigOut getById(@RequestBody RecallConfigOut recallConfigOut) throws ServiceException;

	@RequestMapping(value = "/RecallConfigOut/insert")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("recallConfigOut") RecallConfigOut recallConfigOut) throws ServiceException;

	@RequestMapping(value = "/RecallConfigOut/insert")
	public List<RecallConfigOut> getAll(@RequestBody RecallConfigOut recallConfigOut) throws ServiceException;

	@RequestMapping(value = "/RecallConfigOut/insert")
	public RecallConfigOut getByCollectionPlanNo(@RequestBody RecallConfigOut recallConfigOut) throws ServiceException;

	@RequestMapping(value = "/RecallConfigOut/insert")
	public List<RecallConfigOut> getByRecKindNo(@RequestBody RecallConfigOut recallConfigOut) throws ServiceException;
}
