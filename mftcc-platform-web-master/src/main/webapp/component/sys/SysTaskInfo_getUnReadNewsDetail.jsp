<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<script type="text/javascript" src="${webPath}/layout/view/js/newsDialog.js"></script>
	<head>
		<title>消息展示</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<form method="post" id="newsDetailShowForm" theme="simple" name="operform" action="${webPath}/mfCusCustomer/insertForBusAjax">
							<dhcc:bootstarpTag property="formsysTaskInfoShow" mode="query"/>
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter" style="display:block;">
				<input id="nextTaskId" type="hidden" value="${dataMap.nextTaskId}"/>
				<dhcc:thirdButton value="下一条" action="保存"  onclick="newsDialog.toNextNews('1');"></dhcc:thirdButton>
				
			</div>
		</div>
	</body>
	<script type="text/javascript">
		newsDialog.newsPageInit("${dataMap.newsCnt}","${dataMap.ifNext}");
	</script>
</html>