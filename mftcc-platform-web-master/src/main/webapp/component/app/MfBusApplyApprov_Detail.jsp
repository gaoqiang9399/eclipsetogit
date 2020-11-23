<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="app.component.common.BizPubParm" %>
<%@ include file="/component/include/common.jsp"%>

<%
	String investigateScNo = BizPubParm.SCENCE_TYPE_DOC_INVESTIGATION;
%>


<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/component/app/css/MfBusApply_Detail.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/zTree/zTreeStyle/zTreeStyle.css" />
<%-- 		<link rel="stylesheet" href="${webPath}/component/wkf/css/wkf_approval.css" /> --%>
		<script type='text/javascript' src='${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js'></script>
<%-- 		<script type="text/javascript" src="${webPath}/component/include/wkfApproveIdea.js"></script> --%>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js"></script>

		<script type="text/javascript">	
		var cusNo,appId,pactId,fincId,pleId;
		var cusType = '${mfCusCustomer.cusType}';
		var cusBaseFlag = '${cusBaseFlag}';//表示客户基本信息是否已经录入
		var cusFullFlag = '${cusFullFlag}';//客户信息所有表单信息是否已经全部录入
		var scNo = '${scNo}';//客户要件场景
		var authFlag = '${authFlag}';
		var headImg = '${headImg}';
		var ifUploadHead = '${ifUploadHead}';
		var appId = '${mfBusApply.appId}';
		var wkfAppId,cusNo,appSts,coreCusNo,wareHouseCusNo;
		var investigateScNo ='<%=investigateScNo%>';
		var headImg = '${headImg}';
		var ifUploadHead = '${ifUploadHead}';
		var busModel = '${busModel}';
		var cusNoTmp = "";
		var riskLevel = '${riskLevel}';
		var modelNo = '${mfBusApply.pactModelNo}';
		var hisTaskList = eval("(" + '${json}' + ")").hisTaskList;
		var webPath = '${webPath}';
		$(function(){
			$(".container").mCustomScrollbar({
				advanced:{
					updateOnContentResize:true
				}
			});
		});
</script>
</head>
<body class="overflowHidden">
	<div class="container" style="height: 100%">
		<div class="row clearfix">	
			<!--申请主信息 -->
			<div class="col-xs-12 column block-left">
				<div class="bg-white block-left-div">
					<!--头部主要信息 -->
					<div class="row clearfix head-info">
						<!--头像 -->
						<div class="col-xs-3 column text-center head-img">
							<div style="position:relative;">
								<button type="button" class="btn btn-font-app padding_left_15" onclick="getBusInfo('${mfBusApply.appId}');">
									<i class="i i-applyinfo font-icon"></i>
								</button>
								<div class="font-text-left"style="left:40%;">申请信息</div>
							</div>
						 	<c:if  test="${fn:length(mfBusApply.kindName)>8}">  
						    	<div class="btn btn-link"  title="${mfBusApply.kindName}">${fn:substring(mfBusApply.kindName, 0, 8)}... </div> 
						  	</c:if>  
						 	<c:if  test="${fn:length(mfBusApply.kindName)=<8}">  
								<div class="btn btn-link" >${mfBusApply.kindName}</div>
						  	</c:if>   
						</div>
						<!--概要信息 -->
						<div class="col-xs-7 column head-content">
							<div class="margin_bottom_5">
								<div  class="head-title">
									${mfBusApply.appName}
								</div>
							</div>
							<!--信息查看入口 -->
							<div class="margin_bottom_10">
								<button class="btn risklevel${riskLevel} btn-view" type="button">
									<i class="i i-risk"></i><span>${riskName}</span>
								</button>
							</div>
							<div>
								<table>
									<tr>
										<td>
											<p class="cont-title">申请金额</p>
											<p><span class="content-span">${mfBusApply.fincAmt}</span><span class="unit-span margin_right_25">万</span> </p>
										</td>
										<td>
											<p class="cont-title">申请期限</p>
											<p><span class="content-span"></i>${mfBusApply.term}</span><span class="unit-span margin_right_25"><c:if  test='${mfBusApply.termType=="1"}' >个月</c:if><s:else>天</s:else></span></p>
										</td>
										<td>
											<p class="cont-title">年利率</p>
											<p><span class="content-span"><s:property value="${formatDouble(mfBusApply.fincRate)}" /></span><span class="unit-span">%</span></p>
										</td>
									</tr>
								</table>
							</div>
						</div>
						<div class="col-xs-2 column btn-special">
						</div>
					</div>
					
					<!--申请表单信息 -->
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="form-table base-info">
								<c:if  test='${cusBaseFlag!="0"}'>
									<div class="content">
										<form method="post" theme="simple" name="operform" action=webPath+"/mfBusApply/updateAjaxByOne">
											<dhcc:propertySeeTag property="formapply0006" mode="query"/>
										</form>
									</div>
								</c:if>
							</div>
						</div>
					</div>
					
					<div class="row clearfix">
						<div class="block-new-block"></div>
					</div>
					
					<div class="row clearfix" >
						<div class="form-table">
						  	<div class="title">
								 <span><i class="i i-xing blockDian"></i>审批历史</span>
								 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
						   	</div>
							<div class="content margin_left_15 collapse in " id="spInfo-div">
<%-- 								<iframe src='${webPath}/tech/wkf/processDetail.jsp?appNo=${mfBusApply.appId}&appWorkflowNo=${mfBusApply.applyProcessId}'  --%>
<!-- 									marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" id = "processDetailIframe"> -->
<!-- 								</iframe> -->
<!-- 								<ul id="wfTree" class="ztree"> -->
<!-- 			 					</ul> -->
									<div class="approval-process">
										<div id="modeler1" class="modeler">
											<ul id="wj-modeler2" class="wj-modeler" isApp = "false">
											</ul>
										</div>
									</div>
							</div>
						</div>
					</div>					
				</div>
			</div>
		</div>
	</div>	
</body>
<script type="text/javascript" src="${webPath}/component/app/js/MfBusApplyApprov_Detail.js"></script>
</html>