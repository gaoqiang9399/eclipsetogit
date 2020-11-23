package app.util;

import java.util.Random;

public class Base64 
{
	/**
	 * 勿动
	 */
	private static final String base64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	/**
	 * 勿动
	 */
	private static final String newBase64 = "ABCXYZabc45defopqrFGH:/@{uv12w}MNOghijP^*()-`DEUVW~[]IJKLQRSTstxyz036klmn789";
	/**
	 * 勿动
	 */
	private static final int length=12;
	/**
	 * 勿动
	 */
	private static final int baseLength=64;
	public static String encoder(byte[] bytes)
	{
		StringBuilder result = new StringBuilder();
		int b=0,i=0;
		for(i=0;i<bytes.length;i++)
		{
			if(((i+1)%3)==1) {
                b = (byte)((0xff & bytes[i])>>2);
            } else if(((i+1)%3)==2) {
                b = (byte)((0x3f&((0xff&bytes[i-1])<<4))|((0xff & bytes[i])>>4));
            } else if(((i+1)%3)==0) {
                b = (byte)((0x3f&((0xff&bytes[i-1])<<2))|((0xff & bytes[i])>>6));
            }else {
			}
			result.append(base64.charAt(b));
			if(((i+1)%3)==0)
			{
				b = (byte)(0x3f&bytes[i]);
				result.append(base64.charAt(b));
			}
			if((i==bytes.length-1)&&(((i+1)%3)>0))
			{
				if(((i+1)%3)==1) {
                    b = (byte)(0x3f&((0xff&bytes[i])<<4));
                } else {
                    b = (byte)(0x3f&((0xff&bytes[i])<<2));
                }
				result.append(base64.charAt(b));
			}
		}
		if(bytes.length%3==2) {
            result.append("=");
        } else if(bytes.length%3==1) {
            result.append("==");
        }else {
		}
		return result.toString();
	}
	
