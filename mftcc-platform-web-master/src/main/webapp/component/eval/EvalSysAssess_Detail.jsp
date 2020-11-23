<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript"  src='${webPath}/component/include/uior_val1.js'> </script>
		<script type="text/javascript">
		$(function(){
// 			$(".scroll-content").mCustomScrollbar({
// 				advanced:{
// 					theme:"minimal-dark",
// 					updateOnContentResize:true
// 				}
// 			});
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
		});
		
		function ajaxUpdateThis(obj){
			var flag = submitJsMethod($(obj).get(0), '');
			if(flag){
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				jQuery.ajax({
						url:url,
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							LoadingAnimate.stop();
							if(data.flag == "success"){
								window.top.alert(data.msg, 1);
							 	//var url="${webPath}/evalSysAssess/getListPage";
								//$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
								myclose_click();
							}
						},error:function(data){
							LoadingAnimate.stop();
							 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				
				}
		
		}
		
		</script>
	</head>
	<body class="body_bg overFlowHidden">
	<div class="mf_content">
		<div class="content-box">
		 	<p class="tip"><span>说明：</span>带*号的为必填项信息，请填写完整。</p>
			<div class="tab-content">
				<form  method="post" id="evalAssessUpdateForm" theme="simple" name="operform" action="${webPath}/evalSysAssess/updateAjax">
					<dhcc:bootstarpTag property="formeval4001" mode="query" />
				</form>
			</div>
		</div>
	</div> 
	<div class="formRowCenter">
		<dhcc:thirdButton value="保存" action="保存" onclick="ajaxUpdateThis('#evalAssessUpdateForm');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	</div>
	</body>
</html>
