<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/component/include/pub_view_table.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet"
	href="${webPath}/component/oa/notice/css/MfOaNoticeList.css" />
<style type="text/css">
	.list-min-div{
		padding: 0px;
	}
 	/* .footer_loader{
		 position: absolute; 
		 bottom: -50px; 
	}  */
 </style>
<script type="text/javascript">
	$(function() {
		MfToolsSendMessageInsert.init();		
	});
</script>
</head>
<style type="text/css">
	.current1 {
		color: #32b5cb;
		cursor: pointer;
	}
	
	.current1:hover {
		color: #009db7;
	}
	
	.title-div {
		margin: 10px 0px 0px 15px;
		font-size: 14px;
		cursor: pointer;
		height: 40px;
	}
</style>
<body class="bg-white overflowHidden">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20 padding_bottom_60">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="MfToolsSendMessageInsert" theme="simple" name="operform" action="${webPath}/mfToolsSendMessage/insertAjax">
						<dhcc:bootstarpTag property="formtools0001" mode="query" />
					</form>
				</div>
				<div class="formRowCenter background-border-none">
					<dhcc:thirdButton value="发送" action="发送" typeclass="insertAjax"></dhcc:thirdButton>
					<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
				</div>
			</div>
			<div align="center" class="col-md-8 col-md-offset-2 margin_top_10">
					<div id="list-div" style="width: 100%; height: auto">
						<div class="list-min-div">
							<table class="list-div-table" id="tablist">
								<tbody>
									<c:forEach items="${mfToolsSendMessageList }" var="statu" varStatus="status">
										<%-- <tr	class="<c:if test='#{ statu.even}'>nth-child-odd</c:if><c:if>nth-child-even</c:if>"> --%>
											<tr>
											<td class="td-first">
												<div class="text-right">
													<p class="marginNone">
														${statu.sendTime.substring(0,10)}
													</p> 
													<p class="padding-right-20px">
														${statu.sendTime.substring(11)}
													</p>
												</div>
											</td>
											<td class="td-second">
												<div class="td-second-div ">
													<c:if test="${statu.sendSts==1}">
														<span class="title-color" id="${statu.id}"
															onclick="getbyid(this)"> 成功 </span>
													</c:if>
													<c:if test="${statu.sendSts!=1}" >
														<span class="title-color" style="color: #D9534F;"
															id="${statu.id}" onclick="getbyid(this)">
															失败 </span>
													</c:if>
													<span class="margin-left-20px">发信人：${statu.opName}</span>
													<c:if test="${statu.receiverName.length()>30}">
														<span mytitle="${statu.receiverName}" class="margin-left-20px">收信人：${statu.receiverName}...
														</span>
													</c:if>
													<c:if test="${statu.receiverName.length()<=30}">
														<span class="margin-left-20px">收信人：${statu.receiverName}.</span>
													</c:if>
												</div>
												<p mytitle="${statu.sendMsg}">
													<c:if test="${statu.sendMsg.length()>100}">
														${statu.sendMsg.substring(0,100)}……
								 	  				</c:if>
								 	  				<c:if test="${statu.sendMsg.length()<=100}">
														${statu.sendMsg}
								 	  				</c:if>
												</p>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					</div>
				</div>
			</div><!--container-->
					<div class="footer_loader">
						<div class="pagerShow">
							当前显示&nbsp; <span class="loadCount"></span>&nbsp;条数据，一共&nbsp; <span class="pageCount"></span>&nbsp;条数据
						</div>
						<div class="backToTop"></div>
					</div>
</body>
<script type="text/javascript" src="${webPath}/component/tools/js/MfToolsSendMessageInsert.js"></script>
</html>