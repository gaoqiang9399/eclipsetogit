<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ include file="/include/tld.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>查询</title>
	</head>
	<body class="body_bg">
		<div class="right_bg" >
			<table align="center">
					<tr>
						<td class="headerFont">
							信息查询         
						</td>
					</tr>
			</table>
	   <div class="headerPaddingtop">

		<form method="post" theme="simple" name="cms_form"
			action="${webPath}/mfOaArchivesFamily/getTabView">
				
			<table width="100%"  align="center" class="searchstyle"  >
				<tr>
					<td class="headerGif">
						&nbsp;
					</td>
				</tr>
			
			<tr>
				<td>
					<dhcc:formTag property="formarchivefamily0001" mode="query" />
				</td>
			</tr>
		</table>

		<div class="tools_444" >
			<dhcc:button value="查询" action="search" onclick="checkboxclick();" commit="true" typeclass="btn_101" ></dhcc:button>
			<dhcc:button value="重写" action="search" onclick="cleanbox();" typeclass="button3" ></dhcc:button>
		</div>
		<div class="endPadding">
		</div>

	</form>
	</div>
	</div>
	</body>
</html>