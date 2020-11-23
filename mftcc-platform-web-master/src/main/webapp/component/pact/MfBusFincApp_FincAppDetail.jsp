<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<body class="body_bg">
		<div class="bigform_content">
			<div class="bigForm_content_form">
				<form id="fincAppDetail" method="post" theme="simple" name="operform" action="${webPath}/mfBusFincApp/updateAjaxByOne">
					<dhcc:propertySeeTag property="formfincapp0002" mode="query" />
				</form>
				<c:if test='${mfBusFincApp.fincSts=="5" || mfBusFincApp.fincSts=="7"}'>
					<div class="title">
								<span ><i class="i i-xing blockDian"></i>收款计划</span>
								<!--
								<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#7820184"><i class="i i-close-up"/><i class="i i-open-down"/></button>
								-->
								</div>
					<div class="list-table">
						<div class="content">
							<dhcc:tableTag property="tablerepayplan0003" paginate="mfRepayPlanList" head="true"></dhcc:tableTag>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</body>
</html>