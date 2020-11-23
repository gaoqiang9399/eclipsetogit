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
	<form method="post" theme="simple" name="cms_form"
		action="${webPath}/wkfApprovalUser/findByPageMapPop" target="curWindow">
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<table width="100%" align="center" class="searchstyle">
							<tr>
								<td><dhcc:formTag property="formwkf0008" mode="query" /></td>
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
							<div style="float:left" class="tabTitle"></div>
							<dhcc:button value="新增" action="新增" typeclass="t_ico_tj"
								onclick="${webPath}/cifBlack/input"></dhcc:button>
						</div>
						<dhcc:tableTag paginate="wkfApprovalUserMapList" property="tablewkf0006" head="true" />
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>