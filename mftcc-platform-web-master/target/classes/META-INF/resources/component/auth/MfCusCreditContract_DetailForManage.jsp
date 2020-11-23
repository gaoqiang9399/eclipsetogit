<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>授信协议</title>
		<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}">
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditContract_Detail.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditAdjustApplyInsert.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditTemplate.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply_Insert.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/creditTool.js'></script>
		<script type='text/javascript' src='dwr/engine.js'></script>  
		<script type='text/javascript' src='dwr/util.js'></script>  
		<script type="text/javascript" src="${webPath}/dwr/interface/PageMessageSend.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<style type="text/css">
			.subBtn{
				color: #32b5cb;
				margin-left:62px;
				margin-top: -31px;
				background-color:#fff
			}
		</style>
		<script type="text/javascript">
			var path = "${webPath}";
			var basePath = "${webPath}";
			var modelNo = "<%=(String)request.getAttribute("modelNo")%>";
			var cusNo = "${cusNo}";
			var creditAppId = "${creditAppId}";
			var wkfAppId = "${wkfAppId}";
			var pactId = "${pactId}";
			var nodeNo = "${nodeNo}";
			var cusType = "${cusType}";
			var creditType = "${creditType}";
			if(creditType == '2'){
				var creditAppIdPro = "${creditAppIdPro}";
				var adjAppIdPro = "${adjAppIdPro}";
			}
			
			var mfCusPorductCreditList = eval("(" + '${json}' + ")").mfCusPorductCreditList;
			var mfSysKinds = eval("(" + '${json}' + ")").mfSysKinds;
			
			var appId=creditAppId;
			var index = 0;  //动态增加产品计数使用
			var relId=pactId;
			var tempType="PROTOCOL";//尽调报告
			$(function(){
				//校验客户是否开户
				var params = {
					"cusNo" : cusNo
				};
				mfCusCreditContractDetail.init();
				//加载尽调报告模板
				//MfCusCreditTemplate.template_init();
			});
			var userId = '<%=(String)request.getSession().getAttribute("regNo")%>';
	        PageMessageSend.onPageLoad(userId); 
			dwr.engine.setActiveReverseAjax(true);
		 	dwr.engine.setNotifyServerOnPageUnload(true);
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<c:if  test="openType == 0">
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						</c:if>
						<form  method="post" theme="simple" name="operform" id="operform" action="${webPath}/mfCusCreditContract/updateForManageAjax">
							<dhcc:bootstarpTag property="formcreditpact0001" mode="query"/>
						</form>
	   				</div>
	   				<%--<dhcc:pmsTag pmsId="credit_pact_detail">--%>
		   				<%--<div class="bigform_content col_content">--%>
							<%--<div class="title"><h5 >合同明细</h5>--%>
								<%--<button id="addPactDetailButton" class='btn list-add color_theme subBtn' onclick='mfCusCreditContractDetail.addPactDetail();' title='新增'>--%>
										<%--<i class='i i-jia3'></i>--%>
								<%--</button>--%>
							<%--</div>--%>
							<%--<div id="contractDetailList" class="table_content">--%>
								<%--<dhcc:tableTag paginate="mfCusCreditContractDetailList" property="tablePactDetail0001" head="true" />--%>
							<%--</div>--%>
						<%--</div> --%>
	   				<%--</dhcc:pmsTag>--%>
	   				<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
				<!-- 尽调报告模板 -->
				<!-- <div class="col-md-10 col-md-offset-1 column" >
					<div id="template_div">
						<div class="list-table margin_0">
							<div class="title">
								<span><i class="i i-xing blockDian"></i>文档模板</span>
							</div>
						</div>
						<div id="bizConfigs" class="template-config item-div padding_left_15"></div>
					</div>
				</div> -->
			</div>
			<div class="formRowCenter">
			 	<dhcc:thirdButton value="保存" action="保存" onclick="mfCusCreditContractDetail.ajaxUpdate('#operform')"></dhcc:thirdButton>
			 	<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="mfCusCreditContractDetail.close();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>