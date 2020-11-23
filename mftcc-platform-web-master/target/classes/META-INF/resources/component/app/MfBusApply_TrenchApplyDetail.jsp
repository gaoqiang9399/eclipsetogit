<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="app.component.common.BizPubParm" %>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/bussNodePmsBiz.jsp"%>
<%
	String investigateScNo = BizPubParm.SCENCE_TYPE_DOC_INVESTIGATION;
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/component/cus/css/MfCusCustomer_Detail.css" />
		<link rel="stylesheet" href="${webPath}/component/app/css/MfBusApply_Detail.css" />
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript" src="${webPath}/component/app/js/pub_apply_view_closure.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<!-- 同盾认证报告 -->
<!-- 		<script type="text/javascript" charset="utf-8" src="//cdnjs.tongdun.cn/preloan/tdreport.1.4.min.js?r=" + (newDate()).getTime()></script>
 -->		<script type="text/javascript" src="${webPath}/component/include/showBusinessCount.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
		var pactId,fincId,pleId;
		var cusNo = '${mfBusApply.cusNo}';
		var appId = '${mfBusApply.appId}';
		var pactId = '${mfBusApply.pactId}';
		var cusType = '${mfCusCustomer.cusType}';
		var appSts = '${mfBusApply.appSts}';//申请状态在BizPubParm.java中的APP_STS_***中
		var wkfAppId = '${mfBusApply.wkfAppId}';
		var kindNo = '${mfBusApply.kindNo}';
		var investigateScNo ='<%=investigateScNo%>';
		var headImg = '${mfCusCustomer.headImg}';
		var ifUploadHead = '${mfCusCustomer.ifUploadHead}';
		var busModel = '${mfBusApply.busModel}';
		var cusNoTmp = "";
		var riskLevel = '${dataMap.riskLevel}';
		var modelNo = '${mfBusApply.kindNo}';
		var webPath = '${webPath}';
		var basePath = '${webPath}';
		var pleFlag = '${pleFlag}';
		var hisTaskObj = '${dataMap.hisTaskList}';
	    hisTaskObj = JSON.parse(hisTaskObj);
		var query = '${query}';
		var operable = '${operable}';
		var docParm = "query="+query+"&cusNo="+cusNo+"&relNo="+cusNo+"&appId="+appId;//查询文档信息的url的参数
		var entrance = "business";
		var vouType = '${mfBusApply.vouType}';
		var applyProcessId = '${mfBusApply.applyProcessId}';
		var rateUnit = '${rateUnit}';
		var busEntrance = '${busEntrance}';
		var busSubmitCnt = '${dataMap.busSubmitCnt}';
		var pageView = "busView";//业务视角
		var idCardNum = '${mfCusCustomer.idNum}';
		var itemNo = '${dataMap.itemNo}';//服务的编号
		var itemType = '${dataMap.itemType}';//服务类型
		var busEndDate = '${busEndDate}';
		var formEditFlag = '${cusFormQuery}';//表单单子段可编辑的标志
		var fincIdTmp=fincId;
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix">	
			<!--申请主信息 -->
			<div class="col-md-8 column block-left">
				<div class="bg-white block-left-div">
					<!--头部信息 -->
					<c:forEach items="${ dataMap.headView}" var="item">
							<jsp:include page="${item.blockUrl }"   flush="true"></jsp:include>
					</c:forEach>

					<!--业务主信息-->
					<c:forEach items="${ dataMap.bodyView}" var="item">
						<c:if test="${item.pmsBizNo==''}">
							<jsp:include page="${item.blockUrl }" flush="true"></jsp:include>
						</c:if>
						<c:if test="${item.pmsBizNo!=''}">
							<jsp:include page="${item.blockUrl}" flush="true"></jsp:include>
							<%-- <dhcc:pmsTag pmsId="${item.pmsBizNo}">
 							</dhcc:pmsTag> --%>
						</c:if>
					</c:forEach>					
				</div>
			</div>
			<!-- 申请附属信息 -->
			<div class="col-md-4 column block-right">
				<div class="bg-white block-right-div">
					<c:forEach items="${ dataMap.afftView}" var="item">
						<c:if test="${item.pmsBizNo==''}">
							<jsp:include page="${item.blockUrl }" flush="true"></jsp:include>
						</c:if>
						<c:if test="${item.pmsBizNo!=''}">
							<jsp:include page="${item.blockUrl}" flush="true"></jsp:include>
							<%-- <dhcc:pmsTag pmsId="${item.pmsBizNo}">
 							</dhcc:pmsTag> --%>
						</c:if>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>	
</body>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_TrenchApplyDetail.js"></script>
<script type="text/javascript" src="${webPath}/component/app/js/inputDisagreeBuss.js"></script>
<%-- <script type="text/javascript" src="${webPath}/component/cus/creditquery/js/MfCreditQueryRecordInfo.js"></script> --%>
<script type="text/javascript" src="${webPath}/component/thirdservice/cloudmftcc/js/MfThirdMftccHighcourt.js"></script>
<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusBorrowerInfo_Insert.js'> </script>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusApplyPersonSurvey.js"></script>
</html>