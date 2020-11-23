package app.component.forewarningInterface;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import app.component.forewarning.entity.MfSysMsgInfo;

/**
 * Title: forewarningInterface.java Description:
 * 
 * @author:yangxingyi@dhcc.com.cn
 * @Wed Jul 05 11:08:48 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface ForewarningInterfaceFeign {

	/**
	 * 获取未读消息数量
	 * 
	 * @param mfSysMsgInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/forewarningInterface/getUnReadMsgCount")
	Long getUnReadMsgCount(@RequestBody MfSysMsgInfo mfSysMsgInfo) throws Exception;


	/**
	 * 添加新消息
	 * 
	 * @param mfSysMsgInfo
	 * @throws Exception
	 */
	@RequestMapping("/forewarningInterface/insertMsg")
	void insertMsg(@RequestBody MfSysMsgInfo mfSysMsgInfo) throws Exception;

	/**
	 * 标记消息为已读
	 * 
	 * @param mfSysMsgInfo
	 * @throws Exception
	 */
	@RequestMapping("/forewarningInterface/setMsgRead")
	void setMsgRead(@RequestBody MfSysMsgInfo mfSysMsgInfo) throws Exception;
	@RequestMapping("/forewarningInterface/insertMsgInfo")
	void insertMsgInfo(@RequestBody cn.mftcc.function.bean.MfSysMsgInfo mfSysMsgInfo) throws Exception;

}
