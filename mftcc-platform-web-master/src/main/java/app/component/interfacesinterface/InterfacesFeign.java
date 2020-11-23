package app.component.interfacesinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.interfaces.mobileinterface.entity.MfAppCusIntegrity;
import app.component.interfaces.mobileinterface.entity.MfAppCusMng;
import app.component.interfaces.mobileinterface.entity.MfAppOperateLog;
import app.component.interfaces.mobileinterface.entity.MfCusRecommend;
import app.component.interfaces.mobileinterface.entity.MfThirdServiceLog;
import app.component.interfaces.mobileinterface.entity.MfThirdServiceResult;
import app.component.interfaces.mobileinterface.entity.WebCusLineReg;
import app.util.toolkit.Ipage;

/**
 * Title: interfaces.java Description:
 * 
 * @author:张冬磊@dhcc.com.cn
 * @Sat Jul 29 09:19:29 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface InterfacesFeign {

	/**
	 * 移动端:验证手机是否认证
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception;
	 */
	@RequestMapping(value = "/interfaces/checkThirdData")
	public Map<String, Object> checkThirdData(@RequestBody String cusNo) throws Exception;

	@RequestMapping(value = "/interfaces/insertMfThirdServiceLog")
	public void insertMfThirdServiceLog(@RequestBody MfThirdServiceLog mfThirdServiceLog) throws Exception;

	@RequestMapping(value = "/interfaces/getMfThirdServiceLogByCusNo")
	public MfThirdServiceLog getMfThirdServiceLogByCusNo(@RequestBody MfThirdServiceLog mfThirdServiceLog)
			throws Exception;

	@RequestMapping(value = "/interfaces/insertMfThirdServiceResult")
	public void insertMfThirdServiceResult(@RequestBody MfThirdServiceResult mfThirdServiceResult) throws Exception;

	@RequestMapping(value = "/interfaces/getMfThirdServiceResult")
	public MfThirdServiceResult getMfThirdServiceResult(@RequestBody MfThirdServiceResult mfThirdServiceResult)
			throws Exception;

	@RequestMapping(value = "/interfaces/getByCusNoAndMethod")
	public MfThirdServiceLog getByCusNoAndMethod(@RequestBody MfThirdServiceLog mfThirdServiceLog) throws Exception;

	@RequestMapping(value = "/interfaces/getByCusNoAndThird")
	public MfThirdServiceLog getByCusNoAndThird(@RequestBody MfThirdServiceLog mfThirdServiceLog) throws Exception;

	@RequestMapping(value = "/interfaces/getListByCusNoAndMethod")
	public List<MfThirdServiceLog> getListByCusNoAndMethod(@RequestBody MfThirdServiceLog mfThirdServiceLog)
			throws Exception;

	@RequestMapping(value = "/interfaces/findList")
	public List<MfAppOperateLog> findList(@RequestBody MfAppOperateLog mfAppOperateLog) throws Exception;


	/**
	 * 获取手机号，身份证号在不同设备号码申请次数
	 * 
	 * @param parmMap
	 *            统一使用key :"num"(@RequestBody 手机号或身份证号码) operateTrait 操作类型
	 *            (@RequestBody 不传默认申请操作)
	 * @return resultMap key:appCount (@RequestBody 返回数值在appCount)
	 * @throws Exception;
	 * @author MaHao
	 * @date 2017-9-15 上午10:06:39
	 */
	@RequestMapping(value = "/interfaces/getAppCountOnDeferentDevice")
	public Map<String, Object> getAppCountOnDeferentDevice(@RequestBody Map<String, String> parmMap) throws Exception;

	/**
	 * 24小时内同一设备登陆的 不同号码 的个数
	 * 
	 * @param parmMap
	 *            key:days 提前天数 (@RequestBody 默认1天)operateTrait操作类型 默认登陆操作
	 * @return resultMap:{deviceId:{idNum:666,bankNum:666,phoneNum:666}}
	 * @throws Exception;
	 * @author MaHao
	 * @date 2017-9-13 上午11:12:17
	 */
	@RequestMapping(value = "/interfaces/getLoginCountOneDeviceDay")
	public Map<String, Object> getLoginCountOneDeviceDay(@RequestBody Map<String, String> parmMap) throws Exception;

	@RequestMapping(value = "/interfaces/getByCusNo")
	public WebCusLineReg getByCusNo(@RequestBody WebCusLineReg webCusLineReg) throws Exception;

	/**
	 * 根据手机号和合同号获取授信信息
	 * 
	 * @param cusTel
	 * @param pactNo
	 * @return
	 * @throws Exception;
	 */
	@RequestMapping(value = "/interfaces/getBusCreditInfo")
	public Map<String, Object> getBusCreditInfo(@RequestBody String cusTel, @RequestParam("pactNo") String pactNo)
			throws Exception;

	/**
	 * 根据购机号获取推荐人列表
	 * 
	 * @param mfCusRecommend
	 * @return
	 * @throws Exception;
	 */
	@RequestMapping(value = "/interfaces/getCusRecommends")
	public List<MfCusRecommend> getCusRecommends(@RequestBody MfCusRecommend mfCusRecommend) throws Exception;

	/**
	 * 插入html分享登录的客户信息
	 * 
	 * @param mfCusRecommend
	 * @throws Exception;
	 * @author Jiasl
	 */
	@RequestMapping(value = "/interfaces/insertMfCusRecommend")
	public void insertMfCusRecommend(@RequestBody MfCusRecommend mfCusRecommend) throws Exception;

	/**
	 * 更新客户信息完整度信息
	 * 
	 * @param mfAppCusIntegrity
	 * @throws Exception;
	 */
	@RequestMapping(value = "/interfaces/update")
	public void update(@RequestBody MfAppCusIntegrity mfAppCusIntegrity) throws Exception;

	/**
	 * 根据客户号获取客户信息完整度信息
	 * 
	 * @param mfAppCusIntegrity
	 * @throws Exception;
	 */
	@RequestMapping(value = "/interfaces/getById")
	public MfAppCusIntegrity getById(@RequestBody MfAppCusIntegrity mfAppCusIntegrity) throws Exception;

	/**
	 * 
	 * 方法描述： 移动端我的分享信息。包括分页推荐人列表信息
	 * 
	 * @param ipage
	 * @param mfCusRecommend
	 * @return
	 * @throws Exception;
	 *             Map<String, Object>
	 * @author 沈浩兵
	 * @date 2017-11-17 下午3:15:13
	 */
	@RequestMapping(value = "/interfaces/getCusRecommendListByPage")
	public Map<String, Object> getCusRecommendListByPage(@RequestBody Ipage ipage,
			@RequestParam("mfCusRecommend") MfCusRecommend mfCusRecommend) throws Exception;

	@RequestMapping(value = "/interfaces/insertOperate")
	public void insertOperate(@RequestBody MfAppOperateLog mfAppOperateLog) throws Exception;

	/**
	 * 
	 * 方法描述： 获取当天潜在客户最少的操作员
	 * 
	 * @return
	 * @throws Exception;
	 *             MfAppCusMng
	 * @author 沈浩兵
	 * @date 2017-12-5 下午8:19:21
	 */
	@RequestMapping(value = "/interfaces/getMfAppCusMngByMin")
	public MfAppCusMng getMfAppCusMngByMin() throws Exception;

	/**
	 * 
	 * 方法描述： 插入客户通讯录
	 * 
	 * @param ajaxData
	 * @return
	 * @throws Exception;
	 *             Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-12-7 下午6:20:41
	 */
	@RequestMapping(value = "/interfaces/insertPhoneBook")
	public Map<String, Object> insertPhoneBook(@RequestBody String ajaxData) throws Exception;

}
