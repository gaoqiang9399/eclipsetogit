package cn.mftcc.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import app.base.ServiceException;
import net.sf.json.JSONObject;

/**
 * httpclient请求工具类
 * @author wzw
 *
 */
public class HttpClientUtil {

	private static RequestConfig requestConfig;

	private static final int MAX_TIMEOUT = 60000*2;

	protected static CloseableHttpClient httpClient;

	/**
	 * 20190618 xzp 修改过时方法，使用连接池管理连接
	 */
	static{
		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
		//设定连接池最大数量
		poolingHttpClientConnectionManager.setMaxTotal(500);
		//设定默认单个路由的最大连接数
		poolingHttpClientConnectionManager.setDefaultMaxPerRoute(50);
		//检测有效连接的间隔
		poolingHttpClientConnectionManager.setValidateAfterInactivity(2000);
		SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(MAX_TIMEOUT).build();
		poolingHttpClientConnectionManager.setDefaultSocketConfig(socketConfig);
		httpClient = HttpClientBuilder.create()
				.setConnectionManager(poolingHttpClientConnectionManager)
				.disableCookieManagement()
				.build();
		requestConfig = RequestConfig.custom()
				//设定连接服务器超时时间
				.setConnectionRequestTimeout(MAX_TIMEOUT)
				//设定从连接池获取可用连接的时间
				.setConnectTimeout(MAX_TIMEOUT)
				//设定获取数据的超时时间
				.setSocketTimeout(MAX_TIMEOUT)
				.build();

	}

