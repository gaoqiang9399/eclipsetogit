package app.base;

import cn.mftcc.util.DateUtil;

/**
 * 时间的工具类转换。
 * 覆盖平台提供的包路径、类名、方法名。
 * @author LiuYF
 * @since 2017-3-20 14:00:00
 */
public class StringTimeStampTools {
	public StringTimeStampTools() {
	}
	private static final StringTimeStampTools INSTANCE = new StringTimeStampTools();
	
	public static final StringTimeStampTools getInstance() {
		return INSTANCE;
	}
	
	/**
	 * setObjValue会调用。一般是保存、入库时使用，需要去格式化。
	 * @param value
	 * @param para
	 * @return
	 */
	public String changeValueByPara(String value, String para) {
		return value.replaceAll("-", "");
	}
	/**
	 * getObjValue会调用。一般是展示时使用，需要加格式化。
	 * @param value
	 * @param para
	 * @return
	 */
	public String changeValueByParaToForm(String value, String para) {
		return DateUtil.getShowDateTime(value);
	}

}
