<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>列表</title>
	<script type="text/javascript" src="${webPath }/component/pact/repayapp/js/MfRepayApply_List.js"></script>
	<script type="text/javascript" >
        $(function(){
            MfRepayApply_List.init();
        });
	</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="btn-div">
			<div class="col-md-12 text-center">
				<span class="top-title">还款申请</span>
			</div>
		</div>
		<dhcc:pmsTag pmsId="apply-repayment">
			<div class="btn-div">
				<button type="button" class="btn btn-primary" onclick="MfRepayApply_List.repaymentApply();">还款申请</button>
			</div>
		</dhcc:pmsTag>
		<div class="col-md-12">
			<div class="search-div">
				<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称"/>
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
