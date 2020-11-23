package cn.mftcc.util;

/**
 * Copyright (C) DXHM 版权所有
 * 文件名： AccountPostMethodUtil.java
 * 包名： caiwu.dingxin.util
 * 说明：
 * @author XieZhenGuo
 * @date 2013-3-25 下午4:54:19
 * @version V1.0
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

//import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.conn.HttpHostConnectException;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.protocol.HTTP;

/**
 * 类名： AccountPostMethodUtil 描述：远程调用处理类
 * 
 * @author XieZhenGuo
 * @date 2013-3-25 下午4:54:19
 * 
 * 
 */
public class AccountPostMethodUtil {
//	private static Log log = LogFactory.getLog(AccountPostMethodUtil.class);

	/**
	 * 
	 * 方法描述： 远程调用方法
	 * @param valuePairs 参数
	 * @param path 路径
	 * @return
	 * String[] 返回远程调用返回值
	 * @author XieZhenGuo
	 * @date 2013-3-25 下午5:27:19
	 */
	public static String[] dealPostMethod(List<NameValuePair> valuePairs, String path) {
		// System.err.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
		String result[];
		result = new String[2];
		PostMethod post = null;
		try {
			HttpClient client = new HttpClient();
//			HttpConnectionManagerParams cmp = client.getHttpConnectionManager()
//					.getParams();
//			cmp.setConnectionTimeout(5000);// http连接超时
//			cmp.setSoTimeout(5000);// 读数据超时
			client.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");// 编码

			post = new PostMethod(path);
			NameValuePair[] paramsPair = valuePairs
					.toArray(new NameValuePair[0]);//(NameValuePair[])valuePairs.toArray()
			post.addParameters(paramsPair);
			int status;
			status = client.executeMethod(post);
			// TODO 处理响应

			if (status >= 200 && status <= 299) {
				result[0] = "0000";
			} else {
				result[0] = "1111";
				result[1] = getStatsContent(status);
//				if (log.isErrorEnabled())
//					log.error("远程请求失败：" + result[1] + " #路径：" + path);
				return result;
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					post.getResponseBodyAsStream(), post.getResponseCharSet()));
			String line = null;
			int i = 0;
			while ((line = reader.readLine()) != null) {
//				if (log.isDebugEnabled())
//					log.debug(line);
				if (i < 2) {
					result[i++] = line;
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			result[0] = "1111";
			result[1] = e.getMessage();
//			if (log.isErrorEnabled())
//				log.error(e.getMessage(), e);
		} finally {
			if (null != post) {
				post.releaseConnection();
			}
		}
		return result;
	}

	/**
	 * 
	 * 方法描述： 远程调用方法
	 * 
	 * @param valuePairs
	 *            参数
	 * @param path
	 *            路径
	 * @return String[] 返回远程调用返回值
	 * @author XieZhenGuo
	 * @date 2013-3-25 下午5:27:19
	 */
	// @SuppressWarnings("deprecation")
	// public synchronized static String[] dealPostMethod(List <NameValuePair>
	// valuePairs, String path){
	// HttpClient httpClient =new DefaultHttpClient();
	// String[] result=new String[2];
	// try {
	// // String url =
	// path;//"http://192.168.2.5:8888/wows/servlet/SuretyServicesSvt";
	// HttpPost httpPost=new HttpPost(path);
	// HttpEntity entity=new UrlEncodedFormEntity(valuePairs,HTTP.UTF_8);
	// httpPost.setEntity(entity);
	// // 执行postMethod
	// HttpResponse response = httpClient.execute(httpPost);
	// // 解析返回结果数据
	// entity=response.getEntity();
	// int
	// status=response.getStatusLine().getStatusCode();
	// if(log.isDebugEnabled())
	// log.debug("response code 响应状态："+status);
	// // 处理服务器返回状态数据
	// if(status>=200&&status<=299){
	// result[0]="0000";
	// }else{
	// result[0]="1111";
	// result[1]=getStatsContent(status);
	// if(log.isErrorEnabled())
	// log.error("远程请求失败："+result[1]+" #路径："+path);
	// return result;
	// }
	// BufferedReader reader = new BufferedReader(new
	// InputStreamReader(entity.getContent(),HTTP.UTF_8));
	// String line = null;
	// int i=0;
	// while ((line = reader.readLine()) != null) {
	// if(log.isInfoEnabled())
	// log.info(line);
	// if(i<2){
	// result[i++]=line;
	// }
	// }
	// if (entity != null) {
	// entity.consumeContent();
	// }
	// }catch(ClientProtocolException e){
	// e.printStackTrace();
	// result[0]="1111";
	// result[1]=e.getMessage();
	// if(log.isErrorEnabled())
	// log.error(e.getMessage(), e);
	// }catch (HttpHostConnectException e) {
	// result[0]="1111";
	// result[1]="请求未正确连接,请检查请求是否正确，远程服务是否开启。";
	// if(log.isErrorEnabled())
	// log.error(e.getMessage(), e);
	// } catch (Exception e) {
	// // e.printStackTrace();
	// result[0]="1111";
	// result[1]=e.getMessage();
	// if(log.isErrorEnabled())
	// log.error(e.getMessage(), e);
	// } finally {
	// // 释放连接
	// httpClient.getConnectionManager().shutdown();
	// }
	// return result;
	// }

	/**
	 * 
	 * 方法描述： 处理服务器返回状态数据
	 * 
	 * @param key
	 *            状态信息
	 * @return String 响应结果信息
	 * @author XieZhenGuo
	 * @date 2013-5-5 上午10:42:04
	 */
	private static String getStatsContent(int key) {
		String result = PropertiesUtil.getProperties("status_" + key);
		if (null == result || "".equals(result)) {
//			if (log.isErrorEnabled())
//				log.error("相应数据错误，读取返回状态配置文件失败。");
			return "响应数据错误。";
		}
		return result;
	}
}
