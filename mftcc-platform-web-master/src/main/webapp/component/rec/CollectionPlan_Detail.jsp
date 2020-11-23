<%-- [{"width":"799px","height":"658px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"517px","height":"153px","left":"807.2666625976562px","top":"0px","name":"业务状态","cellid":"cell_2"},{"width":"517px","height":"194px","left":"807.2666625976562px","top":"161.4499969482422px","name":"押品性质","cellid":"cell_3"},{"width":"517px","height":"295px","left":"807.2666625976562px","top":"363.26666259765625px","name":"历史信息","cellid":"cell_4"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"业务状态","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"押品性质","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_4":{"cellid":"cell_4","cellname":"历史信息","celltype":"","cellsts":"","plugintype":"","chart":{}}} --%>
<%@page import="app.component.sys.entity.SysOrg"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<%
	String cellDatas = (String) request.getAttribute("cellDatas");
	System.out.println("cellDatas:"+cellDatas);
	String blockDatas = (String) request.getAttribute("blockDatas");
	System.out.println("blockDatas:"+blockDatas);
	//String authFlag = (String) request.getAttribute("authFlag");
	String authFormHtml = "";
	/* if(authFlag.equals("1")){
		authFormHtml = (String) request.getAttribute("authFormHtml");
	}else{
	} */
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/component/rec/css/CollectionPlan_InsertDetail.css" />
		<%-- <script type="text/javascript" src="${webPath}/themes/factor/js/viewpointggl.js"></script> --%>
		<script type="text/javascript">
			var menuBtn=[];
			//var isApprove = false;
			var wkfFlag;//表示工作流是否还有节点；0结束，1还有节点
			var wkfAppId = "${wkfAppId}";
			var cusType = '${mfCusCustomer.cusType}';
			var cusBaseFlag = '${cusBaseFlag}';//表示客户基本信息是否已经录入
			var cusFullFlag = '${cusFullFlag}';//客户信息所有表单信息是否已经全部录入
			var scNo = '${scNo}';//客户要件场景
			var authFlag = '${authFlag}';
			var headImg = '${headImg}';
			var ifUploadHead = '${ifUploadHead}';
			var	cusNo = '${cusNo}';
			var collectionPlanNo = '${collectionPlanNo}';
			var appId = '${mfBusApply.appId}';
			//dataMap是业务参数，包括客户表单信息和参与业务信息等。
			var dataMap = <%=request.getAttribute("dataMap")%>;
			var collectionTableList = dataMap.collectionTableList;
			//alert(collateralTableList);
			var relNo = "cusNo-"+cusNo;
			var cusClassify= '${mfCusClassify.classifyType}';//客户类别，黑名单或者优质客户
			var rankTypeName = '${mfCusClassify.rankTypeName}';
			var webPath = '${webPath}';
			var authFormHtml = '<%=authFormHtml%>';
			var query = '${query}';
			var docParm = "cusNo="+cusNo+"&relNo="+cusNo+"&scNo="+scNo;//查询文档信息的url的参数
			var moreApplyList = eval("(" + '${json}' + ")").mfBusApplyList;
			//alert(333);
			
		</script>
		
		<style type="text/css">
		.head-title-self {
			font-size: 15px;
			padding: 10px 0px;
			text-overflow: ellipsis;
			width: 50%;
			white-space: nowrap;
			overflow: hidden;
			display: inline-block;
		}
		</style>
	</head>
	
<body style="overflow-x:hidden;">
	<div class="container">
		<div class="row clearfix">
			<div class="col-xs-12" style="padding-top: 15px; padding-right: 15px; padding-left:15px;">
				<div class="bg-white block-left-div" >	
					<!--头部主要信息 -->
					<div class="row clearfix head-info">
						<div class="col-xs-3 column text-center head-img">
							<div>
								<button type="button" class="btn btn-font-pledge padding_left_15">
									<i class="i i-pledge font-icon"></i>
									<div class="font-text-left">催收方案信息</div>
								</button>
							</div>
						</div>
						<div class="col-xs-9 column head-content">
							<!--<div class="margin_bottom_10">
								<p class="head-title-self">方案名称：${collectionPlan.collectionPlanName}</p>
								<p class="head-title-self">方案说明：${collectionPlan.collectionPlanExplain}</p>
								<p class="head-title-self">已引用方案产品名称：</p>
							</div>-->
							<table style="border-collapse:separate; border-spacing:0px 10px;">
							<tr>
								<td>方案名称:${collectionPlan.collectionPlanName}</td>
								<td>已引用方案产品名称:</td>
							</tr>
							<tr>
								<td>方案说明:${collectionPlan.collectionPlanExplain}</td>
							</tr>
							</table>
							<!--
							<div class="margin_bottom_5">
								<s:if test="%{collectionPlan.collectionPlanName.length()>8}">  
									<span class="head-title-self" title="${collectionPlan.collectionPlanName}"><s:property value="collectionPlan.collectionPlanName.substring(0,8)"/>... </span>
								</s:if>  
							 	<s:else>  
									<span class="head-title-self">${collectionPlan.collectionPlanName}</span>
							  	</s:else>
							</div>
							<div>
								<table>
									<tr>
										<td>
											<p class="head-title-self">方案说明</p>
											<p><span id='collectionPlanExplain' class="head-title-self">${collectionPlan.collectionPlanExplain}</span></p>
										</td>
										<td>
										</td>
									</tr>
								</table>
							</div>
							-->
						</div>
					</div>
					
					<!--催收其他信息-->
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="block-add" style="display: none;">
							</div>
						</div>
					</div>
					<div class="row clearfix">
						<div class="col-xs-12 column" >
							<div id="rotate-body"></div>
						</div>
					</div> 
					
				</div>
			</div>
		</div>
	</div>
</body>

</html>