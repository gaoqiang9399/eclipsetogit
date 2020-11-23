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
			url:webPath+"/mfBusPact/getCheckListAjax?opNo="+opNo,//列表数据查询的url
			tableId:"tablepact1001",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			topHeight:0,
			pageSize:30//加载默认行数(不填为系统默认行数)
		});
	});

	function inputForCheck(obj,lk) {
		top.openBigForm(webPath + lk, "费用确认", function () {
			window.location.reload();
		});
	}

	function checkAmt(){

	}
</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div style="display:none;">
				<input name="cusName" type="hidden"></input>
				<input name="cusNo" type="hidden"></input>
			</div>
			<div class="btn-div">
				<dhcc:pmsTag pmsId="auth-credit-list-add">
					<button type="button" class="btn btn-primary" onclick="checkAmt();">批量费用确认</button>
				</dhcc:pmsTag>
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
