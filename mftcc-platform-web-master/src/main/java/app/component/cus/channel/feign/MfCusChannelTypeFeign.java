package app.component.cus.channel.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.channelType.entity.MfCusChannelType;

import app.util.toolkit.Ipage;

/**
 * Title: mfCusChannelTypeBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Dec 07 14:51:12 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfCusChannelTypeFeign {

	@RequestMapping(value = "/MfCusChannelType/insert")
	public void insert(@RequestBody MfCusChannelType mfCusChannelType) throws Exception;

	@RequestMapping(value = "/MfCusChannelType/delete")
	public void delete(@RequestBody MfCusChannelType mfCusChannelType) throws Exception;

	@RequestMapping(value = "/MfCusChannelType/update")
	public void update(@RequestBody MfCusChannelType mfCusChannelType) throws Exception;

	@RequestMapping(value = "/MfCusChannelType/getById")
	public MfCusChannelType getById(@RequestBody MfCusChannelType mfCusChannelType) throws Exception;

	@RequestMapping(value = "/MfCusChannelType/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	/**
	 * 
	 *<p>Description:查询所有的客户渠道 </p> 
	 *@return
	 *@throws Exception
	 *@author 周凯强
	 *@date 2018年7月10日下午4:48:32
	 */
	@RequestMapping(value = "/MfCusChannelType/getAllByUplev")
	public  List<MfCusChannelType>   getAllByUplev() throws  Exception;

}
