/**
 * 文件名： IPhoneMessage.java
 * 包名： com.dingxinsoft.phoneMessage
 * 说明：
 * @作者 张治平
 * @日期 2014-9-9 下午2:09:47
 * @version V1.0
 */ 
package cn.mftcc.util.phoneMessage;

import com.google.gson.Gson;

/**
 * 类名： IPhoneMessage
 * 描述：
 * @作者 张治平
 * @日期 2014-9-9 下午2:09:47
 *
 *
 */
public interface IPhoneMessage {
	public Gson gson = new Gson();
	/**
	 * 方法描述： 发送短消息接口
	 * @param phoneNum  多个号码用,隔开
	 * @param text   消息文本
	 * @param companyName  公司名称 如【鼎信华铭】
	 * @return String[]  result[0] 0000:成功   1111:失败
	 * 					 result[1] 具体信息
	 * @作者 张治平
	 * @日期 2014-9-5 上午10:31:10
	 */
	public String[] sendMessage(String tel,String content);
	/**
	 * 方法描述： 发送短消息接口
	 * @param tel  
	 * @param content   消息文本
	 * @return String[]  result[0] 0000:成功   1111:失败
	 * 					 result[1] 具体信息
	 * @作者 刘宇博
	 * @日期 2018年1月16日10:20:12
	 */
	public String[] sendMessage(String tel, String content, MsgTypeEnum msgTypeEnum);


	
}

