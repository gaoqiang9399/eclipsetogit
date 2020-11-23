package  app.component.interfaces.mobileinterface.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.interfaces.mobileinterface.entity.MfAccessInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfAccessInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Oct 14 15:33:56 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfAccessInfoFeign{
	
	@RequestMapping(value = "/mfAccessInfo/insert")
	public MfAccessInfo insert(@RequestBody  MfAccessInfo mfAccessInfo) throws Exception;

	@RequestMapping(value = "/mfAccessInfo/delete")
	public void delete(@RequestBody  MfAccessInfo mfAccessInfo) throws Exception;

	@RequestMapping(value = "/mfAccessInfo/update")
	public void update(@RequestBody  MfAccessInfo mfAccessInfo) throws Exception;

	@RequestMapping(value = "/mfAccessInfo/getById")
	public MfAccessInfo getById(@RequestBody  MfAccessInfo mfAccessInfo) throws Exception;

	@RequestMapping(value = "/mfAccessInfo/getMfAccessInfoListByPhone")
	public List<MfAccessInfo> getMfAccessInfoListByPhone(@RequestBody  MfAccessInfo mfAccessInfo) throws Exception;

	@RequestMapping(value = "/mfAccessInfo/findByPage")
	public Ipage findByPage(@RequestBody  Ipage ipage,@RequestParam("mfAccessInfo") MfAccessInfo mfAccessInfo) throws Exception;
	
	
}
