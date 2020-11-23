<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>原始单据</title>
		<script type="text/javascript">
			var auditStsed = '${param.auditStsed }';
			var docBizNo = '${param.docBizNo }';
			var aloneFlag = true;
			var dataDocParm;
			if (auditStsed == "1") {
				dataDocParm = {
					relNo:docBizNo,
					docType:'pssDoc',
					docTypeName:"",
					docSplitName:"原始单据",
					query:'query'
				};
			} else {
				dataDocParm = {
					relNo:docBizNo,
					docType:'pssDoc',
					docTypeName:"",
					docSplitName:"原始单据",
					query:''
				};
			}
		</script>
	</head>
	<body style="overflow-x:hidden;overflow-y:visible">
		<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
	</body>
</html>