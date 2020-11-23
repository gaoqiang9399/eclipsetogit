<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
				MfBusTour_List.init();
			});
		</script>
	</head>
	<body class="overflowHidden">
		<div class="track-div padding_top_10">
					<c:if test="${dataMap.size()==0}">
						<div class="message margin_top_10">暂无跟进信息</div>
					</c:if>
					<c:if test="${dataMap.size()!=0}">
						<c:forEach items="${dataMap}" var="cusTrack" varStatus="status">
							<div class="container track-line" id="track-line">
								<div id="track-info" class="track-info">
									<div class="row" style="text-align: left;">
										<span class="track-type-list">
											[${dataMap.opNo}] </span> <span class="padding_0">${dataMap.opName}</span>
										<span class="track-reg-time">登记于 ${dataMap.tourTime} /></span>
									</div>
									<div class="row">
										<div class="track-content">
											${dataMap.tourContext}
										</div>
										<div class="track-opt">
											<a href="javascript:void(0);" id="comment-num"
												data-toggle="collapse"
												data-target="#${cusTrack.trackId}Comment"
												num='${cusTrack.commentList.size()}'>评论(${cusTrack.commentList.size()})
											</a>
										</div>
									</div>
								</div>
		
							</div>
						</c:forEach>
					</c:if>
				</div>
	</body>
</html>
