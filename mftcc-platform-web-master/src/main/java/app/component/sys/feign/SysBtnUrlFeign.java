package app.component.sys.feign;


import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.sys.entity.SysBtnUrl;

@FeignClient("mftcc-platform-factor")
public interface SysBtnUrlFeign {
	@RequestMapping("/sysBtnUrl/update")
	public void update(@RequestBody SysBtnUrl sysBtnUrl) throws ServiceException;
	@RequestMapping("/sysBtnUrl/getById")
	public List<SysBtnUrl> getById(@RequestBody SysBtnUrl SysBtnUrl) throws ServiceException;
	@RequestMapping("/sysBtnUrl/getByUrlId")
	public List<SysBtnUrl> getByUrlId(String urlId) throws ServiceException;
	@RequestMapping("/sysBtnUrl/insert")
	public void insert(@RequestBody SysBtnUrl sysBtnUrl) throws ServiceException;
	@RequestMapping("/sysBtnUrl/delete")
	public void delete(@RequestBody SysBtnUrl sysBtnUrl) throws ServiceException;

}
