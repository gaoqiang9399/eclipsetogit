package app.component.thirdservice.zgldatas.util;

import java.util.HashMap;
import java.util.Map;

import cn.mftcc.util.PropertiesUtil;

public class ZglUtil {

	/**
	 * 用户key 第三方分配
	 */
	public final static String key = PropertiesUtil.getMsgConfigProperty("TD_ZGL_KEY");
	/**
	 * RSA私钥  第三方分配,用于生成签名
	 */
	public final static String privateKey = PropertiesUtil.getMsgConfigProperty("TD_ZGL_PRIVATE_KEY");
	/**
	 * RSA公钥  第三方分配,用于验证签名
	 */
	public final static String publicKey = PropertiesUtil.getMsgConfigProperty("TD_ZGL_PUBLIC_KEY");
	/**
	 * ASE加密
	 */
	public final static String aesKey = PropertiesUtil.getMsgConfigProperty("TD_ZGL_AES_KEY");
//	/**
//	 * 用户key 第三方分配
//	 */
//	public final static String key = "5c6b0b7a8209c037c02408d4ded538b0";
//	/**
//	 * RSA私钥  第三方分配,用于生成签名
//	 */
//	public final static String privateKey = "<RSAKeyValue><Modulus>ywUaTmMdfYh5djPaLYloQiqBl+Sgpu5uB/b1oCuvuVOpoonP14fBwHPS/ctrm61lVDxBRk0guPhjG6lMDj7jfm7Z5+yLwDD7whGeluKICplr6JcpA+8VmxMQMmkkqz4wW9lhalOyx/fxjiWFV5Qu5c6WlOzx83O3VUEl8DSesqk=</Modulus><Exponent>AQAB</Exponent><P>+XBmnqnjbq9F3RT6WifMwKYEAdiBliwyjHlFZN5DToYM/d0EJ4YIxF1dZI9yytTH4yhawind7NbMPYk57y1mTw==</P><Q>0FwjcSA+M+LxBz2Afy6PR3OXjoJbUdJRsFUjofTcRRm74cGY0i9Wr2WluzOMpcof3nT4dSV2ZBJIFgH1s9WRhw==</Q><DP>J71BjBuS0Up5PeyeqghXNYX9c3HTy2IFN54GcTh7zqHKphM1GzreYLJjM6cq6aeWpNkEBJT490eNSGx5X2KDPw==</DP><DQ>dx1VfaSrKsqAdF0qioq5imWArQ3E2v/bh20E09/B1RcjG6qrjO1XJyp7Fowfyd/FXE2OhJoIFqmE4F/6cPaVHQ==</DQ><InverseQ>faqOFqljpXLpuWNO/CvDHen7WqmIszHvxEjLmo7S7/qeDWWeOiiESJUbMYC6hA+o2zS3bGMPnbUzLJJf11m9uQ==</InverseQ><D>GE7xGmeltU0s9ek5e3BTanC6eZxnW/V6eTupp1bzwGXzDsIff7gCi++SWM8NgGNfmZlpW2iKvXsIKty3fZEsHdWJZy6yhDCbFI4E6epz52oBB7YD4wpnSlW3JMy/5NA0ZVTAbWQUAnqpM/4pifIgIJQfGC4OXa2GMqIAgIQuuE0=</D></RSAKeyValue>";
//	/**
//	 * RSA公钥  第三方分配,用于验证签名
//	 */
//	public final static String publicKey = "<RSAKeyValue><Modulus>6TugMLi2nrkd7WGgBA9o4rgQ73Xk9SzTWqaNfH+f8ahTulMGYvmPWv6mF3xMkRhVLwQWX0zu3+d9Ofzni000XyfQsnk9GPfJ4o/yxaU6axB6ys8oy23ON+k1+571bPu8Ifh4shSxTHpzv5CTFkefsHDqQI3NXp/pA1ArzCsNsVE=</Modulus><Exponent>AQAB</Exponent></RSAKeyValue>";
//	/**
//	 * ASE加密
//	 */
//	public final static String aesKey = "slpgenquirysvcxy";
	/**
	 * 服务地址
	 */
	public final static String url = "http://dispatcher.visscaa.com/api/webapiaccess/bjzhxdzc/enquiry";

