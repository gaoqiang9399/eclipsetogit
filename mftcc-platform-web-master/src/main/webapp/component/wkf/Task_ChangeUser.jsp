<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<script type="text/javascript">
		$(function(){
			window.parent.reloadFream();
		});
		function regNameCallBack(data){
			$("input[name=regName]").val(data.opName);
			$("input[name=userId]").val(data.opNo);
		}
	</script>
	<body>
		<div class="bigform_content bigform_wrap">
			<div name="formtest-5" class="formDiv">
					<form method="post" theme="simple" name="operform" action="">
						<dhcc:bigFormTag property="formwkf0051" mode="query"/>
						<div class="formRow">
	    					<dhcc:thirdButton value="保存" action="保存" onclick="updateInfo(this.form)"></dhcc:thirdButton>
	    				</div>
					</form>
			</div>
		<script type="text/javascript">
			function updateInfo(obj){
				var flag = submitJsMethod(obj, '');
				if(flag){
					var url = webPath+"/task/reAssignAjax";
					var dataParam = JSON.stringify($(obj).serializeArray()); 
					jQuery.ajax({
						url:url,
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							if(data.flag == "success"){
								 //$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
								 alert(top.getMessage("SUCCEED_OPERATION"),1);
							}else if(data.flag=="error"){
								if(data.flag!==undefined&&data.flag!=null&&data.flag!=""){
									//$.myAlert.Alert(data.msg);
									alert(data.msg,0);
								}else{
									//$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
									alert(top.getMessage("FAILED_OPERATION"," "),0);
								}
							}
						},error:function(data){
							 //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
							 alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				}
			}
		</script>
	</body>
</html>
