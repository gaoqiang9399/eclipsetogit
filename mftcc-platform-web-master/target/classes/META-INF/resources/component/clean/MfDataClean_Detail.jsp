<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<link rel="stylesheet" href="${webPath}/component/clean/css/MfDataClean_Detail.css?v=v0419" />
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container"> 
		<div id="successDiv" class="bg-white text-center" style="height:100%;padding-top:14%;display:none;">
			<i class="i i-duihao2" style="font-size:45px;color:#32b5cb;"></i>
			<span style="font-size:30px;">清理完成，本次清理客户1个，项目<span id="cleanBusCnt">1</span>个</span>
			<button class="btn btn-primary" onclick="myclose_click();" style="padding: 3px 30px;border-radius: 20px;font-size: 18px;margin-top: -10px;margin-left: 10px;">完成</button>
		</div>
		<div id="cleanContent" class="scroll-content">
			<div class="bg-white clean-div">
				<!--头部信息 -->
				<div class="row clearfix head-div">
					<!--客户信息 -->
					<div class="col-md-2 column text-center head-img">
						<img id="headImgShow" name="headImgShow" class="img-circle"/>
					</div>
					<div class="col-md-6 column margin_top_10">
						<div class="row clearfix ">
							<div class="col-md-12 column">
								<div class="font_size_16">${dataMap.mfCusCustomer.cusName}</div>
							</div>
						</div>
						<div class="row clearfix margin_top_20">
							<div class="col-md-12 column">
								<p>
									<span><i class="i i-ren1"></i>${dataMap.mfCusCustomer.contactsName}</span>
									<span class="margin_left_20"><i class="i i-dianhua"></i>${dataMap.mfCusCustomer.contactsTel}</span>
									<span class="margin_left_20"><i class="i i-idcard2"></i>${dataMap.mfCusCustomer.idNum}</span>
								</p>
								<p>
									<span><i class="i i-location "></i>${dataMap.mfCusCustomer.commAddress}</span>
									<span class="margin_left_20"><i class="i i-youjian1"></i>${dataMap.mfCusCustomer.postalCode}</span>
								</p>
							</div>
						</div>
					</div>
					<!--数据汇总 -->
					<div class="col-md-2 column data-sum">
						<div>
							<p>此次清理包括：</p>
							<p><span id="cusItems" class="sum-count">0</span>个客户,<span id="checkedItems" class="sum-count">0</span>笔项目</p>
							<span id="allItems" class="hide">${dataMap.listCnt}</span>
						
						</div>
					</div>
					<div class="col-md-2 column text-center margin_top_60">
						<span class="checkbox-span" id="cusCheck"><i class="i i-gouxuan"></i></span>
					</div>
				</div>
				<!--客户业务列表 -->
				<div class="row clearfix">
					<div class="bus-list font_size_14"> 
						<c:forEach items="${dataMap.mfDataCleanList}" varStatus="st" var="mfDataClean">
							<c:choose>
	   							<c:when test="${!empty st}">  
									<div id="item${mfDataClean.appId}" class="list-div row clearfix even-div">
								</c:when>
	   							<c:otherwise>
									<div id="item${mfDataClean.appId}" class="list-div row clearfix">
								</c:otherwise>
							</c:choose>
							<div class="col-md-10 column">
								<div class="info-title">${mfDataClean.appName}</div>
								<div class="info-cont">
									<div>
										<div class="item-div2">
											<p class="item-title">合同编号</p>
											<p class="item-value">${mfDataClean.pactNo}</p>
										</div>
										<div class="item-div">
											<p class="item-title">产品种类</p>
											<p class="item-value">${mfDataClean.kindName}</p>
										</div>
										<div class="item-div">
											<p class="item-title">金额</p>
											<p class="item-value">${mfDataClean.pactAmt}元</p>
										</div>
										<div class="item-div">
											<p class="item-title">余额</p>
											<p class="item-value">${mfDataClean.pactBal}元</p>
										</div>
										<div class="item-div">
											<p class="item-title">期限</p>
											<p class="item-value">${mfDataClean.termShow}</p>
										</div>
										<div class="item-div">
											<p class="item-title">利率</p>
											<p class="item-value">${mfDataClean.fincRate}</p>
										</div>
										<div class="item-div">
											<p class="item-title">办理阶段</p>
											<p class="item-value">${mfDataClean.busStage}</p>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-2 column margin_top_45 text-center">
								<c:choose>
									<c:when test='#mfDataClean.pactSts=="8"'>
										<div class="fengdang"></div>
									</c:when>
									<c:otherwise>
										<span class="checkbox-span" id="busCheck" wkfappid ="${mfDataClean.wkfAppId}" appid="${mfDataClean.appId}" pactid="${mfDataClean.pactId}" pactno="${mfDataClean.pactNo}"><i class="i i-gouxuan"></i></span>
									</c:otherwise>
								</c:choose>
							</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<div class="formRowCenter" >
   			 <dhcc:thirdButton value="清理" action="清理"  onclick="DataClean.dataClean();"></dhcc:thirdButton>
			 <dhcc:thirdButton value="取消" action="取消"  typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   	</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/clean/js/MfDataClean_Detail.js?v=v0419"></script>
<script type="text/javascript">
var appId = '${appId}';
var cusNo = '${cusNo}';
var headImg = '${dataMap.mfCusCustomer.headImg}';
var ifUploadHead = '${dataMap.mfCusCustomer.ifUploadHead}';
var webPath = '${webPath}';
	$(function() {
		DataClean.init();
	});
</script>
</html>
