package app.component.nmd.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.nmd.entity.NmdComInf;
import app.util.toolkit.Ipage;

/**
 * Title: NmdComInfBo.java Description:
 * 
 * @author:renyongxian@dhcc.com.cn
 * @Tue May 07 09:53:21 CST 2013
 **/
@FeignClient("mftcc-platform-factor")
public interface NmdComInfFeign {

	@RequestMapping("/nmdComInf/getById")
	public NmdComInf getById(@RequestBody NmdComInf nmdComInf) throws ServiceException;

	@RequestMapping("/nmdComInf/del")
	public void del(@RequestBody NmdComInf nmdComInf) throws ServiceException;

	@RequestMapping("/nmdComInf/insert")
	public void insert(@RequestBody NmdComInf nmdComInf) throws ServiceException;

	@RequestMapping("/nmdComInf/update")
	public void update(@RequestBody NmdComInf nmdComInf) throws ServiceException;

	@RequestMapping("/nmdComInf/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipg,@RequestParam("nmdComInf") NmdComInf nmdComInf) throws ServiceException;

}