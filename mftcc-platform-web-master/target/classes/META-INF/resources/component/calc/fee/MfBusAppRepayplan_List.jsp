<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			/* $(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfBusAppFee/findByPageAjax",//列表数据查询的url
			    	tableId:"tablebusfee0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 }); */
		</script>
	</head>
	<body>
	<div class="bigform_content">
		<div class="content_table">
			<dhcc:thirdTableTag property="tablerepayplanapply" paginate="mfrepayplanList" head="true"></dhcc:thirdTableTag>
		</div>
    </div>
	</body>	
</html>
