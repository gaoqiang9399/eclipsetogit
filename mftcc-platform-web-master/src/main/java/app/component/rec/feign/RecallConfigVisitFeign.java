package app.component.rec.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.rec.entity.RecallConfigVisit;
import app.util.toolkit.Ipage;

/**
 * Title: RecallConfigVisitBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 23 16:29:22 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface RecallConfigVisitFeign {
	@RequestMapping(value = "/RecallConfigVisit/insert")
	public void insert(@RequestBody RecallConfigVisit recallConfigVisit) throws ServiceException;

	@RequestMapping(value = "/RecallConfigVisit/delete")
	public void delete(@RequestBody RecallConfigVisit recallConfigVisit) throws ServiceException;

	@RequestMapping(value = "/RecallConfigVisit/update")
	public void update(@RequestBody RecallConfigVisit recallConfigVisit) throws ServiceException;

	@RequestMapping(value = "/RecallConfigVisit/getById")
	public RecallConfigVisit getById(@RequestBody RecallConfigVisit recallConfigVisit) throws ServiceException;

	@RequestMapping(value = "/RecallConfigVisit/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("recallConfigVisit") RecallConfigVisit recallConfigVisit) throws ServiceException;

	@RequestMapping(value = "/RecallConfigVisit/getAll")
	public List<RecallConfigVisit> getAll(@RequestBody RecallConfigVisit recallConfigVisit) throws ServiceException;

	@RequestMapping(value = "/RecallConfigVisit/getByCollectionPlanNo")
	public RecallConfigVisit getByCollectionPlanNo(@RequestBody RecallConfigVisit recallConfigVisit) throws ServiceException;

	@RequestMapping(value = "/RecallConfigVisit/getByRecKindNo")
	public List<RecallConfigVisit> getByRecKindNo(@RequestBody RecallConfigVisit recallConfigvisit) throws ServiceException;
}
