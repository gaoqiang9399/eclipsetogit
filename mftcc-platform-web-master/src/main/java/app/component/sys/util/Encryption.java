package app.component.sys.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import app.util.Base64;

/**
 * 密码加密工具类
 * @author Administrator
 *
 */
public class Encryption {
	/**
	 * 将msg字符串采用MD5算法处理，返回一个Sring结果
	 * @param msg	明文
	 * @return		密文
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	public static String md5(String msg) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		//原始信息input
		byte[] input = msg.getBytes();
		//加密信息output
		byte[] output = md.digest(input);
		//将加密内容output转成String字符串
		//为解决乱码问题采用BASE64转换,需要引一个工具包commons codec
		String result = Base64.encoder(output);
		return result;
	}
	 /**
	 * @Name EncoderByMd5
	 * @Description: md5加密
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @author Yang Xingyi
	 * @date 2017年4月7日
	 */
	public static String encoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
       //确定计算方法
       MessageDigest md5=MessageDigest.getInstance("MD5");
       org.apache.commons.codec.binary.Base64 base64en = new  org.apache.commons.codec.binary.Base64();
       //加密后的字符串
       String newstr=base64en.encodeAsString(md5.digest(str.getBytes("utf-8")));
       return newstr;
	}
	
	     
	  public static String toMd5(String str) {
		  MessageDigest messageDigest = null;    
	        try    
	        {    
	            messageDigest = MessageDigest.getInstance("MD5");    
	            messageDigest.reset();    
	            messageDigest.update(str.getBytes("UTF-8"));    
	        } catch (NoSuchAlgorithmException e)    
	        {    
	            System.out.println("NoSuchAlgorithmException caught!");    
	            System.exit(-1);    
	        } catch (UnsupportedEncodingException e)    
	        {    
	            e.printStackTrace();    
	        }    
	    
	        byte[] byteArray = messageDigest.digest();    
	    
	        StringBuffer md5StrBuff = new StringBuffer();    
	    
	        for (int i = 0; i < byteArray.length; i++)    
	        {    
	            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
					md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
				} else {
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
				}
	        }    
	        return md5StrBuff.toString();   

	    }
	  
	  public static void main(String[] args) {
		System.out.println(toMd5("17082615264085996pass"));
	}
	 

}
