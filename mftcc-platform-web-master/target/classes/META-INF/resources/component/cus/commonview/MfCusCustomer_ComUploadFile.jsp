<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/webPath.jsp" %>
<% 
// ↓ 使用规范命名的变量。
String contextPath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+webPath+"/";
 %>
<script type="text/javascript">
	var docParm='${param.docParm}';//后台拼接好的参数
	var cusType = '${param.cusType}';
	if(cusType != undefined && cusType != ""){
		docParm = docParm + "&cusType=" + cusType;
	}
</script>
<!--要件 -->
	<div class="row clearfix">
		<div class="col-xs-12 column" >
				<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
			</div>
	</div>