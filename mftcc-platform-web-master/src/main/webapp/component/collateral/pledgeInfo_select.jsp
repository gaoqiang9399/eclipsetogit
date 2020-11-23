<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" >
		var cusType = '${cusType}';
		var cusNo = '${cusNo}';
		var appId = '${appId}';
		var pledgeMethod = '${pledgeMethod}';
			$(function(){
				LoadingAnimate.start();
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/pledgeBaseInfo/getPledgeInfoListByCusNoPage",//列表数据查询的url
			    	tableId:"tableselectcollateral0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{pledgeMethod:pledgeMethod,cusNo:cusNo,appId:appId},//指定参数
			    	callback:function(){
			    		LoadingAnimate.stop();
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
			function enterClick(parm){
				var parm=parm.split("?")[1];
				var parmArray=parm.split("&");
				var pledgeNo = parmArray[0].split("=")[1];
				var pledgeName = parmArray[1].split("=")[1];
				var data = new Object();
				data.pledgeNo=pledgeNo;
				data.pledgeName=pledgeName;
				parent.dialog.get('collateralDataDialog').close(data).remove();
			};
		</script>
	</head>
	<body>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div col-xs-7 pull-right">
					<div class="znsearch-div">
						<div class="input-group pull-right">
							<i class="i i-fangdajing"></i>
							<input type="text" class="form-control" id="filter_in_input" placeholder="押品名称">
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
