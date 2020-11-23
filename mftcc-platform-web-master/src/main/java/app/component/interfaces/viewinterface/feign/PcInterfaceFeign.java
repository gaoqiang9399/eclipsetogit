package app.component.interfaces.viewinterface.feign;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.app.entity.MfBusApply;

@FeignClient("mftcc-platform-factor")
public interface PcInterfaceFeign {
	/**
	 * 完善账户信息
	 * @param paramMap
	 * @param request 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/pcInterface/insertAccountInfo")
	public Map<String, Object> insertAccountInfo (@RequestBody Map<String, String> paramMap,@RequestParam("request")  HttpServletRequest request) throws Exception;
	/**
	 * 企业账户信息维护
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pcInterface/updateAccountInfo")
	public Map<String, Object> updateAccountInfo(@RequestBody Map<String, String> paramMap) throws Exception;
	/**
	 * 融资申请
	 * @param paramMap
	 * @param httpServletRequest 
	 * @return
	 */
	@RequestMapping(value = "/pcInterface/mfBusApplyInsert")
	public Map<String, Object> mfBusApplyInsert (@RequestBody Map<String, String> paramMap,@RequestParam("httpServletRequest")  HttpServletRequest httpServletRequest) throws Exception;
	/**
	 * 融资申请表单产品选择onchange事件需要填充的数据
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value = "/pcInterface/selectedKindNo")
	public Map<String, Object> selectedKindNo(@RequestBody Map<String, String> paramMap) throws Exception;
	/**
	 * 获取贷款投向页面
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pcInterface/getPageFinUse")
	public Map<String, Object> getPageFinUse(@RequestBody Map<String, String> paramMap) throws Exception;
	/**
	 * 获取客户最近的一次业务申请
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pcInterface/getRecentBusApply")
	public Map<String, Object> getRecentBusApply(@RequestBody Map<String, String> paramMap) throws Exception;
	/**
	 * 获取客户信息
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value = "/pcInterface/getCifInfoByCusNo")
	public Map<String, Object> getCifInfoByCusNo(@RequestBody Map<String, String> paramMap) throws Exception;
	/**
	 * 获取所有字典项，供前端缓存
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value = "/pcInterface/getDicParmList")
	public Map<String, Object> getDicParmList(@RequestBody Map<String, String> paramMap)  throws Exception;
	/**
	 * 获取融资申请列表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pcInterface/getMfBusApplyList")
	public Map<String, Object> getMfBusApplyList(@RequestBody Map<String, String> paramMap) throws Exception;
	/**
	 * 根据融资申请编号获取详情
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pcInterface/getMfBusApplyByAppId")
	public Map<String, Object> getMfBusApplyByAppId(@RequestBody Map<String, String> paramMap) throws Exception;
	/**
	 * 完善个人客户信息
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pcInterface/updateAccountInfo_pers")
	public Map<String, Object> updateAccountInfo_pers(@RequestBody Map<String, String> paramMap)  throws Exception;
	/**
	 * 获取行政区划数据
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pcInterface/getNmdAreaData")
	public Map<String, Object> getNmdAreaData(@RequestBody Map<String, String> paramMap)  throws Exception;
	/**
	 * 根据appId获取此业务申请的状态
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pcInterface/getAppSts")
	public MfBusApply getAppSts(@RequestBody Map<String, String> paramMap) throws Exception;
	/**
	 * 修改密码
	 * @param paramMap
	 * @param webCusLineRegBo 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pcInterface/updatePasswd")
	public Map<String, Object> updatePasswd (@RequestBody Map<String, String> paramMap)  throws Exception;

}
