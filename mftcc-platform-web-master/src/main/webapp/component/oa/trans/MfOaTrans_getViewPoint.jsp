<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
String path = request.getContextPath();
String id = (String)request.getParameter("id");
String jsonBean = (String)request.getAttribute("jsonBean");
%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript">
			var id = '<%=id%>';
		</script>
	</head>
<body class="overflowHidden bg-white">
		<div class="inner-center">
			<div class="scroll-content showOrHide">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag " >
						<form method="post" id="OaTransInsert" theme="simple" name="operform" action="<%=webPath %>/mfOaTrans/updateAjax">
							<dhcc:bootstarpTag property="formoatrans0003" mode="query" />
						</form>
					</div>
					<div class="tabCont">
					</div>
					<div class="row clearfix form-table margin_0">
							<div class="title">
								<span><i class="i i-xing blockDian"></i>移交明细</span>
								<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfoatransList">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
							</div>
						<div id="mfoatransList" class="table_content margin_top_10 collapse in" >
							<dhcc:tableTag paginate="mfOaTransList" property="tableoatrans0002" head="true" />
						</div> 		
					</div>
					<div class="list-table margin_0 margin_top_10 approval-hist">
							<div class="title">
								<span><i class="i i-xing blockDian"></i>审批信息</span>
								<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#approHis">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
							</div>
							<div class="content margin_left_15 collapse in" id="approHis">
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
			<div class="formRowCenter showOrHide">
				<dhcc:thirdButton value="提交" action="提交" typeclass ="insertAjax" ></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
			</div>
			<iframe src="" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" id="iframepage" name="iframepage"></iframe>
		</div>
</body>
<script type="text/javascript" src="${webPath}/component/oa/trans/js/MfOaTransGetViewPoint.js"></script>
<script type="text/javascript">
	$(function() {
    	MfOaTransGetViewPoint.init("${id}");
    	showWkfFlowVertical($("#wj-modeler2"),id, "","");
	});
</script>
</html>
