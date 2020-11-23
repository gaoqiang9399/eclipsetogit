package app.component.pms.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.nmd.entity.ParmDic;
import app.component.pms.entity.PmsUserFilter;

@FeignClient("mftcc-platform-factor")
public interface PmsUserFilterFeign {
	@RequestMapping(value = "/pmsUserFilter/delete")
	public void delete(@RequestBody PmsUserFilter pmsUserFilter) throws ServiceException;

	@RequestMapping(value = "/pmsUserFilter/update")
	public void update(@RequestBody PmsUserFilter pmsUserFilter) throws ServiceException;

	@RequestMapping(value = "/pmsUserFilter/insert")
	public void insert(@RequestBody PmsUserFilter pmsUserFilter) throws ServiceException;

	@RequestMapping(value = "/pmsUserFilter/getById")
	public PmsUserFilter getById(@RequestBody PmsUserFilter pmsUserFilter) throws ServiceException;

	@RequestMapping(value = "/pmsUserFilter/findByList")
	public List<PmsUserFilter> findByList(@RequestBody PmsUserFilter pmsUserFilter) throws ServiceException;

	@RequestMapping(value = "/pmsUserFilter/getTableName")
	public List<PmsUserFilter> getTableName() throws ServiceException;

	@RequestMapping(value = "/pmsUserFilter/getTableColumn")
	public List<PmsUserFilter> getTableColumn(@RequestBody String filterName) throws ServiceException;

	@RequestMapping(value = "/pmsUserFilter/getParmDic")
	public List<PmsUserFilter> getParmDic(@RequestBody String filterName) throws ServiceException;

	@RequestMapping(value = "/pmsUserFilter/getCacheSelectFromDB")
	public List<ParmDic> getCacheSelectFromDB(@RequestBody String filterName, @RequestParam("methodName") String methodName) throws ServiceException;
}
