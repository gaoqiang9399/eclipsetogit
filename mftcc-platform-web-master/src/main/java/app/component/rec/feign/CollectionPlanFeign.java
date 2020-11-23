package  app.component.rec.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.prdct.entity.MfSysKind;
import app.component.rec.entity.CollectionPlan;
import app.util.toolkit.Ipage;

/**
* Title: CollectionPlanBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sun Mar 19 14:05:01 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface CollectionPlanFeign {
	
	@RequestMapping(value = "/collectionPlan/insert")
	public void insert(@RequestBody CollectionPlan collectionPlan) throws ServiceException;
	@RequestMapping(value = "/collectionPlan/delete")
	public void delete(@RequestBody CollectionPlan collectionPlan) throws ServiceException;
	@RequestMapping(value = "/collectionPlan/update")
	public void update(@RequestBody CollectionPlan collectionPlan) throws ServiceException;
	@RequestMapping(value = "/collectionPlan/getById")
	public CollectionPlan getById(@RequestBody CollectionPlan collectionPlan) throws ServiceException;
	@RequestMapping(value = "/collectionPlan/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("collectionPlan") CollectionPlan collectionPlan) throws ServiceException;
	@RequestMapping(value = "/collectionPlan/getAll")
	public List<CollectionPlan> getAll(@RequestBody CollectionPlan collectionPlan) throws ServiceException;
	@RequestMapping(value = "/collectionPlan/getRecallConfigMap")
	public Map<String, Object> getRecallConfigMap(@RequestBody List<MfSysKind> mfSysKindList) throws ServiceException;
}
