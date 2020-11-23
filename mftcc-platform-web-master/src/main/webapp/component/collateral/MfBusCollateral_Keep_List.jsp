<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
	<head>
		<title>保管信息</title>
		<link rel="stylesheet" href="${webPath}/component/oa/notice/css/MfOaNoticeList.css" />
	</head>

<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix classfiyinfo">
			<div class="col-xs-3 column text-center">
				<button type="button" class="btn btn-font-pledge padding_left_15" style="margin-right: 56px;margin-top: 20px;">
					<i class="i i-pledge font-icon"></i>
					<div class="font-text-left">保管信息</div>
				</button>
			</div>
			<div class="col-xs-7 column margin-left-30px">
				<div class="col-xs-10 column">
					<div class="padding_top_30 clearfix">
						<div class="bus head-title pull-left">${keepInfo.collateralName}</div>
					</div>
					<div class="padding_top_10">
						<p>
							<span><i class="i i-cangKu"></i> 
								<span id="keepOrgName"> 
									<c:choose>
										<c:when test="${keepInfo.keepOrgName!=''}">
											${keepInfo.keepOrgName}
										</c:when>
										<c:otherwise>
											<span  class="unregistered">未登记</span>
										</c:otherwise>
									</c:choose>
								</span> 
						    </span> 
						</p>
						<p>
							<i class="i i-location "></i> <span id="keepAddr">
								<c:choose>
										<c:when test="${keepInfo.keepAddr!=''}">
											${keepInfo.keepAddr}
										</c:when>
										<c:otherwise>
											<span class="unregistered">未登记</span>
										</c:otherwise>
									</c:choose>
							</span> 
						</p>
					</div>
				</div>
				<div class="col-xs-2 column ">
					<!--保管类型类型图标-->
					<div class="i i-warehouse class-type">
						<div class="classtype-name">${keepInfo.keepTypeName}</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="list-div">
		<div class="list-min-div">
			<table class="list-div-table">
				<tbody>
					</c:forEach items="${keepInfoList}" varStatus="statu" var="keepInfo">
						<tr class="<c:if test='${statu.index % 2 == 1 }'>nth-child-even</c:if><c:if ${statu.index % 2 == 0 }>nth-child-odd</c:if>">
							<td class="td-first">
								<div class="text-right">
									<p class="marginNone">${fn:substring(operateDate, 0, 4)+'-'+fn:substring(operateDate, 4, 6)+'-'+fn:substring(operateDate, 6, 8)}</p>
								</div>
							</td>
							<td class="td-second">
								<div  class="td-second-div">
									<span class="title-color">${keepTypeName}</span>
									<span class="margin-left-20px">保管人：${keeperName}</span>
									<span class="margin-left-20px">经办人：${operatorCusName}</span>
									<span class="margin-left-20px">负责人：${dutyCusName}</span>
								</div>
								<p mytitle="keepDesc">
								  <c:choose>
										<c:when test="${fn:length(keepDesc)>100}">
											${fn:substring(keepDesc, 0, 100)}......
										</c:when>
										<c:otherwise>
											${keepDesc}
										</c:otherwise>
									</c:choose>		
								</p>
							</td>
						</tr>
					</c:forEach>
				</tbody> 
			</table>
		</div>
	</div>
	<div class="footer_loader">
		<!-- <div class="loader">
			<i class="fa fa-spinner fa-pulse fa-3x"></i>
		</div>
		<div class="pagerShow">
			当前显示&nbsp;
			<span class="loadCount"></span>&nbsp;条数据，一共&nbsp;
			<span class="pageCount"></span>&nbsp;条数据
		</div>
		<div class="backToTop"></div> -->
	</div>		
</body>
<script type="text/javascript">
	var webPath = '${webPath}';
	var keepType = '${keepInfo.keepType}';
	var	cusNo = '${cusNo}';
	
	$(function() {
	$(".list-div-table p[mytitle]:contains('...')").initMytitle();
	var len = $("#list-div tbody tr").length;
		$(".pageCount").text(len);
		$(".loadCount").text(len);
	    $("#list-div").mCustomScrollbar({
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});	 
		//处理暂无数据的情况
		if ($('#list-div tbody tr').length == 0) {
			var thCount = $('.notice-div thead th').length;
			$('#list-div tbody')
					.append(
							'<tr><td style="text-align: center;padding-top:20px;font-size:18px;">暂无数据</td></tr>');
		};
		/* if (keepType == '3') {
			$(".class-type").addClass("i-blacklist");
			$(".classtype-name").addClass("i-blacklist");
		} else if (keepType == '1') {
			$(".class-type").addClass("i-good");
			$(".classtype-name").addClass("i-good");
		} else if(keepType == '2'){
			$(".class-type").addClass("i-color");
			$(".classtype-name").addClass("i-color");
		} */
		if(keepType == '0'){
			//红色
			$(".class-type").addClass("i-blacklist");
			$(".classtype-name").addClass("i-blacklist");
		}else if(keepType == '7' || keepType == '10' || keepType == '11'){
			//黑色
			$(".class-type").addClass("i-color");
			$(".classtype-name").addClass("i-color");
		}else{
			//绿色
			$(".class-type").addClass("i-good");
			$(".classtype-name").addClass("i-good");
		}
	});
</script>
</html>
