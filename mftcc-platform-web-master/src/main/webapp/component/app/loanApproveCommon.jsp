<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/component/include/common.jsp"%>
<%
String appId = (String)request.getParameter("appId");
String cusType = (String)request.getParameter("cusType");
String path = request.getContextPath();
%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<script type="text/javascript" src='${webPath}/component/app/js/loanApproveCommon.js'> </script>
<script type="text/javascript"
	src='${webPath}/component/app/js/mfbusInitFeeInfo.js'></script>
<!DOCTYPE>
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript">
			var appId = '<%=appId%>';
			var cusNo = '${cusNo}';
			var scNo = '${scNo}'
			var docParm = "cusNo="+cusNo+"&relNo="+cusNo+"&scNo="+scNo+"&appId="+appId;//查询文档信息的url的参数
			var termType = '${mfBusApply.termType}';
			$(function(){
				viewPointApprove.init();
				feeInfo.init();
			});
			
			function updatePactEndDate(){
				viewPointApprove.updatePactEndDate();
			}
		</script>
</head>
 
<body  class="overflowHidden bg-white">
	<div class="container form-container">
		<div id="infoDiv" style="display:block;height:100%;">
			<iframe src="${webPath}/mfBusApply/getSummary?appId=${appId}&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
		</div>
		<div id="approvalDiv" class="scroll-content" style="display:none;">
			<div class="col-md-8 col-md-offset-2 column margin_top_20" >
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="edit-form">
						<dhcc:bootstarpTag property="formapply0003" mode="query" />
					</form>	
					
					 <c:if  test="${fn:length(pageHelper.list)>0}">
					    <div class="feeInfo">
							<div class="list-table margin_0">
							<div class="title">
								<span>
								<i class="i i-xing blockDian"></i>
								费用标准</span>
							</div>
								<div class="content_table" id="busfee-div">
									<dhcc:tableTag property="tablebusfee0002" paginate="mfBusAppFeeList" head="true"></dhcc:tableTag>
								</div>
							</div>
						</div>
				    </c:if>
				</div>
				<div class="row clearfix">
					<div class="col-xs-12 column" >
						<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
					</div>
				</div>
			</div>
		</div>
		<div id="approvalBtn" class="formRowCenter " style="display:block;">
			<dhcc:thirdButton value="审批" action="审批" onclick="viewPointApprove.getApprovaPage();"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
		<div id="submitBtn" class="formRowCenter" style="display:none;">
			<dhcc:thirdButton value="提交" action="提交" onclick="viewPointApprove.doSubmit('#edit-form');"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="viewPointApprove.approvalBack();"></dhcc:thirdButton>
		</div>
	</div>
	<input name="taskId" id="taskId" type="hidden" value=${taskId}/> 
	<input name="activityType" id="activityType" type="hidden" value=${activityType} 
	<input name="isAssignNext" id="isAssignNext" type="hidden" value=${isAssignNext}
	<input name="transition" type="hidden" />
	<input name="nextUser" type="hidden" />
	<input name="designateType" type="hidden" value=${designateType} 
</body>
</html>