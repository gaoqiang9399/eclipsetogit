<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>离职详情</title>
	</head>
	<body class="body_bg">
		<div class="right_bg" >
			<table align="center">
					<tr>
						<td class="headerFont">
							离职申请详情       
						</td>
					</tr>
			</table>
	   <div class="headerPaddingtop">

		<form method="post" theme="simple" name="cms_form"
			action="${webPath}/mfOaDimission/getTabView">
				
			<table width="100%"  align="center" class="searchstyle"  >
				<tr>
					<td class="headerGif">
						&nbsp;
					</td>
				</tr>
			
			<tr>
				<td>
					<dhcc:formTag property="formdimission0001" mode="query" />
				</td>
			</tr>
		</table>

		<div class="tools_444" >
			<dhcc:button value="保存" action="search" onclick="checkboxclick();" commit="true" typeclass="btn_101" ></dhcc:button>
			<dhcc:button value="返回" action="search" onclick="cleanbox();" typeclass="button3" ></dhcc:button>
		</div>
		<div class="endPadding">
		</div>

	</form>
	</div>
	</div>
	</body>
</html>