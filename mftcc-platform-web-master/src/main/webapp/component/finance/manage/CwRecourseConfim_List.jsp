<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript" >
	var cusNo = '${cusNo}';
	$(function(){
		myCustomScrollbar({
			obj:"#content",//页面内容绑定的id
			url:webPath+"/cwRecourseConfim/findPactListByAjax",//列表数据查询的url
			tableId:"tablecwrecourseconfimList",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			data:{cusNo:cusNo},//指定参数
			pageSize:30//加载默认行数(不填为系统默认行数)
		});
	 });

	function payConfim(obj,lk) {
		top.openBigForm(webPath + lk, "追偿到账确认", function () {
			window.location.reload();
		});
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
				<div class="col-md-2">
					<%--<button type="button" class="btn btn-primary" onclick="applyInsert();">借阅申请</button>--%>
				</div>
				<div class="col-md-8 text-center">
					<span class="top-title">追偿到账确认</span>
				</div>
				<div class="col-md-2">
				</div>
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
