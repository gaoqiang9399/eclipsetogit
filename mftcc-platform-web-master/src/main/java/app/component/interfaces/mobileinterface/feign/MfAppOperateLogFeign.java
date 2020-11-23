package  app.component.interfaces.mobileinterface.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.interfaces.mobileinterface.entity.MfAppOperateLog;
import app.util.toolkit.Ipage;

/**
* Title: MfAppOperateLogBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Sep 12 17:20:20 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfAppOperateLogFeign{
	
	@RequestMapping(value = "/mfAppOperateLog/insert")
	public void insert(@RequestBody  MfAppOperateLog mfAppOperateLog) throws Exception;

	@RequestMapping(value = "/mfAppOperateLog/delete")
	public void delete(@RequestBody  MfAppOperateLog mfAppOperateLog) throws Exception;

	@RequestMapping(value = "/mfAppOperateLog/update")
	public void update(@RequestBody  MfAppOperateLog mfAppOperateLog) throws Exception;

	@RequestMapping(value = "/mfAppOperateLog/getById")
	public MfAppOperateLog getById(@RequestBody  MfAppOperateLog mfAppOperateLog) throws Exception;

	@RequestMapping(value = "/mfAppOperateLog/findByPage")
	public Ipage findByPage(@RequestBody  Ipage ipage,@RequestParam("mfAppOperateLog") MfAppOperateLog mfAppOperateLog) throws Exception;

	@RequestMapping(value = "/mfAppOperateLog/findList")
	public List<MfAppOperateLog> findList(@RequestBody  MfAppOperateLog mfAppOperateLog) throws Exception;

	@RequestMapping(value = "/mfAppOperateLog/getTimeFromRegToApp")
	public Map<String, Object> getTimeFromRegToApp(@RequestBody  MfAppOperateLog mfAppOperateLog) throws Exception;

	@RequestMapping(value = "/mfAppOperateLog/getIdCardCountRecently")
	public Map<String, Object> getIdCardCountRecently(@RequestBody  Map<String, String> parmMap) throws Exception;

	@RequestMapping(value = "/mfAppOperateLog/getAppCountOnDeferentDevice")
	public Map<String, Object> getAppCountOnDeferentDevice(@RequestBody  Map<String, String> parmMap) throws Exception;

	@RequestMapping(value = "/mfAppOperateLog/insertBatch")
	public void insertBatch(@RequestBody  List<MfAppOperateLog> list) throws Exception;

	@RequestMapping(value = "/mfAppOperateLog/getLoginCountOneDeviceDay")
	public Map<String, Object> getLoginCountOneDeviceDay(@RequestBody  Map<String, String> parmMap) throws Exception;
	
	
}
