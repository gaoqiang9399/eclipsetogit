<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
		var fileName = '${fileName}';
			$(function(){
				MfOaDimission_List.init();
			});
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<div class="btn-div">
						<div class="col-md-2">
							<button type="button" class="btn btn-primary" onclick="MfOaDimission_List.applyInsert();">新增</button>
						</div>
						<div class="col-md-8 text-center">
							<span class="top-title">离职管理</span>
						</div>
					</div>
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=姓名/部门名称"/>
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
<script type="text/javascript" src="${webPath}/component/oa/dimission/js/MfOaDimission_List.js"></script>
<script type="text/javascript">
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
