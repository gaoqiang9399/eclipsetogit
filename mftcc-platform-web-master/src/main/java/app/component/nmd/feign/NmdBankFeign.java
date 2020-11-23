package app.component.nmd.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.nmd.entity.NmdBank;
import app.util.toolkit.Ipage;

/**
 * Title: NmdBankBo.java Description:
 * 
 * @author:renyongxian@dhcc.com.cn
 * @Tue Mar 26 10:10:12 GMT 2013
 **/
@FeignClient("mftcc-platform-factor")
public interface NmdBankFeign {

	@RequestMapping("/nmdBank/getById")
	public NmdBank getById(@RequestBody NmdBank nmdBank) throws ServiceException;

	@RequestMapping("/nmdBank/del")
	public void del(@RequestBody NmdBank nmdBank) throws ServiceException;

	@RequestMapping("/nmdBank/insert")
	public void insert(@RequestBody NmdBank nmdBank) throws ServiceException;

	@RequestMapping("/nmdBank/update")
	public void update(@RequestBody NmdBank nmdBank) throws ServiceException;

	@RequestMapping("/nmdBank/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipg, @RequestParam("nmdBank") NmdBank nmdBank) throws ServiceException;

}