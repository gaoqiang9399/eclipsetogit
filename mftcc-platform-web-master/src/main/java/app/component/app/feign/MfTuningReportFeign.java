package  app.component.app.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.app.entity.MfTuningReport;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfTuningReportBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 13 19:48:40 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfTuningReportFeign {
	
	@RequestMapping(value = "/mfTuningReport/insert")
	public void insert(@RequestBody MfTuningReport mfTuningReport) throws ServiceException;
	
	@RequestMapping(value = "/mfTuningReport/delete")
	public void delete(@RequestBody MfTuningReport mfTuningReport) throws ServiceException;
	
	@RequestMapping(value = "/mfTuningReport/update")
	public void update(@RequestBody MfTuningReport mfTuningReport) throws ServiceException;
	
	@RequestMapping(value = "/mfTuningReport/getById")
	public MfTuningReport getById(@RequestBody MfTuningReport mfTuningReport) throws ServiceException;
	
	@RequestMapping(value = "/mfTuningReport/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfTuningReport") MfTuningReport mfTuningReport) throws ServiceException;

	@RequestMapping(value = "/mfTuningReport/insertAndCommit")
	public Result insertAndCommit(@RequestBody MfTuningReport mfTuningReport)throws ServiceException;
	
}
