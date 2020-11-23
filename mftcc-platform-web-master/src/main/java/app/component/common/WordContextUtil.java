package app.component.common;

import java.util.List;

/**
 * 在线编辑数据填充
 * 
 */
public class WordContextUtil {

	/**
	 * 1、普通的绑值
	 * @param sb
	 * @param bookMarkName 书签名
	 * @param value 值
	 * @return
	 */
	public static StringBuffer createTextData(StringBuffer sb, String bookMarkName, String value) {
		if (sb.length() != 0) {
			sb.append(",");
		}
		
		sb.append("'"+bookMarkName+"':'"+value+"'");
		
		return sb;
	}
	
	/**
	 * 2、列表的绑值
	 * @param sb
	 * @param tableIndex 模板中的第几个表格，从1开始
	 * @param startRow   表格中，从第几行开始填充记录，从1开始
	 * @param valueList  填充值集合
	 * @return
	 */
	public static StringBuffer createTableData(StringBuffer sb, int tableIndex, int startRow, List<List<String>> valueList) {
		if (sb.length() != 0) {
			sb.append(",");
		}
		
		sb.append("'tab_"+tableIndex+"_"+startRow+"':[");
		for (int i = 0; i < valueList.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			List<String> values = valueList.get(i);
			
			sb.append("{");
			for(int j = 0; j < values.size(); j++) {
				if (j != 0) {
					sb.append(",");
				}
				
				sb.append("'data_"+(j+1)+"':'"+values.get(j)+"'");
			}
			sb.append("}");			
		}	
		sb.append("]");
		
		return sb;
	}
	
	/**
	 * 4、图标弹出页面
	 * @param sb
	 * @param bookMarkName 书签名
	 * @param imgPath      图片地址
	 * @param url          图片路径
	 * @return
	 */
	public static StringBuffer createImgHyperlinkData(StringBuffer sb, String bookMarkName, String imgPath, String url) {
		if (sb.length() != 0) {
			sb.append(",");
		}
		
		sb.append("'"+bookMarkName+"':{'type':'4','imgPath':'"+imgPath+"','url':'"+url+"'}");
		
		return sb;
	}
	
	/**
	 * 3、段落的重复
	 * @param sb
	 * @param bookMarkName 书签名
	 * @param values       重复的段落
	 * @return
	 */
	public static StringBuffer createRepeatData(StringBuffer sb, String bookMarkName, List<String> values) {
		if (sb.length() != 0) {
			sb.append(",");
		}
		
		sb.append("'"+bookMarkName+"':[");
		for(int i = 0; i < values.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append("{'data"+"':'"+values.get(i)+"'}");
		}
		sb.append("]");
		
		return sb;
	}
	
}
