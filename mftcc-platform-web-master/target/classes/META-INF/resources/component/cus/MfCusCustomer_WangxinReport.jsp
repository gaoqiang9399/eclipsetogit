<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>认证报告</title>
		<link rel="stylesheet" href="${webPath}/component/cus/css/MfCusCustomer_wangxinReport.css" /> 
		<style>
			.content .font-smallup{
				margin-top:10px;
				width:100%;
			}
			.content .fieldShow{
				max-width:100%;
			}
		</style>
		<script type="text/javascript">
			var cusNo = "${cusNo}";
		</script>
	</head>
	<body class="overflowHidden bg-white" style="display:none">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-12 column">
					<div class="report_content">
						<div class="report_content_box">
							<div class="report_items" id="basicInfo">
						  		<div class="item_title"><i class="i i-xing blockDian"></i>授信总额</div>
						  		<div class="basicInfo_content">                                                 
			                         <div class="basicInfo_content_title"><em style="color:#000000">授信额度：</em><span id="basicInfo_amt"></span></div>
			                         <div class="basicInfo_content_title"><em style="color:#000000">授信期限：</em><span id="basicInfo_term"></span></div>
			                         <div class="basicInfo_content_title"><em style="color:#000000">授信费率：</em><span id="basicInfo_rate"></span></div>
			                    </div>
							</div>
						  	<div class="report_items" id="mfPhoneBook">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>客户基本信息</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#personInfo" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="personInfo">
						  			<table id="" class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<tbody id="personInfoContent">
										
										</tbody>
									</table>
									<%-- <dhcc:propertySeeTag property="formcusPersonInfoReport0001" mode="query"/> --%>
								</div>
								<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>紧急联系人</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#familyInfo" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="familyInfo">
									<dhcc:tableTag property="tablecusfam00003" paginate="familyInfoList" head="true"></dhcc:tableTag>
								</div>
								<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>资产信息</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#mfCusPersonAssetsInfoList" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="mfCusPersonAssetsInfoList">
						  			
								</div>
								<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>运营商</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#operatorInfo" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="operatorInfo">
						  			<table class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<tbody id="operatorInfoContent">
											
										</tbody>
									</table>
									
								</div>
								<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>公积金</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#reservedFundInfo" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="reservedFundInfo">
						  			<table class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<tbody id="reservedFundInfoContent">
											
										</tbody>
									</table>
								</div>
								<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>芝麻分</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#sesamePointsInfo" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="sesamePointsInfo">
						  			<table class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<tbody id="sesamePointsInfoContent">
											
										</tbody>
									</table>
								</div>
								<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>银联交易变量及评分</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#unionpayTransaction" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="unionpayTransaction">
						  			<table class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<tbody id="unionpayTransactionContent">
											
										</tbody>
									</table>
								</div>
								<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>个人失信黑名单</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#getPersonDishonesty" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="getPersonDishonesty">
						  			<table class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<tbody id="getPersonDishonestyContent">
											
										</tbody>
									</table>
								</div>
								<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>个人黑名单</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#getPersonalBlacklist" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="getPersonalBlacklist">
						  			<table class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<tbody id="getPersonalBlacklistContent">
										</tbody>
									</table>
								</div>
								<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>风云决</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#fengyunjue" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="fengyunjue">
						  			<table class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<tbody id="fengyunjueContent">
											
										</tbody>
									</table>
								</div>
							</div>
						<div>
					</div>
				</div>	
			</div>
   		</div>
   		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusCustomer_WangxinReport.js"></script>
	</body>
</html>
