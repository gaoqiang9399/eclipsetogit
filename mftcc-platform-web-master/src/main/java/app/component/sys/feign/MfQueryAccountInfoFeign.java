package  app.component.sys.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.sys.entity.MfQueryAccountInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfQueryAccountInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jan 23 11:30:41 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface MfQueryAccountInfoFeign {
	@RequestMapping("/mfQueryAccountInfo/insert")
	public void insert(@RequestBody MfQueryAccountInfo mfQueryAccountInfo) throws Exception;
	@RequestMapping("/mfQueryAccountInfo/delete")
	public void delete(@RequestBody MfQueryAccountInfo mfQueryAccountInfo) throws Exception;
	@RequestMapping("/mfQueryAccountInfo/update")
	public void update(@RequestBody MfQueryAccountInfo mfQueryAccountInfo) throws Exception;
	@RequestMapping("/mfQueryAccountInfo/getById")
	public MfQueryAccountInfo getById(@RequestBody MfQueryAccountInfo mfQueryAccountInfo) throws Exception;
	@RequestMapping("/mfQueryAccountInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述： 获得累计查询次数最少的查询员信息
	 * @param mfQueryAccountInfo
	 * @return
	 * @throws Exception
	 * MfQueryAccountInfo
	 * @author 沈浩兵
	 * @date 2018-1-23 下午1:47:56
	 */
	@RequestMapping("/mfQueryAccountInfo/getQueryNoByMinQueryNum")
	public MfQueryAccountInfo getQueryNoByMinQueryNum(@RequestBody MfQueryAccountInfo mfQueryAccountInfo) throws Exception;
}
