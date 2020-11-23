<%@page import="app.tech.oscache.CodeUtils"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%
 String[] roleNoStr=(String[]) request.getSession().getAttribute("roleNo");
String roleNo="";
Map<String, String> pmsBizMap =new HashMap<String, String>();
String pmBizNo="";
CodeUtils codeUtils = new CodeUtils();
for(int i=0;i<roleNoStr.length;i++){
	if(roleNoStr[i]!=null&&!"".equals(roleNoStr[i])){
		roleNo=roleNoStr[i];
		pmBizNo=(String)codeUtils.getObjCache4Pms(roleNo);
		if(pmBizNo!=null&&!"".equals(pmBizNo)){
			String[] pmBizNoArr=pmBizNo.split(",");
			for(String pmsBizNo:pmBizNoArr){
				pmsBizMap.put(pmsBizNo, pmsBizNo);
			}
		}
	}
}
if(!pmsBizMap.isEmpty()){
	pmBizNo="";
	for(String key:pmsBizMap.keySet()){
		pmBizNo=pmBizNo+","+key;
	}
} 
%>
<script type="text/javascript" src="${webPath }/component/include/bussNodePmsBiz.js"></script>
<script type="text/javascript">
	//当前角色开启的功能权限。
	var pmBizNo='<%=pmBizNo%>';
</script>
