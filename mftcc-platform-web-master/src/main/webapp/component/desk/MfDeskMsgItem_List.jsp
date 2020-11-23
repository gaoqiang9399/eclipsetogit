<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<link rel="stylesheet"
	href="${webPath}/component/desk/css/MfDeskMsgItem_List.css" />
<style type="text/css">
.info-box-div {
	position: relative;
	padding: 0px 30px;
	display: inline-block;
}

.info-box-icon {
	width: 48px;
}
</style>
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div id="attentionDiv" class="row margin_top_20"
				style="padding: 0px 30px;">
				<div class="info-title">已选</div>
				<c:if test = "${dataMap != null}">
				<c:forEach items="${dataMap.attention}" var="attention">
					<dhcc:pmsTag pmsId="${attention.msgType}">
						<div class="col-xs-3 info-box-div" id="${attention.msgId}">
							<div class="info-box margin_bottom_25">
								<i class="info-box-icon i ${attention.msgImg}"></i>
								<div class="info-box-content margin_left_60">
									<span class="info-box-text">${attention.msgTitle}</span>
								</div>
							</div>
							<div class="box-hover">
								<div class="box-hover-content">
									<button class="opt-btn i i-x42" onclick="MfDeskMsgItem_List.deleteItem(this);"></button>
								</div>
							</div>
						</div>
					</dhcc:pmsTag>
				</c:forEach>
				</c:if>
			</div>
			<div id="unattentionDiv" class="row margin_top_20"
				style="padding: 0px 30px;">
				<div class="info-title">未选</div>
				<c:if test = "${dataMap != null}">
				<c:forEach items="${dataMap.unattention}" var="unattention">
					<dhcc:pmsTag pmsId="${unattention.msgType}">
						<div class="col-xs-3 info-box-div box-unselect" id="${unattention.msgId}">
							<div class="info-box margin_bottom_25">
								<i class="info-box-icon i ${unattention.msgImg}"></i>
								<div class="info-box-content margin_left_60">
									<span class="info-box-text">${unattention.msgTitle}</span>
								</div>
							</div>
							<div class="box-hover">
								<div class="box-hover-content">
									<button class="opt-btn i i-gouxuan" onclick="MfDeskMsgItem_List.addItem(this);"></button>
								</div>
							</div>
						</div>
					</dhcc:pmsTag>
				</c:forEach>
				</c:if>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="确认" action="确认"
				onclick="MfDeskMsgItem_List.selectConfirm();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="${webPath}/component/desk/js/MfDeskMsgItem_List.js"></script>
<script type="text/javascript">
	$(function() {
		MfDeskMsgItem_List.init();
	});
</script>
</html>
