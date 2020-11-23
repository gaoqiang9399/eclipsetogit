<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link id="MfQueryEntrance" rel="stylesheet" href="${webPath}/component/query/css/MfQueryEntrance${skinSuffix}.css?v=${cssJsVersion}" />
		<style>
			.option-div{
				margin-right:0px;
			}
		</style>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div id="itemsDiv" class="margin_top_25">
				<c:forEach items="${resList}" var="mapObj">
				<div class="row clearfix margin_top_20" style="padding: 10px 30px;border-bottom: 1px solid #d2d2d2;">
					<div class="col-xs-12 column">	
					<c:forEach items="${mapObj}" var="queryItem">
						<c:forEach items="${queryItem.value}" var="item">
						<dhcc:pmsTag pmsId="${item.pmsBizNo}">
							<div class="col-xs-3" style="text-align:center">
								<div class="option-div <c:if test='${item.attentionFlag== 1}'>selected</c:if>" data-itemid="${item.itemId}"><span>${item.itemName}</span><i class="i i-sanjiaoduihao"></i></div>
							</div>
						</dhcc:pmsTag>
						</c:forEach>
					</c:forEach>
					</div>
				</div>
				</c:forEach>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="确认" action="确认" onclick="QueryItemList.selectConfirm();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/query/js/MfQueryItem_List.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    $(function(){
    	QueryItemList.init();
    });
   </script>
</html>
