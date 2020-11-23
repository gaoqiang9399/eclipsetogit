<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
	<title>新增</title>
	<link rel="stylesheet" href="${webPath}/component/report/css/MfReportConfig.css?v=${cssJsVersion}"/>
	<script type="text/javascript">
	 cusTableList = eval("(" + '${json}' + ")").mfReportItemList;
	 var funcType = '${funcType}';
	 //cusTableList = eval("(" + '${json}' + ")").unattentionarray;
	</script>
	<script type="text/javascript" src="${webPath}/component/report/js/MfCusReport_UpdateStas.js?v=${cssJsVersion}"></script>			
	</head>
	<body class="bg-white overflowHidden">
	<div class="container form-container">
	<div class="scroll-content">
	<div class="row clearfix margin_top_10" >
		<div class="col-xs-12 column " id="yes">已选</div>
		<div class="row info-content margin_top_20" id="rotate-body-report">
			<c:if test="${! empty mfBaseItemList}">
				<c:forEach var="mfQueryItem" items="${mfBaseItemList}" varStatus="status">
		     		<dhcc:pmsTag pmsId="${mfQueryItem.itemId}">
						<div class="col-md-3 info-box-div" id="${mfQueryItem.itemId}">
							<div class="info-box" onclick="ReportEntrance.openBase(this,'${mfQueryItem.itemId}');">
								<c:choose>
									<c:when test="${mfQueryItem.funcType == '3'}">
										<div class="info-box-icon-circle"><i class="${mfQueryItem.itemIcon}"></i></div>
									</c:when>
									<c:otherwise>
										<i class="${mfQueryItem.itemIcon}"></i>
									</c:otherwise>
								</c:choose>
								<div class="info-box-content">
									<span class="info-box-text">${mfQueryItem.itemName}</span> 
								</div>
							</div>
							<div class="box-hover">
								<div class="box-hover-content" >
									<button class="rotate-add i i-x42" onclick="deletegouxuan(this,'${mfQueryItem.itemId}','${mfQueryItem.itemId}_no','${mfQueryItem.funcType}');"></button>
								</div>
							</div>
						</div>
		     		</dhcc:pmsTag>
				</c:forEach>
			</c:if>	
		</div>
		<div class="col-xs-12 column" id="no">未选</div>
		<div class="row info-content margin_top_20" id="rotate-body-report-no">
		<c:if test="${! empty mfBaseItemList}">
				<c:forEach var="mfQueryItem" items="${mfBaseItemList}" varStatus="status">
		     		<dhcc:pmsTag pmsId="${mfQueryItem.itemId}">
						<div class="col-md-3 info-box-div" id="${mfQueryItem.itemId}_no">
							<div class="info-box color-no" >
								<c:choose>
									<c:when test="${mfQueryItem.funcType == '3'}">
										<div class="info-box-icon-circle"><i class="${mfQueryItem.itemType}"></i></div>
									</c:when>
									<c:otherwise>
										<i class="${mfQueryItem.itemIcon} i-color-no"></i>
									</c:otherwise>
								</c:choose>
								<div class="info-box-content">
									<span class="info-box-text i-color-no">${mfQueryItem.itemName}</span> 
								</div>
							</div>
							<div class="box-hover">
								<div class="box-hover-content" >
									<button class="rotate-add i i-gouxuan" onclick="gouxuan(this,'${mfQueryItem.itemId}_no','${mfQueryItem.itemId}','${mfQueryItem.funcType}');"></button>
								</div>
							</div>
						</div>
		     		</dhcc:pmsTag>
				</c:forEach>
			</c:if>
		</div>
	</div> 
	</div>
	 	<div class="formRowCenter">
			<dhcc:thirdButton value="确认" action="确认" onclick="setConfirm();"></dhcc:thirdButton>
		</div> 
	</div>
</body>
</html>



