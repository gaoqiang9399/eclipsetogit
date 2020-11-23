package  app.component.deskinterface;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.desk.entity.MfDeskMsgItem;



/**
* Title: RulesInterface.java
* Description:
* @author:QiuZhao
* @Thu 2017-08-27 11:54:35
**/

@FeignClient("mftcc-platform-factor")
public interface DeskInterfaceFeign {
	/**
	 * 获取所有个人预警中心的关注项和未关注项
	 * @param mfDeskMsgItem
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deskInterface/getMsgList")
	List<MfDeskMsgItem> getMsgList(@RequestBody MfDeskMsgItem mfDeskMsgItem) throws Exception;
	/**
	 * 增加消息和减少消息接口
	 * @param mfDeskMsgItem
	 * @throws Exception
	 */
	@RequestMapping(value = "/deskInterface/updateMsgCount")
	void updateMsgCount(@RequestBody MfDeskMsgItem mfDeskMsgItem) throws Exception;
}


