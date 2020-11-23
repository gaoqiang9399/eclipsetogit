<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/component/oa/notice/css/MfOaNoticeList.css" />
		<link rel="stylesheet" href="${webPath}/component/cus/css/MfCusCustomer_Detail.css" />
		<style type="text/css">
			.classtype-name2{
				font-size: 20px;
			    text-align: center;
			    position: absolute;
			    top: 50px;
			}
			.block-left {
			    padding-left: 60px;
			}
			#auditButton{
				border: 0;
				top:17px;
				width: 105px;
				right: 20px !important;
				left: auto;
				position: absolute;
				background-color: #fff;
			}
			.td-second-div{
				float:left; 
				padding-right:40px;
			}
			.td-third-div{
				overflow: hidden;
				white-space: nowrap; 
				text-overflow: ellipsis;
				width:600px; 
				float:left; 
			}
		</style>
	</head>

<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix classfiyinfo">
			<div class="col-md-8 column block-left">
				<div class="bg-white">
					<div  class="row clearfix btn bg-danger next-div hide">
						<div class="col-xs-12 column text-center">
							<div class="block-next"></div>
						</div>
					</div>
					<!--业务主要信息 -->
					<div class="row clearfix">
						<!--头像 -->
						<div class="col-xs-3 column text-center head-img">
							<div>
								<button type="button" class="btn btn-font-pact padding_left_15">
									<i class="i i-pact font-icon"></i>
									<div class="font-text-left">合同信息</div>
								</button>
							</div>
							<c:if test="${fn:length(mfbusfincApp.kindName) > 8 }">
								<div class="btn btn-link" title="${mfbusfincApp.kindName}">
									${fn:substring(mfbusfincApp.kindName, 0, 8)}...
								</div>
							</c:if>
							<c:if test="${fn:length(mfbusfincApp.kindName) <= 8 }">
								<div class="btn btn-link">${mfbusfincApp.kindName}</div>
							</c:if>
						</div>
						<!--概要信息 -->
						<div class="col-xs-7 column head-content">
							<div class="margin_bottom_5 clearfix">
								<div class="head-title pull-left">${mfbusfincApp.appName}</div>
							</div>
							<!--信息查看入口 -->
							<div>
								<table>
									<tr>
										<td>
											<p class="cont-title">合同金额</p>
											<p><span class="content-span">${mfbusfincApp.putoutAmtFormat}</span><span class="unit-span margin_right_25">万</span> </p>
										</td>
										<td>
											<p class="cont-title">合同期限</p>
											<p>
											<span class="content-span">${mfbusfincApp.termMonth}</span>
											<span class="unit-span margin_right_25">
											<c:if test='${mfbusfincApp.termType=="1"}'>个月</c:if>
											<c:if test='${mfbusfincApp.termType!="1"}'>天</c:if>
											</span> 
											</p>
										</td>
										<td>
											<p class="cont-title">年利率</p>
											<p><span class="content-span"> <fmt:formatNumber type="number" minFractionDigits="2" value="${mfbusfincApp.fincRate}" />
											</span> <span class="unit-span">%</span></p>
										</td>
									</tr>
								</table>
							</div>
							<%--  资金机构和核心企业
							<div class="btn-special">
								<s:if test='%{wareHouseCusNo!=null && wareHouseCusNo!="" && wareHouseCusNo!="0"}'>
									<span  class="relate-corp" data-view='cuswarehouse'>
										<i class="i i-cangKu"></i><span>由仓储机构<a href="javascript:void(0);" onclick="getInfoForView('cus','${wareHouseCusNo}','仓储机构');">${mfBusApply.cusNameWarehouse}</a>保管货物 </span>
									</span>
								</s:if>
								<s:if test='%{coreCusNo!=null && coreCusNo!="" && coreCusNo!="0"}'>
									<span class="relate-corp" data-view='cuscore'>
										<i class="i i-qiYe"></i><span>由核心企业 <a href="javascript:void(0);"  onclick="getInfoForView('cus','${coreCusNo}','核心企业');">${mfBusApply.cusNameCore}</a> 推荐</span>
									</span>
								</s:if>
								<s:if test='%{fundCusNo!=null && fundCusNo!="" && fundCusNo !="0"}'>
									<span class="relate-corp" data-view='fundorg' >
										<i class="i i-fundorg"></i><span>由资金机构 <a href="javascript:void(0);" onclick="getInfoForView('cus','${fundCusNo}','资金机构 ');">${mfBusApply.cusNameFund}</a> 放款</span>
									</span>
								</s:if>
							</div>
							 --%>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-xs-2 column">
				<!--五级分类-->
				<div class="classtype-name2">
					<button class="btn cus-integrity" type="button" id="fiveclass">
						<i class="i i-fenlei"></i><span>
						<c:if test="${mfPactFiveclass == null}">暂无分类</c:if>
						<c:if test="${mfPactFiveclass.fiveclass == 1}">正常</c:if>
						<c:if test="${mfPactFiveclass.fiveclass == 2}">关注</c:if>
						<c:if test="${mfPactFiveclass.fiveclass == 3}">次级</c:if>
						<c:if test="${mfPactFiveclass.fiveclass == 4}">可疑</c:if>
						<c:if test="${mfPactFiveclass.fiveclass == 5}">损失</c:if></span>
					</button>
				</div>
			</div>
		</div>
	</div>
	<div id="list-div">
		<div class="list-min-div" style="position: relative;">
			<table class="list-div-table">
				<!--// TODO 预留一个权限id，暂时未添加数据。-->
				<dhcc:pmsTag pmsId="loanafter-fiveclass-auditButton">
					<div id="auditButton" class="formRowCenter">
						<input  type="button" value="审批功能导出" onclick="MfPactFiveclass_View.openFiveClass();" class="cancel">
					</div>
				</dhcc:pmsTag>
				<tbody>
					<c:forEach items="${mfPactFiveclassList }" var="m" >
						<%-- <tr class="<c:c test='#statu.even'>nth-child-even</c:if><c:else>nth-child-odd</c:else>"> --%>
						<tr class="<c:choose><c:when test='#statu.even'>nth-child-even</c:when><c:otherwise>nth-child-odd</c:otherwise></c:choose>">
							<td class="td-first">
								<div class="text-right">
									<p class="marginNone">${fn:substring(m.regTime,0,4) }-${fn:substring(m.regTime,4,6) }-${fn:substring(m.regTime,6,8) }</p>
									<p class="padding-right-20px">${fn:substring(m.regTime,9,17) }</p>
								</div>
							</td>
							<td class="td-second">
								<div class="td-second-div">
									<span class="title-color">
										<c:if test="${m.fiveclass == 1}">正常</c:if>
										<c:if test="${m.fiveclass == 2}">关注</c:if>
										<c:if test="${m.fiveclass == 3}">次级</c:if>
										<c:if test="${m.fiveclass == 4}">可疑</c:if>
										<c:if test="${m.fiveclass == 5}">损失</c:if>
									</span>
									<span class="margin-left-20px">操作员：${m.opName }</span>
								</div>
								<div class="td-third-div" title="${m.changeReason}" >
									  ${m.changeReason }
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
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
</body>
<script type="text/javascript">
	var webPath = '${webPath}';
	var mfPactFiveclass = '${mfPactFiveclass}';
	var fiveclass = '${mfPactFiveclass.fiveclass}';
	var fiveclassId = '${mfPactFiveclass.fiveclassId}';
	var pactId = '${mfPactFiveclass.pactId}';
	var fincId = '${mfPactFiveclass.fincId}';
	var appId = '${mfbusfincApp.appId}';
	var cusNo = '${mfbusfincApp.cusNo}';
	$(function() {
		MfPactFiveclass_View.init();
	});
</script>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
<script type="text/javascript" src="${webPath}/component/pact/js/MfPactFiveclass_View.js"></script>

</html>
