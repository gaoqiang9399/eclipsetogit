<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="app.component.alliance.entity.MfBusAlliance" %>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<%
	String allianceId = (String)request.getParameter("allianceId");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<!--头部主要信息 -->
	<div class="row clearfix head-info">
		<!--头像 -->
		<div class="col-xs-3 column text-center head-img padding_top_20">
			<div style="position:relative;">
				<button type="button" class="btn btn-font-app padding_left_15 font-app-div">
					<i class="i i-applyinfo font-icon"></i>
					<div class="font-text-left">联保体</div>
				</button>
			</div>
		</div>

		<!--概要信息 -->
		<div class="col-xs-9 column head-content">
			<div class="row clearfix">
				<div class="col-xs-10 column">
					<div class="margin_bottom_5">
						<%--
						<button class="btn btn-link cus head-title" >${alliance.allianceName}</button>
						--%>
						<div class="cus head-title">${alliance.allianceName}</div>
					</div>
					<!--信息查看入口 -->
					<div class="margin_bottom_10">

					</div>
					<div>
						<p>
							<span><i class="i i-ren1 "></i>
								<span id="contactsName">
									<c:if test="${alliance.allianceLeaderName!=null&&alliance.allianceLeaderName!=''}">
										${alliance.allianceLeaderName}
									</c:if>
									<c:if test="${alliance.allianceLeaderName==null|| alliance.allianceLeaderName==''}">
										未登记
									</c:if>
								</span>
							</span>
							<span class="vertical-line"></span>
							<span><i class="i i-dianhua "></i>
								<span id="contactsTel">
									<c:if test="${alliance.contactInformation!=null&&alliance.contactInformation!=''}">
										${alliance.contactInformation}
									</c:if>
									<c:if test="${alliance.contactInformation==null||alliance.contactInformation==''}">
										未登记
									</c:if>
								</span>
							</span>
						</p>
					</div>
					<div>

						<table>
							<tbody><tr>
								<%--<td>
									<p class="cont-title">授信额度</p>
									<p><span id="loanLimit" class="content-span"><fmt:formatNumber value="${alliance.loanLimit}" pattern="#,#00.00"/></span><span class="unit-span margin_right_25">元</span> </p>
								</td>
								<td>
									<p class="cont-title">可用额度</p>
									<p><span id="availableBalance" class="content-span"><fmt:formatNumber value="${alliance.availableBalance}" pattern="#,#00.00"/></span><span class="unit-span margin_right_25">元</span></p>
								</td>--%>
								<td>
									<p class="cont-title">到期日</p>
									<p><span id="validTerm" class="content-span">

										<fmt:parseDate value="${alliance.validEndDate}" pattern="yyyyMMdd" var="validEndDate"/>
										<fmt:formatDate value="${validEndDate}" pattern="yyyy-MM-dd" />
									</span><span class="unit-span"></span></p>
								</td>
							</tr>
							</tbody></table>
					</div>
				</div>
				<div class="col-xs-2 column">
					<div class="i i-warehouse cus-type-font">
						<div class="type-name-div">联保体</div>
					</div>
				</div>
			</div>

		</div>
	</div>
