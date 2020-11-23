<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html> 
<html>
	<head>
		<title>报表信息</title>
		<script type="text/javascript">
		    var dataMap = ${dataMap};
            var finCapFlag = dataMap.mfCusReportAcount.assetsDataId != null && dataMap.mfCusReportAcount.assetsDataId != ''?true:false;
            var finProFlag = dataMap.mfCusReportAcount.incomeDataId != null && dataMap.mfCusReportAcount.incomeDataId!= ''?true:false;
            var finCashFlag = dataMap.mfCusReportAcount.cashDataId != null && dataMap.mfCusReportAcount.cashDataId!= ''?true:false;
            var finSubjectFlag = dataMap.mfCusReportAcount.balanceDataId != null && dataMap.mfCusReportAcount.balanceDataId != ''?true:false;
		    var query =  '<%=request.getAttribute("query")%>';
		    var finRptSts = '<%=request.getAttribute("finRptSts")%>';
		    var webPath = "${webPath}";
		    var teplateType = "${teplateType}";
		    var cusNo = "${cusNo}";
		    var finRatioEditFlag = "${finRatioEditFlag}";
		</script>
		<script type="text/javascript" src="${webPath}/component/pfs/js/cusFinReport_InsertOrUpdate.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<link rel="stylesheet" href="${webPath}/component/pfs/css/cusFinReport_InsertOrUpdate.css?v=${cssJsVersion}" type="text/css"/>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container content_data">
			<div class="scroll-content">
				<div class="col-md-1 content_col" style="width:10%;">
					<div class="row_ul">
						<ul>
						    <c:if test="${empty teplateType }">
								<c:if test="${dataMap.mfCusReportAcount.assetsDataId != null and dataMap.mfCusReportAcount.assetsDataId != '' }">
									<li class="li_selected" data-ctrl="cusFinCapData">资产负债表</li>
								</c:if>
								<c:if test="${dataMap.mfCusReportAcount.cashDataId != null and dataMap.mfCusReportAcount.cashDataId!= '' }">
									<li data-ctrl="cusFinCashData">现金流量表</li>
								</c:if>
								<c:if test="${dataMap.mfCusReportAcount.incomeDataId != null and dataMap.mfCusReportAcount.incomeDataId!= '' }">
									<li data-ctrl="cusFinProData">利润分配表</li>
								</c:if>
								<dhcc:pmsTag pmsId="cus-edit-SubjectData">
								<c:if test="${dataMap.mfCusReportAcount.balanceDataId != null and dataMap.mfCusReportAcount.balanceDataId != '' }">
									<li data-ctrl="cusFinSubjectData">科目余额表</li>
								</c:if>
								</dhcc:pmsTag>
								<%--<c:if test="${dataMap.cusFinMain.finSubjectFlag == true }">--%>
									<%--<li data-ctrl="cusFinSubjectData">科目余额表</li>--%>
								<%--</c:if>--%>
							</c:if>
							<c:if test="${! empty teplateType}">
								<li class="li_selected" data-ctrl="cusFinCapData">资产负债表</li>
								<li data-ctrl="cusFinCashData">现金流量表</li>
								<li data-ctrl="cusFinProData">利润分配表</li>
								<dhcc:pmsTag pmsId="cus-edit-SubjectData">
									<c:if test="${dataMap.mfCusReportAcount.balanceDataId != null and dataMap.mfCusReportAcount.balanceDataId != '' }">
								      <li data-ctrl="cusFinSubjectData">科目余额表</li>
									</c:if>
								</dhcc:pmsTag>
							</c:if>
							<c:if test="${finRptSts== '2' }">
								 <li data-ctrl="cusFinRatioData">财务指标及分析</li>
							</c:if>
						</ul>
					</div>
				</div>
				<div class="col-md-11 content_col" style="width:90%;height:100%">
					<div class="row_ctrl" data-ctrl="cusFinCapData"><!--资产负债表 peng-财务改-->
						<div class="row_content row_selected">
							<%--<form name="operform" id="cusFinCapData" action="${webPath}/cusFinCapData/insertOrUpdateAjax" method="post">--%>
								<form name="operform" id="cusFinCapData" action="${webPath}/mfCusReportData/insertOrUpdateAjax" method="post">
								<table width="100%" align="center" name="zc_table" style="float: left;">
									<thead>
										<tr  bordercolor="#ff0000">
											<th>项目</th>
											<th>行次</th>
											<th>期末数</th>
											<%--<th>调整期末数</th>--%>
											<th>年初数</th>
											<%--<th>调整年初数</th>--%>
										</tr>
									</thead>
									<tbody>
										
									</tbody>
								</table>
								<%--<table align="center" width="50%" name="fz_table" style="float: right;">--%>
									<%--<thead>--%>
										<%--<tr align="center" bordercolor="#ff0000">--%>
											<%--<th>负债类</th>--%>
											<%--<th>期初金额</th>--%>
											<%--<th>原始期末金额</th>--%>
											<%--<th>调整期末金额</th>--%>
										<%--</tr>--%>
									<%--</thead>--%>
									<%--<tbody>--%>
										<%----%>
									<%--</tbody>--%>
								<%--</table>--%>
							</form>
						</div>
					</div>
					<div class="row_ctrl" data-ctrl="cusFinCashData"><!--现金流量表-->
						<div class="row_content" >
							<form name="operform" id="cusFinCashData" action="${webPath}/mfCusReportData/insertOrUpdateAjax" method="post">
								<table width="100%" align="center">
									<thead>
										<tr align="center" bordercolor="#ff0000">
											<th>项目</th>
											<th>行次</th>
											<th>本年数</th>
											<%--<th>调整本年数</th>--%>
											<th>上年数</th>
											<%--<th>调整上年数</th>--%>
										</tr>
									</thead>
									<tbody>
										
									</tbody>
								</table>
							</form>
						</div>
					</div>
					<div class="row_ctrl" data-ctrl="cusFinProData"><!--利润分配表-->
						<div class="row_content" >
							<form name="operform" id="cusFinProData" action="${webPath}/mfCusReportData/insertOrUpdateAjax" method="post">
								<table width="100%" align="center">
									<thead>
										<tr align="center" bordercolor="#ff0000">
											<th>项目</th>
											<th>行次</th>
											<th>本期数</th>
											<%--<th>调整本期数</th>--%>
											<th>本年累计数</th>
											<%--<th>调整本年累计数</th>--%>
										</tr>
									</thead>
									<tbody>
										
									</tbody>
								</table>
							</form>
						</div>
					</div>
					<div class="row_ctrl" data-ctrl="cusFinSubjectData">
						<div class="row_content" >
							<%--<form name="operform" id="cusFinSubjectData" action="${webPath}/mfCusReportData/insertOrUpdateAjax" method="post">--%>
							<form name="operform" id="cusFinSubjectData" action="${webPath}/cusFinSubjectData/updateAjax" method="post">
								<table width="100%" align="center" style="margin-bottom:0px;">
									<thead>
										<tr align="center" bordercolor="#ff0000">
											<%--使用原来的--%>
											<%--<th width="25%">科目编码</th>--%>
											<%--<th width="25%">科目名称</th>--%>
											<%--<th width="25%">款项性质</th>--%>
											<%--<th width="25%">金额</th>--%>
											<th width="20%">科目名称</th>
											<th width="35%">客户名称</th>
											<th width="15%">科目余额（元）</th>
											<th width="15%">百分比（%）</th>
											<th width="15%">款项性质</th>
										</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
							</form>
						</div>
					</div>

					<div class="row_ctrl" data-ctrl="cusFinRatioData">
						<div class="row_content" >
							<form name="operform" id="cusFinRatioData" action="${webPath}/cusFinRatioData/updateAjax" method="post">
								<table width="100%" align="center" style="margin-bottom:0px;">
									<thead>
										<tr align="center" bordercolor="#ff0000" style="height: 30px;line-height: 30px;">
											<th>财务指标</th>
											<th>原始数据</th>
											<th>调整数据</th>
											<th>评分结果</th>
										</tr>
									</thead>
									<tbody>


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
								<dhcc:thirdButton value="保存" action="保存" onclick="submitDataNew('#cusFinCapData','1');"></dhcc:thirdButton>
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
								<dhcc:thirdButton value="保存" action="保存" onclick="submitDataNew('#cusFinCashData','3');"></dhcc:thirdButton>
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
								<dhcc:thirdButton value="保存" action="保存" onclick="submitDataNew('#cusFinProData','2');"></dhcc:thirdButton>
							</c:if>
						</c:if>
						<c:if test="${uploadFlag == 1 }">
				   			<dhcc:thirdButton value="继续上传" action="继续上传" onclick="continueUpload();"></dhcc:thirdButton>
				   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
						</c:if>
					</div>
				</div>
				<div data-ctrl="cusFinSubjectData">
					<div class="btn_content">
						<c:if test="${uploadFlag != 1 }">
							<c:if test="${query!='query' }">
								<%--<dhcc:thirdButton value="保存" action="保存" onclick="submitDataNew('#cusFinSubjectData','5');"></dhcc:thirdButton>--%>
								<dhcc:thirdButton value="保存" action="保存" onclick="submitSubjectData('#cusFinSubjectData','5');"></dhcc:thirdButton>
							</c:if>
						</c:if>
						<c:if test="${uploadFlag == 1 }">
				   			<dhcc:thirdButton value="继续上传" action="继续上传" onclick="continueUpload();"></dhcc:thirdButton>
				   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
						</c:if>
					</div>
				</div>
				<%--财务指标--%>
				<div data-ctrl="cusFinRatioData">
					<div class="btn_content">
						<c:if test="${finRatioEditFlag == 1 }">
							<dhcc:thirdButton value="保存" action="保存" onclick="submitFinRatioData('#cusFinRatioData');"></dhcc:thirdButton>
						</c:if>
						<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
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