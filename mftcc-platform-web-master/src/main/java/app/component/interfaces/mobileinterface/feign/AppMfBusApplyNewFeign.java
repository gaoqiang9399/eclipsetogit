package app.component.interfaces.mobileinterface.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.util.toolkit.Ipage;

/**
 * 
 * 处理融资申请的Bo类
 * @author zhang_dlei
 *
 */
@FeignClient("mftcc-platform-factor")
public interface AppMfBusApplyNewFeign{
	
	
	@RequestMapping(value = "/appMfBusApplyNew/findByPage")
	public Map<String, Object> findByPage(@RequestBody  Ipage ipage,@RequestParam("cusNo") String cusNo,@RequestParam("cusTel") String cusTel,@RequestParam("stage") String stage,@RequestParam("regNo") String regNo) throws Exception;

	@RequestMapping(value = "/appMfBusApplyNew/findByPage1")
	public Map<String, Object> findByPage1(@RequestBody  Ipage ipage,@RequestParam("cusNo") String cusNo) throws Exception;

	@RequestMapping(value = "/appMfBusApplyNew/findByPageForFlag")
	public Map<String, Object> findByPageForFlag(@RequestBody  Ipage ipage,@RequestParam("cusNo") String cusNo) throws Exception;

	@RequestMapping(value = "/appMfBusApplyNew/isInBusApply")
	public Map<String, Object> isInBusApply(@RequestBody  String cusNo,@RequestParam("kindNo") String kindNo,@RequestParam("terminalType") String terminalType) throws Exception;

	@RequestMapping(value = "/appMfBusApplyNew/isInBusPact")
	public Map<String, Object> isInBusPact(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/appMfBusApplyNew/getBusCreditInfo")
	public Map<String, Object> getBusCreditInfo(@RequestBody  String cusNo,@RequestParam("pactNo") String pactNo) throws Exception;

	@RequestMapping(value = "/appMfBusApplyNew/getRepayListByCusNo")
	public Map<String, Object> getRepayListByCusNo(@RequestBody  String cusNo) throws Exception;
	
	

	
}
