package app.component.thirdservice.adapt.feign;

import app.component.thirdservice.adapt.entity.MfThirdApiAdapt;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
* Title: MfThirdApiAdaptBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Oct 10 09:43:55 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface MfThirdApiAdaptFeign {

	@RequestMapping(value = "/mfThirdApiAdapt/insert")
	public void insert(@RequestBody MfThirdApiAdapt mfThirdApiAdapt) throws Exception;

	@RequestMapping(value = "/mfThirdApiAdapt/delete")
	public void delete(@RequestBody MfThirdApiAdapt mfThirdApiAdapt) throws Exception;

	@RequestMapping(value = "/mfThirdApiAdapt/update")
	public void update(@RequestBody MfThirdApiAdapt mfThirdApiAdapt) throws Exception;

	@RequestMapping(value = "/mfThirdApiAdapt/getById")
	public MfThirdApiAdapt getById(@RequestBody MfThirdApiAdapt mfThirdApiAdapt) throws Exception;

	@RequestMapping(value = "/mfThirdApiAdapt/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfThirdApiAdapt/getList")
	public List<MfThirdApiAdapt> getList(@RequestBody MfThirdApiAdapt mfThirdApiAdapt) throws Exception;

	@RequestMapping(value = "/mfThirdApiAdapt/saveSetting")
	public void saveSetting(@RequestBody Map<String,Object> paramMap);
}
