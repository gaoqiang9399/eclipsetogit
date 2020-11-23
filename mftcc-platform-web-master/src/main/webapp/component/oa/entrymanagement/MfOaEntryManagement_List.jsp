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
						<div class="col-md-2">
							<button type="button" class="btn btn-primary" onclick="MfOaEntryManagement_List.applyInsert();">新增</button>
						</div>
						<div class="col-md-8 text-center">
							<span class="top-title">入职管理</span>
						</div>
					</div>
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=员工姓名/证件号码"/>
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
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
		<script type="text/javascript" src="${webPath}/component/oa/entrymanagement/js/MfOaEntryManagement_List.js"></script>
		<script type="text/javascript" >
		var fileName = '${fileName}';
			$(function(){
			   MfOaEntryManagement_List.init();
			 });
			 filter_dic =[{
					"optCode":"applySts",
					"optName":"申请状态",
					"parm":[{"optName":"未提交","optCode":"0"},
							{"optName":"审批中","optCode":"1"},
							{"optName":"审批通过","optCode":"2"},
							{"optName":"退回","optCode":"3"},
							{"optName":"通过","optCode":"4"},
							{"optName":"否决","optCode":"5"}],
					"dicType":"y_n"}];
		</script>
	</body>
</html>