	/**
	 * post请求
	 * 20190618 xzp 修改过时方法，使用连接池
	 * @param parmMap
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String sendPost(Map<String, String> parmMap, String url) throws Exception {
		String result;
		HttpPost httppost;
		HttpResponse response;
		try {
			httppost = new HttpPost(url);
			httppost.setConfig(requestConfig);
			if(null != parmMap && parmMap.size() > 0){
				List<NameValuePair> params = new ArrayList<>();
				for (Map.Entry<String, String> entry : parmMap.entrySet()) {
					if (entry.getValue() == null || "".equals(entry.getValue())) {
						continue;
					}
					BasicNameValuePair pair = new BasicNameValuePair(entry.getKey(),entry.getValue());
					params.add(pair);
				}
				//解决中文乱码问题
				httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			}
			response = httpClient.execute(httppost);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
			httppost.abort();
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	/**
	 * post请求 JSON格式
	 * @param parmMap
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public static String sendPostJson(Map<String, String> parmMap,String url) throws Exception {
		String result = null;
		CloseableHttpClient httpclient = null;
		HttpPost httppost = null;
		try {
			httpclient = HttpClients.createDefault();
			httppost = new HttpPost(url);
			
			JSONObject jsonParam = new JSONObject(); 
			for (Map.Entry<String, String> entry : parmMap.entrySet()) {
				if(entry.getValue() == null|| "".equals(entry.getValue())) {
                    continue;
                }
				jsonParam.put(entry.getKey(), entry.getValue());
			}
			StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题
			entity.setContentEncoding("UTF-8");   
			entity.setContentType("application/json");    
			httppost.setEntity(entity);
			HttpResponse response = httpclient.execute(httppost);
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
			httppost.abort();
		} catch (Exception e) {
//			log.error(log_req_url + url);
//			log.error(log_req_parm + parmMap);
			throw e;
		} finally {
			if(null != httpclient)
			{
				try {
					httpclient.close();
				} catch (IOException e) {
					throw new ServiceException(e);
				}
			}
		}
		return result;
	}
	
	/**
	 * post请求 JSON格式
	 * @param jsonParm
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public static String sendPostJson(String jsonParm,String url) throws Exception {
		String result = null;
		CloseableHttpClient httpclient = null;
		HttpPost httppost = null;
		try {
			httpclient = HttpClients.createDefault();
			httppost = new HttpPost(url);
			StringEntity entity = new StringEntity(jsonParm,"utf-8");//解决中文乱码问题
			entity.setContentEncoding("UTF-8");   
			entity.setContentType("application/json");    
			httppost.setEntity(entity);
			HttpResponse response = httpclient.execute(httppost);
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
			httppost.abort();
		} catch (Exception e) {
//			log.error(log_req_url + url);
//			log.error(log_req_parm + jsonParm);
			throw e;
		} finally {
			if(null != httpclient)
			{
				try {
					httpclient.close();
				} catch (IOException e) {
					throw new ServiceException(e);
				}
			}
		}
		return result;
	}
	/**
	 * 发送https请求
	 * @param parmMap
	 * @param url
	 * @return
	 */
	public static String doPostSSLJson(Map<String, String> parmMap,String url) {
		CloseableHttpClient httpClient = createSSLClientDefault();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		String httpStr = null;
		try {
			Gson gson = new Gson();
			StringEntity stringEntity = new StringEntity(gson.toJson(parmMap),"UTF-8");//解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送https请求
	 * @param parmMap
	 * @param url
	 * @return
	 */
	public static String doPostSSL(Map<String, String> parmMap,String url) {
		CloseableHttpClient httpClient = createSSLClientDefault();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		String httpStr = null;
		try {
			JSONObject jsonParam = new JSONObject();
			for (Map.Entry<String, String> entry : parmMap.entrySet()) {
				if(entry.getValue() == null) {
                    continue;
                }
				jsonParam.put(entry.getKey(), entry.getValue());
			}
			StringEntity stringEntity = new StringEntity(jsonParam.toString(),"UTF-8");//解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				if(null == response.getEntity()){
//					log.error("本次请求返回数据状态码不是200，状态码："+statusCode+",请求参数："+jsonParam.toString()+",返回数据信息为null");
					return null;
				}
//				log.error("本次请求返回数据状态码不是200，状态码："+statusCode+",请求参数："+jsonParam.toString()+",返回数据信息"+EntityUtils.toString(response.getEntity(), "utf-8"));
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	public static String doPostSSLForm(Map<String, String> parmMap,String url) {
		CloseableHttpClient httpClient = createSSLClientDefault();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		String httpStr = null;
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : parmMap.entrySet()) {
				if (entry.getValue() == null || "".equals(entry.getValue())) {
                    continue;
                }
				BasicNameValuePair pair = new BasicNameValuePair(entry.getKey(), (String) entry.getValue());
				params.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));//解决中文乱码问题
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 创建SSL安全连接
	 *
	 * @return
	 */
	public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						// 信任所有
						@Override
						public boolean isTrusted(X509Certificate[] chain,
												 String authType) {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}

	/**
	 * 下载文件
	 *
	 * @param url
	 * @param filePath
	 */
	private void httpDownloadFile(String url, String filePath, Map<String, String> headMap) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url);
			setGetHead(httpGet, headMap);
			HttpResponse response1 = httpclient.execute(httpGet);

			System.out.println(response1.getStatusLine());
			HttpEntity httpEntity = response1.getEntity();
			InputStream is = httpEntity.getContent();
			// 根据InputStream 下载文件
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int r;
			while ((r = is.read(buffer)) > 0) {
				output.write(buffer, 0, r);
			}
			FileOutputStream fos = new FileOutputStream(filePath);
			output.writeTo(fos);
			output.flush();
			output.close();
			fos.close();
			EntityUtils.consume(httpEntity);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 上传文件
	 *
	 * @param serverUrl
	 *            服务器地址
	 * @param localFilePath
	 *            本地文件路径
	 * @param serverFieldName
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String uploadFileImpl(String serverUrl, String localFilePath,
								 String serverFieldName, Map<String, String> params)
			throws Exception {
		String respStr ;
		CloseableHttpClient httpclient = createSSLClientDefault();
		try {
			HttpPost httppost = new HttpPost(serverUrl);
			FileBody binFileBody = new FileBody(new File(localFilePath));

			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
					.create();
			// add the file params
			multipartEntityBuilder.addPart(serverFieldName, binFileBody);
			// 设置上传的其他参数
			setUploadParams(multipartEntityBuilder, params);

			HttpEntity reqEntity = multipartEntityBuilder.build();
			httppost.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(httppost);
			System.out.println(response.getStatusLine());
			HttpEntity resEntity = response.getEntity();
			respStr = getRespString(resEntity);
			EntityUtils.consume(resEntity);

		} finally {
			httpclient.close();
		}
		System.out.println("resp=" + respStr);
		return respStr;
	}
	
	/**
	 * 上传文件
	 *
	 * @param serverUrl
	 *            服务器地址
	 * @param localFilePath
	 *            本地文件路径
	 * @param serverFieldName
	 * @param params
	 * @param fileMap key:文件参数域名称
	  					value:文件路径
	 * @return
	 * @throws Exception
	 */
	public static String uploadMultiFileImpl(String serverUrl,Map<String,String>  fileMap, Map<String, String> params)
			throws Exception {
		String respStr ;
		CloseableHttpClient httpclient = createSSLClientDefault();
		try {
			HttpPost httppost = new HttpPost(serverUrl);

			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
					.create();
			// add the file params
			for (String key : fileMap.keySet()) {
				FileBody binFileBody = new FileBody(new File(fileMap.get(key)));
				multipartEntityBuilder.addPart(key, binFileBody);
			}
			
			
			// 设置上传的其他参数
			setUploadParams(multipartEntityBuilder, params);

			HttpEntity reqEntity = multipartEntityBuilder.build();
			httppost.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(httppost);
			System.out.println(response.getStatusLine());
			HttpEntity resEntity = response.getEntity();
			respStr = getRespString(resEntity);
			EntityUtils.consume(resEntity);

		} finally {
			httpclient.close();
		}
		System.out.println("resp=" + respStr);
		return respStr;
	}
	
	/**
	 * 上传文件
	 *
	 * @param serverUrl
	 *            服务器地址
	 * @param localFilePath
	 *            本地文件路径
	 * @param serverFieldName
	 * @param params
	 * @param fileMap key:文件参数域名称
	  					value:文件路径
	 * @return
	 * @throws Exception
	 */
	public static String uploadMultiFileImplhttp(String serverUrl,Map<String,String>  fileMap, Map<String, String> params)
			throws Exception {
		String respStr ;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(serverUrl);

			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
					.create();
			// add the file params
			for (String key : fileMap.keySet()) {
				FileBody binFileBody = new FileBody(new File(fileMap.get(key)));
				multipartEntityBuilder.addPart(key, binFileBody);
			}
			
			
			// 设置上传的其他参数
			setUploadParams(multipartEntityBuilder, params);

			HttpEntity reqEntity = multipartEntityBuilder.build();
			httppost.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(httppost);
			System.out.println(response.getStatusLine());
			HttpEntity resEntity = response.getEntity();
			respStr = getRespString(resEntity);
			EntityUtils.consume(resEntity);

		} finally {
			httpclient.close();
		}
		System.out.println("resp=" + respStr);
		return respStr;
	}

