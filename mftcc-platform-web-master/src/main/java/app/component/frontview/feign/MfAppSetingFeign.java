package app.component.frontview.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.frontview.entity.MfAppSeting;
import app.util.toolkit.Ipage;

/**
 * Title: MfAppSetingBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Oct 24 12:56:14 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfAppSetingFeign {

	@RequestMapping("/mfAppSeting/insert")
	public void insert(@RequestBody MfAppSeting mfAppSeting) throws Exception;

	@RequestMapping("/mfAppSeting/delete")
	public void delete(@RequestBody MfAppSeting mfAppSeting) throws Exception;

	@RequestMapping("/mfAppSeting/update")
	public void update(@RequestBody MfAppSeting mfAppSeting) throws Exception;

	@RequestMapping("/mfAppSeting/getById")
	public MfAppSeting getById(@RequestBody MfAppSeting mfAppSeting) throws Exception;

	@RequestMapping("/mfAppSeting/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfAppSeting") MfAppSeting mfAppSeting) throws Exception;

	@RequestMapping("/mfAppSeting/findByPage")
	public List<MfAppSeting> findByPage(@RequestBody MfAppSeting mfAppSeting) throws Exception;

}
