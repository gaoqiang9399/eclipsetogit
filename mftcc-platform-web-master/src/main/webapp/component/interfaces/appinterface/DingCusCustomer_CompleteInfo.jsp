<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/interfaces/appinterface/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<script type="text/javascript" src="/factor/component/interfaces/appinterface/js/MfAppBusApply.js"></script>
	<link rel="stylesheet" href="UIplug/iconmoon/style.css" >
		<title>完善资料</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	  	<style>
	  		html{
		  		background-color:#efeff4;
		  	}
		  	.weui_cell{
		  		padding:14px 15px;
		  	}
			.weui_cells:before{
				border-top-width: 0px;
			}
	  	</style>
	</head>
	<body>
		<!-- 个人 -->
		<c:if test="${cusBaseType eq 2}">
			<!-- <div id="addCareer" class="weui_cells weui_cells_access">
			    <a class="weui_cell" href="javascript:;">
			        <div class="weui_cell_bd weui_cell_primary">
			            <p>登记职业信息</p>
			        </div>
			        <div class="weui_cell_ft">
			        </div>
			    </a>
			</div>  -->
			<div id="addSocialRelation" class="weui_cells weui_cells_access">
			    <a class="weui_cell" href="javascript:;">
			        <div class="weui_cell_bd weui_cell_primary">
			            <p>登记社会关系</p>
			        </div>
			        <div class="weui_cell_ft">
			        </div>
			    </a>
			</div> 
			<div id="addAsset" class="weui_cells weui_cells_access" >
			    <a class="weui_cell" href="javascript:;">
			        <div class="weui_cell_bd weui_cell_primary" >
			            <p id="addcus_p">登记资产信息</p>
			        </div>
			        <div class="weui_cell_ft">
			        </div>
			    </a>
			</div>
			<div id="addBankAccManage" class="weui_cells weui_cells_access" >
			    <a class="weui_cell" href="javascript:;">
			        <div class="weui_cell_bd weui_cell_primary" >
			            <p id="addcus_p">登记账户管理</p>
			        </div>
			        <div class="weui_cell_ft">
			        </div>
			    </a>
			</div>
		</c:if>
		<!-- 企业 -->
		<c:if test="${cusBaseType eq 1}">
		<div id="addShareholder" class="weui_cells weui_cells_access">
			    <a class="weui_cell" href="javascript:;">
			        <div class="weui_cell_bd weui_cell_primary">
			            <p>登记股东信息</p>
			        </div>
			        <div class="weui_cell_ft">
			        </div>
			    </a>
			</div> 
			<div id="addHighManager" class="weui_cells weui_cells_access">
			    <a class="weui_cell" href="javascript:;">
			        <div class="weui_cell_bd weui_cell_primary">
			            <p>登记高管信息</p>
			        </div>
			        <div class="weui_cell_ft">
			        </div>
			    </a>
			</div> 
			<div id="addEquityInfo" class="weui_cells weui_cells_access" >
			    <a class="weui_cell" href="javascript:;">
			        <div class="weui_cell_bd weui_cell_primary" >
			            <p id="addcus_p">登记对外投资信息</p>
			        </div>
			        <div class="weui_cell_ft">
			        </div>
			    </a>
			</div>
			<div id="addBankAccManage" class="weui_cells weui_cells_access" >
			    <a class="weui_cell" href="javascript:;">
			        <div class="weui_cell_bd weui_cell_primary" >
			            <p id="addcus_p">登记账户管理</p>
			        </div>
			        <div class="weui_cell_ft">
			        </div>
			    </a>
			</div>
		</c:if>
			
	</body>
	<script type="text/javascript">
		var cusNo = "${cusNo}";
		var appId = "${appId}";
		$(function(){
			//个人
			$("#addCareer").click(function(){
				location.href="${webPath}/dingCusPerson/inputJob?cusNo="+cusNo+"&appId="+appId;
			});
			$("#addSocialRelation").click(function(){
				location.href="${webPath}/dingCusPerson/inputFamily?cusNo="+cusNo+"&appId="+appId;
			});
			$("#addAsset").click(function(){
				location.href="${webPath}/dingCusPerson/inputAssets?cusNo="+cusNo+"&appId="+appId;
			});
			//企业
			$("#addShareholder").click(function(){
				location.href="${webPath}/dingCusBusiness/inputShareholder?cusNo="+cusNo+"&appId="+appId;
			});
			$("#addHighManager").click(function(){
				location.href="${webPath}/dingCusBusiness/inputHighManager?cusNo="+cusNo+"&appId="+appId;
			});
			$("#addEquityInfo").click(function(){
				location.href="${webPath}/dingCusBusiness/inputEquityInfo?cusNo="+cusNo+"&appId="+appId;
			});
			$("#addBankAccManage").click(function(){
				location.href="${webPath}/dingCusBusiness/inputBankAccManage?cusNo="+cusNo+"&appId="+appId;
			});
			
		});
	</script>
</html>
