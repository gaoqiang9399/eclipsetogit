<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%
	String cellDatas = (String)request.getAttribute("cellDatas");
	String blockDatas = (String)request.getAttribute("blockDatas");
%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript">
			function testDemo(data){
				console.log(data);
			}
			function oneCallback(data){
				console.log(data);
			}
		</script>
	</head>
<body>
	<!--标记点 未后退准备-->
	<dhcc:markPoint markPointName="demo_query"/>
    <div class="layout">
		<div class='cell' style='top:0px; left:0px;width:400px; background-color:#EBEBEB' data-handle=".handle">
			<div class="info">
				<form  method="post" theme="simple" name="operform" action="${webPath}/demo/updateAjaxByOne">
						<dhcc:propertySeeTag property="formdemo0001" mode="query" ifBizManger="ifBizManger"/>
				</form>
			</div>
		</div>
    </div>
</body>
</html>