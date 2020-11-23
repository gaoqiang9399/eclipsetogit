package app.component.frontview.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.frontview.entity.MfAppVersion;
import app.util.toolkit.Ipage;

/**
 * Title: MfAppVersionBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Dec 01 10:51:33 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfAppVersionFeign {

	@RequestMapping("/mfAppVersion/insert")
	public void insert(@RequestBody MfAppVersion mfAppVersion) throws Exception;

	@RequestMapping("/mfAppVersion/delete")
	public void delete(@RequestBody MfAppVersion mfAppVersion) throws Exception;

	@RequestMapping("/mfAppVersion/update")
	public void update(@RequestBody MfAppVersion mfAppVersion) throws Exception;

	@RequestMapping("/mfAppVersion/getById")
	public MfAppVersion getById(@RequestBody MfAppVersion mfAppVersion) throws Exception;

	@RequestMapping("/mfAppVersion/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfAppVersion") MfAppVersion mfAppVersion) throws Exception;

	/**
	 * 获取最新一条app更新记录
	 * 
	 * @param mfAppVersion
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfAppVersion/getLatestOne")
	public MfAppVersion getLatestOne() throws Exception;

	/**
	 * 根据版本号获取版本信息
	 * 
	 * @param mfAppVersion
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfAppVersion/getAppVersionByVer")
	public MfAppVersion getAppVersionByVer(@RequestBody MfAppVersion mfAppVersion) throws Exception;

	/**
	 * 获取大于当前时间的版本列表
	 * 
	 * @param mfAppVersion
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfAppVersion/getAppVersionListByTime")
	public List<MfAppVersion> getAppVersionListByTime(@RequestBody MfAppVersion mfAppVersion) throws Exception;
}
