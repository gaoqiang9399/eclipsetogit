<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
				<div class="right_v">
				<form method="post" theme="simple" name="cms_form"
					action="${webPath}/wkfApprovalUser/Update">
					<dhcc:formTag property="formwkf0004" mode="query" />
					<div class="from_btn">
					<c:if test="query!='query'">
						 <dhcc:button typeclass="button3" commit="true" value="保存" action="保存"  ></dhcc:button>
					 </c:if>
	                     <dhcc:button typeclass="button_form" value="返回" action="返回" onclick="${webPath}/wkfApprovalUser/findByPage"></dhcc:button>
					</div>
				</form>
			</div>
			</div>
			</div>
		</div>
	</body>
</html>