
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<!DOCTYPE html>
<html>
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" rel="stylesheet" type="text/css">
		<link href="${webPath}/themes/view/css/view-main.css" rel="stylesheet"/>
		<link href="${webPath}/component/cus/css/CusInfIntegrityList.css" rel="stylesheet"/>
	</head>
<body class="overflowHidden">
	<div class="ratingBg">
		<div class="contentB" style="height: calc(100% -   50px)">
			<c:if test="${mfCusCustomer.cusBaseType==2 && fn:length(mfCusTableList) == 0}">
				<div style="text-align: center;">暂无数据</div>
			</c:if>
			<c:if test="${mfCusCustomer.cusBaseType!=2 || fn:length(mfCusTableList) != 0}">
				<c:forEach items="${mfCusTableList}" varStatus="statu" var="mfCusTable">
				
					<div
						style="overflow: hidden; margin-left: 30px; margin-right: 30px;"
						class="ratingDiv">
						<ul>
						
							<li class="ratingLi4">${mfCusTable.tableDes}</li>
							<c:if test="${mfCusTable.dataFullFlag==0}">

								<c:choose>
									<c:when test="${formEditFlag == 'query'}">
										<li class="ratingLi3"><i class="fa fa-exclamation-triangle"></i>
											查无/待补充
										</li>
									</c:when>
									<c:otherwise>
										<li class="ratingLi3"
												<dhcc:pmsTag pmsId="cus-edit-${mfCusTable.action}">
													onclick="CusInfIntegrityList.addCusInfo('${mfCusTable.action}','${mfCusTable.tableDes}','${mfCusTable.isMust}','${mfCusTable.showType}','${mfCusTable.sort}','${mfCusTable.tableName}');"
												</dhcc:pmsTag>
										><i class="fa fa-exclamation-triangle"></i>
											查无/待补充
										</li>
									</c:otherwise>
								</c:choose>


							</c:if>
							<c:if test="${mfCusTable.dataFullFlag!=0}">
								<li class="ratingLi2"><i class="fa fa-check-circle-o"></i>填写</li>
							</c:if>
						
						</ul>
					</div>
				
				</c:forEach>
				<c:if test="${mfCusCustomer.cusBaseType==1}">
				<c:if test="${fn:length(cusFinMainList) > 0}">
				<div style="overflow: hidden; margin-left: 30ox; margin-right: 30px;"class="ratingDiv">
					<ul>
						<li class="ratingLi4">财务报表</li>
						<li class="ratingLi2"><i class="fa fa-check-circle-o"></i>填写</li>
					</ul>
				</div>
				</c:if>
				<c:if test="${fn:length(cusFinMainList)<=0}">
				<div style="overflow: hidden; margin-left: 30ox; margin-right: 30px;"class="ratingDiv">
					<ul>
						<li class="ratingLi4">财务报表</li>
						<li class="ratingLi3" onclick="CusInfIntegrityList.addCusFinMain()"><i class="fa fa-exclamation-triangle"></i>未填写</li>
					</ul>
					</div>
				</c:if>
				</c:if>
			</c:if>
		</div>
	</div>
	<script type="text/javascript" src="${webPath}/component/cus/js/CusInfIntegrityList.js"></script>
	<script type="text/javascript">
		var cusNo = "${cusNo}";
		var webPath = "${webPath}";
		$(function(){
			CusInfIntegrityList.init();
		});
    </script>
</body>
</html>