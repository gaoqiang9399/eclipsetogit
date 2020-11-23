<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			var reportType= '${reportType}';
			var zcfzType = '${zcfzType}';
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cusFinParm/findByPageAjax?reportType="+reportType+"&zcfzType="+zcfzType,//列表数据查询的url
			    	tableId:"tablepfs0002",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	myFilter:false, //是否有我的筛选(列表列动态切换)
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{}//指定参数
			    });
			 });
			
			function enterClick(lk){
				var parm=lk.split("?")[1];
				var parmArray=parm.split("&");
				var cusFinParm = new Object();
				cusFinParm.codeColumn = parmArray[0].split("=")[1];
				cusFinParm.codeName = parmArray[1].split("=")[1];
				parent.dialog.get("selectParmDialog").close(cusFinParm);
			};
		</script>
	</head>
<body class="body_bg" style="overflow-y: hidden;">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<div class="col-xs-5 column">
					</div>
					<div class="col-xs-7 column znsearch-div">
						<div class="input-group pull-right">
							<i class="i i-fangdajing"></i>
							<input type="text" class="form-control" id="filter_in_input" placeholder="智能搜索">
							<span class="input-group-addon" id="filter_btn_search">搜索</span>
						</div>
					</div>
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
