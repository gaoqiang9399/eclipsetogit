package app.component.sys.util;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;



/** 
 * @author Yang Xignyi 
 * @date 创建时间：2017年4月7日 上午11:17:11 
 * @version 1.0 
 */
public class LoginEncodeUtil {
	
	public static String EncodeByMD5(String tel, String phoneNo,String timeTamp) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String result = null;
		StringBuffer sb = new StringBuffer();
//		long timeTamp = System.currentTimeMillis();
		sb.append("tel=");
		sb.append(tel);
		sb.append("&");
		sb.append("phoneNo=");
		sb.append(phoneNo);
		sb.append("&");
		sb.append("timeTamp=");
		sb.append(timeTamp);
		String s = sb.toString();
		result = Encryption.encoderByMd5(s);
		return result;
	}
	
//	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, InterruptedException {
//		String str=""+System.currentTimeMillis();

//		System.out.println(str);
//		System.out.println(EncodeByMD5("0000","16945684577",str));
////		Thread.sleep(1000);
//		System.out.println(EncodeByMD5("0000","16945684577",str));
//	}
}
