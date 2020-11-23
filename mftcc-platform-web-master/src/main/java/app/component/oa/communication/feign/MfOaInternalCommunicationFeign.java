package  app.component.oa.communication.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.oa.communication.entity.MfOaInternalCommunication;
import app.util.toolkit.Ipage;

/**
* Title: MfOaInternalCommunicationBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Sep 29 10:25:41 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfOaInternalCommunicationFeign {
	@RequestMapping(value = "/mfOaInternalCommunication/insert")
	public void insert(@RequestBody MfOaInternalCommunication mfOaInternalCommunication) throws Exception;
	/**
	 * 
	 * 方法描述： 回复新增保存
	 * @param mfOaInternalCommunication
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-9-29 下午4:09:28
	 */
	@RequestMapping(value = "/mfOaInternalCommunication/recoveryInsert")
	public void recoveryInsert(@RequestBody MfOaInternalCommunication mfOaInternalCommunication) throws Exception;
	
	@RequestMapping(value = "/mfOaInternalCommunication/delete")
	public void delete(@RequestBody MfOaInternalCommunication mfOaInternalCommunication) throws Exception;
	
	@RequestMapping(value = "/mfOaInternalCommunication/update")
	public void update(@RequestBody MfOaInternalCommunication mfOaInternalCommunication) throws Exception;
	
	@RequestMapping(value = "/mfOaInternalCommunication/getById")
	public MfOaInternalCommunication getById(@RequestBody MfOaInternalCommunication mfOaInternalCommunication) throws Exception;
	
	@RequestMapping(value = "/mfOaInternalCommunication/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述： 获得消息总数
	 * @param mfOaInternalCommunication
	 * @return
	 * @throws Exception
	 * Integer
	 * @author 沈浩兵
	 * @date 2017-9-29 下午8:00:32
	 */
	@RequestMapping(value = "/mfOaInternalCommunication/getCount")
	public Integer getCount(@RequestBody MfOaInternalCommunication mfOaInternalCommunication) throws Exception;
	
}
