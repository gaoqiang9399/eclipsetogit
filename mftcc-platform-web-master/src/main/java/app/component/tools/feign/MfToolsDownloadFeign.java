package  app.component.tools.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.tools.entity.MfToolsDownload;
import app.util.toolkit.Ipage;

/**
* Title: MfToolsDownloadBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Oct 17 18:29:27 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfToolsDownloadFeign {
	@RequestMapping(value = "/mfToolsDownload/getById")
	public MfToolsDownload getById(@RequestBody MfToolsDownload mfToolsDownload) throws ServiceException;
	@RequestMapping(value = "/mfToolsDownload/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;


}
