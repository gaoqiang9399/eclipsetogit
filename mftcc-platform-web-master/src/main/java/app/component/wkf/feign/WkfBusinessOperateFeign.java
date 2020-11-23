package  app.component.wkf.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;

/**
* Title: WkfBusinessOperateBo.java
* Description:
* @author:zhanglei@dhcc.com.cn
* @Thu Feb 28 12:59:54 GMT 2013
**/
@FeignClient("mftcc-platform-factor")
public interface WkfBusinessOperateFeign  {

	@RequestMapping(value = "/wkfBusinessOperate/updateMangInfo")
	void updateMangInfo(@RequestBody String appId,@RequestParam("opNo") String opNo,@RequestParam("taskId")  String taskId)throws ServiceException;
	
	@RequestMapping(value = "/wkfBusinessOperate/updateMangInfo")
	void updateMangInfo(@RequestBody String appId,@RequestParam("opNo") String opNo)throws ServiceException;

	//List<PrdBase> getPrdBaseListForWkf(PrdBase prdBase)throws ServiceException;
	@RequestMapping(value = "/wkfBusinessOperate/getApprovalOpNo")
	public String getApprovalOpNo(@RequestBody String roleNo,@RequestParam("appNo") String appNo)throws ServiceException;

	@RequestMapping(value = "/wkfBusinessOperate/getUpOneOrg")
	public String getUpOneOrg(@RequestBody String brNo)throws ServiceException;
	
	@RequestMapping(value = "/wkfBusinessOperate/getUpTwoOrg")
	public String getUpTwoOrg(@RequestBody String brNo)throws ServiceException;
	
	@RequestMapping(value = "/wkfBusinessOperate/getExtApprover")
	public String getExtApprover(@RequestBody String appNo)throws ServiceException;
	
}