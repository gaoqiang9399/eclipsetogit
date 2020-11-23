package app.component.rec.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.rec.entity.RecallConfigLaw;
import app.util.toolkit.Ipage;

/**
 * Title: RecallConfigLawBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 23 17:49:46 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface RecallConfigLawFeign {
	@RequestMapping(value = "/RecallConfigLaw/insert")
	public void insert(@RequestBody RecallConfigLaw recallConfigLaw) throws ServiceException;

	@RequestMapping(value = "/RecallConfigLaw/delete")
	public void delete(@RequestBody RecallConfigLaw recallConfigLaw) throws ServiceException;

	@RequestMapping(value = "/RecallConfigLaw/update")
	public void update(@RequestBody RecallConfigLaw recallConfigLaw) throws ServiceException;

	@RequestMapping(value = "/RecallConfigLaw/getById")
	public RecallConfigLaw getById(@RequestBody RecallConfigLaw recallConfigLaw) throws ServiceException;

	@RequestMapping(value = "/RecallConfigLaw/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("recallConfigLaw") RecallConfigLaw recallConfigLaw) throws ServiceException;

	@RequestMapping(value = "/RecallConfigLaw/getAll")
	public List<RecallConfigLaw> getAll(@RequestBody RecallConfigLaw recallConfigLaw) throws ServiceException;

	@RequestMapping(value = "/RecallConfigLaw/getByCollectionPlanNo")
	public RecallConfigLaw getByCollectionPlanNo(@RequestBody RecallConfigLaw recallConfigLaw) throws ServiceException;

	@RequestMapping(value = "/RecallConfigLaw/getByRecKindNo")
	public List<RecallConfigLaw> getByRecKindNo(@RequestBody RecallConfigLaw recallConfigLaw) throws ServiceException;
}
