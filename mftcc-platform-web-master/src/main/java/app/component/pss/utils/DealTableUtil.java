package app.component.pss.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class DealTableUtil {
	
	/**
	 * 
	 * @param tableHtml
	 * @param map：需要隐藏列的map
	 * @return
	 * @throws Exception
	 */
	public static String dealTabStr(String tableHtml, Map<String, String> map) throws Exception{
		tableHtml = dealInput(tableHtml);
		tableHtml = dealSpeChar(tableHtml);
		String[] tabArr = dealTableLab(tableHtml);
		Document document = DocumentHelper.parseText(tabArr[0]); 
		Element root = document.getRootElement();
		List<Element> theadTrThList = root.element("thead").element("tr").elements("th");
		List<String> indexList = new ArrayList<String>();
		for (int i = 0; i < theadTrThList.size(); i++) {
			Element thElement = theadTrThList.get(i);
			String name = thElement.attributeValue("name");
			if(name != null && map.get(name) != null){
//				thElement.detach();
				thElement.addAttribute("style", "display:none");
				System.out.println("delete:"+name);
				indexList.add(String.valueOf(i));
			}
		}

		List<Element> tbodyTrList = root.element("tbody").elements("tr");
		for (Element tbodyTrElement:tbodyTrList) {
			List<Element> tbodyTrTdList = tbodyTrElement.elements("td");
			for (String indexStr:indexList){
//				tbodyTrTdList.get(Integer.valueOf(indexStr)).detach();
				tbodyTrTdList.get(Integer.valueOf(indexStr)).addAttribute("style", "display:none");
			}
		}

		return document.getRootElement().asXML() + tabArr[1];
	}
	
	//处理table多个标签问题
	private static String[] dealTableLab(String tableStr){
		String[] tables = tableStr.split("</table>");
		String[] tablesTemp = new String[2];
		tablesTemp[1] = "";
		for(int i=0;i<tables.length;i++){
			if(i == 0){
				tablesTemp[i] = tables[i] + "</table>";
			}else{
				tablesTemp[1] += tables[i] + "</table>";
			}
		}
		return tablesTemp;
	}
	
	//处理input没有结束符问题。
	private static String dealInput(String tableStr){
		String[] tabArr = tableStr.split("<input");
		String newTableStr = "";
		String temStr = "";
		for(int i = 0;i < tabArr.length;i++){
			temStr = tabArr[i];
			if(i != 0){
				temStr = "<input" + temStr.replaceFirst(">", "/>");
			}
			
			newTableStr += temStr;
		}
		
		return newTableStr;
	}
	
	//处理特殊字符 &
	private static String dealSpeChar(String tableStr){
		return tableStr.replace("&", "&amp;").replace("<br>", "&lt;br&gt;");
	}
}
