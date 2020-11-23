package cn.mftcc.util;

import java.text.SimpleDateFormat;

/**
 * @author LiuYF
 *
 */
public class WaterIdUtil {
	private static byte[] lock = new byte[0];
	private static short _suffix = 10;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");

	public WaterIdUtil() {
	}
	/**
	 * 根据当前毫秒时间，获取带动态后缀的流水号。
	 * @author LiuYF
	 * @return
	 */
	public static String getWaterId() {
		StringBuffer strb = new StringBuffer();
		synchronized (lock) {
			if (_suffix > 99) {
				_suffix = 10;
			}
			strb.append(sdf.format(System.currentTimeMillis()));
			strb.append(_suffix++);
		}
		return strb.toString();
	}
	
	/**
	 * @param prefix 前缀，需要根据调用自行决定是否带"_"符号。
	 * @return prefix + waterId
	 * @author LiuYF
	 */
	public static String getWaterId(String prefix) {
		return prefix + getWaterId();
	}
	
}
