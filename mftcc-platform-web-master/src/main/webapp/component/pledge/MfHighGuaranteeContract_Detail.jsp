<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="app.component.common.BizPubParm" %>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/component/collateral/css/MfBusCollateral_Detail.css" />
		<link rel="stylesheet" href="${webPath}/component/collateral/css/MfCollateralClass_InsertDetail.css" />
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<style type="text/css">
			.cover {
				cursor: default;
			}
			.list-table .list-add{
				color: #32b5cb
			}
			.infoTilte{
				margin-left: 14px;
				margin-bottom: 20px;
				margin-top:0px;
			}
			.form-margin{
				margin-top: 20px;
    			margin-left: 4px;
			}
			.set-disabled{
				color:#aaa;
			}
			.button-bac32B5CB{
				background-color:#32B5CB
			}
			.button-bac32B5CB:hover{
			    color: #fff;
			    background-color: #018FA7;
			}
			.button-bacFCB865{
				background-color:#FCB865
			}
			.button-bacFCB865:hover{
				background-color : #E9AA64;
				color : #fff;
			}
			.button-bacE14A47{
				background-color:#E14A47
			}
			.button-bacE14A47:hover{
				color : #fff;
				background-color:#C9302C
			}
			.button-his{
				margin-top: 20px;
			}
			.info-collect{
				margin-top: -30px;
			}
			#highGrtSubListInfo table{
				font-size: 14px;
			}
			#highGrtSubListInfo table td{
				font-size: 12px;
				vertical-align: middle;
			}
			#highGrtSubListInfo table tr{
				height: 30px;
			}
			.list-table-sub {
				margin-top: 27px;
			}
			.list-table-sub .content{
				padding-left: 15px;
				padding-top: 0px;
			}
			.list-table-sub .title,.form-table .title{
				height: 37px;
				background-color: #f8f9fc;
			}
			.list-table-sub .title span,.form-table .title span {
				margin-left: 15px;
				height: 37px;
				line-height: 37px;
				font-size:16px;
				font-weight:bold;
			}
			.list-table-sub .ls_list thead tr {
				height: 47px;
				line-height: 47px;
				color: #000000;
			}

			.list-table-sub .ls_list thead tr th {
				font-size: 14px;
				font-weight: normal;
				color: #000000;
				padding: 2px 0px 2px 0px;
				border: 1px solid #e9ebf2;
			}
			.list-table-sub .ls_list tbody tr {
				height: 40px ;
				line-height: 40px;
				font-size: 14px;
				color: #7f7f7f;
				background: #ffffff;
				border-bottom: 1px solid #f3f3f3;
			}

			.list-table-sub .ls_list tbody tr:hover {
				background: #E0F4F7;
			}

			.list-table-sub .ls_list tbody tr td {
				border-top: none ;
				padding: 0px 0px ;
				line-height: 40px;
				vertical-align: top;
				color: #7f7f7f;
			}
			.list-table-sub .ls_list tbody tr td input{
				height:45px;
			}
			.list-table-sub .ls_list tbody tr td {
				border-top: none !important;
				padding: 0px 0px !important;
				line-height: 40px;
				vertical-align: top;
				color: #7f7f7f;
			}
		</style>
	<script type="text/javascript">
        var query='${query}';
        var ifBizManger='${ifBizManger}';
	</script>
</head>
<body class="overflowHidden">
    <div class="container" id="container">
		<div class="row clearfix">	
			<!--申请主信息 -->
			<div class="col-md-12 column block-left" style="width:90%;">
				<div class="bg-white block-left-div">
					<!--头部主要信息 -->
					<div class="row clearfix head-info">
						<div class="col-xs-3 column text-center head-img">
							<div>
								<button type="button" class="btn btn-font-pledge font-pledge-div padding_left_15">
									<i class="i i-pledge font-icon"></i>
									<div class="font-text-left">最高额合同信息</div>
								</button>
							</div>
							<div>${mfHighGuaranteeContract.typeName}</div>
						</div>
						<div class="col-xs-9 column head-content">
							<div class="row clearfix">
								<!--信息查看入口 -->
								<div class="col-xs-10 column button-his">
									<div class="margin_bottom_10">
									</div>
								</div>
								<div class="col-xs-2 column">
									<div class="i i-warehouse cus-type-font">
										<div class="type-name-div">${mfHighGuaranteeContract.contractStsName}</div>
									</div>
								</div>
							</div>
							<div class="row clearfix">
								<div class="col-xs-12 column info-collect">
									<table>
										<tr>
											<td>
												<p class="cont-title">担保数量</p>
												<p><span id='colCnt' class="content-span"></span></p>
											</td>
											<td>
												<p class="cont-title">合同额度</p>
												<p><span id='amt' class="content-span"></span><span class="unit-span margin_right_25">万</span></p>
											</td>
										</tr>
										<tr>
											<td>
												<p class="cont-title">担保总额</p>
												<p><span id='colAmt' class="content-span"></span><span class="unit-span margin_right_25">万</span></p>
											</td>
											<td>
												<p class="cont-title">合同余额</p>
												<p><span id='bal' class="content-span"></span><span class="unit-span margin_right_25">万</span></p>
											</td>
										</tr>
									</table>
							</div>
							</div>
						</div>
					</div>
					<!--合同信息 -->
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="form-table base-info">
								<div class="title">
									<span><i class="i i-xing blockDian"></i>最高额担保合同详情</span>
									<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#highGrtDetailInfo">
										<i class="i i-close-up"></i><i class="i i-open-down"></i>
									</button>
								</div>
								<div class="content collapse in" id="highGrtDetailInfo" name="highGrtDetailInfo">
									<form method="post" theme="simple" id="highGrtDetailForm" name="operform" action="${webPath}/mfHighGuaranteeContract/updateAjaxByOne">
										<dhcc:propertySeeTag property="formhighGrtContractDetail" mode="query" ifBizManger="ifBizManger"/>
									</form>
								</div>
							</div>
						</div>
					</div>
					<!--明细信息-->
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="list-table-sub">
								<div class="title">
									<span><i class="i i-xing blockDian"></i>担保明细</span>
									<button class="btn btn-link formAdd-btn hidden" id="addSub" onclick="MfHighGuaranteeContract.insertSub('${mfHighGuaranteeContract.highGrtContractId}');" title="新增" >新增</button>
									<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#highGrtSubListInfo">
										<i class="i i-close-up"></i><i class="i i-open-down"></i>
									</button>
								</div>
								<div class="content collapse in" id="highGrtSubListInfo" name="highGrtSubListInfo" style="width:98%;">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/pledge/js/MfHighGuaranteeContract.js"></script>
<script type="text/javascript">
		</script>
		<script type="text/javascript">		
		var relNo = '${mfHighGuaranteeContract.highGrtContractId}';
		var highGrtContractId = '${mfHighGuaranteeContract.highGrtContractId}';
		var contractSts = '${mfHighGuaranteeContract.contractSts}';
		var operable="";
		$(function(){
            myCustomScrollbarForForm({
                obj:"#container",
                advanced : {
                    updateOnContentResize : true
                }
            });

			MfHighGuaranteeContract.showHighGrtContractSubList(highGrtContractId);
			if(contractSts=='1'){
                $("#addSub").removeClass("hidden");
			}
		});

		function oneCallback(data){
		    //单字段编辑后，更新担保合同信息
		    MfHighGuaranteeContract.refreshHeadMsg(highGrtContractId);
		}
		</script>
</html>