<%-- [{"width":"799px","height":"658px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"517px","height":"153px","left":"807.2666625976562px","top":"0px","name":"业务状态","cellid":"cell_2"},{"width":"517px","height":"194px","left":"807.2666625976562px","top":"161.4499969482422px","name":"押品性质","cellid":"cell_3"},{"width":"517px","height":"295px","left":"807.2666625976562px","top":"363.26666259765625px","name":"历史信息","cellid":"cell_4"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"业务状态","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"押品性质","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_4":{"cellid":"cell_4","cellname":"历史信息","celltype":"","cellsts":"","plugintype":"","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/bussNodePmsBiz.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String cellDatas = (String) request.getAttribute("cellDatas");
	String blockDatas = (String) request.getAttribute("blockDatas");
%>
<!DOCTYPE html>
<html lang="zh-cn">

<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link id="MfCusCustomer_Detail" rel="stylesheet" href="${webPath}/component/cus/css/MfCusCustomer_Detail${skinSuffix}.css" />
		<link rel="stylesheet" href="${webPath}/component/app/css/MfBusApply_Detail.css" />
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" /> 
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<%--评级相关 strat--%>
		<script type="text/javascript" src="${webPath}/component/eval/js/cardDetailResult.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<script type="text/javascript" src="${webPath}/component/cus/creditquery/js/MfCreditQueryRecordInfo.js"></script>
		<script type="text/javascript" src="${webPath}/component/thirdservice/cloudmftcc/js/MfThirdMftccHighcourt.js"></script>
		<%--评级相关 end--%>
		<!-- 同盾认证报告 -->
		<script type="text/javascript" charset="utf-8" src="//cdnjs.tongdun.cn/preloan/tdreport.1.4.min.js?r=" + (newDate()).getTime()></script>
		<script type="text/javascript">
			var cusNo="",appId="",pleId="",fincId="",pactId="",creditId="",bidissueId="";
			cusNo = '${cusNo}';
			appId = '${appId}';
			fincId = '${fincId}';
			pactId = '${pactId}';
			creditId='${creditId}';
			bidissueId='${bidissueId}';
			//var isApprove = false;
			var wkfFlag;//表示工作流是否还有节点；0结束，1还有节点
			var wkfAppId = "${wkfAppId}";
			var completeFlag ='${dataMap.completeFlag}';
			//授信评级申请id 供展示使用
			//var creditApproveId = "${creditApproveId}";
			var evalAppNo = "${evalAppNo}";
			var creditType = "";
			var creditAppId = "";
			var cusType = '${mfCusCustomer.cusType}';
			//var cusBaseType = '${mfCusCustomer.cusType}';
			var cusBaseType = '${cusBaseType}';
			var cusBaseFlag = '${cusBaseFlag}';//表示客户基本信息是否已经录入
			var cusFullFlag = '${cusFullFlag}';//客户信息所有表单信息是否已经全部录入
			var scNo = '${scNo}';//客户要件场景
			//var authFlag = '${authFlag}';//是否存在正在进行的客户授信 0否1是
			//var creditSts = '${creditSts}';//授信状态
			var headImg = '${mfCusCustomer.headImg}';
			var ifUploadHead = '${mfCusCustomer.ifUploadHead}';
			//dataMap是业务参数，包括客户表单信息和参与业务信息等。
			var dataMap = <%=request.getAttribute("dataMap")%>;
			var cusTableList = dataMap.cusTableList;
			var cusClassify= '${mfCusClassify.classifyType}';//客户类别，黑名单或者优质客户
			var rankTypeName = '${mfCusClassify.rankTypeName}';
			var cusInfIntegrity = '${mfCusCustomer.infIntegrity}';
			var webPath = '${webPath}';
			var basePath = '${webPath}';
			var query = '${query}';
			var queryFile = '${queryFile}';
			var operable = '${operable}';
            var docParm = "query="+queryFile+"&relNo="+cusNo+"&cusNo="+cusNo+"&scNo="+scNo+"&cusType="+cusType+"&appId="+appId+"&pactId="+pactId+"&fincId="+fincId;//查询文档信息的url的参数
			var cusSubType = "${cusSubType}";
            if(cusSubType == "B"){
                docParm = "query="+queryFile+"&relNo="+cusNo+"&cusNo="+cusNo+"&scNo="+scNo+"&cusType="+cusType;//查询文档信息的url的参数
            }
			var firstKindNo = '${firstKindNo}';
			var cusName = '${mfCusCustomer.cusName}';
			var idNum = '${mfCusCustomer.idNum}';
			var cusTel = '${mfCusCustomer.contactsTel}';
			var headImgShowSrc;
			var busSubmitCnt = '${dataMap.busSubmitCnt}';
			var relation = dataMap.relation;//是否有关联关系
			var busEntrance ="${busEntrance}";//业务入口
			var pageView = "cusView";//客户视角
			var isCusDoc = "cusDoc";//客户视角要件实时刷新
			var evalCredit = '${evalCredit}';
		    var formEditFlag = '${query}';//表单单子段可编辑的标志
			var effectFlag = '${effectFlag}';//客户信息生效标志
			var cusAccountStatus = '${cusAccountStatus}';
			var cusAccountStatusName = '${cusAccountStatusName}';
			var examResultFlag=false;//是否能查看贷后检查历史标识
			var cusLevelName = "${mfCusCustomer.cusLevelName}";
			var reportConfirmFlag = "${reportConfirmFlag}";
			MfThirdMftccHighcourt.init();//法执情况按钮初始化
			var expansionFlag = 0;
			var sysProjectName = "${sysProjectName}";
			var comReportFlag = "${comReportFlag}";
            var projectName = '${projectName}';
            var creditModel = '${creditModel}';
            var examEntrance = 'cus';//贷后检查入口标识（客户）
		</script>
	</head>
	
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-8 column block-left">
				<div class="bg-white block-left-div" >
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
			<!--客户附属信息-->
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
		<div class="backToTop2" onclick="backUp();"></div>
</body>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusGetNotCusInfo.js"></script>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusCustomer_Detail.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_common.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/themes/factor/js/selectInfo.js?v=${cssJsVersion}"></script>

<script type="text/javascript" src="${webPath}/component/cus/js/MfCusBankAccManage.js?v=${cssJsVersion}"></script>

<script type="text/javascript" src="${webPath}/component/collateral/js/collateral.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/auth/js/MfCusCredit_cus.js"></script>
<script type="text/javascript" src="${webPath}/component/auth/js/MfCusCredit.js"></script>
<%-- <script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply.js'></script> --%>
<script type="text/javascript" src="${webPath}/component/cus/trenchsubsidiary/js/MfTrenchUser_Insert.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/examine/js/BusExamine.js"></script>
<script type="text/javascript" src="${webPath}/component/eval/js/cusEval.js"></script>
<script type="text/javascript" src="${webPath}/component/nmd/js/NmdWay.js?v=${cssJsVersion}"></script>
<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusFamilyInfo.js'> </script>
<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusPersonAssets.js?v=${cssJsVersion}'> </script>
<script type="text/javascript"  src='${webPath}/component/cus/share/js/MfCusCustomerShare.js?v=${cssJsVersion}'> </script>
<script type="text/javascript" src="${webPath}/component/eval/js/pub_init_eval_info.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyTable.js?v=${cssJsVersion}"></script>
<script>
    $(function () {
        MfCusCustomerShare.changeShareStatus(cusNo);
        MfCusDyTable.init();
    });
</script>
</html>