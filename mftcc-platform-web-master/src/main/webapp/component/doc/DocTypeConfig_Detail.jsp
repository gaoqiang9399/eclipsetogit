<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
			<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
			
				<form  method="post" id="docTypeInsert" theme="simple" name="operform" action="${webPath}/docTypeConfig/updateAjax">
					<dhcc:bootstarpTag property="formdoc0005" mode="query" />
				</form>
			</div>
		</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertThis('#docTypeInsert');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
	</body>
	<script type="text/javascript">
	$(function() {
// 		$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						theme:"minimal-dark",
// 						updateOnContentResize:true
// 					}
// 		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	});
	function ajaxInsertThis(obj){
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
						  top.addFlag = true;
						  if(data.htmlStrFlag == "1"){
							  top.htmlStrFlag = true;
							  top.htmlString = data.htmlStr;
						  }
						  myclose_click();
						  
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					LoadingAnimate.stop(); 
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
	</script>
</html>