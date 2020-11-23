<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
		function insertAjax(obj){
			ajaxInput(obj,"${webPath}/mfCusBankAcceptanceBill/insertAjax?cusNo=${cusNo}&cusName=${cusName}");
		}
		</script>
	</head>
	<body class="body_bg">
		<div class="bigform_content">
  		
		<div class="content_table">
			
			<dhcc:tableTag property="tablecusbankbill0001" paginate="mfCusBankAcceptanceBillList" head="true"></dhcc:tableTag>
			
		</div>
    </div>
	</body>	
</html>
