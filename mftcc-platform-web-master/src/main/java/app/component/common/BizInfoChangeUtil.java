package app.component.common;


public class BizInfoChangeUtil {
	/**
	 * 转换成超链接
	 * @param str 转换的字符串
	 * @param url 转换的链接地址
	 * @return
	 */
	public static String getHyperlink(String ... params){
		StringBuffer link = new StringBuffer();
		link.append("<a onClick=refparent('"+params[1]+"');return false;href='javascript:void(0);'>").append(params[0]).append("</a>");
		return link.toString();
	}
	
}
