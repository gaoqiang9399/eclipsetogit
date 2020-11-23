package app.component.frontview.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.frontview.entity.MfFrontAppSetting;
import app.util.toolkit.Ipage;

/**
 * Title: MfFrontAppSettingBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Jul 20 11:25:24 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfFrontAppSettingFeign {

	@RequestMapping("/mfFrontAppSetting/insert")
	public void insert(@RequestBody MfFrontAppSetting mfFrontAppSetting) throws Exception;

	@RequestMapping("/mfFrontAppSetting/delete")
	public void delete(@RequestBody MfFrontAppSetting mfFrontAppSetting) throws Exception;

	@RequestMapping("/mfFrontAppSetting/update")
	public void update(@RequestBody MfFrontAppSetting mfFrontAppSetting) throws Exception;

	@RequestMapping("/mfFrontAppSetting/getById")
	public MfFrontAppSetting getById(@RequestBody MfFrontAppSetting mfFrontAppSetting) throws Exception;

	@RequestMapping("/mfFrontAppSetting/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfFrontAppSetting") MfFrontAppSetting mfFrontAppSetting) throws Exception;

	/**
	 * 移动端设置基本信息表只有一条数据 多条数据抛异常
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfFrontAppSetting/get")
	public MfFrontAppSetting get() throws Exception;

}
