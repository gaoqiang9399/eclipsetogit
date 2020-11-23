<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/pub.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
				<form method="post" theme="simple" name="cms_form"
					action="${webPath}/sysRole/findByPage">
					<table width="100%" align="center" class="searchstyle">
						<tr>
							<td>
								<dhcc:formTag property="formsys0017" mode="query" />
							</td>
						</tr>
					</table>

					<!-- button div -->
					
					<div class="tools_372">
						<dhcc:button value="查询" action="search" commit="true"
									typeclass="btn_80"></dhcc:button>
						<dhcc:button value="新增" action="新增" typeclass="t_ico_tj"
										onclick="${webPath}/sysRole/input"></dhcc:button>
					</div>
					
					<!-- 列表div -->
					<div class="table_w">
							<dhcc:tableTag paginate="sysRoleList" property="tablesys0009"
								head="true" />
					</div>
				</form>
			</div>
			</div>
		</div>
	</body>
</html>