<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>征信结果</title>
		<style type="text/css">
			.footer_loader,.backToTop{
				bottom:50px;
			}
		</style>
		<script type="text/javascript" src="${webPath}/component/cus/creditquery/js/MfCreditQueryRecordInfo.js"></script>
	</head>
	<script type="text/javascript">
		var cusNo="${cusNo}";
		var appId="${appId}";
		var queryId="${queryId}";
		var busEntrance="${busEntrance}";
		var creditQueryFlag="${creditQueryFlag}";
		var cusBaseType="${mfCusCustomer.cusBaseType}";
		var ajaxData = JSON.parse('${ajaxData}');
		var aloneFlag = true;
		var dataDocParm={
			relNo:cusNo,
			docType:"collateralDoc",
			docTypeName:"征信资料",
			docSplitName:"征信资料",
			query:'query',
			docSplitNoArr:ajaxData.docSplitNoArr,
			appId:appId
		};
		var docParm="";
		$(function(){
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
			//征信资料块滚动条
			myCustomScrollbarForForm({
				obj:"#creditDocInfo",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
			MfCreditQueryRecordInfo.initCreditContent();
		});
	</script>
	<body class="overflowHidden bg-white">
		<div class="container block-left padding_right_10">
	   		<div class="scroll-content">
				<!-- 客户业务汇总信息 -->
		   		<div id="cusAppLyInfo" class="col-md-12 column">
			   		<div class="row clearfix head-info">
			   			<div class="col-md-6" style="padding:0px 80px;">
							<div class="row clearfix margin_bottom_15 font_size_18">
								${mfCusCustomer.cusName}
							</div>
							<div class="row clearfix">
								<p>
									<span><i class="i i-ren1 "></i>
										<span id = "contactsName">
											<c:if test="${mfCusCustomer.contactsName!=null&&mfCusCustomer.contactsName!=''}">
												${mfCusCustomer.contactsName}
											</c:if>
											<c:if test="${mfCusCustomer.contactsName==null||mfCusCustomer.contactsName==''}"><span class="unregistered">未登记</span></c:if>
										</span>
									</span>
									<span class="vertical-line"></span> 
									<span><i class="i i-dianhua "></i>
									<span id = "contactsTel">
										<c:if test="${mfCusCustomer.contactsTel!=null&&mfCusCustomer.contactsTel!=''}">
												${mfCusCustomer.contactsTel}
										</c:if>
										<c:if test="${mfCusCustomer.contactsTel==null||mfCusCustomer.contactsTel==''}"><span class="unregistered">未登记</span></c:if></span></span>
									<span class="vertical-line"></span> 
									<span><i class="i i-idcard2 " ></i>
										<span id="idNum">
											<c:if test="${mfCusCustomer.idNum!=null&&mfCusCustomer.idNum!=''}">
												${mfCusCustomer.idNum}
											</c:if>
											<c:if test="${mfCusCustomer.idNum==null||mfCusCustomer.idNum==''}"><span class="unregistered">未登记</span></c:if>
										</span>
									</span>
								</p>
								<p>
									<i class="i i-location "></i>
									<span id = "commAddress">
										<c:if test="${mfCusCustomer.commAddress!=null&&mfCusCustomer.commAddress!=''}">
											${mfCusCustomer.commAddress}
										</c:if>
										<c:if test="${mfCusCustomer.commAddress==null||mfCusCustomer.commAddress==''}">
											<span class="unregistered">未登记</span>
										</c:if>
									</span>
									<span class="vertical-line"></span>
									<i class="i i-youjian1 "></i>
									<span id = "postalCode">
										<c:if test="${mfCusCustomer.postalCode!=null&&mfCusCustomer.postalCode!=''}">
											${mfCusCustomer.postalCode}
										</c:if>
										<c:if test="${mfCusCustomer.postalCode==null||mfCusCustomer.postalCode==''}">
											<span class="unregistered">未登记</span>
										</c:if>
									</span>
								</p>
							</div>
						</div>
						<div class="col-md-6" style="padding:0px 80px;">
							<div class="row clearfix">
								<div class="col-xs-10 col-md-10 column">
									<button class="btn btn-link content-title">${mfBusApply.appName}</button>
								</div>
							</div>
							<div class="row clearfix margin_top_5" style="margin-left:8px">
								<div class="col-xs-10 col-md-10 column">
									<span class="content-span"><i class="i i-rmb"></i>${mfBusApply.fincAmt}</span><span class="unit-span">万</span>
									<span class="content-span"><i class="i i-richengshezhi"></i>${mfBusApply.term}</span><span class="unit-span">${dataMap.termUnit}</span>
									<span class="content-span"><i class="i i-tongji1"></i>${mfBusApply.fincRate}</span><span class="unit-span">${dataMap.rateUnit}</span>
								</div>
							</div>
						</div>
					</div>
					<!-- 业务征信查询历史 -->
					<div id="creditQueryHisList" class="row column clearfix">
						<div class="row clearfix bg-white tabCont">
							<div class="col-md-12 column">
								<div class="search-div">
									<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=查询客户名称/查询时间"/>
								</div>
							</div>
						</div>
						<div class="row clearfix">
							<div class="col-md-12 column">
								<div id="creditQueryHisContentList" class="table_content"  style="height: auto;">
									<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
								</div>
							</div>
						</div>
					</div>
		   		</div>
		   		<!-- 征信查询表单 -->
		   		<div id="creditQueryForm" class="col-md-12 column tabCont" style="display:none">
		   			<div class="col-md-10 col-md-offset-1 margin_top_20">
						<div class="bootstarpTag">
							<!-- <div class="form-title">征信查询记录表</div> -->
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form  method="post" id="MfCreditQueryRecordInfoForm" theme="simple" name="operform" action="${webPath}/mfCreditQueryRecordInfo/insertAjax">
								<dhcc:bootstarpTag property="formcreditquery0001" mode="query"/>
							</form>
						</div>
					</div>
		   			<!-- 客户征信资料 身份证、授权书 -->
					<div class="row clearfix">
						<div id="creditDocInfo" class="col-xs-10 col-md-offset-1 margin_top_20">
								<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
						</div>
					</div>
		   		</div>
		   		<!-- 征信结果 -->
		   		<div id="creditContent" class="col-md-8 col-md-offset-2 margin_top_20" style="display:none">
					<div class="bootstarpTag" id="creditContent">
						
					</div>
				</div>
	   		</div>
	   		<div id="showCreditQueryButton" class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="查询" action="查询" typeclass="cancel" onclick="MfCreditQueryRecordInfo.showCreditQueryForm()"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
	   		</div>
	   		<div id="bussButton" class="formRowCenter" style="display:none">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="查询" action="查询" typeclass="cancel" onclick="MfCreditQueryRecordInfo.creditQuery('#MfCreditQueryRecordInfoForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="MfCreditQueryRecordInfo.back();"></dhcc:thirdButton>
	   		</div>
	   		<div id="againButton" class="formRowCenter" style="display:none">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="重新查询" action="重新查询" typeclass="cancel" onclick="MfCreditQueryRecordInfo.creditQueryAgain()"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="MfCreditQueryRecordInfo.back();"></dhcc:thirdButton>
	   		</div>
	   		<%-- <div id="cusButton" class="formRowCenter">
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div> --%>
	   	</div>
	</body>
</html>