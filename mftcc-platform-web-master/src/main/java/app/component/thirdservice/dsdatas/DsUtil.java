package app.component.thirdservice.dsdatas;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import app.base.ServiceException;
import net.sf.json.JSONObject;

/**
 * 大圣数据服务工具类
 * @author wzw
 *
 */
public class DsUtil {

	protected static Logger logger = LoggerFactory.getLogger(DsUtil.class);
	
	/**
	 * 商户标识 key，在每次请求的时候作为参数传入
	 */
	public final static String API_KEY = "0d0171817cbd1ad1a349e9e73b819fd0";
	
	/**
	 * 签名时使用的 key
	 */
	public final static String SECRET_KEY = "65d76e20ed16f324a87ea60ee78028e1c01fd910642457070d6b66fbcc7d938f";

    public static String sign(String apiKey, String channelNo, String interfaceName, long timestamp, String secretKey, Map<String, Object> payload) throws ServiceException {
        Map<String, Object> paramMap = new HashMap<String,Object>();
        paramMap.put("apiKey", apiKey);
        if(channelNo != null){
            paramMap.put("channelNo", channelNo);
        }
        if(interfaceName != null){
            paramMap.put("interfaceName", interfaceName);
        }
        paramMap.put("timestamp", timestamp);
        if (payload != null){
            Set<String> payloadKeys = payload.keySet();
            for (String payloadKey : payloadKeys) {
                paramMap.put(payloadKey, payload.get(payloadKey));
            }
        }
        Map<String,Object> signJson = new LinkedHashMap<String,Object>();
        List<String> sortKeyList = sortMapKeyList(paramMap);
        for (String key : sortKeyList) {
            signJson.put(key, paramMap.get(key));
        }
        /*对sign签名进行验证*/
        String signature = null;
        try {
            logger.error("签名信息:"+new Gson().toJson(signJson));
            signature = encodeHmacSHA256(new Gson().toJson(signJson), secretKey);
            logger.error("签名结果:"+signature);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return signature;
    }
    
    private static List<String> sortMapKeyList(Map<String, Object> map) {
        Set<String> listKeys = map.keySet();
        Iterator<String> it = listKeys.iterator();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            list.add(it.next());
        }
        Collections.sort(list);
        return list;
    }
	
    private static String encodeHmacSHA256(String paramForSign, String secretKey) throws Exception {
        String signature = encodeHmacSHA256(paramForSign.getBytes("UTF-8"), secretKey.getBytes());
        return signature;
    }
    
    private static String encodeHmacSHA256(byte[] data, byte[] key) throws Exception {
        // 还原密钥
        SecretKey secretKey = new SecretKeySpec(key, "HmacSHA256");
        // 实例化Mac
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        //初始化mac
        mac.init(secretKey);
        //执行消息摘要
        byte[] digest = mac.doFinal(data);
        return new String(encodeHex(digest, DIGITS_LOWER));
    }
    
    /**
     * 用于建立十六进制字符的输出的小写字符数组
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }
    
    
    public static String doPostSSL(Map<String, Object> parmMap,String url) {  
		CloseableHttpClient httpClient = createSSLClientDefault(); 
        HttpPost httpPost = new HttpPost(url);  
        CloseableHttpResponse response = null;  
        String httpStr = null;  
        try {  
        	JSONObject jsonParam = new JSONObject(); 
			for (Map.Entry<String, Object> entry : parmMap.entrySet()) {
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
	 * 解压 http压缩内容
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static String decompress(byte[] data) throws IOException {
        if (data == null || data.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        GZIPInputStream gzipInputStream = null;
        try {
            gzipInputStream = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int n;
            while ((n = gzipInputStream.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            out.flush();
            return out.toString("UTF-8");
        } catch (ZipException e) {
            // TODO LOGGER.error(String.format("解压缩数据 ZipException 异常, data.length = [%d]", data.length), e);
            throw e;
        } catch (IOException e) {
            // TODO LOGGER.error(String.format("解压缩数据 IOException 异常, data.length = [%d]", data.length), e);
            throw e;
        } finally {
            if (gzipInputStream != null) {
                try {
                    gzipInputStream.close();
                } catch (Exception e) {
                    // TODO LOGGER.error("关闭 GZIPInputStream 异常 : ", e);
                }
            }
            try {
                in.close();
            } catch (Exception e) {
                // TODO LOGGER.error("关闭 ByteArrayInputStream 异常 : ", e);
            }
        }
    }

}
