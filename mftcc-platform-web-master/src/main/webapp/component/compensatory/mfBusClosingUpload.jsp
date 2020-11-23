<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>要件上传</title>
		<script type="text/javascript">
			var relNo = '${param.closingId}';
			if (relNo == "") {
				relNo = "relNo";
			}
			var query = '${param.query}';
			var aloneFlag = true;
			var dataDocParm;
			dataDocParm = {
				relNo : relNo,
				docType : 'certificateDoc',
				docTypeName : "",
				docSplitName : "结案资料",
				query : query
			};
		</script>
	</head>
	<body style="overflow-x: hidden; overflow-y: hidden">
		<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
	</body>
</html>