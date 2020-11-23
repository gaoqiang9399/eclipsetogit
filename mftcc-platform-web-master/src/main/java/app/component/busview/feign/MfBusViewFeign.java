package app.component.busview.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.busview.entity.MfBusView;
import app.util.toolkit.Ipage;

/**
 * Title: MfBusViewBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 26 15:18:44 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfBusViewFeign {

	@RequestMapping(value = "/mfBusView/insert")
	public void insert(@RequestBody MfBusView mfBusView) throws Exception;

	@RequestMapping(value = "/mfBusView/delete")
	public void delete(@RequestBody MfBusView mfBusView) throws Exception;

	@RequestMapping(value = "/mfBusView/update")
	public void update(@RequestBody MfBusView mfBusView) throws Exception;

	@RequestMapping(value = "/mfBusView/getById")
	public MfBusView getById(@RequestBody MfBusView mfBusView) throws Exception;

	@RequestMapping(value = "/mfBusView/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfBusView") MfBusView mfBusView) throws Exception;

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
	@RequestMapping(value = "/mfBusView/getBusViewMapByAppId")
	public Map<String, Object> getBusViewMapByAppId(@RequestBody String appId,
			@RequestParam("busEntrance") String busEntrance, @RequestParam("parmMap") Map<String, String> parmMap)
			throws Exception;

	/**
	 * 获取客户视图
	 * 
	 * @param id
	 * @param cusEntrance
	 * @param parmMap
	 * @return
	 */
	@RequestMapping(value = "/mfBusView/getCusViewMapByAppId")
	public Map<String, Object> getCusViewMapByAppId(@RequestParam("id") String id,
			@RequestParam("cusEntrance") String cusEntrance, @RequestBody Map<String, String> parmMap) throws Exception;
}
