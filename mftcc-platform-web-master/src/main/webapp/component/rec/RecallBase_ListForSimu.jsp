<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
	String cusNo = request.getParameter("cusNo");
%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>人工增加催收任务</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>	
		<script type="text/javascript" src="${webPath}/component/rec/js/recallBase_Pers.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" >
			$(function(){
// 				$(".scroll-content").mCustomScrollbar({
// 				advanced:{
// 					theme:"minimal-dark",
// 					updateOnContentResize:true
// 				}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
				$("select[name=recallWay] option[value=1]").remove();
			});
			function doSubmit(obj){
				var dataParam = JSON.stringify($(obj).serializeArray()); 
				var ajaxUrl = webPath+"/recallBase/insertForSimuAjax";
				
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
							//$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src","factor/RecallBaseAction_getListPage.action");
							myclose_click();
						}else{
							window.top.alert(data.msg,0);
						}
					},
					error : function(data) {
						if(data.flag == "error"){
							window.top.alert("操作失败！",0);
						}
					}
				});

			}
			function returnChoose(){
				window.location.href=webPath+"/recallBase/openConSelectTableList";
			}
			function closeMyDialog(){
				//$(top.window.document).find("#showDialog").remove();
				myclose_click();
			}
		</script>
	</head>
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="edit-form" theme="simple" name="edit-form">
							<dhcc:bootstarpTag property="formrec0013" mode="query" />
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			 <dhcc:thirdButton value="保存" action="保存" onclick="doSubmit('#edit-form');"></dhcc:thirdButton>
				 <!--<dhcc:thirdButton value="返回" action="返回" onclick="returnChoose();"></dhcc:thirdButton>-->
				 <dhcc:thirdButton value="关闭" action="关闭" onclick="closeMyDialog();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>