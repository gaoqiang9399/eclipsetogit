<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%
String path = request.getContextPath();
%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript">
			$(function(){
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced:{
						updateOnContentResize:true
					}
				});
			});
			
			function doSubmit(obj){
				var flag=submitJsMethod($(obj).get(0), '');
 				if(flag){
					var dataParam = JSON.stringify($(obj).serializeArray());
					jQuery.ajax({
						url : webPath+"/recallBase/submitAjax",
						data : {ajaxData : dataParam},
						success : function(data){
							if(data.flag == "success"){
								window.top.alert(data.msg,1);
								$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",webPath+"/sysTaskInfo/getListPage");
								$(top.window.document).find("#taskShowDialog").remove();
							}else{
								window.top.alert(data.msg,0);
							}
						},
						error : function(data) {
							if(data.flag == "error"){
								window.top.alert(data.msg,0);
							}
						}
					});					
				}
			}
			
		</script>
</head>
 
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<form method="post" id="operform" theme="simple" name="operform">
							<dhcc:bootstarpTag property="formrec0006" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			 <dhcc:thirdButton value="保存" action="保存" onclick="doSubmit('#operform');"></dhcc:thirdButton>
	   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>