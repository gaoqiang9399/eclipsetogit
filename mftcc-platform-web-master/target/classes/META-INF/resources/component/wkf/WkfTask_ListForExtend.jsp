<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script language="javascript">
<!--
var isGoBack = true;
-->
</script>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>在办业务</title>
	</head>
	<body class="body_bg">
	<form method="post" theme="simple" name="cms_form"
		action="${webPath}/wkfTask/findByPageForExtend">
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<table width="100%" align="center" class="searchstyle">
							<tr>
								<td><dhcc:formTag property="formwkf0025" mode="query" /></td>
							</tr>
						</table>
						<div class="tools_372">
							<dhcc:button value="查询" action="查询" commit="true"
								typeclass="btn_80"></dhcc:button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<p class="p_blank">&nbsp;</p>
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<div style="vertical-align: bottom;" class="tabCont">
						<div style="float: left" class="tabTitle">在办任务信息列表</div>
						<dhcc:tableTag paginate="wkfTaskList" property="tablewkf0039" head="true" />
						<div style="vertical-align: bottom;" class="tabCont">
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>