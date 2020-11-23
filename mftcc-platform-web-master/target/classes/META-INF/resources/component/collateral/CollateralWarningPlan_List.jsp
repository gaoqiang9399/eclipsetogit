<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<!--<script type="text/javascript" src="${webPath}/component/include/rightForm.js"></script>-->
<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css"/>
<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
<script type="text/javascript">
	$(function() {
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url:webPath+"/collateralWarningPlan/findByPageAjax",//列表数据查询的url
			tableId : "tabledlcollateralwarningplan0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			//topHeight : 100,//顶部区域的高度，用于计算列表区域高度。
			callback : function() {
				//$("#tablist td[mytitle]").initMytitle();
				$("table").tableRcswitcher({
					name : "isStart",
					onText : "启用",
					offText : "停用"
				});
			}// 方法执行完回调函数（取完数据做处理的时候）
		});
	});

	function collateralInsert() {
		window.location.href = "${webPath}/collateralWarningPlan/input";
	};
	
	function collateralUpdate(obj,url){
		top.openBigForm(url,"催收方案修改","10","80");
	}
	
</script>
<style type="text/css">
.table_content .ls_list tbody tr.selected {
	background-color: #F7F7F7;
}
</style>
</head>
<body style="overflow-y: hidden;">
	<dhcc:markPoint markPointName="collateralPlan_List" />
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<button type="button" class="btn btn-primary pull-left" onclick="collateralInsert();">新增</button>
					<ol class="breadcrumb pull-left">
						<li><a
							href="${webPath}/sysDevView/setC?setType=argu">参数设置</a></li>
						<li class="active">押品预警方案设置</li>
					</ol>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content" style="height: auto;">
				</div>
			</div>
		</div>
	</div>
</body>
</html>