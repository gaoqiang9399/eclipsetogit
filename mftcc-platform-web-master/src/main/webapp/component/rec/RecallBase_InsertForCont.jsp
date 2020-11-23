<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%
String conNo=(String)request.getParameter("conNo")==null?"":(String)request.getParameter("conNo");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<style>
		.formRow input[type="submit"], .formRow input[type="button"]{
		position:absolute;
		left:400px;
		}
		</style>
	</head>
	<body class="body_bg">
		<div class="bigform_content " style="margin-top: 60px;">
			<div class="bigForm_content_form">
				<form method="post" theme="simple" name="operform" action="${webPath}/recallBase/updateAjax">
					<dhcc:formTag property="formrec0008" mode="query" />
					<div class="formRow" >
						<div style="position:relative;left:10px">
						<dhcc:thirdButton value="保存" action="保存"  onclick="insertInfo(this.form)"></dhcc:thirdButton>
						</div>
		    		</div>
	    		</form>
			</div>
		</div>
		<script>

		function taskNameCallBack(data){
			$("input[name=taskNo]").val(data.taskNo);
		}
		
		function sysUserCallBack(data){//个人的回调
			$("input[name=regNo]").val(data.opNo);
		}
		function insertInfo(obj){
			var flag = submitJsMethod(obj, '');
			if(flag){
				var contentForm = $(obj).parents(".content_table");
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray()); 
				jQuery.ajax({
					url:url,
					data:{ajaxData:dataParam},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						if(data.flag == "success"){
							$.myAlert.Choose( top.getMessage("SUCCEED_OPERATION") ,"提示消息" ,["确认" ],[function(){
								window.top.window.cloesShowDialog();
                          }], false);
						 
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