
package app.component.thirdservice.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.WaterIdUtil;




public class FactorTcphParmUtil {
	/**
	 * 方法描述： 获取配置的云平台的账号和密码 void
	 * 
	 * @author 仇招
	 * @date 2017-12-29 上午10:48:26
	 */
	public static Map<String, String> getSnAndPwd() {
		Map<String, String> parmMap;
		String sn = PropertiesUtil.getCloudProperty("tcph.username");
		String password = PropertiesUtil.getCloudProperty("tcph.password");
		String traceNo = WaterIdUtil.getWaterId();
		StringBuilder pwd = new StringBuilder();
		pwd.append(traceNo).append(sn).append(password);
		String pwdHd5 = DigestUtils.md5Hex(pwd.toString()).toUpperCase();
		parmMap = new HashMap<String, String>();
		parmMap.put("sn", sn);
		parmMap.put("traceNo", traceNo);
		parmMap.put("pwd", pwdHd5);
		return parmMap;
	}
	/**
	 * 获取签名
	 * @return
	 */
	public static Map<String,String> getSignParm(){
		Map<String ,String> parmMap=new HashMap<String, String>();
		String traceNo=WaterIdUtil.getWaterId();
		String cusAccount=PropertiesUtil.getCloudProperty("tcph.username");
		String password=PropertiesUtil.getCloudProperty("tcph.password");
		String source = traceNo.concat(cusAccount).concat(password);
		String signature =  DigestUtils.md5Hex(source).toUpperCase();
		parmMap.put("traceNo", traceNo);
		parmMap.put("cusAccount", cusAccount);
		parmMap.put("signature", signature);
		return parmMap;
	}
	/**
	 * 获取请求地址
	 * @return
	 */
	public static String  getServiceUrl(){
		return PropertiesUtil.getCloudProperty("tcph.url");
	}
}
