package cn.mftcc.util;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.base.SpringUtil;
import config.YmlConfig;




public class PropertiesUtil{

	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	private static Properties properties=null;
	
	/**
	 * 
	 * 方法描述： 获取tomcat根目录下msgConfig.properties的根目录路径信息
	 * @param key 配置信息的key值
	 * @return 配置信息的value
	 * String
	 * @author 沈浩兵
	 * @throws Exception 
	 * @date 2016-10-17 上午10:23:28
	 */
	/**
	 * 
	 * @param key-配置文件中对应的属性名。
	 * @return String
	 */
	public static String getMsgConfigProperty(String key){
		return getProperties(PropTypeEnum.MsgConfig, key);
	}
	public static String getWebServiceProperty(String key){
		return getProperties(PropTypeEnum.WebService, key);
	}
	public static String getCloudProperty(String key){
		return getProperties(PropTypeEnum.Cloud, key);
	}
	
	public static String getEmailProperty(String key){
		return getProperties(PropTypeEnum.Email, key);
	}
	
	public static String getSysParamsProperty(String key){
		return getProperties(PropTypeEnum.SysParams, key);
	}
	public static String getDingConfigProperty(String key){
		return getProperties(PropTypeEnum.DingConfig, key);
	}
	
	public static String getPageOfficeConfigProperty(String key){
		return getProperties(PropTypeEnum.pageOfficeConfig, key);
	}
	public static String getZMParamProperty(String key){
		return getProperties(PropTypeEnum.zmParams, key);
	}
	//upload配置文件
	public static String getUploadProperty(String key){
		return getProperties(PropTypeEnum.upload, key);
	}
	//征信查询读取配置文件
	public static String getCreditQueryProperty(String key){
		return getProperties(PropTypeEnum.creditQueryConfig, key);
	}
	
	public static String getSsqProperty(String key){
		return getProperties(PropTypeEnum.ssq, key);
	}
	//第三方划扣具体标识哪家配置文件
		public static String getThirdPayProperty(String key){
			return getProperties(PropTypeEnum.thirdPay, key);
		}	
	//esign读取配置文件
	public static String getEsignProperty(String key){
		return getProperties(PropTypeEnum.esign, key);
	}
	
	// officePage配置文件
	public static String getOfficeConfigProperty(String key) {
		return getProperties(PropTypeEnum.officeConfig, key);
	}
	//baofoo配置文件
	public static String getBaoFooProperty(String key){
		return getProperties(PropTypeEnum.baoFoo, key);
	}
		
	
	/**
	 * 公用接口，根据要读取的类别返回对应配置文件中对应key的值。
	 * @param propTypeEnum
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	private static String getProperties(PropTypeEnum propTypeEnum, String key) {
		try {
			if (propTypeEnum != null) {
				String propertiesName = propTypeEnum.getPropertiesName();
				YmlConfig yc = SpringUtil.getBean(YmlConfig.class);
				String methodName = "get"+ propertiesName.substring(0, 1).toUpperCase()+propertiesName.substring(1);
				Method method = yc.getClass().getMethod(methodName, new Class[0]);
				
				Map<String,String> uploadMap = (Map<String, String>) method.invoke(yc, new Object[0]);
				return uploadMap.get(key);
			}
		} catch (Exception e) {
			System.err.println("读取配置文件内容发生错误："+ propTypeEnum.getPropertiesName() + "；错误消息：" + e.getMessage());
		}
		return null;
	}	
	
	private enum PropTypeEnum {
		/**
		 * webservice
		 */
		WebService("webservice"),
		/**
		 * msgConfig
		 */
		MsgConfig("msgConfig"),
		/**
		 * cloud
		 */
		Cloud("cloud"),
		/**
		 * emailConfig
		 */
		Email("emailConfig"),
		/**
		 * sysParams
		 */
		SysParams("sysParams"),
		/**
		 * ding
		 */
		DingConfig("ding"),
		
		/**
		 *中茂项目参数配置文件
		 */
		zmParams("zhongmaoParams"),
		/**
		 *华北小贷项目参数配置文件
		 */
		ssq("thirdparty/ssq"),

		/**
		 *中茂esign电子签章参数配置文件
		 */
		esign("thirdparty/esign"),
		

		/**
		 *第三方参数配置文件
		 */
		thirdPay("thirdPay/ThirdPay"),

		/**
		 *baofoo配置文件
		 */
		baoFoo("thirdPay/baofoo/baoFoo"),
		/**
		 *pageoffice
		 */
		pageOfficeConfig("pageoffice"),
		/**
		 *creditQuery
		 */
		creditQueryConfig("creditQuery"),
		/**
		 *upload
		 */
		upload("upload"),
		/**
		 *officeConfig
		 */
		officeConfig("officeConfig");
		
