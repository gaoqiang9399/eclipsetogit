<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>要件上传</title>
		<script type="text/javascript">
			var relNo2 = '${param.recourseId}';
			if (relNo2 == "") {
				relNo2 = "relNo";
			}
			var query = '${param.query}';
			var aloneFlag = true;
            var dataDocParm;
            dataDocParm = {
                relNo : relNo2,
                docType : 'certificateDoc',
                docTypeName : "",
                docSplitName : "追偿资料",
                query : query
            };
		</script>
	</head>
	<body style="overflow-x: hidden; overflow-y: hidden">
		<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
	</body>
</html>