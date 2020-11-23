<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/bussNodePmsBiz.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="${webPath}/component/cus/css/MfCusCustomer_Detail.css" />
	<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js"></script>
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
	<script type="text/javascript" src="${webPath}/component/app/js/pub_apply_view_closure.js"></script>
	<script type="text/javascript" src="${webPath}/component/pact/js/pub_pact_view_closure.js"></script>

	<script type="text/javascript">

	   $(function(){
		   myCustomScrollbarForForm({
			   obj: ".container",
			   advanced: {
				   updateOnContentResize: true
			   }
		   });
	   });

	</script>

</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix">
			<!--主信息 -->
			<div class="col-md-8 column block-left">
				<div class="bg-white block-left-div">
					<!--头部信息 -->
					<c:forEach items="${ dataMap.headView}" var="item">
						<jsp:include page="${item.blockUrl }"   flush="true"></jsp:include>
					</c:forEach>
					<!--业务主信息-->
					<c:forEach items="${ dataMap.bodyView}" var="item" varStatus="status">

						<c:if test="${item.pmsBizNo==''}">
							<jsp:include page="${item.blockUrl }" flush="true"></jsp:include>
						</c:if>
						<c:if test="${item.pmsBizNo!=''}">
							<dhcc:pmsTag pmsId="${item.pmsBizNo}">
								<jsp:include page="${item.blockUrl}" flush="true"></jsp:include>
							</dhcc:pmsTag>
						</c:if>
					</c:forEach>
				</div>
			</div>
			<!--附属信息 -->
			<div class="col-md-4 column block-right">
				<div class="bg-white block-right-div">
					<c:forEach items="${dataMap.afftView}" var="item">
						<c:if test="${item.pmsBizNo==''|| item.pmsBizNo == null}">
							<jsp:include page="${item.blockUrl }" flush="true"></jsp:include>
						</c:if>
						<c:if test="${item.pmsBizNo!=''&& item.pmsBizNo != null}">
							<dhcc:pmsTag pmsId="${item.pmsBizNo}">
								<jsp:include page="${item.blockUrl}" flush="true"></jsp:include>
 							</dhcc:pmsTag>
						</c:if>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	<%--<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
	<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/component/pact/js/MfBusPact_DynaDetail.js?v=${cssJsVersion}"></script>--%>
	<script type="text/javascript" src="${webPath}/component/collateral/js/collateral.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/component/app/js/inputDisagreeBuss.js?v=${cssJsVersion}"></script>
<script>




</script>
</body>
</html>