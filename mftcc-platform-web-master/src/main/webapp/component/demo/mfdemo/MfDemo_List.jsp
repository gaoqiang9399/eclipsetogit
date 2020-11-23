<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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
				<div class="col-md-12">
					<div class="btn-div">
						<button type="button" class="btn btn-primary" onclick="MfDemoList.demoInsert();">新增</button>
					</div>
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/项目名称"/>
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
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>
	<!--闭包写法 -->
	<script type="text/javascript" src="${webPath}/component/demo/mfdemo/js/MfDemo_List.js"></script>
	<script type="text/javascript">
		// 接收传参等
		$(function() {
			MfDemoList.init();
		});
	/*我的筛选加载的json*/
	filter_dic = [ {
		"optName" : "申请金额",
		"parm" : [],
		"optCode" : "appAmt",
		"dicType" : "num"
	},{
		"optName" : "申请利率",
		"parm" : [],
		"optCode" : "rate",
		"dicType" : "num"
	}, {
		"optName" : "申请期限",
		"parm" : [],
		"optCode" : "term",
		"dicType" : "num"
	},{
		"optCode" : "cusType",
		"optName" : "客户类型",
		"parm" : ${cusTypeJsonArray},
		"dicType" : "y_n"
	}];
		
	</script>
</html>
