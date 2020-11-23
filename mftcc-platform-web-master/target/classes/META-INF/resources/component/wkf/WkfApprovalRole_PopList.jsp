<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<script type="text/javascript">
	window.name = "curWindow";
	</script>
	<body class="body_bg">
	<form method="post" theme="simple" name="cms_form" action="${webPath}/wkfApprovalRole/findByPagePop" target="curWindow">
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<table width="100%" align="center" class="searchstyle">
							<tr>
								<td><dhcc:formTag property="formwkf0005" mode="query" /></td>
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
						<dhcc:tableTag paginate="wkfApprovalRoleList" property="tablewkf0003" head="true" />
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>