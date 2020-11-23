<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>进销存-报表入口页面</title>
	<link rel="stylesheet" href="${webPath}/component/report/css/MfReportEntrance.css?v=${cssJsVersion}" />
	<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
	<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
	<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
	<style type="text/css">
		.leftBorderDiv {
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
		}
	</style>
	<script type="text/javascript">
		totalList = $.parseJSON('${json}').pssReportItemListTotal;
	</script>
<body class="overflowHidden">
	<div class="container" id="report_query_div">
		<!-- 查询条件div开始 -->
			<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<!-- 我的筛选选中后的显示块 -->
				<div class="row clearfix">
					<div class="col-xs-4 column mysearch-div" id="pills">
						<!-- 更多查询条件功能开始 -->
						<div class="mod-toolbar-top">
							<div class="left">
								<!-- 查询标题 -->
								<div class="ui-btn-menu fl" id="filter-menu">
									<!-- 显示条件 -->
									<span class="ui-btn menu-btn" style="display:none"><span id="selected-period"></span><b></b>
									</span>
									<!-- 弹窗  -->
									<div class="search_con">
										<form id="cwListForm">
											<!-- 展开的更多条件  -->
											<!-- <ul class="filter-list" id="more-conditions" style="display: none;"> -->
											<ul class="filter-list" id="more-conditions" >
												
												<li class="li-two-wrap" id="dateIntervalLi">
													<label id="dateIntervalName">单据日期:</label> 
													<input type="text" class="form-control form-warp cw-week dateInterver" readonly name="beginDate"  saveFlag="1"  noFlag="0"  id="beginDate" autocomplete="off" onclick="PssReportEntrance.showCalendarDlg(this);" onkeydown="enterKey();">
													<span>至</span> 
													<input type="text" class="form-control form-warp cw-week dateInterver" readonly name="endDate"  saveFlag="1"  noFlag="0"  id="endDate" autocomplete="off" onclick="PssReportEntrance.showCalendarDlg(this);" onkeydown="enterKey();">
												</li>
												
												<li class="li-one-wrap" id="cusNo2Li">
													<label for="#filter-fromSubject" id="opNameLable">供应商:</label>
													<input type="hidden" id="cusNo2" saveFlag="1" noFlag="1">
													<input type="text" class="form-control form-warp" name="cusName2" id="cusName2" saveFlag="0" noFlag="0"  autocomplete="off">
													<span class="glyphicon glyphicon-search search-addon comitem_select" for="cusNo2"></span>
												</li>
												
												<li class="li-one-wrap" id="cusNo1Li">
													<label for="#filter-fromSubject" id="opNameLable">客户:</label>
													<input type="hidden" id="cusNo1" saveFlag="1" noFlag="1">
													<input type="text" class="form-control form-warp" name="cusName1" id="cusName1" saveFlag="0" noFlag="0"  autocomplete="off">
													<span class="glyphicon glyphicon-search search-addon comitem_select" for="cusNo1"></span>
												</li>
												
												<li class="li-one-wrap" id="goodsIdLi">
													<label for="#filter-fromSubject">商品:</label>
													<input type="hidden" id="goodsId" saveFlag="1" noFlag="1">
													<input type="text" class="form-control form-warp" name="goodsName" id="goodsName" saveFlag="0"  noFlag="0" autocomplete="off">
													<span class="glyphicon glyphicon-search search-addon comitem_select" for="goodsName"></span>
												</li>
												
												<li class="li-one-wrap" id="storehouseIdLi">
													<label for="#filter-fromSubject">仓库:</label>
													<input type="hidden" id="storehouseId" saveFlag="1" noFlag="1">
													<input type="text" class="form-control form-warp" name="storehouseName" id="storehouseName" saveFlag="0" noFlag="0" autocomplete="off">
													<span class="glyphicon glyphicon-search search-addon comitem_select" for="storehouseName"></span>
												</li>
												
												<li class="li-one-wrap" id="salerNoLi">
													<label for="#filter-fromSubject" id="salerNoLable">销售人员:</label>
													<input type="hidden" id="salerNo" saveFlag="1" noFlag="1">
													<input type="text" class="form-control form-warp" name="salerName" id="salerName" saveFlag="0" noFlag="0"  autocomplete="off">
													<span class="glyphicon glyphicon-search search-addon comitem_select" for="salerNo"></span> 
												</li>
																								
											</ul>
											<br>
										</form>
										<div class="btns">
											<a class="ui-btn ui-btn-sp" id="filter-submit" onclick="PssReportEntrance.reportSave()">确定</a> 
											<a class="ui-btn" id="filter-reset" onclick="PssReportEntrance.resetQueryInput();" tabindex="-1" style="display: inline;">重置</a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 查询条件div结束 -->
	
		<div class="row info-block bg-white">
			<div class="row info-title">购货报表</div>
			<div class="row info-content" id="PssBuyReportDiv">
			<c:if test="${! empty buyOrderQueryList}">
				<c:forEach var="pssQueryItem" items="${buyOrderQueryList}" varStatus="status">
		     		<%-- <dhcc:pmsTag pmsId="${pssQueryItem.itemId}"> --%>
						<div class="col-md-3 info-box-div" id="${pssQueryItem.itemId}">
		     				<i class="${pssQueryItem.itemIcon} leftBorderDiv" reportId='${pssQueryItem.itemId}'></i>
							<div class="info-box">
								<div class="info-box-content" onclick="PssReportEntrance.reportQuery('1',this,'${pssQueryItem.itemId}','');">
									<span class="info-box-text">${pssQueryItem.itemName}</span> 
								</div>
							</div>
						</div>
		     		<%-- </dhcc:pmsTag> --%>
				</c:forEach>
			</c:if>
			</div>
		<div class="row info-title">销货报表</div>
			<div class="row info-content" id="PssSaleReportDiv">
			<c:if test="${! empty saleOrderQueryList}">
				<c:forEach var="pssQueryItem" items="${saleOrderQueryList}" varStatus="status">
		     		<%-- <dhcc:pmsTag pmsId="${pssQueryItem.itemId}"> --%>
						<div class="col-md-3 info-box-div" id="${pssQueryItem.itemId}">
		     				<i class="${pssQueryItem.itemIcon} leftBorderDiv" reportId='${pssQueryItem.itemId}'></i>
							<div class="info-box">
								<div class="info-box-content" onclick="PssReportEntrance.reportQuery('2',this,'${pssQueryItem.itemId}','');">
									<span class="info-box-text">${pssQueryItem.itemName}</span> 
								</div>
							</div>
						</div>
		     		<%-- </dhcc:pmsTag> --%>
				</c:forEach>
			</c:if>
			</div>
		<div class="row info-title">仓库报表</div>
			<div class="row info-content" id="PssStoreReportDiv">
			<c:if test="${! empty storeOrderQueryList}">
				<c:forEach var="pssQueryItem" items="${storeOrderQueryList}" varStatus="status">
		     		<%-- <dhcc:pmsTag pmsId="${pssQueryItem.itemId}"> --%>
						<div class="col-md-3 info-box-div" id="${pssQueryItem.itemId}">
		     				<i class="${pssQueryItem.itemIcon} leftBorderDiv" reportId='${pssQueryItem.itemId}'></i>
							<div class="info-box">
								<div class="info-box-content" onclick="PssReportEntrance.reportQuery('3',this,'${pssQueryItem.itemId}','');">
									<span class="info-box-text">${pssQueryItem.itemName}</span> 
								</div>
							</div>
						</div>
		     		<%-- </dhcc:pmsTag> --%>
				</c:forEach>
			</c:if>
			</div>
		
		<div class="row info-block bg-white analysis-report">
			<div class="info-title">图形报表</div>
			<div class="row info-content">
				<c:if test="${! empty graphReportQueryList}">
				<c:forEach var="pssQueryItem" items="${graphReportQueryList}" varStatus="status">
		     		<%-- <dhcc:pmsTag pmsId="${pssQueryItem.itemId}"> --%>
						<div class="col-md-3 info-box-div" >
							<div class="info-box" >
								<div class="info-box-title" onclick="PssReportEntrance.openGraph('${pssQueryItem.itemId}','${pssQueryItem.itemName}');">${pssQueryItem.itemName}</div>
								<div class="info-box-content ${pssQueryItem.itemIcon}" onclick="PssReportEntrance.openGraph('${pssQueryItem.itemId}','${pssQueryItem.itemName}');"></div>
							</div>
						</div>
		     		<%-- </dhcc:pmsTag> --%>
				</c:forEach>
				</c:if>
			</div>
		</div>
		
	</div>
	</div>
	<script type="text/javascript" src="${webPath}/component/pss/js/PssReportEntrance.js?v=${cssJsVersion}"></script>
	<script type="text/javascript">
	var wholeReportId="";
	var iObj;
	$(function(){
		PssReportEntrance.init();
	});
	</script>
</body>
</html>