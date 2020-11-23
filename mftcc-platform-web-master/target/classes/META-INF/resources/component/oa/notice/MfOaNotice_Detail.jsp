<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript"  src='${webPath}/component/include/uior_val1.js'> </script>
<%-- 		<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/umeditor-dev/themes/default/_css/umeditor.css"/> --%>
<%-- 		<script type="text/javascript" src='${webPath}/UIplug/umeditor-dev/umeditor.config.js'></script> --%>
<%-- 		<script type="text/javascript" src='${webPath}/UIplug/umeditor-dev/editor_api.js'></script> --%>
	</head>
	<body class="bg-white overflowHidden">
	<div class="container">
		<div class="row clearfix">
			<div class="col-sm-12 column" >
				<div style="height:80px; text-align:center;">  
					<div style=" padding :10px 10px;color:#D40A0A;  font-size:24px;"><span>${mfOaNotice.noticeTitle}</span></div>  
					<div style="color:#8c8c8c; ">发布人:<span>${mfOaNotice.opName}</span>&nbsp;发布时间:<span>${fn:substring(mfOaNotice.publishTime,0,4)}-${fn:substring(mfOaNotice.publishTime,4,6)}-${fn:substring(mfOaNotice.publishTime,6,17)}</span></div>
				</div>
			</div>	
		</div>
		<div class="row clearfix mf_content">
			<div class="col-sm-12 column">
				<div class="noticeContent" id="noticeDivContent" style ="margin:20px 160px;">

					${mfOaNotice.noticeContent}
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/oa/notice/js/MfOaNoticeDetail.js"></script>
<script type="text/javascript">
	$(function() {
		OaNoticeDetail.init();
	});	
</script>
</html>