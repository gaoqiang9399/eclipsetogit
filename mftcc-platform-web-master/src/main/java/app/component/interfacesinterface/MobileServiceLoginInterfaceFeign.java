package app.component.interfacesinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.interfaces.mobileserviceinterface.entity.TestBean;


/**
 * 类名： mobileservicecusinterface
 * 描述：移动服务接口
 * @author ywh
 * @date 2017-10-11 下午04:10:17
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MobileServiceLoginInterfaceFeign {
	/**
	 * 测试hession服务DEMO
	 * @param test
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-10-17 下午2:13:33
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/testHessionService")
	public List<TestBean> testHessionService(@RequestBody TestBean test) throws Exception;
	/**
	 * 发送短信获取验证码
	 * 发送成功后验证码存放在map.put("data",134561)
	 * @param cusTel 客户手机号
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/sendMessage")
	public Map<String, Object> sendMessage(@RequestBody String cusTel) throws Exception;
	/**
	 * 验证验证码
	 * @param cusTel 手机号
	 * @param verifyNum 验证码
	 * @return
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/validateVerifyCode")
	public Map<String, Object> validateVerifyCode(@RequestBody String cusTel,@RequestParam("verifyNum") String verifyNum);
	/**
	 * 密码登陆 通过密码登陆登陆注册
	 * @param openId
	 * @param verifyNum 验证码
	 * @param cusType 客户类型
	 * @param cusTel 客户手机号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/doLoginForPwd")
	public Map<String, Object> doLoginForPwd(@RequestBody String pwd,@RequestParam("cusTel") String cusTel) throws Exception;
	/**
	 * 微信登陆 通过openid登陆登陆注册
	 * 用户第一次请求时先注册再登陆
	 * 微信openid 和 tel都存在时 执行微信绑定操作
	 * @param openId
	 * @param cusType 客户类型
	 * @param cusTel 客户手机号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/doLoginForWX")
	public Map<String, Object> doLoginForWX(@RequestBody String openid) throws Exception;
	/**
	 * APP登陆 通过手机号登陆登陆注册
	 * 用户第一次请求时先注册再登陆
	 * @param verifyNum 验证码
	 * @param cusType 客户类型
	 * @param cusTel 客户手机号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/doLoginForAPP")
	public Map<String, Object> doLoginForAPP(@RequestBody String verifyNum,@RequestParam("cusType") String cusType,@RequestParam("cusTel") String cusTel,@RequestParam("osType") String osType,@RequestParam("channelSourceNo") String channelSourceNo) throws Exception;
	/**
	 * APPwx登陆 通过AppWxOpenid登陆
	 * 用户第一次请求时先注册再登陆
	 * @param verifyNum 验证码
	 * @param cusType 客户类型
	 * @param AppWxOpenid 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/doLoginForAPPWX")
	public Map<String, Object> doLoginForAPPWX(@RequestBody String appWxOpenid) throws Exception;
	/**
	 * APPwx登陆 通过AppQQOpenid登陆
	 * 用户第一次请求时先注册再登陆
	 * @param verifyNum 验证码
	 * @param cusType 客户类型
	 * @param AppQQOpenid 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/doLoginForQQ")
	public Map<String, Object> doLoginForQQ(@RequestBody String appQQOpenid) throws Exception;
	/**
	 * QQ绑定或注册
	 * @param tel
	 * @param verifyNum
	 * @param openid
	 * @param cusType 客户类型
	 * @param httpServletRequest
	 * @param osType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/doBindTelPhoneForQQ")
	public Map<String, Object> doBindTelPhoneForQQ(@RequestBody String tel,@RequestParam("verifyNum")  String verifyNum,@RequestParam("openid") String openid,@RequestParam("cusType") String cusType,@RequestParam("osType") String osType,@RequestParam("channelSourceNo") String channelSourceNo) throws Exception;
	/**
	 * 微信绑定或注册
	 * @param tel
	 * @param verifyNum
	 * @param openid
	 * @param cusType 客户类型
	 * @param httpServletRequest
	 * @param osType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/doBindTelPhoneForWX")
	public Map<String, Object> doBindTelPhoneForWX(@RequestBody String tel,@RequestParam("verifyNum")  String verifyNum,@RequestParam("openid") String openid,@RequestParam("cusType") String cusType,@RequestParam("osType") String osType,@RequestParam("channelSourceNo") String channelSourceNo) throws Exception;
	/**
	 * 修改手机号
	 * @param tel 原手机号
	 * @param newTel 新号码
	 * @param verifyNum 新号码验证码
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/updateTelPhone")
	public Map<String, Object> updateTelPhone(@RequestBody String tel,@RequestParam("newTel")  String newTel,@RequestParam("verifyNum") String verifyNum) throws Exception;
	/**
	 * 微信公众号绑定或注册
	 * @param tel
	 * @param verifyNum
	 * @param openid
	 * @param cusType 客户类型
	 * @param httpServletRequest
	 * @param osType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/doBindTelPhoneForWXGZ")
	public Map<String, Object> doBindTelPhoneForWXGZ(@RequestBody String tel,@RequestParam("verifyNum")  String verifyNum,@RequestParam("openid") String openid,@RequestParam("cusType") String cusType,@RequestParam("osType") String osType) throws Exception;
	/**
	 * 修改密码
	 * @param pwd 原密码
	 * @param newTel 新密码
	 * @param cusTel 手机号
	 * @param verifyNum 验证码
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/updateCusPwd")
	public Map<String, Object> updateCusPwd(@RequestBody String pwd,@RequestParam("newPwd")  String newPwd,@RequestParam("cusTel") String cusTel,@RequestParam("verifyNum") String verifyNum) throws Exception;
	/**
	 * html5分享页面注册引导登录
	 * @param verifyNum   验证码  
	 * @param cusType     客户类型
	 * @param cusTel      手机号
	 * @param random      图片验证码
	 * @param recomTel    推荐手机号
	 * @param cusName     客户姓名
	 * @param httpServletRequest
	 * @return
	 * @author Jiasl
	 * @date 2017-11-06 下午5:05:07
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/doLoginForHTML5")
	public Map<String, Object> doLoginForHTML5(@RequestBody String verifyNum,@RequestParam("cusType") String cusType,@RequestParam("cusTel") String cusTel,@RequestParam("random") String random,@RequestParam("recomTel") String recomTel,@RequestParam("cusName") String cusName) throws Exception;
	
	/**
	 * 
	 * 方法描述： 判断微信是否绑定
	 * @param openId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-13 下午1:44:52
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/isBindForWX")
	public Map<String, Object> isBindForWX(@RequestBody String openId) throws Exception;


	/**
	 * 
	 * 方法描述： 判断QQ是否绑定
	 * @param openId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-13 下午1:44:52
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/isBindForQQ")
	public Map<String, Object> isBindForQQ(@RequestBody String openId) throws Exception;
	/**
	 * 
	 * 方法描述： B端登陆
	 * @param phoneNo
	 * @param code
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-21 下午4:40:19
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/loginForManager")
	public Map<String, Object> loginForManager(@RequestBody String phoneNo,@RequestParam("code") String code);
	/**
	 * 
	 * 方法描述： pad端账户登录
	 * @param opNo
	 * @param pwd
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-4 下午4:54:22
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/loginForPad")
	public Map<String, Object> loginForPad(@RequestBody String opNo,@RequestParam("pwd") String pwd);
	/**
	 * 新增支付密码
	 * @param cusNo
	 * @param payPwd
	 * @param cusName
	 * @param cusTel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/insertPayPwd")
	public Map<String, Object> insertPayPwd(@RequestBody String cusNo,@RequestParam("payPwd") String payPwd,@RequestParam("cusName") String cusName,@RequestParam("cusTel") String cusTel) throws Exception;
	
	/**
	 * 根据客户号来更新密码
	 * @param cusNo
	 * @param payPwd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/updatePayPwd")
	public Map<String, Object> updatePayPwd(@RequestBody String cusNo,@RequestParam("payPwd") String payPwd) throws Exception;
	
	/**
	 * 根据客户号来更新密码
	 * @param cusNo
	 * @param payPwd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/getPayPwdByCusNo")
	public Map<String, Object> getPayPwdByCusNo(@RequestBody String cusNo) throws Exception;

	/**
	 * 根据手机号来更新密码
	 * @param cusTel
	 * @param payPwd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/getPayPwdByCusTel")
	public Map<String, Object> getPayPwdByCusTel(@RequestBody String cusTel) throws Exception;
	
	/**
	 * 校验验证码
	 * @param cusTel
	 * @param verifyNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/checkMessage")
	Map<String, Object> checkMessage(@RequestBody String cusTel,@RequestParam("verifyNum")  String verifyNum)throws Exception;
	/**
	 * 校验支付密码
	 * @param cusTel
	 * @param pwdValue
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceLoginInterface/checkPayPwd")
	public Map<String, Object> checkPayPwd(@RequestBody String cusNo,@RequestParam("pwdValue")  String pwdValue)throws Exception;
}
