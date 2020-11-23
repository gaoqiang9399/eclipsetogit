<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="../include/pub.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type='text/javascript'>
	window.name="curWindow";
</script>
	</head>
	<body class="body_bg">
		<form method="post" theme="simple" name="cms_form"
			action="${webPath}/recallDef/findSysRolePop" target="curWindow">
			<div class="right_bg">
				<div class="right_w">
					<div class="from_bg">
						<div class="right_v">
							<table width="100%" align="center" class="searchstyle">
								<tr>
									<td>
										<dhcc:formTag property="formsys1017" mode="query" />
									</td>
								</tr>
							</table>
							<div class="tools_372">
								<dhcc:button value="查询" action="search" commit="true"
									typeclass="btn_80"></dhcc:button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<p class="p_blank">
				&nbsp;
			</p>

			<div class="right_bg">
				<div class="right_w">
					<div class="from_bg">
						<div class="right_v">
							<div style="vertical-align: bottom;" class="tabCont">
								<div style="float: left" class="tabTitle">
									角色信息列表
								</div>
							</div>
							<dhcc:tableTag paginate="sysRoleList" property="tablesys1009"
								head="true" />
						</div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>