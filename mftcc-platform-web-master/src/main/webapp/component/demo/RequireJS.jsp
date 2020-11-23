<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="css/MfOaEntrance.css" />
	<script type="text/javascript" src="${webPath}/UIplug/ie8/js/json2.js"></script>
</head>
<body>
	<div class="container box">
		<div class="btn btn-app" id="notice">
			<span class="badge"><i class="fa fa-pulse"><i class="fa fa-spinner"></i></i></span>
			<img alt="通知公告" src="images/OA_2.png" />
			<div>通知公告</div>
		</div>
		<div class="btn btn-app" id="schedule">
			<span class="badge"><i class="fa fa-pulse"><i class="fa fa-spinner"></i></i></span>
			<img alt="日程管理" src="images/OA_3.png" />
			<div>日程管理</div>
		</div>
		<div class="btn btn-app" id="trace">
			<span class="badge"><i class="fa fa-pulse"><i class="fa fa-spinner"></i></i></span>
			<img alt="客户营销" src="images/OA_4.png" />
			<div>客户营销</div>
		</div>
		<div class="btn btn-app" id="leave">
			<span class="badge"><i class="fa fa-pulse"><i class="fa fa-spinner"></i></i></span>
			<img alt="请假申请" src="images/OA_5.png" />
			<div>请假申请</div>
		</div>
		<div class="btn btn-app" id="borrow">
			<span class="badge"><i class="fa fa-pulse"><i class="fa fa-spinner"></i></i></span>
			<img alt="借款申请" src="images/OA_1.png" />
			<div>借款申请</div>
		</div>
		<div class="btn btn-app" id="reimbursement">
			<span class="badge"><i class="fa fa-pulse"><i class="fa fa-spinner"></i></i></span>
			<img alt="报销申请" src="images/OA_9.png" />
			<div>报销申请</div>
		</div>
		<div class="btn btn-app" id="consumable">
			<span class="badge"><i class="fa fa-pulse"><i class="fa fa-spinner"></i></i></span>
			<img alt="低值易耗品" src="images/OA_6.png" />
			<div>低值易耗品</div>
		</div>
		<div class="btn btn-app" id="wage">
			<span class="badge"><i class="fa fa-pulse"><i class="fa fa-spinner"></i></i></span>
			<img alt="薪酬管理" src="images/OA_7.png" />
			<div>薪酬管理</div>
		</div>
		<div class="btn btn-app" id="robot">
			<span class="badge"><i class="fa fa-pulse"><i class="fa fa-spinner"></i></i></span>
			<img alt="网络爬虫" src="images/OA_8.png" />
			<div>网络爬虫</div>
		</div>
		<div class="btn btn-app" id="bbs">
			<span class="badge"><i class="fa fa-pulse"><i class="fa fa-spinner"></i></i></span>
			<img alt="论坛管理" src="images/OA_10.png" />
			<div>论坛管理</div>
		</div>
		<div class="btn btn-app" id="personnel">
			<span class="badge"><i class="fa fa-pulse"><i class="fa fa-spinner"></i></i></span>
			<img alt="人事管理" src="images/OA_11.png" />
			<div>人事管理</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="js/MfOaEntrance.js"></script>
<script type="text/javascript">
	// 接收传参等
	OaEntrance.path = "${webPath}";
	$(function() {
		OaEntrance.init();
	});
</script>
</html>