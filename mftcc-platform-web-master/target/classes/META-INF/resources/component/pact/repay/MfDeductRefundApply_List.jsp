<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/pact/repay/js/MfDeductRefundApplyList.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url:webPath+"/mfDeductRefundApply/findByPageAjax", //列表数据查询的url
				tableId : "tabledeductrefundapplylist", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
			    });
			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<div class="btn-div">
						<div class="col-md-2">
							<button type="button" class="btn btn-primary" onclick="MfDeductRefundApplyList.applyInsert();">新增</button>
						</div>
						<div class="col-md-8 text-center">
							<span class="top-title">减免/退款申请</span>
						</div>
						<div class="col-md-2">
						</div>
					</div>
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=合同号/借据号"/>
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
	<script type="text/javascript">
	/*我的筛选加载的json*/
	filter_dic = [ {
		"optName" : "申请金额",
		"parm" : [],
		"optCode" : "appSum",
		"dicType" : "num"
	},{
		"optName" : "申请日期",
		"parm" : [],
		"optCode" : "appTime",
		"dicType" : "date"
	},{
		"optCode" : "appType",
		"optName" : "申请类型",
		"parm" : ${appTypeJsonArray},
		"dicType" : "y_n"
	},{
		"optCode" : "appSts",
		"optName" : "申请状态",
		"parm" : ${appStsJsonArray},
		"dicType" : "y_n"
	}];
</script>
</html>
