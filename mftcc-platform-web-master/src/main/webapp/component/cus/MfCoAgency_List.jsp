<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="app.tech.oscache.CodeUtils"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
	 CodeUtils cu =new CodeUtils();
	Map<String, String> cusTypeMap = (Map<String, String>) cu.getMapByKeyName("CUS_TYPE");
	String cusTypeMapStr = JSONObject.fromObject(cusTypeMap).toString(); 
%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div top-title">合作机构</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/联系人"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCoAgency_List.js"></script>
<script type="text/javascript">
coAgency.path = '${webPath}';
var cusTypeMap = '<%=cusTypeMapStr%>';
		$(function() {
			coAgency.init(); 
		});	
		
		/*我的筛选加载的json*/
		filter_dic = [
		     {
				"optCode" : "cusType",
				"optName" : "客户类别",
				"parm" : ${cusTypeJsonArray},
				"dicType" : "y_n"
			}, {
           		"optName" : "业务身份",
				"parm" : ${cusTypeSetJsonArray},
				"optCode" : "ext1",
				"dicType" : "y_n"
              }
          ];
     </script>
</html>
