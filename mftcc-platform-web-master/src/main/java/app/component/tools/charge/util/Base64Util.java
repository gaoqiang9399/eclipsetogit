package app.component.tools.charge.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {
	/**
	 * 将文件加密为base64字符
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String encodeBase64ByPath(String path) throws IOException{
		FileInputStream inputFile = null;
		byte[] buffer = null;
		try {
			File file = new File(path);
			inputFile = new FileInputStream(file);
			buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputFile != null) {
				inputFile.close();
			}
		}
		return new BASE64Encoder().encode(buffer);
	}
	/**
	 * 将文件加密为base64字符
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String encodeBase64ByFile(File file) throws IOException{
		FileInputStream inputFile= null;
		byte[] buffer = null;
		try{
			inputFile=new FileInputStream(file);
			buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
		}catch (Exception e)
		{
			e.printStackTrace();
		}finally {
			if (inputFile != null) {
				inputFile.close();
			}
		}

		  return new BASE64Encoder().encode(buffer);
	}
	 /**
	  * 将base64字符解码，保存文件
	  * @param base64Code
	  * @param targetPath
	  * @throws Exception
	  */

	 public static void decoderBase64File(String base64Code, String targetPath,String FileName)
	   throws Exception {
		 byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
		 FileOutputStream out = null;
		 try {
			 File file = new File(targetPath);
			 if (!file.exists() && !file.isDirectory()) {
				 //创建目录
				 file.mkdirs();
			 }
			 out = new FileOutputStream(targetPath + File.separator + FileName);
			 out.write(buffer);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 if (out != null) {
				 out.close();
			 }
		 }

	 }
	/**
	 * 将页面传来的file转为base64字符串
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String encodeBase64MultFile(MultipartFile file) throws IOException{
		  return new BASE64Encoder().encode(file.getBytes());
	}
	/**
	 * base64加密字符串
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String str) throws UnsupportedEncodingException{
		return new BASE64Encoder().encodeBuffer(str.getBytes("UTF8"));
	}
	/**
	 * 解密base64字符串
	 * @param baseStr
	 * @return
	 * @throws IOException
	 */
	public static String decode(String baseStr) throws IOException{
		byte[] de=new BASE64Decoder().decodeBuffer(baseStr);
		return new String(de,"UTF8");
	}
//	public static void main(String[] args) {
//		File file=new File("D:");
//		System.out.println(file.isDirectory());
//		File f=new File("D:/a/a/a/a/a");
//		f.mkdirs();
//		System.out.println(f.isDirectory());
//	}
}
