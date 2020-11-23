<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
				<div class="right_v">
				<form method="post" theme="simple" name="operform"
					action="${webPath}/wkfApprovalUser/Insert">
					<dhcc:formTag property="formwkf0004" mode="query" />
					<div class="from_btn">
						<dhcc:button typeclass="button3" commit="true" value="保存" action="保存"  ></dhcc:button>
						<dhcc:button typeclass="button_form" value="返回" action="返回" onclick="${webPath}/wkfApprovalUser/findByPage"></dhcc:button>
					</div>
				</form>
			</div>
			</div>
			</div>
		</div>
	</body>
</html>