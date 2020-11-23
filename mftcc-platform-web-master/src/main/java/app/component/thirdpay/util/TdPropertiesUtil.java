package app.component.thirdpay.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TdPropertiesUtil{
	private static Logger logger = LoggerFactory.getLogger(TdPropertiesUtil.class);
	
	/**
	 * 第三方主配置文件
	 */
	public static Properties propertiesThridPay = getThridPayProperty();
	/**
	 * 易宝配置文件
	 */
	public static Properties propertiesYeePay = getYeePayProperty();
	
	/**
	 * 公用接口，根据要读取的类别返回对应配置文件中对应key的值。
	 * @param propTypeEnum
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	private static Properties getThridPayProperty() {
		Properties p = null;
		try {
				String configPath = getThirdPayPath();
				String filePath = configPath + File.separator + "ThirdPay.properties";
				File file = new File(filePath);
				if(file.exists())
				{
					p = loadProperties(filePath);
				}
				else
				{
					p = new Properties();
				}
		} catch (Exception e) {
			logger.error("读取配置文件内容发生错误：", e);
		}
		return p;
	}
	
	/**
	 * 公用接口，根据要读取的类别返回对应配置文件中对应key的值。
	 * @param propTypeEnum
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	private static Properties getYeePayProperty() {
		Properties p = null;
		try {
				String configPath = getThirdPayPath();
				String filePath = configPath + File.separator + "YeePay"+File.separator+"YeePayTransfer.properties";
				File file = new File(filePath);
				if(file.exists())
				{
					p = loadProperties(filePath);
				}
				else
				{
					p = new Properties();
				}
		} catch (Exception e) {
			logger.error("读取配置文件内容发生错误：", e);
		}
		return p;
	}


	/**
	 * 在tomcat/pubconfig目录下读取指定文件名的配置信息。
	 * @param filePath
	 * @return Properties
	 * @throws Exception
	 */
	private static Properties loadProperties(String filePath) throws Exception {
		
		if (filePath.isEmpty()) {
			throw new FileNotFoundException("文件路径为空，无法加载配置文件");
		}
		File file = new File(filePath);
		if (!file.exists()) {
			throw new FileNotFoundException("要读取的配置文件不存在："+filePath);
		}
		
		Properties properties = new Properties();
		FileInputStream inStream = new FileInputStream(file);
		try {
			properties.load(inStream);
		} catch (Exception e) {
			logger.error("从配置文件读取时出错，将properties置为null。文件路径：{}", filePath, e);
			properties = null;
		} finally {
			inStream.close();
		}
		return properties;
	}
	
	
	
	
	
	/**
	 * 
	 * 方法描述：  获取${tomcat}/pubconfig目录下thirdPay路径地址
	 * @return
	 * @throws Exception
	 * String
	 * @author wzw
	 * @date 2017-11-11 下午1:51:54
	 */
	public static String getThirdPayPath() throws Exception {
		String path = System.getProperty("catalina.home") + File.separator  + "pubconfig"+File.separator+"thirdPay";
		return path;
	}
	
}