	public static byte[] decoder(String str) throws Exception {
		//StringBuilder result = new StringBuilder();
		int l = str.length();
		if (!(l % 4 == 0)) {
			throw new Exception("非base64编码");
		}
		int i = 0;
		int j = 0;
		int n = 0;
		int m = 0;
		if (str.charAt(str.length() - 2) == '=') {
			n = str.length() - 2;
			m = n / 4 * 3 + 1;
		} else {
			if (str.charAt(str.length() - 1) == '=') {
				n = str.length() - 1;
				m = n / 4 * 3 + 2;
			} else {
				n = str.length();
				m = n / 4 * 3;
			}
		}
		//str=new String(str.getBytes(),"UTF-8");
		char[] chars = str.toCharArray();
		byte[] charbytes = new byte[n];
		byte[] bytes = new byte[m];
		byte b = 0;
		int k = 0;
		for (i = 0; i < n; i++) {
			charbytes[i] = (byte) base64.indexOf(chars[i]);
			for (; j < i; j++) {
				if (i % 4 == 0) {
					continue;
				}
				if (i % 4 == 1) {
					b = (byte) (((0xff & charbytes[j]) << 2) | ((0xff & charbytes[j + 1]) >> 4));
				} else if (i % 4 == 2) {
					b = (byte) (((0x0f & charbytes[j]) << 4) | ((0xff & charbytes[j + 1]) >> 2));
				} else if (i % 4 == 3) {
					b = (byte) (((0x03 & charbytes[j]) << 6) | (0xff & charbytes[j + 1]));
				}else {
				}
				bytes[k] = b;
				k++;
			}
		}
		return bytes;
	}
	private static int getRandom(int max)
	{
		Random random=new Random();
		return random.nextInt(max);
	}
	public static String newEncoder(byte[] bytes)
	{
		StringBuilder result = new StringBuilder();
		int startIndex=getRandom(length+1);
		String base64=newBase64;
		base64=base64.substring(startIndex,startIndex+baseLength);
		int b=0,i=0;
		for(i=0;i<bytes.length;i++)
		{
			if(((i+1)%3)==1) {
                b = (byte)((0xff & bytes[i])>>2);
            } else if(((i+1)%3)==2) {
                b = (byte)((0x3f&((0xff&bytes[i-1])<<4))|((0xff & bytes[i])>>4));
            } else if(((i+1)%3)==0) {
                b = (byte)((0x3f&((0xff&bytes[i-1])<<2))|((0xff & bytes[i])>>6));
            }else {
			}
			result.append(base64.charAt(b));
			if(((i+1)%3)==0)
			{
				b = (byte)(0x3f&bytes[i]);
				result.append(base64.charAt(b));
			}
			if((i==bytes.length-1)&&(((i+1)%3)>0))
			{
				if(((i+1)%3)==1) {
                    b = (byte)(0x3f&((0xff&bytes[i])<<4));
                } else {
                    b = (byte)(0x3f&((0xff&bytes[i])<<2));
                }
				result.append(base64.charAt(b));
			}
		}
		if(bytes.length%3==2) {
            result.append("=");
        } else if(bytes.length%3==1) {
            result.append("==");
        }else {
		}
		return  getNewResult(result.toString(),startIndex) ;
	}
	private static String getNewResult(String result,int index)
	{
		if(result.replaceAll("=","").length()>=4)
		{
			char s1=newBase64.charAt(index);
			char s2=newBase64.charAt(getRandom(baseLength)+index);
			char s3=newBase64.charAt(getRandom(baseLength)+index);
			char s4=newBase64.charAt(getRandom(baseLength)+index);
			String baseStr1=result.substring(0,result.length()/2);
			String baseStr2=result.substring(result.length()/2,result.length());
			String str1=baseStr1.substring(0,baseStr1.length()/2);
			String str2=baseStr1.substring(baseStr1.length()/2,baseStr1.length());
			String str3=baseStr2.substring(0,baseStr2.length()/2);
			String str4=baseStr2.substring(baseStr2.length()/2,baseStr2.length());
			StringBuilder sb=new StringBuilder();
			sb.append(s1).append(str1).append(s2).append(str2).append(s3).append(str3).append(s4).append(str4);
			result=sb.toString();
		}
		return result;
	}
	private static String backBase64(String str)
	{
		String baseStr1=str.substring(0,str.length()/2);
		String baseStr2=str.substring(str.length()/2,str.length());
		String str1=baseStr1.substring(0,baseStr1.length()/2);
		String str2=baseStr1.substring(baseStr1.length()/2,baseStr1.length());
		String str3=baseStr2.substring(0,baseStr2.length()/2);
		String str4=baseStr2.substring(baseStr2.length()/2,baseStr2.length());
		StringBuilder sb=new StringBuilder();
		sb.append(str1.substring(1)).append(str2.substring(1)).append(str3.substring(1)).append(str4.substring(1));
		return sb.toString();
	}
	private static int getStartIndex(String str)
	{
		return newBase64.indexOf(str.charAt(0));
	}
	public static byte[] newDecoder(String str) throws Exception
	{
		
		String base64=newBase64;
		if(str.length()>=8)
		{
			int startIndex=getStartIndex(str);
			base64=base64.substring(startIndex,startIndex+baseLength);
			str=backBase64(str);
		}
		int l=str.length();
		if(!(l%4==0)) {
            throw new Exception("非base64编码");
        }
		int i=0;
		int j=0;
		int n=0;
		int m=0;
		if(str.charAt(str.length()-2)=='=')
		{
			n=str.length()-2;
			m=n/4*3+1;
		}
		else
		{
			if(str.charAt(str.length()-1)=='=')
			{
				n=str.length()-1;
				m=n/4*3+2;
			}
			else
			{
				n=str.length();
				m=n/4*3;
			}
		}
		char [] chars=str.toCharArray();
		byte [] charbytes=new byte[n];
		byte [] bytes=new byte[m];
		byte b = 0;
		int k=0;
		for(i=0;i<n;i++)
		{
			charbytes[i]=(byte)base64.indexOf(chars[i]);
			for(;j<i;j++) {
				if (i % 4 == 0) {
					continue;
				}
				if (i % 4 == 1) {
					b = (byte) (((0xff & charbytes[j]) << 2) | ((0xff & charbytes[j + 1]) >> 4));
				} else if (i % 4 == 2) {
					b = (byte) (((0x0f & charbytes[j]) << 4) | ((0xff & charbytes[j + 1]) >> 2));
				} else if (i % 4 == 3) {
					b = (byte) (((0x03 & charbytes[j]) << 6) | (0xff & charbytes[j + 1]));
				}else {
				}
				bytes[k] = b;
				k++;
			}
		}
		return bytes;
	}
}