		private String propertiesName;
		
		private PropTypeEnum(String propertiesName){
			this.propertiesName = propertiesName;
		}
		
		public String getPropertiesName() {
			return propertiesName;
		}
	}
	/**
	 * 
	 * 方法描述： 读取属性文件配置数据信息
	 * @param key 配置信息key值
	 * @return
	 * String 配置信息Value值
	 * @author XieZhenGuo
	 * @date 2013-3-27 上午10:35:23
	 */

	public static String getProperties(String key){
		return getJarProperties().getProperty(key);
	}
	/**
	 * 
	 * 方法描述： 封装jar包内部属性文件数据信息 
	 * @return
	 * Properties
	 * @author XieZhenGuo
	 * @date Jun 14, 2013 6:13:01 PM
	 */
	private static Properties getJarProperties(){
		if(null==properties){
			properties=new Properties();
			properties.put("amountClass","caiwu.dingxin.httpclient.impl.AmountServicesImpl");
			properties.put("psotmethodClass","caiwu.dingxin.httpclient.impl.HeightPostMethodImpl");
			properties.put("send_sms_class","caiwu.dingxin.httpclient.impl.SendSmsServicesImpl");
			properties.put("custImplClass","caiwu.dingxin.httpclient.impl.CusManageServicesImpl");
			properties.put("status_100","客户必须继续发出请求。");
			properties.put("status_101","客户要求服务器根据请求转换HTTP协议版本。");
			properties.put("status_200","交易成功。");
			properties.put("status_201","提示知道新文件的URL。");
			properties.put("status_202","接受和处理、但处理未完成。");
			properties.put("status_203","返回信息不确定或不完整。");
			properties.put("status_204","请求收到，但返回信息为空。");
			properties.put("status_205","服务器完成了请求，用户代理必须复位当前已经浏览过的文件。");
			properties.put("status_206","服务器已经完成了部分用户的GET请求。");
			properties.put("status_300","请求的资源可在多处得到。");
			properties.put("status_301","删除请求数据。");
			properties.put("status_302","在其他地址发现了请求数据。");
			properties.put("status_303","建议客户访问其他URL或访问方式。");
			properties.put("status_304","客户端已经执行了GET，但文件未变化。");
			properties.put("status_305","请求的资源必须从服务器指定的地址得到。");
			properties.put("status_306","前一版本HTTP中使用的代码，现行版本中不再使用。");
			properties.put("status_307","申明请求的资源临时性删除。");
			properties.put("status_400","错误请求，如语法错误或者请求的服务为开启。");
			properties.put("status_401","请求授权失败。");
			properties.put("status_402","保留有效ChargeTo头响应。");
			properties.put("status_403","请求不允许。");
			properties.put("status_404","没有发现文件、查询或URl。");
			properties.put("status_405","用户在Request-Line字段定义的方法不允许。");
			properties.put("status_406","根据用户发送的Accept拖，请求资源不可访问。");
			properties.put("status_407","类似401，用户必须首先在代理服务器上得到授权。");
			properties.put("status_408","客户端没有在用户指定的饿时间内完成请求。");
			properties.put("status_409","对当前资源状态，请求不能完成。");
			properties.put("status_410","服务器上不再有此资源且无进一步的参考地址。");
			properties.put("status_411","服务器拒绝用户定义的Content-Length属性请求。");
			properties.put("status_412","一个或多个请求头字段在当前请求中错误。");
			properties.put("status_413","请求的资源大于服务器允许的大小。");
			properties.put("status_414","请求的资源URL长于服务器允许的长度。");
			properties.put("status_415","请求资源不支持请求项目格式。");
			properties.put("status_416","请求中包含Range请求头字段，在当前请求资源范围内没有range指示值，请求也不包含If-Range请求头字段。");
			properties.put("status_417","服务器不满足请求Expect头字段指定的期望值，如果是代理服务器，可能是下一级服务器不能满足请求。");
			properties.put("status_500","服务器产生内部错误。");
			properties.put("status_501","服务器不支持请求的函数。");
			properties.put("status_502","服务器暂时不可用，有时是为了防止发生系统过载。");
			properties.put("status_503","服务器过载或暂停维修。");
			properties.put("status_504","关口过载，服务器使用另一个关口或服务来响应用户，等待时间设定值较长。");
			properties.put("status_505","服务器不支持或拒绝支请求头中指定的HTTP版本。");
		}
		return properties;
	}


}
