<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>

<head>
		<title>查阅情况</title>
		<script type="text/javascript" >
			$(function(){
				var noticeId = '${noticeId}';
				//var url = "/mfOaNotice/findNoticeLookingByPageAjax?noticeId="+noticeId;
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfOaNotice/findNoticeLookingByPageAjax",//列表数据查询的url
			    	data:{noticeId:noticeId},
			    	tableId:"tableoanotice0002",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
		</script>
	</head>
	<body class="bg-white">
		<div class="container">
		<div class="row clearfix bg-white">
			<!-- <div class="col-md-12 column">
					<div class="search-div col-xs-5 pull-right"">
						<div class="znsearch-div">
						<div class="input-group pull-right">
							<i class="i i-fangdajing"></i>
							<input type="text" class="form-control" id="filter_in_input" placeholder="智能搜索">
							<span class="input-group-addon" id="filter_btn_search">搜索</span>
						</div>
					</div>
					</div>
				</div>
			</div> -->
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
