<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
String contextPath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+webPath+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/demo/js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="${webPath}/component/demo/js/jquery.serializejson.min.js"></script>
	<style>
		body {
			background: #f7f7f7;
			margin: 10%;
			font-family: "microsoft YaHei"
		}
		.content {
			width: 800px;
			background: #fff;
			padding: 50px;
			margin: auto;
			;
		}
		.formRow {
			margin: 15px 0;
			width: 350px;
		}
		.formLbl {
			color: #333;
			font-size: 12px;
			line-height: 2.3em;
			font-weight: bold;
		}
		input[type="text"],
		input[type="password"],
		input[type="email"],
		input[type="number"],
		input[type="tel"],
		input[type="url"],
		select,
		textarea {
			box-sizing: border-box;
			padding: 7px 8px;
			margin-right: 5px;
			font-size: 13px;
			color: #333;
			vertical-align: middle;
			background-color: #fafafa;
			border: 1px solid #ccc;
			border-radius: 3px;
			outline: none;
			box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.075);
		}
		input[type="text"].focus,
		input[type="text"]:focus,
		.focused .drag-and-drop,
		input[type="password"].focus,
		input[type="password"]:focus,
		input[type="email"].focus,
		input[type="email"]:focus,
		input[type="number"].focus,
		input[type="number"]:focus,
		input[type="tel"].focus,
		input[type="tel"]:focus,
		input[type="url"].focus,
		input[type="url"]:focus,
		select.focus,
		select:focus,
		textarea.focus,
		textarea:focus {
			background: #fff;
			border-color: #51a7e8;
			box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.075), 0 0 5px rgba(81, 167, 232, 0.5);
		}
		.labGroup {
			display: inline-block;
			box-sizing: border-box;
			font-size: 12px;
			color: #333;
			vertical-align: middle;
		}
		.labGroup input[type="checkbox"],
		.labGroup input[type="radio"] {
			vertical-align: middle;
		}
		.col1 {
			width: 100%;
		}
		.col2 {
			width: 48%;
		}
		.col3 {
			width: 30%;
		}
		.formRow input[type="submit"] {
			border-radius: 2px;
			background: #009ce2;
			border: none;
			width: 180px;
			line-height: 34px;
			font-weight: bold;
			font-size: 14px;
			color: #fff;
			margin: 20px 0;
			cursor: pointer;
		}
		.formRow input[type="submit"]:hover {
			background: #08b2ff;
		}
		.formRow input[type="submit"]:focus {
			background: #008ccc;
		}
		.formCol{
			font-size: 12px;
		}
	</style>
	<script type='text/javascript'>
			function submit1(obj){
				var flag = submitJsMethod(obj, '');
				if(flag){
					var dataParam = encodeURI(JSON.stringify($(obj).serializeJSON())); 
					jQuery.ajax({
						url:webPath+"/demoTest/insertAjax",
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							 alert(top.getMessage("SUCCEED_OPERATION"));
							  $.each(data,function(name,value) {
								  setValue(name, value);
							  });
							   alert("操作成功！,并刷新成功");
						},error:function(data){
							alert(top.getMessage("FAILED_OPERATION"," "));
							
						}
					});
				}
			}
			function submitBtn(obj){
				var flag = submitJsMethod(obj, '');
				if(flag){
					var dataParam = encodeURI(JSON.stringify($(obj).serializeJSON())); 
					jQuery.ajax({
						url:webPath+'/testGn/insertAjax',
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						success:function(data){
							if(data.msg=="error"){
								sAlert(  top.getMessage("FAILED_SAVE"));
							}else{
								sAlert(top.getMessage("SUCCEED_SAVE"));
								 //刷新页面
								 $.each(data,function(name,value) {
								   setFormEleValue(name, value);//调用公共js文件的方法表单赋值
							  	 });
							}
						},error:function(data){
							sAlert(  top.getMessage("FAILED_SAVE"));
						}
					});
				}
				//$("#screenLockDiv").hide();
			}
			
		function ajaxInsert(obj){
			var dataParam = JSON.stringify($(obj).serializeJSON()); 
			jQuery.ajax({
				url:webPath+"/demoTest/insertAjax",
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						 alert(top.getMessage("SUCCEED_OPERATION"));
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "));
				}
			});
		}
		function ajaxUpdate(obj){
			var dataParam = JSON.stringify($(obj).serializeJSON()); 
			jQuery.ajax({
				url:webPath+"/demoTest/upateAjax",
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						 alert(top.getMessage("SUCCEED_OPERATION"));
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "));
				}
			});
		}
		function ajaxRefurbish(obj){
			jQuery.ajax({
				url:webPath+"/demoTest/getByIdAjax",
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						 alert(top.getMessage("SUCCEED_OPERATION"));
						 $.each(data.demo,function(name,value) {
						  setValue(name, value);
					    });
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "));
				}
			});
		}
		</script>
	</head>
	<body>
	<div class="content">
		<form  method="post" theme="simple" name="operform"
			action="${webPath}/demoTest/insert">
			<dhcc:formTag property="formdemo0005" mode="query"/>
			<div class="formRow">
    			<input type="submit" value="提交"/>
    			<input type="button" value="ajax提交" onclick="ajaxInsert(this.form)" />
    			<input type="button" value="ajax更新" onclick="ajaxUpdate(this.form)" />
    			<input type="button" value="ajax刷新" onclick="ajaxRefurbish(this.form)" />
    		</div>
		</form>	
	</div>
	</body>
</html>
