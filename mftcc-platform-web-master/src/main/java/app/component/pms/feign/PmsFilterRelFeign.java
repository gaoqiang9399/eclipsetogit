package app.component.pms.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.pms.entity.PmsFilterRel;
import app.util.toolkit.Ipage;

/**
 * Title: PmsFilterRelBo.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Fri May 13 01:04:42 GMT 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface PmsFilterRelFeign {

	@RequestMapping(value = "/pmsFilterRel/insert")
	public void insert(@RequestBody PmsFilterRel pmsFilterRel) throws ServiceException;

	@RequestMapping(value = "/pmsFilterRel/delete")
	public void delete(@RequestBody PmsFilterRel pmsFilterRel) throws ServiceException;

	@RequestMapping(value = "/pmsFilterRel/update")
	public void update(@RequestBody PmsFilterRel pmsFilterRel) throws ServiceException;

	@RequestMapping(value = "/pmsFilterRel/getById")
	public PmsFilterRel getById(@RequestBody PmsFilterRel pmsFilterRel) throws ServiceException;

	@RequestMapping(value = "/pmsFilterRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

}
