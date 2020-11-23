<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html>
	<head>
		<title>法律诉讼详情-查询</title>
		<script type="text/javascript" src="/component/lawsuit/js/MfLawsuitDetail.js"></script>
		<link rel="stylesheet" href="/component/lawsuit/css/MfLawsuit_Detail.css?v=${cssJsVersion}"></link>
		<script type="text/javascript">
			var pactId = '${mfLawsuit.pactId}';
			var caseId = '${mfLawsuit.caseId}';
			var scNo = '${scNo}';
			var query = '${query}';
			var docParm = "cusNo=''&relNo=" + caseId + "&scNo=" + scNo;//查询文档信息的url的参数
			var aloneFlag = true;
			var dataDocParm={
					relNo:"caseId",
					docType:"lawDoc",
					docTypeName:"案件资料",
					docSplitName:"法律诉讼相关文件",
					query:query,
			};
			$(function() {
				lawsuitDetail.init();
			});
		</script>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content" style="padding:0px 10px;">
			<div class="row clearfix head-info" style="margin-top:10px;">
				<div class="col-md-3 text-center head-img padding_top_20">
					<div style="position:relative;">
						<button type="button" class="btn btn-font-pact font-pact-div padding_left_15">
							<i class="i i-lawsuit font-icon"></i>
							<div class="font-text-left">案件信息</div>
						</button>
					</div>
				</div>
				<div class="col-md-6 head-content">
					<div class="bus head-title" title="${mfLawsuit.caseTitle}">${mfLawsuit.caseTitle}</div>
					<div class="margin_top_10">
						<table>
							<tr>
								<td>
									<p class="cont-title">诉讼类型</p>
									<p><span class="content-span font_size_18">${mfLawsuit.caseType}</span></p>
								</td>
								<td>
									<p class="cont-title">诉讼金额</p>
									<p><span class="content-span font_size_18">${mfLawsuit.caseAmt}</span><span class="unit-span margin_right_25">元</span> </p>
								</td>
								<td>
									<p class="cont-title">起诉时间</p>
									<p><span class="content-span font_size_18">${mfLawsuit.caseTime}</span></p>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="col-md-3">
					<div class="i i-warehouse seal-font">
						<div class="seal-name-div">${mfLawsuit.caseState}</div>
					</div>
				</div>
			</div>
			<!--案件详情 -->
			<div class="row clearfix">
				<div class="form-table base-info">
					<div class="title">
						<span><i class="i i-xing blockDian"></i>案件详情</span>
						<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#suitDetail"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
					</div>
					<div id="suitDetail" class="content collapse in">
						<form id="edit-form" theme="simple" name="operform" action="/mfLawsuit/updateAjax">
							<dhcc:propertySeeTag property="formlawsuit0001" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<!--案件资料 -->
			<div class="row clearfix">
				<div class="col-xs-12 column" >
					<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
				</div>
			</div>
			<!--案件跟进 -->
			<div class="row clearfix">
				<div class="list-table">
					<div class="title">
						<span><i class="i i-xing blockDian"></i>案件跟进</span>
						<!-- 新增的新增跟进功能的button按钮  以前没有 -->
						<button class="btn btn-link formAdd-btn" onclick="" title="新增跟进" ><i class="i i-jia3"></i></button>
						<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#suitTrack"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
					</div>
					<div id="suitTrack" class="content collapse in">
						<div id="follow-info">
							<c:if test="${fn:length(mfLawsuit.follows)==0}">
								<div class="message">暂无跟进信息</div>
							</c:if>
							<c:if test="${fn:length(mfLawsuit.follows) != 0}">
								<c:forEach items="${mfLawsuit.follows}" var="follow" varStatus="status">
									<div class="follow-item">
										<div class="margin_bottom_10">
											<input type="hidden" class="follow-type" value="${follow.followType}" /> 
											<span class="follow-type-name">[${follow.followTypeName}]</span>
											<span class="follow-op">${follow.opName}</span> 
											<span class="follow-date">登记于:&nbsp;${follow.creatDate}</span>
										</div>
										<div class="follow-content">
											<p>${follow.remark}</p>
										</div>
									</div>
								</c:forEach>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>