	/**
	 * 将返回结果转化为String
	 *
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private static String getRespString(HttpEntity entity) throws Exception {
		if (entity == null) {
			return null;
		}
		InputStream is = entity.getContent();
		StringBuffer strBuf = new StringBuffer();
		byte[] buffer = new byte[4096];
		int r = 0;
		while ((r = is.read(buffer)) > 0) {
			strBuf.append(new String(buffer, 0, r, "UTF-8"));
		}
		return strBuf.toString();
	}


	/**
	 * 设置上传文件时所附带的其他参数
	 *
	 * @param multipartEntityBuilder
	 * @param params
	 */
	private static void setUploadParams(MultipartEntityBuilder multipartEntityBuilder,
								 Map<String, String> params) {
		if (params != null && params.size() > 0) {
			Set<String> keys = params.keySet();
			for (String key : keys) {
				multipartEntityBuilder
						.addPart(key, new StringBody(params.get(key),
								ContentType.TEXT_PLAIN));
			}
		}
	}

	/**
	 * 设置http的HEAD
	 *
	 * @param httpGet
	 * @param headMap
	 */
	private void setGetHead(HttpGet httpGet, Map<String, String> headMap) {
		if (headMap != null && headMap.size() > 0) {
			Set<String> keySet = headMap.keySet();
			for (String key : keySet) {
				httpGet.addHeader(key, headMap.get(key));
			}
		}
	}

	
	/**
	 * 
	 * 方法描述： get请求 https
	 * @param url
	 * @return
	 * String
	 * @author wzw
	 * @date 2017-11-17 下午4:01:50
	 */
	public static String sendGetHttps(String url){
		String result = null;
		CloseableHttpClient httpclient = null;
		HttpGet httpget = null;
		try {
			httpclient = HttpClientUtil.createSSLClientDefault();
			httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}finally {
			if(null!=httpget){
				httpget.abort();
			}
		}
		return result;
	}

	
	
	
	/**
	 * 使用demo
	 * @param args
	 */
	/*public static void main(String[] args) {
	// GET 同步方法
		httpDownloadFile( "http://wthrcdn.etouch.cn/weather_mini?city=北京", filePath, null, null);
		// 上传文件 POST 同步方法
		try {
			Map<String,String> uploadParams = new LinkedHashMap<String, String>();
			uploadParams.put("userImageContentType", "image");
			uploadParams.put("userImageFileName", "testaa.png");
			this.uploadFileImpl(
					"http://192.168.31.183:8080/SSHMySql/upload", "android_bug_1.png",
					"userImage", uploadParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	
	
	
}
