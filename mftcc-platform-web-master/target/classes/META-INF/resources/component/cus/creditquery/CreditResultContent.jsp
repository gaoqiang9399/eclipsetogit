<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>征信结果</title>
		<link rel="stylesheet" href="${webPath}/component/thirdservice/baihang/css/potreport.css" />
		<script type="text/javascript" src="${webPath}/component/cus/creditquery/js/MfCreditQueryRecordInfo.js"></script>
	</head>
	<script type="text/javascript">
		var queryId="${queryId}";
		$(function(){
            myCustomScrollbarForForm({
                obj:".scroll-content",
                advanced:{
                    updateOnContentResize:true
                }
            });
			MfCreditQueryRecordInfo.getCreditContent(queryId);
		});
	</script>
	<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag fourColumn">
					<form method="post" id="creditContent" theme="simple" name="operform" >
					</form>
				</div>
			</div>
		</div>
		<div id="bussButton" class="formRowCenter">
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
	</body>
</html>