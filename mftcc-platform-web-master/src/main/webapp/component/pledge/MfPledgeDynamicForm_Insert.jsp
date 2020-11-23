<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
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
		window.ajaxInsert = function(obj){
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
							window.top.alert(data.msg,1);
							myclose_click();
						}
					},error:function(data){
						 alert(top.getMessage("FAILED_OPERATION"," "),0);
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
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" theme="simple" name="operform" id="operform" action="<%=webPath %>/mfPledgeDynamicForm/insertAjax">
							<dhcc:bootstarpTag property="formpledy0002" mode="query"/>
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
					  <dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsert('#operform')"></dhcc:thirdButton>
					   <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
