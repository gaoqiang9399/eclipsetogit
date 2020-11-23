<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html> 
<html>
	<head>
		<title>报表信息</title>
		<script type="text/javascript">
		    var dataMap = ${dataMap};
		    var finCapFlag = dataMap.cusFinMain.finCapFlag;
		    var finProFlag = dataMap.cusFinMain.finProFlag;
		    var finCashFlag = dataMap.cusFinMain.finCashFlag;
		    var query =  '<%=request.getAttribute("query")%>';
		    var finRptSts = '<%=request.getAttribute("finRptSts")%>';
		    var webPath = "${webPath}";
		    var teplateType = "${teplateType}";
		    var cusNo = "${cusNo}";
		</script>
		<script type="text/javascript" src="${webPath}/component/pfs/js/multiPeriodComparison_View.js?v=${cssJsVersion}"></script>
		<link rel="stylesheet" href="${webPath}/component/pfs/css/cusFinReport_InsertOrUpdate.css?v=${cssJsVersion}" type="text/css"/>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container content_data">
			<div class="scroll-content">
				<div class="col-md-1 content_col" style="width:10%;">
					<div class="row_ul">
						<ul>
						    <c:if test="${empty teplateType }">
								<c:if test="${dataMap.cusFinMain.finCapFlag == true }">
									<li class="li_selected" data-ctrl="cusFinCapData">资产负债表</li>
								</c:if>
								<c:if test="${dataMap.cusFinMain.finCashFlag == true }">
									<li data-ctrl="cusFinCashData">现金流量表</li>
								</c:if>
								<c:if test="${dataMap.cusFinMain.finProFlag == true }">
									<li data-ctrl="cusFinProData">利润分配表</li>
								</c:if>
							</c:if>
							<c:if test="${! empty teplateType}">
								<li class="li_selected" data-ctrl="cusFinCapData">资产负债表</li>
								<li data-ctrl="cusFinCashData">现金流量表</li>
								<li data-ctrl="cusFinProData">利润分配表</li>
							</c:if>
							<!-- <li data-ctrl="cusFinRatioData">财务指标</li> -->
						</ul>
					</div>
				</div>
				<div class="col-md-11 content_col" style="width:90%;height:100%">
					<div class="row_ctrl" data-ctrl="cusFinCapData"><!--资产负债表-->
						<div class="row_content row_selected">
							<form name="operform" id="cusFinCapData" action="${webPath}/cusFinCapData/insertOrUpdateAjax" method="post">
								<table width="100%" align="center" name="zc_table" style="float: left;">
									<thead>
										<tr id = "cusFinCapData_thead" bordercolor="#ff0000">
											<th>资产类</th>
											<th>期初金额</th>
											<th>期末金额</th>
										</tr>
									</thead>
									<tbody id = "cusFinCapData_tbody">
										
									</tbody>
								</table>
								<%--<table align="center" width="50%" name="fz_table" style="float: right;">
									<thead>
										<tr align="center" bordercolor="#ff0000">
											<th>负债类</th>
											<th>期初金额</th>
											<th>期末金额</th>
										</tr>
									</thead>
									<tbody>
										
									</tbody>
								</table>--%>
							</form>
						</div>
					</div>
					<div class="row_ctrl" data-ctrl="cusFinCashData"><!--现金流量表-->
						<div class="row_content" >
							<form name="operform" id="cusFinCashData" action="${webPath}/cusFinCashData/insertOrUpdateAjax" method="post">
								<table width="100%" align="center">
									<thead>
										<tr id = "cusFinCashData_thead" align="center" bordercolor="#ff0000">
											<th>项目</th>
											<th>上期金额</th>
											<th>本期金额</th>
										</tr>
									</thead>
									<tbody id = "cusFinCashData_tbody">
										
									</tbody>
								</table>
							</form>
						</div>
					</div>
					<div class="row_ctrl" data-ctrl="cusFinProData"><!--利润分配表-->
						<div class="row_content" >
							<form name="operform" id="cusFinProData" action="${webPath}/cusFinProData/insertOrUpdateAjax" method="post">
								<table width="100%" align="center">
									<thead>
										<tr id = "cusFinProData_thead" align="center" bordercolor="#ff0000">
											<th>项目</th>
											<th>上期金额</th>
											<th>本期金额</th>
										</tr>
									</thead>
									<tbody id = "cusFinProData_tbody">
										
									</tbody>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="btn-fin formRowCenter">
				<div data-ctrl="cusFinCapData">
					<div class="btn_content row_selected">
						<c:if test="${uploadFlag != 1 }">
							<c:if test="${query!='query' }">
								<dhcc:thirdButton value="保存" action="保存" onclick="submitData('#cusFinCapData','1');"></dhcc:thirdButton>
							</c:if>
						</c:if>
						<c:if test="${uploadFlag == 1 }">
				   			<dhcc:thirdButton value="继续上传" action="继续上传" onclick="continueUpload();"></dhcc:thirdButton>
				   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
						</c:if>
					</div>
				</div>
				<div  data-ctrl="cusFinCashData">
					<div class="btn_content">
						<c:if test="${uploadFlag != 1 }">
							<c:if test="${query!='query' }">
								<dhcc:thirdButton value="保存" action="保存" onclick="submitData('#cusFinCashData','3');"></dhcc:thirdButton>
							</c:if>
						</c:if>
						<c:if test="${uploadFlag == 1 }">
				   			<dhcc:thirdButton value="继续上传" action="继续上传" onclick="continueUpload();"></dhcc:thirdButton>
				   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
						</c:if>
					</div>
				</div>
				<div data-ctrl="cusFinProData">
					<div class="btn_content">
						<c:if test="${uploadFlag != 1 }">
							<c:if test="${query!='query' }">
								<dhcc:thirdButton value="保存" action="保存" onclick="submitData('#cusFinProData','2');"></dhcc:thirdButton>
							</c:if>
						</c:if>
						<c:if test="${uploadFlag == 1 }">
				   			<dhcc:thirdButton value="继续上传" action="继续上传" onclick="continueUpload();"></dhcc:thirdButton>
				   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" name="cusNo" value="${cusNo}"/>
		<input type="hidden" name="finRptType" value="${finRptType}"/>
		<input type="hidden" name="finRptDate" value="${finRptDate}"/>
		<input type="hidden" name="accRule" value="${accRule}"/>
		<input type="hidden" name="relationCorpName" value="${relationCorpName}"/>
		<input type="hidden" name="relationCorpNo" value="${relationCorpNo}"/>
	</body>
</html>