package  app.component.tools.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.tools.entity.MfToolsSendMessage;
import app.util.toolkit.Ipage;

/**
* Title: MfToolsSendMessageBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Oct 08 14:57:44 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfToolsSendMessageFeign {
	
	@RequestMapping(value = "/mfToolsSendMessage/insert")
	public MfToolsSendMessage insert(@RequestBody MfToolsSendMessage mfToolsSendMessage) throws ServiceException;
	
	@RequestMapping(value = "/mfToolsSendMessage/delete")
	public void delete(@RequestBody MfToolsSendMessage mfToolsSendMessage) throws ServiceException;
	
	@RequestMapping(value = "/mfToolsSendMessage/update")
	public void update(@RequestBody MfToolsSendMessage mfToolsSendMessage) throws ServiceException;
	
	@RequestMapping(value = "/mfToolsSendMessage/getById")
	public MfToolsSendMessage getById(@RequestBody MfToolsSendMessage mfToolsSendMessage) throws ServiceException;
	
	@RequestMapping(value = "/mfToolsSendMessage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfToolsSendMessage") MfToolsSendMessage mfToolsSendMessage) throws ServiceException;
	
	@RequestMapping(value = "/mfToolsSendMessage/getAllList")
	public List<MfToolsSendMessage> getAllList(@RequestBody MfToolsSendMessage mfToolsSendMessage) throws ServiceException;
	
}
