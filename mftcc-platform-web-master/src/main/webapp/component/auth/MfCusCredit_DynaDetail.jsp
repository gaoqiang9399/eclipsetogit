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
		<link rel="stylesheet" href="${webPath}/component/cus/css/MfCusCustomer_Detail.css" /> 
		<link rel="stylesheet" href="${webPath}/component/app/css/MfBusApply_Detail.css" />
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" /> 
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
			<%--评级相关 strat--%>
		<script type="text/javascript" src="${webPath}/component/eval/js/cardDetailResult.js"></script>
		<script type="text/javascript" src="${webPath}/component/cus/creditquery/js/MfCreditQueryRecordInfo.js"></script>
		<script type="text/javascript" src="${webPath}/component/thirdservice/cloudmftcc/js/MfThirdMftccHighcourt.js"></script>
		<%--评级相关 end--%>
		<script type="text/javascript">
			var cusNo = '${cusNo}';
			var useType = '${useType}';
			var appId = "${relNo}";
			var scNo = "${scNo}";
			var creditSts = "${creditSts}";
			var creditType = "${creditType}";
			var pactId = '${pactId}';//授信合同编号
			var fincId;
			var wkfFlag;//表示工作流是否还有节点；0结束，1还有节点
			//授信评级申请id 供展示使用
			var creditAppId = "${relNo}";
            var adjustAppId = "${relNo}";
			var opNo = "${opNo}";
			var cusType = '${mfCusCustomer.cusType}';
			var wkfAppId = '${wkfAppId}';
			var cusBaseType = '${cusBaseType}';
			var baseType = '${baseType}';
			var cusBaseFlag = '${cusBaseFlag}';//表示客户基本信息是否已经录入
			var cusFullFlag = '${cusFullFlag}';//客户信息所有表单信息是否已经全部录入
			var scNo = '${scNo}';//客户要件场景
			var headImg = '${mfCusCustomer.headImg}';
			var ifUploadHead = '${mfCusCustomer.ifUploadHead}';
			var evalCredit = '${evalCredit}';
			var creditStsName = '${creditStsName}';
			//dataMap是业务参数，包括客户表单信息和参与业务信息等。
			var dataMap = '<%=request.getAttribute("dataMap")%>';
			var webPath = '${webPath}';
			var basePath = '${webPath}';
			var query = '${query}';
			var operable = '${operable}';
			var docParm = "query="+query+"&cusNo="+cusNo+"&cusType=credit&relNo="+creditAppId;//查询文档信息的url的参数
			var cusName = '${mfCusCustomer.cusName}';
			var idNum = '${mfCusCustomer.idNum}';
			var cusTel = '${mfCusCustomer.contactsTel}';
			var headImgShowSrc;
			var busEntrance ="${busEntrance}";//业务入口
			var pageView = "cusView";//客户视角
		    var formEditFlag = '${query}';//表单单子段可编辑的标志
			var effectFlag = '${effectFlag}';//客户信息生效标志
			var examResultFlag=false;//是否能查看贷后检查历史标识
			var expansionFlag = 0;
			var entrance = '${entrance}';//授信标识
			var examEntrance = 'pact';//贷后检查入口标识（合同）
			var isValid = '${isValid}';//授信有效标识
			var creditModel = '${creditModel}';//授信模型
			var creditName = '${creditName}';//授信流程名称
			var isCreditFlag = '${isCreditFlag}';//是否发起首笔担保业务
			var appNodeNo = '${appNodeNo}';//是否发起首笔担保业务
			var templateCreditId = '${templateCreditId}';//是否发起首笔担保业务
            var busModel = '${busModel}';//业务模式
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
			
			<%-- <div name="creditTemplateInfo" title="授信模板信息" class="dynamic-block list-table" >
				<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
			</div>  --%>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/auth/js/MfCusCredit_DynaDetail.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_common.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/themes/factor/js/selectInfo.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusBankAccManage.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/collateral.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/auth/js/MfCusCredit.js"></script>
<script type="text/javascript" src="${webPath}/component/cus/trenchsubsidiary/js/MfTrenchUser_Insert.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/examine/js/BusExamine.js"></script>
<script type="text/javascript" src="${webPath}/component/eval/js/cusEval.js"></script>
<script type="text/javascript" src="${webPath }/component/nmd/js/NmdWay.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditOpenHisData.js'></script>
<script type="text/javascript" src="${webPath}/component/eval/js/cusEval.js"></script>
<%--暂时注释掉这个JS引用，没看到用到的地方，如发现错误把方法抽出来，因为这个js会影响单字段编辑回调和授信的冲突--%>
<%--<script type="text/javascript" src="${webPath}/component/cus/js/MfCusCustomer_Detail.js?v=${cssJsVersion}"></script>--%>
<script type="text/javascript" src="${webPath}/component/eval/js/pub_init_eval_info.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply_Insert.js'></script>
<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
<script type="text/javascript" src='${webPath}/component/auth/js/creditTool.js'></script>
</html>