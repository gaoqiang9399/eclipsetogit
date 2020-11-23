<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>客户权限申请列表</title>
		<script type="text/javascript" >
			$(function(){
				$(function(){
					MfApplyCusPer_List.init();
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
							<button type="button" class="btn btn-primary" onclick="MfApplyCusPer_List.toApplyInsert();">新增申请</button>
						</div>
						<div class="col-md-8 text-center">
							<span class="top-title">客户权限申请记录查询</span>
						</div>
						<div class="col-md-2">
						</div>
					</div>
					<div class="search-div" id="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称/证件号码"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column"  >
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<script type="text/javascript" src="${webPath}/component/cus/applycusper/js/MfApplyCusPer_List.js"></script>
	</body>
</html>
