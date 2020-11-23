package app.component.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import org.apache.shiro.codec.Base64;


public class FileUtil {
	/**
	 * 文件复制  f为true 如果文件存在则覆盖
	 * @param sourcePath
	 * @param targetPath
	 * @param f
	 */
	public static boolean copy(String sourcePath,String targetPath,boolean f) {  
	    FileChannel in = null;  
	    FileChannel out = null;  
	    FileInputStream inStream = null;  
	    FileOutputStream outStream = null; 
	    File source=new File(sourcePath);
	    File target=new File(targetPath);
	    if(!f){
	    	if(target.exists()){
	    		return true;
	    	}
	    }
	    try {  
	        inStream = new FileInputStream(source);  
	        outStream = new FileOutputStream(target);  
	        in = inStream.getChannel();  
	        out = outStream.getChannel();  
	        in.transferTo(0, in.size(), out);  
	    } catch (IOException e) {  
	        e.printStackTrace();
	        return false;
	    } finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (outStream != null) {
					outStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	    return true;
	}
	/**
	 * 文件复制  f为true 如果文件存在则覆盖
	 * @param sourcePath
	 * @param targetPath
	 * @param f
	 */
	public static boolean copy(File source,File target,boolean f) {  
	    FileChannel in = null;  
	    FileChannel out = null;  
	    FileInputStream inStream = null;  
	    FileOutputStream outStream = null; 
	    if(!f){
	    	if(target.exists()){
	    		return true;
	    	}
	    }
	    try {  
	        inStream = new FileInputStream(source);  
	        outStream = new FileOutputStream(target);  
	        in = inStream.getChannel();  
	        out = outStream.getChannel();  
	        in.transferTo(0, in.size(), out);  
	    } catch (IOException e) {  
	        e.printStackTrace();
	        return false;
	    } finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (outStream != null) {
					outStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	    return true;
	}
	
	public static boolean save(String sourceBase64,File target,boolean f) {  
		try {
			byte[] buffer = Base64.decode(sourceBase64);
			return save(buffer, target, f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	public static boolean save(byte[] source,File target,boolean f) {  
		InputStream in = null;  
		FileOutputStream out = null; 
		if(!f){
			if(target.exists()){
				return true;
			}
		}
		try {  
			in = new ByteArrayInputStream(source) ;
			out = new FileOutputStream(target);  
			byte[] bf=new byte[1024*2]; 
			int len=0;
			while((len=in.read(bf))>-1) {
				out.write(bf, 0, len);
			}
		} catch (IOException e) {  
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	/**
	 * 创建文件目录
	 * @author LJW
	 * @param dirPath  文件路径
	 * date 2017-4-8
	 */
	public static void createDir(String dirPath) {
		File dirFile = new File(dirPath);
		if (!dirFile.exists()) {
			if(dirFile.mkdirs()){
				System.out.println("创建目录为：" + dirPath+"成功");
			}else{
				System.out.println("创建目录为：" + dirPath+"失败");
			}
		}
	}
	
	/** 
	* 复制整个文件夹内容 
	* @param oldPath String 原文件路径 如：c:/fqf 
	* @param newPath String 复制后路径 如：f:/fqf/ff 
	* @return boolean 
	*/ 
	public static void copyFolder(String oldPath, String newPath) {
		FileInputStream input = null;
		FileOutputStream output = null;
		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					input = new FileInputStream(temp);
					output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch(Exception e){
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		String sourceFilePath = "D:\\test";  
        String zipFilePath = "D:\\tmp";  
        copyFolder(sourceFilePath, zipFilePath);
	}
}
