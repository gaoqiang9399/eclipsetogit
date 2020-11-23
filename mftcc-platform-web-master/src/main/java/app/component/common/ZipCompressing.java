package app.component.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 压缩zip工具类
 * 
 * @author WANGCONG
 * 
 */
public class ZipCompressing {

	public ZipCompressing() {
	}

	/*public static void main(String[] args) {
		try {
			List<File> files = new ArrayList<File>();
			files.add(new File(
					"D:\\Work\\upload\\file\\APP20160803000743\\01\\5e64cfa233bb486baffca8d380f1c6eb/1 (2).png"));
			files.add(new File(
					"D:\\Work\\upload\\file\\C010000506\\01\\5b076ab873f24220b026a7fa5501b3b3/1 (1).jpg"));
			files.add(new File("D:\\Work\\测试打包文件.txt"));
			ZipCompressing.zipDirFile("d:/Work", "d:/test3.zip");
			ZipCompressing.zipFiles(files, "d:/test2.zip");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}*/

	/**
	 * 压缩指定的多个文件
	 * 
	 * @param fileList
	 *            文件集合
	 * @param fileName
	 *            压缩包名称包含全路径
	 * @throws Exception
	 */
	public static boolean zipFiles(List<File> fileList, String fileName) throws Exception {
		ZipOutputStream zos = null;
		InputStream is = null;
		try {
			createDir(fileName);
			zos = new ZipOutputStream(new FileOutputStream(fileName));
			ZipEntry ze = null;
			byte[] buf = new byte[1024];
			int readLen = 0;
			for (int i = 0; i < fileList.size(); i++) {
				File f = (File) fileList.get(i);
				if(!f.exists()){
					continue;
				}
				ze = new ZipEntry(f.getName());
				ze.setSize(f.length());
				ze.setTime(f.lastModified());
				zos.putNextEntry(ze);
				is = new BufferedInputStream(new FileInputStream(f));
				while ((readLen = is.read(buf, 0, 1024)) != -1) {
					zos.write(buf, 0, readLen);
				}
				zos.setEncoding("GBK");
				is.close();
			}
			//zos.close();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			try {
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				//throw new RuntimeException(e);
			}
			try {
				if (null != zos) {
					zos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 压缩指定文件夹
	 * 
	 * @param baseDir
	 *            文件夹全路径
	 * @param fileName
	 *            压缩包名称包含全路径
	 * @throws Exception
	 */
	public static boolean zipDirFile(String baseDir, String fileName)
			throws Exception {
		List<File> fileList = getSubFiles(new File(baseDir));
		createDir(fileName);
		ZipOutputStream zos = null;
		InputStream is = null;
		try {
			zos = new ZipOutputStream(new FileOutputStream(fileName));
			ZipEntry ze = null;
			byte[] buf = new byte[1024];
			int readLen = 0;
			for (int i = 0; i < fileList.size(); i++) {
				File f = (File) fileList.get(i);
				ze = new ZipEntry(getAbsFileName(baseDir, f));
				ze.setSize(f.length());
				ze.setTime(f.lastModified());
				zos.putNextEntry(ze);
				is = new BufferedInputStream(new FileInputStream(f));
				while ((readLen = is.read(buf, 0, 1024)) != -1) {
					zos.write(buf, 0, readLen);
				}
				zos.setEncoding("GBK");
				is.close();
			}
			//zos.close();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			try {
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				//throw new RuntimeException(e);
			}
			try {
				if (null != zos) {
					zos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * 获取文件全路径
	 * 
	 * @param baseDir
	 *            文件夹名称
	 * @param realFileName
	 *            当前文件
	 * @return
	 */
	private static String getAbsFileName(String baseDir, File realFileName) {
		File real = realFileName;
		File base = new File(baseDir);
		String ret = real.getName();
		while (true) {
			real = real.getParentFile();
			if (real == null) {
                break;
            }
			if (real.equals(base)) {
                break;
            } else {
                ret = real.getName() + "/" + ret;
            }
		}
		return ret;
	}

	/**
	 * 递归取文件夹下文件
	 * 
	 * @param baseDir
	 * @return
	 */
	private static List<File> getSubFiles(File baseDir) {
		List<File> ret = new ArrayList<File>();
		File[] tmp = baseDir.listFiles();
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].isFile()) {
                ret.add(tmp[i]);
            }
			if (tmp[i].isDirectory()) {
                ret.addAll(getSubFiles(tmp[i]));
            }
		}
		return ret;
	}

	/**
	 * 目录不存在时，先创建目录
	 * 
	 * @param zipFileName
	 */
	private static void createDir(String zipFileName) {
		String filePath = StringUtils.substringBeforeLast(zipFileName, "/");
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {// 目录不存在时，先创建目录
			targetFile.mkdirs();
		}
	}
}
