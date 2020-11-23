<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>
		<script type="text/javascript" src="${webPath}/component/wkf/js/wkfTypeRightForm.js" ></script>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/wkfType/findByPageAjax",//列表数据查询的url
			    	tableId:"tablewkf5001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
		</script>
	</head>
	<body style="overflow-y: hidden;">
	<dhcc:markPoint markPointName="WkfType_List"/>
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<button type="button" class="btn btn-primary pull-left" onclick="func_input('${webPath}/wkfType/inputAjax')">新增</button>
						<ol class="breadcrumb pull-left">
							<li><a href="${webPath}/sysDevView/setC?setType=developer">开发者设置</a></li>
							<li class="active">流程视角配置 </li>
						</ol>
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
	</body>	
</html>
