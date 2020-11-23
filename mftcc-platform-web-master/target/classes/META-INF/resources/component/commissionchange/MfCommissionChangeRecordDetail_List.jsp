<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			var cusNo = '${cusNo}';
			var tableId = '${tableId}';
			var calcBase = '${calcBase}';
			var commissionChangeId = '${commissionChangeId}';
			var url = webPath + "/mfCommissionChangeRecordDetail/findByPageAjax";
			$(function(){
			    myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : url + "?commissionChangeId=" + commissionChangeId + "&cusNo=" + cusNo + "&calcBase=" + calcBase, //列表数据查询的url
				tableId : tableId, //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			    });
			 });
			function exportExcel(){
				window.top.location.href = encodeURI(url +  "Excel?commissionChangeId=" + commissionChangeId + "&cusNo=" + cusNo + "&calcBase=" + calcBase+ "&tableId=" + tableId);
			};
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<dhcc:pmsTag pmsId="excel-shareProfit">
					<div class="btn-div">
							<button type="button" class="btn myclose cancel" onclick="exportExcel();">导出</button>
					</div>
					</dhcc:pmsTag>
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=${showName}"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
