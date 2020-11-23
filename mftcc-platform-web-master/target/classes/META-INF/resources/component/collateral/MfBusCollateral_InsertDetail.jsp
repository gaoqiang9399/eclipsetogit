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
		<link rel="stylesheet" href="${webPath}/component/collateral/css/MfCollateralClass_InsertDetail.css" />
		<%-- <script type="text/javascript" src="${webPath}/themes/factor/js/viewpointggl.js"></script> --%>
		<script type="text/javascript">
			var scNo = '${scNo}';//客户要件场景
			var	cusNo = '${cusNo}';
			var collateralNo = '${collateralNo}';
			var vouType = '${vouType}';
			var classId = '${classId}';
			var appId = '${mfBusApply.appId}';
			//dataMap是业务参数，包括客户表单信息和参与业务信息等。
			var dataMap = <%=request.getAttribute("dataMap")%>;
			/* var dataMap = '${dataMap}'; */
			console.log(dataMap);
			var collateralTableList = dataMap.collateralTableList;
			var relNo = "cusNo-"+cusNo;
			var webPath = '${webPath}';
			var query = '${query}';
			var docParm = "cusNo="+cusNo+"&relNo="+cusNo+"&scNo="+scNo;//查询文档信息的url的参数
		</script>
		<script type="text/javascript">
			function colseCollateral(){
				window.location.href = "../factor/component/collateral/action/mfBusCollateral/getListPage";
			}
		</script>
	</head>
	
<body style="overflow-x:hidden;">
	<div class="container">
		<div class="row clearfix">
			<div class="col-xs-12" style="padding-top: 15px; padding-right: 15px; padding-left:15px;">
				<div class="bg-white block-left-div" >	
					<!--客户其他信息-->
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="block-add" style="display: none;">
							</div>
						</div>
					</div>
					
					
					<div class="row clearfix">
						<div class="col-xs-12 column" >
 						<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
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
	<div class="formRowCenter">
   			<%-- <dhcc:thirdButton value="保存" action="保存" onclick="insertImpawnBase('#impawnBaseInsert');"></dhcc:thirdButton> --%>
   			<dhcc:thirdButton value="关闭" action="关闭" onclick="colseCollateral();"></dhcc:thirdButton>
   	</div>
</body>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/MfCollateralClass_InsertDetail.js"></script>
</html>