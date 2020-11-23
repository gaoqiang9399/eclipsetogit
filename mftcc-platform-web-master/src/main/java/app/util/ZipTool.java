package app.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
/**
 * 压缩工具类
 * @author wangcong
 *
 */
public class ZipTool {
    
	private ByteArrayOutputStream outputStream = null;
	private ZipOutputStream zip = null;
	
	public ZipTool() {
		outputStream = new ByteArrayOutputStream();
		zip = new ZipOutputStream(outputStream);
	}
	/**
	 * 添加文件到压缩包
	 * @param filepath
	 */
	public void addFile(String filepath) {
		File file = new File(filepath);
		String filename = file.getName();
		try {
			zip.putNextEntry(new ZipEntry(filename));
			byte[] filebyte = file2byte(file);
			IOUtils.write(filebyte, zip);
			zip.closeEntry();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加文件到压缩包,并指定新的文件名
	 * @param filepath
	 */
	public void addFileReName(String filepath,String NewFileName) {
		File file = new File(filepath);
		try {
			zip.putNextEntry(new ZipEntry(NewFileName));
			byte[] filebyte = file2byte(file);
			IOUtils.write(filebyte, zip);
			zip.closeEntry();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加文件到压缩包，并指定压缩包内路径
	 * @param filepath
	 * @param inZipPath
	 */
	public void addFile(String filepath,String inZipPath) {
		File file = new File(filepath);
		try {
			zip.putNextEntry(new ZipEntry(inZipPath));
			byte[] filebyte = file2byte(file);
			IOUtils.write(filebyte, zip);
			zip.closeEntry();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将压缩包生成到指定位置
	 * @param targetFileName
	 */
	public void outZip(String targetFileName) {
		String fileOutName = targetFileName + ".zip";
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileOutName);
			IOUtils.closeQuietly(zip);
			IOUtils.write(outputStream.toByteArray(), fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			close();
		}

	}
	/**
	 * File 转换成 byte
	 * @param file
	 * @return
	 */
	public static byte[] file2byte(File file) {
		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
	/**
	 * 获取输出流字节
	 * @return byte[]
	 */
	public byte[] getOutputStream() {
		IOUtils.closeQuietly(zip);
		byte[] byteArray = outputStream.toByteArray();
		close();
		return byteArray;
	}
	/**
	 * 关闭流
	 */
	public void close() {
		try {
			if (outputStream != null) {
				outputStream.close();

			}
			if (zip != null) {
				zip.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		ZipTool zt = new ZipTool();
//		zt.addFile("C:\\Users\\Admin\\Pictures\\Coderstory_avatar_4\\Coderstory_4_01.png");
//		zt.addFile("C:\\Users\\Admin\\Pictures\\Coderstory_avatar_4\\Coderstory_4_21.png");
//		zt.addFile("C:\\Users\\Admin\\Pictures\\Coderstory_avatar_4\\Coderstory_4_22.png");
		zt.outZip("F:\\spring_boot\\model\\docmodel\\demo");

		zt.close();
		
	/*	打包并下载方法
	 * response.reset();  
        response.setHeader("Content-Disposition", "attachment; filename=\"dhcc.zip\"");  
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");  
  
        IOUtils.write(data, response.getOutputStream());  */
	}
}
