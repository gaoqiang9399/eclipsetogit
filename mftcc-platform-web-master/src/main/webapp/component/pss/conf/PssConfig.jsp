<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript">
			$(function(){
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
			});
			function saveMsgVar(obj){
				var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {ajaxData : dataParam},
				type : "POST",
				dataType : "json",
				beforeSend : function() {},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
					    alert(top.getMessage("SUCCEED_OPERATION") ,1);
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
			};
			
		</script>
	</head>
		<body class="overflowHidden bg-white">
			<div class="container form-container">
				<div class="scroll-content">
					<div class="col-md-8 col-md-offset-2 column margin_top_20">
						<div class="bootstarpTag">
							<form method="post" id="varForm" theme="simple" name="operform" action="<%=webPath %>/pssConfig/updateAjax">
								<dhcc:bootstarpTag property="formpssconf0002" mode="query"/>
							</form>
						</div>
				</div>
			</div>
				<div class="formRowCenter">
					<dhcc:pmsTag pmsId="pss-config-insert">
						<dhcc:thirdButton value="保存" action="保存" onclick="saveMsgVar('#varForm')"></dhcc:thirdButton>
					</dhcc:pmsTag>
	   			</div>
   		</div>
	</body>
</html>