<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/bussNodePmsBiz.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css?v=${cssJsVersion}" />
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/component/pact/js/pub_finc_main_view_closure.js"></script>
	<script type="text/javascript" src="${webPath}/component/pact/extension/js/MfBusExtensionCommonForAccount.js"></script>
	<script type="text/javascript" src="${webPath}/component/pact/receaccount/js/MfBusReceBaseInfo_Detail.js?v=${cssJsVersion}"></script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix">
		<!--合同主信息 -->
		<div class="col-md-12 column block-left">
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
		<%--<!--合同 附属信息 -->--%>
		<%--<div class="col-md-4 column block-right">--%>
			<%--<div class="bg-white block-right-div">--%>
				<%--<c:forEach items="${ dataMap.afftView}" var="item">--%>
					<%--<c:if test="${item.pmsBizNo==''}">--%>
						<%--<jsp:include page="${item.blockUrl }" flush="true"></jsp:include>--%>
					<%--</c:if>--%>
					<%--<c:if test="${item.pmsBizNo!=''}">--%>
						<%--<dhcc:pmsTag pmsId="${item.pmsBizNo}">--%>
							<%--<jsp:include page="${item.blockUrl}" flush="true"></jsp:include>--%>
						<%--</dhcc:pmsTag>--%>
					<%--</c:if>--%>
				<%--</c:forEach>--%>
			<%--</div>--%>
		<%--</div>--%>
	</div>
</div>
</body>
<script type="text/javascript">
    var busEntrance ="${busEntrance}";//业务入口
    var cusNo ='${mfBusApply.cusNo}';
    var appId ='${mfBusApply.appId}';
    var pactId ='${mfBusApply.pactId}';
    var fincId ='${fincId}';
    var receId ='${receId}';
    var query ='${query}';
    var repayFlag ='${repayFlag}';
    var jiefuFlag ='${jiefuFlag}';
    var buyBackFlag ='${buyBackFlag}';
    var fincSts ='${fincSts}';
    $(function(){
        MfBusReceBaseInfo_Detail.init();
    });
</script>
</html>