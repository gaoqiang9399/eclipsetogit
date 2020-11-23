package app.component.nmd.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.nmd.entity.NmdWay;
import app.util.toolkit.Ipage;

/**
 * Title: NmdWayBo.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Apr 05 03:32:05 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface NmdWayFeign {

	@RequestMapping("/nmdWay/insert")
	public void insert(@RequestBody NmdWay nmdWay) throws ServiceException;

	@RequestMapping("/nmdWay/delete")
	public void delete(@RequestBody NmdWay nmdWay) throws ServiceException;

	@RequestMapping("/nmdWay/update")
	public void update(@RequestBody NmdWay nmdWay) throws ServiceException;

	@RequestMapping("/nmdWay/getById")
	public NmdWay getById(@RequestBody NmdWay nmdWay) throws ServiceException;

	@RequestMapping("/nmdWay/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("nmdWay") NmdWay nmdWay) throws ServiceException;

	@RequestMapping("/nmdWay/getAll")
	public List<NmdWay> getAll(@RequestBody NmdWay nmdWay) throws ServiceException;

}
