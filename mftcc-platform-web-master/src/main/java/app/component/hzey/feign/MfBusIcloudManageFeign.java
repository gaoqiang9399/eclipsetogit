package  app.component.hzey.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.hzey.entity.MfBusIcloudManage;
import app.util.toolkit.Ipage;

/**
* Title: MfBusIcloudManageBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Jul 19 15:07:00 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfBusIcloudManageFeign{
	
	@RequestMapping(value = "/mfBusIcloudManage/mfBusIcloudManage/insert")
	public void insert(@RequestBody  MfBusIcloudManage mfBusIcloudManage) throws Exception;

	@RequestMapping(value = "/mfBusIcloudManage/delete")
	public void delete(@RequestBody  MfBusIcloudManage mfBusIcloudManage) throws Exception;

	@RequestMapping(value = "/mfBusIcloudManage/update")
	public void update(@RequestBody  MfBusIcloudManage mfBusIcloudManage) throws Exception;

	@RequestMapping(value = "/mfBusIcloudManage/getById")
	public MfBusIcloudManage getById(@RequestBody  MfBusIcloudManage mfBusIcloudManage) throws Exception;

	@RequestMapping(value = "/mfBusIcloudManage/findByPage")
	public Ipage findByPage(@RequestBody  Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusIcloudManage/insertAllHis")
	public void insertAllHis(@RequestBody  MfBusIcloudManage mfBusIcloudManage) throws Exception;

	@RequestMapping(value = "/mfBusIcloudManage/updateCloudPwd")
	public void updateCloudPwd(@RequestBody  MfBusIcloudManage mfBusIcloudManage) throws Exception;

	@RequestMapping(value = "/mfBusIcloudManage/updateCifInfo")
	public void updateCifInfo(@RequestBody  MfBusIcloudManage mfBusIcloudManage) throws Exception;

	@RequestMapping(value = "/mfBusIcloudManage/unBinding")
	public void unBinding(@RequestBody  MfBusIcloudManage mfBusIcloudManage) throws Exception;

	@RequestMapping(value = "/mfBusIcloudManage/getIcloudPwd")
	public String getIcloudPwd() throws Exception;

	@RequestMapping(value = "/mfBusIcloudManage/getUnbindIcloud")
	public MfBusIcloudManage getUnbindIcloud() throws Exception;

	@RequestMapping(value = "/mfBusIcloudManage/getIcloudByCifno")
	public MfBusIcloudManage getIcloudByCifno(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/mfBusIcloudManage/updateByCusNo")
	public void updateByCusNo(@RequestBody  MfBusIcloudManage mfBusIcloudManage) throws Exception;
	

	
}
