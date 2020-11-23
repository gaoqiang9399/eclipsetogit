<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<%
// 	String baseType=(String) request.getParameter("baseType");//0:客户 1:合作机构 2.渠道商 3资金机构
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/component/cus/css/MfCusCustomer_Detail.css" />
		<link rel="stylesheet" href="${webPath}/component/app/css/MfBusApply_Detail.css" />
		<%-- <link rel="stylesheet" href="${webPath}/tech/wkf/css/wjProcessDetail.css" /> --%>
		<%-- <script type="text/javascript" src="${webPath}/tech/wkf/js/wjProcessDetail.js"></script> --%>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		
		<!-- 同盾认证报告 -->
		<!-- <script type="text/javascript" charset="utf-8" src="//cdnjs.tongdun.cn/preloan/tdreport.1.4.min.js?r=" + (newDate()).getTime()></script> -->
		
<script type="text/javascript">
	var cusNo='${dataMap.cusNo}';//客户号
	var query='${query}';
	var baseType='${dataMap.baseType}';
	var baseTypeName='${baseTypeName}';
	var cusType='${dataMap.cusType}';
	var creditSum ='${creditSum}';
	var cusName ='${cusName}';
	var appId ='${relId}';
	var entrance ='${entrance}';
	var usedAmt ='${usedAmt}';
	var dataMap = '${dataMap}';
	var creditModel = '${creditModel}';
	var wkfAppId = "${wkfAppId}";
	var creditAppId = "${creditAppId}";
	var opNoType = "${opNoType}";
	var agenciesId = '${dataMap.agenciesId}';
	var id = '${dataMap.id}';
    var cusTableList = dataMap.cusTableList;
	$(function(){
		MfCusCredit.init(cusTableList);
		$("body").mCustomScrollbar({
			advanced:{
				//滚动条根据内容实时变化
				updateOnContentResize:true
			},
			callbacks: {
				//正在滚动的时候执行回调函数
				whileScrolling: function(){
					if ($(".changeval").length>0) {
						$(".changeval").css("top", parseInt($(".changeval").data("top")) + parseInt(this.mcs.top) - $(".changeval").data("msctop"));
					}
				}
			}
		});
	})
</script>
<script type="text/javascript" src="${webPath}/component/cus/commonview/js/MfCusCustomer_ComView.js"></script>
<script type="text/javascript" src="${webPath}/component/cus/trenchsubsidiary/js/MfTrenchUser_Insert.js"></script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix">
			<!--主信息 -->
			<div class="col-md-8 column block-left">
				<div class="bg-white block-left-div">
					<!--头部信息 -->
					<c:forEach items="${dataMap.headView}" var="item">
							<jsp:include page="${item.blockUrl }"   flush="true"></jsp:include>
					</c:forEach>

					<!--主信息-->
					<c:forEach items="${dataMap.bodyView}" var="item">
						<c:if test="${item.pmsBizNo=='' || item.pmsBizNo == null}">
							<jsp:include page="${item.blockUrl }" flush="true"></jsp:include>
						</c:if>
						<c:if test="${item.pmsBizNo!='' && item.pmsBizNo != null}">
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
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/auth/js/MfCusCredit.js"></script>
<script type="text/javascript" src="${webPath}/component/cus/js/MfBusAgencies.js"></script>
</body>
</html>