/**
 * Copyright (C) DXHM 版权所有
 * 文件名： IThirdPartyServices.java
 * 包名： caiwu.dingxin.httpclient
 * 说明：
 * @author XieZhenGuo
 * @date 2013-5-11 下午2:03:01
 * @version V1.0
 */ 
package cn.mftcc.util.ThirdParty;

import java.util.Map;

/**
 * 类名： IThirdPartyServices
 * 描述：第三方调用方法
 * @author XieZhenGuo
 * @date 2013-5-11 下午2:03:01
 *
 *
 */
public interface IThirdPartyServices {

	/**
	 * 
	 * 方法描述： 处理远程调用方法
	 * @param path 远程调用路径 如：http://127.0.0.1:8080/messager/message_receiveMsg.action
	 * @param parmMap 访问参数集合对象
	 * @return
	 * String[] [0]:0000,标识成功，1111,标识失败；[1]:状态对应的结果信息 
	 * @author XieZhenGuo
	 * @date 2013-5-11 下午2:05:36
	 */
	public String[] allPowerful(String path,Map<String, String> parmMap); 
}
