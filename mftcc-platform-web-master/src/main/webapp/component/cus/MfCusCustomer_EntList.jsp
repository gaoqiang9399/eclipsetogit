<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%--<%@ include file="/component/include/pub_view.jsp"%>  --%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<%-- <script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script> --%>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusCustomer_EntList.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" >
			var query = '${query}';
			var cusType = '${cusType}';
			var filterFrom = 'cusList';
			$(function(){
				MfCusCustomer_EntList.init();
			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div style="display:none;">
					<input name="kindName" type="hidden"></input>
					<input name="kindNo" type="hidden"></input>
				</div>
					<div class="btn-div">
						<dhcc:pmsTag pmsId="cus-add-button">
							<button type="button" class="btn btn-primary" onclick="MfCusCustomer_EntList.cusInput();">新增</button>
						</dhcc:pmsTag>
					</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/证件号码"/>
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
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
<script type="text/javascript">
	/*我的筛选加载的json*/
	filter_dic = [
		{
		"optName" : "客户类别",
		"parm" : ${cusTypeJsonArray},
		"optCode" : "cusType",
		"dicType" : "y_n"
	},{
		"optName" : "客户分类",
		"parm" : ${classifyTypeJsonArray},
		"optCode" : "classifyType",
		"dicType" : "y_n"
	},{
		"optName" : "联系人",
		"parm" : [],
		"optCode" : "contactsName",
		"dicType" : "val"
	},{
		"optName" : "联系电话",
		"parm" : [],
		"optCode" : "contactsTel",
		"dicType" : "val"
	}
	];
</script>
</html>
