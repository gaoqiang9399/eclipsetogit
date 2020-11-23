<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
<body class="overflowHidden">
	<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-xs-8 column">
							
						</div>
						<div class="col-xs-4 column znsearch-div">
							<div class="input-group pull-right">
								<i class="i i-fangdajing"></i>
								<input type="text" class="form-control" id="filter_in_input" placeholder="智能搜索">
								<span class="input-group-addon" id="filter_btn_search">搜索</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<div class="row clearfix">
		<div class="col-sm-12 column">
			<div id="content" class="table_content" style="height: auto;">
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" >
	var itemNo="${param.itemNo}";
	$(function() {
	LoadingAnimate.start();
	myCustomScrollbar({
		obj : "#content",//页面内容绑定的id
		url : webPath+"/accountPay/getSfUsedViewAjax?itemNo="+itemNo,//列表数据查询的url
		tableId : "tableCM005",//列表数据查询的table编号
		tableType : "thirdTableTag",//table所需解析标签的种类
		pageSize : 30,//加载默认行数(不填为系统默认行数)
		ownHeight : true,
		parentHeight:true,
		callback:function(){}
		});
	LoadingAnimate.stop();
	}); 
</script>
</html>