	public final static String AreaCode = "";

	/**
	 * 行政区域缓存
	 */
	public static Map<String,Object> AreaCodeCacheMap = new HashMap<String, Object>(){{
		put("Data",AreaCode);
	}};
	
	/**
	 * 物业缓存
	 */
	public static Map<String,Object> PropertyTypeCacheMap = new HashMap<String, Object>();
	
	/****以下是测试环境配置2017年8月10日10:04:27****/
//	/**
//	 * 用户key 第三方分配
//	 */
//	public final static String key = "a1b20c2a3f10c235962408d37e0206d5";
//	/**
//	 * RSA私钥  第三方分配,用于生成签名
//	 */
//	public final static String privateKey = "<RSAKeyValue><Modulus>p3hLthaU4ei0dWI1QP99JaVYbghOooJWet7bNXyOWyOAQQzfITRbMCGaBZXg7M0w2SMtxyPr+w54kcsQQfDpMn61BXpWLg/5lYvYKkIG5DzFmhjyUwUC+g91bR7hHfj55YOFehvUwX6x6CFC9CQ2jJRsIWfqT3pTFfrFmfmgdiM=</Modulus><Exponent>AQAB</Exponent><P>0JvjbXXIb6ndTeDVlIWZeJZ3CYxMIXII8qvKQ0cYh3K0NOQLFZJIcCQitJ7rL2gNadDLijr5p+fp9ovZlbKX6Q==</P><Q>zYPgWmK47mje4MUF+KzZNQ05SPj/iU/nL+X3v90d9eat1Rtf7iito0qsdFp3LgTfle294jrEJy6ji5CZpFQiKw==</Q><DP>w2Vg70RlzAHlom64X3eMOyFkunLJVIKF0xgKSl4roaNVHD2GDFyKsU+HmntIe40RE05ZeE6pThayVRbFZax1EQ==</DP><DQ>UlNqsypq5G40IhwqyTQMirjyYq4ER3AvrztTJJOiJdgzeHPP2OqIrCoErVNz/IZNPpUPBKn/26ZOM2FIetCNIw==</DQ><InverseQ>rPqhs20F8gxmLbgqIj0EOloUaQIe+AdIrgqNQYr67J7YE4m7+pKutOt+TMl8HsOQR0Gpyf7FsoPouIJpJMy2vw==</InverseQ><D>NYKiCnYPr1loI+Oz6WdZSQiah1n/KjzkPhFsUJxSbjubNO3Uc+sjQe9So/s+adusgo0TiQBo3AjFLKyKLs+36wavmlTahC45+XUmaHCuOvTFnMlquAE2K+I14swToZFmeewAEl2cjFtjDpzzAPHYJbXfe+L8Xbktt9t6DOgGysk=</D></RSAKeyValue>";
//	/**
//	 * RSA公钥  第三方分配,用于验证签名
//	 */
//	public final static String publicKey = "<RSAKeyValue><Modulus>6TugMLi2nrkd7WGgBA9o4rgQ73Xk9SzTWqaNfH+f8ahTulMGYvmPWv6mF3xMkRhVLwQWX0zu3+d9Ofzni000XyfQsnk9GPfJ4o/yxaU6axB6ys8oy23ON+k1+571bPu8Ifh4shSxTHpzv5CTFkefsHDqQI3NXp/pA1ArzCsNsVE=</Modulus><Exponent>AQAB</Exponent></RSAKeyValue>";
//	/**
//	 * ASE加密
//	 */
//	public final static String aesKey = "slpgenquirysvcxx";
//	/**
//	 * 服务地址
//	 */
//	public final static String url = "http://dispatchertest.visscaa.com/api/webapiaccess/PublicTestEnquiry/Enquiry";
//	
	
	
}
