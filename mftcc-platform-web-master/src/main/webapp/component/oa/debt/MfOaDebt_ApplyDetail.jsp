<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	    
	    
		<title>详情</title>
		<script type="text/javascript">
		    var debtId = '${debtId}';
	        
	        function doSubmit(obj){
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					var opinionType = $("select[name=opinionType]").val();
					var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
					commitProcess(webPath+"/mfOaDebt/submitForUpdate?opinionType"+opinionType+"&debtId="+debtId,obj,'debtSP');
				}				
			}
		    
					
		</script>
	</head>
	<%-- <body class="body_bg">
		<div class="bigform_content">
			<div class="bigForm_content_form">
				<div class="form_title">请假申请表</div>
				<s:form method="post" theme="simple" name="operform" action="">
					<dhcc:formTag property="formoaleave0001" mode="query" />
				</s:form>	
			</div>
		</div>
	</body> --%>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="edit-form" theme="simple" name="operform" >
						<dhcc:bootstarpTag property="formdebt00003" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter showOrHide">
				 <input type="button" value="提交" onclick="doSubmit('#edit-form');">
		</div>
	</div>
</body>
<%-- <script type="text/javascript" src="${webPath}/component/oa/leave/js/MfOaLeaveApplyDetail.js"></script> --%>
<script type="text/javascript">
OaDebt.path = "${webPath}";
		$(function() {
			OaDebt.init();  
		});	
	</script>
</html>