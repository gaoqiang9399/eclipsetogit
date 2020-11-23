<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		 <script type="text/javascript" src="${webPath}/component/oa/communication/js/MfOaInternalCommunication_AcceptList.js"></script>
		<script type="text/javascript" >
		MfOaInternalCommunication_AcceptList.path = "${webPath}";
			$(function(){
				MfOaInternalCommunication_AcceptList.init();
			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<%--<div class="btn-div">
						<dhcc:pmsTag pmsId="oa-message-insert">
							<button type="button" class="btn btn-primary" id="messageInsert" onclick="MfOaInternalCommunication_AcceptList.messageInsert();">发消息</button>
						</dhcc:pmsTag>
					</div>--%>
					<div class="btn-group">
							<ul class="search-tab" style="margin-top: 10px;margin-left: 14px;">
								<li class="current" style="margin-right: 0px;"  onclick="MfOaInternalCommunication_AcceptList.toMessageAcceptInfoList()">
								收件箱
								</li>
								<li onclick="MfOaInternalCommunication_AcceptList.toMessageSendInfoList()">
								发件箱
								</li>
							</ul>
						</div>
					<div class="search-div" style="margin-left: 169px;margin-top: -43px;">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=发信人/消息内容"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>
	<script type="text/javascript">
	filter_dic = [{
		"optName" : "收信日期",
		"parm" : [],
		"optCode" : "sendDate",
		"dicType" : "date"
	}, {
		"optCode" : "readSts",
		"optName" : "读取状态",
		"parm" : [{"optName":"未读","optCode":"0"},
		{"optName":"已读","optCode":"1"}],
		"dicType" : "y_n"
	}
	];
</script>
</html>
