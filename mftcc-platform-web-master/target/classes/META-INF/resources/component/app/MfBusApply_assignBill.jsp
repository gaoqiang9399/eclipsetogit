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
				advanced:{
					theme:"minimal-dark",
					updateOnContentResize:true
				}
			});
		});
		function assignBill(obj){
			var url = $(obj).attr("action");
			var flag=submitJsMethod($(obj).get(0), '');
			if(flag){
				$("input[name='brName']").val($("select[name=brNo]").find("option:selected").text());
				var dataParam = JSON.stringify($(obj).serializeArray()); 
				LoadingAnimate.start();
				$.ajax({
					url:url,
					data:{ajaxData:dataParam},
					type:'post',
					dataType:'json',
					success:function(data){
						LoadingAnimate.stop();
						if(data.flag=="success"){
							alert("分单成功",1);
							window.location.href = webPath+'/mfBusApply/getOnlineList';
						}else{
							alert(  top.getMessage("FAILED_SAVE"),0);
						}
					},error:function(){
						LoadingAnimate.stop();
						alert(  top.getMessage("FAILED_SAVE"),0);
					}
			});
			}else{
				alert(  top.getMessage("FAILED_SAVE"),0);
			} 
		
		}
		
		</script>
	</head>
	<body class=" overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
	            		<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="cusInsertForm" theme="simple" name="operform" action="${webPath}/mfBusApply/assginBillAjax">
							<dhcc:bootstarpTag property="formapply0009" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="assignBill('#cusInsertForm');"></dhcc:thirdButton>
	   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
