<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%
	String wkfAppId = (String)request.getParameter("wkfAppId");
	String taskId = (String)request.getParameter("taskId");
	String appId = (String)request.getParameter("appId");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<script type="text/javascript">
	var wkfAppId='<%=wkfAppId%>';
	var taskId='<%=taskId%>';
	var appId='<%=appId%>';
	window.ajaxRepayment = function(obj,callback){
		var flag = submitJsMethod(obj, '');
		if(flag){
			var contentForm = $(obj).parents(".content_table");
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam,appId:appId},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						  //$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
						  window.top.alert(data.msg,1);
						  $.each(data,function(name,value) {
							   setFormEleValue(name, value);//调用公共js文件的方法表单赋值
						  });
						  top.flag=true;
						  top.putoutFlag =true;
						 myclose_click();
					}
				},error:function(data){
					 LoadingAnimate.stop();
					 //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	}
	</script>
	<body class="overflowAuto body_bg">
		<div class="bigform_content">
			<div class="content_table">
				<form method="post" theme="simple" name="operform" action="${webPath}/mfRepayHistory/repaymentAjax">
					<dhcc:bigFormTag property="formrepay0003" mode="query"/>
					<div class="formRowCenter">
		    			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxRepayment(this.form,myclose)"></dhcc:thirdButton>
		    		</div>
				</form>	
			</div>
		</div>
	</body>
</html>
