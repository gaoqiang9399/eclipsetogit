package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusCashEnum;
import app.util.toolkit.Ipage;

/**
* Title: MfCusCashEnumBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jun 04 09:13:34 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusCashEnumFeign {
	
	@RequestMapping("/mfCusCashEnum/insert")
	public void insert(@RequestBody MfCusCashEnum mfCusCashEnum) throws Exception;
	
	@RequestMapping("/mfCusCashEnum/delete")
	public void delete(@RequestBody MfCusCashEnum mfCusCashEnum) throws Exception;
	
	@RequestMapping("/mfCusCashEnum/update")
	public void update(@RequestBody MfCusCashEnum mfCusCashEnum) throws Exception;
	
	@RequestMapping("/mfCusCashEnum/getById")
	public MfCusCashEnum getById(@RequestBody MfCusCashEnum mfCusCashEnum) throws Exception;
	
	@RequestMapping("/mfCusCashEnum/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
