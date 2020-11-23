
package app.component.thirdservice.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.WaterIdUtil;




public class FactorNfaParmUtil {
	/**
	 * 获取签名
	 * @return
	 */
	public static Map<String,String> getSignParm(){
		Map<String ,String> parmMap=new HashMap<String, String>();
		String traceNo=WaterIdUtil.getWaterId();
		String cusAccount=PropertiesUtil.getCloudProperty("zm_nfa.acount");
		String password=PropertiesUtil.getCloudProperty("zm_nfa.password");
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
		return PropertiesUtil.getCloudProperty("zm_nfa.url");
	}
	
	

}
