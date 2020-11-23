package app.component.financingoptions.feign;

import app.util.toolkit.Ipage;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.component.financingoptions.entity.MfSalesOptions;

/**
 * Title: MfSalesOptionsBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 10 09:40:36 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfSalesOptionsFeign {
	@RequestMapping(value = "/mfSalesOptions/insert", method = RequestMethod.POST)
	public void insert(@RequestBody MfSalesOptions mfSalesOptions) throws Exception;

	@RequestMapping(value = "/mfSalesOptions/delete", method = RequestMethod.POST)
	public void delete(@RequestBody MfSalesOptions mfSalesOptions) throws Exception;

	@RequestMapping(value = "/mfSalesOptions/update", method = RequestMethod.POST)
	public void update(@RequestBody MfSalesOptions mfSalesOptions) throws Exception;

	@RequestMapping(value = "/mfSalesOptions/getById", method = RequestMethod.POST)
	public MfSalesOptions getById(@RequestBody MfSalesOptions mfSalesOptions) throws Exception;

	@RequestMapping(value = "/mfSalesOptions/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfSalesOptions/ifRepeat")
	public Map<String, Object> ifRepeat(@RequestBody MfSalesOptions mfSalesOptions) throws Exception;

}
