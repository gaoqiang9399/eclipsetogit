package app.component.sys.feign;

import app.base.ServiceException;
import app.component.sys.entity.MfSysSkinUser;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
* Title: MfSysCompanyMstBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed May 03 15:16:02 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfSysSkinUserFeign {
	@RequestMapping("/mfSysSkinUser/update")
	public Map<String,Object> update(@RequestBody MfSysSkinUser mfSysSkinUser) throws Exception;

	@RequestMapping("/mfSysSkinUser/getById")
	public MfSysSkinUser getById(@RequestBody MfSysSkinUser mfSysSkinUser) throws Exception;
}
