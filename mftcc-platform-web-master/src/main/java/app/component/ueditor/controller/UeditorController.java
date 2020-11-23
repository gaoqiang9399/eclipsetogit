package app.component.ueditor.controller;

import app.component.ueditor.api.UeditorConfig;
import app.component.ueditor.entity.Ueditor;
import app.component.ueditor.util.UploadfileUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;

/**
 * @ClassName UeditorController
 * @Description TODO
 * @Author jyc
 * @Date 2018/11/6 15:26
 * @Version 1.0
 **/
@Slf4j
@Controller
public class UeditorController {

	@Value("${mftcc.ueditor.UeditorImgUrl}")
	private String flag;
	@Value("${mftcc.upload.uploadFilePath}")
	private String url;

	protected static final String uploadRootDir = "/";


	@RequestMapping(value = "/ueditor", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String ueditor(String action, MultipartFile upfile, HttpServletRequest request) {
		Ueditor ueditor = new Ueditor();
		if (action != null && "config".equals(action)) {
			String webPath= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+"/"+request.getContextPath();
			String ueditorConfig = "";
			if ("pro".equals(flag)) {
				ueditorConfig = UeditorConfig.UEDITOR_CONFIG_PRO.replace("{webPath}",webPath);
			} else if ("dev".equals(flag)) {
				ueditorConfig = UeditorConfig.UEDITOR_CONFIG_DEV.replace("{webPath}",webPath);
			} else {
				ueditorConfig = UeditorConfig.UEDITOR_CONFIG_TEST.replace("{webPath}",webPath);
			}
			return ueditorConfig;
		} else if (action != null && ("uploadimage".equals(action) || "uploadscrawl".equals(action))) {
			if (upfile != null) {
				try {
					return JSON.toJSONString(UploadfileUtils.uploadImage(url, upfile));
				} catch (IOException e) {
					e.printStackTrace();
					ueditor.setState("上传失败");
					return JSON.toJSONString(ueditor);
				}
			} else {
				ueditor.setState("文件为空！");
				return JSON.toJSONString(ueditor);
			}
		} else {
			ueditor.setState("不支持该操作！");
			return JSON.toJSONString(ueditor);
		}
	}

	/**
	 * 在线预览图片
	 *
	 * @param path
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/showImage")
	public void showImage(String path, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("image/jpeg");
		//String fullFileName = ("/upload/" + path);
		String fullFileName = path;
		FileInputStream fis = new FileInputStream(fullFileName);
		OutputStream os = response.getOutputStream();
		// OutputStream os = null;
		try {
			int count = 0;
			byte[] buffer = new byte[1024 * 1024];
			while ((count = fis.read(buffer)) != -1) {
				os.write(buffer, 0, count);
				os.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
	}


	/**
	 * 等比例修改图片大小
	 *
	 * @param path
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/showImage1")
	public void showImage1(String path, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("image/jpeg");
		String fullFileName = path;
		int newWidth = 750;
		int newHight = 0;
		FileInputStream fis = new FileInputStream(fullFileName);
		if (fis != null) {
			BufferedImage sourceImg = ImageIO.read(new FileInputStream(fullFileName));
			if (sourceImg.getWidth() > 750) {
				newHight = newWidth * sourceImg.getHeight() / sourceImg.getWidth();
				fullFileName = url + System.currentTimeMillis() + ".png";
				BufferedImage image = new BufferedImage(newWidth, newHight, BufferedImage.TYPE_INT_BGR);
				Graphics garphics = image.createGraphics();
				garphics.drawImage(sourceImg, 0, 0, newWidth, newHight, null);
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fullFileName));
				//JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				//encoder.encode(tag);
				ImageIO.write(image, "PNG", out);
				out.close();
				fis = new FileInputStream(fullFileName);
			}
		}
		OutputStream os = response.getOutputStream();
		// OutputStream os = null;
		try {
			int count = 0;
			byte[] buffer = new byte[1024 * 1024];
			while ((count = fis.read(buffer)) != -1) {
				os.write(buffer, 0, count);
			}
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
	}

}
