package app.component.prdct.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.prdct.entity.MfKindTableConfig;
import app.util.toolkit.Ipage;

/**
 * Title: MfKindTableConfigBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Nov 25 17:04:10 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfKindTableConfigFeign {
	
	@RequestMapping(value = "/mfKindTableConfig/insert", method = RequestMethod.POST)
	public void insert(@RequestBody MfKindTableConfig mfKindTableConfig) throws Exception;

	@RequestMapping(value = "/mfKindTableConfig/delete", method = RequestMethod.POST)
	public void delete(@RequestBody MfKindTableConfig mfKindTableConfig) throws Exception;

	@RequestMapping(value = "/mfKindTableConfig/update", method = RequestMethod.POST)
	public void update(@RequestBody MfKindTableConfig mfKindTableConfig) throws Exception;

	@RequestMapping(value = "/mfKindTableConfig/getById", method = RequestMethod.POST)
	public MfKindTableConfig getById(@RequestBody MfKindTableConfig mfKindTableConfig) throws Exception ;

	@RequestMapping(value = "/mfKindTableConfig/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam MfKindTableConfig mfKindTableConfig) throws Exception; 

	@RequestMapping(value = "/mfKindTableConfig/getList", method = RequestMethod.POST)
	public List<MfKindTableConfig> getList(@RequestBody MfKindTableConfig mfKindTableConfig) throws Exception ;
}
