<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#dataTree.ztree li span.button{
		opacity: 0.5;
		margin-top: 0px;
	}
	#dataTree {
		height: 355px;
		overflow: auto;
	}
	
</style>
</head>
<body class="bg-white">
	<div class="container">
		<div class="row clearfix">
			<div class="col-xs-12 column" id="ztreeDiv">
				<div>
					<ul id="dataTree" class="ztree"></ul>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-xs-12 column">
				<div style="text-align: center; margin-top: 5px;">
					<button id="noticeScorpConfirm" type="button"
						class="btn btn-primary pull-center"
					>确认</button>
				</div>
			</div>
		</div>
	</div>
</body>

<!-- 滚动轴 -->
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.min.css" />
<!--zTree  -->
<script type="text/javascript" src="${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/zTree/metroStyle/metroStyle.css" />
<script type="text/javascript" src="${webPath}/component/oa/notice/js/MfOaNoticeScorp.js"></script>
<script type="text/javascript" >
	var ajaxData = '${ajaxData}';
	ajaxData = eval(ajaxData);
	var zNodes = ajaxData;
	
	$(document).ready(function() {
		OaNoticeScorp.init(zNodes);
		OaNoticeScorp.bindScorpConfirm();
	});
	
</script>
</html>
