<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><%=request.getAttribute("title")%></title>
<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}">
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
<script type="text/javascript" src="${webPath}/component/model/js/templateBiz_printSurveyReportList.js"></script>

<script type="text/javascript">
	var appId = '${appId}';
	var temParm = 'appId=' + appId;// 文档书签取值依赖条件，目前支持appId pactId cusNo fincId repayDetailId
	$(function() {
	});
</script>
</head>
<body class="bg-white">
	<div class="list-table margin_0">
		<div class="title">
			<span><i class="i i-xing blockDian"></i>调查报告</span>
		</div>
	</div>

	<div id="bizConfigs" class="template-config item-div padding_left_15">
		<div class="item-div">
			<c:forEach var="bizConfig" items="${bizConfigList}" varStatus="stat">
				<div id="${bizConfig.templateBizConfigId}" class="block-item">
					<c:set var="imgClass" value="item-word" />
					<c:if test="${bizConfig.templateSuffix == '2'}">
						<c:set var="imgClass" value="item-excel" />
					</c:if>
					<c:if test="${bizConfig.templateSuffix == '3'}">
						<c:set var="imgClass" value="item-pdf" />
					</c:if>

					<div class="item-title ${imgClass}" onclick="templateBiz_printSurveyReportList.printFile('${bizConfig.templateBizConfigId}');">
						<span>${bizConfig.templateNameZh}</span>
						<c:if test="${not empty bizConfig.docFileName}">
							<div class="color_theme">
								<i class="i i-jia3"></i>已保存
							</div>
						</c:if>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

</body>
</html>