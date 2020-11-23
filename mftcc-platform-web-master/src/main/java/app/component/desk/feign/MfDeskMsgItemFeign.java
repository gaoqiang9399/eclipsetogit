package  app.component.desk.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.desk.entity.MfDeskMsgItem;

/**
* Title: MfDeskMsgItemBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sun Aug 27 10:57:59 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfDeskMsgItemFeign {
	
	@RequestMapping(value = "/mfDeskMsgItem/getMsgList")
	public List<MfDeskMsgItem> getMsgList(@RequestBody MfDeskMsgItem mfDeskMsgItem)throws Exception;

	@RequestMapping(value = "/mfDeskMsgItem/updateSort")
	public void updateSort(@RequestBody MfDeskMsgItem mfDeskMsgItem)throws Exception;

	@RequestMapping(value = "/mfDeskMsgItem/getDeskMsgItemByOpNo")
	public MfDeskMsgItem getDeskMsgItemByOpNo(@RequestBody MfDeskMsgItem mfDeskMsgItem)throws Exception;

	@RequestMapping(value = "/mfDeskMsgItem/insert")
	public void insert(@RequestBody MfDeskMsgItem mfDeskMsgItem)throws Exception;

	@RequestMapping(value = "/mfDeskMsgItem/getMsgListByOpNo")
	public Map<String, Object> getMsgListByOpNo(@RequestBody MfDeskMsgItem mfDeskMsgItem)throws Exception;

	@RequestMapping(value = "/mfDeskMsgItem/updateUseFlagAjax")
	public Map<String, Object> updateUseFlagAjax(@RequestBody MfDeskMsgItem mfDeskMsgItem)throws Exception;
	
	@RequestMapping(value = "/mfDeskMsgItem/updateMsgCount")
	public void updateMsgCount(@RequestBody MfDeskMsgItem mfDeskMsgItem)throws Exception;
}
