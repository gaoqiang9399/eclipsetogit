<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/vouafter/js/MfBankStatementRegister_List.js"></script>
		<script type="text/javascript" >
			$(function(){
                MfBankStatementRegister_List.init();
			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<div class="btn-div">
						<div class="col-md-12 text-center">
							<span class="top-title">银行结算单</span>
						</div>
					</div>
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称/委托担保合同编号"/>
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