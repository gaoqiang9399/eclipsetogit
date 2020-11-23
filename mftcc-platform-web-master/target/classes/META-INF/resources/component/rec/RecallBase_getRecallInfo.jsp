<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<link rel="stylesheet" href="${webPath}/component/rec/css/RecallBase.css" />
		<script type="text/javascript">
		var conNo='${conNo}';
		var pactId='${pactId}';
		var cusNo = '${cusNo}';
		var fincId = '${fincId}';
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<c:if test='${recallBase.taskNo ne "" && recallBase.taskNo ne null}'>
						<div class="head-div">
							<div class="row clearfix margin_bottom_20">
								<span class="font_size_16">${recallBase.appName}</span>
								<input id="taskNo" type="hidden" value="${recallBase.taskNo}"></input>
							</div>
							<div class="row clearfix">
								<div class="col-md-10 column">
									<table>
										<tr>
											<td class="tdlabel">客户名称：</td>
											<td class="tdvalue" colspan="5">${recallBase.cusName}</td>
										</tr>
										<tr>
											<td class="tdlabel">联系人：</td>
											<td class="tdvalue">${recallBase.cusContactName}</td>
											<td class="tdlabel">应还本金：</td>
											<td class="tdvalue">${recallBase.recallUnpayAmt1}元</td>
											<td class="tdlabel">应还利息：</td>
											<td class="tdvalue">${recallBase.recallUnpayAmt2}元</td>
										</tr>
										<tr>
											<td class="tdlabel">联系电话：</td>
											<td class="tdvalue">${recallBase.cusTel}</td>
											<td class="tdlabel">违约金：</td>
											<td class="tdvalue">${recallBase.brcContAmt}元</td>
											<td class="tdlabel">催收总额：</td>
											<td class="tdvalue">${recallBase.recallAmt}元</td>
										</tr>
										<tr>
											<td class="tdlabel">催收方式：</td>
											<td class="tdvalue">${recallBase.recallWay}</td>
											<td class="tdlabel">催收人：</td>
											<td class="tdvalue"><span id="recOpName">${recallBase.mgrName}</span> <a href="javascript:void(0);" onclick="ReCallDetailInfo.recallReassign();">改派</a></td>
											<td class="tdlabel"></td>
											<td class="tdvalue"></td>
										</tr>
									</table>
								</div>
								<div class="col-md-2 column">
									<div style="height:120px;padding-top: 80px;">
										<button type="button" class="btn btn-primary btn-rec" onclick="ReCallDetailInfo.recallRegist('${pactId}','${cusNo}');">催收登记</button>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test='${recallBase.taskNo eq "" || recallBase.taskNo eq null}'>
						<div>
							<button type="button" class="btn btn-primary" onclick="ReCallDetailInfo.recallRegist('${pactId}','${cusNo}');">催收登记</button>
						</div>
					</c:if>
					<div class="his-list">
						<div class="col-md-12 column margin_top_20 ">
							<table>
								<tbody>
									<c:forEach var="recallBase" items="${recallBaseList}" varStatus="statu">
										<tr>
											<td>
												<div class="dateDiv">
													<p>${recallBase.recallDate}</p>
												</div>
											</td>
											<td>
												<div>
													<span class="color_theme">${recallBase.recallWay}</span>
													<span>${recallBase.mgrName}</span>
												</div>
												<div >
													<c:if test="${fn:length(recallBase.recallDesc)>80}">  
														<p title="${recallBase.recallDesc}">${fn:substring(recallBase.recallDesc,0,80)}...</p>
												  	</c:if>  
												 	<c:if test="${fn:length(recallBase.recallDesc)<=80}">  
														<p>${recallBase.recallDesc}</p>
												  	</c:if> 
													<p>催收回执： ${recallBase.returnDesc}</p>
												</div>
											</td>
										</tr>
									</c:forEach>
								<tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
   		<div class="footer_loader">
			<div class="loader">
				<i class="fa fa-spinner fa-pulse fa-3x"></i>
			</div>
			<div class="pagerShow">
				当前显示&nbsp;
				<span class="loadCount"></span>&nbsp;条数据，一共&nbsp;
				<span class="pageCount"></span>&nbsp;条数据
			</div>
			<div class="backToTop"></div>
		</div>
 	</div>
</body>
<script type="text/javascript" src="${webPath}/component/rec/js/RecallBase_getRecallInfo.js"></script>
<script type="text/javascript">
	$(function(){
		ReCallDetailInfo.init();
	});
</script>
</html>
