/**
 * Copyright (C) DXHM 版权所有
 * 文件名： BusViewInterface.java
 * 包名： app.component.busviewinterface
 * 说明：
 * @author Javelin
 * @date 2017-7-26 下午3:44:54
 * @version V1.0
 */ 
package app.component.busviewinterface;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 类名： BusViewInterface
 * 描述：
 * @author Javelin
 * @date 2017-7-26 下午3:44:54
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface BusViewInterfaceFeign {

	
	/**
	 * 方法描述： 根据申请号,页面参数获取业务视图map
	 * @param appId
	 * @param busEntrance 入口编号
	 * @param parmMap 页面参数
	 * 		appId=@appId@
	 *		wkfAppId=@wkfAppId@
	 *		operable=@operable@
	 *		cusNo=@cusNo@
	 *		busModel=@busModel@
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * 	headView 头部视图
	 * 	bodyView 业务主视图
	 * 	afftView 附属视图
	 * @author Javelin
	 * @date 2017-7-26 下午4:21:58
	 */
	@RequestMapping(value = "/busViewInterface/getBusViewMapByAppId")
	public Map<String, Object> getBusViewMapByAppId( @RequestBody Map<String, String> parmMap) throws Exception ;
	/**
	 * 获取客户视图，使用方式同上
	 * @param trenchId
	 * @param cusEntrence
	 * @param parmMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/busViewInterface/getCusViewMapByAppId")
	public Map<String, Object> getCusViewMapByAppId(@RequestParam("id")String id,@RequestParam("cusEntrance")String cusEntrance, @RequestBody Map<String, String> parmMap)  throws Exception ;
	
	/**
	 * 获取客户视图
	 * 
	 * @param 
	 * @param busEntrence
	 * @param parmMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/busViewInterface/getCommonViewMap")
	public Map<String, Object> getCommonViewMap(@RequestParam("generalClass") String generalClass,
			@RequestParam("busEntrance") String busEntrance, @RequestBody Map<String, String> parmMap) throws Exception;
	
	/**
	 * 根据所属大类，业务分类，业务入口获取视图
	 * 
	 * @param trenchId
	 * @param cusEntrence
	 * @param parmMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/busViewInterface/getViewMap")
	public Map<String, Object> getViewMap(@RequestParam("generalClass") String generalClass,@RequestParam("busClass") String busClass,@RequestParam("busEntrance") String busEntrance,@RequestBody Map<String, String> parmMap);
	
	/**
	 * 根据所属大类，业务分类，业务入口获取视图(器中业务分类包括公共数据)
	 * 
	 * @param trenchId
	 * @param cusEntrence
	 * @param parmMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/busViewInterface/getBusViewMap")
	public Map<String, Object> getBusViewMap(@RequestParam("generalClass") String generalClass,@RequestParam("busClass") String busClass,@RequestParam("busEntrance") String busEntrance,@RequestBody Map<String, String> parmMap);
}
