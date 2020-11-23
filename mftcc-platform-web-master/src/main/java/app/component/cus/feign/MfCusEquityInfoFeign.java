package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusEquityInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfCusEquityInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue May 31 15:43:25 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusEquityInfoFeign {
	
	@RequestMapping("/mfCusEquityInfo/insert")
	public void insert(@RequestBody MfCusEquityInfo mfCusEquityInfo) throws Exception;
	
	@RequestMapping("/mfCusEquityInfo/delete")
	public void delete(@RequestBody MfCusEquityInfo mfCusEquityInfo) throws Exception;
	
	@RequestMapping("/mfCusEquityInfo/update")
	public void update(@RequestBody MfCusEquityInfo mfCusEquityInfo) throws Exception;
	
	@RequestMapping("/mfCusEquityInfo/getById")
	public MfCusEquityInfo getById(@RequestBody MfCusEquityInfo mfCusEquityInfo) throws Exception;
	
	@RequestMapping("/mfCusEquityInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 获取对外投资不分页
	 * @param mfCusEquityInfo
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-31 下午7:15:19
	 */
	@RequestMapping("/mfCusEquityInfo/findByEntity")
	public List<MfCusEquityInfo> findByEntity(@RequestBody MfCusEquityInfo mfCusEquityInfo) throws Exception;
	
}
