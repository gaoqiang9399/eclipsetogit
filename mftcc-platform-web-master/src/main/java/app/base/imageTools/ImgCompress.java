package app.base.imageTools;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import app.tech.upload.UploadUtil;
import cn.mftcc.util.PropertiesUtil;
import net.coobird.thumbnailator.Thumbnails;

public class ImgCompress {
	private Image img;
	private int width;
	private int height;
	private String path;
	private String fileName;
	private static final String PREFIX = "Compress_";
	private static final String POSTFIX = ".jpg";
	private File file;
	 /*public static void main(String[] args) throws Exception {
		 System.out.println("开始：" + new Date().toLocaleString());
//		 File file = new File("G:\\Chrysanthemum.jpg");
//		 ImgCompress imgCom = new ImgCompress(file);
//		 imgCom.resizeFix(400, 400);
		 System.out.println("结束：" + new Date().toLocaleString());
	 }*/
	/**
	 * 构造函数
	 */
	public ImgCompress(File file) throws IOException {
		this.file = file;
		img = ImageIO.read(file); // 构造Image对象
		width = img.getWidth(null); // 得到源图宽
		height = img.getHeight(null); // 得到源图长
		UUID uuid = UUID.randomUUID();
		this.fileName = PREFIX + uuid.toString().replaceAll("-", "") +POSTFIX;
		try {
			this.path = UploadUtil.getFileUploadPath();// 文件存储路径
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构造函数
	 */
	public ImgCompress(File file, String path, String filename) throws IOException {
		try {
			this.img = ImageIO.read(file); // 构造Image对象
			if(img==null){//如果构造Image对象是null,构造系统默认缩略图Image对象
				//默认走配置文件读取图片，如果配置读取不到图片则使用系统内置默认图片
				String temUrl=PropertiesUtil.getUploadProperty("compressFilePath");
				File temFile=new File(temUrl);
				if(!temFile.exists()){
					temUrl= System.getProperty("catalina.home")+File.separator +"webapps"+File.separator+"factor"+File.separator+
							"themes"+File.separator +"factor"+File.separator +"images"+File.separator+"compressFile.jpg";
					temFile=new File(temUrl);
				}
				this.img = ImageIO.read(temFile); // 构造Image对象
				this.width = img.getWidth(null); // 得到源图宽
				this.height = img.getHeight(null); // 得到源图长
			}else{
				this.width = img.getWidth(null); // 得到源图宽
				this.height = img.getHeight(null); // 得到源图长
			}
			this.path = path; // 文件存储路径
			if ((filename != null) && (filename.length() > 0)) {
				int dot = filename.lastIndexOf('.');
				if ((dot > -1) && (dot < (filename.length()))) {
					this.fileName = PREFIX +filename.substring(0, dot)+POSTFIX;
				}else{
					this.fileName = PREFIX + filename+POSTFIX;// 文件名称
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ImgCompress(InputStream is, String path, String filename) throws IOException {
		try {
			this.img = ImageIO.read(is); // 构造Image对象
			if(img==null){//如果构造Image对象是null,构造系统默认缩略图Image对象
				//默认走配置文件读取图片，如果配置读取不到图片则使用系统内置默认图片
				String temUrl=PropertiesUtil.getUploadProperty("compressFilePath");
				File temFile=new File(temUrl);
				if(!temFile.exists()){
					temUrl= System.getProperty("catalina.home")+File.separator +"webapps"+File.separator+"factor"+File.separator+
							"themes"+File.separator +"factor"+File.separator +"images"+File.separator+"compressFile.jpg";
					temFile=new File(temUrl);
				}
				this.img = ImageIO.read(temFile); // 构造Image对象
				this.width = img.getWidth(null); // 得到源图宽
				this.height = img.getHeight(null); // 得到源图长
			}else{
				this.width = img.getWidth(null); // 得到源图宽
				this.height = img.getHeight(null); // 得到源图长
			}
			this.path = path; // 文件存储路径
			if ((filename != null) && (filename.length() > 0)) {
				int dot = filename.lastIndexOf('.');
				if ((dot > -1) && (dot < (filename.length()))) {
					this.fileName = PREFIX +filename.substring(0, dot)+POSTFIX;
				}else{
					this.fileName = PREFIX + filename+POSTFIX;// 文件名称
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 按照宽度还是高度进行压缩
	 * 
	 * @param w
	 *            int 最大宽度
	 * @param h
	 *            int 最大高度
	 */
	public File resizeFix(int w, int h) throws IOException {
		if (width / height > w / h) {
			return resizeByWidth(w);
		} else {
			return resizeByHeight(h);
		}
	}

	/**
	 * 以宽度为基准，等比例放缩图片
	 * 
	 * @param w
	 *            int 新宽度
	 */
	public File resizeByWidth(int w) throws IOException {
		int h = (int) (height * w / width);
		return resize(w, h);
	}

	/**
	 * 以高度为基准，等比例缩放图片
	 * 
	 * @param h
	 *            int 新高度
	 */
	public File resizeByHeight(int h) throws IOException {
		int w = (int) (width * h / height);
		return resize(w, h);
	}

	/**
	 * 强制压缩/放大图片到固定的大小
	 * 
	 * @param w
	 *            int 新宽度
	 * @param h
	 *            int 新高度
	 */
	public File resize(int w, int h) throws IOException {
		File destFile = null;
		// SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
		FileOutputStream out = null;
		try {
			StringBuilder tempPath = new StringBuilder();
			tempPath.append(path);
			String path = tempPath.toString();
			destFile = new File(path, fileName);
			out = new FileOutputStream(destFile); // 输出到文件流
			// 可以正常实现bmp、png、gif转jpg
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			encoder.encode(image); // JPEG编码
			Thumbnails.of(file).size(w,h).toFile(destFile); 
			//out.close();
		} catch (Exception e) {
			System.out.println("未获取到上传路径");
			e.printStackTrace();
		}
		finally {
			try{
				if(out != null)
				{
					out.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return destFile;
	}
}
