<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html> 
<html>
	<head>
		<title>报表信息</title>
		<script type="text/javascript">
		    var dataMap = ${dataMap};
		    var query =  '${query}';
		    var reportTypeId = "${reportTypeId}";
		    var webPath = "${webPath}";
		    var cusNo = "${cusNo}";
		</script>
		<script type="text/javascript" src="${webPath}/component/pfs/js/cusReport_InsertOnline.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<link rel="stylesheet" href="${webPath}/component/pfs/css/cusFinReport_InsertOrUpdate.css?v=${cssJsVersion}" type="text/css"/>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container content_data">
			<div class="scroll-content">
				<div class="col-md-1 content_col" style="width:10%;">
					<div class="row_ul">
						<ul>
							<c:if test="${reportTypeId == '001' }">
							<li class="li_selected" data-ctrl="cusFinCapData">资产负债表</li>
						</c:if>
							<c:if test="${reportTypeId == '002' }">
								<li data-ctrl="cusFinCashData">现金流量表</li>
							</c:if>
							<c:if test="${reportTypeId == '003' }">
								<li data-ctrl="cusFinProData">利润分配表</li>
							</c:if>
							<dhcc:pmsTag pmsId="cus-edit-SubjectData">
							<c:if test="${reportTypeId == '004' }">
								<li data-ctrl="cusFinSubjectData">科目余额表</li>
							</c:if>
							</dhcc:pmsTag>
							<c:if test="${reportTypeId == '005' }">
								<li class="li_selected" data-ctrl="cusFinCapData">资产负债表</li>
								<li data-ctrl="cusFinCashData">现金流量表</li>
								<li data-ctrl="cusFinProData">利润分配表</li>
								<dhcc:pmsTag pmsId="cus-edit-SubjectData">
								<li data-ctrl="cusFinSubjectData">科目余额表</li>
								</dhcc:pmsTag>
							</c:if>
						</ul>
					</div>
				</div>
				<div class="col-md-11 content_col" style="width:90%;height:100%">
					<table width="100%" class="main_Rpt_table" style="margin: 10px 0px;">
						<tbody>
						<tr bordercolor="#ff0000">
							<td class="titleClass21">报表类型</td>
							<td><select id="reportType">
								<option value="1">月报</option>
								<option value="3">年报</option>
							</select>
							</td>
							<td class="titleClass21">报表时间</td>
							<td><input id="weeks" placeholder="年报4位(2019),月报6位(201905)"/></td>
							<td class="titleClass21">是否审计</td>
							<td><select id="ifAud"><option value="1">已审计</option><option value="0">未审计</option></select></td>
							<td class="titleClass21">审计单位</td>
							<td><input id="audOrg" placeholder="选已审记,则此项必填"/></td>
							<td class="titleClass21">审计日期</td>
							<td><input id="audDate" placeholder="选已审记,则此项必填"/></td>
							<td class="titleClass21">审计意见</td>
							<td><input id="audIdea" placeholder="选已审记,则此项必填"/></td>
						</tr>
						</tbody>
					</table>
					<div class="row_ctrl" data-ctrl="cusFinCapData"><!--资产负债表 peng-财务改-->
						<div class="row_content row_selected">
							<form name="operform" id="cusFinCapData" action="${webPath}/mfCusReportData/insertOnlineAjax" method="post">
								<table width="48%" align="center" name="zc_table" style="float: left;">
									<thead>
										<tr  bordercolor="#ff0000">
											<th>资产类</th>
											<th>行次</th>
											<th>期末数</th>
											<th>年初数</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
								<table align="center" width="48%" name="fz_table" style="float: right;">
									<thead>
									<tr align="center" bordercolor="#ff0000">
										<th>负债类</th>
										<th>行次</th>
										<th>期末数</th>
										<th>年初数</th>
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
							<form name="operform" id="cusFinCashData" action="${webPath}/mfCusReportData/insertOnlineAjax" method="post">
								<table width="100%" align="center">
									<thead>
										<tr align="center" bordercolor="#ff0000">
											<th>项目</th>
											<th>行次</th>
											<th>本年数</th>
											<th>上年数</th>
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
							<form name="operform" id="cusFinProData" action="${webPath}/mfCusReportData/insertOnlineAjax" method="post">
								<table width="100%" align="center">
									<thead>
										<tr align="center" bordercolor="#ff0000">
											<th>项目</th>
											<th>行次</th>
											<th>本期数</th>
											<th>本年累计数</th>
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
							<form name="operform" id="cusFinSubjectData" action="${webPath}/mfCusReportData/insertOnlineAjax" method="post">
								<table width="100%" align="center" style="margin-bottom:0px;">
									<thead>
									<tr align="center" bordercolor="#ff0000">
										<th width="25%">科目编码</th>
										<th width="25%">科目名称</th>
										<th width="20%">款项性质</th>
										<th width="20%">金额</th>
										<th width="10%">操作
											<button title="新增" onclick="addTr();return false;" class="btn btn-link formAdd-btn">
											<i class="i i-jia3"></i>
										</button>
										</th>
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
						<dhcc:thirdButton value="保存" action="保存" onclick="submitDataNew('#cusFinCapData','001');"></dhcc:thirdButton>
					</div>
				</div>
				<div  data-ctrl="cusFinCashData">
					<div class="btn_content">
						<dhcc:thirdButton value="保存" action="保存" onclick="submitDataNew('#cusFinCashData','002');"></dhcc:thirdButton>
					</div>
				</div>
				<div data-ctrl="cusFinProData">
					<div class="btn_content">
						<dhcc:thirdButton value="保存" action="保存" onclick="submitDataNew('#cusFinProData','003');"></dhcc:thirdButton>
					</div>
				</div>
				<div data-ctrl="cusFinSubjectData">
					<div class="btn_content">
						<dhcc:thirdButton value="保存" action="保存" onclick="submitDataNew('#cusFinSubjectData','004');"></dhcc:thirdButton>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" name="cusNo" value="${cusNo}"/>
	</body>
</html>