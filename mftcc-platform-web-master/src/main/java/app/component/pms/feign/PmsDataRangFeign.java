package app.component.pms.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.pms.entity.PmsDataRang;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface PmsDataRangFeign {
	@RequestMapping(value = "/pmsDataRang/getById")
	public PmsDataRang getById(@RequestBody PmsDataRang pmsDataRang) throws ServiceException;

	@RequestMapping(value = "/pmsDataRang/delete")
	public void delete(@RequestBody PmsDataRang pmsDataRang) throws ServiceException;
	
	@RequestMapping(value = "/pmsDataRang/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipg) throws ServiceException;

	@RequestMapping(value = "/pmsDataRang/update")
	public void update(@RequestBody PmsDataRang pmsDataRang) throws ServiceException;

	@RequestMapping(value = "/pmsDataRang/insert")
	public void insert(@RequestBody PmsDataRang pmsDataRang) throws ServiceException;

	@RequestMapping(value = "/pmsDataRang/getAllList")
	public List<PmsDataRang> getAllList() throws ServiceException;
}
