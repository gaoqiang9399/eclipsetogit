<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="../include/pub.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<body class="body_bg">
	<form method="post" theme="simple" name="cms_form"
		action="${webPath}/sysRole/Update">
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<dhcc:formTag property="formsys0018" mode="query" />
						<div class="from_btn">
						<c:if test="query!='query'">
							 <dhcc:button typeclass="button3" commit="true" value="保存" action="保存"  ></dhcc:button>
						 </c:if>
		                     <dhcc:button typeclass="button_form" value="返回" action="返回" onclick="${webPath}/sysRole/findByPage"></dhcc:button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	</body>
</html>