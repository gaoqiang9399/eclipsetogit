<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="${webPath}/component/oa/css/MfOaEntrance.css" />
</head>
<body>
	<div class="container box">
	 <c:forEach items="${ dataMap.mfQueryItemList}" var="mfQueryItem">
		<dhcc:pmsTag pmsId="${ mfQueryItem.pmsBizNo}">
			<div class="btn btn-app" id="${ mfQueryItem.itemId}">
				<c:if test="${ mfQueryItem.itemId !='frontview' && mfQueryItem.itemId !='trans'}">
					<span class="badge"><i class="fa fa-pulse"><i class="fa fa-spinner"></i></i></span>
				</c:if>
				<div><i class="i i-${ mfQueryItem.itemIcon}"></i></div>
				<div><c:out value="${mfQueryItem.itemName}"></c:out></div>
				<a class="attention-link">移除</a>
			</div>
		</dhcc:pmsTag>
	</c:forEach>
		<div class="btn btn-app" id="addItem">
			<div class="margin_top_15"><i class="i i-jia1 color_theme"></i></div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/oa/js/MfOaEntrance.js"></script>
<script type="text/javascript">
	// 接收传参等
	OaEntrance.path = "${webPath}";
	var mfQueryItemList = 
	$(function() {
		OaEntrance.init();
	});
</script>
</html>