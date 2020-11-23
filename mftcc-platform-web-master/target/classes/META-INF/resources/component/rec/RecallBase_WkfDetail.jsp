<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
String path = request.getContextPath();
String taskNo = request.getParameter("taskNo");
%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		
		<style type="text/css">
			.inner-center{
				/* height : calc(100% - 150px) !important; */
			}
			.inner-north,.inner-center{
				width : calc(100% - 25px) !important;
			}
			.inner-center span{
				margin-left: 30px;
				color: #32b5cb;
				font-size: 14px;
			}
			th{
				text-align:center;
			}
			.content-box{
				width: 80%;
			}
		</style>
		<script type="text/javascript">
			$(function(){
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						updateOnContentResize:true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
			});
			
			function doSubmit(obj){
				var dataParam = JSON.stringify($(obj).serializeArray()); 
				var ajaxUrl = webPath+"/recallBase/submitUpdateAjax";
				
				jQuery.ajax({
					url : ajaxUrl,
					data : {
						ajaxData : dataParam
					},
					type : "POST",
					dataType : "json",
					beforeSend : function() {
					},
					success : function(data){
						if(data.flag == "success"){
							window.top.alert("操作成功！",1);
							$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src","factor/recallBase/getListPageBySelf");
							$(top.window.document).find("#showDialog").remove();
						}else{
							window.top.alert("操作失败！",0);
						}
					},
					error : function(data) {
						if(data.flag == "error"){
							window.top.alert("操作失败！",0);
						}
					}
				});
			}
			
			function closeMyDialog(){
				$(top.window.document).find("#showDialog").remove();
			}
		</script>
</head>
 
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<form method="post" id="edit-form" theme="simple" name="edit-form">
							<dhcc:bootstarpTag property="formrec0010" mode="query" />
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			 <dhcc:thirdButton value="保存" action="保存" onclick="doSubmit('#edit-form');"></dhcc:thirdButton>
				 <dhcc:thirdButton value="关闭" action="关闭" onclick="closeMyDialog();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
