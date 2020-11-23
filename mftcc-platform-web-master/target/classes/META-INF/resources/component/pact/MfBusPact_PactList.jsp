<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
		var opNo = '${opNo}';
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfBusPact/getPactListAjax?opNo="+opNo,//列表数据查询的url
			    	tableId:"tablepact0002",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	topHeight:0,
			    	pageSize:3,//加载默认行数(不填为系统默认行数)
			    	callback:function(){
			    		$(".footer_loader").remove();
			    		var contheight = parseInt($("#content").outerHeight());
			    		$("#content").css("height",contheight+30);
			    	}
			    });
			    
			    
			    
			 });
			 
			 
			 
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="max-height: 135px;">
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
