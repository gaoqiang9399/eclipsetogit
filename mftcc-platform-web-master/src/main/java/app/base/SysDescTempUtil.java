package app.base;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import app.component.sys.entity.SysDescTemp;
import app.util.CodeUtils;

public class SysDescTempUtil {
	public static String replace(String desctempNo,Map<String,Object> map,HttpServletRequest request){
		CodeUtils cu =new CodeUtils();
		try {
			SysDescTemp sysDescTemp = (SysDescTemp) cu.getCacheByKeyName(desctempNo);
			return replace(sysDescTemp, map,request);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String replace(SysDescTemp sysDescTemp,Map<String,Object> map,HttpServletRequest request){
		String descTempContent = "";
		if(sysDescTemp!=null){
			descTempContent = sysDescTemp.getDesctempContent();
			for (String key : map.keySet()) {
				descTempContent = descTempContent.replaceAll("\\{"+key+"\\}", map.get(key)+"");
			}
			descTempContent = descTempContent.replaceFirst("\\[","<span class=\"improSpan\">").replaceFirst("\\]", "</span>");
		}else{
			descTempContent = "未找到模板数据";
		}
		request.getSession().setAttribute("pageInfoMsg",descTempContent);
		return descTempContent;
	}
}
