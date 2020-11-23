<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ include file="/component/include/common.jsp"%>
<%
String id = (String)request.getParameter("id");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
<%-- <script type='text/javascript' src='${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js'></script> --%>
<%-- <link rel="stylesheet" href="${webPath}/UIplug/zTree/zTreeStyle/zTreeStyle.css" /> --%>
<%-- <link rel="stylesheet" href="${webPath}/component/wkf/css/wkf_approval.css" /> --%>
<%-- <script type="text/javascript" src="${webPath}/component/include/wkfApproveIdea.js"></script> --%>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js"></script>
		
	</head>
<body class="overflowHidden bg-white" >
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="row clearfix">
					<div class="form-table">
						<div class="content margin_left_15 ">
<%-- 							<iframe src='tech/wkf/processDetail.jsp?appNo=${id}' --%>
<!-- 								marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" height="35%" id="processDetailIframe"> -->
<!-- 							</iframe> -->
<!-- 							<ul id="wfTree" class="ztree"> -->
<!-- 							</ul> -->
								<div class="approval-process">
									<div id="modeler1" class="modeler">
										<ul id="wj-modeler2" class="wj-modeler" isApp = "false">
										</ul>
									</div>
								</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/oa/trans/js/MfOaTransGetTransAppHis.js"></script>
<script type="text/javascript">
var id = '<%=id%>';
OaTrans.path = '${webPath}';
	$(function() {
		OaTrans.init();
	});
</script>
</html>
