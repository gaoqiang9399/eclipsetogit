<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/bussNodePmsBiz.jsp"%>
<%
	String cellDatas = (String) request.getAttribute("cellDatas");
	String blockDatas = (String) request.getAttribute("blockDatas");
%>
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
		<script type="text/javascript" src="${webPath}/component/pact/js/pub_finc_main_view_closure.js"></script>

		<script type="text/javascript">
		var pactId,appId,wkfAppId,cusNo,pactSts,fincSts,fincId,pleId;
		var examHisId = '${examHisId}';
		var examHis = '${examHis}';//==examHis贷后检查审批页面跳转过来，合同审批历史和放款审批历史不展示
		var wkfFlag;//表示工作流是否还有节点；0结束，1还有节点
		var headImg = '${headImg}';
		var ifUploadHead = '${ifUploadHead}';
		var busModel = '${busModel}';
		var coreCusNo = '${coreCusNo}';
		var fundCusNo = '${fundCusNo}';
		var wareHouseCusNo = '${wareHouseCusNo}';
		var pactModelNo = '${mfBusPact.kindNo}';
		var kindNo = '${mfBusPact.kindNo}';
		var pactId = '${mfBusPact.pactId}';
		var pact_id=pactId;
		var cusNo = '${mfBusPact.cusNo}';
		var appId = '${mfBusPact.appId}';
		var appSts = '${mfBusApply.appSts}';
		var wkfAppId = '${mfBusPact.wkfAppId}';
		var pactSts = '${mfBusPact.pactSts}';//合同状态值参考BizPubParm中的字段PACT_STS**
		var fincSts = '${mfBusFincApp.fincSts}';
		var fincId = '${mfBusFincApp.fincId}';
		var fincChidId = '${fincChidId}';
		var wkfRepayId = '${mfBusFincApp.wkfRepayId}';
		var pleId = '${mfBusPledge.pleId}';
		var term = '${mfBusPact.term}';
		var termType = '${mfBusPact.termType}';
		var cusNoTmp = "";
		var webPath = '${webPath}';
		var query = '${query}';
		var operateflag='${operateflag}';
		var operable = '${operable}';
		var queryFile = '${queryFile}';
		var docParm = "query="+queryFile+"&cusNo="+cusNo+"&relNo="+cusNo+"&appId="+appId+"&pactId="+pactId;//查询文档信息的url的参数
		var hasLawsuit = '${mfBusPact.hasLawsuit}';
		var fiveclassId = "${fiveclassId}";
		var recParam = <%=request.getAttribute("recParam")%>;
		var hasRecallFlag = recParam.hasRecallFlag;
		var recallingFlag = recParam.recallingFlag;
		var entrance = "business";
		//审批子流程开启标志
		var approvalSubFlag = "0";
		//bug修改：显示五级分类流程标识
		var fiveFlag='${dataMap.fiveFlag}';
		var getfiveclassId="${fiveclassId}";//从后台传来的id
		var fincProcessId ="${fincProcessId}";//放款审批流程编号
		var pactProcessId ="${mfBusPact.pactProcessId}";//放款审批流程编号
		var busEntrance ="${busEntrance}";//业务入口
		var transferSts ='${transferSts}';//应收账款转让状态
		var pageView = "busView";//业务/合同视角
        var fincIdTmp=fincId;
		var busSubmitCnt = '1';
        var pactEndFlag ='${dataMap.pactEndFlag}';
        var jiefuFlag ='${jiefuFlag}';
        var huankuanFlag ='${huankuanFlag}';
		</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix">
			<!--合同主信息 -->
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
			<!--合同 附属信息 -->
			<div class="col-md-4 column block-right">
				<div class="bg-white block-right-div">
					<c:forEach items="${ dataMap.afftView}" var="item">
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
		</div>
	</div>

</body>

<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/pact/js/MfBusFincMain_DynaDetail.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/collateral.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/app/js/inputDisagreeBuss.js?v=${cssJsVersion}"></script>
</html>