<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
	<head>
		<title>认证报告</title>
		<link rel="stylesheet" href="${webPath}/component/cus/css/MfCusCustomer_certificationReport.css" /> 
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusCustomer_certificationReport.js"></script>
		<script type="text/javascript">
			var cusNo = "${cusNo}";
			var classifyType ="${mfCusCustomer.classifyType }"; 
			var appSts ="${appSts}" || "1";
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-12 column">
					<div class="report_content">
						<div class="report_content_box">
						  	<div class="report_items" id="basicInfo">
						  		<div class="item_title"><i class="i i-xing blockDian"></i>基本信息</div>
						  		<div class="basicInfo_content">                                                 
			                         <div class="basicInfo_content_title"><em>用户姓名：</em><span>${mfCusCustomer.cusName}</span></div>
			                         <div class="basicInfo_content_title"><em>身份证号：</em><span>${mfCusCustomer.idNum}</span></div>
			                         <div class="basicInfo_content_title"><em>手机号码：</em><span>${mfCusCustomer.cusTel}</span></div>
			                         <div class="basicInfo_content_title"><em>客户类型：</em>
				                        <c:if test="${mfCusCustomer.classifyType== '1'}"> 
				                        	<span style="color:#FF0000">黑名单客户</span>
				                        </c:if>
	            						<c:if test="${mfCusCustomer.classifyType== '2'}">
	            						 	<span style="color:#008000">优质客户</span>
	            						</c:if>
	            						<c:if test="${mfCusCustomer.classifyType== '3'}">
	            						 	<span>普通客户</span>
	            						</c:if>
			                         </div>
			                         <div class="basicInfo_content_title"><em>芝麻评分：</em>
			                         	<input type="text" name="" value="" class="report_input" id="zhimascore" onkeyup="value=value.replace(/[^\d.]/g,'');" <c:if test='${appSts == "4"}'>readOnly</c:if> />
			                         	<button class="report_button" onclick="queryThirdService.saveZhimaCreditScore();" >保存</button>
									</div>
			                    </div>
							</div>
							<!-- <div class="report_items" id="mfPhoneBook">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>芝麻分</span>
						  			 <button  class="btn btn-link pull-right" aria-expanded="true" data-toggle="collapse" data-target="#zhimafen">
										<i class='i i-close-up'></i>
										<i class='i i-open-down'></i>
									 </button>
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="zhimafen">
						  			芝麻分：<input type="text" name="" value="" id="zhimascore" onkeyup="value=value.replace(/[^\d.]/g,'');" onblur="queryThirdService.saveZhimaCreditScore();"/>
								</div>
							</div> -->
							<%-- <div class="report_items" id="mfPhoneBook">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>通讯录</span>
						  			 <button  class="btn btn-link pull-right" aria-expanded="true" data-toggle="collapse" data-target="#mfPhoneBookList">
										<i class='i i-close-up'></i>
										<i class='i i-open-down'></i>
									 </button>
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="mfPhoneBookList">
									<dhcc:tableTag property="tablecustomerCerReport0001" paginate="mfPhoneBookList" head="true"></dhcc:tableTag>
								</div>
							</div> --%>
							<div class="report_items" id="">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>通讯录统计</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#getBookDataList1" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
								<div class="list-table margin_left_15 content collapse in" id="getBookDataList1">
									<table id="getBookDataList" class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<thead>
											<tr>
												<th scope="col" name="baCnt" align="center" width="100px">爸</th>
												<th scope="col" name="maCnt" align="center"  width="100px">妈</th>
												<th scope="col" name="shuCnt" align="center"  width="100px">叔</th>
												<th scope="col" name="guCnt" align="center"  width="100px">姑</th>
												<th scope="col" name="jiuCnt" align="center"  width="100px">舅</th>
												<th scope="col" name="qiTaCnt" align="center"  width="100px">其他</th>
												<th scope="col" name="recordCnt" align="center">通话记录中出现人数</th>
												<th scope="col" name="zongCnt" align="center">通讯录总人数</th>
											</tr>
										</thead>
										<tbody id="getBookDataListContent">
											
										</tbody>
									</table>
								</div>
							</div>
							<div class="report_items" id="">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>通话记录</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#getTelecomDataList1" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
								<div class="list-table margin_left_15 content collapse in" id="getTelecomDataList1">
									<table id="getTelecomDataList" class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<thead>
											<tr>
												<th scope="col" name="num" align="center">序号</th>
												<th scope="col" name="contact_name" align="center">姓名</th>
												<th scope="col" name="phone_num" align="center">联系电话</th>
												<th scope="col" name="phone_num_loc" align="center">归属地</th>
												<th scope="col" name="call_cnt" align="center">通话次数</th>
												<th scope="col" name="call_len" align="center">总时长</th>
												<th scope="col" name="call_out_len" align="center">呼出时长</th>
												<th scope="col" name="call_in_len" align="center">呼入时长</th>
												
												<th scope="col" name="contact_1w" align="center"></th>
											</tr>
										</thead>
										<tbody id="getTelecomDataListContent">
											
										</tbody>
									</table>
								</div>
							</div>
							<!-- <div class="report_items" id="">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>紧急联系人分析</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#getTelecomDataList2" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
						  		
						  		<div class="list-table margin_left_15 content collapse in" id="getTelecomDataList2">
									<table id="getfamilyInfoList" class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<thead>
											<tr>
												<th scope="col" name="num" align="center">序号</th>
												<th scope="col" name="relName" align="center">联系人姓名</th>
												<th scope="col" name="relTel" align="center">联系人手机号</th>
												<th scope="col" name="voiceCount" align="center">近半年通话次数</th>
												<th scope="col" name="voiceTime" align="center">近半年通话时长（单位为分钟）</th>
											</tr>
										</thead>
										<tbody id="getfamilyInfoListContent">
											
										</tbody>
									</table>
								</div>
							</div> -->
							<div class="report_items">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>大圣数据_天行_运营商三要素</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#getMobileVerifyThree" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  			<input value="点击查询" type="button" class="btn btn-primary pull-right"  onclick="queryThirdService.getMobileVerifyThree();">
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="getMobileVerifyThree" aria-expanded="true">
						  			<table id="getMobileVerifyThreeList" class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<thead>
											<tr>
												<th scope="col" name="num" align="center">姓名</th>
												<th scope="col" name="prcpAmt" align="center">手机号</th>
												<th scope="col" name="normInt" align="center">身份证号</th>
												<th scope="col" name="normInt" align="center">匹配结果</th>
											</tr>
										</thead>
										<tbody id="getMobileVerifyThreeContent">
											
										</tbody>
									</table>
								</div>
							</div>
							<div class="report_items" id="mfPhoneBook">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>大圣数据-百融-多次申请规则评分</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#brRuleApplyLoan" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  			<input value="点击查询" type="button" class="btn btn-primary pull-right"  onclick="queryThirdService.getBrRuleApplyLoan();">
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="brRuleApplyLoan">
						  			<table id="brRuleApplyLoanlist" class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<thead>
											<tr>
												<th scope="col" name="num" align="center" >序号</th>
												<th scope="col" name="prcpAmt" align="center">命中规则项</th>
												<th scope="col" name="normInt" align="center">规则评分</th>
											</tr>
										</thead>
										<tbody id="brRuleApplyLoanContent">
											
										</tbody>
									</table>
								</div>
							</div>
							<div class="report_items" id="mfPhoneBook">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>大圣数据-百融-反欺诈特殊名单</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#getBrRuleSpecial" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  			<input value="点击查询" type="button" class="btn btn-primary pull-right"  onclick="queryThirdService.getBrRuleSpecial();">
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="getBrRuleSpecial">
									<table id="getBrRuleSpecialList" class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<thead>
											<tr>
												<th scope="col" name="num" align="center">序号</th>
												<th scope="col" name="prcpAmt" align="center">命中规则项</th>
												<th scope="col" name="normInt" align="center">规则评分</th>
											</tr>
										</thead>
										<tbody id="getBrRuleSpecialListContent">
											
										</tbody>
									</table>
								</div>
							</div>
							<div class="report_items" id="mfPhoneBook">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>索伦数据-贷后邦-反欺诈</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#getDhbGetSauronC" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  			<input value="点击查询" type="button" class="btn btn-primary pull-right"  onclick="queryThirdService.getDhbGetSauronC();">
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="getDhbGetSauronC">
						  			<table id="getDhbGetSauronCList" class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<tbody id="getDhbGetSauronCListContent">
											
										</tbody>
									</table>
								</div>
							</div>
							<div class="report_items" id="mfPhoneBook">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>大圣数据-圣数-火眼黑名单</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#getFireEyesBlack" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  			<input value="点击查询" type="button" class="btn btn-primary pull-right"  onclick="queryThirdService.getFireEyesBlack();">
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="getFireEyesBlack">
									<table id="getFireEyesBlackList" class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<thead>
											<tr>
												<th scope="col" name="num" align="center">序号</th>
												<th scope="col" name="prcpAmt" align="center">规则序号</th>
												<th scope="col" name="normInt" align="center">命中结果</th>
												<th scope="col" name="normInt" align="center">说明</th>
											</tr>
										</thead>
										<tbody id="getFireEyesBlackListContent">
											
										</tbody>
									</table>
								</div>
							</div>
						<div>
					</div>
				</div>	
			</div>
			<%-- <div class="formRowCenter">
	   			<dhcc:thirdButton value="确定" action="确定" onclick="myclose();"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div> --%>
   		</div>
	</body>
</html>
