package app.component.thirdservice.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;



public class ThirdServiceUtil {
	private static ThirdServiceUtil  instance = null; 
	private static Properties propThirdService = null;
	private static Logger logger = LoggerFactory.getLogger(ThirdServiceUtil.class);


	public static String getValue(String key){
		return getProperties(ThirdServiceUtil.PropTypeEnum.thirdService, key);
	}

	private static String getProperties(ThirdServiceUtil.PropTypeEnum propTypeEnum, String key) {
		try {
			if (propTypeEnum.properties == null) {
				String configPath = getPubconfigPath();

				String filePath = configPath + File.separator + propTypeEnum.getFileName();

				Properties p = loadProperties(filePath);

				propTypeEnum.setProperties(p);
			}
			return propTypeEnum.properties.getProperty(key);
		} catch (Exception e) {
			logger.error("读取配置文件内容发生错误：", e);
		}

		return null;
	}
	/**
	 * 在tomcat/pubconfig目录下读取指定文件名的配置信息。
	 * @param filePath
	 * @return Properties
	 * @throws Exception
	 */
	private static Properties loadProperties(String filePath) throws Exception {

//		if (filePath.isEmpty()) {
//			throw new PathException("文件路径为空，无法加载配置文件");
//		}
//		File file = new File(filePath);
//		if (!file.exists()) {
//			throw new FileNotFoundException("要读取的配置文件不存在："+filePath);
//		}

		Properties properties = new Properties();
//		FileInputStream inStream = new FileInputStream(file);
//		try {
//			properties.load(inStream);
//		} catch (Exception e) {
//			logger.error("从配置文件读取时出错，将properties置为null。文件路径：{}", filePath, e);
//			properties = null;
//		} finally {
//			inStream.close();
//		}

		return properties;
	}


	/**
	 *
	 * 方法描述： 获取${tomcat}/pubconfig目录下*.properties路径地址
	 * @return
	 * String
	 * @author 沈浩兵
	 * @throws Exception
	 * @date 2016-10-17 上午10:21:58
	 */
	public static String getPubconfigPath() throws Exception {
		String path = StringUtil.class.getResource("/").toURI().getPath();
		logger.debug("读取getResource路径为：{}", path);

//		if (null != path && !"".equals(path)) {
//			if (path.contains("/lib/")) {
//				path = path.substring(0, path.indexOf("lib"));
//			} else if (path.contains("webapps")) {
//				path = path.substring(0, path.indexOf("webapps"));
//			} else if (path.contains("home")) {
//			} else {
//				throw new NullPointerException("无法获取正确的tomcat 根目录地址，请检查环境变量是否正确:" + path
//						+ " 该错误的原因是由于安装其他软件（QTP，LoadRunner等等）导致java环境变量被篡改，而导致程序引用到的环境变量错误。");
//			}
//			path = path.substring(0, path.lastIndexOf("/"));
//		} else {
//			throw new NullPointerException("无法获取正确的tomcat 根目录地址。");
//		}

		path = path + File.separator + "pubconfig";
		return path;
	}

	/**
	 * 方法描述： 读取Resource目录下.properties的配置信息
	 * @param fileName
	 * @param key
	 * @return
	 * @throws Exception
	 * String
	 * @author Javelin
	 * @date 2017-7-17 下午3:09:19
	 */
	public static String getResourcePropValue(String fileName, String key) throws Exception{
		Properties prop = new Properties();
		String value = null;
		InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
		try {
			prop.load(in);
			value = prop.getProperty(key).trim();
		} catch (IOException e) {
			logger.error("读取配置文件内容发生错误：", e);
			e.printStackTrace();
		}
		return value;
	}

	private enum PropTypeEnum {
		/**
		 * propThirdService
		 */
		thirdService("thirdservice.properties", propThirdService);


		private String fileName;
		private Properties properties;

		private PropTypeEnum(String fileName, Properties properties){
			this.fileName = fileName;
			this.properties = properties;
		}

		public String getFileName() {
			return fileName;
		}

		public void setProperties (Properties properties) {
			this.properties = properties;
		}
	}

}
