package app.component.finance.bankacc.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.component.finance.bankacc.entity.CwCusBankAccManage;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface CwCusBankAccManageFeign {
	@RequestMapping(value = "/cwCusBankAccManage/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwCusBankAccManage cwCusBankAccManage) throws Exception;
	
	@RequestMapping(value = "/cwCusBankAccManage/update", method = RequestMethod.POST)
	public void update(@RequestBody CwCusBankAccManage cwCusBankAccManage) throws Exception;
	
	@RequestMapping(value = "/cwCusBankAccManage/getById", method = RequestMethod.POST)
	public CwCusBankAccManage getById(@RequestBody CwCusBankAccManage cwCusBankAccManage) throws Exception;
	
	@RequestMapping(value = "/cwCusBankAccManage/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwCusBankAccManage cwCusBankAccManage) throws Exception;
	
	@RequestMapping(value = "/cwCusBankAccManage/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/cwCusBankAccManage/busfindByPage", method = RequestMethod.POST)
	public Ipage busfindByPage(@RequestBody Ipage ipage) throws Exception;
}
