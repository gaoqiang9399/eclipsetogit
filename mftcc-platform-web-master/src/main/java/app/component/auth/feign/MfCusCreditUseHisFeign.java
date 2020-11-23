package  app.component.auth.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.auth.entity.MfCusCreditUseHis;
import app.util.toolkit.Ipage;

/**
* Title: MfCusCreditUseHisBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 27 22:28:51 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusCreditUseHisFeign {
	
	@RequestMapping(value = "/mfCusCreditUseHis/insert")
	public void insert(@RequestBody MfCusCreditUseHis mfCusCreditUseHis) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditUseHis/delete")
	public void delete(@RequestBody MfCusCreditUseHis mfCusCreditUseHis) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditUseHis/update")
	public void update(@RequestBody MfCusCreditUseHis mfCusCreditUseHis) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditUseHis/getById")
	public MfCusCreditUseHis getById(@RequestBody MfCusCreditUseHis mfCusCreditUseHis) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditUseHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCusCreditUseHis") MfCusCreditUseHis mfCusCreditUseHis) throws Exception;
	/**
	 * 
	 * 方法描述： 获得用信历史列表
	 * @param mfCusCreditUseHis
	 * @return
	 * @throws Exception
	 * List<MfCusCreditUseHis>
	 * @author 沈浩兵
	 * @date 2017-7-2 下午3:52:39
	 */
	@RequestMapping(value = "/mfCusCreditUseHis/getMfCusCreditUseHis")
	public List<MfCusCreditUseHis> getMfCusCreditUseHis(@RequestBody MfCusCreditUseHis mfCusCreditUseHis) throws Exception;
	
}
