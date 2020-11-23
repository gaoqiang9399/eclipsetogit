package app.component.ncfgroup.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mftcc-platform-factor")
public interface NcfTCAPIFeign {
	/**
	 * 是否已经获取到公积金状态接口
	 * 
	 * @param cusNo
	 * @return 返回值为true时则获取数据成功 返回值为false时，没有授权，需要走授权流程
	 *         抛出异常时，没有获取成功，需要重新调用获取接口，可反复调此接口
	 * @throws Exception
	 */
	@RequestMapping("/bizWhitelist/hasGongjijinFund")
	public Map<String, Object> hasGongjijinFund(@RequestBody String cusNo) throws Exception;

	/**
	 * 是否已经获取到公积金状态接口
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bizWhitelist/hasGongjijinFundB")
	public Map<String, Object> hasGongjijinFundB(@RequestBody String cusNo) throws Exception;

	@RequestMapping("/bizWhitelist/getGongjijinFundB")
	public Map<String, Object> getGongjijinFundB(@RequestBody String cusNo) throws Exception;

	/**
	 * 根据客户号获取到公积金获取内容
	 * 
	 * @param cusNo
	 * @return 返回值为null时，没有授权，需要走授权流程 抛出异常时，没有获取成功，需要重新调用获取接口，可反复调此接口
	 * @throws Exception
	 */
	@RequestMapping("/bizWhitelist/getGongjijinFund")
	public Map<String, Object> getGongjijinFund(@RequestBody String cusNo) throws Exception;

	/**
	 * 根据客户号获取芝麻分
	 * 
	 * @param cusNo
	 * @return
	 */
	@RequestMapping("/bizWhitelist/getSesamePoints")
	public Map<String, Object> getSesamePoints(@RequestBody String cusNo) throws Exception;

	/**
	 * 根据客户号获取云运营商状态
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bizWhitelist/getOrderStatus")
	public Map<String, Object> getOrderStatus(@RequestBody String cusNo) throws Exception;

	/**
	 * 根据客户号获取客户运营商报告 并统计运营商信息
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bizWhitelist/getUserReport")
	public Map<String, Object> getUserReport(@RequestBody String cusNo) throws Exception;

	/**
	 * 根据客户号获取客户运营商状态
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bizWhitelist/getUserReportStatus")
	public Map<String, Object> getUserReportStatus(@RequestBody String cusNo) throws Exception;

	/**
	 * 根据客户号获取客户授权orderId,输入验证码验证
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bizWhitelist/verifyLoginAsync")
	public Map<String, Object> verifyLoginAsync(@RequestBody String cusNo, @RequestParam("code") String code,
			@RequestParam("orderId") String orderId) throws Exception;

	/**
	 * 调用风云决
	 * 
	 * @param paramMap
	 *            扩展参数，
	 *            可以传null。key:deviceId设备号;phoneType用户手机系统类型（ios,android,other）;phoneIP:用户手机IP地址
	 * @param cusNo
	 *            必须参数
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-9-15 下午2:21:16
	 */
	@RequestMapping("/bizWhitelist/doFengyunjue")
	public Map<String, Object> doFengyunjue(@RequestBody Map<String, Object> paramMap, @RequestParam("cusNo") String cusNo)
			throws Exception;

	/**
	 * 调用风云决(@RequestBody 汇达产品)
	 * 
	 * @param paramMap
	 *            扩展参数，
	 *            可以传null。key:deviceId设备号;phoneType用户手机系统类型（ios,android,other）;phoneIP:用户手机IP地址
	 * @param cusNo
	 *            必须参数
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-9-15 下午2:21:16
	 */
	@RequestMapping("/bizWhitelist/doFengyunjueForHd")
	public Map<String, Object> doFengyunjueForHd(@RequestBody Map<String, Object> paramMap,
			@RequestParam("cusNo") String cusNo) throws Exception;

	@RequestMapping("/bizWhitelist/doRiskAssessmentTcredit")
	public Map<String, Object> doRiskAssessmentTcredit(@RequestBody Map<String, String> paramMap) throws Exception;
}
