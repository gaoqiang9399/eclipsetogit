<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
 <%
 String reportUseFlag = (String)request.getAttribute("reportUseFlag"); 
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>报表入口页面</title>
	<link rel="stylesheet" href="${webPath}/component/report/css/MfReportEntrance.css?v=${cssJsVersion}" />
	<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
	<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
	<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
	<script type="text/javascript">
	tatolList = $.parseJSON('${json}').mfReportItemListTatol;
	</script>
	<style type="text/css">
		/*	.leftBorderDiv {
               border-right-width: 1px;
               border-left-width: 0px;
               border-top-width: 0px;
               border-bottom-width: 0px;
               border-style: solid;
               border-color: #ddd;
               text-align:center;
               height:70px;
               z-index:1001;
            }
            .leftBorderDiv:hover {
                border: 1px solid #32b5cb;
                cursor: pointer;
                background: #fff;
            }

            .leftBorderDivWidth{
                margin-left: 10px;
            }

            .dateInterver{
                padding-left:0px;
                font-size:13px;
            }*/
	</style>
<body class="overflowHidden">
	<div class="container" id="report_query_div">
		<dhcc:pmsTag pmsId="report-standing-book">
		<div class="row info-block bg-white">
			<div class="row info-title">台账查询</div>
			<div class="row info-content" id="StandingBookReportDiv">
			<c:if test="${! empty mfBaseItemList}">
				<c:forEach var="mfQueryItem" items="${mfBaseItemList}" varStatus="status">
		     		<dhcc:pmsTag pmsId="${mfQueryItem.itemId}">
						<div class="col-md-3 info-box-div" id="${mfQueryItem.itemId}">
							<%-- <i class="info-box-icon i i-chilun leftBorderDiv" reportId='${mfQueryItem.itemId}'></i> --%>
		     				<i class="${mfQueryItem.itemIcon} leftBorderDiv" style="margin-right: 0px" reportId='${mfQueryItem.itemId}' onclick="ReportEntrance.reportQuery('1',this,'${mfQueryItem.itemId}','');"></i>
							<div class="info-box">
								<div class="info-box-content" onclick="ReportEntrance.reportQuery('1',this,'${mfQueryItem.itemId}','');">
									<span class="info-box-text-span" mytitle="${mfQueryItem.itemName}" >${mfQueryItem.itemName}</span>
								</div>
							</div>
						</div>
		     		</dhcc:pmsTag>
				</c:forEach>
			</c:if>
			<div class="col-md-3 info-box-div addQuery">
					<div class="info-box" id="addQuery">
						<div class="info-box-add">
							<span class="btn-add i i-jia1"></span> 
						</div>
					</div>
				</div>
			</div>
		</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="report-statistical">
		<div class="row info-block bg-white statistical-report">
			<div class="row info-title">统计报表</div>
			<div class="row info-content" id="countReportDiv">
			<c:if test="${! empty mfReportBaseItemList}">
				<c:forEach var="mfQueryItem" items="${mfReportBaseItemList}" varStatus="status">
		     		<dhcc:pmsTag pmsId="${mfQueryItem.itemId}">
						<div class="col-md-3 info-box-div" id="${mfQueryItem.itemId}" >
							<%-- <div class="info-box" onclick="ReportEntrance.openReport(this,'${mfQueryItem.itemId}','${mfQueryItem.itemName}');"> --%>
							<div class="info-box-icon-circle leftBorderDiv"  reportId='${mfQueryItem.itemId}' onclick="ReportEntrance.reportQuery('2',this,'${mfQueryItem.itemId}','${mfQueryItem.itemName}');">
								<i class="${mfQueryItem.itemIcon}" style="margin-right: 0px"></i>
							</div>
							<div class="info-box" onclick="ReportEntrance.reportQuery('2',this,'${mfQueryItem.itemId}','${mfQueryItem.itemName}');">
								<div class="info-box-content">
									<span class="info-box-text-span"  mytitle="${mfQueryItem.itemName}" >${mfQueryItem.itemName}</span>
								</div>
							</div>
						</div>
		     		</dhcc:pmsTag>
				</c:forEach>
			</c:if>
				<div class="col-md-3 info-box-div addQuery">
					<div class="info-box" id="addTotalQuery">
						<div class="info-box-add">
							<span class="btn-add i i-jia1"></span> 
						</div>
					</div>
				</div>
			</div>
		</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="report-larger-screen">
			<div class="row info-block bg-white analysis-report">
				<div class="info-title">大屏报表</div>
				<div class="row info-content">
					<c:if test="${! empty largeResultList}">
						<c:forEach var="mfQueryItem" items="${largeResultList}" varStatus="status">
							<dhcc:pmsTag pmsId="${mfQueryItem.itemId}">
								<div class="col-md-3 info-box-div" >
									<div class="info-box" >
										<div class="info-box-title-larger" onclick="ReportEntrance.openLargeScreen(this,'${mfQueryItem.itemId}','${mfQueryItem.itemName}');">${mfQueryItem.itemName}</div>
										<div class="info-box-content-larger ${mfQueryItem.itemIcon}" onclick="ReportEntrance.openLargeScreen(this,'${mfQueryItem.itemId}','${mfQueryItem.itemName}');"></div>
									</div>
								</div>
							</dhcc:pmsTag>
						</c:forEach>
					</c:if>
				</div>
			</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="report-analysis">
			<div class="row info-block bg-white analysis-report">
				<div class="info-title">分析报表</div>
				<div class="row info-content">
					<c:if test="${! empty analysisResultList}">
					<c:forEach var="mfQueryItem" items="${analysisResultList}" varStatus="status">
						<dhcc:pmsTag pmsId="${mfQueryItem.itemId}">
							<div class="col-md-3 info-box-div" >
								<div class="info-box" >
									<div class="info-box-title" onclick="ReportEntrance.openTuDemo('${mfQueryItem.itemId}','${mfQueryItem.itemName}');">${mfQueryItem.itemName}</div>
									<div class="info-box-content ${mfQueryItem.itemIcon}" onclick="ReportEntrance.openTuDemo('${mfQueryItem.itemId}','${mfQueryItem.itemName}');"></div>
								</div>
							</div>
						</dhcc:pmsTag>
					</c:forEach>
					</c:if>
				</div>
			</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="report-monitor">
		<div class="row info-block bg-white monitor-report">
			<div class="info-title">监测报表</div>
			<div class="row info-content">
				<c:if test="${! empty watchResultList}">
				<c:forEach var="mfQueryItem" items="${watchResultList}" varStatus="status">
		     		<dhcc:pmsTag pmsId="${mfQueryItem.itemId}">
						<div class="col-md-4 info-box-div">
								<div class="info-box">
								<div class="info-box-content ${mfQueryItem.itemIcon}" onclick="ReportEntrance.openJIanGuan('${mfQueryItem.itemId}','${mfQueryItem.itemName}')"></div>
								</div>
						</div>
		     		</dhcc:pmsTag>
				</c:forEach>
				</c:if>
			</div>
		</div>
		</dhcc:pmsTag>
	</div>
	<script type="text/javascript" src="${webPath}/component/report/js/MfReportEntrance.js?v=${cssJsVersion}"></script>
	<script type="text/javascript">
	var wholeReportId="";
	var iObj;
	var funRoleType ='${sysUser.funRoleType}';
	var currDate = '${dataMap.currDate}';
    var yesterday = '${dataMap.yesterday}';
	var currMonth = '${dataMap.currMonth}';
	var lastMonth = '${dataMap.lastMonth}';
    var reduceThirtyOne = '${dataMap.reduceThirtyOne}';
	var reportUrl = '${reportUrl}'
    var reportProjectFlag = '${reportProjectFlag}'
	$(function(){
		ReportEntrance.init();
        $("#StandingBookReportDiv span[mytitle]").initMytitle();
        $("#countReportDiv span[mytitle]").initMytitle();
	});
	
	</script>
</body>
</html>