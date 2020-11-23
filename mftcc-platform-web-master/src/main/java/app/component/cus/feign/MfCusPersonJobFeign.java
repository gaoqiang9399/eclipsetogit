package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusPersonJob;
import app.util.toolkit.Ipage;

/**
* Title: MfCusPersonJobBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Jun 01 16:14:12 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusPersonJobFeign {
	
	@RequestMapping("/mfCusPersonJob/insert")
	public void insert(@RequestBody MfCusPersonJob mfCusPersonJob) throws Exception;
	
	@RequestMapping("/mfCusPersonJob/insertForApp")
	public MfCusPersonJob insertForApp(@RequestBody MfCusPersonJob mfCusPersonJob) throws Exception;
	
	@RequestMapping("/mfCusPersonJob/delete")
	public void delete(@RequestBody MfCusPersonJob mfCusPersonJob) throws Exception;
	
	@RequestMapping("/mfCusPersonJob/update")
	public void update(@RequestBody MfCusPersonJob mfCusPersonJob) throws Exception;
	
	@RequestMapping("/mfCusPersonJob/getById")
	public MfCusPersonJob getById(@RequestBody MfCusPersonJob mfCusPersonJob) throws Exception;
	
	@RequestMapping("/mfCusPersonJob/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * @param mfCusCustomer
	 * @param mfCusPersBaseInfo
	 * @throws Exception
	 */
	@RequestMapping("/mfCusPersonJob/findMfCusPersonJobByPage")
	public List<MfCusPersonJob> findMfCusPersonJobByPage(@RequestBody MfCusPersonJob mfCusPersonJob)throws Exception;
	/**
	 * 
	 * 方法描述： 获取最新的职业信息
	 * @param mfCusPersonJob
	 * @return
	 * @throws Exception
	 * MfCusPersonJob
	 * @author zhs
	 * @date 2017-12-5 上午10:16:57
	 */
	@RequestMapping("/mfCusPersonJob/getNewById")
	public MfCusPersonJob getNewById(@RequestBody MfCusPersonJob mfCusPersonJob)throws Exception;
}
