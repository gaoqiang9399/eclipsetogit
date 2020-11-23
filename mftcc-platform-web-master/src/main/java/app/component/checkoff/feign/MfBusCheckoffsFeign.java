package  app.component.checkoff.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.checkoff.entity.MfBusCheckoffs;
import app.component.sys.entity.SysUser;
import app.util.toolkit.Ipage;

/**
* Title: MfBusCheckoffsBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Aug 18 11:23:06 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusCheckoffsFeign {
	
	@RequestMapping(value = "/mfBusCheckoffs/insert")
	public void insert(@RequestBody MfBusCheckoffs mfBusCheckoffs) throws Exception;
	
	@RequestMapping(value = "/mfBusCheckoffs/delete")
	public void delete(@RequestBody MfBusCheckoffs mfBusCheckoffs) throws Exception;
	
	@RequestMapping(value = "/mfBusCheckoffs/update")
	public void update(@RequestBody MfBusCheckoffs mfBusCheckoffs) throws Exception;
	
	@RequestMapping(value = "/mfBusCheckoffs/getById")
	public MfBusCheckoffs getById(@RequestBody MfBusCheckoffs mfBusCheckoffs) throws Exception;
	
	@RequestMapping(value = "/mfBusCheckoffs/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfBusCheckoffs/findDataSourceByPage")
	public Ipage findDataSourceByPage(@RequestBody Ipage ipg) throws ServiceException;
	/**
	 * 贷款核销收回
	 * @param mfBusCheckoffs
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusCheckoffs/checkOffBack")
	public void checkOffBack(@RequestBody MfBusCheckoffs mfBusCheckoffs) throws Exception;
	/**
	 * 提交核销申请
	 * @param checkOffId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusCheckoffs/checkOffAppSubmit")
	public MfBusCheckoffs checkOffAppSubmit(@RequestBody MfBusCheckoffs mfBusCheckoffs)throws Exception;
	/**
	 * 
	 * @param checkOffId 执行核销操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusCheckoffs/doExeCheckOff")
	public String  doExeCheckOff(@RequestBody String checkOffId)throws Exception;

	
}
