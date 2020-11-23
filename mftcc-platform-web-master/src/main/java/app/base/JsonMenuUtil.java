package app.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.component.sys.entity.SysMenu;

public class JsonMenuUtil {

	/**
	 * 把全部无序的树形菜单列表，排成有序，并以json字符串的形式输出
	 * @param sysMenuList
	 * @return
	 * @author zhangweijr 
	 * @date Dec 31, 2011 9:53:25 AM
	 */
	public static String ulist2json(List<SysMenu> sysMenuList){
		String jsonStr = null;
		List<SysMenu> list = JsonMenuUtil.getTreeMenuList(sysMenuList);
		jsonStr = JsonMenuUtil.llist2json(list);
		return jsonStr;
	}
	
	/**
	 * 将有序的菜单树转换成json格式
	 * @param sysMenuList
	 * @return
	 * @author zhangweijr 
	 * @date Dec 31, 2011 10:33:14 AM
	 */
	public static String llist2json(List<SysMenu> sysMenuList){
		StringBuffer jsonbf = new StringBuffer();

		try{
		
		/*[{ "id":"1", "name":"日常提醒", "url":"#", "target":"", "imgurl":"pageframe/images1/menu/nav_ico1.png", nodes:[
           { "id":"11", "name":"日常提醒", "url":"#", "target":"", nodes:[
       		{ "id":"117", "name":"查看页面20", "url":"查看页面20.html", "target":"rightFrame"},*/
		String mF= "";
		
		jsonbf.append("[");
		for(int i=0; i<sysMenuList.size(); i++){
			SysMenu sysMenu = sysMenuList.get(i);
			jsonbf.append("{").append("\"id\":\"").append(sysMenu.getMenuNo()).append("\", \"name\":\"").append(sysMenu.getMenuName())
			.append("\" ,\"url\":\"#\", \"tartget\":\"\", \"imgurl\":\"pageframe/images1/menu/nav_ico").append(i+1).append(".png\", nodes:[");
			for(int j=0; j<sysMenu.getChildren().size(); j++){
				SysMenu sysMenu2 = sysMenu.getChildren().get(j);
				jsonbf.append("{\"id\":\"").append(sysMenu2.getMenuNo()).append("\", \"name\":\"").append(sysMenu2.getMenuName())
				.append("\", \"url\":\"#\", \"target\":\"\", nodes:[");
				for(int k=0; k<sysMenu2.getChildren().size(); k++){
					SysMenu sysMenu3 = sysMenu2.getChildren().get(k);
					if((null !=sysMenu3.getMenuUrl()) && ( sysMenu3.getMenuUrl().contains("?"))){
						mF = "&menuno="+sysMenu3.getMenuNo();
					}else{
						mF = "?menuno="+sysMenu3.getMenuNo();
					}
					jsonbf.append("{\"id\":\"").append(sysMenu3.getMenuNo()).append("\", \"name\":\"").append(sysMenu3.getMenuName().trim())
					.append("\", \"url\":\"").append(sysMenu3.getMenuUrl()+mF).append("\", \"target\":\"rightFrame\"}");
					if(k < sysMenu2.getChildren().size()-1){
						jsonbf.append(",");
					}
				}
				jsonbf.append("]}");
				if(j < sysMenu.getChildren().size()-1){
					jsonbf.append(",");
				}
			}
			jsonbf.append("]}");
			if(i < sysMenuList.size()-1){
				jsonbf.append(",");
			}
		}
		jsonbf.append("]");
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return jsonbf.toString();
	}
	
	public static Map<String,String> ulist2tree(List<SysMenu> sysMenuList){
		List<SysMenu> list = JsonMenuUtil.getTreeMenuList(sysMenuList);
		return JsonMenuUtil.llist2tree(list);
	}
	public static List<SysMenu> ulistwin8(List<SysMenu> sysMenuList) {
		List<SysMenu> list = JsonMenuUtil.getMenuList(sysMenuList);//一级菜单对应其下的三级菜单
		return list;
	}
	
	private static List<SysMenu> getMenuList(List<SysMenu> sysMenuList) {
		List<SysMenu> list1 = new ArrayList<SysMenu>();
		List<SysMenu> list2 = new ArrayList<SysMenu>();
		List<SysMenu> list3 = new ArrayList<SysMenu>();
		for(SysMenu sysMenu : sysMenuList){
			if("1".equals(sysMenu.getMenuLvl())){
				list1.add(sysMenu);
			}
			if("2".equals(sysMenu.getMenuLvl())){
				list2.add(sysMenu);
			}
			if("3".equals(sysMenu.getMenuLvl())){
				list3.add(sysMenu);
			}
		}
		
		
		
		for(SysMenu sysMenu : list3){
			for(SysMenu vsysMenu : list2){
				if(vsysMenu.getMenuNo().equals(sysMenu.getMenuParent())){
					for(SysMenu vsys1Menu : list1){
						if(vsys1Menu.getMenuNo().equals(vsysMenu.getMenuParent())){
							vsys1Menu.getChildren().add(sysMenu);
							break;
						}
					}
				}
			}
		}
		
		return list1;
	}

	public static Map<String,String> llist2tree(List<SysMenu> sysMenuList){
		Map<String,String> map = new HashMap<String,String>();
		try{
			SysMenu sysMenu2,sysMenu3;
			for(SysMenu sysMenu1 : sysMenuList){
				StringBuffer content = new StringBuffer("<div id='accordion' class='entry_basic'>");
				if(sysMenu1.getChildren()!=null && sysMenu1.getChildren().size()>0){
					for(int i=0;i<sysMenu1.getChildren().size();i++){
						sysMenu2 = sysMenu1.getChildren().get(i);
						/*modify by LiuShaofeng 20130924 begin 
						  if(i==0){
							content.append("<a>");
						}else {
							content.append("<a class='entry_basic_a'>");
						}
						end*/
						content.append("<a class='entry_bassic_a'>");
						content.append(sysMenu2.getMenuName()).append("</a><div class='zTreeDemoBackground left'>");
						if(sysMenu2.getChildren()!=null && sysMenu2.getChildren().size()>0){
							content.append("<ul class='entry_basic_ul'>");
							for(int j=0;j<sysMenu2.getChildren().size();j++){
								sysMenu3 = sysMenu2.getChildren().get(j);
								String url = sysMenu3.getMenuUrl();
								if(url==null || "".equals(url)){
									url = "";
								}else {
									if(url.contains("?")){
										url = url + "&menuno=" + sysMenu3.getMenuNo();
									}else {
										url = url + "?menuno=" + sysMenu3.getMenuNo();
									}
								}
								content.append("<li onclick='goMenuUrl(this)' url='").append(url).append("'><span>")
								.append(sysMenu3.getMenuName()).append("</span></li>");
							}
							content.append("</ul>");
						}
						content.append("</div>\n");
					}
				}
				content.append("</div>");
				map.put(sysMenu1.getMenuNo(), content.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 根据菜单列表，按标准树形菜单进行整理
	 * @param sysMenuList
	 * @return 返回整个树形菜单列表
	 * @author zhangweijr 
	 * @date Dec 31, 2011 9:50:50 AM
	 */
	public static List<SysMenu> getTreeMenuList(List<SysMenu> sysMenuList){
		List<SysMenu> list1 = new ArrayList<SysMenu>();
		List<SysMenu> list2 = new ArrayList<SysMenu>();
		List<SysMenu> list3 = new ArrayList<SysMenu>();
		for(SysMenu sysMenu : sysMenuList){
			if("1".equals(sysMenu.getMenuLvl())){
				list1.add(sysMenu);
			}
			if("2".equals(sysMenu.getMenuLvl())){
				list2.add(sysMenu);
			}
			if("3".equals(sysMenu.getMenuLvl())){
				list3.add(sysMenu);
			}
		}
		for(SysMenu sysMenu : list3){
			for(SysMenu vsysMenu : list2){
				if(vsysMenu.getMenuNo().equals(sysMenu.getMenuParent())){
					vsysMenu.getChildren().add(sysMenu);
					break;
				}
			}
		}
		for(SysMenu sysMenu : list2){
			for(SysMenu vsysMenu : list1){
				if(vsysMenu.getMenuNo().equals(sysMenu.getMenuParent())){
					vsysMenu.getChildren().add(sysMenu);
					break;
				}
			}
		}
		return list1;
	}

	public static String ulistMenuNo(List<SysMenu> sysMenuList) {
		List<SysMenu> list = JsonMenuUtil.getMenuList(sysMenuList);//一级菜单对应其下的三级菜单
		//		List<String> sml=new ArrayList<String>();
		 StringBuilder str=new StringBuilder();
		 boolean flag=false;
		for(SysMenu sm:list){
			if(sm.getMenuNo() != null && !"".equals(sm.getMenuNo())){
				if (flag) {
					str.append(",");
	            }else {
	                flag=true;
	            }
				str.append("\""+sm.getMenuNo()+"\"");
			}
		}
//		for(SysMenu sm:list){
//			if(sm.getMenuNo() != null && !sm.getMenuNo().equals("")){
//				sml.add(sm.getMenuNo());
//			}
//		}
//		String str=sml.toString();
		return "["+str.toString()+"]";
	}


}
