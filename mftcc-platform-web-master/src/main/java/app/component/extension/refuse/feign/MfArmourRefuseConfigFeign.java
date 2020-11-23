package app.component.extension.refuse.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.extension.refuse.entity.MfArmourRefuseConfig;
import app.util.toolkit.Ipage;

/**
 * Title: MfArmourRefuseConfigBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jan 03 15:38:12 CST 2018
 **/

@FeignClient("mftcc-platform-factor")
public interface MfArmourRefuseConfigFeign {

	@RequestMapping("/mfArmourRefuseConfig/insert")
	public void insert(@RequestBody MfArmourRefuseConfig mfArmourRefuseConfig) throws Exception;

	@RequestMapping("/mfArmourRefuseConfig/delete")
	public void delete(@RequestBody MfArmourRefuseConfig mfArmourRefuseConfig) throws Exception;

	@RequestMapping("/mfArmourRefuseConfig/update")
	public void update(@RequestBody MfArmourRefuseConfig mfArmourRefuseConfig) throws Exception;

	@RequestMapping("/mfArmourRefuseConfig/getById")
	public MfArmourRefuseConfig getById(@RequestBody MfArmourRefuseConfig mfArmourRefuseConfig) throws Exception;

	@RequestMapping("/mfArmourRefuseConfig/insert")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfArmourRefuseConfig") MfArmourRefuseConfig mfArmourRefuseConfig) throws Exception;

	@RequestMapping("/mfArmourRefuseConfig/getAll")
	public List<MfArmourRefuseConfig> getAll(@RequestBody MfArmourRefuseConfig mfArmourRefuseConfig) throws Exception;
	@RequestMapping("/mfArmourRefuseConfig/getListByTypeAjax")
	public List<MfArmourRefuseConfig> getListByTypeAjax(@RequestBody MfArmourRefuseConfig mfArmourRefuseConfig)  throws Exception;
}
