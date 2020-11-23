package cn.mftcc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 音视频转换工具类
 * @author yudongwei@mftcc.cn
 *
 */
public class MediaConvertUtil implements Runnable {

	/**
	 * 环境变量下面的officeConfig.properties的绝对路径
	 */
	private static final String APP_PATH = Thread.currentThread()
			.getContextClassLoader().getResource("").getPath()
			.replace("%20", " ")
			+ "officeConfig.properties";

	private static String ffmpegPath = "";
	private static Process process;
	private String inputMediaFilePath;
	private String outputMediaDirPath;
	private String fileNameWithoutSuffix;

	public MediaConvertUtil(String inputMediaFilePath,
			String outputMediaDirPath, String fileNameWithoutSuffix) {
		this.inputMediaFilePath = inputMediaFilePath;
		this.outputMediaDirPath = outputMediaDirPath;
		this.fileNameWithoutSuffix = fileNameWithoutSuffix;
	}

	public static void init() throws Exception {
		Properties prop = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(APP_PATH);
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw e;
				}
			}
		}
		ffmpegPath = prop.getProperty("archives.ffmpeg.path");
	}

	@Override
	public void run() {
		convertCommand(inputMediaFilePath, outputMediaDirPath,
				fileNameWithoutSuffix);
		processImg(inputMediaFilePath, outputMediaDirPath,
				fileNameWithoutSuffix);
	}

	public static String convertCommand(String inputMediaFilePath,
			String outputMediaDirPath, String fileNameWithoutSuffix) {
		if (ffmpegPath == null || "".equals(ffmpegPath)) {
			try {
				init();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		String outputMediaFilePath = "";
		if (inputMediaFilePath == null || "".equals(inputMediaFilePath)) {
			return null;
		}
		File inputMediaFile = new File(inputMediaFilePath);
		if (!inputMediaFile.exists()) {
			return null;
		}
		File outputMediaDir = new File(outputMediaDirPath);
		if (!outputMediaDir.exists()) {
			outputMediaDir.mkdirs();
		}
		String format = ".mp4";
		try {
			outputMediaFilePath = outputMediaDirPath + File.separator
					+ fileNameWithoutSuffix + format;
			Integer type = checkVideoType(inputMediaFilePath);
			if (0 == type || 8 == type) {
				List<String> commands = new ArrayList<String>();
				commands.add(ffmpegPath);
				commands.add("-loglevel");
				commands.add("quiet");
				commands.add("-i");
				commands.add(inputMediaFilePath);
				commands.add("-vcodec");
				commands.add("libx264");
				commands.add("-preset");
				commands.add("ultrafast");
				commands.add("-profile:v");
				commands.add("baseline");
				commands.add("-acodec");
				commands.add("aac");
				commands.add("-strict");
				commands.add("experimental");
				// commands.add("-s");
				// commands.add("640*480");
				// commands.add("-b");//视频品质设置（有模糊，要视频清晰使用-qscale）
				// commands.add("568k");
				commands.add("-qscale");// 视频品质
				commands.add("6");// 视频品质参数
				commands.add("-ab");
				commands.add("128k");
				commands.add("-y");// 文件存在选择重写
				commands.add(outputMediaFilePath);
				ProcessBuilder builder = new ProcessBuilder();
				builder.command(commands);
				System.out.println("正在将文件[" + inputMediaFilePath + "]转换为MP4..."); 
				process = builder.start();
				process.waitFor();//等待进程执行完毕
				System.out.println("文件[" + inputMediaFilePath + "]转换完成，输出路径：" + outputMediaFilePath); 
				// 防止ffmpeg进程塞满缓存造成死锁
				/*InputStream error = process.getErrorStream();
				InputStream is = process.getInputStream();
				byte[] b = new byte[1024];
				int readbytes = -1;
				try {
					while ((readbytes = error.read(b)) != -1) {
						// System.out.println("FFMPEG视频转换进程错误信息：" + new
						// String(b, 0, readbytes));
					}
					while ((readbytes = is.read(b)) != -1) {
						// System.out.println("FFMPEG视频转换进程输出内容为：" + new
						// String(b, 0, readbytes));
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					error.close();
					is.close();
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				process.getErrorStream().close();
				process.getInputStream().close();
				process.getOutputStream().close();
			} catch (Exception ee) {
			}
		}
		return outputMediaFilePath;
	}

	private static Integer checkVideoType(String PATH) {
		String type = PATH.substring(PATH.lastIndexOf(".") + 1, PATH.length())
				.toLowerCase();
		// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
		if ("avi".equals(type)) {
			return 0;
		} else if ("mpg".equals(type)) {
			return 0;
		} else if ("wmv".equals(type)) {
			return 0;
		} else if ("3gp".equals(type)) {
			return 0;
		} else if ("mov".equals(type)) {
			return 0;
		} else if ("mp4".equals(type)) {
			// return 9;// 本身是MP4格式不用转换
			return 0;
		} else if ("asf".equals(type)) {
			return 0;
		} else if ("asx".equals(type)) {
			return 0;
		} else if ("flv".equals(type)) {
			return 0;
		} else if ("mp3".equals(type)) {
			return 8;
		}
		// 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
		else if ("wmv9".equals(type)) {
			return 1;
		} else if ("rm".equals(type)) {
			return 1;
		} else if ("rmvb".equals(type)) {
			return 1;
		}else {
		}
		return 9;
	}

	public static String processImg(String videoFilePath, String outputDirPath,
			String fileNameWithoutSuffix) {
		String imageFilePath = "";
		File videoFile = new File(videoFilePath);
		if (!videoFile.exists()) {
			return null;
		}

		try {
			imageFilePath = outputDirPath + File.separator
					+ fileNameWithoutSuffix + ".jpg";
			Integer type = checkVideoType(videoFilePath);
			if (0 == type) {
				List<String> commands = new java.util.ArrayList<String>();
				commands.add(ffmpegPath);
				commands.add("-loglevel");
				commands.add("quiet");
				commands.add("-i");
				commands.add(videoFilePath);
				commands.add("-y");
				commands.add("-f");
				commands.add("image2");
				commands.add("-ss");
				commands.add("10");// 这个参数是设置截取视频多少秒时的画面
				// commands.add("-t");
				// commands.add("0.001");
				commands.add("-s");
				commands.add("1280x720");
				commands.add(imageFilePath);
				ProcessBuilder builder = new ProcessBuilder();
				builder.command(commands);
				process = builder.start();
				process.waitFor();// 等待进程执行完毕
				// 防止ffmpeg进程塞满缓存造成死锁
				/*InputStream error = process.getErrorStream();
				InputStream is = process.getInputStream();
				byte[] b = new byte[1024];
				int readbytes = -1;
				try {
					while ((readbytes = error.read(b)) != -1) {
						// System.out.println("FFMPEG截图进程错误信息："
						// + new String(b, 0, readbytes));
					}
					while ((readbytes = is.read(b)) != -1) {
						// System.out.println("FFMPEG截图进程输出内容为："
						// + new String(b, 0, readbytes));
					}
				} catch (IOException e) {

				} finally {
					error.close();
					is.close();
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				process.getErrorStream().close();
				process.getInputStream().close();
				process.getOutputStream().close();
			} catch (Exception ee) {
			}
		}
		return imageFilePath;
	}

	public static void main(String[] args) {
		try {
			MediaConvertUtil.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*MediaConvertUtil mediaConvertUtil = new MediaConvertUtil(
				"D:\\Work\\upload\\Wildlife.wmv", "D:\\Work\\upload");
		Thread thread = new Thread(mediaConvertUtil);
		thread.start();

		MediaConvertUtil mediaConvertUtil1 = new MediaConvertUtil(
				"D:\\Work\\upload\\Wildlife1.wmv", "D:\\Work\\upload");
		Thread thread1 = new Thread(mediaConvertUtil1);
		thread1.start();

		MediaConvertUtil mediaConvertUtil2 = new MediaConvertUtil(
				"D:\\Work\\upload\\Wildlife2.wmv", "D:\\Work\\upload");
		Thread thread2 = new Thread(mediaConvertUtil2);
		thread2.start();

		MediaConvertUtil mediaConvertUtil3 = new MediaConvertUtil(
				"D:\\Work\\upload\\Wildlife3.wmv", "D:\\Work\\upload");
		Thread thread3 = new Thread(mediaConvertUtil3);
		thread3.start();

		MediaConvertUtil mediaConvertUtil4 = new MediaConvertUtil(
				"D:\\Work\\测试.mp4", "D:\\Work\\upload");
		Thread thread4 = new Thread(mediaConvertUtil4);
		thread4.start();*/
	}

}
