<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html> 
<html>
	<head>
		<title>财务报表对比</title>
		<script type="text/javascript">
		    var dataMap = ${dataMap};
		    var query =  '<%=request.getAttribute("query")%>';
		    var webPath = "${webPath}";
            var finCapFlag = "${finCapFlag}";
            var finProFlag = "${finProFlag}";
            var finCashFlag = "${finCashFlag}";
		</script>
		<script type="text/javascript" src="${webPath}/component/pfs/js/cusFinReport_inputReportContrastView.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/echarts-all.js"></script>
<%--		<script type="text/javascript" src="${webPath}/component/include/echarts.min.js"></script>--%>
		<link rel="stylesheet" href="${webPath}/component/pfs/css/cusFinReport_InsertOrUpdate.css?v=${cssJsVersion}" type="text/css"/>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container content_data">
			<div class="scroll-content">
				<div class="col-md-1 content_col" style="width:10%;">
					<div class="row_ul">
						<ul>
							<li class="li_selected" data-ctrl="cusFinCapData">资产负债表</li>
							<li data-ctrl="cusFinCashData">现金流量表</li>
							<li data-ctrl="cusFinProData">利润分配表</li>
							<li data-ctrl="cusFinRatioData">科目变化趋势图</li>
							<li data-ctrl="cusFinRatioData2">指标变化趋势图</li>
						</ul>
					</div>
				</div>
				<div class="col-md-11 content_col" style="width:90%;height:100%">
					<div class="row_ctrl" data-ctrl="cusFinCapData">
						<div class="row_content row_selected">
								<form name="operform" id="cusFinCapData" action="" method="post">
								<table width="100%" align="center" name="zc_table" style="float: left;">
									<thead>
										<tr  bordercolor="#ff0000">
											<th>项目</th>
											<c:forEach items="${capTitleList}" var="item">
												<th>期末数(${item})</th>
											</c:forEach>

										</tr>
									</thead>
									<tbody>
										
									</tbody>
								</table>
							</form>
						</div>
					</div>
					<div class="row_ctrl" data-ctrl="cusFinCashData"><!--现金流量表-->
						<div class="row_content" >
							<form name="operform" id="cusFinCashData" action="" method="post">
								<table width="100%" align="center">
									<thead>
										<tr align="center" bordercolor="#ff0000">
											<th>项目</th>
											<c:forEach items="${cashTitleList}" var="item">
												<th>本年数(${item})</th>
											</c:forEach>
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
							<form name="operform" id="cusFinProData" action="" method="post">
								<table width="100%" align="center">
									<thead>
										<tr align="center" bordercolor="#ff0000">
											<th>项目</th>
											<c:forEach items="${incomeTitleList}" var="item">
												<th>本期数(${item})</th>
											</c:forEach>
										</tr>
									</thead>
									<tbody>
										
									</tbody>
								</table>
							</form>
						</div>
					</div>
					<div class="row_ctrl" data-ctrl="cusFinRatioData">
						<div class="row_content" id="cusFinRatioData" style="width: 1100px;height: 600px;">
						</div>
					</div>
					<div class="row_ctrl" data-ctrl="cusFinRatioData2" style="overflow: auto">
						<div class="row_content" id="cusFinRatioData2" style="width: 1500px;height: 600px; float: left">
						</div>
						<div class="row_content" id="cusFinRatioData3" style="width: 1500px;height: 400px;float: left">
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script>
		var cusFinRatioData=echarts.init(document.getElementById("cusFinRatioData"));
		var cusFinRatioData2=echarts.init(document.getElementById("cusFinRatioData2"));
		var cusFinRatioData3=echarts.init(document.getElementById("cusFinRatioData3"));

		var dateArray = ${dataMap.dateArray}
		var xItem = ${dataMap.xItem}
		var dataArrayList = ${dataMap.dataArrayList}
		cusFinRatioData.setOption({
			xAxis:{data: xItem,splitLine:{show:false}},
			tooltip: {trigger: 'axis'},
			yAxis: {},
			legend: {data: dateArray},
			series:  dataArrayList
		});
		cusFinRatioData2.setOption({
			xAxis:{data: ${dataMap.xItemIndex},splitLine:{show:false},axisLabel: {
					interval:0,
				}},
			tooltip: {trigger: 'axis'},
			yAxis: {},
			legend: {data: ${dataMap.dateArrayIndex}},
			series:  ${dataMap.dataArrayListIndex}
		});

		cusFinRatioData3.setOption({
			xAxis:{data: ${dataMap.xItemIndex2},splitLine:{show:false},axisLabel: {
					interval:0,
				}},
			tooltip: {trigger: 'axis'},
			yAxis: {},
			legend: {data: ${dataMap.dateArrayIndex2}},
			series:  ${dataMap.dataArrayListIndex2}
		});
	</script>
</html>