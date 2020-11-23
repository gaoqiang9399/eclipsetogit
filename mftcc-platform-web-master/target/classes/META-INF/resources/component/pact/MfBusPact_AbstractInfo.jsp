<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String pleId = (String)request.getParameter("pleId");
%>

<!--合同摘要信息-->
<!-- 合同未签订 -->
<c:if test='${mfBusApply.appSts!="4"}'>
	<div class="row clearfix no-content">
		<div class="col-xs-3 col-md-3 column">
			<button type="button" class="btn btn-font-none no-link padding_left_15">
				<i class="i i-pact font-icon"></i>
				<div class="font-text">合同信息</div>
			</button>
		</div>
		<div class="col-xs-9 col-md-9 column none-content-tip">
			<span>合同未签订</span>
		</div>
	</div>
</c:if>
<!-- 展示合同信息 -->
<c:if test='${mfBusApply.appSts=="4"}'>
	<div  id="busInfo" class="row clearfix has-content">
		<c:if test="${fn:length(mfBusApplyList)>3}">
			<div class="col-xs-3 col-md-3 column padding_top_20">
				<button type="button" class="btn btn-font-pact padding_left_15" onclick="getMultiBusList();">
					<i class="i i-pact font-icon"></i>
					<div class="font-text">合同信息</div>
				</button>
			</div>
			<div class="col-xs-9 col-md-9 column">
				<div class="row clearfix padding_top_40">
					<span>客户共有 <a  class="moreCnt more-apply-count pointer" onclick="getMultiBusList();"></a> 笔在履行业务
<!-- 						<a href="javascript:void(0)" class="toview pointer" onclick="getMultiBusList();">查看</a> -->
					</span>
				</div>
			</div>
		</c:if>
		<c:if test="${fn:length(mfBusApplyList)<=3}">
			<div class="col-xs-3 col-md-3 column padding_top_20">
				<button type="button" class="btn btn-font-pact padding_left_15" onclick="getPactInfo();">
					<i class="i i-pact font-icon"></i>
					<div class="font-text">合同信息</div>
				</button>
			</div>
			<div class="col-xs-9 col-md-9 column">
				<div class="row clearfix">
					<div class="col-xs-10 col-md-10 column">	
						<c:if test='${query=="cus"}'>
							<c:if test="${fn:length(mfBusApplyList)==0}">
								<button class="btn btn-link content-title" title="${mfBusPact.appName}" onclick="getPactInfo();">
									${mfBusPact.appName}
								</button>
							</c:if>
							<c:if test="${fn:length(mfBusApplyList)!=0}">
								 <div class="btn-group">
								 	  <c:if test="${fn:length(mfBusPact.appName)>8}">  
									  	<button type="button" id="apply-name" class="btn btn-link content-title margin_bottom_20 dropdown-toggle"  title="${mfBusPact.appName}" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${fn:substring(mfBusPact.appName,0,8)}...</button>
								 	  </c:if>
									   <c:if test="${fn:length(mfBusPact.appName)<=8}">  
									 	 <button type="button" id="apply-name" class="btn btn-link content-title margin_bottom_20 dropdown-toggle"  title="${mfBusPact.appName}"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${mfBusPact.appName}</button>
								 	  </c:if>
									  <button type="button" id="more-apply" class="btn btn-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									     <span class="badge"></span>
									  </button>
									  <ul class="dropdown-menu">
									  	<c:forEach items="${mfBusApplyList }" varStatus="statu" var="appObj">
						        			<li class="more-content-li" onclick="switchBus('${appObj.appId}');">
						        				<p class="more-title-p"><span>${appObj.appName}</span></p>
						        				<p class="more-content-p"><span class="more-span">总金额 ${appObj.appAmt}元</span><span class="more-span">期限 ${appObj.term}月</span><span class="more-span">利率 ${appObj.fincRate}%</span></p>
						        			</li>
								    	</c:forEach>
									  </ul>
								</div>	
							</c:if>
						</c:if>
						<c:if test='${query!="cus"}'>
							<c:if test="${fn:length(mfBusPact.appName)>8}">  
						  		 <button class="btn btn-link content-title" title="${mfBusPact.appName}" onclick="getPactInfo();">
						  			${fn:substring(mfBusPact.appName,0,8) }...
						  		</button>
					 	 	</c:if>
						    <c:if test="${fn:length(mfBusPact.appName)<=8}">  
						 	 <button class="btn btn-link content-title" title="${mfBusPact.appName}" onclick="getPactInfo();">
								${mfBusPact.appName}
							</button>
						   </c:if>
						</c:if>
					</div>
					<div class="col-xs-2 col-md-2 column">
						<button type="button" class="btn btn-font-qiehuan qiehuan"   onclick="getPactInfo();"><i class="i i-qiehuan" style="font-size:22px;"></i></button>
					</div>
				</div>
				<div class="row clearfix">
					<p>
						<span  class="content-span"><i class="i i-rmb"></i>${mfBusPact.fincAmt}</span><span class="unit-span">万</span> 
						<span  class="content-span"><i class="i i-richengshezhi"></i>${mfBusPact.term}</span><span class="unit-span"><c:if test='${mfBusApply.termType=="1"}' >月</c:if><c:if test='${mfBusApply.termType!="1"}' >天</c:if></span>
						<span  class="content-span"><i class="i i-tongji1"></i><fmt:formatNumber type="number" minFractionDigits="2" value="${mfBusPact.fincRate}" /></span> <span class="unit-span">%</span>
					</p>
				</div>
			</div>
		</c:if>
	</div>
</c:if>
<div class="row clearfix">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>
				

	
								