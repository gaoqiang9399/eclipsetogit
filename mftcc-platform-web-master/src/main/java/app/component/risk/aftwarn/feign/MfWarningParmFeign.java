package  app.component.risk.aftwarn.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.risk.aftwarn.entity.MfWarningParm;
import app.util.toolkit.Ipage;

/**
* Title: MfWarningParmBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Jun 01 17:50:53 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfWarningParmFeign {
	
	@RequestMapping(value = "/mfWarningParm/insert")
	public void insert(@RequestBody MfWarningParm mfWarningParm) throws ServiceException;
	
	@RequestMapping(value = "/mfWarningParm/delete")
	public void delete(@RequestBody MfWarningParm mfWarningParm) throws ServiceException;
	
	@RequestMapping(value = "/mfWarningParm/update")
	public void update(@RequestBody MfWarningParm mfWarningParm) throws ServiceException;
	
	@RequestMapping(value = "/mfWarningParm/getById")
	public MfWarningParm getById(@RequestBody MfWarningParm mfWarningParm) throws ServiceException;
	
	@RequestMapping(value = "/mfWarningParm/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfWarningParm") MfWarningParm mfWarningParm) throws ServiceException;
	
	@RequestMapping(value = "/mfWarningParm/getList")
	public List<MfWarningParm> getList(@RequestBody MfWarningParm mfWarningParm) throws ServiceException;
	
	@RequestMapping(value = "/mfWarningParm/updateList")
	public void updateList(@RequestBody List<MfWarningParm> mfWarningParmList) throws ServiceException;
}
