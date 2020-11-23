package app.component.rec.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.rec.entity.RecallConfigPhone;
import app.util.toolkit.Ipage;

/**
 * Title: RecallConfigPhoneBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 23 09:28:27 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface RecallConfigPhoneFeign {
	@RequestMapping(value = "/RecallConfigPhone/insert")
	public void insert(@RequestBody RecallConfigPhone recallConfigPhone) throws ServiceException;

	@RequestMapping(value = "/RecallConfigPhone/delete")
	public void delete(@RequestBody RecallConfigPhone recallConfigPhone) throws ServiceException;

	@RequestMapping(value = "/RecallConfigPhone/update")
	public void update(@RequestBody RecallConfigPhone recallConfigPhone) throws ServiceException;

	@RequestMapping(value = "/RecallConfigPhone/getById")
	public RecallConfigPhone getById(@RequestBody RecallConfigPhone recallConfigPhone) throws ServiceException;

	@RequestMapping(value = "/RecallConfigPhone/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("recallConfigPhone") RecallConfigPhone recallConfigPhone) throws ServiceException;

	@RequestMapping(value = "/RecallConfigPhone/getAll")
	public List<RecallConfigPhone> getAll(@RequestBody RecallConfigPhone recallConfigPhone) throws ServiceException;

	@RequestMapping(value = "/RecallConfigPhone/getByCollectionPlanNo")
	public RecallConfigPhone getByCollectionPlanNo(@RequestBody RecallConfigPhone recallConfigPhone) throws ServiceException;

	@RequestMapping(value = "/RecallConfigPhone/getByRecKindNo")
	public List<RecallConfigPhone> getByRecKindNo(@RequestBody RecallConfigPhone recallConfigPhone) throws ServiceException;
}
