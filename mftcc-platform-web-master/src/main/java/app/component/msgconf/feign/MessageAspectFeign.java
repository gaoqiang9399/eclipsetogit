package app.component.msgconf.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("mftcc-platform-factor")
public interface MessageAspectFeign {
	/**
	@RequestMapping(value = "/messageAspect/getMfMsgListenerAll")
	public List<MfMsgListener> getMfMsgListenerAll(@RequestBody MfMsgListener mfMsgListener) throws Exception;
	@RequestMapping(value = "/messageAspect/getMainTableNameTablePk")
	public Map<String,Object> getMainTableNameTablePk(@RequestBody String listenerArgs) throws Exception;
	@RequestMapping(value = "/messageAspect/setAttributeValue")
	public Map<String,Object> setAttributeValue@RequestParam("map") (@RequestBody Map<String,Object> map,@RequestParam("className") String className,@RequestParam("attributeName") String attributeName,@RequestParam("returnVal") Object returnVal) throws Exception;
	@RequestMapping(value = "/messageAspect/getMfMsgUtil")
	public List<MfMsgUtil> getMfMsgUtil(@RequestBody String listenerArgs,@RequestParam("pkValue") String pkValue,@RequestParam("pkName") String pkName) throws Exception;
	**/
}
