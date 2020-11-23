<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript">
			$(function(){
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						theme:"minimal-dark",
// 						updateOnContentResize:true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
			});
			//更新操作
			function ajaxUpdate(formObj){
				var flag = submitJsMethod($(formObj).get(0), '');
				if(flag){
					var url = $(formObj).attr("action");
					var dataForm = JSON.stringify($(formObj).serializeArray());
					LoadingAnimate.start();
					$.ajax({
						url:url,
						data:{ajaxData:dataForm},
						type:"post",
						dataType:"json",
						success:function(data){
							LoadingAnimate.stop();
							if(data.flag == "success"){
								window.top.alert(data.msg,1);
								$(top.window.document).find("#bigFormShow .close").click();
							}else{
								alert(data.msg,0);
							}
						},
						error:function(data){
							loadingAnimate.stop();
							alert("更新操作发生异常",0);
						}
					});
				}
			}
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
				 	<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" theme="simple" name="operform" id="operform" action="${webPath}/mfDynamicForm/updateAjax">
							<dhcc:bootstarpTag property="formcredit0002" mode="query"/>
						</form>
						</div>
					</div>
				</div>
			
			<div class="formRowCenter">
				 <dhcc:thirdButton value="保存" action="保存" onclick="ajaxUpdate('#operform')"></dhcc:thirdButton>
				 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>