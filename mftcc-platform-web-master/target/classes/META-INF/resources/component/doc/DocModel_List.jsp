<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/docModel/findByPageAjax",//列表数据查询的url
			    	tableId:"tabledocmodel0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
			function newDocModel(url){
				top.addFlag = false;
				top.openBigForm(url,"文档模型配置",function(){
					if(top.addFlag){
						window.location.reload();
					}
				});
			};
		</script>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<div class="col-xs-9 column">
						<button type="button" class="btn btn-primary pull-left" onclick="newDocModel('${webPath}/docModel/input');">新增</button>
						<ol class="breadcrumb pull-left">
							<li><a href="${webPath}/sysDevView/setC?setType=base">基础设置</a></li>
							<li class="active">文档模型配置</li>
						</ol>
					</div>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=模型名称/产品种类"/>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
				</div>
			</div>
		</div>
	</div>
</body>
</html>
