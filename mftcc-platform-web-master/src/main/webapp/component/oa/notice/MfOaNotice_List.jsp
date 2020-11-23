<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	    <link rel="stylesheet" href="${webPath}/component/oa/notice/css/MfOaNoticeList.css" />
	</head>
	<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<c:choose>
					<c:when test="${menuNo==80}">
						<div class="btn-div">
							<%--<dhcc:pmsTag pmsId="oa-release-notice-btn">--%>
								<button type="button" class="btn btn-primary" id="noticeInsert">发布产品</button>
							<%--</dhcc:pmsTag>--%>
						</div>
					</c:when>
					<c:otherwise>
						<div class="btn-div">
							<%--<dhcc:pmsTag pmsId="oa-release-notice-btn">--%>
								<button type="button" class="btn btn-primary" id="noticeInsert">发布公告</button>
							<%--</dhcc:pmsTag>--%>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="notice-div " >
			<table class="notice-list">
				<tbody>
					<c:forEach var="mfOaNotice" items="${mfOaNoticeList}" varStatus="sts" >
						<tr	class="<c:if test='${sts.index%2 eq 0}'>nth-child-even</c:if><c:if  test='${sts.index%2 ne 0}'>nth-child-odd</c:if>">					
							<c:if test='${mfOaNotice.noticeSts ne "1"}'>
							<td class="td-classify-first padding_left_0">
								<div class="text_center">
								<i class="i i-warehouse noticeSts-font ">
									<div class="noticeSts-font-div">未发布</div>
								</i>
								</div>
							</td>
							</c:if>
							<c:if test='${mfOaNotice.noticeSts eq "1"}'>
							<td class="td-classify-first padding_left_0 text_center">
								<div  class=" font_size_14">
									<span class="marginNone">${mfOaNotice.publishTime}</span>
								</div>
							</td>
							</c:if>
							<td >
								<div>
									<c:if test='${mfOaNotice.isTop ne "0"}'>
										<span>
											<button class="btn-top">重要</button>
										</span>
									</c:if>
									<span>
										<button class="button-title button-notice" name ="${mfOaNotice.noticeSts}" value="${mfOaNotice.noticeId}">
											${mfOaNotice.noticeTitle}										    
										</button>
									</span>
									<span class="margin-left-20px">发布人：${mfOaNotice.opName}</span>
								</div>
								<div class="td-span">
									<span>
										${mfOaNotice.contentAbstract}
									</span>
								</div>
							</td>
							<td class="notice-operation">
								<div class="operation-notice">
									<c:if test='${mfOaNotice.noticeSts ne "1"}'>
										<button type="button" value="${mfOaNotice.noticeId}" class="button-small button-notice noticePublish">发布</button>
										<button type="button" value="${mfOaNotice.noticeId}" class="button-small button-notice delete">删除</button>
									</c:if>
									<c:if test='${mfOaNotice.noticeSts eq "1"}'>
										<%--<dhcc:pmsTag pmsId="oa-look-notice-btn">--%>
											<button type="button" value="${mfOaNotice.noticeId}" class="button-small button-notice noticeLooking">查阅情况</button>
										<%--</dhcc:pmsTag>--%>
									</c:if>
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
  <script type="text/javascript" src="${webPath}/component/oa/notice/js/MfOaNoticeList.js"></script>
<script type="text/javascript">
OaNoticeList.path = "${webPath}";
var menuNo = '${menuNo}';
		$(function() {
			OaNoticeList.init(); 
		});	
          </script> 
</html>


