package app.component.cus.trenchsubsidiary.feign;

import app.util.toolkit.Ipage;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.trenchsubsidiary.entity.MfShareProfitConfig;

/**
 * 类名： MfShareProfitConfigBo 描述：分润配置
 * 
 * @author 仇招
 * @date 2018年9月3日 上午10:39:24
 */
@FeignClient("mftcc-platform-factor")
public interface MfShareProfitConfigFeign {
	
	@RequestMapping(value = "/mfShareProfitConfig/insert")
	public void insert(@RequestBody MfShareProfitConfig mfShareProfitConfig) throws Exception;

	@RequestMapping(value = "/mfShareProfitConfig/delete")
	public void delete(@RequestBody MfShareProfitConfig mfShareProfitConfig) throws Exception;

	@RequestMapping(value = "/mfShareProfitConfig/update")
	public void update(@RequestBody MfShareProfitConfig mfShareProfitConfig) throws Exception;

	@RequestMapping(value = "/mfShareProfitConfig/getById")
	public MfShareProfitConfig getById(@RequestBody MfShareProfitConfig mfShareProfitConfig) throws Exception;

	@RequestMapping(value = "/mfShareProfitConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfShareProfitConfig/checkShareProfitConfig")
	public Map<String, Object> checkShareProfitConfig(@RequestBody MfShareProfitConfig mfShareProfitConfig) throws Exception;

}
