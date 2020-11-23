package app.component.cus.feign;

import app.component.cus.entity.MfCusHighChildInfo;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
* Title: MfCusHighChildInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue 09:01:54 CST 2020
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusHighChildInfoFeign {
	@RequestMapping("/mfCusHighChildInfo/insert")
	public void insert(@RequestBody MfCusHighChildInfo mfCusHighChildInfo) throws Exception;
	
	@RequestMapping("/mfCusHighChildInfo/delete")
	public void delete(@RequestBody MfCusHighChildInfo mfCusHighChildInfo) throws Exception;
	
	@RequestMapping("/mfCusHighChildInfo/update")
	public void update(@RequestBody MfCusHighChildInfo mfCusHighChildInfo) throws Exception;
	
	@RequestMapping("/mfCusHighChildInfo/getById")
	public MfCusHighChildInfo getById(@RequestBody MfCusHighChildInfo mfCusHighChildInfo) throws Exception;
	
	@RequestMapping("/mfCusHighChildInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCusHighChildInfo/findByEntity")
	public List<MfCusHighChildInfo> findByEntity(@RequestBody MfCusHighChildInfo mfCusHighChildInfo) throws Exception;;
}
